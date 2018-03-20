package com.tangu.tcmts.controller;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.po.Module;
import com.tangu.tcmts.po.SysRole;
import com.tangu.tcmts.service.ModuleService;
import com.tangu.tcmts.service.RoleService;
import com.tangu.tcmts.util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

@Api(value = "/role" , description = "角色管理")
@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private ModuleService moduleService;

    @ApiOperation(tags = "role",value = "获取角色列表",notes = "获取角色列表",response = SysRole.class)
    @RequestMapping(value = "listRoleLists",method = RequestMethod.GET)
    public Object listRoleLists(){
        return new ResponseModel(roleService.listRoles());
    }

    @ApiOperation(tags = "role",value = "新增并保存角色",notes = "返回参数:errorMsg:操作失败;feedbackMsg:操作成功"
            ,response = SysRole.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createAndSaveRole",value = "新增并保存角色",paramType = "SysRole")
    })
    @RequestMapping(value = "createAndSaveRole",method = RequestMethod.POST)
    public Object createAndSaveRole(@RequestBody SysRole sysRole){
        Integer result=0;
        if(sysRole.getId() != null){
            result=roleService.updateSysRole(sysRole);
        }else {
            result=roleService.insertSysRole(sysRole);
            if(CollectionUtils.isNotEmpty(sysRole.getFunctionList())
                    &&CollectionUtils.isNotEmpty(sysRole.getModuleList())){
                roleService.insertSysRoleAuthority(sysRole);
            }
        }
        if(result.equals(1)){
            return new ResponseModel(roleService.listRoles()).attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }


    @ApiOperation(tags = "role",value = "获取角色权限详情",notes = "获取角色权限详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "getRoleContent",value = "获取角色权限详情",paramType = "Module")
    })
    @RequestMapping(value = "getRoleContent",method = RequestMethod.POST)
    public Object getRoleContent(@RequestBody Module module){
        TreeSet<Module> menuModules = moduleService.selectMenuModules();
        Map<String, Object> permission = moduleService.selectRolePermission(module.getRoleId());
        return new ResponseModel().attr("permission", permission).attr("menuModules", menuModules);
    }

    @ApiOperation(tags = "role",value = "保存权限",notes = "返回参数:errorMsg:操作失败;feedbackMsg:操作成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "getRoleContent",value = "保存权限",paramType = "Module")
    })
    @RequestMapping(value = "changePermission",method = RequestMethod.POST)
    public Object changePermission(@RequestBody SysRole role){
        Integer result=moduleService.updatePermission(role);
        if(result.equals(1)){
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constants.INSERT_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
    }


    @ApiOperation(tags = "role",value = "删除角色",notes = "返回参数:errorMsg:操作失败;feedbackMsg:操作成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deleteRole",value = "删除角色",paramType = "SysRole")
    })
    @RequestMapping(value = "deleteRole",method = RequestMethod.POST)
    public Object deleteRole(@RequestBody SysRole sysRole){
        Integer result=roleService.deleteSysRole(sysRole.getId());
        if(result.equals(1)){
            return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constants.DELETE_SUCCESS);
        }
        return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MAY_EXIST_DATA);
    }

    @ApiOperation(tags = "role",value = "获取权限可访问菜单")
    @RequestMapping(value = "/getMenu",method = RequestMethod.POST)
    public Object getMenu() {
        String roleid = JwtUserTool.getRoleId();
        TreeSet<Module> moduleList = roleService.selectMenuModulesByRoleid(roleid);
        Map<String,Boolean> map = new HashMap<>(16);
        moduleList.stream().forEach(
                module -> module.getChildren().stream().forEach(
                        children-> children.getFunctions().stream().forEach(
                                function->map.putIfAbsent(function.getCode(),true)
                        )
                )
        );
        return new ResponseModel().attr(ResponseModel.KEY_DATA,moduleList).attr("authorityList",map);
    }


    @ApiOperation(tags = "role",value = "获取角色列表")
    @RequestMapping(value = "getRoleName",method = RequestMethod.GET)
    public Object getRoleName(){
        return new ResponseModel(roleService.getSysRoleName());
    }
}
