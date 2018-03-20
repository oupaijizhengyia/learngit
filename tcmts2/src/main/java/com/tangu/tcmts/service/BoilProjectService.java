package com.tangu.tcmts.service;

import java.util.List;

import com.tangu.tcmts.po.BoilProject;

public interface BoilProjectService {

  //新增,修改煎煮方案
  Object saveBoilProject(BoilProject medicineShelves);

  Object deleteBoilProject(Integer id);

  Object listBoilProject(BoilProject boilProject);

  Object getBoilProjectById(BoilProject boilProject);
  List<BoilProject> listBoilProjectName();
}
