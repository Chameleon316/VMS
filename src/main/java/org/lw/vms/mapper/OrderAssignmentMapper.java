package org.lw.vms.mapper;

import org.apache.ibatis.annotations.*;
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
    @Select("SELECT assignment_id, order_id, mechanic_id, hours_worked, status FROM order_assignment WHERE mechanic_id = #{mechanicId}")
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
            "hours_worked = #{hoursWorked}, status = #{status} WHERE assignment_id = #{assignmentId}")
    int updateOrderAssignment(OrderAssignment orderAssignment);

    /**
     * 删除工单分配记录。
     * @param assignmentId 分配 ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM order_assignment WHERE assignment_id = #{assignmentId}")
    int deleteOrderAssignment(Integer assignmentId);
}
