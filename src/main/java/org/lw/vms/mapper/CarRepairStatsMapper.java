package org.lw.vms.mapper;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */

import org.apache.ibatis.annotations.Mapper;
import org.lw.vms.DTOs.CarRepairStatsDto;

import java.util.List;

@Mapper // 标记这是一个 MyBatis Mapper 接口
public interface CarRepairStatsMapper {

    /**
     * 统计各车型的维修次数与平均维修费用
     * @return 包含各车型统计数据的列表
     */
    List<CarRepairStatsDto> getCarRepairStatistics();
}