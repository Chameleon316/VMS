package org.lw.vms.service;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.lw.vms.entity.Material;

import java.util.List;

public interface MaterialService {
    /**
     * 获取所有材料信息。
     * @return 材料列表
     */
    List<Material> getAllMaterials();

    /**
     * 根据ID获取材料信息。
     * @param materialId 材料ID
     * @return 材料对象
     */
    Material getMaterialById(Integer materialId);

    /**
     * 新增材料。
     * @param material 材料对象
     * @return 新增的材料对象
     */
    Material addMaterial(Material material);

    /**
     * 更新材料信息。
     * @param material 材料对象
     * @return 更新后的材料对象
     */
    Material updateMaterial(Material material);

    /**
     * 删除材料。
     * @param materialId 材料ID
     * @return 是否成功删除
     */
    boolean deleteMaterial(Integer materialId);
}