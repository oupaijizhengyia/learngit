package com.tangu.tcmts.service.imp;

import java.util.Date;
import java.util.List;

import com.tangu.common.util.Constant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.dao.MedicineMaxDoseMapper;
import com.tangu.tcmts.po.MedicineMaxDose;
import com.tangu.tcmts.po.RecipeMedicine;
import com.tangu.tcmts.service.MedicineMaxDoseService;
import com.tangu.tcmts.util.Constants;

@Service
public class MedicineMaxDoseServiceImpl implements MedicineMaxDoseService{

  private static final String KG = "公斤";
  private static final String G = "g";
  
  @Autowired
  MedicineMaxDoseMapper medicineMaxDoseMapper;
  
  @Override
  public Object listMedicineMaxDose(MedicineMaxDose medicineMaxDose) {
    List<MedicineMaxDose> list  = medicineMaxDoseMapper.listMedicineMaxDose(medicineMaxDose);
    list.forEach(m->{
      if (m.getStandard().equals(KG)) {
        m.setStandard(G);
      }
    });
    return new ResponseModel(list);
  }

  @Override
	public Object saveMedicineMaxDose(MedicineMaxDose medicineMaxDose) {
		String errMsg = "药品已存在,不允许重复!";
		if (medicineMaxDoseMapper.listMedicineMaxDoseByMedicineId(medicineMaxDose) > Constants.STATE_ZERO) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, errMsg);
		}
		int rows = Constants.STATE_ZERO;
		medicineMaxDose.setModUser(JwtUserTool.getId());
		if (medicineMaxDose.getId() == null || medicineMaxDose.getId() == Constants.STATE_ZERO) {
			rows = insertMedicineMaxDose(medicineMaxDose);
		} else {
			rows = updateMedicineMaxDose(medicineMaxDose);
		}
		if (rows > Constants.STATE_ZERO) {
			return new ResponseModel().attr(ResponseModel.KEY_MESSAGE, Constant.OPRATION_SUCCESS);
		}
		return new ResponseModel().attr(ResponseModel.KEY_ERROR, Constant.ADD_FAILED);
	}

  @Override
  public Object deleteMedicineMaxDose(Integer id) {
    int i = medicineMaxDoseMapper.deleteByPrimaryKey(id);
    return Constants.operation(i, Constants.DELETE_SUCCESS, "删除失败");
  }
  
  public Integer insertMedicineMaxDose(MedicineMaxDose medicineMaxDose){
    medicineMaxDose.setCreateTime(new Date());
    return medicineMaxDoseMapper.insertSelective(medicineMaxDose);
  }
  
  public Integer updateMedicineMaxDose(MedicineMaxDose medicineMaxDose){
    return medicineMaxDoseMapper.updateByPrimaryKeySelective(medicineMaxDose);
  }

	@Override
	public List<MedicineMaxDose> listMedicineMaxDoseByMedicineIds(List<RecipeMedicine> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			return medicineMaxDoseMapper.listMedicineMaxDoseByMedicineIds(list);
		}
		return null;
	}
}
