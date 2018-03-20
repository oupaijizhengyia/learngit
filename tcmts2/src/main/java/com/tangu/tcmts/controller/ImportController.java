package com.tangu.tcmts.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tangu.tcmts.po.PriceTempletDetailDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "import", description = "导入")
@RestController
@RequestMapping("/import")
public class ImportController {
  
    @ApiOperation(value = "价格模板-导入excel", notes = "传入file")
    @RequestMapping(value = "importTemplet", method = RequestMethod.GET)
    public Object importTemplet(@RequestParam(value = "file") MultipartFile file){
        List<PriceTempletDetailDTO> templetList = importExcel(file,0,1,PriceTempletDetailDTO.class);
        if(templetList.get(0) != null){
          if(StringUtils.isBlank(templetList.get(0).getMedicineName()) 
              || templetList.get(0).getPriceStart() == null){
            // TODO 返回必需字段提醒
          }
        }
        // 查出模板Id
        // 更新模板价格
        return null;
    }
  
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass){
      if (file == null){
          return null;
      }
      ImportParams params = new ImportParams();
      params.setTitleRows(titleRows);
      params.setHeadRows(headerRows);
      List<T> list = null;
      try {
          list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
      } catch (Exception e) {
      }
      return list;
    }
}
