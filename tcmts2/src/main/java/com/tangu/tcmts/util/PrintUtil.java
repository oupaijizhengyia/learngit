package com.tangu.tcmts.util;

import com.alibaba.fastjson.JSONObject;
import com.tangu.common.util.DateUtil;
import com.tangu.tcmts.po.PrintItem;
import com.tangu.tcmts.po.PrintPage;
import com.tangu.tcmts.po.PrintPageObj;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by djl on 2017/11/3.
 */
@Slf4j
public class PrintUtil {
    private static final String TEXT_R = "text_r";

    private static final String DATE_F = "date_f";

    private static final String MONEY = "money";

    private static final String MONEY_P = "money_p";
    /**
     * 组装通用打印对象
     * 如无特殊情况调用此方法，有特殊情况需另写
     *
     * @param page
     * @param billObj
     * @return
     */
    public static PrintPageObj getPrintableCanvasObj(PrintPage page, Object billObj) {
        PrintPageObj printObj = setPrintObj(page);
        List itemList = new ArrayList();
        if (page.getPrintItemList() == null || page.getPrintItemList().size() == 0) {
            return printObj;
        }
        JSONObject itemObj = null;
        for (PrintItem item : page.getPrintItemList()) {
            itemObj = new JSONObject();
            setItem(itemObj, item);
            itemObj.put(Constants.PrintItemFields.TEXT, calculatePrintText(item, billObj));
            if(Constants.PrintItemFields.QRCODE_KEY.equals(item.getFieldSource())){
            	itemObj.put(Constants.PrintItemFields.TYPE, item.getFieldSource());
            }
            itemList.add(itemObj);
        }
        printObj.setPrintItemList(itemList);
        return printObj;
    }

    /**
     * 设置打印头属性
     *
     * @param page
     * @return
     */
    private static PrintPageObj setPrintObj(PrintPage page) {
        PrintPageObj printObj = new PrintPageObj();
        printObj.setId(page.getId());
        printObj.setPageHeight(page.getPageHeight());
        printObj.setPageWidth(page.getPageWidth());
        printObj.setImageHeight(page.getImageHeight());
        printObj.setImageWidth(page.getImageWidth());
        printObj.setPrinterId(page.getPrinterId());
        if(page.getPrintBackgroundImage()!=null && page.getPrintBackgroundImage()==true){
            printObj.setBackgroundImage(page.getBackgroundImage());
        }
        return printObj;
    }

    /**
     * 设置打印明细属性
     *
     * @param itemObj
     * @param item
     */
    private static void setItem(JSONObject itemObj, PrintItem item) {
        itemObj.put(Constants.PrintItemFields.X, item.getItemX());
        itemObj.put(Constants.PrintItemFields.Y, item.getItemY());
        itemObj.put(Constants.PrintItemFields.W, item.getItemWidth());
        itemObj.put(Constants.PrintItemFields.H, item.getItemHeight());
        itemObj.put(Constants.PrintItemFields.FONT, item.getFontFamily());
        itemObj.put(Constants.PrintItemFields.SIZE, item.getFontSize());
        itemObj.put(Constants.PrintItemFields.BOLD, item.getFontWeight());
        itemObj.put(Constants.PrintItemFields.ITALIC, item.getFontStyle());
        itemObj.put(Constants.PrintItemFields.LINE, item.getFontDecoration());
        itemObj.put(Constants.PrintItemFields.ALIGN, item.getTextAlign());
        itemObj.put(Constants.PrintItemFields.GAP, item.getLetterSpacing());
        itemObj.put(Constants.PrintItemFields.TYPE, item.getFieldType());
        //非必须字段不传输
        if (item.getAutoSize()!=null) {
            itemObj.put(Constants.PrintItemFields.AUTO, item.getAutoSize());
        }
        if (item.getBackgroundColor()!=null&&item.getBackgroundColor()) {
            itemObj.put(Constants.PrintItemFields.BLACK, item.getBackgroundColor());
        }
        
        itemObj.put(Constants.PrintItemFields.FIELD_SOURCE, item.getFieldSource());
        itemObj.put(Constants.PrintItemFields.ITEM_PARA, item.getItemPara());
    }

    /**
     * 创建空白打印页
     *
     * @param page
     * @return
     */
    public static PrintPageObj getEmptyCanvas(PrintPage page) {
        PrintPageObj printObj = setPrintObj(page);
        printObj.setPrintItemList(new ArrayList<JSONObject>());
        return printObj;
    }

    /**
     * 根据字段获取打印值
     *
     * @param item
     * @param billObj
     * @return String
     */
    private static String calculatePrintText(PrintItem item, Object billObj) {
        Map paraObj = setFieldParaObject(item.getItemPara());
        Object preResult = null;
        String result = "";
        List listObj = null;
        Integer index = 0;
        try {
            switch (item.getFieldType()) {
                case "text":
                    preResult = getValue(billObj, item.getFieldSource());
                    if (StringUtils.isNotBlank(item.getFieldSource()) && preResult != null) {
                        result = getTextReplaceLabel(preResult.toString(), paraObj);
                    } else {
                        result = getFieldPara("text", paraObj);
                    }
                    result=replaceLastZero(result);
                    break;
                case "text_p":
                    preResult = getObjectValue(billObj, item.getFieldSource());
                    if (StringUtils.isNotBlank(item.getFieldSource()) && preResult != null) {
                        result = preResult.toString().split(getFieldPara("split", paraObj))
                                [Integer.valueOf(getFieldPara("text_p", paraObj)) - 1];
                    }
                    break;
                case "custom":
                    result = getFieldPara("custom", paraObj);
                    break;
                case "money":
                    preResult = getObjectValue(billObj, item.getFieldSource());
                    if (preResult == null || preResult.toString().length() == 0) {
                        result = "";
                    } else {
                        result = getMoneyLabel(preResult.toString(), paraObj);
                    }
                    break;
                case "money_p":
                    preResult = getObjectValue(billObj, item.getFieldSource());
                    if (preResult != null || preResult.toString().length() == 0) {
                        result = "";
                    } else {
                        result = getMoneyPartLabel(preResult.toString(), paraObj);
                    }
                    break;
                case "currency":
                    result = getFieldPara("currency", paraObj);
                    break;
                case "date":
                	if("printDate".equals(item.getFieldSource())){
                		result = getDateLabel(new Date(), paraObj);
                	}else{
                		result = getDateLabel((Date) getObjectValue(billObj, item.getFieldSource()), paraObj);
                	}
                    break;
                case "date_p":
                    result = getDatePartLabel((Date) getObjectValue(billObj, item.getFieldSource()), paraObj);
                    break;
                case "check":
                    result = "√";
                    break;
                case "boolean":
                    if ((Boolean) getObjectValue(billObj, item.getFieldSource())) {
                        result = "√";
                    }
                    break;
                case "list|money":
                    break;
                case "list|text":
                    listObj = (ArrayList) getObjectValue(billObj, item.getFieldSource());
                    index = Integer.valueOf(getFieldPara("list", paraObj)) - 1;
                    if (index >-1 && index < listObj.size()) {
                        try {
                            result = getObjectValue(listObj.get(index), getFieldPara("field", paraObj)).toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "list|custom":
                    listObj = (ArrayList) getObjectValue(billObj, item.getFieldSource());
                    index = Integer.valueOf(getFieldPara("list", paraObj)) - 1;
                    if (index >= listObj.size()) {
                        result = "";
                    } else {
                        result = getFieldPara("custom", paraObj);
                    }
                    break;
                case "barcode":
                    preResult = getObjectValue(billObj, item.getFieldSource());
                    if (StringUtils.isNotBlank(item.getFieldSource()) && preResult != null) {
                        result = getTextReplaceLabel(preResult.toString(), paraObj);
                    } else {
                        result = getFieldPara("text", paraObj);
                    }
                    break;
                case "serialNumber":
                    if (StringUtils.isNotBlank(getFieldPara(TEXT_R, paraObj))) {
                        result = getFieldPara(TEXT_R, paraObj);
                    }
                    break;
                case "qrcode":
                    preResult = getObjectValue(billObj, item.getFieldSource());
                    if (StringUtils.isNotBlank(item.getFieldSource()) && preResult != null) {
                        result = getTextReplaceLabel(preResult.toString(), paraObj);
                    } else {
                        result = getFieldPara("text", paraObj);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 拆分itemPara to Map
     *
     * @param fieldParaStr
     * @return Map
     */
    public static Map setFieldParaObject(String fieldParaStr) {
        Map<String, Object> fieldPara = new HashMap();
        if (fieldParaStr == null) {
            return fieldPara;
        }
        int length = fieldParaStr.split("\\|").length;
        String subStr;
        if (length == 0) {
            return fieldPara;
        }
        for (int i = 0; i < length; i++) {
            subStr = fieldParaStr.split("\\|")[i];
            if (subStr.split(":").length > 1) {
//                fieldPara.put(subStr.split(":")[0], subStr.split(":")[1]);
            	fieldPara.put(subStr.split(":")[0], subStr.substring(subStr.split(":")[0].length()+1));
            } else {
                fieldPara.put(subStr.split(":")[0], "");
            }
        }
        return fieldPara;
    }

    /**
     * 替换功能
     *
     * @param data
     * @param paraObj
     * @return
     */
    private static String getTextReplaceLabel(String data, Map paraObj) {
        String replaceStr = getFieldPara("replace", paraObj) == null ? "" : getFieldPara("replace", paraObj);
        if (getFieldPara(TEXT_R, paraObj) != "" && getFieldPara(TEXT_R, paraObj).length() > 0) {
            if (data.equals(getFieldPara(TEXT_R, paraObj))) {
                return replaceStr;
            }
        }
        return data;
    }

    /**
     * 根据key 得到value
     *
     * @param field
     * @param paraObj
     * @return
     */
    public static String getFieldPara(String field, Map paraObj) {
        if (paraObj.get(field) != null) {
            return paraObj.get(field).toString();
        } else {
            return "";
        }
    }

    /**
     * 获取时间 年月日 时分秒
     *
     * @param data
     * @param paraObj
     * @return
     */
    private static String getDateLabel(Date data, Map paraObj) {
        return DateUtil.getDate(data, getFieldPara("date", paraObj));
    }
    
    /**
     * 获取单个年、月、日
     *
     * @param date
     * @param paraObj
     * @return
     */
    private static String getDatePartLabel(Date date, Map paraObj) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        String part = getFieldPara("date_p", paraObj);
        String result = "";
        int value = 0;
        switch (part) {
            case "y":
                value = now.get(Calendar.YEAR);
                break;
            case "m":
                value = now.get(Calendar.MONTH) + 1;
                break;
            case "d":
                value = now.get(Calendar.DAY_OF_MONTH);
                break;
            case "h":
                value = now.get(Calendar.HOUR_OF_DAY);
                break;
            default:
                value = 0;
        }
        result = "" + value;
        if (!"".equals(getFieldPara(DATE_F, paraObj) )) {
            if (Integer.valueOf(getFieldPara(DATE_F, paraObj)) > 0 && !"".equals(result )) {
                result = TransformUtil.dateToBig(result);
            }
        }
        return result;
    }

    private static String getMoneyLabel(String data, Map paraObj) {
        String returnText = "";
        if (!"".equals(getFieldPara(MONEY, paraObj))) {
            if (Integer.valueOf(getFieldPara(MONEY, paraObj)) > 0) {
                returnText = TransformUtil.toBigAmt(BigDecimal.valueOf(Double.valueOf(data)));
            } else {
                //此处涉及小数保留位数，需获取账套配置
                if (true) {
                    returnText = TransformUtil.formatCurrencyPoint(BigDecimal.valueOf(Double.valueOf(data)), false, 2);
                } else {
                	returnText = TransformUtil.formatCurrency(BigDecimal.valueOf(Double.valueOf(data)));
                }
            }
        }else{
        	//此处涉及小数保留位数，需获取账套配置
            if (true) {
                returnText = TransformUtil.formatCurrencyPoint(BigDecimal.valueOf(Double.valueOf(data)), false, 2);
            } else {
            	returnText = TransformUtil.formatCurrency(BigDecimal.valueOf(Double.valueOf(data)));
            }
        }
        if (!"".equals(getFieldPara("symbol", paraObj) )) {
            //returnText = symbolArray[Number(getFieldPara("symbol",paraObj))].data+returnText;
        }
        return returnText;
    }

    private static String getMoneyPartLabel(String data, Map paraObj) {
        String dotPart = ""; //取小数位
        String intPart = ""; //取整数位
        int dotPos;
        int part = Integer.valueOf(getFieldPara("part", paraObj));
        String returnText = "";
        if ((dotPos = data.indexOf('.')) != -1) {
            intPart = data.substring(0, dotPos);
            dotPart = data.substring(dotPos + 1);
        } else {
            intPart = data;
        }
        if (part < 0) {
            if (Math.abs(part) <= dotPart.length()){
                returnText = dotPart.substring(Math.abs(part) - 1, 1);
            }
        } else if (part < 6) {
            if (part <= intPart.length()) {
                returnText = intPart.substring(intPart.length() - part, 1);
            }
        } else {
            if (intPart.length() >= 6) {
                returnText = intPart.substring(0, intPart.length() - 5);
            }
        }
        if (!"".equals(getFieldPara(MONEY_P, paraObj))) {
            if (Integer.valueOf(getFieldPara(MONEY_P, paraObj)) > 0 && !"".equals(returnText)) {
                returnText = TransformUtil.intToBig(returnText);
            }
            if (Integer.valueOf(getFieldPara(MONEY_P, paraObj)) > 0 && "".equals(returnText )) {
                if (part < intPart.length()) {
                    returnText = "零";
                } else {
                    returnText = "";
                }
            }
        }
        return returnText;
    }
    
    public static Object getValue(Object object, String field){
    	if(field.indexOf("+")>-1){
    		String[] arr=field.split("\\+");
    		String value="";
    		for(int i=0;i<arr.length;i++){
    			value+=" "+(String)getObjectValue(object,arr[i]);
    		}
    		return value;
    	}
    	return getObjectValue(object,field);
    }
    
    /**
     * 反射机制获取值
     *
     * @param object
     * @param field
     * @return
     */
    public static Object getObjectValue(Object object, String field) {
        try {
            if (object != null) {
                Method m = (Method) object.getClass().getMethod(
                        "get" + getMethodName(field));
                if (isInstance(m.getReturnType(), String.class)) {
                    String val = (String) m.invoke(object);
                    if (val != null) {
                        return val;
                    }
                } else if (isInstance(m.getReturnType(), Integer.class)) {
                    Integer val = (Integer) m.invoke(object);
                    if (val != null) {
                        return val;
                    }
                } else if (isInstance(m.getReturnType(), Double.class)) {
                    Double val = (Double) m.invoke(object);
                    if (val != null) {
                        return val;
                    }
                } else if (isInstance(m.getReturnType(), Long.class)) {
                    Long val = (Long) m.invoke(object);
                    if (val != null) {
                        return val;
                    }
                } else if (isInstance(m.getReturnType(), Boolean.class)) {
                    Boolean val = (Boolean) m.invoke(object);
                    if (val != null) {
                        return val;
                    }
                } else if (isInstance(m.getReturnType(), List.class)) {
                    return (List) m.invoke(object);
                } else if (isInstance(m.getReturnType(), Date.class)) {
                    Date val = (Date) m.invoke(object);
                    if (val != null) {
                        return val;
                    }
                } else if (isInstance(m.getReturnType(), BigDecimal.class)) {
                    BigDecimal val = (BigDecimal) m.invoke(object);
                    if (val != null) {
                        return val;
                    }
                }
            }
        } catch (Exception e) {
        	log.error("field="+field);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断类型
     *
     * @param obj
     * @param cls
     * @return
     */
    public static boolean isInstance(Object obj, Object cls) {
        return cls == obj;
    }

    /**
     * 根据字段组装方法名
     *
     * @param fildeName
     * @return
     * @throws Exception
     */
    private static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
    
    public static String replaceLastZero(String s) {
	  if(StringUtils.isNotBlank(s) && s.indexOf(".") > 0){
		  //正则表达
          s = s.replaceAll("0+?$", "");//去掉后面无用的零
          s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
	  }
	  return s;
	}
}
