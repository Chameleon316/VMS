package org.lw.vms.DAO;

import lombok.Data;
import org.lw.vms.enums.UserRole;

@Data
public class User {
    private int userId;
    private String userName;
    private String realName;
    private String password;
    private String phone;
    private UserRole role;
}
