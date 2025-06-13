package org.lw.vms.service;

import org.lw.vms.entity.Mechanic;
import org.lw.vms.entity.RepairOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 维修人员业务逻辑服务接口。
 * 定义了维修人员相关的查询操作。
 */
public interface MechanicService {

    /**
     * 根据用户ID获取维修人员的详细信息 (含工种、时薪)。
     * @param userId 用户 ID
     * @return 维修人员对象
     */
    Mechanic getMechanicDetailsByUserId(Integer userId);

    /**
     * 根据维修人员ID查询其当前和历史维修工单。
     * @param mechanicId 维修人员 ID
     * @return 维修工单列表
     */
    List<RepairOrder> getMechanicRepairOrders(Integer mechanicId);

    /**
     * 根据维修人员ID查询其累计工时费收入。
     * @param mechanicId 维修人员 ID
     * @return 累计工时费收入
     */
    BigDecimal getMechanicTotalLaborCostIncome(Integer mechanicId);

    /**
     * 更新维修人员的工种和时薪。
     * @param mechanicId 维修人员 ID
     * @param specialty 新工种
     * @param hourlyRate 新时薪
     * @return 更新后的维修人员对象
     */
    Mechanic updateMechanicInfo(Integer mechanicId, String specialty, BigDecimal hourlyRate);

}
