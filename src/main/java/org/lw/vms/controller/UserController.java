package org.lw.vms.controller;

import io.jsonwebtoken.Claims;
import org.lw.vms.DTOs.LoginResponse;
import org.lw.vms.DTOs.UserLoginRequest;
import org.lw.vms.DTOs.UserRegisterRequest;
import org.lw.vms.entity.RepairOrder;
import org.lw.vms.entity.User;
import org.lw.vms.entity.Vehicle;
import org.lw.vms.service.UserService;
import org.lw.vms.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest; // 用于获取请求头
import org.lw.vms.utils.JwtUtil; // 用于解析 JWT
import io.jsonwebtoken.Claims;


import java.util.List;

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

    @Autowired
    private JwtUtil jwtUtil; // 注入 JwtUtil，用于解析 JWT

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
        System.out.println("login start");
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
    // --- 用户角色查询需求接口 ---

    /**
     * 查询当前登录用户的账户信息。
     * @param request HttpServletRequest 用于获取 Authorization 头
     * @return 用户账户信息 (不含密码)
     */
    @GetMapping("/myAccount")
    public Result<User> getMyAccountInfo(@RequestHeader("Authorization") String token) {
//        System.out.println("Controller getMyAccountInfo called");
//        System.out.println("getMyAccountInfo called");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "未授权：缺少或无效的JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer userId = claims.get("userId", Integer.class);

        User user = userService.getUserAccountInfo(userId);
        if (user != null) {
            return Result.success(user, "查询账户信息成功");
        }
        return Result.fail("用户不存在");
    }

    /**
     * 查询当前登录用户的所有车辆信息。
     * @param request HttpServletRequest 用于获取 Authorization 头
     * @return 车辆列表
     */
    @GetMapping("/myVehicles")
    public Result<List<Vehicle>> getMyVehicles(@RequestHeader("Authorization") String token) {
//        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "未授权：缺少或无效的JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer userId = claims.get("userId", Integer.class);
        String role = claims.get("role", String.class);

        if (!"user".equals(role) && !"admin".equals(role)) { // 只有 'user' 和 'admin' 角色可以查询车辆
            return Result.fail(403, "无权限查询车辆信息");
        }

        List<Vehicle> vehicles = userService.getUserVehicles(userId);
        return Result.success(vehicles, "查询我的车辆成功");
    }

    /**
     * 查询当前登录用户的所有维修工单信息。
     * @param request HttpServletRequest 用于获取 Authorization 头
     * @return 维修工单列表
     */
    @GetMapping("/myRepairOrders")
    public Result<List<RepairOrder>> getMyRepairOrders(@RequestHeader("Authorization") String token) {
//        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "未授权：缺少或无效的JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer userId = claims.get("userId", Integer.class);
        String role = claims.get("role", String.class);

        if (!"user".equals(role) && !"admin".equals(role)) { // 只有 'user' 和 'admin' 角色可以查询工单
            return Result.fail(403, "无权限查询维修工单");
        }

        List<RepairOrder> orders = userService.getUserRepairOrders(userId);
        return Result.success(orders, "查询我的维修工单成功");
    }

    /**
     * 查询当前登录用户的历史维修记录 (已完成的工单)。
     * @param request HttpServletRequest 用于获取 Authorization 头
     * @return 历史维修记录列表
     */
    @GetMapping("/myHistoricalRepairRecords")
    public Result<List<RepairOrder>> getMyHistoricalRepairRecords(@RequestHeader("Authorization") String token) {
//        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "未授权：缺少或无效的JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        Integer userId = claims.get("userId", Integer.class);
        String role = claims.get("role", String.class);

        if (!"user".equals(role) && !"admin".equals(role)) {
            return Result.fail(403, "无权限查询历史维修记录");
        }

        List<RepairOrder> records = userService.getUserHistoricalRepairRecords(userId);
        return Result.success(records, "查询历史维修记录成功");
    }

    // --- 管理员角色查询需求接口 (需要更严格的权限控制) ---

    /**
     * 查询所有用户账户信息 (仅限管理员)。
     * @param request HttpServletRequest 用于获取 Authorization 头
     * @return 所有用户列表
     */
    @GetMapping("/admin/allUsers")
    public Result<List<User>> getAllUsers(@RequestHeader("Authorization") String token) {
//        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "未授权：缺少或无效的JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"admin".equals(role)) {
            return Result.fail(403, "无权限访问：此接口仅限管理员");
        }

        List<User> users = userService.getAllUsers();
        return Result.success(users, "查询所有用户成功");
    }

    /**
     * 查询所有维修人员账户信息 (仅限管理员)。
     * @param request HttpServletRequest 用于获取 Authorization 头
     * @return 所有维修人员列表
     */
    @GetMapping("/admin/allMechanics")
    public Result<List<User>> getAllMechanicsAccountInfo(@RequestHeader("Authorization") String token) {
//        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "未授权：缺少或无效的JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"admin".equals(role)) {
            return Result.fail(403, "无权限访问：此接口仅限管理员");
        }

        List<User> mechanics = userService.getAllMechanicsAccountInfo();
        return Result.success(mechanics, "查询所有维修人员成功");
    }

    /**
     * 查询所有维修工单信息 (仅限管理员)。
     * @param request HttpServletRequest 用于获取 Authorization 头
     * @return 所有维修工单列表
     */
    @GetMapping("/admin/allRepairOrders")
    public Result<List<RepairOrder>> getAllRepairOrders(@RequestHeader("Authorization") String token) {
//        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "未授权：缺少或无效的JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"admin".equals(role)) {
            return Result.fail(403, "无权限访问：此接口仅限管理员");
        }

        List<RepairOrder> orders = userService.getAllRepairOrders();
        return Result.success(orders, "查询所有维修工单成功");
    }

    /**
     * 查询所有车辆信息 (仅限管理员)。
     * @param request HttpServletRequest 用于获取 Authorization 头
     * @return 所有车辆列表
     */
    @GetMapping("/admin/allVehicles")
    public Result<List<Vehicle>> getAllVehicles(@RequestHeader("Authorization") String token) {
//        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return Result.fail(401, "未授权：缺少或无效的JWT");
        }
        String jwt = token.substring(7);
        Claims claims = jwtUtil.extractAllClaims(jwt);
        String role = claims.get("role", String.class);

        if (!"admin".equals(role)) {
            return Result.fail(403, "无权限访问：此接口仅限管理员");
        }

        List<Vehicle> vehicles = userService.getAllVehicles();
        return Result.success(vehicles, "查询所有车辆成功");
    }

}
