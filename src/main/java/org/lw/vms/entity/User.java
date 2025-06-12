package org.lw.vms.entity;

import java.io.Serializable;

/**
 * 用户实体类，对应数据库中的 'user' 表。
 */
public class User implements Serializable {
    private Integer userId; // 对应 user_id，主键
    private String username; // 对应 username，用户登录名
    private String password; // 对应 password，加密后的密码
    private String name; // 对应 name，用户姓名
    private String contact; // 对应 contact，联系方式
    private String role; // 对应 role，用户角色，枚举类型 ('user', 'mechanic', 'admin')

    // 默认构造函数
    public User() {
    }

    // 全参构造函数 (可选，通常通过setter设置)
    public User(Integer userId, String username, String password, String name, String contact, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.contact = contact;
        this.role = role;
    }

    // --- Getter 和 Setter 方法 ---

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + "[PROTECTED]" + '\'' + // 避免日志中打印密码
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
