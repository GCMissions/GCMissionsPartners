$(function(){
	var that = this;
	var mainSpecs = [];
	var prices = [];
	var limits = [];
	
	//过滤html转义字符
	function HTMLDecode(text){
	    var temp = document.createElement("div");
	    temp.innerHTML = text;
	    var output = temp.innerText || temp.textContent;
	    temp = null;
	    return output;
	}
	
	function getProductInfo(){
		var teamBuyId = $("#teamBuyId").val();
		var isReview = $("#isReview").val();
		if (isReview != "0") {
			$.ajax({
				url: urlPrefix+"groupPurchase/getSpecInfo",
				type:"POST",
				dataType: 'json',
				data:{"teamBuyId":teamBuyId },
				success:function(msg){
					if (msg.code == "ACK") {
						var spec = msg.data;
						var pid= spec[0].productId;
						var pname = spec[0].productName;
						var pcode =spec[0].productCode;
						if (isReview == "1") {
							var pro ='<tr><td>'+pcode+'</td><td>'+pname+'</td></tr>';
						} else {
							var pro ='<tr><td>'+pcode+'</td><td>'+pname+'</td><td><a href="javascript:void(0)" proId="'+pid+'" class="j-delpro">删除</a></td></tr>';
						}
						$(".m-pro").find("tbody").html(pro);
						$.ajax({
							url:urlPrefix+"coolbag/product/findSpecInfo",
							type:"POST",
							dataType: 'json',
							data:{"productId":pid },
							success:function(msg){
								if(msg.code ==="ACK"){
									var data = msg.data;
									var tableTh = '',tableTd='',specTd='';
									mainSpecs = [];
									for (var k = 0; k < spec.length; k++) {
										var specInfoName = "";
										specTd += "<tr>";
					            		if (spec[k].specInfo != null) {
					            			for(var i=0;i<data.length;i++){
					            				var specs = JSON.parse(HTMLDecode(spec[k].specInfo));
					            				specTd+="<td>" + specs[data[i].mainSpec] + "</td>";
					            				specInfoName+=specs[data[i].mainSpec]+"-";
					            			}
					            			specInfoName=specInfoName.substring(0,specInfoName.length-1);
					            			choosedSpec.push(specInfoName);
					            			if (isReview == "1") {
					            				specTd += "<td>" + spec[k].realPrice + "</td><td>" + spec[k].price + "</td><td>" + spec[k].limitCount + "</td>";
					            			} else {
					            				specTd += "<td>" + spec[k].realPrice + "</td><td>" + spec[k].price + "</td><td>" + spec[k].limitCount + "</td>"
						            			+ "<td><a href='javascript:void(0)' class='btn btn-default j-reSpecItem'>删除</a></td>";
					            				prices.push(spec[k].price);
					            				limits.push(spec[k].limitCount);
					            			}
					            		}
					            		specTd += "</tr>";
									}
						            for(var i=0;i<data.length;i++){
						            	mainSpecs.push(data[i].mainSpec);
						            	tableTh+='<th>'+data[i].mainSpec+'</th>';
						            	if (isReview != "1") {
						            		var option ='<option>请选择</option>';
							            	for(var j=0;j<data[i].subSpecs.length;j++){
							            		option+='<option>'+data[i].subSpecs[j]+'</option>';
							            	}
							            	if (specTd == "<tr></tr>") {
							            		tableTd+='<td><select class="form-control j-specselc" disabled="disabled" >'+option+'</select></td>';
							            	} else {
							            		tableTd+='<td><select class="form-control j-specselc">'+option+'</select></td>';	
							            	}
										}
						            }
						            if (isReview == "1") {
						            	var specHtml='<label class="checkbox-inline" style="padding-left: 0;"></label>'
							                +'<div class="m-choosegg clearfix"><table id="specTable"><thead><tr>'+tableTh+'<th>价格</th><th>活动价</th><th>限购数量</th></tr></thead>'
							                	+'<tbody>' + specTd + '</tbody></table></div><p id="proChooseTip"></p>';
						            } else {
						            	var checkHtml = "";
						            	var inputHtml = "";
						            	if (specTd == "<tr></tr>") {
						            		checkHtml = '<input type="checkbox" class="j-isCheck" />';
						            		inputHtml = '<td><input type="text" class="ac-p-input form-control" value="0" disabled="disabled" /> 元</td>'
						                    	+'<td><input type="text" class="ac-l-input form-control" placeholder="0表示不限购" disabled="disabled" /></td>'
						                   		+'<td><a href="javascript:void(0)" class="btn btn-default" disabled="disabled" >添 加</a></td>';
						            	} else {
						            		checkHtml = '<input type="checkbox" class="j-isCheck" checked="checked" />';
						            		inputHtml = '<td><input type="text" class="ac-p-input form-control" value="0" /> 元</td>'
						                    	+'<td><input type="text" class="ac-l-input form-control" placeholder="0表示不限购" /></td>'
						                   		+'<td><a href="javascript:void(0)" class="btn btn-default j-add">添 加</a></td>';
						            	}
						            	var specHtml='<label class="checkbox-inline" style="padding-left: 0;">' + checkHtml + '选择规格</label>'
							                +'<div class="m-choosegg clearfix"><table id="specTable"><thead><tr>'+tableTh+'<th>价格</th><th>活动价</th><th>限购数量</th><th></th></tr></thead>'
							                	+'<tbody>' + specTd + '<tr id="opraTr">'+tableTd
							                    	+'<td class="priceTd"></td>'
							                    	+ inputHtml
							                +'</tr></tbody></table></div><p id="proChooseTip"></p>';
						            }
						            $("#specChooseBox").html(specHtml);
						            $('#chooseProduct').modal('hide');
									$(".j-addpro").hide();$(".addpro").show();
									
									//获取各规格价格信息
									$.ajax({
										url:urlPrefix+"coolbag/product/findPriceDetail",
										type:"POST",
										dataType: 'json',
										data:{"productId":pid },
										success:function(msg){
											if(msg.code ==="ACK"){
												specPriceInfo = msg.data;//各种规格的价格信息
											}
										}
									});
								}
							}
						});
					}
				}
			});
		}
	};
	getProductInfo();
	
	//开始日期
	// 获取当前时间
	function getCurTimeStr() {
		var curDate = new Date();
    	var month = curDate.getMonth() + 1 < 10 ? "0" + curDate.getMonth() + 1 : curDate.getMonth() + 1;
    	var date = curDate.getDate() < 10 ? "0" + curDate.getDate() : curDate.getDate();
    	var hour = curDate.getHours() < 10 ? "0" + curDate.getHours() : curDate.getHours();
    	var minutes = curDate.getMinutes() < 10 ? "0" + curDate.getMinutes() : curDate.getMinutes();
    	
    	return curDate.getFullYear() + "-" + month + "-" + date + " " + hour + ":" + minutes;
	}

	// 初始化
	$('#startDate, #endDate').datetimepicker({
		startDate: new Date(),
		minView: 'hour',
		format: 'yyyy-MM-dd hh:ii',
		language: 'ch',
		autoclose : true,
		todayBtn : true,
	});
	
	// 开始时间
	$('#startDate').on('changeDate', function(ev){
		if (ev.date){
			$('#endDate').datetimepicker('setStartDate', $('#startDate').val());
		} else {
			$("#endDate").datetimepicker('setStartDate', getCurTimeStr());
		}
		if(!ev.date){
			$("#startDate").next().css('display','none');
		}else{
			$("#startDate").next().css('display','inline-block');
		}
	});
	
	// 结束时间
	$('#endDate').on('changeDate', function(ev){
		// 如果先选择了结束日期，则将开始日期的截止日期设为结束日期的值
		if (ev.date) {
			$('#startDate').datetimepicker('setEndDate', $('#endDate').val());
		} else {
			$('#startDate').datetimepicker('setEndDate', '');
		}
		if (!ev.date){
			$("#endDate").next().css('display','none');
		} else {
			$("#endDate").next().css('display','inline-block');
		}
	});
	
	function checkTime(mins){
		if (mins<10){
			mins = "0" + mins;
		}
		return mins;
	};
	
	//添加活动商品
	$(".j-addpro").click(function(){
		$('#chooseProduct').modal('show');
		$(".procode").val("");
		$(".proname").val("");
		getPrdList.bootTable.refresh();
	});
	//radio选择商品
	$(".j-prosList").on("click",".j-prochoose",function(){
		$(".j-prosList").find("tr").removeClass("j-curpro");
		$(this).parents("tr").addClass("j-curpro");
	});
	var specPriceInfo;
	$("#confirmPrd").click(function(){
		var curPro = $(".j-curpro");
		if(curPro.length > 0){
			var pid= curPro.find(".j-prochoose").attr("proId");
			var pname = curPro.find(".u-pname").text();
			var pcode =curPro.find(".u-pcode").text();
			var pro ='<tr><td>'+pcode+'</td><td>'+pname+'</td><td><a href="javascript:void(0)" proId="'+pid+'" class="j-delpro">删除</a></td></tr>';
			$(".m-pro").find("tbody").html(pro);
			$.ajax({
				url:urlPrefix+"coolbag/product/findSpecInfo",
				type:"POST",
				dataType: 'json',
				data:{"productId":pid },
				success:function(msg){
					if(msg.code ==="ACK"){
						var data = msg.data;
						var tableTh = '',tableTd='';
						mainSpecs = [];
			            for(var i=0;i<data.length;i++){
			            	mainSpecs.push(data[i].mainSpec);
			            	tableTh+='<th>'+data[i].mainSpec+'</th>';
			            	var option ='<option>请选择</option>';
			            	for(var j=0;j<data[i].subSpecs.length;j++){
			            		option+='<option>'+data[i].subSpecs[j]+'</option>';
			            	}
			            	tableTd+='<td><select class="form-control j-specselc" disabled="disabled">'+option+'</select></td>';
			            }
			            var specHtml='<label class="checkbox-inline" style="padding-left: 0;"><input type="checkbox" class="j-isCheck" />选择规格</label>'
			                +'<div class="m-choosegg clearfix"><table id="specTable"><thead><tr>'+tableTh+'<th>价格</th><th>活动价</th><th>限购数量</th><th></th></tr></thead>'
			                	+'<tbody><tr id="opraTr">'+tableTd
			                    	+'<td class="priceTd"></td>'
			                    	+'<td><input type="text" class="ac-p-input form-control" value="0" disabled="disabled" /> 元</td>'
			                    	+'<td><input type="text" class="ac-l-input form-control" placeholder="0表示不限购" disabled="disabled" /></td>'
			                   		+'<td><a href="javascript:void(0)" class="btn btn-default" disabled="disabled" >添 加</a></td>'
			                +'</tr></tbody></table></div><p id="proChooseTip"></p>';
			            $("#specChooseBox").html(specHtml);
			            $('#chooseProduct').modal('hide');
						$(".j-addpro").hide();$(".addpro").show();
						
						//获取各规格价格信息
						$.ajax({
							url:urlPrefix+"coolbag/product/findPriceDetail",
							type:"POST",
							dataType: 'json',
							data:{"productId":pid },
							success:function(msg){
								if(msg.code ==="ACK"){
									specPriceInfo = msg.data;//各种规格的价格信息
								}
							}
						});
					}
				}
			});
		}
	});
	
	$("#specChooseBox").on('click','.j-isCheck',function(){
		console.log($(this).is(':checked'));
		if ($(this).is(':checked')) {
			$("#specChooseBox").find("tbody").find("select").attr("disabled", false);
			$("#specChooseBox").find("tbody").find("input").attr("disabled", false);
			$("#specChooseBox").find("tbody").find("a").attr("disabled", false);
			$("#specChooseBox").find("tbody").find("a").addClass("j-add");
		} else {
			$("#specChooseBox").find("tbody").find("select").attr("disabled", true);
			$("#specChooseBox").find("tbody").find("input").attr("disabled", true);
			$("#specChooseBox").find("tbody").find("a").attr("disabled", true);
			$("#specChooseBox").find("tbody").find("a").removeClass("j-add");
		}
	});
	
	//切换规格选择显示价格
	var specName = "";
	$("#specChooseBox").on("change",".j-specselc",function(){
		specName = "";
		var specLength= $(".j-specselc").length;
		for(var k=0;k < specLength;k++){
			specName+= $(".j-specselc").eq(k).find("option:selected").text()+'-';
		}
		specName=specName.substring(0,specName.length-1);
		for(var a=0;a<specPriceInfo.length;a++){
			if(specName == specPriceInfo[a].specGroup){
				$(this).parents("tr").find(".priceTd").text((specPriceInfo[a].price / 100)+"元");
				$(this).parents("tr").find(".ac-p-input").attr("maxprice",specPriceInfo[a].price / 100);
			}
		}
	});
	//输入活动价不能大于商品原价
	$("#specChooseBox").on("keyup",".ac-p-input",function(){
		var V = $(".ac-p-input").val();
		var maxV = $(".ac-p-input").attr("maxprice");
		// 自然数或两位小数
		var patternDouble =  /^([1-9]\d{0,}|0)([.]\d{0,2})?$/;	
		if (!patternDouble.test(V)){
			$(this).val('0');
		}else if (parseFloat(V) > parseFloat(maxV)) {
			$(this).val('0');
		}
	});
	//删除商品
	$(".m-pro").on("click",".j-delpro",function(){
		$(".j-addpro").show();$(".addpro").hide();
		$(this).parents("tr").remove();
		$("#specChooseBox").html("");
		choosedSpec=[];//清空规格数组
		mainSpecs = [];
		prices = [];
		limits = [];
	});
	
	//添加规格组合
	var choosedSpec=[];
	$("#specChooseBox").on("click",".j-add",function(){
		if(!$(".j-isCheck").is(':checked')){
			$(window).loadingInfo("warn", "请勾选选择规格！");
			//$("#proChooseTip").text("请勾选选择规格！");
		}else if(specName === ""|| specName.indexOf("请选择") >= 0){//保证所有规格都已选择
			$(window).loadingInfo("warn", "请选择所有规格！");
			//$("#proChooseTip").text("请选择所有规格！");
		}else{//添加成功
			//$("#proChooseTip").text("");
			//判断是否添加过这个规格，如果添加过相同规格则return false
			for(var i=0;i<choosedSpec.length;i++){
				if(choosedSpec[i] == specName){
					$(window).loadingInfo("warn", "该规格已选择过！");
					return false;
				}
			}
			if (parseInt($(".ac-p-input").val()) > parseInt($(".priceTd").text())) {
				$(window).loadingInfo("warn", "团购价不能大于原价！");
				return false;
			}
			if ($.trim($(".ac-l-input").val()) == null || $.trim($(".ac-l-input").val()) == "") {
				$(window).loadingInfo("warn", "请输入每个规格的限购数量！");
				return false;
			}
			for(var k=0;k < specLength;k++){
				addHtml+= '<td>'+$(".j-specselc").eq(k).find("option:selected").text()+'</td>';
			}
			var specLength= $(".j-specselc").length;
			var addHtml='<tr text="'+specName+'">';
			for(var k=0;k < specLength;k++){
				addHtml+= '<td>'+$(".j-specselc").eq(k).find("option:selected").text()+'</td>';
			}
			addHtml+= '<td>'+$(".priceTd").text()+'</td><td>'+$(".ac-p-input").val()+' 元</td>';
			addHtml+= '<td>'+$(".ac-l-input").val()+'</td>';
			$("#opraTr").before(addHtml+"<td><a href='javascript:void(0)' class='btn btn-default j-reSpecItem'>删除</a></td></tr>");
			choosedSpec.push(specName);
			prices.push($(".ac-p-input").val());
			limits.push($.trim($(".ac-l-input").val()));
		}
	});
	//删除规格组合
	$("#specChooseBox").on("click",".j-reSpecItem",function(){
		$(this).parents("tr").remove();
		var index = $(this).parents("tr").index();
		choosedSpec.splice(index, 1);
		prices.splice(index, 1);
		limits.splice(index, 1);
	});
	Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
		if (this[i] == val) return i;
		}
		return -1;
	};
		//然后使用通过得到这个元素的索引，使用js数组自己固有的函数去删除这个元素：
		//代码为：

	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
		this.splice(index, 1);
		}
	};
	//获取添加商品列表，并分页显示，且可搜索查询
	var getPrdList = {
		$dataList : $('.j-prosList'),
		$searchItem : $('#j-selectpro'),
		bootTable : void 0,
		init : function() {
			this.bindEvents();
			this.bootTable = $.GLOBAL.utils.loadBootTable({
				table : this.$dataList,
				removeBtn : $('#removeRecord'),
				refreshBtn : $('#refreshRecord'),
				idField : "",
				url: 'groupPurchase/searchProduct',
				sidePagination:'server',
				pageSize : 10,
				pagination : true,
				currentPage: 1,
				queryParamsType: "limit",
				queryAddParams: function() {
					return {
						pname : '',
						pcode : ''
					}
				},
				columns: [
					{
						title:"序号",
						width: 50,
						align: 'center',
						formatter:function(value,row,index){
							return '<input type="radio" name="prd" class="j-prochoose" proId="'+row.id+'" />';
						}
					} ,
					{
						title:"商品编号",
						field: 'pcode',
						align: 'center',
						formatter:function(value,row,index){
							return '<span class="u-pcode">'+row.pcode+'</span>';
						}
					} ,
					{
						title:"商品名称",
						field: 'pname',
						align: 'center',
						formatter:function(value,row,index){
							return '<span class="u-pname">'+row.pname+'</span>';
						}
					}
				],
			});
		},
		bindEvents : function() {
			var that = this;
			that.$searchItem.on("click",function() {
				that.bootTable.options.queryAddParams = function(){
					return {
						pname : $(".proname").val(),
						pcode : $(".procode").val()
					}
				};
				that.bootTable.refresh();
			});
		}
	};
	
	var selProduct = getPrdList.init();
	// 添加图片
	imageOperInit($("#mainForm"));
	$(".addNewPic").on('click', function(){
		if (getAllPicCount($("#mainForm")) < 5) {
			addNewPic(20, $("#mainForm"));
		}
	});
	
	$(".rinput").on('change', '.upload-btn input[type="file"]', function(){
        ajaxFileUpload();
    });
	
	var ajaxFileUpload = function () {
		$('#desc_imageUrl').attr('src', uiBase + "/img/loading.gif");
		$.ajaxFileUpload({
			url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.kdProduct}),
			secureuri : false,
			fileElementId : "desc_imageUpload",
			dataType : 'json',
			global : false,
			data : {},
			success : function (data, status) {
				if (data.code == "ACK") {
					$('#desc_imageUrl').attr('src', data.data.url);   
				}else {
					$(window).loadingInfo("error", data.messge);
				}
			}
			
		});
		return false;  
	};
	
	$("#saveBaseInfo").click(function(){
		var groupBuyName = $.trim($("#productName").val());
		if (groupBuyName == null || groupBuyName == "") {
			$(window).loadingInfo("warn", "活动标题不能为空！");
			return;
		}
		var startDate = $.trim($("#startDate").val());
		var endDate = $.trim($("#endDate").val());
		if (!(startDate != "" && endDate != "")) {
			$(window).loadingInfo("warn", "请选择正确的开始时间和结束时间！");
			return;
		}
		if (startDate == endDate) {
			$(window).loadingInfo("warn", "开始时间和结束时间不能够完全一致！");
			return;
		}
		var groupBuyImage = $("#desc_imageUrl").attr("src");
		if (groupBuyImage == uiBase + "img/default_goods_image_240.gif" || groupBuyImage == uiBase + "/img/default_goods_image_240.gif") {
			$(window).loadingInfo("warn", "请上传活动说明图片！");
			return;
		}
		if (groupBuyImage == uiBase + "img/loading.gif" || groupBuyImage == uiBase + "/img/loading.gif") {
			$(window).loadingInfo("warn", "请等待图片上传！");
			return;
		}
		var specialDesc = $("#special_desc").val().split("\n").join("<br />");//特别说明
		var productId = $("#pro_list").find(".j-delpro").attr("proid");
		if (productId == null || productId == "") {
			$(window).loadingInfo("warn", "请选择商品！");
			return;
		}
		var specInfoArry = buildSpec();;
		var totalPrice = $.trim($("#price").val());
		var limitCount = $.trim($("#limitCount").val());
		if(!$(".j-isCheck").is(':checked')){
			if (totalPrice == null || totalPrice == "") {
				$(window).loadingInfo("warn", "请填写活动商品价格！");
				return;	
			}
			if (limitCount == null || limitCount == "") {
				$(window).loadingInfo("warn", "请填写团购商品限购数量！");
				return;	
			}
			limits = [];
			prices = [];
			specInfoArry = [];
		} else {
			if (prices.length > 0 && !(totalPrice == null || totalPrice == "")) {
				$(window).loadingInfo("warn", "活动价和活动商品价格只能选择一种填写！");
				return;
			}
			if (prices.length == 0) {
				$(window).loadingInfo("warn", "请填写各规格价格！");
				return;
			}
			if (limits.length > 0 && !(limitCount == null || limitCount == "")) {
				$(window).loadingInfo("warn", "各规格限购数量和团购商品限购数量只能选择一种填写！");
				return;
			}
			if (limits.length == 0) {
				$(window).loadingInfo("warn", "请填写各规格限购数量！");
				return;
			}
			totalPrice = "";
			limitCount = "";
		}
		var startCount = $.trim($("#startCount").val());
		if (startCount == null || startCount == "") {
			$(window).loadingInfo("warn", "成团商品数量不能为空！");
			return;
		}
		var images = getPicList($("#mainForm"));
		if (0 == images.length) {
			$(window).loadingInfo("warn", "请上传活动图片！");
			return;
		}
		if (3 > images.length) {
			$(window).loadingInfo("warn", "活动图片最少需要上传3张！");
			return;
		}
		if (5 < images.length) {
			$(window).loadingInfo("warn", "活动图片最多只能上传5张！");
			return;
		}
		var formData = $('#mainForm').frmSerialize();
    	var description= _.isUndefined(formData['description']) ? "" : formData['description'];
		if (description == null || description == "") {
			$(window).loadingInfo("warn", "请填写商品详情！");
			return;
		}
		if ($("#isReview").val() == "0") {
			$('#saveBaseInfo').prop("disabled",true).text("添加中...");
		} else {
			$('#saveBaseInfo').prop("disabled",true).text("编辑中...");
		}
		var teamBuyId = $("#teamBuyId").val();
		saveInfo(groupBuyName,startDate,endDate,groupBuyImage,specialDesc,productId,specInfoArry,prices,totalPrice,startCount,limitCount,images,description,teamBuyId,limits);
	});
	
	var saveInfo = function(groupBuyName,startDate,endDate,groupBuyImage,specialDesc,productId,specInfoArry,prices,totalPrice,startCount,limitCount,images,description,teamBuyId,limits){
		var operType = "1";
		if (teamBuyId == null || teamBuyId == "-1") {
			operType = "0";
		}
		$.ajax({ 
    		type         : 'post',
			url          : urlPrefix+"groupPurchase/saveAndUpdate",
			dataType     : 'json',
			contentType: "application/json;charset=utf-8",
			data: JSON.stringify({
				groupBuyId : teamBuyId,
				groupBuyName : groupBuyName,
				startDate : startDate,
				endDate : endDate,
				groupBuyImage : groupBuyImage,
				specialDesc : specialDesc,
				productId : productId,
				specInfoArry : specInfoArry,
				prices : prices,
				totalPrice : totalPrice,
				startCount : startCount,
				limitCount : limitCount,
				imageDtos : images,
				description : description,
				operType : operType,
				limits : limits
			}),
			success : function(msg){
				if (msg.code == "ACK") {
					$(window).loadingInfo({
	                    type : "ok",
	                    text : msg.message,
	                    callBack : function() {
	                    	$('#saveBaseInfo').prop("disabled",false).text("确 认");
	                    	window.location.href=urlPrefix+"groupPurchase/";
	                    }
	                });
				} else {
					$(window).loadingInfo("warn", msg.message);
					$('#saveBaseInfo').prop("disabled",false).text("确定");
				}
			}
		});
	};
	
	var buildSpec = function(){
		var specInfoArry = [];
		if (mainSpecs.length == 0) {
			$(window).loadingInfo("warn", "请添加商品！");
			return;
		}
		if (choosedSpec.length == 0) {
			return specInfoArry;
		}
		for(var i = 0; i < choosedSpec.length; i++) {
			var specInfo = '{"';
			var subSpec = choosedSpec[i].split("-");
			for (var j = 0; j < mainSpecs.length; j++) {
				if (j == mainSpecs.length - 1){
					specInfo += mainSpecs[j] + '":"' + subSpec[j];
				} else {
					specInfo += mainSpecs[j] + '":"' + subSpec[j] + '","';
				}
			}
			specInfo += '"}';
			specInfoArry.push(specInfo);
		}
		return specInfoArry;
	};
	
	$("#backToHome").click(function(){
		//window.location.href=urlPrefix+"groupPurchase/";
	});
});