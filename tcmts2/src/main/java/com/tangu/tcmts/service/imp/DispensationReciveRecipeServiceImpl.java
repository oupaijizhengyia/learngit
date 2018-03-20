package com.tangu.tcmts.service.imp;

import com.tangu.security.JwtService;
import com.tangu.security.JwtUser;
import com.tangu.security.JwtUserTool;
import com.tangu.tcmts.dao.RecipeFileMapper;
import com.tangu.tcmts.dao.RecipeMapper;
import com.tangu.tcmts.po.DispensationReciveRecipeVO;
import com.tangu.tcmts.po.Employee;
import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.service.DispensationReciveRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */
@Service
public class DispensationReciveRecipeServiceImpl implements DispensationReciveRecipeService {

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private RecipeFileMapper recipeFileMapper;


    @Override
    public Boolean checkEmployeeAuthrity(Employee employee) {

        return true;
    }

    @Override
    public DispensationReciveRecipeVO dispatchRecipe(Employee employee) {
        DispensationReciveRecipeVO dispensationReciveRecipeVO = recipeMapper.getDispensationReciveRecipeVO(employee);
        List<String> recipesByUnion = new ArrayList<>();
        if(dispensationReciveRecipeVO != null){
            dispensationReciveRecipeVO.setDispensationState(1);
            RecipeDO recipeDO = recipeMapper.getRecipeContent(dispensationReciveRecipeVO.getRecipeId());
            List<DispensationReciveRecipeVO> dispensationReciveRecipeVOList = recipeMapper.getDispensationRecipesByUnionIdAndDispenseId(recipeDO);
            for(DispensationReciveRecipeVO d : dispensationReciveRecipeVOList){
                recipesByUnion.add(d.getRecipeCode());
            }
            dispensationReciveRecipeVO.setRecipeList(recipesByUnion);
            dispensationReciveRecipeVO.setRecipeListCount(recipesByUnion.size());
            dispensationReciveRecipeVO.setRecipeFileList(recipeFileMapper.listRecipeFile(recipesByUnion));
        }else{
            RecipeDO recipeDO = recipeMapper.getRecipeIfUrgent();
            if(recipeDO == null){
                recipeDO = recipeMapper.getRecipeIfNotUrgent();
            }
            if(recipeDO == null) {
                return null;
            }
            List<DispensationReciveRecipeVO> dispensationReciveRecipeVOList = recipeMapper.getDispensationRecipesByUnionId(recipeDO);
            for(DispensationReciveRecipeVO drr : dispensationReciveRecipeVOList){
                drr.setEmployeeName(employee.getEmployeeName());
                drr.setEmployeeId(employee.getId());
                drr.setEmployeeCode(employee.getEmployeeCode());
            }
            recipeMapper.updateDispenseIdByUnion(dispensationReciveRecipeVOList);
            employee.setRecipeId(recipeDO.getId());
            for(DispensationReciveRecipeVO d : dispensationReciveRecipeVOList){
                recipesByUnion.add(d.getRecipeCode());
            }
            dispensationReciveRecipeVO = recipeMapper.getDispensationReciveRecipeVO(employee);
            dispensationReciveRecipeVO.setRecipeList(recipesByUnion);
            dispensationReciveRecipeVO.setRecipeListCount(recipesByUnion.size());
            dispensationReciveRecipeVO.setDispensationState(0);
            dispensationReciveRecipeVO.setRecipeFileList(recipeFileMapper.listRecipeFile(recipesByUnion));
        }
        return dispensationReciveRecipeVO;
    }


}
