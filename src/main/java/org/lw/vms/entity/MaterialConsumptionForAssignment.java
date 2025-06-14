package org.lw.vms.entity;

import java.io.Serializable;

public class MaterialConsumptionForAssignment implements Serializable {
    private Integer consumptionId; // 对应 consumption_id，主键
    private Integer assignmentId;       // 对应 order_id，关联维修工单表
    private Integer materialId;    // 对应 material_id，关联材料表
    private Integer quantity;      // 对应 quantity，消耗数量


    public MaterialConsumptionForAssignment() {
    }

    public Integer getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(Integer consumptionId) {
        this.consumptionId = consumptionId;
    }

    public Integer getOrderId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer orderId) {
        this.assignmentId = orderId;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "MaterialConsumption{" +
                "consumptionId=" + consumptionId +
                ", assignmentId=" + assignmentId +
                ", materialId=" + materialId +
                ", quantity=" + quantity +
                '}';
    }
}
