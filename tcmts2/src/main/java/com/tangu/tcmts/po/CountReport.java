/**
 * FileName: CountReport
 * Author: laocu
 * Date: 2018/1/26 11:59
 */
package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CountReport {
    @ApiModelProperty("合计数量")
    private BigDecimal countNum;
    @ApiModelProperty("合计批发价格")
    private BigDecimal countTradeFreight;
    @ApiModelProperty("合计零售价格")
    private BigDecimal countRetailFreight;
}
