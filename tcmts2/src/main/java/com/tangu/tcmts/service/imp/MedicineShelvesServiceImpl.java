package com.tangu.tcmts.service.imp;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangu.common.util.Constant;
import com.tangu.common.util.entity.ResponseModel;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.dao.MedicineShelvesMapper;
import com.tangu.tcmts.po.MedicineShelves;
import com.tangu.tcmts.service.MedicineShelvesService;
import com.tangu.tcmts.util.Constants;

@Service
public class MedicineShelvesServiceImpl implements MedicineShelvesService{
  
  @Autowired
  MedicineShelvesMapper medicineShelvesMapper;

  @Override
  public Object listMedicineShelves(MedicineShelves medicineShelves) {
    return new ResponseModel(medicineShelvesMapper.listMedicineShelves(medicineShelves));
  }

  @Override
	public Object saveMedicineShelve(MedicineShelves medicineShelves) {
		int i = Constants.STATE_ZERO;
		String errMsg = "货架号已存在,不允许重复!";
		MedicineShelves medicineCode = new MedicineShelves();
		medicineCode.setShelvesCode(medicineShelves.getShelvesCode());
		medicineCode.setId(medicineShelves.getId());
		List<MedicineShelves> lp = medicineShelvesMapper.listMedicineShelves(medicineCode);
		if (CollectionUtils.isNotEmpty(lp)) {
			return new ResponseModel().attr(ResponseModel.KEY_ERROR, errMsg);
		}
		if (medicineShelves.getId() != null) {
			medicineShelves.setModUser(JwtUserTool.getId());
			i = updateMedicineShelve(medicineShelves);
		} else {
			i = insertMedicineShelve(medicineShelves);
		}
		return Constants.operation(i, Constant.OPRATION_SUCCESS, Constant.OPRATION_FAILED);
	}

  @Override
  public Object deleteMedicineShelve(Integer id) {
    int i = medicineShelvesMapper.deleteByPrimaryKey(id);
    return Constants.operation(i, Constants.DELETE_SUCCESS, "删除失败");
  }
  
  @Override
  public List<MedicineShelves> listMedicineShelvesByIds(List<MedicineShelves> list) {
    return medicineShelvesMapper.listMedicineShelvesByIds(list);
  }
  
  public Integer insertMedicineShelve(MedicineShelves medicineShelves){
    medicineShelves.setCreateTime(new Date());
    return medicineShelvesMapper.insertSelective(medicineShelves);
  }
  
  public Integer updateMedicineShelve(MedicineShelves medicineShelves){
    return medicineShelvesMapper.updateByPrimaryKeySelective(medicineShelves);
  }

@Override
public Integer seleteRecipeByShelvesCode(MedicineShelves medicineShelves) {
	return medicineShelvesMapper.seleteRecipeByShelvesCode(medicineShelves);
}

}
