package com.tangu.tcmts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tangu.tcmts.po.MedicineMaxDose;
import com.tangu.tcmts.service.MedicineMaxDoseService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/medicineMaxDose")
public class MedicineMaxDoseController {
  private final String tags = "medicineMaxDose";
  
  @Autowired
  MedicineMaxDoseService medicineMaxDoseService;
  
  @ApiOperation(tags = tags, value = "查询超剂量", 
      notes="传入medicineId",
      response = MedicineMaxDose.class)
  @RequestMapping(value = "/listMedicineMaxDose", method = RequestMethod.POST)
  public Object listMedicineMaxDose(@RequestBody MedicineMaxDose medicineMaxDose) {
      return medicineMaxDoseService.listMedicineMaxDose(medicineMaxDose);
  }
  
  @ApiOperation(tags = tags, value = "新增/编辑超剂量", 
      notes="传入id, medicineId, maxWeightEvery",
      response = MedicineMaxDose.class)
  @RequestMapping(value = "/saveMedicineMaxDose", method = RequestMethod.POST)
  public Object saveMedicineMaxDose(@RequestBody MedicineMaxDose medicineMaxDose) {
      return medicineMaxDoseService.saveMedicineMaxDose(medicineMaxDose);
  }
  
  @ApiOperation(tags = tags, value = "删除超剂量", 
      notes="传入id",
      response = MedicineMaxDose.class)
  @RequestMapping(value = "/deleteMedicineMaxDose", method = RequestMethod.POST)
  public Object deleteMedicineMaxDose(@RequestBody MedicineMaxDose medicineMaxDose) {
    return medicineMaxDoseService.deleteMedicineMaxDose(medicineMaxDose.getId());
  }
}
