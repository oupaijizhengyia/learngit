package com.tangu.tcmts.service;

import java.util.List;

import com.tangu.tcmts.po.ExceptionCase;

public interface ExceptionCaseService {

  //根据条件获取列表
  List<ExceptionCase> listExceptionCases(ExceptionCase exceptionCase);
  
  // 新增异常
  Object insertExceptionCase(ExceptionCase exceptionCase);
  
  // 处理异常
  Object updateDealExceptionCase(ExceptionCase exceptionCase);
  
  // 根据Id 查找异常记录
  ExceptionCase getExceptionCaseById(Integer id);
  
  //app端新增异常
  ExceptionCase saveExceptionCaseByApp(ExceptionCase exceptionCase);


}
