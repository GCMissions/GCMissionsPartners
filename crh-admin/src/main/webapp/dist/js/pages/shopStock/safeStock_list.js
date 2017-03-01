$(function() {
	var safeStock_list = {
			init : function(){
				var _self = this;
				
				var safeStockList = $.GLOBAL.utils.loadBootTable({
					table : $('#safeStockList'),
				    url: 'safeStock/list',
				    sidePagination:'server',
				    pagination : true,
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	return {
							orgCode: $("#orgCode").val(),
							orgName: $("#orgName").val(),
							orgCate: $("#orgCate").val()
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
				  	            field:'orgCate'
				  	        }, 
				  	        {
				  	            field: 'safeNum'
				  	        },
				  	        {
				  	            field:'standardNum'
				  	        },
				  	      	{
					            align: 'center',
					            checkbox: false,
					            formatter:function(value,row,index){  
					                return ' <a  title="【查看】" target="_self" href="detail/'+row.orgId+'" class="editItem"> <i class="fa fa-eye"  style="font-size:20px"></i></a>';
					            } 
				  	        }
				  	     ]
				});
				
				$('#search').on("click", function() {
					safeStockList.options.queryAddParams = function(){
						return {
							orgCode: $("#orgCode").val(),
							orgName: $("#orgName").val(),
							orgCate: $("#orgCate").val()
						}
					};
					safeStockList.refresh();
				});
				
				_self.bindEvents();
			},
			
			bindEvents : function(){
				var _self = this;
				
				
			}
	};
	
	safeStock_list.init();
});
