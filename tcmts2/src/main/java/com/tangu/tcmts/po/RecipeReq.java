package com.tangu.tcmts.po;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecipeReq{
	@ApiModelProperty("下划线连接打印类型,"
    		+ "列：groupPrint=recipe_recipeTag_recipeProcess "
    		+ "表示打印处方、标签、流程单")
    private String groupPrint;
	@ApiModelProperty("主键ID")
	private Integer id;
	@ApiModelProperty("标签打印数量")
	private Integer tagNum;
	@ApiModelProperty("打印列表只传ID")
	private List<RecipeDO> recipeList;
}
