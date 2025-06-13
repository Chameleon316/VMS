package org.lw.vms.DTOs;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import java.io.Serializable;
import java.util.Date; // 引入 Date

/**
 * 反馈请求数据传输对象。
 * 用于用户提交反馈。
 */
public class FeedbackRequest implements Serializable {
    private Integer orderId;
    private Integer userId; // 提交反馈的用户ID，在Controller中从JWT获取
    private Integer rating;
    private String comment;

    // Getter and Setter
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
}

