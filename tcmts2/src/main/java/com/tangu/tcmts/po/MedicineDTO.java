package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:huangyifan
 * @Description:中药管理DO
 * @Date: create in 11：20 2017/10/31
 */
@Data
public class MedicineDTO {
	@ApiModelProperty(value = "id", required = true)
    private Integer id;
	@ApiModelProperty(value = "药品名称", required = true)
    private String medicineName;
	@ApiModelProperty(value = "药品编码", required = true)
    private String medicineCode;
	@ApiModelProperty(value = "0.在用,1.停止")
    private Integer useState;
	@ApiModelProperty(value = "修改人ID")
    private Integer modUser;

}
