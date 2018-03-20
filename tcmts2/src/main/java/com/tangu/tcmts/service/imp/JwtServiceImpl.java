package com.tangu.tcmts.service.imp;

import com.tangu.security.JwtService;
import com.tangu.security.JwtUser;
import com.tangu.tcmts.dao.EmployeeMapper;
import com.tangu.tcmts.po.Employee;
import com.tangu.tcmts.util.CommonUtil;
import com.tangu.tcmts.util.exception.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 实现security里的接口
 * @author fenglei on 8/29/17.
 */
@Service
public class JwtServiceImpl implements JwtService {
    @Autowired
    EmployeeMapper employeeMapper;

    private final Integer NOT_OPERATOR = 0;
    @Override
    public UserDetails auth(String username, String password) throws UsernameNotFoundException, PermissionDeniedException {
//        Employee user = new Employee();
//        user.setUserCode(username);
//        user.setPassword(MD5.encrypt(password));
//        user = employeeMapper.login(user);
//        if(user == null){
//            throw new UsernameNotFoundException(String.format("No user found with useranme '%s' password '%s'",username, password));
//        }
//        UserDetails userDetails = create(user);
//        return userDetails;
        return auth("",username,password);
    }

    @Override
    public UserDetails auth(String tenant, String username, String password) throws UsernameNotFoundException,PermissionDeniedException{
        if(password == null){
            password = "";
        }
        Employee user = new Employee();
        user.setEmployeeName(username);
        user.setEmployeePassword(CommonUtil.DoubleMD5(password));
        user = employeeMapper.login(user);
        if(user == null ){
            throw new UsernameNotFoundException(String.format("No user found with useranme '%s' password '%s'",username, password));
        }
        if (NOT_OPERATOR.equals(user.getIsOperator())){
            throw new PermissionDeniedException("PermissionDenied");
        }
        UserDetails userDetails = create(user, tenant);
        return userDetails;
    }

    //TODO implement it later
    @Override
    public String refresh(String oldtoken){
        return oldtoken;
    }

//    private static JwtUser create(Employee employee){
//        return new JwtUser( employee.getId().longValue(),
//                employee.getUserCode(),
//                employee.getPassword(),
//                mapToGrantedAuthorities(GRENT_PREFEX+employee.getRole()),
//                true,
//                new Date());
//    }
//
//    private static List<GrantedAuthority> mapToGrantedAuthorities(String role){
//        return Arrays.asList(new SimpleGrantedAuthority(role));
//    }

    private static JwtUser create(Employee employee, String tenant){
        return new JwtUser( employee.getId().longValue(),
                tenant,
                employee.getEmployeeCode(),
                employee.getEmployeePassword(),
                ""+employee.getRoleId(),
                true,
                new Date());
    }
}
