package com.tangu.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tangu.tcmts.util.exception.PermissionDeniedException;



/**
 * @author fenglei108 on 8/29/17.
 */
public interface JwtService {
//    User register(User userToAdd);

    /**
     * 以用户名，密码登录
     * @param username
     * @param password
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails auth(String username, String password) throws UsernameNotFoundException, PermissionDeniedException;

    /**
     * 以账套，用户名，密码登录
     * @param tenant
     * @param username
     * @param password
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails auth(String tenant, String username, String password) throws UsernameNotFoundException, PermissionDeniedException;

    /**
     * 刷新一个旧的token， 目前还没有用到
     * @param oldToken
     * @return
     */
    String refresh(String oldToken);
}
