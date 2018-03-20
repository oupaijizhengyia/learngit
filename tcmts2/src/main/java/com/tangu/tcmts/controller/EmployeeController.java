package com.tangu.tcmts.controller;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.po.PrintPage;
import com.tangu.tcmts.po.PrintPageObj;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.PrintUtil;
import com.tangu.tcmts.po.Employee;
import com.tangu.tcmts.service.EmployeeService;
import com.tangu.tcmts.service.PrintService;
import com.tangu.tcmts.util.ChineseCharacterUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:yinghuaxu
 * @Description:员工管理
 * @Date: create in 11:35 2017/10/26
 */
@Api(value = "employee", description = "员工管理")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PrintService printService;

    @ApiOperation(tags = "employee", value = "查找员工", response = Employee.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employee", value = "查找员工", dataType = "Employee")
    })
    @RequestMapping(value = "/findEmployee", method = RequestMethod.POST)
    public Object findEmployee(@RequestBody Employee employee) {
        return new ResponseModel(employeeService.listEmployees(employee));
    }

    @ApiOperation(tags = "employee", value = "员工详情", response = Employee.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employee", value = "员工详情", dataType = "Employee")
    })
    @RequestMapping(value = "/getEmployeeContent", method = RequestMethod.POST)
    public Object getEmployeeContent(@RequestBody Employee employee) {
        return new ResponseModel(employeeService.getEmployeeContent(employee.getId()));
    }


    @ApiOperation(tags = "employee", value = "新建并保存员工", notes = "返回参数:errorMsg:操作失败;feedbackMsg:操作成功"
            , response = Employee.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employee", value = "新建并保存员工", dataType = "Employee")
    })
    @RequestMapping(value = "/createAndSaveEmployee", method = RequestMethod.POST)
    public Object createAndSaveEmployee(@RequestBody Employee employee) {
        Integer result = 0;
        employee.setInitialCode(ChineseCharacterUtil.convertHanzi2Pinyin(employee.getEmployeeName()));
        if (employeeService.getRepeatEmployee(employee) > 0) {
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.USER_NAME_EXIST);
        }
        try {
            if (employee.getId() != null) {
                employee.setModTime(new Date(System.currentTimeMillis()));
                result = employeeService.updateEmployee(employee);
            } else {
                result = employeeService.insertEmployee(employee);
            }
        } catch (Exception e) {
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.USER_CODE_EXIST);
        }
        Employee employeeId = new Employee();
        employeeId.setId(employee.getId());
        if (result > Constants.STATE_ZERO) {
            return new ResponseModel(employeeService.getEmployeeContent(employeeId.getId()))
                    .attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(tags = "employee", value = "启用/停用员工", notes = "返回参数:errorMsg:操作失败;feedbackMsg:操作成功"
            , response = Employee.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employee", value = "启用/停用员工", dataType = "Employee")
    })
    @RequestMapping(value = "/changeEmployeeState", method = RequestMethod.POST)
    public Object changeEmployeeState(@RequestBody Employee employee) {
        employee.setModTime(new Date(System.currentTimeMillis()));
        Integer result = employeeService.updateState(employee);
        if (result > Constants.STATE_ZERO) {
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(tags = "employee",value = "修改密码",notes = "返回参数:errorMsg:操作失败;feedbackMsg:操作成功")
    @ApiImplicitParam(name = "employee",value = "修改密码",dataType = "Employee")
    @RequestMapping(value = "changePassword",method = RequestMethod.POST)
    public Object changePassword(@RequestBody Employee employee){
        employee.setModTime(new Date(System.currentTimeMillis()));
        employee.setId(JwtUserTool.getId());
        Integer result = employeeService.updatePassword(employee);
        if (result > Constants.STATE_ZERO) {
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(tags = "employee", value = "重置密码", response = Employee.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employee", value = "重置密码", dataType = "Employee")
    })
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public Object resetPassword(@RequestBody Employee employee) {
        employee.setModTime(new Date(System.currentTimeMillis()));
        Integer result = employeeService.updateToDefalutPassword(employee);
        if (result > Constants.STATE_ZERO) {
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(tags = "employee", value = "批量打印条码", response = Employee.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employee", value = "批量打印条码", dataType = "Employee")
    })
    @RequestMapping(value = "printCode/{printType}", method = RequestMethod.POST)
    public Object printCode(@RequestBody List<Employee> listEmployee, @PathVariable String printType) {
        PrintPage printPage = printService.getPrintPageByBillType(printType);
        List<PrintPageObj> pritPageList = new ArrayList<PrintPageObj>();
        List<Employee> listEmployeeContent = employeeService.listEmployeeContent(listEmployee);
        if (printPage != null) {
            for (Employee employee : listEmployeeContent) {
                pritPageList.add(PrintUtil.getPrintableCanvasObj(printPage, employee));
            }
            return pritPageList;
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(tags = "employee", value = "获取员工列表")
    @RequestMapping(value = "getEmployeeName", method = RequestMethod.GET)
    public Object getEmployeeName() {
        return new ResponseModel(employeeService.getEmployeeName());
    }
}
