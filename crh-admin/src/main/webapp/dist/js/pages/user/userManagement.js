$(function(){
	var user = {
			$dataList : $('#dataList'),
	        $addItem : $('.addItem'),
	        $removeItem : $('.removeItem'),
	        $searchItem : $('#searchBtn'),
	        dialog : void 0,
	        bootTable : void 0,
	        addURL :'user/add',
			editURL : 'user/edit',
			addDataURL : 'user/addData',
			editDataURL : 'user/editData/{{id}}',
			deleteURL : 'user/delete/{{id}}',
			//unlockURL : 'user/unlock/{{id}}',
	        
	        init : function() {
	        	this.bindEvents();
	        	
				this.bootTable = $.GLOBAL.utils.loadBootTable({
					table : this.$dataList,
					removeBtn : $('#removeRecord'),
					refreshBtn : $('#refreshRecord'),
					idField : "",
					url: 'user/list',
					sidePagination:'server',
					pageSize : 10,
					pagination : true,
					currentPage: 1,
					queryParamsType: "limit",
					queryAddParams: function() {
						return {
							userName: "",
							loginId: "",
							phone: "",
							email: ""
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
							field: 'loginId',
							align: 'center'
						} ,
						{
							field: 'email',
							align: 'center'
						} ,
						{
							field: 'role',
							align: 'center'
						} ,
						{
							field: 'createTime',
							align: 'center'
						} ,
						{
							field: 'id',
							align: 'center',
							checkbox: false, 
							formatter:function(value,row,index){  
								var handleField;
								handleField = '<a  title="Edit" class="editItem" href="javascript:void(0)" data-id="'+row.id+'" style="margin-right: 4%;">Edit</a>'
								+'<a  title="Delete" class="removeItem" href="javascript:void(0)" data-id="'+row.id+'">Delete</a>';
								return handleField;
							} 
						}
					 ]
				});
			},
			
			getJson : function($form) {
				$.fn.serializeObject = function(){
				    var o = {};
				    var a = this.serializeArray();
				    $.each(a, function() {
				        if (o[this.name] !== undefined) {
				            if (!o[this.name].push) {
				                o[this.name] = [o[this.name]];
				            }
				            o[this.name].push($.trim(this.value) || '');
				        } else {
				            o[this.name] = $.trim(this.value) || '';
				        }
				    });
				    return o;
				};
				var data = $form.serializeObject();
//				data.password =  $.md5(data.password);
				if(data.roleIds && !(data.roleIds instanceof Array)) {
					data.roleIds = new Array(data.roleIds);
				}	
				return data;
			},
			
			bindEvents : function() {
				var that = this;
				that.$addItem.on("click", function() {
					$.ajax({
						type: "POST",
						url: urlPrefix + that.addDataURL,
						dataType: 'json',
						contentType: 'application/json'
					})
					.done(function(response){
							if(response.code == "ACK"){
								that.dialog =  BootstrapDialog.show({
									title: 'Add User',
									message: $(template('addEditTpl',response)),
									draggable: true,
									buttons: [{
										label: 'Save',
										cssClass: 'btn-primary saveAddEditTpl',
										action: function(dialog, e) {
											that.save($(e.target));
										}
									}, {
										label: 'Cancel',
										action: function(dialog) {
											dialog.close();
										}
									}],
									
								});
							}
						});
				});
				
				that.$dataList.on("click", "a.removeItem", function() {
					var	id = this.getAttribute('data-id');
					that.dialog =  BootstrapDialog.show({
		                title: 'Delete User',
		                type : BootstrapDialog.TYPE_WARNING,
		                message: 'Confirm to delete the user?',
		                draggable: true,
		                size : BootstrapDialog.SIZE_SMALL,
		                buttons: [{
		                    label: 'Confirm',
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
							 userName: $("input[name='userName']").val(),
							 phone: $("input[name='phone']").val(),
							 email: $("input[name='email']").val(),
							 loginId: $("input[name='loginId']").val(),
							 roleId:$('#roleId').val()
						}
					};
					that.bootTable.refresh();
				});
				
				that.$dataList.on("click", "a.editItem", function() {
					var id = this.getAttribute('data-id');
					$.ajax({
						type: "POST",
						url: urlPrefix+ that.editDataURL.template({id : id}),
						dataType: "json",
						contentType: "application/json"
					})
					.done(function(response){
						if(response.code == "ACK"){
							that.dialog =  BootstrapDialog.show({
								title: 'Edit User',
								message: $(template('editTpl',response.data)),
								draggable: true,
								buttons: [{
									label: 'Save',
									cssClass: 'btn-primary saveAddEditTpl',
									action: function(dialog, e) {
										that.save($(e.target));
									}
								}, {
									label: 'Cancel',
									action: function(dialog) {
										dialog.close();
									}
								}],
								
							});
							$(document).on("click","input[name='lockUser']",function(){
								$("input[name='lockUser']").attr("disabled","disabled");
								$("input[name='unlock']").val("0");
							});
						}
					});
				});
			},
			
			doRemove : function(that, id) {
	        	$.ajax(
	            { 
	        		type         : 'GET',
					url          : urlPrefix + this.deleteURL.template({id: id}),
					dataType     : 'json',
					contentType  : 'application/json',
	    		})
	    		.done(function(result) {
	    			if(result.code == "ACK"){
	    				$(window).loadingInfo({
	    					type : "success", 
	    					text: message("admin.message.success"),
	    					callBack : function() {
	    						//@TODO Delete rows
	    						that.dialog.close();
	    						that.bootTable.refresh(); 
	    					}
	    				});
	    			}
	    		})
	    		.fail(function(result) {
	    			$(window).loadingInfo("error", message("admin.message.error"))
	    		});
	        },
			
			save : function($btn) {
				
				var $form = $('#addEditForm'),
		   		    that = this,
		            userId = $form.find('input[name="id"]').val(),
		            action = userId ? that.editURL: that.addURL,
		            data = that.getJson($form);
				
		        if($form.validate().form()) {
		        	$btn.saving();
		            $.ajax({
					  type: 'POST',
					  url: urlPrefix + action,
					  dataType: 'json',
					  contentType: 'application/json',
					  data:  JSON.stringify(data),
					  $loadingObject : $form
		            })
		        	.done(function(result) {
	        	 		if(result.code == "ACK"){
	        	 			$form.loadingInfo({
	        	 				type : "success",
	        	 				text: message("admin.message.success"),
	        	 				callBack : function() {
	        	 					that.dialog.close();
	        	 					that.bootTable.refresh(); 
	        	 				}
	        	 			});
	        	 		}
		        	 })
		        	 .fail(function(result) {
		        		 $form.loadingInfo({
		        	 		text : "Save failed",
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
		        } else {
		        	console.log("valid fail");
		        }
	        }
			
	};
	
	user.init();
});