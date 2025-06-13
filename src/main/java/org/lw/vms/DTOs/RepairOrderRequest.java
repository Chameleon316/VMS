package org.lw.vms.DTOs;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import java.io.Serializable;
import java.math.BigDecimal; // 引入 BigDecimal
import java.util.Date; // 引入 Date

/**
 * 维修工单请求数据传输对象。
 * 用于用户提交报修或维修人员更新工单。
 */
public class RepairOrderRequest implements Serializable {
    private Integer orderId; // 更新时使用
    private Integer vehicleId;
    private Integer userId; // 提交工单的用户ID，在Controller中从JWT获取
    private String status; // 维修人员更新状态时使用
    private BigDecimal totalMaterialCost; // 维修人员更新时使用
    private BigDecimal totalLaborCost;    // 维修人员更新时使用
    private Date completionTime; // 维修人员完成工单时使用

    // Getter and Setter
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalMaterialCost() {
        return totalMaterialCost;
    }

    public void setTotalMaterialCost(BigDecimal totalMaterialCost) {
        this.totalMaterialCost = totalMaterialCost;
    }

    public BigDecimal getTotalLaborCost() {
        return totalLaborCost;
    }

    public void setTotalLaborCost(BigDecimal totalLaborCost) {
        this.totalLaborCost = totalLaborCost;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }
}
