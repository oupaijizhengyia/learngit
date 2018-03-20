package com.tangu.tcmts.po;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */
@Data
public class DispensationReciveRecipeVO {

    @ApiModelProperty("员工ID")
    private Integer employeeId;
    @ApiModelProperty("处方ID")
    private Integer recipeId;
    @ApiModelProperty("处方号")
    private String recipeCode;
    @ApiModelProperty("员工编号")
    private String employeeCode;
    @ApiModelProperty("员工姓名")
    private String employeeName;
    @ApiModelProperty("员工今日配方数量")
    private Integer recipeCount;
    @ApiModelProperty("处方和关联的处方的集合")
    private List<String> recipeList;
    @ApiModelProperty("处方单数")
    private Integer recipeListCount;
    @ApiModelProperty("患者姓名")
    private String recipientName;
    @ApiModelProperty("贴数")
    private Integer quantity;
    @ApiModelProperty("处方状态")
    private Integer shippingState;
    @ApiModelProperty("员工配药状态 1:有处方已打印未完成配药 0:已自动派发处方")
    private Integer dispensationState;
    @ApiModelProperty("包含图片路径等的包装类list")
    private List<RecipeFile> recipeFileList;

}
