// 
var bargainApp = {
    maxPics : 20,
    currentSeq : 0,
    $mainForm : void 0,
	$navTabs : void 0,
	dialog : void 0,
    validator : void 0,
    isCoverText : "[封面图片]",
    notCoverText : "[设为封面]",
    $picList : $('.goodspic-list'),
    coverElement : ".goodsCover",
    removeBtn : ".remove-btn",
    newPostUrl : "kdBargain/add",
    editPostUrl : "kdBargain/update",
    defaultImg   : uiBase + "img/default_goods_image_240.gif",
    selectedGoods : {},
    listUrl    : "kdBargain/",
    $choosedSpec :new Array(),
    $specName : '',
    init : function(id, imagesList) {
    	this.id = id; 
    	this.$navTabs = $('ul.nav-tabs');
        this.$mainForm = $('#mainForm');
        this.$goodspicList = $('.goodspic-list');
        this.defaultImageData = {showImageUrl : this.defaultImg, imageUrl : "", isCover : 0, CoverText : this.notCoverText, imageId: ""}; 
        this.initEvents();
        this.renderLists(imagesList);   
        //init sort
        $(".goodspic-list ul").sortable({connectWith:".connectList"}).disableSelection();
        
        // 预览商品
        if($("#isReview").val() == "1" || (typeof($("#tfId").val()) != "undefined" && $("#tfId").val() != "")) {
        	this.review24Th($("#tfId").val());
        }
        
        //初始化商品查询
        getPrdList.init();
        getTfList.init();
        
        return this;
        
    },
    review24Th : function(tfId){
    	var that = this;
		var pname = $("#pName").val();
		var pcode = $("#pCode").val();
		var pid = $("#productId").val();
		var productOnTime = $("#productOnTime").val();
		var productOffTime = $("#productOffTime").val();
		var proPriceRange = $("#proPriceRange").val();
		var pro = "";
		var isReview = "";
		if($("#isReview").val() == "1"){
			pro ='<tr><td>'+pcode+'</td><td>'+pname+'</td><td id="onTime">'+productOnTime+'</td><td id="offTime">'+productOffTime+'</td><td id="priceRange">'+proPriceRange+'</td></tr>';
			isReview = "1";
		}else{
			pro ='<tr><td>'+pcode+'</td><td>'+pname+'</td><td id="onTime">'+productOnTime+'</td><td id="offTime">'+productOffTime+'</td><td id="priceRange">'+proPriceRange+'</td><td><a href="javascript:void(0)" proId="'+pid+'" class="j-delpro">删除</a></td></tr>';
			isReview = "0";
		}
		$(".m-pro").find("tbody").html(pro);
		if($("#specType").val() != "0"){
			$.ajax({
				url:urlPrefix +"kdBargain/findSpecInfo",
				type:"POST",
				dataType: 'json',
				data:{"tfId":tfId,"isReview":isReview},
				success:function(msg){
					if(msg.code ==="ACK"){
						var data = msg.data;
			            $("#specChooseBox").html($(template('reviewProSpecTpl',data)));
		    			
			            $('#chooseProduct').modal('hide');
						$(".j-addpro").hide();$(".addpro").show();
						$('#selSpec').click();
						$("#specType").val('1');
						// 选中记录信息录入
		    			for(var i=0;i< data.kdBargainSpecDtos.length;i++){
		    				if(typeof(specInfoList) == "undefined"  || !$.isArray(specInfoList)){
			    				specInfoList = new Array();
			    			}
		    				var tempObj = data.kdBargainSpecDtos[i];
		    				tempObj.specInfo = that.htmlDecode(tempObj.specInfo);
			    			specInfoList.push(tempObj);
			    			that.$choosedSpec.push(tempObj.specGroup);
		    			}
		    			
		    			// 获取规格商品的价格信息
		    			that.findPriceDetail(pid);
					}
				}
			});
		}
    },
    //过滤html转义字符
    htmlDecode :function(text){
	    var temp = document.createElement("div");
	    temp.innerHTML = text;
	    var output = temp.innerText || temp.textContent;
	    temp = null;
	    return output;
	},
    convertToReviewPage : function() {
        var that = this;
        $(".mt20").off().remove();
        $('#copy-product').off().remove();
        $('.addNewPic').off().remove();
        that.$picList.find('ul').empty();
        _(imagesList).each(function(image, ik) {
            isCover = 0;
            
            var defaultData = $.extend({id: that.currentSeq}, that.defaultImageData);
            if(that.productImage && that.productImage == image.imageUrl) {
            	image.isCover = 1;
            	image.CoverText = that.isCoverText;
            }
            if(image.imageUrl) {
            	image.showImageUrl = image.imageUrl;
            }
            
            var $newPic = template('picShowItemTpl', $.extend(defaultData, image));
            
            
            that.$picList.find('ul').append($newPic);
            that.currentSeq ++ ;
        });
        $(".goodspic-list ul").sortable('destroy');
    },
    initEvents : function() {
        var that = this;
        //开始日期
    	var date = new Date();
    	var year = date.getFullYear();
    	var month = date.getMonth()+1;
    	var day = date.getDate();
    	var hours = that.checkTime(date.getHours());
    	var min = that.checkTime(date.getMinutes());

    	$('#startDate,#endDate').datetimepicker({
    		minView: 'hour',
    		format: 'yyyy-MM-dd hh:ii',
    		language: 'ch',
    		startDate: year+'-'+month+'-'+day + " "+hours + ":" + min,
    		autoclose : true,
    		todayBtn : true
    	});
    	//开始使用日期
		$('#startDate').on('changeDate',_(this.changeStartDate).bind($('#startDate')));
		
		//结束使用日期
		$('#endDate').on('changeDate',_(this.changeEndDate).bind($('#endDate')));		
		
		//提交数据
        $('button.submitMainForm').on("click", _(this.submitForm).bind(this));
        
        //图片操作
        $('.addNewPic').on('click', _(this.addNewPic).bind(this));
        
        //移除图片
        this.$mainForm.on('click', this.removeBtn, _(this.removePic).bind(this));
        
        ////添加活动商品
        $('.j-addpro').on('click', _(this.addAct).bind(this));
        
        $('.j-queryTf').on('click', _(this.queryTf).bind(this));
        
        // 选择商品
        $(".j-prosList").on("click",".j-prochoose",function(){
    		$(".j-prosList").find("tr").removeClass("j-curpro");
    		$(this).parents("tr").addClass("j-curpro");
    	});
        
        // 删除商品
        $(".m-pro").on("click",".j-delpro",function(){
    		$(".j-addpro").show();
    		$(".addpro").hide();
    		$("#queryTf").hide();
    		$(this).parents("tr").remove();
    		$("#specChooseBox").html("");
    		$("#productId").val("");
    		that.$choosedSpec=[];//清空规格数组
    		specInfoList=[];//清空规格数组
    		$("#specType").val('0');
    	});
        
        // 添加规格组合
    	$("#specChooseBox").on("click",".j-add",function(){
    		if(!$(".j-isCheck").is(':checked')){
    			that.msgTip('操作提示', '请勾选选择规格！');
    		}else if(that.$specName === ""|| that.$specName.indexOf("请选择") >= 0){
    			//保证所有规格都已选择
    			that.msgTip('操作提示', '请选择所有规格！');
    		}else{
    			//添加成功
    			//判断是否添加过这个规格，如果添加过相同规格则return false
    			for(var i=0;i<that.$choosedSpec.length;i++){
    				if(that.$choosedSpec[i] == that.$specName){
    					that.msgTip('操作提示', '该规格已选择过！');
    					return false;
    				}
    			}
    			// 商品规格价格
    			var curProPrice = $(this).parents("tr").find(".priceTd").text();
    			// 底价
    			var curBasePrice = $(".ac-p-input").val();
    			// 当前砍价方式
    			var curType = $(this).parents("tr").find(".bargaintype").attr('bargaintype');
    			// 最小金额
    			var bargainMinAmount =  $(this).parents("tr").find(".bargaintype").find('input[name="bargainMinAmount"]').val();
    			// 最大金额
    			var bargainMaxAmount =  $(this).parents("tr").find(".bargaintype").find('input[name="bargainMaxAmount"]').val();
    			// 固定砍价金额
    			var bargainAmount =  $(this).parents("tr").find(".bargaintype").find('input[name="bargainAmount"]').val();
    			
    			// 校验金额
    			var checkResult =  that.checkBargainPrice(curType,bargainMinAmount,bargainMaxAmount,bargainAmount,curProPrice,curBasePrice);
    			if(!checkResult){
    				return checkResult;
    			}
    			
    			// 添加规格信息
    			var specLength= $(".j-specselc").length;
    			var selDataObj = new Object();
    			selDataObj.specName = that.$specName;
    			var proSpecArray = new Array();
    			for(var k=0;k < specLength;k++){
    				proSpecArray.push($(".j-specselc").eq(k).find("option:selected").text());
    			}
    			selDataObj.proSpecArray = proSpecArray;
    			selDataObj.bargainMinAmount = bargainMinAmount;
    			selDataObj.bargainMaxAmount = bargainMaxAmount;
    			selDataObj.bargainAmount = bargainAmount;
    			selDataObj.proPrice = $(".priceTd").text();
    			selDataObj.basePrice = curBasePrice;
    			selDataObj.curType = curType;
    			$("#opraTr").before($(template('addProSpecTpl',selDataObj)));
    			that.$choosedSpec.push(that.$specName);
    			
    			// 添加规格商品底价信息
    			var proSpec = new Object();
    			var specInfo = new Array();
    			var curAllTdObj = $(".j-specselc");
    			for(var i=0;i< curAllTdObj.length;i++){
    				var curObj = curAllTdObj.eq(i);
    				// 主规格
    				var curMainSpec = curObj.attr("mainspec");
    				// 子规格
    				var curSubSpec = curObj.find("option:selected").text();
    				
    				var specElem = {
        					mainSpec : $.trim(curMainSpec)
        			}
        			var subSpecs = new Array();
    				subSpecs.push($.trim(curSubSpec));
        			specElem.subSpecs = subSpecs;
        			specInfo.push(specElem);
    			}
    			proSpec.specInfo = JSON.stringify(specInfo);
    			proSpec.productBasePrice = parseFloat(curBasePrice);
    			proSpec.productPrice = parseFloat(curProPrice);
    			proSpec.specGroup = that.$specName;
    			proSpec.bargainType = curType;
    			proSpec.bargainMinAmount = bargainMinAmount;
    			proSpec.bargainMaxAmount = bargainMaxAmount;
    			proSpec.bargainAmount = bargainAmount;
    			if(typeof(specInfoList) == "undefined"  || !$.isArray(specInfoList)){
    				specInfoList = new Array();
    			}
    			specInfoList.push(proSpec);
    		}
    	});
    	
    	// 选择规格商品按钮
    	$("#specChooseBox").on('change','.j-isCheck',function() {
    		if($('#selSpec').is(':checked')) {
    			$("#specType").val('1');
    			$("#fixed").addClass("hide");
            	$("#random").addClass("hide");
            	
            	$("#specChooseBox").find("tbody").find("select").attr("disabled", false);
    			$("#specChooseBox").find("tbody").find("input").attr("disabled", false);
    			$("#specChooseBox").find("tbody").find("a").attr("disabled", false);
    			$("#specChooseBox").find("tbody").find("a").addClass("j-add");
    			
    		}else{
    			$("#specChooseBox").find("tbody").find("select").attr("disabled", true);
    			$("#specChooseBox").find("tbody").find("input").attr("disabled", true);
    			$("#specChooseBox").find("tbody").find("a").attr("disabled", true);
    			$("#specChooseBox").find("tbody").find("a").removeClass("j-add");
    			
    			
    			$("#specType").val('0');
    			
        		var type = $('input[name="bargainType"]:checked').val();
                if (type==0) {
                	$("#random").removeClass("hide");
                	$("#fixed").addClass("hide");
                }else{
                	$("#random").addClass("hide");
                	$("#fixed").removeClass("hide");
                }
    		}
    	});
    	
    	//删除规格组合
    	$("#specChooseBox").on("click",".j-reSpecItem",function(){
    		$(this).parents("tr").remove();
    		var specGroup = $(this).parents("tr").attr("text");
    		that.$choosedSpec.remove(specGroup);//从数组中删除规格组合
    		
    		// 遍历商品规格信息
    		specInfoList = jQuery.grep(specInfoList,function(value,index) {
    			return value.specGroup != specGroup;
    		});
    	});
        
        // 切换规格选择显示价格
        $("#specChooseBox").on("change",".j-specselc",function(){
    		that.$specName = "";
    		var specLength= $(".j-specselc").length;
    		for(var k=0;k < specLength;k++){
    			that.$specName+= $(".j-specselc").eq(k).find("option:selected").text()+'-';
    		}
    		that.$specName=that.$specName.substring(0,that.$specName.length-1);
    		for(var a=0;a<specPriceInfo.length;a++){
    			if(that.$specName == specPriceInfo[a].specGroup){
    				$(this).parents("tr").find(".priceTd").text(specPriceInfo[a].priceYuan);
    			}
    		}
    	});
        
        // 选中商品确认
        $("#confirmPrd").on('click', _(this.confirmPrd).bind(this));
        
        //校验
        this.validator = $('#mainForm').validate({
        	rules : {
        		name : {
        			required : true,
        			maxlength:50
        		},
        		supportDesc : {
        			required : true,
        			maxlength:50
        		},
         		basePrice : {
        			max   : 999999
         		},
         		bargainMinAmount : {
        			minPrice : true,
        			max   : 999999
         		},
         		bargainMaxAmount : {
        			maxPrice : true,
        			max   : 999999,
//        			randomPrice : true
         		},
         		bargainAmount : {
        			price : true,
        			max   : 999999
         		},
        	},
        	messages:{
        		name : {
        			required : "活动名称不能为空"
        		},
        		supportDesc : {
        			required : "资助说明不能为空",
        			maxlength: "资助说明请勿大于50字符",
        		},
        		bargainMinAmount:{
					max:$.validator.format("最小金额不能大于999999")
				},
				bargainMaxAmount:{
					max:$.validator.format("最大金额不能大于999999")
				}
        	},
        	errorPlacement: function(error, element) {
				var fieldSet = element.closest("span.fieldSet"),
					bs       = element.closest('.bootstrap-select');
				
				if (fieldSet.size() > 0) {
					error.appendTo(fieldSet);
				} else if(bs.length>0) {
					bs.after(error);
				} else if ($(element).attr("type") === "checkbox" ){
					error.insertAfter($(element).parent().parent().parent());
				} else if ($(element).attr("name") === "bargainMinAmount" || $(element).attr("name") === "bargainMaxAmount"){
					error.insertAfter($(element).parent());
				} else {
					error.insertAfter(element);
				}
			},
        });
        jQuery.validator.addMethod("minPrice", function(value, element) {
        	return this.optional(element) || /^\d+(\.\d{1,2})?$/.test(value.trim());         
		},'最小金额格式错误！');
        jQuery.validator.addMethod("maxPrice", function(value, element) {
        	return this.optional(element) || /^\d+(\.\d{1,2})?$/.test(value.trim());         
		},'最大金额格式错误！');
        // 活动图片ajax上传
        this.$mainForm.on('change', '.upload-btn input[type="file"]', function(){
            var id = $(this).attr('id');
            that.ajaxFileUpload(id);
        });
        this.$mainForm.on('change', 'input[name="bargainType"]', function(){
        	// 当不关联商品规格时
        	if(!$(".j-isCheck").is(':checked')){
        		var type = $(this).val();
                if (type==0) {
                	$("#random").removeClass("hide");
                	$("#fixed").addClass("hide");
                }else{
                	$("#random").addClass("hide");
                	$("#fixed").removeClass("hide");
                }
            }else{
            	$("#fixed").addClass("hide");
            	$("#random").addClass("hide");
            	that.changeBargainStyle($("input[name='bargainType']:checked").val());
            }
        });
        
    },
    changeBargainStyle : function (curType){
    	var selDataObj = new Object();
    	selDataObj.curType = $("input[name='bargainType']:checked").val();
    	// 获取模板
    	$("#opraTr").find(".bargaintype").attr('bargaintype',curType);
    	$("#opraTr").find(".bargaintype").html($(template('changeBargainStyleTpl',selDataObj)));
    },
    checkBargainPrice :function(curType,bargainMinAmount,bargainMaxAmount,bargainAmount,curProPrice,curBasePrice){
    	var that = this;
    	if(parseFloat(curBasePrice) >= parseFloat(curProPrice)){
			that.msgTip('操作提示', '底价必须小于该规格商品价格！');
			return false;
		}
    	
    	if(curType == '0') {
        	if(bargainMinAmount && bargainMaxAmount){
        		if(!(/^\d+(\.\d{1,2})?$/.test(bargainMinAmount)) ||  !(/^\d+(\.\d{1,2})?$/.test(bargainMaxAmount))){
        			that.msgTip('操作提示', '金额范围中最小、大值格式不正确！');
            		return false;
        		}
				if(parseFloat(bargainMinAmount) < 0.01){
            			that.msgTip('操作提示', '金额范围中最小值最低为0.01！');
                		return false;
            	}
        		if (parseFloat(bargainMinAmount) >= parseFloat(bargainMaxAmount)){
        			that.msgTip('操作提示', '金额范围中最小值必须小于最大值！');
            		return false;
        		}
        		if (parseFloat(bargainMaxAmount) > ((parseFloat(curProPrice))-parseFloat(curBasePrice))) {
        			that.msgTip('操作提示', '金额范围中最大值必须小于等于商品原价和商品底价的差！');
            		return false;
        		}
        	}else{
        		that.msgTip('操作提示', '请输入砍价金额范围！');
        		return false;
        	}
        	
        } else {
        	if (bargainAmount){
        		if(!(/^\d+(\.\d{1,2})?$/.test(bargainAmount))){
        			that.msgTip('操作提示', '固定金额格式不正确！');
            		return false;
        		}
				if(parseFloat(bargainAmount) < 0.01){
            			that.msgTip('操作提示', '固定金额最低为0.01！');
                		return false;
            	}
        		if (parseFloat(bargainAmount) > ((parseFloat(curProPrice))-parseFloat(curBasePrice))) {
        			that.msgTip('操作提示', '固定金额必须小于等于商品原价和商品底价的差！');
            		return false;
        		}
        	} else {
        		that.msgTip('操作提示', '请输入固定金额！');
        		return false;
            }
        }
    	return true;
    },
    msgTip :function(title, msg){
    	var data = {
				'title':title,
				'msg':msg,
		}
		$('#alertMsgDialog').html($(template('alertMsgModalTpl', data)));
		$('#alertMsgDialog').modal('show');
    },
    checkTime : function(mins){
    	if (mins<10){
    		mins = "0" + mins;
        }
        return mins;
    },
    renderLists : function(images) {
        var that = this, 
        isCover, 
        defaultData = {}
        ;
	    this.initImages = images;
	    _(images).each(function(image, ik) {
	        isCover = 0;
	        var defaultData = $.extend({id: that.currentSeq}, that.defaultImageData);
	        if(that.productImage && that.productImage == image.imageUrl) {
	        	image.isCover = 1;
	        	image.CoverText = that.isCoverText;
	        }
	        if(image.imageUrl) {
	        	image.showImageUrl = image.imageUrl;
	        }
	        var $newPic = template('picItemTpl', $.extend(defaultData, image));
	        that.$picList.find('ul').append($newPic);
	        that.currentSeq ++ ;
	    });
	   
    },
    confirmPrd : function() {
    	var that = this;
    	var curPro = $(".j-curpro");
		if(curPro.length > 0){
			var pid= curPro.find(".j-prochoose").attr("proId");
			var pname = curPro.find(".u-pname").text();
			var pcode =curPro.find(".u-pcode").text();
			var onTime =curPro.find(".onTime").text();
			var offTime =curPro.find(".offTime").text();
			var priceRange =curPro.find(".priceRange").text();
			var pro ='<tr><td>'+pcode+'</td><td>'+pname+'</td><td id="onTime">'+onTime+'</td><td id="offTime">'+offTime+'</td><td id="priceRange">'+priceRange+'</td><td><a href="javascript:void(0)" proId="'+pid+'" class="j-delpro">删除</a></td></tr>';
			$(".m-pro").find("tbody").html(pro);
			$.ajax({
				url:urlPrefix +"coolbag/product/findSpecInfo",
				type:"POST",
				dataType: 'json',
				data:{"productId":pid },
				success:function(msg){
					if(msg.code ==="ACK"){
						var data = msg.data;
						data.curType = $("input[name='bargainType']:checked").val();
			            $("#specChooseBox").html($(template('proSpecTpl',{"dataList" : data})));
			            $('#chooseProduct').modal('hide');
						$(".j-addpro").hide();
						$(".addpro").show();
						$("#queryTf").show();
						$("#productId").val(pid);
						that.findPriceDetail(pid);
					}else{
						that.msgTip('操作提示', '查询商品规格失败！');
					}
				}
			});
		}
    },
    findPriceDetail :function(pid){
    	//获取各规格价格信息
		$.ajax({
			url:urlPrefix +"coolbag/product/findPriceDetail",
			type:"POST",
			dataType: 'json',
			data:{"productId":pid },
			success:function(msg){
				if(msg.code ==="ACK"){
					specPriceInfo = msg.data;//各种规格的价格信息
					//赋值
					$("#productId").val(pid);
					//移除警告信息
					$("#productId-error").remove();
				}
			}
		});
    },
    changeStartDate : function(e) {
		var endDate =  this.closest(".timegroup").find(".endDate");
		if (e.date) {
			endDate.datetimepicker('setStartDate', this.val());
		}else{
			endDate.datetimepicker('setStartDate', new Date());
		}
		if(this.val()=="" && this.next().css('display') == 'inline-block'){
			this.next().css('display','none');
			this.parent().next(".dateError").removeClass("hidden");
		}else{
			this.next().css('display','inline-block');
			this.parent().next(".dateError").addClass("hidden");
		}
	},
	changeEndDate : function(e) {
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth()+1;
		var day = date.getDate();
		var startDate =  this.closest(".timegroup").find(".startDate");

		if (e.date) {
			startDate.datetimepicker('setEndDate', this.val());
		}else{
			startDate.datetimepicker('setEndDate', '');
		};
		if(this.val()=="" && this.next().css('display') == 'inline-block'){
			this.next().css('display','none');
			this.parent().next(".dateError").removeClass("hidden");
		}else{
			this.next().css('display','inline-block');
			this.parent().next(".dateError").addClass("hidden");
		}
	},
    submitForm : function() {
    	if(this.$mainForm.valid()) {
    		this.doSave();
    	}
    },
    doSave : function() {
    	if(!this.getPostData()) return;
    	 var result = this.getPostData(),
    	     url    = this.newPostUrl,
    	 	 that   = this;
    	 if(this.id) {
    		url = this.editPostUrl;
            result.id = this.id;
    	 }
    	 $('button.submitMainForm').prop('disabled', true).text('保存中....');
    	 $.ajax(
            { 
        		type         : 'post',
				url          : urlPrefix+url,
				dataType     : 'json',
				contentType  : 'application/json',
				data         : JSON.stringify(result)
		 })
		 .done(function(result) {
			if(result.code == "ACK") {
				$('button.submitMainForm').prop('disabled', true).text('保存成功');
				that.$mainForm .loadingInfo({
    				type : "success", 
    				text: message("admin.message.success"),
    				callBack : function() {
    					window.location.href=urlPrefix + that.listUrl;
    				}
    			});
			} 
		 }).always(function() {
			 $('button.submitMainForm').prop('disabled', false).text('保存');
		 });
    },
    addNewPic : function() {
    	if(this.$goodspicList.find('li').length >= this.maxPics) {
    		this.$mainForm.loadingInfo("error", "最多只能添加 "+ this.maxPics + " 张图片");
    		return false;
    	}
    	
        var $newPic = template('picItemTpl', $.extend({id: this.currentSeq}, this.defaultImageData));
        this.$picList.find('ul').append($newPic);
        this.currentSeq ++ ;
    },
    // 图片上传ajax
    ajaxFileUpload:   function (id, o) {
        var that = this;
        $('#img_' + id + '').attr('src', uiBase + "/img/loading.gif");
    
        $.ajaxFileUpload({
            url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.kdProduct}), 
            secureuri : false,
            fileElementId : id,
            dataType : 'json',
            global : false,
            data : {},
            success : function (data, status) {
                if (data.code == "ACK") {
                    $('input[name="picUrl['+id+']"]').val(data.data.url);
                    $('input[name="picKey['+id+']"]').val(data.data.key);
                    $('#img_{{id}}'.template({id: id})).attr('src', data.data.url);
                }else {
                	that.msgTip("操作提示", data.message);
                }
                
            }
        });
        return false;
 
    },
   
    removePic : function (e) {
    	var $target = $(e.target),
    		$goodUpload = $target.closest('.goodspic-upload'),
    		picName = $goodUpload.find('input[name="pic.name"]').val();
           
        $goodUpload.remove();
    },
    
    addAct : function() {
    	$(".proname").val('');
		$(".procode").val('');
		getPrdList.bootTable.refresh();
		$('#chooseProduct').modal('show');
    },
    queryTf : function() {
    	$("#showTfTitle").text("关联相同商品的24小时活动列表");
    	$(".tf-onTime").val(''),
		$(".tf-offTime").val(''),
    	getTfList.bootTable.refresh();
    	$('#queryTfModel').modal('show');
    },
    checkTfDate : function(productId,onTime,offTime){
    	var checkResult = false;
    	$.ajax({
			type: "post",
			url:urlPrefix + 'kdBargain/getTfByProductIdAndTime',
			dataType     : 'json',
			contentType: "application/json;charset=utf-8",
			data: JSON.stringify({
				productId : productId,
    			onTime : onTime,
    			offTime : offTime,
    			id :$("#tfId").val()
			}),
			async:false,
			success: function(response){
				if(response.code == "ACK" && response.data > 0){
					checkResult =  true;
				}
			},
		});
    	return checkResult;
    },
    checkProductStatus : function(productId) {
    	var checkResult = false;
    	$.ajax({
			url:urlPrefix + 'kdBargain/findSaleStatusByProductId',
			type:"POST",
			dataType: 'json',
			data:{"productId":productId},
			async:false,
			success: function(response){
				if(response.code == "ACK" && response.data == '2'){
					checkResult =  true;
				}
			},
		});
    	return checkResult;
    },
    getPostData : function() {
    	var that = this;
        //序列化表单值
        var formData = $('#mainForm').frmSerialize();
        var checkResult =  that.checkProductStatus(formData['productId']);
        if(!checkResult){
        	that.msgTip("操作提示", "活动关联商品状态异常，请确保商品已上架！");
			return;
        }
        if(typeof(specInfoList) == "undefined"  || !$.isArray(specInfoList)){
			specInfoList = new Array();
		}
        //获取商品价格范围
        var priceRange = $('#priceRange').text();
        var priceRangeArr = new Array();
        priceRangeArr = priceRange.split('~');
        for (var i = 0; i < priceRangeArr.length; i++) {
        	priceRangeArr[i] = priceRangeArr[i].replace(',', '');
        }
        var result = {
            name: formData['name'],
            explainInfo:null,
            supportDesc: $("#supportDesc").val().split("\n").join("<br />"),//formData['supportDesc'],
            specialDesc: $("#specialDesc").val().split("\n").join("<br />"),//formData['specialDesc'],
            effectiveStartDate:formData['effectiveStartDate'],
            effectiveEndDate:formData['effectiveEndDate'],
            specType: formData['specType'],
            specInfoList: specInfoList,
            productId: formData['productId'],
            basePrice: formData['basePrice'],
            bargainType:formData['bargainType'],
            description: _.isUndefined(formData['description']) ? "" : formData['description'] ,
            status:$("#status").val(),
            id:$("#tfId").val(),
            bargainMinAmount:'',
            bargainMaxAmount:'',
            bargainAmount:'',
        };
        
        var descImage = $(".desc-image .upload-thumb");
		var imagKey = descImage.find("input[name^=picKey]").val();
		if ("" == imagKey || imagKey == undefined) {
			that.msgTip("操作提示", "请上传活动说明图片！");
			return;
		}else {
			var picData = new Object();  
			picData.imageUrl = $(descImage).find("input[name='picUrl[50]']").val();
			picData.imageKey = $(descImage).find("input[name='picKey[50]']").val();
			picData.imageId = $(descImage).find("input[name='picId[50]']").val();
			picData.sort = 0;
			picData.title = "吾儿网";
			result.explainInfoPics = picData;
		}
        
        if(!formData['productId']) {
        	that.msgTip('操作提示', '请选择活动商品！');
        	return false;
        }
        
        if(!$(".j-isCheck").is(':checked')){
        	//检验统一活动商品底价
        	if(!formData['basePrice']) {
            	that.msgTip('操作提示', '请输入统一活动商品底价！');
            	return false;
            }
        	if (parseFloat(priceRangeArr[0]) <= parseFloat(formData['basePrice'])) {
            	that.msgTip('操作提示', '统一活动商品底价必须小于商品最低原价！');
            	return false;
            }
        }
        
        // 勾选'选择规格'
        if($(".j-isCheck").is(':checked')){
        	if(this.$choosedSpec.length == 0) {
        		that.msgTip('操作提示', '请选择商品规格信息！');
            	return false;
        	}
        	
        }
        
        if(!$('#selSpec').is(':checked')) {
            if(formData['bargainType']==0) {
            	if(formData['bargainMinAmount'] && formData['bargainMaxAmount']){
            		if(!(/^\d+(\.\d{1,2})?$/.test(formData['bargainMinAmount'])) ||  !(/^\d+(\.\d{1,2})?$/.test(formData['bargainMaxAmount']))){
            			that.msgTip('操作提示', '金额范围中最小、大值格式不正确！');
                		return false;
            		}
            		if(parseFloat(formData['bargainMinAmount']) < 0.01){
            			that.msgTip('操作提示', '金额范围中最小值最低为0.01！');
                		return false;
            		}
            		if (parseFloat(formData['bargainMinAmount']) >= parseFloat(formData['bargainMaxAmount'])){
            			that.msgTip('操作提示', '金额范围中最小值必须小于最大值！');
                		return false;
            		}
            		if (parseFloat(formData['bargainMaxAmount']) > ((parseFloat(priceRangeArr[0]))-parseFloat(formData['basePrice']))) {
            			that.msgTip('操作提示', '金额范围中最大值必须小于等于商品最低原价和商品底价的差！');
                		return false;
            		}
            		result.bargainMinAmount = formData['bargainMinAmount'];
                	result.bargainMaxAmount = formData['bargainMaxAmount'];
            	}else{
            		that.msgTip('操作提示', '请输入金额范围！');
            		return false;
            	}
            	
            } else {
            	if (formData['bargainAmount']){
            		if(!(/^\d+(\.\d{1,2})?$/.test(formData['bargainAmount']))){
            			that.msgTip('操作提示', '固定金额格式不正确！');
                		return false;
            		}
            		if(parseFloat(formData['bargainAmount']) < 0.01){
            			that.msgTip('操作提示', '固定金额最低为0.01！');
                		return false;
            		}
            		if (parseFloat(formData['bargainAmount']) > ((parseFloat(priceRangeArr[0]))-parseFloat(formData['basePrice']))) {
            			that.msgTip('操作提示', '固定金额必须小于等于商品最低原价和商品底价的差！');
                		return false;
            		}
            		result.bargainAmount = formData['bargainAmount'];
            	} else {
            		that.msgTip('操作提示', '请输入固定金额！');
            		return false;
                }
            	
            }
        }
        
        if(!result.effectiveStartDate || !result.effectiveEndDate) {
        	that.msgTip('操作提示', '请选择活动有效期！');
        	return false;
        }else {
        	result.effectiveStartDate += ":00";
        	result.effectiveEndDate += ":00";
        	
        	$(".tf-onTime").val(result.effectiveStartDate);
        	$(".tf-offTime").val(result.effectiveEndDate);
        	var checkResult = that.checkTfDate(formData['productId'],result.effectiveStartDate,result.effectiveEndDate);
        	if(checkResult){
        		$("#j-selectTf").click();
        		$("#showTfTitle").text("相同商品的活动，其有效期存在时间交叉！请重新选择！");
        		$('#queryTfModel').modal('show');
        		return false;
        	}
        }
        
        // 检验活动时间是不是在商品上下架时间内
        var onTime = new Date(($("#onTime").text()).replace(new RegExp("-","gm"),"/")).getTime();
        var offTime = new Date(($("#offTime").text()).replace(new RegExp("-","gm"),"/")).getTime();
        var effectiveStartDate = new Date((result.effectiveStartDate).replace(new RegExp("-","gm"),"/")).getTime();
        var effectiveEndDate = new Date((result.effectiveEndDate).replace(new RegExp("-","gm"),"/")).getTime();
        var nowTime = new Date().getTime();
        if(effectiveEndDate <= nowTime){
        	that.msgTip('操作提示', '活动结束时间不能小于等于当前时间，请重新选择！');
        	return false;
        }
        if(!(onTime <= effectiveStartDate && offTime >= effectiveEndDate)){
        	that.msgTip('操作提示', '活动时间必须在商品上下架时间范围内');
        	return false;
        }
        if(effectiveStartDate == effectiveEndDate){
        	that.msgTip('操作提示', '活动有效期不能出现开始和结束时间相同，请重新选择！');
        	return false;
        }
        if(effectiveStartDate > effectiveEndDate){
        	that.msgTip('操作提示', '活动有效期不能出现开始时间大于结束时间，请重新选择！');
        	return false;
        }
        
        //file
        var listImages = getPicList($("#mainForm"));
        if (0 == listImages.length) {
        	that.msgTip('操作提示', '请添加图片！');
        	return false;
		}
        if(listImages.length < 3 ){
        	that.msgTip('操作提示', '请至少添加三张活动图片！');
        	return false;
        }
        if(listImages.length > 5){
        	that.msgTip('操作提示', '活动图片最多上传五张！');
        	return false;
        }
        result.listImages = listImages;
        if (!result.description) {
        	that.msgTip('操作提示', '请添加活动详情！');
        	return false;
        }
        return result;
    }
};
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
			url: 'coolbag/product/search',
			sidePagination:'server',
			pageSize : 10,
			pagination : true,
			currentPage: 1,
			queryParamsType: "limit",
			queryAddParams: function() {
				return {
					pname : '',
					pcode : '',
					saleStatus : "2"
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
					field: 'code',
					align: 'center',
					formatter:function(value,row,index){
						return '<span class="u-pcode">'+row.pcode+'</span>';
					}
				} ,
				{
					title:"商品名称",
					field: 'name',
					align: 'center',
					formatter:function(value,row,index){
						return '<span class="u-pname">'+row.pname+'</span>';
					}
				},
				{
					title:"上架时间",
					field: 'onTime',
					align: 'center',
					formatter:function(value,row,index){
						return '<span class="onTime">'+row.onTime+'</span>';
					}
				},
				{
					title:"下架时间",
					field: 'offTime',
					align: 'center',
					formatter:function(value,row,index){
						return '<span class="offTime">'+row.offTime+'</span>';
					}
				},
				{
					title:"价格范围",
					field: 'priceRange',
					align: 'center',
					formatter:function(value,row,index){
						return '<span class="priceRange">'+row.priceRange+'</span>';
					}
				}
			]
		});
	},
	bindEvents : function() {
		var that = this;
		that.$searchItem.on("click",function() {
			that.bootTable.options.queryAddParams = function(){
				return {
					pname : $(".proname").val(),
					pcode : $(".procode").val(),
					saleStatus : "1,2"
				}
			};
			that.bootTable.refresh();
		});
	}
};

//获取添加商品列表，并分页显示，且可搜索查询
var getTfList = {
	$dataList : $('.j-tfList'),
	$searchItem : $('#j-selectTf'),
	bootTable : void 0,
	init : function() {
		this.bindEvents();
		this.bootTable = $.GLOBAL.utils.loadBootTable({
			table : this.$dataList,
			removeBtn : $('#removeRecord'),
			refreshBtn : $('#refreshRecord'),
			idField : "",
			url: 'kdBargain/list',
			sidePagination:'server',
			pageSize : 10,
			pagination : true,
			currentPage: 1,
			queryParamsType: "limit",
			queryAddParams: function() {
				return {
					productId : $("#productId").val(),
					status :"2,3"
				}
			},
			columns: [
				{
					title:"活动编号",
					width: 50,
					align: 'center',
					formatter:function(value,row,index){
						return '<span class="u-pname">'+row.id+'</span>';
					}
				} ,
				{
					title:"活动名称",
					field: 'tfName',
					align: 'center',
					formatter:function(value,row,index){
						return '<span class="u-pname">'+row.name+'</span>';
					}
				},
				{
					title:"开始时间",
					field: 'effectiveStartDate',
					align: 'center',
					formatter:function(value,row,index){
						return '<span class="onTime">'+row.effectiveStartDate+'</span>';
					}
				},
				{
					title:"结束时间",
					field: 'effectiveEndDate',
					align: 'center',
					formatter:function(value,row,index){
						return '<span class="offTime">'+row.effectiveEndDate+'</span>';
					}
				}
			]
		});
	},
	bindEvents : function() {
		var that = this;
		that.$searchItem.on("click",function() {
			that.bootTable.options.queryAddParams = function(){
				return {
					onTime : $(".tf-onTime").val(),
					offTime : $(".tf-offTime").val(),
					productId : $("#productId").val(),
					status :"2,3",
					id :$("#tfId").val()
				}
			};
			that.bootTable.refresh();
		});
	}
};
//事件绑定，上传图片
$("#mainForm").on('change', '.upload-desc-img input[type="file"]', function(){
	ajaxFileUpload($(this).attr('id'));
});

// 事件绑定，删除图片
$("#mainForm").on('click', '.remove-desc-img', function(){
	var imgDiv = $(this).prev().prev();
	imgDiv.children("img").attr('src', uiBase + "img/default_goods_image_240.gif");
	imgDiv.children("input").val('');
});