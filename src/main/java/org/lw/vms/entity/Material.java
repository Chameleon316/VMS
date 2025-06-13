package org.lw.vms.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 材料实体类，对应数据库中的 'material' 表。
 */
public class Material implements Serializable {
    private Integer materialId;  // 对应 material_id，主键
    private String name;         // 对应 name，材料名称
    private BigDecimal unitPrice; // 对应 unit_price，单价
    private Integer stockQuantity; // 对应 stock_quantity，库存数量

    public Material() {
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "Material{" +
                "materialId=" + materialId +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}