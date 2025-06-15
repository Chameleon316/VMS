package org.lw.vms.controller;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import io.jsonwebtoken.Claims;
import org.lw.vms.DTOs.RepairOrderRequest;
import org.lw.vms.entity.RepairOrder;
import org.lw.vms.service.RepairOrderService;
import org.lw.vms.utils.JwtUtil;
import org.lw.vms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 维修工单相关的 RESTful API 控制器。
 */
@RestController
@RequestMapping("/api/repair-order")
public class RepairOrderController {

    @Autowired
    private RepairOrderService repairOrderService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户提交车辆报修信息。
     * 权限：user, admin
     * @param request 报修请求 DTO
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @PostMapping
    public Result<RepairOrder> createRepairOrder(@RequestBody RepairOrderRequest request, @RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer userId = claims.get("userId", Integer.class);
        String role = claims.get("role", String.class);

        if (!"user".equals(role) && !"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Only users or admins can create repair orders");
        }

        request.setUserId(userId); // 从 JWT 中设置用户ID
        RepairOrder newOrder = repairOrderService.createRepairOrder(request);
        if (newOrder != null) {
            return Result.success(newOrder, "Repair order created successfully");
        }
        return Result.fail("Failed to create repair order");
    }

    /**
     * 维修人员或管理员更新维修工单状态、材料费、工时费和完成时间。
     * 权限：mechanic, admin
     * @param request 维修工单更新请求 DTO
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @PutMapping("/{orderId}")
    public Result<RepairOrder> updateRepairOrder(@PathVariable Integer orderId, @RequestBody RepairOrderRequest request, @RequestHeader("Authorization") String token) {
//        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"mechanic".equals(role) && !"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Only mechanics or admins can update repair orders");
        }

        request.setOrderId(orderId);
        RepairOrder updatedOrder = repairOrderService.updateRepairOrder(request);
        if (updatedOrder != null) {
            return Result.success(updatedOrder, "Repair order updated successfully");
        }
        return Result.fail("Failed to update repair order or order not found");
    }

    /**
     * 查询单个维修工单详情。
     * 权限：user (如果属于自己的工单), mechanic (如果分配给自己), admin
     * @param orderId 工单 ID
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @GetMapping("/{orderId}")
    public Result<RepairOrder> getRepairOrderDetails(@PathVariable Integer orderId, @RequestHeader("Authorization") String token) {
//        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer currentUserId = claims.get("userId", Integer.class);
        String role = claims.get("role", String.class);

        RepairOrder order = repairOrderService.getRepairOrderById(orderId);
        if (order == null) {
            return Result.fail("Repair order not found");
        }

        // 权限校验
        if ("user".equals(role) && !order.getUserId().equals(currentUserId)) {
            return Result.fail(403, "Forbidden: User can only view their own repair orders");
        }
        // 维修人员需要进一步校验是否是分配给自己的工单
        if ("mechanic".equals(role)) {
            List<RepairOrder> mechanicOrders = repairOrderService.getRepairOrdersByMechanicId(currentUserId);
            boolean isAssignedToMechanic = mechanicOrders.stream().anyMatch(o -> o.getOrderId().equals(orderId));
            if (!isAssignedToMechanic) {
                return Result.fail(403, "Forbidden: Mechanic can only view assigned repair orders");
            }
        }
        // 管理员无需额外校验

        return Result.success(order, "Repair order details fetched successfully");
    }

    /**
     * 用户催单（更新工单状态，例如从 'pending' 到 'urgent' 或 'in_progress'）。
     * 权限：user (自己的工单), admin
     * @param orderId 工单 ID
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @PostMapping("/{orderId}/urge")
    public Result<RepairOrder> urgeRepairOrder(@PathVariable Integer orderId, @RequestHeader("Authorization") String token) {
//        String token = httpServletRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer userId = claims.get("userId", Integer.class);
        String role = claims.get("role", String.class);

        RepairOrder order = repairOrderService.getRepairOrderById(orderId);
        if (order == null) {
            return Result.fail("Repair order not found");
        }

        // 权限校验：只能催自己的单，或管理员
        if (!"user".equals(role) && !"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Only users or admins can urge repair orders");
        }
        if ("user".equals(role) && !order.getUserId().equals(userId)) {
            return Result.fail(403, "Forbidden: You can only urge your own repair orders");
        }

        // 催单逻辑：这里简单地将状态更新为 'in_progress' 或其他自定义的催单状态
        // 实际中可能需要更复杂的逻辑，例如发送通知、优先级提升等
        repairOrderService.setUrged(orderId, true);// 示例：更新为进行中
        RepairOrder updatedOrder = repairOrderService.getRepairOrderById(orderId);
        if (updatedOrder != null) {
            return Result.success(updatedOrder, "Repair order urged successfully. Status updated to 'in_progress'.");
        }
        return Result.fail("Failed to urge repair order");
    }

    @GetMapping("/urgedOrder")
    public Result<List<RepairOrder>> getUrgedOrders(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer userId = claims.get("userId", Integer.class);
        String role = claims.get("role", String.class);
        if (!"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Only admins can view urged orders");
        }
        List<RepairOrder> urgedOrders = repairOrderService.getUrgedOrders();
        return Result.success(urgedOrders, "Urged orders retrieved successfully");
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> deleteRepairOrder(@PathVariable("id") Integer id) {
        if (repairOrderService.deleteRepairOrder(id))
            return Result.success("Repair order with id " + id + " deleted successfully");
        else
            return Result.fail(404, "Repair order with id " + id + " not found");
    }
}
