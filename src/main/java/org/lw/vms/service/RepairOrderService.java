package org.lw.vms.service;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.lw.vms.DTOs.RepairOrderRequest;
import org.lw.vms.entity.RepairOrder;

import java.util.List;

public interface RepairOrderService {
    /**
     * 用户提交新的维修工单。
     * @param request 维修工单请求 DTO
     * @return 创建成功的工单对象
     */
    RepairOrder createRepairOrder(RepairOrderRequest request);

    /**
     * 更新维修工单信息，包括状态、材料费、工时费和完成时间。
     * @param request 维修工单请求 DTO
     * @return 更新后的工单对象
     */
    RepairOrder updateRepairOrder(RepairOrderRequest request);

    /**
     * 获取单个维修工单详情。
     * @param orderId 工单 ID
     * @return 维修工单对象
     */
    RepairOrder getRepairOrderById(Integer orderId);

    /**
     * 根据用户 ID 获取其所有维修工单。
     * @param userId 用户 ID
     * @return 维修工单列表
     */
    List<RepairOrder> getRepairOrdersByUserId(Integer userId);

    /**
     * 根据维修人员 ID 获取其分配到的所有维修工单。
     * @param mechanicId 维修人员 ID
     * @return 维修工单列表
     */
    List<RepairOrder> getRepairOrdersByMechanicId(Integer mechanicId);

    /**
     * 获取所有维修工单（管理员）。
     * @return 所有维修工单列表
     */
    List<RepairOrder> getAllRepairOrders();

    /**
     * 更新维修工单状态（用于催单等）。
     * @param orderId 工单 ID
     * @param status 新状态
     * @return 更新后的工单对象
     */
    RepairOrder updateRepairOrderStatus(Integer orderId, String status);
}
