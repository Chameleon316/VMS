package org.lw.vms.DTOs;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */

import java.math.BigDecimal; // 用于 averageRepairCost

public class CarRepairStatsDto {
    private String brand;
    private String model;
    private Long repairCount; // 对应 SQL 中的 COUNT(ro.order_id)
    private BigDecimal averageRepairCost; // 对应 SQL 中的 AVG(...)，使用 BigDecimal 保证精度

    public CarRepairStatsDto() {
        // 默认构造函数
    }

    public CarRepairStatsDto(String brand, String model, Long repairCount, BigDecimal averageRepairCost) {
        this.brand = brand;
        this.model = model;
        this.repairCount = repairCount;
        this.averageRepairCost = averageRepairCost;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public Long getRepairCount() {
        return repairCount;
    }
    public void setRepairCount(Long repairCount) {
        this.repairCount = repairCount;
    }
    public BigDecimal getAverageRepairCost() {
        return averageRepairCost;
    }
    public void setAverageRepairCost(BigDecimal averageRepairCost) {
        this.averageRepairCost = averageRepairCost;
    }

}
