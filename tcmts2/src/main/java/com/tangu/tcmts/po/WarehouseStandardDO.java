package com.tangu.tcmts.po;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:huangyifan
 * @Description:仓库/药房 药品DO
 * @Date: create in 10:00 2017/11/6
 */
@Data
public class WarehouseStandardDO {
	@ApiModelProperty(value = "id", required = true)
	private Integer id;
	@ApiModelProperty(value = "药品id", required = true)
	private Integer medicineId;
	@ApiModelProperty(value = "仓库id/药房id", required = true)
	private Integer warehouseId;
	@ApiModelProperty(value = "药品规格id", required = true)
	private Integer standardId;
	@ApiModelProperty(value = "库存量")
	private BigDecimal stockValue;
	@ApiModelProperty(value = "库位")
	private String storageLocation;
	@ApiModelProperty(value = "上限")
	private BigDecimal upLimit;
	@ApiModelProperty(value = "下限")
	private BigDecimal lowerLimit;
	@ApiModelProperty(value = "批号")
	private String batchNumber;
	@ApiModelProperty(value = "生产商/产地")
	private Integer producerId;
	@ApiModelProperty(value = "药品名称")
	private String medicineName;
	@ApiModelProperty(value = "规格编码")
	private String standardCode;
	@ApiModelProperty(value = "生产企业")
	private String manufacturingEnterprise;
	@ApiModelProperty(value = "产地")
	private String producingArea;
	@ApiModelProperty(value = "药品编码")
	private String medicineCode;
	@ApiModelProperty(value = "仓库类型")
	private Integer warehouseType;
	
	private String warehouseName;

}
