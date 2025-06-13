package org.lw.vms.mapper;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.apache.ibatis.annotations.*;
import org.lw.vms.entity.Vehicle;

import java.util.List;

/**
 * 车辆数据访问接口 (Mybatis Mapper)。
 */
@Mapper
public interface VehicleMapper {

    /**
     * 根据 ID 查询车辆。
     * @param vehicleId 车辆 ID
     * @return 车辆对象
     */
    @Select("SELECT vehicle_id, user_id, license_plate, brand, model, year, mileage FROM vehicle WHERE vehicle_id = #{vehicleId}")
    Vehicle findById(Integer vehicleId);

    /**
     * 根据用户 ID 查询车辆列表。
     * @param userId 用户 ID
     * @return 车辆列表
     */
    @Select("SELECT vehicle_id, user_id, license_plate, brand, model, year, mileage FROM vehicle WHERE user_id = #{userId}")
    List<Vehicle> findByUserId(Integer userId);

    /**
     * 根据车牌号查询车辆。
     * @param licensePlate 车牌号
     * @return 车辆对象
     */
    @Select("SELECT vehicle_id, user_id, license_plate, brand, model, year, mileage FROM vehicle WHERE license_plate = #{licensePlate}")
    Vehicle findByLicensePlate(String licensePlate);

    /**
     * 插入新的车辆记录。
     * @param vehicle 车辆对象
     * @return 影响的行数
     */
    @Insert("INSERT INTO vehicle(user_id, license_plate, brand, model, year, mileage) " +
            "VALUES(#{userId}, #{licensePlate}, #{brand}, #{model}, #{year}, #{mileage})")
    @Options(useGeneratedKeys = true, keyProperty = "vehicleId")
    int insertVehicle(Vehicle vehicle);

    /**
     * 更新车辆信息。
     * @param vehicle 车辆对象
     * @return 影响的行数
     */
    @Update("UPDATE vehicle SET license_plate = #{licensePlate}, brand = #{brand}, model = #{model}, " +
            "year = #{year}, mileage = #{mileage} WHERE vehicle_id = #{vehicleId}")
    int updateVehicle(Vehicle vehicle);

    /**
     * 删除车辆记录。
     * @param vehicleId 车辆 ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM vehicle WHERE vehicle_id = #{vehicleId}")
    int deleteVehicle(Integer vehicleId);

    /**
     * 查询所有车辆信息 (管理员用)。
     * @return 所有车辆的列表
     */
    @Select("SELECT vehicle_id, user_id, license_plate, brand, model, year, mileage FROM vehicle")
    List<Vehicle> findAllVehicles();
}
