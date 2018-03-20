package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.PublicDO;
import com.tangu.tcmts.po.SettleCompanyDO;
import com.tangu.tcmts.po.SettleCompanyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @author:yinghuaxu
 * @Description:结算方Mapper
 * @Date: create in 10:24 2017/10/20
 */
@Mapper
public interface SettleCompanyMapper {
    int insertSettleCompany(SettleCompanyDO settleCompanyDO);

    List<SettleCompanyDO> listSettleCompanys(SettleCompanyDTO settleCompany);

    List<SettleCompanyDO> getRepeatSettleCompanyName(SettleCompanyDO settleCompanyDO);

    SettleCompanyDO getSettleCompanyContent(Integer id);

    int updateSettleCompany(SettleCompanyDO record);

    int updateUseState(SettleCompanyDTO settleCompany);

    @Select("SELECT id AS value,settle_company_name AS label FROM settle_company Where use_state != 1")
    List<PublicDO> getSettleCompanyName();
    
    @Select("select price_type_id from settle_company")
    @ResultType(java.util.Set.class)
    Set<Integer> getPriceTypeId();
}