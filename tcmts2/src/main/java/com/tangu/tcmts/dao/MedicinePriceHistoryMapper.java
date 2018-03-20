package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tangu.tcmts.po.MedicinePriceHistoryDO;
import com.tangu.tcmts.po.MedicinePriceHistoryDTO;

/**
 * @author:huangyifan
 * @Description:中药调价记录Mapper
 * @Date: create in 15：27 2017/11/3
 */
@Mapper
public interface MedicinePriceHistoryMapper {
  List<MedicinePriceHistoryDO> listHistory(MedicinePriceHistoryDTO medicinePriceHistoryDTO);
}