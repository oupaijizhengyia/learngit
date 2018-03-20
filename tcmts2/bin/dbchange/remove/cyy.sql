insert into `print_field` (`id`, `field_name`, `field_type`, `field_source`, `field_para`, `display_text`, `display_width`, `display_height`, `is_hidden`) values
('1','自定义文本','custom',NULL,'custom:自定义文本','自定义文本','200','20','0'),
('2','开单时间','date','createTime','date:0','2010.08.28 09:30:45','110','20','0'),
('3','开单时间-年','date_p','createTime','date_p:y','2010','40','20','0'),
('4','开单时间-月','date_p','createTime','date_p:m','08','20','20','0'),
('5','开单时间-日','date_p','createTime','date_p:d','28','20','20','0'),
('6','年月日','text','ymd','text_r:|replace:','年月日','100','20','0'),
('7','月日','text','md','text_r:|replace:','月日','100','20','0'),

('49','页码','text','pageIndexString','text_r:|replace:','1/2','100','22','0'),
('50','第几页','text','pageIndex','text_r:|replace:','第几页','100','22','0'),
('51','共几页','text','pageCount','text_r:|replace:','共几页','100','22','0'),
('52','本页合计','text','singlePageCount','text_r:|replace:','本页合计','100','22','0'),
('53','本页合计-大写','text','singlePageCountBig','text_r:|replace:','本页合计-大写','100','22','0'),
('54','金额总合计','text','totalMoney','text_r:|replace:','8888','100','22','0'),

('80','医院全称','text','hospitalCompany','text_r:|replace:','浙江省中西医院','100','20','0'),
('81','医院简称','text','hospitalName','text_r:|replace:','中西医院','100','20','0'),
('82','医院处方号','text','recipeCode','text_r:|replace:','1','100','20','0'),
('83','系统处方号','text','sysRecipeCode','text_r:|replace:','1','100','20','0'),
('84','处方序号','text','recipeSerial','text_r:|replace:','1','100','20','0'),
('85','处方编号','text','recipeBh','text_r:|replace:','123','100','20','0'),
('86','病人姓名','text','recipientName','text_r:|replace:','姓名','100','20','0'),
('87','性别','text','sex','text_r:|replace:','性别','100','20','0'),
('88','年龄','text','age','text_r:|replace:','年龄','100','20','0'),
('89','病人电话','text','recipientTel','text_r:|replace:','电话','100','20','0'),
('90','处方医师','text','doctorName','text_r:|replace:','处方医师','100','20','0'),
('91','病区','text','inpatientArea','text_r:|replace:','病区','100','20','0'),
('92','床号','text','bedNumber','text_r:|replace:','床号','100','20','0'),
('93','门诊号','text','outpatientNum','text_r:|replace:','门诊号','100','20','0'),
('94','就诊科室','text','department','text_r:|replace:','就诊科室','100','20','0'),
('95','服法','text','usage','text_r:|replace:','内服,一日两次，每次一袋','100','20','0'),
('96','收件人','text','consignee','text_r:|replace:','张三','100','20','0'),
('97','收件人电话','text','consigneeTel','text_r:|replace:','18888888888','100','20','0'),
('98','收件地址','text','recipientAddress','text_r:|replace:','浙江省杭州市西湖区古墩路187号','100','20','0'),
('99','临床辩证','text','pathogen','text_r:|replace:','临床辩证','100','20','0'),
('100','处方贴数','text','quantity','text_r:|replace:','处方贴数','100','20','0'),
('101','单贴数量','text','weightEvery','text_r:|replace:','单贴数量','100','20','0'),
('102','加工总量','text','totalWeight','text_r:|replace:','500','100','20','0'),
('103','单贴金额','text','moneyEvery','text_r:|replace:','单贴金额','100','20','0'),
('104','医院处方金额','text','money','text_r:|replace:','300.00','100','20','0'),
('105','配送方式','text','transferType','text_r:|replace:','顺丰','100','20','0'),
('106','草药味数','text','medicineQuantity','text_r:|replace:','草药味数','100','20','0'),
('107','煎药方式','text','boilTypeName','text_r:|replace:','煎药方式','100','20','0'),
('108','特殊煎药方式','text','specialBoilType','text_r:|replace:','特殊煎药方式','100','20','0'),
('109','条码(系统处方号)','barcode','sysRecipeCode','text_r:|replace:','123456789','100','20','0'),
('110','条码(医院处方号)','barcode','recipeCode','text_r:|replace:','123456789','200','20','0'),
('112','每贴几包','text','packagePaste','text_r:|replace:','2','100','20','0'),
('113','总包数','text','totalPackagePaste','text_r:|replace:','15','100','20','0'),
('114','共包袋','text','totalPackagePasteStr','text_r:|replace:','共:14袋','100','20','0'),
('115','另包','text','otherPackage','text_r:|replace:','另包','100','20','0'),
('116','另包+备注','text','otherPackageRemark','text_r:|replace:','另包+备注','100','20','0'),
('117','备注','text','remark','text_r:|replace:','备注','100','20','0'),
('118','包装规格+服用方式+备注','text','packageUsageRemark','text_r:|replace:','包装规格+服用方式+备注','100','20','0'),
('119','物流单号','text','logisticsCode','text_r:|replace:','628388349221','100','20','0'),
('120','原寄地区号','text','originCode','text_r:|replace:','874','100','20','0'),
('121','目的码','text','destCode','text_r:|replace:','234','100','20','0'),
('122','寄件方+电话','hospitalName+companyTel',NULL,'custom:自定义文本','某某医院 18888888888','200','20','0'),
('123','寄件方地址','companyAddress',NULL,'custom:自定义文本','浙江省杭州市西湖区古墩路187号','200','20','0'),
('124','寄件方+电话+地址','hospitalName+companyTel+companyAddress',NULL,'custom:自定义文本','某某医院 18888888888 浙江省杭州市西湖区古墩路187号','200','20','0'),
('125','收件人+电话+地址','consignee+consigneeTel+totalAddress',NULL,'custom:自定义文本','张三 18888888888 浙江省杭州市西湖区古墩路187号','200','20','0'),
('126','序号','serialNumber',NULL,'text_r:|replace:','1','200','20','0'),

('400','药品-编号','list|text','recipeMedicineList','list:1|field:medicineCode','1001','100','20','0'),
('401','药品-商品名称','list|text','recipeMedicineList','list:1|field:medicineName','三七','100','20','0'),
('402','药品-单位','list|text','recipeMedicineList','list:1|field:unit','g','100','20','0'),
('403','药品-单贴量','list|text','recipeMedicineList','list:1|field:weightEvery','6','100','20','0'),
('404','药品-总贴量','list|text','recipeMedicineList','list:1|field:weight','150','100','20','0'),
('405','药品-特殊煎药方式','list|text','recipeMedicineList','list:1|field:specialBoilType','先煎','100','20','0'),
('406','药品-货号','list|text','recipeMedicineList','list:1|field:mnemonicCode','A1015','100','20','0'),
('407','药品-单价','list|text','recipeMedicineList','list:1|field:actualPrcie','800','80','20','0'),
('408','药品-批发价','list|text','recipeMedicineList','list:1|field:tradPrice','800','100','20','0'),
('409','药品-零售价','list|text','recipeMedicineList','list:1|field:retailPrice','1000','100','20','0'),
('410','药品-金额','list|text','recipeMedicineList','list:1|field:tradeFreight','300','80','20','0'),

('460','药房标签打印-药品规格','text','medicineName','text_r:|replace:','三七9g','200','20','0'),
('461','药房标签打印-药品规格编号(条码)','barcode','standardCode','text_r:|replace:','药品编号(条码)','200','20','0'),
('462','药房标签打印-药品规格编号','text','standardCode','text_r:|replace:','10000001','200','20','0'),

('490','员工姓名','text','employeeName','text_r:|replace:','张三','200','20','0'),
('492','员工工号','text','employeeCode','text_r:|replace:','007','200','20','0'),
('491','条码(员工工号)','barcode','employeeCode','text_r:|replace:','药品编号(条码)','200','20','0'),

('501','煎药机编号','text','machineCode','text_r:|replace:','jy001','200','20','0'),
('502','条码(煎药机编号)','barcode','machineCode','text_r:|replace:','条码(煎药机编号)','200','20','0');


insert into sys_config(name,value,type,comments) values('SOAK_TIME','40',1,'默认浸泡时间');
insert into sys_config(name,value,type,comments) values('BOIL_TIME','40',1,'默认煎煮时间');
insert into sys_config(name,value,type,comments) values('PACKAGE_PASTE','2',1,'默认每贴包数');
insert into sys_config(name,value,type,comments) values('CHECK_TABOO','N',0,'配伍禁忌');
insert into sys_config(name,value,type,comments) values('RELATION_MEDICINE','Y',0,'接收订单关联药品');
insert into sys_config(name,value,type,comments) values('SF_EXPRESS_TYPE','3',0,'默认快件类型');


/*20171211*/
insert  into `print_field`(`field_id`,`field_name`,`field_type`,`field_source`,`field_para`,`display_text`,`display_width`,`display_height`,`is_hidden`) values 
(460,'宅急便单号条码','barcode','sfCode','text_r:|replace:','宅急便单号条码',200,20,0);
insert  into `print_field`(`field_id`,`field_name`,`field_type`,`field_source`,`field_para`,`display_text`,`display_width`,`display_height`,`is_hidden`) values 
(461,'宅急便单号','text','sfCode','text_r:|replace:','宅急便单号',200,20,0);
insert  into `print_field`(`field_id`,`field_name`,`field_type`,`field_source`,`field_para`,`display_text`,`display_width`,`display_height`,`is_hidden`) values 
(462,'寄件人','text','senderName','text_r:|replace:','寄件人',200,20,0);
insert  into `print_field`(`field_id`,`field_name`,`field_type`,`field_source`,`field_para`,`display_text`,`display_width`,`display_height`,`is_hidden`) values 
(463,'寄件电话','text','senderTel','text_r:|replace:','寄件电话',200,20,0);
insert  into `print_field`(`field_id`,`field_name`,`field_type`,`field_source`,`field_para`,`display_text`,`display_width`,`display_height`,`is_hidden`) values 
(464,'寄件地址','text','senderAddress','text_r:|replace:','寄件地址',200,20,0);

/*20180119*/
insert into sys_shipping_state values(46,'已开始二煎',46);
alter table `recipe_proccess` add column `second_boil_id` int(10) UNSIGNED NULL COMMENT '二煎人id';
alter table `recipe_proccess` add column `start_second_boil_time` timestamp NULL COMMENT '二煎时间';
insert into sys_function(name,icon,code,module_id,seq,use_state) values('药品上架', null,'MEDICINE_SHELVES',23,21,0);
insert into sys_function(name,icon,code,module_id,seq,use_state) values('取药', null,'MEDICINE_TAKE',23,22,0);