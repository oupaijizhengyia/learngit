package com.tangu.tcmts.controller;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.po.UserPref;
import com.tangu.tcmts.service.UserPrefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fenglei
 * @date 11/15/17
 */

@Api(value = "/preference", description = "偏好设置")
@RestController
@RequestMapping("/preference")
public class PreferenceController {

    @Autowired
    private UserPrefService userPrefService;


    @ApiOperation(tags = "用户偏好设置", value = "获取用户偏好设置",
            notes = "查询用户的偏好设置例如grid column，可页面首次加载时访问并保存到前端也可以在使用时调用, 后台返回key:value形式的map")
    @RequestMapping(value = "/getUserPrefs", method = RequestMethod.GET)
    public Object listUserPre() {
        List<UserPref> list = userPrefService.listUserPref(JwtUserTool.getId());
        Map<String,Object> map = list.stream()
                .collect(Collectors.toMap(UserPref::getType,UserPref::getValue));

        return new ResponseModel(map);
    }


    @ApiOperation(tags = "用户偏好设置", value = "更新用户偏好设置",
            notes = "查询用户的偏好设置例如grid column，可页面首次加载时访问并保存到前端也可以在使用时调用",response = UserPref.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UserPref" , required = true, dataType = "UserPref")
    })
    @RequestMapping(value = "/setUserPref", method = RequestMethod.POST)
    public Object updateUserPre(@RequestBody UserPref userPref) {
        userPref.setUserId(JwtUserTool.getId());

        int result  = userPrefService.updateUsrePref(userPref);
        if (result > 0) {
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }

}
