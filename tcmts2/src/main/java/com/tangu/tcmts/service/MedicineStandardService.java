package com.tangu.tcmts.service;

import java.util.List;

import com.tangu.tcmts.po.MedicineDO;
import com.tangu.tcmts.po.MedicineDTO;
import com.tangu.tcmts.po.MedicineStandardDO;

/**
 * @author:huangyifan
 * @Description:中药管理规格接口
 * @Date: create in 9:48 2017/11/14
 */
public interface MedicineStandardService {
	Integer listStandardByCode(MedicineDO medicineDO);
	
	List<MedicineStandardDO> listMedicineStandardByMedicineId(List<MedicineDTO> list);
	
	MedicineStandardDO getStandardById(MedicineStandardDO medicineStandardDO);
	MedicineStandardDO getMedicineByNumber(MedicineStandardDO medicineStandardDO);
}
