package com.tangu.tcmts.service.imp;

import com.tangu.tcmts.dao.EmployeeMapper;
import com.tangu.tcmts.po.Employee;
import com.tangu.tcmts.po.PublicDO;
import com.tangu.tcmts.service.EmployeeService;
import com.tangu.tcmts.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee getEmployeeContent(Integer id) {
        return employeeMapper.getEmployeeContent(id);
    }

    @Override
    public Employee login(String username, String password) {
        Employee employee = new Employee();
        employee.setEmployeeName(username);
        employee.setEmployeePassword(CommonUtil.DoubleMD5(password));
        return employeeMapper.login(employee);
    }

    @Override
    public Integer getRepeatEmployee(Employee employee) {
        return employeeMapper.getRepeatEmployee(employee);
    }

    @Override
    public List<Employee> listEmployeeContent(List<Employee> employees) {
        return employeeMapper.listEmployeeContent(employees);
    }

    @Override
    public List<Employee> listEmployees(Employee employee) {
        return employeeMapper.listEmployees(employee);
    }

    @Override
    public List<PublicDO> getEmployeeName() {
        return employeeMapper.getEmployeeName();
    }

    @Override
    public int insertEmployee(Employee employee) {
        return employeeMapper.insertEmployee(employee);
    }

    @Override
    public int updateEmployee(Employee employee) {
        return employeeMapper.updateEmployee(employee);
    }

    @Override
    public int updateState(Employee employee) {
        return employeeMapper.updateUseState(employee);
    }

    @Override
    public int updateToDefalutPassword(Employee employee) {
        return employeeMapper.updateToDefalutPassword(employee);
    }

    @Override
    public List<Employee> findEmployee(Employee employee) {
        return employeeMapper.findEmployee(employee);
    }
    @Override
    public int updatePassword(Employee employee) {
        employee.setNewEmployeePassword(CommonUtil.DoubleMD5(employee.getNewEmployeePassword()));
        employee.setEmployeePassword(CommonUtil.DoubleMD5(employee.getEmployeePassword()));
        return employeeMapper.updatePassword(employee);
    }

	@Override
	public Map<String, Object> findAllPrivilegeListByRole(Employee employee) {
		List<Employee> list=employeeMapper.findAllPrivilegeListByRole(employee);
//		list.stream().
		
		Map<String,Object> configMap =list.stream()
				.collect(Collectors.toMap(Employee::getFunctionCode,Employee::getFunctionName));
		return configMap;
	}

	@Override
	public Integer getEmployeeId(String code) {
		return employeeMapper.getEmployeeId(code);
	}

    @Override
    public String getEmployeeNameByCode(String code) {
        return employeeMapper.getEmployeeNameByCode(code);
    }
}
