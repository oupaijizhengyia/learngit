package com.tangu.tcmts.service.imp;

import com.tangu.tcmts.dao.ModuleMapper;
import com.tangu.tcmts.dao.SysRoleMapper;
import com.tangu.tcmts.po.Module;
import com.tangu.tcmts.po.PublicDO;
import com.tangu.tcmts.po.SysRole;
import com.tangu.tcmts.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
/**
 * @author:yinghuaxu
 * @Description:权限模块管理
 * @Date: create in 16:09 2017/10/30
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Override
    public TreeSet<Module> selectMenuModulesByRoleid(String roleid) {
        Map<String, Object> params = new HashMap<String, Object>(16);
        params.put("roleid", roleid);

        List<Module> list = moduleMapper.getFunctionByRole(params);

        return listToTree(list);
    }

    @Override
    public List<SysRole> listRoles() {
        return sysRoleMapper.listRoles();
    }

    @Override
    public int updateSysRole(SysRole sysRole) {
        return sysRoleMapper.updateSysRole(sysRole);
    }

    @Override
    public int insertSysRole(SysRole sysRole) {
        return sysRoleMapper.insertSysRole(sysRole);
    }

    @Override
    public int insertSysRoleAuthority(SysRole sysRole) {
        Integer result=sysRoleMapper.insertSysRoleFunction(sysRole);
        Integer result2=sysRoleMapper.insertSysRoleModule(sysRole);
        if(result.equals(1)&&result2.equals(1)){
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public int deleteSysRole(Integer id) {
        if(moduleMapper.usingRole(id).size() == 0){
            return sysRoleMapper.deleteSysRole(id);
        }
        return 0;
    }

    @Override
    public List<PublicDO> getSysRoleName() {
        return sysRoleMapper.getSysRoleName();
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
