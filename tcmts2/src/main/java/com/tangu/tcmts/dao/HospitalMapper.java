package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HospitalMapper {

    HospitalDO getHospitalContent(Integer id);

    Integer getRepeatHospital(HospitalDO hospitalDO);

    List<HospitalListVO> listHospitals(HospitalDTO hospitalDTO);

    int insertHospital(HospitalDO hospitalDO);

    int updateHospital(HospitalDO hospitalDO);

    int updateUseState(HospitalDTO hospitalDTO);

    @Select("SELECT id AS value,hospital_company AS label,hospital_code,initial_code FROM hospital where use_state != 1 ")
    List<PublicDO> getHospitalName();

    @Select("SELECT hospital_code AS value,hospital_company AS label FROM hospital where use_state != 1 ")
    List<SysLookup> getHospitalNameByCode();

    List<HospitalListVO> listHospitalName(HospitalDTO hospitalDTO);
}