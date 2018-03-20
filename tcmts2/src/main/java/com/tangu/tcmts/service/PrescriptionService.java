package com.tangu.tcmts.service;

import com.tangu.tcmts.po.Prescription;
import com.tangu.tcmts.po.PrescriptionDetail;

import java.util.List;

public interface PrescriptionService {
    List<Prescription> getPrescriptionList(Prescription prescription);

    List<PrescriptionDetail> getPrescriptionDetailList(Integer id);

    List<Prescription> repeatPrescription(Prescription prescription);

    Integer insertPrescription(Prescription prescription);

    Integer insertPrescriptionDetail(PrescriptionDetail prescriptionDetail);

    Integer updatePrescription(Prescription prescription);

    Integer deletePrescription(Integer id);

    Integer deletePrescriptionDetail(Integer id);
    
    List<Prescription> listPrescriptionByHospital(Integer hospitalId);
}
