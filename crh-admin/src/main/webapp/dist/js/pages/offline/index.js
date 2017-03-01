$(function(){
	var offlinePurchase = {			
		initEvents:function(){	
			var urlArr = [];//所有“已扫码”
			prefixCodeArr = [];// 所有已扫码明码、子码集合
			var $inputTxt = $("#shipmentInput");//扫码框
			var that = this;
			$("#shipmentInput").on("keyup",function(e){	
				$("#orderSubmit").removeClass("disabled");
				$(".orderId").text("");
				var idx = $inputTxt.index(this);
				//回车键自动换行
				if(this.value){				
					if(e.keyCode == "13"){
						e.preventDefault();
						//单个扫描完直接请求后台去做验证
						if($.inArray(this.value,urlArr) != -1){
							$('body').loadingInfo("error", "重复扫描");
							$(this).val("");
							$(this).focus();
						}else{
							that.doValidate();
							$($inputTxt).val("");
						}
					}
				}
			});
			
			$("#orderSubmit").on("click",function(){
				that.orderSubmit();
			});
			
			
			
			
			$(".box-payMethod .payBtn").on("click",function(){
				var _self = this;
				if($(_self).hasClass("weixin")) {
					$("#paymentType").attr("value","3");
				}else if($(_self).hasClass("pay")) {
					$("#paymentType").attr("value","2");
				}
				that.dialog = BootstrapDialog.show({
					title : "扫码支付",
					message : $(template('payTpl', {})),
					draggable : true,
					buttons : [ {
						label : '确定',
						action : function(dialog) {
							dialog.close();
						}
					} ],
					onshown: function(){
						$("#authCode").focus();
						if($(_self).hasClass("weixin")) {
							$(".paymethod").text("微信支付条码：");
						}else if($(_self).hasClass("pay")) {
							$(".paymethod").text("支付宝支付条码：");
						}
					}
				});
				that.dialog.getModalDialog().css('width', '300px');
			});
			
			$(document).on("change","#authCode",function(){
				/*if($("#paymentType").attr("value")=="3"){
					that.wechatSubmit();
				}
				else if($("#paymentType").attr("value")=="2"){
					that.alipaySubmit();
				}*/
				that.doSubmit();
			});
			
			
			
			//删除商品
			$(document).on("click",".datailItem",function(){
				$(".orderId").text("");
				var prefixCode = $(this).closest("tr").find(".prefixCode").text();
				prefixCodeArr.splice($.inArray(prefixCode,prefixCodeArr),1);
				
				$(this).closest("tr").remove();
				
				that.calculate();
			})
		},
		
		//支付按钮显示隐藏
		
		payMethodIsShow : function(){
			if($("#product_list").find('tr').length<1) {
				$(".payMethodDiv").addClass("hidden");
			}else {
				$(".payMethodDiv").removeClass("hidden");
			}
		},
		
		//提交订单后删除按钮失效
		
		deleteUnCkick : function() {
			$("#product_list").find("tr").find(".delete").each(function (){
				var item = $(this);
				item.addClass("fa-dis");
				item.find(".datailItem").removeClass("datailItem")
			});
		},
		
		//删除按钮初始化
		
		deleteCkick : function() {
			$("#product_list").find("tr").find(".delete").each(function (){
				var item = $(this);
				item.removeClass("fa-dis");
				item.find(".datailItem").addClass("datailItem");
			});
		},
		
		//订单提交
		orderSubmit : function() {
			var that = this;
			var products = [];
			$("#product_list").find('tr').each(function() {
				var productId = $(this).find('.productId').text();
				var num = 1;
				var prefixCode = $(this).find('.prefixCode').text();
				products.push({
					productId : productId,
					num       : num,
					prefixCode: prefixCode
					});
	        });
			var data = {
					products    : products,
					source    : 7
			}
			$.ajax({
				type: 'POST',
				url: urlPrefix + "/offline/order/submit",
				dataType: 'json',
				contentType: 'application/json',
				data : JSON.stringify(data)
			})
			.done(function(data){
				if(data.code == "ACK") {
					$("#orderId").attr("value",data.data.orderId);
					$("#actualAmount").attr("value",data.data.actualAmount);
					$(".orderId").text(data.data.orderId);
					$('body').loadingInfo("error","订单提交成功，请支付!");
					
					$("#orderSubmit").addClass("disabled");
					that.payMethodIsShow();
					that.deleteUnCkick();
				}
			})
			.fail(function(){
				
			});
			
		},
		
		
		calculate : function() {
			var productNum = $("#product_list").find("tr").length;
			var productRealPay = 0;
			$("#product_list").find('.salePrice').each(function() {
				productRealPay = parseFloat(productRealPay) + parseFloat($(this).text());
	        });
			$(".productNum").text(productNum);
			$(".productPay").text(productRealPay.toFixed(2));
			$(".productRealPay").text(productRealPay.toFixed(2));
		},
		
		
		doSubmit : function(){
			var that = this;
			var paymentType = $("#paymentType").val();
			if(paymentType == "3") {  //微信支付
				var url = urlPrefix + "wechat/micropay";
			}else if(paymentType == "2") {   //支付宝支付
				var url = urlPrefix + "alipay/pay";
			}
			/*var products = [];
			$("#product_list").find('tr').each(function() {
				var productId = $(this).find('.productId').text();
				var num = 1;
				var prefixCode = $(this).find('.prefixCode').text();
				products.push({
					productId : productId,
					num       : num,
					prefixCode: prefixCode
					});
	        });
			var data = {
					products    : products,
					totalAmount : parseFloat($(".productRealPay").text()) * 100,
					//totalAmount : "1",
					paymentType : $("#paymentType").val(),
					authCode    : $("#authCode").val()
			}*/
			
			var data = {
					orderId : $("#orderId").val(),
					authCode    : $("#authCode").val()
			};
			
			$(".payStatus").text('支付中...').removeClass("hidden");
			$.ajax({
				type: 'POST',
				//url: urlPrefix + "offline/product/pay",
				url : url,
				dataType: 'json',
				contentType: 'application/json',
				data : JSON.stringify(data)
			})
			.done(function(data){
				if(data.code == "ACK") {
					if(data.data.code == "success"||data.data.code == "SUCCESS") {
						$(".payStatus").text('支付成功').removeClass("hidden");					
						that.paySuccess();					
					}else {
						$(".payStatus").text('支付失败').removeClass("hidden");
						$("#authCode").val("");
						$("#authCode").focus();
					}					
				}else {
					$(".payStatus").text('支付失败').removeClass("hidden");
					$("#authCode").val("");
					$("#authCode").focus();
				}		
			})
			.fail(function(){
				$(".payStatus").text('支付失败').removeClass("hidden");
				$("#authCode").val("");
				$("#authCode").focus();
			});
			
			$("#orderSubmit").removeClass("disabled");
		},
		
		paySuccess : function(){
			var that = this;
			setTimeout(function(){
				that.dialog.close();	
			},3000);
						
			  // 清除商品信息
			$("#product_list").html("");
			that.payMethodIsShow();
			prefixCodeArr = [];
			that.calculate();
			
		},
		doValidate : function(){
			var that = this;
			var condition = $("#shipmentInput").val();
			var data = {
				condition : condition
			};
			if(condition.indexOf("http")=="-1") {
				$('#product_list').html("");
				that.payMethodIsShow();
				prefixCodeArr = [];
			}
			$.ajax({
				type: 'POST',
				url: urlPrefix + "offline/product/query",
				dataType: 'json',
				contentType: 'application/json',
				data : JSON.stringify(data)
			})
			.done(function(result){
				if(result.code == "ACK"){
					var dataList = result.data;
					var length = dataList.length;
					var errorTmpl = "";
					var hintMsg = "";
					_.each(dataList,function(data,index){
						var productId = data.productId;
						var productName = data.productName;
						var productCode = data.productCode;
						var salePrice = data.salePrice;
						var unit = data.unit;
						var available = data.available;
						
						if(available) {
							if(salePrice == null) {
								salePrice = 0;
							}
							if(unit == null) {
								unit = "";
							}
							var prefixCode = data.prefixCode;
							
							if(prefixCode == null) {
								prefixCode = "";
							}
							if(prefixCode != ""&&$.inArray(prefixCode,prefixCodeArr)!=-1) {
								//$('body').loadingInfo("error","重复扫码!");
								errorTmpl = errorTmpl + "<p>"+prefixCode+"重复扫码</p>"
							}else {
								prefixCodeArr.push(prefixCode);
								tmpl =  "<tr><td class='hidden productId'>"+productId+"</td><td>"+productName+"</td><td>"+productCode+"</td><td class='salePrice'>"+salePrice+"元</td><td>"+unit+"</td><td class='prefixCode'>"+prefixCode+"</td><td><a class='oper delete' title='删除'><i class='datailItem fa fa-trash'  style='font-size:20px'></i></a></td></tr>";
								$('#product_list').append(tmpl);
							}
						}else {
							hintMsg = hintMsg + "<p>" + data.hintMsg + "</p>";
							$('body').loadingInfo("error",hintMsg);
						}	
						
					});	
					
					/*if(condition.indexOf("http")=="-1") {
						$('#product_list').html("");
					}*/
					
					that.calculate();
					
					//扫母码重复扫码显示错误
					if(condition.indexOf("http")!="-1"&&errorTmpl!="") {
						var dialog = BootstrapDialog.show({
							title : "重复扫码",
							message : $(template('errorTpl', {})),
							draggable : true,
							buttons : [ {
								label : '确定',
								action : function(dialog) {
									dialog.close();
								}
							} ],
							onshown: function(){
								$(".errorMessage").html(errorTmpl);
							}
						});
						dialog.getModalDialog().css('width', '300px');

					}

					
				}else if(result.code == "NACK"){
					$($inputTxt).val("");
				}
			})
			.fail(function(result){
				$('body').loadingInfo("error", message("admin.message.error"));
			})
		},
			
		init:function(){
			var _self = this;
			_self.initEvents();
		}
	}.init();
});