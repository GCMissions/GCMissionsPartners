$(function() {
	var orderList = {
			bootTable : void 0,
			numReg : '^[-+]?[0-9]+(\.[0-9]+)?$',
			init : function() {
				var _self = this;
				_self.initEvents();
			},
			
			initEvents : function() {
				var that = this;
				var date = new Date();
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				var day = date.getDate();
				var globalOrderIds = [];
	
				$('#csDate,#ceDate').datetimepicker({
					minView: 'month',
					format: 'yyyy-MM-dd',
					language: 'ch',
					endDate: year+'-'+month+'-'+day,
					autoclose : true,
					todayBtn : true
				});
				// 创建日期
				$('#csDate').on('changeDate',function(){
					$('#ceDate').datetimepicker('setStartDate', $('#csDate').val());
				});

				// 结束日期
				$('#ceDate').on('changeDate',function(){
					if ($('#ceDate').val()) {
						$('#csDate').datetimepicker('setEndDate', $('#ceDate').val());
					}else{
						$('#csDate').datetimepicker('setEndDate', year+'-'+month+'-'+day);
					};
				});
				//验证手机
				$("#phone").on('change',function(){
					that.valiPrice();
				});
				//查询清空数组
				$("#search").on('click',function(){
					globalOrderIds= [];
					$("#orderIds").val("");
				});
				
				// 列表
				var bootTable = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					refreshBtn : $('#search'), 
					pagination : true,
					pageSize : 50,
					url: 'kdOrder/list',
					sidePagination:'server',
					queryAddParams: function() {
						var queryObj =  {
								startTime:		$.trim($('#csDate').val()),
								endDate:		$.trim($('#ceDate').val()),
								orderId:	$.trim($('#orderId').val()),
								phone:	$.trim($('#phone').val()),
								typeId:		$('#typeId').val(),
								status     : $('#status').val(),
								vip : $('#vip').val()
						} ;
						return queryObj;
					},
					initSearchForm : true, 
				    fillSearchData : function(data) {
				    	 $("#csDate").val(data.startTime);
				    	 $("#ceDate").val(data.endDate);
				    	 $("#orderId").val(data.orderId);
				    	 $("#phone").val(data.phone);
				    	 $("#typeId ").val(data.typeId);
				    	 $("#status ").val(data.status);
				    	 $("#vip ").val(data.vip);
				    },
					    
					columns: [{
                		field : 'orderId',
                		align: 'center',
                		title : "<input type='checkbox' id='totalCheck'>",
                		width : '5%',
                		formatter:function(value,row,index){
                			$("#totalCheck").prop("checked",true);
                			if (globalOrderIds.indexOf(value) > -1) {
                				return "<input value='" + value + "' name='btCheckItem' type='checkbox' class='order-check' checked='checked'>"
                			} else {
                				$("#totalCheck").prop("checked",false);
                				return "<input value='" + value + "' name='btCheckItem' type='checkbox' class='order-check'>"
                			}

                		}
					} ,{
						field: 'orderId',
						title: '订单号',
						width : '10%'
					} , {
						field: 'createDate',
						title: ' 创建时间  ',
						width : '10%'
					} , {
						field: 'phone',
						title: '手机号',
						width : '5%'
					} ,
					{
						field: 'productName',
						title: '商品名称',
						width:'10%',
	  	           		formatter:function(value,row,index){
	  	           			var result = "";	  	        	
	  	           			var str = value;   
	  	           			var arr=new Array();
	  	           			arr=str.split('#&amp;#');
	  	           			for(var i=0;i<arr.length;i++)   
	  	           				{
	  	           				var str = arr[i];
	  	           				re=new RegExp(" ","g");
	  	           				var newstr=str.replace(re,"&nbsp;");
	  	           				var span = "<p style='color:#3c8dbc;' ";
	  	           				span += "title='"+newstr+"' class='trackRecord-p'>"+newstr+"</p>";
	  	        	
	  	           				result += span;
	  	           				}
	  	        	  
	  	           			return result;
	  	           		}
					} , {
						field: 'amount',
						title: '金额（元）',
						width : '5%'
					} , {
						field: 'status',
						title: '状态',
						width : '5%'
					} , {
						field: 'buyVip',
						title: 'VIP',
						width : '5%'
					} , {
						field: 'productType',
						title: '订单类型',
						width : '5%',
						formatter:function(value,row,index){
							var result = "";
	  	        	
							var str = value;   
							var arr=new Array();
							arr=str.split('/');
							for(var i=0;i<arr.length-1;i++)   
							{    
								var span = "<p ";
								if(i != 0 && arr[i] == arr[i-1]){
									span += "style ='visibility:hidden;' "
								}
								span += " >"+arr[i]+"</p>";
	  	        	
								result += span;
							}
	  	        	  
							return result;
	  	           		}
					} , {
						field: 'orderId',
						align: 'center',
						title: '操作',
						width : '5%',
						formatter: function(value,row,index){
							var result = "";
							result += ' <div><a   href="'+urlPrefix+'kdOrder/view/'+ row.orderId +'/0" class="viewItem" data-id="'+row.orderId+'"> 查看详情</a></div>';
							if(row.status != "已取消" && row.status != "已退款"){
								result += ' <div><a href="'+urlPrefix+'kdOrder/view/'+ row.orderId +'/1" class="editItem" data-id="'+row.orderId+'">编辑订单</a></div>';
							}
							if(row.status == "待发货"){
								result += ' <div><a href="javascript:void(0);" class="deliveryItem" data-id="'+row.orderId+'">确认发货</a></div>';
							}
							return result;
						} 
					}],
					onLoadSuccess: function () {
						$("div.pagination-detail").on('click',function(){
							globalOrderIds= [];
							$("#orderIds").val("");
							$("#totalCheck").prop("checked",false);
						})
					},
				});
/*				$('#dataList').bootstrapTable({
					onPageChange: function () {
						globalOrderIds= [];
						$("#orderIds").val("");
						$("#totalCheck").prop("checked",false);
				    }
				});*/
	
				$("#dataList").on('click','#totalCheck',function(){
					totalcheck();
				});
	
				$("#dataList").on('click','.order-check',function(){
					if ($(this).is(':checked') == true) {
						globalOrderIds.push($(this).val());
					} else {
						$("#totalCheck").checked = false;
						var index = globalOrderIds.indexOf($(this).val());
						if (index > -1) {
							globalOrderIds.splice(index, 1);
						}
					}
					$("#orderIds").val(globalOrderIds);
				});
	
				var totalcheck = function(){
					var box = $('input[name=btCheckItem]');
					if ($("#totalCheck").is(':checked') == true) {
						for(var i = 0; i < box.length; i++) {
							if (box[i].checked == false) {
								globalOrderIds.push(box[i].value);
							}
							box[i].checked = true;
						}
					} else {
						for(var i = 0; i < box.length; i++) {
							if (box[i].checked == true) {
								var index = globalOrderIds.indexOf(box[i].value);
								if (index > -1) {
									globalOrderIds.splice(index, 1);
								}
							}
							box[i].checked = false;
						}
					}
					$("#orderIds").val(globalOrderIds);
				};
				// excel导出
				$('#exportOrder').click(function(){
					// 导出
					BootstrapDialog.show({
						title: "导出",
						type : BootstrapDialog.TYPE_WARNING,
						message: message('确认导出订单列表吗？'),
						draggable: true,
						size : BootstrapDialog.SIZE_SMALL,
						buttons: [{
							label: '确认',
							cssClass: 'btn-primary saveAddEditTpl',
							action: function(dialog) {
								dialog.close();
//								$('#search').click();
								$("#orderForm").submit();
								globalOrderIds = [];
								$("#orderIds").val("");
								document.getElementById("totalCheck").checked = false;
								setTimeout(function(){
									$('#search').click()
								},1000);
								
							}
							}, {
								label: '取消',
								action: function(dialog) {
									dialog.close();
								}
							}]
					});
				});
				
				$(".orderList").on('click', '.deliveryItem', function(){
					var orderId = $(this).data("id");
					showExpressTmp(orderId);
				});
				
				var showExpressTmp = function(orderId){
					var _this = this;
					_this.deliveryDialog = BootstrapDialog.show({
				        title: '确认发货',		    
				        size :  BootstrapDialog.SIZE_WIDE,
				        message: $(template('deliveryTpl',{})),
				        draggable: false,
				        onshown: function(dialogRef){
				        	$("#expressInfo").focus();
				        	$("#close").click(function(){
				        		_this.deliveryDialog.close();
				        	});
				        	$("#ok").click(function(){
				        		$.ajax({
									url: urlPrefix+"kdOrder/delivery",
									type:"post",
									dataType : "json",
									data :  {
										"orderId":orderId,
										"expressInfo":$.trim($("#expressInfo").val())
									},
									success:function(msg){
										if(msg.code == "ACK") {
											$(window).loadingInfo("success", msg.message);
											_this.deliveryDialog.close();
											bootTable.refreshThis();
										} else {
											$(window).loadingInfo("warn", msg.message);
										}
									}
				        		});
				        	});
				        }
					});
				};
			},
			valiPrice : function(){
				var phone = $.trim($('#phone').val());
				var pflag = true;
				var reg = new RegExp(this.numReg);
				if(phone){
					if(!reg.test(phone)){
						pflag = false;
					}else{
						pflag = true;
					}
				}
				if(pflag){
					$('#numVali').hide();
				}else{
					$('#numVali').show();
			
				}
			},
	}.init();
})
