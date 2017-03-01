$(function() {
	var groupList = {
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
				var teamBuyIds = [];
	
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
				// 列表
				var bootTable = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					refreshBtn : $('#search'), 
					pagination : true,
					pageSize : 50,
					url: 'groupPurchase/list',
					sidePagination:'server',
					queryAddParams: function() {
						var queryObj =  {
								startTime:		$.trim($('#csDate').val()),
								endTime:		$.trim($('#ceDate').val()),
								groupBuyName:	$.trim($('#groupName').val()),
								productName:	$.trim($('#productName').val()),
								productCode:	$.trim($('#productCode').val()),
								status     : 	$('#status').val()
						} ;
						return queryObj;
					},
					initSearchForm : true, 
				    fillSearchData : function(data) {
				    	 $("#csDate").val(data.startTime);
				    	 $("#ceDate").val(data.endTime);
				    	 $("#groupName").val(data.groupBuyName);
				    	 $("#productName").val(data.productName);
				    	 $("#productCode ").val(data.productCode);
				    	 $("#status ").val(data.status);
				    	
				    },
					columns: [{
                		field : 'groupBuyId',
                		align: 'center',
                		title : "<input type='checkbox' id='totalCheck'>",
                		formatter:function(value,row,index){
                			$("#totalCheck").prop("checked",true);
                			if (teamBuyIds.indexOf(value) > -1) {
                				return "<input value='" + value + "' name='btCheckItem' type='checkbox' class='team-check' status='" + row.status + "' checked='checked'>"
                			} else {
                				$("#totalCheck").prop("checked",false);
                				return "<input value='" + value + "' name='btCheckItem' type='checkbox' class='team-check' status='" + row.status + "'>"
                			}

                		}
					} ,{
						field: 'groupBuyName',
						title: '团购名称'
					} , {
						field: 'productCode',
						title: '关联商品编号  '
					},{
						field: 'productName',
						title: '关联商品',
						width:'210px'
					} , {
						field: 'createDate',
						title: '创建时间'
					} , {
						field: 'startDate',
						title: '有效期',
						align: 'center',
						formatter: function(value,row,index){
							var result = "";
							result += "<div><span>" + value + "</span></div>";
							result += "<p>至</p>";
							result += "<div><span>" + row.endDate + "</span></div>";
							return result;
						}
					} , {
						field: 'status',
						title: '状态',
						width:"100px"
					}, {
						field: 'groupBuyId',
						align: 'center',
						title: '操作',
						width:"85px",
						formatter: function(value,row,index){
							var result = "";
							result += '<a target="_blank" href="'+urlPrefix+'groupPurchase/toPreview/' + value + '" class="previewItem" data-id="'+value+'">预览</a>';
							result += '<a href="'+urlPrefix+'groupPurchase/add/1/'+ value +'" class="viewItem" data-id="'+value+'"> 查看</a>';
							if (row.status == "未开始" || row.status == "商品已下架") {
								result += '<a href="'+urlPrefix+'groupPurchase/add/2/'+value+'" class="editItem" data-id="'+value+'">编辑</a>';
							}
							return result;
						} 
					}],
					onLoadSuccess: function () {
						$("div.pagination-detail").on('click',function(){
							teamBuyIds= [];
							$("#totalCheck").prop("checked",false);
						})
					},
				});
				
				$("#dataList").on('click','#totalCheck',function(){
					totalcheck();
				});
				
				$("#dataList").on('click','.team-check',function(){
					if ($(this).attr("status") == "进行中") {
						$(window).loadingInfo("warn", "进行中的活动不能删除！");
						$(this).prop("checked",false);
						return;
					}
					if ($(this).is(':checked') == true) {
						teamBuyIds.push($(this).val());
					} else {
						var index = teamBuyIds.indexOf($(this).val());
						if (index > -1) {
							teamBuyIds.splice(index, 1);
						}
					}
				});
				
				var totalcheck = function(){
					var box = document.getElementsByName("btCheckItem");
					if ($("#totalCheck").is(':checked') == true) {
						for(var i = 0; i < box.length; i++) {
							if (box[i].getAttribute("status") != "进行中") {
								if (box[i].checked == false) {
									teamBuyIds.push(box[i].value);
								}
								box[i].checked = true;
							}
						}
					} else {
						for(var i = 0; i < box.length; i++) {
							if (box[i].checked == true) {
								var index = teamBuyIds.indexOf(box[i].value);
								if (index > -1) {
									teamBuyIds.splice(index, 1);
								}
							}
							box[i].checked = false;
						}
					}
				};
				
				$("#del_btn").click(function(){
					var count = teamBuyIds.length;
					if(count == 0){
						 this.dialog =  BootstrapDialog.show({
					            title: '请选择要删除的团购',
					            type : BootstrapDialog.TYPE_WARNING,
					            message: message('已选择'+count+'条团购'),
					            draggable: true,
					            size : BootstrapDialog.SIZE_SMALL,
					            buttons: [{
					                label: '返回',
					                cssClass: 'btn-primary ',
					                action: function(dialog) {
					                	dialog.close();
					                    
					                }
					            }]
					        });
					 }else{
						 this.dialog =  BootstrapDialog.show({
						 title: '删除团购',
						 type : BootstrapDialog.TYPE_WARNING,
						 message: "您确定要删除吗？删除之后会自动将关联的广告位删除，请谨慎操作。",
						 
						 draggable: true,
						 size : BootstrapDialog.SIZE_SMALL,
						 buttons: [{
							 label: '确认删除',
							 cssClass: 'btn-primary ',
							 action: function(dialog) {
								 dialog.close();
								 deleteTeamBuy();
							 }
						 }, {
							 label: '取消',
							 action: function(dialog) {
								 dialog.close();
							 }
						 }]
						 });
					 }
				});
				
				var deleteTeamBuy = function(){
					$.ajax({
						url: urlPrefix+"groupPurchase/delete",
						type:"POST",
						dataType: 'json',
						data:{"teamBuyIds":teamBuyIds },
						success:function(msg){
							if (msg.code == "ACK") {
								$(window).loadingInfo("success", msg.message);
								bootTable.refreshThis();
								teamBuyIds = [];
							} else {
								$(window).loadingInfo("warn", msg.message);
							}
						}
					});
				};
			}
	}.init();
})
