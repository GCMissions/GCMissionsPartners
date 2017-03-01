$(function(){
	
	var rechargeList = {
			initEvents:function(){
				//开始日期
				var date = new Date();
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				var day = date.getDate();

				$('#csDate,#ceDate').datetimepicker({
					minView: 'month',
					format: 'yyyy-MM-dd',
					language: 'ch',
					//endDate: year+'-'+month+'-'+day,
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
				
				$.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
				    refreshBtn : $('#refreshRecord'),
				    url: 'memberRecharge/list',   
				    sidePagination:'server',
				    pagination : true,
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	var result= {
				    			memberPhone  : $("#memberPhone").val(),
				    			memberName: $("#memberName").val(), 
				    			payDateBegin: $("#csDate").val(), 
				    			payDateEnd: $("#ceDate").val(), 	    		
					    		amount: $("#amount").val(), 
					    		paymentType: $("#paymentType").val()
							}
				    	if(result.payDateBegin && $.GLOBAL.utils.isDate(result.payDateBegin)) {
				    		result.payDateBegin += " 00:00:00";
		                }
		                if(result.payDateEnd && $.GLOBAL.utils.isDate(result.payDateEnd)) {
		                	result.payDateEnd += " 23:59:59";
		                }
						if($("#csDate").val()==""){delete result.payDateBegin}
						if($("#ceDate").val()==""){delete result.payDateEnd}
						return result;
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
				            field: 'amountYuan',
				        } ,
				        {
				            field: 'paymentType',
				            formatter:function(value,row,index){
				            	if(value=="2") value="支付宝";
				            	if(value=="3") value="微信";
				            	return value;
				            }
				        } ,
				        {
				            field: 'payDate',
				        } ,
				        {
				            field: 'memberPhone',
				        } ,
				        {
				            field: 'memberName',
				        } 
				     ]
				
				});
			//	this.initInputMask();
			},
			
			initInputMask : function() {
		    	$('#amount').inputmask({
		    		"mask" : "9",
		    		repeat : 7,
		    		"greedy": false
		    	});
		    },
			
			init:function(){
				var _self = this;
				_self.initEvents();
			}
	}.init();
	
});