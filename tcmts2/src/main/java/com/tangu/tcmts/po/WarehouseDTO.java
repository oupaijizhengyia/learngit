package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:huangyifan
 * @Description:仓库、药房DTO
 * @Date: create in 17:00 2017/10/24
 */
@Data
public class WarehouseDTO {
	@ApiModelProperty(value = "id", required = true)
    private Integer id;
	@ApiModelProperty(value = "仓库/药房名称", required = true)
    private String warehouseName;
	@ApiModelProperty(value = "数据类型：1.仓库,3.药房(饮片),4.药房(小包装)")
    private Integer warehouseType;
	@ApiModelProperty(value = "0.在用,1.停用")
    private Integer useState;
	@ApiModelProperty(value = "修改人ID")
    private Integer modUser;
}
