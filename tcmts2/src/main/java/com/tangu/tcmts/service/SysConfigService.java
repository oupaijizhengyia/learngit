package com.tangu.tcmts.service;

import com.tangu.tcmts.po.*;

import java.util.List;

/**
 * @author fenglei
 * @date 10/31/17
 */
public interface SysConfigService {

    List<SysConfig> listConfig();

    List<SysConfig> listConfigByType(Integer type);

    List<SysLookup> listLookupByCode(String code);

    SysLookup getExts(SysLookup sysLookup);

    Company listCompany();

}
