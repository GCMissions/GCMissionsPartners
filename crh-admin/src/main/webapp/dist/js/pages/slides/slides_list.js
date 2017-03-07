$(function(){
	var slideList = {
			init : function() {
				var _self = this;
				_self.initEvents();
			},
			initEvents : function(){
				var bootTable = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					pagination : true,
					pageSize : 10,
					url: 'slides/list',
					sidePagination:'server',
					queryAddParams: function() {
						
					},
					columns: [{
						field: 'index',
                		title: 'Order',
                		align: 'center',
                		width: '20%'
					} ,{
						field: 'image',
						title: 'Slide',
						align: 'center',
						width: '20%'
					} , {
						field: 'displayed',
						title: 'Displayed/Hidden',
						align: 'center',
						width: '20%'
					}, {
						field: 'id',
						align: 'center',
						title: 'Action',
						width: '20%',
						formatter: function(value,row,index){
							var result = "";
							result += "<a href='" + urlPrefix + "slides/view/" + value + "' class='detailItem' data-id='"+value+"'>Edit</a>";
							if (row.index == "1") {
								result += "<a style='margin-left: 10%;' href='" + urlPrefix + "slides/sort/" + value + "/0' class='downItem' data-id='"+value+"'>Down</a>";
							}
							
							result += "<a style='margin-left: 10%;' href='" + urlPrefix + "slides/delete/" + value + "' class='deleteItem' data-id='"+value+"'>Delete</a>";
							return "<a href='" + urlPrefix + "member/detail/" + value + "/' class='detailItem' data-id='"+value+"'>Edit</a>"
							+ "<a style='margin-left: 10%;' href='" + urlPrefix + "member/order/" + value + "/' class='orderItem' data-id='"+value+"'>消费</a>";
						} 
					}]
				});
			}
	}.init();
})