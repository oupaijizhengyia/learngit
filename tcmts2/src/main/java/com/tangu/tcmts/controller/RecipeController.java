package com.tangu.tcmts.controller;

import static com.tangu.tcmts.util.Constants.MEDICINE_TABOO_KEY;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tangu.tcmts.po.*;
import com.tangu.tcmts.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.bean.SysPropertyUtil;
import com.tangu.tcmts.po.Company;
import com.tangu.tcmts.po.CountRecipes;
import com.tangu.tcmts.po.Employee;
import com.tangu.tcmts.po.HospitalDO;
import com.tangu.tcmts.po.PrintPage;
import com.tangu.tcmts.po.PrintPageObj;
import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.RecipeDTO;
import com.tangu.tcmts.po.RecipeMedicine;
import com.tangu.tcmts.po.RecipePatrol;
import com.tangu.tcmts.po.RecipePrintObj;
import com.tangu.tcmts.po.RecipeProccess;
import com.tangu.tcmts.po.RecipeReq;
import com.tangu.tcmts.po.RecipeScan;
import com.tangu.tcmts.po.SysLookup;
import com.tangu.tcmts.po.TransferMedicineObj;
import com.tangu.tcmts.service.EmployeeService;
import com.tangu.tcmts.service.HospitalService;
import com.tangu.tcmts.service.MedicineMaxDoseService;
import com.tangu.tcmts.service.MedicineTabooService;
import com.tangu.tcmts.service.PrintService;
import com.tangu.tcmts.service.RecipeService;
import com.tangu.tcmts.service.SysConfigService;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.RecipePrintUtil;
import com.tangu.tcmts.util.RecipeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author:yinghuaxu
 * @Description:处方列表控制层
 * @Date: create in 16:57 2017/11/16
 */
@Api(value = "recipe",description = "处方列表")
@RestController
@RequestMapping("recipe")
@Slf4j
public class RecipeController {
    private static final String RECIPE_EXAMINE = "处方已审核，不能修改处方！";
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private PrintService printService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private MedicineTabooService medicineTabooService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private SysPropertyUtil sysPropertyUtil;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private MedicineMaxDoseService medicineMaxDoseService;
    @Autowired
    private UnionService unionService;


    public static final String NONE_LOGISTIC_LIST = "该单快递信息被非法修改";

    @ApiOperation(tags = "recipe", value = "查询处方列表", response = RecipeDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recipe", value = "查询处方列表", dataType = "RecipeDTO")
    })
    @RequestMapping(value = "findRecipes", method = RequestMethod.POST)
    public Object findRecipes(@RequestBody RecipeDTO recipeDTO) {
    	String hospitalType = sysPropertyUtil.getProperty(Constants.HOSPITAL_TYPE, "1");
    	if (hospitalType.equals("0")) {
    		recipeDTO.setDateType("createTime");
    	}
        CountRecipes recipeNumber = recipeService.getRecipeNumbers(recipeDTO);
        return new ResponseModel(recipeService.listRecipes(recipeDTO)).attr("recipeNumbers", recipeNumber);
    }

    /**
     * 处方详情
     * @param recipeDTO
     * @return
     */

    //TODO
    @ApiOperation(tags = "recipe", value = "查询详情", notes = "通过id查询", response = RecipeDO.class)
    @ApiImplicitParam(name = "getRecipeContent", value = "查询详情", dataType = "RecipeDTO")
    @RequestMapping(value = "getRecipeContent", method = RequestMethod.POST)
    public Object getTransferRecipeContent(@RequestBody RecipeDTO recipeDTO) {
        RecipeDO recipe = recipeService.getRecipeContent(recipeDTO.getId());
        HospitalDO hospitalContent = hospitalService.getHospitalContent(recipe.getHospitalId());
        List<RecipeMedicine> recipeMedicineList = recipeService.getRecipeMedicineByRecipe(recipeDTO.getId());
        return new ResponseModel(recipe).attr("recipeMedicineList", recipeMedicineList).
                attr("hospitalContent", hospitalContent);
    }

    @ApiOperation(tags = "recipe", value = "报废处方")
    @ApiImplicitParam(name = "changeRecipeState", value = "报废处方", dataType = "RecipeDTO")
    @RequestMapping(value = "changeRecipeState", method = RequestMethod.POST)
    public Object changeRecipeState(@RequestBody RecipeDTO recipeDTO) {
        Integer result = 0;
        recipeDTO.setShippingState(Constants.TYPE_MAX);
        recipeDTO.setModUser(JwtUserTool.getId());
        result = recipeService.updateShippingStateToReject(recipeDTO);
        if (result.equals(1)) {
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(tags = "recipe", value = "审核处方", notes = "传list")
    @ApiImplicitParam(name = "checkRecipe", value = "报废处方", dataType = "RecipeDTO")
    @RequestMapping(value = "checkRecipe", method = RequestMethod.POST)
    public Object checkRecipe(@RequestBody List<RecipeDTO> recipeDTOList) {
        //暂未做仓储变更
    	List<RecipeDO> errorList =  recipeService.listRecipeState(recipeDTOList);
    	String recipeCode = "";
    	for (RecipeDO r : errorList) {
			if (r.getInputState() == null || r.getInputState() != 1) {
				recipeCode = ","+r.getSysRecipeCode();
			}
		}
    	if (StringUtils.isNotBlank(recipeCode)) {
    		return new ResponseModel().attr(ResponseModel.KEY_ERROR, "系统处方号："+StringUtils.stripStart(recipeCode, ",")+"未补录,不允许审核!");
    	}
        Integer result = 0;
        recipeDTOList.forEach(recipeDTO -> recipeDTO.setModUser(JwtUserTool.getId()));
        result = recipeService.updateCheckState(recipeDTOList);
        if (result.equals(1)) {
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(tags = "recipe", value = "物流查单")
    @ApiImplicitParam(name = "queryLogistics", value = "物流查单", dataType = "RecipeDTO")
    @RequestMapping(value = "queryLogistics", method = RequestMethod.POST)
    public Object queryLogistics(@RequestBody RecipeDTO recipeDTO) throws Exception {
        List<Map<String, String>> list = recipeService.queryLogistics(recipeDTO);
        if (CollectionUtils.isEmpty(list)) {
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, "暂无配送记录");
        }
        return new ResponseModel(list);
    }

    @ApiOperation(tags = "recipeCheck", value = "查看审方列表")
    @ApiImplicitParam(name = "findCheckRecipes", value = "查看审方列表", dataType = "RecipeDTO")
    @RequestMapping(value = "findCheckRecipes", method = RequestMethod.POST)
    public Object findCheckRecipes(@RequestBody RecipeDTO recipeDTO) {
        //设置endDate为startDate的下一天
        recipeDTO.setEndDate(LocalDate.parse(recipeDTO.getEndDate()).plusDays(1).toString());
        List<RecipeDO> recipeDTOList = recipeService.listCheckRecipes(recipeDTO);
        //设置null的处方显示为正常
        recipeDTOList.stream().filter(recipeDO -> recipeDO.getPreCheckState() == null)
                .forEach(recipeDO -> recipeDO.setPreCheckState(1));
        return new ResponseModel(recipeDTOList);
    }

    @ApiOperation(tags = "recipeCheck", value = "审方通过")
    @ApiImplicitParam(name = "changeCheckState", value = "审方通过", dataType = "RecipeDO")
    @RequestMapping(value = "changeCheckState", method = RequestMethod.POST)
    public Object changeCheckState(@RequestBody List<RecipeDTO> recipeDTOList) {
        Integer result = 0;
        RecipeProccess recipeProccess = new RecipeProccess();
        for (RecipeDTO recipeDTO : recipeDTOList) {
            recipeProccess.setRecipeId(recipeDTO.getId());
            recipeDTO.setShippingState(5);
        }
        recipeDTOList.forEach(recipeDTO -> recipeDTO.setModUser(JwtUserTool.getId()));
        result = recipeService.updateShippingState(recipeDTOList);
        recipeProccess.setCheckBillTime(new Date(System.currentTimeMillis()));
        recipeProccess.setCheckBillId(JwtUserTool.getId());
        recipeService.updateRecipeCheckProccess(recipeProccess);
        if (result.equals(1)) {
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(tags = "recipe", value = "获取状态列表")
    @RequestMapping(value = "getShippingState", method = RequestMethod.GET)
    public Object getShippingState() {
        return new ResponseModel(recipeService.getShippingState());
    }

    @ApiOperation(tags = "recipePatrol", value = "抽检列表", response = RecipePatrol.class)
    @ApiImplicitParam(name = "findRecipePatrols", value = "抽检列表", dataType = "RecipePatrol")
    @RequestMapping(value = "findRecipePatrols", method = RequestMethod.POST)
    public Object findRecipePatrols(@RequestBody RecipePatrol recipePatrol) {
        recipePatrol.setEndDate(LocalDate.parse(recipePatrol.getEndDate()).plusDays(1).toString());
        List<RecipePatrol> recipePatrolList = recipeService.listRecipePatrols(recipePatrol);
        return new ResponseModel(RecipeUtil.convertRecipePatrol(recipePatrolList));
    }

    @ApiOperation(tags = "recipePatrol", value = "报废抽检记录")
    @ApiImplicitParam(name = "changeRecipeResult", value = "报废抽检记录", dataType = "RecipePatrol")
    @RequestMapping(value = "changeRecipeResult", method = RequestMethod.POST)
    public Object changeRecipeResult(@RequestBody RecipePatrol recipePatrol) {
        Integer result = 0;
        result = recipeService.updatePatrolStatus(recipePatrol.getId());
        if (result.equals(1)) {
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }


   @ApiOperation(tags = "recipe", value = "处方详情-操作记录",notes="传入id  参数 参考RAP", response = Object.class)
   @ApiImplicitParam(name = "recipeDTO", value = "查询", required = false, dataType = "RecipeDTO") 
   @RequestMapping(value = "/listOperationRecord", method = RequestMethod.POST)
   public Object listOperationRecord(@RequestBody RecipeDTO recipeDTO){
	   if (recipeDTO.getId() == null) {
		   return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
	   }
	   return new ResponseModel(recipeService.listOperationRecord(recipeDTO));
   }

    //TODO
   @ApiOperation(tags = "recipe", value = "新增/修改 处方", response = Object.class)
   @ApiImplicitParam(name = "recipeDO", value = "查询", required = false, dataType = "RecipeDO") 
   @RequestMapping(value = "/addOrUpdateRecipe", method = RequestMethod.POST)
   public Object addOrUpdateRecipe(@RequestBody RecipeDO recipeDO) throws Exception {
        //处方目前为作废后不允许修改，其他状态都允许修改
	   String hospitalType = sysPropertyUtil.getProperty(Constants.HOSPITAL_TYPE, "1");
	   if (RecipeUtil.ifNUll(recipeDO, hospitalType)) {
		   return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
	   }
	   if (!hospitalType.equals("0")) {
		   if (recipeDO.getDiscount() == null || recipeDO.getDiscount().compareTo(BigDecimal.ZERO) != 1) {
			   return new ResponseModel().attr(ResponseModel.KEY_ERROR, "扣率必须大于0!");
		   }
		   if (recipeDO.getPreCheckState() == null||recipeDO.getPreCheckState() == 1){
	           Map<String, String> tabooUnionMap = medicineTabooService.getMedicineTabooUnionMap();
	           List<RecipeDO> list = new ArrayList<>();
	           list.add(recipeDO);
	           List<SysLookup> result = RecipeUtil.getMedicineTaboo(list, tabooUnionMap);
	           if (result.size() > 0) {
	               return new ResponseModel().attr(MEDICINE_TABOO_KEY, result);
	           }
	       }
	   }
	   Integer id = JwtUserTool.getId();
	   StringBuilder sb = new StringBuilder();
	   Map<String,String> map = new HashMap<>();
	   if (CollectionUtils.isNotEmpty( recipeDO.getRecipeMedicineList())) {
		   recipeDO.getRecipeMedicineList().stream()
		   .filter(m -> map.get(m.getSpecialBoilType()) == null)
		   .peek(m -> {
			   sb.append(",");
			   sb.append(m.getSpecialBoilType());
			   map.put(m.getSpecialBoilType(), "");
		   }).collect(Collectors.toList());
	   }
	   if (CollectionUtils.isNotEmpty(recipeDO.getRecipeMedicineList())) {
		   recipeDO.setInputState(Constants.STATE_ONE);
		   recipeDO.setInputEmployee(id);
		   recipeDO.setInputTime(new Date());
		   recipeDO.setMedicineQuantity(recipeDO.getRecipeMedicineList().size());
	   }
	   recipeDO.setTotalWeight(RecipeUtil.setTotalWeight(recipeDO));
	   recipeDO.setSpecialPrint(StringUtils.stripStart(sb.toString(), ","));
	   RecipeUtil.setSpecialPrint(recipeDO);
	   /**
	    * 修改
	    */
	   
	   Integer result = 0;
	   if(recipeDO.getId() == null) {
       //新增
       recipeDO.setCreatorId(id);
       result = recipeService.addRecipe(recipeDO);
     }else if (recipeDO.getId() > 0) {//修改
		   if (recipeService.getCheckState(recipeDO).getCheckState() == 1) {
			   return new ResponseModel().attr(ResponseModel.KEY_ERROR, RECIPE_EXAMINE);
		   }
		   recipeDO.setModUser(id);
		   //[没看出这段代码的意义,待测试检验]
//		   if (recipeDO.getPreCheckState() != null&&recipeDO.getPreCheckState() == 2){
//			   recipeDO.setPreCheckState(recipeDO.getPreCheckState());
//		   }
		   result = recipeService.updateRecipe(recipeDO, hospitalType);
	   }
		if (result > 0) {
//			RecipeDTO recipeDTO = new RecipeDTO(recipeDO.getId());
//			List<RecipeDO> list = recipeService.listRecipes(recipeDTO);
            RecipeDO recipeDO1 = recipeService.getRecipeContent(recipeDO.getId());
//			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS)
//					.attr(ResponseModel.KEY_DATA, CollectionUtils.isEmpty(list) ? list : list.get(0));

            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS)
                    .attr(ResponseModel.KEY_DATA, recipeDO1);
		}
 	   return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
   }
   
   @ApiOperation(tags = "recipe", value = "打印面单")
   @ApiImplicitParam(value = "打印处方参数", dataType = "RecipeReq")
   @RequestMapping(value = "/printRecipe", method = RequestMethod.POST)
   public Object printRecipe(@RequestBody RecipeReq recipeReq) {
       List<PrintPageObj> pageObjs=new ArrayList<>();
       List<RecipePrintObj> printPageObjList = printService.listRecipePrintMessages(recipeReq);
       Map<Integer,String> speTagMap=new HashMap<>();
       boolean isHospital = sysPropertyUtil.getProperty(Constants.HOSPITAL_TYPE, "1").equals("0")?true:false;
       if(isHospital && recipeReq.getGroupPrint().indexOf(Constants.PrintType.RECIPE_TAG)>-1){
    	   for(RecipePrintObj e:printPageObjList){
    		   if(speTagMap.containsKey(e.getTakeType())){
    			   continue;
    		   }
    		   speTagMap.put(e.getTakeType(), RecipeUtil.getTakeTypePrintKey(e.getTakeType()));
    	   }
       }
       if(speTagMap.size()>0){
    	   recipeReq.getGroupPrint().replaceAll(Constants.PrintType.RECIPE_TAG, "");
       }
       String[] arr=recipeReq.getGroupPrint().split("_");
       List<String> printTypeList=new ArrayList<String>(Arrays.asList(arr));
       if(speTagMap.size()>0){
    	   printTypeList.addAll(new ArrayList<String>(speTagMap.values()));
       }
       Map<String,Object> map=new HashMap<>();
       map.put("typeIds", printTypeList);
       Map<String,PrintPage> pageMap=printService.listPrintPageItem(map);
       List<Integer> recipeIdList =printPageObjList.stream().map(RecipePrintObj::getId).collect(Collectors.toList());
       //查询recipeMedicine列
       List<TransferMedicineObj> listRecipeMedicine = null;
       if(RecipePrintUtil.isFindRecipeMedicine(printTypeList)){
    	   if(!isHospital){
    		   listRecipeMedicine=printService.listRecipeMedicinePrintMessages(recipeIdList);
    	   }else{
    		   List<Long> billIdList =printPageObjList.stream().map(RecipePrintObj::getBillId).collect(Collectors.toList());
    		   //listRecipeMedicine=printService.listRecipeMedicinePrintMessages(recipeIdList);
    		   listRecipeMedicine=printService.listTransferRecipeMedicinePrintMessages(billIdList);
    	   }
       }
       Company company=sysConfigService.listCompany();
       String cardNum=sysPropertyUtil.getProperty(Constants.ConfigKey.SF_CARD_NUM);
       Date now=new Date();
       String operator=JwtUserTool.getUserName();
       for (RecipePrintObj recipePrintObj : printPageObjList) {
    	   recipePrintObj.setPrintDate(now);
    	   recipePrintObj.setOperator(operator);
    	   recipePrintObj.setPayMethod(1);
       	   recipePrintObj.setPayMethodName(Constants.PayMethod.getMethodeName(recipePrintObj.getPayMethod()));
       	   recipePrintObj.setCardNum(cardNum);
       	   RecipePrintUtil.setCompanyInfo(company, recipePrintObj);
       }	   
       if(listRecipeMedicine !=null && listRecipeMedicine.size()>0){
    	   if(!isHospital){
    		   listRecipeMedicine.stream()
    		   .filter(e ->e.getUnitType()==1)
    		   .peek(e ->e.setUnit(Constants.UNIT_G)).collect(Collectors.toList());
    	   }
           List<TransferMedicineObj> reciepMedicineList=null;
           for (RecipePrintObj recipePrintObj : printPageObjList) {
        	   if(!isHospital){
        		   reciepMedicineList=listRecipeMedicine.stream()
        				   .filter(e ->e.getRecipeId().equals(recipePrintObj.getId()))
        				   .collect(Collectors.toList());
        	   }else{
        		   reciepMedicineList=listRecipeMedicine.stream()
        				   .filter(e ->e.getBillId().equals(recipePrintObj.getBillId()) && 
        						   e.getHospitalCode().equals(recipePrintObj.getHospitalCode()))
        				   .collect(Collectors.toList());
        		   reciepMedicineList.sort((TransferMedicineObj h1, TransferMedicineObj h2) -> h1.getMedicineCode().compareTo(h2.getMedicineCode()));
        	   }
               recipePrintObj.setTransferMedicineList(reciepMedicineList);
               int n=0;
               for(TransferMedicineObj temp:recipePrintObj.getTransferMedicineList()){
            	   if(!isHospital){
            		   temp.setWeight(temp.getWeightEvery().multiply(BigDecimal.valueOf(recipePrintObj.getQuantity())));
            	   }else{
            		   temp.setMoneyEvery(temp.getMoney().divide(BigDecimal.valueOf(recipePrintObj.getQuantity()), 4, BigDecimal.ROUND_HALF_UP));
            	   }
	     		   temp.setSerialNumber(++n+"");
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
    	   recipeService.updateRecipePrintState(recordIds);
       }
       return pageObjs;
   }
   
   @ApiOperation(tags = "recipe", value = "根据工号分配处方",notes = "参数员工工号：code", response = RecipeDO.class)
   @RequestMapping(value = "getRecipeByEmployeeCode", method = RequestMethod.POST)
   public Object getRecipeByEmployeeCode(@RequestBody Employee employee) {
	   if (StringUtils.isBlank(employee.getEmployeeCode())) {
		   return new ResponseModel().attr(ResponseModel.KEY_ERROR, "请传入员工工号!");
	   }
	   Integer employeeId=employeeService.getEmployeeId(employee.getEmployeeCode());
	   if(employeeId == null ||  employeeId < 1){
		   return new ResponseModel().attr(ResponseModel.KEY_ERROR,"该工号不存在!");
	   }

//	   if (recipeService.getExamineRecipe(employeeId)) {
//		   return new ResponseModel().attr(ResponseModel.KEY_ERROR, "该工号有处方已审方未配药!");
//	   }

	   employee.setId(employeeId);
	   RecipeDO recipeDO=recipeService.dispatchRecipe(employee);
	   if(recipeDO == null){
		   return new ResponseModel().attr(ResponseModel.KEY_MESSAGE,"暂时没有处方需要审方!");
	   }
//	   if (employee.getExamineType() == 0) {
//		   return new ResponseModel(recipeDO.getId()).attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
//	   }
	   return new ResponseModel(recipeDO.getId());
	}
   
   @ApiOperation(tags = "recipe", value = "根据id获取审方详情",notes = "参数id", response = RecipeDO.class)
   @RequestMapping(value = "getRecipeByRecipeId", method = RequestMethod.POST)
   public Object getRecipeByRecipeId(@RequestBody RecipeDTO recipeDTO) {
	   if (recipeDTO.getId() == null) {
		   return new ResponseModel().attr(ResponseModel.KEY_ERROR, "请传入处方id!");
	   }
	   RecipeDO recipe = recipeService.getRecipeContent(recipeDTO.getId());
	   if (recipe != null) {
		   List<RecipeMedicine> medicineList = recipeService.getRecipeMedicineByRecipe(recipeDTO.getId());
		   if (CollectionUtils.isNotEmpty(medicineList)) {
			   recipe.setRecipeMedicineList(medicineList);
		   }
		   /**
	        * 判断是否存在配伍禁忌
	        */
			Map<String, String> tabooUnionMap = medicineTabooService.getMedicineTabooUnionMap();
			List<RecipeDO> list = new ArrayList<>();
			list.add(recipe);
			List<SysLookup> result = RecipeUtil.getMedicineTaboo(list, tabooUnionMap);
			if (CollectionUtils.isNotEmpty(result)) {
				String medicineTaboo = "";
				for (SysLookup sysLookup : result) {
					medicineTaboo = "," + sysLookup.getExt2();
				}
				recipe.setMedicineTaboo(StringUtils.stripStart(medicineTaboo, ","));
			}
			/**
			 * 判断超剂量
			 */
			recipe.setMedicineMaxDose(RecipeUtil.getMedicineMaxDose(
					medicineMaxDoseService.listMedicineMaxDoseByMedicineIds(recipe.getRecipeMedicineList()),
					recipe.getRecipeMedicineList()));

	   }
		return new ResponseModel(recipe);
   }
   
	@ApiOperation(tags = "recipe", value = "确认审方", notes = "传入recipeDO", response = Object.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "recipeDO", value = "确认审方", required = false, dataType = "RecipeDO") })
	@RequestMapping(value = "confirmationExamine", method = RequestMethod.POST)
	public Object confirmationExamine(@RequestBody RecipeDO recipeDO) {
		if (recipeDO == null) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, "请传入处方信息!");
		}
		try {
			Integer id = JwtUserTool.getId();
			recipeDO.setShippingState(5);
			if (recipeService.updateByConfirmationExamine(recipeDO) > 0) {
				if (CollectionUtils.isNotEmpty(recipeDO.getRecipeMedicineList())) {
					recipeService.updateRecipeMedicines(recipeDO.getRecipeMedicineList());
				}
				List<RecipeScan> sacnList = new ArrayList<>();
				sacnList.add(new RecipeScan(recipeDO.getId(), recipeDO.getDispenseId(), 5));
				recipeService.insertRecipeScan(sacnList);
				
				recipeDO.setCheckBillId(id);
				recipeDO.setCheckBillTime(new Date());
				recipeService.updateProccess(recipeDO);

				return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}
	@ApiOperation(tags = "recipe", value = "员工业绩统计列表", notes = "传入startDate,endDate,operateType = 工序，receiveScanUse = 员工姓名id", response = RecipeScan.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "recipeScan", value = "查询", required = false, dataType = "RecipeScan") })
	@RequestMapping(value = "countEmployeeAchievement", method = RequestMethod.POST)
	public Object countEmployeeAchievement(@RequestBody RecipeScan recipeScan) {
		
		return new ResponseModel(recipeService.countEmployeeAchievement(recipeScan));
	}
	
	/**
	 * 需求不明
	 */
	/*@ApiOperation(tags = "recipe", value = "合并处方", notes = "传入格式 ：[{id=处方id},{id}]", response = String.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "paramList", value = "查询", required = false, dataType = "RecipeDTO") })
	@RequestMapping(value = "mergeRecipe", method = RequestMethod.POST)
	public Object mergeRecipe(@RequestBody List<RecipeDTO> paramList) {
		if (CollectionUtils.isEmpty(paramList)) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, "请传入处方id!");
		}
		if (paramList.size() != 2) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, "只能2张处方一起才可以合并!");
		}
		List<RecipeDO> stateList = recipeService.listRecipeState(paramList);
		if (CollectionUtils.isEmpty(stateList)) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, "没有对应的处方，请确认!");
		}
		String result = RecipeUtil.ifRecipeState(stateList);
		if (StringUtils.isBlank(result)) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, result);
		}
		List<RecipeDO> nativeRecipeList = new ArrayList<>();
		for (RecipeDTO recipeDTO : paramList) {
			RecipeDO r = recipeService.getRecipeContent(recipeDTO.getId());
			r.setRecipeMedicineList(recipeService.getRecipeMedicineByRecipe(recipeDTO.getId()));
			nativeRecipeList.add(r);
		}
		if (CollectionUtils.isNotEmpty(nativeRecipeList)) {
			RecipeUtil.contrastRecipeMedicine(nativeRecipeList);
		}
		return null;
	}*/


    @ApiOperation(tags = "recipe", value = "锁定与解锁处方", notes = "传入id", response = RecipeDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recipeDO", value = "只有id的处方", required = false, dataType = "RecipeDO")
    })
    @RequestMapping(value = "lockingRecipe", method = RequestMethod.POST)
    public Object lockingRecipe(@RequestBody RecipeDO recipeDO) {
        if (recipeDO.getId() == null || recipeDO.getId() == 0) {
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, "请传入处方id!");
        }
        return recipeService.lockingRecipe(recipeDO.getId());
    }


    /**
     * 建立关联与取消关联
     * @param unionDTOList
     * @return
     * 已经写好 待测
     */
    @ApiOperation(tags = "recipe", value = "建立关联和取消关联")
    @ApiImplicitParam(name = "UnionDTO",value = "单个的传入对象",dataType = "UnionDTO")
    @RequestMapping(value = "union",method = RequestMethod.POST)
    public Object unionRecipe(@RequestBody List<UnionDTO> unionDTOList){
        int re = unionService.createAndCancelUnion(unionDTOList);
        if (re == 0){
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
        }
        return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
    }

    /**
     * 在详情中删除照片
     * @param recipeId
     * @return
     * 在处方锁定时,禁止修改
     * 删除照片，只有未配药的处方才会显示该删除按钮
     * 若处方流程状态为“已配药”及之后的状态时，点击删除按钮提示已配药无法删除
     */
    @ApiOperation(tags = "recipe",value = "删除处方照片",notes = "传入id")
    @ApiImplicitParam(name = "recipeId",value = "处方Id")
    @RequestMapping(value = "picture",method = RequestMethod.DELETE)
    public Object deletePicture(Integer recipeId){
        /*
        只有一张图片时禁止删除
         */
        return new ResponseModel();
    }
    /**
     * 上传图片
     * @return
     *
     */
    @RequestMapping(value = "picture",method = RequestMethod.POST)
    @ApiImplicitParam(name = "picture",value = "上传图片",dataType = "MultipartFile")
    @ApiOperation(tags = "recipe",value = "上传处方照片",notes = "传入图片")
    public Object uploadPicture(@RequestBody  MultipartFile picture){
        return  new ResponseModel();
    }






















}



   
