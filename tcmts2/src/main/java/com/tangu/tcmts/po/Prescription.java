package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Prescription {
    @ApiModelProperty(value = "协定方id",required = true)
    private Integer id;
    @ApiModelProperty(value = "医院id",required = true)
    private Integer hospitalId;
    @ApiModelProperty(value = "协定方名称",required = true)
    private String prescriptionName;

    private String unifiedCode;

    private String systemName;

    private Date createTime;

    private Integer modUser;

    private Date modTime;
    @ApiModelProperty(value = "医院名称",required = true)
    private String hospitalCompany;
    
    private String medicineName;
    private Integer medicineId;
    private BigDecimal weight;
}