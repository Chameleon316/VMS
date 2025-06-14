package org.lw.vms.service.impl;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.lw.vms.DTOs.OrderAssignmentUpdateRequest;
import org.lw.vms.DTOs.UpdateWorkingHourRequest;
import org.lw.vms.entity.OrderAssignment;
import org.lw.vms.mapper.OrderAssignmentMapper;
import org.lw.vms.service.OrderAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderAssignmentServiceImpl implements OrderAssignmentService {

    @Autowired
    private OrderAssignmentMapper orderAssignmentMapper;

    @Override
    @Transactional
    public OrderAssignment updateOrderAssignmentStatus(OrderAssignmentUpdateRequest request) {
        OrderAssignment existingAssignment = orderAssignmentMapper.findById(request.getAssignmentId());
        if (existingAssignment == null) {
            return null; // 分配记录不存在
        }
        existingAssignment.setStatus(request.getStatus()); // 更新状态
        int rowsAffected = orderAssignmentMapper.updateOrderAssignment(existingAssignment);
        return rowsAffected > 0 ? existingAssignment : null;
    }

    @Override
    public List<OrderAssignment> getAssignmentsByOrderId(Integer orderId) {
        return orderAssignmentMapper.findByOrderId(orderId);
    }

    @Override
    public List<OrderAssignment> getAssignmentsByMechanicId(Integer mechanicId) {
        return orderAssignmentMapper.findByMechanicId(mechanicId);
    }

    @Override
    @Transactional
    public OrderAssignment createOrderAssignment(Integer orderId, Integer mechanicId) {
        System.out.println(orderId + ", " + mechanicId);
        OrderAssignment newAssignment = new OrderAssignment();
        newAssignment.setOrderId(orderId);
        newAssignment.setMechanicId(mechanicId);
        newAssignment.setStatus("pending"); // 新分配默认为待接受

        int rowsAffected = orderAssignmentMapper.insertOrderAssignment(newAssignment);
        return rowsAffected > 0 ? newAssignment : null;
    }

    @Override
    public OrderAssignment acceptByMechanic(Integer assignmentId) {
        return orderAssignmentMapper.acceptByMechanic(assignmentId);
    }

    @Override
    public List<OrderAssignment> getAllAssignmentsByOrderId(Integer orderId){
        return orderAssignmentMapper.findByOrderId(orderId);
    }

    @Override
    public int updateWorkingTime(UpdateWorkingHourRequest request){
        return orderAssignmentMapper.updateWorkingTime(request);
    }

    @Override
    public OrderAssignment getAllAssignmentsByAssignmentId(Integer assignmentId){
        return orderAssignmentMapper.findByAssignmentId(assignmentId);
    }
}
