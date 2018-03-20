package com.tangu.tcmts.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tangu.common.util.DateUtil;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.po.MedicinePriceHistoryDO;
import com.tangu.tcmts.po.MedicinePriceHistoryDTO;
import com.tangu.tcmts.po.PriceTempletDTO;
import com.tangu.tcmts.po.PriceTempletDetailDTO;
import com.tangu.tcmts.service.MedicineService;
import com.tangu.tcmts.service.PriceTempletService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/priceTemplet", description = "价格模块")
@RestController
@RequestMapping("/priceTemplet")
public class PriceTempletController {
	  private final static String tags="价格模板";
	
    @Autowired
    MedicineService medicineService;
    
	  @Autowired
	  PriceTempletService priceTempletService;
	  
	  @ApiOperation(tags = tags, value = "获取价格模板"
	     , response = PriceTempletDTO.class)
	  @RequestMapping(value = "/getPriceTempletList", method = RequestMethod.POST)
	  public Object getPriceTempletList(){
	    return priceTempletService.getPriceTempletList();
	  }//ok

    @ApiOperation(tags = tags, value = "价格模板下拉列表"
       , response = PriceTempletDTO.class)
    @RequestMapping(value = "/getPriceTempletListDropDown", method = RequestMethod.GET)
    public Object getPriceTempletListDropDown(){
     return priceTempletService.getPriceTempletListDropDown();
    }//ok

    @ApiOperation(tags = tags, value = "新增/保存价格模板"
       , response = PriceTempletDTO.class
       , notes = "传入templetId, templetName")
    @RequestMapping(value = "/savePriceTemplet", method = RequestMethod.POST)
    public Object savePriceTemplet(@RequestBody PriceTempletDTO record) {
        return priceTempletService.savePriceTempletName(record);
    }//ok
  	  
	  @ApiOperation(tags = tags, value = "获取价格模板详情"
	       , response = PriceTempletDTO.class
	       , notes = "传入templetId")
	  @RequestMapping(value = "/getPriceTempletDetail", method = RequestMethod.POST)
	  public Object getPriceTempletDetail(@RequestBody PriceTempletDetailDTO record){
      return priceTempletService.getPriceTempletDetail(record);
	  }//ok

	  @ApiOperation(tags = tags, value = "获取单个价格模板详情"
        , response = PriceTempletDTO.class
        , notes = "传入templetId, medicineId")
    @RequestMapping(value = "/getSinglePriceTempletDetail", method = RequestMethod.GET)
    public Object getSinglePriceTempletDetail(@RequestParam Integer templetId, @RequestParam Integer medicineId){
	    PriceTempletDetailDTO record = new PriceTempletDetailDTO();
	    record.setTempletId(templetId);
	    record.setMedicineId(medicineId);
      return priceTempletService.getPriceTempletDetail(record);
    }//ok

	  
 	  @ApiOperation(tags = tags, value = "删除模板"
 	    , response = ResponseModel.class
 	    , notes = "传入 templetId")
    @RequestMapping(value = "/delPriceTemplet", method = RequestMethod.POST)
    public Object delPriceTemplet(@RequestBody PriceTempletDTO record) {
        return priceTempletService.delPriceTemplet(record);
    }//ok

    
    @ApiOperation(tags = tags, value = "查看单个模板单个药品调价记录详情", notes = "传入medicineId, templetId", response = PriceTempletDetailDTO.class)
    @RequestMapping(value = "/listPriceTempleByTemidAndMedid", method = RequestMethod.POST)
    public Object listPriceTempleByTemidAndMedid(@RequestBody PriceTempletDetailDTO priceTempletDetailDTO) {
      return priceTempletService.listPriceTempleByTemidAndMedid(priceTempletDetailDTO);
    }
    
    @ApiOperation(tags = tags, value = "删除调价记录", 
        notes = "传入templetDetailId, medicineLast, templetId, medicineId", 
        response = PriceTempletDetailDTO.class)
    @RequestMapping(value = "/deleteByTempletDetailId", method = RequestMethod.POST)
    public Object deleteByTempletDetailId(@RequestBody PriceTempletDetailDTO priceTempletDetailDTO) {
      return priceTempletService.deleteByTempletDetailId(priceTempletDetailDTO);
    }
    
 	  @ApiOperation(tags = tags, value = "单个调价"
 	     , response = PriceTempletDetailDTO.class
 	     , notes = "传入templetDetailId, templetId, medicineId, "
 	         + "nowTradePrice, nowRetailPrice, priceStart ")
 	  @RequestMapping(value = "/changePrice", method = RequestMethod.POST)
    public Object changePrice(@RequestBody PriceTempletDetailDTO record) {
        return priceTempletService.changeSingeMedPrice(record);
    }//ok
 	
   	@ApiOperation(tags = tags, value = "批量调价",
   	   response = PriceTempletDetailDTO.class,
   	   notes = "传入 templetDetailId, templetId, medicineId,"
   	       + "nowReailPrice, nowTradePrice, retailPrice, tradePrice,  priceStart")
    @RequestMapping(value = "/changePriceList", method = RequestMethod.POST)
    public Object changePriceList(@RequestBody List<PriceTempletDetailDTO> record) {
       return priceTempletService.changeListMedPrice(record);
    }//ok
   	
    @ApiOperation(tags = tags, value = "载入药品"
        , response = PriceTempletDTO.class
        , notes = "传入  medicineId")
    @RequestMapping(value = "/loadMedicines", method = RequestMethod.POST)
    public Object loadMedicines(@RequestBody PriceTempletDetailDTO record) {
       return priceTempletService.loadMedicines(record);
    }//ok

    @ApiOperation(tags = tags, value = "调价记录列表/条件查询", notes = "传入medicineName = 药品名称,priceType = 价格类型,startDate = 开始时间,endDate = 结束时间", response = MedicinePriceHistoryDO.class)
    @RequestMapping(value = "/listHistory", method = RequestMethod.POST)
    public Object listHistory(@RequestBody MedicinePriceHistoryDTO medicinePriceHistoryDTO) {
      return new ResponseModel(priceTempletService.listHistory(medicinePriceHistoryDTO));
    }//ok
    
    @ApiOperation(tags = tags, value = "开方获取模板内多个药品价格"
        , response = PriceTempletDetailDTO.class
        , notes = "传入 priceStart(处方受理日) templetId(结算方模板ID),(药品ID)medicineIdList[1,2,3] ")
    @RequestMapping(value = "/listPriceByTemIdsOrMedIds", method = RequestMethod.POST)
    public Object listPriceByTemIdsOrMedIds(@RequestBody PriceTempletDetailDTO record) {
       List<PriceTempletDetailDTO> list=priceTempletService.listPriceByTemIdsOrMedIds(record);
       Map<Integer,PriceTempletDetailDTO> map=new HashMap<>();
       if(list !=null){
    	   list.forEach(e ->{
    		   if(!map.containsKey(e.getMedicineId())){
    			   if(record.getPriceStart().compareTo(e.getPriceStart())>=0
    					   &&(e.getPriceEnd()!=null?(record.getPriceStart().compareTo(e.getPriceEnd())<=0):true)) {
    				   map.put(e.getMedicineId(), e);
    			   }
    		   }
    	   });
       }
       return new ResponseModel(map.values());
    }
    
    @ApiOperation(tags = tags, value = "开方获取单个药品价格"
            , response = PriceTempletDetailDTO.class
            , notes = "传入 priceStart(处方受理日) templetId(结算方模板ID),(药品ID)medicineId ")
    @RequestMapping(value = "/getPriceByTemIdAndMedId", method = RequestMethod.GET)
    public Object getPriceByTemIdAndMedId(@RequestParam String priceStart,@RequestParam Integer templetId,@RequestParam Integer medicineId) {
    	Date priceStartDate=null;
		try {
			priceStartDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm", priceStart);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	PriceTempletDetailDTO record=new PriceTempletDetailDTO();
    	record.setMedicineId(medicineId);
    	record.setTempletId(templetId);
    	record.setPriceStart(priceStartDate);
    	PriceTempletDetailDTO result=priceTempletService.getPriceByTemIdAndMedId(record);
       return new ResponseModel(result);
    }
}
