package org.lw.vms.entity;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 工单分配实体类，对应数据库中的 'order_assignment' 表。
 * 记录了维修人员对特定工单的分配情况、工时和状态。
 */
public class OrderAssignment implements Serializable {
    private Integer assignmentId; // 对应 assignment_id，主键
    private Integer orderId;      // 对应 order_id，关联维修工单表
    private Integer mechanicId;   // 对应 mechanic_id，关联维修人员表
    private BigDecimal hoursWorked; // 对应 hours_worked，实际工作小时数
    private String status;        // 对应 status，分配状态 ENUM('accepted', 'rejected')

    public OrderAssignment() {
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Integer mechanicId) {
        this.mechanicId = mechanicId;
    }

    public BigDecimal getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(BigDecimal hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderAssignment{" +
                "assignmentId=" + assignmentId +
                ", orderId=" + orderId +
                ", mechanicId=" + mechanicId +
                ", hoursWorked=" + hoursWorked +
                ", status='" + status + '\'' +
                '}';
    }
}

