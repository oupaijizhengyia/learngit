package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:huangyifan
 * @Description:仓库/药房 药品DTO
 * @Date: create in 10:00 2017/11/6
 */
@Data
public class WarehouseStandardDTO {
	@ApiModelProperty(value = "id", required = true)
	private Integer id;
	@ApiModelProperty(value = "药品规格", required = true)
	private String standardName;
	@ApiModelProperty(value = "仓库类型", required = true)
	private Integer warehouseType;
	@ApiModelProperty(value = "药品名称", required = true)
	private String medicineName;
	@ApiModelProperty(value = "仓库id/药房id", required = true)
	private Integer warehouseId;
	private String warehouseName;
	private String warehouseTypeName;
	
}
