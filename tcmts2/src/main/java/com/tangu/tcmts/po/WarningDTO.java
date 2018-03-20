/**
 * FileName: WarningDTO
 * Author: yeyang
 * Date: 2018/1/30 9:51
 * 查询预警信息的条件
 */
package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class WarningDTO {
    @ApiModelProperty("开始时间")
    private Date startTime;
    @ApiModelProperty("结束时间")
    private Date endTime;
    @ApiModelProperty("处理状态")
    private Boolean warningStatus;
    @ApiModelProperty("操作类型")
    private Integer operateType;


}
