package org.lw.vms.service.impl;

import org.lw.vms.DTOs.MechanicAssignmentsResponse;
import org.lw.vms.entity.Mechanic;
import org.lw.vms.entity.OrderAssignment;
import org.lw.vms.entity.RepairOrder;
import org.lw.vms.mapper.MechanicMapper;
import org.lw.vms.mapper.OrderAssignmentMapper;
import org.lw.vms.mapper.RepairOrderMapper;
import org.lw.vms.service.MechanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 维修人员业务逻辑服务实现类。
 */
@Service
public class MechanicServiceImpl implements MechanicService {

    @Autowired
    private MechanicMapper mechanicMapper;

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Autowired
    private OrderAssignmentMapper orderAssignmentMapper;

    @Override
    public Mechanic getMechanicDetailsByUserId(Integer userId) {
        return mechanicMapper.findByUserId(userId);
    }

    public Mechanic getMechanicDetailsById(Integer mechanicId) {
        return mechanicMapper.findById(mechanicId);
    }

    @Override
    public List<RepairOrder> getMechanicRepairOrders(Integer mechanicId) {
        // 查询该维修人员所有相关的工单分配记录
        List<OrderAssignment> assignments = orderAssignmentMapper.findByMechanicId(mechanicId);

        // 从这些分配记录中提取工单ID，然后查询对应的维修工单
        return assignments.stream()
                .map(assignment -> repairOrderMapper.findById(assignment.getOrderId()))
                .filter(java.util.Objects::nonNull) // 过滤掉可能为空的工单
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public Mechanic gerMechanicDerailsByMechanicId(Integer mechanicId) {
        return mechanicMapper.findById(mechanicId);
    }

    @Override
    public BigDecimal getMechanicTotalLaborCostIncome(Integer mechanicId) {
        Mechanic mechanic = mechanicMapper.findById(mechanicId);
        if (mechanic == null || mechanic.getHourlyRate() == null) {
            return BigDecimal.ZERO; // 如果维修人员不存在或时薪未设置，返回0
        }

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal hourlyRate = mechanic.getHourlyRate();

        // 获取该维修人员所有的工单分配记录
        List<OrderAssignment> assignments = orderAssignmentMapper.findByMechanicId(mechanicId);

        for (OrderAssignment assignment : assignments) {
            // 只计算已接受的工单分配，并且有工作小时数
            if ("completed".equals(assignment.getStatus()) && assignment.getLaborCost() != null) {
                BigDecimal laborCost = assignment.getLaborCost();
                totalIncome = totalIncome.add(laborCost);
            }
        }
        return totalIncome;
    }

    @Override
    public Mechanic updateMechanicInfo(Integer mechanicId, String specialty, BigDecimal hourlyRate) {
        Mechanic mechanic = mechanicMapper.findById(mechanicId);
        if (mechanic == null) {
            return null; // 维修人员不存在
        }
        mechanic.setSpecialty(specialty);
        mechanic.setHourlyRate(hourlyRate);
        int rowsAffected = mechanicMapper.updateMechanic(mechanic);
        return rowsAffected > 0 ? mechanic : null;
    }

    @Override
    public List<MechanicAssignmentsResponse> getAssignmentsByMechanicId(Integer mechanicId) {
        return orderAssignmentMapper.getAssignmentsByMechanicId(mechanicId);
    }
}