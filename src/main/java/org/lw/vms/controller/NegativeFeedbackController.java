package org.lw.vms.controller;

import org.lw.vms.DTOs.NegativeFeedbackDto;
import org.lw.vms.mapper.NegativeFeedbackMapper;
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
public class NegativeFeedbackController {
    // 注入你的 Mapper
    private final NegativeFeedbackMapper negativeFeedbackMapper;


    @Autowired
    public NegativeFeedbackController(NegativeFeedbackMapper negativeFeedbackMapper) {
        this.negativeFeedbackMapper = negativeFeedbackMapper;
    }

    @GetMapping("/negative-feedback-mechanics") // 定义新的 API 路径
    public ResponseEntity<List<NegativeFeedbackDto>> getNegativeFeedbackMechanics() {
        // 调用 Mapper 接口中注解定义的方法
        List<NegativeFeedbackDto> result = negativeFeedbackMapper.getNegativeFeedbackOrdersAndStaff();

        if (result.isEmpty()) {
            return ResponseEntity.noContent().build(); // 如果没有数据，返回 204 No Content
        }
        return ResponseEntity.ok(result); // 返回 200 OK 和查询结果
    }
}
