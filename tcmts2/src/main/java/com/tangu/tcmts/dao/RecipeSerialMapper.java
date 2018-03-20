package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tangu.tcmts.po.RecipeSerial;

@Mapper
public interface RecipeSerialMapper {
    int deleteByPrimaryKey(String recipeSerial);

    int insertSelective(RecipeSerial record);

    RecipeSerial selectByPrimaryKey(RecipeSerial record);
    RecipeSerial selectByRecipeSerial(RecipeSerial record);

    int updateByPrimaryKeySelective(RecipeSerial record);

    List<RecipeSerial> listTakeMedicine(RecipeSerial recipeSerial);

}