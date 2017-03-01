var productApp = {
	init : function(cateId) {
		this.$dataList = $('#dataList');
		this.initTable();
		$('#back').on("click", function() {
			window.location.href = urlPrefix + "/goods/";
		});
	},
	initTable : function() {
		this.bootTable = $.GLOBAL.utils.loadBootTable({
			table : this.$dataList,
			refreshBtn : $('#refreshRecord'),
			pagination : true,
			pageSize : 10,
			url : 'goods/searchProducts',
			sidePagination : 'server',
			queryAddParams : function() {
				var queryObj = {
					goodsId : $("#goodsId").val()
				};
				return queryObj;
			},
			columns : [ {
				width : 50,
				align : 'center',
				formatter : function(value, row, index) {
					return index + 1;
				}
			}, {
				field : 'productCode',
			}, {
				field : 'productName',
			}, {
				field : 'cateName',
			}, {
				field : 'brandName',
			}, {
				field : 'specNum',
			} ]
		});
	}
};