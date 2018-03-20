package com.tangu.tcmts.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.dao.RecipeSerialMapper;
import com.tangu.tcmts.po.RecipeSerial;
import com.tangu.tcmts.service.TakeMedicineService;

@Service
public class TakeMedicineServiceImpl implements TakeMedicineService{

  @Autowired
  RecipeSerialMapper recipeSerialMapper;

  @Override
  public Object takeMedicineInfo(RecipeSerial recipeSerial) {
	RecipeSerial t = recipeSerialMapper.selectByRecipeSerial(recipeSerial);
	if (t == null) {
		t = recipeSerialMapper.selectByPrimaryKey(recipeSerial);
	}
    if (t != null && t.getShippingState() != null) {
    	if (t.getShippingState() == 100) {
        	t.setStateTime(t.getDeliveryTime());
        } else if (t.getShippingState() == 50) {
        	t.setStateTime(t.getEndBoilTime());
        } else if (t.getShippingState() == 40) {
        	t.setStateTime(t.getStartBoilTime());
        } else if (t.getShippingState() == 10) {
        	t.setStateTime(t.getMakeTime());
        } else if (t.getShippingState() == 15) {
        	t.setStateTime(t.getCheckTime());
        } else if (t.getShippingState() == 30) {
        	t.setStateTime(t.getStartSoakTime());
        } else if (t.getShippingState() == 85) {
        	t.setStateTime(t.getCollectTime());
        } else if (t.getShippingState() == 90) {
        	t.setStateTime(t.getPackageTime());
        } else if (t.getShippingState() == 55) {
        	t.setStateTime(t.getStartConcentrateTime());
        } else if (t.getShippingState() == 60) {
        	t.setStateTime(t.getEndConcentrateTime());
        } else if (t.getShippingState() == 70) {
        	t.setStateTime(t.getEndSubsideTime());
        } else if (t.getShippingState() == 35) {
        	t.setStateTime(t.getFristBoilTime());
        } else if (t.getShippingState() == 45) {
        	t.setStateTime(t.getAfterBoilTime());
        } else if (t.getShippingState() == 5) {
        	t.setStateTime(t.getCheckBillTime());
        } 
    }
    return t == null ? new ResponseModel().attr("error1", "无法找到该取药条码")
        : takeMedState(t);
  }
  
  private Object takeMedState(RecipeSerial t){
    // TODO 状态设置
    return t.getShippingState() == 95 ? new ResponseModel(t) 
        : new ResponseModel().attr("error1", "该取药条码的药品状态为"+t.getShippingStateName())
                .attr("error2",t.getStateTime());
  }

  @Override
  public Object listTakeMedicine(RecipeSerial recipeSerial) {
    return new ResponseModel(recipeSerialMapper.listTakeMedicine(recipeSerial));
  }

  
}
