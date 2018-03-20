package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class TransferMedicine {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("transferMedicineId")
    private Long transferMedicineId;
    @ApiModelProperty("返前端用")
    private String name_medicineName;
    @ApiModelProperty("药品名称")
    private String medicineName;
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("单贴量")
    private BigDecimal weightEvery;
    @ApiModelProperty("服法")
    private String eatType;
    @ApiModelProperty("医院处方处方ID")
    private Long billId;
    @ApiModelProperty("药品编号")
    private String medicineCode;
    @ApiModelProperty("单据号")
    private String billCode;
    @ApiModelProperty("单据号")
    private String batchNumber;
    @ApiModelProperty("含税单价")
    private BigDecimal price;
    @ApiModelProperty("货号")
    private String mnemonicCode;
    @ApiModelProperty("对应的系统药品名")
    private String sysMedicineName;
    @ApiModelProperty("用法")
    private String specialBoilType;
    @ApiModelProperty("订单列表打印药品时使用")
    private String printMedicine;
    @ApiModelProperty("总量")
    private BigDecimal weight;
    @ApiModelProperty("含税金额")
    private BigDecimal money;
    @ApiModelProperty("医院编号")
    private String hospitalCode;
    @ApiModelProperty("药品来源：自备、自提")
    private String medicineSource;
    @ApiModelProperty("产地")
    private String medicineAddress;
    @ApiModelProperty("规格量")
    private Float standardWeight;
    @ApiModelProperty("药品规格")
    private String standard;
    @ApiModelProperty("生产厂商")
    private String factory;
    @ApiModelProperty("0非重量 ， 1重量")
    private Integer unitType;
    @ApiModelProperty("单帖量")
    private Integer quantity;
    private BigDecimal totalWeight;
    public Long getTransferMedicineId() {
        return id;
    }

	public String getSpecialBoilType() {
		return specialBoilType;
	}

	public void setSpecialBoilType(String specialBoilType) {
		this.specialBoilType = specialBoilType;
	}

}