$(function(){
	var deliveryAdd = {
			initEvents:function(){
				var _self = this;			
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
						}
					},
					error: function(message){
					}		
				});
				
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
				
		        $('.addItem').on('click', _(this.addItem).bind(this));
				
				$("#save").on('click',_(this.submitForm).bind(this));
				
				//$("#dataList").on('change','.bs-checkbox input',_(this.removeSuccess).bind(this));

				//验证
				this.validator = $('#addForm').validate({
		        	rules : {
		        		orgName : {
		        			required : true,
		        			maxlength:10
		        		},
		        		loginId : {
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
		        		barcodeFlag : {
		        			required : true
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
		        		}

		        	}, 
		        	messages : {
		        		orgName : {
		        			required : "区域平台商名称不能为空"
		        		},
		        		loginId : {
		        			required : "区域平台商登录名不能为空"
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
		        		barcodeFlag : {
		        			required : "请选择是否扫描终端配送商"
		        		},
		        		businessNumber : {
		        			required : "商户号不能为空"
		        		},
		        		terminalNumber : {
		        			required : "终端号不能为空"
		        		},
		        		deviceNumber : {
		        			required : "设备序列号不能为空"
		        		}
		        	},
		        	errorPlacement: function(error, element) { //指定错误信息位置 
		        		if (element.hasClass("provinceId")) { 
		        		var eid = element.attr('name'); //获取元素的name属性 
		        		error.appendTo(element.parent()); //将错误信息添加当前元素的父结点后面 
		        		} else { 
		        		error.insertAfter(element); 
		        		} 	        		
		        	}, 
		        	
		        });
				
			},
			
			/*removeSuccess : function(){
				$("#dataList tr").removeClass("success");
			},*/
			
			addItem : function(){
				var that = this;
				that.dialog =  BootstrapDialog.show({
			        title: '关联区域平台商',
			        message: $(template('addEditTpl', {})),
			        draggable: true,
			        buttons: [{
			        	label: '确认',
			            cssClass: 'btn-primary',
			            action: function(dialog) {
			            	$("#relatedSupplier").html("");
			                //dialog.setMessage('Message 1');
			            	var relatedSupplier = $('#dataList').bootstrapTable('getSelections');
			            	if(relatedSupplier!=""){
			            		var orgId = relatedSupplier[0].orgId;
				            	var orgCode = relatedSupplier[0].orgCode;
				            	var orgName = relatedSupplier[0].orgName;
				            	var contact = relatedSupplier[0].contact;
				            	var phone = relatedSupplier[0].phone;
				            			            	
				            	var html = '<thead><th>区域平台商编号</th><th>区域平台商名称</th><th>联系人</th><th>联系电话</th><th class="hidden"></th></thead>' +
		            			'<tbody><tr><td>'+orgCode+'</td><td>'+orgName+'</td><td>'+contact+'</td><td>'+phone+'</td><td id="orgId" class="hidden" value="'+orgId+'"></td></tr></tbody>';
		            	
				            	$("#relatedSupplier").append(html);
			            	}
			            	
			            	
			            	that.validateSupplier();
			            	dialog.close();
			            	
			            }
			        }, {
			        	label: '取消',
			            action: function(dialog) {
			                dialog.close();
			            }
			        }],
			        onshown : function() {
			        	var table = $.GLOBAL.utils.loadBootTable({
							table : $('#dataList'),
						    refreshBtn : $('#refreshRecord'),
						    idField : "orgId",
						    url: 'supplier/list',
						    sidePagination:'server',
						    pagination : true,
						    queryParamsType: "limit", 
						    singleSelect : true,
						    queryAddParams: function() {
						    	return {
						    		cityId  : $("#cityId").val(),
						    		orgCode : $("#supplierOrgCode").val(),
						    		orgName : $("#supplierOrgName").val(),
						    		contact : $("#supplierContact").val(),
						    		phone   : $("#supplierPhone").val(),
						    		status  : 1
						    	}
						    },
						     
						    columns: [
						        {
						            //field: 'orgId',
						            radio: true,
						        } ,
						        {
						            field: 'orgCode',
						        } ,
						        {
						            field: 'orgName',
						        } ,
						        {
						            field: 'contact',
						        } ,
						        {
						            field: 'phone',
						        },
						        {
						            field: 'orgId',
						        }						        						     
						     ]
						});
						
			        }
			    }); 
		        that.dialog.getModalDialog().css('width', '700px');

			},
			
			validateSupplier : function() {
				if($("#relatedSupplier").find("tr").length>0) {
					$(".supplierError").addClass("hidden");
					return true;
				}else {
					$(".supplierError").removeClass("hidden");
					return false;
				}
			},
			
			submitForm : function(){
				var formFlag = this.validator.form();
				var supplierFlag = this.validateSupplier();
				if(!formFlag||!supplierFlag) {
					return false;
				}else {
					this.doSave();
				}
			},
			
			doSave : function(){
				var province = $("#provinceId").val();
				var city = $("#cityId").val();
				var areaId = $("#areaId").val();
				var barcodeFlag = $("[name='barcodeFlag']").filter(":checked").val(); 
				if(areaId == "") {
					var region = city;
				}else {
					var region = areaId;
				}
				
				var areaAddForm = {
						orgName     : $("#orgName").val(),
						loginId     : $("#loginId").val(),
						contact     : $("#contact").val(),
						phone       : $("#phone").val(),	
						region      : region,
						address     : $("#serviceAddress").val(),
						lng         : $("#lng").val(),
						lat         : $("#lat").val(),
						bankName    : $("#bankName").val(),
						branchName  : $("#branchName").val(),
						bankAcct    : $("#bankAcct").val(),
						bankContact : $("#bankContact").val(),
						parentId    : $("#orgId").attr("value"),
						barcodeFlag : barcodeFlag,
						businessNumber : $("#businessNumber").val(),
						terminalNumber : $("#terminalNumber").val(),
						deviceNumber : $("#deviceNumber").val()
				};
				$.ajax({
					type: "POST",
					url: "add",
					contentType: "application/json;charset=utf-8",
					data: JSON.stringify(areaAddForm),
					dataType: "json",
					success: function(message){
						if(message.code=="ACK"){
							window.location.href= urlPrefix + "delivery/";
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
