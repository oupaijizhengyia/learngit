package com.tangu.tcmts.po;

import lombok.Data;

import java.util.List;

@Data
public class SysRole {
    private Integer id;

    private Integer parentRole;

    private String rolename;

    private String describe;

    private List<Function> functionList;

    private List<Module> moduleList;

    private List<Long> functions;
    private List<Long> modules;

    public List<Function> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<Function> functionList) {
        this.functionList = functionList;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }
}