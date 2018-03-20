package com.tangu.tcmts.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.po.LoadMedicineDO;
import com.tangu.tcmts.po.MedicineStandardDO;
import com.tangu.tcmts.po.MedicineStandardDTO;
import com.tangu.tcmts.po.PublicDO;
import com.tangu.tcmts.po.WarehouseDO;
import com.tangu.tcmts.po.WarehouseDTO;
import com.tangu.tcmts.po.WarehouseStandardDO;
import com.tangu.tcmts.po.WarehouseStandardDTO;
import com.tangu.tcmts.service.MedicineService;
import com.tangu.tcmts.service.WarehouseService;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.ImportExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author:huangyifan
 * @Description:仓库、药房model
 * @Date: create in 17:40 2017/10/24
 */
@Api(value = "/warehouse", description = "仓库/药房管理")
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
	static final String WAREHOUSE_TYPE_NAME1 = "仓库";
	static final String WAREHOUSE_TYPE_NAME3 = "药房(饮片)";
	static final String WAREHOUSE_TYPE_NAME4 = "药房(小包装)";
	static final String WAREHOUSE_NAME_EXIST = "仓库药房名称已存在！";

	@Autowired
	WarehouseService warehouseService;
	@Autowired
	MedicineService medicineService;

	@ApiOperation(value = "仓库药房列表和条件查询", notes = "传入warehouseName,warehouseType,useState", response = WarehouseDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "warehouseDTO", value = "查询", required = false, dataType = "WarehouseDTO") })
	@RequestMapping(value = "/listWarehouse", method = RequestMethod.POST)
	public Object listWarehouse(@RequestBody WarehouseDTO warehouseDTO) {

		return new ResponseModel(warehouseService.listWarehouse(warehouseDTO));
	}

	@ApiOperation(value = "仓库药房下拉列表", notes = ")", response = PublicDO.class)
	@RequestMapping(value = "/listWarehouseTypeName", method = RequestMethod.GET)
	public Object listWarehouseTypeName() {
		int i = 1;
		List<PublicDO> list = new ArrayList<PublicDO>();
		PublicDO pd = new PublicDO();
		pd.setLabel(WAREHOUSE_TYPE_NAME1);
		pd.setValue(i++);
		list.add(pd);
		pd = new PublicDO();
		pd.setLabel(WAREHOUSE_TYPE_NAME3);
		pd.setValue(++i);
		list.add(pd);
		pd = new PublicDO();
		pd.setLabel(WAREHOUSE_TYPE_NAME4);
		pd.setValue(++i);
		list.add(pd);
		return new ResponseModel(list);
	}

	@ApiOperation(value = "启用药房/仓库", notes = "传入id", response = String.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "warehouseDTO", value = "启用", required = false, dataType = "WarehouseDTO") })
	@RequestMapping(value = "/openWarehouse", method = RequestMethod.POST)
	public Object openWarehouse(@RequestBody WarehouseDTO warehouseDTO, HttpServletRequest request) {
		warehouseDTO.setUseState(Constants.STATE_ZERO);
		return getResponse(warehouseDTO, request);
	}

	@ApiOperation(value = "停用药房/仓库", notes = "传入id", response = String.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "warehouseDTO", value = "停用", required = false, dataType = "WarehouseDTO") })
	@RequestMapping(value = "/closeWarehouse", method = RequestMethod.POST)
	public Object closeWarehouse(@RequestBody WarehouseDTO warehouseDTO, HttpServletRequest request) {
		warehouseDTO.setUseState(Constants.STATE_ONE);
		return getResponse(warehouseDTO, request);
	}

	@ApiOperation(value = "仓库药房新增", notes = "传入warehouseName,warehouseType,remark", response = WarehouseDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "warehouseDO", value = "新增", required = false, dataType = "WarehouseDO") })
	@RequestMapping(value = "/addWarehouse", method = RequestMethod.POST)
	public Object addWarehouse(@RequestBody WarehouseDO warehouseDO) {
		if (warehouseDO.getWarehouseName() == null || warehouseDO.getWarehouseType() == null
				|| StringUtils.isBlank(warehouseDO.getWarehouseName())) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		if (warehouseService.getWarehouseByName(warehouseDO) > 0) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, WAREHOUSE_NAME_EXIST);
		}
		Integer result = warehouseService.addWarehouse(warehouseDO);
		if (result > 0) {
			WarehouseDTO warehouseDTO = new WarehouseDTO();
			warehouseDTO.setId(warehouseDO.getId());
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS)
					.attr(ResponseModel.KEY_DATA, warehouseService.listWarehouse(warehouseDTO).get(0));
		}
		return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_FAILED);
	}

	@ApiOperation(value = "仓库药房修改", notes = "传入id,warehouseName,warehouseType,remark", response = WarehouseDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "warehouseDO", value = "修改", required = false, dataType = "WarehouseDO") })
	@RequestMapping(value = "/updateWarehouse", method = RequestMethod.POST)
	public Object updateWarehouse(@RequestBody WarehouseDO warehouseDO, HttpServletRequest request) {
		if (warehouseDO.getId() == null || warehouseDO.getWarehouseName() == null
				|| warehouseDO.getWarehouseType() == null || StringUtils.isBlank(warehouseDO.getWarehouseName())) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		if (warehouseService.getWarehouseByName(warehouseDO) > 0) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, WAREHOUSE_NAME_EXIST);
		}
		warehouseDO.setModUser(JwtUserTool.getId());
		Integer result = warehouseService.updateWarehouse(warehouseDO);
		if (result > 0) {
			WarehouseDTO warehouseDTO = new WarehouseDTO();
			warehouseDTO.setId(warehouseDO.getId());
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS)
					.attr(ResponseModel.KEY_DATA, warehouseService.listWarehouse(warehouseDTO).get(0));
		}
		return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_FAILED);
	}

	private ResponseModel getResponse(WarehouseDTO warehouseDTO, HttpServletRequest request) {
		Integer id = JwtUserTool.getId();
		warehouseDTO.setModUser(id);
		if (warehouseDTO.getId() == null) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		Integer result = warehouseService.updateUseState(warehouseDTO);
		if (result > 0) {
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}

	/**
	 * @param warehouseDTO
	 * @return
	 */
	@ApiOperation(value = "仓库药房药品管理-获取仓库名称和类型", notes = "传入warehouseName,warehouseType", response = WarehouseDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "warehouseDTO", value = "查询", required = false, dataType = "WarehouseDTO") })
	@RequestMapping(value = "/listWarehouseNameAndType", method = RequestMethod.POST)
	public Object listWarehouseNameAndType(@RequestBody WarehouseDTO warehouseDTO) {
		warehouseDTO.setUseState(0);
		return new ResponseModel(warehouseService.listWarehouse(warehouseDTO));
	}

	@ApiOperation(value = "仓库药房药品管理-获取仓库对应的药品", notes = "传入warehouseId,warehouseType,standardName,medicineName", response = WarehouseStandardDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "warehouseStandardDTO", value = "查询", required = true, dataType = "WarehouseStandardDTO") })
	@RequestMapping(value = "/listWarehouseStandard", method = RequestMethod.POST)
	public Object listWarehouseStandard(@RequestBody WarehouseStandardDTO warehouseStandardDTO) {
		if (warehouseStandardDTO.getWarehouseId() == null || warehouseStandardDTO.getWarehouseType() == null) {
			return new ResponseModel(null);
		}
		return new ResponseModel(warehouseService.listWarehouseStandard(warehouseStandardDTO));
	}
	@ApiOperation(value = "仓库药房药品管理-修改库位号", notes = "传入id,storageLocation", response = WarehouseStandardDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "warehouseStandardDO", value = "修改库位号", required = true, dataType = "WarehouseStandardDO") })
	@RequestMapping(value = "/updateStorageLocationById", method = RequestMethod.POST)
	public Object updateStorageLocationById(@RequestBody WarehouseStandardDO warehouseStandardDO){
		if (warehouseStandardDO.getId() == null) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		Integer result = warehouseService.updateStorageLocationById(warehouseStandardDO);
		if (result > 0) {
			WarehouseStandardDTO warehouseStandardDTO =new  WarehouseStandardDTO();
			warehouseStandardDTO.setId(warehouseStandardDO.getId());
			warehouseStandardDTO.setWarehouseType(warehouseStandardDO.getWarehouseType());
			List<WarehouseStandardDO> list=warehouseService.listWarehouseStandard(warehouseStandardDTO);
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS)
					.attr(ResponseModel.KEY_DATA, list==null||list.isEmpty()?list:list.get(0));
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}

	@ApiOperation(value = "仓库药房药品管理-获取载入的药品", notes = "传入:warehouseType = 仓库/药房种类，warehouseId = 仓库/药房id", response = MedicineStandardDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "medicineStandardDTO", value = "载入药品", required = true, dataType = "MedicineStandardDTO") })
	@RequestMapping(value = "/listLoadMedicine", method = RequestMethod.POST)
	public Object listLoadMedicine(@RequestBody MedicineStandardDTO medicineStandardDTO){
		if(medicineStandardDTO.getWarehouseType() == null){
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		Map<String,List<MedicineStandardDO>> map=new HashMap<String,List<MedicineStandardDO>>();
		List<MedicineStandardDO> list=medicineService.listNotLoadMedicine(medicineStandardDTO);
		List<MedicineStandardDO> load=medicineService.listLoadMedicine(medicineStandardDTO);
		List<MedicineStandardDO> notLoad=new ArrayList<>();
		Map<String,String> loadMap=new HashMap<>();
		for (MedicineStandardDO ms : load) {
			if (ms.getStandardId() != null) {
				loadMap.put(ms.getMedicineId().toString()+ms.getStandardId().toString(),"");
			} else {
				loadMap.put(ms.getMedicineId().toString(),"");
			}
		}
		for (MedicineStandardDO ms : list) {
			if (ms.getStandardId() != null) {
				if (loadMap.get(ms.getMedicineId().toString()+ms.getStandardId().toString()) == null) {
					notLoad.add(ms);
				}
			} else {
				if (loadMap.get(ms.getMedicineId().toString()) == null) {
					notLoad.add(ms);
				}
			}
		}
		map.put("load", load);
		map.put("notLoad", notLoad);
		return new ResponseModel(map);
	}

	@ApiOperation(value = "仓库药房药品管理-保存载入的药品", notes = "传入：warhouseid,list{获取载入药品接口--已载入药品的整个list}", response = WarehouseStandardDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loadMedicineDO", value = "保存载入药品", required = false, dataType = "LoadMedicineDO") })
	@RequestMapping(value = "/insertWarehouseStandard", method = RequestMethod.POST)
	public Object insertWarehouseStandard(@RequestBody LoadMedicineDO loadMedicineDO) {
		Integer result = warehouseService.insertWarehouseStandard(loadMedicineDO);
		if (result > 0) {
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "仓库药房药品管理-导入excel", notes = "传入file", response = Object.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "file", value = "导入", required = true, dataType = "MultipartFile") })
	@RequestMapping(value = "/warehouseImportExcel", method = RequestMethod.POST)
	public Object warehouseImportExcel(@RequestParam(value = "file") MultipartFile file) {
		Map<String, String> medicineMap = new HashMap<String, String>();
		medicineMap.put("唯一", "id");
		medicineMap.put("库位号", "storageLocation");
		
		List<WarehouseStandardDO> list = (List<WarehouseStandardDO>) ImportExcelUtil.getData(file, medicineMap, Constants.EXCEL_WAREHOUSE);
		if (CollectionUtils.isEmpty(list)) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.IS_NULL_EXCEL);
		}
		Map<String, String> map = new HashMap<>();
		String key = null;
		for (WarehouseStandardDO w : list) {
			key = w.getId().toString();
			if (map.get(key) != null) {
				return new ResponseModel().attr(ResponseModel.KEY_ERROR, "唯一："+key + "重复,请确认！");
			}
			map.put(key, "");
		}
		//修改库位号
		Integer result = warehouseService.updateStorageLocationByIds(list);
		if (result > Constants.STATE_ZERO) {
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}
}
