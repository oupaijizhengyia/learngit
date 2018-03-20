package com.tangu.tcmts.controller;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.po.Prescription;
import com.tangu.tcmts.po.PrescriptionDetail;
import com.tangu.tcmts.service.PrescriptionService;
import com.tangu.tcmts.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:yinghuaxu
 * @Description:协定方管理
 * @Date: create in 16:14 2017/11/1
 */
@Api(value = "prescription", description = "协定方管理")
@RestController
@RequestMapping("prescription")
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;

    @ApiOperation(tags = "prescription", value = "查找协定方", response = Prescription.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prescription", value = "查找协定方", dataType = "Prescription")
    })
    @RequestMapping(value = "getPrescription", method = RequestMethod.POST)
    public Object getPrescription(@RequestBody Prescription prescription) {
        return new ResponseModel(prescriptionService.getPrescriptionList(prescription));
    }

    @ApiOperation(tags = "prescription", value = "查找协定方详情", response = PrescriptionDetail.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prescription", value = "查找协定方详情", dataType = "Prescription")
    })
    @RequestMapping(value = "getPrescriptionDetail", method = RequestMethod.POST)
    public Object getPrescriptionDetail(@RequestBody Prescription prescription) {
        List<PrescriptionDetail> prescriptionDetail = prescriptionService.getPrescriptionDetailList(prescription.getId());
        List<PrescriptionDetail> prescriptionDetailList = new ArrayList<>();
        for (PrescriptionDetail prescriptionDetail2 : prescriptionDetail) {
            if (prescriptionDetail2.getUnitType() != null
                    && prescriptionDetail2.getUnitType() == Constants.TYPE_ONE) {
                prescriptionDetail2.setUnit(Constants.UNIT_G);
            }
            prescriptionDetailList.add(prescriptionDetail2);
        }
        return new ResponseModel(prescriptionDetailList);
    }

    @ApiOperation(tags = "prescription", value = "创建并保存协定方", notes = "返回参数:errorMsg:操作失败;feedbackMsg:操作成功"
            , response = Prescription.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prescription", value = "创建并保存协定方", dataType = "Prescription")
    })
    @RequestMapping(value = "createAndSavePrescription", method = RequestMethod.POST)
    public Object createAndSavePrescription(@RequestBody Prescription prescription) {
        //token获取id
        prescription.setModUser(JwtUserTool.getId());
        Integer result = 0;
        if (prescriptionService.repeatPrescription(prescription).size() > 0) {
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.PRESCRIPTION_EXIST);
        } else {
            if (prescription.getId() != null) {
                result = prescriptionService.updatePrescription(prescription);
            } else {
                result = prescriptionService.insertPrescription(prescription);
            }
        }
        if (result.equals(1)) {
            return new ResponseModel(prescriptionService.getPrescriptionList(prescription))
                    .attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(tags = "prescription", value = "修改协定方详情", notes = "返回参数:errorMsg:操作失败;feedbackMsg:操作成功"
            , response = Prescription.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prescription", value = "修改协定方详情", dataType = "Prescription")
    })
    @RequestMapping(value = "changePrescription", method = RequestMethod.POST)
    public Object createAndSavePrescriptionDetail(@RequestBody List<PrescriptionDetail> listPrescriptionDetail) {
        Integer deletePrescriptionDetail = prescriptionService.deletePrescriptionDetail(listPrescriptionDetail.get(0).getPrescriptionId());
        Integer result = 0;
        if (deletePrescriptionDetail.toString() != null) {
            for (PrescriptionDetail prescriptionDetail : listPrescriptionDetail) {
                result = prescriptionService.insertPrescriptionDetail(prescriptionDetail);
            }
        }
        if (result.equals(1)) {
            return new ResponseModel()
                    .attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(tags = "prescription", value = "删除协定方及其详情", notes = "返回参数:errorMsg:操作失败;feedbackMsg:操作成功"
            , response = Prescription.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "prescription", value = "删除协定方及其详情", dataType = "Prescription")
    })
    @RequestMapping(value = "deletePrescription", method = RequestMethod.POST)
    public Object deletePrescription(@RequestBody Prescription prescription) {
        Integer result = prescriptionService.deletePrescription(prescription.getId());
        if (result.equals(1)) {
            return new ResponseModel(prescriptionService.getPrescriptionList(prescription))
                    .attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }
}
