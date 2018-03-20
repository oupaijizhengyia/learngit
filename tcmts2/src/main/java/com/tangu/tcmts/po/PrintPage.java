package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PrintPage {
    @ApiModelProperty(value = "主键ID(新增无ID，修改有ID)")
    private Integer id;
    @ApiModelProperty(value = "模板名称 varchar(45)", required = true)
    private String pageName;
    @ApiModelProperty(value = "模板宽度", required = true)
    private Integer pageWidth;
    @ApiModelProperty(value = "模板高度", required = true)
    private Integer pageHeight;
    @ApiModelProperty(value = "排版方向:1.橫向,2.纵向")
    private Integer layoutDirection;
    @ApiModelProperty(value = "列数或行数")
    private Integer colNumber;
    @ApiModelProperty(value = "行间距")
    private Integer rowSpacing;
    @ApiModelProperty(value = "列间距")
    private Integer colSpacing;
    @ApiModelProperty(value = "单页数量")
    private Integer singlePageNumber;
    @ApiModelProperty(value = "模板类型", required = true)
    private String billType;
    @ApiModelProperty(value = "是否默认模板:true.是,false.否")
    private Boolean defaultState;
    @ApiModelProperty(value = "背景图片路径", required = true)
    private String image;
    @ApiModelProperty(value = "背景图片宽度", required = true)
    private Integer imageWidth;
    @ApiModelProperty(value = "背景图片高度", required = true)
    private Integer imageHeight;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "打印背景:图片路径")
    private String backgroundImage;
    @ApiModelProperty(value = "是否打印背景图片")
    private Boolean printBackgroundImage;
    @ApiModelProperty(value = "打印机ID")
    private Integer printerId;
    @ApiModelProperty(value = "修改人ID(无需传参数)")
    private Integer modUser;
    @ApiModelProperty(value = "修改时间(无需传参数)")
    private Date modTime;
    @ApiModelProperty(value = "打印模板名称")
    private String billTypeName;
    @ApiModelProperty(value = "打印明细")
    private List<PrintItem> printItemList;
    @ApiModelProperty(value = "打印机名称")
    private String printerName;

}