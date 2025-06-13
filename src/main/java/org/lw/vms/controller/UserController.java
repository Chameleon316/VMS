package org.lw.vms.controller;

import org.lw.vms.DTOs.LoginResponse;
import org.lw.vms.DTOs.UserLoginRequest;
import org.lw.vms.DTOs.UserRegisterRequest;
import org.lw.vms.entity.User;
import org.lw.vms.service.UserService;
import org.lw.vms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
/**
 * 用户相关的 RESTful API 控制器。
 * 负责处理用户注册和登录的 HTTP 请求。
 */
@CrossOrigin(origins = "http://localhost:5173") // 允许来自该源的跨域请求
@RestController // 标识这是一个 RESTful 控制器，其方法返回的数据直接作为 HTTP 响应体
@RequestMapping("/api/user") // 定义所有接口的父路径
public class UserController {
    // 自动注入 UserService，用于调用业务逻辑
    @Autowired
    private UserService userService;

    /**
     * 用户注册接口。
     * 接收 POST 请求，路径为 /api/user/register。
     *
     * @param request 包含注册信息的请求体 (JSON 格式)，会自动映射到 UserRegisterRequest 对象。
     * @return 统一响应结果，包含注册是否成功的信息和数据。
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody UserRegisterRequest request) {
        System.out.println("Received registration request: " + request);
        // 1. 对传入参数进行基本校验
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            return Result.fail("用户名不能为空");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return Result.fail("密码不能为空");
        }
        if (request.getName() == null || request.getName().isEmpty()) {
            return Result.fail("姓名不能为空");
        }
        // contact 和 role 可以是可选的，根据业务需求决定是否校验

        // 2. 调用 UserService 进行用户注册业务处理
        User registeredUser = userService.register(request);

        // 3. 根据业务处理结果返回统一响应
        if (registeredUser != null) {
            return Result.success(registeredUser, "注册成功！");
        } else {
            // 通常是用户名已存在导致的注册失败
            return Result.fail("注册失败：用户名可能已存在或服务器内部错误。");
        }
    }

    /**
     * 用户登录接口。
     * 接收 POST 请求，路径为 /api/user/login。
     *
     * @param request 包含登录凭据的请求体 (JSON 格式)，会自动映射到 UserLoginRequest 对象。
     * @return 统一响应结果，包含登录是否成功的信息和数据。
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody UserLoginRequest request) {
        // 1. 对传入参数进行基本校验
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            return Result.fail("用户名不能为空");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return Result.fail("密码不能为空");
        }

        // 2. 调用 UserService 进行用户登录业务处理
        LoginResponse loggedInResponse = userService.login(request); // 接收 LoginResponse

        // 3. 根据业务处理结果返回统一响应
        if (loggedInResponse != null) {
            return Result.success(loggedInResponse, "登录成功！"); // 返回 LoginResponse
        } else {
            // 通常是用户名不存在或密码错误导致的登录失败
            return Result.fail("登录失败：用户名或密码不正确。");
        }
    }

}
