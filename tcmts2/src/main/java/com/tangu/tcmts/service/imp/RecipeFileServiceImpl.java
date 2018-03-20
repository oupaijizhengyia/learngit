/**
 * FileName: RecipeFileServiceImpl
 * Author: yeyang
 * Date: 2018/1/31 11:16
 */
package com.tangu.tcmts.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tangu.tcmts.dao.RecipeFileMapper;
import com.tangu.tcmts.po.RecipeFile;
import com.tangu.tcmts.service.RecipeFileService;
import com.tangu.tcmts.util.CommonUtil;

@Service
public class RecipeFileServiceImpl implements RecipeFileService{

  @Value("${tangu.file.uploadPath}")
  private String path;
  @Value("${tangu.file.recipePhotoFiles}")
  private String recipeImg;
  
  @Autowired
  RecipeFileMapper recipeFileMapper; 
  
  @Override
  public boolean insertFiles(List<RecipeFile> record) {
    String file = CommonUtil.getFileName(recipeImg);
    record.forEach(p->{
      p.setFilePath(file);
      String absoluteFile = path + file;
      CommonUtil.encodeStr(p.getFileBase64(),absoluteFile);        
    });
    int i = recipeFileMapper.addRecipeFile(record);
    return i>0;
  }

  @Override
  public boolean deleteFiles(List<RecipeFile> record) {
    record.forEach(p->{
      CommonUtil.deleteFile(p.getFilePath());
    });
    int i = recipeFileMapper.deleteByIds(record);
    return i>0;
  }

    @Override
    public int deleteRecipeFile(Integer id) {
        return recipeFileMapper.deleteRecipeFile(id);
    }

    @Override
    public List<RecipeFile> listRecipeFile(List<String> recipeIds) {
        return recipeFileMapper.listRecipeFile(recipeIds);
    }

    @Override
    public int addRecipeFile(List<RecipeFile> recipeFileList) {
        return recipeFileMapper.addRecipeFile(recipeFileList);
    }

    @Override
    public boolean insertFile(RecipeFile record) {
      int i =0;
      String file = CommonUtil.getFileName(recipeImg);
      record.setFilePath(file);
      String absoluteFile = path + file;
      try{ 
        CommonUtil.encodeStr(record.getFileBase64(), 
            absoluteFile/*"C:\\Users\\Administrator\\Desktop\\1.png"*/);
        i = recipeFileMapper.insertRecipeFile(record);
      } catch(Exception e){
        return false;
      }
      return i > 0;
    }

    @Override
    public boolean deleteFile(RecipeFile record) {
      try{
        if(!CommonUtil.deleteFile(record.getFilePath())){
          return false;
        }
        recipeFileMapper.deleteRecipeFile(record.getId());
      } catch(Exception e){
        return false;
      }
      return true;
    }
    
}
