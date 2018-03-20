package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HospitalListVO  {
    @ApiModelProperty(value="id",required = true)
    private Integer id;

    @ApiModelProperty(value = "医院名称",required = true)
    private String hospitalCompany;

    @ApiModelProperty(value = "医院简称",required = true)
    private String hospitalNickname;

    @ApiModelProperty(value = "医院编号",required = true)
    private String hospitalCode;

    @ApiModelProperty(value = "结算方",required = true)
    private String settleCompanyName;

    @ApiModelProperty(value = "价格类型",required = true)
    private String priceTypeName;

    @ApiModelProperty(value = "启用状态 0.启用 1.停用",required = true)
    private Integer useState;
}
