package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.RecipeProccess;

/**
 * @author:huangyifan
 * @Description:处方修改记录mapper
 * @Date: create in 14：20 2017/11/18
 */
@Mapper
public interface RecipeProccessMapper {
	
	@Select("update recipe_proccess set start_concentrate_id = #{scanUser},start_concentrate_time = #{startBoilTime} where recipe_id = #{id}")
	Integer updateProccessConcentrate(RecipeDO recipe);
	@Select("update recipe_proccess set end_concentrate_id = #{scanUser},end_concentrate_time = #{startBoilTime} where recipe_id = #{id}")
	Integer updateProccessEndConcentrate(RecipeDO recipe);
	@Update("update recipe_proccess set delivery_id = #{deliveryId} , delivery_time = NOW() where recipe_id = #{recipeId}")
	Integer updateProccessDeliveryTime(RecipeProccess recipeProccess);
	Integer updatesProccessDeliveryTime(List<RecipeProccess> list);
}