package com.tangu.tcmts.util.excel;

import java.io.File;
import java.util.List;

public class ExcelExportVO {
    private List data;
    private List fieldsList;
    private List fieldsLabelList;
    public String title;
    public Object headerList;
    public List footList;
    public String xmlPath;
    public Object[] args;
    private Object item;
    private int decimalPointIndex=2;
    public String fileName;
    public File file;
    public String xmlString;

    public ExcelExportVO() {
    }

    public Object getItem() {
        return this.item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getHeaderList() {
        return this.headerList;
    }

    public void setHeaderList(Object headerList) {
        this.headerList = headerList;
    }

    public List getData() {
        return this.data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public List getFieldsList() {
        return this.fieldsList;
    }

    public void setFieldsList(List fieldsList) {
        this.fieldsList = fieldsList;
    }

    public List getFieldsLabelList() {
        return this.fieldsLabelList;
    }

    public void setFieldsLabelList(List fieldsLabelList) {
        this.fieldsLabelList = fieldsLabelList;
    }

    public List getFootList() {
        return this.footList;
    }

    public void setFootList(List footList) {
        this.footList = footList;
    }

    public String getXmlPath() {
        return this.xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public int getDecimalPointIndex() {
        return this.decimalPointIndex;
    }

    public void setDecimalPointIndex(int decimalPointIndex) {
        this.decimalPointIndex = decimalPointIndex;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
