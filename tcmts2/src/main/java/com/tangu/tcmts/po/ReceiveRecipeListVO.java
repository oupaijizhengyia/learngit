package com.tangu.tcmts.po;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */

@Data
public class ReceiveRecipeListVO {

    @ApiModelProperty("处方ID")
    private Integer id;
    @ApiModelProperty("处方号")
    private String recipeCode;
    @ApiModelProperty("患者姓名")
    private String recipientName;
    @ApiModelProperty("患者联系方式")
    private String recipientTel;
    @ApiModelProperty("贴数")
    private Integer quantity;
    @ApiModelProperty("医生姓名")
    private String doctorName;
    @ApiModelProperty("是否已接受 1：已接受 0：未接受 999：已报废")
    private Integer ifReceive;
    @ApiModelProperty("煎药方式 1:代煎 2:代配 3:膏方代煎 4:膏方代配 5:馆内代煎")
    private Integer boilType;
    @ApiModelProperty("配送方式ID")
    private Integer carryId;
    @ApiModelProperty("是否加急 true(1)加急 false(0)普通")
    private boolean ifUrgent;
    @ApiModelProperty("交易时间/收费时间")
    private Date tradeTime;
    @ApiModelProperty("接收时间")
    private Date receiveTime;
    @ApiModelProperty("关联处方单号")
    private List<String> recipeCodes;
    @ApiModelProperty("图片路径")
    private List<RecipeFile> recipeFileList;



}
