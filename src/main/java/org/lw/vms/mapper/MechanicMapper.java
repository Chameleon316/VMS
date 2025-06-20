package org.lw.vms.mapper;

import org.apache.ibatis.annotations.*;
import org.lw.vms.entity.Mechanic;

import java.util.List;

/**
 * 维修人员数据访问接口 (Mybatis Mapper)。
 */
@Mapper
public interface MechanicMapper {

    /**
     * 根据 ID 查询维修人员。
     * @param mechanicId 维修人员 ID
     * @return 维修人员对象
     */
    @Select("SELECT mechanic_id, user_id, specialty, hourly_rate FROM mechanic WHERE mechanic_id = #{mechanicId}")
    Mechanic findById(Integer mechanicId);

    /**
     * 根据用户 ID 查询维修人员。
     * @param userId 用户 ID
     * @return 维修人员对象
     */
    @Select("SELECT mechanic_id, user_id, specialty, hourly_rate FROM mechanic WHERE user_id = #{userId}")
    Mechanic findByUserId(Integer userId);

    /**
     * 插入新的维修人员记录。
     * @param mechanic 维修人员对象
     * @return 影响的行数
     */
    @Insert("INSERT INTO mechanic(user_id, specialty, hourly_rate) VALUES(#{userId}, #{specialty}, #{hourlyRate})")
    @Options(useGeneratedKeys = true, keyProperty = "mechanicId")
    int insertMechanic(Mechanic mechanic);

    /**
     * 更新维修人员信息。
     * @param mechanic 维修人员对象
     * @return 影响的行数
     */
    @Update("UPDATE mechanic SET specialty = #{specialty}, hourly_rate = #{hourlyRate} WHERE mechanic_id = #{mechanicId}")
    int updateMechanic(Mechanic mechanic);

    /**
     * 删除维修人员记录。
     * @param mechanicId 维修人员 ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM mechanic WHERE mechanic_id = #{mechanicId}")
    int deleteMechanic(Integer mechanicId);

    /**
     * 查询所有维修人员信息 (管理员用)。
     * @return 所有维修人员的列表
     */
    @Select("SELECT mechanic_id, user_id, specialty, hourly_rate FROM mechanic")
    List<Mechanic> findAllMechanics();

    @Select("SELECT mechanic_id, user_id, specialty, hourly_rate FROM mechanic WHERE specialty = #{s}")
    List<Mechanic> getMechanicBySpecialty(String s);
}
