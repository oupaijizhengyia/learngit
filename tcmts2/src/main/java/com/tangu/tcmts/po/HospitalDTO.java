package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class HospitalDTO {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "医院名称")
    private String hospitalCompany;

    @ApiModelProperty(value = "医院编码varchar(11)")
    private String hospitalCode;

    @ApiModelProperty(value = "结算方ID int(10)")
    private Integer settleCompanyId;

    @ApiModelProperty(value = "启用状态 0.启用 1.停用")
    private Integer useState;

    private Integer modUser;

    private Date modTime;
}
