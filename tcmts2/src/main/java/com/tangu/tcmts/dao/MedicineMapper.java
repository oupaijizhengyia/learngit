package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tangu.tcmts.po.MedicineDO;
import com.tangu.tcmts.po.MedicineDTO;
import com.tangu.tcmts.po.SysLookup;

/**
 * @author:huangyifan
 * @Description:中药管理Mapper
 * @Date: create in 11：20 2017/10/31
 */
@Mapper
public interface MedicineMapper {
	List<MedicineDO> listMedicine(MedicineDTO medicineDTO);
	
	List<SysLookup> listSunMedicineCode();
	
	Integer addMedicine(MedicineDO medicineDO);
	
	MedicineDO getMedicineById(MedicineDTO medicineDTO);
	
	Integer updateMedicine(MedicineDO medicineDO);
	
	Integer updateState(MedicineDTO medicineDTO);

	@Select("SELECT id,ifNULL(medicine_code, '') as medicine_code, ifNULL(medicine_name, '') as medicine_name,"
			+ " ifNULL(initial_code,'') as initial_code, ifNull(mnemonic_code,'') as mnemonic_code , standard "
			+ " ,unit_type, special_boil_type "
			+ " FROM medicine where use_state != 1 ")
	List<MedicineDO> listMedicineForLookup();
	
	Integer addMedicines(List<MedicineDO> list);
	
	Integer getMedicineByName(MedicineDO medicineDO);
	
	List<MedicineDO> findXjMedicineList(MedicineDO medicine);
	
}