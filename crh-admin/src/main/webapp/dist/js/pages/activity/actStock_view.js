$(function() {
	var productId = $(".j-prd").attr("product_id");
	
	//过滤html转义字符
	function HTMLDecode(text){
	    var temp = document.createElement("div");
	    temp.innerHTML = text;
	    var output = temp.innerText || temp.textContent;
	    temp = null;
	    return output;
	}
	
	// 时间选择
	var actStock = {
			init : function(){
				return this;
			},
			
			initMainSpecDeal : function(specInfo) {
				// 初始化
				if (specInfo) {
					for (var i = 0; i < specInfo.length; i++) {
						if (i == 0) {
							var	subSpecList = specInfo[i].subSpecList;
							$('.mainSpec>div:eq(0) .subSpec ul').html($(template('subSpecTpl', {'subSpecList':subSpecList})));
						} else {
							$('.mainSpec').append($(template('mainSpecTpl', specInfo[i])));
						}
					}
				} 
				$('#addMainSpec').css('display', "none");
			},
			
			initSpecStockDeal : function(actSpecList) {
				if (actSpecList) {
					// 初始化
					for (var i = 0; i < actSpecList.length; i ++) {
						var subSpecPrice = HTMLDecode(actSpecList[i].priceDesc);
						actSpecList[i].priceDesc = JSON.parse(subSpecPrice);
					}
					
					$('.stockInfo').html($(template('stockInfoCopyTpl', {'actSpecList' : actSpecList})));
				}
			},
			
			initModifyStockDeal : function (actDateStockList) {
				// 初始化
				if (actDateStockList) {
					$('.modifyStock').html($(template('modifyStockModalTpl', {'status': '0', 'actDateStockList' : actDateStockList})));
				}
			},
			
			initPartakeInfoDeal: function() {
				$.ajax({
					url :  urlPrefix + 'activity/stock/findPartakeInfo',
					type : 'GET',
					data : {
						'productId': productId
					},
					dataType:'json',
					success : function(res) {
						if (res.code == 'ACK') {
							$('.partakeInfo tbody').html($(template('partakeInfoTpl', {'actPartakes': res.data})));
						}
					}
				});
			},
			
			initPurchaseInfoDeal : function () {
				$(".sale_time input[type='text'], .sale_time .dateEnd_tip").css("display", "none");
				// 获取购买信息数据
				var that = this;
				$.ajax({
					url : urlPrefix + 'activity/stock/findPurchaseInfo',
					data : {
						'productId' : productId,
					},
					dataType : 'json',
					type : 'POST',
					success : function(result) {
						if (result.code == 'ACK') {
							// 购买信息
							var purchaseInfo = result.data;
							if (purchaseInfo) {
								if (purchaseInfo.buyDeadline) {
									$('.deadlineInfo p').html(purchaseInfo.buyDeadline + "&nbsp;（0表示下单时间不提前截止）");
								} else {
									$('.deadlineInfo p').html(0 + "&nbsp;（0表示下单时间不提前截止）");
								} 
								
								if (purchaseInfo.requireFields) {
									var requireFields = HTMLDecode(purchaseInfo.requireFields);
									var reqFields = requireFields.split('&');
									
									$('.requireInfo div input').each(function(){
										if ($.inArray($(this).val(), reqFields) > -1) {
											$(this).prop('checked', true);
										}
									});
								} 
								if ('1' == purchaseInfo.saleType) {
									$("input[name='sale_type']").eq(0).removeProp('checked');
									$("input[name='sale_type']").eq(1).prop('checked', 'checked');
									if (purchaseInfo.startDate){
										$('#sale_start').val(purchaseInfo.startDate);
									}	
									if (purchaseInfo.endDate){
										$('#sale_end').val(purchaseInfo.endDate);
									}
									// 显示时间选择内容
									$(".sale_time input[type='text']").css("display", "inline-block");
									$('.sale_time .dateEnd_tip').css("display", "inline");
								}
							}
						} 
						
						that.initPartakeInfoDeal();
						$("#tab_4 input").prop('disabled', true);
					}
					
				});
			},
			
			disabledStockDeal : function() {
				$('#stockDialog input').prop('disabled', true);
				$('.stockInfo .price_area .limit').prop('disabled', true);
				$("input[name='stockType'], input[name='show_stock']").prop("disabled", true);
			}, 
			
			clearContentDeal : function() {
				// 活动日期
				$('#addActDate').next().empty();
				$('#addActDate').css("display", "none");
				
				// 主规格
				$('.mainSpec').children().each(function(index){
					if (index == 0) {
						$(this).find('.subSpec ul').empty();
					} else {
						$(this).remove();
					}
				});
				// 规格库存（含价格）
				$('.stockInfo').empty();
				$('#modify_num').val('');
			},
			
			initInfoDeal: function(actDate) {
				// 清除数据
				this.clearContentDeal();
				
				// 活动日期
				$('#addActDate').next().html('<li>' + actDate + "</li>");
				
				var that = this;
				// 获取数据
				$.ajax({
					url : urlPrefix + 'activity/stock/query/' + productId,
					data :{
						'actDate' : actDate,
					},
					dataType : 'json',
					type : 'GET',
					success : function(result) {
						if (result.code == 'ACK') {
							var data = result.data;
							// 商品规格
							var specInfo = JSON.parse(HTMLDecode(data.specInfo));
							that.initMainSpecDeal(specInfo);
							// 库存规格
							that.initSpecStockDeal(data.actSpecList);
							
							if (data.stockType == '1' || data.stockType == '2') {
								var index = parseInt(data.stockType);
								$("input[name='stockType']").eq(0).prop('checked', false);
								if (data.stockType == '1') {
									// 按人数设置库存
									that.initModifyStockDeal(data.actDateList);
									$('#modify_num').val(data.originalCount);
									$('#modify_num').css('display', 'inline-block');
									$("input[name='stockType']").eq(index).prop('checked', true);
								} else {
									$("input[name='stockType']").eq(index).prop('checked', true);
								}
							} 
							
							// 是否显示库存
							if (data.showStock == '0' || data.showStock == '1') {
								var index = parseInt(data.showStock);
								$("input[name='show_stock']").eq(0).prop('checked', false);
								$("input[name='show_stock']").eq(index).prop('checked', true);
							}
						} 
						
						// 进入按钮、输入框
						that.disabledStockDeal();
					}
				});
			},
	}
	//初始化
	var actStockInstance = actStock.init();
	// 获取购买信息
	actStockInstance.initPurchaseInfoDeal();
	
	// 查看库存
	$('.j-prd').on('click', ".pull-right", function(){
    	var actDate = $(this).parent().children('label').text();
    	actStockInstance.initInfoDeal(actDate);
    	$('#stockDialog').modal('show');
    });
});