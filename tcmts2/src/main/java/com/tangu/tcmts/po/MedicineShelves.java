package com.tangu.tcmts.po;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MedicineShelves {
    private Integer id;

    @ApiModelProperty(value = "货架号")
    private String shelvesCode;

    @ApiModelProperty(value = "货架类型id:1.门诊,2.住院,3.快递")
    private Integer shelvesType;
    
    @ApiModelProperty(value = "货架类型")
    private String shelvesTypeName;

    @ApiModelProperty(value = "备注")
    private String remark;

    private Date createTime;

    private Integer modUser;

    private Date modTime;

}