package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.SettleCompanyDO;
import com.tangu.tcmts.po.SettleCompanyHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SettleCompanyHistoryMapper {

    List<SettleCompanyHistory> listHistoryRecords(Integer id);

    int insertHistoryRecords(SettleCompanyDO settleCompanyDO);
}