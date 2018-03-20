package com.tangu.tcmts.service;

import com.tangu.tcmts.po.*;

import java.util.List;

/**
 * @author:yinghuaxu
 * @Description:结算方接口
 * @Date: create in 13:43 2017/10/20
 */
public interface SettleCompanyService {

    SettleCompanyDO getSettleCompanyContent(Integer id);

    List<SettleCompanyDO> listSettleCompany(SettleCompanyDTO settleCompanyDTO);

    List<SettleCompanyDO> getRepeatSettleCompanyName(SettleCompanyDO settleCompanyDO);

    List<SettleCompanyHistory> listHistoryRecords(Integer id);

    List<SpecialDiscount> listSpecialDiscounts(Integer id);

    int insertSettleCompany(SettleCompanyDO settleCompanyDO);

    int insertSpecialDiscountList(SettleCompanyDO settleCompanyDO);

    int insertSettleCompanyHistory(SettleCompanyDO settleCompanyDO);

    int updateSettleCompany(SettleCompanyDO settleCompanyDO);

    int updateSpecialDiscount(List<SpecialDiscount> specialDiscountList);

    int updateSettleCompanyState(SettleCompanyDTO settleCompany);

    int deleteSpecialDiscount(SpecialDiscount specialDiscount);

    List<PublicDO> getSettleCompanyName();

    List<SpecialDiscount> getAllDiscount(SpecialDiscount record);
}
