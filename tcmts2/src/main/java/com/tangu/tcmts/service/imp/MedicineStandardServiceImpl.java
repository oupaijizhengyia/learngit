package com.tangu.tcmts.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangu.tcmts.dao.MedicineStandardMapper;
import com.tangu.tcmts.po.MedicineDO;
import com.tangu.tcmts.po.MedicineDTO;
import com.tangu.tcmts.po.MedicineStandardDO;
import com.tangu.tcmts.service.MedicineStandardService;

/**
 * @author:huangyifan
 * @Description:煎药机管理接口实现类
 * @Date: create in 10:48 2017/10/24
 */
@Service
public class MedicineStandardServiceImpl implements MedicineStandardService {
	@Autowired
	MedicineStandardMapper medicineStandardMapper;
	@Override
	public Integer listStandardByCode(MedicineDO medicineDO) {
		// TODO Auto-generated method stub
		return medicineStandardMapper.listStandardByCode(medicineDO);
	}
	@Override
	public List<MedicineStandardDO> listMedicineStandardByMedicineId(List<MedicineDTO> list) {
		// TODO Auto-generated method stub
		return medicineStandardMapper.listMedicineStandardByMedicineId(list);
	}
	@Override
	public MedicineStandardDO getStandardById(MedicineStandardDO medicineStandardDO) {
		// TODO Auto-generated method stub
		return medicineStandardMapper.getStandardById(medicineStandardDO);
	}
	@Override
    public MedicineStandardDO getMedicineByNumber(MedicineStandardDO medicineStandardDO) {
		// TODO Auto-generated method stub
		return medicineStandardMapper.getMedicineByNumber(medicineStandardDO);
	}

}
