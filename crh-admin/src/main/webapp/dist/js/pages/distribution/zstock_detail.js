$(function() {
	
	$.GLOBAL.utils.loadBootTable({
		table : $('#recordDataList'),
	    refreshBtn : $('#search'),
	    url: 'distribution/recordlist',
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