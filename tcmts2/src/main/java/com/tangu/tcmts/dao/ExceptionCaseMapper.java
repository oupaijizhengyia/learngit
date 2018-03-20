package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tangu.tcmts.po.ExceptionCase;

@Mapper
public interface ExceptionCaseMapper {
  // 查询异常列表
  List<ExceptionCase> listExceptionCases(ExceptionCase exceptionCase);
  
  // 新增异常
  int insertExceptionCase(ExceptionCase exceptionCase);
  
  // 更新异常
  int updateDealExceptionCase(ExceptionCase exceptionCase);

  // 根据Id 查找异常记录
  ExceptionCase getExceptionCaseById(@Param("id")Integer id);
  
  //app端新增异常
  ExceptionCase saveExceptionCaseByApp(ExceptionCase exceptionCase);
}
