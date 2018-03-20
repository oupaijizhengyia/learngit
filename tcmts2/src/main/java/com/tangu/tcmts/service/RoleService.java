package com.tangu.tcmts.service;

import com.tangu.tcmts.po.Module;
import com.tangu.tcmts.po.PublicDO;
import com.tangu.tcmts.po.SysRole;

import java.util.List;
import java.util.TreeSet;

public interface RoleService {
    TreeSet<Module> selectMenuModulesByRoleid(String roleid);

    List<SysRole> listRoles();

    int updateSysRole(SysRole sysRole);

    int insertSysRole(SysRole sysRole);

    int insertSysRoleAuthority(SysRole sysRole);

    int deleteSysRole(Integer id);

    List<PublicDO> getSysRoleName();
}
