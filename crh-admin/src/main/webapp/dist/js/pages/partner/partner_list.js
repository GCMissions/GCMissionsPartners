$(function(){
	var partnerList = {
			init : function() {
				var _self = this;
				_self.initEvents();
			},
			initEvents : function(){
				var bootTable = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					pagination : true,
					pageSize : 10,
					url: 'partner/list',
					sidePagination:'server',
					queryAddParams: function() {
						
					},
					columns: [{
                		width: '5%',
                		formatter: function(value,row,index){
                			return index+1;
                		}
					} ,{
						field: 'partnerName',
						width: '25%'
					} , {
						field: 'mission',
						width: '20%'
					} , {
						field: 'countryName',
						width: '15%'
					} , {
						field: 'regionName',
						width: '15%'
					} , {
						field: 'id',
						width: '20%',
						formatter: function(value,row,index){
							var result = "";
							result += "<a href='" + urlPrefix + "partner/view/" + value + "/1' class='detailItem' data-id='"+value+"'>View</a>";
							result += "<a style='margin-left: 10%;' href='" + urlPrefix + "partner/view/" + value + "/2' class='editItem' data-id='"+value+"'>Edit</a>";
							result += "<a style='margin-left: 10%;' href='javascript:void(0)' class='deleteItem' data-id='"+value+"'>Delete</a>";
							return result;
						} 
					}]
				});
				
				$("#dataList").on('click','.deleteItem',function(){
					var id = $(this).data("id");
					this.dialog =  BootstrapDialog.show({
		                title: 'Delete Partner',
		                type : BootstrapDialog.TYPE_WARNING,
		                message: 'Confirm to delete the partner?',
		                draggable: true,
		                size : BootstrapDialog.SIZE_SMALL,
		                buttons: [{
		                    label: 'Confirm',
		                    cssClass: 'btn-primary saveAddEditTpl',
		                    action: function(dialog) {
		                    	dialog.close();
		                    	deletePartner(id);
		                    }
		                }, {
		                    label: 'Cancel',
		                    action: function(dialog) {
		                        dialog.close();
		                    }
		                }]
		            });
				});
				
				var deletePartner = function(id){
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "partner/delete",
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