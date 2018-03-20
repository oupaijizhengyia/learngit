package com.tangu.tcmts.service;

import com.tangu.tcmts.po.Module;
import com.tangu.tcmts.po.SysRole;

import java.util.Map;
import java.util.TreeSet;

public interface ModuleService {
    TreeSet<Module> selectMenuModules();

    Integer updatePermission(SysRole sysRole);

    Map<String,Object> selectRolePermission(Integer roleid);
}
