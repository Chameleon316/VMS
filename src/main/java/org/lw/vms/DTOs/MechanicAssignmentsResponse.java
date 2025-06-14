package org.lw.vms.DTOs;

import java.math.BigDecimal;
import java.util.Date;

public record MechanicAssignmentsResponse(
        Integer orderId,            // 工单主键（对应order_id）
        Integer vehicleId,          // 关联车辆ID（对应vehicle_id）
        Integer userId,             // 报修用户ID（对应user_id）
        Date datetime,              // 报修时间
        String orderStatus,         // 工单状态（原status字段）
        BigDecimal totalMaterialCost, // 总材料费
        BigDecimal totalLaborCost,   // 总工时费
        Date completionTime,        // 完成时间
        String description,         // 描述信息
        Integer assignmentId,       // 分配记录主键（对应assignment_id）
        Integer mechanicId,         // 维修人员ID（对应mechanic_id）
        BigDecimal hoursWorked,     // 实际工作小时数
        String assignmentStatus     // 分配状态（原status字段）
) {
    // Record 自动包含构造函数、访问器和 equals/hashCode/toString 方法
}
