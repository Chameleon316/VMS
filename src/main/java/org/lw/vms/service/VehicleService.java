package org.lw.vms.service;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.lw.vms.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle getVehicleById(Integer vehicleId);
    List<Vehicle> getVehiclesByUserId(Integer userId);
    Vehicle addVehicle(Vehicle vehicle);
    Vehicle updateVehicle(Vehicle vehicle);
    boolean deleteVehicle(Integer vehicleId);
    List<Vehicle> getAllVehicles();
}