$(function() {
	var memberList = {
			init :function(){
				var date = new Date();
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				var day = date.getDate();
				
				this.validator = $('#searchForm').validate({
		        	rules : {
		        		pointStart : {
		        			digits  : true,
		        			min : 1
		        		},
		        		pointEnd : {
		        			digits  : true,
		        			min : 1
		        		},
		        		balanceStart : {
		        			//number : true,
		        			price : true
		         		},
		         		balanceEnd : {
		        			//number : true,
		        			price : true
		         		}
		        	}, 
		        	messages : {
		        		pointStart : {
		        			digits  : ""
		        		},
		        		pointEnd : {
		        			digits  : ""
		        		},
		        		balanceStart : {
		        			//number : true,
		        			price : ""
		         		},
		         		balanceEnd : {
		        			//number : true,
		        			price : ""
		         		}
		        	}
		        	
		        });
				
				
				
				$('#csDate,#ceDate').datetimepicker({
					minView: 'month',
					format: 'yyyy-MM-dd',
					language: 'ch',
					endDate: year+'-'+month+'-'+day,
					autoclose : true,
					todayBtn : true
				});
				
				var memberDataList = $.GLOBAL.utils.loadBootTable({
					table : $('#memberDataList'),
				    //refreshBtn : $('#search'),
				    url: 'memberMng/list',
				    sidePagination:'server',
				    pagination : true,
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	return {
				    		phone: $("#phone").val(),
				    		memberName: $("#memberName").val(),
				    		dateStart: $("#csDate").val(),
				    		dateEnd: $("#ceDate").val(),
				    		balanceStart: $("#balanceStart").val(),
				    		balanceEnd: $("#balanceEnd").val(),
				    		pointStart: $("#pointStart").val(),
				    		pointEnd: $("#pointEnd").val()
				    	}
				    },
				    columns: [
							{
								width: 50,
								align: 'center',
								formatter:function(value,row,index){  
							    	return index+1; 
							    }
							} ,
				  	        {
				  	            field:'phone'
				  	        },
				  	        {
				  	            field:'memberNo'
				  	        }, 
				  	        {
				  	            field:'memberName'
				  	        }, 
				  	        {
				  	            field: 'balance'
				  	        },
				  	        {
				  	            field:'point'
				  	        },
				  	        {
				  	            field:'registerDate'
				  	        },
				  	      {
					            align: 'center',
					            checkbox: false,
					            formatter:function(value,row,index){	
					                return ' <a  title="【查看】" target="_self" href="detail/'+row.memberNo+'" class="editItem"> <i class="fa fa-eye "  style="font-size:20px"></i></a>';
					            } 
				  	        }
				  	     ]
				});
				
				//搜索
				$('#search').on("click", function() {
					var pointStart = $('#pointStart').val();
					var pointEnd = $('#pointEnd').val();
					var balanceStart = $('#balanceStart').val();
					var balanceEnd = $('#balanceEnd').val();
					
					var pointHasError = $('#pointStart').hasClass('fieldError')||$('#pointEnd').hasClass('fieldError');
					var balanceHasError = $('#balanceStart').hasClass('fieldError')||$('#balanceEnd').hasClass('fieldError');
					
					if(pointHasError){
						$("body").loadingInfo("error", "请输入正确的积分");
						return false;
					}
					if(balanceHasError){
						$("body").loadingInfo("error", "请输入正确的余额");
						return false;
					}
					
					memberDataList.options.queryAddParams = function(){
						return {
							phone: $("#phone").val(),
				    		memberName: $("#memberName").val(),
				    		dateStart: $("#csDate").val(),
				    		dateEnd: $("#ceDate").val(),
				    		balanceStart: $("#balanceStart").val(),
				    		balanceEnd: $("#balanceEnd").val(),
				    		pointStart: $("#pointStart").val(),
				    		pointEnd: $("#pointEnd").val()
				    	}
					};
					memberDataList.refresh();
				});
				
				this.bindEvents();
			},
			
			bindEvents : function(){
				var _self = this;
				
				$('#shipment').on("click", function() {
					window.location.href="creatShipment";
				});
				
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
			
			creatShipment : function(orgId){
				var data = {};
				var detailList = [];
				var gogo = true;
				data.orgId = orgId;
				data.shipmentDetails = detailList;
				$('#warnStockShipmentList .shipmentEdit').each(function(i){
					detailList[i] = {};
					detailList[i].productId = $(this).closest('tr').find('.shipmentEdit_productId').html();
					detailList[i].shipmentNum = $(this).val();
				});
				$.ajax({
					url : 'save',
					type : 'POST',
					dataType : 'json',
					contentType : 'application/json',
					data : JSON.stringify(data),
					success: function(){
						window.location= 'platformShipment/';
					}
				});
			},
			
			
	};
	
	memberList.init();
});