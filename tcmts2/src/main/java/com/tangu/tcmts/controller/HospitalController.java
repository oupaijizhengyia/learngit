package com.tangu.tcmts.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tangu.tcmts.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.po.HospitalDO;
import com.tangu.tcmts.po.HospitalDTO;
import com.tangu.tcmts.po.HospitalListVO;
import com.tangu.tcmts.service.HospitalService;
import com.tangu.tcmts.util.ChineseCharacterUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author:yinghuaxu
 * @Description:医院管理Controller
 * @Date: create in 8:59 2017/10/24
 */
@Api(value = "/hospital", description = "医院管理")
@RestController
@RequestMapping("/hospital")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    @ApiOperation(tags = "hospital", value = "查询医院列表", notes = "查询医院", response = HospitalListVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hospital", value = "查询医院列表", dataType = "HospitalDTO")
    })
    @RequestMapping(value = "/findHospitals", method = RequestMethod.POST)
    public Object findHospitals(@RequestBody HospitalDTO hospitalDTO) {
        return new ResponseModel(hospitalService.listHospitals(hospitalDTO));
    }

    @ApiOperation(tags = "hospital", value = "查询医院详情", notes = "查询医院详情ByID", response = HospitalDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hospital", value = "查询医院详情", dataType = "HospitalDTO")
    })
    @RequestMapping(value = "/hospitalContent", method = RequestMethod.POST)
    public Object hospitalContent(@RequestBody HospitalDTO hospitalDTO) {
        return new ResponseModel(hospitalService.getHospitalContent(hospitalDTO.getId()));
    }

    @ApiOperation(tags = "hospital", value = "新增医院", notes = "返回参数:errorMsg:操作失败;feedbackMsg:操作成功"
            , response = HospitalListVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createAndSaveHospital", value = "新增并保存医院", dataType = "HospitalDO")
    })
    @RequestMapping(value = "/createAndSaveHospital", method = RequestMethod.POST)
    public Object insertHospital(@RequestBody HospitalDO hospitalDO) {
        Integer result = Constants.STATE_ZERO;
        //token获取当前id
        hospitalDO.setModUser(JwtUserTool.getId());
        hospitalDO.setInitialCode(ChineseCharacterUtil.convertHanzi2Pinyin(hospitalDO.getHospitalNickname()));
        if (hospitalService.getRepeatHospital(hospitalDO) > Constants.STATE_ZERO) {
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.HOSPITAL_NAME_EXIST);
        }
        try {
            if (hospitalDO.getId() != null) {
                //获取当前时间
                hospitalDO.setModTime(new Date(System.currentTimeMillis()));
                result = hospitalService.updateHospital(hospitalDO);
            } else {
                result = hospitalService.insertHospital(hospitalDO);
            }
        } catch (Exception e) {
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.HOSPITAL_CODE_EXIST);
        }
        HospitalDO hospitalId = new HospitalDO();
        hospitalId.setId(hospitalDO.getId());
        if (result > Constants.STATE_ZERO) {
            return new ResponseModel(hospitalService.getHospitalContent(hospitalId.getId()))
                    .attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(tags = "hospital", value = "启用/停用医院", notes = "返回参数:errorMsg:操作失败;feedbackMsg:操作成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "changeHospitalState", value = "启用/停用医院", dataType = "HospitalDTO")
    })
    @RequestMapping(value = "/changeHospitalState", method = RequestMethod.POST)
    public Object changeHospitalState(@RequestBody HospitalDTO hospitalDTO) {
        //token获取当前id
        hospitalDTO.setModUser(JwtUserTool.getId());
        //获取当前时间
        hospitalDTO.setModTime(new Date(System.currentTimeMillis()));
        Integer result = hospitalService.updateState(hospitalDTO);
        if (result > Constants.STATE_ZERO) {
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

    @ApiOperation(tags = "hospital", value = "医院名称(返HospitalName)")
    @RequestMapping(value = "/getHospitalName", method = RequestMethod.GET)
    public Object getHospitalName() {
        return new ResponseModel(hospitalService.getHospitalName());
    }

    @ApiOperation(tags = "hospital", value = "医院名称(返HospitalCode)")
    @RequestMapping(value = "/getHospitalNameByCode", method = RequestMethod.GET)
    public Object getHospitalNameByCode() {
        return new ResponseModel(hospitalService.getHospitalNameByCode());
    }

    @ApiOperation(tags = "hospital", value = "药品关联-获取药品列表和名称查询", notes = "传入：HospitalCompany", response = HospitalListVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hospitalDTO", value = "获取医院名称", dataType = "HospitalDTO")
    })
    @RequestMapping(value = "/listHospitalName", method = RequestMethod.POST)
    public Object listHospitalName(@RequestBody HospitalDTO hospitalDTO) {
        List<HospitalListVO> list = new ArrayList<>();
        if (hospitalDTO.getHospitalCompany() == null || StringUtils.isBlank(hospitalDTO.getHospitalCompany())) {
            HospitalListVO lv = new HospitalListVO();
            lv.setHospitalCompany("通用药品");
            lv.setId(Constants.STATE_ZERO);
            list.add(lv);
        }
        list.addAll(hospitalService.listHospitalName(hospitalDTO));
        return new ResponseModel(list);
    }
}
