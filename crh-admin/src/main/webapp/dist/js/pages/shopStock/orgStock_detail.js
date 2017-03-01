$(function() {
	var orgStockDetail = {
			saveStockURL : 'orgStock/setting',
			init :function(){
				this.bindEvents();
				this.initInputMask();
			},
			
			bindEvents : function(){
				var _self = this;
				$('#back').on("click", function() {
					window.location.href="../../orgStock/";
				});
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
				    pagination : true,
				    pageSize : 5,
				});
				$('#dataList').on('click','.fa-edit', function(event) {
					var $stockTr = $(this).parents('tr');
					var $stockTd = $stockTr.find('.stockNum');
					$stockTd.find('.stockNumVal').hide();
					$stockTd.find('.stockNumEdit').show();
					$stockTd.find('.stockNumEdit').val($stockTd.find('.stockNumVal').html());
					$stockTr.find('.fa-check,.fa-remove').show();
					$stockTr.find('.fa-edit').hide();
				});
				$('#dataList').on('click', '.fa-check',function() {
					var $stockTr = $(this).parents('tr');
					var $stockTd = $stockTr.find('.stockNum');
					var $orgStateTd = $stockTr.find('.orgState');
					var stockDetail = {};
					stockDetail.stockId = $stockTr.find('.stockId').html();
					stockDetail.stockSetting = $stockTd.find('.stockNumEdit').val();
					$.ajax({
						  type: 'POST',
						  url: '../../orgStock/setting/'+stockDetail.stockId+'/'+stockDetail.stockSetting,
						  dataType: 'json',
						  contentType: 'application/json'/*,
						  data:  stockDetail*/
			            }).done(function(result) {
			            	var stockNum = stockDetail.stockSetting;
			            	var safeNum = $stockTr.find('.safeNum').html();
			            	$stockTd.find('.stockNumEdit').val(stockNum);
			            	$stockTd.find('.stockNumVal').html(stockNum);
			            	if(stockNum < safeNum)
			            		$orgStateTd.html("预警");
			            	else
			            		$orgStateTd.html("正常");
			            	location.reload() ;
			        	 }) .fail(function(result) {
			        		 //TDO
			        	 });
					$stockTd.find('.stockNumVal').show();
					$stockTd.find('.stockNumEdit').hide();
					$stockTr.find('.fa-check,.fa-remove').hide();
					$stockTr.find('.fa-edit').show();
				});
				$('#dataList').on('click', '.fa-remove',function() {
					var $stockTr = $(this).parents('tr');
					var $stockTd = $stockTr.find('.stockNum');
					var stockDetail = {};
					stockDetail.stockId = $stockTr.find('.stockId').html();
					stockDetail.stockSetting = $stockTd.find('.stockNumEdit').val();
					$stockTd.find('.stockNumVal').show();
					$stockTd.find('.stockNumEdit').hide();
					$stockTr.find('.fa-check,.fa-remove').hide();
					$stockTr.find('.fa-edit').show();
				});
			},
			
			initInputMask : function() {
		    	$('.stockNumEdit').inputmask({
		    		"mask" : "9",
		    		repeat : 6,
		    		"greedy": false
		    	});
		    },
			
			
	};
	
	orgStockDetail.init();
});