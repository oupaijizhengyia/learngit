package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.RecipeFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecipeFileMapper {

    int deleteRecipeFile(Integer id);

    List<RecipeFile> listRecipeFile(List<String> recipeIds);

    //TODO
    int insertRecipeFile(RecipeFile record);
    
    int addRecipeFile(List<RecipeFile> recipeFileList);

    int deleteByIds(List<RecipeFile> record);
}
