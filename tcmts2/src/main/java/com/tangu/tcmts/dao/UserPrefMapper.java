package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.UserPref;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author fenglei
 * @date 11/15/17
 */

@Mapper
public interface UserPrefMapper {

    @Select("SELECT user_id, type, value FROM user_pref where user_id = #{userId}")
    List<UserPref> listUserPref(Integer userId);

    @Insert("REPLACE into `user_pref` (user_id, type, value) values (#{userId}, #{type}, #{value})")
    int updateUserPref(UserPref userPref);
}
