-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: uat.tangusoft.com    Database: tcmts2
-- ------------------------------------------------------
-- Server version	5.7.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `boil_project`
--

DROP TABLE IF EXISTS `boil_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `boil_project` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `project_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '煎煮方案名称',
  `soak_time` int(5) unsigned DEFAULT '0' COMMENT '浸泡时间',
  `first_boil_time` int(5) unsigned DEFAULT '0' COMMENT '头煎时间',
  `second_boil_time` int(5) unsigned DEFAULT '0' COMMENT '二煎时间',
  `volume` decimal(16,0) DEFAULT '0' COMMENT '药液量',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_user` int(10) unsigned DEFAULT NULL COMMENT '修改人ID',
  `mod_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='煎煮方案';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bt_relation_zd`
--

DROP TABLE IF EXISTS `bt_relation_zd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bt_relation_zd` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `hospital_id` int(10) unsigned DEFAULT NULL COMMENT '医院id',
  `header` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '表头名',
  `field_name` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '字段名',
  `table_type` int(2) DEFAULT NULL COMMENT '表类型 1=transfer_recipe，2=transfer_medicine',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `check_scan`
--

DROP TABLE IF EXISTS `check_scan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `check_scan` (
  `check_scan_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '关联id',
  `recipe_id` int(10) unsigned DEFAULT NULL COMMENT '处方id',
  `check_time` timestamp NULL DEFAULT NULL COMMENT '复核时间（年月日时分秒）',
  `check_id` int(10) unsigned DEFAULT NULL COMMENT '审核人id',
  PRIMARY KEY (`check_scan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `company_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公司全称',
  `company_short_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公司简称',
  `company_address` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公司地址',
  `company_tel` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公司电话',
  `company_contact` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `mod_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='煎药中心公司信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `employee_name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '员工姓名',
  `initial_code` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '周字母拼音',
  `use_state` tinyint(1) DEFAULT '0' COMMENT '0.在职,1.停止',
  `employee_code` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '员工编号',
  `employee_tel` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '办公电话',
  `role_id` int(10) unsigned DEFAULT '1' COMMENT '员工角色',
  `employee_password` varchar(50) COLLATE utf8_unicode_ci DEFAULT '28c8edde3d61a0411511d3b1866f0636' COMMENT '密码',
  `is_operator` int(10) unsigned DEFAULT '0' COMMENT '是否操作员',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间时间',
  `mod_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_employee_code` (`employee_code`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='员工表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exception_case`
--

DROP TABLE IF EXISTS `exception_case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exception_case` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `recipe_id` int(10) unsigned DEFAULT NULL COMMENT '处方id',
  `exception_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生成时间',
  `creator_id` int(10) unsigned DEFAULT NULL COMMENT '创建人ID',
  `exception_type` int(2) unsigned DEFAULT NULL COMMENT '异常类型',
  `exception_comment` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '异常描述',
  `deal_status` int(1) unsigned DEFAULT '0' COMMENT '处理状态:0.未处理,1.已处理',
  `deal_result` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '处理结果',
  `deal_time` timestamp NULL DEFAULT NULL COMMENT '处理时间',
  `deal_user` int(10) unsigned DEFAULT NULL COMMENT '处理人',
  `duty_id` int(10) unsigned DEFAULT NULL COMMENT '责任人id',
  `mod_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='异常处理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `export_xml`
--

DROP TABLE IF EXISTS `export_xml`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `export_xml` (
  `xml_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模板',
  `xml_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模板描述',
  `xml_value` text COLLATE utf8_unicode_ci COMMENT '模板内容'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hospital`
--

DROP TABLE IF EXISTS `hospital`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hospital` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `hospital_code` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '医院编码',
  `hospital_company` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '医院名称',
  `initial_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '拼音',
  `hospital_nickname` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '医院简称',
  `hospital_manager` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系人',
  `hospital_tel` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '电话',
  `province` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '市',
  `region` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '区',
  `address` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '地址',
  `settle_company_id` int(10) unsigned DEFAULT '0' COMMENT '结算方id',
  `outpatient_tag_print` int(10) unsigned DEFAULT '0' COMMENT '门诊标签打印模板ID',
  `outpatient_recipe_print` int(10) unsigned DEFAULT '0' COMMENT '门诊处方打印模板ID',
  `outpatient_tag_type` int(2) unsigned DEFAULT '0' COMMENT '门诊代配标签打印类型,1.固定,2.包数*贴数,3.贴数',
  `outpatient_tag_num` int(2) unsigned DEFAULT '0' COMMENT '门诊代配固定数量/额外数量',
  `outpatient_boil_type` int(2) DEFAULT '0' COMMENT '门诊代煎标签打印类型,1.固定,2.包数*贴数,3.贴数',
  `outpatient_boil_num` int(2) DEFAULT '0' COMMENT '门诊代煎固定数量/额外数量',
  `hospital_tag_print` int(10) unsigned DEFAULT '0' COMMENT '住院标签打印模板ID',
  `hospital_recipe_print` int(10) unsigned DEFAULT '0' COMMENT '住院处方打印模板ID',
  `hospital_tag_type` int(2) unsigned DEFAULT '0' COMMENT '住院代配标签打印类型,1.固定,2.包数*贴数,3.贴数',
  `hospital_tag_num` int(2) unsigned DEFAULT '0' COMMENT '住院代配固定数量/额外数量',
  `hospital_boil_type` int(2) DEFAULT '0' COMMENT '住院代煎标签打印类型,1.固定,2.包数*贴数,3.贴数',
  `hospital_boil_num` int(2) DEFAULT '0' COMMENT '住院代煎固定数量/额外数量',
  `carry_type` int(10) unsigned DEFAULT '0' COMMENT '默认快递方式',
  `sunshine_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '平台编号',
  `is_print_process` tinyint(1) DEFAULT '0' COMMENT '是否打印流程单，1打印，0不打印',
  `use_state` tinyint(1) DEFAULT '0' COMMENT '0.启用,1.停用',
  `mod_user` int(10) unsigned DEFAULT NULL COMMENT '修改人ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_hospital_code` (`hospital_code`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='医院';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `machine`
--

DROP TABLE IF EXISTS `machine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `machine` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `machine_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '煎药机编号',
  `machine_type` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '煎药机型号',
  `pack_machine_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '包装机编号',
  `machine_status` int(1) unsigned DEFAULT '0' COMMENT '状态：0正常,1.正在使用,9损坏',
  `checktor_id` int(10) unsigned DEFAULT NULL COMMENT '巡检人ID',
  `check_time` timestamp NULL DEFAULT NULL COMMENT '巡检时间',
  `health_status` int(1) unsigned DEFAULT '1' COMMENT '卫生状态1：合格 、0：不合格',
  `ster_status` int(1) unsigned DEFAULT '1' COMMENT '消毒状态1：合格、0：不合格',
  `use_state` tinyint(4) DEFAULT '1' COMMENT '1.启用,0.停用',
  `packing_mac` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '包装mac地址',
  `tag_printer_num` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '打印机型号',
  `tp_mac` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '温度采集MAC',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_user` int(10) unsigned DEFAULT NULL COMMENT '修改人ID',
  `mod_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_machine_code` (`machine_code`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='煎药机';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `medicine`
--

DROP TABLE IF EXISTS `medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `medicine_name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '中药名称',
  `medicine_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '中药编号',
  `initial_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称首字母',
  `mnemonic_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '助记码',
  `standard` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '单位规格',
  `special_boil_type` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '特殊煎药方式:先煎,后下',
  `tax_rate` decimal(10,2) DEFAULT NULL COMMENT '税率',
  `medicine_type` int(10) unsigned DEFAULT NULL COMMENT '药品类型:1.饮片,2.精制 3.细料 4.辅料',
  `cream_formula_type` int(2) unsigned DEFAULT '0' COMMENT '膏方类型:1.胶类,2.精类 3.粉类 4.参类 5.糖类 6.其他',
  `unit_type` int(2) DEFAULT NULL COMMENT '单位类型:0表示非重量，1表示重量',
  `common_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '平台编号',
  `operate_time` int(10) unsigned DEFAULT '0' COMMENT '操作时间',
  `use_state` tinyint(1) DEFAULT '0' COMMENT '0.在用,1.停止',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_user` int(10) unsigned DEFAULT NULL COMMENT '修改人ID',
  `mod_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=234 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='药品信息表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `ins_medicine` AFTER INSERT ON `medicine`
    FOR EACH ROW BEGIN
	insert into `medicine_relation`(hospital_medicine_name,initial_code,native_medicine_id,hospital_id)
	values(NEW.medicine_name,NEW.initial_code,NEW.id,0);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `medicine_detail`
--

DROP TABLE IF EXISTS `medicine_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine_detail` (
  `medicine_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `medicine_price_id` int(10) unsigned NOT NULL COMMENT '药品ID',
  PRIMARY KEY (`medicine_id`)
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `medicine_max_dose`
--

DROP TABLE IF EXISTS `medicine_max_dose`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine_max_dose` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `medicine_id` int(10) unsigned DEFAULT NULL COMMENT '药品ID',
  `max_weight_every` decimal(20,5) DEFAULT NULL COMMENT '单贴量上限',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_user` int(10) unsigned DEFAULT NULL COMMENT '修改人ID',
  `mod_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='超剂量管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `medicine_price`
--

DROP TABLE IF EXISTS `medicine_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine_price` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `medicine_id` int(10) unsigned DEFAULT NULL COMMENT '药品ID',
  `price_start` date DEFAULT NULL COMMENT '生效日期',
  `price_end` date DEFAULT NULL COMMENT '终止日期',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_user` int(10) unsigned DEFAULT NULL COMMENT '修改人ID',
  `mod_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `trade_price_one` decimal(20,6) DEFAULT '0.000000' COMMENT '一号批发价',
  `trade_price_two` decimal(20,6) DEFAULT '0.000000' COMMENT '二号批发价',
  `trade_price_three` decimal(20,6) DEFAULT '0.000000' COMMENT '三号批发价',
  `trade_price_four` decimal(20,6) DEFAULT '0.000000' COMMENT '四号批发价',
  `trade_price_five` decimal(20,6) DEFAULT '0.000000' COMMENT '五号批发价',
  `trade_price_six` decimal(20,6) DEFAULT '0.000000' COMMENT '六号批发价',
  `trade_price_seven` decimal(20,6) DEFAULT '0.000000' COMMENT '七号批发价',
  `trade_price_eight` decimal(20,6) DEFAULT '0.000000' COMMENT '八号批发价',
  `retail_price_one` decimal(20,6) DEFAULT '0.000000' COMMENT '一号零售价',
  `retail_price_two` decimal(20,6) DEFAULT '0.000000' COMMENT '二号零售价',
  `retail_price_three` decimal(20,6) DEFAULT '0.000000' COMMENT '三号零售价',
  `retail_price_four` decimal(20,6) DEFAULT '0.000000' COMMENT '四号零售价',
  `retail_price_five` decimal(20,6) DEFAULT '0.000000' COMMENT '五号零售价',
  `retail_price_six` decimal(20,6) DEFAULT '0.000000' COMMENT '六号零售价',
  `retail_price_seven` decimal(20,6) DEFAULT '0.000000' COMMENT '七号零售价',
  `retail_price_eight` decimal(20,6) DEFAULT '0.000000' COMMENT '八号零售价',
  PRIMARY KEY (`id`),
  KEY `idx_medicine_id` (`medicine_id`)
) ENGINE=InnoDB AUTO_INCREMENT=209 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='药品价格表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `ins_medicine_price` AFTER INSERT ON `medicine_price`
    FOR EACH ROW BEGIN
	IF IFNULL(NEW.trade_price_one,0) >0 OR IFNULL(NEW.retail_price_ONE,0) >0 THEN
		insert into `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		values(NEW.mod_user,now(),NEW.medicine_id,1,NEW.trade_price_one,NEW.retail_price_one,NEW.price_start);
	END IF;
	IF IFNULL(NEW.trade_price_two,0) >0  OR IFNULL(NEW.retail_price_two,0) >0 THEN
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,2,NEW.trade_price_two,NEW.retail_price_two,NEW.price_start);
	END IF;
	IF IFNULL(NEW.trade_price_three,0) >0  OR IFNULL(NEW.retail_price_three,0) >0 THEN
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,3,NEW.trade_price_three,NEW.retail_price_three,NEW.price_start);
	END IF;
	IF IFNULL(NEW.trade_price_four,0) >0  OR IFNULL(NEW.retail_price_four,0) >0 THEN
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,4,NEW.trade_price_four,NEW.retail_price_four,NEW.price_start);
	END IF;
	IF IFNULL(NEW.trade_price_five,0) >0  OR IFNULL(NEW.retail_price_five,0) >0 THEN
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,5,NEW.trade_price_five,NEW.retail_price_five,NEW.price_start);
	END IF;
	IF IFNULL(NEW.trade_price_six,0) >0  OR IFNULL(NEW.retail_price_six,0) >0 THEN
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,6,NEW.trade_price_six,NEW.retail_price_six,NEW.price_start);
	END IF;
	IF IFNULL(NEW.trade_price_seven,0) >0  OR IFNULL(NEW.retail_price_seven,0) >0 THEN
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,7,NEW.trade_price_seven,NEW.retail_price_seven,NEW.price_start);
	END IF;
	IF IFNULL(NEW.trade_price_eight,0) >0  OR IFNULL(NEW.retail_price_eight,0) >0 THEN
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,8,NEW.trade_price_eight,NEW.retail_price_eight,NEW.price_start);
	END IF;
	replace into medicine_detail (medicine_id,medicine_price_id)
	values(NEW.medicine_id,NEW.id);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `update_medicine_price` AFTER UPDATE ON `medicine_price`
    FOR EACH ROW BEGIN
	IF ifnull(NEW.trade_price_one,0) <> IFNULL(OLD.trade_price_one,0)  OR ifnull(NEW.retail_price_ONE,0) <> IFNULL(OLD.retail_price_ONE,0) THEN
		insert into `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		values(NEW.mod_user,now(),NEW.medicine_id,1,NEW.trade_price_one,NEW.retail_price_one,NEW.price_start);
	END IF;
	IF ifnull(NEW.trade_price_two,0) <> IFNULL(OLD.trade_price_two,0)  OR ifnull(NEW.retail_price_two,0) <> IFNULL(OLD.retail_price_two,0) THEN
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,2,NEW.trade_price_two,NEW.retail_price_two,NEW.price_start);
	END IF;
	IF IFNULL(NEW.trade_price_three,0) <> IFNULL(OLD.trade_price_three,0)  OR IFNULL(NEW.retail_price_three,0) <> IFNULL(OLD.retail_price_three,0) THEN
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,3,NEW.trade_price_three,NEW.retail_price_three,NEW.price_start);
	END IF;
	IF IFNULL(NEW.trade_price_four,0) <> IFNULL(OLD.trade_price_four,0)  OR IFNULL(NEW.retail_price_four,0) <> IFNULL(OLD.retail_price_four,0) THEN
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,4,NEW.trade_price_four,NEW.retail_price_four,NEW.price_start);
	END IF;
	IF IFNULL(NEW.trade_price_five,0) <> IFNULL(OLD.trade_price_five,0)  OR IFNULL(NEW.retail_price_five,0) <> IFNULL(OLD.retail_price_five,0) THEN
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,5,NEW.trade_price_five,NEW.retail_price_five,NEW.price_start);
	END IF;
	IF IFNULL(NEW.trade_price_six,0) <> IFNULL(OLD.trade_price_six,0)  OR IFNULL(NEW.retail_price_six,0) <> IFNULL(OLD.retail_price_six,0) THEN
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,6,NEW.trade_price_six,NEW.retail_price_six,NEW.price_start);
	END IF;
	IF IFNULL(NEW.trade_price_seven,0) <> IFNULL(OLD.trade_price_seven,0)  OR IFNULL(NEW.retail_price_seven,0) <> IFNULL(OLD.retail_price_seven,0) THEN
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,7,NEW.trade_price_seven,NEW.retail_price_seven,NEW.price_start);
	END IF;
	IF IFNULL(NEW.trade_price_eight,0) <> IFNULL(OLD.trade_price_eight,0)  OR IFNULL(NEW.retail_price_eight,0) <> IFNULL(OLD.retail_price_eight,0) THEN
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,8,NEW.trade_price_eight,NEW.retail_price_eight,NEW.price_start);
	END IF;
	if IFNULL(NEW.trade_price_one,0) = IFNULL(OLD.trade_price_one,0)  and IFNULL(NEW.retail_price_ONE,0) = IFNULL(OLD.retail_price_ONE,0)
	and IFNULL(NEW.trade_price_two,0) = IFNULL(OLD.trade_price_two,0)  and IFNULL(NEW.retail_price_two,0) = IFNULL(OLD.retail_price_two,0)
	and IFNULL(NEW.trade_price_three,0) = IFNULL(OLD.trade_price_three,0)  and  IFNULL(NEW.retail_price_three,0) = IFNULL(OLD.retail_price_three,0)
	and IFNULL(NEW.trade_price_four,0) = IFNULL(OLD.trade_price_four,0)  and IFNULL(NEW.retail_price_four,0) = IFNULL(OLD.retail_price_four,0)
	and IFNULL(NEW.trade_price_five,0) = IFNULL(OLD.trade_price_five,0)  AND IFNULL(NEW.retail_price_five,0) = IFNULL(OLD.retail_price_five,0)
	and IFNULL(NEW.trade_price_six,0) = IFNULL(OLD.trade_price_six,0)  AND IFNULL(NEW.retail_price_six,0) = IFNULL(OLD.retail_price_six,0)
	and IFNULL(NEW.trade_price_seven,0) = IFNULL(OLD.trade_price_seven,0)  AND IFNULL(NEW.retail_price_seven,0) = IFNULL(OLD.retail_price_seven,0)
	and IFNULL(NEW.trade_price_eight,0) = IFNULL(OLD.trade_price_eight,0)  AND IFNULL(NEW.retail_price_eight,0) = IFNULL(OLD.retail_price_eight,0)
	and NEW.price_start <> OLD.price_start then
		INSERT INTO `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		VALUES(NEW.mod_user,NOW(),NEW.medicine_id,0,null,null,NEW.price_start);
	end if;
	REPLACE INTO medicine_detail (medicine_id,medicine_price_id)
	VALUES(NEW.medicine_id,NEW.id);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `medicine_price_history`
--

DROP TABLE IF EXISTS `medicine_price_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine_price_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `creator_id` int(10) unsigned DEFAULT NULL COMMENT '调价人ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '调价时间',
  `medicine_id` int(10) unsigned DEFAULT NULL COMMENT '药品id',
  `price_type` int(10) unsigned DEFAULT NULL COMMENT '价格模板',
  `now_trade_price` decimal(20,8) DEFAULT NULL COMMENT '现批发价',
  `now_retail_price` decimal(20,8) DEFAULT NULL COMMENT '现零售价',
  `effective_date` date DEFAULT NULL COMMENT '生效日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=515 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='药品调整记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `medicine_producer`
--

DROP TABLE IF EXISTS `medicine_producer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine_producer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '产地',
  `company` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '生产企业',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `NewIndex1` (`area`,`company`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='药品产商';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `medicine_relation`
--

DROP TABLE IF EXISTS `medicine_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine_relation` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `hospital_medicine_name` varchar(50) DEFAULT NULL COMMENT '对接药品名称',
  `hospital_medicine_code` varchar(50) DEFAULT NULL COMMENT '对接药品编号',
  `initial_code` varchar(20) DEFAULT NULL COMMENT '对接药品名称拼音',
  `native_medicine_id` int(10) unsigned DEFAULT NULL COMMENT '本地药品ID',
  `hospital_id` int(10) unsigned NOT NULL COMMENT '通用ID=0/医院ID',
  `mod_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1694 DEFAULT CHARSET=utf8 COMMENT='药品关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `medicine_shelves`
--

DROP TABLE IF EXISTS `medicine_shelves`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine_shelves` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `shelves_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '货架号',
  `shelves_type` int(2) unsigned DEFAULT '0' COMMENT '货架类型:1.门诊,2.住院,3.快递',
  `remark` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_user` int(10) unsigned DEFAULT NULL COMMENT '修改人ID',
  `mod_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='货架管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `medicine_standard`
--

DROP TABLE IF EXISTS `medicine_standard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine_standard` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `medicine_id` int(10) unsigned DEFAULT NULL COMMENT '药品ID',
  `standard_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '规格名称',
  `warning_value` decimal(20,6) DEFAULT NULL COMMENT '预警值',
  `standard_code` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '规格编码',
  `batch_number` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '药品批号',
  `manufacturing_enterprise` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '生产企业',
  `producing_area` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '产地',
  `producer_id` int(10) DEFAULT NULL COMMENT '生产商/产地',
  `mod_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_medicine_id` (`medicine_id`)
) ENGINE=InnoDB AUTO_INCREMENT=258 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='药品规格';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `medicine_taboo`
--

DROP TABLE IF EXISTS `medicine_taboo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine_taboo` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_medicine_id` int(10) unsigned DEFAULT NULL COMMENT '药品ID',
  `sub_medicine_id` int(10) unsigned DEFAULT NULL COMMENT '禁忌药品ID',
  `taboo_type` int(2) DEFAULT NULL COMMENT '配伍禁忌类型',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_user` int(10) unsigned DEFAULT NULL COMMENT '修改人ID',
  `mod_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='配伍禁忌';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prescription`
--

DROP TABLE IF EXISTS `prescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prescription` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `hospital_id` int(10) unsigned DEFAULT NULL COMMENT '医院id',
  `prescription_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '配方名',
  `unified_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '统编代码',
  `system_name` varchar(40) DEFAULT NULL COMMENT '系统名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_user` int(10) unsigned DEFAULT NULL COMMENT '修改人ID',
  `mod_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8 COMMENT='协定方';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prescription_detail`
--

DROP TABLE IF EXISTS `prescription_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prescription_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `prescription_id` int(10) unsigned DEFAULT NULL COMMENT '药方ID',
  `medicine_id` int(10) unsigned DEFAULT NULL COMMENT '药品ID',
  `weight` decimal(20,5) DEFAULT NULL COMMENT '单贴量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=527 DEFAULT CHARSET=utf8 COMMENT='协定方明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `price_templet`
--

DROP TABLE IF EXISTS `price_templet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `price_templet` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `templet_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模板名称',
  `creator_id` int(10) DEFAULT NULL COMMENT '创建人ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='价格模板';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`cutebiz`@`%`*/ /*!50003 TRIGGER `insert_templet_detail` AFTER INSERT ON `price_templet`
FOR EACH ROW BEGIN
	INSERT INTO `price_templet_detail`
		(medicine_id, `templet_id`, price_start, mod_user)
	SELECT m.id, NEW.id , NOW(), NEW.creator_id FROM medicine m;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `price_templet_detail`
--

DROP TABLE IF EXISTS `price_templet_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `price_templet_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `templet_id` int(11) NOT NULL COMMENT '模板id',
  `medicine_id` int(10) unsigned DEFAULT NULL COMMENT '药品ID',
  `price_start` date DEFAULT NULL COMMENT '生效日期',
  `price_end` date DEFAULT NULL COMMENT '终止日期',
  `trade_price` decimal(20,8) DEFAULT NULL COMMENT '批发价',
  `retail_price` decimal(20,8) DEFAULT NULL COMMENT '零售价',
  `medicine_last` tinyint(1) DEFAULT '1' COMMENT '最后调价:1.是,0.否',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_user` int(10) DEFAULT NULL COMMENT '修改人',
  `modify_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_template_id` (`templet_id`),
  KEY `idx_medicine_id` (`medicine_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2083 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='价格模板明细';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`cutebiz`@`%`*/ /*!50003 TRIGGER `ins_price_templet_detail` AFTER INSERT ON `price_templet_detail`
FOR EACH ROW BEGIN
	IF ifnull(NEW.trade_price,0) >0  OR ifnull(NEW.retail_price,0) >0 THEN
		insert into `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		values(NEW.mod_user,now(),NEW.medicine_id,NEW.templet_id, NEW.trade_price,NEW.retail_price,NEW.price_start);
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`cutebiz`@`%`*/ /*!50003 TRIGGER `upd_price_templet_detail` AFTER UPDATE ON `price_templet_detail`
FOR EACH ROW BEGIN
	IF ifnull(NEW.trade_price,0) <> IFNULL(OLD.trade_price,0)  OR ifnull(NEW.retail_price,0) <> IFNULL(OLD.retail_price,0) THEN
		insert into `medicine_price_history`(creator_id,create_time,medicine_id,price_type,now_trade_price,now_retail_price,effective_date)
		values(NEW.mod_user,now(),NEW.medicine_id,NEW.templet_id, NEW.trade_price,NEW.retail_price,NEW.price_start);
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `print_field`
--

DROP TABLE IF EXISTS `print_field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `print_field` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `field_name` varchar(80) COLLATE utf8_unicode_ci NOT NULL COMMENT '字段名称',
  `field_type` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '字段类型',
  `field_source` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '字段',
  `field_para` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '存储组合',
  `display_text` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'tooltip',
  `display_width` int(10) unsigned DEFAULT '100' COMMENT '默认宽度',
  `display_height` int(10) unsigned DEFAULT '22' COMMENT '默认高度',
  `is_hidden` tinyint(1) DEFAULT '0' COMMENT '是否隐藏',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=503 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='打印字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `print_item`
--

DROP TABLE IF EXISTS `print_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `print_item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `page_id` int(10) unsigned NOT NULL COMMENT '打印模板ID',
  `field_id` int(10) unsigned NOT NULL COMMENT '字段ID',
  `item_x` int(10) unsigned NOT NULL DEFAULT '10' COMMENT 'x点',
  `item_y` int(10) unsigned NOT NULL DEFAULT '10' COMMENT 'y点',
  `item_width` int(10) unsigned NOT NULL DEFAULT '100' COMMENT '宽',
  `item_height` int(10) unsigned NOT NULL DEFAULT '20' COMMENT '高',
  `item_para` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '打印组合',
  `font_family` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '字体类型',
  `font_size` int(10) unsigned NOT NULL DEFAULT '14' COMMENT '字体大小',
  `font_weight` tinyint(1) NOT NULL COMMENT '是否加粗',
  `font_style` tinyint(1) NOT NULL COMMENT '斜体',
  `font_decoration` tinyint(1) NOT NULL COMMENT '下划线',
  `text_align` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '字体对齐方式',
  `letter_spacing` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '字体间距',
  `background_color` tinyint(1) DEFAULT '0' COMMENT '是否黑色背景',
  `auto_size` tinyint(1) unsigned DEFAULT '0' COMMENT '自动缩放字体:1是,0.否',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_page_id` (`page_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5708 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='打印模板明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `print_page`
--

DROP TABLE IF EXISTS `print_page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `print_page` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `page_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL COMMENT '模板名称',
  `page_width` int(10) unsigned DEFAULT '230' COMMENT '打印宽度',
  `page_height` int(10) unsigned DEFAULT '127' COMMENT '打印高度',
  `layout_direction` int(1) unsigned DEFAULT '1' COMMENT '排版方向:1.横向,2.纵向',
  `col_number` int(2) unsigned DEFAULT '0' COMMENT '列数或行数',
  `row_spacing` int(4) unsigned DEFAULT '0' COMMENT '行间距',
  `col_spacing` int(4) unsigned DEFAULT '0' COMMENT '列间距',
  `single_page_number` int(4) unsigned DEFAULT '1' COMMENT '单页数量',
  `bill_type` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模板类型',
  `default_state` tinyint(1) DEFAULT '0' COMMENT '是否默认模板:1.是,0.否',
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '背景图片路径',
  `image_width` int(10) unsigned DEFAULT '0' COMMENT '背景图片宽度',
  `image_height` int(10) unsigned DEFAULT '0' COMMENT '背景图片高度',
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `background_image` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '打印背景:图片路径',
  `print_background_image` tinyint(1) DEFAULT '0' COMMENT '是否打印背景图',
  `printer_id` int(10) DEFAULT '0' COMMENT '打印机ID',
  `mod_user` int(10) unsigned DEFAULT NULL COMMENT '修改人ID',
  `mod_time` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='打印模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `printer`
--

DROP TABLE IF EXISTS `printer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `printer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `printer_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '打印机名称',
  `printer_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '打印机编号',
  `printer_ip` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '打印机IP',
  `seq` int(2) DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='打印机列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipe` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开单时间',
  `deal_date` timestamp NULL DEFAULT NULL COMMENT '受理日期',
  `sys_recipe_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '系统处方号',
  `recipe_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '医院处方号',
  `recipe_serial` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '处方序号',
  `recipe_bh` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '处方编号',
  `mod_user` int(10) unsigned DEFAULT NULL COMMENT '修改人ID',
  `mod_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator_id` int(10) unsigned DEFAULT NULL COMMENT '开单人ID',
  `shipping_state` int(3) unsigned DEFAULT NULL COMMENT '状态',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `hospital_id` int(10) unsigned DEFAULT NULL COMMENT '医院id',
  `settle_company_id` int(10) unsigned DEFAULT NULL COMMENT '结算方id',
  `boil_type` int(2) unsigned DEFAULT NULL COMMENT '煎药方式(1.代煎药,2.自煎,3,膏方代煎,4.膏方自煎)',
  `recipient_id` int(10) unsigned DEFAULT NULL COMMENT '收件人id',
  `recipient_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '病人姓名',
  `sex` varchar(5) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '病人性别',
  `age` varchar(5) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '病人年龄',
  `recipient_tel` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '病人电话',
  `consignee` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '收货人',
  `consignee_tel` varchar(25) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '收货人电话',
  `province` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '省',
  `city` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '市',
  `region` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '区',
  `recipient_address` varchar(100) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '收货地址',
  `inpatient_area` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '病区',
  `bed_number` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '床位',
  `department` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '科别',
  `express_site` varchar(30) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '配送点',
  `belong` int(2) unsigned DEFAULT '1' COMMENT '1.属于门诊，2.属于住院',
  `doctor_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '医生',
  `pathogen` varchar(500) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '临床病症',
  `usage` varchar(200) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '服法',
  `quantity` int(10) unsigned DEFAULT NULL COMMENT '贴数',
  `package_paste` int(2) DEFAULT NULL COMMENT '每贴几包',
  `total_package_paste` int(2) unsigned DEFAULT NULL COMMENT '总包数',
  `medicine_quantity` int(3) unsigned DEFAULT NULL COMMENT '药味数',
  `total_weight` decimal(20,5) DEFAULT NULL COMMENT '药品总量',
  `soak_time` int(3) unsigned DEFAULT '0' COMMENT '浸泡时间',
  `boil_time` int(3) unsigned DEFAULT '0' COMMENT '煎药时间',
  `carry_id` int(2) unsigned DEFAULT NULL COMMENT '配送公司',
  `business_type` int(2) unsigned DEFAULT NULL COMMENT '业务类型',
  `input_time` timestamp NULL DEFAULT NULL COMMENT '配方补录时间',
  `input_employee` int(10) unsigned DEFAULT NULL COMMENT '配方补录人ID',
  `input_state` int(2) unsigned DEFAULT '0' COMMENT '配方录入状态,1.已录入',
  `logistics_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '物流单号',
  `origin_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '原寄地区号',
  `dest_code` varchar(10) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '目的地区号',
  `make_order_state` int(2) unsigned DEFAULT NULL COMMENT '物流下单状态',
  `boil_id` int(10) unsigned DEFAULT NULL COMMENT '煎煮员ID',
  `actual_boil_time` int(4) DEFAULT NULL COMMENT '实际煎药时间',
  `soak_id` int(10) unsigned DEFAULT NULL COMMENT '浸泡员id',
  `start_soak_time` timestamp NULL DEFAULT NULL COMMENT '开始浸泡时间',
  `check_state` int(2) unsigned DEFAULT '0' COMMENT '审核状态0：未审核 1.审核',
  `machine_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '煎药机编码',
  `dispense_id` int(10) DEFAULT '0' COMMENT '审方员ID',
  `recipe_source` int(2) unsigned DEFAULT '0' COMMENT '0.手工方,1.电子处方,2.excel导入',
  `use_his_money` tinyint(1) DEFAULT '0' COMMENT '是否使用医院金额',
  `money` decimal(20,6) DEFAULT NULL COMMENT '医院金额',
  `trade_freight` decimal(20,6) DEFAULT NULL COMMENT '批发费用',
  `retail_freight` decimal(20,6) DEFAULT NULL COMMENT '药品零售费用',
  `vat_freight` decimal(20,5) DEFAULT NULL COMMENT '含税金额',
  `taxless_freight` decimal(20,6) DEFAULT NULL COMMENT '无税金额',
  `discount` decimal(10,3) DEFAULT NULL COMMENT '扣率',
  `process_cost` decimal(20,6) DEFAULT NULL COMMENT '加工费',
  `special_print` varchar(100) CHARACTER SET utf8 DEFAULT '' COMMENT '特殊打印字段',
  `print_state` int(2) unsigned DEFAULT '0' COMMENT '打印状态,1.已打印',
  `pack_type` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '包装规格',
  `other_package` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '另包',
  `packing_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '包装名称(袋装,瓶装...)',
  `pre_check_state` int(2) DEFAULT '1' COMMENT '预审核方状态:1正常,2.配伍禁忌',
  `custom_one_text` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '自定义文本1',
  `custom_two_text` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '自定义文本2',
  `custom_one_fee` decimal(20,5) DEFAULT NULL COMMENT '自定义费用1',
  `custom_two_fee` decimal(20,5) DEFAULT NULL COMMENT '自定义费用2',
  `outpatient_num` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '门诊号/住院号',
  `invoice_code` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '发票号',
  `take_type` int(2) unsigned DEFAULT NULL COMMENT '用法：1.内服,2.外用,3.灌肠',
  `take_time` datetime DEFAULT NULL COMMENT '取药时间',
  `shelves_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '货架号',
  `receive_remark` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '接方备注',
  `second_boil_time` int(5) unsigned DEFAULT '0' COMMENT '二煎时间',
  `boil_project_id` int(10) unsigned DEFAULT NULL COMMENT '煎煮方案id',
  PRIMARY KEY (`id`),
  KEY `idx_recipe_code` (`sys_recipe_code`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1358 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='处方表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 trigger `trig_recipe_insert` AFTER INSERT on `recipe`
for each row BEGIN
	DECLARE nickname varchar(100);
	select hospital_nickname into nickname from hospital where id=NEW.hospital_id;
	insert into `recipe_track`(`hospital_nickname`,`recipient_name`,`quantity`,`shipping_state`,`accept_time`,`last_time`,`recipe_id`) values
	(nickname,NEW.recipient_name,NEW.quantity,1,now(),now(),NEW.id);
    END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `update_recipe` AFTER UPDATE ON `recipe`
    FOR EACH ROW BEGIN
    DECLARE old_comment VARCHAR(50);
    DECLARE new_comment VARCHAR(50);

    if ifnull(NEW.recipe_code,'') <> ifnull(OLD.recipe_code,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改医院处方号：',IFNULL(OLD.recipe_code,''),'->',IFNULL(NEW.recipe_code,'')));
    end if;
    IF IFNULL(NEW.deal_date,'') <> IFNULL(OLD.deal_date,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改受理时间：',IFNULL(OLD.deal_date,''),'->',IFNULL(NEW.deal_date,'')));
    END IF;
    IF IFNULL(NEW.recipe_serial,'') <> IFNULL(OLD.recipe_serial,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改处方序号：',IFNULL(OLD.recipe_serial,''),'->',IFNULL(NEW.recipe_serial,'')));
    END IF;
    IF IFNULL(NEW.recipe_bh,'') <> IFNULL(OLD.recipe_bh,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改处方编号：',IFNULL(OLD.recipe_bh,''),'->',IFNULL(NEW.recipe_bh,'')));
    END IF;
    IF IFNULL(NEW.remark,'') <> IFNULL(OLD.remark,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改备注：',IFNULL(OLD.remark,''),'->',IFNULL(NEW.remark,'')));
    END IF;
    IF IFNULL(NEW.hospital_id,'') <> IFNULL(OLD.hospital_id,'') THEN
	select hospital_company into new_comment from `hospital` where id = NEW.hospital_id;
	SELECT hospital_company INTO old_comment FROM `hospital` WHERE id = OLD.hospital_id;
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改医院名称：',old_comment,'->',new_comment));
    END IF;
    IF IFNULL(NEW.boil_type,0) <> IFNULL(OLD.boil_type,0) THEN

	if IFNULL(OLD.boil_type,0) = 0 THEN
		set old_comment = '';
	end if;
	IF IFNULL(OLD.boil_type,0) = 1 THEN
		SET old_comment = '代煎';
	END IF;
	IF IFNULL(OLD.boil_type,0) = 2 THEN
		SET old_comment = '自煎';
	END IF;
	IF IFNULL(OLD.boil_type,0) = 3 THEN
		SET old_comment = '膏方代煎';
	END IF;
	IF IFNULL(OLD.boil_type,0) = 4 THEN
		SET old_comment = '膏方自煎';
	END IF;
	IF IFNULL(NEW.boil_type,0) = 0 THEN
		SET new_comment = '';
	END IF;
	IF IFNULL(NEW.boil_type,0) = 1 THEN
		SET new_comment = '代煎';
	END IF;
	IF IFNULL(NEW.boil_type,0) = 2 THEN
		SET new_comment = '自煎';
	END IF;
	IF IFNULL(NEW.boil_type,0) = 3 THEN
		SET new_comment = '膏方代煎';
	END IF;
	IF IFNULL(NEW.boil_type,0) = 4 THEN
		SET new_comment = '膏方自煎';
	END IF;
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改煎药方式：',old_comment,'->',new_comment));
    END IF;
    IF IFNULL(NEW.recipient_name,'') <> IFNULL(OLD.recipient_name,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改病人姓名：',IFNULL(OLD.recipient_name,''),'->',IFNULL(NEW.recipient_name,'')));
    END IF;
    IF IFNULL(NEW.sex,'') <> IFNULL(OLD.sex,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改病人性别：',IFNULL(OLD.sex,''),'->',IFNULL(NEW.sex,'')));
    END IF;
    IF IFNULL(NEW.age,'') <> IFNULL(OLD.age,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改病人年龄：',IFNULL(OLD.age,''),'->',IFNULL(NEW.age,'')));
    END IF;
    IF IFNULL(NEW.recipient_tel,'') <> IFNULL(OLD.recipient_tel,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改病人电话：',IFNULL(OLD.recipient_tel,''),'->',IFNULL(NEW.recipient_tel,'')));
    END IF;
    IF IFNULL(NEW.consignee,'') <> IFNULL(OLD.consignee,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改收货人：',IFNULL(OLD.consignee,''),'->',IFNULL(NEW.consignee,'')));
    END IF;
    IF IFNULL(NEW.consignee_tel,'') <> IFNULL(OLD.consignee_tel,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改收货人电话：',IFNULL(OLD.consignee_tel,''),'->',IFNULL(NEW.consignee_tel,'')));
    END IF;
    IF IFNULL(NEW.province,'') <> IFNULL(OLD.province,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改省：',IFNULL(OLD.province,''),'->',IFNULL(NEW.province,'')));
    END IF;
    IF IFNULL(NEW.city,'') <> IFNULL(OLD.city,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改市：',IFNULL(OLD.city,''),'->',IFNULL(NEW.city,'')));
    END IF;
    IF IFNULL(NEW.region,'') <> IFNULL(OLD.region,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改区：',IFNULL(OLD.region,''),'->',IFNULL(NEW.region,'')));
    END IF;
    IF IFNULL(NEW.recipient_address,'') <> IFNULL(OLD.recipient_address,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改收货地址：',IFNULL(OLD.recipient_address,''),'->',IFNULL(NEW.recipient_address,'')));
    END IF;
    IF IFNULL(NEW.inpatient_area,'') <> IFNULL(OLD.inpatient_area,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改病区：',IFNULL(OLD.inpatient_area,''),'->',IFNULL(NEW.inpatient_area,'')));
    END IF;
    IF IFNULL(NEW.bed_number,'') <> IFNULL(OLD.bed_number,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改床位：',IFNULL(OLD.bed_number,''),'->',IFNULL(NEW.bed_number,'')));
    END IF;
    IF IFNULL(NEW.department,'') <> IFNULL(OLD.department,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改就诊科室：',IFNULL(OLD.department,''),'->',IFNULL(NEW.department,'')));
    END IF;
    IF IFNULL(NEW.express_site,'') <> IFNULL(OLD.express_site,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改就配送点：',IFNULL(OLD.express_site,''),'->',IFNULL(NEW.express_site,'')));
    END IF;
    IF IFNULL(NEW.belong,0) <> IFNULL(OLD.belong,0) THEN
	if IFNULL(NEW.belong,0) = 0 then
		set new_comment = '';
	end if;
	IF IFNULL(NEW.belong,0) = 1 THEN
		SET new_comment = '门诊';
	END IF;
	IF IFNULL(NEW.belong,0) = 2 THEN
		SET new_comment = '住院';
	END IF;
	IF IFNULL(OLD.belong,0) = 0 THEN
		SET old_comment = '';
	END IF;
	IF IFNULL(OLD.belong,0) = 1 THEN
		SET old_comment = '门诊';
	END IF;
	IF IFNULL(OLD.belong,0) = 2 THEN
		SET old_comment = '住院';
	END IF;
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改门诊住院：',old_comment,'->',new_comment));
    END IF;
    IF IFNULL(NEW.doctor_name,'') <> IFNULL(OLD.doctor_name,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改医生姓名：',IFNULL(OLD.doctor_name,''),'->',IFNULL(NEW.doctor_name,'')));
    END IF;
    IF IFNULL(NEW.pathogen,'') <> IFNULL(OLD.pathogen,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改临床诊断：',IFNULL(OLD.pathogen,''),'->',IFNULL(NEW.pathogen,'')));
    END IF;
    IF IFNULL(NEW.usage,'') <> IFNULL(OLD.usage,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改服法：',IFNULL(OLD.usage,''),'->',IFNULL(NEW.usage,'')));
    END IF;
    IF IFNULL(NEW.quantity,0) <> IFNULL(OLD.quantity,0) THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改帖数：',IFNULL(OLD.quantity,0),'->',IFNULL(NEW.quantity,0)));
    END IF;
    IF IFNULL(NEW.package_paste,0) <> IFNULL(OLD.package_paste,0) THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改包数：',IFNULL(OLD.package_paste,0),'->',IFNULL(NEW.package_paste,0)));
    END IF;
    IF IFNULL(NEW.soak_time,0) <> IFNULL(OLD.soak_time,0) THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改浸泡时间：',IFNULL(OLD.soak_time,0),'->',IFNULL(NEW.soak_time,0)));
    END IF;
    IF IFNULL(NEW.boil_time,0) <> IFNULL(OLD.boil_time,0) THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改煎药时间：',IFNULL(OLD.boil_time,0),'->',IFNULL(NEW.boil_time,0)));
    END IF;
    IF IFNULL(NEW.carry_id,0) <> IFNULL(OLD.carry_id,0) THEN
	if  IFNULL(NEW.carry_id,0) = 0 then
		set new_comment = '';
	end if;
	IF  IFNULL(NEW.carry_id,0) = 1 THEN
		SET new_comment = '顺丰';
	END IF;
	IF  IFNULL(NEW.carry_id,0) = 2 THEN
		SET new_comment = '邮政';
	END IF;
	IF  IFNULL(NEW.carry_id,0) = 3 THEN
		SET new_comment = '送医院';
	END IF;
	IF  IFNULL(OLD.carry_id,0) = 0 THEN
		SET old_comment = '';
	END IF;
	IF  IFNULL(OLD.carry_id,0) = 1 THEN
		SET old_comment = '顺丰';
	END IF;
	IF  IFNULL(OLD.carry_id,0) = 2 THEN
		SET old_comment = '邮政';
	END IF;
	IF  IFNULL(OLD.carry_id,0) = 3 THEN
		SET old_comment = '送医院';
	END IF;
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改配送公司：',old_comment,'->',new_comment));
    END IF;
    IF IFNULL(NEW.business_type,0) <> IFNULL(OLD.business_type,0) THEN
	IF  IFNULL(NEW.business_type,0) = 0 THEN
		SET new_comment = '';
	END IF;
	IF  IFNULL(NEW.business_type,0) = 1 THEN
		SET new_comment = '当天快件';
	END IF;
	IF  IFNULL(NEW.business_type,0) = 2 THEN
		SET new_comment = '电商特惠';
	END IF;
	IF  IFNULL(NEW.business_type,0) = 3 THEN
		SET new_comment = '标准快递';
	END IF;
	IF  IFNULL(NEW.business_type,0) = 4 THEN
		SET new_comment = '医药常温';
	END IF;
	IF  IFNULL(OLD.business_type,0) = 0 THEN
		SET old_comment = '';
	END IF;
	IF  IFNULL(OLD.business_type,0) = 1 THEN
		SET old_comment = '当天快件';
	END IF;
	IF  IFNULL(OLD.business_type,0) = 2 THEN
		SET old_comment = '电商特惠';
	END IF;
	IF  IFNULL(OLD.business_type,0) = 3 THEN
		SET old_comment = '标准快递';
	END IF;
	IF  IFNULL(OLD.business_type,0) = 4 THEN
		SET old_comment = '医药常温';
	END IF;
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改快件类型：',old_comment,'->',new_comment));
    END IF;
    IF IFNULL(NEW.discount,0) <> IFNULL(OLD.discount,0) THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改扣率：',IFNULL(OLD.discount,0),'->',IFNULL(NEW.discount,0)));
    END IF;
    IF IFNULL(NEW.process_cost,0) <> IFNULL(OLD.process_cost,0) THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改加工费：',FORMAT(IFNULL(OLD.process_cost,0),3),'->',FORMAT(IFNULL(NEW.process_cost,0),3)));
    END IF;
    IF IFNULL(NEW.pack_type,'') <> IFNULL(OLD.pack_type,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改包装规格：',IFNULL(OLD.pack_type,''),'->',IFNULL(NEW.pack_type,'')));
    END IF;
    IF IFNULL(NEW.packing_name,'') <> IFNULL(OLD.packing_name,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改包装类型：',IFNULL(OLD.packing_name,''),'->',IFNULL(NEW.packing_name,'')));
    END IF;
    IF IFNULL(NEW.medicine_quantity,0) <> IFNULL(OLD.medicine_quantity,0) THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改草药位数：',IFNULL(OLD.medicine_quantity,0),'->',IFNULL(NEW.medicine_quantity,0)));
    END IF;
    IF IFNULL(NEW.total_package_paste,0) <> IFNULL(OLD.total_package_paste,0) THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改总包数：',IFNULL(OLD.total_package_paste,0),'->',IFNULL(NEW.total_package_paste,0)));
    END IF;
    IF IFNULL(NEW.other_package,'') <> IFNULL(OLD.other_package,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改另包：',IFNULL(OLD.other_package,''),'->',IFNULL(NEW.other_package,'')));
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`cutebiz`@`%`*/ /*!50003 TRIGGER `trig_recipe_update` AFTER UPDATE ON `recipe`
FOR EACH ROW BEGIN
	IF OLD.shipping_state!=NEW.shipping_state THEN
		IF NEW.shipping_state=5 THEN
			update recipe_track set last_time=now(),shipping_state=17 where recipe_id=OLD.id;
		END IF;
		IF NEW.shipping_state=10 THEN
			update recipe_track set last_time=now(),shipping_state=3 where recipe_id=OLD.id;
		END IF;
		IF NEW.shipping_state=15 THEN
			update recipe_track set last_time=now(),shipping_state=4 where recipe_id=OLD.id;
		END IF;
		IF NEW.shipping_state=30 THEN
			update recipe_track set last_time=now(),shipping_state=5 where recipe_id=OLD.id;
		END IF;
		IF NEW.shipping_state=40 THEN
			update recipe_track set last_time=now(),shipping_state=6 where recipe_id=OLD.id;
		END IF;
		IF NEW.shipping_state=50 THEN
			update recipe_track set last_time=now(),shipping_state=7 where recipe_id=OLD.id;
		END IF;
		IF NEW.shipping_state=85 THEN
			update recipe_track set last_time=now(),shipping_state=8 where recipe_id=OLD.id;

		END IF;
		IF NEW.shipping_state=90 THEN
			update recipe_track set last_time=now(),shipping_state=9 where recipe_id=OLD.id;

		END IF;
		IF NEW.shipping_state=999 THEN
			update recipe_track set last_time=now(),shipping_state=999 where recipe_id=OLD.id;
                                          INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
                               	VALUES(NEW.id,NEW.mod_user,'作废');
		END IF;
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `recipe_finance`
--

DROP TABLE IF EXISTS `recipe_finance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipe_finance` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `recipe_id` int(10) unsigned DEFAULT NULL COMMENT '处方id',
  `hospital_status` int(2) unsigned DEFAULT NULL COMMENT '医院对账状态',
  `hospital_close_time` timestamp NULL DEFAULT NULL COMMENT '对账时间',
  `hospital_closed_by` int(10) unsigned DEFAULT NULL COMMENT '对账人',
  PRIMARY KEY (`id`),
  KEY `idx_recipe_id` (`recipe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='处方财务状态';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe_history`
--

DROP TABLE IF EXISTS `recipe_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipe_history` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `recipe_id` int(10) unsigned DEFAULT NULL COMMENT '处方id',
  `operate_type` int(10) unsigned DEFAULT NULL COMMENT '操作类型',
  `operate_user` int(10) unsigned DEFAULT NULL COMMENT '修改人id',
  `operate_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `operate_comment` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '修改内容',
  PRIMARY KEY (`id`),
  KEY `idx_recipe_id` (`recipe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1711 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='处方记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe_medicine`
--

DROP TABLE IF EXISTS `recipe_medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipe_medicine` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `recipe_id` int(10) unsigned DEFAULT NULL COMMENT '处方id',
  `medicine_id` int(10) unsigned DEFAULT NULL COMMENT '中药id',
  `medicine_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '药品名',
  `initial_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '药品拼音',
  `weight_every` decimal(20,5) DEFAULT NULL COMMENT '单贴量',
  `trad_price` decimal(20,6) DEFAULT NULL COMMENT '批发价',
  `actual_prcie` decimal(20,6) DEFAULT NULL COMMENT '实价=批发价*扣率，但可以在对账是修改',
  `retail_price` decimal(20,6) DEFAULT NULL COMMENT '零售价',
  `trade_freight` decimal(20,6) DEFAULT NULL COMMENT '费用=药量*批发价',
  `retail_freight` decimal(20,6) DEFAULT NULL COMMENT '零售费用=药量*零售价',
  `money` decimal(20,6) DEFAULT NULL COMMENT '含税金额',
  `standard_weight` decimal(15,2) DEFAULT NULL COMMENT '规格量',
  `special_boil_type` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '特殊煎药方式',
  `transfer_medicine_id` bigint(20) unsigned DEFAULT NULL COMMENT '订单明细id',
  PRIMARY KEY (`id`),
  KEY `idx_recipe_id` (`recipe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9501 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='处方明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe_packing_print`
--

DROP TABLE IF EXISTS `recipe_packing_print`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipe_packing_print` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '中间表id',
  `recipe_id` int(10) unsigned DEFAULT NULL COMMENT '处方id',
  `machine_id` int(10) unsigned DEFAULT NULL COMMENT '煎药机id',
  `print_number` int(30) unsigned DEFAULT NULL COMMENT '打印次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe_patrol`
--

DROP TABLE IF EXISTS `recipe_patrol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipe_patrol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recipe_id` int(10) DEFAULT NULL COMMENT '处方ID',
  `operate_id` int(11) DEFAULT NULL COMMENT '操作人ID',
  `operate_type` int(11) DEFAULT NULL COMMENT '1.配方抽检,2.煎药抽检',
  `operate_comment` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `actual_amount` decimal(20,8) DEFAULT NULL COMMENT '实际量',
  `actual_kind` int(11) DEFAULT NULL COMMENT '实际药味',
  `patrol_result` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '巡查结果',
  `patrol_status` int(2) DEFAULT NULL COMMENT '巡查状态',
  `plan_amount` decimal(20,8) DEFAULT NULL COMMENT '计划量',
  `operate_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生产时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='抽检';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe_proccess`
--

DROP TABLE IF EXISTS `recipe_proccess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipe_proccess` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '处方流程id',
  `recipe_id` int(10) unsigned DEFAULT NULL COMMENT '处方id',
  `make_id` int(10) unsigned DEFAULT NULL COMMENT '配药人id',
  `make_time` timestamp NULL DEFAULT NULL COMMENT '配药时间',
  `check_id` int(10) unsigned DEFAULT NULL COMMENT '复核人id',
  `check_time` timestamp NULL DEFAULT NULL COMMENT '复核时间',
  `soak_id` int(10) unsigned DEFAULT NULL COMMENT '浸泡人id',
  `start_soak_time` timestamp NULL DEFAULT NULL COMMENT '开始浸泡时间',
  `start_boil_id` int(10) unsigned DEFAULT NULL COMMENT '开始煎煮人id',
  `start_boil_time` timestamp NULL DEFAULT NULL COMMENT '开始煎煮时间',
  `boil_machine_id` int(10) unsigned DEFAULT NULL COMMENT '煎药机id',
  `end_boil_id` int(10) unsigned DEFAULT NULL COMMENT '结束煎煮人id',
  `end_boil_time` timestamp NULL DEFAULT NULL COMMENT '结束煎煮时间',
  `collect_id` int(10) unsigned DEFAULT NULL COMMENT '收膏人id',
  `collect_time` timestamp NULL DEFAULT NULL COMMENT '收膏时间',
  `package_id` int(10) unsigned DEFAULT NULL COMMENT '包装人id',
  `package_time` timestamp NULL DEFAULT NULL COMMENT '包装时间',
  `package_machine_id` int(10) unsigned DEFAULT NULL COMMENT '包装机id',
  `start_concentrate_id` int(10) unsigned DEFAULT NULL COMMENT '开始浓缩人id',
  `start_concentrate_time` timestamp NULL DEFAULT NULL COMMENT '开始浓缩时间',
  `start_concentrate_volume` float DEFAULT NULL COMMENT '开始浓缩体积',
  `end_concentrate_id` int(10) unsigned DEFAULT NULL COMMENT '结束浓缩人id',
  `end_concentrate_time` timestamp NULL DEFAULT NULL COMMENT '结束浓缩时间',
  `end_concentrate_volume` float DEFAULT NULL COMMENT '结束浓缩体积',
  `end_subside_id` int(10) DEFAULT NULL COMMENT '结束沉淀人id',
  `end_subside_time` timestamp NULL DEFAULT NULL COMMENT '结束沉淀时间',
  `frist_boil_id` int(10) unsigned DEFAULT NULL COMMENT '先煎人id',
  `frist_boil_time` timestamp NULL DEFAULT NULL COMMENT '先煎开始时间',
  `check_bill_id` int(10) unsigned DEFAULT NULL COMMENT '审方人id',
  `check_bill_time` timestamp NULL DEFAULT NULL COMMENT '审方时间',
  `after_boil_id` int(10) unsigned DEFAULT NULL COMMENT '后下人id',
  `after_boil_time` timestamp NULL DEFAULT NULL COMMENT '后下时间',
  `delivery_id` int(10) unsigned DEFAULT NULL COMMENT '发货人id',
  `delivery_time` timestamp NULL DEFAULT NULL COMMENT '发货时间',
  `shelve_time` datetime DEFAULT NULL COMMENT '上架时间',
  `modify_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `shelve_user` int(10) unsigned DEFAULT NULL COMMENT '上架人id',
  `second_boil_id` int(10) unsigned DEFAULT NULL COMMENT '二煎人id',
  `start_second_boil_time` timestamp NULL DEFAULT NULL COMMENT '二煎时间',
  PRIMARY KEY (`id`),
  KEY `idx_recipe_id` (`recipe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1255 DEFAULT CHARSET=utf8 COMMENT='流程记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe_scan`
--

DROP TABLE IF EXISTS `recipe_scan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipe_scan` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `recipe_id` int(10) unsigned DEFAULT NULL COMMENT '处方id',
  `receive_scan_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '节点时间',
  `receive_scan_use` int(10) unsigned DEFAULT NULL COMMENT '操作人',
  `picture_path` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '图片路径',
  `operate_comment` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作说明',
  `operate_type` int(2) unsigned DEFAULT NULL COMMENT '操作类型',
  `machine_id` int(10) unsigned DEFAULT NULL COMMENT '煎药机id',
  `quantity` int(10) unsigned DEFAULT NULL COMMENT '相应处方贴数',
  `concentrate_volume` float DEFAULT NULL COMMENT '浓缩体积',
  `mod_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_recipe_id` (`recipe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=549 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='操作流程表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe_serial`
--

DROP TABLE IF EXISTS `recipe_serial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipe_serial` (
  `recipe_serial` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '取药号',
  `recipe_id` int(10) DEFAULT NULL COMMENT '处方ID',
  PRIMARY KEY (`recipe_serial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipe_track`
--

DROP TABLE IF EXISTS `recipe_track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipe_track` (
  `recipe_track_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '处方跟踪id',
  `hospital_nickname` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '医院简称',
  `recipient_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收件人',
  `quantity` int(10) unsigned DEFAULT NULL COMMENT '贴数',
  `shipping_state` int(2) unsigned DEFAULT NULL COMMENT '状态',
  `accept_time` timestamp NULL DEFAULT NULL COMMENT '接收时间',
  `last_time` timestamp NULL DEFAULT NULL COMMENT '最新更新时间',
  `recipe_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`recipe_track_id`),
  KEY `idx_recipe_id` (`recipe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1275570 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recipient`
--

DROP TABLE IF EXISTS `recipient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipient` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `recipient_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '病人姓名',
  `recipient_tel` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '病人电话',
  `consignee` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货人',
  `consignee_tel` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货人电话',
  `sex` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '性别',
  `age` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '年龄',
  `recipient_address` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '地址',
  `province` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '市',
  `region` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '区',
  `initial_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '拼音',
  `target_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '目的码',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `mod_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_recipient_tel` (`recipient_tel`)
) ENGINE=InnoDB AUTO_INCREMENT=1033 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='病人记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `server_user`
--

DROP TABLE IF EXISTS `server_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server_user` (
  `user_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `user_password` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='电子处方对接用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `settle_company`
--

DROP TABLE IF EXISTS `settle_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settle_company` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `settle_company_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '结算方名称',
  `initial_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '拼音',
  `price_type_id` int(10) unsigned DEFAULT NULL COMMENT '几号价',
  `discount` decimal(10,2) DEFAULT NULL COMMENT '扣率',
  `tax_rate` decimal(10,2) DEFAULT NULL COMMENT '税率',
  `process_cost` decimal(10,2) DEFAULT NULL COMMENT '加工费',
  `fee_rate` decimal(10,2) DEFAULT NULL COMMENT '加工费扣率',
  `sale_employee_id` int(10) unsigned DEFAULT NULL COMMENT '业务员ID',
  `realtime_stock` int(1) unsigned DEFAULT NULL COMMENT '是否需要实时库存',
  `packing` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '包装,多个包装以,分割',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `use_state` tinyint(1) DEFAULT '0' COMMENT '0.启用,1.停用',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_user` int(10) unsigned DEFAULT NULL COMMENT '修改人ID',
  `mod_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `settle_company_name` (`settle_company_name`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='结算方';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `trig_settle_company_update` AFTER UPDATE ON `settle_company`
    FOR EACH ROW BEGIN
	DECLARE mod_value VARCHAR(300);
	DECLARE old_employee,new_employee VARCHAR(45);
	SET mod_value='';
	IF OLD.price_type_id != NEW.price_type_id THEN
	    SET mod_value=CONCAT(mod_value,' 价格类型由【',IFNULL(OLD.price_type_id,NEW.price_type_id),'号价】改为【',NEW.price_type_id,'号价】');
	END IF;
	IF OLD.discount != NEW.discount THEN
	    SET mod_value=CONCAT(mod_value,' 扣率由【',IFNULL(OLD.discount,NEW.discount),'】改为【',NEW.discount,'】');
	END IF;
	IF OLD.tax_rate != NEW.tax_rate THEN
	    SET mod_value=CONCAT(mod_value,' 税率由【',IFNULL(OLD.tax_rate,NEW.tax_rate),'】改为【',NEW.tax_rate,'】');
	END IF;
	IF OLD.process_cost != NEW.process_cost THEN
	    SET mod_value=CONCAT(mod_value,' 加工费由【',IFNULL(OLD.process_cost,NEW.process_cost),'】改为【',NEW.process_cost,'】');
	END IF;
	IF OLD.fee_rate != NEW.fee_rate THEN
	    SET mod_value=CONCAT(mod_value,' 加工费扣率由【',IFNULL(OLD.fee_rate,NEW.fee_rate),'】改为【',NEW.fee_rate,'】');
	END IF;
	IF OLD.sale_employee_id IS NULL AND NEW.sale_employee_id IS NOT null THEN
	    SELECT employee_name INTO new_employee FROM employee WHERE employee.id=NEW.sale_employee_id;
	    SET mod_value=CONCAT(mod_value,' 业务员由【',' ','】改为【',new_employee,'】');
	END IF;
	IF OLD.sale_employee_id != NEW.sale_employee_id THEN
	    SELECT employee_name INTO new_employee FROM employee WHERE employee.id=NEW.sale_employee_id;
	    SELECT employee_name INTO old_employee FROM employee WHERE employee.id=OLD.sale_employee_id;
	    SET mod_value=CONCAT(mod_value,' 业务员由【',IFNULL(old_employee,new_employee),'】改为【',new_employee,'】');
	END IF;
	IF mod_value != '' THEN
	    INSERT INTO settle_company_history(settle_company_id,mod_user,mod_comment)
	    VALUES (NEW.id,NEW.mod_user,mod_value);
	END IF;
    END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `settle_company_history`
--

DROP TABLE IF EXISTS `settle_company_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settle_company_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `settle_company_id` int(10) unsigned NOT NULL COMMENT '结算方ID',
  `mod_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_user` int(10) unsigned DEFAULT NULL COMMENT '修改人ID',
  `mod_comment` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '修改备注',
  PRIMARY KEY (`id`),
  KEY `idx_settle_company_id` (`settle_company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=282 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='结算方历史记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `special_discount`
--

DROP TABLE IF EXISTS `special_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `special_discount` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `settle_company_id` int(10) unsigned DEFAULT NULL COMMENT '结算方ID',
  `medicine_id` int(10) unsigned DEFAULT NULL COMMENT '药品ID',
  `discount` decimal(10,3) DEFAULT NULL COMMENT '扣率',
  `tax_rate` decimal(10,3) DEFAULT NULL COMMENT '税率',
  PRIMARY KEY (`id`),
  KEY `idx_settle_company_id` (`settle_company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1156 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sunshine_medicine`
--

DROP TABLE IF EXISTS `sunshine_medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sunshine_medicine` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `medicine_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '药品编号',
  `medicine_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '药品名称',
  PRIMARY KEY (`id`),
  KEY `idx_medicine_code` (`medicine_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT=' 阳光平台药品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT 'key',
  `value` varchar(300) CHARACTER SET utf8 NOT NULL COMMENT 'value',
  `type` int(2) unsigned DEFAULT NULL COMMENT '1.需要传输到前端控制,0.只后台用到',
  `comments` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '注释',
  `mod_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `mod_user` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='系统配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_function`
--

DROP TABLE IF EXISTS `sys_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_function` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '功能名称',
  `icon` varchar(20) DEFAULT NULL COMMENT '图标',
  `code` varchar(30) DEFAULT NULL COMMENT '功能字符KEY',
  `module_id` int(10) DEFAULT NULL COMMENT '对应模块ID',
  `seq` int(11) DEFAULT NULL COMMENT '功能排序',
  `use_state` tinyint(1) DEFAULT '0' COMMENT '0:启用,1.未启用',
  PRIMARY KEY (`id`),
  KEY `idx_module_id` (`module_id`)
) ENGINE=MyISAM AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 COMMENT='菜单功能权限关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_lookup`
--

DROP TABLE IF EXISTS `sys_lookup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_lookup` (
  `code` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '字典代码, 相同代码的行组成一组数据',
  `value` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '数值',
  `label` varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '显示名称',
  `seq` int(2) DEFAULT NULL COMMENT '排序顺序',
  `ext1` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扩展字段2',
  `ext3` varchar(80) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扩展字段3',
  `desc` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '说明',
  `mod_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`code`,`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='常量字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_module`
--

DROP TABLE IF EXISTS `sys_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_module` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) DEFAULT NULL COMMENT '模块名称',
  `url` varchar(50) DEFAULT NULL COMMENT '模块url',
  `parent_id` int(11) DEFAULT NULL COMMENT '模块父级ID',
  `icon` varchar(20) DEFAULT NULL COMMENT '模块图标',
  `orderitem` tinyint(3) DEFAULT NULL COMMENT '排序',
  `is_menu` tinyint(1) DEFAULT NULL COMMENT '是否菜单',
  `use_state` tinyint(1) DEFAULT '0' COMMENT '0:启用,1.未启用',
  `config` json DEFAULT NULL COMMENT '账套级别的系统配置-Lei',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_role` int(11) DEFAULT NULL COMMENT '父级角色',
  `rolename` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `describe` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色表(职务)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role_function`
--

DROP TABLE IF EXISTS `sys_role_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_function` (
  `role_id` int(20) NOT NULL COMMENT '角色编号',
  `function_id` int(11) NOT NULL COMMENT '模块对应操作ID',
  PRIMARY KEY (`role_id`,`function_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='角色菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role_module`
--

DROP TABLE IF EXISTS `sys_role_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_module` (
  `role_id` int(10) NOT NULL COMMENT '角色编号',
  `module_id` int(11) NOT NULL COMMENT '模块ID',
  PRIMARY KEY (`role_id`,`module_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='角色操作功能权限关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_shipping_state`
--

DROP TABLE IF EXISTS `sys_shipping_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_shipping_state` (
  `state_id` int(5) unsigned DEFAULT NULL COMMENT '状态ID',
  `state_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '状态名称',
  `old_state_id` int(5) DEFAULT NULL COMMENT '老状态ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='状态字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_order_id`
--

DROP TABLE IF EXISTS `tmp_order_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_order_id` (
  `last_order_id` int(11) DEFAULT NULL,
  `last_update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transfer_medicine`
--

DROP TABLE IF EXISTS `transfer_medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transfer_medicine` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `medicine_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '药品名称',
  `unit` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '单位',
  `weight_every` decimal(16,5) DEFAULT NULL COMMENT '单贴量',
  `eat_type` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '服法',
  `bill_id` bigint(20) DEFAULT NULL COMMENT '医院处方ID',
  `medicine_code` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '药品编号',
  `bill_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '单据号',
  `batch_number` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '生产批号',
  `price` decimal(20,5) DEFAULT NULL COMMENT '含税单价',
  `mnemonic_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '货号（助记码）',
  `sys_medicine_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '对应的系统药品名',
  `special_boil_type` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '特殊煎药方式',
  `print_medicine` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '订单列表打印药品时使用',
  `weight` decimal(16,5) DEFAULT NULL COMMENT '总量',
  `money` decimal(20,5) DEFAULT NULL COMMENT '含税金额',
  `hospital_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '医院编号',
  `medicine_source` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '药品来源：自备、自提',
  `medicine_address` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '产地',
  `standard_weight` float DEFAULT NULL COMMENT '规格量',
  `standard` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '药品规格',
  `factory` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '生产厂商',
  PRIMARY KEY (`id`),
  KEY `idx_bill_id` (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46609 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='订单明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transfer_recipe`
--

DROP TABLE IF EXISTS `transfer_recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transfer_recipe` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `trade_time` timestamp NULL DEFAULT NULL COMMENT '交易时间',
  `receive_time` timestamp NULL DEFAULT NULL COMMENT '接收时间',
  `if_receive` int(2) unsigned DEFAULT '0' COMMENT '是否接收,1.已接收 999作废',
  `bill_id` bigint(20) DEFAULT NULL COMMENT '医院处方ID',
  `recipe_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '医院处方号',
  `recipe_serial` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '处方序号',
  `recipe_bh` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '处方编号',
  `carry_id` int(2) unsigned DEFAULT NULL COMMENT '配送方式',
  `hospital_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '医院编号',
  `part_hospital_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '分院号',
  `boil_type` int(2) unsigned DEFAULT NULL COMMENT '煎药方式(1.代煎药,2.自煎,3,膏方代煎,4.膏方自煎)',
  `outpatient_num` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '门诊号/住院号',
  `recipient_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '病历号',
  `recipient_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '病人姓名',
  `sex` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '病人性别',
  `age` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '病人年龄',
  `recipient_tel` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '病人电话',
  `consignee` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货人',
  `consignee_tel` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货人电话',
  `province` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '市',
  `region` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '区',
  `recipient_address` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货人地址',
  `doctor_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '医生姓名',
  `doctor_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '医生工号',
  `quantity` int(5) unsigned DEFAULT NULL COMMENT '处方贴数',
  `package_paste` int(2) DEFAULT NULL COMMENT '每贴几包',
  `total_package_paste` int(2) unsigned DEFAULT NULL COMMENT '总包数',
  `medicine_quantity` int(5) unsigned DEFAULT NULL COMMENT '草药位数',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `print_state` int(2) unsigned DEFAULT '0' COMMENT '打印状态,1.已打印,0.未打印',
  `money` decimal(20,5) DEFAULT NULL COMMENT '医院金额',
  `his_hospital` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '出方客户单位',
  `native_recipe_id` int(10) unsigned DEFAULT '0' COMMENT '本地处方ID',
  `inpatient_area` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '病区',
  `bed_number` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '床号',
  `total_weight` decimal(20,5) DEFAULT NULL COMMENT '总重量',
  `recipe_type` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '处方类型:普通处方',
  `department` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '就诊科室',
  `pathogen` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '临床病症',
  `cal_type` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '费用类别:医保/自费',
  `usage` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '服法',
  `pack_type` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '包装规格',
  `use_his_money` tinyint(1) DEFAULT '0' COMMENT '1.使用医院金额',
  `recipe_source` int(1) DEFAULT '0' COMMENT '处方来源(1.电子处方,2.excel导入)',
  `express_site` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '配送点',
  `process_cost` decimal(20,2) DEFAULT NULL COMMENT '加工费',
  `belong` int(2) unsigned DEFAULT NULL COMMENT '1表示属于门诊，2表示属于病房',
  `custom_one_text` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '自定义文本1',
  `custom_two_text` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '自定义文本2',
  `custom_one_fee` decimal(20,5) DEFAULT NULL COMMENT '自定义费用1',
  `custom_two_fee` decimal(20,5) DEFAULT NULL COMMENT '自定义费用2',
  `download_date` timestamp NULL DEFAULT NULL COMMENT '下载处方日期',
  `packing_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '包装名称(袋装,瓶装...)',
  `invoice_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '发票号',
  `take_type` int(2) unsigned DEFAULT NULL COMMENT '用法：1.内服,2.外用,3.灌肠',
  `medicine_match` tinyint(1) DEFAULT '1' COMMENT '是否完全匹配：true.是,false.否',
  PRIMARY KEY (`id`),
  KEY `idx_bill_id` (`bill_id`),
  KEY `idx_native_recipe_id` (`native_recipe_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2251 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='订单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transfer_recipe_tmp`
--

DROP TABLE IF EXISTS `transfer_recipe_tmp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transfer_recipe_tmp` (
  `transfer_recipe_id` int(10) unsigned NOT NULL COMMENT '订单主键ID',
  `carry_id` int(2) unsigned DEFAULT NULL COMMENT '配送公司',
  `take_time` datetime DEFAULT NULL COMMENT '取药时间',
  `receive_remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '接方备注',
  `receiver_id` int(10) unsigned DEFAULT NULL COMMENT '接方人ID',
  `logistics_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '物流单号',
  PRIMARY KEY (`transfer_recipe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='接方订单临时表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_pref`
--

DROP TABLE IF EXISTS `user_pref`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_pref` (
  `user_id` int(10) NOT NULL COMMENT '用户id ',
  `type` varchar(45) CHARACTER SET utf8 NOT NULL COMMENT '配置类型',
  `value` json NOT NULL COMMENT '配置详细',
  `mod_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户喜好配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `warehouse` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `warehouse_name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '仓库/药房名称',
  `warehouse_type` int(2) unsigned DEFAULT NULL COMMENT '数据类型：1.仓库,3.饮片药房,4.小包装药房',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `use_state` tinyint(1) DEFAULT '0' COMMENT '0.在用,1.停用',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `mod_user` int(10) unsigned DEFAULT NULL COMMENT '修改人ID',
  `mod_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='仓库/药房';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `warehouse_standard`
--

DROP TABLE IF EXISTS `warehouse_standard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `warehouse_standard` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `medicine_id` int(10) unsigned DEFAULT NULL COMMENT '药品ID',
  `warehouse_id` int(10) unsigned DEFAULT NULL COMMENT '仓库id/药房id',
  `standard_id` int(10) unsigned DEFAULT NULL COMMENT '药品规格id',
  `stock_value` decimal(20,5) DEFAULT '0.00000' COMMENT '库存量',
  `storage_location` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '库位',
  `up_limit` decimal(20,5) DEFAULT NULL COMMENT '上限',
  `lower_limit` decimal(20,5) DEFAULT NULL COMMENT '下限',
  `batch_number` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '批号',
  `producer_id` int(10) DEFAULT NULL COMMENT '生产商/产地',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1040 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='仓库药房规格';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'tcmts2'
--

--
-- Dumping routines for database 'tcmts2'
--
/*!50003 DROP PROCEDURE IF EXISTS `generate_order_id` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `generate_order_id`(OUT new_id INT)
BEGIN
	DECLARE last_id INT DEFAULT 0;
    DECLARE last_time TIMESTAMP;
    DECLARE updated_count INT DEFAULT 0;
    DECLARE max_try INT DEFAULT 5;
    DECLARE try_times INT DEFAULT 0;
    SET new_id = 0;

	SELECT last_order_id ,last_update_at INTO  last_id, last_time FROM tmp_order_id;

    IF last_id <> 0 THEN
		IF TO_DAYS(last_time) = TO_DAYS(CURRENT_DATE()) THEN
			SET new_id = last_id;
			WHILE updated_count = 0 AND try_times < max_try DO
				SET new_id = new_id + 1;
				UPDATE tmp_order_id SET last_order_id = new_id,last_update_at=NOW()  WHERE last_order_id = last_id ;
				SET updated_count = ROW_COUNT();
				SET try_times=try_times+1;
			END WHILE;
			IF updated_count = 0 THEN
				SET new_id = 0;
			END IF;
		ELSE
			SET new_id = 1;
			UPDATE tmp_order_id SET last_order_id = new_id,last_update_at=NOW();
			SET updated_count = ROW_COUNT();
			IF updated_count = 0 THEN
				SET new_id = 0;
			END IF;
	        END IF;
	ELSE
		INSERT INTO tmp_order_id (last_order_id) VALUES (1);
		SET new_id = 1;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-19 18:34:33

--2018-1-22 WZY
DROP TRIGGER `ins_medicine`;

CREATE DEFINER=`root`@`%` TRIGGER `ins_medicine` AFTER INSERT ON `medicine`
FOR EACH ROW BEGIN
	insert into `medicine_relation`(hospital_medicine_name,initial_code,native_medicine_id,hospital_id)
	values(NEW.medicine_name,NEW.initial_code,NEW.id,0);
	insert into `price_templet_detail`(medicine_id, `templet_id`, price_start, mod_user)
    SELECT NEW.id, pt.id , NOW(), NEW.mod_user FROM price_templet pt;
END;