package org.lw.vms.service.impl;

import org.lw.vms.DTOs.LoginResponse;
import org.lw.vms.DTOs.UserLoginRequest;
import org.lw.vms.DTOs.UserRegisterRequest;
import org.lw.vms.entity.*;
import org.lw.vms.mapper.*;
import org.lw.vms.service.UserService;
import org.lw.vms.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
/**
        * @version 1.0
        * @auther Yongqi Wang
 */


/**
        * 用户业务逻辑服务实现类。
        * 实现了用户注册和登录的核心逻辑。
        */
@Service // 标识这是一个 Spring Service 组件
public class UserServiceImpl implements UserService {

    // 自动注入 UserMapper，用于数据库操作
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VehicleMapper vehicleMapper; // 注入 VehicleMapper

    @Autowired
    private RepairOrderMapper repairOrderMapper; // 注入 RepairOrderMapper

    @Autowired
    private MechanicMapper mechanicMapper; // 注入 MechanicMapper

    // 自动注入 JwtUtil，用于生成和解析 JWT
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserInfoHistoryMapper userInfoHistoryMapper; // 注入 UserInfoHistoryMapper

    /**
            * 用户注册实现。
            * 包含用户名存在性检查，直接存储明文密码和用户数据插入。
            *
            * @param request 注册请求DTO
     * @return 注册成功的用户对象（密码已置空），注册失败则返回 null
            */
    @Override
    @Transactional // 确保注册操作的原子性，如果在方法执行过程中发生异常，所有数据库操作将回滚
    public User register(UserRegisterRequest request) {
        // 1. 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(request.getUsername());
        if (existingUser != null) {
            System.err.println("注册失败：用户名 '" + request.getUsername() + "' 已存在。");
            return null; // 用户名已存在，注册失败
        }

        // 2. 直接使用明文密码（不再加密）
        String plainPassword = request.getPassword();

        // 3. 构建新的用户对象
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(plainPassword); // 直接存储明文密码
        newUser.setName(request.getName());
        newUser.setContact(request.getContact());
        // 如果请求中没有指定角色，则默认设置为 'user'
        newUser.setRole(request.getRole() != null ? request.getRole() : "user");

        // 4. 插入新用户到数据库
        int rowsAffected = userMapper.insertUser(newUser);

        if(rowsAffected > 0 && Objects.equals(request.getRole(), "mechanic")){
            Mechanic mechanic = new Mechanic();
            mechanic.setUserId(newUser.getUserId());
            mechanic.setSpecialty("待分配");
            mechanic.setHourlyRate(BigDecimal.ZERO);
            int rowsAffected1 = mechanicMapper.insertMechanic(mechanic);
        }


        if (rowsAffected > 0) {
            // 注册成功，出于安全考虑，返回前将密码置空
            newUser.setPassword(null);
            System.out.println("用户 '" + newUser.getUsername() + "' 注册成功。");
            return newUser;
        } else {
            System.err.println("用户 '" + request.getUsername() + "' 注册失败：数据库插入操作未影响任何行。");
            return null; // 数据库操作失败
        }
    }

    /**
            * 用户登录实现。
            * 包含用户存在性检查和明文密码匹配验证。
            *
            * @param request 登录请求DTO
     * @return 登录成功的用户对象（密码已置空），登录失败则返回 null
            */
    public LoginResponse login(UserLoginRequest request) {
        System.out.println("用户 '" + request.getUsername() + "' 正在尝试登录...");
        // 1. 根据用户名查询用户
        User user = userMapper.findByUsername(request.getUsername());

        // 2. 检查用户是否存在
        if (user == null) {
            System.err.println("登录失败：用户 '" + request.getUsername() + "' 不存在。");
            return null; // 用户不存在
        }

        // 3. 验证密码
        // 使用 passwordEncoder.matches() 比较前端传来的明文密码和数据库中存储的加密密码
        if (request.getPassword().equals(user.getPassword())) {
            // 登录成功
            System.out.println("用户 '" + user.getUsername() + "' 登录成功。");

            // 4. 生成 JWT Token
            String jwtToken = jwtUtil.generateToken(user);

            // 5. 构建 LoginResponse 对象，出于安全考虑，返回前将用户密码置空
            User responseUser = new User();
            responseUser.setUserId(user.getUserId());
            responseUser.setUsername(user.getUsername());
            responseUser.setName(user.getName());
            responseUser.setContact(user.getContact());
            responseUser.setRole(user.getRole());

            return new LoginResponse(responseUser, jwtToken);
        } else {
            System.err.println("登录失败：用户 '" + request.getUsername() + "' 密码不匹配。");
            return null; // 密码不匹配
        }
    }

    // --- 用户查询需求实现 ---

    @Override
    public User getUserAccountInfo(Integer userId) {
        System.out.println("info service start");
        User user = userMapper.findById(userId);
        if (user != null) {
            user.setPassword(null); // 安全起见，不返回密码
        }
        return user;
    }

    @Override
    public List<Vehicle> getUserVehicles(Integer userId) {
        return vehicleMapper.findByUserId(userId);
    }

    @Override
    public List<RepairOrder> getUserRepairOrders(Integer userId) {
        return repairOrderMapper.findByUserId(userId);
    }

    @Override
    public List<RepairOrder> getUserHistoricalRepairRecords(Integer userId) {
        // 假设历史维修记录是已完成的工单
        return repairOrderMapper.findByUserId(userId).stream()
                .filter(order -> "completed".equals(order.getStatus()))
                .collect(Collectors.toList());
    }

    // --- 管理员查询需求实现 ---

    @Override
    public List<User> getAllUsers() {
        // 获取所有用户，并移除密码信息
        return userMapper.findAllUsers().stream()
                .peek(user -> user.setPassword(null))
                .collect(Collectors.toList());
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleMapper.findAllVehicles();
    }

    @Override
    public List<RepairOrder> getAllRepairOrders() {
        return repairOrderMapper.findAllRepairOrders();
    }

    @Override
    public List<User> getAllMechanicsAccountInfo() {
        // 获取所有角色为 'mechanic' 的用户信息
        List<User> users = userMapper.findAllUsers();
        return users.stream()
                .filter(user -> "mechanic".equals(user.getRole()))
                .peek(user -> user.setPassword(null)) // 移除密码
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        User oldUser = userMapper.findById(user.getUserId());
        String oldName = oldUser.getName();
        String oldContact = oldUser.getContact();
        userMapper.updateUser(user);
        String newName = user.getName();
        String newContact = user.getContact();
        UserInfoHistory history = new UserInfoHistory();
        history.setUserId(user.getUserId());
        history.setOldName(oldName);
        history.setOldContact(oldContact);
        history.setNewName(newName);
        history.setNewContact(newContact);
        history.setUpdateTime(LocalDateTime.now());
        userInfoHistoryMapper.insertHistory(history);
        return user;
    }
}