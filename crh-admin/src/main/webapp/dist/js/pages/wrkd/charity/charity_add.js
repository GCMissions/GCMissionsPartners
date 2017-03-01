$(function(){
	
	var charityId = $("#mainForm").attr("charity-id");
	
	// 获取当前时间
	function getCurTimeStr() {
		var curDate = new Date();
    	var month = curDate.getMonth() + 1 < 10 ? "0" + curDate.getMonth() + 1 : curDate.getMonth() + 1;
    	var date = curDate.getDate() < 10 ? "0" + curDate.getDate() : curDate.getDate();
    	var hour = curDate.getHours() < 10 ? "0" + curDate.getHours() : curDate.getHours();
    	var minutes = curDate.getMinutes() < 10 ? "0" + curDate.getMinutes() : curDate.getMinutes();
    	
    	return curDate.getFullYear() + "-" + month + "-" + date + " " + hour + ":" + minutes;
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
	
	// 返回按钮控制
	var oper = $("#mainForm").attr("oper");
	$(".back-away").click(function(){
		if ("add" == oper) {
    		var tab = $(this).closest(".tab-pane.active");
    		if ("tab_1" == tab.attr("id")){
    			window.location.href = urlPrefix + 'coolbag/charity/';    			
    		} else {
    			tab = $(tab).prev().attr("id");
    			$("#mytabs li a[href='#"+ tab + "']").tab('show');
    		}
    	} else {
    		window.location.href = urlPrefix + 'coolbag/charity/';
    	}
	})
	
	// 初始化时间选择控件
	function dateInit() {
		//　编辑时初始化显示删除按钮
		if ($('#startDate').val()) {
			$("#startDate").next().css('display','inline-block');
		}
		if ($('#endDate').val()) {
			$("#endDate").next().css('display','inline-block');
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
	}
	
	dateInit();
	
	// 事件绑定，上传图片
	$("#mainForm").on('change', '.upload-desc-img input[type="file"]', function(){
		ajaxFileUpload($(this).attr('id'));
	});

	// 事件绑定，删除图片
	$("#mainForm").on('click', '.remove-desc-img', function(){
		var imgDiv = $(this).prev().prev();
		imgDiv.children("img").attr('src', uiBase + "img/default_goods_image_240.gif");
		imgDiv.children("input").val('');
	});

	// 加载专辑列表
	var bootTable = $.GLOBAL.utils.loadBootTable({
		table : $("#featureList"),
		refreshBtn : $("#search"),
	    pagination : true,
	    pageSize :10,
	    url : "coolbag/feature/list",
	    sidePagination:'server',
	    queryAddParams: function() {
	    	var queryParam = {
	    			name : $("#featureName").val(),
	    			source : 1
	    	}
	    	var tags = new Array();
	    	$("input[name='tags']:checked").each(function(){
	    		tags.push($(this).val());
	    	});
	    	if (0 < tags.length) {
	    		queryParam.tagNames = tags;
	    	}
	    	return queryParam;
	    },
	    columns: [{
	    	field: 'id',
			width: 50,
			align: 'center',
			formatter:function(value,row,index){
				return "<input type='radio' name='feature' feature-id='"+ row.id +"' />";
			}
		} , {
            field: 'name',
            align: 'center'
        } , {
        	field: 'description',
        	width: 350,
            align: 'center'
        } , {
        	field: 'tagName',
            align: 'center'
        }]
	});
	
	// 确认选择-专辑
	$("#confirmAdd").click(function(){
		var rowSelected = $("input[name='feature']:checked");
		var htmlArray = new Array();
		var featureId = rowSelected.attr("feature-id");
		htmlArray.push("<tr feature-id='"+ featureId + "'>");
		var feature = rowSelected.parents("tr:first");
		for (var i = 1, len = feature.children("td").length; i < len; i++){
			htmlArray.push("<td>" + feature.children("td").eq(i).text() + "</td>");
		}
		htmlArray.push("<td><span>删除</span></td></tr>");
		// 将选中的专辑显示到表格中
		$("#feature-table tbody").html(htmlArray.join(""));
		// 关联专辑隐藏
		$(".u-feature").parent().css("display", "none");
		$("#chooseFeature").modal("hide");
	});
	
	// 活动图片
	imageOperInit($("#mainForm"));
	$(".addNewPic").click(function(){
		addNewPic(5, $("#mainForm"));
	});
	
	// 专辑选择弹框
	$(".u-feature").click(function(){
		//　清除之前的选择
		$("input[name='feature']:checked").prop("checked", false);
		$("#chooseFeature").modal("show");
	});
	
	// 清除专辑选择框中的查询记录
	$('#chooseFeature').on('hide.bs.modal, hidden.bs.modal', function() {
		//　清空专辑名称
		$('.select-feature #featureName').val('');
		// 重置标签
		$("input[name='tags']").each(function(){
    		if($(this).prop("checked")){
    			$(this).prop("checked", false);
    		}
    	});
		//　刷新列表
		bootTable.refresh();
	})
	
	// 删除已选择的专辑
	$("#feature-table tbody").on("click", "tr span", function(){
		// 清除已选择的专辑
		$(this).parents("tbody:first").empty();
		// 关联专辑显示
		$(".u-feature").parent().css("display", "block");
	});
	
	// 保存公益活动基本信息
	$("#saveBaseInfo").click(function(){
		if ("" == $.trim($("#charityName").val())){
			msgTip("操作提示", "活动名称不能为空！");
			return;
		}
		var explainNote = $("#explainNote").val().split("\n").join("<br />");//活动说明
		if ("" == $.trim(explainNote)){
			msgTip("操作提示", "活动说明不能为空！");
			return;
		}
		var startTime = $("#startDate").val();
		var endTime = $("#endDate").val();
		if ("" == startTime || "" == endTime) {
			msgTip("操作提示", "活动时间不能为空！");
			return;
		}
		if (startTime == endTime) {
			msgTip("操作提示", "活动开始时间不能和结束时间相同！");
			return;
		}
		var descImage = $(".desc-image .upload-thumb");
		var imagKey = descImage.find("input[name^=picKey]").val();
		if ("" == imagKey || imagKey == undefined) {
			msgTip("操作提示", "请上传活动感谢图片");
			return;
		}
		var listImages = getPicList($("#mainForm"));
		if (0 == listImages.length) {
			msgTip("操作提示", "至少上传3张图片");
			return;
		}
		if ('' == $("#feature-table tbody").text()){
			msgTip("操作提示", "请选择关联的专辑");
			return;
		}
		
		var baseInfo = {
				id : charityId,
				name : $("#charityName").val(),
				explainNote : explainNote,
				startTime : startTime,
				endTime : endTime,
		}
		var formData = $('#mainForm').frmSerialize();
    	var descDetail= _.isUndefined(formData['detailDesc']) ? "" : formData['detailDesc'];
		baseInfo.imgUrl = $(descImage).find("img").attr("src");
		baseInfo.featureId = $("#feature-table tbody tr").eq(0).attr("feature-id");
		baseInfo.listImages = listImages;
		baseInfo.detailDesc = descDetail;
		
		console.log(baseInfo);

		$("#saveBaseInfo").prop("disabled", true);
		$.ajax({
			url : urlPrefix + "coolbag/charity/save",
			type : 'POST',
			data : JSON.stringify(baseInfo),
			dataType : 'json',
			contentType : "application/json",
			success : function(result){
				if ("ACK" == result.code) {
					charityId = result.data;
					$(window).loadingInfo("success", result.message);
					window.location.href = urlPrefix + "coolbag/charity/";
				//	$("#mytabs li a[href='#tab_2']").tab("show");
				} else {
					$(window).loadingInfo("error", result.message);
				}
			},
			complete : function() {
				$("#saveBaseInfo").prop("disabled", false);
			}
		});
	});
	
	// 保存活动详情
	$("#saveDetailDesc").click(function(){
		var formData = $('#detailForm').frmSerialize();
    	var detailDesc= _.isUndefined(formData['detailDesc']) ? "" : formData['detailDesc'];
    	if ("" != detailDesc) {
    		$.ajax({
    			url : urlPrefix + "coolbag/charity/saveDetail",
    			type : 'POST',
    			data : JSON.stringify({
    				id : charityId,
    				detailDesc : detailDesc
    			}),
    			dataType : 'json',
    			contentType : 'application/json',
    			success : function(result) {
    				if ("ACK" == result.code) {
    					$(window).loadingInfo("success", result.message);
    					if ('add' == oper) {
    						window.location.href = urlPrefix + "coolbag/charity/";
    					}
    				} else {
    					$(window).loadingInfo("error", result.message);
    				}
    			}
    		});
    	} else {
    		if ('add' == oper) {
				window.location.href = urlPrefix + "coolbag/charity/";
			}
    	}
	});
	
	// 保存反馈详情
	$("#saveFeedback").click(function(){
		var formData = $('#feedbackForm').frmSerialize();
    	var feedback= _.isUndefined(formData['detailFeedback']) ? "" : formData['detailFeedback'];
    	if ("" != feedback) {
    		$.ajax({
    			url : urlPrefix + "coolbag/charity/saveFeedback",
    			type : 'POST',
    			data : JSON.stringify({
    				id : charityId,
    				feedback : feedback
    			}),
    			dataType : 'json',
    			contentType : 'application/json',
    			success : function(result) {
    				if ("ACK" == result.code) {
    					$(window).loadingInfo("success", result.message);
    					window.location.href = urlPrefix + "coolbag/charity/";
    				} else {
    					$(window).loadingInfo("error", result.message);
    				}
    			}
    		});
    	} else {
    		window.location.href = urlPrefix + "coolbag/charity/";
    	}
	});
});