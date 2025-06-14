package org.lw.vms.controller;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import io.jsonwebtoken.Claims;
import org.lw.vms.DTOs.OrderAssignmentUpdateRequest;
import org.lw.vms.DTOs.RepairOrderRequest;
import org.lw.vms.DTOs.UpdateWorkingHourRequest;
import org.lw.vms.entity.*;
import org.lw.vms.service.*;
import org.lw.vms.utils.JwtUtil;
import org.lw.vms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 工单分配相关的 RESTful API 控制器。
 */
@RestController
@RequestMapping("/api/assignment")
public class OrderAssignmentController {

    @Autowired
    private OrderAssignmentService orderAssignmentService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MechanicService mechanicService;

    @Autowired
    private MaterialConsumptionService materialConsumptionService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private RepairOrderService repairOrderService;

    /**
     * 维修人员接收或拒绝系统分配的维修工单。
     * 权限：mechanic
     * @param assignmentId 工单分配 ID
     * @param request 更新请求 DTO (包含 status)
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @PostMapping("/{assignmentId}")
    public Result<OrderAssignment> updateOrderAssignmentStatus(
            @PathVariable Integer assignmentId,
            @RequestBody OrderAssignmentUpdateRequest request,
            @RequestHeader("Authorization") String token
           ) {
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer mechanicUserId = claims.get("userId", Integer.class);
        String role = claims.get("role", String.class);

        if (!"mechanic".equals(role)) {
            return Result.fail(403, "Forbidden: Only mechanics can update assignments");
        }

        request.setAssignmentId(assignmentId);
        OrderAssignment updatedAssignment = orderAssignmentService.updateOrderAssignmentStatus(request);
        if (updatedAssignment != null) {
            // 如果拒绝了工单，可以在此处触发重新分配的逻辑（当前版本未实现）
            if ("rejected".equalsIgnoreCase(request.getStatus())) {
                return Result.success(updatedAssignment, "Assignment rejected. System will reassign.");
            }
            return Result.success(updatedAssignment, "Assignment status updated successfully");
        }
        return Result.fail("Failed to update assignment status or assignment not found");
    }

    /**
     * 管理员创建工单分配。
     * 权限：admin
     * @param orderId 工单 ID
     * @param mechanicId 维修人员 ID
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @PostMapping
    public Result<OrderAssignment> createOrderAssignment(
            @RequestParam Integer orderId,
            @RequestParam Integer mechanicId,
            @RequestHeader("Authorization") String token) {
//        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Only admins can create assignments");
        }

        OrderAssignment newAssignment = orderAssignmentService.createOrderAssignment(orderId, mechanicId);
        if (newAssignment != null) {
            return Result.success(newAssignment, "Order assigned successfully");
        }
        return Result.fail("Failed to assign order");
    }

    @GetMapping("/byMechanic/{mechanicId}")
    public Result<List<OrderAssignment>> acceptByMechanic(@PathVariable Integer mechanicId,@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"mechanic".equals(role)) {
            return Result.fail(403, "Forbidden: Only mechanics can accept assignments");
        }

        List<OrderAssignment> assignments = orderAssignmentService.getAssignmentsByMechanicId(mechanicId);
        if (assignments != null) {
            return Result.success(assignments, "Assignment accepted successfully");
        }
        return Result.fail("Failed to accept assignment");
    }

    //管理员查看当前订单被分配给了哪些人
    @GetMapping("/{orderId}")
    public Result<List<OrderAssignment>> getOrderAssignments(@PathVariable Integer orderId ,@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Only admins can view all order assignments");
        }

        List<OrderAssignment> assignments = orderAssignmentService.getAllAssignmentsByOrderId(orderId);
        if (assignments != null)
            return Result.success(assignments, "Order assignment retrieved successfully");
        return null;
    }

    @PostMapping("/updateWorkingTime")
    public Result<OrderAssignment> updateWorkingTime(@RequestBody UpdateWorkingHourRequest request,@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        int i = orderAssignmentService.updateWorkingTime(request);
        if (i == 1){
            OrderAssignment assignment = orderAssignmentService.getAllAssignmentsByAssignmentId(request.assignmentId());
            List<OrderAssignment> assignments = orderAssignmentService.getAllAssignmentsByOrderId(assignment.getOrderId());
            if (assignments != null){
                boolean finished = true;
                for (OrderAssignment orderAssignment : assignments){
                    if (!Objects.equals(orderAssignment.getStatus(), "completed")) {
                        finished = false;
                        break;
                    }
                }
                if(finished){
                    BigDecimal totalWorkingCost = BigDecimal.valueOf(0);
                    BigDecimal totalMaterialCost = BigDecimal.valueOf(0);
                    List<Mechanic> mechanics = new ArrayList<>();
                    List<MaterialConsumptionForAssignment> consumptions = new ArrayList<>();
                    for (OrderAssignment orderAssignment : assignments){
                        Mechanic mechanic = mechanicService.getMechanicDetailsById(orderAssignment.getMechanicId());
                        mechanics.add(mechanic);
                        totalWorkingCost = totalWorkingCost.add(orderAssignment.getHoursWorked().multiply(mechanic.getHourlyRate()));
                        consumptions = materialConsumptionService.getMaterialConsumptionByAssignmentId(orderAssignment.getAssignmentId().longValue());
                        for (MaterialConsumptionForAssignment consumption : consumptions){
                            Material material = materialService.getMaterialById(consumption.getMaterialId());
                            totalMaterialCost = totalMaterialCost.add(material.getUnitPrice().multiply(BigDecimal.valueOf(consumption.getQuantity())));
                        }
                    }
                    RepairOrder repairOrder = repairOrderService.getRepairOrderById(assignment.getOrderId());
                    repairOrder.setTotalMaterialCost(totalMaterialCost);
                    repairOrder.setTotalLaborCost(totalWorkingCost);
                    repairOrder.setCompletionTime(new Date());
                    repairOrder.setStatus("completed");
                    repairOrderService.updateFinalRepairOrder(repairOrder);
                }
            }
            return Result.success(assignment,"Update working time successfully");
        }
        return Result.fail(404, "Update working time failed");
    }
}