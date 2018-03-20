package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tangu.tcmts.po.PriceTempletDTO;
import com.tangu.tcmts.po.PriceTempletDetailDTO;

@Mapper
public interface PriceTempletDetailMapper {
  
    //根据模板id 删除
    @Delete("delete from price_templet_detail where templet_id = #{templetId,jdbcType=INTEGER}")
    int deleteByTempletId(PriceTempletDTO record);
    
    int insertSelective(PriceTempletDetailDTO record);
    
    @Select("select templet_id, medicine_id, price_start, "
        + "trade_price, retail_price from price_templet_detail "
        + "where id = #{templetDetailId} and medicine_last = 1")
    PriceTempletDetailDTO selectById(PriceTempletDetailDTO record);
    
    //根据模板id,药品id获取价格模板详情
    List<PriceTempletDetailDTO> selectByTemIdAndMedId(PriceTempletDetailDTO record);
    
    //单条更新
    int updateByIdSelective(PriceTempletDetailDTO record);
    
    //批量更新
    int updateListById(List<PriceTempletDetailDTO> record);

    //插入最新记录
    int insertList(List<PriceTempletDetailDTO> oldInsertRecord);
    
    List<PriceTempletDetailDTO> listPriceByTemIdsOrMedIds(PriceTempletDetailDTO record);
    PriceTempletDetailDTO getPriceByTemIdAndMedId(PriceTempletDetailDTO record);

    @Select("select ptd.id as id, ptd.id as templetDetailId, price_start, "
        + "trade_price, retail_price, ptd.modify_time, mod_user, "
        + "employee_name as modifyName, medicine_last "
        + "from price_templet_detail ptd  "
        + "left join employee e on e.id = ptd.mod_user "
        + "where templet_id = #{templetId} and medicine_id = #{medicineId} "
        + "order by price_start desc")
    List<PriceTempletDetailDTO> listPriceTempleByTemidAndMedid(
        PriceTempletDetailDTO priceTempletDetailDTO);
    
    @Delete("delete from price_templet_detail "
        + "where id = #{templetDetailId}")
    Integer deleteByTempletDetailId(
        PriceTempletDetailDTO priceTempletDetailDTO);
    
    @Select("select count(*) from price_templet_detail "
        + "where id = #{templetDetailId}")
    Integer selectByTempletDetailId(
        PriceTempletDetailDTO priceTempletDetailDTO);
    
    @Update("UPDATE price_templet_detail SET "
        + "medicine_last = 1 WHERE id in ( "
        + "select id from ( "
        + "SELECT id FROM price_templet_detail "
        + "WHERE medicine_id = #{medicineId} and templet_id = #{templetId} and medicine_last = 0 "
        + "ORDER BY create_time desc LIMIT 1 ) "
        + "b)")
    Integer updatePriceTempletDetailLast(PriceTempletDetailDTO priceTempletDetailDTO);
}