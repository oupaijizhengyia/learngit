package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author:yinghuaxu
 * @Description:结算方DO
 * @Date: create in 17:16 2017/10/20
 */
@Data
@ApiModel
public class SettleCompanyDO {
    @ApiModelProperty(value = "id" , required = true)
    private Integer id;
    @ApiModelProperty(value = "结算方名称",required = true)
    private String settleCompanyName;

    private String initialCode;
    @ApiModelProperty(value = "价格类型id",required = true)
    private Integer priceTypeId;
    @ApiModelProperty(value = "扣率",required = true)
    private BigDecimal discount;

    private BigDecimal taxRate;
    @ApiModelProperty(value = "加工费",required = true)
    private BigDecimal processCost;
    @ApiModelProperty(value = "加工费扣率",required = true)
    private BigDecimal feeRate;
    @ApiModelProperty(value = "业务人员",required = true)
    private Integer saleEmployeeId;
    @ApiModelProperty(value = "是否需要实时库存",required = true)
    private Integer realtimeStock;
    @ApiModelProperty(value = "包装,多个包装以,分割varchar(200)",required = true)
    private String packing;
    @ApiModelProperty(value = "备注varchar(200)",required = true)
    private String remark;
    @ApiModelProperty(value = "0.启用,1.停用")
    private Integer useState;
    @ApiModelProperty(value = "价格模板ID")
    private Integer priceTempletId;
    private Date createTime;

    private Integer modUser;

    private Date modTime;
    
    /**
     * 价格类型
     */
    private String priceTypeName;
    
    private List<SpecialDiscount> specialDiscountList;

    public List<SpecialDiscount> getSpecialDiscountList() {
        return specialDiscountList;
    }

}
