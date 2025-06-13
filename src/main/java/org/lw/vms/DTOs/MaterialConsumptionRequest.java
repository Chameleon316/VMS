package org.lw.vms.DTOs;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import java.io.Serializable;

/**
 * 材料消耗请求数据传输对象。
 * 用于维修人员记录材料消耗。
 */
public class MaterialConsumptionRequest implements Serializable {
    private Integer orderId;
    private Integer materialId;
    private Integer quantity;

    // Getter and Setter
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
}