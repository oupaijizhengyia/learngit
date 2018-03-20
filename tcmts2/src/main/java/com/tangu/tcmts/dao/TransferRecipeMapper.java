package com.tangu.tcmts.dao;

import java.util.List;
import java.util.Set;

import com.tangu.tcmts.po.ReceiveRecipeListVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.TransferRecipeDO;
import com.tangu.tcmts.po.TransferRecipeDTO;

@Mapper
public interface TransferRecipeMapper {

    TransferRecipeDO getTransferRecipeContent(Integer id);

    List<TransferRecipeDO> listTransferRecipes(TransferRecipeDTO transferRecipeDTO);

    Integer updateTransferRecipePrintState(List<Integer> list);

    Integer updateUseState(TransferRecipeDO recipeDO);
    
    RecipeDO checkTransferRecipe(Integer id);
    
    List<RecipeDO> listReceiveTransferRecipe(List billIdList);
    List<RecipeDO> listReceiveTransferRecipes(List billIdList);
    
    Integer updateUseStateByIds(List list);
    
    Integer insertTranferRecipe(List<TransferRecipeDO> list);
    
    List<TransferRecipeDO> listTransferRecipe(TransferRecipeDTO transferRecipeDTO);
    
    Integer updateMedicineMatch(List<TransferRecipeDTO> list);
    List<TransferRecipeDO> listTransferRecipeByInvoiceCode(String invoiceCode);
    
    @Insert("replace into transfer_recipe_tmp(transfer_recipe_id,carry_id,take_time,receive_remark,logistics_code)"
    		+" values(#{id},#{carryId},#{takeTime},#{receiveRemark},#{logisticsCode}) ")
    Integer replaceReceiveInfo(TransferRecipeDO tr);
    
    Integer updateByPrimaryKeySelective(TransferRecipeDO transferRecipeDO);

    //接方列表查询
    List<TransferRecipeDO> listReciveRecipeList(TransferRecipeDTO record);

    List<ReceiveRecipeListVO> listReceiveRecipeList(TransferRecipeDTO transferRecipeDTO);
    
    Integer insertTransferRecipeTmp(List<TransferRecipeDTO> list);
    
    Integer updateMedicineMatchById(List<TransferRecipeDTO> list);

    List<RecipeDO> listByRecipeCode(@Param("set")Set<String> set);
    
}