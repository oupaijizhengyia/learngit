package com.tangu.tcmts.service;

import java.util.List;

import com.tangu.tcmts.po.MachineDO;
import com.tangu.tcmts.po.MachineDTO;

/**
 * @author:huangyifan
 * @Description:煎药机管理接口
 * @Date: create in 10:48 2017/10/24
 */
public interface MachineService {
	List<MachineDO> listMachines(MachineDTO machineDTO);
	
	Integer updateUseState(MachineDTO machineDTO);
	
	Integer insertMachine(MachineDO machineDO);
	
	Boolean getMachineCode(MachineDO machineDO);
	
	Integer updateMachine(MachineDO machineDO);
	
	MachineDO getMachineById(MachineDTO machineDTO);
	
	List<MachineDO> listMachinesById(List<MachineDTO> machineDTO);
	
	Integer updateMachineByCode(MachineDO machineDO);
	
	List<MachineDO> listMachinesByCode(MachineDO machineDO);
}
