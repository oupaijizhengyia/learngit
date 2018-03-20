package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:huangyifan
 * @Description:中药管理 -药品关联 DO
 * @Date: create in 18：20 2017/11/6
 */
@Data
public class MedicineRelationDTO {
    @ApiModelProperty(value = "id", required = true)
    private Integer id;
    @ApiModelProperty(value = "医院药品名称")
    private String hospitalMedicineName;
    @ApiModelProperty(value = "医院药品编码")
    private String hospitalMedicineCode;
    @ApiModelProperty(value = "药品名称或编码")
    private String medicineNameOrCode;
    @ApiModelProperty(value = "通用id=0/医院id")
    private Integer hospitalId;
}
