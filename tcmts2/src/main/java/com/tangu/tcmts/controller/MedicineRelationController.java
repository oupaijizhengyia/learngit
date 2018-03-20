package com.tangu.tcmts.controller;

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
import com.tangu.tcmts.po.MedicineRelationDO;
import com.tangu.tcmts.po.MedicineRelationDTO;
import com.tangu.tcmts.service.MedicineRelationService;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.MedicineUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author:huangyifan
 * @Description:药品关联model
 * @Date: create in 18：41 2017/11/6
 */
@Api(value = "/medicineRelation", description = "药品关联")
@RestController
@RequestMapping("/medicineRelation")
public class MedicineRelationController {
	
	private static final String MEDICINE_NAME_EXIST = "医院药品名称已存在!";
	private static final String MEDICINE_CODE_EXIST = "医院药品编码已存在!";
	@Autowired
	MedicineRelationService medicineRelationService;

	@ApiOperation(value = "药品关联-根据医院获取药品", notes = "传入,hospitalId=医院id,medicineNameOrCode = 药品名称/药品编码", response = MedicineRelationDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "medicineRelationDTO", value = "根据医院获取药品列表", required = false, dataType = "MedicineRelationDTO") })
	@RequestMapping(value = "/listMedicineByHospital", method = RequestMethod.POST)
	public Object listMedicineByHospital(@RequestBody MedicineRelationDTO medicineRelationDTO) {
		if (medicineRelationDTO.getHospitalId() == null) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		List<MedicineRelationDO> list = medicineRelationService.listMedicineRelation(medicineRelationDTO);
		return new ResponseModel(list);
	}

	@ApiOperation(value = "药品关联-批量新增药品关联", notes = "传入hospitalMedicineName,hospitalMedicineCode,nativeMedicineId,hospitalId", response = MedicineRelationDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "list", value = "根据医院获取药品列表", required = true, dataType = "MedicineRelationDO") })
	@RequestMapping(value = "/insertMedicineRelation", method = RequestMethod.POST)
	public Object insertMedicineRelation(@RequestBody List<MedicineRelationDO> list) {
		if (list == null || list.isEmpty()) {
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
		}
		MedicineRelationDTO md = new MedicineRelationDTO();
		md.setHospitalId(list.get(0).getHospitalId());
		List<MedicineRelationDO> list1 = medicineRelationService.listMedicineRelation(md);
		Map<String, Map<String, String>> nativeMap = MedicineUtil.nativeHospitalMedicineMap(list1);
		Map<String, String> nameMap = nativeMap.get(MedicineUtil.NAME);
		Map<String, String> codeMap = nativeMap.get(MedicineUtil.CODE);
		//判断新增列表里面是否有重复的
		String result1 = MedicineUtil.ifRepeat(list);
		if (result1 != null) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, result1);
		}
		for (MedicineRelationDO m : list) {
			if (nameMap.get(m.getHospitalMedicineName()) != null) {
				return new ResponseModel().attr(ResponseModel.KEY_ERROR, "医院药品名称："+m.getHospitalMedicineName()+"已存在关联！");
			}
			if (codeMap.get(m.getHospitalMedicineCode()) != null) {
				return new ResponseModel().attr(ResponseModel.KEY_ERROR, "医院药品编码："+m.getHospitalMedicineCode()+"已存在关联！");
			}
		}
		
		Integer result = medicineRelationService.insertMedicineRelation(list);
		if (result > Constants.STATE_ZERO) {
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}

	@ApiOperation(value = "药品关联-修改关联药品", notes = "传入id,hospitalMedicineName=医院药品名称，hospitalMedicineCode=医院药品编码，nativeMedicineId=本地药品id，hospitalId =医院id", response = Object.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "medicineRelationDO", value = "修改", required = true, dataType = "MedicineRelationDO") })
	@RequestMapping(value = "/updateMedicineRelationById", method = RequestMethod.POST)
	public Object updateMedicineRelationById(@RequestBody MedicineRelationDO medicineRelationDO) {
		ResponseModel response = ifNull(medicineRelationDO);
		if (response != null) {
			return response;
		}
		Integer result = medicineRelationService.updateMedicineRelationById(medicineRelationDO);
		if (result > Constants.STATE_ZERO) {
			MedicineRelationDTO medicineRelationDTO = new MedicineRelationDTO();
			medicineRelationDTO.setId(medicineRelationDO.getId());
			List<MedicineRelationDO> list = medicineRelationService.listMedicineRelation(medicineRelationDTO);
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS)
					.attr(ResponseModel.KEY_DATA, list == null ? list : list.get(0));
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}

	@ApiOperation(value = "药品关联-删除关联药品", notes = "传入id， 返回，成功:feedbackMsg,失败：errorMsg", response = MedicineRelationDTO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "medicineRelationDTO", value = "删除", required = true, dataType = "MedicineRelationDTO") })
	@RequestMapping(value = "/deleteMedicineRelationById", method = RequestMethod.POST)
	public Object deleteMedicineRelationById(@RequestBody MedicineRelationDTO medicineRelationDTO) {
		if (medicineRelationDTO.getId() == null) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		Integer result = medicineRelationService.deleteMedicineRelationById(medicineRelationDTO);
		if (result > Constants.STATE_ZERO) {
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}

	private ResponseModel ifNull(MedicineRelationDO medicineRelationDO) {
		if (medicineRelationDO.getId() == null || (medicineRelationDO.getHospitalMedicineCode() == null
				&& StringUtils.isBlank(medicineRelationDO.getHospitalMedicineCode())
				&& medicineRelationDO.getHospitalMedicineName() == null
				&& StringUtils.isBlank(medicineRelationDO.getHospitalMedicineName()))) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		List<MedicineRelationDO> list = medicineRelationService.getMedicineRelationByName(medicineRelationDO);
		Map<String, String> nameMap = new HashMap<>();
		Map<String, String> codeMap = new HashMap<>();
		for (MedicineRelationDO m : list) {
			if (m.getHospitalMedicineCode() != null && !"".equals(m.getHospitalMedicineCode())) {
				codeMap.put(m.getHospitalMedicineCode(), "");
			}
			if (m.getHospitalMedicineName() != null && !"".equals(m.getHospitalMedicineName())) {
				nameMap.put(m.getHospitalMedicineName(), "");
			}
		}
		if (nameMap.get(medicineRelationDO.getHospitalMedicineName()) != null) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, MEDICINE_NAME_EXIST);
		}
		if (codeMap.get(medicineRelationDO.getHospitalMedicineCode()) != null) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, MEDICINE_CODE_EXIST);
		}
		return null;
	}
}
