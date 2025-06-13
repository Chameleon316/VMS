package org.lw.vms.service;

import org.lw.vms.DTOs.LoginResponse;
import org.lw.vms.DTOs.UserLoginRequest;
import org.lw.vms.DTOs.UserRegisterRequest;
import org.lw.vms.entity.User;

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
}
