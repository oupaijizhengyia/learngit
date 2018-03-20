package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
/**
 * @author:yinghuaxu
 * @Description:结算方DTO
 * @Date: create in 10:24 2017/10/20
 */
@Data
@ApiModel
public class SettleCompanyDTO {
    @ApiModelProperty(value = "id" )
    private Integer id;
    @ApiModelProperty(value = "拼音缩写(20)")
    private String initialCode;
    @ApiModelProperty(value = "结算方名称")
    private String settleCompanyName;
    @ApiModelProperty(value = "0.启用,1.停用")
    private Integer useState;
    @ApiModelProperty(value = "价格类型")
    private Integer priceTypeId;

    private Integer modUser;

    private Date modTime;
}