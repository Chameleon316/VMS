package org.lw.vms.service;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.lw.vms.DTOs.FeedbackRequest;
import org.lw.vms.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    /**
     * 用户提交反馈。
     * @param request 反馈请求 DTO
     * @return 创建成功的反馈对象
     */
    Feedback createFeedback(FeedbackRequest request);

    /**
     * 获取单个反馈详情。
     * @param feedbackId 反馈 ID
     * @return 反馈对象
     */
    Feedback getFeedbackById(Integer feedbackId);

    /**
     * 根据工单 ID 获取反馈。
     * @param orderId 工单 ID
     * @return 反馈对象
     */
    Feedback getFeedbackByOrderId(Integer orderId);

    /**
     * 根据用户 ID 获取其所有反馈。
     * @param userId 用户 ID
     * @return 反馈列表
     */
    List<Feedback> getFeedbacksByUserId(Integer userId);
}