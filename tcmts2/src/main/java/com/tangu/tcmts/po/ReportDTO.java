/**
 * FileName: Condition
 * Author: laocu
 * Date: 2018/1/25 13:46
 * 传入参数对象
 */
package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class ReportDTO {

    @ApiModelProperty("开始时间")
    private Date startTime;
    @ApiModelProperty("结束时间")
    private Date endTime;
    @ApiModelProperty("药品ID")
    private Integer medicineId;
    @ApiModelProperty("煎煮类型")
    private Integer boilType;
    @ApiModelProperty("统计类型")
    private Integer countType;
    @ApiModelProperty("结算方ID")
    private Integer settleCompanyId;
     @ApiModelProperty("医院ID")
    private Integer hospitalId;

}
