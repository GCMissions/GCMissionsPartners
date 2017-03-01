$(function() {
	var $inputTxt = $("#shipmentInput");// 扫码框
	var action = "barcode/checkUrl";// 验证扫码信息
	var orderId = $("#orderId").attr("value");// 获取订单号
	var orderStatus = $("#orderStatus").attr("value");
	var submitAction = "barcode/shipment"; // 确认发货提交
	var deleteAction = "barcode/checkCode";// 删除扫码

	/**
	 * 事件绑定
	 */
	var bindEvent = function() {
		var _self = this;
		var prefixCodeArr = [];//所有已扫码明码
		var packCodeArr =[]; //所有已扫码明码、母码集合
		var newUrls;// 对应物料的所有扫码号
		var goodArr = [];

		$('.submitOk').on('click', function() {
			$("#shipmentInput").focus();
		});

		$("#shipmentInput").on("keyup", function(e) {
			var idx = $inputTxt.index(this);
			// 回车键自动换行
			if (this.value) {
				var urls = {
					url : this.value,
					orderId : orderId,
					status : orderStatus
				}
				if (e.keyCode == "13") {
					e.preventDefault();
					// 单个扫描完直接请求后台去做验证
						doValidate(urls, idx);
						$($inputTxt).val("");
				}
			}
		});

		var doValidate = function(urls, idx) {
			$.ajax({
				type : 'POST',
				url : urlPrefix + action,
				dataType : 'json',
				contentType : 'application/json',
				data : JSON.stringify(urls)
			}).done(function(result) {
				if (result.code == "ACK") {
					var nowGoodId = result.data.goodId;
					var nowSelectNum = result.data.goodSelectNum;
					var nowGoodName = result.data.goodName;
					var nowPrefixCode = result.data.prefixCode;
					var nowCodeUrl = result.data.codeUrl;
					var nowPackCode = result.data.packCode;//如果扫描的是母码，则packCode有值，如果扫描的是子码，则packCode没值
					var countRepNum = 0;
					var countCodeName ="";
					for (var c = 0; c < nowPrefixCode.length; c++) {
						var nowCode = nowPrefixCode[c];
						if ($.inArray(nowCode, prefixCodeArr) != -1) {
							countRepNum +=1;
							countCodeName +=nowCode+",";
						}
					}
					if(countRepNum == 6){
						$('body').loadingInfo("error","重复扫码!");
						$($inputTxt).val("");
						$($inputTxt).focus();
						return;
					}else if(countRepNum >0 && countRepNum <6){
						var msg = "重复扫码,"+countCodeName.substring(0,countCodeName.length-1)+"已存在!"
						$('body').loadingInfo("error",msg);
						$($inputTxt).val("");
						$($inputTxt).focus();
						return;
					}
					var classNum = parseInt($("#num_ok" + nowGoodId).text());//页面已扫码数量
					var totalNum = parseInt($("#data_" + nowGoodId).text());//页面需要扫码总数
					if (classNum + nowSelectNum <= totalNum) {
						if (nowSelectNum == 6) {
							$('body').loadingInfo("success",message("扫码成功,该二维码为母码,总数+6"));
						} else {
							$('body').loadingInfo("success",message("扫码成功!"));
						}
						classNum = parseInt($("#num_ok" + nowGoodId).text())+ nowSelectNum;
						$("#num_ok" + nowGoodId).text(classNum);
						for(var i=0;i<nowPrefixCode.length;i++){
							prefixCodeArr.push(nowPrefixCode[i]);
						}
						if(nowPackCode !=null && nowPackCode != ""){
							packCodeArr.push(nowPackCode);
						}
						var goodData = $('#data' + nowGoodId).val();// 获取该物料的url
						newUrls = goodData + nowPrefixCode + ",";// 加入新扫描的url
						$('#data' + nowGoodId).attr('value', newUrls);// 重新赋值给该物料的value
						var list_code = newUrls.split(',');
						var a = "";
						for (var i = 0; i < list_code.length - 1; i++) {
							a += '<li class="one_code">'+ list_code[i]+ '<a class="delCode"><i class="fa fa-remove"></i></a></li>';
						}
						$('#myModal' + nowGoodId + ' #codeList' + nowGoodId).html(a);
						del(list_code);
					} else {
						if(classNum == totalNum){
							$('body').loadingInfo("error",message("扫码失败,物料为"+ nowGoodName+ "的订单数量已满足!"));
						}else{
							$('body').loadingInfo("error",message("扫码失败,物料为"+ nowGoodName+ "的订单数量已超出所需要的数量!"));
						}
						
						$($inputTxt).val("");
					}
				} else if (result.code == "NACK") {
					$($inputTxt).val("");
				}
				}).fail(function(result) {
					$('body').loadingInfo("error",message("admin.message.error"));
				})
				var del = function(list_code){
				// 删除扫码
				$('.delCode').on('click',function() {
						var pId = $(this).parent().parent().siblings().val();
						var thsCode = $(this).parent().text();// 获取当前删除扫码的内容
						var new_list_code = [];
						for (var i = 0; i < list_code.length; i++) {
							if (list_code[i] != thsCode) {// 如果删除的扫码与遍历出来的扫码内容不符，则加入新的字符串
								new_list_code.push(list_code[i]);
							}
						}
						list_code = new_list_code;
						$('#data' + pId).val(new_list_code);
						for ( var a in prefixCodeArr) {
							if (prefixCodeArr[a] == thsCode) {
								prefixCodeArr.splice(a, 1)
							}
						}
						var code_num;
						$('#product_list tr').each(function() {
							code_num = $('#num_ok'+ pId).text();
						});
						var data = {
							"prefixCode" : thsCode
						};
						$.ajax({
							type : 'POST',
							url : urlPrefix + deleteAction,
							dataType : 'json',
							contentType : 'application/json',
							data : JSON.stringify(data),
							success : function(result) {
								var delNum = code_num - parseInt(result.data);
								$('#num_ok'+ pId).text(delNum);
							}
						});
						$(this).parent().remove();
				});
				
			}
		};
		
		var currentIframe = window.parent.document.getElementsByName(window.name);
		var backUrl = currentIframe[0].src;
		
		// 确认发货
		$('#save1').on('click',function() {
					
					var saveGoods = function() {
						var numCounts1 = 0;
						$('#product_list tr').each(
								function() {
									var id_good = $(this).data('id');
									var num_good = $(this).find(
											'#data_' + id_good).text();
									numCounts1 = numCounts1 + parseInt(num_good);
									var list_good = {};
									list_good.goodId = id_good;
									list_good.goodNum = num_good;
									goodArr.push(list_good);
								});
						var data = {
							"source" : 0,
							"orderId" : orderId,
							"goodNum" : numCounts1,
							"prefixCodes" : prefixCodeArr,
							"status" : orderStatus,
							"goodDtos" : goodArr,
							"packCodes":packCodeArr
						}
						$.ajax({
							url : urlPrefix + submitAction,
							type : "POST",
							data : JSON.stringify(data),
							dataType : "json",
							contentType : "application/json",
							success : function(result) {
								if (result.code == "ACK") {
									$('body').loadingInfo("success",
											message(result.message));
									setTimeout(function(){
										currentIframe[0].contentWindow.location.href = backUrl;
									},3000);
								}
							}
						});// AJAX结束
					}

					BootstrapDialog.show({
						title : "提示",
						type : BootstrapDialog.TYPE_WARNING,
						message : message("是否确认发货？"),
						draggable : true,
						size : BootstrapDialog.SIZE_SMALL,
						buttons : [ {
							label : '确认',
							cssClass : 'btn-primary',
							action : function(dialog) {
								dialog.close();
								saveGoods();
							}
						}, {
							label : '取消',
							action : function(dialog) {
								dialog.close();
							}
						} ]
					});

				});
		// 确认收货
		$('#save2').on('click',function() {

					var saveGoods = function() {
						var numCounts = 0;
						$('#product_list tr').each(
								function() {
									var id_good = $(this).data('id');
									var num_good = $(this).find(
											'#data_' + id_good).text();
									numCounts = numCounts + parseInt(num_good);
									var list_good = {};
									list_good.goodId = id_good;
									list_good.goodNum = num_good;
									goodArr.push(list_good);
								});
						var data = {
							"source" : 0,
							"orderId" : orderId,
							"goodNum" : numCounts,
							"prefixCodes" : prefixCodeArr,
							"status" : orderStatus,
							"goodDtos" : goodArr,
							"packCodes":packCodeArr
						}
						$.ajax({
							url : urlPrefix + submitAction,
							type : "POST",
							data : JSON.stringify(data),
							dataType : "json",
							contentType : "application/json",
							success : function(result) {
								if (result.code == "ACK") {
									$('body').loadingInfo("success",
											message(result.message));
									setTimeout(function(){
										currentIframe[0].contentWindow.location.href = backUrl;
									},3000);
								}
							}
						});// AJAX结束
					}

					BootstrapDialog.show({
						title : "提示",
						type : BootstrapDialog.TYPE_WARNING,
						message : message("是否确认收货？"),
						draggable : true,
						size : BootstrapDialog.SIZE_SMALL,
						buttons : [ {
							label : '确认',
							cssClass : 'btn-primary',
							action : function(dialog) {
								dialog.close();
								saveGoods();
							}
						}, {
							label : '取消',
							action : function(dialog) {
								dialog.close();
							}
						} ]
					});

				});
		// 确认退货
		$("#returnConfirm").on('click',function() {
			
			var saveGoods = function() {
				var numCounts2 = 0;
				$('#product_list tr').each(
						function() {
							var id_good = $(this).data('id');
							var num_good = $(this).find(
									'#data_' + id_good).text();
							numCounts2 = numCounts2 + parseInt(num_good);
							var list_good = {};
							list_good.goodId = id_good;
							list_good.goodNum = num_good;
							goodArr.push(list_good);
						});
				var data = {
					"source" : 1,
					"orderId" : orderId,
					"goodNum" : numCounts2,
					"prefixCodes" : prefixCodeArr,
					"status" : orderStatus,
					"goodDtos" : goodArr,
					"packCodes":packCodeArr
				}
				$.ajax({
					url : urlPrefix + submitAction,
					type : "POST",
					data : JSON.stringify(data),
					dataType : "json",
					contentType : "application/json",
					success : function(result) {
						if (result.code == "ACK") {
							$('body').loadingInfo("success",
									message(result.message));
							setTimeout(function(){
								currentIframe[0].contentWindow.location.href = backUrl;
							},3000);
						}
					}
				});// AJAX结束
			}

			BootstrapDialog.show({
				title : "提示",
				type : BootstrapDialog.TYPE_WARNING,
				message : message("是否确认发货？"),
				draggable : true,
				size : BootstrapDialog.SIZE_SMALL,
				buttons : [ {
					label : '确认',
					cssClass : 'btn-primary',
					action : function(dialog) {
						dialog.close();
						saveGoods();
					}
				}, {
					label : '取消',
					action : function(dialog) {
						dialog.close();
					}
				} ]
			});

		});
	}

	/**
	 * 页面初始化
	 */
	var init = function() {
		$("#shipmentInput").focus();
		bindEvent();
	}
	init();

});
