package com.qfleaf.bootstarter.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfleaf.bootstarter.model.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    @Select("""
            select t1.*
            from permissions t1
            left join role_permission_relations t2
            on t1.id = t2.permission_id
            where t2.role_id = #{roleId}
            """)
    List<Permission> selectPermissionsByRoleId(@Param("roleId") Long roleId);

    @Select("""
            select t1.*
            from permissions t1
            left join role_permission_relations t2
            on t1.id = t2.permission_id
            where t2.role_id = any(array[#{roleIdList}]::bigint[])
            """)
    List<Permission> selectPermissionsByRoleIds(@Param("roleIdList") Long[] roleIds);

}
