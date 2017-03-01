$(function(){
	var couponList = {
			initEvents:function(){
				var _self = this;
				//开始日期
				var date = new Date();
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				var day = date.getDate();

				$('#csDate,#ceDate,#usDate,#ueDate').datetimepicker({
					minView: 'month',
					format: 'yyyy-mm-dd',
					language: 'ch',
					endDate: year+'-'+month+'-'+day,
					autoclose : true,
					todayBtn : true
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
				
				//使用日期
				$('#usDate').on('changeDate',function(){
					$('#ueDate').datetimepicker('setStartDate', $('#usDate').val());
					if($('#usDate').val()=="" && $("#usDate").next().css('display') == 'inline-block'){
						$("#usDate").next().css('display','none');
					}else{
						$("#usDate").next().css('display','inline-block');
					}
				});

				//结束日期
				$('#ueDate').on('changeDate',function(){
					if ($('#ueDate').val()) {
						$('#usDate').datetimepicker('setEndDate', $('#ueDate').val());
					}else{
						$('#usDate').datetimepicker('setEndDate', year+'-'+month+'-'+day);
					};
					if($('#ueDate').val()=="" && $("#ueDate").next().css('display') == 'inline-block'){
						$("#ueDate").next().css('display','none');
					}else{
						$("#ueDate").next().css('display','inline-block');
					}
				});
				
				//优惠券使用类型
				$('#useTypeSelect').on('click', _(this.showuseTypeMenu).bind(this));
				
				$(".useType").on('change',_(this.useTypeChange).bind(this));
				
				if($("input[name='useType']:checked").val() == 0){
					$("#useTypeInit").val(0);
					this.useTypeChange();
				}
				
				$("#sendCoupon").on('click',_(this.sendCouponDeal).bind(this));
				
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
	        	    refreshBtn : $('#refreshRecord'),
					idField : "couponId",
				    url: 'coupon/list',
				    sidePagination:'server',
				    pagination : true,
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	var result= {
								couponId  : $("#couponId").val(),
					    		couponName: $("#couponName").val(), 
					    		createDateStrat: $("#csDate").val(), 
					    		createDateEnd: $("#ceDate").val(), 	    		
					    		value: $("#valueYuan").val(), 
					    		type: $("#couponType").val(), 
					    		status: $("#status").val(), 
					    		effectDateStart: $("#usDate").val(), 
					    		effectDateEnd: $("#ueDate").val(),
					    		useTypeDetail: $("#useTypeData").val(),
					    		useType: $("input[name='useType']:checked").val(),
					    		//text: $('#useTypeDetail').text()
							};
				    	if(result.createDateStrat && $.GLOBAL.utils.isDate(result.createDateStrat)) {
				    		result.createDateStrat += " 00:00:00";
		                }
		                if(result.createDateEnd && $.GLOBAL.utils.isDate(result.createDateEnd)) {
		                	result.createDateEnd += " 23:59:59";
		                }
		                
		                if(result.effectDateStart && $.GLOBAL.utils.isDate(result.effectDateStart)) {
				    		result.effectDateStart += " 00:00:00";
		                }
		                if(result.effectDateEnd && $.GLOBAL.utils.isDate(result.effectDateEnd)) {
		                	result.effectDateEnd += " 23:59:59";
		                }
	                
						if($("#csDate").val()==""){delete result.createDateStrat}
						if($("#ceDate").val()==""){delete result.createDateEnd}
						if($("#usDate").val()==""){delete result.effectDate}
						if($("#ueDate").val()==""){delete result.invalidDate}
						return result;
				    },
				    initSearchForm : true, 
				    fillSearchData : function(data) {
				    	$("#couponName").val(data.couponName);
				    	$("#csDate").val(data.createDateStrat);
				    	$("#ceDate").val(data.createDateEnd);
				    	$("#valueYuan").val(data.value);
				    	$("#couponType").val(data.type);
				    	
				    	
				    	$("#status").val(data.status);
				    
			    		
			    		
				    	$("#usDate").val(data.effectDateStart);
				    	$("#ueDate").val(data.effectDateEnd);
				    	$("#useTypeData").val(data.useTypeDetail);
				    	$("input[name='useType']").each(function() {
				    		if($(this).val() == data.useType) {
				    			$(this).prop("checked", true);
				    		}				    		
				    	});
				    	//匹配品类弹框选择
				    	if(data.useType == '0'){
				    		$("#useTypeShow").show();
				    		if($("#useTypeDetail").text() != ''){
				    			$(".useTypeDetail").show();
				    		}
				    	}else{
				    		$("#useTypeShow").hide();
				    		$(".useTypeDetail").hide();
				    	}
				    	//获取data.useTypeDetail的文字 从下面抄来的
				    	var detailIds = data.useTypeDetail ? data.useTypeDetail.split(',') : [], detailNames=[];
				    	if(detailIds.length>0) {
				    		$.ajax({
								type: "post",
								dataType: "json",
								url:urlPrefix + 'category/list',
								async:true,
								success: function(response){
									if(response.code == "ACK"){
										var tNodes = response.data;
										for (var i = 0, l = tNodes.length; i < l; i++) {
											if(_.indexOf( detailIds, String(tNodes[i].cateId) ) != -1) {
												detailNames .push(tNodes[i].cateName);
											}
							            }
									
										$('#useTypeDetail').parent().show().end().text(detailNames.join(','));
										return;
				        	 		}
								},
							});
				    		
				    	}
				    	
				    },
				     
				    columns: [
						{
							checkbox:true,
							formatter:function(value,row,index){  
								 if(row.status == "0" || row.status == "2") {
							            return {
							                disabled: true
							            };
								}
							}
						},
						{
							width:50,
							align: 'center',
							formatter:function(value,row,index){  
								return index+1;
							}
						} ,
				        {
				            field: 'couponName',
				            class:'couponName',
				            formatter:function(value,row,index){
				            	return value.replace(/\s/g, "&nbsp;");
				            }
				        } ,
				        {
				            field: 'valueYuan',
				            class:'valueYuan'
				        } ,
				        {
				            field: 'createDate',
				        } , 
				        {
				            field: 'effectiveDate',
				            formatter:function(value,row,index){
				        		var effectDate = row.effectDate.substring(0,10);
				        		var invalidDate = row.invalidDate;
				        		
				            	return effectDate+" 至 "+invalidDate;
				            }
				        } , 
				        {
				        	field: 'status',
				        	class:'status',
				        	formatter:function(value,row,index){
				        		var curDate = new Date().getTime();
								var effectDate = new Date((row.effectDate).replace(new RegExp("-","gm"),"/")).getTime();
								var invalidDate = new Date((row.invalidDate).replace(new RegExp("-","gm"),"/")).getTime();
				            	if(value=="1" && effectDate <=curDate && invalidDate >=curDate){
				            		value="启用";
				            	}else if(value=="2" ) {
				            		value="禁用";
				            	}else if(value=="0" || !(effectDate <=curDate && invalidDate >=curDate)){
				            		 value="失效";
				            	}
				            	return value;
				            }
				        } ,
				        {
				        	width:90,
				        	field: 'couponId',
				            align: 'center',
				            //checkbox: false,
				            formatter:function(value,row,index){  
				                if(row.status == "1" || row.status == "2") {
				                	var html = '<a  title="修改" class="editItem" data-id="'+row.couponId+'" data-value="'+row.couponName+'">' 
									+'<i class="fa fa-edit"  style="font-size:20px;margin-right: 4%;"></i></a>'
									+'<a  title="删除" href="#" class="removeItem" data-id="'+row.couponId+'"><i class="fa fa-trash"  style="font-size:20px"></i></a>';
				                }else {
				                	var html = '<a  title="删除" data-id="'+row.couponId+'" class="a-dis"><i class="fa fa-trash fa-dis"  style="font-size:20px"></i></a>';
				                }
				            	return ' <a  title="查看" couponid='+row.couponId+' style="margin-right: 9px;" href="detail/'+row.couponId+'" class="datailItem" data-id="'+row.couponId+'"> <i class="fa fa-eye"  style="font-size:20px"></i></a>'
			                    + html;
				            } 
				        }
				     ]
				});

				$('#dataList').on("click", "a.removeItem", function() {
					var couponId = this.getAttribute('data-id');
					var isConfirm = window.confirm("确认要删除此优惠券吗?");
					if(isConfirm){
						$.ajax({
							type: "GET",							
							contentType: "application/json;charset=utf-8",
							url:"delete/"+couponId,
							dataType: "json",
							success: function(message){
								if(message.code=="ACK"){
									window.confirm("删除成功");
									table.refresh();									
								}
							},
							error:function(message){
								window.confirm("删除出错");
							}
						});					
					}
				});
				
				$('#dataList').on("click", "a.editItem", function() {
					var couponId = this.getAttribute('data-id');
					var couponName = this.getAttribute('data-value');
					var obj = $(this);
					var status = obj.parent('td').parent('tr').find('.status').text();
					var couponObj = new Object();
					couponObj.couponName = couponName;
					couponObj.couponId = couponId;
					couponObj.status = status;
					this.dialog =  BootstrapDialog.show({
						title: '优惠券是否启用',
						message: $(template('editTpl',couponObj)),
						draggable: true,
						buttons: [{
							label: '保存',
							cssClass: 'btn-primary saveAddEditTpl',
							action: function(dialog, e) {
								saveEditData($(e.target),dialog);
							}
						}, {
							label: '取消',
							action: function(dialog) {
								dialog.close();
							}
						}],
						
					});
				});
				
				function saveEditData($btn,$dialog) {
					var couponUpdateForm = {
							couponId  : $('.addEditTpl').find('input[name="couponId"]').val(),
							status : $('#couponSelect').find("option:selected").val()
					};
		            $.ajax({
					  type: 'POST',
					  url: urlPrefix + "coupon/updtaeCoupon",
					  dataType: 'json',
					  contentType: 'application/json',
					  data:  JSON.stringify(couponUpdateForm)
		            })
		        	.done(function(result) {
	        	 		if(result.code == "ACK"){
	        	 			$(".addEditTpl").loadingInfo({
	        	 				type : "success",
	        	 				text: message("admin.message.success"),
	        	 				callBack : function() {
	        	 					$dialog.close();
	        	 					table.refresh(); 
	        	 				}
	        	 			});
	        	 		}
		        	 })
		        	 .fail(function(result) {
		        		 $(".addEditTpl").loadingInfo({
		        	 		text : "保存失败",
		        	 		type : "error",
		        	 		callBack : function() {
		        	 			//that.dialog.close();
		                   	    //that.bootTable.refresh(); 
		        	 		}
		        	 	})
		        	 })
		        	 .always(function(result) {
		        		 $btn.saved();
		        	 });
		        }

				$('body').on("click", "a.removePhone2", function() {
					var phone = this.getAttribute('data-id');
					var isConfirm = window.confirm("确定要删除该条记录吗?");
					if(isConfirm){
						$("."+phone).remove();
						var usersList = $('#chooseUsersList tr');
						if(usersList.length == 0){
							$(".sendCounponSign").attr("disabled", true);
						}
					}
				});
				
			},
			
			initInputMask : function() {
		    	$('#couponId').inputmask({
		    		"mask" : "9",
		    		repeat : 8,
		    		"greedy": false
		    	});
		    	$("#valueYuan").inputmask({
		    		"mask" : "9",
		    		repeat : 4,
		    		"greedy": false
		    	});
		    },
		    
		    sendTypeDeal : function(){
		    	// 指定用户发送
		    	if($("input[name='sendType']:checked").val() =='1') {
		    		$(".phoneDiv").show();
		    		$("#chooseUsersList").show();
		    		$("#batchSend").show();
		    		var usersList = $('#chooseUsersList tr:gt(0)');
    				if(usersList.length > 0){
    					$(".sendCounponSign").attr("disabled", false);
    				}else{
    					$(".sendCounponSign").attr("disabled", true);
    				}
		    	}else{
		    		$(".phoneDiv").hide();
		    		$("#chooseUsersList").hide();
		    		$("#batchSend").hide();
		    		$(".sendCounponSign").attr("disabled", false);
		    	}
		    },
		    
		    useTypeChange : function(){
		    	var useType = $("input[name='useType']:checked").val();
		    	if($("#useTypeInit").val() == 0){
					//初始化ztree
					this.loadCategoryTree("categoryTree");
					$("#useTypeInit").val(1);
				}
		    	//匹配品类弹框选择
		    	if(useType == '0'){
		    		$("#useTypeShow").show();
		    		if($("#useTypeDetail").text() != ''){
		    			$(".useTypeDetail").show();
		    		}
		    	}else{
		    		$("#useTypeShow").hide();
		    		$(".useTypeDetail").hide();
		    	}
		    },
		    
		    useTypeDeal : function(){
		    	if($("#useTypeInit").val() == 0){
					//初始化ztree
					this.loadCategoryTree("categoryTree");
					$("#useTypeInit").val(1);
				}
			},
			
			showuseTypeMenu : function(){
				this.useTypeDeal();
				$(".Tree").modal("show");
			},
			
			//添加页初始化Ztree
			loadCategoryTree : function(targetName) {
				var setting = {
						check: {
							//设置 zTree 的节点上是否显示 checkbox / radio 默认值: false
							enable: true,
							chkboxType: {"Y":"s", "N":"ps"},
							autoCheckTrigger: true
						},
						view: {
							//双击节点时，是否自动展开父节点的标识,默认值: true
							dblClickExpand: true
						},
						data: {
							simpleData: {
								enable: true
							}
						},
						callback: {
							beforeClick: this.beforeClick,
							onCheck: this.onCheck
						}
					};
			    var zNodes = $("#zNodes").val();
			    if(zNodes =='' || zNodes == null){
			    	zNodes = [];
			    	 $.ajax({
							type: "post",
							dataType: "json",
							url:urlPrefix + 'category/list',
							async:true,
							success: function(response){
								if(response.code == "ACK"){
									var tNodes = response.data;
									for (var i = 0, l = tNodes.length; i < l; i++) {
										var tempNode = new Object();
										tempNode.id=tNodes[i].cateId;
										tempNode.pId=tNodes[i].parentId;
										tempNode.name=tNodes[i].cateName;
										if(tempNode.pId == 0){
											tempNode.open=true;
										}
						                zNodes.push(tempNode);
						            }
									$.fn.zTree.init($("#"+targetName), setting, zNodes);
									return;
			        	 		}
							},
						});
			    }
			    //初始化ztree
				$.fn.zTree.init($("#"+targetName), setting, zNodes);
			},
			
			beforeClick : function(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("categoryTree");
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				return false;
			},
			
			onCheck : function(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("categoryTree"),
				// true：获取 被勾选 的节点集合  false:获取 未勾选 的节点集合
				nodes = zTree.getCheckedNodes(true),
				v = "";
				var categoryIds = "";
				for (var i=0, l=nodes.length; i<l; i++) {
					if(!(nodes.length > 1 && nodes[i].isParent)){
						v += nodes[i].name + ",";
						categoryIds += nodes[i].id + ",";
					}
				}
				if (v.length > 0 ) v = v.substring(0, v.length-1);
				if (categoryIds.length > 0 ) categoryIds = categoryIds.substring(0, categoryIds.length-1);
				var cityObj = $("#useTypeDetail");
				$("#useTypeData").val(categoryIds);
				if(v !=''){
					$(".useTypeDetail").show();
				}else{
					$(".useTypeDetail").hide();
				}
				cityObj.html(v);
			},
			
			sendCouponDeal : function(){
				var couponObj = new Object();
				var couponId = "";
				var checkCount = 0;
				$('#dataList tbody tr').each(function(i){
					if($(this).hasClass('selected')){
						//获取当前的orderId
						var id = $(this).find('.datailItem').attr('couponid');
						couponId +=id+",";
						checkCount++;
					}
				});
				if(couponId !=null && couponId !=""){
					couponId = couponId.substring(0,couponId.length-1);
					couponObj.couponCnt = checkCount;
					$("#couponIds").val(couponId);
					if(checkCount == 1){
						$.ajax({
							type: "POST",
							url: urlPrefix + 'coupon/getCoupon',
							async: false,
							contentType: "application/json;charset=utf-8",
							data: JSON.stringify(couponId),
							dataType: "json",
							success: function(message){
								var curData = message.data;
								couponObj.couponName = curData.couponName;
								couponObj.valueYuan = curData.valueYuan;
								couponObj.effectDate = curData.effectDate.substring(0,10);
								couponObj.invalidDate = curData.invalidDate;
								if(curData.fetchType == '0'){
									couponObj.fetchTypeDetailNames = curData.fetchTypeDetailNames;
								}else{
									couponObj.fetchTypeDetailNames = "全品类";
								}
								if(curData.useType == '0'){
									couponObj.useTypeDetailNames = curData.useTypeDetailNames;
								}else{
									couponObj.useTypeDetailNames = "全品类";
								}
							},
							error:function(message){
								//alert('shibai');
							}
						});
					}
					var that = this;
					that.dialog =  BootstrapDialog.show({
			            title: '发送优惠券',
			            message: $(template('sendCouponTpl', couponObj)),
			            draggable: true,
			            buttons: [{
			                label: '确认发送',
			                cssClass: 'btn-primary sendCounponSign',
			                action: function(dialog) {
		    					that.saveCoupon();
		    					$(".sendCounponSign").attr("disabled", true);
		    					$("#addEditForm").loadingInfo({
		        	 				type : "success",
		        	 				text: message("admin.message.success"),
		        	 				callBack : function() {
		        	 					dialog.close();
		        	 					window.location.href= urlPrefix + "coupon/";
		        	 				}
		        	 			});
			                }
			            }, {
			                label: '取消',
			                cssClass: 'cancelSendCounponSign',
			                action: function(dialog) {
			                    dialog.close();
			                }
			            }],
			            onshown : function() {
			            	if(checkCount == 1){
			            		$("#onCouponShow").show();
			            	}else{
			            		$("#moreCouponShow").show();
			            	}
			            	$('.sendType').on('click', that.sendTypeDeal);
			            	$('#searchmember').on('click', that.addPhoneDeal);
			            	
							$("#import").on('click',that.importClick);
							$("body").on('change','#postExcel',that.importExcel);
							
							$("#importTml").click(function(){
								$("#userTmlForm").submit();
							});
			            }
			       });
			       that.dialog.getModalDialog().css('width', '950px');
				}else{
					BootstrapDialog.show({
						title: "提示",
						type : BootstrapDialog.TYPE_WARNING,
						message: message("请至少选择一张优惠劵!"),
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
			},
			
			saveCoupon : function(){
				var usersList = $('#chooseUsersList tr:gt(0)');
				var sendType = $("input[name='sendType']:checked").val();
				if(sendType=='1' && usersList.length > 0){
					var userIds = "";
					for (var i=0, l=usersList.length; i<l; i++) {
						userIds += usersList[i].id + ",";
					}
					if (userIds.length > 0 ) userIds = userIds.substring(0, userIds.length-1);
					$("#userIds").val(userIds);
				}
				var couponSendForm = {
						userIds : $("#userIds").val(),
						couponIds  : $("#couponIds").val(),
						sendType : sendType
				};
				$.ajax({
					type: "POST",
					url: urlPrefix + 'coupon/sendCoupon',
					contentType: "application/json;charset=utf-8",
					data: JSON.stringify(couponSendForm),
					dataType: "json",
					success: function(message){
						if(message.code == "ACK"){
							
						}
					},
					error:function(message){
						//alert('shibai');
					}
				});
			},
			
			importClick : function(){
				$("#postExcel").trigger('click');
			},
			
			importExcel : function(){
				$('body').loadingInfo("show", '导入中...',200000);
				var sendType = $("input[name='sendType']:checked").val();
				var couponSendForm = {
						userIds : $("#userIds").val(),
						couponIds  : $("#couponIds").val(),
						sendType : sendType
				};
				$.ajaxFileUpload({
		            url:"importExcel",            
					dataType : 'json',
		            fileElementId:'postExcel',                   
		            /*type: 'post',*/
		            secureuri:false,
		            data: {
		            		userIds: $("#userIds").val(),
		            		couponIds: $("#couponIds").val(),
		            		sendType:sendType
		            	}, 
		            success: function(data){     
						if (data.code == "ACK") {
							$('body').loadingInfo("success", "批量导入手机号成功！");
							$("#chooseUsersList thead tr:gt(0)").empty();  
							$.each(data.data.phones,function(index,phone){
								var html = '<tr id="'+index+'" class="'+phone+'"><td>'+phone+'</td><td><a  title="删除" href="#" class="removePhone2" data-id="'+phone+'"><i class="fa fa-trash"  style="font-size:20px"></i></a></td></tr>';
				            	$("#chooseUsersList thead").append(html);   
							});
							$(".sendCounponSign").attr("disabled", false);
						} else if(data.code == "BUSINESS_ERROR"){
							var errorMsg = data.message.split("！");
							var phoneObject = data.data.phones;
							if(typeof(phoneObject) == "undefined"){
								$('body').loadingInfo("success", "导入的所有手机号都无效！", 500);
								$("#chooseUsersList thead tr:gt(0)").empty();  
								$(".sendCounponSign").attr("disabled", true);
							}else{
								$('body').loadingInfo("success", "部分手机号导入成功！", 500);
								$("#chooseUsersList thead tr:gt(0)").empty();  
								$.each(data.data.phones,function(index,phone){
									var html = '<tr id="'+index+'" class="'+phone+'"><td>'+phone+'</td><td><a  title="删除" href="#" class="removePhone2" data-id="'+phone+'"><i class="fa fa-trash"  style="font-size:20px"></i></a></td></tr>';
					            	$("#chooseUsersList thead").append(html);   
								});
				            	$(".sendCounponSign").attr("disabled", false);
							}
							BootstrapDialog.show({
								title: "无效手机号",
								type : BootstrapDialog.TYPE_WARNING,
								message: message(errorMsg[0]),
								draggable: true,
								size : BootstrapDialog.SIZE_NORMAL,
								buttons: [{
									label: '确认',
									cssClass: 'btn-primary',
									action: function(dialog) {
										dialog.close();
									}
								}]
							});
						}else {
							$('body').loadingInfo("error", data.message);
							$("#chooseUsersList thead tr:gt(0)").empty();  
							$(".sendCounponSign").attr("disabled", true);
						} 
		            },
		            error: function (data, e){  
		            	$('body').loadingInfo("error", "上传失败！");
		            	$(".sendCounponSign").attr("disabled", true);
		            }  
		        }); 
				$("#exportDiv").empty().append("<input type='file' name='postExcel' id='postExcel' style='display:none;' accept='.xls,.xlsx'>");
			},
			
			fileChangeFunc : function(){
				importExcel();
			},
			
			addPhoneDeal : function(){
				var $form = $('#addEditForm');
				if($form.validate().form()) {
					var phone = parseInt($("#sPhone").val());
					if($("."+phone).length == 0 ){
						$.ajax({
							type: "POST",
							url: urlPrefix + 'memberMng/findBypPhone',
							async: false,
							contentType: "application/json;charset=utf-8",
							data: JSON.stringify(phone),
							dataType: "json",
							success: function(message){
								if(message.code == "ACK"){
									var html = '<tr id="'+message.data+'" class="'+phone+'"><td>'+phone+'</td><td><a  title="删除" href="#" class="removePhone2" data-id="'+phone+'"><i class="fa fa-trash"  style="font-size:20px"></i></a></td></tr>';
					            	$("#chooseUsersList thead").append(html);   
					            	$(".sendCounponSign").attr("disabled", false);
					            	// 清空数据
					            	$("#sPhone").val('');
								}
							},
							error:function(message){
								//alert('shibai');
							}
						});
					}else{
						BootstrapDialog.show({
							title: "提示",
							type : BootstrapDialog.TYPE_WARNING,
							message: message("输入错误,该号码已添加!"),
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
				}else{
					console.log("valid fail");
				}
			},

			
			init:function(){
				var _self = this;
				_self.initEvents();
			}
	
	}.init();	
});
