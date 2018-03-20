package com.tangu.tcmts.po;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecipeSerial {

    @ApiModelProperty("处方id")
    private Integer recipeId;

    @ApiModelProperty("取药条码")
    private String recipeSerial;
    
    @ApiModelProperty("标签条码")
    private String sysRecipeCode;
    
    @ApiModelProperty("患者姓名")
    private String recipientName;

    @ApiModelProperty("联系方式")
    private String recipientTel;
    
    @ApiModelProperty("货位号")
    private String shelvesCode;

    @ApiModelProperty("处方状态id/发药状态   100.已发药  上架待定")
    private Integer shippingState;
    
    @ApiModelProperty("处方状态名")
    private String shippingStateName;
    
    @ApiModelProperty("状态时间")
    private Date stateTime;
    
    @ApiModelProperty("上架时间")
    private Date shelveTime;
    
    @ApiModelProperty("发药时间")
    private Date deliveryTime;

    @ApiModelProperty("取药时间")
    private Date takeTime;
    
    @ApiModelProperty("开始时间")
    private Date startTime;
    
    @ApiModelProperty("结束时间")
    private Date endTime;
    
    @ApiModelProperty("发票号")
    private String invoiceCode;
    
    @ApiModelProperty("门诊号")
    private String outpatientNum;
    
    private Date endBoilTime;
    
    private Date startBoilTime;
    
    private Date makeTime;
    
    private Date checkTime;
    private Date startSoakTime;
    private Date collectTime;
    private Date packageTime;
    private Date startConcentrateTime;
    private Date endConcentrateTime;
    private Date endSubsideTime;
    private Date fristBoilTime;
    private Date afterBoilTime;
    private Date checkBillTime;
}