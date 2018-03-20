package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fenglei
 * @date 11/1/17
 */
@Data
public class SysLookup {
    @ApiModelProperty("字典代码")
    private String code;

    @ApiModelProperty(value = "属性真实值")
    private String value;

    @ApiModelProperty(value = "属性显示值")
    private String label;

    @ApiModelProperty(value = "保留字段1")
    private String ext1;

    @ApiModelProperty(value = "保留字段2")
    private String ext2;

    @ApiModelProperty(value = "保留字段3")
    private String ext3;

}
