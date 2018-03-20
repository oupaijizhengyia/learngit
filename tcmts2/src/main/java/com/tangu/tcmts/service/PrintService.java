package com.tangu.tcmts.service;

import com.tangu.tcmts.po.*;

import java.util.List;
import java.util.Map;

/**
 * @author:djl
 * @Description:打印接口
 * @Date: create in 13:43 2017/10/20
 */
public interface PrintService {
	//订单导出信息查询
    List<RecipePrintObj> listTransferRecipePrintMessages(RecipeReq recipeReq);    
    List<TransferMedicineObj> listTransferRecipeMedicinePrintMessages(List<Long> billIdList);
    List<RecipePrintObj> listTackMedicine(TransferRecipeDO id);
    //处方订单查询
    List<RecipePrintObj> listRecipePrintMessages(RecipeReq recipeReq);
    List<TransferMedicineObj> listRecipeMedicinePrintMessages(List<Integer> recipeIdList);
    
    List<PrintField> listPrintField();
    List<PrintPage> listPrintPage(PrintPage printPage);
    PrintPage getPrintPageById(Integer id);
    List<PrintPage> getRepeatPageName(PrintPage printPage);
    Integer savePrintItem(PrintPage printPage);
    PrintPage getPrintPageByBillType(String billType);
    List<PublicDO> listPrintDropDown(String billType);
    List<Printer> listPrinter();
    List<Printer> listPrintBackgroundImg();
    int deletePrintPage(Integer id);
    int insertPrintPage(PrintPage printPage);
    int updatePrintPage(PrintPage printPage);
    List<PublicDO> listDropDownPrinter();
    Map<String,PrintPage> listPrintPageItem(Map map);
}
