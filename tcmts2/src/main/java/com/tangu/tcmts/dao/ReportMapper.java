package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.Report;
import com.tangu.tcmts.po.ReportDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {
    List<Report> listReportMedicineByMedicine(ReportDTO reportDTO);

    List<Report> listReportMedicineBySettle(ReportDTO reportDTO);

    List<Report> listReportMedicineByHospital(ReportDTO reportDTO);
}
