package com.tangu.tcmts.util;

import static java.math.BigDecimal.ROUND_DOWN;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import com.tangu.tcmts.po.MedicineDO;
import com.tangu.tcmts.po.MedicineMaxDose;
import com.tangu.tcmts.po.MedicineRelationDO;
import com.tangu.tcmts.po.PriceTempletDetailDTO;
import com.tangu.tcmts.po.RecipeDO;
import com.tangu.tcmts.po.RecipeMedicine;
import com.tangu.tcmts.po.RecipePatrol;
import com.tangu.tcmts.po.SpecialDiscount;
import com.tangu.tcmts.po.SysLookup;
import com.tangu.tcmts.po.TransferRecipeDO;

/**
 * Created by djl on 2017/11/17.
 */
public class RecipeUtil {
//	private static Pattern pattern = Pattern.compile("\\d+");
    private static Integer DIV_SCALE = 3;
    
    public static String getTakeTypePrintKey(Integer takeType){
    	if(takeType==null){
    		return Constants.PrintType.RECIPE_TAG; 
    	}
    	if(takeType==Constants.TAKE_TYPE_NF){
			 return Constants.PrintType.RECIPE_TAG_NF;
	    }
    	if(takeType==Constants.TAKE_TYPE_WY){
		   return Constants.PrintType.RECIPE_TAG_WY;
	    }
    	if(takeType.equals(Constants.TAKE_TYPE_GC)){
		   return Constants.PrintType.RECIPE_TAG_GC;
	   }
       return Constants.PrintType.RECIPE_TAG; 
    }
    /**
     * 配伍禁忌检测
     *
     * @param list
     * @param tabooUnionMap
     * @return
     */
    public static List<SysLookup> getMedicineTaboo(List<RecipeDO> list, Map<String, String> tabooUnionMap) {
        List<SysLookup> result = new ArrayList<SysLookup>();
        Map<String, SysLookup> medicineMap = new HashedMap();
        String key = null;
        SysLookup sl = null;
        Boolean flag=null;
        RecipeDO record=null;
        for (int i=0;i<list.size();i++) {
        	record=list.get(i);
        	flag=false;
            if (StringUtils.isNotBlank(record.getRecipeSerial())) {
                key = "序号:" + record.getRecipeSerial();
            } else if (StringUtils.isNotBlank(record.getRecipeCode())) {
                key = "处方号:" + record.getRecipeSerial();
            } else if (StringUtils.isNotBlank(record.getRecipeBh())) {
                key = "编号:" + record.getRecipeBh();
            }
            if (CollectionUtils.isNotEmpty(record.getRecipeMedicineList())) {
            	for (RecipeMedicine rm : record.getRecipeMedicineList()) {
                    for (RecipeMedicine  rm2: record.getRecipeMedicineList()) {
                        if (!rm.getMedicineId().equals(rm2.getMedicineId())) {
                            if (tabooUnionMap.get(rm.getMedicineId() + "_" + rm2.getMedicineId()) != null||
                            		tabooUnionMap.get(rm2.getMedicineId() + "_" + rm.getMedicineId()) != null) {
                                if (medicineMap.get(key) == null) {
                                    sl = new SysLookup();
                                    if (record.getId() != null){
                                        sl.setValue(record.getId().toString());
                                    }
                                    sl.setExt1(key);
                                    sl.setExt2(rm.getName_medicineName() + "和" + rm2.getName_medicineName());
                                    medicineMap.put(key, sl);
                                } else {
                                	if(medicineMap.get(key).getExt2().indexOf(rm2.getName_medicineName() + "和" + rm.getName_medicineName())==-1 &&
                                			medicineMap.get(key).getExt2().indexOf(rm.getName_medicineName() + "和" + rm2.getName_medicineName())==-1){
                                		medicineMap.get(key).setExt2(medicineMap.get(key).getExt2() + "," + rm.getName_medicineName() + "和" + rm2.getName_medicineName());
                                	}
                                }
                                flag=true;
                            }
                        }
                    }
                }
            }
            if(flag){
            	list.remove(i);
            	i--;
            }
        }
        if (medicineMap.size() > 0) {
            Collection<SysLookup> valueCollection = medicineMap.values();
            result = new ArrayList(valueCollection);
        }
        return result;
    }

    public static boolean setPrice(RecipeDO recipe,RecipeMedicine rm2,PriceTempletDetailDTO mp ) {
    	boolean flag=false;
        if(rm2.getMedicineId().intValue()==mp.getMedicineId().intValue()
                &&recipe.getDealDate().compareTo(mp.getPriceStart())>=0
                &&(mp.getPriceEnd()!=null?(recipe.getDealDate().compareTo(mp.getPriceEnd())<=0):true)) {
        		flag=true;
                rm2.setTradPrice(mp.getTradePrice()==null?BigDecimal.ZERO:mp.getTradePrice());
                rm2.setRetailPrice(mp.getRetailPrice()==null?BigDecimal.ZERO:mp.getTradePrice());
        }
        return flag;
    }

    /**
     * 设置特殊打印
     *
     * @param recipe
     */
    public static void setSpecialPrint(RecipeDO recipe) {
        if (recipe.getRecipeMedicineList() == null || recipe.getRecipeMedicineList().size() == 0) {
            return;
        }
        RecipeMedicine rm = null;
        String specialPrint = "";
        String otherPackage = "";
        try {
        	for (int i = 0; i < recipe.getRecipeMedicineList().size(); i++) {
                rm = (RecipeMedicine) recipe.getRecipeMedicineList().get(i);
                if (StringUtils.isBlank(rm.getSpecialBoilType())) {
                	continue;
                }
                if (isOtherPackage(rm.getSpecialBoilType()) ) {
                    otherPackage = otherPackage +
                            ((StringUtils.isNotBlank(otherPackage) && StringUtils.isNotBlank(rm.getSpecialBoilType())) ? "|" : "")
                            + rm.getMedicineName() + (rm.getWeightEvery() + "*").replaceAll("[.][0]+$", "") + recipe.getQuantity();
                }
                if (isSpecialBoil(rm.getSpecialBoilType()) ) {
                    specialPrint = specialPrint +
                            ((StringUtils.isNotBlank(specialPrint) && StringUtils.isNotBlank(rm.getSpecialBoilType())) ? "|" : "")
                            + rm.getSpecialBoilType();
                }
            }
             recipe.setSpecialPrint(specialPrint);
             /**
              * 药品有另包取
              */
             if(StringUtils.isNotBlank(otherPackage)){
            	 recipe.setOtherPackage(otherPackage);
             }
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public static boolean isSpecialBoil(String value){
    	if(StringUtils.isBlank(value)){
    		return false;
    	}
    	 if (value.indexOf(Constants.SpecialBoil.SEPECIAL_BOIL_XJ) > -1 ||
    			 value.indexOf(Constants.SpecialBoil.SEPECIAL_BOIL_HX) > -1 ||
    			 value.indexOf(Constants.SpecialBoil.SEPECIAL_BOIL_BJ) > -1 ||
    			 value.indexOf(Constants.SpecialBoil.SEPECIAL_BOIL_LB) > -1 ||
    			 value.indexOf(Constants.SpecialBoil.SEPECIAL_BOIL_TJ) > -1) {
    		 return true;
    	 }
    	return false;
    }
    
    public static boolean isOtherPackage(String value){
    	if(StringUtils.isBlank(value)){
    		return false;
    	}
    	 if (value.indexOf(Constants.SpecialBoil.SEPECIAL_BOIL_LB) > -1 ||
    			 value.indexOf(Constants.SpecialBoil.SEPECIAL_BOIL_TJ) > -1) {
    		 return true;
    	 }
    	return false;
    }
    
    public static void setReceiveRecipe(List<PriceTempletDetailDTO> medicinePriceList,List<SpecialDiscount> allDiscountList,RecipeDO recipe){
    	PriceTempletDetailDTO mp = null;
        SpecialDiscount sd = null;
        recipe.setVatFreight(BigDecimal.ZERO);
        recipe.setTaxlessFreight(BigDecimal.ZERO);
        recipe.setTotalWeight(BigDecimal.ZERO);
        recipe.setRetailFreight(BigDecimal.ZERO);
        recipe.setTradeFreight(BigDecimal.ZERO);
        recipe.setMedicineQuantity(recipe.getRecipeMedicineList().size());
        recipe.setTotalWeight(BigDecimal.ZERO);
        for(RecipeMedicine rm:recipe.getRecipeMedicineList()){
       	 	recipe.setTotalWeight(recipe.getTotalWeight().add(rm.getWeightEvery().multiply(BigDecimal.valueOf(recipe.getQuantity()))));
            if (recipe.getUseHisMoney()) {
                rm.setTradeFreight(rm.getMoney());
                rm.setRetailFreight(rm.getMoney());
                recipe.setTradeFreight(recipe.getTradeFreight().add(rm.getTradeFreight()));;
                recipe.setRetailFreight(recipe.getRetailFreight().add(rm.getRetailFreight()));
                if(rm.getUnitType()!=null&&rm.getUnitType()==1){
                    rm.setRetailPrice(rm.getMoney().divide(new BigDecimal(rm.getWeightEvery().doubleValue()*recipe.getQuantity()),3,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(1000)));
                }else{
                    rm.setRetailPrice(rm.getMoney().divide(new BigDecimal(rm.getWeightEvery().doubleValue()*recipe.getQuantity()),3,BigDecimal.ROUND_HALF_UP));
                }
                rm.setTradPrice(rm.getRetailPrice());
                rm.setActualPrcie(rm.getRetailPrice());
                continue;
            }
            rm.setTradPrice(new BigDecimal(0));
            rm.setRetailPrice(new BigDecimal(0));
            if(medicinePriceList !=null){
	            for(int j=0;j<medicinePriceList.size();j++){
	                mp = (PriceTempletDetailDTO)medicinePriceList.get(j);
	                if(setPrice(recipe,rm,mp)){
	                	break;
	                }
	            }
            }
            rm.setTradeFreight((rm.getUnitType()!=null&&rm.getUnitType().intValue()==1)?
                    rm.getTradPrice().multiply(new BigDecimal(rm.getWeightEvery().doubleValue()*0.001*recipe.getQuantity())):
                    rm.getTradPrice().multiply(new BigDecimal(rm.getWeightEvery().doubleValue()*recipe.getQuantity())));
            rm.setRetailFreight((rm.getUnitType()!=null&&rm.getUnitType().intValue()==1)?
                    rm.getRetailPrice().multiply(new BigDecimal(rm.getWeightEvery().doubleValue()*0.001*recipe.getQuantity())):
                    rm.getRetailPrice().multiply(new BigDecimal(rm.getWeightEvery().doubleValue()*recipe.getQuantity())));
            
            if(allDiscountList!=null&&allDiscountList.size()>0){
				for(int j=0;j<allDiscountList.size();j++){
					sd = allDiscountList.get(j);
					if(recipe.getSettleCompanyId().intValue()==sd.getSettleCompanyId().intValue() && 
							rm.getMedicineId().intValue()==sd.getMedicineId().intValue()){
						rm.setDiscount(sd.getDiscount());
						break;
					}
				}
			}
            if(rm.getTaxRate()!=null){
            	if(rm.getDiscount()==null){
            		rm.setMoney(rm.getRetailFreight().multiply(recipe.getDiscount()).multiply(new BigDecimal(0.1)));
            	}else{
            		rm.setMoney(rm.getRetailFreight().multiply(rm.getDiscount()).multiply(new BigDecimal(0.1)));
            	}
            	recipe.setVatFreight(recipe.getVatFreight().add(rm.getMoney()));
            	recipe.setTaxlessFreight(recipe.getTaxlessFreight()
            			.add(rm.getMoney().divide(rm.getTaxRate().multiply(new BigDecimal(0.01)).add(new BigDecimal(1)),3,BigDecimal.ROUND_HALF_UP)));
            }
            recipe.setTradeFreight(recipe.getTradeFreight().add(rm.getTradeFreight()));;
            recipe.setRetailFreight(recipe.getRetailFreight().add(rm.getRetailFreight()));
        }
        setSpecialPrint(recipe);
   }
    /**
     * 关联药品检测
     * @param recipeDO
     * @param medicineMap
     * @return
     */
	public static Map<String,MedicineRelationDO> medicineRelation(RecipeDO recipeDO, Map<String, MedicineRelationDO> medicineMap) {
		Map<String,MedicineRelationDO> resultMap = new HashMap();
		MedicineRelationDO rmd = null;
		for (RecipeMedicine r : recipeDO.getRecipeMedicineList()) {
			rmd = medicineMap.get(r.getMedicineName() + recipeDO.getHospitalId());
			if (rmd != null) {// 名称关联存在
				getRecipeMedicine(r, rmd);
				continue;
			}
			if (r.getMedicineCode() != null && StringUtils.isNotBlank(r.getMedicineCode())) {
				rmd = medicineMap.get(r.getMedicineCode() + recipeDO.getHospitalId());
				if (rmd != null) {// 编码关联存在
					getRecipeMedicine(r, rmd);
					continue;
				}
			}
			rmd = medicineMap.get(r.getMedicineName() + Constants.STATE_ZERO);
			if (rmd != null) {// 通用药品名称关联存在
				getRecipeMedicine(r, rmd);
				continue;
			}
			if (r.getMedicineCode() != null && StringUtils.isNotBlank(r.getMedicineCode())) {
				rmd = medicineMap.get(r.getMedicineCode() + Constants.STATE_ZERO);
				if (rmd != null) {// 通用药品编码关联存在
					getRecipeMedicine(r, rmd);
					continue;
				}
			}
			// 没有关联药品存在，返回给前端的数据
			rmd = new MedicineRelationDO();
			rmd.setHospitalMedicineName(r.getMedicineName());
			rmd.setHospitalId(recipeDO.getHospitalId());
			rmd.setHospitalName(recipeDO.getHospitalName());
			resultMap.put(recipeDO.getHospitalId()+r.getMedicineName(), rmd);
		}
		return resultMap;
	}

	private static void getRecipeMedicine(RecipeMedicine r, MedicineRelationDO rmd) {
		r.setUnitType(rmd.getUnitType());
		if (StringUtils.isEmpty(r.getSpecialBoilType())){
            r.setSpecialBoilType(rmd.getSpecialBoilType());
        }else {
		    r.setSpecialBoilType(r.getSpecialBoilType());
        }
		r.setTaxRate(rmd.getTaxRate());
		r.setMedicineId(rmd.getNativeMedicineId());
		r.setMedicineName(rmd.getMedicineName());
	}
	
	/**
     * 获取关联药品
     * @return
     */
 	public static Map<String, MedicineRelationDO> getMedicineMap(List<MedicineRelationDO> medicineList) {
 		Map<String, MedicineRelationDO> medicineMap = new HashMap<String, MedicineRelationDO>();
 		for (MedicineRelationDO m : medicineList) {
 				if (m.getHospitalMedicineCode() != null && StringUtils.isNotBlank(m.getHospitalMedicineCode())) {
 	 				medicineMap.put(m.getHospitalMedicineCode()+m.getHospitalId(), m);
 	 			}
 	 			if (m.getHospitalMedicineName() != null && StringUtils.isNotBlank(m.getHospitalMedicineName())) {
 	 				medicineMap.put(m.getHospitalMedicineName()+m.getHospitalId(), m);
 	 			}
		}
 		return medicineMap;
 	}
 	
 	public static boolean ifNUll (RecipeDO r, String hospitalType) {
 		System.out.println("hospitalType:"+hospitalType);
 		if (!hospitalType.equals("0")) {
 			if (r.getHospitalId() == null || r.getPackType() == null ) {
 				return true;
 			}
 		}else{
 			//设置默认医院
 			if(r.getHospitalId()==null || r.getHospitalId()==0){
 				r.setHospitalId(1);
 			}
 		}
 		if (StringUtils.isBlank(r.getRecipientName())|| r.getQuantity() == null 
 				|| r.getPackagePaste() == null || r.getBoilType() == null ) {
 			return true;
 		}
 		return false;
 	}
 	
	public static List<RecipePatrol> convertRecipePatrol(List<RecipePatrol> list) {
		List<RecipePatrol> recipePatrolResult = new ArrayList<RecipePatrol>();
		for (RecipePatrol recipePatrolTemp : list) {
			// 处理0,1与不合格,合格的转化
			if (recipePatrolTemp.getPatrolStatus().equals(Constants.TYPE_ONE)) {
				recipePatrolTemp.setPatrolStatusName(Constants.QUALIFIED);
			}else if (recipePatrolTemp.getPatrolStatus().equals(Constants.STATE_ZERO)) {
				recipePatrolTemp.setPatrolStatusName(Constants.UNQUALIFIED);
			}else if (recipePatrolTemp.getPatrolStatus().equals(Constants.STATE_INVALID)) {
				recipePatrolTemp.setPatrolStatusName(Constants.STATE_INVALID_NAME);
			}
			// 设置误差重量与百分比
			BigDecimal mistakeNumber = recipePatrolTemp.getActualAmount().subtract(recipePatrolTemp.getPlanAmount());
			recipePatrolTemp.setMistakeGram(mistakeNumber);
			recipePatrolTemp.setMistakePercent(mistakeNumber.divide(recipePatrolTemp.getPlanAmount(),
                    DIV_SCALE,ROUND_DOWN).multiply(BigDecimal.valueOf(100)));
			recipePatrolResult.add(recipePatrolTemp);
		}
		return recipePatrolResult;
	}
	
	public static BigDecimal setTotalWeight(RecipeDO recipeDO){
		BigDecimal sumWeightEvery = BigDecimal.ZERO;
		if (CollectionUtils.isNotEmpty(recipeDO.getRecipeMedicineList())) {
			for (RecipeMedicine m : recipeDO.getRecipeMedicineList()) {
				sumWeightEvery = sumWeightEvery.add(m.getWeightEvery());
			}
		}
		return sumWeightEvery.multiply(new BigDecimal(recipeDO.getQuantity()));
	}
	
	public static List<RecipeMedicine> contrastData(Map<String, MedicineDO> nativeMap, List<RecipeMedicine> medicineList) {
		List<RecipeMedicine> resultList = new ArrayList<>();
		for (RecipeMedicine r : medicineList) {
			if (nativeMap.get(r.getMedicineName()) == null) {
				resultList.add(r);
			} else {
				r.setMedicineId(nativeMap.get(r.getMedicineName()).getId());
				if(StringUtils.isBlank(r.getSpecialBoilType())){
					r.setSpecialBoilType(nativeMap.get(r.getMedicineName()).getSpecialBoilType());
				}
				r.setUnitType(nativeMap.get(r.getMedicineName()).getUnitType());
			}
		}
		return resultList;
	}
	
	public static RecipeDO transferToRecipie(TransferRecipeDO tr){
		RecipeDO r=new RecipeDO();
		r.setId(tr.getId());
		r.setDealDate(tr.getTakeTime());
		r.setRecipeCode(tr.getRecipeCode());
		r.setShippingState(Constants.STATE_CREATE);
		r.setRemark(tr.getRemark());
		r.setHospitalId(tr.getHospitalId());
		r.setSettleCompanyId(tr.getSettleCompanyId());
		r.setBoilType(tr.getBoilType());
		r.setRecipientName(tr.getRecipientName());
		r.setRecipientTel(tr.getRecipientTel());
		r.setConsignee(tr.getConsignee());
		r.setConsigneeTel(tr.getConsigneeTel());
		if(StringUtils.isBlank(r.getConsignee())){
			r.setConsignee(r.getRecipientName());
		}
		if(StringUtils.isBlank(r.getConsigneeTel())){
			r.setConsignee(r.getRecipientTel());
		}
		r.setAge(tr.getAge());
		r.setSex(tr.getSex());
		r.setRecipientTel(tr.getRecipientTel());
		r.setProvince(tr.getProvince());
		r.setCity(tr.getCity());
		r.setRegion(tr.getRegion());
		r.setRecipientAddress(tr.getRecipientAddress());
		r.setInpatientArea(tr.getInpatientArea());
		r.setBedNumber(tr.getBedNumber());
		r.setDepartment(tr.getDepartment());
		r.setDoctorName(tr.getDoctorName());
		r.setPathogen(tr.getPathogen());
		r.setUsage(tr.getUsage());
		r.setQuantity(tr.getQuantity());
		r.setPackagePaste(tr.getPackagePaste());
		r.setTotalPackagePaste(tr.getTotalPackagePaste());
		r.setCarryId(tr.getCarryId());
		r.setRecipeSource(tr.getRecipeSource());
		r.setUseHisMoney(tr.getUseHisMoney());
		r.setMoney(tr.getMoney());
		r.setProcessCost(tr.getProcessCost());
		r.setCustomOneText(tr.getCustomOneText());
		r.setCustomTwoText(tr.getCustomOneText());
		r.setOutpatientNum(tr.getOutpatientNum());
		r.setInvoiceCode(tr.getInvoiceCode());
		return r;
	}
	
	public static String leftZero(Integer value){
		 return String.format("%09d", value);    
	}
	public static String getMedicineMaxDose(List<MedicineMaxDose> maxList, List<RecipeMedicine> medicineList) {
		Map<Integer, BigDecimal> map = new HashMap<>();
		if (CollectionUtils.isNotEmpty(maxList)) {
			for (MedicineMaxDose rm : maxList) {
				map.put(rm.getMedicineId(), rm.getMaxWeightEvery());
			}
		}
		/*Map<Integer, BigDecimal> map = maxList.stream()
				.collect(Collectors.toMap(MedicineMaxDose::getMedicineId, MedicineMaxDose::getMaxWeightEvery));*/
		String result = "";
		if (CollectionUtils.isNotEmpty(medicineList)) {
			for (RecipeMedicine rm : medicineList) {
				if (map.get(rm.getMedicineId()) != null) {
					if (rm.getWeightEvery().compareTo(map.get(rm.getMedicineId())) == 1) {
						if (rm.getStandard().equals("公斤")) {
							rm.setStandard("g");
						}
						result += ","+rm.getName_medicineName()+rm.getWeightEvery();
						result = StringUtils.stripEnd(result, "0");
						result = StringUtils.stripEnd(result, ".");
						result +=rm.getStandard();
					}
				}
			}
		}
		return StringUtils.stripStart(result, ",");
	}
	
	public static String ifRecipeState(List<RecipeDO> list) {
		for (RecipeDO r : list) {
			if (r.getShippingState() != null && r.getShippingState() == 999) {
				return "处方已作废,不能合并!";
			} else if (r.getInputState() != 1) {
				return "处方已录入,不能合并!";
			} else if (r.getCheckState() != 1) {
				return "处方已审核,不能合并!";
			} else if (r.getHospitalStatus() != 1) {
				return "处方已对账,不能合并!";
			}
		}
		return null;
	}
	
	public static void contrastRecipeMedicine(List<RecipeDO> list) {
		RecipeDO r1 = null;
		RecipeDO r2 = null;
		for (RecipeDO r : list) {
			if (r1 == null) {
				r1 = r;
			} else {
				r2 = r;
			}
		}
		if (r1 != null && r2 != null) {
			
		}
	}
	public static Date getCalendar() {
		Calendar c =  Calendar.getInstance();
   	 	c.add(Calendar.DAY_OF_MONTH, 1);  
        c.set(Calendar.HOUR_OF_DAY, 14);
        c.set(Calendar.MINUTE, 00);// 分钟  
        return c.getTime();
	}
}
