package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tangu.tcmts.po.BoilProject;

@Mapper
public interface BoilProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(BoilProject record);

    int updateByPrimaryKeySelective(BoilProject record);
    
    List<BoilProject> listBoilProject(BoilProject record);
    
    @Select("SELECT  id AS `value` , project_name AS label FROM `boil_project` ")
    List<BoilProject> listBoilProjectName();
}