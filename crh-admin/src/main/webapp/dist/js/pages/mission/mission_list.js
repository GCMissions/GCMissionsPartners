$(function(){
	var slideList = {
			init : function() {
				var _self = this;
				_self.initEvents();
			},
			initEvents : function(){
				var bootTable = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					pagination : true,
					pageSize : 10,
					url: 'slides/list',
					sidePagination:'server',
					queryAddParams: function() {
						
					},
					columns: [{
						field: 'index',
                		width: '5%'
					} ,{
						field: 'image',
						width: '20%',
						formatter: function(value,row,index){
							return "<div class='adspic-list'><ul><li class='adspic-upload'><div class='upload-thumb'>" +
								   "<img src='" + value + "' class='slideImage'>" +
								   "</div></li></ul></div>";
						}
					} , {
						field: 'description',
						width: '35%'
					} , {
						field: 'displayed',
						width: '20%'
					}, {
						field: 'id',
						width: '20%',
						formatter: function(value,row,index){
							var result = "";
							result += "<a href='" + urlPrefix + "slides/view/" + value + "' class='detailItem' data-id='"+value+"'>Edit</a>";
							if (row.index != row.totalRecords) {
								result += "<a style='margin-left: 10%;' href=''  class='downItem' data-id='"+value+"'>Down</a>";
							}
							if (row.index != "1") {
								result += "<a style='margin-left: 10%;' href='' class='upItem' data-id='"+value+"'>Up</a>";
							}
							result += "<a style='margin-left: 10%;' href='javascript:void(0)' class='deleteItem' data-id='"+value+"'>Delete</a>";
							return result;
						} 
					}]
				});
				
				$("#dataList").on('click','.deleteItem',function(){
					var id = $(this).data("id");
					this.dialog =  BootstrapDialog.show({
		                title: 'Delete Slide',
		                type : BootstrapDialog.TYPE_WARNING,
		                message: 'Confirm to delete the slide?',
		                draggable: true,
		                size : BootstrapDialog.SIZE_SMALL,
		                buttons: [{
		                    label: 'Confirm',
		                    cssClass: 'btn-primary saveAddEditTpl',
		                    action: function(dialog) {
		                    	dialog.close();
		                    	deleteSlide(id);
		                    }
		                }, {
		                    label: 'Cancel',
		                    action: function(dialog) {
		                        dialog.close();
		                    }
		                }]
		            });
				});
				
				var deleteSlide = function(id){
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "slides/delete",
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
				
				$("#dataList").on('click','.downItem',function(){
					var id = $(this).data("id");
					var dataParam = {
							"id":id,
							"type": "0"
					};
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "slides/sort",
						dataType     : 'json',
						contentType: "application/json;charset=utf-8",
						data 	     : JSON.stringify(dataParam),
						success : function(msg){
							if (msg.code == "ACK") {
								bootTable.refreshThis();
							} else {
								$("body").loadingInfo("warn", msg.message);
							}
						}
			    	});
				});
				
				$("#dataList").on('click','.upItem',function(){
					var id = $(this).data("id");
					var dataParam = {
							"id":id,
							"type": "1"
					};
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "slides/sort",
						dataType     : 'json',
						contentType: "application/json;charset=utf-8",
						data 	     : JSON.stringify(dataParam),
						success : function(msg){
							if (msg.code == "ACK") {
								bootTable.refreshThis();
							} else {
								$("body").loadingInfo("warn", msg.message);
							}
						}
			    	});
				});
				
				var freshTable = function(){
					var num =  $('#dataList').bootstrapTable('getOptions').pageNumber;
					var size =  $('#dataList').bootstrapTable('getOptions').pageSize;
					var record =  $('#dataList').bootstrapTable('getOptions').totalRows;
					var total =  $('#dataList').bootstrapTable('getOptions').totalPages
					if(num == total && (record - 1)%size == 0&&num>1){
						 num = num-1;
						 $('#dataList').bootstrapTable('refresh', {query: {currentPage:num}});
					}else{
						 $('#dataList').bootstrapTable('refresh', {query: {currentPage:num}});
					}; 
				};
			}
	}.init();
})