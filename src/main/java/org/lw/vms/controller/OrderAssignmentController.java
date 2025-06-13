package org.lw.vms.controller;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import io.jsonwebtoken.Claims;
import org.lw.vms.DTOs.OrderAssignmentUpdateRequest;
import org.lw.vms.entity.OrderAssignment;
import org.lw.vms.service.OrderAssignmentService;
import org.lw.vms.utils.JwtUtil;
import org.lw.vms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 维修人员接收或拒绝系统分配的维修工单。
     * 权限：mechanic
     * @param assignmentId 工单分配 ID
     * @param request 更新请求 DTO (包含 status)
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @PatchMapping("/{assignmentId}")
    public Result<OrderAssignment> updateOrderAssignmentStatus(
            @PathVariable Integer assignmentId,
            @RequestBody OrderAssignmentUpdateRequest request,
            HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
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
            HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
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
}