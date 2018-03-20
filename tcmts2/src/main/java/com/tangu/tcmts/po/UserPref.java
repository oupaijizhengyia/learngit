package com.tangu.tcmts.po;

import lombok.Data;

/**
 * @author fenglei
 * @date 11/15/17
 */
@Data
public class UserPref {
    private Integer userId;
    private String type;
    private Object value;
}
