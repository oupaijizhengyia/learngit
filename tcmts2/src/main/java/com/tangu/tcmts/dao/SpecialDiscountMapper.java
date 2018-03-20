package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.SettleCompanyDO;
import com.tangu.tcmts.po.SpecialDiscount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SpecialDiscountMapper {
    List<SpecialDiscount> listSpecialDiscounts(Integer id);

    int insertSpecialDiscount(SettleCompanyDO settleCompanyDO);

    int updateSpecialDiscount(SpecialDiscount specialDiscount);

    int deleteSpecialDiscount(SpecialDiscount specialDiscount);

    List<SpecialDiscount> getAllDiscount(SpecialDiscount record);
    
    List<SpecialDiscount> listSpecialDiscountBySettleCompanyId(Integer settleCompanyId);
}