package com.tangu.tcmts.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.tangu.tcmts.po.Company;
import com.tangu.tcmts.po.PrintPage;
import com.tangu.tcmts.po.PrintPageObj;
import com.tangu.tcmts.po.RecipePrintObj;
import com.tangu.tcmts.po.TransferMedicineObj;
import lombok.extern.slf4j.Slf4j;

/**
 * 处方打印util
 * 
 * Created by djl on 2017/11/24.
 */
@Slf4j
public class RecipePrintUtil {
	public static void setCompanyInfo(Company company,RecipePrintObj recipePrintObj){
		recipePrintObj.setCompanyTel(company.getCompanyTel());
		recipePrintObj.setCompanyAddress(company.getCompanyAddress());
	}
	
	public static boolean isFindRecipeMedicine(List<String> printTypeList){
 		boolean flag=printTypeList.stream()
 				.anyMatch(e -> !e.equals(Constants.PrintType.RECIPE_TAG));
 		return flag;
 	}
	
	private static TransferMedicineObj getDefualtAccessories(String type) {
		TransferMedicineObj obj=null;
		if(Constants.Accessories.FL_HJF.getName().equals(type)){
			obj=new TransferMedicineObj("1","",
					Constants.Accessories.FL_HJF.getName(),
					Constants.Accessories.FL_HJF.getValue(),"次",Constants.FL_TYPE);
		}else if(Constants.Accessories.FL_BZF.getName().equals(type)){
			obj=new TransferMedicineObj("2","",
					Constants.Accessories.FL_BZF.getName(),
					Constants.Accessories.FL_BZF.getValue(),"次",Constants.FL_TYPE);
		}else{
			obj=new TransferMedicineObj("3","",
					Constants.Accessories.FL_JGF.getName(),
					Constants.Accessories.FL_JGF.getValue(),"次",Constants.FL_TYPE);
		}
		return obj;
    }
	
	private static List<TransferMedicineObj> getAllDefualtAccessories() {
		List<TransferMedicineObj> list=new ArrayList<>();
		list.add(getDefualtAccessories(Constants.Accessories.FL_HJF.getName()));
		list.add(getDefualtAccessories(Constants.Accessories.FL_BZF.getName()));
		list.add(getDefualtAccessories(Constants.Accessories.FL_JGF.getName()));
		return list;
	}
	private static void transferMedicine(TransferMedicineObj obj,TransferMedicineObj defaultObj){
		obj.setUnit(defaultObj.getUnit());
		obj.setMedicineType(defaultObj.getMedicineType());
		obj.setSerialNumber(defaultObj.getSerialNumber());
	}
	/**
	 * 
	 * @param printPageObjList
	 * @param printType
	 * @param pageMap
	 * @return
	 */
	public static List<RecipePrintObj> resetGfRecipePrintObjList(List<RecipePrintObj> printPageObjList,String printType,Map pageMap){
		List<RecipePrintObj> recipeList=new ArrayList<>();
		RecipePrintObj re = null;
		if(printPageObjList==null||printPageObjList.size()==0){
			return null;
		}
		Map<Integer,BigDecimal> sumWeightMap=null;
		PrintPage printPage=null;
		for(int k=0;k<printPageObjList.size();k++){
			sumWeightMap=new HashMap();
			re = printPageObjList.get(k);
			printPage=(PrintPage)pageMap.get(printType);
			int disCount = printPage.getSinglePageNumber();
	     	List<TransferMedicineObj> logList=null;
	     	List<TransferMedicineObj> logisticList=re.getTransferMedicineList();
	     	if(logisticList==null||logisticList.size()==0){
	     		re.setPageCount(1);
          		re.setPageIndex(1);
          		re.setPageIndexString(re.getPageIndex()+"/"+re.getPageCount());
          		re.setTransferMedicineList(new ArrayList<>());
          		recipeList.add(re);
          		continue;
	     	}else{
	     		//按照药品类型排序
	     		StringBuffer sb=new StringBuffer();
	     		logisticList.stream().forEach(e ->{
	     			TransferMedicineObj temp=null;
	     			if(e.getMedicineName().equals(Constants.Accessories.FL_HJF.getName())){
	     				temp=getDefualtAccessories(Constants.Accessories.FL_HJF.getName());
	     				transferMedicine(e,temp);
	     				sb.append("1");
	     			}else if(e.getMedicineName().equals(Constants.Accessories.FL_BZF.getName())){
	     				temp=getDefualtAccessories(Constants.Accessories.FL_BZF.getName());
	     				transferMedicine(e,temp);
	     				sb.append("1");
	     			}else if(e.getMedicineName().equals(Constants.Accessories.FL_JGF.getName())){
	     				temp=getDefualtAccessories(Constants.Accessories.FL_JGF.getName());
	     				transferMedicine(e,temp);
	     				sb.append("1");
	     			}
	     		});
	             if(sb.length()==0){
	            	 logisticList.addAll(getAllDefualtAccessories());
	             }
	             logisticList.sort((TransferMedicineObj m1, 
	            		 TransferMedicineObj m2) -> 
	             		 m1.getMedicineType().compareTo(m2.getMedicineType()));
	     	}
	     	int receiveCount = logisticList.size()/disCount;
	     	int mos = logisticList.size()%disCount;
	     	TransferMedicineObj recipeMedicine = null;
	     	if(mos>0){
	     		receiveCount = receiveCount+1;
	     	}
	 		int n=0;
	     	int type=0;
	     	String totalWeightStr="";
	 		for(int i=0; i<receiveCount; i++){
	     		logList = new ArrayList();
	     		for(int j=0; j<disCount; j++){
	     			if(logisticList.size()>i*disCount+j){
	     				recipeMedicine=(TransferMedicineObj)logisticList.get(i*disCount+j).clone();
	     				if(j>0 && type>0 &&type!=recipeMedicine.getMedicineType()){
	     					n=1;
	     					if(recipeMedicine.getMedicineType()>1){
	     						logList.add(new TransferMedicineObj("","","",BigDecimal.ZERO,"",Constants.FL_TYPE));
	     					}
	     				}
	     				if(n==0){
	     					n=1;
	     				}
	     				if(recipeMedicine.getMedicineType()<99){
	     					recipeMedicine.setSerialNumber(""+n++);
	     				}
	     				logList.add(recipeMedicine);
	     				if(recipeMedicine.getMedicineType()>0 ){
	     					type=recipeMedicine.getMedicineType();
	     					if(recipeMedicine.getMedicineType()<99 ){
	     						if(sumWeightMap.get(type)==null){
	     							sumWeightMap.put(type,recipeMedicine.getWeight());
	     						}else{
	     							sumWeightMap.put(type,sumWeightMap.get(type).add(recipeMedicine.getWeight()));
	     						}
	     					}
	     				}
	         		}else{
	     				break;
	     			}	
	 			}	
	     		RecipePrintObj receiving=null;
 				try {
 					receiving = (RecipePrintObj) re.clone();
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
	     		receiving.setPageCount(receiveCount);
          		receiving.setPageIndex(i+1);
          		receiving.setPageIndexString(receiving.getPageIndex()+"/"+receiving.getPageCount());
          		receiving.setTransferMedicineList(logList);
          		recipeList.add(receiving);
			}
	 		if(sumWeightMap!=null&&sumWeightMap.size()>0){
	 			final StringBuffer sbf=new StringBuffer();
	 			sumWeightMap.forEach((key,v) ->
	 				sbf.append(Constants.MedicineType.getTypeName(key)+"："+v+"g		")
	 			);
	 			totalWeightStr+=sbf.toString();
	 		}
			for(int p=0;p<recipeList.size();p++){
				if(recipeList.get(p).getId().equals(re.getId())){
					recipeList.get(p).setTotalWeightPart(totalWeightStr);
				}
			}
		}
		return recipeList;
	}
	
 	public static List<RecipePrintObj> resetRecipePrintObjList(List<RecipePrintObj> printPageObjList,String printType,Map pageMap){
 		List<RecipePrintObj> recipeList=new ArrayList<>();
		RecipePrintObj re= null;
 		int recieSize=printPageObjList.size();
 		PrintPage printPage=null;
 		for(int k=0;k<recieSize;k++){
 			re = printPageObjList.get(k);
 			int disCount =0; 
 			if(re.getBelong().intValue()==1){
 				printPage=(PrintPage) pageMap.get(re.getOutpatientRecipePrint().toString());
 			}else{
 				printPage=(PrintPage) pageMap.get(re.getHospitalRecipePrint().toString());
 			}
 			if(printPage==null){
 				continue;
 			}
 			disCount=printPage.getSinglePageNumber()==null?1:printPage.getSinglePageNumber();
          	
          	List<TransferMedicineObj> logisticList=re.getTransferMedicineList();
          	
          	if(logisticList==null||logisticList.size()==0){
          		re.setPageCount(1);
          		re.setPageIndex(1);
          		re.setPageIndexString(re.getPageIndex()+"/"+re.getPageCount());
          		re.setTransferMedicineList(new ArrayList<>());
          		recipeList.add(re);
          		continue;
          	}
          	int receiveCount = logisticList.size()/disCount;
          	int mos = logisticList.size()%disCount;
          	TransferMedicineObj recipeMedicine = null;
          	if(mos>0){
          		receiveCount = receiveCount+1;
          	}
          	List logList=null;
          	for(int i=0; i<receiveCount; i++){
          		logList =new ArrayList<>();
          		for(int j=0; j<disCount; j++){
          			if(logisticList.size()>i*disCount+j){
 						recipeMedicine=(TransferMedicineObj)logisticList.get(i*disCount+j).clone();
 						if(recipeMedicine==null){
 							continue;
 						}
          				/**
          				 * 暂时注释 from djl
          				 */
// 	 						if(systemModel.isSetNewMedicine&&recipeMedicine.ypgg!=null){
// 	 							recipeMedicine.medicineName=recipeMedicine.medicineName+recipeMedicine.ypgg;
// 	 						}
          				logList.add(recipeMedicine);
          			}else{
          				break;
          			}
          		}
          		RecipePrintObj receiving=null;
 				try {
 					receiving = (RecipePrintObj) re.clone();
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 				log.info("logList.size="+logList.size());
          		receiving.setPageCount(receiveCount);
          		receiving.setPageIndex(i+1);
          		receiving.setPageIndexString(receiving.getPageIndex()+"/"+receiving.getPageCount());
          		receiving.setTransferMedicineList(logList);
          		recipeList.add(receiving);
          	}
 		}
 		return recipeList;
 	}
 	public static boolean isBoil(Integer boilType){
 		if(boilType==Constants.DJ||boilType==Constants.GF){
 			return true;
 		}
 		return false;
 	}
 	public static int getHosPitalTagNum(RecipePrintObj recipe){
 		int num=1;
 		if(recipe.getTotalPackagePaste()==null||recipe.getTotalPackagePaste()==0){
 			return num;
 		}
 		if(recipe.getTotalPackagePaste()<=7){
 			return 2;
 		}
 		if(recipe.getTotalPackagePaste()>7 && recipe.getTotalPackagePaste()<=14){
 			return 4;
 		}
 		if(recipe.getTotalPackagePaste()>14 && recipe.getTotalPackagePaste()<=20){
 			return 6;
 		}
 		return 8;
 	}
 	public static int getTagNum(RecipePrintObj recipe){
 		int num=1;
 		if(isBoil(recipe.getBoilType())){
 			if(recipe.getBelong().intValue()==1){
				num=recipe.getOutpatientBoilNum();
				if(recipe.getOutpatientBoilType().equals(Constants.QUANTITY_PACKAGE)){
					num+=recipe.getPackagePaste()*recipe.getQuantity();
				}else if(recipe.getOutpatientBoilType().equals(Constants.QUANTITY)){
					num+=recipe.getQuantity();
				}
			}else{
				num=recipe.getHospitalBoilNum();
				if(recipe.getHospitalBoilType().equals(Constants.QUANTITY_PACKAGE)){
					num+=recipe.getPackagePaste()*recipe.getQuantity();
				}else if(recipe.getHospitalBoilType().equals(Constants.QUANTITY)){
					num+=recipe.getQuantity();
				}
			}
		}else{
			if(recipe.getBelong().intValue()==1){
				num=recipe.getOutpatientTagNum();
				if(recipe.getOutpatientTagType().equals(Constants.QUANTITY_PACKAGE)){
					num+=recipe.getPackagePaste()*recipe.getQuantity();
				}else if(recipe.getOutpatientTagType().equals(Constants.QUANTITY)){
					num+=recipe.getQuantity();
				}
			}else{
				num=recipe.getHospitalTagNum();
				if(recipe.getHospitalTagType().equals(Constants.QUANTITY_PACKAGE)){
					num+=recipe.getPackagePaste()*recipe.getQuantity();
				}else if(recipe.getHospitalTagType().equals(Constants.QUANTITY)){
					num+=recipe.getQuantity();
				}
			}
		}
 		return num;
 	}
 	
 	public static String setOddNumber(String code){
		if(StringUtils.isBlank(code)){
			return "";
		}
		if(code.length()<=3){
			return code;
		}
		String spaceCode = "";
		int a = code.length()/3;
		if(code.length()%3>0){
			a++;
		}
		for(int i=1;i<=a;i++){
			if(i==a){
				spaceCode+=code.substring((i-1)*3,code.length());
			}else{
				spaceCode+=code.substring((i-1)*3,i*3)+"  ";
			}
		}
		return spaceCode;
 	}
 	
 	public static void setTotalAddress(RecipePrintObj recipe){
 		StringBuffer sb=new StringBuffer();
 		if(StringUtils.isNotBlank(recipe.getProvince())){
 			sb.append(recipe.getProvince());
 		}
 		if(StringUtils.isNotBlank(recipe.getCity())){
 			sb.append(recipe.getCity());
 		}
 		if(StringUtils.isNotBlank(recipe.getRegion())){
 			sb.append(recipe.getRegion());
 		}
 		if(StringUtils.isNotBlank(recipe.getRecipientAddress())){
 			sb.append(recipe.getRecipientAddress());
 		}
 		if(sb.length()>0){
 			recipe.setTotalAddress(sb.toString());
 		}
 	}
 	public static List<PrintPageObj> setRecipePrintList(List<RecipePrintObj> printPageObjList,String printType,Map pageMap,Integer tagNum,List<Integer> recordIds) {
 		PrintPage page=null;
 		List<PrintPageObj> pageObjs=new ArrayList<>();
 		PrintPageObj printObj=null;
 		List<RecipePrintObj> recipeList=null;
 		if(printType.equals(Constants.PrintType.RECIPE)){
 			recipeList=resetRecipePrintObjList(printPageObjList,printType,pageMap);
 		}else if(printType.equals(Constants.PrintType.CREAM_FORMULA_DETAIL)){
 			recipeList=resetGfRecipePrintObjList(printPageObjList,printType,pageMap);
 		}
 		else{
 			recipeList=printPageObjList;
 		}
 		int repeat=0;
 		for(RecipePrintObj recipe:recipeList){
 			if(printType.equals(Constants.PrintType.RECIPE_PROCESS)&&!recipe.getIsPrintProcess()){
 				continue;
 			}
 			if(recipe.getTotalWeight()==null){
 				recipe.setTotalWeight(BigDecimal.ZERO);
 			}
 			recipe.setWeightEvery(
 					recipe.getTotalWeight().divide(BigDecimal.valueOf(recipe.getQuantity()), 3, BigDecimal.ROUND_HALF_UP)
 					);
 			setTotalAddress(recipe);
 			if(printType.equals(Constants.PrintType.SF_BILL)&&
 					StringUtils.isNotBlank(recipe.getLogisticsCode())){
 				recipe.setOddNumber(setOddNumber(recipe.getLogisticsCode()));
 				recipe.setBusinessTypeName(Constants.BusinessType.getBusinessTypeName(recipe.getBusinessType()));
 			}
 			if(printType.equals(Constants.PrintType.RECIPE_TAG)||
 				printType.equals(Constants.PrintType.RECIPE)){
 				if(recipe.getBelong().intValue()==1){
 					if(printType.equals(Constants.PrintType.RECIPE)){
						page=(PrintPage)pageMap.get(recipe.getOutpatientRecipePrint().toString());
					}else{
						page=(PrintPage)pageMap.get(recipe.getOutpatientTagPrint().toString());
					}
 				}else{
 					if(printType.equals(Constants.PrintType.RECIPE)){
						page=(PrintPage)pageMap.get(recipe.getHospitalRecipePrint().toString());
					}else{
						page=(PrintPage)pageMap.get(recipe.getHospitalTagPrint().toString());
					}
 				}
 				
 			}else{
 				page=(PrintPage)pageMap.get(printType);
 			}
 			if(page==null){
 				continue;
 			}
 			printObj = PrintUtil.getPrintableCanvasObj(page,recipe);
 			if(printType.equals(Constants.PrintType.RECIPE)||
 					printType.equals(Constants.PrintType.CREAM_FORMULA_DETAIL)	){
 				setMedicine(page, recipe, printObj.getPrintItemList());
 				recordIds.add(recipe.getId());
 			}
 			if(printType.equals(Constants.PrintType.RECIPE_TAG)||
 					printType.equals(Constants.PrintType.RECIPE_TAG_NF)||
 					printType.equals(Constants.PrintType.RECIPE_TAG_WY)||
 					printType.equals(Constants.PrintType.RECIPE_TAG_GC)){
 				((List<JSONObject>)printObj.getPrintItemList()).stream()
 				.filter(e -> e.containsKey(Constants.PrintItemFields.BLACK) && recipe.getCarryId() !=2)
 				.forEach(e -> {
 					e.remove(Constants.PrintItemFields.BLACK);
 				});
 				if(tagNum==null||tagNum==0){
 					if(printType.equals(Constants.PrintType.RECIPE_TAG)){
 						tagNum=getTagNum(recipe);
 					}else{
 						tagNum=getHosPitalTagNum(recipe);
 					}
 				}
 				if(page.getSinglePageNumber()!=null&&page.getSinglePageNumber()>1){
 					repeat=tagNum/page.getSinglePageNumber();
 					if(tagNum%page.getSinglePageNumber()>0){
 						repeat++;
 					}
 				}else{
 					repeat=tagNum;
 				}
 				printObj.setRepeat(repeat);
 			}
 			pageObjs.add(printObj);
 		}
        return pageObjs;
    }
 	
 	public static void setMedicine(PrintPage page,RecipePrintObj recipe,List printItemList){
 		JSONObject item=null;
 		List<JSONObject> newItemList=new ArrayList<>();
 		List<TransferMedicineObj> medicineList=recipe.getTransferMedicineList();
		TransferMedicineObj recipeMedicine = null;
		JSONObject copyItem = new JSONObject();
		int colSpacing=page.getColSpacing();
		int rowSpacing=page.getRowSpacing();
		int maxCol=page.getColNumber();
		Map paraObj =null;
		for(int i=0;i<printItemList.size();i++){
			item =(JSONObject) printItemList.get(i);
			if(Constants.RECIPE_MEDICINE_LIST.equals(item.get(Constants.PrintItemFields.FIELD_SOURCE))){
				newItemList.add(item);
			}
		}
		if(page.getLayoutDirection()==Constants.HORIZENTAL){
			int row=1;
			int col=0;
			for(int i=0;i<newItemList.size();i++){
				row=1;
				col=0;
				item = newItemList.get(i);
				for(int j=0;j<medicineList.size();j++){
					recipeMedicine = medicineList.get(j);
					copyItem = (JSONObject) item.clone();
					if(copyItem==null){
						log.info("copyItem is null");
						continue;
					}
					//药品
					col++;
					copyItem.put(Constants.PrintItemFields.X, (Integer)copyItem.get(Constants.PrintItemFields.X)+(col-1)*colSpacing);
					copyItem.put(Constants.PrintItemFields.Y, (Integer)copyItem.get(Constants.PrintItemFields.Y)+(row-1)*rowSpacing);
					paraObj = PrintUtil.setFieldParaObject(copyItem.get(Constants.PrintItemFields.ITEM_PARA).toString());
					copyItem.put(Constants.PrintItemFields.TEXT, 
							PrintUtil.getObjectValue(recipeMedicine,PrintUtil.getFieldPara("field", paraObj))
							);
					if(col==maxCol){
						row++;
						col=0;
					}
					printItemList.add(copyItem);
				}
			}
		}else{
			int row=0;
			int col=1;
			for(int i=0;i<newItemList.size();i++){
				row=0;
				col=1;
				item = (JSONObject) newItemList.get(i);
				for(int j=0;j<medicineList.size();j++){
					recipeMedicine = medicineList.get(j);
					copyItem = (JSONObject) item.clone();
					if(copyItem==null){
						log.info("copyItem is null");
						continue;
					}
					//药品
					row++;
					copyItem.put(Constants.PrintItemFields.X, (Integer)copyItem.get(Constants.PrintItemFields.X)+(col-1)*colSpacing);
					copyItem.put(Constants.PrintItemFields.Y, (Integer)copyItem.get(Constants.PrintItemFields.Y)+(row-1)*rowSpacing);
					paraObj = PrintUtil.setFieldParaObject(copyItem.get(Constants.PrintItemFields.ITEM_PARA).toString());
					copyItem.put(Constants.PrintItemFields.TEXT, 
							PrintUtil.getObjectValue(recipeMedicine,PrintUtil.getFieldPara("field", paraObj))
							);
					if(row==maxCol){
						col++;
						row=0;
					}
					printItemList.add(copyItem);
				}
			}	
		}
 	}
 	
}
