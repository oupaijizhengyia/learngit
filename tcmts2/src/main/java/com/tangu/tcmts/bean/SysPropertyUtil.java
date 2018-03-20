package com.tangu.tcmts.bean;

import com.tangu.tcmts.po.SysConfig;
import com.tangu.tcmts.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fenglei
 * @date 11/1/17
 */
@Component
public class SysPropertyUtil {

    @Autowired
    SysConfigService sysConfigService;

    /**
     * 跟据传入的key获取sysConfig里对应的值
     * @param key
     * @param defalutValue 默认值
     * @return String
     */
    public String getProperty(String key, String defalutValue){
        List<SysConfig> list = sysConfigService.listConfig();
        return list.stream().filter(sysConfig -> key.equals(sysConfig.getName()))
                .map(s-> s.getValue())
                .findFirst()
                .orElse(defalutValue);
    }

    /**
     * 跟据传入的key获取sysConfig里对应的值
     * @param key
     * @return String
     */
    public String getProperty(String key){
        return this.getProperty(key,null);
    }

}
