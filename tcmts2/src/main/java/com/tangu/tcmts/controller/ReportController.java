/**
 * FileName: ReportController
 * Author: laocu
 * Date: 2018/1/25 11:53
 */
package com.tangu.tcmts.controller;

import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.po.CountReport;
import com.tangu.tcmts.po.Report;
import com.tangu.tcmts.po.ReportDTO;
import com.tangu.tcmts.service.RecipeService;
import com.tangu.tcmts.util.CommonUtil;
import com.tangu.tcmts.util.Constants;
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

@Api(value = "report",description = "药品明细统计")
@RestController
@RequestMapping("report")
public class ReportController {

    @Autowired
    private RecipeService recipeService;

    @ApiOperation(tags = "出库药品统计", value = "列出出库药品", notes = "列出出库药品", response = Report.class)
    @ApiImplicitParam(name = "listOutMedicine", value ="列出出库药品",dataType = "ReportDTO")
    @RequestMapping(value = "listOutMedicine", method = RequestMethod.POST)
    public Object listOutMedicine(@RequestBody ReportDTO reportDTO){
        /*
        得到report列表
         */
        List<Report> reportList = new ArrayList<Report>();
        /*
        判定使用哪个语句
         */
        if(Constants.TYPE_ONE == reportDTO.getCountType()){
            reportList = recipeService.listReportMedicineBySettle(reportDTO);
        } else if (Constants.TYPE_TWO == reportDTO.getCountType()) {
            reportList = recipeService.listReportMedicineByHospital(reportDTO);
        }else {
            reportList = recipeService.listReportMedicineByMedicine(reportDTO);
        }
        CountReport countReport = CommonUtil.setCountValue(reportList);
        return new ResponseModel(reportList).attr("countReport",countReport);
    }



















}
