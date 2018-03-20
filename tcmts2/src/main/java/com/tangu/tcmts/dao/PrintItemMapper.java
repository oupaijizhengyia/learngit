package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.PrintField;
import com.tangu.tcmts.po.PrintItem;
import com.tangu.tcmts.po.PrintPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

@Mapper
public interface PrintItemMapper {
    @Delete("delete from print_item where page_id = #{value}")
    int deleteByPageId(Integer id);
    int insertBatch(PrintPage record);
    List<PrintField> listPrintField();
    List<PrintItem> listPrintItemByPageId(Integer id);
    
    List<PrintItem> listPrintItem(List list);
}