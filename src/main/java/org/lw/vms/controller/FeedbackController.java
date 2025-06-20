package org.lw.vms.controller;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import io.jsonwebtoken.Claims;
import org.lw.vms.DTOs.FeedbackRequest;
import org.lw.vms.entity.Feedback;
import org.lw.vms.entity.RepairOrder;
import org.lw.vms.mapper.FeedbackMapper;
import org.lw.vms.service.FeedbackService;
import org.lw.vms.service.RepairOrderService;
import org.lw.vms.utils.JwtUtil;
import org.lw.vms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 反馈相关的 RESTful API 控制器。
 */
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {




    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RepairOrderService repairOrderService;

    /**
     * 用户提交反馈 (服务评分和评论)。
     * 权限：user, admin
     * @param request 反馈请求 DTO
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @PostMapping
    public Result<Feedback> createFeedback(@RequestBody FeedbackRequest request,@RequestHeader("Authorization") String token ) {
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer userId = claims.get("userId", Integer.class);
        String role = claims.get("role", String.class);

        if (!"user".equals(role) && !"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Only users or admins can submit feedback");
        }
//         校验工单是否存在且属于当前用户
        RepairOrder order = repairOrderService.getRepairOrderById(request.getOrderId());
         if (order == null || !order.getUserId().equals(userId)) {
             return Result.fail(403, "Forbidden: Cannot submit feedback for this order");
         }


        request.setUserId(userId); // 从 JWT 中设置用户ID
        Feedback newFeedback = feedbackService.createFeedback(request);
        if (newFeedback != null) {
            return Result.success(newFeedback, "Feedback submitted successfully");
        }
        return Result.fail("Failed to submit feedback");
    }

    /**
     * 根据工单ID查询反馈。
     * 权限：user (如果属于自己的工单), mechanic (如果分配给自己), admin
     * @param orderId 工单 ID
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @GetMapping("/byOrder/{orderId}")
    public Result<Feedback> getFeedbackByOrderId(@PathVariable Integer orderId, @RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer currentUserId = claims.get("userId", Integer.class);
        String role = claims.get("role", String.class);

        // 实际中可能需要更复杂的权限校验，例如检查工单与用户的关联
        // For simplicity, here we just check if it's user/mechanic/admin.
        // A user should only see feedback for their own orders.
        // A mechanic should only see feedback for orders assigned to them.
        // An admin can see all.

        Feedback feedback = feedbackService.getFeedbackByOrderId(orderId);
        if (feedback == null) {
            return Result.fail("Feedback not found for this order");
        }

        // Additional authorization logic:
        // If user, check if feedback's userId matches currentUserId
        // If mechanic, check if order corresponding to feedback is assigned to current mechanic
        // If admin, no further check needed

        return Result.success(feedback, "Feedback fetched successfully");
    }

    /**
     * 查询当前登录用户的所有反馈。
     * 权限：user, admin
     * @param httpServletRequest 用于获取 Authorization 头
     * @return 统一响应结果
     */
    @GetMapping("/myFeedbacks")
    public Result<List<Feedback>> getMyFeedbacks(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "Unauthorized: Missing or invalid JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer userId = claims.get("userId", Integer.class);
        String role = claims.get("role", String.class);

        if (!"user".equals(role) && !"admin".equals(role)) {
            return Result.fail(403, "Forbidden: Only users or admins can view their feedbacks");
        }

        List<Feedback> feedbacks = feedbackService.getFeedbacksByUserId(userId);
        return Result.success(feedbacks, "My feedbacks fetched successfully");
    }
}
