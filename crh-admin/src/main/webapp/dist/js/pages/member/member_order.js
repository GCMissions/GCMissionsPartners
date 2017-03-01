$(function() {
	var memberOrder = {
			init : function() {
				var _self = this;
				_self.initEvents();
			},
			initEvents : function(){
				// 列表
				var bootTable = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					pagination : true,
					pageSize : 10,
					url: 'member/order/list',
					sidePagination:'server',
					queryAddParams: function() {
						var queryObj =  {
								memberId : $("#memberId").val()
						} ;
						return queryObj;
					},
					columns: [{
                		title: '订单号',
                		width: '10%',
                		align: 'center',
                		field: 'orderId'
					} ,{
						field: 'createDate',
						title: '创建时间',
						align: 'center',
						width: '10%'
					} , {
						field: 'mobile',
						title: '手机号',
						align: 'center',
						width: '10%'
					},{
						field: 'productCode',
						title: '商品编号',
						align: 'center',
						width:'10%',
						formatter:function(value,row,index){
	  	           			var result = "";	  	        	
	  	           			var str = value;   
	  	           			var arr = str.split('#&amp;#');
							for (var i = 0; i < arr.length; i++) {
								var str = arr[i];
								re = new RegExp(" ", "g");
								var newstr = str.replace(re, "&nbsp;");
								var span = "<p style='color:#3c8dbc;' ";
								span += "title='" + newstr + "' class='trackRecord-p'>" + newstr + "</p>";
								result += span;
							}
	  	        	  
	  	           			return result;
	  	           		}
					} , {
						field: 'productName',
						title: '商品名称',
						align: 'center',
						width: '10%',
						formatter:function(value,row,index){
	  	           			var result = "";	  	        	
	  	           			var str = value;   
	  	           			var arr = str.split('#&amp;#');
							for (var i = 0; i < arr.length; i++) {
								var str = arr[i];
								re = new RegExp(" ", "g");
								var newstr = str.replace(re, "&nbsp;");
								var span = "<p style='color:#3c8dbc;' ";
								span += "title='" + newstr + "' class='trackRecord-p'>" + newstr + "</p>";
								result += span;
							}
	  	        	  
	  	           			return result;
	  	           		}
					} , {
						field: 'orgName',
						title: '服务商',
						align: 'center',
						width: '10%',
						formatter:function(value,row,index){
							var result = "";
	  	        	
							var str = value;   
							var arr = str.split(',/,');
							for (var i = 0;i < arr.length; i++) {    
								var span = "<p ";
								if (arr[i] == arr[i-1] && i != 0){
									span += "style ='visibility:hidden;' "
								}
								span += "title='"+arr[i]+"' class='trackRecord-s'>"+arr[i]+"</p>";
	  	        	
								result += span;
							}
	  	        	  
							return result;
	  	           		}
					} , {
						field: 'productType',
						align: 'center',
						title: '商品类型',
						width: '10%',
						formatter:function(value,row,index){
							var result = "";
	  	        	
							var str = value;   
							var arr = str.split('/');
							for (var i = 0;i < arr.length - 1;i++) {    
								var span = "<p ";
								if (arr[i] == arr[i-1] && i != 0){
									span += "style ='visibility:hidden;' "
								}
								span += " >"+arr[i]+"</p>";
	  	        	
								result += span;
							}
	  	        	  
							return result;
	  	           		}
					} , {
						field: 'orderAmount',
						title: '金额（元）',
						align: 'center',
						width: '10%'
					} , {
						field: 'orderStatus',
						title: '状态',
						align: 'center',
						width: '10%'
					} , {
						field: 'vip',
						title: 'VIP',
						align: 'center',
						width: '5%'
					} , {
						field: 'orderId',
						title: '操作',
						align: 'center',
						width: '5%',
						formatter: function(value,row,index){
							if (row.orderType == "0") {
								result = "<a href='" + urlPrefix + "order/view/" + value + "/0/" + $("#memberId").val() + "'>查看</a>";
							} else {
								result = "<a href='" + urlPrefix + "kdOrder/view/" + value + "/0/" + $("#memberId").val() + "'>查看</a>";
							}
							return result;
						}
					}]
				});
				
				$("#orderBack").click(function(){
					window.location.href=urlPrefix+"member/order/" + $("#memberId").val();
				});
			}
	}.init();
})