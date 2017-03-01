$(function() {
	var memberList = {
			init : function() {
				var _self = this;
				_self.initEvents();
			},
			initEvents : function(){
				var date = new Date();
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				var day = date.getDate();
	
				$('#csDate,#ceDate').datetimepicker({
					minView: 'month',
					format: 'yyyy-MM-dd',
					language: 'ch',
					endDate: year+'-'+month+'-'+day,
					autoclose : true,
					todayBtn : true
				});
				// 创建日期
				$('#csDate').on('changeDate',function(){
					$('#ceDate').datetimepicker('setStartDate', $('#csDate').val());
				});

				// 结束日期
				$('#ceDate').on('changeDate',function(){
					if ($('#ceDate').val()) {
						$('#csDate').datetimepicker('setEndDate', $('#ceDate').val());
					}else{
						$('#csDate').datetimepicker('setEndDate', year+'-'+month+'-'+day);
					};
				});
				
				// 列表
				var bootTable = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					refreshBtn : $('#searchBtn'), 
					pagination : true,
					pageSize : 10,
					url: 'member/list',
					sidePagination:'server',
					queryAddParams: function() {
						var queryObj =  {
								startDate:		$.trim($('#csDate').val()),
								endDate:		$.trim($('#ceDate').val()),
								mobile:	        $.trim($('#mobile').val()),
								custName:		$.trim($('#nickName').val()),
								vip:			$('#vip').val(),
								sex     : 		$('#sex').val()
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
						field: 'mobile',
						title: '手机号码',
						align: 'center',
						width: '20%'
					} , {
						field: 'custName',
						title: '昵称  ',
						align: 'center',
						width: '20%'
					},{
						field: 'sex',
						title: '性别',
						align: 'center',
						width:'10%'
					} , {
						field: 'vip',
						title: '是否VIP',
						align: 'center',
						width: '10%'
					} , {
						field: 'createDate',
						title: '注册时间',
						align: 'center',
						width: '20%'
					} , {
						field: 'memberId',
						align: 'center',
						title: '操作',
						width: '10%',
						formatter: function(value,row,index){
							return "<a href='" + urlPrefix + "member/detail/" + value + "/' class='detailItem' data-id='"+value+"'>详情</a>"
							+ "<a style='margin-left: 10%;' href='" + urlPrefix + "member/order/" + value + "/' class='orderItem' data-id='"+value+"'>消费</a>";
						} 
					}]
				});
			}
	}.init();
})