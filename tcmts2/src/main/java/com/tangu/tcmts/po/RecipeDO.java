package com.tangu.tcmts.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecipeDO{
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("开单时间")
    private Date createTime;
    @ApiModelProperty("受理日期")
    private Date dealDate;
    @ApiModelProperty("系统处方号")
    private String sysRecipeCode;
    @ApiModelProperty("医院处方号")
    private String recipeCode;
    @ApiModelProperty("处方序号")
    private String recipeSerial;
    @ApiModelProperty("处方编号")
    private String recipeBh;
    @ApiModelProperty("修改人ID")
    private Integer modUser;
    @ApiModelProperty("修改时间")
    private Date modTime;
    @ApiModelProperty("开单人ID")
    private Integer creatorId;
    @ApiModelProperty("开单人")
    private String creator;
    @ApiModelProperty("流程状态 999.报废")
    private Integer shippingState;
    @ApiModelProperty("状态名称")
    private String stateName;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("医院id")
    private Integer hospitalId;
    @ApiModelProperty("结算方id")
    private Integer settleCompanyId;
    @ApiModelProperty("煎药方式(1.代煎药,2.自煎,3,膏方代煎,4.膏方自煎)")
    private Integer boilType;
    @ApiModelProperty("收件人id")
    private Integer recipientId;
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
    @ApiModelProperty("收货地址")
    private String recipientAddress;
    @ApiModelProperty("病区")
    private String inpatientArea;
    @ApiModelProperty("床位")
    private String bedNumber;
    @ApiModelProperty("科别")
    private String department;
    @ApiModelProperty("配送点")
    private String expressSite;
    @ApiModelProperty("1.属于门诊，2.属于病房")
    private Integer belong;
    @ApiModelProperty("门诊住院名称")
    private String belongName;
    @ApiModelProperty("医生")
    private String doctorName;
    @ApiModelProperty("临床病症")
    private String pathogen;
    @ApiModelProperty("服法")
    private String usage;
    @ApiModelProperty("贴数")
    private Integer quantity;
    @ApiModelProperty("每贴几包")
    private Integer packagePaste;
    @ApiModelProperty("总包数")
    private Integer totalPackagePaste;
    @ApiModelProperty("药味数")
    private Integer medicineQuantity;
    @ApiModelProperty("药品总量")
    private BigDecimal totalWeight;
    @ApiModelProperty("浸泡时间")
    private Integer soakTime;
    @ApiModelProperty("煎药时间")
    private Integer boilTime;
    @ApiModelProperty("配送公司")
    private Integer carryId;
    @ApiModelProperty("业务类型")
    private Integer businessType;
    @ApiModelProperty("快件类型名")
    private String businessTypeName;
    @ApiModelProperty("配方补录时间")
    private Date inputTime;
    @ApiModelProperty("配方补录人ID")
    private Integer inputEmployee;
    @ApiModelProperty("配方录入状态,1.已录入")
    private Integer inputState;
    @ApiModelProperty("物流单号")
    private String logisticsCode;
    @ApiModelProperty("原寄地区号")
    private String originCode;
    @ApiModelProperty("目的地区号")
    private String destCode;
    @ApiModelProperty("物流下单状态")
    private Integer makeOrderState;
    @ApiModelProperty("煎煮员ID")
    private Integer boilId;
    @ApiModelProperty("实际煎药时间")
    private Integer actualBoilTime;
    @ApiModelProperty("浸泡员id")
    private Integer soakId;
    @ApiModelProperty("开始浸泡时间")
    private Date startSoakTime;
    @ApiModelProperty("审核状态 1.审核 0.未审核")
    private Integer checkState;
    @ApiModelProperty("煎药机编码")
    private String machineCode;
    @ApiModelProperty("审方员ID")
    private Integer dispenseId;
    @ApiModelProperty("审方员姓名")
    private String dispenseName;
    @ApiModelProperty("0.手工方,1.电子处方,2.excel导入")
    private Integer recipeSource;
    @ApiModelProperty("是否使用医院金额")
    private Boolean useHisMoney;
    @ApiModelProperty("医院金额")
    private BigDecimal money;
    @ApiModelProperty("批发费用")
    private BigDecimal tradeFreight;
    @ApiModelProperty("药品零售费用")
    private BigDecimal retailFreight;
    @ApiModelProperty("含税金额")
    private BigDecimal vatFreight;
    @ApiModelProperty("无税金额")
    private BigDecimal taxlessFreight;
    @ApiModelProperty("扣率")
    private BigDecimal discount;
    @ApiModelProperty("加工费")
    private BigDecimal processCost;
    @ApiModelProperty("特殊打印字段")
    private String specialPrint;
    @ApiModelProperty("打印状态,1.已打印")
    private Integer printState;
    @ApiModelProperty("包装规格")
    private String packType;
    @ApiModelProperty("另包")
    private String otherPackage;
    @ApiModelProperty("包装名称")
    private String packingName;
    @ApiModelProperty("药品id")
    private Long  medicineId;
    @ApiModelProperty("医院名称")
    private String hospitalName;
    @ApiModelProperty("配送公司")
    private String transferType;
    @ApiModelProperty("预审核方状态:1正常,2.配伍禁忌")
    private Integer preCheckState;
    @ApiModelProperty("药品明细")
    private List<RecipeMedicine> recipeMedicineList;

    @ApiModelProperty("几号价")
    private Integer priceTypeId;
    @ApiModelProperty("煎药方式名称")
    private String boilTypeName;
    private Long billId;
    private String hospitalCode;
    @ApiModelProperty("自定义文本1")
    private String customOneText;
    @ApiModelProperty("自定义文本2")
    private String customTwoText;
    @ApiModelProperty("自定义费用1")
    private BigDecimal customOneFee;
    @ApiModelProperty("自定义费用2")
    private BigDecimal customTwoFee;
    @ApiModelProperty("操作人ID")
    private Integer scanUser;
    @ApiModelProperty("扫描时间")
    private Date receiveScanTime;
    @ApiModelProperty("操作类型")
    private Integer operateType;
    @ApiModelProperty("开始煎煮人id")
    private Integer startBoilId;
    @ApiModelProperty("开始煎煮时间")
    private Date startBoilTime;
    @ApiModelProperty("结束煎煮人id")
    private Integer endBoilId;
    @ApiModelProperty("结束煎煮时间")
    private Date endBoilTime;
    @ApiModelProperty("煎药机id")
    private Integer machineId;
    @ApiModelProperty("温度mac")
    private String spMac;
    @ApiModelProperty("配药时间")
    private Date makeTime;
    @ApiModelProperty("配药人id")
    private Integer makeId;
    @ApiModelProperty("煎药机id")
    private Integer boilMachineId;
    @ApiModelProperty("收膏id")
    private Integer collectId;
    @ApiModelProperty("收膏时间")
    private Date collectTime;
    @ApiModelProperty("包装id")
    private Integer packageId;
    @ApiModelProperty("包装时间")
    private Date packageTime;
    @ApiModelProperty("结束沉淀时间")
    private Date endSubsideTime;
    @ApiModelProperty("先煎人id")
    private Integer fristBoilId;
    @ApiModelProperty("开始先煎时间")
    private Date fristBoilTime;
    @ApiModelProperty("审方人id")
    private Integer checkBillId;
    @ApiModelProperty("审方时间")
    private Date checkBillTime;
    @ApiModelProperty("后下人id")
    private Integer afterBoilId;
    @ApiModelProperty("后下时间")
    private Date afterBoilTime;
    @ApiModelProperty("发货人id")
    private Integer deliveryId;
    @ApiModelProperty("发货时间")
    private Date deliveryTime;
    @ApiModelProperty("是否沉淀")
    private Integer addSubside;
    @ApiModelProperty("复核人id")
    private Integer checkId;
    @ApiModelProperty("复核时间")
    private Date checkTime;
    @ApiModelProperty("上架人id")
    private Integer shelveUser;
    @ApiModelProperty("上架时间")
    private Date shelveTime;
    @ApiModelProperty("住院号/门诊号")
    private String outpatientNum;

    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;

    @ApiModelProperty
    private BigDecimal totalProcessCost;
    
    @ApiModelProperty("医院名称")
    private String hospitalNickname;
    @ApiModelProperty(value = "操作人名称")
	private String employeeName;
    @ApiModelProperty(value = "员工ID")
    private Integer employeeId;
    
    @ApiModelProperty("浸泡时间")
    private Integer soakTimeLong;
    @ApiModelProperty("处方List")
    private List<RecipeDO> recipeList;
    @ApiModelProperty("列表")
    private List selectedList;
    @ApiModelProperty("开始浓缩体积")
    private float startConcentrateVolume;
    @ApiModelProperty("结束浓缩体积")
	private float endConcentrateVolume;
	@ApiModelProperty("操作结果返回类型")
	private float actionResult;
	@ApiModelProperty("药品列表")
	private List medicineList;
	private BigDecimal unitPrice;
	@ApiModelProperty("发票号")
	private String invoiceCode;
	@ApiModelProperty("取药时间")
	private String takeTime;
	@ApiModelProperty("接方备注")
	private String receiveRemark;
	@ApiModelProperty("用法：1.内服,2.外用,3.灌肠")
	private Integer takeType;
	@ApiModelProperty("二煎时间")
	private Integer secondBoilTime;
	@ApiModelProperty("货架号")
	private String shelvesCode;
	@ApiModelProperty("存在配伍禁忌")
	private String medicineTaboo;
	@ApiModelProperty("存在超剂量")
	private String medicineMaxDose;
	@ApiModelProperty("0.院内接口,1.非院内接口")
	private Integer orderType;
    @ApiModelProperty("锁定状态 1-锁定")
    private Integer lockState;
	private Integer hospitalStatus;
	@ApiModelProperty("煎煮方案id")
	private Integer boilProjectId;
	@ApiModelProperty("煎煮方案名称")
	private String boilProjectName;
	@ApiModelProperty("药液量 == 页面上的包装规格")
	private BigDecimal volume;
	@ApiModelProperty("后下人id")
    private Integer secondBoilId;
    @ApiModelProperty("二煎开始时间")
    private Date startSecondBoilTime;
    private Integer tmpCarryId;
    private String tmpLogisicsCode;
    @ApiModelProperty("是否加急,true(1)加急,false(0)普通")
    private Boolean isUrgent;
    @ApiModelProperty("处方详情图片")
    private List<String> filePath;
    @ApiModelProperty("病历号")
    private String recipientCode;
    @ApiModelProperty("关联id")
    private Integer unionId;
    @ApiModelProperty("关联状态,1关联,0不关联")
    private boolean unionState;

    @ApiModelProperty("处方详情图片")
    private List<RecipeFile> recipeFile;
    @ApiModelProperty("配药人姓名.")
    private String makeName;

    public void setUnionId(Integer unionId) {
        this.unionId = unionId;
        if (unionId == null){
            setUnionId(0);
        }
        this.unionState = unionId.equals(0) ? false : true;
    }


}