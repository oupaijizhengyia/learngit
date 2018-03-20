package com.tangu.tcmts.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @author:huangyifan
 * @Description:中药管理DO
 * @Date: create in 11：20 2017/10/31
 */
@Data
public class MedicineDO {
	@ApiModelProperty(value = "id", required = true)
    private Integer id;
	@ApiModelProperty(value = "药品名称", required = true)
    private String medicineName;
	@ApiModelProperty(value = "药品编码", required = true)
    private String medicineCode;
	@ApiModelProperty(value = "名称首字母")
    private String initialCode;
	@ApiModelProperty(value = "助记码", required = true)
    private String mnemonicCode;
	@ApiModelProperty(value = "规格")
    private String standard;
	@ApiModelProperty(value = "特殊煎药方式")
    private String specialBoilType;
	@ApiModelProperty(value = "税率")
    private BigDecimal taxRate;
	@ApiModelProperty(value = "药品类型:1.饮片,2.精制...")
    private Integer medicineType;
	@ApiModelProperty(value = "膏方类型:1.胶类,2.精类...")
    private Integer creamFormulaType;
	@ApiModelProperty(value = "单位类型:0表示非重量，1表示重量")
    private Integer unitType;
	@ApiModelProperty(value = "平台编号")
    private String commonCode;
	@ApiModelProperty(value = "操作时间")
    private Integer operateTime;
	@ApiModelProperty(value = "0.在用,1.停止")
    private Integer useState;
	  private String  useStateName;
	@ApiModelProperty(value = "创建时间")
    private Date createTime;
	@ApiModelProperty(value = "修改人ID")
    private Integer modUser;
	@ApiModelProperty(value = "规格管理list")
	private List<MedicineStandardDO> standardList;
	@ApiModelProperty(value = "0.院内  1.非院内")
	private Integer medicineHospitalType;
}