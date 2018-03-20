package com.tangu.tcmts.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tangu.tcmts.po.MedicineDO;
import com.tangu.tcmts.po.MedicineRelationDO;

public class MedicineUtil {

	static final String UNIT = "公斤";
	static final Integer ZERO = 0;
	static final Integer ONE = 1;
	private final static String XLS = "xls";
	private final static String XLSX = "xlsx";

	public static Integer getMedicineUnit(String str) {
		if (UNIT.equals(str)) {
			return ONE;
		}
		return ZERO;
	}

	public static String ifRepeat(List<MedicineRelationDO> list) {
		Map<String, String> map = new HashMap<>();
		Map<String, String> map1 = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			// get
			if (map.get(list.get(i).getHospitalMedicineName()) != null) {
				return "医院药品名称：" + list.get(i).getMedicineName() + "不允许重复！";
			}
			if (map1.get(list.get(i).getHospitalMedicineCode()) != null) {
				return "医院药品编码：" + list.get(i).getHospitalMedicineCode() + "不允许重复！";
			}
			// put
			if (list.get(i).getHospitalMedicineName() != null
					&& !StringUtils.isBlank(list.get(i).getHospitalMedicineName())) {
				map.put(list.get(i).getHospitalMedicineName(), "");
			}
			if (list.get(i).getHospitalMedicineCode() != null
					&& !StringUtils.isBlank(list.get(i).getHospitalMedicineCode())) {
				map1.put(list.get(i).getHospitalMedicineCode(), "");
			}
		}
		return null;
	}

	public static final String CODE = "code";
	public static final String NAME = "name";

	public static Map<String, Map<String, String>> nativeHospitalMedicineMap(List<MedicineRelationDO> list1) {
		Map<String, String> code = new HashMap<>();
		Map<String, String> name = new HashMap<>();
		for (MedicineRelationDO m : list1) {
			if (m.getHospitalMedicineName() != null && StringUtils.isNotBlank(m.getHospitalMedicineName())) {
				name.put(m.getHospitalMedicineName(), "");
			}
			if (m.getHospitalMedicineCode() != null && StringUtils.isNotBlank(m.getHospitalMedicineCode())) {
				code.put(m.getHospitalMedicineCode(), "");
			}
		}
		Map<String, Map<String, String>> map = new HashMap<>();
		map.put(CODE, code);
		map.put(NAME, name);
		return map;
	}
	
	public static Map<String, MedicineDO> getMap(List<MedicineDO> list) {
//		list.stream().collect(Collectors.toMap(MedicineDO::getMedicineName, MedicineDO::getId))
		Map<String, MedicineDO> map = new HashMap<>();
		for (MedicineDO m : list) {
			map.put(m.getMedicineName(), m);
		}
		return map;
	}
}
