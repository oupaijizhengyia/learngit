package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PackingType {
	@ApiModelProperty(value = "包装名称")
	private String packingName;
	@ApiModelProperty(value = "包装类型")
	private Integer packingTypeId;
}
