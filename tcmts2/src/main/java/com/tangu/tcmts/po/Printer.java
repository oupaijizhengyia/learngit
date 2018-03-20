package com.tangu.tcmts.po;

import lombok.Data;

import java.util.Date;
@Data
public class Printer {
    private Integer id;

    private String printerName;

    private String printerCode;

    private String printerIp;

    private String backgroundImage;

    private Date createTime;

    private Date modTime;
}