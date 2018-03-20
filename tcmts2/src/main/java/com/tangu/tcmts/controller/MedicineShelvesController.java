package com.tangu.tcmts.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.po.MedicineShelves;
import com.tangu.tcmts.po.PrintPage;
import com.tangu.tcmts.po.PrintPageObj;
import com.tangu.tcmts.service.MedicineShelvesService;
import com.tangu.tcmts.service.PrintService;
import com.tangu.tcmts.util.PrintUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/medicineShelves")
@Api(value = "/medicineShelves", description = "货架管理")
public class MedicineShelvesController {
  
  private final String tags = "medicineShelves";
  
  @Autowired
  MedicineShelvesService medicineShelvesService;
  
  @Autowired
  PrintService printService;
  
  @ApiOperation(tags = tags, value = "查询货架列表",
      notes="传入shelvesCode, shelvesType",
      response = MedicineShelves.class)
  @RequestMapping(value = "/listMedicineShelves", method = RequestMethod.POST)
  //TODO 返回货架类型
  public Object listMedicineShelves(@RequestBody MedicineShelves medicineShelves) {
      return medicineShelvesService.listMedicineShelves(medicineShelves);
  }
  
  @ApiOperation(tags = tags, value = "新增/编辑货架", 
      notes="传入id, shelvesCode, shelvesType, remark",
      response = MedicineShelves.class)
  @RequestMapping(value = "/saveMedicineShelves", method = RequestMethod.POST)
  public Object saveMedicineShelve(@RequestBody MedicineShelves medicineShelves) {
      return medicineShelvesService.saveMedicineShelve(medicineShelves);
  }
  
  @ApiOperation(tags = tags, value = "删除货架",
      notes="传入id",
      response = MedicineShelves.class)
  @RequestMapping(value = "/deleteMedicineShelves", method = RequestMethod.POST)
  public Object deleteMedicineShelve(@RequestBody MedicineShelves medicineShelves) {
	  if (medicineShelvesService.seleteRecipeByShelvesCode(medicineShelves) > 0) {
		  return new ResponseModel().attr(ResponseModel.KEY_ERROR, "货架已被使用，不允许删除!");
	  }
    return medicineShelvesService.deleteMedicineShelve(medicineShelves.getId());
  }
  
  @ApiOperation(value = "货架管理-打印", notes = "传入id", response = PrintPageObj.class)
  @RequestMapping(value = "/printMedicineShelves", method = RequestMethod.POST)
  public Object printMedicineShelves(@RequestBody List<MedicineShelves> list) {
    List<MedicineShelves> medicineShelvesList = medicineShelvesService.listMedicineShelvesByIds(list);
    //TODO 打印模板  后台定义
    PrintPage printPage = printService.getPrintPageByBillType("medicineShelves");
    List<PrintPageObj> resultList = new ArrayList<>();
    for (MedicineShelves medicineShelves : medicineShelvesList) {
      resultList.add(PrintUtil.getPrintableCanvasObj(printPage, medicineShelves));
    }
    return resultList;
  }
}
