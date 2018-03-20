package com.tangu.tcmts.bean;

import com.tangu.security.JwtUserTool;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author fenglei
 * @date 11/2/17
 * 根据用户当前的账套生成对应的key
 */
@Component
public class CacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object o, Method method, Object... objects) {
        String tenant = JwtUserTool.getTenant();
        Object key = Arrays.stream(objects).reduce((x, y) -> x.toString().concat("-").concat(y.toString()));
        return tenant+key;
    }
}
