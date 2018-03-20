package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RecipePatrol {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("处方id")
    private Integer recipeId;
    @ApiModelProperty("处方序号")
    private String recipeSerial;
    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;
    @ApiModelProperty("操作人id")
    private Integer operateId;
    @ApiModelProperty("1.配方抽检,2.煎药抽检")
    private Integer operateType;
    @ApiModelProperty("备注")
    private String operateComment;
    @ApiModelProperty("实际量")
    private BigDecimal actualAmount;
    @ApiModelProperty("实际药味")
    private Integer actualKind;
    @ApiModelProperty("巡查结果")
    private String patrolResult;
    @ApiModelProperty("巡查状态 1.合格 0.不合格")
    private Integer patrolStatus;
    @ApiModelProperty("巡查状态名称")
    private String patrolStatusName;
    @ApiModelProperty("计划量")
    private BigDecimal planAmount;
    @ApiModelProperty("记录时间")
    private Date operateTime;
    @ApiModelProperty("医院名称")
    private String hospitalName;
    @ApiModelProperty("医院id")
    private Integer hospitalId;
    @ApiModelProperty("系统处方号")
    private String sysRecipeCode;
    @ApiModelProperty("病人名称")
    private String recipientName;
    @ApiModelProperty("帖数")
    private Integer quantity;
    @ApiModelProperty("医生名称")
    private String doctorName;
    @ApiModelProperty("药味")
    private Integer medicineQuantity;
    @ApiModelProperty("误差(g)")
    private BigDecimal mistakeGram;
    @ApiModelProperty("误差(%)")
    private BigDecimal mistakePercent;
    @ApiModelProperty("配方员")
    private String dispenseName;
    @ApiModelProperty("验方员")
    private String operator;
    @ApiModelProperty("煎药员")
    private String decoctingMember;
    @ApiModelProperty("实际煎药时间")
    private Integer actualBoilTime;
    @ApiModelProperty("煎药机")
    private String machineCode;
}