package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.tangu.tcmts.po.MedicineDO;
import com.tangu.tcmts.po.MedicineDTO;
import com.tangu.tcmts.po.MedicineStandardDO;
import com.tangu.tcmts.po.MedicineStandardDTO;

/**
 * @author:huangyifan
 * @Description:中药规格管理Mapper
 * @Date: create in 15：58 2017/10/31
 */
@Mapper
public interface MedicineStandardMapper {
	Integer addMedicineStandard(List<MedicineStandardDO> list);
	Integer updateMedicineStandard(List<MedicineStandardDO> list);
	
	List<MedicineStandardDO> listLoadMedicine(MedicineStandardDTO medicineStandardDTO);
	List<MedicineStandardDO> listNotLoadMedicine(MedicineStandardDTO medicineStandardDTO);
	
	@Select("SELECT id,medicine_id,standard_name,standard_code FROM `medicine_standard` WHERE medicine_id=#{id}")
	List<MedicineStandardDO> listStandardByMedicineId(MedicineDO medicineDO);
	
	Integer deleteMedicineStandard(List<MedicineStandardDO> list);
	
	Integer countWarehouseStandradById(List<MedicineStandardDO> list);
	
	Integer listStandardByCode(MedicineDO medicineDO);
	
	List<MedicineStandardDO> listMedicineStandardByMedicineId(List<MedicineDTO> list);
	
	MedicineStandardDO getStandardById(MedicineStandardDO medicineStandardDO);
	MedicineStandardDO getMedicineByNumber(MedicineStandardDO medicineStandardDO);
	
}