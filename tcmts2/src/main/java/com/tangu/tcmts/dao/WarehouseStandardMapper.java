package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.tangu.tcmts.po.WarehouseStandardDO;
import com.tangu.tcmts.po.WarehouseStandardDTO;

/**
 * @author:huangyifan
 * @Description:仓库、药房 药品Mappers
 * @Date: create in 10：02 2017/11/6
 */
@Mapper
public interface WarehouseStandardMapper {
	List<WarehouseStandardDO> listWarehouseStandard(WarehouseStandardDTO warehouseStandardDTO);
	@Update(" UPDATE `warehouse_standard` SET storage_location=#{storageLocation} WHERE id =#{id} ")
	Integer updateStorageLocationById(WarehouseStandardDO warehouseStandardDO);

	Integer insertWarehouseStandard(List<WarehouseStandardDO> warehouseStandardDO);

	List<WarehouseStandardDO> listNativeWarehouserStandard(Integer warehouseId);

	Integer deleteWarehouseStandard(List<WarehouseStandardDO> list);
	
	Integer updateStorageLocationByIds(List<WarehouseStandardDO> warehouseStandardDO);
	
}
