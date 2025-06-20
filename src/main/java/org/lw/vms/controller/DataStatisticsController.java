package org.lw.vms.controller;

import org.lw.vms.DTOs.CarRepairStatsDto;
import org.lw.vms.mapper.CarRepairStatsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
@RestController
public class DataStatisticsController {

    private final CarRepairStatsMapper carRepairStatsMapper;

    @Autowired
    public DataStatisticsController(CarRepairStatsMapper carRepairStatsMapper) {
        this.carRepairStatsMapper = carRepairStatsMapper;

    }


    @GetMapping("/repair-summary") // 定义具体的 API 路径
    public ResponseEntity<List<CarRepairStatsDto>> getCarRepairSummary() {
        List<CarRepairStatsDto> stats = carRepairStatsMapper.getCarRepairStatistics();
        if (stats.isEmpty()) {
            return ResponseEntity.noContent().build(); // 如果没有数据，返回 204 No Content
        }
        return ResponseEntity.ok(stats); // 返回 200 OK 和统计数据
    }



}
