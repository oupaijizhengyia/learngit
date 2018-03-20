package com.tangu.tcmts.dao;

import java.util.List;
import java.util.Map;

import com.tangu.tcmts.po.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RecipeMapper {
	int updateByPrimaryKeySelective(RecipeDO recipeDO);

    int updateByConfirmationExamine(RecipeDO recipeDO);
	
    List<RecipeDO> listRecipes(RecipeDTO recipeDTO);

    List<RecipeDO> listCheckRecipes(RecipeDTO recipeDTO);

    Integer updateRecipePrintState(List<Integer> list);

    CountRecipes getRecipeNumbers(RecipeDTO recipeDTO);

    @Select("SELECT state_id AS `value`,state_name AS label FROM sys_shipping_state ORDER BY `value`")
    List<PublicDO> getShippingState();
    
    @Select("SELECT id FROM recipe where sys_recipe_code = #{recipeCode} ")
    Integer getRecipeIdbyRecipeCode(ExceptionCase exceptionCase);

    RecipeDO getRecipeContent(Integer id);

    Integer updateShippingStateToReject(RecipeDTO recipeDTO);

    Integer updateShippingState(List<RecipeDTO> list);

    Integer updateCheckState(List<RecipeDTO> list);

    Integer updateRecipeCheckProccess(RecipeProccess recipeProccess);
    
    Integer insertSelective(RecipeDO recipeDO);
    
    Integer insertProccess(List list);
    
    Integer updateRecipe(RecipeDO recipeDO);
    Integer updateHisRecipe(RecipeDO recipeDO);
    Integer generateBillIndex(Map<String, Object> map);
    //List<PackingType> listPackingType();
    List<BtRelationZd> listBtRelationZdByHospitalId(Integer hospitalId);
    List<RecipeDO> checkRecipeStatus(RecipeDO recipe);
    Integer updateRecipeStatus(RecipeDO recipe);
    Integer updateProccess(RecipeDO recipe);
    Integer insertRecipeScan(RecipeScan scan);
    List<RecipeDO> selectRecipeByCode(RecipeDO recipe);
    String generateBillIdIndex(String prefix);
    RecipeDO insertRecipePackingPrint(RecipeDO recipeDO);
    List<RecipeDO> findRecipeScanList(RecipeDO recipeDO);
    List<RecipeDO> getZnpgRecipeList();
    Integer addCheckScan(RecipeDO recipeDO);
    List<MedicineDO> getRecipeMedicineForXj(RecipeDO recipe);
    RecipeDO getCheckState(RecipeDO recipe);
    
    RecipeDO  getNotAuthRecipe(Employee employee);

    RecipeDO getExamineRecipe(Employee employee);

    @Update("UPDATE recipe r SET r.dispense_id = -#{dispenseId} WHERE r.id = #{id}")
    Integer lockingRecipe(RecipeDO recipeDO);

    @Update("UPDATE recipe r SET r.dispense_id = -999999 WHERE r.id = #{id}")
    Integer lockingRecipeZero(RecipeDO recipeDO);

    @Update("UPDATE recipe r SET r.dispense_id = -#{dispenseId} WHERE r.id = #{id}")
    Integer unlockRecipe(RecipeDO recipeDO);

    @Update("UPDATE recipe r SET r.dispense_id = 0 WHERE r.id = #{id}")
    Integer unlockRecipeZero(RecipeDO recipeDO);

    List<RecipeDO> listRecipeState(List<RecipeDTO> list);
    @Select("SELECT id,dispense_id as dispenseId  FROM recipe  WHERE shipping_state=1 and id = #{value}")
    RecipeDO  getDispenseIdById(Integer id);

    RecipeVO getRecipeVO(RecipeDTO recipeDTO);

    RecipeSerialVO getRecipeSerial(RecipeDTO recipeDTO);
    
    RecipeSerialVO getRecipeSerialVO(RecipeDTO recipeDTO);

    DispensationReciveRecipeVO getDispensationReciveRecipeVO(Employee employee);

    
    List<DispensationReciveRecipeVO> getDispensationRecipesByUnionIdAndDispenseId(RecipeDO recipeDO);

    List<DispensationReciveRecipeVO> getDispensationRecipesByUnionId(RecipeDO recipeDO);

    Integer updateDispenseIdByUnion(List<DispensationReciveRecipeVO> dispensationReciveRecipeVOList);

    RecipeDO getRecipeIfUrgent();

    RecipeDO getRecipeIfNotUrgent();

    Map<Integer,String> getDispenseName(List<RecipeDO> recipeDOList);

    Integer insertRecipeListMapper(List<RecipeDO> l);

    List<String> getRecipeCodeByUnion(RecipeDO recipeDO);
    
}