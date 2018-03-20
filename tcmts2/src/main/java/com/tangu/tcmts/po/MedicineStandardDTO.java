package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:huangyifan
 * @Description:中药规格管理DTO
 * @Date: create in 17：35 2017/11/3
 */
@Data
public class MedicineStandardDTO {
	@ApiModelProperty(value = "id", required = true)
    private Integer id;
	@ApiModelProperty(value = "规格名称", required = true)	
    private String standardName;
	@ApiModelProperty(value = "1.仓库,3.饮片药房,4.小包装药房", required = true)	
	private Integer warehouseType;
	@ApiModelProperty(value = "药品名称")	
    private String medicineName;
	@ApiModelProperty(value = "仓库/药房id")	
	private Integer warehouseId;
}
