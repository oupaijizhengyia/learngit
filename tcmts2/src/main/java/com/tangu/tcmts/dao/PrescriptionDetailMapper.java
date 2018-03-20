package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.PrescriptionDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrescriptionDetailMapper {

    List<PrescriptionDetail> getPrescriptionDetailList(Integer id);

    Integer insertPrescriptionDetail(PrescriptionDetail prescriptionDetail);

    @Delete(" delete from prescription_detail where prescription_id = #{id}")
    Integer deletePrescriptionDetail(Integer id);
}