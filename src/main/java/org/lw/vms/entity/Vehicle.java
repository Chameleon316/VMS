package org.lw.vms.entity;

import org.lw.vms.enums.VehicleBrand;
import org.lw.vms.enums.VehicleModel;

import java.io.Serializable;

/**
 * 车辆实体类，对应数据库中的 'vehicle' 表。
 */
public class Vehicle implements Serializable {
    private Integer vehicleId;  // 对应 vehicle_id，主键
    private Integer userId;     // 对应 user_id，关联用户表
    private String licensePlate; // 对应 license_plate，车牌号
    private String brand;       // 对应 brand，品牌
    private String model;       // 对应 model，车型
    private Integer year;       // 对应 year，年份
    private Integer mileage;    // 对应 mileage，里程

    public Vehicle() {
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

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", userId=" + userId +
                ", licensePlate='" + licensePlate + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", mileage=" + mileage +
                '}';
    }
}

