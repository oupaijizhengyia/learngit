package com.tangu.tcmts.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.po.MachineDO;
import com.tangu.tcmts.po.MachineDTO;
import com.tangu.tcmts.po.PrintPage;
import com.tangu.tcmts.po.PrintPageObj;
import com.tangu.tcmts.service.MachineService;
import com.tangu.tcmts.service.PrintService;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.PrintUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author:huangyifan
 * @Description:煎药机管理model
 * @Date: create in 10:53 2017/10/24
 */
@Api(value = "/machine", description = "煎药机管理")
@RestController
@RequestMapping("/machine")
public class MachineController {

	static final String MACHINE_CODE_EXISTS = "煎药机编号存在！";
	@Autowired
	MachineService machineService;
	@Autowired
	PrintService printService;

	@ApiOperation(value = "煎药机获取列表和条件查询", notes = "传入machineCode,machineStatus,useState", response = MachineDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "machineDTO", value = "查询", required = false, dataType = "MachineDTO") })
	@RequestMapping(value = "/listMachine", method = RequestMethod.POST)
	public Object listMachine(@RequestBody MachineDTO machineDTO) {
		return new ResponseModel(machineService.listMachines(machineDTO));
	}

	@ApiOperation(value = "启用煎药机", notes = "传入id", response = String.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "machineDTO", value = "启用", required = true, dataType = "MachineDTO") })
	@RequestMapping(value = "/openMachine", method = RequestMethod.POST)
	public Object openMachine(@RequestBody MachineDTO machineDTO, HttpServletRequest request) {
		machineDTO.setUseState(Constants.STATE_ONE);
		return getResponse(machineDTO, request);
	}

	@ApiOperation(value = "停用煎药机", notes = "传入id", response = String.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "machineDTO", value = "停用", required = true, dataType = "MachineDTO") })
	@RequestMapping(value = "/closeMachine", method = RequestMethod.POST)
	public Object closeMachine(@RequestBody MachineDTO machineDTO, HttpServletRequest request) {
		machineDTO.setUseState(Constants.STATE_ZERO);
		return getResponse(machineDTO, request);
	}

	@ApiOperation(value = "新增煎药机", notes = "传入machineCode,machineType,packMachineCode,packingMac,tpMac", response = String.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "machineDO", value = "新增", required = true, dataType = "MachineDO") })
	@RequestMapping(value = "/insertMachine", method = RequestMethod.POST)
	public Object insertMachine(@RequestBody MachineDO machineDO) {
		if (StringUtils.isBlank(machineDO.getMachineCode())) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		if (machineService.getMachineCode(machineDO)) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, MACHINE_CODE_EXISTS);
		}
		Integer result = machineService.insertMachine(machineDO);
		if (result > Constants.STATE_ZERO) {
			MachineDTO machineDTO = new MachineDTO();
			machineDTO.setId(machineDO.getId());
			List<MachineDO> list = machineService.listMachines(machineDTO);
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS)
					.attr(ResponseModel.KEY_DATA, list == null ? list : list.get(0));
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}

	@ApiOperation(value = "修改煎药机", notes = "传入id,machineCode,machineType,packMachineCode,packingMac,tpMac", response = String.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "machineDO", value = "修改", required = true, dataType = "MachineDO") })
	@RequestMapping(value = "/updateMachine", method = RequestMethod.POST)
	public Object updateMachine(@RequestBody MachineDO machineDO, HttpServletRequest request) {
		if (StringUtils.isBlank(machineDO.getMachineCode())) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		Integer id = JwtUserTool.getId();
		machineDO.setModUser(id);
		if (machineService.getMachineCode(machineDO)) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, MACHINE_CODE_EXISTS);
		}
		Integer result = machineService.updateMachine(machineDO);
		if (result > Constants.STATE_ZERO) {
			MachineDTO machineDTO = new MachineDTO();
			machineDTO.setId(machineDO.getId());
			List<MachineDO> list = machineService.listMachines(machineDTO);
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS)
					.attr(ResponseModel.KEY_DATA, list == null ? list : list.get(0));
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}

	@ApiOperation(value = "查询煎药机详情", notes = "传入id", response = MachineDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "machineDTO", value = "查看详情", required = true, dataType = "MachineDTO") })
	@RequestMapping(value = "/getMachineById", method = RequestMethod.POST)
	public Object getMachineById(@RequestBody MachineDTO machineDTO) {
		if (isId(machineDTO.getId())) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		return new ResponseModel(machineService.getMachineById(machineDTO));
	}

	@ApiOperation(value = "煎药机管理-打印条码", notes = "传入id", response = PrintPageObj.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "machineDTO", value = "查询", required = false, dataType = "MachineDTO") })
	@RequestMapping(value = "/printCode", method = RequestMethod.POST)
	public Object printCode(@RequestBody List<MachineDTO> list) {
		List<MachineDO> machineList = machineService.listMachinesById(list);
		PrintPage printPage = printService.getPrintPageByBillType("machine");
		List<PrintPageObj> resultList = new ArrayList<>();
		for (MachineDO machineDO : machineList) {
			resultList.add(PrintUtil.getPrintableCanvasObj(printPage, machineDO));
		}
		return resultList;
	}

	private static boolean isId(Integer id) {
		return id != null && id > Constants.STATE_ZERO ? false : true;
	}

	private ResponseModel getResponse(MachineDTO machineDTO, HttpServletRequest request) {
		if (isId(machineDTO.getId())) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		machineDTO.setModUser(JwtUserTool.getId());
		Integer result = machineService.updateUseState(machineDTO);
		if (result > Constants.STATE_ZERO) {
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}
}
