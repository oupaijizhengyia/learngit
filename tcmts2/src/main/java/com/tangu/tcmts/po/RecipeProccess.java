package com.tangu.tcmts.po;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecipeProccess{
    @ApiModelProperty("处方id")
    private Integer recipeId;
    @ApiModelProperty("审方员")
    private Integer checkBillId;
    @ApiModelProperty("审方时间")
    private Date checkBillTime;
    @ApiModelProperty("发货人id")
    private Integer deliveryId;
    @ApiModelProperty("发货时间")
    private Date deliveryTime;
    @ApiModelProperty("上架时间")
    private Date shelveTime;
    
    public RecipeProccess(){
    	
    }
    
    public RecipeProccess(Integer recipeId, Integer deliveryId) {
    	this.recipeId = recipeId;
    	this.deliveryId = deliveryId;
    }
}