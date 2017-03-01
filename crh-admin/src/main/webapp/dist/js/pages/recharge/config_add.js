$(function(){
	
	var configAdd = {
			initEvents:function(){
				
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
				    refreshBtn : $('#refreshRecord'),
				    url: 'rechargeConfig/list',
				    sidePagination:'server',
				    pagination : true,
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	return {
				    	}
				    },
				   
				     
				    columns: [				        
				        {
				            field: 'amountYuan',
				            align: 'center'
				        } ,
				        {
				            field: 'couponDtos',
				            formatter:function(value,row,index){
				            	var result = "<div>";
				            	_(value).each(function(v, k) {
				            		result+="<div class='div_big'><div class='div_small'>"+v.couponId+"</div><div class='div_small'>"+v.couponName+"</div><div class='div_small'>"+v.valueYuan+"</div><div class='div_small'>"+v.num+"</div></div>";
				            	});
				            	result+="</div>";
				            	return result;
				            }
				        },
				        {
				        	field: 'configId',
				            align: 'center',
				        	width : 200,
				            formatter:function(value,row,index){  
				               
				            	return ' <a  title="编辑" href="detail/'+row.configId+'" class="datailItem" data-id="'+row.configId+'"> <i class="fa fa-edit"  style="font-size:20px"></i></a>'
			                    +' <a  title="删除" href="#" class="removeItem" data-id="'+row.configId+'"> <i class="fa fa-trash"  style="font-size:20px"></i></a>'
			                    ;
				            } 
				        } 
				     ]
				
				});
				
				$(".fixed-table-pagination").addClass("hidden");
				
				
				$('#dataList').on("click", "a.removeItem", function() {
					var configId = this.getAttribute('data-id');
					var isConfirm = window.confirm("确定要删除此类型吗?");
					if(isConfirm){
						$.ajax({
							type: "GET",
							dataType: "json",
							contentType: "application/json;charset=utf-8",
							url:"delete/"+configId,
							success: function(message){
								if(message.code=="ACK"){
									table.refresh();
								}
							},
							error:function(message){
								//alert('shibai');
							}
						});
					}
				});	
				
				
			},
			init:function(){
				var _self = this;
				_self.initEvents();
			}
	}.init();
	
});