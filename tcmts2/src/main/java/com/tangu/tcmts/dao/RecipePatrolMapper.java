package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.RecipePatrol;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecipePatrolMapper {
    List<RecipePatrol> listRecipePatrols(RecipePatrol recipePatrol);

    Integer updatePatrolStatus(Integer id);
    
    Integer addRecipePatrol(RecipePatrol recipePatrol);
}