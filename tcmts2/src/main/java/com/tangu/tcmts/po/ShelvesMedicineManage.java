package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/1/10.
 */

@Data
public class ShelvesMedicineManage {

    @ApiModelProperty(value = "货架号")
    private String shelvesCode;

    @ApiModelProperty(value = "货架类型")
    private Integer shelvesType;

    @ApiModelProperty(value = "发票号")
    private String invoiceCode;

    @ApiModelProperty(value = "患者姓名")
    private String recipientName;

    @ApiModelProperty(value = "系统处方号")
    private String sysRecipeCode;

}
