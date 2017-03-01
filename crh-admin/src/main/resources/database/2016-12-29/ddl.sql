
--修改吾儿乐园购买信息的必填字段 @zhongyidong
delete from s_basic_para where type_id = 3;
insert into s_basic_para (para_id,type_id, para_name, para_value1) values(15, 3, '姓名', '1');
insert into s_basic_para (para_id,type_id, para_name, para_value1) values(16, 3, '手机号', '2');
insert into s_basic_para (para_id,type_id, para_name, para_value1) values(17, 3, '身份证号', '3');