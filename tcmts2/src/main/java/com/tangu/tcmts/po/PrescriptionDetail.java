package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class PrescriptionDetail {

    private Integer id;
    @ApiModelProperty(value = "药方ID")
    private Integer prescriptionId;
    @ApiModelProperty(value = "药品ID")
    private Integer medicineId;
    @ApiModelProperty(value = "单贴量")
    private BigDecimal weight;
    @ApiModelProperty(value = "药方名称")
    private String prescriptionName;
    @ApiModelProperty(value = "药品名称")
    private String medicineName;

    private String name_medicineName;
    @ApiModelProperty(value = "单位规格")
    private String unit;

    private Integer unitType;
    @ApiModelProperty(value = "特殊煎药方式")
    private String specialBoilType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}