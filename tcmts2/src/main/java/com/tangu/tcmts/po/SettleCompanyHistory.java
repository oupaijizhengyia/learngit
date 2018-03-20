package com.tangu.tcmts.po;

import lombok.Data;

import java.util.Date;
@Data
public class SettleCompanyHistory {
    private Integer id;

    private Integer modUser;

    private String modComment;

    private String modUserName;

    private Integer settleCompanyId;

    private Date modTime;


}