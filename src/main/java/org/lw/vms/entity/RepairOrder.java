package org.lw.vms.entity;

import org.lw.vms.enums.RepairOrderStatus;

import java.time.LocalDateTime;

public class RepairOrder {
    private int repairOrderID;
    private int userID;
    private int vehicleID;
    private RepairOrderStatus status;
    private double totalLaborCost;
    private double totalMaterialCost;
    private LocalDateTime cratedAt;
    private LocalDateTime completedAt;
}
