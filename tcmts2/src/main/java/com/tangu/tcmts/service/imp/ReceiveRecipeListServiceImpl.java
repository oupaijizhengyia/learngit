package com.tangu.tcmts.service.imp;

import com.tangu.tcmts.dao.RecipeFileMapper;
import com.tangu.tcmts.dao.RecipeMapper;
import com.tangu.tcmts.dao.TransferRecipeMapper;
import com.tangu.tcmts.po.*;
import com.tangu.tcmts.service.ReceiveRecipeListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */
@Service
public class ReceiveRecipeListServiceImpl implements ReceiveRecipeListService {

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private RecipeFileMapper recipeFileMapper;

    @Autowired
    private TransferRecipeMapper transferRecipeMapper;

    @Override
    public List<ReceiveRecipeListVO> listReceiveRecipeList(TransferRecipeDTO transferRecipeDTO) {
        return transferRecipeMapper.listReceiveRecipeList(transferRecipeDTO);
    }

    @Override
    public ReceiveRecipeListVO getReceiveRecipeDetails(TransferRecipeDTO transferRecipeDTO) {
        ReceiveRecipeListVO receiveRecipeListVO = new ReceiveRecipeListVO();

        TransferRecipeDO transferRecipeDO = transferRecipeMapper.getTransferRecipeContent(transferRecipeDTO.getId());
        RecipeDO recipeDO = recipeMapper.getRecipeContent(transferRecipeDO.getNativeRecipeId());
        List<String> recipeCodes = recipeMapper.getRecipeCodeByUnion(recipeDO);
        List<RecipeFile> recipeFileList = recipeFileMapper.listRecipeFile(recipeCodes);

        receiveRecipeListVO.setRecipeCode(recipeDO.getSysRecipeCode());
        receiveRecipeListVO.setRecipeCodes(recipeCodes);
        receiveRecipeListVO.setRecipeFileList(recipeFileList);
        return receiveRecipeListVO;
    }
}
