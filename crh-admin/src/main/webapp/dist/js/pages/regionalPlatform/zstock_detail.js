$(function() {

	
	$.GLOBAL.utils.loadBootTable({
		table : $('#dataList'),
	    refreshBtn : $('#search'),
	    url: 'terminalStock/zStocklist',
	    sidePagination:'server',
	    pagination : true,
	    queryParamsType: "limit",
	    queryAddParams: function() {
	    	return {
	    		 orgCode: $("#orgCode").val(),
	    		 orgType:'Z'
	    	}
	    },
	    columns: [
	  	        {
	  	            field:'goodCode'
	  	        },
	  	        {
	  	            field:'name'
	  	        }, 
	  	        {
	  	            field:'priceYuan'
	  	        }, 
	  	        {
	  	            field: 'createDate'
	  	        },
	  	        {
	  	            field:'stockNum'
	  	        },
	  	        {
	  	            field:'safeNum'
	  	        },
	  	      	{
	  	            field:'status'
	  	        }
	  	     ]
	});
});