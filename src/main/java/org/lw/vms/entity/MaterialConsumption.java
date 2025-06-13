package org.lw.vms.entity;

import java.io.Serializable;

/**
 * 材料消耗实体类，对应数据库中的 'material_consumption' 表。
 * 记录了在特定工单中消耗的材料及其数量。
 */
public class MaterialConsumption implements Serializable {
    private Integer consumptionId; // 对应 consumption_id，主键
    private Integer orderId;       // 对应 order_id，关联维修工单表
    private Integer materialId;    // 对应 material_id，关联材料表
    private Integer quantity;      // 对应 quantity，消耗数量

    public MaterialConsumption() {
    }

    public Integer getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(Integer consumptionId) {
        this.consumptionId = consumptionId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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
                ", orderId=" + orderId +
                ", materialId=" + materialId +
                ", quantity=" + quantity +
                '}';
    }
}
