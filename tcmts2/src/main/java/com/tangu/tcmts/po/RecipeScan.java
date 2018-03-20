package com.tangu.tcmts.po;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @author:huangyifan
 * @Description:操作流程
 * @Date: create in 15：32 2017/11/18
 */
@Data
public class RecipeScan {
	@ApiModelProperty(value = "id")
    private Long id;
	@ApiModelProperty(value = "处方id")
    private Integer recipeId;
	@ApiModelProperty(value = "节点时间")
    private Date receiveScanTime;
	@ApiModelProperty(value = "操作人")
    private Integer receiveScanUse;
	@ApiModelProperty(value = "图片路径")
    private String picturePath;
	@ApiModelProperty(value = "操作说明")
    private String operateComment;
	@ApiModelProperty(value = "操作类型")
    private Integer operateType;
	@ApiModelProperty(value = "煎药机id")
    private Integer machineId;
	@ApiModelProperty(value = "相应处方贴数")
    private Integer quantity;
	@ApiModelProperty(value = "浓缩体积")
    private Float concentrateVolume;
	@ApiModelProperty(value = "操作类型名称")
	private String operateTypeName;
	@ApiModelProperty(value = "操作人名称")
	private String employeeName;
	@ApiModelProperty("浸泡时间")
    private Integer soakTime;
    @ApiModelProperty("煎药时间")
    private Integer boilTime;
    @ApiModelProperty("流程状态 999.报废")
    private Integer shippingState;
    @ApiModelProperty("病人姓名")
    private String recipientName;
    @ApiModelProperty("医院名称")
    private String hospitalNickname;
    @ApiModelProperty("开始浓缩体积")
    private float startConcentrateVolume;
    @ApiModelProperty("结束浓缩体积")
	private float endConcentrateVolume;
    @ApiModelProperty("总处方数")
    private Integer countRecipe;
    @ApiModelProperty("总帖数")
    private Integer sumquantity;
    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;
    
    public RecipeScan() {
    	
    }
    public RecipeScan(Integer recipeId, Integer receiveScanUse, Integer operateType) {
    	this.recipeId = recipeId;
    	this.receiveScanUse = receiveScanUse;
    	this.operateType = operateType;
    }
}