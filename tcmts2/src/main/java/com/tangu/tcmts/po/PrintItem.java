package com.tangu.tcmts.po;

import io.swagger.annotations.ApiModelProperty;

public class PrintItem {
    @ApiModelProperty(value = "主键ID(新增id无值，修改有ID)")
    private Integer id;
    @ApiModelProperty(value = "模板ID", required = true)
    private Integer pageId;
    @ApiModelProperty(value = "字段ID", required = true)
    private Integer fieldId;
    @ApiModelProperty(value = "字段x点", required = true)
    private Integer itemX;
    @ApiModelProperty(value = "字段y点", required = true)
    private Integer itemY;
    @ApiModelProperty(value = "字段打印宽度", required = true)
    private Integer itemWidth;
    @ApiModelProperty(value = "字段打印高度", required = true)
    private Integer itemHeight;
    @ApiModelProperty(value = "字段打印组合", required = true)
    private String itemPara;
    @ApiModelProperty(value = "字体类型:宋体，楷体。。。", required = true)
    private String fontFamily;
    @ApiModelProperty(value = "字体大小", required = true)
    private Integer fontSize;
    @ApiModelProperty(value = "正常/加粗:false/true")
    private Boolean fontWeight;
    @ApiModelProperty(value = "正常/斜体:false/true")
    private Boolean fontStyle;
    @ApiModelProperty(value = "无下划线/有下划线:false/true")
    private Boolean fontDecoration;
    @ApiModelProperty(value = "字体对齐方式:left/center/right")
    private String textAlign;
    @ApiModelProperty(value = "字体间距")
    private Integer letterSpacing;
    @ApiModelProperty(value = "是否自动缩放:false.否,true.是")
    private Boolean autoSize;
    @ApiModelProperty(value = "黑色背景:false.否,true.是")
    private Boolean backgroundColor;


    @ApiModelProperty(value = "字段名称")
    private String fieldName;
    @ApiModelProperty(value = "字段类型")
    private String fieldType;
    @ApiModelProperty(value = "字段")
    private String fieldSource;
    @ApiModelProperty(value = "面单实际显示名称")
    private String displayText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getItemX() {
        return itemX;
    }

    public void setItemX(Integer itemX) {
        this.itemX = itemX;
    }

    public Integer getItemY() {
        return itemY;
    }

    public void setItemY(Integer itemY) {
        this.itemY = itemY;
    }

    public Integer getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(Integer itemWidth) {
        this.itemWidth = itemWidth;
    }

    public Integer getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(Integer itemHeight) {
        this.itemHeight = itemHeight;
    }

    public String getItemPara() {
        return itemPara;
    }

    public void setItemPara(String itemPara) {
        this.itemPara = itemPara;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Boolean getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(Boolean fontWeight) {
        this.fontWeight = fontWeight;
    }

    public Boolean getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(Boolean fontStyle) {
        this.fontStyle = fontStyle;
    }

    public Boolean getFontDecoration() {
        return fontDecoration;
    }

    public void setFontDecoration(Boolean fontDecoration) {
        this.fontDecoration = fontDecoration;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public Integer getLetterSpacing() {
        return letterSpacing;
    }

    public void setLetterSpacing(Integer letterSpacing) {
        this.letterSpacing = letterSpacing;
    }

    public Boolean getAutoSize() {
        return autoSize;
    }

    public void setAutoSize(Boolean autoSize) {
        this.autoSize = autoSize;
    }

    public Boolean getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Boolean backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldSource() {
        return fieldSource;
    }

    public void setFieldSource(String fieldSource) {
        this.fieldSource = fieldSource;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }
}