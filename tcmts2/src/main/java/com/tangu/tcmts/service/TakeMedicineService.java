package com.tangu.tcmts.service;

import com.tangu.tcmts.po.RecipeSerial;

public interface TakeMedicineService {

  Object takeMedicineInfo(RecipeSerial takeMedicine);

  Object listTakeMedicine(RecipeSerial recipeSerial);
}
