package com.tangu.tcmts.po;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class RecipePara {
	@ApiModelProperty("单个处方接收参数")
	private RecipeDO recipe;
	@ApiModelProperty("批量接收参数，只需要传入id即可")
	private List<TransferRecipeDTO> transferRecipeList;
	@ApiModelProperty("药品关联集合")
	private List<MedicineRelationDO> relationList;
	@ApiModelProperty("订单类型：0，院内 1，非院内")
	private Integer orderType;
}
