package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.RecipePrintObj;
import com.tangu.tcmts.po.RecipeReq;
import com.tangu.tcmts.po.TransferMedicineObj;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PrintMapper {
    List<RecipePrintObj> listTransferRecipePrintMessages(RecipeReq recipeReq);

    List<TransferMedicineObj> listTransferRecipeMedicinePrintMessages(List<Long> billIdList);
    
    List<RecipePrintObj> listRecipePrintMessages(RecipeReq recipeReq);

    List<TransferMedicineObj> listRecipeMedicinePrintMessages(List<Integer> recipeIdList);
    
    List<RecipePrintObj> listTackMedicine(Integer id);
    
    @Select("SELECT native_recipe_id from transfer_recipe where id=#{value}")
    Integer getNativeRecipeId(Integer transferRecipeId);
    
}
