package org.lw.vms.service;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.lw.vms.DTOs.OrderAssignmentUpdateRequest;
import org.lw.vms.entity.OrderAssignment;

import java.util.List;

public interface OrderAssignmentService {
    /**
     * 更新工单分配状态 (接受/拒绝)。
     * @param request 工单分配更新请求 DTO
     * @return 更新后的工单分配对象
     */
    OrderAssignment updateOrderAssignmentStatus(OrderAssignmentUpdateRequest request);

    /**
     * 根据工单 ID 获取所有分配记录。
     * @param orderId 工单 ID
     * @return 工单分配列表
     */
    List<OrderAssignment> getAssignmentsByOrderId(Integer orderId);

    /**
     * 根据维修人员 ID 获取所有分配记录。
     * @param mechanicId 维修人员 ID
     * @return 工单分配列表
     */
    List<OrderAssignment> getAssignmentsByMechanicId(Integer mechanicId);

    /**
     * 创建一个新的工单分配记录。
     * @param orderId 工单 ID
     * @param mechanicId 维修人员 ID
     * @return 创建成功的工单分配对象
     */
    OrderAssignment createOrderAssignment(Integer orderId, Integer mechanicId);
}
