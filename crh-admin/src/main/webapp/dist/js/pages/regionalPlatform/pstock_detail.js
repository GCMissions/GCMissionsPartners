$(function() {
	
	$.GLOBAL.utils.loadBootTable({
		table : $('#dataList'),
	    refreshBtn : $('#search'),
	    url: 'regionalPlatform/recordlist',
	    sidePagination:'server',
	    pagination : true,
	    queryParamsType: "limit",
	    queryAddParams: function() {
	    	return {
	    		 stockId: $("#stockId").val()
	    	}
	    },
	    columns: [
	  	        {
	  	            field:'changeNum'
	  	        },
	  	        {
	  	            field:'operType'
	  	        }, 
	  	        {
	  	            field:'operDate'
	  	        }
	  	     ]
	});
});