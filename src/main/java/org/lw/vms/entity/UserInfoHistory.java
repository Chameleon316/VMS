package org.lw.vms.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserInfoHistory implements Serializable {
    /**
     * 历史记录ID (主键)
     */
    private Integer historyId;

    /**
     * 用户ID (外键关联user表)
     */
    private Integer userId;

    /**
     * 旧的姓名
     */
    private String oldName;

    /**
     * 旧的联系方式
     */
    private String oldContact;

    /**
     * 新的姓名
     */
    private String newName;

    /**
     * 新的联系方式
     */
    private String newContact;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    // 构造方法
    public UserInfoHistory() {
    }

    public UserInfoHistory(Integer userId, String oldName, String oldContact,
                           String newName, String newContact) {
        this.userId = userId;
        this.oldName = oldName;
        this.oldContact = oldContact;
        this.newName = newName;
        this.newContact = newContact;
    }

    // Getter 和 Setter 方法
    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getOldContact() {
        return oldContact;
    }

    public void setOldContact(String oldContact) {
        this.oldContact = oldContact;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewContact() {
        return newContact;
    }

    public void setNewContact(String newContact) {
        this.newContact = newContact;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    // toString 方法（可选，用于调试）
    @Override
    public String toString() {
        return "UserInfoHistory{" +
                "changeId=" + historyId +
                ", userId=" + userId +
                ", oldName='" + oldName + '\'' +
                ", oldContact='" + oldContact + '\'' +
                ", newName='" + newName + '\'' +
                ", newContact='" + newContact + '\'' +
                ", changeTime=" + updateTime +
                '}';
    }
}
