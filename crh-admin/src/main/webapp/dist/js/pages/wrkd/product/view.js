$(function(){
	
	var productId = $("#mainForm").attr("productId");
	var oper = $("#mainForm").attr("oper");
	
	if ("edit" == oper) {
		$.ajax({
			url : urlPrefix + "coolbag/product/sale/checkStatus",
			type : 'GET',
			data : {
				productId : productId
			},
			dataType : 'json',
			success : function(result){
				if ("ACK" == result.code) {
					if (result.data) {
						$(window).loadingInfo("error", "该商品已上架，不可编辑！");
						setTimeout("window.location.href='" + urlPrefix + "coolbag/product/'", 1000);
					}
				} 
			}
		});
	}
	
	// 显示规格
	function initSpecInfo(productId) {
		if (!productId) {
			return;
		}
		$.ajax({
			url : urlPrefix + "coolbag/product/findSpecInfo",
			type : 'POST',
			data : {
				productId : productId
			},
			dataType : 'json',
			success : function(result){
				if ("ACK" == result.code && result.data) {
					$("#specArea").append($(template("mainSpecInitTpl", {dataList : result.data})));
					initStockSpec(result.data);
					initPriceStock(productId, oper);
				} 
			}
		});
	}
	
	// 显示库存区的规格信息
	function initStockSpec(specInfo) {
		if (!specInfo) {
			return;
		}
		var mainSpecLen = specInfo.length;
		//  初始化
		var subSpecList = new Array();
		for (var i = 0; i < 4; i++) {
			var subSpecs = new Array();
			subSpecList.push(subSpecs);
		}
		
		var inputCount = 1;
		for (var i = 0; i < mainSpecLen; i++) {
			var subSpecs = specInfo[i].subSpecs;
			for (var j = 0; j < subSpecs.length; j++) {
				subSpecList[i].push(subSpecs[j]);
			}
			if (0 < i) {
				inputCount = inputCount * subSpecs.length;
			}
		}
		// 价格、库存输入框
		var inputCounts = new Array();
		for (var k = 0; k < inputCount; k++) {
			inputCounts.push(k);
		}
		
		var data = {
				'firstList' : subSpecList[0],
				'secondList': subSpecList[1],
				'thirdList' : subSpecList[2],
				'fourthList' : subSpecList[3],
				'inputList' : inputCounts
			};
		
		$('#stockInfo').append($(template('tableInfo', data)));
	}
	
	//　获取规格组合对应的输入框（价格/库存/限购）的序号
	function getSpecGroupMap() {
		var specGroupMap = new Array(), 
			mBoxs = $('.m-box'), 
			mBoxLen = mBoxs.length,
			baseSeqNum = 1,
			lastMainSubSpecLen = 0;
		
		for (var i = 1; i < mBoxLen; i++) {
			baseSeqNum *= mBoxs.eq(i).find("ul li").length;
			if (i == mBoxLen - 1) {
				lastMainSubSpecLen = mBoxs.eq(i).find("ul li").length;
			}
		}
		
		$("#stockInfo tr").each(function(index, element){
			var lastUls = $(this).find(".spf .last-ul");
			for (var i = 0, len = lastUls.length; i < len; i++) {
				var lastUl;
				var lastSpan = lastUls.eq(i).find('span');
				for (var j = 0; j < lastSpan.length; j++) {
					// 每次初始化
					lastUl = lastUls.eq(i);
					var stockDetail = {};
					var specGroup = $.trim(lastSpan.eq(j).text());
					for (var k = 1; k < mBoxLen; k++) {
						specGroup = $.trim(lastUl.prev().text()) + '-' + specGroup;
						lastUl= lastUl.parent().parent();
					}
					var seqNum = index * baseSeqNum + lastMainSubSpecLen * i + j;
					specGroupMap[specGroup] = seqNum;
				}
			}
		});
		
		return specGroupMap;
	}
	
	// 价格和库存
	function initPriceStock(productId, oper){
		if (!productId) {
			return;
		}
		$.ajax({
			url : urlPrefix + "coolbag/product/findPriceDetail",
			type : "POST",
			data : {
				productId : productId
			},
			dataType : "json",
			success : function(result) {
				if ("ACK" == result.code && result.data) {
					var prices = $("#stockInfo tr .u-price input");
					var stocks = $("#stockInfo tr .u-stock input");
					var limits = $("#stockInfo tr .u-limit input");
					var vipPrices = $("#stockInfo tr .u-vip-price input");
					var seqNum, specGroupMap = getSpecGroupMap();
					for (var i = 0; i < result.data.length; i++) {
						seqNum = specGroupMap[result.data[i].specGroup];
						prices.eq(seqNum).val(result.data[i].price/100);
						stocks.eq(seqNum).val(result.data[i].restAmount);
						limits.eq(seqNum).val(result.data[i].limitAmount);
						vipPrices.eq(seqNum).val(result.data[i].vipPrice/100);
					}
					var stockType =$("input[name='stockType']:checked").val();
					// 库存类型初始化
					stockTypeListener(stockType, oper);
				}
			}
		});
	}
	
	// 规格库存初始化
	function stockTypeListener(stockType, oper){
		if ("view" == oper) {
			$("#stockInfo .cent input").prop("disabled", true);
			return;
		} else {
			var vipFlag = $('#mainForm').attr('vip-flag');
			if ('0' == vipFlag) {
				$("#stockInfo .u-vip-price input").prop("disabled", true);
			} else {
				$("#stockInfo .u-vip-price input").prop("disabled", false);
			}
		}
		// 编辑情况下
		if ("0" == stockType){
			$("#limitAmount").val("").prop("disabled", true);
			$("#originAmount").val("").prop("disabled", true);
			$("#stockInfo .u-stock input").prop("disabled", false);
			$("#stockInfo .u-limit input").prop("disabled", false);
		} else {
			$("#limitAmount").prop("disabled", false);
			$("#originAmount").prop("disabled", false);
			$("#stockInfo .u-stock input").val("").prop("disabled", true);
			$("#stockInfo .u-limit input").val("").prop("disabled", true);
		}
	}
	
	initSpecInfo(productId);
	
})