package com.tangu.tcmts.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import com.tangu.common.util.entity.ResponseModel;
import com.tangu.tcmts.po.City;
import com.tangu.tcmts.po.Region;

public class Constants {

    public static final String USER_NOT_FOUND = "用户名或密码错误!";
    public static final String USER_CODE_EXIST = "员工工号已存在";
    public static final String USER_NAME_EXIST = "员工名已存在";

    public static final String MISSING_PARAM = "缺少必要参数!";

    public static final String MAY_EXIST_DATA = "该角色仍在使用，无法删除!";

    public static final String DELETE_SUCCESS = "删除成功!";

    public static final String INSERT_SUCCESS = "保存成功!";

    public static final String QUALIFIED = "合格";
    public static final String UNQUALIFIED = "不合格";

    public static final String HOSPITAL_CODE_EXIST = "医院编号已存在!";
    public static final String SETTLE_COMPANY_EXIST = "该结算方已存在!";
    public static final String HOSPITAL_NAME_EXIST = "该医院名称或简称已存在!";

    public static final String PRESCRIPTION_EXIST = "该协定方已存在!";

    public static final String PAGE_EXIST = "该面单名已存在";

    public static final String UNIT_G = "g";

    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;
    public static final int TYPE_FOUR = 4;
    public static final int TYPE_FIVE = 5;
    public static final int TYPE_SIX = 6;
    public static final int TYPE_SEVEN = 7;
    public static final int TYPE_EIGHT = 8;
    
    //货架类型
    //门诊
    public static final int SHELVES_TYPE_OUT = 1;
    //住院
    public static final int SHELVES_TYPE_IN = 2;
    //快递
    public static final int SHELVES_TYPE_EXPRESS = 3;
    
    public static final int EXPRESS_SELF = 3;
    
    public static final int TYPE_MAX = 999;
    //停用
    public static final int STATE_ZERO = 0;
    //启用
    public static final int STATE_ONE = 1;

    public static final String TYPE_STANDARD = "公斤";
    public final static String BILL_ID = "billId";

    public static final String MEDICINE_TABOO_REPEAT = "配伍禁忌已存在！";
    public static final String MEDICINE_REPEATE = "药品名与禁忌药品名重复！";

    public static final String MEDICINE_TABOO_KEY = "medicineTaboo";

    public static final String CACHE_KEY_MEDICINE = "medicine";

    public static final String RECIPE_HISTORY = "recipeHistory";
    public static final String RECIPE_SCAN = "recipeScan";
    
    public static final String EXCEL_MEDICINE = "excelMedicine";
    public static final String EXCEL_WAREHOUSE = "excelWarehouse";


    public static final String CACHE_KEY_CONFIG = "config";

    public static final String CACHE_KEY_ALLCONFIG = "allConfig";

    public static final String CACHE_KEY_LOOKUP = "lookup";

    public static final String CACHE_KEY_COMPANY = "company";

    public static final String MEDICINE_STANDARD_EXIST = "药品规格已使用，不能删除！";

    public static final String IS_NULL_EXCEL = "excel不能为空！";
    
    public static final String RECIPE_MEDICINE_LIST="transferMedicineList";
    public static final int HORIZENTAL=1;
    public static final int VERTICAL=2;
    public static class PrintItemFields {
        public static final String TYPE = "type";// 字段类型
        public static final String X = "x";// x轴
        public static final String Y = "y";// y轴
        public static final String W = "w";// 宽度
        public static final String H = "h";// 高度
        public static final String FONT = "font";// 字体类型
        public static final String SIZE = "size";// 字体大小
        public static final String BOLD = "bold";// 加粗
        public static final String ITALIC = "italic";// 斜体
        public static final String LINE = "line";// 下划线
        public static final String ALIGN = "align";// 对齐方式
        public static final String GAP = "gap";// 字间距
        public static final String AUTO = "auto";// 字体自动缩放
        public static final String TEXT = "text";// 打印值
        public static final String BLACK = "black";// 黑色背景
        public static final String FIELD_SOURCE="fieldSource";
        public static final String ITEM_PARA="itemPara";
        public static final String CUSTOM_KEY="custom:";
        public static final String QRCODE_KEY="qrcode";
    }
    
    public static class PrintType {
    	public static final String RECIPE = "recipeDetail";// 处方
    	public static final String RECIPE_TAG = "recipeTag";// 处方标签
    	public static final String RECIPE_TAG_NF = "recipeOralTag";// 内服标签
    	public static final String RECIPE_TAG_WY = "recipeExternalTag";// 外用标签
    	public static final String RECIPE_TAG_GC = "recipeEnemaTag";// 灌肠标签
    	public static final String RECIPE_PROCESS = "recipeProcess";// 流程单
    	public static final String RECIPE_SPECIAL_TAG = "recipeSpecialTag";// 打印特殊
    	public static final String CREAM_FORMULA_DELIVERY = "creamFormulaDelivery";// 打印膏方送货单
    	public static final String CREAM_FORMULA_DETAIL = "creamFormulaDetail";// 打印膏方明细
    	public static final String CREAM_FORMULA_FINE_MATE = "creamFormulaFineMate";// 打印膏方细料
    	public static final String CREAM_FORMULA_PROCESS = "creamFormulaProcess";// 打印膏方加工单
    	
    	public static final String SF_BILL = "sfBill";// 顺丰面单
    	public static final String SZ_BILL = "postalBill";// 顺真
    	
    	public static final String EMPLOYEE = "employee";// 员工
    	public static final String MEDICINE_STANDARD_CODE = "medicineStandardCode";// 药品规格
    	public static final String MACHINE = "machine";// 煎药机编号
    	public static final String TAKE_MEDICINE = "takeMedicine";// 取药单
    }
    
    
    
    //判断规格量是否乘以100
  	public final static String   IF_EXIST_HOSPITAL = "IF_EXIST_HOSPITAL";
  	public final static String   REFORM_MEDICINE_NAME = "REFORM_MEDICINE_NAME";
  	public final static String   DEFAULT_CITY_CODE = "DEFAULT_CITY_CODE";
  	public final static String   DR_RECIPE_PSFS_BZ = "DR_RECIPE_PSFS_BZ";
  	public final static String SHOW_CHOOSE_CARRY = "SHOW_CHOOSE_CARRY";
  	public final static String   IF_ADD_WUZI_WHILE_BQ_IS_NULL = "IF_ADD_WUZI_WHILE_BQ_IS_NULL";
  	public final static String   EXCEL_MONEY = "EXCEL_MONEY";
  	public final static String   DR_RECIPE_BAOHAN_GUIGE_BZ = "DR_RECIPE_BAOHAN_GUIGE_BZ";
  //药品重量计算公式配置
  	public final static String   DR_RECIPE_ZONGLIANG_GS = "DR_RECIPE_ZONGLIANG_GS";

    public static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String JSON_CONTENT_TYPE = "application/json";
    public static final String TRANSFER_TYPE = "transferType";
    
    //手持扫描
    public static int TOLERANCE_RANGE=1;
    public enum State{
    	CHECK_BILL(5,17,"已审方"),DISPENSE_SCAN(10,3,"已配药"),
    	BILL_STATE_XBZFH(20,88,"已小包装复核"),RE_CHECK_SCAN(15,4,"已复核"),
    	SOAK_SCAN(30,5,"已浸泡"),START_FRIST_BOIL(35,16,"已先煎"),
    	START_DECOCT_SCAN(40,6,"已开始煎煮"),	START_AFTER_BOIL(45,18,"已后下"),START_SECOND_BOIL(46,46,"已开始二煎"),
    	END_DECOCT_SCAN(50,7,"已结束煎煮"),CONCENTRATE_START(55,11,"已开始浓缩"),
    	CONCENTRATE_END(60,12,"已结束浓缩"),SUBSIDE_START(65,13,"已开始沉淀"),
    	SUBSIDE_END(70,14,"已结束沉淀"),FORCE_SUBSIDE_END(80,15,"已强制沉淀"),
    	BOIL_SCAN(85,8,"已收膏"),PACK_SCAN(90,9,"已打包，未上架"),
    	STATE_SHELVES_ALREADY(95,95,"已上架"),DELIVERY_BILL(100,19,"已发药"),
    	RECIPE_PICKING(300,30,""),INVALID(999,999,"已作废");
    	private final Integer newState;
    	private final Integer oldState;
    	private final String stateName;
    	private State(Integer newState,Integer oldState,String stateName) {   
            this.newState = newState;
            this.oldState = oldState;
            this.stateName = stateName;
    	}
		public Integer getNewState() {
			return newState;
		}
		public Integer getOldState() {
			return oldState;
		}   
		   	
  }
    
    public static String getStateName(Integer state) {
      String stateName = "";
      for (State e : State.values()) {
        if(e.newState.equals(state)){
          stateName = e.stateName;
        }
      }
      return stateName;
    }
    
    public static void main(String[] args) {
      System.out.println(State.values());
      for (State e : State.values()) {
        if(e.newState.equals(new Integer(95))){
          System.out.println(e.stateName);
        }
      }
    }
    
    public static class StateMap{
    	public static int getState(int state){
    		int newState=0;
    		switch (state) {
				case 3:newState=10;break;
				case 4:newState=15;break;
				case 5:newState=30;break;
				case 6:newState=40;break;
				case 7:newState=50;break;
				case 8:newState=85;break;
				case 9:newState=90;break;
				case 11:newState=55;break;
				case 12:newState=60;break;
				case 13:newState=65;break;
				case 14:newState=70;break;
				case 15:newState=80;break;
				case 16:newState=35;break;
				case 17:newState=5;break;
				case 18:newState=45;break;
				case 19:newState=100;break;
				case 30:newState=300;break;
				case 88:newState=20;break;
				case 999:newState=999;break;
				default: newState=state;
			}
    		return newState;
    	}
    }
    public static class OldStateMap{
    	public static int getOldState(int state){
    		int oldState=0;
    		switch (state) {
				case 10:oldState=3;break;
				case 15:oldState=4;break;
				case 30:oldState=5;break;
				case 40:oldState=6;break;
				case 50:oldState=7;break;
				case 85:oldState=8;break;
				case 90:oldState=9;break;
				case 55:oldState=11;break;
				case 60:oldState=12;break;
				case 65:oldState=13;break;
				case 70:oldState=14;break;
				case 80:oldState=15;break;
				case 35:oldState=16;break;
				case 5:oldState=17;break;
				case 45:oldState=18;break;
				case 100:oldState=19;break;
				case 300:oldState=30;break;
				case 20:oldState=88;break;
				case 999:oldState=999;break;
				default: oldState=state;
			}
    		return oldState;
    	}
    }
    public static class NextStateMap{
    	public static int getNextState(int state){
    		int newState=0;
    		switch (state) {
				case 1:newState=10;break;
				case 5:newState=10;break;
				case 10:newState=15;break;
				case 15:newState=30;break;
				case 25:newState=30;break;
				case 30:newState=40;break;
				case 35:newState=40;break;
				case 40:newState=50;break;
				case 45:newState=50;break;
				case 50:newState=85;break;
				case 55:newState=60;break;
				case 65:newState=70;break;
				case 85:newState=90;break;
				case 95:newState=100;break;
				default:newState=state+1;
			}
    		return newState;
    	}
    }
    public final static int STATE_CREATE = 1;//开方
	public final static int STATE_CHECK_BILL    = 5;//已审方
	public final static int STATE_DISPENSE_SCAN = 10;//配药
	public final static int STATE_PATROL_SCAN    = 10;//煎药机巡查
	public final static int STATE_RE_CHECK_SCAN    = 15;//复核
	public final static int STATE_BILL_STATE_XBZFH=20;//小包装复核
	public final static int STATE_SOAK_SCAN    = 30;//浸泡
	public final static int STATE_START_FRIST_BOIL    = 35;//已开始先煎
	public final static int STATE_START_DECOCT_SCAN    = 40;//开始煎煮
	public final static int STATE_START_AFTER_BOIL    = 45;//已开始后下
	public final static int STATE_START_SECOND_BOIL    = 46;//已开始二煎
	public final static int STATE_END_DECOCT_SCAN    = 50;//结束煎煮
	public final static int STATE_CONCENTRATE_START    = 55;//开始浓缩
	public final static int STATE_CONCENTRATE_END    = 60;//结束浓缩
	public final static int STATE_SUBSIDE_START    = 60;//开始沉淀
	public final static int STATE_SUBSIDE_END    = 70;//结束沉淀
	public final static int STATE_FORCE_SUBSIDE_END    = 80;//强制结束沉淀
	public final static int STATE_BOIL_SCAN    = 85;//收膏
	public final static int STATE_PACK_SCAN    = 90;//已打包
	public final static int STATE_SHELVES_ALREADY  = 95;//已上架
	public final static int STATE_DELIVERY_BILL    = 100;//已发货
	public final static int STATE_RECIPE_PICKING=300;//领料
	public final static int STATE_INVALID = 999;//已作废
	public final static String STATE_INVALID_NAME = "已作废";//已作废
	public final static int DJ = 1;//代煎
    public final static int ZJ = 2;//自煎
    public final static int GF = 3;//膏方代煎
    public final static int GFZJ = 4;//膏方自煎
    
    public final static String HOSPITAL_TYPE = "HOSPITAL_TYPE";

    public static final Integer QUANTITY_PACKAGE=2;//贴数*包数
    public static final Integer QUANTITY=3;//贴数
    
    public static final Integer TAKE_TYPE_NF=1;//内服
    public static final Integer TAKE_TYPE_WY=2;//外用
    public static final Integer TAKE_TYPE_GC=3;//灌肠
    public enum Accessories{
    	FL_HJF("黄酒费",BigDecimal.valueOf(1L)),
    	FL_BZF("包装费",BigDecimal.valueOf(1L)),
    	FL_JGF("加工费",BigDecimal.valueOf(60L));
    	public final String name;
    	public final BigDecimal value;
    	private Accessories(String name,BigDecimal value) {   
            this.name = name;
            this.value = value;
    	}   
		public BigDecimal getValue() {
			return value;
		}
		public String getName() {
			return name;
		}
    }
    
    public static Map userType = new HashMap();
    public static Map privilegList = new HashMap();
    public static class ConfigKey {
    	public static final String SOAK_TIME = "SOAK_TIME";// 默认浸泡时间
    	public static final String BOIL_TIME = "BOIL_TIME";// 默认煎煮时间
    	public static final String PACKAGE_PASTE = "PACKAGE_PASTE";// 默认煎煮时间
    	public static final String CHECK_TABOO = "CHECK_TABOO";// 接收检测配伍禁忌
    	public static final String RELATION_MEDICINE = "RELATION_MEDICINE";//接收关联药品
    	public static final String SF_EXPRESS_TYPE = "SF_EXPRESS_TYPE";//顺丰快件类型
    	
    	public static final String SF_CARD_NUM = "SF_CARD_NUM";// 月结卡号
    }
    public static final int FL_TYPE=99;//辅料类型
    public static class MedicineType {
    	public static String getTypeName(int type){
    		String result="";
    		switch (type) {
			case 1:result="饮片";
				break;
			case 2:result="精制";
				break;
			case 3:result="细料";
				break;
			case 4:result="饮片";
				break;
			}
    		return result;
    	}
    }

    public static class BusinessType {
    	public static String getBusinessTypeName(int type){
    		String result="";
    		switch (type) {
			case 1:result="当天快件";
				break;
			case 2:result="电商特惠";
				break;
			case 3:result="标准快递";
				break;
			case 4:result="医药常温";
				break;
			}
    		return result;
    	}
    }
    
    //获取上海市的区
    public static List<Region> rangeList = new ArrayList<>();
    //获取所以的市
    public static List<City> cityList = new ArrayList<>();
    
    public static class PayMethod {
    	public static String getMethodeName(int type){
    		String result="";
    		switch (type) {
				case 1:result="寄付月结";
					break;
				case 2:result="到付";
					break;
				case 3:result="现付";
					break;
    		}
    		return result;
    	}
   }
    
    public static class SpecialBoil {
    	public static final String SEPECIAL_BOIL_XJ = "先煎";
    	public static final String SEPECIAL_BOIL_HX = "后下";
    	public static final String SEPECIAL_BOIL_BJ  = "包煎";
    	public static final String SEPECIAL_BOIL_LB = "另包";
    	public static final String SEPECIAL_BOIL_TJ = "同煎";
    }
    
    public static Object operation(Integer i, 
        String value, String errMsg, Object obj){
      return i > 0 ? new ResponseModel(value).attr(ResponseModel.KEY_DATA, obj) :
        new ResponseModel().attr(ResponseModel.KEY_ERROR, errMsg);
    }
    
    public static Object operation(Integer i, 
        String value, String errMsg){
      return operation(i > Constants.STATE_ZERO, value, errMsg);
    }
    
    public static Object operation(Object i, String value, String errMsg){
      return operation(i == null, value, errMsg);
    }
    
    public static Object operation(boolean i, String value, String errMsg){
      return i ? new ResponseModel(value) :
        new ResponseModel().attr(ResponseModel.KEY_ERROR, errMsg);
    }
}
