package com.tangu.tcmts.po;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:huangyifan
 * @Description:载入药品
 * @Date: create in 17：55 2017/11/7
 */
@Data
public class LoadMedicineDO {
	@ApiModelProperty(value = "仓库id", required = true)	
	private Integer warehouseId;
	@ApiModelProperty(value = "仓库药品规格list", required = true)	
	private List<MedicineStandardDO> list;
}
