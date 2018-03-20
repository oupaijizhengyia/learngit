package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecipeMedicine {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("处方id")
    private Integer recipeId;
    @ApiModelProperty("中药id")
    private Integer medicineId;
    @ApiModelProperty("药品名")
    private String name_medicineName;
    private String medicineName;
    @ApiModelProperty("药品拼音")
    private String initialCode;
    @ApiModelProperty("单贴量")
    private BigDecimal weightEvery;
    @ApiModelProperty("批发价")
    private BigDecimal tradPrice;
    @ApiModelProperty("实价=批发价*扣率，但可以在对账是修改")
    private BigDecimal actualPrcie;
    @ApiModelProperty("零售价")
    private BigDecimal retailPrice;
    @ApiModelProperty("费用=药量*批发价")
    private BigDecimal tradeFreight;
    @ApiModelProperty("零售费用=药量*零售价")
    private BigDecimal retailFreight;
    @ApiModelProperty("含税金额")
    private BigDecimal money;
    @ApiModelProperty("规格量")
    private BigDecimal standardWeight;
    @ApiModelProperty("特殊煎药方式")
    private String specialBoilType;
    @ApiModelProperty("订单明细id")
    private Long transferMedicineId;
    @ApiModelProperty("单位类型,1.重量")
    private Integer unitType;
    @ApiModelProperty("药品编码")
    private String medicineCode;
    
    @ApiModelProperty(value = "税率")
    private BigDecimal taxRate;
    @ApiModelProperty(value = "特殊扣率")
    private BigDecimal discount;
    private Long billId;
    private String hospitalCode;
    @ApiModelProperty(value = "规格")
    private String standard;

    
}