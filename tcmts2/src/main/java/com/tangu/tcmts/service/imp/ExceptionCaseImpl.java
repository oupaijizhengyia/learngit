package com.tangu.tcmts.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.dao.ExceptionCaseMapper;
import com.tangu.tcmts.dao.RecipeMapper;
import com.tangu.tcmts.po.ExceptionCase;
import com.tangu.tcmts.service.ExceptionCaseService;

@Service
public class ExceptionCaseImpl implements ExceptionCaseService{

  @Autowired
  private ExceptionCaseMapper exceptionCaseMapper;
  
  @Autowired
  private RecipeMapper recipeMapper; 
  
  @Override
  public List<ExceptionCase> listExceptionCases(ExceptionCase exceptionCase) {
    return exceptionCaseMapper.listExceptionCases(exceptionCase);
  }

  @Override
  public Object insertExceptionCase(ExceptionCase exceptionCase) {
    Integer id = recipeMapper.getRecipeIdbyRecipeCode(exceptionCase);
    if(exceptionCase.getRecipeCode() != null && exceptionCase.getRecipeCode() != ""){
      if(id == null){
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, "查无此单");
      }
    }
    exceptionCase.setRecipeId(id);
    int i = exceptionCaseMapper.insertExceptionCase(exceptionCase); 
    if(i > 0){
      return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS)
          .attr(ResponseModel.KEY_DATA, exceptionCaseMapper.getExceptionCaseById(exceptionCase.getId()));
    }
    return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
  }

  @Override
  public Object updateDealExceptionCase(ExceptionCase exceptionCase) {
    int i = exceptionCaseMapper.updateDealExceptionCase(exceptionCase);
    if(i > 0){
      return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS).attr(ResponseModel.KEY_DATA, exceptionCaseMapper.getExceptionCaseById(exceptionCase.getId()));
    }
    return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
  }


  @Override
  public ExceptionCase getExceptionCaseById(Integer id) {
    return exceptionCaseMapper.getExceptionCaseById(id);
  }

@Override
public ExceptionCase saveExceptionCaseByApp(ExceptionCase exceptionCase) {
	// TODO Auto-generated method stub
	return exceptionCaseMapper.saveExceptionCaseByApp(exceptionCase);
}

  
}
