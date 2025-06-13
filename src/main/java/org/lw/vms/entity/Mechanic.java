package org.lw.vms.entity;

import org.lw.vms.enums.MechanicSpecialty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 维修人员实体类，对应数据库中的 'mechanic' 表。
 */
public class Mechanic implements Serializable {
    private Integer mechanicId; // 对应 mechanic_id，主键
    private Integer userId;     // 对应 user_id，关联用户表
    private String specialty;   // 对应 specialty，工种
    private BigDecimal hourlyRate; // 对应 hourly_rate，时薪

    public Mechanic() {
    }

    public Integer getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Integer mechanicId) {
        this.mechanicId = mechanicId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String toString() {
        return "Mechanic{" +
                "mechanicId=" + mechanicId +
                ", userId=" + userId +
                ", specialty='" + specialty + '\'' +
                ", hourlyRate=" + hourlyRate +
                '}';
    }
}