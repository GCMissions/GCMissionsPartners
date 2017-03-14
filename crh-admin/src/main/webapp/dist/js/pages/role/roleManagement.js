$(function(){
	var role = {
			$dataList : $('#dataList'),
	        $searchItem : $('#searchBtn'),
	        dialog : void 0,
	        bootTable : void 0,
	        deleteURL : 'role/delete/{{id}}',
	        
	        init : function() {
	        	this.bindEvents();
	        	
				this.bootTable = $.GLOBAL.utils.loadBootTable({
					table : this.$dataList,
					removeBtn : $('#removeRecord'),
					refreshBtn : $('#refreshRecord'),
					idField : "",
					url: 'role/list',
					sidePagination:'server',
					pageSize : 10,
					pagination : true,
					currentPage: 1,
					queryParamsType: "limit",
					queryAddParams: function() {
						return {
//							role: ""
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
							field: 'role',
							align: 'center'
						} ,
//						{
//							field: 'status',
//							align: 'center'
//						} ,
						{
							field: 'createDate',
							align: 'center'
						} ,
						{
							field: 'roleId',
							align: 'center',
							checkbox: false, 
							formatter:function(value,row,index){  
								var handleField = '<a  title="Editor" class="editItem" data-id="'+row.roleId+'" href="edit/'+row.roleId+'" style="margin-right: 4%;">Edit</a>'
											+'<a  title="Check" class="detailItem" data-id="'+row.roleId+'" href="detail/'+row.roleId+'" style="margin-right: 4%;">View</a>'
											+'<a  title="Delete" class="removeItem" data-id="'+row.roleId+'">Delete</a>';
								return handleField;
							} 
						}
					 ]
				});
			},
			
			bindEvents : function() {
				var that = this;
				
				that.$dataList.on("click", "a.removeItem", function() {
					var id = this.getAttribute('data-id');
					that.dialog =  BootstrapDialog.show({
		                title: 'Delete Role',
		                type : BootstrapDialog.TYPE_WARNING,
		                message: message('admin.dialog.deleteConfirm'),
		                draggable: true,
		                size : BootstrapDialog.SIZE_SMALL,
		                buttons: [{
		                    label: 'Confirm the deletion',
		                    cssClass: 'btn-primary saveAddEditTpl',
		                    action: function(dialog) {
		                    	dialog.close();
		                        that.doRemove(that, id);
		                    }
		                }, {
		                    label: 'Cancel',
		                    action: function(dialog) {
		                        dialog.close();
		                    }
		                }]
		            });
				});
				
				that.$searchItem.on("click",function() {
					that.bootTable.options.queryAddParams = function(){
						return {
							 role: $("input[name='role']").val(),
						}
					};
					that.bootTable.refresh();
				});
				
			},
			
			doRemove : function(that, id) {
	        	$.ajax(
	            { 
	        		type         : 'GET',
					url          : urlPrefix + that.deleteURL.template({id: id}),
					dataType     : 'json',
					contentType  : 'application/json',
	    		})
	    		.done(function(result) {
	    			if(result.code == "ACK") {
	    				$(window).loadingInfo({
	    					type : "success", 
	    					text: message("admin.message.success"),
	    					callBack : function() {
	    						//@TODO 删除行
	    						that.dialog.close();
	    						that.bootTable.refresh(); 
	    					}
	    				});
	    			}
	    		})
	    		.fail(function(result) {
	    			$(window).loadingInfo("error", message("admin.message.error"));
	    			
	    		});
	        }
			
	};
	
	role.init();
});