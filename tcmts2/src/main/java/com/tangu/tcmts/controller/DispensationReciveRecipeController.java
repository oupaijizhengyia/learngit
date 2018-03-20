package com.tangu.tcmts.controller;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.dao.RecipeMapper;
import com.tangu.tcmts.po.DispensationReciveRecipeVO;
import com.tangu.tcmts.po.Employee;
import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.service.DispensationReciveRecipeService;
import com.tangu.tcmts.service.EmployeeService;
import com.tangu.tcmts.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */

@Api(value = "dispensationReciveRecipe", description = "配药接方")
@RestController
@RequestMapping("/dispensationReciveRecipe")
public class DispensationReciveRecipeController {

    @Autowired
    private DispensationReciveRecipeService dispensationReciveRecipeService;

    @Autowired
    private EmployeeService employeeService;



    @ApiOperation(value = "根据工号获取分配的配方信息", notes = "传入参数员工工号：employeeCode", response = DispensationReciveRecipeVO.class)
    @RequestMapping(value = "/listDispensationReciveRecipe", method = RequestMethod.POST)
    public Object listDispensationReciveRecipe(@RequestBody Employee employee){
        if(StringUtils.isBlank(employee.getEmployeeCode())){
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, "请传入员工工号!");
        }
        Integer employeeId = employeeService.getEmployeeId(employee.getEmployeeCode());
        if(employeeId == null ||  employeeId < 1){
            return new ResponseModel().attr(ResponseModel.KEY_ERROR,"该工号不存在!");
        }
        if(!dispensationReciveRecipeService.checkEmployeeAuthrity(employee)){
            return new ResponseModel().attr(ResponseModel.KEY_ERROR,"权限不够!");
        }
        employee.setId(employeeId);
        employee.setEmployeeName(employeeService.getEmployeeNameByCode(employee.getEmployeeCode()));
        DispensationReciveRecipeVO dispensationReciveRecipeVO = dispensationReciveRecipeService.dispatchRecipe(employee);
        if(dispensationReciveRecipeVO == null){
            return new ResponseModel(ResponseModel.KEY_ERROR,"暂无处方可配药!");
        }

        return new ResponseModel(dispensationReciveRecipeVO);
    }

}
