package org.lw.vms.DTOs;

import org.lw.vms.mapper.NegativeFeedbackMapper;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
public class NegativeFeedbackDto {
    private Long orderId;            // 负面工单的ID
    private String orderDescription; // 负面工单的描述

    private Long mechanicId;         // 涉及的维修人员ID
    private String mechanicName;     // 涉及的维修人员姓名 (来自 user.name)

    public NegativeFeedbackDto(Long orderId, String orderDescription, Long mechanicId, String mechanicName) {
        this.orderId = orderId;
        this.orderDescription = orderDescription;
        this.mechanicId = mechanicId;
        this.mechanicName = mechanicName;
    }
    public NegativeFeedbackDto(){}

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public Long getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Long mechanicId) {
        this.mechanicId = mechanicId;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }
}