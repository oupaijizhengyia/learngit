package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.Prescription;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrescriptionMapper {

    List<Prescription> getPrescriptionList(Prescription prescription);

    List<Prescription> repeatPrescription(Prescription prescription);

    Integer insertPrescription(Prescription prescription);

    Integer updatePrescription(Prescription prescription);

    @Delete("delete from prescription where id=#{id}")
    Integer deletePrescription(Integer id);
    
    List<Prescription> listPrescriptionByHospital(Integer hospitalId);
}