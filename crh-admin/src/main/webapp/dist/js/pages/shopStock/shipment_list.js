
$(function() {
	var shipmentList = {
			$warnStockBtn  : $('#warnStock'),
			saveStockURL : 'orgStock/setting',
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
				
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
				    //refreshBtn : $('#search'),
				    url: 'platformShipment/list',
				    sidePagination:'server',
				    pagination : true,
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	return {
				    		shipmentConde: $("#shipmentConde").val(),
				    		shipmentState: $("#shipmentState").val(),
				    		createDateStart: $("#csDate").val(),
				    		createDateEnd: $("#ceDate").val(),
				    		orgCode: $("#orgCode").val(),
				    		orgName: $("#orgName").val(),
				    		orgCate: $("#orgCate").val()
				    	}
				    },
				    columns: [
				            {
				            	checkbox:true,
				            },
							{
								width:50,
								align: 'center',
								formatter:function(value,row,index){  
									return index+1;
								}
							} ,
				  	        {
				  	            field:'orderId',
				  	            class:'orderId'
				  	        },
				  	        {
				  	            field:'orgName'
				  	        }, 
				  	        {
				  	            field:'orgCate'
				  	        }, 
				  	        {
				  	            field: 'shipmentNum'
				  	        },
				  	        {
				  	            field:'createDate'
				  	        },
				  	        {
				  	            field:'shipmentState'
				  	        },
				  	      {
					            align: 'center',
					            checkbox: false,
					            formatter:function(value,row,index){
					            	var html ;
					            	if(row.orgCate == "区域平台商" && row.shipmentState == "已创建"){
					            		html = ' <a  title="【查看】" target="_self" href="detail/'+row.orderId+'" class="editItem"> <i class="fa fa-eye "  style="font-size:20px"></i></a>';
//					            		html += '<a  title="【发货】" target="_self" href="#" class="shipment">【发货】</a><input type="hidden" class="hiddenOrgId" value="'+row.orderId+'" >';
					            	}else {
					            		html = ' <a  title="【查看】" target="_self" href="detail/'+row.orderId+'" class="editItem"> <i class="fa fa-eye "  style="font-size:20px"></i></a>';
					            	}	
					                return html;
					            } 
				  	        }
				  	     ]
				});
				
				//搜索
				$('#search').on("click", function() {
					table.options.queryAddParams = function(){
						return {
							shipmentConde: $("#shipmentConde").val(),
				    		shipmentState: $("#shipmentState").val(),
				    		createDateStart: $("#csDate").val(),
				    		createDateEnd: $("#ceDate").val(),
				    		orgCode: $("#orgCode").val(),
				    		orgName: $("#orgName").val(),
				    		orgCate: $("#orgCate").val()
				    	}
					};
					table.refresh();
				});
				
				//平台发货
				$('#shipmentBtn').on('click',function(){
					var orderId = "";
					$('#dataList tbody tr').each(function(){
						if($(this).hasClass('selected')){
							var text = $(this).find('td').text();
							if(text.indexOf('区域平台商') != -1 && text.indexOf('已创建') != -1){
								//获取当前的orderId
								var id = $(this).find('.orderId').text();
								orderId +=id+",";
							}else if(text.indexOf('区域平台商') == -1){
								orderId="";
								return;
							}else if(text.indexOf('已创建') == -1){
								orderId="";
								return;
							}
						}
					});
					
					if(orderId !=null && orderId !=""){
						orderId = orderId.substring(0,orderId.length-1);
						window.location.href = $.GLOBAL.config.urlPrefix +"barcode/index/1/"+orderId;
					}else{
						BootstrapDialog.show({
							title: "提示",
							type : BootstrapDialog.TYPE_WARNING,
							message: message("选择错误，请至少选择一个已创建的区域平台商的订单!"),
							draggable: true,
							size : BootstrapDialog.SIZE_SMALL,
							buttons: [{
								label: '确认',
								cssClass: 'btn-primary',
								action: function(dialog) {
									dialog.close();
								}
							}/*, {
								label: '取消',
								action: function(dialog) {
									dialog.close();
								}
							}*/]
						});
					}
								
				});
				//导出表格
				$('#exportBtn').on('click',function(){
					var orderId = "";
					$('#dataList tbody tr').each(function(){
						if($(this).hasClass('selected')){
							var text = $(this).find('td').text();
							if(text.indexOf('区域平台商') != -1 ){
								//获取当前的orderId
								var id = $(this).find('.orderId').text();
								orderId +=id+",";
							}else if(text.indexOf('区域平台商') == -1){
								orderId="";
								return;
							}
						}
					});
					
					if(orderId !=null && orderId !=""){
						orderId = orderId.substring(0,orderId.length-1);
						window.location.href = $.GLOBAL.config.urlPrefix +"platformShipment/toExcelShipment/?orderId="+orderId;
					}else{
						BootstrapDialog.show({
							title: "提示",
							type : BootstrapDialog.TYPE_WARNING,
							message: message("选择错误，请至少选择一个区域平台商的订单!"),
							draggable: true,
							size : BootstrapDialog.SIZE_SMALL,
							buttons: [{
								label: '确认',
								cssClass: 'btn-primary',
								action: function(dialog) {
									dialog.close();
								}
							}]
						});
					}
				});
				
				$("#exportWarning").on('click',function(){
					//弹出框
					_self.dialog = BootstrapDialog.show({
						title: "导出选择",
						/*type : BootstrapDialog.TYPE_WARNING,*/
						message: $(template('exportWarningDialog',{subAttrHtml:""})),
						draggable: true,
						/*size : BootstrapDialog.SIZE_SMALL,*/
						buttons: [/*{
							label: '确认',
							cssClass: 'btn-primary saveAddEditTpl',
							action: function(dialog) {
								dialog.close();
							}
						}, */{
							label: '取消',
							cssClass:'closeBtn',
							action: function(dialog) {
								dialog.close();
							}
						}],
						onshown : function(){
							$("#exportWarningP").click(function(){
								$('.closeBtn').trigger('click');
								BootstrapDialog.show({
									title: "提示",
									type : BootstrapDialog.TYPE_WARNING,
									message: message("确定导出区域平台商安全库存预警表？"),
									draggable: true,
									size : BootstrapDialog.SIZE_SMALL,
									buttons: [{
										label: '确认',
										cssClass: 'btn-primary',
										action: function(dialog) {
											dialog.close();
											$("#orgType").val("P");
											$("#stockForm").submit();
										}
									},{
										label: '取消',
										action: function(dialog) {
											dialog.close();
										}
									}]
								});
							})	
							$("#exportWarningZ").click(function(){
								$('.closeBtn').trigger('click');
								BootstrapDialog.show({
									title: "提示",
									type : BootstrapDialog.TYPE_WARNING,
									message: message("确定导出终端配送商安全库存预警表？"),
									draggable: true,
									size : BootstrapDialog.SIZE_SMALL,
									buttons: [{
										label: '确认',
										cssClass: 'btn-primary',
										action: function(dialog) {
											dialog.close();
											$("#orgType").val("Z");
											$("#stockForm").submit();
										}
									},{
										label: '取消',
										action: function(dialog) {
											dialog.close();
										}
									}]
								});
							})
						}
					});
					_self.dialog.getModalDialog().css('width', '500px');
				});
//				$('#dataList').on('click','.shipment', function(event) {
//					var orgId = $(this).closest('td').find('.hiddenOrgId').val();
//					_self.shipmentP(orgId).then(function() {
//						table.refresh();
//					});
//					
//				});
				
				this.bindEvents();
				
			},
			
			bindEvents : function(){
				var _self = this;
				
				$('#shipment').on("click", function() {
					window.location.href="creatShipment";
				});
				
				//show库存预警dialog
				this.$warnStockBtn.on("click", function() {
					_self.dialog =  BootstrapDialog.show({
		                    title: '库存预警',
		                    //type : BootstrapDialog.TYPE_DEFAULT,
		                    message: $(template('warnStockDialog', {})),
		                    draggable: true,
		                    onshown : function() {
		                    	var warnStocktable = $.GLOBAL.utils.loadBootTable({
		        					table : $('#warnStockList'),
		        				    //refreshBtn : $('#search'),
		        				    url: 'platformShipment/list/stockWarn',
		        				    sidePagination:'server',
		        				    pagination : true,
		        				    queryParamsType: "limit",
		        				    queryAddParams: function() {
		        				    	return {}
		        				    },
		        				    columns: [
		        				  	        {
		        				  	            field:'orgCode'
		        				  	        },
		        				  	        {
		        				  	            field:'orgName'
		        				  	        }, 
		        				  	        {
		        				  	            field:'orgCate'
		        				  	        },
		        				  	      {
		        					            align: 'center',
		        					            checkbox: false,
		        					            formatter:function(value,row,index){  
		        					                return ' <span class="warnShipmentS">【创建发货单】</span><input type="hidden" class="warnOrgId" value="'+ row.orgId + '"></inpput>';
		        					            } 
		        				  	        }
		        				  	     ]
		        				});
		                    	
		                    	//创建预警库存发货单
		        				$('#warnStockList').on('click','.warnShipmentS', function(event) {
		        					var orgId = $(this).closest('td').find('.warnOrgId').val();
		        					
		        					_self.dialog =  BootstrapDialog.show({
		        	                    title: '创建发货单',
		        	                    //type : BootstrapDialog.TYPE_DEFAULT,
		        	                    message: $(template('warnStockShipmentDialog', {})),
		        	                    draggable: true,
		        	                    buttons: [{
		        	                        label: '创建发货单',
		        	                        cssClass: 'btn-primary saveAddEditTpl',
		        	                        action: function(dialog) {
		        	                            _self.creatShipment(orgId);
		        	                            dialog.close();
		        	                        }
		        	                    }, {
		        	                        label: '取消',
		        	                        action: function(dialog) {
		        	                            dialog.close();
		        	                        }
		        	                    }],
		        	                    onshown : function() {
		        	                    	$.ajax({
		  		      						  type: 'POST',
		  		      						  url: 'list/productWarn/'+ orgId +'',
		  		      						  dataType: 'json',
		  		      						  contentType: 'application/json',
		  		      						  //data:  JSON.stringify(stockDetail)
		  		      			            }).done(function(result) {
		  		      			            	var orgCode = result.data.orgCode;
		  		      			            	var orgCate = result.data.orgCate;
		  		      			            	var orgName = result.data.orgName;
		  		      			            	$('#orgCodeShow').html(orgCode);
		  		      			            	$('#orgNameShow').html(orgName);
		  		      			            	$('#orgCateShow').html(orgCate);
		  		      			            	//location.reload() ;
		  		      			        	 }) .fail(function(result) {
		  		      			        		 //TDO
		  		      			        	 });
		        	                    	var warnStockList = $.GLOBAL.utils.loadBootTable({
		        	        					table : $('#warnStockShipmentList'),
		        	        				    //refreshBtn : $('#search'),
		        	        				    url: 'platformShipment/list/productWarn/'+ orgId +'',
		        	        				    pagination : false,
		        	        				    queryParamsType: "limit",
		        	        				    queryAddParams: function() {
		        	        				    	return {}
		        	        				    },
		        	        				    columns: [
		        	        				  	        {
		        	        				  	            field:'goodsCode'
		        	        				  	        },
		        	        				  	        {
		        	        				  	        	align: 'center',
		        	        					            checkbox: false,
		        	        					            formatter:function(value,row,index){  
		        	        					                return ' <span class="shipmentEdit_productName"> '+row.goodsName+' </span>';
		        	        					            }   	
		        	        				  	        }, 
		        	        				  	        {
		        	        				  	            field:'price'
		        	        				  	        }, 
		        	        				  	        {
		        	        				  	            field:'safeStockSetting'
		        	        				  	        },
		        	        				  	        {
		        	        				  	            field:'standardStockSetting'
		        	        				  	        },
		        	        				  	        {
		        	        				  	            field:'stockSetting'
		        	        				  	        },
		        	        				  	        {
		        	        				  	        	align: 'center',
		        	        					            checkbox: false,
		        	        					            formatter:function(value,row,index){  
		        	        					                return ' <span class="shipmentEdit_productId" style="display:none"> '+row.goodsId+' </span>';
		        	        					            } 
		        	        				  	        		
		        	        				  	        },
		        	        				  	        {
		        	        					            align: 'center',
		        	        					            checkbox: false,
		        	        					            formatter:function(value,row,index){  
		        	        					            	var newShipment = row.standardStockSetting-row.stockSetting;
		        	        					                return ' <input class="shipmentEdit" value="'+ newShipment +'">';
		        	        					            } 
		        	        				  	        }
		        	        				  	     ]
		        	        				});
		        	                    	$('#warnStockShipmentList').on("post-body.bs.table ", _(_self.initInputMask).bind(_self));
		        	                    }
		        	               });
		        					_self.dialog.getModalDialog().css('width', '800px');
		        					_self.dialog.getModalDialog().css('margin-top', '130px');
		        					
		        				});
		                    }
		               });
					_self.dialog.getModalDialog().css('width', '800px');
					_self.dialog.getModalDialog().css('margin-top', '130px');
					
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
				var shipmentValid = true;
				data.orgId = orgId;
				data.type = 1;
				data.shipmentDetails = detailList;
				$('#warnStockShipmentList .shipmentEdit').each(function(i){
					if($(this).val() <= 0 || !$(this).val())
						shipmentValid = false;
					
					detailList[i] = {};
					detailList[i].goodsId = $(this).closest('tr').find('.shipmentEdit_productId').html();
					detailList[i].goodsName = $(this).closest('tr').find('.shipmentEdit_productName').html();
					detailList[i].shipmentNum = $(this).val();
				});
				if(!shipmentValid){
					$(".modal-content").loadingInfo("error", "发货数量必须大于0");
					return false;
				}
				$.ajax({
					url : 'save',
					type : 'POST',
					dataType : 'json',
					contentType : 'application/json',
					data : JSON.stringify(data),
					success: function(){
						$('body').loadingInfo({
		    				type : "success", 
		    				text : "成功创建发货单",
		    				callBack : function() {
		    					window.location= '../platformShipment/';
		    					//window.location.href=urlPrefix + that.listUrl;
		    				}
		    			});
						/*$('body').loadingInfo('success', '发货成功');
						window.location= '../platformShipment/';*/
					}
				});
			},
			
			initInputMask : function() {
		    	$('.shipmentEdit').inputmask({
		    		"mask" : "9",
		    		repeat : 6,
		    		"greedy": false
		    	});
		    },
			
			shipmentP : function(orgId){
				var orgId = orgId;
				return $.ajax({
					url : 'deliverGoods/'+orgId+'',
					type : 'POST',
					dataType : 'json',
					contentType : 'application/json',
					success: function(){
						$('body').loadingInfo({
		    				type : "success", 
		    				text : "发货成功",
		    				callBack : function() {
		    				}
		    			});
						/*$('body').loadingInfo('success', '发货成功');
						window.location= '../platformShipment/';*/
					}
				});
			}
			
			
	};
	
	shipmentList.init();
});
