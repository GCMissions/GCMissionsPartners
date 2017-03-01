$(function(){
	var pointsMain = {
			initEvents:function(){
				$("#buyAmount").attr("value",$("#buyAmountHidden").val()/100);
				$('#pointAdd').on("click",_(this.submitForm).bind(this));	
				//验证
				this.validator = $('#pointAddForm').validate({
		        	rules : {
		        		registerPoints : {
		        			required : true,
		        			digits:true,
		        			//min:1,
		        			maxlength:5
		        		},
		        		buyAmount : {
		        			required : true,
		        			digits:true,
		        			//min:1,
		        			maxlength:7
		        		},
		        		buyPoints : {
		        			required : true,
		        			digits:true,
		        			//min:1,
		        			maxlength:5
		        		},
		        	}, 
		        	messages : {
		        		registerPoints : {
		        			required : "注册积分值不能为空"
		        		},
		        		buyAmount : {
		        			required : "消费金额不能为空",
		        			digits   : "消费金额只允许输入零或正整数",
		        			//min      : "消费金额只允许输入正整数",
		        			maxlength: "消费金额长度不允许大于7"
		        		},
		        		buyPoints : {
		        			required : "消费积分不能为空",
		        			digits   : "消费积分只允许输入零或正整数",
		        			//min      : "消费积分只允许输入正整数",
		        			maxlength: "消费积分长度不允许大于5"
		        		}
		        	},
		        	errorPlacement: function(error, element) { //指定错误信息位置 
		        		if (element.hasClass("points")) { 
		        		var eid = element.attr('name'); //获取元素的name属性 
		        		error.appendTo(element.parent()); //将错误信息添加当前元素的父结点后面 
		        		} else { 
		        		error.insertAfter(element); 
		        		} 	        		
		        	}, 		        	
		        });
				
			},
			
			submitForm : function(){
				if(!this.validator.form()) {
					return false;
				}else {
					this.doSave();
				}
			},
			
			doSave : function(){
				var pointAddForm = {
						registerPoints : $("#registerPoints").val(),
						buyAmount : ($("#buyAmount").val())*100,
						buyPoints : $("#buyPoints").val()
				};
				$.ajax({
					type: "POST",
					url: "savePoints",
					contentType: "application/json;charset=utf-8",
					data: JSON.stringify(pointAddForm),
					dataType: "json",
					success: function(message){
						if(message.code=="ACK"){
							alert('保存成功');
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