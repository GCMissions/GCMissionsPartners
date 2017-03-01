$(function(){
	
	var couponDetail = {
			initEvents:function(){
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					idField : "couponId",
				    url: 'coupon/recordList',
				    sidePagination:'server',
				    pagination : true,
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	return {
				    		couponId : $("#couponDetailId").val()
				    	}
				    },
				     
				    columns: [
				        {
							width:50,
							align: 'center',
							formatter:function(value,row,index){  
								return index+1;
							}
						} ,
				        {
				            field: 'phone',
				            align: 'center'
				        } ,
				        {
				            field: 'memberName',
				            align: 'center'
				        } ,
				        {
				            field: 'createDate',
				            align: 'center'
				        } ,
				        {
				            field: 'usedDate',
				            align: 'center'
				        } ,
				        {
				            field: 'orderId',
				            align: 'center'
				        }
				     ]
				});
				
				var couponType = $(".couponType").val();
				if(couponType == 2) {
					$(".tab_3").removeClass("hidden");
					var couponId = $("#couponDetailId").val();
					$.ajax({
						type: "GET",
						url: urlPrefix + "coupon/couponCodeList/" + couponId,
						contentType: "application/json;charset=utf-8",
						dataType: "json",
						success: function(message) {
							if(message.code=="ACK"){
								var dataList = message.data;
								var html = "";
								_.each(dataList,function(data,index){
									html = html + "<div>"+data+"</div>";
								});
								$(".cooperate").html(html);
							}
						},
						error: function(message) {
							alert(message);
						}
					});
				}
				
				
				
				
			},
			
			typeShowDate : function() {
				var type = $('.couponType').val();
				if(type==3) {
					$(".rechargeCoupon").hide();
				}else {
					$(".rechargeCoupon").show();
				}
			},
			
			init:function(){
				var _self = this;
				_self.initEvents();
				_self.typeShowDate();
			}
	}.init();
	
	
	
});