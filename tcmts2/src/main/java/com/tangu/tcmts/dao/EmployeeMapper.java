package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.Employee;
import com.tangu.tcmts.po.PublicDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("select e.id,employee_name,employee_code,use_state,role_id,rolename, is_operator,employee_password as password from employee e,sys_role s where employee_name=#{employeeName} AND employee_password=#{employeePassword} AND e.role_id=s.id AND use_state != 1")
    Employee login(Employee employee);

    Employee getEmployeeContent(Integer id);

    Integer getRepeatEmployee(Employee employee);

    List<Employee> listEmployeeContent(List<Employee> employees);

    List<Employee> listEmployees(Employee employee);

    @Select("SELECT id AS value,employee_name AS label FROM employee Where use_state != 1 ")
    List<PublicDO> getEmployeeName();

    int insertEmployee(Employee record);

    int updateEmployee(Employee record);

    int updateUseState(Employee employee);

    int updateToDefalutPassword(Employee employee);
    
    List<Employee> findEmployee(Employee employee);

    int updatePassword(Employee employee);
    
    List<Employee> findAllPrivilegeListByRole(Employee employee);
    
    @Select("SELECT id FROM employee WHERE employee_code = #{value} ")
    Integer getEmployeeId(String code);

    @Select("SELECT employee_name FROM employee WHERE employee_code = #{value} ")
    String getEmployeeNameByCode(String code);
}