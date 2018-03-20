package com.tangu.tcmts.controller;

import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.po.Printer;
import com.tangu.tcmts.service.PrintService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Api
@RestController
@RequestMapping("service")
public class PublicController {
    @Autowired
    private PrintService printService;

    @ApiOperation(tags = "service",value = "打印信息")
    @RequestMapping(value = "/printMessage",method = RequestMethod.GET)
    public Object printMessage(HttpServletRequest req){
        Map<String,List<Printer>> map=new HashMap<String,List<Printer>>(16);
        map.put("printerList",printService.listPrinter());
        List<Printer> list=printService.listPrintBackgroundImg();
        map.put("printPageList",list);
        return new ResponseModel(map);
    }
}
