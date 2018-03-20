package com.tangu.tcmts.po;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:huangyifan
 * @Description:中药调价记录DTO
 * @Date: create in 15：20 2017/11/3
 */
@Data
public class MedicinePriceHistoryDTO {
  private Integer id;
	@ApiModelProperty(value = "药品名称")
  private String medicineName;
	@ApiModelProperty(value = "价格类型")
	private Integer priceType;
	@ApiModelProperty(value = "价格模板类型")
	private Integer templetId;
	@ApiModelProperty(value = "开始时间")
	private Date startDate;
	@ApiModelProperty(value = "结束时间")
	private Date endDate;
}
