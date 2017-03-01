$(function() {
	$('#warnStock').on("click", function() {
		table.options.queryAddParams = function(){
			return {
				orgType:'P',
				isWarning:'T'
			}
		};
		table.refresh();
	});
	$('#search').on("click", function() {
		table.options.queryAddParams = function(){
			return {
				 name: $("#goodsName").val(),
	    		 sn: $("#goodsCode").val(),
	    		 status: $("#status").val(),
	    		 stockLow : $("#stockLow").val(),
	    		 stockHigh : $("#stockHigh").val(),
	    		 orgType:'P'
			}
		};
		table.refresh();
	});
	var table = $.GLOBAL.utils.loadBootTable({
		table : $('#dataList'),
	    url: 'regionalPlatform/list',
	    sidePagination:'server',
	    pagination : true,
	    queryParamsType: "limit",
	    queryAddParams: function() {
	    	return {
	    		 orgType:'P'
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
	  	            field:'goodCode'
	  	        },
	  	        {
	  	            field:'name'
	  	        }, 
	  	        {
	  	            field:'priceYuan'
	  	        }, 
	  	        {
	  	            field:'stockNum'
	  	        },
	  	        {
	  	            field:'safeNum'
	  	        },
	  	      	{
	  	            field:'status'
	  	        },
	  	      	{
		            align: 'center',
		            checkbox: false,
		            formatter:function(value,row,index){  
		                return ' <a  title="【查看】" target="_self" href="detail/'+row.id+'" class="editItem"> <i class="fa fa-eye"  style="font-size:20px"></i></a>';
		            } 
	  	        }
	  	     ]
	});
});