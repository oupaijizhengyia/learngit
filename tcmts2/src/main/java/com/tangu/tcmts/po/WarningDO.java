/**
 * FileName: WarningDO
 * Author: yeyang
 * Date: 2018/1/30 9:49
 * 预警的显示信息  把它当成VO
 */
package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WarningDO {

    @ApiModelProperty("id")
    private BigDecimal id;
    @ApiModelProperty("处方id")
    private BigDecimal recipeId;
    @ApiModelProperty("处方编号")
    private BigDecimal recipeBh;
    @ApiModelProperty("患者名称")
    private String recipientName;
    @ApiModelProperty("操作类型,10配药,已配送93")
    private Integer operateType;
    @ApiModelProperty("开始时间")
    private Date startTime;
    @ApiModelProperty("结束时间")
    private Date endTime;
    @ApiModelProperty("处理状态")
    private Boolean warningStatus;


}
