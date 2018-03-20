/**
 * 院内  已执行
 */
INSERT INTO `sys_config` (`name`,`value`,`type`,`comments`)
    VALUES('HOSPITAL_TYPE','0',1,'是否是院内：0.是 1.否');

INSERT INTO `sys_lookup` (`code`,`value`,`label`,seq)
VALUES('transferType','3','自取',1),('transferType','2','邮政',2);

INSERT INTO `sys_lookup` (`code`,`value`,`label`,seq)
VALUES('lockState','1','已锁定',1);

UPDATE `sys_module` SET config = '{ "ribbon": [{"type": "dateTime","label": "开始时间","code": "startDate"},{"type": "dateTime","label": "结束时间","code": "endDate"},{"type": "dropdown", "label": "门诊/住院","code": "belong"},{"type": "dropdown", "label": "煎药方式","code": "boilType"},{"type": "dropdown", "label": "流程状态","code": "shippingState"},{"type": "dropdown", "label": "打印状态","code": "printState"},{"type": "dropdown", "label": "用法","code": "takeType"},{"type": "dropdown", "label": "锁定状态","code": "lockState"},{"label": "系统处方号","code": "sysRecipeCode"},{"label": "发票号","code": "invoiceCode"},{"label": "门诊号","code": "outpatientNum"},{"label": "患者姓名","code": "recipientName"},{"type":"dropdown","label": "审方员","code": "dispenseId"},{"label": "医生","code": "doctorName"},{"label": "病区","code": "inpatientArea"},{"label": "床号","code": "bedNumber"},{"type":"dropdown","label": "配送方式","code": "carryId"},{"label": "物流单号","code": "logisticsCode"}],"button": [{"label": "查询","action":"search","method":"onSearch"},{"label": "重置","action":"reset","method":"onReset"},{"label": "新增","action":"addNew","method":"onAddNew"},{"label":"审方", "action": "insideCheck", "method":"onInsideCheck"}, {"label": "合并","action":"merge","method":"onMerge"},{"label": "锁定","action":"changeLock","method":"onChangeLock"},{"label": "作废","action":"delete","method":"onDelete"}] }'
WHERE `name`='处方列表';


insert into sys_lookup(code,value,label,seq)
values('printType','recipeOralTag','内服标签','12'),('printType','recipeExternalTag','外用标签','13'),('printType','recipeEnemaTag','灌肠标签','14');

insert into `print_field` (`id`, `field_name`, `field_type`, `field_source`, `field_para`, `display_text`, `display_width`, `display_height`, `is_hidden`) values
('510','货架号','text',"shelvesCode",'text_r:|replace:','051-1','200','20','0'),
('511','货架条码','barcode',"shelvesCode",'text_r:|replace:','货架条码','200','20','0');

insert into sys_module(id,name,url,parent_id,orderitem,is_menu) values(28,'货架管理','/base/shelfManage',1,14,1),(29,'货架药品管理','/base/medShelfManage',1,15,1);
insert into sys_module(id,name,url,parent_id,orderitem,is_menu) values(30,'业务统计',NULL,NULL,4,1);
insert into sys_module(id,name,url,parent_id,orderitem,is_menu) 
values(31,'煎煮方案管理','/base/decoctionProgramme',1,16,1),(32,'员工业绩','/base/reportForms',30,1,1);

insert into sys_shipping_state values(46,'已开始二煎',46);


update sys_module set use_state=1 where id=16;
update sys_module set orderitem=1 where id=26;
update sys_module set orderitem=2 where id=19;
update sys_module set orderitem=3 where id=17;
update sys_module set orderitem=4 where id=18;
update sys_module set orderitem=5 where id=27;
update sys_module set use_state=1 where id in(3,5,7,11,12,13,25);
update sys_function set use_state=1 where id in(34,35,36,40,31,32);
