package org.lw.vms.mapper;

import org.apache.ibatis.annotations.*;
import org.lw.vms.entity.Feedback;

import java.util.List;

/**
 * 反馈数据访问接口 (Mybatis Mapper)。
 */
@Mapper
public interface FeedbackMapper {

    /**
     * 根据 ID 查询反馈记录。
     * @param feedbackId 反馈 ID
     * @return 反馈对象
     */
    @Select("SELECT feedback_id, order_id, user_id, rating, comment, feedback_time FROM feedback WHERE feedback_id = #{feedbackId}")
    Feedback findById(Integer feedbackId);

    /**
     * 根据工单 ID 查询反馈记录列表。
     * @param orderId 工单 ID
     * @return 反馈列表
     */
    @Select("SELECT feedback_id, order_id, user_id, rating, comment, feedback_time FROM feedback WHERE order_id = #{orderId}")
    List<Feedback> findByOrderId(Integer orderId);

    /**
     * 根据用户 ID 查询反馈记录列表。
     * @param userId 用户 ID
     * @return 反馈列表
     */
    @Select("SELECT feedback_id, order_id, user_id, rating, comment, feedback_time FROM feedback WHERE user_id = #{userId}")
    List<Feedback> findByUserId(Integer userId);

    /**
     * 插入新的反馈记录。
     * @param feedback 反馈对象
     * @return 影响的行数
     */
    @Insert("INSERT INTO feedback(order_id, user_id, rating, comment, feedback_time) " +
            "VALUES(#{orderId}, #{userId}, #{rating}, #{comment}, #{feedbackTime})")
    @Options(useGeneratedKeys = true, keyProperty = "feedbackId")
    int insertFeedback(Feedback feedback);

    /**
     * 更新反馈记录。
     * @param feedback 反馈对象
     * @return 影响的行数
     */
    @Update("UPDATE feedback SET order_id = #{orderId}, user_id = #{userId}, rating = #{rating}, " +
            "comment = #{comment}, feedback_time = #{feedbackTime} WHERE feedback_id = #{feedbackId}")
    int updateFeedback(Feedback feedback);

    /**
     * 删除反馈记录。
     * @param feedbackId 反馈 ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM feedback WHERE feedback_id = #{feedbackId}")
    int deleteFeedback(Integer feedbackId);


}