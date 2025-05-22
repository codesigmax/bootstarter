package com.qfleaf.bootstarter.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qfleaf.bootstarter.model.Role;
import com.qfleaf.bootstarter.model.response.admin.role.RolePageResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    @Select("""
            select t1.*
            from roles t1
            left join user_role_relations t2
            on t1.id = t2.role_id
            where t2.user_id = #{userId}
            """)
    List<Role> selectRolesByUserId(@Param("userId") Long userId);

    @Select("""
            select *
            from roles
            ${ew.customSqlSegment}
            """)
    IPage<RolePageResponse> selectPageVo(IPage<RolePageResponse> page, @Param("ew") LambdaQueryWrapper<Role> wrapper);
}
