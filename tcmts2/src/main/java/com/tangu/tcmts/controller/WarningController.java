/**
 * FileName: WarningController
 * Author: yeyang
 * Date: 2018/1/30 9:50
 */
package com.tangu.tcmts.controller;

import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.po.WarningDO;
import com.tangu.tcmts.po.WarningDTO;
import com.tangu.tcmts.service.WarningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "预警信息",description = "预警信息")
@RestController
@RequestMapping("warning")
public class WarningController {

    @Autowired
    private WarningService warningService;

    @ApiOperation(tags = "预警信息",response = WarningDO.class,value = "列出预警信息")
    @ApiImplicitParam(name = "warningDTO",dataType = "WarningDTO",value = "查询")
    @RequestMapping(value = "listWarning",method = RequestMethod.POST)
    public Object listWarning(@RequestBody WarningDTO warningDTO){
        List<WarningDO> warningDOList = warningService.listWarning(warningDTO);
        return new ResponseModel(warningDOList);
    }

}
