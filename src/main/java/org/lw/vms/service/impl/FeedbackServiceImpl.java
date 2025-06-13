package org.lw.vms.service.impl;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.lw.vms.DTOs.FeedbackRequest;
import org.lw.vms.entity.Feedback;
import org.lw.vms.mapper.FeedbackMapper;
import org.lw.vms.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    @Transactional
    public Feedback createFeedback(FeedbackRequest request) {
        Feedback feedback = new Feedback();
        feedback.setOrderId(request.getOrderId());
        feedback.setUserId(request.getUserId());
        feedback.setRating(request.getRating());
        feedback.setComment(request.getComment());
        feedback.setFeedbackTime(new Date()); // 设置当前时间为反馈时间

        int rowsAffected = feedbackMapper.insertFeedback(feedback);
        return rowsAffected > 0 ? feedback : null;
    }

    @Override
    public Feedback getFeedbackById(Integer feedbackId) {
        return feedbackMapper.findById(feedbackId);
    }

    @Override
    public Feedback getFeedbackByOrderId(Integer orderId) {
        // 由于 order_id 在 feedback 表中是 UNIQUE，因此直接取第一个
        List<Feedback> feedbacks = feedbackMapper.findByOrderId(orderId);
        return feedbacks.isEmpty() ? null : feedbacks.get(0);
    }

    @Override
    public List<Feedback> getFeedbacksByUserId(Integer userId) {
        return feedbackMapper.findByUserId(userId);
    }
}