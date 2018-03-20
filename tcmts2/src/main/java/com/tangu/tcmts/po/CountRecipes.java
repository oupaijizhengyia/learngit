package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class CountRecipes {
    @ApiModelProperty("已开单")
    private Integer orderBillingNumber;
    @ApiModelProperty("已配药")
    private Integer tisaneNumber;
    @ApiModelProperty("已复核")
    private Integer checkedNumber;
    @ApiModelProperty("已浸泡")
    private Integer soakedNumber;
    @ApiModelProperty("开始煎药")
    private Integer boilingNumber;
    @ApiModelProperty("先煎")
    private Integer boilFirstNumber;
    @ApiModelProperty("结束煎药")
    private Integer boilEndNumber;
    @ApiModelProperty("已收膏")
    private Integer pasteNumber;
    @ApiModelProperty("已打包")
    private Integer packedNumber;
    @ApiModelProperty("已签收")
    private Integer signedNumber;
    @ApiModelProperty("柜台签收")
    private Integer counterSigned;
    @ApiModelProperty("已发药")
    private Integer dispensing;
    @ApiModelProperty("已配送")
    private Integer delivery;
}
