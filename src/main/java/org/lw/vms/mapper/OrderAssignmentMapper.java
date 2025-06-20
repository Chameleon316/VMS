package org.lw.vms.mapper;

import org.apache.ibatis.annotations.*;
import org.lw.vms.DTOs.MechanicAssignmentsResponse;
import org.lw.vms.DTOs.UpdateWorkingHourRequest;
import org.lw.vms.entity.OrderAssignment;

import java.util.List;

/**
 * 工单分配数据访问接口 (Mybatis Mapper)。
 */
@Mapper
public interface OrderAssignmentMapper {

    /**
     * 根据 ID 查询工单分配记录。
     * @param assignmentId 分配 ID
     * @return 工单分配对象
     */
    @Select("SELECT assignment_id, order_id, mechanic_id, hours_worked, status FROM order_assignment WHERE assignment_id = #{assignmentId}")
    OrderAssignment findById(Integer assignmentId);

    /**
     * 根据工单 ID 查询工单分配记录列表。
     * @param orderId 工单 ID
     * @return 工单分配列表
     */
    @Select("SELECT assignment_id, order_id, mechanic_id, hours_worked, status FROM order_assignment WHERE order_id = #{orderId}")
    List<OrderAssignment> findByOrderId(Integer orderId);

    /**
     * 根据维修人员 ID 查询工单分配记录列表。
     * @param mechanicId 维修人员 ID
     * @return 工单分配列表
     */
    @Select("SELECT assignment_id, order_id, mechanic_id, hours_worked, status,labor_cost FROM order_assignment WHERE mechanic_id = #{mechanicId}")
    List<OrderAssignment> findByMechanicId(Integer mechanicId);

    /**
     * 插入新的工单分配记录。
     * @param orderAssignment 工单分配对象
     * @return 影响的行数
     */
    @Insert("INSERT INTO order_assignment(order_id, mechanic_id, hours_worked, status) " +
            "VALUES(#{orderId}, #{mechanicId}, #{hoursWorked}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "assignmentId")
    int insertOrderAssignment(OrderAssignment orderAssignment);

    /**
     * 更新工单分配记录。
     * @param orderAssignment 工单分配对象
     * @return 影响的行数
     */
    @Update("UPDATE order_assignment SET order_id = #{orderId}, mechanic_id = #{mechanicId}, " +
            "hours_worked = #{hoursWorked}, status = #{status},labor_cost = #{laborCost} WHERE assignment_id = #{assignmentId}")
    int updateOrderAssignment(OrderAssignment orderAssignment);

    /**
     * 删除工单分配记录。
     * @param assignmentId 分配 ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM order_assignment WHERE assignment_id = #{assignmentId}")
    int deleteOrderAssignment(Integer assignmentId);

    @Update("UPDATE order_assignment SET status = 'accepted' WHERE assignment_id = #{assignmentId}")
    OrderAssignment acceptByMechanic(Integer assignmentId);

    @Select("SELECT " +
            "ro.order_id AS orderId, " +
            "ro.vehicle_id AS vehicleId, " +
            "ro.user_id AS userId, " +
            "ro.create_time AS datetime, " +
            "ro.status AS orderStatus, " +
            "ro.total_material_cost AS totalMaterialCost, " +
            "ro.total_labor_cost AS totalLaborCost, " +
            "ro.completion_time AS completionTime, " +
            "ro.description AS description, " +
            "oa.assignment_id AS assignmentId, " +
            "oa.mechanic_id AS mechanicId, " +
            "oa.hours_worked AS hoursWorked, " +
            "oa.status AS assignmentStatus, " +
            "oa.labor_cost AS laborCost " +
            "FROM repair_order ro " +
            "JOIN order_assignment oa ON ro.order_id = oa.order_id " +
            "WHERE oa.mechanic_id = #{mechanicId}")
    List<MechanicAssignmentsResponse> getAssignmentsByMechanicId(Integer mechanicId);

    @Update("UPDATE order_assignment SET status = 'completed',hours_worked = #{workingHour} WHERE assignment_id = #{assignmentId}")
    int updateWorkingTime(UpdateWorkingHourRequest request);

    @Select("SELECT * FROM order_assignment WHERE assignment_id = #{assignmentId}")
    OrderAssignment findByAssignmentId(Integer assignmentId);
}
