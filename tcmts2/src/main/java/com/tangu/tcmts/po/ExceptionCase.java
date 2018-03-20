package com.tangu.tcmts.po;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExceptionCase {
  private Integer id;

  private Integer recipeId;
  
  private String recipeCode;

  private Date exceptionTime;
  
  private Date startDate;
  
  private Date endDate;

  private String creatorName;
  
  private Integer creatorId;

  @ApiModelProperty(value = "1.配药,2.复核,3.浸泡,4.煎煮,5.收膏,"
      + "6.包装,7.接方,8.开单,9.审方,10.审方,99.其他")
  private Integer exceptionType;
  
  private String exceptionName;

  private String exceptionComment;

  @ApiModelProperty(value = "0.未处理,1.已处理")
  private Integer dealStatus;
  
  private String dealStatusName;
  
  private String dealResult;

  private Date dealTime;

  private String dealUserName;

  private Integer dealUser;

  private String dutyName;
  
  private Integer dutyId;

}
