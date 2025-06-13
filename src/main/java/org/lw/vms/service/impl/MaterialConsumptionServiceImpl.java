package org.lw.vms.service.impl;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.lw.vms.DTOs.MaterialConsumptionRequest;
import org.lw.vms.entity.MaterialConsumption;
import org.lw.vms.mapper.MaterialConsumptionMapper;
import org.lw.vms.service.MaterialConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaterialConsumptionServiceImpl implements MaterialConsumptionService {

    @Autowired
    private MaterialConsumptionMapper materialConsumptionMapper;

    @Override
    @Transactional
    public MaterialConsumption recordMaterialConsumption(MaterialConsumptionRequest request) {
        MaterialConsumption consumption = new MaterialConsumption();
        consumption.setOrderId(request.getOrderId());
        consumption.setMaterialId(request.getMaterialId());
        consumption.setQuantity(request.getQuantity());

        int rowsAffected = materialConsumptionMapper.insertMaterialConsumption(consumption);
        return rowsAffected > 0 ? consumption : null;
    }

    @Override
    public List<MaterialConsumption> getMaterialConsumptionsByOrderId(Integer orderId) {
        return materialConsumptionMapper.findByOrderId(orderId);
    }
}
