package com.tangu.tcmts.po;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @author:huangyifan
 * @Description:处方记录
 * @Date: create in 14：20 2017/11/18
 */
@Data
public class RecipeHistory {
	@ApiModelProperty("id")
    private Long id;
	@ApiModelProperty("处方id")
    private Integer recipeId;
	@ApiModelProperty("操作类型")
    private Integer operateType;
	@ApiModelProperty("修改人id")
    private Integer operateUser;
	@ApiModelProperty("修改时间")
    private Date operateTime;
	@ApiModelProperty("修改说明")
    private String operateComment;
	@ApiModelProperty("操作类型名称")
	private String operateTypeName;
	@ApiModelProperty("操作人名称")
	private String employeeName;
}