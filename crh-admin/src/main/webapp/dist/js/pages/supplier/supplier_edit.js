$(function(){
	var supplierEdit = {
			initEvents:function(){
				var _self = this;	
				var status = $("#status_input").val();
				$("#status option[value='"+status+"']").attr("selected",true);
				
				var provinceId = $("#provinceId_input").val();
				var cityId = $("#cityId_input").val();
				var areaId = $("#areaId_input").val();
				
				
				//省份
				$.ajax({
					type: "GET",
					url: urlPrefix + "region/findAllProvince",
					contentType: "application/json;charset=utf-8",
					dataType: "json",
					success: function(message){
						if(message.code=="ACK"){
							var option = "<option value=''>请选择省份</option>";
							_.each(message.data,function(value,index){
								option += "<option value='"+value.id+"'>"+value.name+"</option>";
							});
							$("#provinceId").html(option);	
							$("#provinceId option[value='"+provinceId+"']").attr("selected",true);

						}
					},
					error: function(message){
					}		
				});
				

				//城市
				$.ajax({
					type: "GET",
					url: urlPrefix + "region/findCityByProv/"+provinceId,
					contentType: "application/json;charset=utf-8",
					dataType: "json",
					success: function(message){
						$("#cityId").html("");
						if(message.code=="ACK"){
							var option = "<option value=''>请选择城市</option>";
							_.each(message.data,function(value,index){
								option += "<option value='"+value.id+"'>"+value.name+"</option>";
							});
							$("#cityId").html(option);	
							$("#cityId option[value='"+cityId+"']").attr("selected",true);
						}
					},
					error: function(message){
					}		
				});
                
				if(areaId!="") {
					//区
					$.ajax({
						type: "GET",
						url: urlPrefix + "region/findProvOpenCity/"+cityId,
						contentType: "application/json;charset=utf-8",
						dataType: "json",
						success: function(message){
							$("#areaId").html("<option value=''>请选择城区</option>");
							if(message.code=="ACK"){
								var option = "";
								_.each(message.data,function(value,index){
									option += "<option value='"+value.id+"'>"+value.name+"</option>";
								});
								$("#areaId").html(option);	
								$("#areaId option[value='"+areaId+"']").attr("selected",true);

							}
						},
						error: function(message){
						}		
					});
				}
				
				

				
				
				
				$("#provinceId").on('change',function(){
					var provinceId= $("#provinceId").val();
					//城市
					if(provinceId!="") {
						$.ajax({
							type: "GET",
							url: urlPrefix + "region/findCityByProv/"+provinceId,
							contentType: "application/json;charset=utf-8",
							dataType: "json",
							success: function(message){
								$("#cityId").html("");
								$("#areaId").html("<option value=''>请选择城市</option>");
								if(message.code=="ACK"){
									var option = "<option value=''>请选择城区</option>";
									_.each(message.data,function(value,index){
										option += "<option value='"+value.id+"'>"+value.name+"</option>";
									});
									$("#cityId").html(option);							
								}
							},
							error: function(message){
							}		
						});
					}else {
						$("#cityId").html("<option value=''>请选择城市</option>");
						$("#areaId").html("<option value=''>请选择城区</option>");
					}
					
				});
				
				$("#cityId").on('change',function(){
					var cityId= $("#cityId").val();
					//区
					if(cityId!=""){
						$.ajax({
							type: "GET",
							url: urlPrefix + "region/findProvOpenCity/"+cityId,
							contentType: "application/json;charset=utf-8",
							dataType: "json",
							success: function(message){
								$("#areaId").html("");
								if(message.code=="ACK"){
									var option = "<option value=''>请选择城区</option>";
									_.each(message.data,function(value,index){
										option += "<option value='"+value.id+"'>"+value.name+"</option>";
									});
									$("#areaId").html(option);							
								}
							},
							error: function(message){
							}		
						});
					}else {
						$("#areaId").html("<option value=''>请选择城区</option>");
					}					
				});
				
				$("#update").on('click',_(this.submitForm).bind(this));
				//验证
				this.validator = $('#addForm').validate({
		        	rules : {
		        		orgName : {
		        			required : true,
		        			maxlength:10
		        		},
		        		contact : {
		        			required : true,
		        			maxlength:10
		        		},
		        		phone : {
		        			required : true,
		        			digits:true,
		        			minlength:11,
		        			maxlength:11
		        		},
		        		provinceId : {
		        			required : true
		        		},
		        		serviceAddress : {
		        			required : true,
		        			maxlength:50
		        		},
		        		lng : {
		        			required : true,	        			
		        		},
		        		lat : {
		        			required : true,	        			
		        		},
		        		bankName : {
		        			required : true,
		        			maxlength:20
		        		},
		        		branchName : {
		        			required : true,
		        			maxlength:20
		        		},
		        		bankAcct : {
		        			required : true,	
		        			digits:true,
		        			maxlength:20
		        		},
		        		bankContact : {
		        			required : true,
		        			maxlength:20
		        		},
		        		businessNumber : {
		        			required : true,
		        			maxlength:50
		        		},
		        		terminalNumber : {
		        			required : true,
		        			maxlength:50
		        		},
		        		deviceNumber : {
		        			required : true,
		        			maxlength:50
		        		},
		        		status : {
		        			required : true
		        		}
		        	}, 
		        	messages : {
		        		orgName : {
		        			required : "区域平台商名称不能为空"
		        		},
		        		contact : {
		        			required : "联系人不能为空"
		        		},
		        		phone : {
		        			required : "联系电话不能为空",
		        			minlength: "请输入11位手机号码",
		        			maxlength: "请输入11位手机号码"
		        		},
		        		
		        		provinceId : {
		        			required : "请选择所在地区"
		        		},
		        		serviceAddress : {
		        			required : "详细地址不能为空"	        			
		        		},
		        		lng : {
		        			required : "未显示正确经度，请填写详细地址"	        			
		        		},
		        		lat : {
		        			required : "未显示正确纬度，请填写详细地址"		        			
		        		},
		        		bankName : {
		        			required : "开户银行不能为空"
		        		},
		        		branchName : {
		        			required : "开户支行不能为空"
		        		},
		        		bankAcct : {
		        			required : "开户账户不能为空",
		        		},
		        		bankContact : {
		        			required : "开户人不能为空"
		        			
		        		},
		        		businessNumber : {
		        			required : "商户号不能为空"
		        		},
		        		terminalNumber : {
		        			required : "终端号不能为空"
		        		},
		        		deviceNumber : {
		        			required : "设备序列号不能为空"
		        		},
		        		status : {
		        			required : "请选择账户状态"
		        		}
		        	}		        	
		        });								
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
				 var orgId = $("#parentId").val();
					var province = $("#provinceId").val();
					var city = $("#cityId").val();
					var areaId = $("#areaId").val();
					if(areaId == "") {
						var region = city;
					}else {
						var region = areaId;
					}
					
					var areaAddForm = {
							orgId       : orgId,
							orgName     : $("#orgName").val(),														
							phone       : $("#phone").val(),	
							contact     : $("#contact").val(),						
							address     : $("#serviceAddress").val(),
							region      : region,
							lng         : $("#lng").val(),
							lat         : $("#lat").val(),							
							branchName  : $("#branchName").val(),
							bankName    : $("#bankName").val(),
							bankContact : $("#bankContact").val(),
							bankAcct    : $("#bankAcct").val(),	
							businessNumber : $("#businessNumber").val(),
							terminalNumber : $("#terminalNumber").val(),
							deviceNumber : $("#deviceNumber").val(),
							status      : $("#status").val(),							
					};
					$.ajax({
						type: "POST",
						url: urlPrefix + "supplier/edit",
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