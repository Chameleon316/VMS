package org.lw.vms.mapper;

import org.apache.ibatis.annotations.*;
import org.lw.vms.entity.MaterialConsumption;

import java.util.List;

/**
 * 材料消耗数据访问接口 (Mybatis Mapper)。
 */
@Mapper
public interface MaterialConsumptionMapper {

    /**
     * 根据 ID 查询材料消耗记录。
     * @param consumptionId 消耗 ID
     * @return 材料消耗对象
     */
    @Select("SELECT consumption_id, order_id, material_id, quantity FROM material_consumption WHERE consumption_id = #{consumptionId}")
    MaterialConsumption findById(Integer consumptionId);

    /**
     * 根据工单 ID 查询材料消耗记录列表。
     * @param orderId 工单 ID
     * @return 材料消耗列表
     */
    @Select("SELECT consumption_id, order_id, material_id, quantity FROM material_consumption WHERE order_id = #{orderId}")
    List<MaterialConsumption> findByOrderId(Integer orderId);

    /**
     * 插入新的材料消耗记录。
     * @param materialConsumption 材料消耗对象
     * @return 影响的行数
     */
    @Insert("INSERT INTO material_consumption(order_id, material_id, quantity) VALUES(#{orderId}, #{materialId}, #{quantity})")
    @Options(useGeneratedKeys = true, keyProperty = "consumptionId")
    int insertMaterialConsumption(MaterialConsumption materialConsumption);

    /**
     * 更新材料消耗记录。
     * @param materialConsumption 材料消耗对象
     * @return 影响的行数
     */
    @Update("UPDATE material_consumption SET order_id = #{orderId}, material_id = #{materialId}, quantity = #{quantity} WHERE consumption_id = #{consumptionId}")
    int updateMaterialConsumption(MaterialConsumption materialConsumption);

    /**
     * 删除材料消耗记录。
     * @param consumptionId 消耗 ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM material_consumption WHERE consumption_id = #{consumptionId}")
    int deleteMaterialConsumption(Integer consumptionId);
}