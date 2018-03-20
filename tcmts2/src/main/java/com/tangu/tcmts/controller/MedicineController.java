package com.tangu.tcmts.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.dao.ExportXmlMapper;
import com.tangu.tcmts.po.MedicineDO;
import com.tangu.tcmts.po.MedicineDTO;
import com.tangu.tcmts.po.MedicineStandardDO;
import com.tangu.tcmts.po.PrintPage;
import com.tangu.tcmts.po.PrintPageObj;
import com.tangu.tcmts.po.PublicDO;
import com.tangu.tcmts.po.SysLookup;
import com.tangu.tcmts.service.MedicineService;
import com.tangu.tcmts.service.MedicineStandardService;
import com.tangu.tcmts.service.PrintService;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.ImportExcelUtil;
import com.tangu.tcmts.util.PrintUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author:huangyifan
 * @Description:中药管理model
 * @Date: create in 11：49 2017/10/31
 */
@Api(value = "/medicine", description = "中药管理")
@RestController
@RequestMapping("/medicine")
public class MedicineController {
	// 快速查找药品里一次返回最大数量，因需求还不明确所以目前暂住定为10，后期可考虑写到数据库配置里［Lei］
	private static final int quickSearchLimit = 10;

	private static final String MEDICINE_NAME_EXIST = "药品名称已存在!";
	private static final String MEDICINE_CODE_EXIST = "药品编码已存在!";
	private static final String STANDARD_CODE_EXIST = "规格编码已存在!";
	private static final String STANDARD_NAME_AREA_EXIST = "规格、产地、企业不能同时重复!";
	private static final String KG = "公斤";
	private static final String G = "g";
	@Autowired
	MedicineService medicineService;
	@Autowired
	PrintService printService;
	@Autowired
	MedicineStandardService medicineStandardService;
	@Autowired
	ExportXmlMapper exportXmlMapper;

	@ApiOperation(value = "中药管理列表和条件查询", notes = "传入medicineName,medicineCode,useState", response = MedicineDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "medicineDTO", value = "查询", required = false, dataType = "MedicineDTO") })
	@RequestMapping(value = "/listMedicine", method = RequestMethod.POST)
	public Object listMedicine(@RequestBody MedicineDTO medicineDTO) {

		return new ResponseModel(medicineService.listMedicine(medicineDTO));
	}

	@ApiOperation(value = "获取平台编码下拉", response = SysLookup.class)
	@RequestMapping(value = "/listSunMedicineCode", method = RequestMethod.GET)
	public Object listSunMedicineCode() {
		return new ResponseModel(medicineService.listSunMedicineCode());
	}

	@ApiOperation(value = "中药管理：新增/修改", notes = "参数看RAP", response = Object.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "medicineDO", value = "新增", required = true, dataType = "MedicineDO") })
	@RequestMapping(value = "/insertMedicine", method = RequestMethod.POST)
	public Object insertMedicine(@RequestBody MedicineDO medicineDO, HttpServletRequest request) {
		ResponseModel response = this.ifUniqueness(medicineDO);
		if (response != null) {
			return response;
		}
		Integer id = JwtUserTool.getId();
		medicineDO.setModUser(id);
		if (medicineDO.getId() != null) {// 修改
//			medicineDO.getMedicinePrice().setModUser(id);
			return medicineService.update(medicineDO);
		}
		// 新增
		return medicineService.insertMedicine(medicineDO);
	}

	@ApiOperation(value = "中药管理：查看详情", notes = "传入id", response = MedicineDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "medicineDTO", value = "查看详情", required = true, dataType = "MedicineDTO") })
	@RequestMapping(value = "/getMedicineById", method = RequestMethod.POST)
	public Object getMedicineById(@RequestBody MedicineDTO medicineDTO) {
		if (medicineDTO.getId() == null) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		return new ResponseModel(medicineService.getMedicineById(medicineDTO));
	}

	@ApiOperation(value = "中药管理：启用药品", notes = "传入id", response = Object.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "medicineDTO", value = "启用", required = false, dataType = "MedicineDTO") })
	@RequestMapping(value = "/openMedicine", method = RequestMethod.POST)
	public Object openMedicine(@RequestBody MedicineDTO medicineDTO, HttpServletRequest request) {
		medicineDTO.setUseState(Constants.STATE_ZERO);
		return getResponse(medicineDTO, request);
	}

	@ApiOperation(value = "中药管理：停用药品", notes = "传入id", response = Object.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "medicineDTO", value = "启用", required = false, dataType = "MedicineDTO") })
	@RequestMapping(value = "/closeMedicine", method = RequestMethod.POST)
	public Object closeMedicine(@RequestBody MedicineDTO medicineDTO, HttpServletRequest request) {
		medicineDTO.setUseState(Constants.STATE_ONE);
		return getResponse(medicineDTO, request);
	}

	// TODO 优化我[Lei]
	@ApiOperation(value = "批量调价，实时获取药品名称", notes = "传入medicineName", response = PublicDO.class)
	@RequestMapping(value = "/getMedicineName/{medicineName}", method = RequestMethod.GET)
	public Object getMedicineName(@PathVariable String medicineName) throws UnsupportedEncodingException {
		final String key = URLDecoder.decode(medicineName, "UTF-8").toUpperCase();
		List<MedicineDO> list = medicineService.listMedicineForLookup();
		for (MedicineDO m : list) {
			if (m.getStandard().equals(KG)) {
				m.setStandard(G);
			}
		}
		List<MedicineDO> firstClass = iteratorMedicineMap(list, key);
		List<PublicDO> publicList = firstClass.stream().map(mdo -> new PublicDO(mdo.getId(), mdo.getMedicineName(), 
				mdo.getMedicineName(), mdo.getId(), mdo.getStandard(), mdo.getUnitType(), mdo.getSpecialBoilType()))
				.collect(Collectors.toList());
		return new ResponseModel(publicList);

	}
	
	@ApiOperation(value = "中药管理-批量打印条码", notes = "传入id", response = PrintPageObj.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "list", value = "打印", required = false, dataType = "MedicineDTO") })
	@RequestMapping(value = "/printCode", method = RequestMethod.POST)
	public Object printCode(@RequestBody List<MedicineDTO> list) {
		/**
		 * 批量打印规格条码
		 */
		List<MedicineStandardDO> medicineList = medicineStandardService.listMedicineStandardByMedicineId(list);
		PrintPage printPage = printService.getPrintPageByBillType(Constants.PrintType.MEDICINE_STANDARD_CODE);
		List<PrintPageObj> resultList = new ArrayList<>();
		PrintPageObj obj = null;
		for (MedicineStandardDO medicineDO : medicineList) {
			obj = PrintUtil.getPrintableCanvasObj(printPage, medicineDO);
			if (obj != null) {
				resultList.add(obj);
			}
		}
		return resultList;
	}
	@ApiOperation(value = "中药管理-单个打印规格条码", notes = "传入规格id", response = PrintPageObj.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "medicineStandardDTO", value = "打印", required = false, dataType = "MedicineStandardDTO") })
	@RequestMapping(value = "/printStandardCode", method = RequestMethod.POST)
	public Object printStandardCode(@RequestBody MedicineStandardDO medicineStandardDO){
		MedicineStandardDO medicine = medicineStandardService.getStandardById(medicineStandardDO);
		PrintPage printPage = printService.getPrintPageByBillType(Constants.PrintType.MEDICINE_STANDARD_CODE);
		List<PrintPageObj> resultList = new ArrayList<>();
		PrintPageObj obj = PrintUtil.getPrintableCanvasObj(printPage, medicine);
		if (obj != null) {
			resultList.add(obj);
		}
		return resultList;
	}
	
	//没有需求，只是暂时写了一个导入的模板
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "中药管理-导入excel", notes = "传入file", response = Object.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "file", value = "导入", required = true, dataType = "MultipartFile") })
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public Object uploadFile(@RequestParam(value = "file") MultipartFile file) {
		Map<String, String> medicineMap = new HashMap<String, String>();
		medicineMap.put("药品名称", "medicineName");
		medicineMap.put("药品编码", "medicineCode");
		List<MedicineDO> list = (List<MedicineDO>) ImportExcelUtil.getData(file, medicineMap, Constants.EXCEL_MEDICINE);
		if (CollectionUtils.isEmpty(list)) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.IS_NULL_EXCEL);
		}
		Map<String, String> map = new HashMap<>();
		String key = null;
		for (MedicineDO medicine : list) {
			key = medicine.getMedicineName() + "/" + medicine.getMedicineCode();
			if (map.get(key) != null) {
				return new ResponseModel().attr(ResponseModel.KEY_ERROR, key + "重复,请确认！");
			}
			map.put(key, "");
		}
		Integer result = medicineService.addMedicines(list);
		if (result > Constants.STATE_ZERO) {
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}

	private ResponseModel getResponse(MedicineDTO medicineDTO, HttpServletRequest request) {
		Integer id = JwtUserTool.getId();
		medicineDTO.setModUser(id);
		if (medicineDTO.getId() == null) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		Integer result = medicineService.updateState(medicineDTO);
		if (result > Constants.STATE_ZERO) {
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}

	private static List<MedicineDO> iteratorMedicineMap(List<MedicineDO> list, String key) {
		Map<Integer, List<MedicineDO>> map = list.stream().collect(Collectors.groupingBy(medicineDO -> {
			int initIndex = medicineDO.getInitialCode().toUpperCase().indexOf(key);
			if (initIndex == Constants.STATE_ZERO) {
				return Constants.STATE_ZERO;
			}
			int mnenemIndix = medicineDO.getMnemonicCode().toUpperCase().indexOf(key);
			if (mnenemIndix == Constants.STATE_ZERO) {
				return Constants.STATE_ZERO;
			}
			int nameIndex = medicineDO.getMedicineName().toUpperCase().indexOf(key);
			if (nameIndex == Constants.STATE_ZERO) {
				return Constants.STATE_ZERO;
			}
			if (initIndex > Constants.STATE_ZERO || mnenemIndix > Constants.STATE_ZERO || nameIndex > Constants.STATE_ZERO) {
				return Constants.STATE_ONE;
			}
			return -1;
		}));

		// 获取最左匹配
		List<MedicineDO> firstClass = map.getOrDefault(0, new ArrayList<>()).stream().limit(quickSearchLimit)
				.collect(Collectors.toList());
		// 获取中间匹配
		int more = quickSearchLimit - firstClass.size();
		List<MedicineDO> secondClass = map.getOrDefault(1, new ArrayList<>()).stream().limit(more)
				.collect(Collectors.toList());
		firstClass.addAll(secondClass);

		return firstClass;
	}

	private ResponseModel ifUniqueness(MedicineDO medicineDO) {
		if (medicineDO.getMedicineName() == null || StringUtils.isBlank(medicineDO.getMedicineName())
				|| medicineDO.getStandard() == null || StringUtils.isBlank(medicineDO.getStandard())) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		MedicineDO medicine = new MedicineDO();
		medicine.setId(medicineDO.getId());
		medicine.setMedicineName(medicineDO.getMedicineName());
		if (medicineService.getMedicineByName(medicine) > Constants.STATE_ZERO) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, MEDICINE_NAME_EXIST);
		}
		medicine.setMedicineName(null);
		medicine.setMedicineCode(medicineDO.getMedicineCode());
		if (medicine.getMedicineCode() != null && !StringUtils.isBlank(medicine.getMedicineCode())
				&& medicineService.getMedicineByName(medicine) > Constants.STATE_ZERO) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, MEDICINE_CODE_EXIST);
		}
		
		if (CollectionUtils.isNotEmpty(medicineDO.getStandardList())) {
			Integer size = medicineDO.getStandardList().size();
			Map<String, String> map = new HashMap<>();
			for (int i = 0; i < size; i++) {
				if ("".equals(medicineDO.getStandardList().get(i).getStandardCode())){
					medicineDO.getStandardList().get(i).setStandardCode(null);
				}
				if (medicineDO.getStandardList().get(i).getProducingArea() == null) {
					medicineDO.getStandardList().get(i).setProducingArea("");
				}
				if (medicineDO.getStandardList().get(i).getManufacturingEnterprise() == null) {
					medicineDO.getStandardList().get(i).setManufacturingEnterprise("");
				}
				String key = medicineDO.getStandardList().get(i).getStandardName()
						+ medicineDO.getStandardList().get(i).getProducingArea()
						+ medicineDO.getStandardList().get(i).getManufacturingEnterprise();
				if (map.get(key) != null) {
					return new ResponseModel().attr(ResponseModel.KEY_ERROR, STANDARD_NAME_AREA_EXIST);
				}
				map.put(key, "");
			}
			if (medicineStandardService.listStandardByCode(medicineDO) > Constants.STATE_ZERO) {
				return new ResponseModel().attr(ResponseModel.KEY_ERROR, STANDARD_CODE_EXIST);
			}
		}
		return null;
	}
	
}
