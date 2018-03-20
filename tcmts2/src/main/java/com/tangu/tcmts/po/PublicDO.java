package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:huangyifan
 * @Description:公共DO
 * @Date: create in 10：11 2017/10/25
 */
@Data
public class PublicDO {
    public PublicDO(){}

	public PublicDO(Integer value, String label, String unit){
		this.value = value;
		this.label = label;
		this.unit = unit;
	}

	public PublicDO(Integer value, String label, String medicineName, Integer id, String standard, Integer unitType
			, String specialBoilType){
		this.value = value;
		this.label = label;
		this.medicineName = medicineName;
		this.medicineId = id;
		this.unit = standard;
		this.unitType = unitType;
		this.specialBoilType = specialBoilType;
	}
	@ApiModelProperty(value = "特殊煎药方式")
    private String specialBoilType;
	@ApiModelProperty(value = "显示名称")
	private String label;
	@ApiModelProperty(value = "序号")
	private Integer value;
	@ApiModelProperty(value = "单位")
	private String unit;
	@ApiModelProperty(value = "药品编码")
	private String medicineCode;
	@ApiModelProperty(value = "药品名称")
	private String medicineName;
	@ApiModelProperty(value = "药品id")
	private Integer medicineId;
	@ApiModelProperty(value = "0非重量  1=重量")
	private Integer unitType;
	private String hospitalCode;
	@ApiModelProperty("医院拼音")
	private String initialCode;
}
