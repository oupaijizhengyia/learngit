package com.tangu.tcmts.service;

import java.util.List;

import com.tangu.tcmts.po.MedicineRelationDO;
import com.tangu.tcmts.po.MedicineRelationDTO;

/**
 * @author:huangyifan
 * @Description:中药规格管理接口
 * @Date: create in 14：07 2017/11/7
 */
public interface MedicineRelationService {
	Integer deleteMedicineRelationById(MedicineRelationDTO medicineRelationDTO);
	Integer updateMedicineRelationById(MedicineRelationDO medicineRelationDO);
	
	Integer insertMedicineRelation(List<MedicineRelationDO> list);
	List<MedicineRelationDO> listMedicineRelation(MedicineRelationDTO medicineRelationDTO);
	
	List<MedicineRelationDO> getMedicineRelationByName(MedicineRelationDO medicineRelationDO);
	
	List<MedicineRelationDO> listMedicineRelationAll();
	List<MedicineRelationDO>  getMnemonicCode();
}
