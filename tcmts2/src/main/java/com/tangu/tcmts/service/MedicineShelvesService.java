package com.tangu.tcmts.service;

import java.util.List;

import com.tangu.tcmts.po.MedicineShelves;

public interface MedicineShelvesService {

  Object listMedicineShelves(MedicineShelves medicineShelves);

  Object saveMedicineShelve(MedicineShelves medicineShelves);

  Object deleteMedicineShelve(Integer integer);

  //货架标签
  List<MedicineShelves> listMedicineShelvesByIds(List<MedicineShelves> list);
  
  Integer seleteRecipeByShelvesCode(MedicineShelves medicineShelves);
  
}
