package org.lw.vms.entity;

import org.lw.vms.enums.RepairOrderAssignmentStatus;

public class RepairOrderAssignment {
    private int repairOrderAssignmentId;
    private int repairOrderId;
    private int mechanicId;
    private double workHours;
    private RepairOrderAssignmentStatus status;
}
