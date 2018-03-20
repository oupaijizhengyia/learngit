package com.tangu.tcmts.service;

import java.util.List;

import com.tangu.tcmts.po.RecipeFile;

public interface RecipeFileService {
  
  boolean insertFiles(List<RecipeFile> record);
  
  boolean deleteFiles(List<RecipeFile> record);
  
  boolean insertFile(RecipeFile record);
  
  boolean deleteFile(RecipeFile record);
  
  int deleteRecipeFile(Integer id);

  List<RecipeFile> listRecipeFile(List<String> recipeIds);

  int addRecipeFile(List<RecipeFile> recipeFileList);

}
