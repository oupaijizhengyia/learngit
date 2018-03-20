package com.tangu.tcmts.controller;

import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.po.ShelvesMedicineDTO;
import com.tangu.tcmts.po.ShelvesMedicineManage;
import com.tangu.tcmts.service.ShelvesMedicineManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/1/10.
 */

@Api(value = "/shelvesMedicineManage", description = "货架药品管理")
@RestController
@RequestMapping("/shelvesMedicineManage")
public class ShelvesMedicineManagerController {

    @Autowired
    private ShelvesMedicineManageService shelvesMedicineService;

    @ApiOperation(value = "获取条件查询后的货架药品信息",notes = "传入shelvesCode,shelvesType,sysRecipeCode,invoiceCode,recipientName", response = ShelvesMedicineDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shelvesMedicineManage", value = "查询", required = false, dataType = "ShelvesMedicineManage") })
    @RequestMapping(value = "/getShelvesMedicine", method = RequestMethod.POST)
    public Object getShelvesMedicine(@RequestBody ShelvesMedicineManage shelvesMedicineManage){
        return new ResponseModel(shelvesMedicineService.listShelvesMedicineDTOByCondition(shelvesMedicineManage));
    }
}
