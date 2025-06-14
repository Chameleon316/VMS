package org.lw.vms.entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 维修工单实体类，对应数据库中的 'repair_order' 表。
 */
@Getter
@Setter
public class RepairOrder implements Serializable {
    private Integer orderId;       // 对应 order_id，主键
    private Integer vehicleId;     // 对应 vehicle_id，关联车辆表
    private Integer userId;        // 对应 user_id，关联用户表 (报修用户)
    private Date datetime;         // 对应 datetime，报修时间
    private String status;         // 对应 status，工单状态
    // ENUM('pending', 'assigned', 'in_progress', 'completed', 'cancelled')
    private BigDecimal totalMaterialCost; // 对应 total_material_cost，总材料费
    private BigDecimal totalLaborCost;    // 对应 total_labor_cost，总工时费
    private Date completionTime;   // 对应 completion_time，完成时间
    private String description;    // 对应 description，描述信息

    public RepairOrder() {
    }

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

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RepairOrder{" +
                "orderId=" + orderId +
                ", vehicleId=" + vehicleId +
                ", userId=" + userId +
                ", datetime=" + datetime +
                ", status='" + status + '\'' +
                ", totalMaterialCost=" + totalMaterialCost +
                ", totalLaborCost=" + totalLaborCost +
                ", completionTime=" + completionTime +
                ", description='" + description + '\'' +
                '}';
    }
}