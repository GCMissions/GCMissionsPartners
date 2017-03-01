    var numMax = 99999, numLen = 5, dateFlag = '至',
		// 自然数
		pattern = /^([1-9][0-9]{0,}|0)$/,
		// 自然数或两位小数
		patternDouble =  /^([1-9]\d{0,}|0)([.]\d{0,2})?$/;

	// 通用消息提示
	function msgTip(title, msg) {
		var data = {
				'title':title,
				'msg':msg,
		}
		$('#alertMsgDialog').html($(template('alertMsgModalTpl', data)));
		$('#alertMsgDialog').modal('show');
	}
	
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
    
	//过滤html转义字符
	function HTMLDecode(text){
	    var temp = document.createElement("div");
	    temp.innerHTML = text;
	    var output = temp.innerText || temp.textContent;
	    temp = null;
	    return output;
	}
	
	// 格式化时间
	function dateFormat(date){  
		var year = date.getFullYear();
		var month = (date.getMonth() + 1)  < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1 ;  
		var date = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();  
		return year + "-" + month + "-" + date;  
	}
	
	// 格式化时间
	function dateStrFormat(year, month, date){  
	    var month = month  < 10 ? "0" + month : month ;  
	    var date = date < 10 ? "0" + date : date;  
	    return year + "-" + month + "-" + date;  
	}
	
	// 时间段分隔
	function dateSplit(startDate, endDate){  
		var ONE_DAY = 24 * 60 * 60 * 1000;
	    var dateList = new Array();
	    
	    startDate = new Date(startDate);
	    endDate = new Date(endDate);
	    var spi = endDate.getTime() - startDate.getTime();  
	    var step = (spi / ONE_DAY);  
	    
	    for (var i = 0; i <= step; i++) { 
	    	var date = dateFormat(new Date(startDate.getTime() + ONE_DAY * i));
	        dateList.push(date);  
	    }  
	    return dateList;  
	}
	
	// 获取活动日期
	function getActDateList() {
		var actDateList = new Array();
		var len = $('#addActDate').next().children();
		if (len == 0) {
			return actDateList;
		}
		$('#addActDate').next().children().each(function(){
			var text = $(this).text().replace('×', '');
			if (text.indexOf(dateFlag) > -1) {
				var actDates = text.split(dateFlag);
				var dateList = dateSplit(actDates[0], actDates[1]);
				actDateList = actDateList.concat(dateList);
			} else {
				actDateList.push(text);
			}
		});
		// 根据日期从小到大排序
		actDateList = actDateList.sort(function(a, b) {
			if (a[0] > b[0]) {
				return 1;
			} if (a[0] == b[0]) {
				return 0;
			} 
			return -1;
		});
		
		return actDateList;
	}
	
	// 检查日期是否重复，交叉
	function pushActDateList(startDate, endDate) {
		var actDateList = getActDateList();
		if (actDateList.length == 0) {
			return true;
		}
		if ($.inArray(startDate, actDateList) > -1) {
			return false;
		}
		if (endDate && $.inArray(endDate, actDateList) > -1) {
			return false;
		}
		
		return true;
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
		
		if (num_1.length > numLen) {
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
	
	// 获取规格库存价格、限购等信息
	function getPriceDescs(index, specInfo, priceDescBase) {
		var priceDescs = new Array();
		for (var j = 0; j < specInfo[index].subSpecList.length; j++) {
			var	priceDesc = {
				'mainSpec': specInfo[index].mainSpec,
				'subSpec' : specInfo[index].subSpecList[j],
				'price': 0,
			}; 							
			if (index + 1 < specInfo.length) {
				getPriceDescs(index + 1, specInfo, priceDesc);
			}								
			priceDescs.push(priceDesc);
		}
		
		if (priceDescs.length > 0) {
			if (index == 0) {
				return priceDescs;
			} else {
				priceDescBase.price = priceDescs;
			}
		}
	}
	
	// vip标识-控制VIP价格字段是否可编辑
	function controlVipPriceInput(mode) {
		var vipFlag, status = false;
		if (mode) {
			vipFlag = $("#tab_1 input[name='isVip']:checked").val(); 
		} else {
			vipFlag = $("#mainForm input[name='vipFlag']").val(); 
		}
		if (vipFlag == '0') {
			$('.stockInfo .price_area .vip-price').val('0').prop('disabled', true);
		} else {
			status = true;
			$('.stockInfo .price_area .vip-price').prop('disabled', false);
		}
		
		return status;
	}
	
