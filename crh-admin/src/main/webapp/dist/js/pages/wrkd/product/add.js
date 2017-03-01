$(function(){
	// 自然数
	var pattern = /^([1-9][0-9]{0,}|0)$/;
	// 自然数或两位小数
	var	patternDouble =  /^([1-9]\d{0,}|0)([.]\d{0,2})?$/;
	
	// 全角转半角
	function converter(num) {
		if (num == '') {
			return false;
		}
		return num.replace(/[０１２３４５６７８９　．]/g, function(v){if(v=='．'){return '.';}else{return v.charCodeAt(0)-65296;}});
	}
	
	// 元转分
    function toInteger(floatNum) {
        if (floatNum == Math.floor(floatNum)) {
            return floatNum * 100;
        }
        var times  = Math.pow(10, 2);
        var intNum = parseInt(floatNum * times + 0.5, 10);
        
        return intNum;
    }
    
    // 校验整型数值
	function checkInteger(elem) {
		var aa = $.trim($(elem).val());
		var num_1 = converter(aa);
		if(num_1 == ""){
			return 0;
		}
		if (!pattern.test(num_1)){
			msgTip('操作提示', '格式错误，请输入非负的正整数！');
			return 0;
		}
		
		if (num_1.length > 5) {
			msgTip('操作提示', "库存最大不超过99999！");
			return 0;
		} 
		
		return num_1;
	}
	
	// 校验小数数值
	function checkDecimal(elem) {
		var aa = $.trim($(elem).val());
		var price = converter(aa);
		if (price == ""){
			return 0;
		}
		if (false == patternDouble.test(price)){
			msgTip('操作提示', '格式错误，请输入非负的正整数或者保留两位的小数！');
			return 0;
		}
		
		if (price > 99999) {
			msgTip('操作提示', "金额最大不可超过99999 !");
			return 0;
		} 
		
		return price;
	}
	
    // 通用消息提示
	function msgTip(title, msg) {
		var data = {
				'title':title,
				'msg':msg,
		}
		$('#alertMsgDialog').html($(template('alertMsgModalTpl', data)));
		$('#alertMsgDialog').modal('show');
	}
	
	//-----------商品信息JS----------------------------------------------------------------------------------------------
    var productId = $("#mainForm").attr("productId");
    // 操作（edit/add)
	var oper = $("#mainForm").attr("oper");
	
    //　返回按钮控制
    $(".back-away").on("click", function(){
    	if ("add" == oper) {
    		var tab = $(this).parents().find(".tab-pane.active");
    		if ("tab_1" == tab.attr("id")){
    			window.location.href = urlPrefix + 'coolbag/product/';    			
    		} else {
    			tab = $(tab).prev().attr("id");
    			$("#mytabs li a[href='#"+ tab + "']").tab('show');
    		}
    	} else {
    		window.location.href = urlPrefix + 'coolbag/product/';
    	}
    });
    
    // 控制价格输入
	$('#mainForm').on('change', '#originalPrice, #defaultFreight', function(e) {
		var price = checkDecimal($(this));
		if (price > 0) {
			$(this).val(price);
		} else {
			$(this).val('');
		}
	});
    
	// 初始化加载区域运费地址
	function getAddress(oper){
		$.ajax({
			url : urlPrefix + 'region/findAllProvince',
			type : 'GET',
			dataType : 'json',
			success : function(result) {
				if (result.code === 'ACK') {
					var data = result.data;
					// 将已添加的地区移除
					if ("edit" == oper) {
						data = removeRegion(data);
					}
					var htmls;
					for (var i = 0, len = data.length; i < len; i++){
						htmls = '<li region-id="'+ data[i].id +'"><input type="checkbox" />'+ data[i].name +'</li>';
						$('.j-xlist').append(htmls);
					}
				} else{
					$(window).loadingInfo("error", result.message);
				}
			}
		});
	}
	
	getAddress(oper);
	
	// 将已添加的地区移除
	function removeRegion(regionAll){
		var regions = regionAll;
		$('#region-table').find("tbody tr").each(function(){
			var _this = this;
			$(this).find(".region-tr span").each(function(){
				var regionId = $(this).attr("region-id");
				for (var i = 0, len = regionAll.length; i < len; i++) {
					if (regions[i].id == regionId) {
						regions.splice(i, 1);
						break;
					}
				}
			});
		});
		return regions;
	}
	
	// 表格删除操作【区域运费展示】
	$('#region-table').on('click','.j-delete',function(){
		var spans = $(this).parents('tr').find('.lft span');
		for (i = 0; i < spans.length; i++ ){ 
			var span = spans.eq(i);
			$('.j-xlist').append("<li region-id='"+ span.attr("region-id") +"'><input type='checkbox' />"+ span.text() +"</li>"); 
		} 
		$(this).parents('tr').remove();
	});
	
	// 区域运费 设置
	$('.j-freight').click(function(){
		// 将之前的选择清空
		rightToLeft('li');
		$('#regionalFreight').modal('show');
	});
	
	// 运费设置点击li选中效果【设置区域运费弹框】
	$('.j-list').on('click','li',function(){
		var _this = $(this);
		var isCheck = _this.find('input[type=checkbox]').prop('checked');
		if (isCheck) {
			_this.find('input[type=checkbox]').prop('checked',false);
			_this.removeClass('ck');
		} else {
			_this.find('input[type=checkbox]').prop('checked',true);
			_this.addClass('ck');
		}
	});
	
	// 运费设置点击复选框选中效果【设置区域运费弹框】
	$('.j-list').on('click','li input[type=checkbox]',function(){
		var _this = $(this);
		var isCheck = _this.prop('checked');
		if (isCheck) {
			_this.prop('checked',false);
			_this.parent('li').removeClass('ck');
		} else {
			_this.prop('checked',true);
			_this.parent('li').addClass('ck');
		}
	});
	
	// 点击添加【设置区域运费弹框】
	$('.j-add').click(function(){
		leftToRight('.ck');
	});
	
	// 点击全部添加【设置区域运费弹框】
	$('.j-alladd').click(function(){
		leftToRight('li');
	});

	// 点击删除【设置区域运费弹框】
	$('.j-remove').click(function(){
		rightToLeft('.ck');
	});
	
	// 点击全部删除【设置区域运费弹框】
	$('.j-allremove').click(function(){
		rightToLeft('li');
	});
	
	// 把左边的地区部分或者全部移动到右边【设置区域运费弹框】
	function leftToRight(elem) {
		var regions = $('.j-xlist').find(elem);
		if (0 < regions.length) {
			regions.clone().appendTo($(".j-ylist"));
			regions.remove();
			$('.j-ylist').find('li').removeClass('ck').find('input[type=checkbox]').prop('checked',false);
		}
	}
	
	// 将右边的地区部分或者全部移动到左边【设置区域运费弹框】
	function rightToLeft(elem){
		var regions = $('.j-ylist').find(elem);
		if (regions && 0 < regions.length) {
			// 将右边的元素添加到左边
			regions.clone().appendTo($('.j-xlist'));
			regions.remove();
			$('.j-xlist').find('li').removeClass('ck').find('input[type=checkbox]').prop('checked',false);
		}
	}
	
	// 区域运费设置保存【设置区域运费弹框】
	$('#saveRegionalFreight').click(function(){
		if (!$("#regionalFreight .msg-warning").hasClass("hidden")) {
			$("#regionalFreight .msg-warning").addClass("hidden");
		}
		var findLi = $('.j-ylist').find('li');
		if (0 == findLi.length) {
			$("#regionalFreight .msg-warning span").text("请添加区域 !").parent().removeClass("hidden");
			return;
		}
		var yfVal = $.trim($('.j-yunfei').val());
		if ('' == yfVal) {
			$("#regionalFreight .msg-warning span").text("请输入价格 !").parent().removeClass("hidden");
			return;
		} 
		// 格式验证
		var reg = new RegExp(patternDouble);
		if (false == reg.test(yfVal)) {
			$("#regionalFreight .msg-warning span").text("价格输入非法 !").parent().removeClass("hidden");
			return;
		}
		
		// 大小验证
		if (yfVal > 99999) {
			$("#regionalFreight .msg-warning span").text("金额最大不可超过99999 !").parent().removeClass("hidden");
			return;
		}
		
		var addrHtml = new Array();
		for (var i = 0; i < findLi.length; i++) {
			addrHtml.push("<span region-id='" + findLi.eq(i).attr("region-id") + "' >"+ findLi.eq(i).text() + "</span>") ;
			if (i != findLi.length - 1) {
				addrHtml.push('、');
			}
		}
		var trHtml = "<tr>";
		    trHtml += "<td class='lft region-tr'>" + addrHtml.join("") + "</td>";
		    trHtml += "<td class='cent' width='20%'>" + yfVal + "</td>";
		    trHtml += "<td class='rit' width='20%'><span class='j-delete'>删除</span></td>";
		    trHtml += "</tr>";
		$('#region-table').find('tbody').append(trHtml);  
		$('#regionalFreight').modal('hide');
		$('.j-yunfei').val('');
		$('.j-ylist').empty();
	});
	
	// 获取运费信息
	function getFreightInfo() {
		var freightInfo = new Array();
		$('#region-table').find("tbody tr").each(function(){
			var _this = this;
			$(this).find(".region-tr span").each(function(){
				var freightElement = {
						regionId : $(this).attr("region-id"),
						regionName : $(this).text(),
						price : toInteger($.trim($(_this).find(".cent").text()))
				};
				freightInfo.push(freightElement);
			});
		});
		return freightInfo;
	}
	
	// 添加图片
	imageOperInit($("#mainForm"));
	$(".addNewPic").on('click', function(){
		addNewPic(20, $("#mainForm"));
	});
	
	// 保存商品基本信息
	$("#saveBaseInfo").on('click', function() {
		var specialNote = $("#specialNote").val();
		specialNote = specialNote.split("\n").join("<br />");//特别说明

		var pname = $.trim($("#productName").val());
		if (!pname) {
			msgTip("操作提示", "商品名称不能为空！");
			return;
		}
		var quickDesc = $.trim($("#quickDesc").val());
		if (!quickDesc) {
			msgTip("操作提示", "商品描述不能为空！");
			return;
		}
		var isShow = "0";
		if ($("#isShow").is(":checked") == true) {
			isShow = "1";
		} 
		
		var vipFlag = $("input[name='isVip']:checked").val();
		var baseInfo = {
				id : productId,
				pname: $("#productName").val(),
				specialNote: specialNote,
				originPrice: toInteger($.trim($("#originalPrice").val())),
				quickDesc : $("#quickDesc").val(),
				isShow : isShow,
				vip : vipFlag,
		}
		//　促销活动
		var actTag = $("input[name='actTags']:checked").val();
		if ($.trim(actTag)){
			baseInfo.actTag = actTag;
		}
		
		var freightInfo = getFreightInfo();
		var defaultPrice = $.trim($("#defaultFreight").val());
		if (0 == freightInfo.length) {
			if (!defaultPrice) {
				msgTip("操作提示", "运费不能为空！");
				return;
			}
		} 
		if (!defaultPrice) {
			defaultPrice = 0;
		}
		var freightElement = {
				regionId : 100000,
				regionName : "其他",
				price : toInteger(defaultPrice)
		};
		freightInfo.push(freightElement);
		
		baseInfo.freightInfo = freightInfo;
		
		var images = getPicList($("#mainForm"));
		if (0 == images.length) {
			msgTip("操作提示", "请上传商品图片！");
			return;
		}
		baseInfo.listImages = images;
		
		$("#saveBaseInfo").prop("disabled", true);
		$.ajax({
			url : urlPrefix + 'coolbag/product/save',
			type : 'POST',
			data : JSON.stringify(baseInfo),
			dataType : 'json',
			contentType : "application/json",
			success : function(result) {
				if (result.code === 'ACK') {
					productId = result.data;
					$('#mytabs li a[href="#tab_2"]').tab('show');
					$('#mainForm').attr('vip-flag', vipFlag);
					// 控制vip价格输入框
					controlVipPriceInput();
				} else {
					$(window).loadingInfo("error", result.message);
				}
			},
			complete : function(){
				 $("#saveBaseInfo").prop("disabled", false);
			}
		});
	});
	
	//-----------库存与管理JS----------------------------------------------------------------------------------------------
	
	// 点击添加按钮事件
	$('body').on('click', '.j-seladd', function(){
		$(this).addClass('disnone').parent('.subSpec').find('.selSubSpec').removeClass('disnone');
		$(this).addClass('disnone').parent('.subSpec').find('.selSubSpec input').focus();
	});
	
	// 点击取消按钮事件
	$('body').on('click', '.j-selcancel', function(){
		$(this).parent().find("input").val("");
		$(this).parent().addClass('disnone').parent().find('a').removeClass('disnone');
	});
	
	// 控制vip输入框是否可以输入
	function controlVipPriceInput(){
		var flag = true;
		var vipFlag = $('#mainForm').attr('vip-flag');
		if ('1' == vipFlag) {
			flag = false;
		}
		$("#stockInfo .u-vip-price input").val("0").prop("disabled", flag);
	}
	
	// 遍历商品规格，添加到table中
	function refreshStockInfo(){
		$('.j-table').find('tbody').empty();
		var mBox = $('.m-box');
		
		//  初始化
		var subSpecList = new Array();
		for (var i = 0; i < 4; i++) {
			var subSpecs = new Array();
			subSpecList.push(subSpecs);
		}
		
		var inputCount = 1;
		for (var i = 0; i < mBox.length; i++) {
			var ulLi = mBox.eq(i).find('.subSpec ul li');
			for (var j = 0; j < ulLi.length; j++) {
				var litext = ulLi.eq(j).find('b').text();
				subSpecList[i].push(litext);
			}
			if (0 < i) {
				inputCount = inputCount * ulLi.length;
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
		controlVipPriceInput();
	}
	
	// 点击确定添加子规格事件
	$('body').on('click','.j-selsure',function(){
		var _this = $(this);
		var subSpecNew = $.trim(_this.prev().val());
		if (!subSpecNew) {
			msgTip('操作提示', '子规格不能为空！');
			return;
		}
		if (15 < subSpecNew.length) {
			msgTip('操作提示', '输入长度过长，长度限制为15个字符！');
			return;
		}
		var seqLi = _this.parents('.subSpec').find('.j-selcont li');
		for(var i = 0; i < seqLi.length; i++){
			var subSpec = seqLi.eq(i).find('b').html();
			if (subSpecNew == subSpec) {
				msgTip('操作提示', '同一主规格下的子规格不能重复！');
				return;
			} 
		}
		
		var str = '<li><b>' + subSpecNew + '</b><span>×</span></li>';
		_this.parents('.subSpec').find('.j-selcont').append(str);
		_this.parent().addClass('disnone').parent().find('a').removeClass('disnone')
		.end().find('.j-selcont').removeClass('disnone')
		.end().find('.selSubSpec input').val('');
		// 给table赋值
		refreshStockInfo();
	});
	
	// 点击取消添加子规格事件
	$('body').on('click','.j-selcont li span',function(){
		var _this = $(this);
		var _index = _this.parent('li').index();
		_this.parent('li').remove();
		$('.j-table').find('tbody tr').eq(_index).remove();
		refreshStockInfo();
	});
	
	// 点击添加主规格事件
	$('body').on('click','.j-seladdmainspf',function(){
		$(this).addClass('disnone').parent().find('.selMainSpec').removeClass('disnone');
		$(this).addClass('disnone').parent().find('.selMainSpec input').focus();
	});
	
	// 点击添加主规格事件-关闭
	$('body').on('click','.j-selmainspfcancel',function(){
		$(this).parent().find("input").val('');
		$(this).parent().addClass('disnone').parent().find('.j-seladdmainspf').removeClass('disnone');
	});
	
	// 点击添加主规格事件-确定
	$('body').on('click','.j-selmainspfsure',function(){
		var mainSpecNew = $.trim($('.selMainSpec').find('input').val());
		if (!mainSpecNew){
			msgTip('操作提示', '主规格不能为空！');
			return;
		}
		if (15 < mainSpecNew.length) {
			msgTip('操作提示', '输入长度过长，长度限制为15个字符！');
			return;
		}
		var mBox = $('.m-box');
		for (var i = 0; i < mBox.length; i++) {
			var mainSpec = mBox.eq(i).find('.u-inputwidth').val();
			if (mainSpecNew == mainSpec) {
				msgTip('操作提示', '主规格不能重复！');
				return;
			} 
		}
		
		$(this).parent().addClass('disnone').parent().find('.j-seladdmainspf').removeClass('disnone');
		$('.j-box').append($(template("mainSpecTpl", {mainSpec : mainSpecNew})));
		$('.selMainSpec').find('input').val('');
		// 校验主规格个数，小于4个放开添加主规格按钮
		if (3 <= mBox.length){
			$('.j-seladdmainspf').prop('disabled', true);
		} else {
			$('.j-seladdmainspf').prop('disabled', false);
		}
	});
	
	// 点击添加主规格事件-删除
	$('body').on('click','.j-seldelete',function(){
		$(this).parent('.m-box').remove();
		refreshStockInfo();
		// 校验主规格个数，小于4个放开添加主规格按钮
		var mBoxLen = $('.m-box').length;
		if (3 < mBoxLen) {
			$('.j-seladdmainspf').prop('disabled', true);
		} else {
			$('.j-seladdmainspf').prop('disabled', false);
		}
	});
	
	// 选择库存类型
	$("input[name='stockType']").on("click", function(){
		stockTypeListener($(this).val());
	});
	
	// 库存类型初始化
	stockTypeListener($("input[name='stockType']:checked").val());
	
	// 库存类型监听
	function stockTypeListener(stockType){
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
	
	// 保存或去除原值
	$('#stockInfo').on('focusin focusout', '.u-price input, .u-vip-price input', function(e){
		if (e.type == 'focusout') {
			$(this).removeAttr('pre-value');
		} else {
			$(this).attr('pre-value', $(this).val());
		}
	});
	
	// 控制规格价格输入
	$('#stockInfo').on('change', '.u-price input, .u-vip-price input', function(){
		var price = checkDecimal($(this));
		if (0 == price) {
//			$(this).val($(this).attr('pre-value'));
			return false;
		}
		var index = $(this).index();
		if ($(this).parent().hasClass('u-price')) {
			var price_2 = parseFloat($(this).closest('td').next().find('input').eq(index).val());
			if (price <= price_2) {
				price = $(this).attr('pre-value');
				msgTip('操作提示', 'VIP价格应小于原价格！');
			}
		} else {
			var price_2 = parseFloat($(this).closest('td').prev().find('input').eq(index).val());
			if (price_2 > 0) {
				if (price >= price_2){
					price = $(this).attr('pre-value');
					msgTip('操作提示', 'VIP价格应小于原价格！');
				} 
			} else {
				price = 0;
				msgTip('操作提示', '请先设定原价格！');
			}
		}
		
		$(this).val(price);
	});
	
	// 控制规格库存和限购输入
	$('#stockInfo').on('change', '.u-stock input, .u-limit input', function(){
		var num = checkInteger($(this));
		if (num > 0) {
			$(this).val(num);
		} else {
			$(this).val('');
		}
	});
	
	// 控制总库存和限购输入
	$('#tab_2').on('change', '#limitAmount, #originAmount', function(){
		var num_1 = checkInteger($(this));
		if (0 == num_1){
			$(this).val("");
			return false;
		}
		
		var limitAmount = $.trim($("#limitAmount").val());
		var originAmount = $.trim($("#originAmount").val());
		if (limitAmount && originAmount && parseInt(originAmount) < parseInt(limitAmount)){
			$(this).val("");
			msgTip('操作提示', "限购数量超过了库存总量，请重新输入！");
		} else {
			$(this).val(num_1);
		}
	});
	
	// 检查主规格下是否有子规格
	function checkSpecInfo() {
		var boxs = $("#specArea").find(".m-box");
		for (var i = 0; i < boxs.length; i++) {
			if (0 == $(boxs).eq(i).find('.subSpec ul li').length){
				return true;
			}
		}
		return false;
	}
	
	// 获取规格信息
	function getSpecInfo() {
		var specInfo = new Array();
		$("#specArea").find(".m-box").each(function(){
			var specElem = {
					mainSpec : $.trim($(this).find(".mainSpec").val())
			}
			var subSpecs = new Array();
			$(this).find('.subSpec ul li b').each(function(){
				subSpecs.push($.trim($(this).text()));
			})
			specElem.subSpecs = subSpecs;
			specInfo.push(specElem);
		});
		return specInfo;
	}
	
	// 检查价格、库存是否符合要求，以及求出价格列表、总库存
	function checkStockInfo() {
		var result = {
				price : false,
				priceList : new Array(),
				vipPriceList : new Array(),
				stock : false,
				totalAmount: 0,
				limit : false,
				limitList : new Array(),
				stockType : $("input[name='stockType']:checked").val()
		};
		
		var prices = $("#stockInfo tr .u-price input");
		var vipPrices = $("#stockInfo tr .u-vip-price input");

		var stocks = $("#stockInfo tr .u-stock input");
		var limits = $("#stockInfo tr .u-limit input");
		
		var price, stock, limit, vipPrice;
		for (var i = 0; i < prices.length; i++) {
			price = toInteger($.trim(prices.eq(i).val()));
			vipPrice = toInteger($.trim(vipPrices.eq(i).val()));
			if (!price) {
				result.price = true;
				return result;
			}
			if ("0" == result.stockType) {
				stock = $.trim(stocks.eq(i).val());
				if (!stock) {
					result.stock = true;
					return result;
				}
				result.totalAmount = result.totalAmount + parseInt(stock);
			}
			
			limit = $.trim(limits.eq(i).val());
			result.priceList.push(price);
			result.vipPriceList.push(vipPrice);
			result.limitList.push(limit);
		}
		
		if ("1" == result.stockType) {
			var originAmount = $.trim($("#originAmount").val());
			if (originAmount){
				result.stock = false;
				result.totalAmount = parseInt(originAmount);
			} else {
				result.stock = true;
			}
		}
		return result;
	}
	
	//　获取库存
	function getStockInfo() {
		var stockDetails = new Array(), mBoxLen = $('.m-box').length,
			lastMainSubSpecLen = $('.m-box').eq(mBoxLen-1).find("ul li").length;
		$("#stockInfo tr").each(function(index, element){
			var lastUls = $(this).find(".spf .last-ul");
			for (var i = 0; i < lastUls.length; i++) {
				var lastUl;
				var lastSpan = lastUls.eq(i).find('span');
				var priceInputs = $(element).find(".u-price input");
				var stockInputs = $(element).find(".u-stock input");
				var limitInputs = $(element).find(".u-limit input");
				var vipPriceInputs = $(element).find(".u-vip-price input");
				for (var j = 0; j < lastSpan.length; j++) {
					// 每次初始化
					lastUl = lastUls.eq(i);
					var stockDetail = {};
					var specGroup = $.trim(lastSpan.eq(j).text());
					for (var k = 1; k < mBoxLen; k++) {
						specGroup = $.trim(lastUl.prev().text()) + '-' + specGroup;
						lastUl= lastUl.parent().parent();
					}
					stockDetail.specGroup = specGroup;
					
					var seqNum = lastMainSubSpecLen * i + j;
					//　价格
					stockDetail.price = toInteger($.trim(priceInputs.eq(seqNum).val()));
					// vip价格
					stockDetail.vipPrice = toInteger($.trim(vipPriceInputs.eq(seqNum).val()));
					// 库存
					stockDetail.originAmount = $.trim(stockInputs.eq(seqNum).val());
					// 限购
					stockDetail.limitAmount = $.trim(limitInputs.eq(seqNum).val());
					
					stockDetails.push(stockDetail);
				}
			}
		});
		
		return stockDetails;
	}
	
	$("#saveStockInfo").on('click', function() {
		if (!productId) {
			msgTip("操作提示", "获取不到商品信息 ！");
			return;
		}
		if (checkSpecInfo()) {
			msgTip("操作提示", "主规格下子规格不能为空 ！");
			return;
		}
		var result = checkStockInfo();
		if (result.price) {
			msgTip("操作提示", "请填写正确的价格 ！");
			return;
		}
		if (result.stock || 0 == result.totalAmount) {
			msgTip("操作提示", "库存不能为空 ！");
			return;
		}
		
		var data = {
				productId : productId,
				stockType: result.stockType,
				originAmount : result.totalAmount,
				restAmount : result.totalAmount,
				limitAmount : $.trim($("#limitAmount").val()),
				specInfo : JSON.stringify(getSpecInfo()),
				priceList : result.priceList,
				vipPriceList : result.vipPriceList,
				stockDetails : getStockInfo(),
		}
		
		$("#saveStockInfo").prop("disabled", true);
		$.ajax({
			url : urlPrefix + 'coolbag/stock/save',
			type : 'POST',
			data : JSON.stringify(data),
			dataType : 'json',
			contentType : "application/json",
			success : function(result) {
				if (result.code === 'ACK') {
					$('#mytabs li a[href="#tab_3"]').tab('show');
				} else {
					$(window).loadingInfo("error", result.message);
				}
			},
			complete : function(){
				 $("#saveStockInfo").prop("disabled", false);
			}
		});
		
	})
	
	// 保存商品详情
	$("#saveDetailDesc").on("click", function(){
		var formData = $('#detailForm').frmSerialize();
    	var description= _.isUndefined(formData['description']) ? "" : formData['description'];
    	if ("" != description) {
    		$("#saveDetailDesc").prop("disabled", true);
    		$.ajax({
    			url : urlPrefix + "coolbag/product/saveDetail",
    			type : 'POST',
    			data : JSON.stringify({
    				id : productId,
    				detailDesc : description
    			}),
    			dataType : 'json',
    			contentType : "application/json",
    			success : function(result) {
    				if ("ACK" == result.code){
    					$(window).loadingInfo("success", "操作成功");
    					window.location.href = urlPrefix + "coolbag/product/";
    				} else {
    					$(window).loadingInfo("error", result.message);
    				}
    			},
    			complete : function() {
    				$("#saveDetailDesc").prop("disabled", false);
    			}
    		});
    	} else {
			window.location.href = urlPrefix + "coolbag/product/";
    	}
	})
});