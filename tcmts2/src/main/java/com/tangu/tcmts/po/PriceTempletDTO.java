package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PriceTempletDTO {
  
  private Integer id;
  
  @ApiModelProperty("价格模板id")
  private Integer templetId; //价格模板id

  @ApiModelProperty("模板名字")
  private String templetName; //价格模板名字  
  
  private Integer creatorId; //药品id
  
//  @ApiModelProperty("药品id")
//  private Integer medicineId; //药品id
}
