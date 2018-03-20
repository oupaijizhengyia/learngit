package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BtRelationZd {
	@ApiModelProperty(value = "id")
	private Integer id;
	@ApiModelProperty(value = "医院id")
	private Integer hospitalId;
	@ApiModelProperty(value = "表头")
	private String header;
	@ApiModelProperty(value = "字段名")
	private String fieldName;
	private Integer tableType;
}
