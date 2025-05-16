package com.qfleaf.bootstarter.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfleaf.bootstarter.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    @Insert("insert into user_role_relations(user_id, role_id) values (#{userId}, #{roleId})")
    int insertDefaultRoles(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
