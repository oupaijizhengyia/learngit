package com.tangu.tcmts.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @author:yinghuaxu
 * @Description:医院管理DO
 * @Date: create in 9:25 2017/10/24
 */
@Data
public class HospitalDO{
    @ApiModelProperty(value = "id",required = true)
    private Integer id;
    @ApiModelProperty(value = "医院编码",required = true)
    private String hospitalCode;
    @ApiModelProperty(value = "医院名称",required = true)
    private String hospitalCompany;

    private String initialCode;
    @ApiModelProperty(value = "医院简称",required = true)
    private String hospitalNickname;
    @ApiModelProperty(value = "联系人",required = true)
    private String hospitalManager;
    @ApiModelProperty(value = "联系电话",required = true)
    private String hospitalTel;
    @ApiModelProperty(value = "省",required = true)
    private String province;
    @ApiModelProperty(value = "市",required = true)
    private String city;
    @ApiModelProperty(value = "区",required = true)
    private String region;
    @ApiModelProperty(value = "地址",required = true)
    private String address;
    @ApiModelProperty(value = "结算方id",required = true)
    private Integer settleCompanyId;
    @ApiModelProperty(value = "门诊标签打印模板id",required = true)
    private Integer outpatientTagPrint;
    @ApiModelProperty(value = "门诊处方打印模板id",required = true)
    private Integer outpatientRecipePrint;
    @ApiModelProperty(value = "门诊代配标签打印类型,1.固定,2.包数*贴数,3.贴数",required = true)
    private Integer outpatientTagType;
    @ApiModelProperty(value = "门诊代配固定数量/额外数量",required = true)
    private Integer outpatientTagNum;
    @ApiModelProperty(value = "门诊代煎标签打印类型,1.固定,2.包数*贴数,3.贴数",required = true)
    private Integer outpatientBoilType;
    @ApiModelProperty(value = "门诊代煎固定数量/额外数量",required = true)
    private Integer outpatientBoilNum;
    @ApiModelProperty(value = "住院标签打印模板id",required = true)
    private Integer hospitalTagPrint;
    @ApiModelProperty(value = "住院处方打印模板id",required = true)
    private Integer hospitalRecipePrint;
    @ApiModelProperty(value = "住院代配标签打印类型,1.固定,2.包数*贴数,3.贴数",required = true)
    private Integer hospitalTagType;
    @ApiModelProperty(value = "住院代配固定数量/额外数量",required = true)
    private Integer hospitalTagNum;
    @ApiModelProperty(value = "住院代煎标签打印类型,1.固定,2.包数*贴数,3.贴数",required = true)
    private Integer hospitalBoilType;
    @ApiModelProperty(value = "住院代煎固定数量/额外数量",required = true)
    private Integer hospitalBoilNum;
    @ApiModelProperty(value = "默认配送方式",required = true)
    private Integer carryType;
    @ApiModelProperty(value = "平台编号",required = true)
    private String sunshineCode;
    @ApiModelProperty(value = "是否打印流程单",required = true)
    private Boolean isPrintProcess;

    private Integer useState;

    private Integer modUser;

    private Date createTime;

    private Date modTime;
    @ApiModelProperty(value = "几号价")
    private Integer priceTypeId;
    @ApiModelProperty(value = "扣率")
    private BigDecimal discount;
    @ApiModelProperty(value = "加工费")
    private BigDecimal processCost;
    @ApiModelProperty(value = "加工费扣率")
    private BigDecimal feeRate;

    @ApiModelProperty("结算方包装")
    private String packing;
    private List<SpecialDiscount> specialDiscountList;
}