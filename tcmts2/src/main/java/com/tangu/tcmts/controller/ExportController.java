package com.tangu.tcmts.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tangu.tcmts.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.tangu.common.util.annotation.Tanguanthority;
import com.tangu.datasource.DynamicDataSourceTenantLocal;
import com.tangu.tcmts.dao.ExportXmlMapper;
import com.tangu.tcmts.service.ExceptionCaseService;
import com.tangu.tcmts.service.MedicineService;
import com.tangu.tcmts.service.PriceTempletService;
import com.tangu.tcmts.service.RecipeService;
import com.tangu.tcmts.service.ShelvesMedicineManageService;
import com.tangu.tcmts.service.WarehouseService;
import com.tangu.tcmts.util.CommonUtil;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.RecipeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "export", description = "导出")
@RestController
@RequestMapping("/export")
public class ExportController {
	private final static String warehouseType1 = "仓库";
	private final static String warehouseType3 = "饮片药房";
	private final static String warehouseType4 = "小包装药房";
    @Autowired
    ExceptionCaseService exceptionCaseService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private ExportXmlMapper exportXmlMapper;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private PriceTempletService priceTempletService;

    @Autowired
    private ShelvesMedicineManageService shelvesMedicineManageService;
    
	@ApiOperation(value = "中药管理-导出excel", notes = "传入medicine")
	@ApiImplicitParams({ @ApiImplicitParam(name = "medicine", value = "导出", required = true, dataType = "String") })
	 @RequestMapping(value = "exportMedicine", method = RequestMethod.GET)
    public void exportMedicine(@RequestParam String tenant, @RequestParam String medicine, HttpServletRequest req, HttpServletResponse resp)
			throws UnsupportedEncodingException, ServletException, IOException {
		MedicineDTO medicineDTO = JSON.parseObject(URLDecoder.decode(medicine, "UTF-8"), MedicineDTO.class);
		DynamicDataSourceTenantLocal.setDataSourceName(URLDecoder.decode(URLDecoder.decode(tenant, "UTF-8"), "UTF-8"));
		List<MedicineDO> list = medicineService.listMedicine(medicineDTO);
		list.forEach(p -> {
		  if(p.getUseState() == 0){
		    p.setUseStateName("启用");
		  }else{
		    p.setUseStateName("停用");
		  }
		});
		String xml = "medicine";
		String xmlString = exportXmlMapper.getXmlValue(xml);
		recipeService.export(CommonUtil.beanToMap(medicineDTO),list, req, resp, xmlString);
	}

    @ApiOperation(tags = "export", value = "导出异常列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exportExceptionCase", value = "导出异常列表"),
            @ApiImplicitParam(name = "tenant", value = "账套")
    })
    @RequestMapping(value = "exportExceptionCase", method = RequestMethod.GET)
    public void exportExceptionCase(@RequestParam String tenant, @RequestParam String exceptionCase, HttpServletRequest req, HttpServletResponse resp)
            throws UnsupportedEncodingException, ServletException, IOException {
        ExceptionCase exceptionCaseEntity = JSON.parseObject(URLDecoder.decode(URLDecoder.decode(exceptionCase, "UTF-8"), "UTF-8"),
                ExceptionCase.class);
        DynamicDataSourceTenantLocal.setDataSourceName(URLDecoder.decode(URLDecoder.decode(tenant, "UTF-8"), "UTF-8"));
        List<ExceptionCase> list = exceptionCaseService.listExceptionCases(exceptionCaseEntity);
        for (int i = 0, len = list.size(); i < len; i++) {
            if (list.get(i).getDealStatus() == null || list.get(i).getDealStatus() == Constants.STATE_ZERO) {
              list.get(i).setDealStatusName("未处理");
            } else {
              list.get(i).setDealStatusName("已处理");
            }
        }
        String xmlString = exportXmlMapper.getXmlValue("exceptionCase");
        medicineService.export(list, req, resp, xmlString);
    }

    @ApiOperation(tags = "export", value = "导出抽检列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recipePatrol", value = "导出抽检列表"),
            @ApiImplicitParam(name = "tenant", value = "账套")
    })
    @RequestMapping(value = "exportRecipePatrol", method = RequestMethod.GET)
    public void exportRecipePatrol(@RequestParam String tenant, @RequestParam String recipePatrol, HttpServletRequest req,
                                   HttpServletResponse resp)
            throws UnsupportedEncodingException, ServletException, IOException {
        RecipePatrol recipePatrolEntity = JSON.parseObject(URLDecoder.decode(recipePatrol, "UTF-8"),
                RecipePatrol.class);
        recipePatrolEntity.setEndDate(LocalDate.parse(recipePatrolEntity.getEndDate()).plusDays(1).toString());
        DynamicDataSourceTenantLocal.setDataSourceName(URLDecoder.decode(URLDecoder.decode(tenant, "UTF-8"), "UTF-8"));
        List<RecipePatrol> list = recipeService.listRecipePatrols(recipePatrolEntity);
        String xmlString = exportXmlMapper.getXmlValue("recipePatrol");
        //查出数据后恢复输入的日期
        recipePatrolEntity.setEndDate(LocalDate.parse(recipePatrolEntity.getEndDate()).minusDays(1).toString());
        recipeService.export(CommonUtil.beanToMap(recipePatrolEntity),RecipeUtil.convertRecipePatrol(list), req, resp, xmlString);
    }

    @ApiOperation(value = "导出处方列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recipeDTO", value = "处方条件"),
            @ApiImplicitParam(name = "tenant", value = "账套"),
            @ApiImplicitParam(name = "exportRecipeType",value = "配送清单")
    })
    @RequestMapping(value = "exportRecipe", method = RequestMethod.GET)
    public void exportRecipe(@RequestParam String tenant, @RequestParam String recipeDTO,
                             @RequestParam String exportRecipeType, HttpServletRequest req, HttpServletResponse resp)
            throws UnsupportedEncodingException, ServletException, IOException {
        RecipeDTO recipe=JSON.parseObject(URLDecoder.decode(recipeDTO, "UTF-8"),
                RecipeDTO.class);
        DynamicDataSourceTenantLocal.setDataSourceName(URLDecoder.decode(URLDecoder.decode(tenant, "UTF-8"), "UTF-8"));
        List<RecipeDO> list = recipeService.listRecipes(recipe);
        String readXML = exportRecipeType;
        String xmlString = exportXmlMapper.getXmlValue(readXML);
        recipeService.export(CommonUtil.beanToMap(recipe),list, req, resp, xmlString);
    }

    /**
     * 药品统计导出
     * @param tenant
     * @param condition
     * @param req
     * @param resp
     * @throws UnsupportedEncodingException
     * @throws ServletException
     * @throws IOException
     */
    @ApiOperation(value = "导出统计列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "condition", value = "导出条件"),
            @ApiImplicitParam(name = "tenant", value = "账套"),
    })
    @RequestMapping(value = "exportReport" , method = RequestMethod.GET)
    public void exportReport(@RequestParam String tenant,
                              @RequestParam String condition,
                              HttpServletRequest req,
                              HttpServletResponse resp) throws UnsupportedEncodingException, ServletException, IOException {
        ReportDTO reportDTO = JSON.parseObject(URLDecoder.decode(condition, "UTF-8"),
                ReportDTO.class);
        DynamicDataSourceTenantLocal.setDataSourceName(URLDecoder.decode(URLDecoder.decode(tenant, "UTF-8"), "UTF-8"));
        /*
        得到report列表
         */
        List<Report> reportList = new ArrayList<Report>();
        /*
        判定使用哪个语句
         */
        String readXML = "";

        if(Constants.TYPE_ONE == reportDTO.getCountType()){
            reportList = recipeService.listReportMedicineBySettle(reportDTO);
            readXML = "reportListBySettle";

        } else if (Constants.TYPE_TWO == reportDTO.getCountType()) {
            reportList = recipeService.listReportMedicineByHospital(reportDTO);
            readXML = "reportListByHospital";
        }else {
            reportList = recipeService.listReportMedicineByMedicine(reportDTO);
            readXML = "reportListByMedicine";
        }
        System.out.println("############################"+reportDTO);
        String xmlString = exportXmlMapper.getXmlValue(readXML);
        recipeService.export(CommonUtil.beanToMap(reportDTO),reportList, req, resp, xmlString);

    }


    @ApiOperation(value = "仓库药房药品管理-导出excel", notes = "传入param")
    @ApiImplicitParams({ @ApiImplicitParam(name = "param", value = "导入", required = true, dataType = "String") })
    @RequestMapping(value = "exportWarehouse", method = RequestMethod.GET)
    public void exportWarehouse(@RequestParam String tenant, @RequestParam String param, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
      WarehouseStandardDTO warehouseStandardDTO = JSON.parseObject(URLDecoder.decode(param, "UTF-8"), WarehouseStandardDTO.class);
      DynamicDataSourceTenantLocal.setDataSourceName(URLDecoder.decode(URLDecoder.decode(tenant, "UTF-8"), "UTF-8"));
      List<WarehouseStandardDO> list = warehouseService.listWarehouseStandard(warehouseStandardDTO);
      warehouseStandardDTO.setWarehouseName(list.get(0).getWarehouseName());
      String xml = "";
      if (warehouseStandardDTO.getWarehouseType() == 1) {
        xml = "warehouseStandardMedicine";
        warehouseStandardDTO.setWarehouseTypeName(warehouseType1);
      } else if (warehouseStandardDTO.getWarehouseType() == 3) {
        xml = "warehouseStandardMedicine3";
        warehouseStandardDTO.setWarehouseTypeName(warehouseType3);
      } else if (warehouseStandardDTO.getWarehouseType() == 4) {
        xml = "warehouseStandardMedicine";
        warehouseStandardDTO.setWarehouseTypeName(warehouseType4);
      }
      String xmlString=exportXmlMapper.getXmlValue(xml);
      recipeService.export(CommonUtil.beanToMap(warehouseStandardDTO),list, req, resp, xmlString);
    }
    
    @ApiOperation(value = "价格模板-导出excel", notes = "传入param")
    @ApiImplicitParams({ @ApiImplicitParam(name = "param", value = "导入", required = true, dataType = "String") })
    @RequestMapping(value = "exportPriceTempletDetail", method = RequestMethod.GET)
    public void exportPriceTempletDetail(@RequestParam String tenant, @RequestParam String param, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        PriceTempletDetailDTO priceTempletDetailDTO = JSON.parseObject(URLDecoder.decode(param, "UTF-8"), PriceTempletDetailDTO.class);
        DynamicDataSourceTenantLocal.setDataSourceName(URLDecoder.decode(URLDecoder.decode(tenant, "UTF-8"), "UTF-8"));
        List<PriceTempletDetailDTO> list = priceTempletService.listPriceByTemIdsOrMedIds(priceTempletDetailDTO);
        String xml = "priceTempletDetail";
        String xmlString=exportXmlMapper.getXmlValue(xml);
        recipeService.export(CommonUtil.beanToMap(priceTempletDetailDTO),list, req, resp, xmlString);
    }



	@ApiOperation(value = "货架药品管理-导出excel", notes = "传入shelvesMedicineManageStr,帐套：tenant")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "shelvesMedicineManageStr", value = "查询条件", required = true, dataType = "String"),})
	@RequestMapping(value = "exportShelvesMedicine", method = RequestMethod.GET)
	public void exportShelvesMedicine(@RequestParam(value = "tenant") String tenant, @RequestParam(value = "shelvesMedicineManageStr") String shelvesMedicineManageStr, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        System.out.println(shelvesMedicineManageStr);
        ShelvesMedicineManage shelvesMedicineManage = JSON.parseObject(URLDecoder.decode(shelvesMedicineManageStr,"UTF-8"), ShelvesMedicineManage.class);
        DynamicDataSourceTenantLocal.setDataSourceName(URLDecoder.decode(URLDecoder.decode(tenant,"UTF-8"), "UTF-8"));
        List<ShelvesMedicineDTO> shelvesMedicineDTOList = shelvesMedicineManageService.listShelvesMedicineDTOByCondition(shelvesMedicineManage);
        String xml = "shelvesMedicineList";
        String xmlString = exportXmlMapper.getXmlValue(xml);
        shelvesMedicineManageService.export(CommonUtil.beanToMap(shelvesMedicineManage),shelvesMedicineDTOList,req,resp,xmlString);
    }
	
	@ApiOperation(value = "导出员工业绩报表", notes = "传入employeeAchievement,tenant")
	@ApiImplicitParams({ @ApiImplicitParam(name = "employeeAchievement", value = "导出", required = true, dataType = "String") })
	 @RequestMapping(value = "exportEmployeeAchievement", method = RequestMethod.GET)
	@Tanguanthority(type=Tanguanthority.AUTH_ALL)
    public void exportEmployeeAchievement(@RequestParam String tenant, @RequestParam String employeeAchievement, HttpServletRequest req, HttpServletResponse resp)
			throws UnsupportedEncodingException, ServletException, IOException {
		RecipeScan recipeScan = JSON.parseObject(URLDecoder.decode(employeeAchievement, "UTF-8"), RecipeScan.class);
		DynamicDataSourceTenantLocal.setDataSourceName(URLDecoder.decode(URLDecoder.decode(tenant, "UTF-8"), "UTF-8"));
		List<RecipeScan> scanList = recipeService.countEmployeeAchievement(recipeScan);
		String xml = "employeeAchievement";
		String xmlString = exportXmlMapper.getXmlValue(xml);
		recipeService.export(CommonUtil.beanToMap(recipeScan),scanList, req, resp, xmlString);
	}

}

