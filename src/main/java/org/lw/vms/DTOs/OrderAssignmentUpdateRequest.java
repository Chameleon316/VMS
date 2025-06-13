package org.lw.vms.DTOs;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import java.io.Serializable;

/**
 * 工单分配更新请求数据传输对象。
 * 用于维修人员接收或拒绝工单。
 */
public class OrderAssignmentUpdateRequest implements Serializable {
    private Integer assignmentId;
    private String status; // 'accepted' 或 'rejected'

    // Getter and Setter
    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

