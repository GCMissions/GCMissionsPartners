$(function() {
	
	var goodsType = {
		$dataList : $('#dataList'),
        $addItem : $('.addItem'),
        dialog : void 0,
        bootTable : void 0,
		iCheckbox : void 0,
		addURL :'ptype/add',
		editURL : 'ptype/edit',
		addDataURL : 'ptype/addData',
		editDataURL : 'ptype/editData/{{id}}',
		deleteURL : 'ptype/delete/{{id}}',
		
		init : function() {
			this.bindEvents();
			
			this.bootTable = $.GLOBAL.utils.loadBootTable({
				table : $('#dataList'),
				removeBtn : $('#removeRecord'),
				refreshBtn : $('#refreshRecord'),
				idField : "brandId",
				url: 'ptype/list',
				sidePagination:'server',
				pageSize : 10,
				pagination : true,
				currentPage: 1,
				queryParamsType: "limit",
				queryAddParams: function() {
					return {
					//	 keywords: "keyworddemo"
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
						field: 'typeName',
						align: 'center'
					} ,
					{
						field: 'brandNames',
						align: 'center'
					} ,
					{
						field: 'attrNames',
						align: 'center'
					} ,
					{
						field: 'sort',
						align: 'center',
					} ,
					{
						field: 'typeId',
						align: 'center',
						checkbox: false,
						formatter:function(value,row,index){  
							return ' <a  title="修改"  class="editItem" data-id="'+row.typeId+'"> <i class="fa fa-edit "  style="font-size:20px"></i></a>'
                            +' <a  title="删除" class="removeItem" data-id="'+row.typeId+'"> <i class="fa fa-trash "  style="font-size:20px"></i></a>'
                            ;
						} 
					}
				 ]
			});
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
							title: '添加类型',
							message: $(template('addEditTpl',response.data)),
							draggable: true,
							buttons: [{
								label: '保存',
								cssClass: 'btn-primary saveAddEditTpl',
								action: function(dialog,e ) {
									that.save($(e.target));
								}
							}, {
								label: '取消',
								action: function(dialog) {
									dialog.close();
								}
							}],
							
						});
					}
				});
			});
			
			this.$dataList.on("click", "a.editItem", function(){
				var typeId = this.getAttribute('data-id');
				$.ajax({
					type: "POST",
					url: urlPrefix + that.editDataURL.template({id : typeId}),
					dataType: 'json',
					contentType: 'application/json'
				})
				.done(function(response){
					if(response.code == "ACK"){
						that.dialog =  BootstrapDialog.show({
							title: '修改类型',
							message: $(template('editTpl',response.data)),
							draggable: true,
							buttons: [{
								label: '保存',
								cssClass: 'btn-primary saveAddEditTpl',
								action: function(dialog, e) {
									that.save($(e.target));
								}
							}, {
								label: '取消',
								action: function(dialog) {
									dialog.close();
								}
							}],
							
						});
					}
				});
			});
			this.$dataList.on("click", "a.removeItem", function() {
				var typeId = this.getAttribute('data-id');
				that.dialog =  BootstrapDialog.show({
	                title: '删除类型',
	                type : BootstrapDialog.TYPE_WARNING,
	                message: message('admin.dialog.deleteConfirm'),
	                draggable: true,
	                size : BootstrapDialog.SIZE_SMALL,
	                buttons: [{
	                    label: '确认删除',
	                    cssClass: 'btn-primary saveAddEditTpl',
	                    action: function(dialog) {
	                    	dialog.close();
	                        that.doRemove(that, typeId);
	                    }
	                }, {
	                    label: '取消',
	                    action: function(dialog) {
	                        dialog.close();
	                    }
	                }]
	            });
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
			if(data.attrIds && !(data.attrIds instanceof Array)) {
				data.attrIds = new Array(data.attrIds);
			}
			if(data.brandIds && !(data.brandIds instanceof Array)) {
				data.brandIds = new Array(data.brandIds);
			}	
			return data;
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
    				$('body').loadingInfo({
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
    			$('body').loadingInfo("error", message("admin.message.error"));
    		});
        },
		
		save : function($btn) {
			var $form = $('#addEditForm'),
	   		    that = this,
	            typeId = $form.find('input[name="typeId"]').val(),
	            action = typeId ? that.editURL: that.addURL,
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
							text : result.message,
        	 				callBack : function() {
        	 					that.dialog.close();
        	 					that.bootTable.refresh(); 
        	 				}
        	 			});
        	 		}
	        	 })
	        	 .fail(function(result) {
	        		 $form.loadingInfo({
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
	        } else {
	        	console.log("valid fail");
	        }
        }
	};
			
	goodsType.init();
});