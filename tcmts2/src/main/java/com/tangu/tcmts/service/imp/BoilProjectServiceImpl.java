package com.tangu.tcmts.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.dao.BoilProjectMapper;
import com.tangu.tcmts.po.BoilProject;
import com.tangu.tcmts.service.BoilProjectService;
import com.tangu.tcmts.util.Constants;

@Service
public class BoilProjectServiceImpl implements BoilProjectService{
  
  @Autowired
  BoilProjectMapper boilProjectMapper;

  @Override
  public Object listBoilProject(BoilProject boilProject) {
    return new ResponseModel(boilProjectMapper.listBoilProject(boilProject));
  }
  
  @Override
  public Object getBoilProjectById(BoilProject boilProject) {
    List<BoilProject> list = boilProjectMapper.listBoilProject(boilProject);
    BoilProject newBoil = list == null || list.isEmpty() ? new BoilProject() :
      list.get(0);
    return new ResponseModel(newBoil);
  }

  @Override
  public Object saveBoilProject(BoilProject boilProject) {
    int i = 0;
    String errMsg = "煎煮方案名称不能重复";
    BoilProject boilName = new BoilProject();
    boilName.setProjectName(boilProject.getProjectName());
    List<BoilProject> lp = boilProjectMapper.listBoilProject(boilName);
    //名字不相同
    //名字相同  id相等
    if(lp == null || lp.isEmpty() || lp.get(0).getId().equals(boilProject.getId())){
      boilProject.setModUser(JwtUserTool.getId());
      errMsg = "保存失败";
      i = boilProject.getId() == null ? insertBoilProject(boilProject)
          : updateBoilProject(boilProject);
    }
    return Constants.operation(i, Constants.INSERT_SUCCESS, errMsg, boilProject);
  }

  @Override
  public Object deleteBoilProject(Integer id) {
    int i = boilProjectMapper.deleteByPrimaryKey(id);
    System.out.println(i);
    return Constants.operation(i, Constants.DELETE_SUCCESS, "删除失败");
  }
  
  public Integer insertBoilProject(BoilProject boilProject){
    return boilProjectMapper.insertSelective(boilProject);
  }
  
  public Integer updateBoilProject(BoilProject boilProject){
    return boilProjectMapper.updateByPrimaryKeySelective(boilProject);
  }

	@Override
	public List<BoilProject> listBoilProjectName() {
		return boilProjectMapper.listBoilProjectName();
	}

}
