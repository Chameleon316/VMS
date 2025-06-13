package org.lw.vms.service.impl;

import org.lw.vms.entity.Vehicle;
import org.lw.vms.mapper.VehicleMapper;
import org.lw.vms.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleMapper vehicleMapper;

    @Override
    public Vehicle getVehicleById(Integer vehicleId) {
        return vehicleMapper.findById(vehicleId);
    }

    @Override
    public List<Vehicle> getVehiclesByUserId(Integer userId) {
        return vehicleMapper.findByUserId(userId);
    }

    @Override
    @Transactional
    public Vehicle addVehicle(Vehicle vehicle) {
        int rowsAffected = vehicleMapper.insertVehicle(vehicle);
        return rowsAffected > 0 ? vehicle : null;
    }

    @Override
    @Transactional
    public Vehicle updateVehicle(Vehicle vehicle) {
        int rowsAffected = vehicleMapper.updateVehicle(vehicle);
        return rowsAffected > 0 ? vehicleMapper.findById(vehicle.getVehicleId()) : null;
    }

    @Override
    @Transactional
    public boolean deleteVehicle(Integer vehicleId) {
        return vehicleMapper.deleteVehicle(vehicleId) > 0;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleMapper.findAllVehicles();
    }
}
