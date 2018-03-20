package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.RecipeMedicine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecipeMedicineMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RecipeMedicine record);

    int insertSelective(RecipeMedicine record);

    List<RecipeMedicine> getRecipeMedicineByRecipe(Integer id);

    int updateByPrimaryKeySelective(RecipeMedicine record);

    int updateByPrimaryKey(RecipeMedicine record);
    int insertByBatch(RecipeDO recipeDO);
    Integer updateRecipeMedicine(List<RecipeMedicine> list);
    List<RecipeMedicine> listNativeRecipeMedicine(RecipeDO recipe);
    Integer deleteRecipeMedicine(RecipeDO recipe);
    List<RecipeMedicine> findRecipeMedicineBySpecialBoilType(RecipeMedicine recipeMedicine);
    Integer updateRecipeMedicines(List<RecipeMedicine> list);
}