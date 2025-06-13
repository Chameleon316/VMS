package org.lw.vms.mapper;

import org.apache.ibatis.annotations.*;
import org.lw.vms.entity.RepairOrder;

import java.util.List;

/**
 * 维修工单数据访问接口 (Mybatis Mapper)。
 */
@Mapper
public interface RepairOrderMapper {

    /**
     * 根据 ID 查询维修工单。
     * @param orderId 工单 ID
     * @return 维修工单对象
     */
    @Select("SELECT order_id, vehicle_id, user_id, create_time, status, total_material_cost, total_labor_cost, completion_time FROM repair_order WHERE order_id = #{orderId}")
    RepairOrder findById(Integer orderId);

    /**
     * 根据用户 ID 查询维修工单列表。
     * @param userId 用户 ID
     * @return 维修工单列表
     */
    @Select("SELECT order_id, vehicle_id, user_id, create_time, status, total_material_cost, total_labor_cost, completion_time FROM repair_order WHERE user_id = #{userId}")
    List<RepairOrder> findByUserId(Integer userId);

    /**
     * 根据车辆 ID 查询维修工单列表。
     * @param vehicleId 车辆 ID
     * @return 维修工单列表
     */
    @Select("SELECT order_id, vehicle_id, user_id, create_time, status, total_material_cost, total_labor_cost, completion_time FROM repair_order WHERE vehicle_id = #{vehicleId}")
    List<RepairOrder> findByVehicleId(Integer vehicleId);

    /**
     * 根据状态查询维修工单列表。
     * @param status 工单状态
     * @return 维修工单列表
     */
    @Select("SELECT order_id, vehicle_id, user_id, create_time, status, total_material_cost, total_labor_cost, completion_time FROM repair_order WHERE status = #{status}")
    List<RepairOrder> findByStatus(String status);

    /**
     * 插入新的维修工单记录。
     * @param repairOrder 维修工单对象
     * @return 影响的行数
     */
    @Insert("INSERT INTO repair_order(vehicle_id, user_id, create_time, status, total_material_cost, total_labor_cost, completion_time) " +
            "VALUES(#{vehicleId}, #{userId}, #{datetime}, #{status}, #{totalMaterialCost}, #{totalLaborCost}, #{completionTime})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    int insertRepairOrder(RepairOrder repairOrder);

    /**
     * 更新维修工单信息。
     * @param repairOrder 维修工单对象
     * @return 影响的行数
     */
    @Update("UPDATE repair_order SET vehicle_id = #{vehicleId}, user_id = #{userId}, create_time = #{datetime}, " +
            "status = #{status}, total_material_cost = #{totalMaterialCost}, total_labor_cost = #{totalLaborCost}, " +
            "completion_time = #{completionTime} WHERE order_id = #{orderId}")
    int updateRepairOrder(RepairOrder repairOrder);

    /**
     * 删除维修工单记录。
     * @param orderId 工单 ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM repair_order WHERE order_id = #{orderId}")
    int deleteRepairOrder(Integer orderId);

    /**
     * 查询所有维修工单 (管理员用)。
     * @return 所有维修工单的列表
     */
    @Select("SELECT order_id, vehicle_id, user_id, create_time, status, total_material_cost, total_labor_cost, completion_time FROM repair_order")
    List<RepairOrder> findAllRepairOrders();

    /**
     * 根据维修人员ID查询其负责的工单列表 (通过 order_assignment 关联)。
     *
     * @param mechanicId 维修人员 ID
     * @return 维修工单列表
     */
    @Select("SELECT ro.order_id, ro.vehicle_id, ro.user_id, ro.create_time, ro.status, " +
            "ro.total_material_cost, ro.total_labor_cost, ro.completion_time " +
            "FROM repair_order ro " +
            "JOIN order_assignment oa ON ro.order_id = oa.order_id " +
            "WHERE oa.mechanic_id = #{mechanicId}")
    List<RepairOrder> findRepairOrdersByMechanicId(Integer mechanicId);
}
