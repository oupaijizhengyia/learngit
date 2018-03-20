package com.tangu.tcmts.service.imp;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangu.common.util.PinyinConverter;
import com.tangu.tcmts.dao.MedicineRelationMapper;
import com.tangu.tcmts.po.MedicineRelationDO;
import com.tangu.tcmts.po.MedicineRelationDTO;
import com.tangu.tcmts.service.MedicineRelationService;

/**
 * @author:huangyifan
 * @Description:中药规格管理接口实现
 * @Date: create in 14：07 2017/11/7
 */
@Service
public class MedicineRelationServiceImpl implements MedicineRelationService {
	@Autowired
	MedicineRelationMapper medicineRelationMapper;

	@Override
	public Integer deleteMedicineRelationById(MedicineRelationDTO medicineRelationDTO) {
		return medicineRelationMapper.deleteMedicineRelationById(medicineRelationDTO);
	}

	@Override
	public Integer updateMedicineRelationById(MedicineRelationDO medicineRelationDO) {
		return medicineRelationMapper.updateMedicineRelationById(medicineRelationDO);
	}

	@Override
	public Integer insertMedicineRelation(List<MedicineRelationDO> list) {
		// 获取药品名称拼音
		list.stream().filter(m -> StringUtils.isNotBlank(m.getHospitalMedicineName()))
		.forEach(m -> m.setInitialCode(PinyinConverter.String2Alpha(m.getHospitalMedicineName())));
		return medicineRelationMapper.insertMedicineRelation(list);
	}

	@Override
	public List<MedicineRelationDO> listMedicineRelation(MedicineRelationDTO medicineRelationDTO) {
		return medicineRelationMapper.listMedicineRelation(medicineRelationDTO);
	}

	@Override
	public List<MedicineRelationDO> getMedicineRelationByName(MedicineRelationDO medicineRelationDO) {
		return medicineRelationMapper.getMedicineRelationByName(medicineRelationDO);
	}

	@Override
	public List<MedicineRelationDO> listMedicineRelationAll() {
		return medicineRelationMapper.listMedicineRelationAll();
	}

	@Override
	public List<MedicineRelationDO> getMnemonicCode() {
		return medicineRelationMapper.getMnemonicCode();
	}

}
