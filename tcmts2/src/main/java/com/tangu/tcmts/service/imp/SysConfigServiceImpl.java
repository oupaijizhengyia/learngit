package com.tangu.tcmts.service.imp;

import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.dao.SysConfigMapper;
import com.tangu.tcmts.po.Company;
import com.tangu.tcmts.po.SysConfig;
import com.tangu.tcmts.po.SysLookup;
import com.tangu.tcmts.service.SysConfigService;
import com.tangu.tcmts.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author fenglei
 * @date 10/31/17
 */
@Service
@Slf4j
public class SysConfigServiceImpl implements SysConfigService {
    @Autowired
    SysConfigMapper sysConfigMapper;

    Object a = JwtUserTool.getTenant();

    @Override
    @Cacheable(value = Constants.CACHE_KEY_ALLCONFIG, keyGenerator = "cacheKeyGenerator")
    public List<SysConfig> listConfig() {
        log.info("-------------load listConfig from database" );
        return sysConfigMapper.listConfig();
    }

    @Override
    @Cacheable(value = Constants.CACHE_KEY_CONFIG, keyGenerator = "cacheKeyGenerator")
    public List<SysConfig> listConfigByType(Integer type) {
//        log.info("-------------load listConfigByType from database for type: "+type);
        return sysConfigMapper.listConfigByType(type);
    }

    @Override
    @Cacheable(value = Constants.CACHE_KEY_LOOKUP, keyGenerator = "cacheKeyGenerator")
    public List<SysLookup> listLookupByCode(String code) {
//        log.info("-------------load listLookupByCode from database for code: "+code);
        return sysConfigMapper.listLookupByCode(code);
    }

    @Override
    public SysLookup getExts(SysLookup sysLookup) {
        return sysConfigMapper.getExts(sysLookup);
    }

    @Override
    @Cacheable(value = Constants.CACHE_KEY_COMPANY,keyGenerator = "cacheKeyGenerator")
    public Company listCompany() {
        return sysConfigMapper.listCompany();
    }

}
