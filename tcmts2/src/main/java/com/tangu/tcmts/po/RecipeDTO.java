package com.tangu.tcmts.po;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecipeDTO {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("医院id")
    private Integer hospitalId;
    @ApiModelProperty("1.属于门诊，2.属于住院")
    private Integer belong;
    @ApiModelProperty("病人姓名")
    private String recipientName;
    @ApiModelProperty("流程状态 999.报废")
    private Integer shippingState;
    @ApiModelProperty("煎药方式(1.代煎药,2.自煎,3,膏方代煎,4.膏方自煎)")
    private Integer boilType;
    @ApiModelProperty("配送公司")
    private Integer carryId;
    @ApiModelProperty("审核状态 1.审核 0.未审核")
    private Integer checkState;
    @ApiModelProperty("配方录入状态,1.已录入 0.未录入")
    private Integer inputState;
    @ApiModelProperty("结算方id")
    private Integer settleCompanyId;
    @ApiModelProperty("系统处方号")
    private String sysRecipeCode;
    @ApiModelProperty("医院处方号")
    private String recipeCode;
    @ApiModelProperty("物流单号")
    private String logisticsCode;
    @ApiModelProperty("配送点")
    private String expressSite;
    @ApiModelProperty("特殊打印字段(特煎方式)")
    private String specialPrint;
    @ApiModelProperty("另包")
    private String otherPackage;
    @ApiModelProperty("打印状态,1.已打印 0.未打印")
    private Integer printState;
    @ApiModelProperty("开单人ID")
    private Integer creatorId;
    @ApiModelProperty("医生")
    private String doctorName;
    @ApiModelProperty("病区")
    private String inpatientArea;
    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;
    @ApiModelProperty("日期类型 dealDate:受理时间,createTime:开单时间")
    private String dateType;
    @ApiModelProperty("药品id")
    private Integer medicineId;
    @ApiModelProperty("预审核方状态:1正常,2.配伍禁忌")
    private Integer preCheckState;
    @ApiModelProperty("包装规格")
    private String packType;
    @ApiModelProperty("医院编号")
    private String hospitalCode;
    @ApiModelProperty("包装类型")
    private String packingName;
    @ApiModelProperty("住院号/门诊号")
    private String outpatientNum;
    @ApiModelProperty("锁定状态 1-锁定")
    private Integer lockState;
    @ApiModelProperty("用法")
    private Integer takeType;

    private String hospitalName;
    private String transferType;
    private Integer modUser;
    private Integer dispenseId;
    @ApiModelProperty("发票号")
    private String invoiceCode;
    @ApiModelProperty("科室")
    private String department;
    @ApiModelProperty("床号")
    private String bedNumber;
    private String recipeSerial;

    @ApiModelProperty("是否加急,true(1)加急,false(0)普通")
    private Boolean isUrgent;
    @ApiModelProperty("配药人id")
    private Integer makeId;

    
    @ApiModelProperty("删除图片列表")
    private List<RecipeFile> deleteRecipeFile;
    @ApiModelProperty("新增图片列表")
    private List<RecipeFile> insertRecipeFile;

	public RecipeDTO() {

	}

	public RecipeDTO(Integer id) {
		this.id = id;
	}
	public RecipeDTO(Integer shippingState, Integer id) {
		this.shippingState = shippingState;
		this.id = id;
	}
}
