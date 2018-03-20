package com.tangu.tcmts.service;

import com.tangu.tcmts.po.Employee;
import com.tangu.tcmts.po.PublicDO;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Employee getEmployeeContent(Integer id);

    Employee login(String username, String password);

    Integer getRepeatEmployee(Employee employee);

    List<Employee> listEmployeeContent(List<Employee> employees);

    List<Employee> listEmployees(Employee employee);

    List<PublicDO> getEmployeeName();

    int insertEmployee(Employee employee);

    int updateEmployee(Employee employee);

    int updateState(Employee employee);

    int updateToDefalutPassword(Employee employee);

    List<Employee> findEmployee(Employee employee);

    int updatePassword(Employee employee);
    
    Integer getEmployeeId(String code);

    String getEmployeeNameByCode(String code);

    Map findAllPrivilegeListByRole(Employee employee);

}
