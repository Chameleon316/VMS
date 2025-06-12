package org.lw.vms.DTOs;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
public class UserRegisterRequest {
    private String username;
    private String password;
    private String name;
    private String contact;
    private String role; // 可选，默认为 'user'

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


}
