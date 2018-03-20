package com.tangu.tcmts.controller;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.po.MedicineTaboo;
import com.tangu.tcmts.service.MedicineTabooService;
import com.tangu.tcmts.util.Constants;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medicineTaboo")
public class MedicineTabooController {
    @Autowired
    private MedicineTabooService medicineTabooService;

    @ApiOperation(tags = "medicineTaboo", value = "查询配伍禁忌列表", notes = "传入parentMedicineId", response = MedicineTaboo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "medicineTaboo", value = "查询配伍禁忌列表", dataType = "MedicineTaboo")
    })
    @RequestMapping(value = "/findMedicineTaboo", method = RequestMethod.POST)
    public Object findMedicineTaboo(@RequestBody MedicineTaboo medicineTaboo) {
        return new ResponseModel(medicineTabooService.getMedicineTabooList(medicineTaboo));
    }

    @ApiOperation(tags = "medicineTaboo", value = "新增并保存禁忌列表", notes = "返回参数:errorMsg:操作失败;feedbackMsg:操作成功"
            , response = MedicineTaboo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "medicineTaboo", value = "新增并保存禁忌列表", dataType = "MedicineTaboo")
    })
    @RequestMapping(value = "/createAndSaveMedicineTaboo", method = RequestMethod.POST)
    public Object createAndSaveMedicineTaboo(@RequestBody MedicineTaboo medicineTaboo) {
        //token获取id
        medicineTaboo.setModUser(JwtUserTool.getId());
        Integer result = 0;
        //判断是否存在重复现象
        if (medicineTabooService.getRepeatMedicineTaboo(medicineTaboo) != null) {
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MEDICINE_TABOO_REPEAT);
        }
        if (medicineTaboo.getParentMedicineId().equals(medicineTaboo.getSubMedicineId())) {
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MEDICINE_REPEATE);
        }
        if (medicineTaboo.getId() != null) {
            result = medicineTabooService.updateMedicineTaboo(medicineTaboo);
        } else {
            result = medicineTabooService.insertMedicineTaboo(medicineTaboo);
        }
        List<MedicineTaboo> medicineTabooResult  = medicineTabooService.getMedicineTabooList(medicineTaboo);
        MedicineTaboo medicineTabooR=medicineTabooResult.get(0);
        if (result > Constants.STATE_ZERO) {
            return new ResponseModel(medicineTabooR)
                    .attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(tags = "medicineTaboo", value = "删除配伍禁忌", notes = "传入id，返回参数:errorMsg:操作失败;feedbackMsg:操作成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "medicineTaboo", value = "删除配伍禁忌", dataType = "MedicineTaboo")
    })
    @RequestMapping(value = "deleteMedicineTaboo", method = RequestMethod.POST)
    public Object deleteMedicineTaboo(@RequestBody MedicineTaboo medicineTaboo) {
        Integer result = medicineTabooService.deleteMedicineTaboo(medicineTaboo.getId());
        if (result > Constants.STATE_ZERO) {
            return new ResponseModel(medicineTabooService.getMedicineTabooList(medicineTaboo))
                    .attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }
}
