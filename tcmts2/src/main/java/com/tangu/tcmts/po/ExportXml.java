package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExportXml {
    @ApiModelProperty("模板")
    private String xmlCode;
    @ApiModelProperty("模板描述")
    private String xmlName;
    @ApiModelProperty("模板内容")
    private String xmlValue;
}
