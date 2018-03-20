package com.tangu.tcmts.controller;

import static com.tangu.tcmts.util.Constants.MEDICINE_TABOO_KEY;
import static com.tangu.tcmts.util.SystemConstant.TRANSFER_DEFAULT_TIME;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.bean.SysPropertyUtil;
import com.tangu.tcmts.po.BtRelationZd;
import com.tangu.tcmts.po.Company;
import com.tangu.tcmts.po.HospitalDO;
import com.tangu.tcmts.po.MedicineDO;
import com.tangu.tcmts.po.MedicineRelationDO;
import com.tangu.tcmts.po.Prescription;
import com.tangu.tcmts.po.PriceTempletDetailDTO;
import com.tangu.tcmts.po.PrintPage;
import com.tangu.tcmts.po.PrintPageObj;
import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.RecipeMedicine;
import com.tangu.tcmts.po.RecipePara;
import com.tangu.tcmts.po.RecipePrintObj;
import com.tangu.tcmts.po.RecipeReq;
import com.tangu.tcmts.po.SpecialDiscount;
import com.tangu.tcmts.po.SysConfig;
import com.tangu.tcmts.po.SysLookup;
import com.tangu.tcmts.po.TransferMedicine;
import com.tangu.tcmts.po.TransferMedicineObj;
import com.tangu.tcmts.po.TransferRecipeDO;
import com.tangu.tcmts.po.TransferRecipeDTO;
import com.tangu.tcmts.service.HospitalService;
import com.tangu.tcmts.service.MedicineRelationService;
import com.tangu.tcmts.service.MedicineService;
import com.tangu.tcmts.service.MedicineTabooService;
import com.tangu.tcmts.service.PrescriptionService;
import com.tangu.tcmts.service.PriceTempletService;
import com.tangu.tcmts.service.PrintService;
import com.tangu.tcmts.service.RecipeService;
import com.tangu.tcmts.service.SettleCompanyService;
import com.tangu.tcmts.service.SysConfigService;
import com.tangu.tcmts.service.TransferRecipeService;
import com.tangu.tcmts.util.CommonUtil;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.ImportExcelUtil;
import com.tangu.tcmts.util.MedicineUtil;
import com.tangu.tcmts.util.RecipePrintUtil;
import com.tangu.tcmts.util.RecipeUtil;
import com.tangu.tcmts.util.SystemConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@Api(value = "transferRecipe", description = "订单列表")
@RestController
@Slf4j
@RequestMapping("transferRecipe")
public class TransferRecipeController {
    @Autowired
    private TransferRecipeService transferRecipeService;
    @Autowired
    private MedicineTabooService medicineTabooService;
    @Autowired
    private SettleCompanyService settleCompanyService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private MedicineRelationService medicineRelationService;
    @Autowired
    private PrintService printService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private SysPropertyUtil sysPropertyUtil;
    @Autowired
    private PriceTempletService priceTempletService;
    @Autowired
    MedicineService medicineService;
    private final String TAGS_TRANSFER_RECIPE="订单管理";
    private final Integer EXPRESS_TO_HOSPITAL = 3;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @ApiOperation(tags = TAGS_TRANSFER_RECIPE, value = "院内--重新匹配订单", notes = "传入 id")
	@RequestMapping(value = "matchTransferRecipeForHos", method = RequestMethod.POST)
	public Object matchTransferRecipeForHos(@RequestBody List<TransferRecipeDTO> transferRecipeDTO) {
		if (CollectionUtils.isEmpty(transferRecipeDTO)) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, "请传入id!");
		}
		Map<String, MedicineDO> nativeMap = MedicineUtil.getMap(medicineService.listMedicine(null));
		List<RecipeMedicine> transferMedicineList = transferRecipeService.listTransferMedicineById(transferRecipeDTO);
		List<RecipeMedicine> resultList = RecipeUtil.contrastData(nativeMap, transferMedicineList);
		
		if (CollectionUtils.isNotEmpty(resultList)) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED)
					.attr(ResponseModel.KEY_DATA, resultList);
		}
		
		if (transferRecipeService.updateMedicineMatch(transferRecipeDTO)) {
			/**
			 * 修改成功：把订单转换成处方
			 */
			List<RecipeDO> list = transferRecipeService.listReceiveTransferRecipes(transferRecipeDTO);
			if (CollectionUtils.isEmpty(list)) {
				return new ResponseModel().attr(ResponseModel.KEY_ERROR, "该订单不存在，请确认!");
			}
			 
		        Date nowTime=new Date();
		        Date minDealDate=nowTime;
		        String defaultTime = sysPropertyUtil.getProperty(TRANSFER_DEFAULT_TIME,SystemConstant.IS_NO);
		        if (list != null && list.size() > 0) {
		            List<RecipeMedicine> tempList=null;
		            Integer userId = JwtUserTool.getId();
		            for (RecipeDO r : list) {
		            	//不启用医院时间,设置当前时间为受理时间
		            	if (SystemConstant.IS_NO.equals(defaultTime)){
		            		r.setDealDate(nowTime);
		            	}else{
		            		if(r.getDealDate().before(minDealDate)){
		            			minDealDate=r.getDealDate();
		            		}
		            	}
		                r.setCreatorId(userId);
		                tempList=transferMedicineList.stream()
		                		.filter(rm -> (r.getBillId() + "_" + r.getHospitalCode())
		                				.equals(rm.getBillId() + "_" + rm.getHospitalCode()))
		                		.collect(Collectors.toList());
		                r.setRecipeMedicineList(tempList);
		            }
		        }
		        Integer userId=JwtUserTool.getId();
		        for(RecipeDO r:list){
		        	if (r.getTakeTime() == null) {
		        		r.setTakeTime(sdf.format(RecipeUtil.getCalendar()));
		        	}
		        	if (r.getTmpCarryId() != null) {
		        		r.setCarryId(r.getTmpCarryId());
		        	}
		        	if (StringUtils.isNotBlank(r.getTmpLogisicsCode())) {
		        		r.setLogisticsCode(r.getTmpLogisicsCode());
		        	}
		        	r.setCreatorId(userId);
		        	r.setShippingState(Constants.STATE_CREATE);
	        		r.setInputEmployee(r.getCreatorId());
	    	        r.setInputState(1);
	    	        r.setInputTime(nowTime);
		        	r.setRecipeSerial(RecipeUtil.leftZero(r.getId()));
		        	if (r.getPackagePaste() == null) {
		        		r.setPackagePaste(Constants.TYPE_TWO);
		        	}
		        	r.setTotalPackagePaste(r.getQuantity()*r.getPackagePaste());
		        	r.setRecipeSerial(RecipeUtil.leftZero(r.getId()));
		        	r.setUseHisMoney(true);
		        	RecipeUtil.setReceiveRecipe(null, null, r);
		        }
		        List<TransferRecipeDO> resultData=null;
		        try {
		            if (list != null && list.size() > 0) {
		            	resultData=recipeService.batchInsertRecipe(list);
		            }
		        } catch (Exception e) {
		        	log.error("bactch receive recipe Exception",e);
		            return new ResponseModel().fail();
		        }
			return new ResponseModel(resultData).attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}
    
	@ApiOperation(tags = TAGS_TRANSFER_RECIPE, value = "院内--查看未匹配药品",
        notes="传入 id",response = TransferRecipeDO.class)
    @RequestMapping(value = "noMatchMedicineForHos", method = RequestMethod.POST)
    public Object noMatchMedicineForHos(@RequestBody TransferRecipeDTO transferRecipeDTO) {
		if (transferRecipeDTO.getId() == null) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, "请传入id!");
		}
		/**
		 * 获取本地全部药品信息
		 */
		Map<String, MedicineDO> nativeMap = MedicineUtil.getMap(medicineService.listMedicine(null));
		/**
		 * 对比数据：查看是否有匹配不上的数据
		 */
		List<TransferRecipeDTO> list = new ArrayList<>();
		list.add(transferRecipeDTO);
        return new ResponseModel(RecipeUtil.contrastData(nativeMap, transferRecipeService.listTransferMedicineById(list)));
    }
    
    @ApiOperation(tags = TAGS_TRANSFER_RECIPE, value = "查询列表",
        notes="传入 startDate endDate belong ifReceive invoiceCode recipientName carryId medicineMatch",
        response = TransferRecipeDO.class)
    @RequestMapping(value = "transferRecipeIndex", method = RequestMethod.POST)
    public Object transferRecipeIndex(@RequestBody TransferRecipeDTO transferRecipeDTO) {
    	String hospitalType = sysPropertyUtil.getProperty(Constants.HOSPITAL_TYPE, "1");
       	if (hospitalType.equals("0")) {
       		transferRecipeDTO.setOrderType(0);
       	}
        return new ResponseModel(transferRecipeService.listTransferRecipes(transferRecipeDTO));
    }

    @ApiOperation(tags = TAGS_TRANSFER_RECIPE, value = "查询详情", response = TransferRecipeDO.class)
    @ApiImplicitParam(name = "getTransferRecipeContent", value = "查询详情", dataType = "java.lang.Integer")
    @RequestMapping(value = "getTransferRecipeContent", method = RequestMethod.POST)
    public Object getTransferRecipeContent(@RequestBody TransferRecipeDTO transferRecipeDTO) {
        TransferRecipeDO transferRecipe = transferRecipeService.getTransferRecipeContent(transferRecipeDTO.getId());
        TransferMedicine transferMedicine = new TransferMedicine();
        transferMedicine.setBillId(transferRecipe.getBillId());
        transferMedicine.setHospitalCode(transferRecipe.getHospitalCode());
        List<TransferMedicine> transferMedicineList = transferRecipeService.getMessageByTransferRecipe(transferMedicine);
        HospitalDO hospital =  hospitalService.getHospitalContent(transferRecipe.getHospitalId());

		if (transferRecipe.getCarryId() == null){
			if (hospital.getCarryType() == null){
				transferRecipe.setCarryId(EXPRESS_TO_HOSPITAL);
			}else {
				transferRecipe.setCarryId(hospital.getCarryType());
			}
		}
        String defaultTime = sysPropertyUtil.getProperty(TRANSFER_DEFAULT_TIME,SystemConstant.IS_NO);
		if (transferRecipe.getIfReceive() == 1 && SystemConstant.IS_YES.equals(defaultTime)){
			return new ResponseModel(transferRecipe).attr("transferMedicineList", transferMedicineList).attr("hospital",hospital);
		}else{
			transferRecipe.setTradeTime(new Date());
		}
		return new ResponseModel(transferRecipe).attr("transferMedicineList", transferMedicineList).attr("hospital",hospital);
    }

    @ApiOperation(tags = TAGS_TRANSFER_RECIPE, value = "报废订单")
    @ApiImplicitParam(name = "changeTransferRecipeState", value = "报废订单", dataType = "java.lang.Integer")
    @RequestMapping(value = "changeTransferRecipeState", method = RequestMethod.POST)
    public Object changeTransferRecipeState(@RequestBody TransferRecipeDO transferRecipeDO) {
        Integer result = 0;
        transferRecipeDO.setIfReceive(999);
        result = transferRecipeService.updateUseState(transferRecipeDO);
        if (result.equals(1)) {
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(tags = TAGS_TRANSFER_RECIPE, value = "接收订单", response = TransferRecipeDO.class)
    @RequestMapping(value = "/receiveTransferRecipe", method = RequestMethod.POST)
    public Object receiveTransferRecipe(@RequestBody RecipePara recipePara) {
        RecipeDO recipe = recipePara.getRecipe();
        RecipeDO checkRecipe = transferRecipeService.checkTransferRecipe(recipe.getId());
        if (checkRecipe == null) {
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, "接收失败，订单已被其他人接收");
        }
        recipe.getRecipeMedicineList().forEach(e -> e.setMedicineName(e.getName_medicineName()));
        //关联药品
        if(recipePara.getRelationList()!=null&&recipePara.getRelationList().size()>0){
        	for (MedicineRelationDO m : recipePara.getRelationList()) {
        		if (m.getMedicineType() == Constants.STATE_ZERO) {
        			m.setHospitalId(Constants.STATE_ZERO);
        			m.setHospitalMedicineCode("");
        		}
        	}
        	medicineRelationService.insertMedicineRelation(recipePara.getRelationList());
        }
        Map<String,Object> configMap =sysConfigService.listConfig().stream()
                .collect(Collectors.toMap(SysConfig::getName,SysConfig::getValue));
        /**
         * 检测药品关联
         */
        //获取全部关联药品
        recipe.setHospitalName(checkRecipe.getHospitalName());
        if(CommonUtil.isTrue(configMap.get(Constants.ConfigKey.RELATION_MEDICINE))){
        	List<MedicineRelationDO> nativeList = medicineRelationService.listMedicineRelationAll();
        	Map<String, MedicineRelationDO> nativeMap = RecipeUtil.getMedicineMap(nativeList);
        	Map<String,MedicineRelationDO> resultList = RecipeUtil.medicineRelation(recipe, nativeMap);
        	if (resultList != null && !resultList.isEmpty()) {
        		return new ResponseModel().attr("relationList", resultList.values());
        	}
        }
        //检测配伍禁忌
        if(CommonUtil.isTrue(configMap.get(Constants.ConfigKey.CHECK_TABOO))){
        	if (recipe.getPreCheckState() == null||recipe.getPreCheckState() == 1) {
        		Map<String, String> tabooUnionMap = medicineTabooService.getMedicineTabooUnionMap();
        		List<RecipeDO> list = new ArrayList<>();
        		list.add(recipe);
        		List<SysLookup> result = RecipeUtil.getMedicineTaboo(list, tabooUnionMap);
        		if (result.size() > 0) {
        			return new ResponseModel().attr(MEDICINE_TABOO_KEY, result);
        		}
        	}
        }
        //补全其他参数
        recipe.setSettleCompanyId(checkRecipe.getSettleCompanyId());
        recipe.setPriceTypeId(checkRecipe.getPriceTypeId());
        recipe.setDiscount(checkRecipe.getDiscount());
        recipe.setHospitalId(checkRecipe.getHospitalId());
        recipe.setCreatorId(JwtUserTool.getId());
        recipe.setUseHisMoney(checkRecipe.getUseHisMoney()==null?false:checkRecipe.getUseHisMoney());
        String defaultTime = sysPropertyUtil.getProperty(TRANSFER_DEFAULT_TIME,SystemConstant.IS_NO);
        if(SystemConstant.IS_YES.equals(defaultTime)){
        	recipe.setDealDate(checkRecipe.getDealDate());
        }else{
        	recipe.setDealDate(new Date());
        }
        recipe.setRecipeSource(checkRecipe.getRecipeSource());
        recipe.setShippingState(Constants.STATE_CREATE);
        List<PriceTempletDetailDTO> medicinePriceList = null;
        List<SpecialDiscount> allDiscountList = null;

        //是否开启接方补录药品信息
        if(CommonUtil.isTrue(configMap.get(Constants.ConfigKey.RELATION_MEDICINE))){
        	recipe.setInputEmployee(recipe.getCreatorId());
        	recipe.setInputState(1);
        	recipe.setInputTime(new Date());
        	List medicineIds = new ArrayList<>();
        	for (RecipeMedicine rm : recipe.getRecipeMedicineList()) {
        		rm.setTransferMedicineId(rm.getId());
        		rm.setId(null);
        		medicineIds.add(rm.getMedicineId());
        	}
        	if (!recipe.getUseHisMoney()) {
        		PriceTempletDetailDTO pd=new PriceTempletDetailDTO();
        		pd.setTempletId(recipe.getPriceTypeId());
        		pd.setMedicineIdList(medicineIds);
        		pd.setPriceStart(recipe.getDealDate());
        		medicinePriceList=priceTempletService.listPriceByTemIdsOrMedIds(pd);
        		SpecialDiscount sd = new SpecialDiscount();
        		sd.setSettleCompanyId(recipe.getSettleCompanyId());
        		allDiscountList = settleCompanyService.getAllDiscount(sd);
        	}
        	RecipeUtil.setReceiveRecipe(medicinePriceList, allDiscountList, recipe);
        }
        try {
            recipeService.insertRecipe(recipe);
        } catch (Exception e) {
        	log.error("receive recipe Exception",e);
            return new ResponseModel().fail();
        }
        return new ResponseModel(Constant.OPRATION_SUCCESS);
    }

    @ApiOperation(tags = TAGS_TRANSFER_RECIPE, value = "批量接收订单", response = TransferRecipeDO.class)
    @RequestMapping(value = "/batchReceiveTransferRecipe", method = RequestMethod.POST)
    public Object batchReceiveTransferRecipe(@RequestBody RecipePara recipePara) {
        List<TransferRecipeDTO> transferRecipeDTOs = recipePara.getTransferRecipeList();
        List<RecipeDO> list = transferRecipeService.listReceiveTransferRecipe(transferRecipeDTOs);
        if (CollectionUtils.isEmpty(list)) {
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, "接收失败，订单已被其他人接收");
        }
        List<RecipeMedicine> transferMedicineList = null;
        Map<Integer,Integer> falseMatchMap = null;
        Date nowTime=new Date();
        Date minDealDate=nowTime;
        String defaultTime = sysPropertyUtil.getProperty(TRANSFER_DEFAULT_TIME,SystemConstant.IS_NO);
        if (list != null && list.size() > 0) {
        	
            if (recipePara.getOrderType() != null && recipePara.getOrderType() == Constants.STATE_ZERO) {//院内
            	transferMedicineList = new ArrayList<>();
            	List<RecipeMedicine> hospitalMedicineList = null;
            	List<TransferRecipeDTO> updateTransferRecipeList = new ArrayList<>();
            	Map<String, MedicineDO> nativeMap1 = MedicineUtil.getMap(medicineService.listMedicine(null));
            	for (TransferRecipeDTO trd : transferRecipeDTOs) {
            		List<TransferRecipeDTO> medicineList = new ArrayList<>();
            		medicineList.add(trd);
            		hospitalMedicineList = transferRecipeService.findMedicineListByBillIds(medicineList);
            		List<RecipeMedicine> resultList = RecipeUtil.contrastData(nativeMap1, hospitalMedicineList);
                    if (CollectionUtils.isNotEmpty(resultList)) {
                    	trd.setIfReceive(1);
                    	trd.setMedicineMatch(false);
                    	updateTransferRecipeList.add(trd);
            		} else {
            			transferMedicineList.addAll(hospitalMedicineList);
            		}
            	}
            	if (CollectionUtils.isNotEmpty(updateTransferRecipeList)) {//有未完全匹配的订单
            		transferRecipeService.updateMedicineMatchById(updateTransferRecipeList);
            		falseMatchMap = updateTransferRecipeList.stream().collect(Collectors.toMap(TransferRecipeDTO::getId, TransferRecipeDTO::getId));
            	}
            } else {//非院内
            	transferMedicineList = transferRecipeService.findMedicineListByBillIds(transferRecipeDTOs);
            }
            List<RecipeMedicine> tempList=null;
            Integer userId = JwtUserTool.getId();
            for (RecipeDO r : list) {
            	//不启用医院时间,设置当前时间为受理时间
            	if (SystemConstant.IS_NO.equals(defaultTime)){
            		r.setDealDate(nowTime);
            	}else{
            		if(r.getDealDate().before(minDealDate)){
            			minDealDate=r.getDealDate();
            		}
            	}
                r.setCreatorId(userId);
                tempList=transferMedicineList.stream()
                		.filter(rm -> (r.getBillId() + "_" + r.getHospitalCode())
                				.equals(rm.getBillId() + "_" + rm.getHospitalCode()))
                		.collect(Collectors.toList());
                r.setRecipeMedicineList(tempList);
            }
        }
        Map<String,Object> configMap =sysConfigService.listConfig().stream()
                .collect(Collectors.toMap(SysConfig::getName,SysConfig::getValue));
        List<SysLookup> tabooResult = null;
        List<PriceTempletDetailDTO> medicinePriceList = null  ;
        List<SpecialDiscount> allDiscountList = null  ;
        if (recipePara.getOrderType() == null || recipePara.getOrderType() != 0) {//非院内
	        //关联药品
	        if(CollectionUtils.isNotEmpty(recipePara.getRelationList())){
	        	Map<String,String> map = new HashMap<>();
	        	for (MedicineRelationDO mr : recipePara.getRelationList()) {
	        		if (mr.getMedicineType()==null || mr.getMedicineType()== Constants.STATE_ZERO) {
	        			mr.setHospitalId(Constants.STATE_ZERO);
	        		}
				}
	        	List<MedicineRelationDO> insertRelationList = new ArrayList<>();
	        	String key = "";
	        	for (MedicineRelationDO e : recipePara.getRelationList()) {
	        		key = e.getHospitalMedicineName()+e.getNativeMedicineId().toString() + e.getHospitalId().toString();
	        		if (map.get(key) == null) {
	        			map.put(key, "");
	        			insertRelationList.add(e);
	        		}
	        	}
	        	if (CollectionUtils.isNotEmpty(insertRelationList)) {
	        		medicineRelationService.insertMedicineRelation(insertRelationList);
	        	}
	        }
	        /**
	         * 检测药品关联
	         */
	        if(CommonUtil.isTrue(configMap.get(Constants.ConfigKey.RELATION_MEDICINE))){
	        	Map<String,MedicineRelationDO> resultList =new HashMap<String,MedicineRelationDO>();
	        	Map<String,MedicineRelationDO> medicineList = null;
	        	//获取本地全部关联药品
	        	List<MedicineRelationDO> nativeList = medicineRelationService.listMedicineRelationAll();
	        	Map<String, MedicineRelationDO> nativeMap = RecipeUtil.getMedicineMap(nativeList);
	        	for (RecipeDO r : list) {
	        		//检测药品关联
	        		r.getRecipeMedicineList().forEach(e -> e.setName_medicineName(e.getMedicineName()));
	        		medicineList = RecipeUtil.medicineRelation(r, nativeMap);
	        		if (medicineList != null && !medicineList.isEmpty()) {
	        			resultList.putAll(medicineList);
	        		}
	        	}
	        	if (resultList != null && !resultList.isEmpty()) {
	        		 List<MedicineRelationDO> mrdList=new ArrayList<MedicineRelationDO>(resultList.values());
	        		 mrdList.sort(Comparator.comparing(MedicineRelationDO::getHospitalId));
	        		return new ResponseModel().attr("relationList", mrdList);
	        	}
	        }
        	//检测配伍禁忌
            if(CommonUtil.isTrue(configMap.get(Constants.ConfigKey.CHECK_TABOO))){
            	Map<String, String> tabooUnionMap = medicineTabooService.getMedicineTabooUnionMap();
            	tabooResult = RecipeUtil.getMedicineTaboo(list, tabooUnionMap);
            	if (list.size() == 0 && tabooResult.size() > 0) {
            		return new ResponseModel().attr(MEDICINE_TABOO_KEY, tabooResult);
            	}
            }
        }
        if(CommonUtil.isTrue(configMap.get(Constants.ConfigKey.RELATION_MEDICINE))){
        	//获取药品价格
        	PriceTempletDetailDTO pd=new PriceTempletDetailDTO();
    		List<Integer> templetIdList=list.stream().map(e -> e.getPriceTypeId()).collect(Collectors.toList());
    		pd.setTempletIdList(templetIdList);
    		pd.setPriceStart(minDealDate);
    		medicinePriceList=priceTempletService.listPriceByTemIdsOrMedIds(pd);
        	//获取特殊扣率
            allDiscountList = settleCompanyService.getAllDiscount(new SpecialDiscount());
        }
        Integer userId=JwtUserTool.getId();
        List<PriceTempletDetailDTO> priceList=null;
        String hospitalType = sysPropertyUtil.getProperty(Constants.HOSPITAL_TYPE, "1");
        List<RecipeDO> insertRecipeList = new ArrayList<>();
        for(RecipeDO r:list){
        	r.setCreatorId(userId);
        	r.setShippingState(Constants.STATE_CREATE);
        	r.setTakeTime(sdf.format(RecipeUtil.getCalendar()));
        	if(CommonUtil.isTrue(configMap.get(Constants.ConfigKey.RELATION_MEDICINE))){
        		r.setInputEmployee(r.getCreatorId());
    	        r.setInputState(1);
    	        r.setInputTime(nowTime);
        	}
        	if (!"0".equals(hospitalType)) {
        		if(1 == r.getBoilType()){
            		r.setBoilTime(Integer.valueOf(configMap.get(Constants.ConfigKey.BOIL_TIME).toString()));
            		r.setSoakTime(Integer.valueOf(configMap.get(Constants.ConfigKey.SOAK_TIME).toString()));
            	}
            	if(r.getPackagePaste()==null||r.getPackagePaste()==0){
            		r.setPackagePaste(Integer.valueOf(configMap.get(Constants.ConfigKey.PACKAGE_PASTE).toString()));
            	}
            	if(r.getCarryId()!=null&&r.getCarryId()==1){
            		r.setBusinessType(Integer.valueOf(configMap.get(Constants.ConfigKey.SF_EXPRESS_TYPE).toString()));
            	}
        	}else{
        		r.setUseHisMoney(true);
        	}
        	if(CommonUtil.isTrue(configMap.get(Constants.ConfigKey.RELATION_MEDICINE))){
        		if (CollectionUtils.isNotEmpty(medicinePriceList)) {
        			priceList=medicinePriceList.stream()
        					.filter(e -> e.getTempletId() != null)
        					.filter(e -> e.getTempletId().equals(r.getPriceTypeId()))
        					.collect(Collectors.toList());
        		}else{
        			priceList=null;
        		}
        		RecipeUtil.setReceiveRecipe(priceList, allDiscountList, r);
        	}else{
        		r.setRecipeMedicineList(null);
        	}
        	if ("0".equals(hospitalType)) {//院内
        		if (r.getPackagePaste() == null) {
        			r.setPackagePaste(Constants.TYPE_TWO);
        		}
        		r.setRecipeSerial(RecipeUtil.leftZero(r.getId()));
        		r.setTotalPackagePaste(r.getQuantity() * r.getPackagePaste());
        		if (falseMatchMap == null || falseMatchMap.get(r.getId()) == null) {//判断是否全匹配
            		insertRecipeList.add(r);
            	}
        	} else {
        		insertRecipeList.add(r);
        	}
        }
    	if (hospitalType.equals("0")) {
            for (TransferRecipeDTO tr : transferRecipeDTOs) {
            	if (tr.getTakeTime() == null) {
            		tr.setTakeTime(RecipeUtil.getCalendar());
            	}
            	if (tr.getCarryId() == null || tr.getCarryId() == 0) {
            		tr.setCarryId(3);
            	}
            	tr.setReceiverId(JwtUserTool.getId());
            }
            transferRecipeService.insertTransferRecipeTmp(transferRecipeDTOs);
    	}
        List<TransferRecipeDO> resultData=null;
        try {
            if (CollectionUtils.isNotEmpty(insertRecipeList)) {
            	resultData=recipeService.batchInsertRecipe(insertRecipeList);
            }
        } catch (Exception e) {
        	log.error("bactch receive recipe Exception",e);
            return new ResponseModel().fail();
        }
        if (tabooResult != null && tabooResult.size() > 0) {
            return new ResponseModel(resultData).attr(MEDICINE_TABOO_KEY, tabooResult);
        }
        if(falseMatchMap!=null&&falseMatchMap.size()>0){
        	if(resultData==null){
        		resultData=new ArrayList<>();
        	}
        	TransferRecipeDO tr=null;
        	for (Integer key : falseMatchMap.keySet()) {  
        		tr=new TransferRecipeDO();
        		tr.setId(key);
        		resultData.add(tr);
        	} 
        }
        return new ResponseModel(resultData).attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
    }
    
    @ApiOperation(tags = TAGS_TRANSFER_RECIPE, value = "打印订单")
    @ApiImplicitParam(value = "打印订单参数", dataType = "RecipeReq")
    @RequestMapping(value = "/printTansferRecipe", method = RequestMethod.POST)
    public Object printTansferRecipe(@RequestBody RecipeReq recipeReq) {
    	List<PrintPageObj> pageObjs=new ArrayList<>();
    	String[] arr=recipeReq.getGroupPrint().split("_");
    	Map<String,Object> map=new HashMap<>();
    	List<String> printTypeList=new ArrayList<String>(Arrays.asList(arr));
    	map.put("typeIds",printTypeList);
    	Map<String,PrintPage> pageMap=printService.listPrintPageItem(map);
        List<RecipePrintObj> printPageObjList = printService.listTransferRecipePrintMessages(recipeReq);
        Company company=sysConfigService.listCompany();
    	Date now=new Date();
    	String operator=JwtUserTool.getUserName();
    	for (RecipePrintObj recipePrintObj : printPageObjList) {
    		recipePrintObj.setPrintDate(now);
    		recipePrintObj.setOperator(operator);
    		RecipePrintUtil.setCompanyInfo(company, recipePrintObj);
    	}	
        //查询TransferRecipeMedicine列
        List<TransferMedicineObj> listTransferRecipeMedicine = null;
        if(RecipePrintUtil.isFindRecipeMedicine(printTypeList)){
        	List<Long> billIdList =printPageObjList.stream().map(RecipePrintObj::getBillId).collect(Collectors.toList());
        	listTransferRecipeMedicine=printService.listTransferRecipeMedicinePrintMessages(billIdList);
        	int size=listTransferRecipeMedicine.size();
        	TransferMedicineObj transferMedicineObj=null;
        	for (RecipePrintObj recipePrintObj : printPageObjList) {
        		if (recipePrintObj.getTransferMedicineList() == null) {
        			recipePrintObj.setTransferMedicineList(new ArrayList<>());
        		}
        		for (int i=0; i<size;i++) {
        			transferMedicineObj=listTransferRecipeMedicine.get(i);
        			if (recipePrintObj.getHospitalCode().equals(transferMedicineObj.getHospitalCode())
        					&& recipePrintObj.getBillId().equals(transferMedicineObj.getBillId())) {
        				recipePrintObj.getTransferMedicineList().add(transferMedicineObj);
        				listTransferRecipeMedicine.remove(i);
        				size--;
        				i--;
        			}
        		}
        	}
        }
        /**
         * 遍历打印类型,添加打印对象
         */
        List<Integer> recordIds=new ArrayList<>();
        printTypeList.forEach(
        		e -> 
        		pageObjs.addAll(
            			RecipePrintUtil.setRecipePrintList(printPageObjList, 
            					e.toString(), pageMap,recipeReq.getTagNum(),recordIds))
        		);
        if (CollectionUtils.isNotEmpty(recordIds)) {
        	transferRecipeService.updateTransferRecipePrintState(recordIds);
        }
        return pageObjs;
    }
    
    
	@ApiOperation(value = "订单列表-导入excel", notes = "传入file = excel,hospitalId=医院id,boilType=煎药方式(1.代煎药,2.自煎,3,膏方代煎,4.膏方自煎)"
			+ "hospitalCode=医院编号，carryId=配送方式", response = Object.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "file", value = "导入", required = true, dataType = "MultipartFile") })
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public Object uploadFile(@RequestParam(value = "file") MultipartFile file,
			@RequestParam(value = "hospitalId") Integer hospitalId, @RequestParam(value = "boilType") Integer boilType,
			@RequestParam(value = "hospitalCode") String hospitalCode,
			@RequestParam(value = "carryId") Integer carryId) throws UnsupportedEncodingException {
		Integer medicineType = 0;
		TransferRecipeDO param = new TransferRecipeDO();
		param.setHospitalId(hospitalId);
		param.setBoilType(boilType);
		param.setHospitalCode(hospitalCode);
		// 获取系统配置
		List<SysConfig> sysList = sysConfigService.listConfig();
		Map<String, String> configMap = new HashMap<>();
		Map<String, Integer> zdColumnIndexMap = null;
		Map<Integer, String> recipeIndexFieldNameMap = new HashMap<>();
		Map<Integer, String> medicineIndexFieldNameMap = new HashMap<>();
		Map<String, String> btzdMap = null;
		for (SysConfig sysConfig : sysList) {
			configMap.put(sysConfig.getName(), sysConfig.getValue());
		}
		// 获取医院药方
		List<Prescription> prescriptionList = prescriptionService.listPrescriptionByHospital(hospitalId);
		// 获取包装类型
		//List<PackingType> packingTypeList = recipeService.listPackingType();
		List<BtRelationZd> btList = null;
		List<TransferMedicine> transferRecipeList = null;
		Workbook wb = null;
		if (file != null) {
			String fileName = file.getOriginalFilename();
			try {
				wb = ImportExcelUtil.getWorkbook(fileName, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (wb == null) {
				return null;
			}
			Sheet sheet = wb.getSheetAt(0);
			List<TransferRecipeDO> recipeList = new ArrayList<>();
			TransferRecipeDO recipe = null;
			btList = recipeService.listBtRelationZdByHospitalId(hospitalId);// 获取excel表头对应的字段名称
			if (CollectionUtils.isEmpty(btList)) {//如果医院配置为空，获取默认配置
				btList = recipeService.listBtRelationZdByHospitalId(Constants.STATE_ZERO);
			}
			Map<String, Object> map = null;
			Map<String, Object> medicineMap = null;
			for (Row row : sheet) {
				recipe = new TransferRecipeDO();
				recipe.initMedicine();
				if (row.getRowNum() == 0) {
					if (btList == null || btList.size() == 0) {
						return new ResponseModel().attr(ResponseModel.KEY_ERROR, "该医院没有配置导入格式，请联系软件开发商协助操作！");
					}
					// 医院对应配置放入map key = header  value = field_name
					btzdMap = ImportExcelUtil.getBtzdMap(btList);
					// 获取字段名称对应的excel里的下标
					zdColumnIndexMap = ImportExcelUtil.zdColumnIndexMap(row, btList, recipeIndexFieldNameMap,
							medicineIndexFieldNameMap);// field_name 跟表头对比获取下标

					if (zdColumnIndexMap.size() < btzdMap.size()) {
						return new ResponseModel().attr(ResponseModel.KEY_ERROR,
								"excel信息不全，必须包含" + btzdMap.values().toString() + "，请仔细核对!");
					}
					if (zdColumnIndexMap.size() > btzdMap.size()) {
						return new ResponseModel().attr(ResponseModel.KEY_ERROR, "excel信息重复，请仔细核对!");
					}
					continue;
				} else {
					
					if (ImportExcelUtil.ifNameAndMedicineName(row, zdColumnIndexMap)) {
						continue;
					}
					/**
					 * 循环遍历获取每列的值
					 */
					map = new HashMap<>();
					medicineMap = new HashMap<>();
					for (Cell cell : row) {
						map.put("rowth", row.getRowNum());
						map.put("colth", cell.getColumnIndex());
						if (recipeIndexFieldNameMap.get(cell.getColumnIndex()) != null) {// 主表
							map.put(recipeIndexFieldNameMap.get(cell.getColumnIndex()),
									ImportExcelUtil.getCellStringValue(cell).trim());
						}
						// 明细  medicine
						if (medicineIndexFieldNameMap.get(cell.getColumnIndex()) != null) {
							medicineMap.put(medicineIndexFieldNameMap.get(cell.getColumnIndex()),
									ImportExcelUtil.getCellStringValue(cell).trim());
						}
					}
					map.put("medicine", medicineMap);
					// map转为java类
					recipeList.add(JSON.parseObject(JSONArray.toJSONString(map), TransferRecipeDO.class));
				}
			}
			/**
			 * 判断excel必须列是否为空
			 */
			String resultStr = ImportExcelUtil.ifExcelData(recipeList, prescriptionList, configMap, param);
			if (resultStr != null) {
				return new ResponseModel().attr(ResponseModel.KEY_ERROR, resultStr);
			}

			// 解析recipeList
			TransferRecipeDO recipeAndMedicine = null;
			TransferRecipeDO recipe2 = null;
			HashMap<String, TransferRecipeDO> recipeMap = new HashMap<String, TransferRecipeDO>();
			Long billId = 0L;
			if (recipeList != null && recipeList.size() > 0) {
				TransferMedicine recipeMedicine = null;
				for (int i = 0; i < recipeList.size(); i++) {
					recipeMedicine = recipeList.get(i).getMedicine();
					if (medicineType == 2 && configMap.get(Constants.REFORM_MEDICINE_NAME) != null
							&& "Y".equals(configMap.get(Constants.REFORM_MEDICINE_NAME))) {
						recipeMedicine.setMedicineName(
								recipeMedicine.getMedicineName() + recipeMedicine.getStandardWeight().toString() + 'g');
					}
				}
				billId = Long.parseLong(recipeService.generateBillIdIndex());
			}
			// 获取库位号
			/*List<MedicineRelationDO> mnemonicCodeList = medicineRelationService.getMnemonicCode();
			MedicineRelationDO relation = null;*/
			/**
			 * 设置配送方式
			 */
			for (int i = 0; i < recipeList.size(); i++) {
				recipeAndMedicine = recipeList.get(i);
				if ("1".equals(configMap.get(Constants.DR_RECIPE_PSFS_BZ))) {
					int result = ImportExcelUtil.checkRecipient(recipeAndMedicine);
					if (result == 1 || result == 2) {
						return new ResponseModel().attr(ResponseModel.KEY_ERROR,
								"第" + (recipeAndMedicine.getRowth() + 1) + "行联系人的[电话、区、地址]信息不全，请补全！");
					} else if (result == 3) {
						if (!ImportExcelUtil.checkRegion(recipeAndMedicine, Constants.rangeList)) {
							return new ResponseModel().attr(ResponseModel.KEY_ERROR,
									"第" + (recipeAndMedicine.getRowth() + 1) + "行联系人的[区]信息不存在于该系统，请联系软件开发商！");
						}
						recipeAndMedicine.setCarryId(1);// 顺丰
					} else if (result == 0) {
						recipeAndMedicine.setCarryId(12);// 厂车
					}
				} // 根据“配送方式列”来判断配送方式-------直接指定
				/*else if ("2".equals(configMap.get(Constants.DR_RECIPE_PSFS_BZ))) {
					if ("Y".equals(configMap.get(Constants.SHOW_CHOOSE_CARRY)) && carryId != -1) {
						if (carryId != 2) {
							int result = ImportExcelUtil.checkRecipient(recipeAndMedicine);
							if (result == 1 || result == 2 || result == 0) {
								return new ResponseModel().attr(ResponseModel.KEY_ERROR,
										"第" + (recipeAndMedicine.getRowth() + 1) + "行联系人的[电话、区、地址]信息不全，请补全！");
							} else if (result == 3) {
								if (recipeAndMedicine.getProvince() != null
										&& !"".equals(recipeAndMedicine.getProvince())) {
									if (!ImportExcelUtil.checkAllRegion(recipeAndMedicine, Constants.cityList)) {
										return new ResponseModel().attr(ResponseModel.KEY_ERROR,
												"第" + (recipeAndMedicine.getRowth() + 1) + "行联系人的[区]与[省]或[市]对应不上！");
									}
								} else {
									if (!ImportExcelUtil.checkRegion(recipeAndMedicine, Constants.rangeList)) {
										return new ResponseModel().attr(ResponseModel.KEY_ERROR,
												"第" + (recipeAndMedicine.getRowth() + 1)
														+ "行联系人的[区]与[省]或[市]对应不上，请联系软件开发商！");
									}
								}
								recipeAndMedicine.setCarryId(carryId);
							} else {
								recipeAndMedicine.setCarryId(12);// 厂车
							}
						} else {
							recipeAndMedicine.setCarryId(12);// 厂车
						}
					}
				}*/ else if ("2".equals(configMap.get(Constants.DR_RECIPE_PSFS_BZ))) {
					if (recipeAndMedicine.getPsfs() != null && "代煎快递".equals(recipeAndMedicine.getPsfs())) {
						recipeAndMedicine.setCarryId(1);// 快递----虹桥只需要分出是否要快递就好，不管哪家快递，具体哪家快递由医院那边统一安排;现在虹桥的配送方式由快递都改为顺丰
					}
					if (recipeAndMedicine.getPsfs() != null && recipeAndMedicine.getPsfs().indexOf("送") > -1) {
						recipeAndMedicine.setCarryId(1);// 快递----虹桥只需要分出是否要快递就好，不管哪家快递，具体哪家快递由医院那边统一安排;现在虹桥的配送方式由快递都改为顺丰
					}
					if(recipeAndMedicine.getPsfs()!=null&&recipeAndMedicine.getPsfs().indexOf("2")>-1){
						recipeAndMedicine.setCarryId(2);//快递----上海邮政;
					}
					if (recipeAndMedicine.getPsfs() != null && recipeAndMedicine.getPsfs().indexOf("1") > -1) {
						int result = ImportExcelUtil.checkRecipient(recipeAndMedicine);
						if (result == 1 || result == 2 || result == 0) {
							return new ResponseModel().attr(ResponseModel.KEY_ERROR,
									"第" + (recipeAndMedicine.getRowth() + 1) + "行联系人的[电话、区、地址]信息不全，请补全！");
						} else if (result == 3) {
							if (recipeAndMedicine.getProvince() != null
									&& !"".equals(recipeAndMedicine.getProvince())) {
								if (!ImportExcelUtil.checkAllRegion(recipeAndMedicine, Constants.cityList)) {
									return new ResponseModel().attr(ResponseModel.KEY_ERROR,
											"第" + (recipeAndMedicine.getRowth() + 1) + "行联系人的[区]与[省]或[市]对应不上！");
								}
							} else {
								if (!ImportExcelUtil.checkRegion(recipeAndMedicine, Constants.rangeList)) {
									return new ResponseModel().attr(ResponseModel.KEY_ERROR,
											"第" + (recipeAndMedicine.getRowth() + 1) + "行联系人的[区]信息不存在于该系统，请联系软件开发商！");
								}
							}
							recipeAndMedicine.setCarryId(1);// 曙光医院配送方式1就表示顺丰，现在虹桥的配送方式由快递都改为顺丰
						} else {
							recipeAndMedicine.setCarryId(12);// 厂车
						}
					}
				}
				// 设置货号、药名、特殊煎药方式、单位
				/*boolean isBoilType = false;
				String specialBoilType = "";
				for (int j = 0; j < mnemonicCodeList.size(); j++) {
					relation = mnemonicCodeList.get(j);
					if (recipeAndMedicine.getMedicine().getMedicineName().trim()
							.equals(relation.getHospitalMedicineName())) {
						if (relation.getHospitalId() != null && relation.getHospitalId().intValue() > 0
								&& hospitalId != relation.getHospitalId().intValue()) {
							continue;
						}
						*//**
						 * 现在库位号只有 getMnemonicCode 没有 getWarehousePlaceZj=自煎
						 *//*
						recipeAndMedicine.getMedicine().setMnemonicCode(relation.getMnemonicCode());
						recipeAndMedicine.setMedicineName2(relation.getMedicineName());
						if (StringUtils.isNotBlank(relation.getCommonCode())) {
							recipeAndMedicine.setCommonCode(relation.getCommonCode());
						}
						
						if (!StringUtils.contains(recipeAndMedicine.getMedicine().getSpecialBoilType(), relation.getSpecialBoilType())) {
							isBoilType = StringUtils.isNotBlank(recipeAndMedicine.getMedicine().getSpecialBoilType());
							specialBoilType = (StringUtils.isNotBlank(relation.getSpecialBoilType())?"-":"");
							if (recipeAndMedicine.getMedicine().getSpecialBoilType() == null) {
								recipeAndMedicine.getMedicine().setSpecialBoilType("");
							}
							recipeAndMedicine.getMedicine().setSpecialBoilType(
									relation.getSpecialBoilType()+
									(isBoilType ? (specialBoilType+recipeAndMedicine.getMedicine().getSpecialBoilType()):""));
						}
						
						if ("公斤".equals(relation.getStandard().trim()) || "".equals(relation.getStandard().trim())
								|| "克".equals(relation.getStandard().trim())) {
							recipeAndMedicine.getMedicine().setUnit("g");
						} else {
							recipeAndMedicine.getMedicine().setUnit(relation.getStandard());
						}
						recipeAndMedicine.getMedicine().setUnitType(relation.getUnitType());

						recipeAndMedicine.setRowth(-1);
						recipeAndMedicine.setColth(-1);
					}
				}*/
				// 判断是否是同一个处方
				if (recipeMap.get(recipeAndMedicine.getOnlyOne()) == null) {
					recipe2 = new TransferRecipeDO();
					if (boilType != null) {
						recipe2.setBoilType(boilType);
					} else {
						if (recipeAndMedicine.getBoilType() != null) {
							recipe2.setBoilType(recipeAndMedicine.getBoilType());
						}
					}
					recipe2.setRecipeCode(recipeAndMedicine.getRecipeCode());
					recipe2.setRecipeSerial(recipeAndMedicine.getRecipeSerial());
					if ("Y".equals(configMap.get(Constants.IF_ADD_WUZI_WHILE_BQ_IS_NULL))) {
						if (null == recipeAndMedicine.getInpatientArea()
								|| "".equals(recipeAndMedicine.getInpatientArea())) {
							recipe2.setInpatientArea("无");
						} else if ("送".equals(recipeAndMedicine.getInpatientArea())) {
							recipe2.setInpatientArea("无送");
						}
					}
					recipe2.setInpatientArea(recipeAndMedicine.getInpatientArea());
					recipe2.setBedNumber(recipeAndMedicine.getBedNumber());
					recipe2.setRecipientName(recipeAndMedicine.getRecipientName());
					recipe2.setTradeTime(new Date());
					recipe2.setDepartment(recipeAndMedicine.getDepartment());

					recipe2.setRecipientTel(recipeAndMedicine.getRecipientTel());
					recipe2.setRecipientAddress(recipeAndMedicine.getRecipientAddress());
					recipe2.setRegion(recipeAndMedicine.getRegion());
					recipe2.setProvince(recipeAndMedicine.getProvince());
					recipe2.setCity(recipeAndMedicine.getCity());
					recipe2.setRemark(recipeAndMedicine.getRemark());
					recipe2.setPackagePaste(recipeAndMedicine.getPackagePaste());
					if (carryId != null) {
						recipe2.setCarryId(carryId);
					} else {
						if (recipeAndMedicine.getCarryId() != null) {
							recipe2.setCarryId(recipeAndMedicine.getCarryId());
						} else {
							recipe2.setCarryId(3);
						}
					}
					recipe2.setInpatientArea(recipeAndMedicine.getInpatientArea());
					recipe2.setPackType(recipeAndMedicine.getPackType());
					recipe2.setRecipeBh(recipeAndMedicine.getRecipeBh());
					recipe2.setUsage(recipeAndMedicine.getUsage());
					recipe2.setBillId(billId + recipeMap.values().size());
					recipe2.setPackingName(recipeAndMedicine.getPackingName());
					recipe2.setDoctorName(recipeAndMedicine.getDoctorName());
					if (recipeAndMedicine.getMedicine().getMoney() != null) {
						recipe2.setMoney(recipeAndMedicine.getMedicine().getMoney());
					}
					if ("Y".equals(configMap.get(Constants.EXCEL_MONEY))
							&& recipe2.getMedicine().getMoney() != null
							&& recipe2.getMedicine().getMoney().compareTo(BigDecimal.ZERO) == 1) {
						recipe2.setCustomOneText("-1");
					}
					recipe2.setTotalWeight(BigDecimal.ZERO);
					recipeAndMedicine.getMedicine().setHospitalCode(recipeAndMedicine.getHospitalCode());
					recipeAndMedicine.getMedicine().setBillId(recipe2.getBillId());
					recipeAndMedicine.getMedicine().setTotalWeight(recipeAndMedicine.getTotalWeight());
					recipeAndMedicine.getMedicine().setQuantity(recipeAndMedicine.getQuantity());
					recipe2.setHospitalCode(hospitalCode);
					transferRecipeList = new ArrayList<>();
					transferRecipeList.add(recipeAndMedicine.getMedicine());
					recipe2.setTransferMedicineList(transferRecipeList);
					recipeMap.put(recipeAndMedicine.getOnlyOne(), recipe2);
				} else {
					/**
					 * recipeAndMedicine = 从excel读取的每条数据，里面包含transferMedicine
					 */
					recipe2 = recipeMap.get(recipeAndMedicine.getOnlyOne());
					recipeAndMedicine.getMedicine().setHospitalCode(recipeAndMedicine.getHospitalCode());
					recipeAndMedicine.getMedicine().setBillId(recipe2.getBillId());
					recipeAndMedicine.getMedicine().setTotalWeight(recipeAndMedicine.getTotalWeight());
					recipeAndMedicine.getMedicine().setQuantity(recipeAndMedicine.getQuantity());
					if (recipe2.getMoney() != null && recipeAndMedicine.getMedicine().getMoney() != null) {
						recipe2.setMoney(recipe2.getMoney().add(recipeAndMedicine.getMedicine().getMoney()));
					}
					recipe2.getTransferMedicineList().add(recipeAndMedicine.getMedicine());
				}
			}
			List<TransferRecipeDO> recipeList1 = new ArrayList<>(recipeMap.values());
			// 根据贴数在配方中出现的频率来确定处方的最终贴数,并计算药品的总重量和单贴量
			if (recipeList1 != null && recipeList1.size() > 0) {
				TransferRecipeDO re = null;
//				Map<Integer, Integer> quantityMap = null;
				TransferRecipeDO rmList = null;
				TransferMedicine rm = null;
//				int maxCS = 0;
				int quantity = 0;
//				Iterator<Map.Entry<Integer, Integer>> iter = null;
//				Map.Entry<Integer, Integer> entry = null;
				Map<Integer, List<TransferMedicine>> employeesByCity = null;
				for (int i = 0; i < recipeList1.size(); i++) {
//					maxCS = 0;
					quantity = 0;
					re = recipeList1.get(i);
//					quantityMap = new HashMap<Integer, Integer>();
					rmList = recipeList1.get(i);
					if (StringUtils.isBlank(recipeList1.get(i).getInpatientArea())
							&& StringUtils.isBlank(recipeList1.get(i).getBedNumber())) {
						recipeList1.get(i).setBelong(Constants.STATE_ONE);
					} else {
						recipeList1.get(i).setBelong(Constants.TYPE_TWO);
					}
				/*	//获得“贴数----出现次数”映射
					for(int j=0;j<rmList.getTransferMedicineList().size();j++){
						rm = rmList.getTransferMedicineList().get(j);
						if(quantityMap.get(rm.getQuantity().intValue())==null){
							quantityMap.put(rm.getQuantity().intValue(), 1);
						}else{
							quantityMap.put(rm.getQuantity().intValue(), quantityMap.get(rm.getQuantity().intValue())+1);
						}
					}
					//获得处方中出现频率最大的贴数
					iter = quantityMap.entrySet().iterator();
					while (iter.hasNext()) {
						entry = (Map.Entry<Integer, Integer>) iter.next();
						if(entry.getValue().intValue() > maxCS){
							maxCS=entry.getValue().intValue();
						}
					}
					//获得贴数
					iter = quantityMap.entrySet().iterator();
					while (iter.hasNext()) {
						entry = (Map.Entry<Integer, Integer>) iter.next();
						if(entry.getValue().intValue()==maxCS){
							quantity=entry.getKey().intValue();
							break;
						}
					}*/
					
					//分组lisi
					employeesByCity =
							 rmList.getTransferMedicineList().stream().collect( Collectors.groupingBy(TransferMedicine::getQuantity));
					//获取最大的list里的帖数
					for (Map.Entry<Integer, List<TransferMedicine>> entry1 :  employeesByCity.entrySet()) {
						if (entry1.getValue().size() >= quantity) {
							quantity = entry1.getValue().get(0).getQuantity();
						}
					}
					for (int j = 0; j < rmList.getTransferMedicineList().size(); j++) {
						rm = rmList.getTransferMedicineList().get(j);
						rm.setQuantity(quantity);
						re.setQuantity(quantity);
						// 计算总量和单贴量
						ImportExcelUtil.calTotalWeight(rm, configMap, medicineType);
						// 累加处方总量
						re.setTotalWeight(re.getTotalWeight().add(rm.getTotalWeight()));
					}
					// 设置贴数
					re.setQuantity(quantity);
					if (re.getPackagePaste() != null) {
						recipeList1.get(i).setTotalPackagePaste(re.getQuantity()*re.getPackagePaste());
					}
				}
			}
			/**
			 * 新增 主表 and 明细表
			 */
			Integer result = transferRecipeService.insertTransferRecipe(recipeList1);
			if (result > 0) {
				return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
			}
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}
	
	@ApiOperation( value = "接方列表", notes = "", response = TransferRecipeDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "transferRecipeDTO", value = "接方列表", required = false, dataType = "TransferRecipeDTO")
    })
    @RequestMapping(value = "listTransferRecipe", method = RequestMethod.POST)
    public Object listTransferRecipe(@RequestBody TransferRecipeDTO transferRecipeDTO) {
		
        return new ResponseModel(transferRecipeService.listTransferRecipe(transferRecipeDTO));
    }
}
   
