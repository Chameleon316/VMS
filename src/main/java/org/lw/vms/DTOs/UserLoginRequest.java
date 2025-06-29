package org.lw.vms.DTOs;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
public class UserLoginRequest {
    private String username;
    private String password;

    // --- Getter 和 Setter 方法 ---

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
}
