package com.tangu.tcmts.po;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:huangyifan
 * @Description:煎药机管理DO
 * @Date: create in 10：20 2017/10/24
 */
@Data
public class MachineDO {
	@ApiModelProperty(value = "id", required = true)
	private Integer id;
	@ApiModelProperty(value = "煎药机编号", required = true)
	private String machineCode;
	@ApiModelProperty(value = "煎药机型号", required = true)
	private String machineType;
	@ApiModelProperty(value = "包装机编号", required = true)
	private String packMachineCode;
	@ApiModelProperty(value = "运行状态：0.正常,1.正在使用,9损坏")
	private Integer machineStatus;
	@ApiModelProperty(value = "巡检人ID")
	private Integer checktorId;
	@ApiModelProperty(value = "巡检时间")
	private Date checkTime;
	@ApiModelProperty(value = "卫生状态:1.合格、0.不合格")
	private Integer healthStatus;
	@ApiModelProperty(value = "消毒状态:1.合格、0.不合格")
	private Integer sterStatus;
	@ApiModelProperty(value = "启用状态：1.启用,0.停用")
	private Integer useState;
	@ApiModelProperty(value = "包装mac地址")
	private String packingMac;
	@ApiModelProperty(value = "打印机型号")
	private String tagPrinterNum;
	@ApiModelProperty(value = "温度采集MAC")
	private String tpMac;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "修改人ID")
	private Integer modUser;
	@ApiModelProperty(value = "巡检员")
	private String modUserName;

}