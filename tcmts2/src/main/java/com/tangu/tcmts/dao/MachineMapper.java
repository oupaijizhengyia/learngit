package com.tangu.tcmts.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tangu.tcmts.po.MachineDO;
import com.tangu.tcmts.po.MachineDTO;

/**
 * @author:huangyifan
 * @Description:煎药机管理Mapper
 * @Date: create in 10：20 2017/10/24
 */
@Mapper
public interface MachineMapper {
	List<MachineDO> listMachines(MachineDTO machineDTO);
	
	Integer updateUseState(MachineDTO machineDTO);
	
	Integer insertMachine(MachineDO machineDO);
	
	Integer updateMachine(MachineDO machineDO);
	
	MachineDO getMachineCode(MachineDO machineDO);
	
	MachineDO getMachineById(MachineDTO machineDTO);
	
	List<MachineDO> listMachinesById(List<MachineDTO> machineDTO);
	
	Integer updateMachineStatus(MachineDO machineDO);
	
	Integer updateMachineByCode(MachineDO machineDO);
	
	List<MachineDO> listMachinesByCode(MachineDO machineDO);
	List<MachineDO> getNotUseMachineList();
}