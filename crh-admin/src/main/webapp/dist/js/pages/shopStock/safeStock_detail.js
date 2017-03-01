$(function() {
	var safeStockDetail = {
			saveStockURL : 'orgStock/setting',
			init :function(){
				this.bindEvents();
				this.initInputMask();
			},
			
			bindEvents : function(){
				var _self = this;
				$('#back').on("click", function() {
					window.location.href = urlPrefix + "safeStock/";
				});
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
				    pagination : true,
				    pageSize : 5,
				});
				$('#dataList').on('click','.fa-edit', function(event) {
					var $stockTr = $(this).parents('tr');
					var $stockTd = $stockTr.find('.stockNum');
					var $safeNumTd = $stockTr.find('.safeNum');
					$stockTd.find('.stockNumVal').hide();
					$safeNumTd.find('.safeNumVal').hide();
					$stockTd.find('.stockNumEdit').show();
					$safeNumTd.find('.safeNumEdit').show();
					$stockTd.find('.stockNumEdit').val($stockTd.find('.stockNumVal').html());
					$safeNumTd.find('.safeNumEdit').val($safeNumTd.find('.safeNumVal').html());
					$stockTr.find('.fa-check,.fa-remove').show();
					$stockTr.find('.fa-edit').hide();
				});
				$('#dataList').on('click', '.fa-check',function() {
					var $stockTr = $(this).parents('tr');
					var $stockTd = $stockTr.find('.stockNum');
					var $safeNumTd = $stockTr.find('.safeNum');
					var stockDetail = {};
					stockDetail.stockId = $stockTr.find('.stockId').html();
					stockDetail.safeStockSetting = $safeNumTd.find('.safeNumEdit').val();
					stockDetail.standardStockSetting = $stockTd.find('.stockNumEdit').val();
					$.ajax({
						  type: 'POST',
						  url: urlPrefix + 'safeStock/setting',
						  dataType: 'json',
						  contentType: 'application/json',
						  data:  JSON.stringify(stockDetail)
			            }).done(function(result) {
			            	$stockTd.find('.stockNumEdit').val(stockDetail.standardStockSetting);
			            	$safeNumTd.find('.safeNumEdit').val(stockDetail.safeStockSetting);
			            	$stockTd.find('.stockNumVal').html(stockDetail.standardStockSetting);
			            	$safeNumTd.find('.safeNumVal').html(stockDetail.safeStockSetting);
			            	//location.reload() ;
			        	 }) .fail(function(result) {
			        		 //TDO
			        	 });
					$stockTd.find('.stockNumVal').show();
					$safeNumTd.find('.safeNumVal').show();
					$stockTd.find('.stockNumEdit').hide();
					$safeNumTd.find('.safeNumEdit').hide();
					$stockTr.find('.fa-check,.fa-remove').hide();
					$stockTr.find('.fa-edit').show();
				});
				$('#dataList').on('click', '.fa-remove',function() {
					var $stockTr = $(this).parents('tr');
					var $stockTd = $stockTr.find('.stockNum');
					var $safeNumTd = $stockTr.find('.safeNum');
					/*var stockDetail = {};
					stockDetail.stockId = $stockTr.find('.stockId').html();
					stockDetail.stockSetting = $stockTd.find('.stockNumEdit').val();*/
					$stockTd.find('.stockNumVal').show();
					$safeNumTd.find('.safeNumVal').show();
					$stockTd.find('.stockNumEdit').hide();
					$safeNumTd.find('.safeNumEdit').hide();
					$stockTr.find('.fa-check,.fa-remove').hide();
					$stockTr.find('.fa-edit').show();
				});
			},
			
			initInputMask : function() {
		    	$('.safeNumEdit').inputmask({
		    		"mask" : "9",
		    		repeat : 6,
		    		"greedy": false
		    	});
		    	$('.stockNumEdit').inputmask({
		    		"mask" : "9",
		    		repeat : 6,
		    		"greedy": false
		    	});
		    },
			
			
	};
	
	safeStockDetail.init();
});