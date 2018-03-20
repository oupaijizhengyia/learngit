package com.tangu.tcmts.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.jeecgframework.poi.excel.annotation.Excel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PriceTempletDetailDTO {
  
  private Integer id;
  
  @ApiModelProperty("价格模板细节id")
//  @Excel(orderNum = "0", name = "templetDetailId")
  private Integer templetDetailId;
  
  @ApiModelProperty("价格模板id")
  private Integer templetId; //价格模板id

  private String name_templetId;

  @ApiModelProperty("模板名字")
  @Excel(orderNum = "1", name = "模板名字")
  private String templetName; //价格模板名字
  
  @ApiModelProperty("药品id")
  private Integer medicineId; //药品id
  
  @ApiModelProperty("药品名")
  @Excel(orderNum = "0", name = "药品名")
  private String medicineName;
  
  private String name_medicineName;
  
  @ApiModelProperty("药品首字母")
  private String initialCode;
  
  @ApiModelProperty("药品编码")
  private String medicineCode;

  @ApiModelProperty("现批发价")
  @Excel(orderNum = "3", name = "现批发价")
  private BigDecimal nowTradePrice; //批发价
  
  @ApiModelProperty("现零售价")
  @Excel(orderNum = "4", name = "现零售价")
  private BigDecimal nowRetailPrice; //零售价
  
  @ApiModelProperty("批发价")
  @Excel(orderNum = "5", name = "批发价")
  private BigDecimal tradePrice; //批发价
  
  @ApiModelProperty("零售价")
  @Excel(orderNum = "6", name = "零售价")
  private BigDecimal retailPrice; //零售价
  
  @ApiModelProperty("生效日期")
  @Excel(orderNum = "7", name = "生效日期")
  private Date priceStart; //生效日期
  
  @ApiModelProperty("终止日期")
  private Date priceEnd;

  @ApiModelProperty("规格")
  @Excel(orderNum = "2", name = "规格")
  private String standard;
  
  private Integer medicineLast;// 最后调价 1.是 0.否
  @ApiModelProperty("修改时间")
  private Date modifyTime;
  
  private Date oldPriceStart; // 旧生效时间
  @ApiModelProperty("价格模板id集合")
  private List templetIdList;
  @ApiModelProperty("药品id集合")
  private List medicineIdList;
  private Integer modUser;  // 修改人id
  @ApiModelProperty("修改人名字")
  private String modifyName;
}
