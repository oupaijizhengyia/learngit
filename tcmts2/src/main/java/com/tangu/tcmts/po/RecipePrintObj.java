package com.tangu.tcmts.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecipePrintObj extends RecipeDO implements Cloneable{
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
    @ApiModelProperty(value = "是否打印流程单",required = true)
    private Boolean isPrintProcess;
    @ApiModelProperty(value = "医院全称")
    private String hospitalCompany;
    @ApiModelProperty(value = "公司电话")
    private String companyTel;
    @ApiModelProperty(value = "公司地址")
    private String companyAddress;
    @ApiModelProperty(value = "收件地址")
    private String totalAddress;
    private Integer pageCount;//第几页
    private Integer pageIndex;//共几页
    private String pageIndexString;//页码
    private Integer tagNum;//标签数量
    private String oddNumber;//带空格物流单号
    private String totalWeightPart;//饮片200,细料：400
    private String cardNum;//月结卡号
    private Integer payMethod;//付款方式
    private String payMethodName;//付款方式名称
    private Date printDate;//打印时间
    private Date takeTimeDate;//取药时间
    private BigDecimal weightEvery;//单贴量
    private String checkBillName;//审方员名称
    private String checkBillCode;//审方员code
    private String makeName;//配药员
    private String checkName;//复核员
    private String soakName;//浸泡员
    private String startBoilName;//开始煎煮员
    private String operator;//操作员
    private String doctorCode;//医生工号
    private List<TransferMedicineObj> transferMedicineList;//药品明细
    
    @Override
    public Object clone(){
    	RecipePrintObj o = null;
        try{
            o = (RecipePrintObj) super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return o;
    }
}
