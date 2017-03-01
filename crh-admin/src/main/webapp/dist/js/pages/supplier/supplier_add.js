$(function(){
	var supplierAdd = {
			initEvents:function(){
				var _self = this;		
				// 将服务商编号传入
				$.ajax({
					type: "POST",
					url: urlPrefix + "supplier/getOrgCode",
					dataType: "json",
					success: function(message){
						if(message.code=="ACK"){
							$("#orgCode").html(message.data);							
						}
					},
					error: function(message){
					}		
				});
				
				$("#save").on('click',_(this.submitForm).bind(this));
				
				//验证
				this.validator = $('#addForm').validate({
		        	rules : {
		        		orgName : {
		        			required : true,
		        			maxlength:50
		        		},
		        		businesser : {
		        			required : true,
		        			maxlength:10,
		        			isBusinesser : true
		        		},
		        		registrationLicense : {
		        			required : true,
		        			maxlength:18
		        		},
		        		contact : {
		        			required : true,
		        			maxlength:10,
		        			isContact : true
		        		},
		        		phone : {
		        			required : true,
		        			digits:true,
		        			minlength:11,
		        			maxlength:11
		        		},
		        		servicePhone : {
		        			required : true,
		        			isServicePhone : true
		        		},
		        		introduce : {
		        			maxlength : 200
		        		}
		        	}, 
		        	messages : {
		        		orgName : {
		        			required : "服务商名称不能为空",
		        			maxlength : "服务商名称不能超过50个字"
		        		},
		        		businesser : {
		        			required : "企业法人姓名不能为空",
		        			maxlength : "企业法人姓名不能超过10个汉字"
		        		},
		        		registrationLicense : {
		        			required : "工商执照注册号不能为空",
			        		maxlength : "工商执照注册号不能超过18位",
		        		},
		        		contact : {
		        			required : "联系人姓名不能为空",
		        			maxlength : "联系人姓名不能超过10个汉字"
		        		},
		        		phone : {
		        			required : "联系人手机号不能为空",
		        			minlength : "请输入11位有效手机号",
		        			maxlength : "请输入11位有效手机号"
		        		},
		        		servicePhone : {
		        			required : "客服电话不能为空"
		        		},
		        		introduce : {
		        			maxlength : "简介不能超过200个字"
		        		}
		        	}
		        });
				
				jQuery.validator.addMethod("isBusinesser", function(value, element) {
					var name = /^[\u2E80-\u9FFF]+$/;
					return this.optional(element) || (name.test(value));
				}, "企业法人姓名只允许为汉字");
				
				jQuery.validator.addMethod("isContact", function(value, element) {
					var name = /^[\u2E80-\u9FFF]+$/;
					return this.optional(element) || (name.test(value));
				}, "联系人姓名只允许为汉字");
				
				jQuery.validator.addMethod("isServicePhone", function(value, element) {
					var name = /^\d{3,4}-?\d{7,9}$/;
					return this.optional(element) || (name.test(value));
				}, "请输入正确的客服号码");
			},
			
			
			submitForm : function(){
				var formFlag = this.validator.form();
				if(!formFlag) {
					return false;
				}else {
					this.doSave();
				}
			},
			
			doSave : function(){
				var orgCode = $("#orgCode").html();
				var orgName = $.trim($("#orgName").val());
				var businesser = $.trim($("#businesser").val());
				var registrationLicense = $.trim($("#registrationLicense").val());
				var contact = $.trim($("#contact").val());
				var phone = $.trim($("#phone").val());
				var servicePhone = $.trim($("#servicePhone").val());
				var introduce = $.trim($("#introduce").val());
				
				var areaAddForm = {
						orgCode 				: orgCode,
						orgName     			: orgName,
						businesser  			: businesser,
						registrationLicense     : registrationLicense,
						contact       			: contact,	
						phone      				: phone,
						servicePhone			: servicePhone,
						introduce				: introduce
				};
				$.ajax({
					type: "POST",
					url: "add",
					contentType: "application/json;charset=utf-8",
					data: JSON.stringify(areaAddForm),
					dataType: "json",
					success: function(message){
						if(message.code=="ACK"){
							window.location.href= urlPrefix + "supplier/";
						}
					},
					error:function(message){
						//alert('shibai');
					}
				});
			},
			
			init:function(){
				var _self = this;
				_self.initEvents();
			}
	
	}.init();	
});