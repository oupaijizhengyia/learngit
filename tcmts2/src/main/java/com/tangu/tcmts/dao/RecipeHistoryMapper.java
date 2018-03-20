package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tangu.tcmts.po.RecipeDTO;
import com.tangu.tcmts.po.RecipeHistory;

/**
 * @author:huangyifan
 * @Description:处方修改记录mapper
 * @Date: create in 14：20 2017/11/18
 */
@Mapper
public interface RecipeHistoryMapper {
	List<RecipeHistory> listRecipeHistory(RecipeDTO recipeDTO);
}