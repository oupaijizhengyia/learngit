INSERT INTO `sys_lookup` (`code`,`value`,label,seq)
VALUES("specialBoilType","包煎","包煎",4),
("specialBoilType","冲服","冲服",5),
("specialBoilType","烊化","烊化",6),
("specialBoilType","另煎","另煎",7),
("specialBoilType","泡服","泡服",8);

update print_field set field_source='takeTimeDate' where id=147;

--以上已执行

DELIMITER $$

DROP TRIGGER /*!50032 IF EXISTS */ `trig_recipe_insert`$$

create trigger `trig_recipe_insert` AFTER INSERT on `recipe` 
for each row BEGIN
	DECLARE nickname varchar(100);
	select hospital_nickname into nickname from hospital where id=NEW.hospital_id;
	insert into `recipe_track`(`hospital_nickname`,`recipient_name`,`quantity`,`shipping_state`,`accept_time`,`last_time`,`recipe_id`) values 
	(nickname,NEW.recipient_name,NEW.quantity,1,now(),now(),NEW.id);
	INSERT INTO recipe_proccess(recipe_id) values(NEW.id);
    END;
$$

DELIMITER ;