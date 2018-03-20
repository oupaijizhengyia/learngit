package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MedicineTaboo {
    @ApiModelProperty(value = "配伍禁忌ID")
    private Integer id;
    @ApiModelProperty(value = "药品ID")
    private Integer parentMedicineId;
    @ApiModelProperty(value = "禁忌药品ID")
    private Integer subMedicineId;
    @ApiModelProperty(value = "配伍禁忌类型ID")
    private Integer tabooType;

    private Date createTime;

    private Integer modUser;

    private Date modTime;
    @ApiModelProperty(value = "药品名")
    private String parentMedicineName;
    @ApiModelProperty(value = "禁忌药品名")
    private String subMedicineName;
    @ApiModelProperty(value = "配伍禁忌类型名")
    private String tabooTypeName;

}