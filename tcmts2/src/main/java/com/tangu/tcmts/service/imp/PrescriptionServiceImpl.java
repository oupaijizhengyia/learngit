package com.tangu.tcmts.service.imp;

import com.tangu.tcmts.dao.PrescriptionDetailMapper;
import com.tangu.tcmts.dao.PrescriptionMapper;
import com.tangu.tcmts.po.Prescription;
import com.tangu.tcmts.po.PrescriptionDetail;
import com.tangu.tcmts.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService{
    @Autowired
    private PrescriptionMapper prescriptionMapper;
    @Autowired
    private PrescriptionDetailMapper prescriptionDetailMapper;

    @Override
    public List<Prescription> getPrescriptionList(Prescription prescription) {
        return prescriptionMapper.getPrescriptionList(prescription);
    }

    @Override
    public List<PrescriptionDetail> getPrescriptionDetailList(Integer id) {
        return prescriptionDetailMapper.getPrescriptionDetailList(id);
    }

    @Override
    public List<Prescription> repeatPrescription(Prescription prescription) {
        return prescriptionMapper.repeatPrescription(prescription);
    }

    @Override
    public Integer insertPrescription(Prescription prescription) {
        return prescriptionMapper.insertPrescription(prescription);
    }

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public Integer insertPrescriptionDetail(PrescriptionDetail prescriptionDetail) {
        Integer insertPrescriptionDetail = prescriptionDetailMapper.insertPrescriptionDetail(prescriptionDetail);
        if(insertPrescriptionDetail.toString() != null){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer updatePrescription(Prescription prescription) {
        return prescriptionMapper.updatePrescription(prescription);
    }

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public Integer deletePrescription(Integer id) {
        Integer deletePrescription =prescriptionMapper.deletePrescription(id);
        Integer deletePrescriptionDetail =prescriptionDetailMapper.deletePrescriptionDetail(id);
        if(deletePrescription.toString() != null && deletePrescriptionDetail.toString() != null){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer deletePrescriptionDetail(Integer id) {
        return prescriptionDetailMapper.deletePrescriptionDetail(id);
    }

	@Override
	public List<Prescription> listPrescriptionByHospital(Integer hospitalId) {
		// TODO Auto-generated method stub
		return prescriptionMapper.listPrescriptionByHospital(hospitalId);
	}


}
