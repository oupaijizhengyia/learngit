--2018-1-15 wzy
USE `wxzyy`$$

ALTER TABLE `medicine_max_dose`
MODIFY COLUMN `mod_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间' AFTER `mod_user`;


INSERT INTO `sys_lookup` (`code`,`value`,`label`,seq)
VALUES('lockState','1','已锁定',1);

UPDATE `sys_module` SET config = '{ "ribbon": [{"type": "dateTime","label": "开始时间","code": "startDate"},{"type": "dateTime","label": "结束时间","code": "endDate"},{"type": "dropdown", "label": "门诊/住院","code": "belong"},{"type": "dropdown", "label": "煎药方式","code": "boilType"},{"type": "dropdown", "label": "流程状态","code": "shippingState"},{"type": "dropdown", "label": "打印状态","code": "printState"},{"type": "dropdown", "label": "用法","code": "takeType"},{"type": "dropdown", "label": "锁定状态","code": "lockState"},{"label": "系统处方号","code": "sysRecipeCode"},{"label": "发票号","code": "invoiceCode"},{"label": "门诊号","code": "outpatientNum"},{"label": "患者姓名","code": "recipientName"},{"type":"dropdown","label": "审方员","code": "dispenseId"},{"label": "医生","code": "doctorName"},{"label": "病区","code": "inpatientArea"},{"label": "床号","code": "bedNumber"},{"type":"dropdown","label": "配送方式","code": "carryId"},{"label": "物流单号","code": "logisticsCode"}],"button": [{"label": "查询","action":"search","method":"onSearch"},{"label": "重置","action":"reset","method":"onReset"},{"label": "新增","action":"addNew","method":"onAddNew"},{"label":"审方", "action": "insideCheck", "method":"onInsideCheck"}, {"label": "合并","action":"merge","method":"onMerge"},{"label": "锁定","action":"changeLock","method":"onChangeLock"},{"label": "作废","action":"delete","method":"onDelete"}] }'
WHERE `name`='处方列表';

DELETE FROM `sys_shipping_state` WHERE state_name IN ("已小包装复核","已领料","已签收");
 
INSERT INTO `sys_function` (`name`,`code`,module_id,seq,use_state)
VALUE("锁定",'LOCK_RECIPE',19,12,0);

DELIMITER $$

USE `wxzyy`$$

DROP TRIGGER /*!50032 IF EXISTS */ `update_recipe`$$

CREATE
    /*!50017 DEFINER = 'root'@'%' */
    TRIGGER `update_recipe` AFTER UPDATE ON `recipe` 
    FOR EACH ROW BEGIN
    DECLARE old_comment VARCHAR(50) DEFAULT '';
    DECLARE new_comment VARCHAR(50) DEFAULT '';
    DECLARE old_comment1 VARCHAR(50) DEFAULT '';
    DECLARE new_comment1 VARCHAR(50) DEFAULT '';
    DECLARE old_volume DECIMAL(16,0) DEFAULT 0;
    DECLARE new_volume DECIMAL(16,0) DEFAULT 0;
    
    IF IFNULL(NEW.boil_type,0) <> IFNULL(OLD.boil_type,0) THEN
	
	IF IFNULL(OLD.boil_type,0) = 0 THEN
		SET old_comment = '';
	END IF;
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
	IF  IFNULL(NEW.carry_id,0) = 0 THEN
		SET new_comment = '';
	END IF;
	
	IF  IFNULL(NEW.carry_id,0) = 2 THEN
		SET new_comment = '邮政';
	END IF;
	IF  IFNULL(NEW.carry_id,0) = 3 THEN
		SET new_comment = '自取';
	END IF;
	IF  IFNULL(OLD.carry_id,0) = 0 THEN
		SET old_comment = '';
	END IF;
	
	IF  IFNULL(OLD.carry_id,0) = 2 THEN
		SET old_comment = '邮政';
	END IF;
	IF  IFNULL(OLD.carry_id,0) = 3 THEN
		SET old_comment = '自取';
	END IF;
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改配送公司：',old_comment,'->',new_comment));
    END IF;
    IF IFNULL(NEW.pack_type,'') <> IFNULL(OLD.pack_type,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改包装规格：',IFNULL(OLD.pack_type,''),'->',IFNULL(NEW.pack_type,'')));
    END IF;
 
    IF IFNULL(NEW.medicine_quantity,0) <> IFNULL(OLD.medicine_quantity,0) THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改草药位数：',IFNULL(OLD.medicine_quantity,0),'->',IFNULL(NEW.medicine_quantity,0)));
    END IF;
    IF IFNULL(NEW.total_package_paste,0) <> IFNULL(OLD.total_package_paste,0) THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改总包数：',IFNULL(OLD.total_package_paste,0),'->',IFNULL(NEW.total_package_paste,0)));
    END IF;
    IF IFNULL(NEW.take_type,0) <> IFNULL(OLD.take_type,0) THEN
	IF  IFNULL(NEW.take_type,0) = 0 THEN
		SET new_comment = '';
	END IF;
	IF  IFNULL(NEW.take_type,0) = 1 THEN
		SET new_comment = '内服';
	END IF;
	IF  IFNULL(NEW.take_type,0) = 2 THEN
		SET new_comment = '外用';
	END IF;
	IF  IFNULL(NEW.take_type,0) = 3 THEN
		SET new_comment = '灌肠';
	END IF;
	IF  IFNULL(OLD.take_type,0) = 0 THEN
		SET old_comment = '';
	END IF;
	IF  IFNULL(OLD.take_type,0) = 1 THEN
		SET old_comment = '内服';
	END IF;
	IF  IFNULL(OLD.take_type,0) = 2 THEN
		SET old_comment = '外用';
	END IF;
	IF  IFNULL(OLD.take_type,0) = 3 THEN
		SET old_comment = '灌肠';
	END IF;
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改用法：',old_comment,'->',new_comment));
    END IF;
    IF IFNULL(NEW.second_boil_time,0) <> IFNULL(OLD.second_boil_time,0) THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改二煎时间：',IFNULL(OLD.second_boil_time,0),'->',IFNULL(NEW.second_boil_time,0)));
    END IF;
    IF IFNULL(NEW.receive_remark,'') <> IFNULL(OLD.receive_remark,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改接方备注：',IFNULL(OLD.receive_remark,''),'->',IFNULL(NEW.receive_remark,"")));
    END IF;
    IF IFNULL(NEW.logistics_code,'') <> IFNULL(OLD.logistics_code,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改物流单号：',IFNULL(OLD.logistics_code,''),'->',IFNULL(NEW.logistics_code,"")));
    END IF;
    IF IFNULL(NEW.take_time,'') <> IFNULL(OLD.take_time,'') THEN
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改取药时间：',IFNULL(OLD.take_time,''),'->',IFNULL(NEW.take_time,"")));
    END IF;
  
    IF IFNULL(NEW.boil_project_id,0) <> IFNULL(OLD.boil_project_id,0) THEN
	SELECT project_name,volume INTO old_comment1,old_volume FROM `boil_project` WHERE id = OLD.boil_project_id;
	SELECT project_name,volume INTO new_comment1,new_volume FROM `boil_project` WHERE id = NEW.boil_project_id;
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改煎煮方案：',old_comment1,'->',new_comment1));
	IF IFNULL(old_volume,0) <> IFNULL(new_volume,0) THEN
		INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
		VALUES(NEW.id,NEW.mod_user,CONCAT('修改药液量：',old_volume,'->',new_volume));
	END IF; 
    END IF;
END;
$$

DELIMITER ;

update print_field set field_source='takeTimeDate' where id=147;
update sys_module set use_state=1 where id=16;
update sys_module set orderitem=1 where id=26;
update sys_module set orderitem=2 where id=19;
update sys_module set orderitem=3 where id=17;
update sys_module set orderitem=4 where id=18;
update sys_module set orderitem=5 where id=27;
update sys_module set use_state=1 where id in(3,5,7,11,12,13,25);
update sys_function set use_state=1 where id in(34,35,36,40);