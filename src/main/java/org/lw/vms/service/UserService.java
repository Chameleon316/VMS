package org.lw.vms.service;

import org.lw.vms.DTOs.LoginResponse;
import org.lw.vms.DTOs.UserLoginRequest;
import org.lw.vms.DTOs.UserRegisterRequest;
import org.lw.vms.entity.RepairOrder;
import org.lw.vms.entity.User;
import org.lw.vms.entity.Vehicle;

import java.util.List;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
public interface UserService {
    /**
     * 用户注册功能。
     *
     * @param request 包含注册信息的请求对象 (username, password, name, contact, role)。
     * @return 注册成功的用户对象（不包含密码信息），如果用户名已存在或注册失败则返回 null。
     */
    User register(UserRegisterRequest request);

    /**
     * 用户登录功能。
     *
     * @param request 包含登录凭据的请求对象 (username, password)。
     * @return 登录成功的 LoginResponse 对象（包含用户信息和 JWT），如果用户名不存在或密码不匹配则返回 null。
     */
    LoginResponse login(UserLoginRequest request); // 返回类型修改为 LoginResponse

    // --- 用户查询需求 (User role specific queries) ---

    /**
     * 根据用户ID获取用户账户信息。
     * @param userId 用户 ID
     * @return 用户对象 (不含密码)
     */
    User getUserAccountInfo(Integer userId);

    /**
     * 根据用户ID查询其名下所有车辆信息。
     * @param userId 用户 ID
     * @return 车辆列表
     */
    List<Vehicle> getUserVehicles(Integer userId);

    /**
     * 根据用户ID查询其提交的所有维修工单信息。
     * @param userId 用户 ID
     * @return 维修工单列表
     */
    List<RepairOrder> getUserRepairOrders(Integer userId);

    /**
     * 根据用户ID查询其历史维修记录 (已完成的工单)。
     * @param userId 用户 ID
     * @return 历史维修记录列表
     */
    List<RepairOrder> getUserHistoricalRepairRecords(Integer userId);


    // --- 管理员查询需求 (Admin role specific queries) ---

    /**
     * 查询所有用户账户信息 (管理员)。
     * @return 所有用户列表 (不含密码)
     */
    List<User> getAllUsers();

    /**
     * 查询所有车辆信息 (管理员)。
     * @return 所有车辆列表
     */
    List<Vehicle> getAllVehicles();

    /**
     * 查询所有维修工单信息 (管理员)。
     * @return 所有维修工单列表
     */
    List<RepairOrder> getAllRepairOrders();

    /**
     * 查询所有维修人员信息 (管理员)。
     * @return 所有维修人员列表
     */
    List<User> getAllMechanicsAccountInfo(); // 获取维修人员的用户信息 (User)
}
