$(function() {
	/*$('#warnStock').on("click", function() {
		table.options.queryAddParams = function(){
			return {
				orgType:'Z',
				isWarning:'T'
			}
		};
		table.refresh();
	});*/
	
	//开始日期
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();

	$('#startTime,#endTime').datetimepicker({
		minView: 'month',
		format: 'yyyy-mm-dd',
		language: 'ch',
		//endDate: year+'-'+month+'-'+day,
		autoclose : true,
		todayBtn : true
	});
	//创建日期
	$('#startTime').on('changeDate',function(){
		$('#endTime').datetimepicker('setStartDate', $('#startTime').val());
		if($('#startTime').val()=="" && $("#startTime").next().css('display') == 'inline-block'){
			$("#startTime").next().css('display','none');
		}else{
			$("#startTime").next().css('display','inline-block');
		}
	});

	//结束日期
	$('#endTime').on('changeDate',function(){
		if ($('#endTime').val()) {
			$('#startTime').datetimepicker('setEndDate', $('#endTime').val());
		}else{
			$('#startTime').datetimepicker('setEndDate', year+'-'+month+'-'+day);
		};
		if($('#endTime').val()=="" && $("#endTime").next().css('display') == 'inline-block'){
			$("#endTime").next().css('display','none');
		}else{
			$("#endTime").next().css('display','inline-block');
		}
	});
	
	$("#import").on('click',function(){
		$("#postExcel").trigger('click');
	});
	
	var importExcel = function(){
		console.log($("#postExcel").val());
		$('body').loadingInfo("show", '导入中...',200000);
		$.ajaxFileUpload({
            url:"importExcel",            
			dataType : 'json',
            fileElementId:'postExcel',                   
            /*type:"POST",*/
            secureuri:false,
            success: function(data){     
				if (data.code == "ACK") {
					$('body').loadingInfo("success", data.message);
				} else if(data.code == "BUSINESS_ERROR"){
					var errorMsg = data.message.split("！");
					$('body').loadingInfo("error", errorMsg[0] + "！", 500);
					BootstrapDialog.show({
						title: "失败条码",
						type : BootstrapDialog.TYPE_WARNING,
						message: message(errorMsg[1]),
						draggable: true,
						size : BootstrapDialog.SIZE_NORMAL,
						buttons: [{
							label: '确认',
							cssClass: 'btn-primary',
							action: function(dialog) {
								dialog.close();
							}
						}, {
							label: '<i class="fa  fa-refresh"></i> 条码导出',
							action: function(dialog) {
								$("#errorCodeForm").submit();
							}
						}]
					});
				}else {
					$('body').loadingInfo("error", data.message);
				} 
            },
            error: function (data, e){  
            	$('body').loadingInfo("error", "上传失败！");
            }  
        }); 
		$("#postExcel").on('change',importExcel);
	}
	
	$("#postExcel").on('change',importExcel);
	
	//导出表格
	$('#exportBtn').on('click',function(){
		$("#myModal").modal('hide');
		BootstrapDialog.show({
			title: "提示",
			type : BootstrapDialog.TYPE_WARNING,
			message: message("是否导出入库表？"),
			draggable: true,
			size : BootstrapDialog.SIZE_SMALL,
			buttons: [{
				label: '确认',
				cssClass: 'btn-primary',
				action: function(dialog) {
					dialog.close();
					$("#inStockForm").submit();
				}
			}, {
				label: '取消',
				action: function(dialog) {
					dialog.close();
				}
			}]
		});
		
			/*var startTime = $('#startTime').val();
			var endTime =$('#endTime').val();
			
			var time = {
					 startTime : startTime,
					 endTime : endTime
			}
			$.ajax({
				type: "GET",
				url: urlPrefix +'adminStock/toExcel',
				contentType: "application/json;charset=utf-8",
				data: JSON.stringify(time),
				dataType: "json",
				success: function(message){
						alert();
				},
				error:function(message){
					//alert('shibai');
				}
			});
			//window.location.href = $.GLOBAL.config.urlPrefix +'"adminStock/toExcel/?startTime="'+startTime+'"&endTime="'+endTime+'"';
*/		
	});
	
	
	$('#scan').on("click", function() {
		window.location.href= urlPrefix+"barcode/adminIndex";
	});
	$('#search').on("click", function() {
		table.options.queryAddParams = function(){
			return {
				goodsCode: $("#materialCode").val(),
				goodsName: $("#materialName").val(),
			}
		};
		table.refresh();
	});
	var table = $.GLOBAL.utils.loadBootTable({
		table : $('#dataList'),
	    url: 'adminStock/list',
	    sidePagination:'server',
	    pagination : true,
	    queryParamsType: "limit",
	    queryAddParams: function() {
	    	return {
				goodsCode: $("#materialCode").val(),
				goodsName: $("#materialName").val(),
			}
	    },
	    columns: [
				{
					width:50,
					align: 'center',
					formatter:function(value,row,index){  
						return index+1;
					}
				} ,
	  	        {
	  	            field:'goodsCode'
	  	        },
	  	        {
	  	            field:'goodsName'
	  	        }, 
	  	        {
	  	            field: 'stockNum'
	  	        }
	  	     ]
	});
});
