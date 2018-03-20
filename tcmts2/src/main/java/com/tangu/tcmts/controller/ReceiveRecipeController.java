package com.tangu.tcmts.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.po.Company;
import com.tangu.tcmts.po.MedicineDO;
import com.tangu.tcmts.po.PrintPage;
import com.tangu.tcmts.po.PrintPageObj;
import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.RecipeMedicine;
import com.tangu.tcmts.po.RecipePrintObj;
import com.tangu.tcmts.po.RecipeReq;
import com.tangu.tcmts.po.TransferMedicine;
import com.tangu.tcmts.po.TransferRecipeDO;
import com.tangu.tcmts.po.TransferRecipeDTO;
import com.tangu.tcmts.service.MedicineService;
import com.tangu.tcmts.service.PrintService;
import com.tangu.tcmts.service.RecipeService;
import com.tangu.tcmts.service.SysConfigService;
import com.tangu.tcmts.service.TransferRecipeService;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.MedicineUtil;
import com.tangu.tcmts.util.RecipePrintUtil;
import com.tangu.tcmts.util.RecipeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value = "receiveRecipe", description = "接方")
@RestController
@RequestMapping("receiveRecipe")
@Slf4j
public class ReceiveRecipeController {
    @Autowired
    private TransferRecipeService transferRecipeService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private PrintService printService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    MedicineService medicineService;
    private final String TAGS_TRANSFER_RECIPE="接方管理";
    
    @ApiOperation(tags = TAGS_TRANSFER_RECIPE, value = "接方列表",
        notes="传入 startDate, endDate, ifReceive, invoiceCode, recipientName, outpatientNum, carrayId", 
        response = TransferRecipeDO.class)
    @RequestMapping(value = "/listReciveRecipeList", method = RequestMethod.POST)
    public Object listReciveRecipeList(@RequestBody TransferRecipeDTO record) {
      log.info("listReciveRecipeList执行时间 {}",System.currentTimeMillis());
      Object obj = transferRecipeService.listReciveRecipeList(record);
      log.info("listReciveRecipeList执行时间 {}",System.currentTimeMillis());
      return obj;
    }
    
    @ApiOperation(tags = TAGS_TRANSFER_RECIPE, value = "参数：invoiceCode(发票号)",
        notes="传入 发票号", response = TransferRecipeDO.class)
    @RequestMapping(value = "/getTransferRecipeList", method = RequestMethod.POST)
    public Object getTransferRecipeList(@RequestBody TransferRecipeDO record) {
    	List<TransferRecipeDO> list=transferRecipeService.listTransferRecipeByInvoiceCode(record.getInvoiceCode());
    	if(list.isEmpty()){
    		//无法找到处方，请与医生确认、患者核实发票号!
    		return new ResponseModel().attr(ResponseModel.KEY_ERROR, "无法找到处方，请与医生确认、患者核实发票号!");
    	}
    	list.forEach(t->{
        if(t.getShippingState() == null){
          t.setShippingState(0);
        }
        if(t.getShippingState() == Constants.TYPE_MAX || t.getIfReceive() == Constants.TYPE_MAX){
          t.setIfReceive(Constants.TYPE_MAX);
        }else if (t.getShippingState() >= Constants.TYPE_FIVE || t.getIfReceive() >= Constants.TYPE_FIVE) {
          t.setIfReceive(Constants.TYPE_FIVE);
        }
    	});
      return new ResponseModel(list);
    }
    
    @ApiOperation(tags = TAGS_TRANSFER_RECIPE,value="门诊接方参数:id,carrId,takeTime,logisticsCode," ,response = ResponseModel.class)
    @ApiImplicitParam(name = "makeRecipe", dataType = "TransferRecipeDO")
    @RequestMapping(value = "/makeRecipe", method = RequestMethod.POST)
    public Object makeRecipe(@RequestBody TransferRecipeDO record) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	TransferRecipeDO tr=transferRecipeService.getTransferRecipeContent(record.getId());
    	if(tr.getIfReceive() != null && tr.getIfReceive()==0){
    		//订单未接收
    		TransferMedicine transferMedicine = new TransferMedicine();
            transferMedicine.setBillId(tr.getBillId());
            transferMedicine.setHospitalCode(tr.getHospitalCode());
            List<RecipeMedicine> transferMedicineList = transferRecipeService.listTrMedicineToRpMedicine(transferMedicine);
            Map<String, MedicineDO> nativeMap=MedicineUtil.getMap(medicineService.listMedicine(null));
            boolean flag=transferMedicineList.stream().anyMatch(e -> nativeMap.get(e.getMedicineName())==null);
            if(flag){
            	//有药品未匹配
            	TransferRecipeDO para=new TransferRecipeDO();
            	para.setId(record.getId());
            	para.setMedicineMatch(false);
            	para.setReceiveTime(new Date());
            	para.setIfReceive(Constants.TYPE_ONE);
            	transferRecipeService.updateByPrimaryKeySelective(para);
            	transferRecipeService.replaceReceiveInfo(record);
            	return new ResponseModel(Constant.OPRATION_SUCCESS);
            }
            transferMedicineList.forEach(e ->{
            	e.setMedicineId(nativeMap.get(e.getMedicineName()).getId());
            	if(StringUtils.isBlank(e.getSpecialBoilType())){
					e.setSpecialBoilType(nativeMap.get(e.getMedicineName()).getSpecialBoilType());
				}
            	e.setUnitType(nativeMap.get(e.getMedicineName()).getUnitType());
            });
            RecipeDO r=RecipeUtil.transferToRecipie(tr);
            r.setTakeTime(sdf.format(record.getTakeTime()));
            r.setDealDate(new Date());
            r.setLogisticsCode(record.getLogisticsCode());
            r.setReceiveRemark(record.getReceiveRemark());
            r.setCarryId(record.getCarryId());
            r.setRecipeMedicineList(transferMedicineList);
            r.setRecipeSerial(RecipeUtil.leftZero(tr.getId()));
            r.setRecipeSource(1);
            r.setCreatorId(JwtUserTool.getId());
            if (r.getPackagePaste() == null) {
        		r.setPackagePaste(Constants.TYPE_TWO);
        	}
        	r.setTotalPackagePaste(r.getQuantity()*r.getPackagePaste());
        	RecipeUtil.setReceiveRecipe(null, null, r);
            recipeService.insertRecipe(r);
            record.setNativeRecipeId(r.getId());
    	}else if(tr.getNativeRecipeId() != null && tr.getNativeRecipeId()>0){
    		//已接收更改接方信息
    		RecipeDO r=new RecipeDO();
    		r.setId(tr.getNativeRecipeId());
    		r.setLogisticsCode(record.getLogisticsCode());
    		r.setTakeTime(sdf.format(record.getTakeTime()));
    		r.setReceiveRemark(record.getReceiveRemark());
    		r.setCarryId(record.getCarryId());
    		record.setNativeRecipeId(tr.getNativeRecipeId());
    		recipeService.updateByPrimaryKeySelective(r);
    	}else if(tr.getIfReceive() != null && tr.getIfReceive()==1 && 
    			tr.getMedicineMatch() !=null && tr.getMedicineMatch()==false){
    		//存在未匹配只更新接收信息
    		transferRecipeService.replaceReceiveInfo(record);
		}
    	List<TransferRecipeDTO> list = new ArrayList<>();
    	TransferRecipeDTO trd = new TransferRecipeDTO();
    	trd.setId(record.getId());
    	trd.setTakeTime(record.getTakeTime());
    	trd.setLogisticsCode(record.getLogisticsCode());
    	trd.setReceiveRemark(record.getReceiveRemark());
    	trd.setReceiverId(JwtUserTool.getId());
    	trd.setCarryId(record.getCarryId());
    	list.add(trd);
		transferRecipeService.insertTransferRecipeTmp(list);
		return new ResponseModel(record).attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
	}

    @ApiOperation(tags = TAGS_TRANSFER_RECIPE, value = "打印取药单", notes="打印取药单参数：id,nativeRecipeId")
    @ApiImplicitParam(value = "打印取药单参数：id,nativeRecipeId", dataType = "TransferRecipeDO")
    @RequestMapping(value = "/printTakeMedicine", method = RequestMethod.POST)
    public Object printTakeMedicine(@RequestBody TransferRecipeDO record) {
    	List<PrintPageObj> pageObjs=new ArrayList<>();
    	Map<String,Object> map=new HashMap<>();
    	List<String> printTypeList=new ArrayList<String>();
    	printTypeList.add(Constants.PrintType.TAKE_MEDICINE);
    	map.put("typeIds",printTypeList);
    	Map<String,PrintPage> pageMap=printService.listPrintPageItem(map);
        List<RecipePrintObj> printPageObjList = printService.listTackMedicine(record);
        Company company=sysConfigService.listCompany();
    	String operator=JwtUserTool.getUserName();
    	printPageObjList.forEach(e ->{
    		e.setOperator(operator);
    		RecipePrintUtil.setCompanyInfo(company, e);
    	});
        /**
         * 遍历打印类型,添加打印对象
         */
        List<Integer> recordIds=new ArrayList<>();
        RecipeReq recipeReq=new RecipeReq();
        printTypeList.forEach(
        		e -> 
        		pageObjs.addAll(
            			RecipePrintUtil.setRecipePrintList(printPageObjList, 
            					e.toString(), pageMap,recipeReq.getTagNum(),recordIds))
        		);
       return pageObjs;
    }

}
   
