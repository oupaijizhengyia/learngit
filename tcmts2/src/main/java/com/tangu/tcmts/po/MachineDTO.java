package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:huangyifan
 * @Description:煎药机管理DTO
 * @Date: create in 10：20 2017/10/24
 */
@Data
public class MachineDTO {
	@ApiModelProperty(value = "id")
	private Integer id;
	@ApiModelProperty(value = "煎药机编号varchar(20)")
	private String machineCode;
	@ApiModelProperty(value = "状态：0正常、1损坏int(1)")
	private Integer machineStatus;
	@ApiModelProperty(value = "0.启用,1.停用tinyint(1)")
	private Integer useState;
	@ApiModelProperty(value = "修改人ID")
	private Integer modUser;
	
}
