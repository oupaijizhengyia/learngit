package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
/**
 * @author:yinghuaxu
 * @Description:订单列表查询条件
 * @Date: create in 13:31 2017/11/15
 */
@Data
public class TransferRecipeDTO {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("医院处方id")
    private Long billId;
    @ApiModelProperty("门诊住院id 1门诊，2住院")
    private Integer belong;
    @ApiModelProperty("交易时间")
    private Date tradeTime;
    @ApiModelProperty("下载处方日期")
    private Date downloadDate;
    @ApiModelProperty("是否接收,1.已接收,999.作废")
    private Integer ifReceive;
    @ApiModelProperty("医院编号")
    private String hospitalCode;
    @ApiModelProperty("医院id")
    private Integer hospitalId;
    @ApiModelProperty("配送方式ID")
    private Integer carryId;
    @ApiModelProperty("配送公司")
    private String transferType;
    @ApiModelProperty("煎药方式(1.代煎药,2.自煎,3,膏方代煎,4.膏方自煎)")
    private Integer boilType;
    @ApiModelProperty("病人姓名")
    private String recipientName;
    @ApiModelProperty("医院处方号")
    private String recipeCode;
    @ApiModelProperty("处方序号")
    private String recipeSerial;
    @ApiModelProperty("打印状态,1.已打印,0.未打印")
    private Integer printState;
    @ApiModelProperty("病区")
    private String inpatientArea;
    @ApiModelProperty("配送点")
    private String expressSite;
    @ApiModelProperty("出方客户单位(医院)")
    private String hisHospital;
    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;
    @ApiModelProperty("医院编号值（下拉获取得）")
    private String hospitalCodeValue;
    @ApiModelProperty("发票号")
    private String invoiceCode;
    @ApiModelProperty("门诊号")
    private String outpatientNum;
    @ApiModelProperty("是否完全匹配：true.是,false.否")
    private Boolean medicineMatch;
    private Date takeTime;
    private Integer orderType;
    private String logisticsCode;
    private String receiveRemark;
    private Integer receiverId;
}
