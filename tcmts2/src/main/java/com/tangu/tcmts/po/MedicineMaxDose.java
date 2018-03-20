package com.tangu.tcmts.po;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MedicineMaxDose {
    private Integer id;

    @ApiModelProperty(value = "药品id")
    private Integer medicineId;
    
    @ApiModelProperty(value = "药品名")
    private String medicineName;

    @ApiModelProperty(value = "单贴量上限")
    private BigDecimal maxWeightEvery;
    
    @ApiModelProperty(value = "规格")
    private String standard;
    
    private Date createTime;

    private Integer modUser;

    private Date modTime;
}