package com.tangu.tcmts.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tangu.common.exception.TanguException;
import com.tangu.tcmts.dao.WarehouseMapper;
import com.tangu.tcmts.dao.WarehouseStandardMapper;
import com.tangu.tcmts.po.LoadMedicineDO;
import com.tangu.tcmts.po.MedicineStandardDO;
import com.tangu.tcmts.po.WarehouseDO;
import com.tangu.tcmts.po.WarehouseDTO;
import com.tangu.tcmts.po.WarehouseStandardDO;
import com.tangu.tcmts.po.WarehouseStandardDTO;
import com.tangu.tcmts.service.WarehouseService;

/**
 * @author:huangyifan
 * @Description:仓库、药房实现类
 * @Date: create in 10：30 2017/10/24
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {
	@Autowired
	WarehouseMapper warehouseMapper;
	@Autowired
	WarehouseStandardMapper warehouseStandardMapper;

	@Override
	public List<WarehouseDO> listWarehouse(WarehouseDTO warehouseDTO) {
		return warehouseMapper.listWarehouse(warehouseDTO);
	}


	@Override
	public Integer updateUseState(WarehouseDTO warehouseDTO) {
		return warehouseMapper.updateUseState(warehouseDTO);
	}

	@Override
	public Integer addWarehouse(WarehouseDO warehouseDO) {
		return warehouseMapper.addWarehouse(warehouseDO);
	}

	@Override
	public Integer updateWarehouse(WarehouseDO warehouseDO) {
		return warehouseMapper.updateWarehouse(warehouseDO);
	}

	@Override
	public List<WarehouseStandardDO> listWarehouseStandard(WarehouseStandardDTO warehouseStandardDTO) {
		return warehouseStandardMapper.listWarehouseStandard(warehouseStandardDTO);
	}

	@Override
	public Integer updateStorageLocationById(WarehouseStandardDO warehouseStandardDO) {
		return warehouseStandardMapper.updateStorageLocationById(warehouseStandardDO);
	}

	@Transactional
	@Override
	public Integer insertWarehouseStandard(LoadMedicineDO loadMedicineDO) {
		try {
			Map<String , WarehouseStandardDO> warehouseMap=new HashMap<>();
			String key = null;
			for (MedicineStandardDO ms : loadMedicineDO.getList()) {
				WarehouseStandardDO ws = new WarehouseStandardDO();
				ws.setMedicineId(ms.getMedicineId());
				ws.setWarehouseId(ms.getWarehouseId());
				ws.setStandardId(ms.getStandardId());
				if (ws.getStandardId() != null) {
					key = ws.getMedicineId().toString()+ws.getWarehouseId().toString()+ws.getStandardId();
				} else {
					key = ws.getMedicineId().toString()+ws.getWarehouseId().toString();
				}
				System.out.println("key1:"+key);
				warehouseMap.put(key, ws);
			}
			/**
			 * 获取本地数据
			 */
			List<WarehouseStandardDO> nativeList = warehouseStandardMapper
					.listNativeWarehouserStandard(loadMedicineDO.getWarehouseId());
			Map<String,WarehouseStandardDO> nativeMap=new HashMap<>();
			/**
			 * 获取需要删除的数据  和  剩余的本地数据
			 */
			List<WarehouseStandardDO> deleteList = new ArrayList<>();
			for (WarehouseStandardDO ws : nativeList) {
				if (ws.getStandardId() == null) {
					key = ws.getMedicineId().toString()+ws.getWarehouseId().toString();
				} else {
					key = ws.getMedicineId().toString()+ws.getWarehouseId().toString()+ws.getStandardId();
				}
				System.out.println("key:"+key);
				if(warehouseMap.get(key) == null){
					deleteList.add(ws);
				}else{
					nativeMap.put(key, ws);
				}

			}
			/**
			 * 获取需要新增的数据
			 */
			List<WarehouseStandardDO> insertList = new ArrayList<>();
			
			 for (String key1 : warehouseMap.keySet()) {
				if(nativeMap==null || nativeMap.isEmpty()){
					insertList.add(warehouseMap.get(key1));
				}else{
					if (nativeMap.get(key1) == null) {
						insertList.add(warehouseMap.get(key1));
					}
				}
			}
			if (insertList != null && !insertList.isEmpty()) {
				warehouseStandardMapper.insertWarehouseStandard(insertList);
			}
			if (deleteList != null && !deleteList.isEmpty()) {
				warehouseStandardMapper.deleteWarehouseStandard(deleteList);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			throw new TanguException(e.getMessage());
		}
	}

	@Override
	public Integer getWarehouseByName(WarehouseDO warehouseDO) {
		return warehouseMapper.getWarehouseByName(warehouseDO);
	}


	@Override
	public String getApplySysCode() {
		String sysCode = "";
//		WarehouseIn bill = new WarehouseIn();
//		int sequenceLength = 4;
//		sysCode = DateUtil.getDate(new Date(), "yyyyMMdd");
//		bill.setSysCode(sysCode);
//		String index = generateSysCodeIndex(sysCode);
//		if (index.length() < sequenceLength) {
//			sysCode = sysCode
//					+ StringUtil.repeat("0", sequenceLength
//							- index.length()) + index;
//		} else {
//			sysCode = sysCode + index;
//		}
		return sysCode;
	}

	@Override
	public Integer updateStorageLocationByIds(List<WarehouseStandardDO> warehouseStandardDO) {
		return warehouseStandardMapper.updateStorageLocationByIds(warehouseStandardDO);
	}
}
