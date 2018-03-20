/**
 * FileName: Report
 * Author: laocu
 * Date: 2018/1/25 10:49
 * 报表查询的传输对象
 */
package com.tangu.tcmts.po;


import com.tangu.tcmts.util.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class Report {
    @ApiModelProperty(value = "医院名称")
    private String hospitalCompany;

    @ApiModelProperty(value = "结算方名称")
    private String settleCompanyName;

    @ApiModelProperty(value = "药品名称")
    private String medicineName;

    @ApiModelProperty(value = "药品编码")
    private String medicineCode;

    @ApiModelProperty(value = "单位")
    private String standard;

    @ApiModelProperty(value = "数量")
    private BigDecimal num;

    @ApiModelProperty(value = "批发价")
    private  BigDecimal tradeFreight;

    @ApiModelProperty(value = "零售价")
    private  BigDecimal retailFreight;

    public void setNum(BigDecimal num) {
        if(Constants.TYPE_STANDARD.equals(this.getStandard())){
           num = num.multiply(new BigDecimal("0.001"));
        }
        this.num = num;
    }
}
