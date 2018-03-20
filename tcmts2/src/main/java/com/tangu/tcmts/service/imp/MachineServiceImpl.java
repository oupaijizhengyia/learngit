package com.tangu.tcmts.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangu.tcmts.dao.MachineMapper;
import com.tangu.tcmts.po.MachineDO;
import com.tangu.tcmts.po.MachineDTO;
import com.tangu.tcmts.service.MachineService;

/**
 * @author:huangyifan
 * @Description:煎药机管理实现
 * @Date: create in 10:48 2017/10/24
 */
@Service
public class MachineServiceImpl implements MachineService {
	@Autowired
	MachineMapper machineMapper;

	@Override
	public List<MachineDO> listMachines(MachineDTO machineDTO) {
		return machineMapper.listMachines(machineDTO);
	}

	@Override
	public Integer updateUseState(MachineDTO machineDTO) {
		return machineMapper.updateUseState(machineDTO);
	}

	@Override
	public Integer insertMachine(MachineDO machineDO) {
		return machineMapper.insertMachine(machineDO);
	}

	@Override
	public Boolean getMachineCode(MachineDO machineDO) {
		if(machineMapper.getMachineCode(machineDO)!=null){
			return true;
		}
		return false;
	}

	@Override
	public Integer updateMachine(MachineDO machineDO) {
		return machineMapper.updateMachine(machineDO);
	}

	@Override
	public MachineDO getMachineById(MachineDTO machineDTO) {
		return machineMapper.getMachineById(machineDTO);
	}

	@Override
	public List<MachineDO> listMachinesById(List<MachineDTO> machineDTO) {
		return machineMapper.listMachinesById(machineDTO);
	}
	
	@Override
	public Integer updateMachineByCode(MachineDO machineDO) {
		return machineMapper.updateMachineByCode(machineDO);
	}

	@Override
	public List<MachineDO> listMachinesByCode(MachineDO machineDO) {
		// TODO Auto-generated method stub
		return machineMapper.listMachinesByCode(machineDO);
	}

}
