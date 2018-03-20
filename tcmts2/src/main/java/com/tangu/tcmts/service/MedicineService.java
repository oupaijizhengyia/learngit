package com.tangu.tcmts.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tangu.tcmts.po.MedicineDO;
import com.tangu.tcmts.po.MedicineDTO;
import com.tangu.tcmts.po.MedicineStandardDO;
import com.tangu.tcmts.po.MedicineStandardDTO;
import com.tangu.tcmts.po.SysLookup;

/**
 * @author:huangyifan
 * @Description:中药管理service
 * @Date: create in 11：49 2017/10/31
 */
public interface MedicineService {
	List<MedicineDO> listMedicine(MedicineDTO medicineDTO);
	
	List<SysLookup> listSunMedicineCode();
	
	Object  insertMedicine(MedicineDO medicineDO);
	
	MedicineDO getMedicineById(MedicineDTO medicineDTO);
	
	Object update(MedicineDO medicineDO);
	
	Integer updateState(MedicineDTO medicineDTO);
	
	List<MedicineDO> listMedicineForLookup();
	
	List<MedicineStandardDO> listLoadMedicine(MedicineStandardDTO medicineStandardDTO);
	List<MedicineStandardDO> listNotLoadMedicine(MedicineStandardDTO medicineStandardDTO);
	
	Integer addMedicines(List<MedicineDO> list);
	
	void export(List<?> list, HttpServletRequest req, HttpServletResponse resp,String xml) throws UnsupportedEncodingException, ServletException, IOException;//导出处方列表

	Integer getMedicineByName(MedicineDO medicineDO);
	
}
