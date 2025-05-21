package com.qfleaf.bootstarter.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qfleaf.bootstarter.model.User;
import com.qfleaf.bootstarter.model.response.admin.user.UserPageResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
    @Insert("insert into user_role_relations(user_id, role_id) values (#{userId}, #{roleId})")
    int insertDefaultRoles(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Select("""
            select id, username, phone, email, create_time, update_time, status
            from sys_users
            ${ew.customSqlSegment}
            """)
    IPage<UserPageResponse> selectPageVo(IPage<UserPageResponse> page, @Param("ew") LambdaQueryWrapper<User> wrapper);
}
