package org.lw.vms.controller;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.lw.vms.entity.Vehicle;
import org.lw.vms.service.VehicleService;
import org.lw.vms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车辆相关的 RESTful API 控制器。
 */
@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    // 假设你有一个 VehicleService 来处理车辆业务逻辑
    @Autowired
    private VehicleService vehicleService; // 注入 VehicleService

    /**
     * 根据车辆ID获取车辆信息。
     * @param vehicleId 车辆ID
     * @return 车辆信息
     */
    @GetMapping("/{vehicleId}")
    public Result<Vehicle> getVehicleById(@PathVariable Integer vehicleId) {
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        if (vehicle != null) {
            return Result.success(vehicle, "查询成功");
        }
        return Result.fail("车辆不存在");
    }

    /**
     * 根据用户ID获取用户所有车辆信息。
     * 只有用户角色才能查询自己的车辆。
     * @param userId 用户ID (从JWT中获取)
     * @return 车辆列表
     */
    @GetMapping("/user/{userId}")
    public Result<List<Vehicle>> getVehiclesByUserId(@PathVariable Integer userId) {
        // 实际应用中，userId 应该从 JWT 中解析，并与路径参数进行比对，防止越权访问
        List<Vehicle> vehicles = vehicleService.getVehiclesByUserId(userId);
        return Result.success(vehicles, "查询成功");
    }

    /**
     * 新增车辆。
     * @param vehicle 车辆信息
     * @return 新增的车辆信息
     */
    @PostMapping
    public Result<Vehicle> addVehicle(@RequestBody Vehicle vehicle) {
        // 实际应用中，vehicle.getUserId() 应该从 JWT 中获取，确保是当前登录用户操作
        Vehicle newVehicle = vehicleService.addVehicle(vehicle);
        if (newVehicle != null) {
            return Result.success(newVehicle, "车辆添加成功");
        }
        return Result.fail("车辆添加失败");
    }

    /**
     * 更新车辆信息。
     * @param vehicle 车辆信息
     * @return 更新后的车辆信息
     */
    @PutMapping
    public Result<Vehicle> updateVehicle(@RequestBody Vehicle vehicle) {
        // 实际应用中，需要校验 vehicle.getUserId() 是否与当前用户匹配
        Vehicle updatedVehicle = vehicleService.updateVehicle(vehicle);
        if (updatedVehicle != null) {
            return Result.success(updatedVehicle, "车辆更新成功");
        }
        return Result.fail("车辆更新失败或不存在");
    }

    /**
     * 删除车辆。
     * @param vehicleId 车辆ID
     * @return 操作结果
     */
    @DeleteMapping("/{vehicleId}")
    public Result<String> deleteVehicle(@PathVariable Integer vehicleId) {
        // 实际应用中，需要校验权限，例如只有车主或管理员才能删除
        boolean deleted = vehicleService.deleteVehicle(vehicleId);
        if (deleted) {
            return Result.success("车辆删除成功");
        }
        return Result.fail("车辆删除失败或不存在");
    }

    /**
     * 获取所有车辆信息 (管理员权限)。
     * @return 所有车辆列表
     */
    @GetMapping("/admin/all")
    public Result<List<Vehicle>> getAllVehicles() {
        // 实际应用中，这里需要添加权限验证，确保只有管理员才能访问
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return Result.success(vehicles, "查询所有车辆成功");
    }
}