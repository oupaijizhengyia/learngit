package com.tangu.tcmts.po;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:huangyifan
 * @Description:中药管理 -药品关联 DO
 * @Date: create in 18：20 2017/11/6
 */
@Data
public class MedicineRelationDO {
    @ApiModelProperty(value = "id", required = true)
    private Integer id;
    @ApiModelProperty(value = "医院药品名称")
    private String hospitalMedicineName;
    @ApiModelProperty(value = "医院药品编码")
    private String hospitalMedicineCode;
    @ApiModelProperty(value = "医院药品名称拼音")
    private String initialCode;
    @ApiModelProperty(value = "煎药系统药品id")
    private Integer nativeMedicineId;
    @ApiModelProperty(value = "通用id=0/医院id")
    private Integer hospitalId;
    @ApiModelProperty(value = "本地药品名称和药品编码")
    private String medicineName;
    @ApiModelProperty(value = "本地药品ids")
    private String[] ids;
    @ApiModelProperty("特殊煎药方式")
    private String specialBoilType;
    @ApiModelProperty("单位类型,1.重量")
    private Integer unitType;
    @ApiModelProperty(value = "税率")
   	private BigDecimal taxRate;
    @ApiModelProperty(value = "医院名称")
    private String hospitalName;
    @ApiModelProperty("药品类别：0通用药品，1医院专属")
    private Integer medicineType;
    
    private Integer medicineId;
    private String standard;
    private String medicinePinyin;
    private String mnemonicCode;
    private String commonCode;
}