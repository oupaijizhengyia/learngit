package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Function implements Comparable<Function>{
    @ApiModelProperty(value = "功能id",required = true)
    private Integer id;
    @ApiModelProperty(value = "功能名称")
    private String name;
    @ApiModelProperty(value = "功能图标")
    private String icon;
    @ApiModelProperty(value = "功能字符KEY")
    private String code;
    @ApiModelProperty(value = "对应模块ID")
    private Integer moduleId;
    @ApiModelProperty(value = "功能排序")
    private int seq;

    @Override
    public int compareTo(Function o) {
        return seq - o.seq;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Function function = (Function) o;

        if (seq != function.seq) {
            return false;
        }
        if (id != null ? !id.equals(function.id) : function.id != null) {
            return false;
        }
        if (name != null ? !name.equals(function.name) : function.name != null) {
            return false;
        }
        if (icon != null ? !icon.equals(function.icon) : function.icon != null) {
            return false;
        }
        if (code != null ? !code.equals(function.code) : function.code != null) {
            return false;
        }
        return moduleId != null ? moduleId.equals(function.moduleId) : function.moduleId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (moduleId != null ? moduleId.hashCode() : 0);
        result = 31 * result + seq;
        return result;
    }
}
