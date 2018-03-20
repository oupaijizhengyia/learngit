package com.tangu.tcmts.service.imp;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tangu.common.util.Constant;
import com.tangu.tcmts.util.excel.ExcelExportVO;
import com.tangu.common.util.PinyinConverter;
import com.tangu.tcmts.util.excel.ReportUtil;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.dao.MedicineMapper;
import com.tangu.tcmts.dao.MedicineStandardMapper;
import com.tangu.tcmts.po.MedicineDO;
import com.tangu.tcmts.po.MedicineDTO;
import com.tangu.tcmts.po.MedicineStandardDO;
import com.tangu.tcmts.po.MedicineStandardDTO;
import com.tangu.tcmts.po.SysLookup;
import com.tangu.tcmts.service.MedicineService;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.MedicineUtil;

/**
 * @author:huangyifan
 * @Description:中药管理impl
 * @Date: create in 11：49 2017/10/31
 */
@Service
public class MedicineServiceImpl implements MedicineService {
	@Autowired
	MedicineMapper medicineMapper;
	@Autowired
	MedicineStandardMapper medicineStandardMapper;

	@Override
	public List<MedicineDO> listMedicine(MedicineDTO medicineDTO) {
		return medicineMapper.listMedicine(medicineDTO);
	}

	@Override
	public List<SysLookup> listSunMedicineCode() {
		return medicineMapper.listSunMedicineCode();
	}

	public Integer addMedicine(MedicineDO medicineDO) {
		return medicineMapper.addMedicine(medicineDO);
	}

	/**
	 * 新增
	 */
	@Transactional
	@Override
	@CacheEvict(value = Constants.CACHE_KEY_MEDICINE, allEntries = true)
	public Object insertMedicine(MedicineDO medicineDO) {
		medicineDO.setUnitType(MedicineUtil.getMedicineUnit(medicineDO.getStandard()));
		medicineDO.setInitialCode(PinyinConverter.String2Alpha(medicineDO.getMedicineName()));
		Integer result = this.addMedicine(medicineDO);
		if (result > 0) {
			/**
			 * 新增药品价格
			 */
//		  medicineDO.getMedicinePrice().setMedicineId(medicineDO.getId());
//			medicinePriceMapper.addMedicinePrice(medicineDO.getMedicinePrice());
			/**
			 * 新增药品规格
			 */
			if (CollectionUtils.isNotEmpty(medicineDO.getStandardList())) {
				medicineDO.getStandardList().stream().forEach(s -> s.setMedicineId(medicineDO.getId()));
				medicineStandardMapper.addMedicineStandard(medicineDO.getStandardList());
			}
			MedicineDTO medicineDTO = new MedicineDTO();
			medicineDTO.setId(medicineDO.getId());
			List<MedicineDO> list = medicineMapper.listMedicine(medicineDTO);
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS)
					.attr(ResponseModel.KEY_DATA, list == null ? list : list.get(0));
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}

	@Override
	public MedicineDO getMedicineById(MedicineDTO medicineDTO) {
		MedicineDO medicineDO = medicineMapper.getMedicineById(medicineDTO);
        if(medicineDO.getCreamFormulaType() != null){
            if(medicineDO.getCreamFormulaType() == 0){
                medicineDO.setCreamFormulaType(null);
            }
        }
		return medicineDO;
	}

	/**
	 * 修改
	 */
	@Transactional
	@Override
	@CacheEvict(value = Constants.CACHE_KEY_MEDICINE, allEntries = true)
	public Object update(MedicineDO medicineDO) {
		if (medicineDO.getId() == null) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
		}
		/**
		 * 药品规格：新增、删除、修改
		 */
		List<MedicineStandardDO> deleteList = new ArrayList<>();
		List<MedicineStandardDO> updateList = new ArrayList<>();
		List<MedicineStandardDO> insertList = new ArrayList<>();
		Map<String, MedicineStandardDO> nowMap = new HashMap<>();
		Map<String, MedicineStandardDO> nativeMap = new HashMap<String, MedicineStandardDO>();
		if (medicineDO.getMedicineHospitalType() == null || medicineDO.getMedicineHospitalType() != 0) {//非院内
			if (CollectionUtils.isNotEmpty(medicineDO.getStandardList())) {
				for (MedicineStandardDO ms : medicineDO.getStandardList()) {
					ms.setMedicineId(medicineDO.getId());
					if (ms.getId() != null) {
						nowMap.put(ms.getId().toString(), ms);
					} else {
						insertList.add(ms);
					}
				}
			}
			/**
			 * 获取需要删除的数据
			 */
			List<MedicineStandardDO> nativeList = medicineStandardMapper.listStandardByMedicineId(medicineDO);
			for (MedicineStandardDO ms : nativeList) {
				String key = ms.getId().toString();

				if (nowMap.get(key) == null) {
					deleteList.add(ms);
				} else {
					nativeMap.put(key, ms);
				}
			}
			/**
			 * 判断药品规格是否已经使用
			 */
			if (deleteList != null && !deleteList.isEmpty()) {
				Integer count = medicineStandardMapper.countWarehouseStandradById(deleteList);
				if (count > 0) {
					return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constants.MEDICINE_STANDARD_EXIST);
				}
			}
		}
		medicineDO.setInitialCode(PinyinConverter.String2Alpha(medicineDO.getMedicineName()));
		medicineDO.setUnitType(MedicineUtil.getMedicineUnit(medicineDO.getStandard()));
		Integer result = medicineMapper.updateMedicine(medicineDO);
		if (result > 0) {
//			medicinePriceMapper.updateMedicinePrice(medicineDO.getMedicinePrice());

			/**
			 * 获取需要新增或修改的数据
			 */
			for (String key : nowMap.keySet()) {
				if (nativeMap.get(key) != null) {
					updateList.add(nowMap.get(key));
				}
			}

			if (CollectionUtils.isNotEmpty(updateList)) {
				medicineStandardMapper.updateMedicineStandard(updateList);
			}
			if (CollectionUtils.isNotEmpty(insertList)) {
				medicineStandardMapper.addMedicineStandard(insertList);
			}
			if (CollectionUtils.isNotEmpty(deleteList)) {
				medicineStandardMapper.deleteMedicineStandard(deleteList);
			}

			MedicineDTO medicineDTO = new MedicineDTO();
			medicineDTO.setId(medicineDO.getId());
			List<MedicineDO> list = medicineMapper.listMedicine(medicineDTO);
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS)
					.attr(ResponseModel.KEY_DATA, list == null ? list : list.get(0));
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.OPRATION_FAILED);
	}

	@Override
	@CacheEvict(value = Constants.CACHE_KEY_MEDICINE, allEntries = true)
	public Integer updateState(MedicineDTO medicineDTO) {
		return medicineMapper.updateState(medicineDTO);
	}

	@Override
	@Cacheable(value = Constants.CACHE_KEY_MEDICINE, keyGenerator = "cacheKeyGenerator")
	public List<MedicineDO> listMedicineForLookup() {
		return medicineMapper.listMedicineForLookup();
	}


	/**
	 * 获取需要载入的药品
	 */
	@Override
	public List<MedicineStandardDO> listLoadMedicine(MedicineStandardDTO medicineStandardDTO) {
		return medicineStandardMapper.listLoadMedicine(medicineStandardDTO);
	}

	@Override
	public List<MedicineStandardDO> listNotLoadMedicine(MedicineStandardDTO medicineStandardDTO) {
		return medicineStandardMapper.listNotLoadMedicine(medicineStandardDTO);
	}

	@Override
	public Integer addMedicines(List<MedicineDO> list) {
		return medicineMapper.addMedicines(list);
	}

	@Override
	public void export(List<?> list, HttpServletRequest req, HttpServletResponse resp, String xml)
			throws UnsupportedEncodingException, ServletException, IOException {
		ExcelExportVO para = new ExcelExportVO();
		para.xmlString = xml;
		para.setData(list);
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("APPLICATION/OCTET-STREAM");
		ReportUtil.export(req, resp, para);
	}

	@Override
	public Integer getMedicineByName(MedicineDO medicineDO) {
		return medicineMapper.getMedicineByName(medicineDO);
	}
}
