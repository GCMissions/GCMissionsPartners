$(function(){

	var actStockInstance, 
		productId = $(".j-prd").attr("product_id"),//获取productID
		delFlag= '×', delReqFlag= '×必填', specTextLength = 15,
		// 自然数或两位小数
		patternDouble =  /^([1-9]\d{0,}|0)([.]\d{0,2})?$/;


    $("body").on("click", ".m-dot", function(){
        var _this = $(this);
		if (_this.hasClass("dot")) {
			_this.removeClass("dot");
		} else {
			_this.addClass("dot");
		}
		var mboxLength = $(".m-box").length;
		var dotLength = $(".dot").length;
		if (mboxLength === dotLength) {
			$(".u-qx").find("label").addClass("u-dot");
		} else {
			$(".u-qx").find("label").removeClass("u-dot");
		}
    });
    
	$("body").on("click", ".u-qx", function(){
		var _this = $(this).find("label");
		if (_this.hasClass("u-dot")) {
			_this.removeClass("u-dot");
			$(".m-dot").removeClass("dot");
		} else {
			_this.addClass("u-dot");
			$(".m-dot").addClass("dot");
		}
	});
    
	// 获取列表的日期
	function getStockDateList() {
		var actDateList = new Array();
		$('.j-prd .act_date').each(function(){
			var text = $.trim($(this).text());
			actDateList.push(text);
		});
		
		return actDateList;
	}
	
	// 时间选择
	var actStock = {
			init : function(){
				var _self = this;
				_self.initEvents();
				return this;
			},
			
			initEvents: function(){
				// 初始化
				$('#act_start, #act_end, #sale_start, #sale_end').datetimepicker({
					minView: 'month',
					format: 'yyyy-mm-dd',
					language: 'ch',
					autoclose : true,
					todayBtn : true,
					startDate : new Date()
				});
				
				this.actDateDeal();
				this.mainSpecDeal();
				this.subSpecDeal();
				this.specStockDeal();
				this.modifyStockDeal();
				this.saveStockInfoDeal();
				this.purchaseInfoDeal();
				this.savePurchaseInfoDeal();
			},
			
			actDateDeal: function() {
				
				var daySlider, monthSlider,
					curDayOfMonth, curMonth, curYear, curDaysOfMonth;
				
				$('#addActDate').click(function(){
					var curDate = new Date();
					curYear = curDate.getFullYear();
					curMonth = curDate.getMonth() + 1;
					curDayOfMonth = curDate.getDate();
					curDaysOfMonth = new Date(curYear, curMonth, "0").getDate();
					
					$("#daySlider").attr({'data-slider-max':31,'data-slider-value':curDayOfMonth});
					$("#monthSlider").attr('data-slider-value', curMonth);
					
					// 初始化slider
					monthSlider = $("#monthSlider").bootstrapSlider({tooltip: 'always'});
					$("#monthSlider").prev().css({'width':'100%'});
					daySlider = $("#daySlider").bootstrapSlider({tooltip: 'always'});
					$("#daySlider").prev().css('width', '100%');
					
					$('#dateDialog').modal('show');
				});
				 
				//活动-开始日期
				$('#act_start').on('changeDate', function(ev){
					// 将结束日期的起始日期设置为开始日期的值
					if (ev.date) {
						$("#act_start").next().css('display','inline-block');
						$('#act_end').datetimepicker('setStartDate', $('#act_start').val());
					} else {
						$("#act_start").next().css('display','none');
						$('#act_end').datetimepicker('setStartDate', dateStrFormat(curYear, curMonth, curDayOfMonth));
					}
				});
	
				//活动-结束日期
				$('#act_end').on('changeDate', function(ev){
					// 如果先选择了结束日期，则将开始日期的截止日期设为结束日期的值
					if (ev.date) {
						$("#act_end").next().css('display','inline-block');
						$('#act_start').datetimepicker('setEndDate', $('#act_end').val());
					} else {
						$("#act_end").next().css('display','none');
						$('#act_start').datetimepicker('setEndDate', '');
					}
				});
				
				$("#monthSlider").on('slideStop', function(slideEvt){
					var monthSliderValue = slideEvt.value;
					if (slideEvt.value < curMonth) {
						monthSliderValue = curMonth;
						monthSlider.bootstrapSlider('setValue', curMonth, true);
					}
					
					var daySliderValue = $("#daySlider").bootstrapSlider('getValue');
					// 获得该月的天数，修改日期滑动条的最大值
					var days = new Date(curYear, monthSliderValue, "0").getDate();
					if (daySliderValue > days) {
						daySlider.bootstrapSlider('setValue', days, true);
					}
					if (monthSliderValue == curMonth && daySliderValue < curDayOfMonth) {
						daySlider.bootstrapSlider('setValue', curDayOfMonth, true);
					}
				});
				
				$("#daySlider").on('slideStop', function(slideEvt){
					var daySliderValue = slideEvt.value,
						monthSliderValue = $("#monthSlider").bootstrapSlider('getValue');

					var days = new Date(curYear, monthSliderValue, "0").getDate();
					if (daySliderValue > days) {
						daySliderValue = days;
					} else {
						if (monthSliderValue == curMonth && daySliderValue < curDayOfMonth) {
							daySliderValue = curDayOfMonth;
						}
					}
					daySlider.bootstrapSlider('setValue', daySliderValue, true);
				});
				
				// 关闭时间选择对话框之后处理（清空已选择的数据）
				function closeDateDialog() {
					$('#dateDialog').modal('hide');
					if ($('#time_2').hasClass('active')) {
						if ($('#act_start').val()) {
							$('#act_start').val('').next().css('display','none');
							$('#act_start').datetimepicker('setEndDate', '');
						}
						if ($('#act_end').val()) {
							$('#act_end').val('').next().css('display','none');
							$('#act_end').datetimepicker('setStartDate', '');
						}
						$('#dateTab a:first').tab('show');
					} else {
						monthSlider.bootstrapSlider('setValue', curMonth, true);
						daySlider.bootstrapSlider('setValue', curDayOfMonth, true);
					}
				}
				
				// 检查活动时间是否可以添加
				function checkActDateDeal(startDate, endDate) {
					$.ajax({
						url : urlPrefix + "activity/stock/check/actDate",
						type : 'POST',
						data : {
							'productId' : productId,
							'dateStart' : startDate,
							'dateEnd' : endDate
						},
						dataType : 'json',
						success : function(result) {
							if (result.code == 'ACK') {
								if (endDate) {
									$('#addActDate').next().append('<li>' + startDate + '至' + endDate + '<span>×</span></li>');
								} else {
									$('#addActDate').next().append('<li>' + startDate + '<span>×</span></li>');
								}
								closeDateDialog();
							} else {
								msgTip("操作提示", "该日期或者时间段与已有活动日期重叠或者不在开售时间内！");
							}
						}
					});
				}
				
				// 确认操作
				$('#addActDateComfirm').click(function(){
					if ($('#time_1').hasClass('active')) {
						var actMonth = parseInt($("#monthSlider").val());
						var actDay = parseInt($("#daySlider").val());
						var myActDate = dateStrFormat(curYear, actMonth, actDay);
						if (false == pushActDateList(myActDate)) {
							msgTip('操作提示', '该日期已经存在 ，不可重复添加 ！');
						} else {
							checkActDateDeal(myActDate);
						}
					} else {
						var startDate = $.trim($('#act_start').val())
						var endDate = $.trim($('#act_end').val());
						if ('' == startDate || '' == endDate) {
							$('#dateDialog').modal('hide');
							return false;
						}
						if (false == pushActDateList(startDate, endDate)) {
							msgTip('操作提示', '该日期已经存在 ，不可重复添加 ！');
						} else {
							if (startDate == endDate) {
								checkActDateDeal(startDate);
							} else {
								checkActDateDeal(startDate, endDate);
							}
						}
					}
				});
				
				// 关闭操作
				$('#addActDateCancle').click(function(){
					closeDateDialog();
				});
				
				$('.actDate').on('click', 'ul li span', function(){
					$(this).parent().remove();
				});
			},
			
			initActDateDeal : function(actDateList){
				// 初始化
				if (actDateList) {
					$('#addActDate').next().html($(template('actDateTpl', {'actDateList' : actDateList})));
				}
				
			},
			
			mainSpecDeal : function(){
				// 检查主规格是否重复
				function checkMainSpec(mainSpec) {
					// 默认为未添加[未重复]
					var mainSpecs =$('.mainSpec').children('div');
					for (var i = 0; i < mainSpecs.length; i++) {
						if (mainSpec == $(mainSpecs).eq(i).children('input').val()) {
							return true;
						}
					}
					return false;
				}
				
				$('#addMainSpec, #addMainSpecCancel').click(function(){
					$('#addMainSpec').toggle();
					$('.selMainSpec').toggle();
					return false;
				});
				
				$('#addMainSpecConfirm').click(function(){
					var mainSpecNum = $('.mainSpec').children('div').length;
					if (mainSpecNum >= 3) {
						return false;
					}
					var mainSpec = $(this).prev().val();
					if (mainSpec) {
						if (checkMainSpec(mainSpec)) {
							msgTip('操作提示', '该主规格已经添加 ！');
							return false;
						} else {
							if (mainSpecNum == 2) {
								$('#addMainSpec').prop('disabled', true);
							}
							$('.mainSpec').append($(template('mainSpecTpl',{'mainSpec':mainSpec})));
							$('.selMainSpec').toggle();
							$('#addMainSpec').toggle();
						} 
					}
				});
				
				$('.mainSpec').on('click', '#delMainSpec', function(){
					$(this).parent().remove();
					var data = {
							'secondList': new Array(),
							'thirdList' : new Array(),
					};
					
					var mainSpecs = $('.mainSpec').children('div');
					for (var i = 1; i < mainSpecs.length; i++) {
						$(mainSpecs).eq(i).find('li').each(function(){
							if (i == 1) {
								data.secondList.push($(this).text().replace('×', ''));
							}
							if (i == 2) {
								data.thirdList.push($(this).text().replace('×', ''));
							}
						});
					}
					
					$('.stockInfo .price_area').each(function(){
						$(this).html($(template('stockInfoTpl_2', data)));
					});
					
					// 控制vip价格可否输入
					controlVipPriceInput();
					
					// 添加按钮可用
					if (mainSpecs.length < 3) {
						$('#addMainSpec').prop('disabled', false);
					}
				});
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
					if (specInfo.length < 3) {
						$('#addMainSpec').prop('disabled', false);
					} else {
						$('#addMainSpec').prop('disabled', true);
					}
				} 
			},
			
			subSpecDeal: function() {
				$('.mainSpec').on('click', 'a', function(){
					$(this).toggle();
					$(this).prev().toggle();
				});
				
				// 子规格[添加,删除]之后的后续处理
				function updateSpecInfo() {
					var baseData = {
						'firstList' : new Array(),
						'secondList' : new Array(),	
						'thirdList' : new Array()
					}
					
					var mainSpecNum = $('.mainSpec').children('div').length;
					for (var i = 0; i < mainSpecNum; i++) {
						var subSpecList = new Array();
						var subSpecs = $('.mainSpec>div').eq(i).find('li');
						$(subSpecs).each(function(){
							subSpecList.push($(this).text().replace('×', ''));
						});
						if (i == 0) {
							baseData.firstList = subSpecList;
						}
						if (i == 1) {
							baseData.secondList = subSpecList;
						} 
						if (i == 2) {
							baseData.thirdList = subSpecList;
						} 
					}
					$('.stockInfo').html($(template('stockInfoTpl_1', baseData)));
					
					// 控制vip价格可否输入
					controlVipPriceInput();
					
					var stockType = $("input[name='stockType']:checked").val();
					if (stockType == '0') {
						$('.stockInfo .price_area .limit').prop('disabled', false);
					} else {
						$('.stockInfo .price_area .limit').prop('disabled', true);
					}
				}
				
				// 添加子规格
				$('.mainSpec').on('click', '#addSubSpecConfirm', function(){
					var subSpec = $.trim($(this).prev().val());
					$(this).prev().val('');
					if (subSpec) {
						if (subSpec.length > specTextLength){
							msgTip('操作提示', '子规格内容长度不能超过15个汉字！');
							return false;
						}
						// 查找当前主规格下已经添加了哪些子规格
						var lis = $(this).parent().prev().find('li');
						if (lis.length == 15) {
							msgTip('操作提示', '每个主规格最多可以添加15个子规格！');
							return false;
						} 
						// 判断是否已经添加过相同的子规格
						var target = subSpec + '×';
						for (var i = 0; i < lis.length; i++) {
							if ($(lis).eq(i).text() == target) {
								msgTip('操作提示', '当前主规格下面已经添加过该子规格！');
								return false;
							}
						}
						$(this).parent().prev().append('<li>' + subSpec + '<span>×</span></li>');
						updateSpecInfo();
					}
					$(this).parent().toggle();
					$(this).parent().next().toggle();
				});
				
				$('.mainSpec').on('click', '#addSubSpecCancel', function(){
					$(this).prev().prev().val('');
					$(this).parent().toggle();
					$(this).parent().next().toggle();
				});
				
				// 删除子规格
				$('.mainSpec').on('click', '.subSpec ul li span', function(){
					$(this).parent().remove();
					updateSpecInfo();
				})
				
			},
			
			specStockDeal : function() {
				// 控制库存输入
				$('.stockInfo').on('input propertychange', '.spec_area input', function() {
					var num_1 = checkInteger($(this));
					$(this).val(num_1);
					
					var num_2;
					if ($(this).hasClass('unit_num')) {
						num_2 = $(this).next().next().val();
					} else {
						num_2 = $(this).prev().prev().val();
					}
					if (num_2) {
						$(this).parents('tr').find('.num_area span').text(num_1 * num_2);
					} 
				});
				
				// 保存原价
				$('.stockInfo').on('focusin focusout', '.price, .vip-price', function(e) {
					if (e.type == 'focusout') {
						$(this).removeAttr('pre-value');
					} else {
						$(this).attr('pre-value', $(this).val());
					}
				});
				
				// 控制价格输入
				$('.stockInfo').on('change', '.price, .vip-price', function(e) {
					var price = checkDecimal($(this));
					if (0 == price) {
//						$(this).val($(this).attr('pre-value'));
						return false;
					}
					if ($(this).hasClass('price')) {
						var price_2 = parseFloat($(this).prev().val());
						if (price <= price_2) {
							price = $(this).attr('pre-value');
							return false;
						}
					} else {
						var price_2 = parseFloat($(this).next().val());
						if (price_2 > 0) {
							if (price >= price_2){
								price = $(this).attr('pre-value');
								return false;
							} 
						} else {
							price = 0;
							msgTip('操作提示', '请先设定原价格！');
						}
					}
					
					$(this).val(parseFloat(price));
				});
				
				// 控制限购输入
				$('.stockInfo').on('input propertychange', '.price_area .limit', function(e) {
					$(this).val(checkInteger($(this)));
				});
			},
			
			initSpecStockDeal : function(actSpecList) {
				if (actSpecList) {
					// 初始化
					for (var i = 0; i < actSpecList.length; i ++) {
						var subSpecPrice = HTMLDecode(actSpecList[i].priceDesc);
						actSpecList[i].priceDesc = JSON.parse(subSpecPrice);
					}
					$('.stockInfo').html($(template('stockInfoCopyTpl', {'actSpecList' : actSpecList})));
					controlVipPriceInput();
				}
			},
			
			modifyStockDeal: function() {
				//探测事件
				$("input[name='stockType'").click(function(){
					if ($(this).val() == '1' || $(this).val() == '2') {
						if ($(this).val() == '1') {
							$('#modify_num').css('display', 'inline-block');
						}
						$('.stockInfo .price_area .limit').val('0').prop('disabled', true);
					} else {
						$('#modify_num').css('display', 'none');
						$('.stockInfo .price_area .limit').prop('disabled', false);
					}
				});
				
				//按人数设置库存，如果修改基础量
				$('#modify_num').on('input propertychange', function(){
					$(this).val(checkInteger($(this)));
				});
			
			},
			
			initModifyStockDeal : function (actDateStockList) {
				// 初始化
				if (actDateStockList) {
					$('.modifyStock').html($(template('modifyStockModalTpl', {'status': '0', 'actDateStockList' : actDateStockList})));
				}
			},
			
			saveStockInfoDeal : function() {
				var that = this;
				$('#saveStockInfo').click(function() {
					if (productId == undefined || productId == '' || productId == null) {
						msgTip("操作提示", "获取不到商品编号 ！");
						return false;
					}
					
					var actDateList = getActDateList();
					if (actDateList.length == 0) {
						msgTip("操作提示", "请选择活动日期 ！");
						return false;
					}
					
					var mainSpecs = $('.mainSpec').children(), specInfo = new Array();
					for (var i = 0; i < mainSpecs.length; i++) {
						var specObject = {
								'mainSpec': $(mainSpecs).eq(i).find('input').val(),
								'subSpecList':new Array()
						};
						$(mainSpecs).eq(i).find('li').each(function() {
							specObject.subSpecList.push($(this).text().replace('×', ''));
						});
						if (specObject.subSpecList.length == 0) {
							msgTip("操作提示", "请为主规格["+specObject.mainSpec + "]添加子规格或者选择删除 ！");
							return false;
						} 
						specInfo.push(specObject); 
					}
					
					var mainSpecNum = specInfo.length, actSpecList = new Array(), totalCount = 0;
					//　所有的价格-价格描述-状态值
					var priceList = new Array(),  vipPriceList = new Array(), statusFlag = true,
						priceDescList = getPriceDescs(0, specInfo);
					$('.stockInfo tr').each(function(i){
						var unitNum = parseInt($.trim($(this).find('.unit_num').val())),
							groupNum = parseInt($.trim($(this).find('.group_num').val()));
						if (unitNum == 0 || groupNum == 0) {
							statusFlag = false;
							msgTip("操作提示", "最小单位量或者份数不能为 0 ！")
							return false;
						}
						
						var	sub_total = parseInt($.trim($(this).find('.num_area span').text()));
						//统计总库存量
						totalCount = totalCount + sub_total;
						
						var priceMap = new Array(), limitMap = new Array(), vipPriceMap = new Array();
						if (1 == mainSpecNum) {
							priceDescList[i].price= $.trim($(this).find('.price_area .price').val());
							priceDescList[i].limit = $.trim($(this).find('.price_area .limit').val());
							priceDescList[i].vipPrice = $.trim($(this).find('.price_area .vip-price').val());
							
							var price = toInteger(priceDescList[i].price), vipPrice = toInteger(priceDescList[i].vipPrice);
							priceList.push(price);
							vipPriceList.push(vipPrice);
							
							var key = "\"" + priceDescList[i].subSpec + "\":";
							limitMap.push(key + priceDescList[i].limit);
							// 转换成分
							priceMap.push(key + toInteger(priceDescList[i].price));
							vipPriceMap.push(key + toInteger(priceDescList[i].vipPrice));
						}
						
						if (2 == mainSpecNum) {								
							$(this).find('.price_area>ul>li').each(function(j){
								var priceDesc = priceDescList[i].price[j];
								priceDesc.price = $.trim($(this).children('.price').val());
								priceDesc.limit = $.trim($(this).children('.limit').val());
								priceDesc.vipPrice = $.trim($(this).children('.vip-price').val());
								
								var price = toInteger(priceDesc.price), vipPrice = toInteger(priceDesc.vipPrice);
								priceList.push(price);
								vipPriceList.push(vipPrice);
								
								var key = "\"" + priceDescList[i].subSpec + '-' + priceDesc.subSpec + "\":";
								limitMap.push(key + priceDesc.limit);
								priceMap.push(key + toInteger(priceDesc.price));
								vipPriceMap.push(key + toInteger(priceDesc.vipPrice));
							});
						}
						
						if (3 == mainSpecNum) {
							$(this).find('.price_area>ul>li').each(function(j){
								$(this).find('ul li').each(function(k){
									var priceDesc = priceDescList[i].price[j].price[k];
									priceDesc.price = $.trim($(this).children('.price').val());
									priceDesc.limit = $.trim($(this).children('.limit').val());
									priceDesc.vipPrice = $.trim($(this).children('.vip-price').val());

									var price = toInteger(priceDesc.price), vipPrice = toInteger(priceDesc.vipPrice);
									priceList.push(price);
									vipPriceList.push(vipPrice);
									
									var key = priceDescList[i].subSpec+ '-' + priceDescList[i].price[j].subSpec + '-' + priceDesc.subSpec;
									priceMap.push("\"" + key + "\":" + price);
									vipPriceMap.push("\"" + key + "\":" + vipPrice);
									limitMap.push("\"" + key + "\":" + priceDesc.limit);
								});
							});
						}
					
						var actSpec = {
							'mainSpec' : priceDescList[i].mainSpec,
							'subSpec' : priceDescList[i].subSpec,
							'unitNum': unitNum,
							'groupNum': groupNum,
							'total': sub_total,
							'prices' : '{' + priceMap.join(",") + '}',
							'vipPrices' : '{' + vipPriceMap.join(",") + '}',
							'limits': '{' + limitMap.join(",") + '}',
							'priceDescJson': JSON.stringify(priceDescList[i]),
						}
						actSpecList.push(actSpec);
					});
					
					if (statusFlag == false){
						return false;
					}
					
					// 校验价格
					if (priceList.length == 0) {
						msgTip("操作提示", "价格不可以为空 ！");
						return false;
					}
					for (var i = 0; i< priceList.length; i++) {
						if (priceList[i] == 0) {
							msgTip("操作提示", "价格不可以为 0 ！");
							return false;
						}
						if (vipPriceList[i] >= priceList[i]) {
							msgTip('操作提示', 'VIP价格应小于原价格！');
							return false;
						}
						if (false == patternDouble.test(priceList[i]) || false == patternDouble.test(vipPriceList[i])) {
							return false;
						}
					}
					
					var stockType = $("input[name='stockType']:checked").val();
					if ("1" == stockType) {
						// 如果有修改过库存
						var modifyNum = $.trim($('#modify_num').val());
						if (modifyNum == '') {
							msgTip("操作提示", "库存不能为空 ！");
							return false;
						}
						
						totalCount = parseInt(modifyNum);
					} else if ("2" == stockType) {
						// 不需要库存
						totalCount = 0;
					}
					
					// 组装数据
					var actDateDtoList = new Array();
					for (var i = 0; i < actDateList.length; i++) {
						var actDateDto = {
								'actDate' : actDateList[i],
								'count': totalCount
						};
						actDateDtoList.push(actDateDto);
					}
					
					var data = {
							'productId': productId,
							'showStock' : $("input[name='show_stock']:checked").val(),
							'stockType' : stockType,
							'totalCount' : totalCount,
							'originalCount' : totalCount, 
							'actDateList' : actDateDtoList,
							'actSpecList' : actSpecList,
							'specInfo': '' + JSON.stringify(specInfo) + '',
							'priceList': priceList,
							'vipPriceList': vipPriceList,
					};
					
					$('#saveStockInfo').prop("disabled", true);
					$.ajax({
						url : urlPrefix + 'activity/stock/updateStock',
						data : {
							'data' :JSON.stringify(data),
						},
						type : 'POST',
						dataType :'json',
						success : function(result) {
							if (result.code == 'ACK') {
								$(window).loadingInfo("success", "操作成功");
								$('#stockDialog').modal('hide');
	                        	getStockList();
	                        	that.updatePartakeInfoDeal();
							} else {
								$(window).loadingInfo("error", result.message);
							}
						},
						complete :function(){
							$('#saveStockInfo').prop("disabled", false);
						}
					});
				});
			},
			
			purchaseInfoDeal: function() {

				// 购买人数
				$(".partakeInfo").on('input propertychange', "input[type='text']", function(){
					var num = checkInteger($(this));
					if (num == 0) {
						$(this).val(1);
					} else {
						$(this).val(num);
					}
				});
				
				// 减
				$(".deadlineInfo .fa-minus").click(function(){
					var dayNum = parseInt($(this).parent().next().val());
					if (dayNum > 0) {
						$(this).parent().next().val(dayNum - 1);
					}
				});
				
				// 加
				$(".deadlineInfo .fa-plus").click(function(){
					var dayNum = parseInt($(this).parent().prev().val());
					$(this).parent().prev().val(dayNum + 1);
				});
				
				// 探测事件
				$(".sale_time input[name='sale_type']").click(function(){
					if ($(this).val() == '0') {
						$('.sale_time .dateDiv, .sale_time .dateEnd_tip').css("display", "none"); 
					} else {
						$('.sale_time .dateDiv').css("display", "inline-block");
						$('.sale_time .dateEnd_tip').css("display", "inline"); 
					}
				});
				
				// 定时开售-开始日期
				$('#sale_start').on('changeDate', function(ev){
					// 将结束日期的起始日期设置为开始日期的值
					if (ev.date) {
						if (dateFormat(ev.date) >= dateFormat(new Date())) {
							var actDateList = getStockDateList();
							if (actDateList.length > 0 && $('#sale_start').val() > actDateList[0]) {
								msgTip("操作提示", '定时开售的时间段必须包含商品的活动日期时间段！');
								$('#sale_start').val('');
							} else {
								$('#sale_end').datetimepicker('setStartDate', $('#sale_start').val());
							}
						}
					} else {
						$('#sale_end').datetimepicker('setStartDate', dateFormat(new Date()));
					}
					if ($('#sale_start').val() == ""){
						$("#sale_start").next().css('display','none');
					} else{
						$("#sale_start").next().css('display','inline-block');
					}
				});
	
				//定时开售-结束日期
				$('#sale_end').on('changeDate', function(ev){
					// 如果先选择了结束日期，则将开始日期的截止日期设为结束日期的值
					if (ev.date) {
						if (dateFormat(ev.date) >= dateFormat(new Date())) {
							var actDateList = getStockDateList();
							if (actDateList.length > 0 && $('#sale_end').val() < actDateList[actDateList.length - 1]) {
								msgTip("操作提示", '定时开售的时间段必须包含商品的活动日期时间段！');
								$('#sale_end').val('');
							} else {
								$('#sale_start').datetimepicker('setEndDate', $('#sale_end').val());
							}
						}
					} else {
						$('#sale_start').datetimepicker('setEndDate', '');
					}
					if ($('#sale_end').val() == ""){
						$("#sale_end").next().css('display','none');
					} else {
						$("#sale_end").next().css('display','inline-block');
					}
				});
				
			},
			
			updatePartakeInfoDeal: function() {
				// 参与人数
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
			
			savePurchaseInfoDeal: function() {
				var that = this;
				$('#savePurchaseInfo').click(function(){
					if (productId == undefined || productId == '' || productId == null) {
						msgTip("操作提示", "获取不到商品编号 ！");
						return false;
					}
					
					var saleType = $("input[name='sale_type']:checked").val();
					var data = {
							'productId': productId,
							'limitNum' : 0,
							'saleType' : saleType,
							'buyDeadline' : $(".deadlineInfo input").val()
					};
					if ("1" == saleType) {
						var startDate = $.trim($('#sale_start').val());
						if ('' == startDate) {
							msgTip('操作提示', '定时开售开始时间不能为空！');
							return false;
						} else {
							data.startDate = startDate;
						}
						//结束时间可以为空
						data.endDate = $.trim($('#sale_end').val());
						if (data.endDate) {
							if (startDate > data.endDate) {
								msgTip('操作提示', '定时开售开始时间不能晚于结束时间！');
								return false;
							}
						}
					}
					
					var reqFields = new Array();
					$('.requireInfo div input:checked').each(function(){
						reqFields.push($(this).val());
					});
					if (0 == reqFields.length) {
						msgTip('操作提示', '必填信息不能为空！');
						return false;
					}
					if ($.inArray('姓名', reqFields) == -1) {
						$('.requireInfo div input').eq(0).prop('checked', true);
						msgTip('操作提示', '姓名是必选项！');
						return false;
					}
					data.requireFields = reqFields.join('&');
					
					var partakeInfoEle = $(".partakeInfo tbody").children();
					if (partakeInfoEle.length > 0) {
						var partakeInfo = new Array();
						$(partakeInfoEle).each(function(){
							var partake = {
									'subSpec' : $(this).find('.sub-spec').text(),
									'unitNum' : parseInt($(this).find("input[type='text']").val()),
									'enabled' : $(this).find("input[type='checkbox']").prop('checked')
							}
							partakeInfo.push(partake);
						});
						data.partakeInfo = ''+ JSON.stringify(partakeInfo) +'';
					}
					
					// 发送请求
					$('#savePurchaseInfo').prop("disabled", true);
					$.ajax({
						url : urlPrefix + 'activity/stock/updatePurchaseInfo',
						data : JSON.stringify(data),
						type :'POST',
						dataType : 'json',
						contentType : 'application/json;charset=utf-8',
						success : function(result){
							if (result.code == 'ACK') {
								$(window).loadingInfo("success", "操作成功");
							} else {
								$(window).loadingInfo("error", result.message);
							}
						},
						complete : function(){
							$('#savePurchaseInfo').prop("disabled", false);
						}
					});
				});
			},
			
			initPurchaseInfoDeal : function () {
				$('.sale_time .dateDiv, .sale_time .dateEnd_tip').css("display", "none");
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
									$('.deadlineInfo input').val(purchaseInfo.buyDeadline);
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
								
								that.updatePartakeInfoDeal();
								
								if ('1' == purchaseInfo.saleType) {
									$("input[name='sale_type']").eq(0).prop('checked', false);
									$("input[name='sale_type']").eq(1).prop('checked', true);
									if (purchaseInfo.startDate){
										$('#sale_start').val(purchaseInfo.startDate);
										$("#sale_start").next().css('display','inline-block');
									}	
									if (purchaseInfo.endDate){
										$('#sale_end').val(purchaseInfo.endDate);
										$("#sale_end").next().css('display','inline-block');
									}
									
									// 显示时间选择内容
									$('.sale_time .dateDiv').css("display", "inline-block");
									$('.sale_time .dateEnd_tip').css("display", "inline"); 
								} 
							}
						} 
					}
				});
			},
			
			clearContentDeal : function() {
				// 活动日期
				$('#addActDate').next().empty();
				$('#addActDate').css("display", "inline");
				$('#addMainSpec').prop('disabled', false);
				
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
			
			initInfoDeal: function(type, actDate) {
				this.clearContentDeal();
				if (type == "edit") {
					// 活动日期
					$('#addActDate').next().html('<li>' + actDate + "</li>");
					$('#addActDate').css("display", "none");
				} 
				
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
									if (data.originalCount) {
										$('#modify_num').val(data.originalCount);
									}
									$('#modify_num').css('display', 'inline-block');
								} 
								$("input[name='stockType']").eq(index).prop('checked', true);
								$('.stockInfo .price_area .limit').prop('disabled', true);
							} else {
								$('.stockInfo .price_area .limit').prop('disabled', false);
							}
							
							// 是否显示库存
							if (data.showStock == '0' || data.showStock == '1') {
								var index = parseInt(data.showStock);
								$("input[name='show_stock']").eq(0).prop('checked', false);
								$("input[name='show_stock']").eq(index).prop('checked', true);
							}
							$("input[name='stockType'],input[name='show_stock']").prop("disabled", true);
						} else {
							$("input[name='stockType'],input[name='show_stock']").prop("disabled", false);
						}
					}
				
				});
			},
	}

	//初始化
	actStockInstance = actStock.init();
	// 获取购买信息
	actStockInstance.initPurchaseInfoDeal();
	
	// 注册字符串截取方法
	$(template.helper('subStr', function(date) {
		return date.substring(0, 10);
	}));
	
	// 获取库存列表
	function getStockList() {
		$.ajax({
			url : urlPrefix + "activity/stock/list/" + productId,
			type : 'GET',
			dataType : 'JSON',
			success : function(result){
				if (result.code == 'ACK') {
					if (result.data) {
						var showType = result.data[0].showStock;
						if (showType == '1') {
							$(".j-prd .maintitle input[name='view_stock']").eq(0).prop("checked", false);
							$(".j-prd .maintitle input[name='view_stock']").eq(1).prop("checked", true);
						} else {
							$(".j-prd .maintitle input[name='view_stock']").eq(0).prop("checked", true);
							$(".j-prd .maintitle input[name='view_stock']").eq(1).prop("checked", false);;
						}
						$('.j-prd .stock_details').html($(template('viewStockListTpl', {'actStockList': result.data})));
					} 
				} else {
					$('.j-prd .stock_details').empty();
				}
			}
		});
	}
	
	getStockList();
	
    // 删除
	$('.j-prd').on('click', ".j-mbtn", function(){
        var dot = $(".dot");
        if (dot.length == 0){
			msgTip("操作提示","请勾选需要删除的活动日期！");
        } else{
            $(".m-tip").show();
        }
    });
	
	$(".j-delete").click(function(){
		var dot = $(".dot");
        $(".m-tip").hide();
        var date = [];
        for(var i = 0;i < dot.length;i++){
            var dateData = dot.eq(i).parents(".m-box").find(".title label").text();
            date.push(dateData);
        }
        $.ajax({
            type:"POST",
            url: urlPrefix +  "activity/stock/delete/" + productId,
            dataType:"json",
            data:{
                actDateList:date
            },
            success:function(msg){
                if (msg.code == "ACK"){
                	getStockList();
					$(".u-qx").find("label").removeClass("u-dot");
					$(window).loadingInfo("success", "操作成功");
                } 
            }
        });
    });
	$(".j-cancel").click(function(){
        $(".m-tip").hide();
    });
	
    // 添加活动日期
	$('.j-prd').on('click', ".j-add", function(){
    	actStockInstance.initInfoDeal('add');
    	$('#stockDialog').modal('show');
    });
    
    // 编辑库存
	$('.j-prd').on('click', ".pull-right", function(){
    	var actDate = $(this).parent().children('label').text();
    	actStockInstance.initInfoDeal('edit', actDate);
    	$('#stockDialog').modal('show');
    });
    
    // 修改库存显示方式
	$('.j-prd').on('click', ".alter_show", function() {
    	var showType = $("input[name='view_stock']:checked").val();
    	var content = $.trim($('.stock_details').html());
    	if (content) {
    		$.ajax({
        		url :  urlPrefix + "activity/stock/isShowStock/" + productId,
        		type : 'POST',
        		data : {
        			'showStock': showType
        		},
        		dataType : 'json',
        		success : function(result) {
        			if (result.code == 'ACK'){
    					$(window).loadingInfo("success", "操作成功");
        			}
        		}
        	});
    	} else {
    		msgTip("操作提示", "当前没有库存信息！");
    	}
    });

	//实时监听关闭模态框事件
	setInterval(function(){
		var modal = $(".modal:visible");
		if(modal.length >= 1){
			$("body").addClass("modal-open");
		}else{
			$("body").removeClass("modal-open");
		}
	},100);
	
});
