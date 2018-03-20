
DELIMITER $$
DROP TRIGGER /*!50032 IF EXISTS */ `update_recipe`$$
CREATE
    /*!50017 DEFINER = 'root'@'%' */
    TRIGGER `update_recipe` AFTER UPDATE ON `recipe`
    FOR EACH ROW BEGIN
    DECLARE old_comment VARCHAR(50);
    DECLARE new_comment VARCHAR(50);
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
	SELECT project_name,volume INTO old_comment,old_volume FROM `boil_project` WHERE id = OLD.boil_project_id;
	SELECT project_name,volume INTO new_comment,new_volume FROM `boil_project` WHERE id = NEW.boil_project_id;
	INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
	VALUES(NEW.id,NEW.mod_user,CONCAT('修改煎煮方案：',old_comment,'->',new_comment));
	IF IFNULL(old_volume,0) <> IFNULL(new_volume,0) THEN
		INSERT INTO `recipe_history`(recipe_id,operate_user,operate_comment)
		VALUES(NEW.id,NEW.mod_user,CONCAT('修改药液量：',old_volume,'->',new_volume));
	END IF;
    END IF;
END;
$$

DELIMITER ;
