package com.tangu.tcmts.service;

import com.tangu.tcmts.po.MedicineTaboo;

import java.util.List;
import java.util.Map;

public interface MedicineTabooService {

    List<MedicineTaboo> getMedicineTabooList(MedicineTaboo medicineTaboo);

    MedicineTaboo getRepeatMedicineTaboo(MedicineTaboo medicineTaboo);

   // MedicineTaboo getParentRepeatMedicineTaboo(MedicineTaboo medicineTaboo);

    Integer deleteMedicineTaboo(Integer id);

    Integer insertMedicineTaboo(MedicineTaboo medicineTaboo);

    Integer updateMedicineTaboo(MedicineTaboo medicineTaboo);
    Map<String,String> getMedicineTabooUnionMap();
}
