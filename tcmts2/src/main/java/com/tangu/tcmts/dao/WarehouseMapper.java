package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tangu.tcmts.po.WarehouseDO;
import com.tangu.tcmts.po.WarehouseDTO;

/**
 * @author:huangyifan
 * @Description:仓库、药房Mapper
 * @Date: create in 10：20 2017/10/24
 */
@Mapper
public interface WarehouseMapper {
	List<WarehouseDO> listWarehouse(WarehouseDTO warehouseDTO);

	Integer updateUseState(WarehouseDTO warehouseDTO);

	Integer addWarehouse(WarehouseDO warehouseDO);
	
	Integer updateWarehouse(WarehouseDO warehouseDO);
	
	Integer getWarehouseByName(WarehouseDO warehouseDO);
}