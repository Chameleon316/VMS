package org.lw.vms.entity;

import org.lw.vms.enums.FeedbackRating;

import java.time.LocalDateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * 反馈实体类，对应数据库中的 'feedback' 表。
 * 记录了用户对维修工单的评价。
 */
public class Feedback implements Serializable {
    private Integer feedbackId; // 对应 feedback_id，主键
    private Integer orderId;    // 对应 order_id，关联维修工单表
    private Integer userId;     // 对应 user_id，关联用户表 (提交反馈的用户)
    private Integer rating;     // 对应 rating，评分
    private String comment;     // 对应 comment，评论
    private Date feedbackTime;  // 对应 feedback_time，反馈时间

    public Feedback() {
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", feedbackTime=" + feedbackTime +
                '}';
    }
}
