ALTER TABLE `price_templet_detail`
CHANGE COLUMN `template_id` `templet_id`  int(11) NOT NULL COMMENT '模板id' AFTER `id`;

/**
 * 2018.1.12
 */
DROP TRIGGER `update_medicine`;
ALTER TABLE `recipe_proccess` ADD end_subside_id INT(10) DEFAULT NULL COMMENT'结束沉淀人id';
/**
 * 院内  已执行
 */
INSERT INTO `sys_config` (`name`,`value`,`type`,`comments`)
    VALUES('HOSPITAL_TYPE','0',1,'是否是院内：0.是 1.否');
    
INSERT INTO `sys_lookup` (`code`,`value`,`label`,seq)
VALUES('transferType','3','自取',1),('transferType','2','邮政',2);