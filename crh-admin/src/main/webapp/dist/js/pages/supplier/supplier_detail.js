$(function(){
	var supplierDetail = {
			qrconfig : $.GLOBAL.config.qrcodes,
			initEvents:function(){
				var _self = this;	
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					idField : "orgId",
				    url: 'delivery/list',
				    sidePagination:'server',
				    pagination : true,
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	return {
				    		parentId : $("#parentId").val()
				    	}
				    },
				     
				    columns: [
				        {
				        	width:50,
				        	align: 'center',
				        	formatter:function(value,row,index){  
				        		return index+1;
				        	}
				        } ,

				        {
				            field: 'orgCode',
				            align: 'center',
				        } ,
				        {
				            field: 'orgName',
				            align: 'center',
				        } ,
				        {
				            field: 'contact',
				            align: 'center'
				        } ,
				        {
				            field: 'phone',
				            align: 'center'
				        }
				     ]
				});
				
				//终端配送商总数
				
				if($('#dataList tbody').find('tr:first').hasClass('no-records-found')) {
					var supplierNum = 0;
				}else {
					var supplierNum = $('#dataList tbody').find('tr').length;
				}
				$("#supplierNum").html(supplierNum);
				
				$("#resetPsd").on('click',function(){
					var loginId = $("#parentId").val();
					var isConfirm = window.confirm("确认将该商家密码重置吗？");
					if(isConfirm){
						$.ajax({
							type: "POST",							
							contentType: "application/json;charset=utf-8",
							url: urlPrefix + "supplier/resetPwd/" +loginId,
							dataType: "json",
							success: function(message){
								if(message.code=="ACK"){
									window.confirm("重置成功");
								}
							},
							error:function(message){
								window.confirm(message.message);
							}
						});
					}
				});
				
				$("#codePic").on('click',function(){
					$("#code").removeClass("hidden");
				});
				//二维码
				userInfo = {
						loginId  : "",
						orgId    : $("#parentId").val(),
						userName : "",

					};
					
				//this.qrText= this.qrconfig.qrText.template(userInfo);
				this.qrText = $("#qrCodeUrl").val();
				this.defaultConfig = this.qrconfig.defaultConfig;
				this.loadDefaultQrcode();

			},
			
			loadDefaultQrcode : function() {
				this.buildQrcode($('#code'),  this.defaultConfig.width, this.defaultConfig.height);
			},
			
			buildQrcode: function($item, width, height) {
				$item.qrcode({ 
					
					width: width, //宽度 
					height:height, //高度 
					text: this.qrText
				}); 
			},
			
			init:function(){
				var _self = this;
				_self.initEvents();
			}
	
	}.init();	
});