package org.lw.vms.DTOs;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */

import org.lw.vms.entity.User;

import java.io.Serializable;

/**
 * 登录响应数据传输对象。
 * 封装了登录成功后的用户信息 (不含密码) 和 JWT Token。
 */
public class LoginResponse implements Serializable {
    private User user; // 登录成功的用户信息 (不含密码)
    private String token; // JWT Token

    // 默认构造函数
    public LoginResponse() {
    }

    // 全参构造函数
    public LoginResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }

    // --- Getter 和 Setter 方法 ---

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "user=" + user +
                ", token='" + "[PROTECTED]" + '\'' + // 避免日志中打印完整 token
                '}';
    }
}

