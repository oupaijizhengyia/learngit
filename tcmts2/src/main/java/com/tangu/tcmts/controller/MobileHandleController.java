package com.tangu.tcmts.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tangu.datasource.DynamicDataSourceTenantLocal;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.tangu.tcmts.bean.SysPropertyUtil;
import com.tangu.tcmts.po.Employee;
import com.tangu.tcmts.po.ExceptionCase;
import com.tangu.tcmts.po.MachineDO;
import com.tangu.tcmts.po.MedicineDO;
import com.tangu.tcmts.po.MedicineStandardDO;
import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.RecipeMedicine;
import com.tangu.tcmts.po.RecipePatrol;
import com.tangu.tcmts.po.RecipeScan;
import com.tangu.tcmts.po.SysConfig;
import com.tangu.tcmts.po.WarehouseDO;
import com.tangu.tcmts.po.WarehouseDTO;
import com.tangu.tcmts.service.EmployeeService;
import com.tangu.tcmts.service.ExceptionCaseService;
import com.tangu.tcmts.service.MachineService;
import com.tangu.tcmts.service.MedicineStandardService;
import com.tangu.tcmts.service.RecipeService;
import com.tangu.tcmts.service.ShelvesMedicineManageService;
import com.tangu.tcmts.service.SysConfigService;
import com.tangu.tcmts.service.WarehouseService;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.MobileConstant;
import com.tangu.tcmts.util.MobileHandleHelper;
import com.tangu.tcmts.util.SystemConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value = "App", description = "手持接口")
@RestController
@Slf4j
public class MobileHandleController {
	// 文件存放路径
	public String uploadPath = "/upload/";

	private int maxPostSize = 100 * 1024 * 1024;
	@Autowired
    private EmployeeService employeeService;
	@Autowired
    private SysConfigService sysService;
	@Autowired
    private RecipeService recipeService;
	@Autowired
    private ExceptionCaseService exceptionService;
	@Autowired
    private MachineService machineService;
	@Autowired
    private WarehouseService warehouseService;
	@Autowired
    private MedicineStandardService medicineStandardService;
	@Autowired
    private SysPropertyUtil sysPropertyUtil;
	@Autowired
	private ShelvesMedicineManageService shelvesMedicineManageService;
	
	@Value("${tangu.file.uploadPath}")
    private String path;
	@ApiOperation(tags = "app all method", value = "app总方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "custom json", value = "自定义json")
    })
    @RequestMapping(value = "/MobileHandle", method = RequestMethod.POST)
    public Object appHandle(HttpServletRequest request,HttpServletResponse response) throws IOException {
	   String type=StringUtils.defaultIfEmpty(request.getParameter("TP"), null);
	   Object resultObj=null;
	   switch (Integer.valueOf(type)) {
		//用户登陆，返回用户权限
		case MobileConstant.LOGIN: resultObj=login(request);break;
		//配药
		case MobileConstant.DISPENSE_SCAN: resultObj=dispenseScan(request);break;
		//配药登录用户
		case MobileConstant.USER_SCAN: resultObj=userScan(request);break;
		//复核拍照
		case MobileConstant.ACCEPT_SCAN: resultObj=accpptScan(request);break;
		//煎药机巡检
		case MobileConstant.MACHINE_PATROL_SCAN: resultObj=patrolScan(request);break;
		//获取时间状态
		case MobileConstant.GET_TIME: resultObj=getTime(request);break;
		//上传复核照片和打包照片
		case MobileConstant.SAVE_PHOTO: resultObj=savePhoto(request,response);break;
		//开始煎煮2（扫描煎药机后）
		case MobileConstant.START_DECOCT_SCAN: resultObj=startDecoctScan(request,response);break;
		//结束煎煮2（扫描煎药机后）
		case MobileConstant.END_DECOCT_SCAN: resultObj=endDecoctScan(request,response);break;
		//打包拍照
		case MobileConstant.PACK_SCAN: resultObj=packScan(request, response);break;
		//处方历史
		case MobileConstant.RECIPE_HISTORY: resultObj=recipeHistory(request, response);break;
		//配方,煎药巡检查看
		case MobileConstant.PATROL_SCAN: resultObj=recipePatrolScan(request, response);break;
		//配方,煎药巡检保存
		case MobileConstant.PATROL_ADD: resultObj=addPatrol(request, response);break;
		//获取版本号
		case MobileConstant.GET_VERSION: resultObj=getVersion(request, response);break;
		//获取药房、仓库
		case MobileConstant.GET_WAREHOUSE: resultObj=getWarehouse(request, response);break;
		//根据条码获取药品信息
		case MobileConstant.GET_MEDICINE_BY_NUMBER: resultObj=getMedicineByNumber(request, response);break;
		//添加调拨申请
		case MobileConstant.ADD_WARE_OUT_APPLY: resultObj=addWareOutApply(request, response);break;//暂不做
		//获得处方列表和煎药机列表
		case MobileConstant.GET_RECIPE_LIST_AND_MACHINE_LIST: resultObj=getZnpgList(request, response);break;
		//智能派工暂无
//		case MobileConstant.ALLOT: resultObj=allotBoil(request, response);break;
		//复核不通过
		case MobileConstant.CHECK_NOT_THROUGH: resultObj=checkNotThrough(request, response);break;
		//开始浓缩
		case MobileConstant.START_CONCENTRATE: resultObj=startConcentrate(request, response);break;
		//结束浓缩
		case MobileConstant.END_CONCENTRATE: resultObj=endConcentrate(request, response);break;
		//先煎药品
		case MobileConstant.GET_BOIL_MEDICINE: resultObj=getFristBoillMedicine(request, response);break;
		//保存先煎药品
		case MobileConstant.SAVE_BOIL_MEDICINE: resultObj=saveFristBoillMedicine(request, response);break;
		//保存后下药品
		case MobileConstant.SAVE_AFTER_MEDICINE: resultObj=saveAfterBoillMedicine(request, response);break;
		//发货2（扫描快递单号后）
		case MobileConstant.DELIVERY_BILL: resultObj=deliveryScan(request, response);break;
		//复核、浸泡、开始煎煮1、结束煎煮、打包 
		case MobileConstant.CHECK_RECIPE_STATUS: resultObj=checkRecipe(request, response);break;
		//领料
		case MobileConstant.RECIPE_PICKING: resultObj=recipePicking(request, response);break;
		//上架
		case MobileConstant.MEDICINE_MATCH_SHELVES: resultObj=medicineMatchSfelves(request, response);break;
		//上架返回处方信息
		case MobileConstant.MEDICINE_MATCH_SHELVES_GET: resultObj=medicineMatchSfelvesGet(request, response);break;
		//取药确认
		case MobileConstant.MEDICINE_TAKE_CONFIRM: resultObj=medicineTakeConfirm(request, response);break;
		//取药返回处方信息
		case MobileConstant.MEDICINE_TAKE_CONFIRM_GET: resultObj=medicineTakeConfirmGet(request, response);break;
		//开始二煎（扫描煎药机后）
		case MobileConstant.START_SECOND_BOIL: resultObj=startSecondBoilScan(request,response);break;
		default:log.info("type= null");
			break;
	   }
	   log.info("resultObj="+resultObj);
	   return resultObj;
    }
	
	private Object medicineTakeConfirm(HttpServletRequest request,
      HttpServletResponse response) {
    return tryCatch(request, Constants.STATE_SHELVES_ALREADY);
  }

  private Object medicineMatchSfelves(HttpServletRequest request,
      HttpServletResponse response) {
    return tryCatch(request, Constants.STATE_PACK_SCAN);
  }

	private Object medicineTakeConfirmGet(HttpServletRequest request,
									   HttpServletResponse response) {
		return tryCatchGet(request, Constants.STATE_SHELVES_ALREADY);
	}

	private Object medicineMatchSfelvesGet(HttpServletRequest request,
										  HttpServletResponse response) {
		return tryCatchGet(request, Constants.STATE_PACK_SCAN);
	}

  
  JSONObject tryCatch(HttpServletRequest request, int statePackScan){
    JSONObject obj= new JSONObject();
    JSONObject object = MobileHandleHelper.parseRequestToObj(request);
    try{
      obj = shelvesMedicineManageService.medicineChangeState(object, obj, statePackScan);
    } catch (Exception e) {
      obj.put("rs","-1");
      log.error("处方info{} 异常{}", object, e);
    }
    return obj;
  }

	JSONObject tryCatchGet(HttpServletRequest request, int statePackScan){
		JSONObject obj= new JSONObject();
		JSONObject object = MobileHandleHelper.parseRequestToObj(request);
		try{
			obj = shelvesMedicineManageService.confirmRecipe(object, obj, statePackScan);
		} catch (Exception e) {
			obj.put("rs","-1");
			log.error("处方info{} 异常{}", object, e);
		}
		return obj;
	}
  
  //用户登陆，返回用户权限
	private JSONObject login(HttpServletRequest request){
		JSONObject obj= new JSONObject();
		JSONObject object = MobileHandleHelper.parseRequestToObj(request);
		if(object!=null){
			String userName=object.getString("UN");
			log.info("userName="+userName);
			Employee employeeUser = new Employee();
			employeeUser.setEmployeeName(userName);
			employeeUser.setIsOperator(1);
			employeeUser.setUseState(0);
	   	   	List<Employee> list = employeeService.findEmployee(employeeUser);
	   	    if(list!=null&&list.size()>0)
	   	   	{
	   	   		 Employee employee = (Employee)list.get(0);
	   	   		 obj.put("ec", employee.getEmployeeCode());
	   	   		 obj.put("id", employee.getId());
	   	   		 obj.put("en", employee.getEmployeeName());
	   	   		 //获取当前登入员工对应的权限
	   	   		 Map privilegList=employeeService.findAllPrivilegeListByRole(employee);
	   	   		 if(privilegList!=null&&privilegList.size()>0)
	   	   		 {
	   	   			 MobileHandleHelper.setPrivilegJSONObject(obj, privilegList);
	   	   			 Constants.privilegList.put(employee.getId().toString(), privilegList) ;
	   	   		 }
	   	   		 //获取系统配置
	   	   		 List<SysConfig> sysList = sysService.listConfig();
	   	   		 //获取拍照像素
		   	   	 SysConfig photoConfig = getSystemConfig(sysList, SystemConstant.PHOTO_PIXELS);
	   	   		 if(photoConfig!=null&&photoConfig.getValue()!=null){
	   	   			 maxPostSize = Integer.valueOf(photoConfig.getValue().toString()).intValue() * 1024 * 1014;
	   	   		 }
	   	   		 //获取"是否支持复核不通过"配置
	   	   		 SysConfig cntConfig = getSystemConfig(sysList, SystemConstant.ACCEPT_CHECK_NOT_THROUGH);
	   	   		 log.info("cntConfig.getValue().toString()="+cntConfig.getValue().toString());
	   	   		 if(cntConfig!=null&&cntConfig.getValue()!=null){
	   	   			 obj.put("ACCEPT_CHECK_NOT_THROUGH", "Y".equals(cntConfig.getValue().toString()) ?"1":"0");
	   	   		 }else{
	   	   			 obj.put("ACCEPT_CHECK_NOT_THROUGH", 0);
	   	   		 }
	   	   		 obj.put("SF", "1");
	   	   	 }
	   	   	 else
	   	   	 {
	   	   		 obj.put("SF", "0");
	   	   	 }
		 }
		 log.info("login req="+object.toJSONString());
		 return obj;		
	}
	private JSONObject userScan(HttpServletRequest request)throws IOException
	{
		log.info("userScan-------------------userScan");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				Employee employee = new Employee();
				employee.setEmployeeCode(object.getString("tm"));
				employee.setEmployeeName(object.getString("tm"));
				employee.setIsOperator(1);
				employee.setUseState(0);
				List<Employee> list = employeeService.findEmployee(employee);
				if(list.size()==0){
					obj.put("rs", "n");//表示该用户不存在
				}else{
					Employee employeeScan = (Employee)list.get(0);
					employeeScan.setFunctionCode("DISPENSE_SCAN");
					 Map privilegList=employeeService.findAllPrivilegeListByRole(employeeScan);
		   	   		 if(privilegList!=null&&privilegList.size()>0)
		   	   		 {
		   	   			 obj.put("rs", "1");//表示该用户有配药权限
		   	   			 obj.put("en", ((Employee)list.get(0)).getEmployeeName());
		   	   			 obj.put("ei", ((Employee)list.get(0)).getId().intValue()+"");
		   	   		 }else{
		   	   			 obj.put("rs", "0");// 表示该用户没有配药权限;
		   	   		 }
				}
				
			}
		}catch(JSONException e)
		{
			try {
				obj.put("rs","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	//配药扫描
	private JSONObject dispenseScan(HttpServletRequest request)throws IOException
	{
		log.info("dispenseScan-------------------dispenseScan");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				//获取系统配置
	   	   	    List<SysConfig> sysList = sysService.listConfig();
	   	   		//获取"是否需要审方"配置
	   	   		SysConfig checkBillConfig = getSystemConfig(sysList, SystemConstant.MUST_CHECK_BILL);
				RecipeDO record = new RecipeDO();
				record.setRecipeCode(object.getString("tm"));
				RecipeDO recipe = recipeService.checkRecipeStatus(record);
		   	   	if(checkBillConfig!=null&& "Y".equals(checkBillConfig.getValue())){
			   	   	if(recipe==null)
		   	   		{
		   	   			obj.put("rs", "n");//该处方不存在
		   	   		}
//			   	   	else if(recipe.getDispenseId()==-1){
//		   	   			obj.put("rs", "18");//该处方已锁定，先解锁再配药
//		   	   		}
		   	   		else if(recipe.getShippingState()<Constants.State.CHECK_BILL.getNewState())
		   	   		{
		   	   			obj.put("rs", "17");//该处方未审方，先审方再配药
		   	   		}
		   	   		else if(Objects.equals(recipe.getShippingState(), Constants.State.INVALID.getNewState()))
		   	   		{
		   	   			obj.put("rs", "d");//该处方已作废
		   	   		}
		   	   		else if(Objects.equals(recipe.getShippingState(), Constants.State.CHECK_BILL.getNewState()))
		   	   		{
		   	   			recipe.setScanUser(object.getInteger("ei")); 
		   	   			recipe.setDispenseId(object.getInteger("ei"));
		   	   			recipe.setShippingState(Constants.State.DISPENSE_SCAN.getNewState());
		   	   			
		   	   			recipe.setMakeId(recipe.getScanUser());
		   	   			recipe.setMakeTime(new Date());
		   	   			
		   	   			recipeService.updateRecipeStatus(recipe);
		   	   			
		   	   			obj.put("rs", "1");//该处方满足配药条件，并已保存
		   	   		}else{
		   	   			obj.put("rs", "0");//该处方已配药
		   	   		}
		   	   	}else{
			   	   	 if(recipe==null)
			   	   	 {
			   	   		 obj.put("rs", "n");//该处方不存在
			   	   	 }
			   	   	 else if(recipe.getShippingState()>=Constants.State.DISPENSE_SCAN.getNewState())
			   	   	 {
			   	   		 obj.put("rs", "0");//该处方已配药
			   	   	 }
			   	   	 else if(Objects.equals(recipe.getShippingState(), Constants.State.INVALID.getNewState()))
			   	   	 {
			   	   		 obj.put("rs", "d");//该处方已作废
			   	   	 }
			   	   	 else
			   	   	 {
			   	   		 recipe.setScanUser(object.getInteger("ei"));
			   	   		 recipe.setDispenseId(object.getInteger("ei"));
			   	   		 recipe.setShippingState(Constants.State.DISPENSE_SCAN.getNewState());
			   	   		 recipe.setMakeId(recipe.getScanUser());
			   	   		 recipe.setMakeTime(new Date());
			   	   		 recipeService.updateRecipeStatus(recipe);
			   	   		 obj.put("rs", "1");//该处方满足配药条件，并已保存
			   	   	 }
				}
			}
		}catch(JSONException e)
		{
			try {
				obj.put("rs","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			obj.put("rs","-1");
			e.printStackTrace();
		}
		return obj;	
	}
	//接方扫描,复核扫描,浸泡，结束煎煮,开始煎煮,收膏扫描
	private JSONObject accpptScan(HttpServletRequest request)throws IOException
	{
		log.info("accpptScan-------------------accpptScan");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				RecipeDO record = new RecipeDO();
				record.setRecipeCode(object.getString("rc"));
				record.setShippingState(object.getIntValue("st"));
				RecipeDO recipe = recipeService.checkRecipeStatus(record);
				if(recipe==null)
		   	   	 { 
		   	   		 obj.put("SF", "0");
		   	   	 }
		   	   	 else if((recipe.getBoilType()==Constants.ZJ||recipe.getBoilType()==Constants.GFZJ)&&
		   	   			 record.getShippingState()>Constants.State.RE_CHECK_SCAN.getNewState()&& !Objects.equals(record.getShippingState(), Constants.State.PACK_SCAN.getNewState()))
		   	   	 {
		   	   		 obj.put("SF", "e");
		   	   		 obj.put("st", -1);
		   	   	 }
		   	   	 else if(recipe.getBoilType()!=Constants.GF&& Objects.equals(record.getShippingState(), Constants.State.BOIL_SCAN.getOldState()))
		   	   	 {
		   	   		 obj.put("SF", "e");
		   	   		 obj.put("st", -4);
		   	   	 }
		   	   	 else if(recipe!=null&&recipe.getShippingState()!=Constants.StateMap.getState(record.getShippingState()-1))
		   	   	 {
		   	   		 obj.put("SF", "e");
		   	   		 obj.put("st", Constants.OldStateMap.getOldState(recipe.getShippingState()));
		   	   	 }
		   	   	 else if(recipe.getShippingState()==Constants.StateMap.getState(record.getShippingState()-1))
		   	   	 {
		   	   		 recipe.setScanUser(object.getIntValue("EI"));
		   	   		 recipe.setShippingState(Constants.StateMap.getState(record.getShippingState()));
		   	   		 if(Objects.equals(record.getShippingState(), Constants.State.END_DECOCT_SCAN.getOldState())) {
                         recipe.setMachineId(object.getIntValue("mi"));
                     }
		   	   		 
		   	   		 if(Objects.equals(record.getShippingState(), Constants.State.RE_CHECK_SCAN.getOldState())){
		   	   			 recipe.setCheckId(recipe.getScanUser());
		   	   			 recipe.setCheckTime(new Date());
		   	   		 }

		   	   		 
		   	   		 
		   	   		 Integer rows =recipeService.updateRecipeStatus(recipe);
		   	   		 obj.put("SF", "1");
		   	   		 obj.put("id", recipe.getId());
		   	   		 obj.put("sid", rows);
		   	   	 }
			}
		}catch(JSONException e)
		{
			try {
				obj.put("rs","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			obj.put("rs","-1");
			e.printStackTrace();
		}
		return obj;
	}
	//煎药机巡查
	private JSONObject patrolScan(HttpServletRequest request)throws IOException
	{
		log.info("patrolScan-------------------patrolScan");
		JSONObject obj= new JSONObject();
		Integer count = 0;
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				MachineDO machine = new MachineDO();
				machine.setMachineStatus(object.getIntValue("s"));
				machine.setMachineCode(object.getString("mc").trim());
				machine.setChecktorId(object.getIntValue("EI"));
				machine.setCheckTime(new Date());
				machine.setHealthStatus(object.getIntValue("ws"));
				machine.setSterStatus(object.getIntValue("xd"));
				count = machineService.updateMachineByCode(machine);
				if(count>0){
					obj.put("SF", "1"); 
				}
				else{
					obj.put("SF", "0");
				}
			}
		}catch(JSONException e)
		{
			try {
				obj.put("rs","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			obj.put("rs","-1");
			e.printStackTrace();
		}
		return obj;
	}
	//获取浸泡时间，煎药时间
	private JSONObject getTime(HttpServletRequest request)throws IOException
	{
		log.info("getTime-------------------getTime");
		request.setCharacterEncoding("UTF-8");
		JSONObject obj= new JSONObject();
		Integer count = 0;
		try{
			String tenant=getTenantForMobile();//账套
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				String recipeCode=object.getString("rc");
				 int type = object.getIntValue("t");
				 RecipeDO re = new RecipeDO();
				 re.setRecipeCode(recipeCode);
				 re.setOperateType(type-1);
		   	   	 List<RecipeDO> list = recipeService.selectRecipeByCode(re);
		   	   	 if(list!=null&&list.size()>0)
		   	   	 {
		   	   		 RecipeDO recipe = (RecipeDO)list.get(0);
			   	   	 if(recipe.getBoilType()==Constants.ZJ||recipe.getBoilType()==Constants.GFZJ)
			   	   	 {
			   	   		 obj.put("SF", "e");
			   	   		 obj.put("st", -1);
			   	   	 }
			   	   	 else if(recipe!=null&&recipe.getShippingState()!=Constants.StateMap.getState(type-1))
		   	   		 {
		   	   			 obj.put("SF", "e");
		   	   			 obj.put("st", recipe.getShippingState());
		   	   		 }
		   	   		 else
		   	   		 {
		   	   			 Long time = (Long)((System.currentTimeMillis()-recipe.getReceiveScanTime().getTime())/ (1000 * 60));
		   	   			 if((type==Constants.State.START_DECOCT_SCAN.getOldState()&&time<(recipe.getSoakTime()-1))||(type==Constants.State.END_DECOCT_SCAN.getOldState()&&time<(recipe.getBoilTime()-1)))
		   	   			 {
		   	   				 obj.put("SF", "e");
		   	   				 obj.put("st", -2);
		   	   			 }
		   	   			 else if(type==Constants.State.END_DECOCT_SCAN.getOldState()&&time>(recipe.getBoilTime()+Constants.TOLERANCE_RANGE))
		   	   			 {
		   	   				ExceptionCase exception = new ExceptionCase();
		   	   				exception.setExceptionType(1);
		   	   				exception.setRecipeId(recipe.getId());
		   	   				exception.setCreatorId(object.getIntValue("EI"));
		   	   				exception.setExceptionTime(new Date());
		   	   				exception.setExceptionComment("处方"+recipeCode+"煎煮超时"+time+"分钟");
		   	   				exceptionService.saveExceptionCaseByApp(exception);
			   	   			obj.put("id", recipe.getId());
			   	   			obj.put("s", recipe.getShippingState());
			   	   			obj.put("st", recipe.getSoakTime());
			   	   			obj.put("bt", recipe.getBoilTime());
			   	   			obj.put("mi", recipe.getMachineId());
			   	   			obj.put("SF", "1");
		   	   			 }
		   	   			 else
		   	   			 {
			   	   			 obj.put("id", recipe.getId());
			   	   			 obj.put("s", recipe.getShippingState());
			   	   			 obj.put("st", recipe.getSoakTime());
			   	   			 obj.put("bt", recipe.getBoilTime());
			   	   			 obj.put("mi", recipe.getMachineId());
			   	   			 obj.put("SF", "1");
		   	   			 }
		   	   		 }
		   	   	 }
		   	   	 else
		   	   	 {
		   	   		 obj.put("SF", "0");
		   	   	 }
			}
		}catch(JSONException e)
		{
			try {
				obj.put("rs","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			obj.put("rs","-1");
			e.printStackTrace();
		}
		return obj;
	}
	//保存图片
	private JSONObject savePhoto(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		log.info("savePhoto-------------------savePhoto");
		response.setContentType("text/html;charset=UTF-8");
		String tenant=getTenantForMobile();//账套
		// 保存文件
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxPostSize);
		String fileName = null;
		RecipeScan scan = new RecipeScan();
		JSONObject obj= new JSONObject();
		log.info("id=="+request.getParameter("id"));
		try {
			MultipartHttpServletRequest multipartRequest =
	                WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
			MultipartFile file = multipartRequest.getFile("myfile");
			
			List fileItems = upload.parseRequest(request);
			log.info("fileItems=="+fileItems);
			Iterator iter = fileItems.iterator();
			fileName=request.getParameter("id").toString()+'_'+UUID.randomUUID().toString();
			scan.setId(Long.parseLong(request.getParameter("id")));
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					if("id".equals(item.getFieldName())){
						scan.setId(Long.parseLong(item.getString()));
						fileName=item.getString()+'_'+UUID.randomUUID().toString();
					}
					log.info(item.getFieldName()+":"+item.getString());
				}
			}
			log.info("fileName=="+fileName);
			String filePath = path + File.separator + tenant + File.separator + "appImg";
	        String storePath = "\\" + tenant +
	                "\\" + "appImg" + "\\" + fileName + ".jpg";
	        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath, fileName + ".jpg"));
//			file.transferTo(new File(path+storePath));
			scan.setPicturePath(storePath);
			recipeService.updateRecipeScan(scan);
		}catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage() + "保存图片失败！");
		}
		return obj;
	}
	//开始二煎扫描
	private JSONObject startSecondBoilScan(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("startSecondBoilScan-------------------startSecondBoilScan");
		JSONObject obj= new JSONObject();
		try{
			String tenant=getTenantForMobile();//账套
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				RecipeDO record = new RecipeDO();
				record.setRecipeCode(object.getString("rc"));
				record.setShippingState(object.getIntValue("st"));
				String machineCode = object.getString("mc");
				MachineDO machineDo = new MachineDO();
				machineDo.setMachineCode(machineCode);
				List<MachineDO> list = machineService.listMachinesByCode(machineDo);
				if(list==null||list.size()==0)
				{
					obj.put("SF", "m0");
					return obj;
				}
				else
				{
					MachineDO machine = (MachineDO)list.get(0);
					if(machine.getMachineStatus()!=null&&machine.getMachineStatus()==9)
					{
						obj.put("SF", "m9");
						return obj;
					}
			   	   	 RecipeDO recipe = recipeService.checkRecipeStatus(record);
			   	   	 if(recipe==null)
			   	   	 {
			   	   		 obj.put("SF", "0");
			   	   	 }
	
			   	   	 else if(!Objects.equals(recipe.getShippingState(), Constants.State.START_DECOCT_SCAN.getNewState())
			   	   			 && !Objects.equals(recipe.getShippingState(), Constants.State.START_AFTER_BOIL.getNewState()))
			   	   	 {
			   	   		 obj.put("SF", "e");
			   	   		 obj.put("st", Constants.State.START_SECOND_BOIL.getOldState());
			   	   	 }
			   	   	 else if(Objects.equals(recipe.getShippingState(), Constants.State.START_DECOCT_SCAN.getNewState())
			   	   			 || Objects.equals(recipe.getShippingState(), Constants.State.START_AFTER_BOIL.getNewState()))
			   	   	 {
			   	   		 recipe.setScanUser(object.getIntValue("EI"));
			   	   		 recipe.setShippingState(Constants.StateMap.getState(record.getShippingState()));
			   	   		 recipe.setMachineId(machine.getId());
			   	   		 recipe.setMachineCode(machineCode);
			   	   		 recipe.setBoilId(object.getIntValue("EI"));
		   	   			 recipe.setSecondBoilId(recipe.getBoilId());
		   	   			 recipe.setStartSecondBoilTime(new Date());
		   	   			 recipeService.updateRecipeStatus(recipe);
		   	   			 obj.put("s", Constants.StateMap.getState(record.getShippingState()));
			   	   		 obj.put("SF", "1");
			   	   		 obj.put("id", recipe.getId());
			   	   		 obj.put("rn", recipe.getRecipientName());
			   	   		 obj.put("q", recipe.getQuantity());
			   	   	 }
				}
			}
		}catch(JSONException e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}catch (Exception e) {
			obj.put("SF","-1");
			e.printStackTrace();
		}
		return obj;
	}
	//开始煎药扫描
	private JSONObject startDecoctScan(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("decoctScan-------------------decoctScan");
		JSONObject obj= new JSONObject();
		try{
			String tenant=getTenantForMobile();//账套
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				RecipeDO record = new RecipeDO();
				record.setRecipeCode(object.getString("rc"));
				record.setShippingState(object.getIntValue("st"));
				String machineCode = object.getString("mc");
				MachineDO machineDo = new MachineDO();
				machineDo.setMachineCode(machineCode);
				List<MachineDO> list = machineService.listMachinesByCode(machineDo);
				if(list==null||list.size()==0)
				{
					obj.put("SF", "m0");
					return obj;
				}
				else
				{
					MachineDO machine = (MachineDO)list.get(0);
//					if(machine.getMachineStatus()==1)
//					{
//						obj.put("SF", "me");
//						outObj(obj.toString(), response);
//						return;
//					}
					if(machine.getMachineStatus()!=null&&machine.getMachineStatus()==9)
					{
						obj.put("SF", "m9");
						return obj;
					}
			   	   	 RecipeDO recipe = recipeService.checkRecipeStatus(record);
			   	   	 if(recipe==null)
			   	   	 {
			   	   		 obj.put("SF", "0");
			   	   	 }
			   	   	 else if((recipe.getBoilType()==Constants.ZJ||recipe.getBoilType()==Constants.GFZJ)&&record.getShippingState()>Constants.State.RE_CHECK_SCAN.getOldState()
			   	   			 && !Objects.equals(record.getShippingState(), Constants.State.PACK_SCAN.getOldState()))
			   	   	 {
			   	   		 obj.put("SF", "e");
			   	   		 obj.put("st", -1);
			   	   	 }
			   	   	 else if(recipe.getShippingState()!=Constants.StateMap.getState((record.getShippingState()-1))
			   	   			 && !Objects.equals(recipe.getShippingState(), Constants.State.START_FRIST_BOIL.getNewState()))
			   	   	 {
			   	   		 obj.put("SF", "e");
			   	   		 obj.put("st", Constants.State.START_FRIST_BOIL.getOldState());
			   	   	 }
			   	   	 else if(recipe.getShippingState()==Constants.StateMap.getState((record.getShippingState()-1))
			   	   			 || Objects.equals(recipe.getShippingState(), Constants.State.START_FRIST_BOIL.getNewState()))
			   	   	 {
			   	   		 recipe.setScanUser(object.getIntValue("EI"));
			   	   		 recipe.setShippingState(Constants.StateMap.getState(record.getShippingState()));
			   	   		 recipe.setMachineId(machine.getId());
			   	   		 recipe.setMachineCode(machineCode);
			   	   		 recipe.setBoilId(object.getIntValue("EI"));
			   	   		 
			   	   		 if(record.getShippingState().intValue()==6){
			   	   			 recipe.setStartBoilId(recipe.getBoilId());
			   	   			 recipe.setStartBoilTime(new Date());
			   	   			 recipe.setBoilMachineId(recipe.getMachineId());
			   	   		 }
			   	   		 if(record.getShippingState().intValue()==7){
			   	   			 recipe.setEndBoilId(recipe.getBoilId());
			   	   			 recipe.setEndBoilTime(new Date());
			   	   		 }
			   	   		 
			   	   		 
			   	   		 recipeService.updateRecipeStatus(recipe);
				   	   	RecipeMedicine recipeMedicine = new RecipeMedicine();
			   	   		recipeMedicine.setSpecialBoilType("后下");
			   	   		recipeMedicine.setRecipeId(recipe.getId());
			   	   		List<RecipeMedicine> listM = recipeService.findRecipeMedicineBySpecialBoilType(recipeMedicine);
			   	   		if(listM!=null && listM.size()>0){
			   	   			obj.put("msg", "该处方已开始煎煮，并存在后下药品！");
			   	   		}else{
			   	   			obj.put("msg", "该处方已开始煎煮");
			   	   		}
			   	   		 /**
			   	   		  * 永安煎药机智能(开启/关闭)煎药机
			   	   		  * 扫描开始煎煮 发送开启煎药机指令33
			   	   		  * 扫描结束煎煮 发送关闭煎药机指令44
			   	   		  */
//					   	 machine.setOperateType(record.getShippingState());
//					   	 machine.setAreaId(recipe.getBoilTime());//借用字段存入煎药时间
//						 openCloseMachine(billServiceImpl, machine,tenant);
			   	   		 
			   	   		 obj.put("SF", "1");
			   	   		 obj.put("id", recipe.getId());
			   	   		 obj.put("rn", recipe.getRecipientName());
			   	   		 obj.put("q", recipe.getQuantity());
//			   	   		 obj.put("mac", machine.getSpMac());
			   	   	 }
				}
			}
		}catch(JSONException e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}catch (Exception e) {
			obj.put("SF","-1");
			e.printStackTrace();
		}
		return obj;
	}
	
	//结束煎药扫描
	private JSONObject endDecoctScan(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("endDecoctScan-------------------endDecoctScan");
		JSONObject obj= new JSONObject();
		try{
			String tenant=getTenantForMobile();//账套
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				RecipeDO record = new RecipeDO();
				record.setRecipeCode(object.getString("rc"));
				record.setShippingState(object.getIntValue("st"));
				record.setScanUser(object.getIntValue("EI"));
				String machineCode = object.getString("mc");
				MachineDO machineDo = new MachineDO();
				machineDo.setMachineCode(machineCode);
				List<MachineDO> list = machineService.listMachinesByCode(machineDo);
				if(list==null||list.size()==0)
				{
					obj.put("SF", "m0");
				}
				else
				{
					MachineDO machine = (MachineDO)list.get(0);
			   	   	RecipeDO recipe = recipeService.checkRecipeStatus(record);
			   	   	log.info("st=="+record.getShippingState());
			   	   	if(machine.getMachineCode()==null){
			   	   		machine.setMachineCode("");
			   	   	}
			   	   	if(recipe!=null&&recipe.getMachineCode()==null){
			   	   		recipe.setMachineCode("");
			   	   	}
			   	   	if(recipe==null)
			   	   	{
			   	   		obj.put("SF", "0");
			   	   	}else if(!machine.getMachineCode().equals(recipe.getMachineCode())){
				   	   	obj.put("SF", "m1");
			   	   	}
			   	   	else if(recipe.getShippingState()!=Constants.StateMap.getState((record.getShippingState()-1)))
			   	   	{
			   	   		obj.put("SF", "e");
			   	   		obj.put("st", recipe.getShippingState());
			   	   	}
			   	   	else if(recipe.getShippingState()==Constants.StateMap.getState((record.getShippingState()-1)))
			   	   	{
			   	   		 //计算已经煎煮了几分钟
			   	   		Long time = (Long)((System.currentTimeMillis()-recipe.getReceiveScanTime().getTime())/ (1000 * 60));
				   	   	recipe.setActualBoilTime(time.intValue());
				   	   	recipe.setScanUser(record.getScanUser());
						recipe.setEndBoilId(recipe.getScanUser());
						recipe.setEndBoilTime(new Date());
						recipe.setShippingState(Constants.StateMap.getState(record.getShippingState()));
						//更新状态
						int rows=recipeService.updateRecipeStatus(recipe);
			   	   		obj.put("sid", rows);
						//插入中间表数据，供包装打印（一体机）使用
						recipeService.insertRecipePackingPrint(recipe);
						/**
			   	   		  * 永安煎药机智能(开启/关闭)煎药机
			   	   		  * 扫描开始煎煮 发送开启煎药机指令33
			   	   		  * 扫描结束煎煮 发送关闭煎药机指令44
			   	   		  */
//						machine.setOperateType(record.getShippingState());
//						machine.setAreaId(recipe.getBoilTime());//借用字段存入煎药时间
//						openCloseMachine(billServiceImpl, machine,tenant);
						
						obj.put("SF","1");
						obj.put("id", recipe.getId());
						obj.put("s", Constants.StateMap.getState(record.getShippingState()));
//						obj.put("mac", recipe.getSpMac());
						obj.put("rn", recipe.getRecipientName());
			   	   		obj.put("q", recipe.getQuantity());
			   	   	 }
				}
			}
		}catch(JSONException e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}catch (Exception e) {
			obj.put("SF","-1");
			e.printStackTrace();
		}
		return obj;
	}
	//包装扫描
	private JSONObject packScan(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("packScan-------------------packScan");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				RecipeDO record = new RecipeDO();
				record.setRecipeCode(object.getString("rc"));
				record.setShippingState(object.getIntValue("st"));
				RecipeDO recipe = recipeService.checkRecipeStatus(record);
		   	   	 if(recipe==null)
		   	   	 {
		   	   		 obj.put("SF", "0");
		   	   	 }
		   	   	 else if((recipe.getBoilType()==Constants.ZJ||recipe.getBoilType()==Constants.GFZJ)
		   	   			 &&Constants.StateMap.getState(record.getShippingState())>Constants.State.RE_CHECK_SCAN.getNewState()
		   	   			 &&Constants.StateMap.getState(record.getShippingState())!=Constants.State.PACK_SCAN.getNewState())
		   	   	 {
		   	   		 obj.put("SF", "e");
		   	   		 obj.put("st", -1);
		   	   	 }
		   	   	 else if(recipe.getBoilType()!=Constants.GF&&Constants.StateMap.getState(record.getShippingState())==Constants.State.BOIL_SCAN.getNewState())
		   	   	 {
		   	   		 obj.put("SF", "e");
		   	   		 obj.put("st", -4);
		   	   	 }
		   	   	 else if(!Objects.equals(recipe.getShippingState(), Constants.State.END_DECOCT_SCAN.getNewState())
		   	   			 && !Objects.equals(recipe.getShippingState(), Constants.State.CONCENTRATE_END.getNewState()) &&recipe.getBoilType()==Constants.DJ)
		   	   	 {
		   	   		 obj.put("SF", "e");
		   	   		 obj.put("st", recipe.getShippingState());
		   	   	 }
		   	   	 else if(!Objects.equals(recipe.getShippingState(), Constants.State.BOIL_SCAN.getNewState()) && !Objects.equals(recipe.getShippingState(), Constants.State.CONCENTRATE_END.getNewState()) &&recipe.getBoilType()==Constants.GF)
		   	   	 {
		   	   		 obj.put("SF", "e");
		   	   		 obj.put("st", recipe.getShippingState());
		   	   	 }
		   	   	 else
		   	   	 {
		   	   		 recipe.setScanUser(object.getIntValue("EI"));
		   	   		 recipe.setShippingState(Constants.StateMap.getState(record.getShippingState()));
		   	   		 if(Objects.equals(record.getShippingState(), Constants.State.END_DECOCT_SCAN.getOldState())) {
                         recipe.setMachineId(object.getIntValue("mi"));
                     }
		   	   		 
		   	   		 if(Objects.equals(record.getShippingState(), Constants.State.PACK_SCAN.getOldState())){
		   	   			 recipe.setPackageId(recipe.getScanUser());
		   	   			 recipe.setPackageTime(new Date());
		   	   		 }
		   	   		 
		   	   		 
		   	   		 int rows = recipeService.updateRecipeStatus(recipe);
		   	   		 obj.put("SF", "1");
		   	   		 obj.put("id", recipe.getId());
		   	   		 obj.put("sid", rows);
		   	   	 }
			}
		}catch(JSONException e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}catch (Exception e) {
			obj.put("SF","-1");
			e.printStackTrace();
		}
		return obj;
	}
	//处方历史
	private JSONObject recipeHistory(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("recipeHistory-------------------recipeHistory");
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				RecipeDO recipe = new RecipeDO();
				recipe.setRecipeCode(object.getString("rc"));
				List<RecipeScan> list = recipeService.findRecipeHistory(recipe);
				if(list==null||list.size()==0){
					obj.put("SF","0");
				}else{
					JSONArray array = new JSONArray();
					for(int i=0;i<list.size();i++)
					{
						RecipeScan recipeScan = (RecipeScan)list.get(i);
						JSONObject scan = new JSONObject();
						scan.put("t",format.format(recipeScan.getReceiveScanTime()));
						scan.put("e", recipeScan.getEmployeeName());
						scan.put("op",  Constants.OldStateMap.getOldState(recipeScan.getOperateType()));
						if(Objects.equals(recipeScan.getOperateType(), Constants.State.SOAK_SCAN.getNewState()) && Objects.equals(recipeScan.getShippingState(), Constants.State.SOAK_SCAN.getNewState()))
						{
							int time = (int)((System.currentTimeMillis()-recipeScan.getReceiveScanTime().getTime())/ (1000 * 60));
							scan.put("sti", recipeScan.getSoakTime() - time);
						}
						if(Objects.equals(recipeScan.getOperateType(), Constants.State.START_DECOCT_SCAN.getNewState()) && Objects.equals(recipeScan.getShippingState(), Constants.State.START_DECOCT_SCAN.getNewState()))
						{
							int time = (int)((System.currentTimeMillis()-recipeScan.getReceiveScanTime().getTime())/ (1000 * 60));
							obj.put("sti", recipeScan.getBoilTime() - time);
						}
						array.add(scan);
					}
					RecipeScan scan = (RecipeScan)list.get(list.size()-1);
					obj.put("msg", scan.getHospitalNickname()!=null?scan.getHospitalNickname():""+"医院\n姓名"+scan.getRecipientName()+" "+scan.getQuantity()+"贴药");
					obj.put("SF", "1");
					obj.put("ar", array);
				}
				
			}
		}catch(JSONException e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	private JSONObject recipePatrolScan(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("recipePatrolScan-------------------recipePatrolScan");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				RecipeDO recipe = new RecipeDO();
				recipe.setRecipeCode(object.getString("rc"));
				List<RecipeDO> list = recipeService.findRecipeScanList(recipe);
				if(list==null||list.size()==0){
					obj.put("SF","0");
				}else{
					RecipeDO recipe2 = (RecipeDO)list.get(0);
					obj.put("h", recipe2.getHospitalNickname());
					obj.put("rn", recipe2.getRecipientName());
					obj.put("q", recipe2.getQuantity());
					obj.put("w", recipe2.getTotalWeight());
//					obj.put("w", recipe2.getTotalWeight().multiply(new BigDecimal(recipe2.getQuantity())));
					obj.put("rId", recipe2.getId());
					if(recipe2.getPackType()!=null&&recipe2.getPackType().indexOf("浓")>-1)
					{
						obj.put("ml", 100*recipe2.getQuantity()*recipe2.getPackagePaste()+100);
					}else{
						obj.put("ml", 200*recipe2.getQuantity()*recipe2.getPackagePaste()+200);
					}
					for(int i=0;i<list.size();i++)
					{
						RecipeDO r = (RecipeDO)list.get(i);
						if(r.getOperateType()==Constants.STATE_DISPENSE_SCAN)
						{
							obj.put("pf", r.getEmployeeName());
						}
						else if(r.getOperateType()==Constants.STATE_RE_CHECK_SCAN)
						{
							obj.put("ck", r.getEmployeeName());
						}
						else if(r.getOperateType()==Constants.STATE_START_DECOCT_SCAN)
						{
							obj.put("dc", r.getEmployeeName());
							obj.put("m", r.getMachineCode());
						}
					}
					obj.put("SF", "1");
				}
				
			}
		}catch(JSONException e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	private JSONObject addPatrol(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("addPatrol-------------------addPatrol");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				RecipePatrol patrol = new RecipePatrol();
				patrol.setRecipeId(object.getIntValue("rId"));
				patrol.setOperateType(object.getIntValue("t"));
				patrol.setOperateId(object.getIntValue("e"));
				patrol.setPlanAmount(new BigDecimal(object.getDouble("PA")));
				patrol.setActualAmount(new BigDecimal(object.getDouble("AA")));
				patrol.setPatrolStatus(object.getIntValue("ps"));
				patrol.setPatrolResult(URLDecoder.decode(object.getString("pr"),"UTF-8"));
				patrol.setOperateTime(new Date());
				if(patrol.getOperateType()==1)
				{
					patrol.setActualKind(object.getIntValue("ak"));
				}
				int count = recipeService.addRecipePatrol(patrol);
				if(count>0) {
                    obj.put("SF", "1");
                }
			}
		}catch(JSONException e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	public JSONObject getVersion(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("Version-------------------Version");
		JSONObject obj= new JSONObject();
		try{
			String tenant=getTenantForMobile();//账套
			List<SysConfig> list = sysService.listConfig();
			if(list!=null&&list.size()>0)
			{
				SysConfig version = getSystemConfig(list, SystemConstant.TCMPHONE_VERSION_NUMBER);
				if(version != null){
					obj.put("v",version.getValue());
				}
				SysConfig tcpUser = getSystemConfig(list, SystemConstant.TCMPHONE_USER);
				if(tcpUser != null){
					obj.put("t",tcpUser.getValue());
					Constants.userType.put(tenant, tcpUser.getValue());
				}
				SysConfig picture = getSystemConfig(list, SystemConstant.TAKE_A_PICTURE_WHILE_CHECK);
				if(picture != null){
					obj.put("p", "Y".equals(picture.getValue().toString()) ?"1":"0");
					Constants.userType.put(tenant+"p", "Y".equals(picture.getValue().toString()) ?"1":"0");
				}else{
					obj.put("p","1");
					Constants.userType.put(tenant+"p","1");
				}
				SysConfig ptp = getSystemConfig(list, SystemConstant.PACKING_TAG_PRINTER);
				if(ptp != null){
					log.info("ptp.getPropertyValue=="+ptp.getValue());
					obj.put("b", "Y".equals(ptp.getValue().toString()) ?"1":"0");
					Constants.userType.put(tenant+"b", "Y".equals(ptp.getValue().toString()) ?"1":"0");
				}else{
					obj.put("b","0");
					Constants.userType.put(tenant+"b","0");
				}
				SysConfig printConfig = getSystemConfig(list, SystemConstant.SHOW_PRINT_SC);
				if(printConfig!=null){
					obj.put("pt", "Y".equals(printConfig.getValue().toString()) ?"1":"0");
				}else{
					obj.put("pt","0");
				}
			}
		}catch(Exception e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	/*获取药房、仓库
	  请求参数
	  参数格式：无
	  返回结果集:
	  成功:{"SF"：1,”l”:[”id”:仓库id，”name”:名称，”type”:类型]}
	  无数据:{"SF"：0}
	*/
	private JSONObject getWarehouse(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("getWarehouse-------------------getWarehouse");
		JSONObject obj= new JSONObject();
		try{
	   	   	List<WarehouseDO> list = warehouseService.listWarehouse(new WarehouseDTO());
	   	   	 
	   	   	if(list!=null&&list.size()>0)
	   	   	{
	   	   		JSONArray array = new JSONArray();
	   	   		JSONObject wareJ= null;
	   	   		WarehouseDO warehouse = null;
	   	   		 
	   	   		for(int i=0;i<list.size();i++){
	   	   			wareJ = new JSONObject();
	   	   			warehouse = (WarehouseDO)list.get(i);
	   	   			wareJ.put("id",warehouse.getId());
	   	   			wareJ.put("name",warehouse.getWarehouseName());
	   	   			wareJ.put("type",warehouse.getWarehouseType());
	   	   			array.add(wareJ);
	   	   		}
	   	   		obj.put("l", array);
	   	   		obj.put("SF", "1");
	   	   	}
	   	   	else
	   	   	{
	   	   		 obj.put("SF", "0");
	   	   	}
		}catch(JSONException e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	/*根据条码获取药品信息
 	请求参数
 	参数格式：JSON
	参数：{"tm":条码}"
	返回结果集:
	成功:{"SF"：1,”mid”:药品id,”sid”:规格id，”name”:名称}
	无数据:{"SF"：-1}*/
	private JSONObject getMedicineByNumber(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("getMedicineByNumber-------------------getMedicineByNumber");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				String standardCode=object.getString("tm");
				MedicineStandardDO ms = new MedicineStandardDO();
				ms.setStandardCode(standardCode);
				ms = medicineStandardService.getMedicineByNumber(ms);
		   	   	if(ms!=null)
		   	   	{
		   	   		obj.put("mid", ms.getMedicineId());
		   	   		obj.put("sid", ms.getId());
		   	   		obj.put("name", ms.getStandardName());
		   	   		obj.put("SF", "1");
		   	   	}
		   	   	else
		   	   	{
		   	   		obj.put("SF", "0");
		   	   	}
			}
		}catch(JSONException e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	/*添加调拨申请
 	请求参数
 	参数格式：JSON
	参数：{"eid":员工id，"wid":仓库id，"did":药房id，"mid":药品id，"sid":规格id，"q":数量}"
	返回结果集:
	成功:{"SF"：1}
	失败:{"SF"：0}*/
	private JSONObject addWareOutApply(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("addWareOutApply-------------------addWareOutApply");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				Integer eid=object.getIntValue("eid");
				Integer wid=object.getIntValue("wid");
				Integer did=object.getIntValue("did");
				Integer mid=object.getIntValue("mid");
				Integer sid=object.getIntValue("sid");
				BigDecimal q=new BigDecimal(object.getString("q"));
				MedicineStandardDO ms = new MedicineStandardDO();
				ms.setEmployeeId(eid);
				ms.setWarehouseId(wid);
				ms.setDrugstoreId(did);
				ms.setMedicineId(mid);
				ms.setStandardId(sid);
				ms.setQuantity(q);
//				ms.setBillCode(warehouseService.getApplySysCode());
//				 
//				warehouseService.addWareOutApply(ms);
		   	   	 
		   	   	obj.put("SF", "1");
			}
		}catch(JSONException e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	/*1、查询处方和煎药机
	功能描述：通过员工id查询所属区域的“完成浸泡”并且“未派工”的处方列表和空闲的煎药机列表
	参数：
	type-----------------操作类型----23
	eid------------------员工id
	返回格式：JSON
	返回内容：
	{"result":1,"recipe":[{"hn":市一医院,"rn":张三,"code":1},{"hn":市二医院,"rn":李四,"code":1}],"machine":[{"code":abc},{"code":xyz}]}
	属性解释：
	result---------是否获取成功（1表示成功，否则失败）
	recipe---------处方信息
	hn-------------医院
	rn-------------病人
	machine--------煎药机
	code-----------煎药机编号*/
	private JSONObject getZnpgList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("getZnpgList-------------------getZnpgList");
		JSONObject obj= new JSONObject();
		try{
			Integer eid=0;
			 
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				eid=object.getIntValue("eid");
				 
				RecipeDO re = recipeService.getZnpgList(eid);
				 
				if(re.getRecipeMedicineList()!=null&&re.getRecipeMedicineList().size()>0)
				{
					JSONArray array = new JSONArray();
					JSONObject stanJ= null;
					RecipeDO stan = null;
					 
					for(int i=0;i<re.getRecipeList().size();i++){
						stanJ = new JSONObject();
						stan = (RecipeDO)re.getRecipeList().get(i);
						 
						stanJ.put("hn",stan.getHospitalNickname());
						stanJ.put("rn",stan.getRecipientName());
						stanJ.put("code",stan.getRecipeCode());
						 
						 array.add(stanJ);
					}
					obj.put("recipe", array);
				}
				 
				if(re.getSelectedList()!=null&&re.getSelectedList().size()>0)
				{
					JSONArray array = new JSONArray();
					JSONObject stanJ= null;
					MachineDO stan = null;
					 
					for(int i=0;i<re.getSelectedList().size();i++){
						stanJ = new JSONObject();
						stan = (MachineDO)re.getSelectedList().get(i);
						stanJ.put("code",stan.getMachineCode());
						array.add(stanJ);
					}
					obj.put("machine", array);
				}
				 
				obj.put("result", "1");
			}else{
				obj.put("result", "-1");
			}
			 
		}catch(JSONException e)
		{
			try {
				obj.put("result","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	//复核不通过
	private JSONObject checkNotThrough(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		log.info("checkNotThrough-------------------checkNotThrough");
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				RecipeDO record = new RecipeDO();
				record.setRecipeCode(object.getString("rc"));//处方号
				record.setScanUser(object.getIntValue("EI"));//扫描用户ID
				RecipeDO re = recipeService.checkRecipeStatus(record);
				if(re==null)
		   	   	{
		   	   		obj.put("SF", "0");
		   	   	}else{
		   	   		record.setId(re.getId());
		   	   		recipeService.addCheckScan(record);
		   	   		obj.put("SF","1");
		   	   		obj.put("s",Constants.State.DISPENSE_SCAN.getOldState());//复核不通过，返回已配药状态
		   	   	}
				
			}
			 
		}catch(Exception e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	//	开始浓缩
	 
	private JSONObject startConcentrate(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				RecipeDO record = new RecipeDO();
				record.setRecipeCode(object.getString("rc"));
				record.setScanUser(object.getIntValue("EI"));//扫描用户ID
				record.setStartConcentrateVolume(Float.valueOf(object.getString("volume")));
				RecipeDO re = recipeService.checkRecipeStatus(record);
		   	   	if(re==null){
		   	   		obj.put("SF", "0");
		   	   		if(!Objects.equals(re.getShippingState(), Constants.State.END_DECOCT_SCAN.getNewState()))
		   	   		{
		   	   			obj.put("SF", "e");
		   	   			obj.put("st", -1);
		   	   		}
		   	   	}
		   	   	else{
		   	   		record.setStartBoilTime(new Date());
		   	   		record.setModUser(record.getScanUser());
		   	   		record.setModTime(new Date());
		   	   		record.setShippingState(Constants.State.CONCENTRATE_START.getNewState());
		   	   		record.setId(re.getId());
		   	   		record.setQuantity(re.getQuantity());
		   	   		List<RecipeScan> list = recipeService.listStartVolume(record);
		   	   		if(list!=null&&list.size()>0){
			   	   		obj.put("SF", "e");
		   	   			obj.put("st", -1);
		   	   		}else{
		   	   			recipeService.updateConcentrateStatus(record);
		   	   			obj.put("SF", "1");
		   	   			obj.put("s", Constants.State.CONCENTRATE_START.getOldState());
		   	   		}
		   	   	}
			}
		}catch(Exception e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	//结束浓缩
	private JSONObject endConcentrate(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		log.info("endConcentrate-------------endConcentrate");
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
				if(object!=null){
					RecipeDO record = new RecipeDO();
					record.setRecipeCode(object.getString("rc"));
					record.setScanUser(object.getIntValue("EI"));//扫描用户ID
					record.setEndConcentrateVolume(Float.valueOf(object.getString("volume")));
					RecipeDO re = recipeService.checkRecipeStatus(record);
			   	   	if(re==null){
			   	   		obj.put("SF", "0");
			   	   		if(!Objects.equals(re.getShippingState(), Constants.State.CONCENTRATE_START.getNewState()))
			   	   		{
			   	   			obj.put("SF", "e");
			   	   			obj.put("st", -1);
			   	   		}
			   	   	}
			   	   	else{
			   	   		//获取系统配置
			   	   	    List<SysConfig> sysList = sysService.listConfig();
			   	   		//获取"是否需要沉淀"配置
			   	   		SysConfig subsideConfig = getSystemConfig(sysList, SystemConstant.MUST_SUBSIDE);
			   	   		if(subsideConfig!=null&&"Y".equals(subsideConfig.getValue())){
			   	   			record.setAddSubside(1);
			   	   		}
			   	   		record.setEndBoilTime(new Date());
			   	   		record.setModUser(record.getScanUser());
			   	   		record.setModTime(new Date());
			   	   		record.setShippingState(Constants.State.CONCENTRATE_END.getNewState());
			   	   		record.setId(re.getId());
			   	   		record.setQuantity(re.getQuantity());
			   	   		recipeService.updateConcentrateStatus(record);
				   	   	List<RecipeScan> list = recipeService.listStartVolume(record);
				   	   	RecipeScan reV = (RecipeScan)list.get(0);
			   	   		if(reV!=null&&reV.getStartConcentrateVolume()<=record.getEndConcentrateVolume()){
				   	   		obj.put("SF", "e");
			   	   			obj.put("st", -1);
			   	   		}
			   	   		obj.put("SF", "1");
			   	   	}
				}
		}catch(Exception e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	//先煎后下药品
	private JSONObject getFristBoillMedicine(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		log.info("xj====hx");
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				RecipeDO record = new RecipeDO();
				record.setRecipeCode(object.getString("rc"));//处方号
				record.setScanUser(object.getIntValue("EI"));//扫描用户ID
				RecipeDO re = recipeService.getRecipeAndXjMedicine(record);
				if(re==null)
		   	   	{
		   	   		obj.put("SF", "0");
		   	   	}else{
		   	   		if(Objects.equals(re.getShippingState(), Constants.State.START_DECOCT_SCAN.getNewState()) || Objects.equals(re.getShippingState(), Constants.State.START_AFTER_BOIL.getNewState())){
		   	   			log.info("2====");
		   	   			RecipeDO recipeHx = recipeService.getRecipeAndHxMedicine(re);
			   	   		if(recipeHx.getActionResult()==11){
				   	   		JSONArray array = new JSONArray();
	   	   					if(recipeHx.getMedicineList()!=null&&recipeHx.getMedicineList().size()>0){
	   	   						List<MedicineDO> list=recipeHx.getMedicineList();
	   	   						JSONObject stanJ= null;
	   	   						for(MedicineDO xjMedicine:list){
	   	   							stanJ=new JSONObject();
	//						 log.info(" xjMedicine.getMedicineName()=="+ xjMedicine.getMedicineName());
	   	   							stanJ.put("medicineName", xjMedicine.getMedicineName());
	   	   							stanJ.put("fristBoilTime", xjMedicine.getOperateTime());
	   	   							array.add(stanJ);
	   	   						}
	   	   					}
	   	   					obj.put("xjMedicine", array);
	   	   					obj.put("SF","22");//表示该处方未配方补录，返回系统里所有后下药品
	   	   					log.info("obj.toString()=="+obj.toString());
			   	   		}else if(recipeHx.getActionResult()==12){
				   	   		if(Objects.equals(recipeHx.getShippingState(), Constants.State.START_AFTER_BOIL.getNewState())){
		   	   					JSONArray array = new JSONArray();
		   	   					if(recipeHx.getSelectedList()!=null&&recipeHx.getSelectedList().size()>0){
		   	   						List<MedicineDO> list=recipeHx.getSelectedList();
		   	   						JSONObject stanJ= null;
		   	   						for(MedicineDO xjMedicine:list){
		   	   							stanJ=new JSONObject();
		   	   							log.info(" hxMedicine.getMedicineName(2)=="+ xjMedicine.getMedicineName());
		   	   							stanJ.put("medicineName", xjMedicine.getMedicineName());
		   	   							stanJ.put("fristBoilTime", xjMedicine.getOperateTime());
		   	   							array.add(stanJ);
		   	   						}
		   	   					}
		   	   					obj.put("xjMedicine", array);
		   	   					obj.put("SF","11");
		   	   					obj.put("s",Constants.State.START_AFTER_BOIL.getOldState());//已开始后下，返回查看页面
		   	   				}else{
			   	   				JSONArray array = new JSONArray();
		   	   					if(recipeHx.getSelectedList()!=null&&recipeHx.getSelectedList().size()>0){
		   	   						List<MedicineDO> list=recipeHx.getSelectedList();
		   	   						JSONObject stanJ= null;
		   	   						for(MedicineDO xjMedicine:list){
		   	   							stanJ=new JSONObject();
		   	   							log.info(" hxMedicine.getMedicineName()=="+ xjMedicine.getMedicineName());
		   	   							stanJ.put("medicineName", xjMedicine.getMedicineName());
		   	   							stanJ.put("fristBoilTime", xjMedicine.getOperateTime());
		   	   							array.add(stanJ);
		   	   						}
		   	   					}
		   	   					obj.put("xjMedicine", array);
		   	   					obj.put("SF","44");//表示该处方已配方补录，配方里有后下药品，
		   	   				}
			   	   		}else if(recipeHx.getActionResult()==13){
			   	   			log.info("1====");
			   	   			obj.put("SF","33");//表示该处方已配方补录，但配方里没后下药品，
			   	   		}
		   	   		}else{
		   	   			if(re.getActionResult()==11){
		   	   				if(Objects.equals(re.getShippingState(), Constants.State.SOAK_SCAN.getNewState())){
		   	   					JSONArray array = new JSONArray();
		   	   					if(re.getMedicineList()!=null&&re.getMedicineList().size()>0){
		   	   						List<MedicineDO> list=re.getMedicineList();
		   	   						JSONObject stanJ= null;
		   	   						for(MedicineDO xjMedicine:list){
		   	   							stanJ=new JSONObject();
//								 log.info(" xjMedicine.getMedicineName()=="+ xjMedicine.getMedicineName());
		   	   							stanJ.put("medicineName", xjMedicine.getMedicineName());
		   	   							stanJ.put("fristBoilTime", xjMedicine.getOperateTime());
		   	   							array.add(stanJ);
		   	   						}
		   	   					}
		   	   					obj.put("xjMedicine", array);
		   	   					obj.put("SF","2");//表示该处方未配方补录，返回系统里所有先煎药品
		   	   					log.info("obj.toString()=="+obj.toString());
		   	   				}else if(re.getShippingState()>Constants.State.SOAK_SCAN.getNewState()&& !Objects.equals(re.getShippingState(), Constants.State.START_FRIST_BOIL.getNewState())){
		   	   					obj.put("SF","-1");//表示该处方已开始煎药
		   	   				}else if(re.getShippingState()<Constants.State.SOAK_SCAN.getNewState()){
		   	   					obj.put("SF","5");//表示该处方未浸泡
		   	   				}
		   	   			}else if(re.getActionResult()==13){
		   	   				obj.put("SF","3");//表示该处方已配方补录，但配方里没先煎药品，
		   	   			}else if(re.getActionResult()==12){
		   	   				if(re.getShippingState()>Constants.State.SOAK_SCAN.getNewState()&& !Objects.equals(re.getShippingState(), Constants.State.START_FRIST_BOIL.getNewState())){
		   	   					obj.put("SF","-1");//表示该处方已开始煎药
		   	   				}else if(re.getShippingState()<Constants.State.SOAK_SCAN.getNewState()){
		   	   					obj.put("SF","5");//表示该处方未浸泡
		   	   				}
		   	   				else if(Objects.equals(re.getShippingState(), Constants.State.START_FRIST_BOIL.getNewState())){
		   	   					JSONArray array = new JSONArray();
		   	   					if(re.getSelectedList()!=null&&re.getSelectedList().size()>0){
		   	   						List<MedicineDO> list=re.getSelectedList();
		   	   						JSONObject stanJ= null;
		   	   						for(MedicineDO xjMedicine:list){
		   	   							stanJ=new JSONObject();
		   	   							log.info(" xjMedicine.getMedicineName()=="+ xjMedicine.getMedicineName());
		   	   							stanJ.put("medicineName", xjMedicine.getMedicineName());
		   	   							stanJ.put("fristBoilTime", xjMedicine.getOperateTime());
		   	   							array.add(stanJ);
		   	   						}
		   	   					}
		   	   					obj.put("xjMedicine", array);
		   	   					obj.put("SF","1");
		   	   					obj.put("s",Constants.State.START_FRIST_BOIL.getOldState());//已开始先煎，返回查看页面
		   	   				}else{
		   	   					JSONArray array = new JSONArray();
		   	   					if(re.getSelectedList()!=null&&re.getSelectedList().size()>0){
		   	   						List<MedicineDO> list=re.getSelectedList();
		   	   						JSONObject stanJ= null;
		   	   						for(MedicineDO xjMedicine:list){
		   	   							stanJ=new JSONObject();
		   	   							log.info(" xjMedicine.getMedicineName()=="+ xjMedicine.getMedicineName());
		   	   							stanJ.put("medicineName", xjMedicine.getMedicineName());
		   	   							stanJ.put("fristBoilTime", xjMedicine.getOperateTime());
		   	   							array.add(stanJ);
		   	   						}
		   	   					}
		   	   					obj.put("xjMedicine", array);
		   	   					obj.put("SF","4");//表示该处方已配方补录，配方里有先煎药品，
		   	   				}
		   	   			}
		   	   		}
		   	   		
		   	   	}
				
			}
			 
		}catch(Exception e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	//保存先煎药品
	private JSONObject saveFristBoillMedicine(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
//			log.info("object=="+object.toString()); 
//			log.info("selectedArray=="+object.getJSONArray("selectedArray"));
			String medicineStr="";
			if(object!=null){
				JSONArray medicineArray=object.getJSONArray("selectedArray");
				JSONObject medicine=null;
				if(medicineArray != null && medicineArray.size()>0){
                    for(int i=0;i<medicineArray.size();i++){
						medicine = medicineArray.getJSONObject(i);
						if("".equals(medicineStr)){
							medicineStr=medicine.get("medicineName").toString();
						}else{
							medicineStr = medicineStr+","+medicine.get("medicineName").toString();
						}
						log.info("medicineStr=="+medicineStr);
					}
				}
				RecipeDO record = new RecipeDO();
				record.setRecipeCode(object.getString("rc"));//处方号
				record.setScanUser(object.getIntValue("EI"));//扫描用户ID
				record.setRemark(medicineStr);
				record.setShippingState(Constants.State.START_FRIST_BOIL.getNewState());
				RecipeDO re = recipeService.saveRecipeAndXjMedicine(record);
				if(re==null)
		   	   	{
		   	   		obj.put("SF", "0");
		   	   	}else{
		   	   		record.setId(re.getId());
//		   	   		billServiceImpl.addCheckScan(record);
		   	   		obj.put("SF","1");
		   	   		obj.put("s",Constants.State.START_FRIST_BOIL.getOldState());//开始先煎
		   	   	}
				
			}
			 
		}catch(Exception e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	/*
	 * 保存后下药品
	 * */
	private JSONObject saveAfterBoillMedicine(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			String medicineStr="";
			if(object!=null){
				JSONArray medicineArray=object.getJSONArray("selectedArray");
				JSONObject medicine=null;
				if(medicineArray!=null&&medicineArray.size()>0){
					for(int i=0;i<medicineArray.size();i++){
						medicine = medicineArray.getJSONObject(i);
						if("".equals(medicineStr)){
							medicineStr=medicine.get("medicineName").toString();
						}else{
							medicineStr = medicineStr+","+medicine.get("medicineName").toString();
						}
						log.info("medicineStr=="+medicineStr);
					}
				}
				RecipeDO record = new RecipeDO();
				record.setRecipeCode(object.getString("rc"));//处方号
				record.setScanUser(object.getIntValue("EI"));//扫描用户ID
				record.setRemark(medicineStr);
				RecipeDO re = recipeService.saveRecipeAndhxMedicine(record);
				if(re==null)
		   	   	{
		   	   		obj.put("SF", "0");
		   	   	}else{
		   	   		record.setId(re.getId());
		   	   		obj.put("SF","1");
		   	   		obj.put("s",Constants.State.START_AFTER_BOIL.getNewState());//开始后下
		   	   	}
				
			}
			 
		}catch(Exception e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
		return obj;
	}
	//发货
	private JSONObject deliveryScan(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("deliveryScan-------------------deliveryScan");
		JSONObject obj= new JSONObject();
		try{
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				RecipeDO record = new RecipeDO();
				record.setRecipeCode(object.getString("rc"));
				String expressCode = object.getString("ec").trim();
//				log.info("expressCode=="+expressCode);
				RecipeDO recipe = recipeService.checkRecipeStatus(record);
//				log.info("recipe.getSfCode()=="+recipe.getSfCode());
				if(recipe==null)
				{
					obj.put("SF", "0");
				}
				else
				{
					 if(recipe.getLogisticsCode()!=null&&!"".equals(recipe.getLogisticsCode())){
						 if(!recipe.getLogisticsCode().equals(expressCode)){
							 obj.put("SF", "e");
				   	   		 obj.put("id", recipe.getId());
				   	   		 obj.put("s", Constants.State.DELIVERY_BILL.getOldState());
						 }else{
							 recipe.setScanUser(object.getIntValue("EI"));
							 recipe.setShippingState(Constants.State.DELIVERY_BILL.getNewState());
							 recipe.setDeliveryId(recipe.getScanUser());
							 recipe.setDeliveryTime(new Date());
							 recipeService.updateRecipeStatus(recipe);
							 obj.put("SF", "1");
							 obj.put("id", recipe.getId());
							 obj.put("q", recipe.getQuantity());
							 obj.put("rn", recipe.getRecipientName());
							 obj.put("s", Constants.State.DELIVERY_BILL.getOldState());
						 }
					 }else{
						 recipe.setScanUser(object.getIntValue("EI"));
						 recipe.setShippingState(Constants.State.DELIVERY_BILL.getNewState());
						 recipe.setDeliveryId(recipe.getScanUser());
						 recipe.setDeliveryTime(new Date());
						 recipeService.updateRecipeStatus(recipe);
						 obj.put("SF", "1");
						 obj.put("id", recipe.getId());
						 obj.put("q", recipe.getQuantity());
						 obj.put("rn", recipe.getRecipientName());
						 obj.put("s", Constants.State.DELIVERY_BILL.getOldState());
					 }
				}
			}
		}catch(JSONException e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}catch (Exception e) {
			obj.put("SF","-1");
			e.printStackTrace();
		}
		return obj;
	}
	//领料
	public JSONObject recipePicking(HttpServletRequest request, HttpServletResponse response) {
		log.info("recipePicking-------------------recipePicking");
		JSONObject obj = new JSONObject();
		RecipeDO recipe = new RecipeDO();
		try {
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			String recipeCode = object.getString("rc");
			if (recipeCode == null || StringUtils.isBlank(recipeCode)) {
				obj.put("SF", "0");
			} else {
				recipe.setRecipeCode(recipeCode);
				/**
				 * 根据recipeCode查询处方状态
				 */
				recipe = recipeService.checkRecipeStatus(recipe);
				if (recipe == null) {
					obj.put("SF", "0");
				} else {
					if (Objects.equals(recipe.getShippingState(), Constants.State.RE_CHECK_SCAN.getNewState())) {
						/**
						 * 领料成功，修改领料状态，添加领料记录
						 */
						Integer id = object.getInteger("employeeId");
						RecipeDO re = new RecipeDO();
						re.setId(recipe.getId());
						re.setShippingState(Constants.State.RECIPE_PICKING.getNewState());
						re.setModTime(new Date());
						re.setModUser(id);
						Integer rows=recipeService.updateRecipeStatus(re);
						if (rows > 0) {
							obj.put("SF", "1");
							obj.put("msg", recipe.getRecipientName() + "的" + recipe.getQuantity() + "贴"
									+ recipe.getRecipeCode() + "处方领料成功！");
						} else {
							obj.put("SF", "-1");
							obj.put("errMsg", recipe.getRecipientName() + "的" + recipe.getQuantity() + "贴"
									+ recipe.getRecipeCode() + "处方领料失败！");
						}
						
					}else if (recipe.getShippingState() < Constants.State.DISPENSE_SCAN.getNewState() ) {
						obj.put("SF", "2");
						obj.put("errMsg", recipe.getRecipientName() + "的" + recipe.getQuantity() + "贴"
								+ recipe.getRecipeCode() + "处方还未配药，请先去配药！");
					} else if (Objects.equals(recipe.getShippingState(), Constants.State.DISPENSE_SCAN.getNewState())) {
						obj.put("SF", "2");
						obj.put("errMsg", recipe.getRecipientName() + "的" + recipe.getQuantity() + "贴"
								+ recipe.getRecipeCode() + "处方还未复核，请先去复核！");
					} else if (recipe.getShippingState() > Constants.State.RE_CHECK_SCAN.getNewState()) {
						obj.put("SF", "2");
						obj.put("errMsg", recipe.getRecipientName() + "的" + recipe.getQuantity() + "贴"
								+ recipe.getRecipeCode() + "处方已领料");
					} 
				}

			}
		} catch (JSONException e) {
			try {
				obj.put("SF", "0");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}catch (Exception e) {
			obj.put("SF","-1");
			e.printStackTrace();
		}
		return obj;
	}
	private JSONObject checkRecipe(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		log.info("checkRecipe-------------------checkRecipe");
		JSONObject obj= new JSONObject();
		try{
			String tenant=getTenantForMobile();//账套
			JSONObject object = MobileHandleHelper.parseRequestToObj(request);
			if(object!=null){
				//获取系统配置
	   	   	    List<SysConfig> sysList = sysService.listConfig();
	   	   		//获取"是否需要沉淀"配置
	   	   		SysConfig subsideConfig = getSystemConfig(sysList, SystemConstant.MUST_SUBSIDE);
	   	   		SysConfig subsideTimeConfig = getSystemConfig(sysList, SystemConstant.SUBSIDE_TIME);
	   	   		SysConfig forceTimeConfig = getSystemConfig(sysList, SystemConstant.ALLOW_FORCE_PASS);
	   	   		SysConfig checkBillConfig = getSystemConfig(sysList, SystemConstant.MUST_CHECK_BILL);
	   	   		SysConfig checkExpressConfig = getSystemConfig(sysList, SystemConstant.IS_CHECK_EXPRESSCODE);
	   	   		SysConfig deliveryConfig = getSystemConfig(sysList, SystemConstant.IS_DELIVERY_BILL);
	   	   		SysConfig recipe_picking = getSystemConfig(sysList, SystemConstant.IS_RECIPE_PICKING);
	   	   		SysConfig errorTimeConfig = getSystemConfig(sysList, SystemConstant.ERROR_TIME);
	   	   		boolean isHospital = sysPropertyUtil.getProperty(Constants.HOSPITAL_TYPE, "1").equals("0")?true:false;
				RecipeDO record = new RecipeDO();
				record.setRecipeCode(object.getString("rc"));
				RecipeDO recipe = recipeService.checkRecipeStatus(record);
				if(recipe==null)
		   	   	{
		   	   		obj.put("SF", "0");
		   	   	}
				else
				{
					log.info("后台获取状态=="+recipe.getShippingState());
					boolean isConcentrate = false;
					recipe.setScanUser(object.getIntValue("EI"));
					//向下一个流程推进一步
					if(Objects.equals(recipe.getShippingState(), Constants.State.INVALID.getNewState()))
					{
						
					}
					//自煎和膏方自煎，如果已复核接跳到打包流程
					else if((recipe.getBoilType()==Constants.ZJ||recipe.getBoilType()==Constants.GFZJ)&& Objects.equals(recipe.getShippingState(), Constants.State.RE_CHECK_SCAN.getNewState())) {
                        recipe.setShippingState(Constants.State.PACK_SCAN.getNewState());
                    }//已开单，进行配药流程
					else if(recipe.getShippingState()==1){
						if(checkBillConfig!=null&& "Y".equals(checkBillConfig.getValue())){
							recipe.setShippingState(Constants.State.CHECK_BILL.getNewState());
						}else{
							recipe.setShippingState(Constants.State.DISPENSE_SCAN.getNewState());
						}
					}else if(recipe.getBoilType()!=Constants.ZJ&&recipe.getBoilType()!=Constants.GFZJ&& Objects.equals(recipe.getShippingState(), Constants.State.RE_CHECK_SCAN.getNewState())){//已复核
						 //判断是否需要领料
						if(recipe_picking!=null && "Y".equals(recipe_picking.getValue())){
							recipe.setShippingState(Constants.State.RECIPE_PICKING.getOldState());
							obj.put("SF", "e");
				   	   		obj.put("st", recipe.getShippingState());
						}else{
							recipe.setShippingState(Constants.State.SOAK_SCAN.getNewState());
						}
					}
					//不是膏方的单子，如果结束煎煮，进行打包流程
					else if(recipe.getBoilType()!=Constants.GF&& Objects.equals(recipe.getShippingState(), Constants.State.END_DECOCT_SCAN.getNewState())){
						log.info("recipe.getShippingState()==="+recipe.getShippingState());
						recipe.setShippingState(Constants.State.PACK_SCAN.getNewState());
					}
					//如果是结束浓缩，进行打包流程
					else if(Objects.equals(recipe.getShippingState(), Constants.State.CONCENTRATE_END.getNewState())){
						recipe.setShippingState(Constants.State.PACK_SCAN.getNewState());
						isConcentrate = true;
					}
					//根据系统配置，判断膏方是否需要沉淀
					else if(subsideConfig!=null&& "Y".equals(subsideConfig.getValue()) && Objects.equals(recipe.getShippingState(), Constants.State.SUBSIDE_START.getNewState())){
						log.info("fs=="+object.containsKey("fs"));
						if(object.containsKey("fs")){
							log.info("fs=="+object.getIntValue("fs"));
							recipe.setShippingState(Constants.State.FORCE_SUBSIDE_END.getNewState()); 
						}else{
							recipe.setShippingState(Constants.State.SUBSIDE_END.getNewState());
						}
					}
					else if(subsideConfig!=null&& "Y".equals(subsideConfig.getValue()) && Objects.equals(recipe.getShippingState(), Constants.State.END_DECOCT_SCAN.getNewState())){
						recipe.setShippingState(Constants.State.SUBSIDE_END.getNewState());
					}else if(Objects.equals(recipe.getShippingState(), Constants.State.SUBSIDE_END.getNewState())){
						recipe.setShippingState(Constants.State.BOIL_SCAN.getNewState());
					}else if(Objects.equals(recipe.getShippingState(), Constants.State.FORCE_SUBSIDE_END.getNewState())){
						recipe.setShippingState(Constants.State.BOIL_SCAN.getNewState());
					}else if(Objects.equals(recipe.getShippingState(), Constants.State.START_FRIST_BOIL.getNewState())){
						recipe.setShippingState(Constants.State.START_DECOCT_SCAN.getNewState());
					}else if(Objects.equals(recipe.getShippingState(), Constants.State.CHECK_BILL.getNewState())){
						recipe.setShippingState(Constants.State.DISPENSE_SCAN.getNewState());
					}else if(Objects.equals(recipe.getShippingState(), Constants.State.START_DECOCT_SCAN.getNewState())){
						if(recipe.getSecondBoilTime()!=null&&recipe.getSecondBoilTime()>0){
							recipe.setShippingState(Constants.State.START_SECOND_BOIL.getNewState());
						}else{
							recipe.setShippingState(Constants.State.END_DECOCT_SCAN.getNewState());
						}
					}else if(Objects.equals(recipe.getShippingState(), Constants.State.START_SECOND_BOIL.getNewState())){
						recipe.setShippingState(Constants.State.END_DECOCT_SCAN.getNewState());
					}
					else if(Objects.equals(recipe.getShippingState(), Constants.State.START_AFTER_BOIL.getNewState())){
						if(recipe.getSecondBoilTime()!=null&&recipe.getSecondBoilTime()>0){
							recipe.setShippingState(Constants.State.START_SECOND_BOIL.getNewState());
						}else{
							recipe.setShippingState(Constants.State.END_DECOCT_SCAN.getNewState());
						}
					}else if(isHospital&&Objects.equals(recipe.getShippingState(), Constants.State.PACK_SCAN.getNewState())){
						recipe.setShippingState(Constants.State.STATE_SHELVES_ALREADY.getNewState());
					}
					else if(!isHospital&&Objects.equals(recipe.getShippingState(), Constants.State.PACK_SCAN.getNewState()) &&deliveryConfig!=null&& "Y".equals(deliveryConfig.getValue())){
						recipe.setShippingState(Constants.State.DELIVERY_BILL.getNewState());
					}else if(Objects.equals(recipe.getShippingState(), Constants.State.BILL_STATE_XBZFH.getNewState()) &&deliveryConfig!=null&& "Y".equals(deliveryConfig.getValue())){
						recipe.setShippingState(Constants.State.DELIVERY_BILL.getNewState());
					}else if(Objects.equals(recipe.getShippingState(), Constants.State.STATE_SHELVES_ALREADY.getNewState()) &&deliveryConfig!=null&& "Y".equals(deliveryConfig.getValue())){
						recipe.setShippingState(Constants.State.DELIVERY_BILL.getNewState());
					}
					else if(Objects.equals(recipe.getShippingState(), Constants.State.RECIPE_PICKING.getNewState())){
						recipe.setShippingState(Constants.State.SOAK_SCAN.getNewState());
					}
					else{
						recipe.setShippingState(Constants.NextStateMap.getNextState(recipe.getShippingState()));
						log.info("recipe.getShippingState()==="+recipe.getShippingState());
					}
					
					
					//自煎，却有不属于自煎的流程，属于不正常流程
					if((recipe.getBoilType()==Constants.ZJ||recipe.getBoilType()==Constants.GFZJ)&&recipe.getShippingState()>Constants.State.RE_CHECK_SCAN.getNewState()&&recipe.getShippingState()<Constants.State.PACK_SCAN.getNewState())
			   	   	 {
			   	   		 obj.put("SF", "e");
			   	   		 obj.put("st", -1);
			   	   	 }
					//不是膏方，却有属于膏方的流程，属于不正常流程
			   	   	 else if(recipe.getBoilType()!=Constants.GF&& Objects.equals(recipe.getShippingState(), Constants.State.BOIL_SCAN.getNewState()))
			   	   	 {
			   	   		 obj.put("SF", "e");
			   	   		 obj.put("st", -4);
			   	   	 }
			   	   	 else
			   	   	 {
			   	   		 //获得扫描用户的所有权限
			   	   		 Map privilegList = (Map)Constants.privilegList.get(recipe.getScanUser().toString());
			   	   		 if(privilegList==null||privilegList.size()==0)
			   	   		 {
					   		Employee employee = employeeService.getEmployeeContent(recipe.getScanUser());
					   		if(employee!=null){
					   			privilegList=employeeService.findAllPrivilegeListByRole(employee);
					   			Constants.privilegList.put(recipe.getScanUser().toString(), privilegList);
					   		}
			   	   		 }
			   	   		 
			   	   		 //根据shippingstate的不同，进入不同的操作
						 switch (recipe.getShippingState()) {
							//接方 好像没有接方的流程
//							case Constants.ACCEPT_SCAN:
//								//有接方权限
//								if(privilegList.get("ACCEPT_SCAN")!=null)
//								{
//									obj.put("SF", "1");
//									obj.put("s", recipe.getShippingState());
//								}
//								//无接方权限
//								else
//								{
//									obj.put("SF", "e");
//									obj.put("st", recipe.getShippingState()); 
//								}
//								break;
							//配方，直接给了异常
							case Constants.STATE_DISPENSE_SCAN:
								obj.put("SF", "e");
								if(checkBillConfig!=null&& "Y".equals(checkBillConfig.getValue())){
									obj.put("st", "177"); 
								}else{
									obj.put("st", Constants.State.DISPENSE_SCAN.getOldState()); 
								}
								break;
								//审方
							case Constants.STATE_CHECK_BILL:
								//有审方权限
								if(privilegList.get("CHECK_BILL")!=null)
								{
									recipe.setScanUser(object.getIntValue("EI"));
					   	   			recipe.setCheckBillId(recipe.getScanUser());
					   	   			recipe.setCheckBillTime(new Date());
//					   	   			obj = normalScan(recipe,billServiceImpl);
					   	   			int rows = recipeService.updateRecipeStatus(recipe);
					   	   			obj.put("id", recipe.getId());
					   	   			obj.put("sid", rows);
									obj.put("SF", "1");
									obj.put("s", Constants.State.CHECK_BILL.getOldState());
								}
								//无审方权限
								else
								{
									obj.put("SF", "e");
									obj.put("st", Constants.State.CHECK_BILL.getOldState()); 
								}
								break;
							//复核
							case Constants.STATE_RE_CHECK_SCAN:
								//有复核权限
								if(privilegList.get("RE_CHECK_SCAN")!=null)
								{
									//从系统配置中获得“复核环节是否拍照”，并保存到userType中
									log.info("Constant.userType.get(tenant)中=="+Constants.userType.get(tenant+"p"));
									if(Constants.userType.get(tenant+"p")==null)
									{
										log.info("tenant=="+tenant);
										List<SysConfig> list = sysService.listConfig();
										SysConfig type = getSystemConfig(list, SystemConstant.TAKE_A_PICTURE_WHILE_CHECK);
										if(type!=null){
											Constants.userType.put(tenant+"p", "Y".equals(type.getValue().toString()) ?"1":"0");
										}
										else
										{
											Constants.userType.put(tenant+"p", "1");
										}
									}
									//判断是否要拍照
									if(Constants.userType.get(tenant+"p")!=null&& "1".equals(Constants.userType.get(tenant + "p").toString()))
									{
										obj.put("SF", "1");
										obj.put("s", Constants.State.RE_CHECK_SCAN.getOldState());
									}
									else
									{
										recipe.setCheckId(recipe.getScanUser());
										recipe.setCheckTime(new Date());
//										obj = normalScan(recipe,billServiceImpl);
										int rows = recipeService.updateRecipeStatus(recipe);
						   	   			obj.put("id", recipe.getId());
						   	   			obj.put("sid", rows);
						   	   			obj.put("SF", "1");
										obj.put("s", Constants.State.RE_CHECK_SCAN.getOldState());
									}
									
									
									obj.put("SF", "1");
									obj.put("s", Constants.State.RE_CHECK_SCAN.getOldState());
								}
								//无复核权限
								else
								{
									obj.put("SF", "e");
									obj.put("st", Constants.State.RE_CHECK_SCAN.getOldState()); 
								}
								break;
							//浸泡
							case Constants.STATE_SOAK_SCAN:
								//有浸泡权限
								if(privilegList.get("SOAK_SCAN")!=null)
								{
									recipe.setSoakId(object.getIntValue("EI"));
					   	   			recipe.setStartSoakTime(new Date());
					   	   			//更新状态
//									obj = normalScan(recipe,billServiceImpl);
						   	   		int rows = recipeService.updateRecipeStatus(recipe);
						   	   		RecipeMedicine recipeMedicine = new RecipeMedicine();
						   	   		recipeMedicine.setSpecialBoilType("先煎");
						   	   		recipeMedicine.setRecipeId(recipe.getId());
						   	   		List<RecipeMedicine> listM = recipeService.findRecipeMedicineBySpecialBoilType(recipeMedicine);
						   	   		if(listM!=null && listM.size()>0){
						   	   			obj.put("msg", "处方已开始浸泡，并存在先煎药品！");
						   	   		}else{
						   	   			obj.put("msg", "该处方已开始浸泡");
						   	   		}
					   	   			obj.put("id", recipe.getId());
					   	   			obj.put("sid", rows);
					   	   			obj.put("SF", "1");
									obj.put("s", Constants.State.SOAK_SCAN.getOldState());
					   	   			obj.put("st", recipe.getSoakTime());
								}
								//无浸泡权限
								else
								{
									obj.put("SF", "e");
									obj.put("st", Constants.State.SOAK_SCAN.getOldState()); 
								}
								break;
							//开始煎煮
							case Constants.STATE_START_DECOCT_SCAN:
								//有开始煎煮权限
								if(privilegList.get("START_DECOCT_SCAN")!=null)
								{
									//计算已经浸泡了几分钟
									int time = (int)((System.currentTimeMillis()-recipe.getStartSoakTime().getTime())/ (1000 * 60));
									/*判断浸泡时间是否已经够了*/
									//浸泡时间不够
					   	   			if((time<(recipe.getSoakTime())))
					   	   			{
					   	   				obj.put("SF", "e");
					   	   				obj.put("st", -3);
					   	   				obj.put("ti", recipe.getSoakTime()-time+"");
					   	   			}
					   	   			//浸泡时间够了
					   	   			else
					   	   			{
					   	   				log.info("25=====");
					   	   				obj.put("SF", "1");
					   	   				obj.put("s", Constants.State.START_DECOCT_SCAN.getOldState());
					   	   				obj.put("bt", recipe.getBoilTime());
					   	   			}
								}
								//无开始煎煮权限
								else
								{
									obj.put("SF", "e");
									obj.put("st", Constants.State.START_DECOCT_SCAN.getOldState()); 
								}
								break;
							//开始二煎
							case Constants.STATE_START_SECOND_BOIL:
								//计算已经煎煮了几分钟
								Long timeL = (Long)((System.currentTimeMillis()-recipe.getStartBoilTime().getTime())/ (1000 * 60));
								/*判断煎煮时间是否已经够了*/
								//煎煮时间不够，允许误差时间根据系统配置,默认2分钟
								Integer errorTime=2;
								if(errorTimeConfig!=null){
									errorTime = Integer.valueOf(errorTimeConfig.getValue());
								}
								if(timeL<(recipe.getBoilTime()-errorTime))
				   	   			{
				   	   				obj.put("SF", "e");
				   	   				obj.put("st", -22);
				   	   				obj.put("errMsg", "头煎还有"+(recipe.getBoilTime()-timeL)+"分钟");
				   	   				obj.put("ti", recipe.getBoilTime()-timeL+"");
				   	   			}
				   	   			//煎煮时间够了
				   	   			else
				   	   			{
				   	   				log.info("46=====");
				   	   				obj.put("SF", "1");
				   	   				obj.put("s", Constants.State.START_SECOND_BOIL.getOldState());
				   	   				obj.put("bt", recipe.getSecondBoilTime());
				   	   			}
								break;
							//结束煎煮
							case Constants.STATE_END_DECOCT_SCAN:
								//有结束煎煮权限
								if(privilegList.get("END_DECOCT_SCAN")!=null)
								{
									//计算已经煎煮了几分钟
									Long time =0L;
									Integer boilTime =0;
									if(recipe.getSecondBoilTime()!=null&&recipe.getSecondBoilTime()>0){
										boilTime=recipe.getSecondBoilTime();
										time= (Long)((System.currentTimeMillis()-recipe.getStartSecondBoilTime().getTime())/ (1000 * 60));
									}else{
										boilTime=recipe.getBoilTime();
										time= (Long)((System.currentTimeMillis()-recipe.getStartBoilTime().getTime())/ (1000 * 60));
									}
									/*判断煎煮时间是否已经够了*/
									//煎煮时间不够，允许误差时间根据系统配置,默认2分钟
									Integer errorTime1=2;
									if(errorTimeConfig!=null){
										errorTime1 = Integer.valueOf(errorTimeConfig.getValue());
									}
					   	   			if(time<(boilTime-errorTime1))
					   	   			{
					   	   				obj.put("SF", "e");
					   	   				obj.put("st", -2);
					   	   				obj.put("ti", boilTime-time+"");
					   	   			}
					   	   			//煎煮时间够了
					   	   			else
					   	   			{
					   	   				time=(Long)((System.currentTimeMillis()-recipe.getStartBoilTime().getTime())/ (1000 * 60));
						   	   			if(subsideConfig!=null&& "Y".equals(subsideConfig.getValue())){
						   	   				recipe.setAddSubside(1);
						   	   			}
					   	   				log.info("userType  bbbb="+Constants.userType.get(tenant+"b"));
						   	   			if(Constants.userType.get(tenant+"b")==null)
										{
											List<SysConfig> list = sysService.listConfig();
											SysConfig ptp = getSystemConfig(list, SystemConstant.PACKING_TAG_PRINTER);
											log.info("煎药机配置==ptp=="+ptp);
											if(ptp!=null)
											{
												log.info("是否扫描煎药机ptp.getPropertyValue()="+ptp.getValue());
												Constants.userType.put(tenant+"b", "Y".equals(ptp.getValue().toString()) ?"1":"0");
											}else{
												Constants.userType.put(tenant+"b", "0");
											}
										}
						   	   			log.info("--------Constant.userType.get(tenant+b)=="+Constants.userType.get(tenant+"b"));
							   	   		if(Constants.userType.get(tenant+"b")!=null&& "1".equals(Constants.userType.get(tenant + "b").toString()))
										{
							   	   			log.info("需要扫描煎药机");
											obj.put("SF", "1");
											obj.put("s", Constants.State.END_DECOCT_SCAN.getOldState());
										}else{
											log.info("不不不不不需要扫描煎药机");
											log.info("getAddSubside=="+recipe.getAddSubside());
											recipe.setActualBoilTime(time.intValue());
											recipe.setEndBoilId(recipe.getScanUser());
											recipe.setEndBoilTime(new Date());
											
//											obj = normalScan(recipe,billServiceImpl);
											int rows = recipeService.updateRecipeStatus(recipe);
							   	   			obj.put("sid", rows);
											/**
								   	   		  * 永安煎药机智能(开启/关闭)煎药机
								   	   		  * 扫描开始煎煮 发送开启煎药机指令33
								   	   		  * 扫描结束煎煮 发送关闭煎药机指令44
								   	   		  */
//											Machine machine=new Machine();
//											machine.setMachineId(recipe.getMachineId());
//										   	machine.setOperateType(recipe.getShippingState());
//										   	machine.setAreaId(recipe.getBoilTime());//借用字段存入煎药时间
//											openCloseMachine(billServiceImpl, machine,tenant);
											 
											obj.put("id", recipe.getId());
											obj.put("s", Constants.State.END_DECOCT_SCAN.getOldState());
											obj.put("mac", recipe.getSpMac());
											obj.put("SF", "1");
										}
					   	   			}
								}
								//无开始煎煮权限
								else
								{
									obj.put("SF", "e");
									obj.put("st", Constants.State.END_DECOCT_SCAN.getOldState()); 
								}
								break;
							case Constants.STATE_SHELVES_ALREADY:
								obj.put("SF", "e");
								obj.put("st", Constants.STATE_SHELVES_ALREADY); 
								break;
							//发货
							case Constants.STATE_DELIVERY_BILL:
//								 boolean isHospital = sysPropertyUtil.getProperty(Constants.HOSPITAL_TYPE, "1").equals("0")?true:false;
								 if(isHospital&&recipe.getCarryId()==3){
									 obj.put("SF", "e");
									 obj.put("st", Constants.STATE_DELIVERY_BILL); 
								 }else{
									 //有发货权限
									 if(privilegList.get("IS_DELIVERY_BILL")!=null)
									 {
										 if(checkExpressConfig!=null&& "Y".equals(checkExpressConfig.getValue()) &&recipe.getLogisticsCode()!=null&&!("").equals(recipe.getLogisticsCode()))
										 {
											 log.info("需要扫描快递单号");
											 obj.put("SF", "1");
											 obj.put("s", 20);//20表示需要扫描快递单号
										 }else{
											 log.info("不不不不不需要扫描快递单号");
											 recipe.setDeliveryId(recipe.getScanUser());
											 recipe.setDeliveryTime(new Date());
											 
//										obj = normalScan(recipe,billServiceImpl);
											 int rows = recipeService.updateRecipeStatus(recipe);
											 obj.put("sid", rows);
											 obj.put("id", recipe.getId());
											 obj.put("s", Constants.State.DELIVERY_BILL.getOldState());
											 obj.put("SF", "1");
										 }
									 }
									 //无发货权限
									 else
									 {
										 obj.put("SF", "e");
										 obj.put("st", Constants.State.DELIVERY_BILL.getOldState()); 
									 }
								 }
								break;
							//沉淀
							case Constants.STATE_SUBSIDE_END:
								//计算已经沉淀了几分钟
								int time = (int)((System.currentTimeMillis()-recipe.getEndBoilTime().getTime())/ (1000 * 60));
								/*判断沉淀时间是否已经够了*/
								//沉淀时间不够
				   	   			if((time<(Integer.valueOf(subsideTimeConfig.getValue()).intValue()*60)))
				   	   			{
				   	   				log.info("沉淀时间不够=======");
				   	   				if(forceTimeConfig!=null&&forceTimeConfig.getValue()!=null&& "Y".equals(forceTimeConfig.getValue().toString())){
				   	   					obj.put("st", -14);
				   	   				}else{
				   	   					obj.put("st", -15);
				   	   				}
				   	   				obj.put("SF", "e");
				   	   				obj.put("ti", Integer.valueOf(subsideTimeConfig.getValue()).intValue()*60-time+"");
				   	   			}
				   	   			//沉淀时间够了
				   	   			else
				   	   			{
				   	   				recipe.setEndSubsideTime(new Date());
//				   	   				obj = normalScan(recipe,billServiceImpl);
					   	   			int rows = recipeService.updateRecipeStatus(recipe);
					   	   			obj.put("sid", rows);
				   	   				obj.put("SF", "1");
				   	   				obj.put("s", Constants.State.SUBSIDE_END.getOldState());
				   	   				obj.put("bt", time);
				   	   			}
				   	   			break;
				   	   		//强制沉淀
							case Constants.STATE_FORCE_SUBSIDE_END:
								int times = (int)((System.currentTimeMillis()-recipe.getEndBoilTime().getTime())/ (1000 * 60));
			   	   				recipe.setEndSubsideTime(new Date());
//			   	   				obj = normalScan(recipe,billServiceImpl);
				   	   			int rows = recipeService.updateRecipeStatus(recipe);
				   	   			obj.put("id", recipe.getId());
				   	   			obj.put("sid", rows);
			   	   				obj.put("SF", "1");
			   	   				obj.put("s", Constants.State.FORCE_SUBSIDE_END.getOldState());
			   	   				obj.put("bt", times);
				   	   			break;
							//收膏
							case Constants.STATE_BOIL_SCAN:
								//有收膏权限
								if(privilegList.get("BOIL_SCAN")!=null)
								{
									recipe.setCollectId(recipe.getScanUser());
									recipe.setCollectTime(new Date());
									
									
//									obj = normalScan(recipe,billServiceImpl);
									int row = recipeService.updateRecipeStatus(recipe);
					   	   			obj.put("id", recipe.getId());
					   	   			obj.put("sid", row);
					   	   			obj.put("SF", "1");
									obj.put("s", Constants.State.BOIL_SCAN.getOldState());
								}
								//无收膏权限
								else
								{
									obj.put("SF", "e");
									obj.put("st", Constants.State.BOIL_SCAN.getOldState()); 
								}
								break;
							//包装
							case Constants.STATE_PACK_SCAN:
								int concentrateType = -1;//表示浓缩与打包组合关系：1浓缩+打包、2浓缩、3打包
								//自煎、膏方自煎、膏方，不需要浓缩流程
								//浓缩结束了，只做打包
								if(recipe.getBoilType()==Constants.ZJ||recipe.getBoilType()==Constants.GFZJ||recipe.getBoilType()==Constants.GF
										||isConcentrate){
									concentrateType = 3;
								}
								if(concentrateType==-1){
									//有浓缩权限
									if(privilegList.get("CONCENTRATE_SCAN")!=null&&privilegList.get("PACK_SCAN")!=null) {
                                        concentrateType = 1;
                                    } else if(privilegList.get("CONCENTRATE_SCAN")!=null&&privilegList.get("PACK_SCAN")==null) {
                                        concentrateType = 2;
                                    } else if(privilegList.get("CONCENTRATE_SCAN")==null&&privilegList.get("PACK_SCAN")!=null) {
                                        concentrateType = 3;
                                    }
								}
								log.info("concentrateType==="+concentrateType);
								boolean haspic = false;
								if(concentrateType==1 || concentrateType==3){
									//从系统配置中获得“包装环节是否拍照”，并保存到userType中
									log.info("Constants.userType.get(tenant)==="+Constants.userType.get(tenant));
									if(Constants.userType.get(tenant)==null)
									{
										List<SysConfig> list = sysService.listConfig();
										SysConfig type = getSystemConfig(list, SystemConstant.TCMPHONE_USER);
										if(type!=null)
										{
											Constants.userType.put(tenant, type.getValue());
										}
									}
									//判断处方是否有另包
									if(recipe.getOtherPackage()!=null&&!"".equals(recipe.getOtherPackage()))
									{
										haspic = true;
									}
									//判断包装环节是否要拍照
									else if(Constants.userType.get(tenant)!=null&& "1".equals(Constants.userType.get(tenant).toString()))
									{
										haspic = true;
									}
								}
								obj.put("concentrateType", concentrateType);
								log.info("haspic==="+haspic);
								log.info("recipe.getRecipientId()==="+recipe.getRecipientId());
								if(concentrateType==1){
									obj.put("SF", "1");
									obj.put("s", Constants.OldStateMap.getOldState(recipe.getShippingState()));
									if(!haspic){
										obj.put("id", recipe.getId());
									}
								}else if(concentrateType==2){
									obj.put("SF", "1");
									obj.put("s", Constants.OldStateMap.getOldState(recipe.getShippingState()));
								}else if(concentrateType==3){
									if(privilegList.get("PACK_SCAN")!=null){
										obj.put("SF", "1");
										if(!haspic){
											recipe.setPackageId(recipe.getScanUser());
											recipe.setPackageTime(new Date());
//											obj = normalScan(recipe,billServiceImpl);
											recipeService.updateRecipeStatus(recipe);
							   	   			obj.put("id", recipe.getId());
							   	   			obj.put("SF", "1");
										}
										obj.put("concentrateType", concentrateType);
										obj.put("s", Constants.OldStateMap.getOldState(recipe.getShippingState()));
									}else{
										obj.put("SF", "e");
										obj.put("concentrateType", concentrateType);
										obj.put("st", Constants.OldStateMap.getOldState(recipe.getShippingState()));
									}
								}
								//无浓缩权限无包装权限
								else if(concentrateType==-1)
								{
									log.info("recipe.getShippingState()=="+Constants.State.PACK_SCAN.getOldState());
									obj.put("SF", "e");
									obj.put("st", Constants.OldStateMap.getOldState(recipe.getShippingState())); 
								}
								
								break;
							//浓缩结束
							case Constants.STATE_CONCENTRATE_END:
								//有浓缩权限
								if(privilegList.get("CONCENTRATE_SCAN")!=null){
						   	   		record.setShippingState(Constants.State.CONCENTRATE_START.getNewState());
						   	   		record.setId(recipe.getId());
						   	   		List<RecipeScan> list = recipeService.listStartVolume(record);
						   	   		RecipeScan reV = (RecipeScan)list.get(0);
						   	   		obj.put("SF", "1");
						   	   		obj.put("s", Constants.State.CONCENTRATE_END.getOldState());
						   	   		obj.put("volume", Math.round(reV.getStartConcentrateVolume()));
								}else{
									obj.put("SF", "e");
									obj.put("st", Constants.State.CONCENTRATE_END.getOldState()); 
								}
								break;
//							//拿去发药
//							case Constants.STATE_DELIVERY_BILL:
//								obj.put("SF", "e");
//								obj.put("st", Constants.STATE_INVALID);
//								break;
							//作废
							case Constants.STATE_INVALID:
								obj.put("SF", "e");
								obj.put("st", Constants.STATE_INVALID);
								break;
							//异常情况
							default:
								obj.put("SF", "e");
								obj.put("st", recipe.getShippingState());
								break;
						}
						obj.put("rn", recipe.getRecipientName());
						obj.put("q", recipe.getQuantity());
					}
				}
			}
		}catch(Exception e)
		{
			try {
				obj.put("SF","-1");
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
	return obj;
}
	private SysConfig getSystemConfig(List<SysConfig> sysconfigList, String sysName){
		SysConfig sc = null;
		if(sysconfigList!=null && sysconfigList.size()>0){
			for(int i=0; i<sysconfigList.size(); i++){
				if(sysName.equals(((SysConfig)sysconfigList.get(i)).getName())){
					sc = (SysConfig)sysconfigList.get(i);
					break;
				}
			}
		}
		return sc;
	}

	private static String getTenantForMobile(){
		return DynamicDataSourceTenantLocal.getDataSourceName();
	}
}
