package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.Company;
import com.tangu.tcmts.po.SysConfig;
import com.tangu.tcmts.po.SysLookup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author fenglei
 * @date 10/31/17
 * sys_config， sys_lookup的方法都在这个类里
 */
@Mapper
public interface SysConfigMapper {

    @Select("select name, value from sys_config")
    List<SysConfig> listConfig();

    @Select("select name, value from sys_config where type = #{type}")
    List<SysConfig> listConfigByType(Integer type);

    @Select("select value, label, ext1, ext2 from sys_lookup where code = #{code} order by seq")
    List<SysLookup> listLookupByCode(String code);

    @Select("select ext1, ext2, ext3 from sys_lookup where code = #{code} and value = #{value}")
    SysLookup getExts(SysLookup sysLookup);
    
    @Select("select name, value from sys_config where name = #{name}")
    List<SysConfig> listConfigByName(String name);

    @Select("select id,company_name,company_short_name,company_address,company_tel,company_contact from company")
    Company listCompany();
}
