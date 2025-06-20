package org.lw.vms.controller;

import org.lw.vms.entity.UserInfoHistory;
import org.lw.vms.mapper.UserInfoHistoryMapper;
import org.lw.vms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/userInfoHistory")
public class UserInfoHistoryController {
    @Autowired
    private UserInfoHistoryMapper userInfoHistoryMapper;

    @GetMapping
    public Result<List<UserInfoHistory>> getAllUserInfoHistory(){
        return Result.success(userInfoHistoryMapper.selectAllHistory(), "获取成功");
    }
}

