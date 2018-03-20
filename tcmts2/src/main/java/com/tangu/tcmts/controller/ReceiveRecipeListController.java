package com.tangu.tcmts.controller;

import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.dao.RecipeMapper;
import com.tangu.tcmts.dao.TransferRecipeMapper;
import com.tangu.tcmts.po.ReceiveRecipeListVO;
import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.RecipeDTO;
import com.tangu.tcmts.po.RecipeFile;
import com.tangu.tcmts.po.TransferRecipeDTO;
import com.tangu.tcmts.service.ReceiveRecipeListService;
import com.tangu.tcmts.service.RecipeFileService;
import com.tangu.tcmts.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/1/29.
 */


@Api(value = "reciveRecipeList", description = "接方")
@RestController
@RequestMapping(value = "/reciveRecipeList")
public class ReceiveRecipeListController {

    @Autowired
    private ReceiveRecipeListService receiveRecipeListService;
    
    @Autowired
    private RecipeFileService recipeFileService;

    @Autowired
    private TransferRecipeMapper transferRecipeMapper;
    
    @Autowired
    private RecipeMapper recipeMapper;
    
    @ApiOperation(value = "接方列表",
                  notes = "传入startDate, endDate, ifReceive, boilType, recipientName, recipeCode",
                  response = ReceiveRecipeListVO.class)
    @RequestMapping(value = "listReceiveRecipe", method = RequestMethod.POST)
    public Object listReceiveRecipe(@RequestBody TransferRecipeDTO transferRecipeDTO){
        return new ResponseModel(receiveRecipeListService.listReceiveRecipeList(transferRecipeDTO));
    }

    @ApiOperation(value = "获取接方列表详情",
            notes = "id(transfer_recipe表的主键)",
            response = ReceiveRecipeListVO.class)
    @RequestMapping(value = "getReceiveRecipeDetails", method = RequestMethod.POST)
    public Object getReceiveRecipeDetails(@RequestBody TransferRecipeDTO transferRecipeDTO){
        return new ResponseModel(receiveRecipeListService.getReceiveRecipeDetails(transferRecipeDTO));
    }



    
    @ApiOperation(value = "保存拍照接方", 
        notes = "传入id, isUrgent, recipeCode, "
            + "insertRecipeFile recipeId, recipeCode, fileBase64,"
            + "deleteRecipeFile id, filePath")
    @RequestMapping(value = "savePhotoRecive", method = RequestMethod.POST)
    public Object savePhotoRecive(@RequestBody RecipeDTO record){
      /*
       * 1.判断参数
       * 2.生成处方  
       * 3.保存图片   
            a.照片保存 删除
       *    b.插入更新 recipe-file表（recipe_id,file_path）
       * 4.建立关联
       */
      List<RecipeFile> insertRecipeFile = record.getInsertRecipeFile();
      List<RecipeFile> deleteRecipeFile = record.getDeleteRecipeFile();
      boolean isNotNullInsertRecipeFile = CollectionUtils.isNotEmpty(insertRecipeFile);
      boolean isNotNullDeleteRecipeFile = CollectionUtils.isNotEmpty(deleteRecipeFile);
      Map<String,Integer> map = null;
      if(record.getId() == null && !isNotNullInsertRecipeFile){
        //图片为空 返回错误信息
      }else if(record.getId() == null && isNotNullInsertRecipeFile){
      //TODO 新增处方  写好后搬到service
        Set<String> s = insertRecipeFile
            .stream()
            .collect(Collectors
                .mapping(RecipeFile::getRecipeCode,Collectors.toSet()));
        //TODO 批量查订单 by recipeCode
        List<RecipeDO> l = transferRecipeMapper.listByRecipeCode(s);
        //TODO 批量查药品详情
        
        //TODO 处方参数初始化
        l.forEach(p->{
          p.setIsUrgent(record.getIsUrgent());
//          p.in
        });
        //TODO 批量插入处方 
        recipeMapper.insertRecipeListMapper(l);
        //TODO 批量插入药品详情
        
        //TODO 取出Map recipeCode recipeId ok
        l.stream().forEach(p->{
          map.put(p.getRecipeCode(), p.getId());
        });
        //TODO 插入对应recipeId ok
        insertRecipeFile.forEach(p ->{
          p.setRecipeId(map.get(p.getRecipeCode()));
        });
      }else{
        String recipeCode = record.getRecipeCode();
        if(StringUtils.isBlank(recipeCode)
            || record.getId() == null){
          return new ResponseModel().
              attr(ResponseModel.KEY_ERROR, Constants.MISSING_PARAM);
        }
        //TODO recipeCode 是否匹配
        if(isNotNullInsertRecipeFile){
          if(insertRecipeFile.stream().anyMatch(p->{
            return !p.getRecipeCode().equals(recipeCode);
          })){
            return new ResponseModel().
                attr(ResponseModel.KEY_ERROR, "处方与图片recipeCode不匹配");
          }
        }
        if(isNotNullDeleteRecipeFile){
          if(deleteRecipeFile.stream().anyMatch(p->{
            return !p.getRecipeCode().equals(recipeCode);
          })){
            return new ResponseModel().
                attr(ResponseModel.KEY_ERROR, "处方与图片recipeCode不匹配");
          }
        }
        //TODO 插入recipeId
        insertRecipeFile.stream().forEach(p->{
          p.setRecipeId(record.getId());
        });
      }
      
      boolean fileSuccess = true;
      //TODO 新增照片
      if(isNotNullInsertRecipeFile){
        fileSuccess = recipeFileService.insertFiles(insertRecipeFile);
      }
      //TODO 删除照片
      if(isNotNullDeleteRecipeFile){
        fileSuccess = recipeFileService.deleteFiles(deleteRecipeFile);
      }
      //TODO 关联
      
      return Constants.operation(fileSuccess, "操作成功", "操作失败");
    }
    
    @ApiOperation(value = "上传照片", 
        notes = "传入recipeId, fileBase64")
    @RequestMapping(value = "uploadPhoto", method = RequestMethod.POST)
    public Object uploadPhoto(@RequestBody RecipeFile record){
      boolean fileSuccess = recipeFileService.insertFile(record);
      return Constants.operation(fileSuccess, "上传成功", "上传失败");
    }
    
    @ApiOperation(value = "删除照片", 
        notes = "传入id")
    @RequestMapping(value = "deletePhoto", method = RequestMethod.POST)
    public Object deletePhoto(@RequestBody RecipeFile record){
      boolean fileSuccess = recipeFileService.deleteFile(record);
      return Constants.operation(fileSuccess, "删除成功", "删除失败");
    }
}
