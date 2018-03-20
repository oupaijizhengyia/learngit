package com.tangu.tcmts.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExportXmlMapper {

    @Select("select xml_value from export_xml where xml_code = #{xmlCode}")
    String getXmlValue(String xmlCode);
}
