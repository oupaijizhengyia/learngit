package com.tangu.tcmts.service;

import java.util.List;

import com.tangu.tcmts.po.LoadMedicineDO;
import com.tangu.tcmts.po.WarehouseDO;
import com.tangu.tcmts.po.WarehouseDTO;
import com.tangu.tcmts.po.WarehouseStandardDO;
import com.tangu.tcmts.po.WarehouseStandardDTO;

/**
 * @author:huangyifan
 * @Description:仓库、药房接口
 * @Date: create in 10：30 2017/10/24
 */
public interface WarehouseService {
	List<WarehouseDO> listWarehouse(WarehouseDTO warehouseDTO);


	Integer updateUseState(WarehouseDTO warehouseDTO);

	Integer addWarehouse(WarehouseDO warehouseDO);

	Integer updateWarehouse(WarehouseDO warehouseDO);

	List<WarehouseStandardDO> listWarehouseStandard(WarehouseStandardDTO warehouseStandardDTO);

	Integer updateStorageLocationById(WarehouseStandardDO warehouseStandardDO);

	Integer insertWarehouseStandard( LoadMedicineDO loadMedicineDO);
	
	Integer getWarehouseByName(WarehouseDO warehouseDO);
	String getApplySysCode();
	Integer updateStorageLocationByIds(List<WarehouseStandardDO> warehouseStandardDO);
}
