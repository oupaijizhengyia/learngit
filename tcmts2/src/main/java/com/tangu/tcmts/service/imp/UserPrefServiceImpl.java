package com.tangu.tcmts.service.imp;

import com.tangu.tcmts.dao.UserPrefMapper;
import com.tangu.tcmts.po.UserPref;
import com.tangu.tcmts.service.UserPrefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fenglei
 * @date 11/15/17
 */
@Service
public class UserPrefServiceImpl implements UserPrefService {
    @Autowired
    private UserPrefMapper userPrefMapper;

    @Override
    public List<UserPref> listUserPref(Integer user_id) {
        return userPrefMapper.listUserPref(user_id);
    }

    @Override
    public int updateUsrePref(UserPref userPref) {
        return userPrefMapper.updateUserPref(userPref);
    }
}
