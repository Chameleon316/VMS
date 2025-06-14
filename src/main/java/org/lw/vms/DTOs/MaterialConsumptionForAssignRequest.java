package org.lw.vms.DTOs;

import java.io.Serializable;

public class MaterialConsumptionForAssignRequest implements Serializable {
    private Integer assignmentId;
    private Integer materialId;
    private Integer quantity;

    // Getter and Setter
    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
