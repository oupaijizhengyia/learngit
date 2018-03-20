package com.tangu.tcmts.controller;

import java.util.Date;

import com.tangu.tcmts.dao.RecipeMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.po.ExceptionCase;
import com.tangu.tcmts.service.ExceptionCaseService;
import com.tangu.tcmts.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "exceptionCase", description = "异常管理")
@RestController
@RequestMapping("/exceptionCase")
public class ExceptionCaseController {
  @Autowired
  ExceptionCaseService exceptionCaseService;
  @Autowired
  RecipeMapper recipeMapper;

  @ApiOperation(tags = "exceptionCase", value = "查询异常列表", notes="startDate, endDate, exceptionType, dealStatus", response = ExceptionCase.class)
  @ApiImplicitParams({
          @ApiImplicitParam(name = "exceptionCase", value = "查找异常", dataType = "ExceptionCase")
  })
  @RequestMapping(value = "/findExceptionCases", method = RequestMethod.POST)
  public Object findExceptionCases(@RequestBody ExceptionCase exceptionCase) {
      return new ResponseModel(exceptionCaseService.listExceptionCases(exceptionCase));
  }
  
  @ApiOperation(tags = "exceptionCase",
      notes="recipeCode, exceptionType, dutyId, exceptionComment   插入成功 feedbackMsg data:response 插入失败 errMsg", 
      value = "新增异常", response = Object.class)
  @ApiImplicitParams({
          @ApiImplicitParam(name = "exceptionCase", value = "新增异常", dataType = "ExceptionCase")
  })
  @RequestMapping(value = "/insertExceptionCase", method = RequestMethod.POST)
  public Object insertExceptionCase(@RequestBody ExceptionCase exceptionCase) {
    exceptionCase.setCreatorId(JwtUserTool.getId());
    exceptionCase.setDealStatus(Constants.STATE_ZERO);
    return exceptionCaseService.insertExceptionCase(exceptionCase);
  }
  
  @ApiOperation(tags = "exceptionCase", 
      notes="id,recipeCode,exceptionType,dutyId,exceptionComment   ResponseModel feedback errMsg", 
      value = "修改异常详情", response = Object.class)
  @ApiImplicitParams({
          @ApiImplicitParam(name = "exceptionCase", value = "修改异常详情", dataType = "ExceptionCase")
  })
  @RequestMapping(value = "/updateExceptionCaseDetail", method = RequestMethod.POST)
  public Object updateExceptionCaseDetail(@RequestBody ExceptionCase exceptionCase) {
    if(StringUtils.isNotBlank(exceptionCase.getRecipeCode())){
      if (recipeMapper.getRecipeIdbyRecipeCode(exceptionCase) != null){
        exceptionCase.setRecipeId(recipeMapper.getRecipeIdbyRecipeCode(exceptionCase));
      }else {
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, "查无此单");
      }
    }else{
      exceptionCase.setRecipeId(null);
    }
    return exceptionCaseService.updateDealExceptionCase(exceptionCase);
  }
  
  @ApiOperation(tags = "exceptionCase", notes="id,dealResult  ResponseModel feedback errMsg", value = "处理异常", response = Object.class)
  @ApiImplicitParams({
          @ApiImplicitParam(name = "exceptionCase", value = "处理异常", dataType = "ExceptionCase")
  })
  @RequestMapping(value = "/updateDealExceptionCase", method = RequestMethod.POST)
  public Object updateDealExceptionCase(@RequestBody ExceptionCase exceptionCase) {
    exceptionCase.setDealUser(JwtUserTool.getId());
    exceptionCase.setDealTime(new Date());
    exceptionCase.setDealStatus(Constants.STATE_ONE);
    return exceptionCaseService.updateDealExceptionCase(exceptionCase);
  }
  
}

