package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.PublicDO;
import com.tangu.tcmts.po.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    List<SysRole> listRoles();

    int deleteSysRole(Integer id);

    int insertSysRole(SysRole record);

    int insertSysRoleFunction(SysRole sysRole);

    int insertSysRoleModule(SysRole sysRole);

    int updateSysRole(SysRole record);

    @Select("SELECT id AS value,rolename AS label FROM sys_role")
    List<PublicDO> getSysRoleName();

}