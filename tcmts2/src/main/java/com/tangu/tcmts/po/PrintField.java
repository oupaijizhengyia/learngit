package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PrintField {
    @ApiModelProperty(value = "主键ID")
    private Integer id;
    @ApiModelProperty(value = "字段名")
    private String fieldName;
    @ApiModelProperty(value = "字段类型")
    private String fieldType;
    @ApiModelProperty(value = "字段")
    private String fieldSource;
    @ApiModelProperty(value = "字段组合")
    private String fieldPara;
    @ApiModelProperty(value = "面板显示名称")
    private String displayText;
    @ApiModelProperty(value = "默认显示宽度")
    private Integer displayWidth;
    @ApiModelProperty(value = "默认显示高度")
    private Integer displayHeight;
}