package org.lw.vms.service;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.lw.vms.DTOs.MaterialConsumptionForAssignRequest;
import org.lw.vms.DTOs.MaterialConsumptionRequest;
import org.lw.vms.entity.MaterialConsumption;
import org.lw.vms.entity.MaterialConsumptionForAssignment;

import java.util.List;

public interface MaterialConsumptionService {
    /**
     * 记录材料消耗。
     * @param request 材料消耗请求 DTO
     * @return 创建成功的材料消耗对象
     */
    MaterialConsumption recordMaterialConsumption(MaterialConsumptionRequest request);

    /**
     * 根据工单 ID 获取所有材料消耗记录。
     * @param orderId 工单 ID
     * @return 材料消耗列表
     */
    List<MaterialConsumption> getMaterialConsumptionsByOrderId(Integer orderId);

    MaterialConsumptionForAssignment recordMaterialConsumptionToAssignment(MaterialConsumptionForAssignRequest request);

    List<MaterialConsumptionForAssignment> getMaterialConsumptionByAssignmentId(Long assignmentId);
}