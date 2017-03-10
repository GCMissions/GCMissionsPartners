$(function(){
	var regionList = {
			init : function() {
				var _self = this;
				_self.initEvents();
			},
			initEvents : function(){
				var bootTable = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					pagination : true,
					refreshBtn : $('#searchBtn'), 
					pageSize : 10,
					url: 'region/list',
					sidePagination:'server',
					queryAddParams: function() {
						var queryObj =  {
								regionName : $("#regionName").val()
						} ;
						return queryObj;
					},
					columns: [{
                		width: '20%',
                		formatter:function(value,row,index){  
                        	return index+1; 
                        }
					} ,{
						field: 'regionName',
						width: '20%'
					} , {
						field: 'createTime',
						width: '20%'
					} , {
						field: 'id',
						width: '20%',
						formatter: function(value,row,index){
							var result = "";
							result += "<a href='" + urlPrefix + "region/view/" + value + "' class='detailItem' data-id='"+value+"'>Edit</a>";
							result += "<a style='margin-left: 10%;' href='javascript:void(0)' class='deleteItem' data-id='"+value+"'>Delete</a>";
							return result;
						} 
					}]
				});
				
				$("#dataList").on('click','.deleteItem',function(){
					var id = $(this).data("id");
					this.dialog =  BootstrapDialog.show({
		                title: 'Delete Region',
		                type : BootstrapDialog.TYPE_WARNING,
		                message: message('admin.dialog.deleteConfirm'),
		                draggable: true,
		                size : BootstrapDialog.SIZE_SMALL,
		                buttons: [{
		                    label: 'Confirm the deletion',
		                    cssClass: 'btn-primary saveAddEditTpl',
		                    action: function(dialog) {
		                    	dialog.close();
		                    	deleteRegion(id);
		                    }
		                }, {
		                    label: 'Cancel',
		                    action: function(dialog) {
		                        dialog.close();
		                    }
		                }]
		            });
				});
				
				var deleteRegion = function(id){
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "region/delete",
						dataType     : 'json',
						data 	     : {
							"id" : id
						},
						success : function(msg){
							if (msg.code == "ACK") {
								freshTable();
							} else {
								$("body").loadingInfo("warn", msg.message);
							}
						}
			    	});
				};
				
				var freshTable = function(){
					var num =  $('#dataList').bootstrapTable('getOptions').pageNumber;
					var size =  $('#dataList').bootstrapTable('getOptions').pageSize;
					var record =  $('#dataList').bootstrapTable('getOptions').totalRows;
					var total =  $('#dataList').bootstrapTable('getOptions').totalPages
					if(num == total && (record - 1)%size == 0){
						num = num-1;
						 $('#dataList').bootstrapTable('refresh', {query: {currentPage:num}});
					}else{
						 $('#dataList').bootstrapTable('refresh', {query: {currentPage:num}});
					}; 
				};
			}
	}.init();
})