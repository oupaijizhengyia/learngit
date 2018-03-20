package com.tangu.tcmts.service.imp;

import com.tangu.tcmts.dao.*;
import com.tangu.tcmts.po.*;
import com.tangu.tcmts.service.PrintService;
import com.tangu.tcmts.util.Constants;
import com.tangu.tcmts.util.RecipeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author:djl
 * @Description:打印接口实现类
 * @Date: create in 13:45 2017/10/26
 */
@Service
public class PrintServiceImpl implements PrintService {
    @Autowired
    private PrintPageMapper printPageMapper;
    @Autowired
    private PrintItemMapper printItemMapper;
    @Autowired
    private PrinterMapper printerMapper;
    @Autowired
    private PrintMapper printMapper;

    @Override
    public List<RecipePrintObj> listTransferRecipePrintMessages(RecipeReq recipeReq) {
        return printMapper.listTransferRecipePrintMessages(recipeReq);
    }

    @Override
    public List<TransferMedicineObj> listTransferRecipeMedicinePrintMessages(List<Long> billIdList) {
        return printMapper.listTransferRecipeMedicinePrintMessages(billIdList);
    }
    

    @Override
    public List<RecipePrintObj> listRecipePrintMessages(RecipeReq recipeReq) {
      return printMapper.listRecipePrintMessages(recipeReq);
    }

    @Override
    public List<TransferMedicineObj> listRecipeMedicinePrintMessages(
    		List<Integer> recipeIdList) {
      return printMapper.listRecipeMedicinePrintMessages(recipeIdList);
    }



    @Override
    public List<PrintField> listPrintField() {
        return printItemMapper.listPrintField();
    }

    @Override
    public List<PrintPage> listPrintPage(PrintPage printPage) {
        return printPageMapper.listPrintPage(printPage);
    }

    @Override
    public PrintPage getPrintPageById(Integer id) {
        PrintPage result = printPageMapper.selectByPrimaryKey(id);
        List<PrintItem> list = printItemMapper.listPrintItemByPageId(id);
        if (list != null) {
        	list.stream().filter(e -> e.getItemPara().startsWith(Constants.PrintItemFields.CUSTOM_KEY))
        	.forEach(e ->{
        		e.setDisplayText(e.getItemPara().substring(Constants.PrintItemFields.CUSTOM_KEY.length()));
        	});
            result.setPrintItemList(list);
        }
        return result;
    }

    @Override
    public List<PrintPage> getRepeatPageName(PrintPage printPage) {
        return printPageMapper.getRepeatPageName(printPage);
    }

    @Override
    public Integer savePrintItem(PrintPage printPage) {
        List<PrintPage> list = printPageMapper.getPrintPageByName(printPage.getPageName());
        if (list != null && list.size() > 0 && list.get(0).getPageName().equals(printPage.getPageName()) &&
                !list.get(0).getId().equals(printPage.getId())) {
            return -1;
        }
        if (printPage.getId() != null && printPage.getId() > 0) {
            PrintPage para = new PrintPage();
            para.setId(printPage.getId());
            para.setPageWidth(printPage.getPageWidth());
            para.setPageHeight(printPage.getPageHeight());
            para.setLayoutDirection(printPage.getLayoutDirection());
            para.setColNumber(printPage.getColNumber());
            para.setColSpacing(printPage.getColSpacing());
            para.setRowSpacing(printPage.getRowSpacing());
            para.setSinglePageNumber(printPage.getSinglePageNumber());
            para.setPrintBackgroundImage(printPage.getPrintBackgroundImage());
            para.setModTime(new Date());
            para.setModUser(printPage.getModUser());
            printPageMapper.updatePrintPage(para);
            printItemMapper.deleteByPageId(printPage.getId());
        } else {
            printPageMapper.insert(printPage);
        }
        if(printPage.getPrintItemList() !=null && printPage.getPrintItemList().size()>0){
        	printItemMapper.insertBatch(printPage);
        }
        return printPage.getId();
    }

    @Override
    public PrintPage getPrintPageByBillType(String billType) {
        PrintPage record = new PrintPage();
        record.setBillType(billType);
        List<PrintPage> list = printPageMapper.listPrintPage(record);
        PrintPage result = null;
        if (list == null || list.size() == 0) {
            return null;
        }
        result = list.get(0);
        List itemList = printItemMapper.listPrintItemByPageId(result.getId());
        if (itemList != null) {
            result.setPrintItemList(itemList);
        }
        return result;
    }

    @Override
    public List<PublicDO> listPrintDropDown(String billType) {
        return printPageMapper.listPrintDropDown(billType);
    }

    @Override
    public List<Printer> listPrinter() {
        return printerMapper.listPrinter();
    }

    @Override
    public List<Printer> listPrintBackgroundImg() {
        return printerMapper.listPrintBackgroundImg();
    }

    @Override
    public int deletePrintPage(Integer id) {
        return printPageMapper.deletePrintPage(id);
    }

    @Override
    public int insertPrintPage(PrintPage printPage) {
        return printPageMapper.insert(printPage);
    }

    @Override
    public int updatePrintPage(PrintPage printPage) {
        return printPageMapper.updatePrintPage(printPage);
    }

    @Override
    public List<PublicDO> listDropDownPrinter() {
        return printerMapper.listDropDownPrinter();
    }

	@Override
	public Map<String,PrintPage> listPrintPageItem(Map map) {
		Map<String,PrintPage> pageMap=new HashMap<>();
		List<PrintPage> list=printPageMapper.listPrintPageByTypes(map);
		List<Integer> pageIdList=list.stream().map(PrintPage::getId).collect(Collectors.toList());
		if(list!=null&&list.size()>0){
			List<PrintItem> itemList=printItemMapper.listPrintItem(pageIdList);
			List<PrintItem> tempList=null;
			for(PrintPage page:list){
				if(itemList!=null){
					tempList=itemList.stream()
							.filter(e ->e.getPageId().equals(page.getId()))
							.collect(Collectors.toList());
					page.setPrintItemList(tempList);
				}
				if(page.getBillType().equals(Constants.PrintType.RECIPE)||
						page.getBillType().equals(Constants.PrintType.RECIPE_TAG)){
					pageMap.put(page.getId().toString(), page);
				}else{
					if(pageMap.get(page.getBillType())==null){
						pageMap.put(page.getBillType(), page);
					}
				}
			}
		}
		return pageMap;
	}

	@Override
	public List<RecipePrintObj> listTackMedicine(TransferRecipeDO record) {
		RecipeReq recipeReq=new RecipeReq();
		Integer NativeRecipeId=printMapper.getNativeRecipeId(record.getId());
		recipeReq.setId(NativeRecipeId);
		List<RecipePrintObj> list=null;
		if(NativeRecipeId!=null && NativeRecipeId>0){
			list=printMapper.listRecipePrintMessages(recipeReq);
		}
		if(list==null||list.size()==0){
			list=printMapper.listTackMedicine(record.getId());
			list.forEach(e ->{
				e.setRecipeSerial(RecipeUtil.leftZero(e.getId()));
			});
		}
		return list;
	}

}
