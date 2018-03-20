package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.PrintField;
import com.tangu.tcmts.po.PrintPage;
import com.tangu.tcmts.po.PublicDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PrintPageMapper {
    @Delete("DELETE FROM print_page where id=#{id}")
    int deletePrintPage(Integer id);

    int updatePrintPage(PrintPage printPage);

    int insert(PrintPage record);

    PrintPage selectByPrimaryKey(Integer id);

    List<PrintPage> getRepeatPageName(PrintPage printPage);

    int updateByPrimaryKey(PrintPage record);

    List<PrintPage> listPrintPage(PrintPage printPage);
    List<PrintField> listPrintField();
    @Select("SELECT id AS value,page_name AS label FROM print_page where bill_type=#{billType}")
    List<PublicDO> listPrintDropDown(String billType);

    @Select("SELECT * FROM print_page where page_name=#{value}")
    List<PrintPage> getPrintPageByName(String pageName);
    
    List<PrintPage> listPrintPageByTypes(Map map);

}