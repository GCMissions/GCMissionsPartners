$(function(){
	var financeReportList = {
			initEvents:function(){
				var _self = this;
				//开始日期
				var date = new Date();
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				var day = date.getDate();

				$('#csDate,#ceDate,#usDate,#ueDate').datetimepicker({
					minView: 'month',
					format: 'yyyy-mm-dd',
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
				
				$("#excel").click(function(){
		        	_self.excelReport();
		        });
				
				$(document).on('click','.datailItem', _(this.datailItem).bind(this));
			
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
	        	    refreshBtn : $('#refreshRecord'),
				    url: 'financeReport/getFinanceReport',
				    sidePagination:'server',
				    pagination : true,
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	var result= {
				    			orgName  : $("#orgName").val(),
				    			orgType: $("#orgType").val(), 
				    			beginDate: $("#csDate").val(), 
				    			endDate: $("#ceDate").val(), 	    		
							};
				    	if(result.beginDate && $.GLOBAL.utils.isDate(result.beginDate)) {
				    		result.beginDate += " 00:00:00";
		                }
		                if(result.endDate && $.GLOBAL.utils.isDate(result.endDate)) {
		                	result.endDate += " 23:59:59";
		                }
	                
						if($("#csDate").val()==""){delete result.beginDate}
						if($("#ceDate").val()==""){delete result.endDate}
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
				            field: 'orgName',
				        } ,
				        {
				            field: 'orgType',
				        } ,
				        {
				            field: 'prodProfit',
				        } ,
				        {
				            field: 'shipProfit',
				        } ,
				        {
				            field: 'fineTotal',
				        } ,
				        {
				            field: 'rewardTotal',
				        } ,
				        {
				            field: 'refereeTotal',
				        } ,
				        {
				        	field: 'orgId',
				            align: 'center',
				            //checkbox: false,
				            formatter:function(value,row,index){  				                
				            	return ' <a  title="查看" class="datailItem" data-id="'+row.orgId+'"> <i class="fa fa-eye"  style="font-size:20px"></i></a>';
				            } 
				        }
				     ],
				     onLoadSuccess:function(data){
		            	 if(data){
		            		 $("#totalAmount").html(data.totalAmount+"元");
		            		 $("#actualAmount").html(data.actualAmount+"元");
		            		 $("#couponAmount").html(data.couponAmount+"元");
		            		 $("#totalProfit").html(data.totalProfit+"元");
		            		 $("#totalSPProfit").html(data.total1Profit+"元");
		            		 $("#pTotalSPProfit").html(data.totalPProfit+"元");
		            		 $("#zTotalSPProfit").html(data.totalZProfit+"元");
		            	 }
		             }
				});			
			},
			
			excelReport:function(){
				var title = "确认导出平台报表？";
								
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
	                    	$("#orderForm").submit();
	                    }
	                }, {
	                    label: '取消',
	                    action: function(dialog) {
	                        dialog.close();
	                    }
	                }]
	            });
			},
			
			datailItem : function(e){
				
				var orgId = $(e.target).parent(".datailItem").attr("data-id");
				var orgType = $("#orgType").val();
				var beginDate = $("#csDate").val();
   			 	var endDate   = $("#ceDate").val();
   			 	
   			 if(beginDate && $.GLOBAL.utils.isDate(beginDate)) {
					beginDate += " 00:00:00";
             }else {
            	 beginDate = "1979-01-01 00:00:00";
             }
             if(endDate && $.GLOBAL.utils.isDate(endDate)) {
             	endDate += " 23:59:59";
             }else {
            	 endDate = "2100-01-01 23:59:59";
             }
				window.location.href= urlPrefix + "financeReport/detail?orgId="+orgId+"&orgType="+orgType+"&beginDate="+beginDate+"&endDate="+endDate;
								
			},
			
			
			init:function(){
				var _self = this;
				_self.initEvents();
			}
	
	}.init();	
});