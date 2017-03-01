$(function(){
	var financeReportDetail = {
			initEvents:function(){
				var _self = this;
				
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
	        	    refreshBtn : $('#refreshRecord'),
				    url: 'financeReport/getFinanceDetailReport',
				    sidePagination:'server',
				    pagination : true,
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	var result= {
				    			orgId  : $("#orgId").val(),
				    			orgType: $("#orgType").val(), 
				    			beginDate: $("#csDate").val(), 
				    			endDate: $("#ceDate").val(), 	    		
							};
	                
						if($("#csDate").val()==""){delete result.beginDate}
						if($("#ceDate").val()==""){delete result.endDate}
						return result;
				    },
				     
				    columns: [
							{
								width : 50,
								align: 'center',
							    formatter:function(value,row,index){  
							    	return index+1;
							    }
							} ,
							{
							    field: 'orderId',
							} ,
							{
							    field: 'shipmentType',
							    formatter:function(value,row,index){
							    	var shipmentName = "";
							    	switch(value){
							    	case "2": 
							    		shipmentName = "配送";
							    		break;
							    	case "3":
							    		shipmentName = "自提";
							    		break;
							    	}
							    	return shipmentName;
							    }
							} ,
							 {
							    field: 'totalAmount'							 
							} ,
							{
							    field: 'couponAmount'						    
							} ,
							{
							    field: 'shipAmount'							   
							} ,
							{
							    field: 'actualAmount'							    
							} ,
							{
							    field: 'shipProfit'							    
							} ,
							{
							    field: 'prodProfit'							    
							} ,
							 {
							    field: 'pShipProfit'
							} ,
							{
							    field: 'pProdProfit'
							} ,
							{
							    field: 'zShipProfit'
							} ,
							{
							    field: 'zProdProfit'
							} 
				     ],
				     onLoadSuccess:function(data){
		            	 if(data){
		            		 $("#totalFine").html(data.totalFine+"元");
		            		 $("#totalReward").html(data.totalReward+"元");
		            		 $("#totalReferee").html(data.totalReferee+"元");
		            	 }
		             }
				    
				});
			
			},
			
			init:function(){
				var _self = this;
				_self.initEvents();
			}
	
	}.init();	
});