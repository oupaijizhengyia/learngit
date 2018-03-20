package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.RecipeDTO;
import com.tangu.tcmts.po.RecipeScan;

/**
 * @author:huangyifan
 * @Description:操作流程Mapper
 * @Date: create in 15：58 2017/11/18
 */
@Mapper
public interface RecipeScanMapper {
	List<RecipeScan> listRecipeScan(RecipeDTO recipeDTO);
	List<RecipeScan> findRecipeHistory(RecipeDO scan);
	List<RecipeScan> listStartVolume(RecipeDO scan);
	List<RecipeScan> findRecipeScanXj(RecipeDO scan);
	Integer updateRecipeScan(RecipeScan scan);
	Integer insertCheckState(List<RecipeDTO> list);
	Integer insertRecipeScan(List<RecipeScan> list);
	List<RecipeScan> countEmployeeAchievement(RecipeScan recipeScan);
}