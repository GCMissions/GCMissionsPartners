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
				var lineOrderImportCnt = 0;
	
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
					if($('#csDate').val()=="" && $("#csDate").next().css('display') == 'inline-block'){
						$("#csDate").next().css('display','none');
					}else{
						$("#csDate").next().css('display','inline-block');
					}
				});

				// 结束日期
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
					url: 'order/list',
					sidePagination:'server',
					queryAddParams: function() {
						var queryObj =  {
								startTime:		$.trim($('#csDate').val()),
								endDate:		$.trim($('#ceDate').val()),
								orderId:	$.trim($('#orderId').val()),
								phone:	$.trim($('#phone').val()),
								productName:  $.trim($('#productName').val()),
								typeId:		$('#typeId').val(),
								orgId		: $('#orgId').val(),
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
				    	 $("#productName").val(data.productName);
				    	 $("#typeId").val(data.typeId);
				    	 $("#orgId").val(data.orgId);
				    	 $("#status").val(data.status);
				    	 $("#vip").val(data.vip);
				    },
				    
					columns: [{
                		field : 'orderId',
                		align: 'center',
                		title : "<input type='checkbox' id='totalCheck'>",
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
						title: '订单号'
					} , {
						field: 'createDate',
						title: ' 创建时间  '
					} , {
						field: 'phone',
						title: '手机号'
					} ,{
						field: 'productCode',
						title: '商品编号',
						formatter:function(value,row,index){
							var result = "";
	  	        	
							var str = value;   
							var arr= str.split(',/,');
							for (var i = 0; i < arr.length; i++) {
								var span = "<p>" + arr[i] + "</p>";
								result += span;
							}
	  	        	  
							return result;
	  	           		}
					},
					{
						field: 'productName',
						title: '商品名称',
						width:'210px',
	  	           		formatter:function(value,row,index){
	  	           			var result = "";	  	        	
	  	           			var str = value;   
	  	           			var arr = str.split('#&amp;#');
							for (var i = 0; i < arr.length; i++) {
								var str = arr[i];
								re = new RegExp(" ", "g");
								var newstr = str.replace(re, "&nbsp;");
								var span = "<p style='color:#3c8dbc;' ";
								span += "title='" + newstr + "' class='trackRecord-p'>" + newstr + "</p>";
								result += span;
							}
	  	        	  
	  	           			return result;
	  	           		}
					} , {
						field: 'orgName',
						title: '服务商',
						width:"100px",
						formatter:function(value,row,index){
							var result = "";
	  	        	
							var str = value;   
							var arr = str.split(',/,');
							for (var i = 0;i < arr.length; i++) {    
								var span = "<p ";
								if (arr[i] == arr[i-1] && i != 0){
									span += "style ='visibility:hidden;' "
								}
								span += "title='"+arr[i]+"' class='trackRecord-s'>"+arr[i]+"</p>";
	  	        	
								result += span;
							}
	  	        	  
							return result;
	  	           		}
					} , {
						field: 'productType',
						title: '商品类型',
						width:"10%",
						formatter:function(value,row,index){
							var result = "";
	  	        	
							var str = value;   
							var arr = str.split('/');
							for (var i = 0;i < arr.length - 1;i++) {    
								var span = "<p ";
								if (arr[i] == arr[i-1] && i != 0){
									span += "style ='visibility:hidden;' "
								}
								span += " >"+arr[i]+"</p>";
	  	        	
								result += span;
							}
	  	        	  
							return result;
	  	           		}
					} , {
						field: 'amount',
						title: '金额（元）'
					} , {
						field: 'status',
						title: '状态',
						width:"100px"
					} , {
						field: 'buyVip',
						title: 'VIP'
					} , {
						field: 'orderId',
						align: 'center',
						title: '操作',
						width:"50px",
						formatter: function(value,row,index){
							var result = "";
							result += ' <a   href="'+urlPrefix+'order/view/'+ row.orderId +'/0" class="viewItem" data-id="'+row.orderId+'"> 查看</a>';
							if (row.status != "已取消"){
								result += ' <a href="'+urlPrefix+'order/view/'+ row.orderId +'/1" class="editItem" data-id="'+row.orderId+'">编辑</a>';
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
				
				//导出线下订单模板
				$("#makeupOrderDown").click(function(){
					$.ajax({
						type: "POST",
						url: "toTmlExcel",
						contentType: "application/json;charset=utf-8",
						dataType: "json",
						success: function(message){
							if(message.code == "ACK"){
								window.location.href = message.data;
							}
						},
						error:function(message){
							//alert('shibai');
						}
					});
				});
				
				//导入线下订单模板
				$('#importMakeupOrder').click(function(){
					// 导出
					BootstrapDialog.show({
			            title: '导入线下订单',
			            type : BootstrapDialog.TYPE_WARNING,
			            message: message('确认导入线下的订单数据么？'),
			            draggable: true,
			            size : BootstrapDialog.SIZE_SMALL,
			            buttons: [{
			                label: '确认导入',
			                cssClass: 'btn-primary importLineOrder',
			                action: function(dialog) {
			                	$("#postExcel").trigger('click');
			                }
			            }, {
			                label: '取消',
			                cssClass: 'cancelSendCounponSign',
			                action: function(dialog) {
			                    dialog.close();
			                }
			            }],
			            onshown : function() {
			            	if(lineOrderImportCnt == 0){
								$("body").on('change','#postExcel',that.importExcel);
								lineOrderImportCnt ++;
		                	}
			            }
			       });
					
				});
				
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
			
			importExcel : function(){
				$('body').loadingInfo("show", '导入中...',200000);
				$.ajaxFileUpload({
		            url:"importExcel",            
					dataType : 'json',
		            fileElementId:'postExcel',                   
		            /*type: 'post',*/
		            secureuri:false,
		            data: {
		            	}, 
		            success: function(data){     
						if (data.code == "ACK") {
							$('.cancelSendCounponSign').click();
							$('body').loadingInfo("success", "处理成功！请稍后查看。");
							setTimeout(function(){location.reload(true)}, 2000);
						} else if(data.code == "BUSINESS_ERROR"){
							$('.cancelSendCounponSign').click();
							$('body').loadingInfo("error", data.message);
							/*BootstrapDialog.show({
								title: "提示",
								type : BootstrapDialog.TYPE_WARNING,
								message: message(data.message),
								draggable: true,
								size : BootstrapDialog.SIZE_SMALL,
								buttons: [{
									label: '确认',
									cssClass: 'btn-primary',
									action: function(dialog) {
										dialog.close();
									}
								}]
							});*/
						}
		            },
		            error: function (data, e){  
		            	$('body').loadingInfo("error", "上传失败！");
		            }  
		        }); 
			},
	}.init();
})
