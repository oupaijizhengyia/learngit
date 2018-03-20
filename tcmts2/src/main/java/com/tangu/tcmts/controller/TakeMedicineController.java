package com.tangu.tcmts.controller;

import java.util.ArrayList;
import java.util.List;

import com.tangu.tcmts.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.po.RecipeDTO;
import com.tangu.tcmts.po.RecipeProccess;
import com.tangu.tcmts.po.RecipeScan;
import com.tangu.tcmts.po.RecipeSerial;
import com.tangu.tcmts.service.RecipeService;
import com.tangu.tcmts.service.TakeMedicineService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value = "takeMedicine", description = "发药")
@RestController
@RequestMapping("takeMedicine")
@Slf4j
public class TakeMedicineController {

	@Autowired
	private TakeMedicineService takeMedicineService;
	@Autowired
	RecipeService recipeService;

	@ApiOperation(value = "门诊发药", notes = "传入 recipeSerial", response = RecipeSerial.class)
	@RequestMapping(value = "transferMedicineOperation", method = RequestMethod.POST)
	public Object takeMedicineOperation(@RequestBody RecipeSerial recipeSerial) {
		return takeMedicineService.takeMedicineInfo(recipeSerial);
	}

	@ApiOperation(value = "查询发药列表", notes = "传入 startTime, endTime, shippingState, invoiceCode,"
			+ "recipientName, outpatientNum, shelvesCode", response = RecipeSerial.class)
	@RequestMapping(value = "listTakeMedicine", method = RequestMethod.POST)
	public Object listTakeMedicine(@RequestBody RecipeSerial recipeSerial) {
		return takeMedicineService.listTakeMedicine(recipeSerial);
	}
	@ApiOperation(value = "确认发药", notes = "传入 recipeId", response = Object.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "recipeSerial", value = "新增", required = true, dataType = "RecipeSerial") })
	@RequestMapping(value = "confirmationDeliverGoods", method = RequestMethod.POST)
	public Object confirmationDeliverGoods(@RequestBody List<RecipeSerial> list) {
		if (CollectionUtils.isEmpty(list)) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, "请传入处方id!");
		}
		try {
			Integer id = JwtUserTool.getId();
			List<RecipeDTO> recipeList = new ArrayList<>();
			List<RecipeProccess> proccessList = new ArrayList<>();
			List<RecipeScan> scanList = new ArrayList<>();
			for (RecipeSerial r : list) {
//				if(r.getShippingState() >= Constants.STATE_DELIVERY_BILL){
//					return new ResponseModel().attr(ResponseModel.KEY_ERROR, "该药品已经被取走!");
//				}
				recipeList.add(new RecipeDTO(100, r.getRecipeId()));
				proccessList.add(new RecipeProccess(r.getRecipeId(), id));
				scanList.add(new RecipeScan(r.getRecipeId(), id, 100));
			}
			
			if (recipeService.updateShippingState(recipeList) > 0) {
				/**
				 * 修改发药时间
				 */
				if (CollectionUtils.isNotEmpty(proccessList)) {
					recipeService.updatesProccessDeliveryTime(proccessList);
				}
				/**
				 * 添加操作流程
				 */
				if (CollectionUtils.isNotEmpty(scanList)) {
					recipeService.insertRecipeScan(scanList);
				}
				return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}

}
