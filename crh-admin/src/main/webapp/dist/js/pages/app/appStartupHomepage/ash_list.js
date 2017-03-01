$(function() {
	var startUpHomepages = {
		initEvents : function() {
			var _this = this;
			var table = $.GLOBAL.utils
					.loadBootTable({
						table : $('#dataList'),
						sidePagination : 'server',
						pagination : true,
						url : "appStartupHomepage/list",
						queryParamsType : "limit",
						pageList : [ 10, 25, 50 ],
						queryAddParams : function() {

						},
						columns : [
								{
									title : '序号',
									field : 'index',
									align : 'center',
									width : '5%',
									formatter : function(value, row, index) {
										var sort = index + 1;
										return "<div class='text-center'><label>"
												+ sort + "：</label></div>";
									}
								},
								{
									title : '启动页图片',
									field : 'uploadImageUrl',
									width : '35%',
									align : 'center',
									formatter : function(value, row, index) {
										if (value != "" && value != null) {
											return "<div class='adspic-list'><ul><li class='adspic-upload ' "
													+ "><div class='upload-thumb'>"
													+ "<img src='"
													+ value
													+ "' class='adImg'>"
													+ "</div></li></ul></div>"
										} else {
											return "<div class='adspic-list col-sm-3'><ul><li class='adspic-upload ' "
													+ "><div class='upload-thumb'>"
													+ "<img src='${base}/dist/img/default_goods_image_240.gif'>"
													+ "</div></li></ul></div>"
										}
									}
								},
								{
									title : '手机类型',
									field : 'mobileTypeStr',
									align : 'center',
								},
								{
									title : '长度',
									field : 'height',
									align : 'center',
								},
								{
									title : '宽度',
									field : 'width',
									align : 'center',
								},
								{
									title : '版本号',
									field : 'version',
									align : 'center',
								},
								{
									title : '操作',
									field : 'operation',
									width : '25%',
									formatter : function(value, row, index) {
										var id = row.id;
										var result = "<div class='btn-group'><button class='btn btn-primary delete_startup' type='button' data-id='"
												+ id + "'>删除 </i></button>"+
												"<button class='btn btn-primary edit_startup' type='button' data-id='"
												+ id + "'>编辑 </i></button>"
										result += "</div>";
										return result;
									}
								} ]
					});
			
			$("#dataList").on('click','.edit_startup',function(){
				var id = $(this).data("id");
				window.location.href = "editor/" + id;
			});
			
			$("#dataList").on('click','.delete_startup',function(){
				var id = $(this).data("id");
				this.dialog =  BootstrapDialog.show({
					 title: '删除启动页',
					 type : BootstrapDialog.TYPE_WARNING,
					 message: message('admin.dialog.deleteConfirm'),
					 draggable: true,
					 size : BootstrapDialog.SIZE_SMALL,
					 buttons: [{
						 label: '确认删除',
						 cssClass: 'btn-primary ',
						 action: function(dialog) {
							 dialog.close();
							 deleteStartUp(id);
						 }
					 }, {
						 label: '取消',
						 action: function(dialog) {
							 dialog.close();
						 }
					 }]
				});
			});
			var deleteStartUp = function(id){
				$.ajax({
		    		type         : 'POST',
					url          : urlPrefix + "appStartupHomepage/delete",
					dataType     : 'json',
					data 	     : {
						"id" : id
					},
					success : function(msg){
						if (msg.code == "ACK") {
							$(window).loadingInfo("success", msg.message);
							freshTable();
						} else {
							$(window).loadingInfo("warn", msg.message);
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
		},
		init : function() {
			var _self = this;
			_self.initEvents();
		}
	}.init();
	

});