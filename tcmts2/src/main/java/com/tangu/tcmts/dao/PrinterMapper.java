package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.Printer;
import com.tangu.tcmts.po.PublicDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PrinterMapper {
    @Select("SELECT id,printer_name,mod_time FROM printer ORDER BY seq")
    List<Printer> listPrinter();
    @Select("SELECT id,background_image,mod_time FROM print_page WHERE IFNULL(background_image,'')!=''")
    List<Printer> listPrintBackgroundImg();
    @Select("SELECT id AS value,printer_name AS label FROM printer ")
    List<PublicDO> listDropDownPrinter();
}