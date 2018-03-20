package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.TreeSet;

@Data
public class Module implements Comparable<Module>{
    @ApiModelProperty(value = "模块id",required = true)
    private Integer id;
    @ApiModelProperty(value = "模块名")
    private String name;

    private String url;
    @ApiModelProperty(value = "父集id")
    private Integer parentId;

    private String icon;

    private Byte orderItem;

    private Integer useState;

    private Integer isMenu;

    private Integer roleId;

    private String role;

    private TreeSet<Function> functions;

    private TreeSet<Module> children;

    @ApiModelProperty(value = "账套级别的系统配置")
    private Object config;

    @Override
    public int compareTo(Module o) {
        return orderItem - o.orderItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Module module = (Module) o;

        if (id != null ? !id.equals(module.id) : module.id != null) {
            return false;
        }
        if (name != null ? !name.equals(module.name) : module.name != null) {
            return false;
        }
        if (url != null ? !url.equals(module.url) : module.url != null) {
            return false;
        }
        if (parentId != null ? !parentId.equals(module.parentId) : module.parentId != null) {
            return false;
        }
        if (icon != null ? !icon.equals(module.icon) : module.icon != null) {
            return false;
        }
        if (orderItem != null ? !orderItem.equals(module.orderItem) : module.orderItem != null) {
            return false;
        }
        if (useState != null ? !useState.equals(module.useState) : module.useState != null) {
            return false;
        }
        if (isMenu != null ? !isMenu.equals(module.isMenu) : module.isMenu != null) {
            return false;
        }
        if (roleId != null ? !roleId.equals(module.roleId) : module.roleId != null) {
            return false;
        }
        return role != null ? role.equals(module.role) : module.role == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (orderItem != null ? orderItem.hashCode() : 0);
        result = 31 * result + (useState != null ? useState.hashCode() : 0);
        result = 31 * result + (isMenu != null ? isMenu.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
