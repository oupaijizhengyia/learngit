package com.tangu.tcmts.po;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:huangyifan
 * @Description:中药调价记录DO
 * @Date: create in 15：20 2017/11/3
 */
@Data
public class MedicinePriceHistoryDO {
	@ApiModelProperty(value = "id", required = true)
    private Integer id;
	@ApiModelProperty(value = "调价人ID", required = true)
    private Integer creatorId;
	@ApiModelProperty(value = "调价时间")
    private Date createTime;
	@ApiModelProperty(value = "药品id")
    private Integer medicineId;
	@ApiModelProperty(value = "价格类型")
    private Integer priceType;
	@ApiModelProperty(value = "现批发价")
    private BigDecimal nowTradePrice;
	@ApiModelProperty(value = "现零售价")
    private BigDecimal nowRetailPrice;
	@ApiModelProperty(value = "生效日期")
    private Date effectiveDate;
	@ApiModelProperty(value = "调价人名称")
    private String creatorName;
	@ApiModelProperty(value = "药品名称")
    private String medicineName;
	@ApiModelProperty(value = "价格模板名称")
	private String templetName;
}