$(function() {
	var memberDetail = {
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
					url: 'member/coupon/list',
					sidePagination:'server',
					queryAddParams: function() {
						var queryObj =  {
								memberId : $("#memberId").val()
						} ;
						return queryObj;
					},
					columns: [{
                		title: '序号',
                		width: '10%',
                		align: 'center',
                		formatter:function(value,row,index){  
                        	return index+1; 
                        }
					} ,{
						field: 'couponName',
						title: '优惠券名称',
						align: 'center',
						width: '20%'
					} , {
						field: 'couponPrice',
						title: '优惠券金额 ',
						align: 'center',
						width: '10%'
					},{
						field: 'containDate',
						title: '获得时间',
						align: 'center',
						width:'20%'
					} , {
						field: 'effectiveDate',
						title: '有效期',
						align: 'center',
						width: '20%'
					} , {
						field: 'status',
						title: '优惠券状态',
						align: 'center',
						width: '10%'
					} , {
						field: 'useStatus',
						align: 'center',
						title: '使用状态',
						width: '10%'
					}]
				});
				
				$("#orderBack").click(function(){
					window.location.href=urlPrefix+"member/order/" + $("#memberId").val();
				});
			}
	}.init();
})