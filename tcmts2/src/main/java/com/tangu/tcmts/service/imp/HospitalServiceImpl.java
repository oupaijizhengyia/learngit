package com.tangu.tcmts.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangu.tcmts.dao.HospitalMapper;
import com.tangu.tcmts.dao.SpecialDiscountMapper;
import com.tangu.tcmts.po.HospitalDO;
import com.tangu.tcmts.po.HospitalDTO;
import com.tangu.tcmts.po.HospitalListVO;
import com.tangu.tcmts.po.PublicDO;
import com.tangu.tcmts.po.SysLookup;
import com.tangu.tcmts.service.HospitalService;
/**
 * @author:yinghuaxu
 * @Description:医院管理
 * @Date: create in 16:37 2017/10/25
 */
@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalMapper hospitalMapper;
    @Autowired
    SpecialDiscountMapper specialDiscountMapper;

    @Override
    public List<HospitalListVO> listHospitals(HospitalDTO hospitalDTO) {
        return hospitalMapper.listHospitals(hospitalDTO);
    }

    @Override
    public HospitalDO getHospitalContent(Integer id) {
    	HospitalDO hs = hospitalMapper.getHospitalContent(id);
    	if (hs != null && hs.getSettleCompanyId() != null) {
    		hs.setSpecialDiscountList(specialDiscountMapper.listSpecialDiscountBySettleCompanyId(hs.getSettleCompanyId()));
    	}
        return hs;
    }

    @Override
    public Integer getRepeatHospital(HospitalDO hospitalDO) {
        return hospitalMapper.getRepeatHospital(hospitalDO);
    }


    @Override
    public int insertHospital(HospitalDO hospitalDO) {
        return hospitalMapper.insertHospital(hospitalDO);
    }

    @Override
    public int updateHospital(HospitalDO hospitalDO) {
        return hospitalMapper.updateHospital(hospitalDO);
    }

    @Override
    public int updateState(HospitalDTO hospitalDTO) {
        return hospitalMapper.updateUseState(hospitalDTO);
    }


    @Override
    public List<PublicDO> getHospitalName() {
        return hospitalMapper.getHospitalName();
    }

    @Override
    public List<SysLookup> getHospitalNameByCode() {
        return hospitalMapper.getHospitalNameByCode();
    }

    @Override
	public List<HospitalListVO> listHospitalName(HospitalDTO hospitalDTO) {
		return hospitalMapper.listHospitalName(hospitalDTO);
	}
}
