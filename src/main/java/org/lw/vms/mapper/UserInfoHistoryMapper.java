package org.lw.vms.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.lw.vms.entity.UserInfoHistory;

import java.util.List;

@Mapper
public interface UserInfoHistoryMapper {
    /**
     * 插入用户信息变更历史记录
     *
     * @param history 历史记录对象
     * @return 影响的行数
     */
    @Insert("INSERT INTO user_info_history(user_id, old_name, old_contact, new_name, new_contact, update_time) " +
            "VALUES(#{userId}, #{oldName}, #{oldContact}, #{newName}, #{newContact}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "historyId", keyColumn = "history_id")
    int insertHistory(UserInfoHistory history);

    /**
     * 查询所有用户信息变更历史记录
     *
     * @return 历史记录列表（按时间倒序排列）
     */
    @Select("SELECT history_id, user_id, old_name, old_contact, new_name, new_contact, update_time " +
            "FROM user_info_history " +
            "ORDER BY update_time DESC")
    List<UserInfoHistory> selectAllHistory();
}
