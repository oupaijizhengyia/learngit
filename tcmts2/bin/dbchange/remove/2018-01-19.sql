insert into sys_lookup(code,value,label,seq) 
values('printType','recipeOralTag','内服标签','12'),('printType','recipeExternalTag','外用标签','13'),('printType','recipeEnemaTag','灌肠标签','14');

insert into `print_field` (`id`, `field_name`, `field_type`, `field_source`, `field_para`, `display_text`, `display_width`, `display_height`, `is_hidden`) values
('510','货架号','text',"shelvesCode",'text_r:|replace:','051-1','200','20','0'),
('511','货架条码','barcode',"shelvesCode",'text_r:|replace:','货架条码','200','20','0');

insert into sys_module(id,name,url,parent_id,orderitem,is_menu) values(28,'货架管理','/base/shelfManage',1,14,1),(29,'货架药品管理','/base/medShelfManage',1,15,1);
insert into sys_module(id,name,url,parent_id,orderitem,is_menu) values(30,'业务统计',NULL,NULL,4,1);
insert into sys_module(id,name,url,parent_id,orderitem,is_menu) 
values(31,'煎煮方案管理','/base/decoctionProgramme',1,16,1),(32,'员工业绩','/base/reportForms',30,1,1);


alter table recipe add column `boil_project_id` int(10) unsigned DEFAULT NULL COMMENT '煎煮方案ID';

insert into `print_field` (`id`, `field_name`, `field_type`, `field_source`, `field_para`, `display_text`, `display_width`, `display_height`, `is_hidden`) values
('150','煎煮方案名称','text',"boilProjectName",'text_r:|replace:','051-1','200','20','0');

alter table sys_function add column `use_state` tinyint(1) DEFAULT '0' COMMENT '0:启用,1.未启用';
