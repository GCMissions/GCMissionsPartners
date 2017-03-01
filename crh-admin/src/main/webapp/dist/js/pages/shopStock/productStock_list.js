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
	
	$('#search').on("click", function() {
		table.options.queryAddParams = function(){
			return {
				 goodsName: $("#goodsName").val(),
				 goodsCode: $("#goodsCode").val()
			}
		};
		table.refresh();
	});
	var table = $.GLOBAL.utils.loadBootTable({
		table : $('#dataList'),
	    url: 'productStock/list',
	    sidePagination:'server',
	    pagination : true,
	    queryParamsType: "limit",
	    queryAddParams: function() {
	    	return {
				 goodsName: $("#goodsName").val(),
				 goodsCode: $("#goodsCode").val()
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
	  	            field:'price'
	  	        },
	  	        {
	  	            field:'productStockNum'
	  	        },
	  	      	{
		            align: 'center',
		            checkbox: false,
		            formatter:function(value,row,index){  
		                return ' <a  title="【查看】" target="_self" href="detail/'+row.goodsId+'" class="editItem"> <i class="fa fa-eye"  style="font-size:20px"></i></a>';
		            } 
	  	        }
	  	     ]
	});
	
});
