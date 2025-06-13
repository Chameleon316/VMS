package org.lw.vms.mapper;

import org.apache.ibatis.annotations.*;
import org.lw.vms.entity.Material;

import java.util.List;

/**
 * 材料数据访问接口 (Mybatis Mapper)。
 */
@Mapper
public interface MaterialMapper {

    /**
     * 根据 ID 查询材料。
     * @param materialId 材料 ID
     * @return 材料对象
     */
    @Select("SELECT material_id, name, unit_price, stock_quantity FROM material WHERE material_id = #{materialId}")
    Material findById(Integer materialId);

    /**
     * 根据名称查询材料。
     * @param name 材料名称
     * @return 材料对象
     */
    @Select("SELECT material_id, name, unit_price, stock_quantity FROM material WHERE name = #{name}")
    Material findByName(String name);

    /**
     * 插入新的材料记录。
     * @param material 材料对象
     * @return 影响的行数
     */
    @Insert("INSERT INTO material(name, unit_price, stock_quantity) VALUES(#{name}, #{unitPrice}, #{stockQuantity})")
    @Options(useGeneratedKeys = true, keyProperty = "materialId")
    int insertMaterial(Material material);

    /**
     * 更新材料信息。
     * @param material 材料对象
     * @return 影响的行数
     */
    @Update("UPDATE material SET name = #{name}, unit_price = #{unitPrice}, stock_quantity = #{stockQuantity} WHERE material_id = #{materialId}")
    int updateMaterial(Material material);

    /**
     * 删除材料记录。
     * @param materialId 材料 ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM material WHERE material_id = #{materialId}")
    int deleteMaterial(Integer materialId);

    /**
     * 查询所有材料 (管理员用)。
     * @return 所有材料的列表
     */
    @Select("SELECT material_id, name, unit_price, stock_quantity FROM material")
    List<Material> findAllMaterials();
}
