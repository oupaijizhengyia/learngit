package com.tangu.tcmts.po;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TakeMedicine {
  private Integer id;
  
  @ApiModelProperty("取药条码")
  private String recipeSerial;
  
  @ApiModelProperty("标签条码")
  private String sysRecipeCode;
  
  @ApiModelProperty("患者姓名")
  private String recipientName;

  @ApiModelProperty("货位号")
  private String shelvesCode;

  @ApiModelProperty("处方状态id")
  private Integer shippingState;
  
  @ApiModelProperty("处方状态名")
  private String shippingStateName;
  
  @ApiModelProperty("状态时间")
  private Date stateTime;
  
}
