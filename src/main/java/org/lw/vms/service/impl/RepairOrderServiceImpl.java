package org.lw.vms.service.impl;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.lw.vms.DTOs.RepairOrderRequest;
import org.lw.vms.entity.RepairOrder;
import org.lw.vms.mapper.RepairOrderMapper;
import org.lw.vms.service.RepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RepairOrderServiceImpl implements RepairOrderService {

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Override
    @Transactional
    public RepairOrder createRepairOrder(RepairOrderRequest request) {
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setVehicleId(request.getVehicleId());
        repairOrder.setUserId(request.getUserId());
        repairOrder.setDatetime(new Date()); // 设置当前时间为报修时间
        repairOrder.setStatus("pending"); // 初始状态为待处理
        repairOrder.setDescription(request.getDescription());

        int rowsAffected = repairOrderMapper.insertRepairOrder(repairOrder);
        return rowsAffected > 0 ? repairOrder : null;
    }

    @Override
    @Transactional
    public RepairOrder updateRepairOrder(RepairOrderRequest request) {
        RepairOrder existingOrder = repairOrderMapper.findById(request.getOrderId());
        if (existingOrder == null) {
            return null; // 工单不存在
        }

        // 仅更新允许维修人员更新的字段
        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            existingOrder.setStatus(request.getStatus());
        }
        if (request.getTotalMaterialCost() != null) {
            existingOrder.setTotalMaterialCost(request.getTotalMaterialCost());
        }
        if (request.getTotalLaborCost() != null) {
            existingOrder.setTotalLaborCost(request.getTotalLaborCost());
        }
        if (request.getCompletionTime() != null) {
            existingOrder.setCompletionTime(request.getCompletionTime());
        }

        int rowsAffected = repairOrderMapper.updateRepairOrder(existingOrder);
        return rowsAffected > 0 ? existingOrder : null;
    }

    @Override
    public RepairOrder getRepairOrderById(Integer orderId) {
        return repairOrderMapper.findById(orderId);
    }

    @Override
    public List<RepairOrder> getRepairOrdersByUserId(Integer userId) {
        return repairOrderMapper.findByUserId(userId);
    }

    @Override
    public List<RepairOrder> getRepairOrdersByMechanicId(Integer mechanicId) {
        return repairOrderMapper.findRepairOrdersByMechanicId(mechanicId);
    }

    @Override
    public List<RepairOrder> getAllRepairOrders() {
        return repairOrderMapper.findAllRepairOrders();
    }

    @Override
    @Transactional
    public RepairOrder updateRepairOrderStatus(Integer orderId, String status) {
        RepairOrder existingOrder = repairOrderMapper.findById(orderId);
        if (existingOrder == null) {
            return null;
        }
        existingOrder.setStatus(status);
        int rowsAffected = repairOrderMapper.updateRepairOrder(existingOrder);
        return rowsAffected > 0 ? existingOrder : null;
    }

    @Override
    public void updateFinalRepairOrder(RepairOrder repairOrder) {
       repairOrderMapper.updateFinalRepairOrder(repairOrder);
    }
}
