package com.tangu.tcmts.service.imp;

import com.tangu.tcmts.dao.SettleCompanyHistoryMapper;
import com.tangu.tcmts.dao.SettleCompanyMapper;
import com.tangu.tcmts.dao.SpecialDiscountMapper;
import com.tangu.tcmts.po.*;
import com.tangu.tcmts.service.SettleCompanyService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:yinghuaxu
 * @Description:结算方接口实现类
 * @Date: create in 13:45 2017/10/20
 */
@Service
public class SettleCompanyServiceImpl implements SettleCompanyService {
    @Autowired
    private SettleCompanyMapper settleCompanyMapper;
    @Autowired
    private SettleCompanyHistoryMapper settleCompanyHistoryMapper;
    @Autowired
    private SpecialDiscountMapper specialDiscountMapper;

    @Override
    public SettleCompanyDO getSettleCompanyContent(Integer id) {
        SettleCompanyDO settleCompany=settleCompanyMapper.getSettleCompanyContent(id);
        settleCompany.setSpecialDiscountList(specialDiscountMapper.listSpecialDiscounts(settleCompany.getId()));
        return settleCompany;
    }

    @Override
    public List<SettleCompanyDO> listSettleCompany(SettleCompanyDTO settleCompanyDTO) {
        return settleCompanyMapper.listSettleCompanys(settleCompanyDTO);
    }

    @Override
    public List<SettleCompanyDO> getRepeatSettleCompanyName(SettleCompanyDO settleCompanyDO) {
        return settleCompanyMapper.getRepeatSettleCompanyName(settleCompanyDO);
    }

    @Override
    public List<SettleCompanyHistory> listHistoryRecords(Integer id) {
        return settleCompanyHistoryMapper.listHistoryRecords(id);
    }

    @Override
    public List<SpecialDiscount> listSpecialDiscounts(Integer id) {
        return specialDiscountMapper.listSpecialDiscounts(id);
    }

    @Override
    public int insertSettleCompany(SettleCompanyDO settleCompanyDO) {
        return settleCompanyMapper.insertSettleCompany(settleCompanyDO);
    }

    @Override
    public int insertSpecialDiscountList(SettleCompanyDO settleCompanyDO) {
        return specialDiscountMapper.insertSpecialDiscount(settleCompanyDO);
    }

    @Override
    public int insertSettleCompanyHistory(SettleCompanyDO settleCompanyDO) {
        return settleCompanyHistoryMapper.insertHistoryRecords(settleCompanyDO);
    }

    @Override
    public int updateSettleCompany(SettleCompanyDO settleCompanyDO) {
        return settleCompanyMapper.updateSettleCompany(settleCompanyDO);
    }

    @Override
    public int updateSpecialDiscount(List<SpecialDiscount> specialDiscountList) {
        int result=0;
        if (CollectionUtils.isNotEmpty(specialDiscountList)){
            for (SpecialDiscount specialDiscount:specialDiscountList) {
                specialDiscountMapper.updateSpecialDiscount(specialDiscount);
            }
            result=1;
        }
        return result;
    }

    @Override
    public int updateSettleCompanyState(SettleCompanyDTO settleCompany) {
        return settleCompanyMapper.updateUseState(settleCompany);
    }

    @Override
    public int deleteSpecialDiscount(SpecialDiscount specialDiscount) {
        return specialDiscountMapper.deleteSpecialDiscount(specialDiscount);
    }

    @Override
    public List<PublicDO> getSettleCompanyName() {
        return settleCompanyMapper.getSettleCompanyName();
    }

    @Override
    public List<SpecialDiscount> getAllDiscount(SpecialDiscount record){
        return specialDiscountMapper.getAllDiscount(record);
    }
}
