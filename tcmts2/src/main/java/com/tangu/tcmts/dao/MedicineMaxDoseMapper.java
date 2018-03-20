package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tangu.tcmts.po.MedicineMaxDose;
import com.tangu.tcmts.po.RecipeMedicine;

@Mapper
public interface MedicineMaxDoseMapper {
  int deleteByPrimaryKey(Integer id);

  int insertSelective(MedicineMaxDose record);

  int updateByPrimaryKeySelective(MedicineMaxDose record);

  List<MedicineMaxDose> listMedicineMaxDose(MedicineMaxDose medicineMaxDose);
  
  Integer listMedicineMaxDoseByMedicineId(MedicineMaxDose medicineMaxDose);
  
  List<MedicineMaxDose> listMedicineMaxDoseByMedicineIds(List<RecipeMedicine> list);

}