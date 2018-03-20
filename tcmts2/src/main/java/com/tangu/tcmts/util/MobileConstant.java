/*
 * Created on 2013-7-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.tangu.tcmts.util;
/**
 * @author diaojialong
 * 
 * TODO To change the template for this generated type comment for Mobile Window -
 * Preferences - Java - Code Style - Code Templates
 */
public final class MobileConstant {
	
	public final static int LOGIN =1;//登陆
	public final static int DISPENSE_SCAN =2;//配药扫描
	public final static int ACCEPT_SCAN =3;//接方扫描
	public final static int MACHINE_PATROL_SCAN =4;//巡查扫描
	public final static int GET_TIME =5;//获取时间状态
	public final static int SAVE_PHOTO =6;//保存图片
	public final static int START_DECOCT_SCAN = 7;//开始煎药扫描
	public final static int CHECK_PATROL = 8;//检查煎药机状态
	public final static int PACK_SCAN =9;//包装扫描
	public final static int CHECK_RECIPE_STATUS = 10;//检查处方状态
	
	public final static int USER_SCAN =11;//用户条码扫描
	public final static int GET_VERSION = 12;//获取版本号
	public final static int RECIPE_HISTORY = 13;//历史记录
	
	public final static int PATROL_SCAN = 14;//配方,煎药巡检查看
	public final static int PATROL_ADD = 15;//配方,煎药巡检保存
	
	
	
	public final static int GET_WAREHOUSE = 16;//获取药房、仓库
	public final static int GET_MEDICINE_BY_NUMBER = 17;//根据条码获取药品信息
	public final static int ADD_WARE_OUT_APPLY = 18;//添加调拨申请
	public final static int GET_WARE_OUT_APPLY = 19;//获取调拨申请
	public final static int DEL_WARE_OUT_APPLY = 20;//删除调拨申请
	public final static int UPDATE_WARE_OUT_APPLY = 21;//修改调拨申请件数
	public final static int CONFIRM_WARE_OUT_APPLY = 22;//确认调拨申请
	
	/*智能派工*/
	public final static int GET_RECIPE_LIST_AND_MACHINE_LIST = 23;//过去处方列表和煎药机列表
	public final static int ALLOT = 24;//派工
	
	public final static int END_DECOCT_SCAN = 25;//结束煎药扫描
	
	public final static int CHECK_NOT_THROUGH = 26;//复核不通过
	public final static int START_CONCENTRATE = 27;//开始浓缩
	public final static int END_CONCENTRATE = 28;//结束浓缩
	public final static int GET_BOIL_MEDICINE = 33;//先煎药品
	public final static int SAVE_BOIL_MEDICINE = 34;//保存先煎药品
	public final static int SCAN_MEDICINE = 35;//盘点扫描药品条码
	public final static int SAVE_INVENTORY=36;//保存盘点信息
	public final static int QUERY_BY_EMPId=37;//查看盘点信息
	public final static int RECIPE_PICKING=38;//领料
	public final static int SAVE_AFTER_MEDICINE = 44;//保存后下药品
	public final static int DELIVERY_BILL = 45;//发货
	public final static int START_SECOND_BOIL  = 46;//开始二煎
	
	public final static int QUERY_RECEIVING_BILL_BY_ID=2;//通过托运单ID查单基本信息
	public final static int QUERY_RECEIVING_BILL_BY_OTHER=3;//通过其他条件查询单据基本信息
	public final static int QUERY_RECEIVING_BILL_HISTORY=4;//查询运单历史记录
	public final static int CREATE_RECEIVING_BILL=5;//新增运单
	public final static int SAVE_RECEIVING_BILL=6;//修改运单
	public final static int ACCEPT_RECEIVING_BILL=7;//签收运单
	public final static int UPLOAD_RECEIVING_BILL_IMG=8;//上传照片
	public final static int READ_RECEIVING_BILL_IMG=9;//读取照片
	public final static int DEL_RECEIVING_BILL_IMG=10;//删除照片
	
	public final static int DISPATCHER_DELIVERY_RECEIVING_BILL=11;//配送员配送货物
	public final static int DISPATCHER_RECEIVED_BILL=12;//配送员交货到仓库
	public final static int DISPATCHER_RECEIVED_LIST=13;//配送列表
	public final static int DISPATCHER_RECEIVING_LIST=14;//未交货列表
	public final static int CANCLE_DISPATCH_RECEIVING_BILL=15;//取消配送
	public final static int QUERY_RECEIVING_BILL_BYCODE=16;//扫货
	
	public final static int QUERY_COUNTERPARTY_LIST=18;//获取单位信息
	public final static int QUERY_DEPOT_LIST=19;//获取网点信息
	public final static int QUERY_PRODUCT_LIST=20;//获取货物信息
	
	public final static int READ_VEHICLE_DRIVER_LIST=17;//获取车牌，司机
	public final static int SAVE_SHIPMENT_BILL=21;//开配载单
	public final static int READ_SHIPMENT_BILL_LIST=22;//读取最近配载单列表,即到货接收
	public final static int READ_SHIPMENT_PROFILE_LIST=23;//读取配载清单
	public final static int MOBILE_UNLOAD=24;//到货接收,来自手持终端发车
	public final static int MOBILE_UNLOAD_SECOND=25;//到货接收,来自手持终端接收电脑端配货
	public final static int ROLL_RECEIVING_BILL=26;//滚动装车
	public final static int READ_BILL_IMG_LIST=27;//浏览照片list
	public final static int READ_RECEIVING_COMMENT_LIST=28;//浏览运单备注list
	public final static int ADD_RECEIVING_COMMENT=29;//新增运单备注
	
	public final static int UPLOAD_BILL=47;//库存盘点上传货物
	public final static int STOCK_TAKING=48;//盘点,用于电脑端接口
	public final static int READ_STOCK_TAKING_REPORT=49;//查看库存盘点报表,用于电脑端接口
	
	public final static int CANCLE_FINANCE_BILL=999;//作废结算列表
	public final static int SAVE_FINANCE_BILL=50;//财务结算
	public final static int READ_FINANCE_BILL_LIST=51;//查询财务结算列表
	public final static int READ_FINANCE_PROFILE_LIST=52;//查询财务结算清单 //查看现金交账单
	public final static int READ_RECEIVING_FINANCE=53;//查询财务未结算
	
	public final static int SET_DEFAULT_DEPT =  54; //通过手持端传来的eid，默认开单网点，更改电脑端的默认开单网点
	public final static int BZ_LIST = 55 ; //常用备注接口
	public final static int 	INSERT_RECE_LIST = 56 ; //批量插入托运单

	public final static int MEDICINE_MATCH_SHELVES = 57;
	public final static int MEDICINE_TAKE_CONFIRM = 58;
	public final static int MEDICINE_MATCH_SHELVES_GET = 59;
	public final static int MEDICINE_TAKE_CONFIRM_GET = 60;


	public final static String bill="{'bid':0,'code':'N1307300004','AID':1,'EID':1,'kdid':1,'sst':1,'did':1,'dn':杭州,'ddid':25,'sn':'刁家龙','stel':'18268164809','sadd':'浙大紫金港188号','rn':'龙龙收货','radd':'四大皆空发送到','rtel':'238293','js':10,'zl':20,'tj':20,'yf':30,'zsf':10,'bxf':10,'bjje':10,'ywf':10,'dsk':10,'qtje':20,'qtbz':'其它备注','tAR':40,'stid':2,'pk':'纸箱','pmode':'送货','hd':1,'tm':'','bz':'试试','commodityList':[{'pid':0,'pn':'桶'}]}";
	public final static String shipmentBill="{'did':1,'ddid':25,'vid':4,'ddid':98,'dtel':'2388238923','EID':1}";
	public final static String findShipmentBill="{'cp':1,'ps':2}";
	public final static String findFinanceBill="{'tyid':12,'EID':1,'cp':1,'ps':3}";
	public final static String findReceivingFinance="{'tyid':12,'EID':1,'AID':1,'cp':1,'ps':4}";
	public final static String billList="{'EID':1,'ddid':25,'bid':22,'ddn':'临安','billList':[{'bid':83},{'bid':84}]}";
	public final static String jh="[{'bid':133,'dn':'杭州'},{'bid':134,'dn':'杭州'},{'bid':135,'dn':'杭州'}]";
	public final static String acceptBill="{'bid':107,'AID':1,'EID':1,'qsr':'刁家龙','bz':'杭州签收','state':5,'rstatus':1}";
	public final static String financeBill="{'bid':118,'AID':1,'EID':1,'tyid':26,'money':11,'sid':38}";
	public final static String ST="[{'bid':165,'tm':'1005'},{'bid':165,'tm':'1006'},{'bid':165,'tm':'1008'},{'bid':166,'tm':'1009'}]";
  
}
