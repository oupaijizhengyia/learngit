package com.tangu.tcmts.service;

import com.tangu.tcmts.po.*;

import java.util.List;

public interface HospitalService {
    List<HospitalListVO> listHospitals(HospitalDTO hospitalDTO);

    HospitalDO getHospitalContent(Integer id);

    Integer getRepeatHospital(HospitalDO hospitalDO);

    int insertHospital(HospitalDO hospitalDO);

    int updateHospital(HospitalDO hospitalDO);

    int updateState(HospitalDTO hospitalDTO);

    List<PublicDO> getHospitalName();
    List<SysLookup> getHospitalNameByCode();
    
    List<HospitalListVO> listHospitalName(HospitalDTO hospitalDTO);
}
