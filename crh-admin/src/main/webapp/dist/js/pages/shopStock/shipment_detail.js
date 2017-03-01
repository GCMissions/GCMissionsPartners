$(function() {
	var shipment_detail = {
			listUrl    : "platformShipment/",
			init :function(){
				this.bindEvents();
			},
			
			bindEvents : function(){
				var _self = this;
				$('#back').on("click", function() {
					window.location = "../../platformShipment/";
				});
				
				$('#shipmentButton').on("click", function() {
					var orderId = $('#orderIdValue').val();
					_self.shipmentP(orderId);
				});
				
				window.onload = function(){
					_self.showShipmentButton();
				};
			},
			
			showShipmentButton : function(){
				var currentOrgCate = $('#orgCateValue').val();
				var currentShipmentStatus = $('#shipmentStatusValue').val();
				if(currentOrgCate=="区域平台商" && currentShipmentStatus=="已创建")
					$('#shipmentButton').show();
				else
					$('#shipmentButton').hide();
			},
			
			shipmentP : function(orderId){
				var orderId = orderId;
				$.ajax({
					url : '../../platformShipment/deliverGoods/'+orderId+'',
					type : 'POST',
					dataType : 'json',
					contentType : 'application/json',
					success: function(){
						$('body').loadingInfo({
		    				type : "success", 
		    				text : "发货成功",
		    				callBack : function() {
		    					location.reload();
		    				}
		    			});
						/*$('body').loadingInfo('success', '发货成功');
						window.location= '../platformShipment/';*/
					}
				});
			}
	};
	
	shipment_detail.init();
});