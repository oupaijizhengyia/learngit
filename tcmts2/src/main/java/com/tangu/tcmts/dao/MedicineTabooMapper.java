package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.MedicineTaboo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MedicineTabooMapper {

    List<MedicineTaboo> getMedicineTabooList(MedicineTaboo medicineTaboo);

    MedicineTaboo getRepeatMedicineTaboo(MedicineTaboo medicineTaboo);

    @Delete(" delete from medicine_taboo where id = #{id}")
    Integer deleteMedicineTaboo(Integer id);

    Integer insertMedicineTaboo(MedicineTaboo medicineTaboo);

    Integer updateMedicineTaboo(MedicineTaboo medicineTaboo);

    List<MedicineTaboo> getMedicineTabooUnionMap();
}