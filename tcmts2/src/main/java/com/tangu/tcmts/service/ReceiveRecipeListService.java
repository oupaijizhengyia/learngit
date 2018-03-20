package com.tangu.tcmts.service;

import com.tangu.tcmts.po.ReceiveRecipeListVO;
import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.RecipeDTO;
import com.tangu.tcmts.po.TransferRecipeDTO;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */

public interface ReceiveRecipeListService {

    List<ReceiveRecipeListVO> listReceiveRecipeList(TransferRecipeDTO transferRecipeDTO);

    ReceiveRecipeListVO getReceiveRecipeDetails(TransferRecipeDTO transferRecipeDTO);
    
    
}
