package com.tangu.tcmts.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.tangu.tcmts.po.BtRelationZd;
import com.tangu.tcmts.po.City;
import com.tangu.tcmts.po.MedicineDO;
import com.tangu.tcmts.po.Prescription;
import com.tangu.tcmts.po.Region;
import com.tangu.tcmts.po.TransferMedicine;
import com.tangu.tcmts.po.TransferRecipeDO;
import com.tangu.tcmts.po.WarehouseStandardDO;

import lombok.extern.slf4j.Slf4j;

/**
 * excel导入util
 * 
 * @author Administrator
 *
 */
@Slf4j
public class ImportExcelUtil {
	private final static String XLS = "xls";
	private final static String XLSX = "xlsx";

	private final static Pattern pattern = Pattern.compile("\\d+");
	private final static Pattern pattern2 = Pattern.compile("[0-9]*");

	public static Map<String, String> getBtzdMap(List<BtRelationZd> btList) {
		Map<String, String> btzdMap = new HashMap<>();
		for (BtRelationZd b : btList) {
			btzdMap.put(b.getFieldName(), b.getHeader());
		}
		return btzdMap;
	}

	public static Map<String, Integer> zdColumnIndexMap(Row row, List<BtRelationZd> btList,
			Map<Integer, String> recipeIndexFieldNameMap, Map<Integer, String> medicineIndexFieldNameMap) {
		Map<String, Integer> zdColumnIndexMap = new HashMap<String, Integer>();
		for (Cell cell : row) {
			for (BtRelationZd b : btList) {
				if (b.getHeader().trim().equals(getCellStringValue(cell).trim())) {
					zdColumnIndexMap.put(b.getFieldName().trim(), cell.getColumnIndex());
					if (b.getTableType() == 1) {
						recipeIndexFieldNameMap.put(cell.getColumnIndex(), b.getFieldName().trim());
					} else {
						medicineIndexFieldNameMap.put(cell.getColumnIndex(), b.getFieldName().trim());
					}
				}
			}
		}
		return zdColumnIndexMap;
	}

	public static boolean ifNameAndMedicineName(Row row, Map<String, Integer> zdColumnIndexMap) {
		for (Cell cell : row) {
			// 姓名
			if (cell.getColumnIndex() == (zdColumnIndexMap.get("recipientName") == null ? -1
					: zdColumnIndexMap.get("recipientName").intValue())) {
				if ("合计".endsWith(getCellStringValue(cell).trim())) {
					return true;
				}
				if ("".endsWith(getCellStringValue(cell).trim())) {
					return true;
				}
			}
			// 药名
			else if (cell.getColumnIndex() == (zdColumnIndexMap.get("medicineName") == null ? -1
					: zdColumnIndexMap.get("medicineName").intValue())) {
				if ("".endsWith(getCellStringValue(cell).trim())) {
					return true;
				}
			}
		}
		return false;
	}

	public static Workbook getWorkbook(String fileName, MultipartFile file) throws IOException {
		if (fileName.endsWith(XLS)) {// 2003
			return new HSSFWorkbook(file.getInputStream());
		} else if (fileName.endsWith(XLSX)) {// 2007
			return new XSSFWorkbook(file.getInputStream());
		} else {
			return null;
		}
	}

	public static boolean isEmptyData(String str) {
		if (str == null || StringUtils.isBlank(str.trim())) {
			return true;
		}
		return false;
	}
	private static int setBoilType (String str) {
		if (StringUtils.isBlank(str)) {
			return 0;
		}
		if (StringUtils.contains(str, "膏") && StringUtils.contains(str, "代")) {
			return 3;
		}
		if (StringUtils.contains(str, "膏") && StringUtils.contains(str, "自")) {
			return 4;
		}
		if (StringUtils.contains(str, "代")) {
			return 1;
		}
		if (StringUtils.contains(str, "自")) {
			return 2;
		}
		return 0;
	}
	private static int setCarryId(String str) {
		if (StringUtils.isBlank(str)) {
			return 0;
		}
		if (StringUtils.contains(str, "顺")) {
			return 1;
		}
		if (StringUtils.contains(str, "邮")) {
			return 2;
		}
		if (StringUtils.contains(str, "送")) {
			return 3;
		}
		return 0;
	}
	public static String ifExcelData(List<TransferRecipeDO> recipeList, List<Prescription> prescriptionList,
			Map<String, String> configMap, TransferRecipeDO param) {
		Boolean ifYyyf = false;
		String hospitalIdStr = "";
		hospitalIdStr = "[" + param.getHospitalId() + "]";
		int boilType = 0;
		Integer carryId = 0;
		for (TransferRecipeDO tr : recipeList) {
			boilType = setBoilType(tr.getBoilTypeName());
			if (boilType > 0) {
				tr.setBoilType(boilType);
			}
			carryId = setCarryId(tr.getPsfs());
			if (carryId > 0) {
				tr.setCarryId(carryId);
				tr.setPsfs(carryId.toString());
			}
			/**
			 * 判断是否为空
			 */
			if (isEmptyData(tr.getRecipientName())) {
				return "第" + (tr.getRowth() + 1) + "行,患者姓名列为空";
			}

			if (isEmptyData(tr.getMedicine().getMedicineName())) {
				return "第" + (tr.getRowth() + 1) + "行,药品名称列为空";
			} else {
				if (!(configMap.get(Constants.IF_EXIST_HOSPITAL).indexOf(hospitalIdStr) > -1)) {
					if (!(tr.getMedicine().getMedicineName().indexOf("加工费") > -1 && param.getBoilType() == 2)) {
						Matcher matcher = pattern.matcher(tr.getMedicine().getMedicineName());
						while (matcher.find()) {
							tr.getMedicine().setStandardWeight(Float.valueOf(matcher.group(0)));
						}
					}
				}
			}
			if (tr.getQuantity() == null || tr.getQuantity() == 0) {
				return "第" + (tr.getRowth() + 1) + "行,帖数列为空";
			}
			if (tr.getTotalWeight() == null) {
				return "第" + (tr.getRowth() + 1) + "行,总数量列为空";
			}
			if (!isEmptyData(tr.getRemark1())) {// 备注，多个备注以，连接
				tr.setRemark(tr.getRemark() + "," + tr.getRemark1());
			}
			if (tr.getMedicine().getStandardWeight() == null) {
				return "第" + (tr.getRowth() + 1) + "行,药品规格列为空";
			} else {
				tr.getMedicine().setStandardWeight(
						getStandardWeightByStandard(tr.getMedicine().getStandardWeight().toString()));
			}

			if (tr.getMedicine().getMedicineName() != null && !"".equals(tr.getMedicine().getMedicineName().trim())) {
				tr.setHospitalCode(param.getHospitalCode());
				// 如果是“配方名字是医院药方”，那么将药方转化成配方
				for (int i = 0; i < prescriptionList.size(); i++) {
					if (prescriptionList.get(i).getPrescriptionName().equals(tr.getMedicine().getMedicineName())) {
						ifYyyf = true;
						// recipeYf =
						// copyRecipeMedicine(tr,prescriptionList.get(i));
						tr.getMedicine().setMedicineName(prescriptionList.get(i).getMedicineName());
						// copyO.setSjsl(medicinePrice.getWeight());//实际数量?字段不明确
						tr.getMedicine().setSpecialBoilType(prescriptionList.get(i).getPrescriptionName());
						tr.getMedicine().setWeightEvery(prescriptionList.get(i).getWeight());// 单帖量
						tr.getMedicine().setMedicineSource("协定方");
						// recipeList.add(recipeYf);
					}
				}

				if (ifYyyf) {
					continue;
				}
				if (tr.getQuantity() == null || tr.getQuantity().intValue() == 0) {
					tr.setQuantity(1);
				}
			}
		}
		return null;
	}

	/**
	 * 判断市、区是否存在
	 * 
	 * @param recipe1
	 * @param list
	 * @return
	 */
	public static boolean checkAllRegion(TransferRecipeDO recipe1, List<City> list) {
		if (recipe1.getCity() != null && !"".equals(recipe1.getCity())) {
			for (City city : list) {
				if (recipe1.getCity().equals(city.getName())) {
					for (Region re : city.getDistricts()) {
						if (recipe1.getRegion() != null && !"".equals(recipe1.getRegion())
								&& recipe1.getRegion().equals(re.getName())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	// 检验区是否是上海市的区
	public static boolean checkRegion(TransferRecipeDO recipe1, List<Region> list) {
		boolean result = false;

		Region range = null;
		for (int i = 0; i < list.size(); i++) {
			range = list.get(i);
			if (range.getName().equals(recipe1.getRegion())) {
				result = true;
				break;
			}
		}
		return result;
	}

	// 检验电话、区、地址信息是否齐全
	public static int checkRecipient(TransferRecipeDO recipe1) {
		int res = 0;
		// logger.info("medicine.getRecipientTel()==="+medicine.getRecipientTel());
		// logger.info("medicine.getRecipientAddress()==="+medicine.getRecipientAddress());
		// logger.info("medicine.getRegion()==="+medicine.getRegion());
		if (recipe1.getRecipientTel() != null && !"".equals(recipe1.getRecipientTel())) {
			res++;
		}
		if (recipe1.getRecipientAddress() != null && !"".equals(recipe1.getRecipientAddress())) {
			res++;
		}
		if (recipe1.getRegion() != null && !"".equals(recipe1.getRegion())) {
			res++;
		}
		return res;
	}

	// 根据“系统配置判断标准”判断药名中是否包含规格量，如果包含取得“规格量”，并根据“系统配置的计算公式”计算出“总药量和单贴量”
	public static void calTotalWeight(TransferMedicine transferMedicine, Map<String, String> configMap,
			int medicineType) {
		// 判断标准种类：康桥标准
		if ("1".equals(configMap.get(Constants.DR_RECIPE_BAOHAN_GUIGE_BZ))) {
			int a = transferMedicine.getMedicineName().lastIndexOf("g");
			int b = transferMedicine.getMedicineName().lastIndexOf("（");
			int c = transferMedicine.getMedicineName().lastIndexOf("）");

			if (a > -1 && b > -1 && c > -1) {
				// 取得规格量
				Matcher isNum = pattern2.matcher(transferMedicine.getMedicineName().substring(b + 1, a).trim());
				if (isNum.matches()) {
					transferMedicine.setStandardWeight(
							Float.parseFloat(transferMedicine.getMedicineName().substring(b + 1, a).trim()));
				}
				/*
				 * if(recipeMedicine.getMedicine().getStandardWeight()!=null){
				 * recipeMedicine.setStandardWeight(recipeMedicine.getGgl().
				 * floatValue()); }
				 */
			}
			// 一种是从药品名称中解析出规格量，另一种是从药品规格中解析出规格量
			if (transferMedicine.getStandardWeight() != null && transferMedicine.getStandardWeight().doubleValue() > 0
					&& transferMedicine.getUnitType().intValue() == 1) {
				// 根据系统配置的计算公式计算出总药量
				if ("a|b".equals(configMap.get(Constants.DR_RECIPE_ZONGLIANG_GS))) {
					if (medicineType == 2 && configMap.get(Constants.REFORM_MEDICINE_NAME) != null
							&& "Y".equals(configMap.get(Constants.REFORM_MEDICINE_NAME))) {
						gongshi(transferMedicine, "a");
					} else {
						gongshi(transferMedicine, "b");
					}
				} else if ("c|d".equals(configMap.get(Constants.DR_RECIPE_ZONGLIANG_GS))) {
					gongshi(transferMedicine, "c");
				}
			} else {
				if ("a|b".equals(configMap.get(Constants.DR_RECIPE_ZONGLIANG_GS))) {
					gongshi(transferMedicine, "a");
				} else if ("c|d".equals(configMap.get(Constants.DR_RECIPE_ZONGLIANG_GS))) {
					gongshi(transferMedicine, "d");
				}
			}
		}
		// 判断标准种类：虹桥标准
		else if ("2".equals(configMap.get("DR_RECIPE_BAOHAN_GUIGE_BZ"))) {
			int a = transferMedicine.getMedicineName().lastIndexOf("克");
			if (a > 0) {
				getGgl(transferMedicine, a);
				// logger.info("recipeMedicine.getGgl().doubleValue()=="+recipeMedicine.getGgl().doubleValue());
			} else {
				if (transferMedicine.getStandardWeight() != null
						&& transferMedicine.getStandardWeight().intValue() > 0) {
					transferMedicine.setStandardWeight(transferMedicine.getStandardWeight());
				}
			}
			// 一种是从药品名称中解析出规格量，另一种是从药品规格中解析出规格量
			if (transferMedicine.getStandardWeight() != null && transferMedicine.getStandardWeight().doubleValue() > 0
					&& transferMedicine.getUnitType() != null && transferMedicine.getUnitType().intValue() == 1) {
				if ("a|b".equals(configMap.get(Constants.DR_RECIPE_ZONGLIANG_GS))) {
					gongshi(transferMedicine, "b");
				} else if ("c|d".equals(configMap.get(Constants.DR_RECIPE_ZONGLIANG_GS))) {
					gongshi(transferMedicine, "c");
				}
			} else {
				if ("a|b".equals(configMap.get(Constants.DR_RECIPE_ZONGLIANG_GS))) {
					gongshi(transferMedicine, "a");
				} else if ("c|d".equals(configMap.get(Constants.DR_RECIPE_ZONGLIANG_GS))) {
					gongshi(transferMedicine, "d");
				}
			}
		}

	}

	// 几种不同的计算方式
	public static void gongshi(TransferMedicine transferMedicine, String type) {
		if ("a".equals(type)) {
			transferMedicine.setTotalWeight(transferMedicine.getTotalWeight());
		} else if ("b".equals(type)) {
			transferMedicine.setTotalWeight(
					transferMedicine.getTotalWeight().multiply(new BigDecimal(transferMedicine.getStandardWeight())));
		} else if ("c".equals(type)) {
			transferMedicine.setTotalWeight(
					transferMedicine.getTotalWeight().multiply(new BigDecimal(transferMedicine.getStandardWeight()))
							.multiply(new BigDecimal(transferMedicine.getQuantity())));
		} else if ("d".equals(type)) {
			transferMedicine.setTotalWeight(
					transferMedicine.getTotalWeight().multiply(new BigDecimal(transferMedicine.getQuantity())));
		}
		/*
		 * //如果有单贴量，那就以单贴量为准
		 * if(recipeMedicine.getDtl()!=null&&recipeMedicine.getDtl().doubleValue
		 * ()>0){
		 * recipeMedicine.setTotalWeight(recipeMedicine.getDtl().multiply(new
		 * BigDecimal(recipeMedicine.getQuantity()))); }
		 */
		// recipeMedicine.setWeight(recipeMedicine.getTotalWeight().divide(new
		// BigDecimal(recipeMedicine.getQuantity()),3,BigDecimal.ROUND_HALF_UP));
	}

	// 从药名中获得数字
	public static void getGgl(TransferMedicine medicine, Integer index) {
		String result = "";
		String a = "";
		for (int i = index - 1; i >= 0; i--) {
			a = medicine.getMedicineName().charAt(i) + "";
			if (a.matches("[0-9.]")) {
				result = a + result;
			}
		}
		if (result != null && result.length() > 0) {
			medicine.setStandardWeight(Float.parseFloat(result));
		}
	}
	// 从药品规格中获得数字
	public static float getStandardWeightByStandard(String standardWeightString) {
		if (standardWeightString == null || "".equals(standardWeightString)) {
			return 0f;
		}
		String result = "";
		String r = "";
		for (int i = 0; i < standardWeightString.length(); i++) {
			r = standardWeightString.charAt(i) + "";
			if (r.matches("[0-9.]")) {
				result = result + r;
			} else {
				if (!"".equals(result)) {
					break;
				}
			}
		}
		if (result != null && result.length() > 0) {
			return Float.valueOf(result);
		} else {
			return 0f;
		}
	}

	public static String getCellStringValue(Cell cell) {
		String cellValue = "";

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				cellValue = "" + cell.getDateCellValue();
			} else {
				cellValue = String.format("%.2f", cell.getNumericCellValue()).replaceAll("\\.00", "");

			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = "" + cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			cellValue = "" + cell.getCellFormula();
			break;
		default:
			cellValue = "";
		}

		return cellValue;
	}
	
	public static List<?> getData(MultipartFile files, Map<String, String> medicineMap, String importType) {
		// 转化取出来的数据为全数值型默认为double类型+.0的情况
		HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
		Workbook wb = null;
		List<Object> list = new ArrayList<>();
		if (files != null) {
			String fileName = files.getOriginalFilename();
			try {
				// wb = WorkbookFactory.create(files[0].getInputStream());
				if (fileName.endsWith(XLS)) {
					// 2003
					wb = new HSSFWorkbook(files.getInputStream());
				} else if (fileName.endsWith(XLSX)) {
					// 2007
					wb = new XSSFWorkbook(files.getInputStream());
				} else {
					return null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (wb == null) {
				return null;
			}

			Sheet sheet = wb.getSheetAt(0);
			// 存放Excel表抬头名称以及对应的列
			Map<Integer, String> map = new HashMap<Integer, String>();
			Integer row = 0;
			for (int rowIndex = 0; rowIndex < sheet.getLastRowNum() + 1; rowIndex++) {
				Row rs = sheet.getRow(rowIndex);
				if (rs == null) {
					continue;
				}
				if (importType.equals(Constants.EXCEL_WAREHOUSE)) {// 仓库药房药品管理，从第三行开始取数据
					row = 2;
				}
				if (rowIndex == row) {
					for (int j = 0; j < sheet.getRow(rowIndex).getPhysicalNumberOfCells(); j++) {
						map.put(j, rs.getCell(j).toString().trim());// 取得excel对应的列数和名称
					}
				}
				if (rowIndex > row) {
					Map<String, String> tempMap = new HashMap<String, String>();
					// 循环遍历map 》》》存的Excel表的抬头名称以及对应的列
					for (int j = 0; j < sheet.getRow(rowIndex).getPhysicalNumberOfCells(); j++) {
						if (map.get(j) == null) {
							continue;
						}
						String key = map.get(j).trim();
						if (medicineMap.get(key) != null) {// excel抬头存在
							/**
							 * dataFormatter.formatCellValue(rs.getCell(j))
							 * 转换excel数值为整数导入的时候会出现.0的情况
							 */
							tempMap.put(medicineMap.get(map.get(j).trim()),
									dataFormatter.formatCellValue(rs.getCell(j)).trim());
						}
					}
					getImportExcelList(importType, tempMap, list);
				}
			}
		}
		return list;
	}

	private static void getImportExcelList(String importType, Map<String, String> tempMap, List<Object> list) {
		if (importType.equals(Constants.EXCEL_MEDICINE)) {
			MedicineDO medicine = new MedicineDO();
			// java反射机制获取数据
			getObjectbyMedicine(tempMap, "com.tangu.tcmts.po.MedicineDO", medicine);
			if (medicine.getMedicineCode() != null && !"".equals(medicine.getMedicineCode())) {
				list.add(medicine);
			}
		} else if (importType.equals(Constants.EXCEL_WAREHOUSE)) {
			WarehouseStandardDO warehouse = new WarehouseStandardDO();
			getObjectbyMedicine(tempMap, "com.tangu.tcmts.po.WarehouseStandardDO", warehouse);
			if (warehouse.getId() != null && StringUtils.isNotBlank(warehouse.getStorageLocation())) {
				list.add(warehouse);
			}
		}
	}

	/**
	 * 获取数据
	 * 
	 * @param map
	 * @param classPath
	 * @param object
	 * @return
	 */
	public static Object getObjectbyMedicine(Map<String, String> map, String classPath, Object object) {
		Class<?> clazz;
		Field[] fields;
		Method[] methlist;
		try {
			clazz = Class.forName(classPath);// 获取类
			fields = clazz.getDeclaredFields();// 获取class的所有属性字段
			methlist = clazz.getDeclaredMethods();// 获取类的所有方法

			for (Field f : fields) {
				String value = map.get(f.getName());// 获取单个属性字段
				if (StringUtils.isBlank(value)) {
					continue;
				}
				String name = "set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);// 获取属性名把首字母变成大写
				for (Method m : methlist) {
					try {
						if (m.getName().equals(name)) {// 是否是对应字段的方法
							Object[] array;
							if ("java.lang.String".equals(f.getType().getName())) {
								array = new Object[] { new String(value) };
							} else if ("java.lang.Double".equals(f.getType().getName())) {
								array = new Object[] { new Double(value) };
							} else if ("java.math.BigDecimal".equals(f.getType().getName())) {
								array = new Object[] { new BigDecimal(value) };
							} else if ("java.lang.Integer".equals(f.getType().getName())) {
								array = new Object[] { Integer.valueOf(value) };
							} else {
								array = new Object[] {};
							}
							m.invoke(object, array);
						}
					} catch (IllegalArgumentException e) {
						log.error(e.getMessage(), e);
					} catch (IllegalAccessException e) {
						log.error(e.getMessage(), e);
					} catch (InvocationTargetException e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		}
		return object;
	}

}
