package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tangu.tcmts.po.RecipeMedicine;
import com.tangu.tcmts.po.TransferMedicine;
import com.tangu.tcmts.po.TransferRecipeDTO;

@Mapper
public interface TransferMedicineMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TransferMedicine record);

    int insertSelective(TransferMedicine record);

    List<TransferMedicine> getMessageByTransferRecipe(TransferMedicine transferMedicine);

    int updateByPrimaryKeySelective(TransferMedicine record);

    int updateByPrimaryKey(TransferMedicine record);
    
    List<RecipeMedicine> findMedicineListByBillIds(List billIdlist);
    
    Integer insertTransferMedicine(List<TransferMedicine> list);
    
    List<RecipeMedicine> listTransferMedicineById(List<TransferRecipeDTO> list);
    
    List<RecipeMedicine> listTrMedicineToRpMedicine(TransferMedicine record);
}