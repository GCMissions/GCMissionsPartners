$(function() {
	var orderReturnList;
	var payMentTypeText = {
			"1" : "余额支付",
			"2" : "支付宝",
			"3" : "微信"
	}
	var orderReturn_List = {
			init :function(){
				var date = new Date();
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				var day = date.getDate();
				var _self = this;
				
				$('#csDate,#ceDate').datetimepicker({
					minView: 'month',
					format: 'yyyy-MM-dd',
					language: 'ch',
					endDate: year+'-'+month+'-'+day,
					autoclose : true,
					todayBtn : true
				});
				
				_self.orderReturnList = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
				    //refreshBtn : $('#search'),
				    url: 'orderReturn/list',
				    sidePagination:'server',
				    pagination : true,
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	return {
				    		orderId: $("#orderId").val(),
				    		shipmentType: $("#shipmentType").val(),
				    		startDate: $("#csDate").val(),
				    		endDate: $("#ceDate").val(),
				    		status: $("#status").val(),
				    		phone: $("#phone").val()
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
				  	            field:'orderId'
				  	        },
				  	        {
				  	            field:'totalAmount'
				  	        }, 
				  	        {
				  	            field:'memberName'
				  	        }, 
				  	        {
				  	            field: 'phone'
				  	        },
				  	        {
				  	            field:'createDate'
				  	        },
				  	        {
				  	            field:'status'
				  	        },
				  	        {
				  	            field:'shipmentType'
				  	        },
				  	        {
				  	            field:'orgName'
				  	        },
				  	      {
					            checkbox: false,
					            formatter:function(value,row,index){
					            	var viewUrl = $.GLOBAL.config.urlPrefix + 'order/view/' + row.orderId +'?type=returnOrderView';
					            	var barcodeUrl = $.GLOBAL.config.urlPrefix +"barcode/index/8/"+row.orderId;
					            	var html = ' <a  title="【查看】" target="_self" href="'+ viewUrl +'" class="editItem"> <i class="fa fa-eye "  style="font-size:20px"></i></a>'; ;
					            	if(row.status == "待退货"&&row.returnType != "0"){
					            		html += '<a  title="【扫码】"  target="_self" href="'+ barcodeUrl +'">【扫码】</a><input type="hidden" class="hiddenOrgId" value="'+row.orderId+'" >';
					            	}
					            	return html;
					            } 
				  	        }
				  	     ]
				});
				
				//搜索
				$('#search').on("click", function() {
					_self.orderReturnList.options.queryAddParams = function(){
						return {
							orderId: $("#orderId").val(),
				    		shipmentType: $("#shipmentType").val(),
				    		startDate: $("#csDate").val(),
				    		endDate: $("#ceDate").val(),
				    		status: $("#status").val(),
				    		phone: $("#phone").val()
				    	}
					};
					_self.orderReturnList.refresh();
				});
				
				this.bindEvents();
				
			},
			
			bindEvents : function(){
				var _self = this;
				
				//创建日期
				$('#csDate').on('changeDate',function(){
					$('#ceDate').datetimepicker('setStartDate', $('#csDate').val());
					if($('#csDate').val()=="" && $("#csDate").next().css('display') == 'inline-block'){
						$("#csDate").next().css('display','none');
					}else{
						$("#csDate").next().css('display','inline-block');
					}
				});

				//结束日期
				$('#ceDate').on('changeDate',function(){
					if ($('#ceDate').val()) {
						$('#csDate').datetimepicker('setEndDate', $('#ceDate').val());
					}else{
						$('#csDate').datetimepicker('setEndDate', year+'-'+month+'-'+day);
					};
					if($('#ceDate').val()=="" && $("#ceDate").next().css('display') == 'inline-block'){
						$("#ceDate").next().css('display','none');
					}else{
						$("#ceDate").next().css('display','inline-block');
					}
				});
				
				
				
			},
			
			initInputMask : function() {
		    	$('#phone').inputmask({
		    		"mask" : "9",
		    		"greedy": false
		    	});
		    },
			
	};
	
	orderReturn_List.init();
});