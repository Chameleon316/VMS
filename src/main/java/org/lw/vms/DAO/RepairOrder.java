package org.lw.vms.DAO;

import org.lw.vms.enums.RepairOrderStatus;
import org.lw.vms.enums.VehicleBrand;
import org.lw.vms.enums.VehicleModel;

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
