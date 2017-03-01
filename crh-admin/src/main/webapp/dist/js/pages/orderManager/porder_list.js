var goodsApp = {
	$dataList : $('#dataList'),
	init : function(cateId) {
		this.initTimeSelect();
		this.initTable();

		this.bindEvents();
	},
	bindEvents : function() {
	},
	initTimeSelect : function() {
		$.pages.initDateTime();
	},
	initTable : function() {
		this.bootTable = $.GLOBAL.utils
				.loadBootTable({
					table : this.$dataList,
					refreshBtn : $('#refreshRecord'),
					pagination : true,
					pageSize : 10,
					url : 'porder/search',
					sidePagination : 'server',
					queryAddParams : function() {
						var queryObj = {
							orderId : $.trim($('#orderId').val()),
							type : $("#type").val(),
							startDate : $('#startDate').val(),
							endDate : $('#endDate').val(),
						};
						if (queryObj.startDate
								&& $.GLOBAL.utils.isDate(queryObj.startDate)) {
							queryObj.startDate += " 00:00:00";
						}
						if (queryObj.endDate
								&& $.GLOBAL.utils.isDate(queryObj.endDate)) {
							queryObj.endDate += " 23:59:59";
						}
						return queryObj;
					},
					columns : [
							{
								width : 50,
								align : 'center',
								formatter : function(value, row, index) {
									return index + 1;
								}
							},
							{
								field : 'orderId',
							},
							{
								field : 'createDate',
							},
							{
								field : 'assignDate',
							},
							{
								field : 'rewardDate',
							},
							{
								field : 'rewardType',
							},
							{
								field : 'orgName',
							},
							{
								field : 'amount',
							},
							/*{
								field : 'orderId',
								align : 'center',
								formatter : function(value, row, index) {
									return '<a title="查看订单" href="'
											+ urlPrefix
											+ 'order/view/'
											+ row.orderId
											+ '" class="viewItem" data-id="'
											+ row.orderId
											+ '"><i class="fa fa-eye" style="font-size:20px"></i></a>';
								}
							} */]
				});
	}
};