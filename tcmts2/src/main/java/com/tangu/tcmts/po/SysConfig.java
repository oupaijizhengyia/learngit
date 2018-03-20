package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fenglei
 * @date 10/31/17
 */

@Data
public class SysConfig {

    @ApiModelProperty(value = "属性名")
    private String name;
    @ApiModelProperty(value = "属性值")
    private String value;

    //目前好像不需要字段
//    @ApiModelProperty(value = "属性类型，1：需要传给前台，0：后台专用")
//    private Integer type;
}
