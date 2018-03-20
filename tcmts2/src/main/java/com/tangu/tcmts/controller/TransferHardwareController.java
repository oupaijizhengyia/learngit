package com.tangu.tcmts.controller;

import com.tangu.common.util.entity.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by Fenglei on 2018/1/11.
 */
@Api(value = "TransferHardware", description = "传送带硬件接口")
@RestController
@RequestMapping("/transfer")
public class TransferHardwareController {

    @ApiOperation(tags = "传送带硬件接口", value = "处方绑定判断及加水量数据", notes = "传入rfid,返回加水量" )
    @RequestMapping(value = "/getVolumn/{rfid}", method = RequestMethod.GET)
    public Object getVolumn(@PathVariable String rfid) {
        HashMap<String,Number> result = new HashMap<>();
        result.put("data",2.5);
        return new ResponseModel(result);
    }

    @ApiOperation(tags = "传送带硬件接口", value = "开始浸泡", notes = "传入rfid,系统触发开始浸流程")
    @RequestMapping(value = "/startDip/{rfid}", method = RequestMethod.GET)
    public Object startDip(@PathVariable String rfid) {
        HashMap result = new HashMap<>();
        result.put("data","保存成功!");
        return new ResponseModel(result);
    }

    @ApiOperation(tags = "传送带硬件接口", value = "待行区满推送", notes = "派工区域获取,rfid")
    @RequestMapping(value = "/getZoonNum/{num}/{rfid}", method = RequestMethod.GET)
    public Object getZoonNum(@PathVariable String num, @PathVariable String rfid) {
        HashMap result = new HashMap<>();
        result.put("data","1");
        return new ResponseModel(result);
    }

    @ApiOperation(tags = "传送带硬件接口", value = "待行区满推送", notes = "传入状态(0:有空,1:满了), rfid")
    @RequestMapping(value = "/setZoonState/{state}/{rfid}", method = RequestMethod.GET)
    public Object setZoonState(@PathVariable String state, @PathVariable String rfid) {
        HashMap result = new HashMap<>();
        result.put("data","保存成功!");
        return new ResponseModel(result);
    }


}
