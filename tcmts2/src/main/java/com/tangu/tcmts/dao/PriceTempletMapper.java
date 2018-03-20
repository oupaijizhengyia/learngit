package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tangu.tcmts.po.DropDown;
import com.tangu.tcmts.po.PriceTempletDTO;

@Mapper
public interface PriceTempletMapper {
    
    // 获取模板类型 id,name
    @Select("select id AS templetId, templet_name from price_templet")
    List<PriceTempletDTO> getPriceTempletList();
    
    @Select("select id AS value, templet_name AS label from price_templet")
    List<DropDown> getPriceTempletListDropDown();
    
    int deleteByPrimaryKey(Integer id);

    int insertSelective(PriceTempletDTO record);

    int updateByPrimaryKeySelective(PriceTempletDTO record);

}