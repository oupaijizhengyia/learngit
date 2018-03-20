package com.tangu.tcmts.service.imp;

import com.tangu.tcmts.dao.ModuleMapper;
import com.tangu.tcmts.po.Module;
import com.tangu.tcmts.po.SysRole;
import com.tangu.tcmts.service.ModuleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

@Service
@Transactional(readOnly = true,rollbackFor = SQLException.class)
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleMapper moduleMapper;

    @Override
    public TreeSet<Module> selectMenuModules(){
        Module module = new Module();
        List<Module> list = moduleMapper.getModuleList(module);
        return listToTree(list);
    }

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public Integer updatePermission(SysRole sysRole) {
        Integer insertSysRoleModule=0;
        Integer insertSysRoleFuntion=0;
        Integer deleteSysRoleFunction= moduleMapper.deleteSysRoleFunction(sysRole.getId());
        Integer deleteSysRoleModule=moduleMapper.deleteSysRoleModule(sysRole.getId());
        if (CollectionUtils.isNotEmpty(sysRole.getModuleList())){
            insertSysRoleModule=moduleMapper.insertSysRoleModule(sysRole);
        }
        if (CollectionUtils.isNotEmpty(sysRole.getFunctionList())){
            insertSysRoleFuntion=moduleMapper.insertSysRoleFuntion(sysRole);
        }
        Boolean requirement=(deleteSysRoleFunction.toString() != null && deleteSysRoleModule.toString() != null
                                && insertSysRoleFuntion.toString() != null && insertSysRoleModule.toString() != null);
        if(requirement){
            return 1;
        }
        return 0;
    }

    @Override
    public Map<String,Object> selectRolePermission(Integer roleid) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Object> functions = moduleMapper.getFunctionById(roleid);
        result.put("functions", functions);
        List<Object> modules = moduleMapper.getModuleById(roleid);
        result.put("modules", modules);
        return result;
    }


    public TreeSet<Module> listToTree(List<Module> list) {
        if (list == null){
            return null;
        }
        TreeSet<Module> result = new TreeSet<Module>();
        Map<Integer,Module> map = new HashMap<Integer,Module>();
        for (Module module : list){
            map.put(module.getId(), module);
        }

        Module parent;
        TreeSet<Module> children;
        for (Module module : list){
            if ((parent = map.get(module.getParentId())) == null){
                result.add(module);
            } else {
                children = parent.getChildren();
                if (children == null){
                    parent.setChildren(children = new TreeSet<Module>());
                }
                children.add(module);
            }
        }

        return result;
    }


}
