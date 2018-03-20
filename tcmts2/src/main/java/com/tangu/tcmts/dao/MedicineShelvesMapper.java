package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tangu.tcmts.po.MedicineShelves;

@Mapper
public interface MedicineShelvesMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(MedicineShelves record);

    List<MedicineShelves> listMedicineShelves(MedicineShelves record);

    int updateByPrimaryKeySelective(MedicineShelves record);

    //货架标签
    List<MedicineShelves> listMedicineShelvesByIds(List<MedicineShelves> list);

    
    Integer seleteRecipeByShelvesCode(MedicineShelves medicineShelves);
    
}