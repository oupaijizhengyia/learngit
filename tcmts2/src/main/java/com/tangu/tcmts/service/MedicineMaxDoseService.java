package com.tangu.tcmts.service;

import java.util.List;

import com.tangu.tcmts.po.MedicineMaxDose;
import com.tangu.tcmts.po.RecipeMedicine;

public interface MedicineMaxDoseService {
  
  Object listMedicineMaxDose(MedicineMaxDose medicineMaxDose);

  Object saveMedicineMaxDose(MedicineMaxDose medicineMaxDose);

  Object deleteMedicineMaxDose(Integer id);
  
  List<MedicineMaxDose> listMedicineMaxDoseByMedicineIds(List<RecipeMedicine> list);

}
