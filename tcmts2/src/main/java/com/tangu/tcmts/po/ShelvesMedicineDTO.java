package com.tangu.tcmts.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2018/1/10.
 */

@Data
public class ShelvesMedicineDTO {

    @ApiModelProperty(value = "处方ID")
    private Integer recipeId;

    @ApiModelProperty(value = "上架时间")
    @JsonFormat(pattern = "MM-dd HH:mm", timezone = "GMT+8")
    private Date shelvesTime;

    @ApiModelProperty(value = "货架号")
    private String shelvesCode;

    @ApiModelProperty(value = "货架类型")
    private Integer shelvesType;

    @ApiModelProperty(value = "货架类型名称")
    private String shelvesTypeName;

    @ApiModelProperty(value = "发票号")
    private String invoiceCode;

    @ApiModelProperty(value = "患者姓名")
    private String recipientName;

    @ApiModelProperty(value = "联系方式")
    private String consigneeTel;

    @ApiModelProperty(value = "取药时间")
    @JsonFormat(pattern = "MM-dd HH:mm", timezone = "GMT+8")
    private Date takeTime;

    @ApiModelProperty(value = "系统处方号")
    private String sysRecipeCode;
}
