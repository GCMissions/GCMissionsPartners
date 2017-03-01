$(function() {
	var memberDetail = {
			$orderDetail : $('#customOrderDetailDiv'),
			$orderList :$('#customOrderListDiv'),
			$orderBackButton : $('#orderListBack'),
			$passwordResetButton : $('#passwordReset'),
			$back : $('#back'),
			$passwordResetButton : $('#passwordReset'),
			init :function(){
				
				this.bindEvents();
			},
			
			bindEvents : function(){
				var _self = this;
				
				$('#back').on("click", function() {
					window.location.href= urlPrefix + "memberMng/";
				});
				
				$('#customOrderdataList').on('click','.fa-eye', function(event) {
					var orderId = $(this).closest('tr').find('.orderId').html();
					_self.showOrderDetial(orderId);
				});
				
				$('.nav-tabs li').on('click', function() {
					var currentTab = $(this).find('a').html();
					_self.test(currentTab);
				});
				
				$('#orderListBack').on('click', function() {
					_self.backToOrderList();
				});
				
				$('#passwordReset').on('click', function() {
					var memberId = $('#memberIdVal').val();
					_self.confirmReset(memberId);
				});
				
				window.onload = function(){
					_self.test();
				};
				
				
			},
			
			showOrderDetial : function(orderId){
				var _self = this;
				var orderId = orderId;
				_self.$orderList.hide();
				_self.$back.hide();
				_self.$orderDetail.show();
				_self.$orderBackButton.show();

            	$.ajax({
					  type: 'POST',
					  url: urlPrefix + 'memberMng/detail/orderDetail/'+ orderId +'',
					  dataType: 'json',
					  contentType: 'application/json',
					  //data:  JSON.stringify(stockDetail)
		            }).done(function(result) {
		            	var data = result.data;
		            	$('#customOrderId').html(data.orderId);
		            	$('#customOrderStatus').html(data.status);
		            	$('#customOrderTotalNum').html(data.totalNum);
		            	$('#customOrderCreatDate').html(data.createDate);
		            	$('#customOrderTotalAmount').html(data.totalAmount);
		            	$('#customOrderCouponAmount').html(data.couponAmount);
		            	$('#customOrderActualAmount').html(data.actualAmount);
		            	$('#customOrderPaymentType').html(data.paymentType);
		            	$('#customOrderPaymentStatus').html(data.paymentStatus);
		            	$('#customOrderShipmentType').html(data.shipmentType);
		            	$('#customOrderRecivingDate').html(data.receivingDate);
		            	$('#customOrderContact').html(data.contact);
		            	$('#customOrderAddress').html(data.address);
		            	$('#customOrderPhone').html(data.phone);
		            	$('#customOrderOrgId').html(data.orgCode);
		            	$('#customOrderOrgName').html(data.orgName);
		            	//location.reload() ;
		        	 }) .fail(function(result) {
		        		 //TDO
		        	 });
            	var custmoOrderDetailList = $.GLOBAL.utils.loadBootTable({
					table : $('#custmoOrderDetailList'),
				    //refreshBtn : $('#search'),
				    url:'memberMng/detail/orderDetail/'+ orderId +'',
				    pagination : false,
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	return {}
				    },
				    columns: [
					  	    {
					  	    	align: 'center',
						        checkbox: false,
						        formatter:function(value,row,index){
						        	var no = index+1;
						        	return ' <span class="no"> '+ no +' </span>';
						        	}   	
					  	    }, 
				  	        {
				  	            field:'productCode'
				  	        },
				  	        {
				  	            field:'productName'
				  	        }, 
				  	        {
				  	            field:'brandName'
				  	        },
				  	        {
				  	            field:'cateName'
				  	        },
				  	        {
				  	            field:'costPrice'
				  	        },
				  	        {
				  	            field:'buyNum'
				  	        },
				  	     ]
				});
			},
			
			backToOrderList : function(){
				this.$orderList.show();
				this.$back.show();
				this.$orderDetail.hide();
				this.$orderBackButton.hide();
			},
			
			test : function(currentTab){
				var activeTab;
				if(currentTab)
					activeTab = currentTab;
				else
					activeTab = $('.nav-tabs .active a').html();
				
				if(activeTab == "基本信息")
					this.$passwordResetButton.show();
				else
					this.$passwordResetButton.hide();
			},
			
			confirmReset : function(memberId){
		    	var _self = this;
		    	
		    	_self.dialog =  BootstrapDialog.show({
		            title: '重置密码',
		            type : BootstrapDialog.TYPE_WARNING,
		            message: "消费者密码重置后原密码无效，是否确认将密码重置为123456",
		            draggable: true,
		            size : BootstrapDialog.SIZE_SMALL,
		            buttons: [{
		                label: '确认',
		                cssClass: 'btn-primary ',
		                action: function(dialog) {
		                	dialog.close();
		                	_self.resetPassword( memberId);
		                }
		            }, {
		                label: '取消',
		                action: function(dialog) {
		                    dialog.close();
		                }
		            }]
		        });
		    },
			
			resetPassword : function(memberId){
				$.ajax({
					  type: 'POST',
					  url: urlPrefix + 'memberMng/resetPwd/'+ memberId +'',
					  dataType: 'json',
					  contentType: 'application/json',
					  //data:  JSON.stringify(stockDetail)
		            }).done(function(result) {
		            	$('body').loadingInfo({
		    				type : "success", 
		    				text : "密码重置成功",
		    				callBack : function() {
		    					//window.location= '../platformShipment/';
		    					//window.location.href=urlPrefix + that.listUrl;
		    				}
		    			});
		            	//location.reload() ;
		        	 }) .fail(function(result) {
		        		 //TDO
		        	 });
			}
			
	};
	
	memberDetail.init();
});