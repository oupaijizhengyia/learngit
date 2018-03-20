package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class TransferRecipeDO {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("交易时间/收费时间")
    private Date tradeTime;
    @ApiModelProperty("接收时间")
    private Date receiveTime;
    @ApiModelProperty("是否接收,1.已接收，999.已作废")
    private Integer ifReceive;
    @ApiModelProperty("医院处方ID")
    private Long billId;
    @ApiModelProperty("医院处方号")
    private String recipeCode;
    @ApiModelProperty("处方序号")
    private String recipeSerial;
    @ApiModelProperty("处方编号")
    private String recipeBh;
    @ApiModelProperty("配送方式")
    private Integer carryId;
    @ApiModelProperty("医院编号")
    private String hospitalCode;
    @ApiModelProperty("医院id")
    private Integer hospitalId;
    @ApiModelProperty("分院号")
    private String partHospitalCode;
    @ApiModelProperty("煎药方式(1.代煎药,2.自煎,3,膏方代煎,4.膏方自煎)")
    private Integer boilType;
    @ApiModelProperty("门诊号/住院号")
    private String outpatientNum;
    @ApiModelProperty("病历号")
    private String recipientCode;
    @ApiModelProperty("病人姓名")
    private String recipientName;
    @ApiModelProperty("病人性别")
    private String sex;
    @ApiModelProperty("病人年龄")
    private String age;
    @ApiModelProperty("病人电话")
    private String recipientTel;
    @ApiModelProperty("收货人")
    private String consignee;
    @ApiModelProperty("收货人电话")
    private String consigneeTel;
    @ApiModelProperty("省")
    private String province;
    @ApiModelProperty("市")
    private String city;
    @ApiModelProperty("区")
    private String region;
    @ApiModelProperty("收货人地址")
    private String recipientAddress;
    @ApiModelProperty("医生姓名")
    private String doctorName;
    @ApiModelProperty("医生工号")
    private String doctorCode;
    @ApiModelProperty("处方贴数")
    private Integer quantity;
    @ApiModelProperty("每贴几包")
    private Integer packagePaste;
    @ApiModelProperty("总包数")
    private Integer totalPackagePaste;
    @ApiModelProperty("草药位数")
    private Integer medicineQuantity;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("打印状态,1.已打印,0.未打印")
    private Integer printState;
    @ApiModelProperty("医院金额")
    private BigDecimal money;
    @ApiModelProperty("出方客户单位")
    private String hisHospital;
    @ApiModelProperty("本地处方ID")
    private Integer nativeRecipeId;
    @ApiModelProperty("病区")
    private String inpatientArea;
    @ApiModelProperty("床号")
    private String bedNumber;
    @ApiModelProperty("总重量")
    private BigDecimal totalWeight;
    @ApiModelProperty("处方类型:普通处方")
    private String recipeType;
    @ApiModelProperty("就诊科室")
    private String department;
    @ApiModelProperty("临床病症")
    private String pathogen;
    @ApiModelProperty("费用类别:医保/自费")
    private String calType;
    @ApiModelProperty("服法")
    private String usage;
    @ApiModelProperty("用法：1.内服,2.外用,3.灌肠")
    private Integer takeType;
    @ApiModelProperty("1.浓煎,2.常煎")
    private String packType;
    @ApiModelProperty("1.使用医院金额")
    private Boolean useHisMoney;
    @ApiModelProperty("处方来源(1.电子处方,2.excel导入)")
    private Integer recipeSource;
    @ApiModelProperty("配送点")
    private String expressSite;
    @ApiModelProperty("加工费")
    private BigDecimal processCost;
    @ApiModelProperty("1.表示属于门诊，2表示属于病房")
    private Integer belong;
    @ApiModelProperty("医院名称")
    private String hospitalName;
    @ApiModelProperty("下载处方日期")
    private Date downloadDate;
    @ApiModelProperty("配送公司")
    private String transferType;
    private List<TransferMedicine> transferMedicineList;
    @ApiModelProperty("煎药方式名称")
    private String boilTypeName;
    @ApiModelProperty("发票号")
    private String invoiceCode;
    @ApiModelProperty("是否完全匹配：true.是,false.否")
    private Boolean medicineMatch;
    
    private TransferMedicine medicine;

    private String otherPackage;
    
    private String onlyOne;
    private String packingName;
    private Integer rowth;
    private Integer colth;
    private String psfs;
    private String medicineName2;
    private String customOneText;
    private String remark1;
    private String commonCode;
    @ApiModelProperty("取药时间")
    private Date takeTime;
    @ApiModelProperty("物流单号")
    private String logisticsCode;
    @ApiModelProperty("接方备注")
    private String receiveRemark;
    private Integer settleCompanyId;
    private Integer shippingState;
    public void initMedicine(){
		this.setRecipientName("");
		this.setQuantity(0);
		this.setRecipeCode("");
		this.setRecipeSerial("");
		this.setRemark("");
		this.setRecipientTel("");
		this.setRegion("");
		this.setRecipientAddress("");
		this.setInpatientArea("");
		this.setBedNumber("");
		this.setTotalWeight(new BigDecimal(0));
	}
    public TransferRecipeDO(){
    	
    }
    public TransferRecipeDO(Integer id, Integer ifReceive, Boolean medicineMatch) {
    	this.id = id;
    	this.ifReceive = ifReceive;
    	this.medicineMatch = medicineMatch;
    }
}