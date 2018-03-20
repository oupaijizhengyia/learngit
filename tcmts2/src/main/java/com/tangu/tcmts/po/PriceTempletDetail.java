package com.tangu.tcmts.po;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class PriceTempletDetail {
    private Integer id;

    private Integer templetId;

    private Integer medicineId;

    private Date priceStart;

    private Date priceEnd;

    private BigDecimal tradePrice;

    private BigDecimal retailPrice;

    private Boolean medicineLast;

    private Date createTime;

}