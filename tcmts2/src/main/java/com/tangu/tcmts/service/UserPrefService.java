package com.tangu.tcmts.service;

import com.tangu.tcmts.po.UserPref;

import java.util.List;

/**
 * @author fenglei
 * @date 11/15/17
 */
public interface UserPrefService {
    List<UserPref> listUserPref(Integer user_id);

    int updateUsrePref(UserPref userPref);
}
