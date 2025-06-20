package org.lw.vms.service.impl;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.lw.vms.DTOs.RepairOrderRequest;
import org.lw.vms.entity.Mechanic;
import org.lw.vms.entity.OrderAssignment;
import org.lw.vms.entity.RepairOrder;
import org.lw.vms.mapper.MechanicMapper;
import org.lw.vms.mapper.RepairOrderMapper;
import org.lw.vms.service.OrderAssignmentService;
import org.lw.vms.service.RepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class RepairOrderServiceImpl implements RepairOrderService {

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Autowired
    private MechanicMapper mechanicMapper;

    @Autowired
    private OrderAssignmentService orderAssignmentService;

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

        if(request.getDescription() != null && !request.getDescription().trim().isEmpty()){
            List<String> Specialty = parseCommaSeparatedString(request.getDescription());
            for(String s : Specialty){
                List<Mechanic> mechanics = mechanicMapper.getMechanicBySpecialty(s);
                if(!mechanics.isEmpty()){
                    Mechanic mechanic = mechanics.get(new Random().nextInt(mechanics.size()));
                    orderAssignmentService.createOrderAssignment(repairOrder.getOrderId(), mechanic.getMechanicId());
                }
            }
            repairOrder.setStatus("assigned");
            rowsAffected = repairOrderMapper.updateRepairOrder(repairOrder);
        }

        return rowsAffected > 0 ? repairOrder : null;
    }

    public static List<String> parseCommaSeparatedString(String input) {
        // 处理空输入
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // 方案1：使用split分割（兼容中英文逗号）
        String[] parts = input.split("[，,]");  // 正则匹配中文和英文逗号
        List<String> result = new ArrayList<>();

        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {  // 忽略空元素
                result.add(trimmed);
            }
        }

        return result;

        /*
        // 方案2：Java 8 流式处理（单行版）
        return Arrays.stream(input.split("，|,"))
                     .map(String::trim)
                     .filter(s -> !s.isEmpty())
                     .collect(Collectors.toList());
        */
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

    @Override
    public void setUrged(Integer orderId, Boolean urge) {
        repairOrderMapper.setUrged(orderId, urge);
    }

    @Override
    public List<RepairOrder> getUrgedOrders() {
        return repairOrderMapper.getUrgedOrders();
    }

    @Override
    public boolean deleteRepairOrder(Integer orderId) {
        return repairOrderMapper.deleteRepairOrder(orderId) > 0;
    }
}
