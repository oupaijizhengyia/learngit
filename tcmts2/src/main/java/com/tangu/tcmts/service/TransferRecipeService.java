package com.tangu.tcmts.service;

import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.RecipeMedicine;
import com.tangu.tcmts.po.TransferMedicine;
import com.tangu.tcmts.po.TransferRecipeDO;
import com.tangu.tcmts.po.TransferRecipeDTO;

import java.util.List;

public interface TransferRecipeService {
    TransferRecipeDO getTransferRecipeContent(Integer id);


    List<TransferRecipeDO> listTransferRecipes(TransferRecipeDTO transferRecipeDTO);

    List<TransferMedicine> getMessageByTransferRecipe(TransferMedicine transferMedicine);

    Integer updateUseState(TransferRecipeDO recipeDO);

    Integer updateTransferRecipePrintState(List<Integer> list);

    RecipeDO checkTransferRecipe(Integer id);
    
    List<RecipeMedicine> findMedicineListByBillIds(List billIdlist);
    
    List<RecipeDO> listReceiveTransferRecipe(List billIdList);
    
    Integer insertTransferRecipe(List<TransferRecipeDO> list);

    List<TransferRecipeDO> listTransferRecipe(TransferRecipeDTO transferRecipeDTO);
    
    List<RecipeMedicine> listTransferMedicineById(List<TransferRecipeDTO> transferRecipeDTO);
    boolean updateMedicineMatch(List<TransferRecipeDTO> transferRecipeDTO);
    List<TransferRecipeDO> listTransferRecipeByInvoiceCode(String invoiceCode);
    List<RecipeMedicine> listTrMedicineToRpMedicine(TransferMedicine record);
    void replaceReceiveInfo(TransferRecipeDO transferRecipeDO);
    Integer updateByPrimaryKeySelective(TransferRecipeDO transferRecipeDO);
    
    List<RecipeDO> listReceiveTransferRecipes(List billIdList);

    Object listReciveRecipeList(TransferRecipeDTO record);
    
    Integer insertTransferRecipeTmp(List<TransferRecipeDTO> list);
    
    Integer updateMedicineMatchById(List<TransferRecipeDTO> list);
}
