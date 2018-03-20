package com.tangu.tcmts.po;

import lombok.Data;

import java.util.List;
/**
 * 打印对象
 * Created by djl on 2017/11/3.
 *
 */
@Data
public class PrintPageObj {
    private Integer id;
    private Integer pageWidth;
    private Integer pageHeight;
    private Integer imageWidth;
    private Integer imageHeight;
    private Integer printerId;
    private Integer repeat;
    private String backgroundImage;
    private List printItemList;
}