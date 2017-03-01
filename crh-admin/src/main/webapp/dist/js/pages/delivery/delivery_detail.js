$(function(){
	var deliveryDetail = {
			qrconfig : $.GLOBAL.config.qrcodes,
			initEvents:function(){
				var _self = this;	
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