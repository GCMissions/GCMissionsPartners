$(function() {
//	$('#search').on("click", function() {
//		alert(12121);
//	});

//开始日期
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();

	$('#csDate,#ceDate').datetimepicker({
		minView: 'month',
		format: 'yyyy-MM-dd',
		language: 'ch',
		endDate: year+'-'+month+'-'+day,
		autoclose : true,
		todayBtn : true
	});
	//创建日期
	$('#csDate').on('changeDate',function(){
		$('#ceDate').datetimepicker('setStartDate', $('#csDate').val());
		if($('#csDate').val()=="" && $("#csDate").next().css('display') == 'inline-block'){
			$("#csDate").next().css('display','none');
		}else{
			$("#csDate").next().css('display','inline-block');
		}
	});

	//结束日期
	$('#ceDate').on('changeDate',function(){
		if ($('#ceDate').val()) {
			$('#csDate').datetimepicker('setEndDate', $('#ceDate').val());
		}else{
			$('#csDate').datetimepicker('setEndDate', year+'-'+month+'-'+day);
		};
		if($('#ceDate').val()=="" && $("#ceDate").next().css('display') == 'inline-block'){
			$("#ceDate").next().css('display','none');
		}else{
			$("#ceDate").next().css('display','inline-block');
		}
	});
	
	
	$('#warnStock').on("click", function() {
		table.options.queryAddParams = function(){
			return {
				isWaring:'T',
				orgIds : $("#orgIds").val()
			}
		};
		table.refresh();
	});
	$('#search').on("click", function() {
		table.options.queryAddParams = function(){
			return {
				 orgName: $("#orgName").val(),
	    		 orgCode: $("#orgCode").val(),
	    		 contactName: $("#contactName").val(),
	    		 phone: $("#phone").val(),
	    		 startTime: $("#csDate").val(),
	    		 endTime: $("#ceDate").val(),
	    		 orgIds : $("#orgIds").val()
			}
		};
		table.refresh();
	});
	
	var table = $.GLOBAL.utils.loadBootTable({
		table : $('#dataList'),
	    //refreshBtn : $('#search'),
	    url: 'terminalStock/list',
	    sidePagination:'server',
	    pagination : true,
	    queryParamsType: "limit", 
	    queryAddParams: function() {
	    	return {
	    		 orgName: $("#orgName").val(),
	    		 orgCode: $("#orgCode").val(),
	    		 contactName: $("#contactName").val(),
	    		 phone: $("#phone").val(),
	    		 startTime: $("#csDate").val(),
	    		 endTime: $("#ceDate").val(),
	    		 orgIds : $("#orgIds").val()
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
	  	            field:'orgCode'
	  	        },
	  	        {
	  	            field:'orgName'
	  	        }, 
	  	        {
	  	            field:'contactName'
	  	        }, 
	  	        {
	  	            field: 'phone'
	  	        },
	  	        {
	  	            field:'stockNum'
	  	        },
	  	        {
	  	            field:'status'
	  	        },
	  	      {
	  	            field:'date'
	  	        },
	  	      {
		            align: 'center',
		            checkbox: false,
		            formatter:function(value,row,index){  
		                return ' <a  title="【查看】" target="_self" href="detail/'+row.orgCode+'" class="editItem"> <i class="fa fa-eye "  style="font-size:20px"></i></a>';
		            } 
	  	        }
	  	     ]
	});
	
	var excelReport = function(){
		var title = "确定导出商家安全库存预警表？";
		//导出
		BootstrapDialog.show({
			title: "导出",
			type : BootstrapDialog.TYPE_WARNING,
			message: message(title),
			draggable: true,
			size : BootstrapDialog.SIZE_SMALL,
			buttons: [{
				label: '确认',
				cssClass: 'btn-primary saveAddEditTpl',
				action: function(dialog) {
					dialog.close();
					$("#stockForm").submit();
				}
			}, {
				label: '取消',
				action: function(dialog) {
					dialog.close();
				}
			}]
		});
	};
	
	$("#excel").on('click',function(){
    	excelReport();
	});
});