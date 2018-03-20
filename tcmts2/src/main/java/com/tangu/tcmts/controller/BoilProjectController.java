package com.tangu.tcmts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.po.BoilProject;
import com.tangu.tcmts.service.BoilProjectService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/boilProject")
public class BoilProjectController {
  
  private final String tags = "boilProject: 煎煮方案";
  
  @Autowired
  BoilProjectService boilProjectService;
  
  @ApiOperation(tags = tags, value = "查询煎煮方案", notes="传入 projectName", response = BoilProject.class)
  @RequestMapping(value = "/listBoilProject", method = RequestMethod.POST)
  public Object listBoilProject(@RequestBody BoilProject boilProject) {
      return boilProjectService.listBoilProject(boilProject);
  }

  @ApiOperation(tags = tags, value = "根据id查询煎煮方案", notes="传入 projectName或者id", response = BoilProject.class)
  @RequestMapping(value = "/getBoilProjectById", method = RequestMethod.POST)
  public Object getBoilProjectById(@RequestBody BoilProject boilProject) {
      return boilProjectService.getBoilProjectById(boilProject);
  }
  
  @ApiOperation(tags = tags, 
      value = "新增/编辑煎煮方案", 
      notes="传入id, projectName, soakTime, firstBoilTime, secondBoilTime, volume",
      response = BoilProject.class)
  @RequestMapping(value = "/saveBoilProject", method = RequestMethod.POST)
  public Object saveBoilProject(@RequestBody BoilProject boilProject) {
      return boilProjectService.saveBoilProject(boilProject);
  }
  
  @ApiOperation(tags = tags, value = "删除煎煮方案", 
      notes="传入id",
      response = BoilProject.class)
  @RequestMapping(value = "/deleteBoilProject", method = RequestMethod.POST)
  public Object deleteBoilProject(@RequestBody BoilProject boilProject) {
    return boilProjectService.deleteBoilProject(boilProject.getId());
  }

	@ApiOperation(tags = tags, value = "煎煮方案名称下拉", response = BoilProject.class)
	@RequestMapping(value = "/listBoilProjectName", method = RequestMethod.GET)
	public Object listBoilProjectName() {
		return new ResponseModel(boilProjectService.listBoilProjectName());
	}
}
