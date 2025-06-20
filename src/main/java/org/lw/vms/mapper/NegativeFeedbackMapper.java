package org.lw.vms.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.lw.vms.DTOs.NegativeFeedbackDto;

import java.util.List;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
public interface NegativeFeedbackMapper {

    /**
     * 筛选负面反馈工单及其涉及的维修人员的核心信息 (评分 < 3)
     * 使用 @Select 注解直接嵌入 SQL
     */
    @Select({
            "SELECT",
            "    ro.order_id,",
            "    ro.description AS order_description,", // 为列名设置别名以便映射到 DTO 属性
            "    m.mechanic_id,",
            "    u.name AS mechanic_name",             // 为列名设置别名以便映射到 DTO 属性
            "FROM",
            "    feedback AS f",
            "JOIN",
            "    repair_order AS ro ON f.order_id = ro.order_id",
            "JOIN",
            "    order_assignment AS oa ON ro.order_id = oa.order_id",
            "JOIN",
            "    mechanic AS m ON oa.mechanic_id = m.mechanic_id",
            "JOIN",
            "    user AS u ON m.user_id = u.user_id",
            "WHERE",
            "    f.rating < 3",
            "ORDER BY",
            "    f.feedback_time DESC" // 可以保留排序，或者根据需求移除
    })
    @Results({ // 手动定义结果集映射，因为列名和属性名不完全一致 (使用了别名)
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "orderDescription", column = "order_description"),
            @Result(property = "mechanicId", column = "mechanic_id"),
            @Result(property = "mechanicName", column = "mechanic_name")
    })
    List<NegativeFeedbackDto> getNegativeFeedbackOrdersAndStaff();
}
