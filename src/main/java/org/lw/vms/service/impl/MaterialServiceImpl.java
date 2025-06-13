package org.lw.vms.service.impl;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.lw.vms.entity.Material;
import org.lw.vms.mapper.MaterialMapper;
import org.lw.vms.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public List<Material> getAllMaterials() {
        return materialMapper.findAllMaterials();
    }

    @Override
    public Material getMaterialById(Integer materialId) {
        return materialMapper.findById(materialId);
    }

    @Override
    @Transactional
    public Material addMaterial(Material material) {
        int rowsAffected = materialMapper.insertMaterial(material);
        return rowsAffected > 0 ? material : null;
    }

    @Override
    @Transactional
    public Material updateMaterial(Material material) {
        int rowsAffected = materialMapper.updateMaterial(material);
        return rowsAffected > 0 ? materialMapper.findById(material.getMaterialId()) : null;
    }

    @Override
    @Transactional
    public boolean deleteMaterial(Integer materialId) {
        return materialMapper.deleteMaterial(materialId) > 0;
    }
}