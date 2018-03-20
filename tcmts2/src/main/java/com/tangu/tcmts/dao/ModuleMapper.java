package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.Employee;
import com.tangu.tcmts.po.Module;
import com.tangu.tcmts.po.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ModuleMapper {
    List<Module> getFunctionByRole(Map<String, Object> params);

    List<Module> getModuleList(Module module);

    @Select("select function_id from sys_role_function where role_id = #{roleid}")
    List<Object> getFunctionById(Integer id);

    @Select("select module_id from sys_role_module where role_id = #{roleid}")
    List<Object> getModuleById(Integer id);

    @Select("select employee_name from employee where role_id = #{roleid}")
    List<Employee> usingRole(Integer id);

    @Delete("delete from sys_role_module where role_id = #{roleid}")
    int deleteSysRoleModule(Integer id);

    @Delete("delete from sys_role_function where role_id = #{roleid}")
    int deleteSysRoleFunction(Integer id);

    int insertSysRoleModule(SysRole sysRole);

    int insertSysRoleFuntion(SysRole sysRole);
}
