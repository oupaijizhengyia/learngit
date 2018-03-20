package com.tangu.tcmts.po;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:huangyifan
 * @Description:中药规格管理DO
 * @Date: create in 15：57 2017/10/31
 */
@Data
public class MedicineStandardDO {
	@ApiModelProperty(value = "id", required = true)
    private Integer id;
	@ApiModelProperty(value = "药品id", required = true)	
    private Integer medicineId;
	@ApiModelProperty(value = "规格名称", required = true)	
    private String standardName;
	@ApiModelProperty(value = "预警值")	
    private BigDecimal warningValue;
	@ApiModelProperty(value = "规格编码")	
    private String standardCode;
	@ApiModelProperty(value = "药品批号")	
    private String batchNumber;
	@ApiModelProperty(value = "生产企业")	
    private String manufacturingEnterprise;
	@ApiModelProperty(value = "产地")	
    private String producingArea;
	@ApiModelProperty(value = "生产商/产地")	
    private Integer producerId;
	@ApiModelProperty(value = "规格名称-前端显示使用")	
	private String name_standardName;
	@ApiModelProperty(value = "药品名称")	
	private String medicineName;
	@ApiModelProperty(value = "药品编码")	
	private String medicineCode;
	@ApiModelProperty(value = "规格id")	
	private Integer standardId;
	@ApiModelProperty(value = "仓库id")	
	private Integer warehouseId;
	@ApiModelProperty(value = "仓库/药房规格id")	
	private Integer warehouseStandardId;
	@ApiModelProperty(value = "名称首字母")	
	private String initialCode;
	@ApiModelProperty(value = "助记码")	
	private String mnemonicCode;
	@ApiModelProperty(value = "药房id")	
	private Integer drugstoreId;
	@ApiModelProperty(value = "操作人id")	
	private Integer employeeId;
	@ApiModelProperty(value = "数量")	
	private BigDecimal quantity;
}