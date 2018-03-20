package com.tangu.tcmts.service;

import com.tangu.tcmts.po.DispensationReciveRecipeVO;
import com.tangu.tcmts.po.Employee;
import com.tangu.tcmts.po.RecipeDO;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */
public interface DispensationReciveRecipeService {

    Boolean checkEmployeeAuthrity(Employee employee);

    DispensationReciveRecipeVO dispatchRecipe(Employee employee);
}
