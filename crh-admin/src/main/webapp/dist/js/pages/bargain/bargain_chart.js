$(function(){
	var bargainChart = {
			$dataList : $('#dataList'),
	        bootTable : void 0,
	        bindEvents : function(){
	        	//excel导出
				$('#export').click(function(){
					//导出
					BootstrapDialog.show({
			            title: "导出",
			            type : BootstrapDialog.TYPE_WARNING,
			            message: message('确认导出砍价活动详情表吗？'),
			            draggable: true,
			            size : BootstrapDialog.SIZE_SMALL,
			            buttons: [{
			                label: '确认',
			                cssClass: 'btn-primary',
			                action: function(dialog) {
			                	dialog.close();
			                	$("#rpForm").submit();
			                }
			            }, {
			                label: '取消',
			                action: function(dialog) {
			                    dialog.close();
			                }
			            }]
			        });
				});
	        },
	        init : function() {
	        	this.bindEvents();
				this.bootTable = $.GLOBAL.utils.loadBootTable({
					table : this.$dataList,
					idField : "",
					url: 'bargain/chart',
					sidePagination:'server',
					pageSize : 10,
					pagination : true,
					currentPage: 1,
					queryParamsType: "limit",
					queryAddParams: function() {
						return {
							bargainId: $("#bargainId").val(),
							productName: $("#productName").val()
						}
					},
					columns: [
						{
							title:"排名",
							width: 50,
							field: 'rownum',
							align: 'center'
						} ,
						{
							title:"用户昵称/姓名",
							field: 'bargainName',
							align: 'center'
						} ,
						{
							title:"手机号",
							field: 'phone',
							align: 'center'
						} ,
						{
							title:"原价（元）",
							field: 'price',
							align: 'center'
						} ,
						{
							title:"现价（元）",
							field: 'currentPrice',
							align: 'center',
						} ,
						{
							title:"底价（元）",
							field: 'basePrice',
							align: 'center'
						} ,
						{
							title:"用户自身转发次数",
							field: 'shareTotal',
							align: 'center'
						} ,
						{
							title:"帮砍人数",
							field: 'bargainNum',
							align: 'center'
						}
					 ]
				});
			},
	};
	
	bargainChart.init();
});