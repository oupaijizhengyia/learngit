package com.tangu.tcmts.service;

import com.tangu.tcmts.po.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface RecipeService {
	int updateByPrimaryKeySelective(RecipeDO recipeDO);

    int updateByConfirmationExamine(RecipeDO recipeDO);
	
    List<Map<String, String>> queryLogistics(RecipeDTO recipeDTO) throws Exception;

    List<RecipeDO> listRecipes(RecipeDTO recipeDTO);

    List<RecipeDO> listCheckRecipes(RecipeDTO recipeDTO);

    List<PublicDO> getShippingState();

    RecipeDO getRecipeContent(Integer id);

    CountRecipes getRecipeNumbers(RecipeDTO recipeDTO);

    List<RecipeMedicine> getRecipeMedicineByRecipe(Integer id);

    List<RecipePatrol> listRecipePatrols(RecipePatrol recipePatrol);

    Integer updatePatrolStatus(Integer id);

    Integer updateShippingStateToReject(RecipeDTO recipeDTO);

    Integer updateCheckState(List<RecipeDTO> list);

    void export(Object headList , List<?> list, HttpServletRequest req, HttpServletResponse resp, String xml) throws UnsupportedEncodingException, ServletException, IOException;


    Integer updateRecipePrintState(List<Integer> list);

    Integer updateShippingState(List<RecipeDTO> list);
    Integer updateRecipeCheckProccess(RecipeProccess recipeProccess);
    Integer insertRecipe(RecipeDO recipeDO);
    Object listOperationRecord(RecipeDTO recipeDTO);
    
    List<TransferRecipeDO> batchInsertRecipe(List<RecipeDO> recipeDO);
    Integer addRecipe(RecipeDO recipeDO) throws Exception;
    Integer updateRecipe(RecipeDO recipeDO, String hospitalType);
  /*  List<PackingType> listPackingType();*/
    List<BtRelationZd> listBtRelationZdByHospitalId(Integer hospitalId);
    RecipeDO checkRecipeStatus(RecipeDO recipeDO);
    Integer updateRecipeStatus(RecipeDO recipe) throws Exception;
    List<RecipeDO> selectRecipeByCode(RecipeDO recipeDO);
    String generateBillIdIndex();
    RecipeDO insertRecipePackingPrint(RecipeDO recipeDO);
    List<RecipeScan> findRecipeHistory(RecipeDO scan);
    List<RecipeDO> findRecipeScanList(RecipeDO recipeDO);
    Integer addRecipePatrol(RecipePatrol recipePatrol);
    RecipeDO getZnpgList(Integer eid);
    Integer addCheckScan(RecipeDO recipeDO);
    List<RecipeScan> listStartVolume(RecipeDO scan);
    Integer updateConcentrateStatus(RecipeDO recipeDO);
    RecipeDO getRecipeAndXjMedicine(RecipeDO recipeDO);
    RecipeDO getRecipeAndHxMedicine(RecipeDO recipeDO);
    RecipeDO saveRecipeAndXjMedicine(RecipeDO recipeDO);
    RecipeDO saveRecipeAndhxMedicine(RecipeDO recipeDO);
    Integer updateRecipeScan(RecipeScan scan);
    RecipeDO getCheckState(RecipeDO recipe);
    List<RecipeMedicine> findRecipeMedicineBySpecialBoilType(RecipeMedicine recipeMedicine);
    RecipeDO  dispatchRecipe(Employee employee);

//    RecipeDO getExamineRecipe(Employee employee);
    
    Integer updateProccessDeliveryTime(RecipeProccess recipeProccess);
    
    Integer updatesProccessDeliveryTime(List<RecipeProccess> list);
    
    Integer insertRecipeScan(List<RecipeScan> list);
    
    Integer updateRecipeMedicines(List<RecipeMedicine> list);
    
    List<RecipeScan> countEmployeeAchievement(RecipeScan recipeScan);

    // 锁定 与 解锁
    Object lockingRecipe(Integer id);


    List<RecipeDO> listRecipeState(List<RecipeDTO> list);

    RecipeVO getRecipeVO(RecipeDTO recipeDTO);

    RecipeSerialVO getRecipeSerialVO(RecipeDTO recipeDTO);
    
    Integer updateProccess(RecipeDO recipe);

    List<Report> listReportMedicineByMedicine(ReportDTO reportDTO);

    List<Report> listReportMedicineBySettle(ReportDTO reportDTO);

    List<Report> listReportMedicineByHospital(ReportDTO reportDTO);

    DispensationReciveRecipeVO getDispensationReciveRecipeVO(Employee employee);

    List<DispensationReciveRecipeVO> getDispensationRecipesByUnionIdAndDispenseId(RecipeDO recipeDO);

    List<DispensationReciveRecipeVO> getDispensationRecipesByUnionId(RecipeDO recipeDO);

    Integer updateDispenseIdByUnion(List<DispensationReciveRecipeVO> dispensationReciveRecipeVOList);













}
