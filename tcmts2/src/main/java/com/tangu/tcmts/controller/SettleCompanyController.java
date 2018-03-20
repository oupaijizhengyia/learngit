package com.tangu.tcmts.controller;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.po.SettleCompanyHistory;
import com.tangu.tcmts.po.SpecialDiscount;
import com.tangu.tcmts.util.ChineseCharacterUtil;
import com.tangu.tcmts.po.SettleCompanyDO;
import com.tangu.tcmts.po.SettleCompanyDTO;
import com.tangu.tcmts.service.SettleCompanyService;
import com.tangu.tcmts.util.Constants;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author:yinghuaxu
 * @Description:结算方MODEL
 * @Date: create in 14:33 2017/10/20
 */
@Api(value = "/settleCompany", description = "结算方")
@RestController
@RequestMapping("/settleCompany")
public class SettleCompanyController {
    @Autowired
    private SettleCompanyService settleCompanyService;

    @ApiOperation(tags = "settleCompany", value = "获取结算方列表", notes = "传入settleCompanyName,priceTypeId,isDeleted"
            , response = SettleCompanyDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "settleCompanyDTO", value = "结算方查询", required = true, dataType = "SettleCompanyDTO")
    })
    @RequestMapping(value = "/findSettleCompany", method = RequestMethod.POST)
    public Object findSettleCompany(@RequestBody SettleCompanyDTO settleCompanyDTO) {
        return new ResponseModel(settleCompanyService.listSettleCompany(settleCompanyDTO));
    }

    @ApiOperation(tags = "settleCompany", value = "获取结算方详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "settleCompanyDTO", value = "结算方详情查询", required = true, dataType = "SettleCompanyDTO")
    })
    @RequestMapping(value = "/getSettleCompanyContent", method = RequestMethod.POST)
    public Object getSettleCompanyContent(@RequestBody SettleCompanyDTO settleCompanyDTO) {
        return new ResponseModel(settleCompanyService.getSettleCompanyContent(settleCompanyDTO.getId()));
    }

    /**
     * @author:yinghuaxu
     * @Description:新建结算方
     * @Date: create in 14:03 2017/10/23
     */
    @ApiOperation(tags = "settleCompany", value = "新建并保存结算方", notes = "返回参数:errorMsg:操作失败;feedbackMsg:操作成功"
            , response = SettleCompanyDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "settleCompanyDO", value = "新建并保存结算方", required = false
                    , dataType = "SettleCompanyDO")
    })
    @RequestMapping(value = "/createAndSaveSettleCompany", method = RequestMethod.POST)
    public Object insertCompany(@RequestBody SettleCompanyDO settleCompanyDO) {
        if (settleCompanyService.getRepeatSettleCompanyName(settleCompanyDO).size() > 0){
            return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.SETTLE_COMPANY_EXIST);
        }
        Integer result = 0;
        //token获取id
        settleCompanyDO.setModUser(JwtUserTool.getId());
        //转换运营方拼音首字母
        settleCompanyDO.setInitialCode(ChineseCharacterUtil.convertHanzi2Pinyin(settleCompanyDO.getSettleCompanyName()));
        if (settleCompanyDO.getId() != null) {
            SettleCompanyDO settleCompanyId = new SettleCompanyDO();
            settleCompanyId.setId(settleCompanyDO.getId());
            //当前数据库内结算方数据
            SettleCompanyDO settleCompanyResult = settleCompanyService.getSettleCompanyContent(settleCompanyId.getId());
            //获取当前时间
            settleCompanyDO.setModTime(new Date(System.currentTimeMillis()));
            try {
                result = settleCompanyService.updateSettleCompany(settleCompanyDO);
                //当前输入的特殊扣率列表
                List<SpecialDiscount> newSpecial = settleCompanyDO.getSpecialDiscountList();
                //获取数据库内特殊扣率列表
                List<SpecialDiscount> specialDiscountList = settleCompanyResult.getSpecialDiscountList();
                if (CollectionUtils.isEmpty(newSpecial)) {
                    for (SpecialDiscount specialDiscount : specialDiscountList) {
                        settleCompanyService.deleteSpecialDiscount(specialDiscount);
                    }
                }
                if (CollectionUtils.isNotEmpty(newSpecial)) {
                    //删除数据库中未在输入列表的数据
                    for (SpecialDiscount specialDiscount : specialDiscountList) {
                        if (!newSpecial.contains(specialDiscountList)) {
                            settleCompanyService.deleteSpecialDiscount(specialDiscount);
                        }
                    }
                    //更新剩余部分
                    settleCompanyService.updateSpecialDiscount(newSpecial);

                    SettleCompanyDO settleCompanyDO2 = new SettleCompanyDO();
                    //移除输入列表与数据库列表的交集数据
                    newSpecial.removeAll(specialDiscountList);
                    //insert需要当前结算方id
                    settleCompanyDO2.setId(settleCompanyDO.getId());
                    settleCompanyDO2.setSpecialDiscountList(newSpecial);
                    //插入输入列表中未在数据库的数据
                    settleCompanyService.insertSpecialDiscountList(settleCompanyDO2);
                    settleCompanyService.updateSpecialDiscount(settleCompanyDO.getSpecialDiscountList());
                }
            } catch (Exception e) {
                return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.SETTLE_COMPANY_EXIST);
            }
        } else {
            try {
                result = settleCompanyService.insertSettleCompany(settleCompanyDO);
                if (CollectionUtils.isNotEmpty(settleCompanyDO.getSpecialDiscountList())) {
                    //新增特殊扣率列表
                    settleCompanyService.insertSpecialDiscountList(settleCompanyDO);
                }
            } catch (Exception e) {
                return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.SETTLE_COMPANY_EXIST);
            }
        }

        SettleCompanyDO settleCompanyId = new SettleCompanyDO();
        settleCompanyId.setId(settleCompanyDO.getId());
        //当前数据库内结算方数据
        if (result.equals(1)) {
            return new ResponseModel(settleCompanyService.getSettleCompanyContent(settleCompanyId.getId()))
                    .attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);

    }

    /**
     * @author:yinghuaxu
     * @Description:历史修改记录
     * @Date: create in 15:39 2017/10/23
     */
    @ApiOperation(tags = "settleCompany", value = "查询历史修改记录", notes = "通过ID查询历史修改记录"
            , response = SettleCompanyHistory.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "selectHistoryByID", value = "历史修改记录", dataType = "SettleCompanyDTO")
    })
    @RequestMapping(value = "/findHistoryRecords", method = RequestMethod.POST)
    public Object findHistory(@RequestBody SettleCompanyDTO settleCompanyDTO) {
        return new ResponseModel(settleCompanyService.listHistoryRecords(settleCompanyDTO.getId()));
    }

    @ApiOperation(tags = "settleCompany", value = "结算方模块")
    @RequestMapping(value = "/getSettleCompany", method = RequestMethod.GET)
    public Object getSettleCompany() {
        return new ResponseModel(settleCompanyService.getSettleCompanyName());
    }

    /**
     * @author:yinghuaxu
     * @Description:启用/停用结算方
     * @Date: create in 9:03 2017/10/24
     */
    @ApiOperation(tags = "settleCompany", value = "启用/停用结算方", notes = "返回参数:errorMsg:操作失败;feedbackMsg:操作成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "changeSettleCompanyStateByID", value = "启用/停用结算方", dataType = "SettleCompanyDTO")
    })
    @RequestMapping(value = "/changeSettleCompanyState", method = RequestMethod.POST)
    public Object changeSettleCompanyState(@RequestBody SettleCompanyDTO settleCompanyDTO) {
        //token获取useId
        settleCompanyDTO.setModUser(JwtUserTool.getId());
        //设置当前时间
        settleCompanyDTO.setModTime(new Date(System.currentTimeMillis()));
        if (settleCompanyDTO.getUseState() != null) {
            settleCompanyService.updateSettleCompanyState(settleCompanyDTO);
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

}
