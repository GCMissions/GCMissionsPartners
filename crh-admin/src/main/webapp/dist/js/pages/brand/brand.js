$(function() {
	var brand = {
		$dataList : $('#dataList'),
        $addItem : $('.addItem'),
        $searchItems :$('#search'),
        $removeItem : $('.removeItem'),
        editPostUrl : "edit",
        addPostUrl : "add",
        picUrl : "",
        editImgFlag:true,
        dialog : void 0,
        bootTable : void 0,
        fileOptions : {
			showUpload: false,
			showCaption: false,
			showRemove:false,
			uploadAsync:true,
			autoReplace : true,
			maxFileCount: 1,
			validateInitialCount: true,
			overwriteInitial: false,
			maxFileSize: 2000,
			uploadUrl: $.GLOBAL.config.uploadUrl.template({source: uploadSourcesMap.brand}), 
			language: 'zh',
			allowedFileExtensions : ['jpg', 'png','gif'],
		},
		init : function() {
			this.bindEvents();
            this.bootTable = $.GLOBAL.utils.loadBootTable({
            	table : this.$dataList,
        	    removeBtn : $('#removeRecord'),
        	    refreshBtn : $('#refreshRecord'),
        	    idField : "brandId",
        	    pagination : true,
        	    url: 'brand/list',
        	    sidePagination:'server',
        	    queryParamsType: "limit",
        	    queryAddParams: function() {
        	    	return {
        	    		keyWord: ""
        	    	}
        	    },
        	    columns: [
//        	              {
//        	            //field: 'brand_id',
//        	            	  checkbox: true
//        	              }, 
        	              {
								width : 50,
								align: 'center',
								formatter:function(value,row,index){  
									return index+1;
								}
        	              },
        	              {
        	            	  field: 'brandName',
        	              },
        	              {
        	            	  field: 'logo',
        	            	  formatter:function(value,row,index){
        	            		  if(value){
        	            			  return ' <a> <img src="'+fileUrlPrefix+row.logo+'" height=30></a>';
        	            		  }
        	            	  } 
        	              },
        	              {
        	            	  field: 'sort',
        	              },
        	              {
        	            	  field: 'createDate',
        	              },
        	              {
        	            	  //title: '操作',
        	            	  field: 'brandId',
        	            	  align: 'center',
        	            	  checkbox: false,
        	            	  formatter:function(value,row,index){         	               
        	            		  return ' <a  title="修改" href="javascript:;" class="editItem" data-id="'+row.brandId+'"> <i class="fa fa-edit "  style="font-size:20px"></i></a>' +
        	            		  ' <a  title="删除"  href="javascript:;" class="removeItem" data-id="'+row.brandId+'"> <i class="fa fa-trash "  style="font-size:20px"></i></a>';
        	            	  } 
        	              }
    	              ]
            	});
		},
		
		bindEvents : function() {
			var that = this;
			this.$addItem.on("click", function() {
				that.dialog = BootstrapDialog.show({
			        title: '添加品牌',
			        message: $(template('addEditTpl', {})),
			        draggable: true,
			        buttons: [{
			            label: '保存',
			            cssClass: 'btn-primary',
			            action: function(dialog) {
			            	if($('#addEditForm').validate().form()){
			            		var previewImg=$('.file-preview-image').length;
			            		if(previewImg>0){
			            			$("#file-0a").fileinput('upload');
			            		}else{
			            			that.save();
			            		}
			            	}			            				            	
			            }
			        }, {
			            label: '取消',
			            action: function(dialog) {
			                dialog.close();
			            }
			        }],
			        onshown : function() {
			        	that.picUrl="";
			        	that.initFileInput("file-0a",that.fileOptions);
			        	//图片上传完成后回调函数
			        	$('#file-0a').on('fileuploaded', function(event, data, previewId, index) {
			        	    var response = data.response;
			        	        that.picUrl = response.data;
			        	        that.save();
			        	});
			        }			      
			    });
			}); 
			this.$dataList.on("click", ".editItem", function() {
		 		//@TODO
		 		var brandId=this.dataset.id;
		 			editurl="detail/"+brandId;
		 		$.ajax({
					type: "POST",
					url : editurl,
					dataType : "json",
					async:true,
					success:function(result){
						that.dialog = BootstrapDialog.show({
					        title: '修改品牌',
					        message: $(template('editTpl', result.data)),
					        draggable: true,
					        buttons: [{
					            label: '保存',
					            cssClass: 'btn-primary',
					            action: function(dialog) {
					            	if($('#addEditForm').validate().form()){
						            	var previewImg=$('.file-preview-image').length;					            	
						            	if(previewImg==0||that.editImgFlag){
						            		that.save();
						            	}else{
						            		$("#file-0a").fileinput('upload');
						            	}
					            	}
					            }
					        }, {
					            label: '取消',
					            action: function(dialog) {
					                dialog.close();
					            }
					        }],
					        onshown : function() {
					        	var len = $('#file-0a')[0].attributes.length;
					        	var	src;
					        	for(i=0;i<len;i++){
					        		var imgSrc=$('#file-0a')[0].attributes[i];
					        		if(imgSrc.name=="value"){
					        			src=imgSrc.value;
				        			}
					        	}
					        	that.editImgFlag=true;
					        	that.picUrl=src;
					        	if(src){
					        		var op =$.extend({initialPreview:["<img src='" + fileUrlPrefix+ src + "' class='file-preview-image'>",]}, that.fileOptions);
					        		that.initFileInput("file-0a",op);
					        	}else{
					        		that.initFileInput("file-0a",that.fileOptions);
					        	}
					        	$('#file-0a').on('fileuploaded', function(event, data, previewId, index) {
					        	    var response = data.response;
					        	        that.picUrl = response.data;
					        	    	that.save();
					        	});
					        	$('#file-0a').on('fileuploaderror', function(event, data, msg) {
					        	    var response = data.response;
					        	    that.$dataList.loadingInfo({text : response.message});
					        	});
					        	$('#file-0a').on('filebatchselected', function(event, files) {
					        	   that.editImgFlag=false;
					        	});
					        }
					    });
						that.bootTable.refresh();
					},
				});
		 		
			});
			this.$dataList.on("click", "a.removeItem", function() {
				var brandId = this.getAttribute('data-id');
				var delUrl="delete/"+brandId;
				that.dialog =  BootstrapDialog.show({
	                title: '删除品牌',
	                type : BootstrapDialog.TYPE_WARNING,
	                message: message('admin.dialog.deleteConfirm'),
	                draggable: true,
	                size : BootstrapDialog.SIZE_SMALL,
	                buttons: [{
	                    label: '确认删除',
	                    cssClass: 'btn-primary saveAddEditTpl',
	                    action: function(dialog) {
	                    	$.ajax({
								type: "get",
								url : delUrl,
								dataType : "JSON",
								contentType : 'application/json',
								data: JSON.stringify(),
							}).done(function(result) {
						 		if(result.code == "ACK"){
						 			that.dialog.close();
						 			that.bootTable.refresh();
						 			that.$dataList.loadingInfo({text : result.message});
						 		}else{
						 			that.dialog.close();
						 		}				 	
							 })
	                    }
	                }, {
	                    label: '取消',
	                    action: function(dialog) {
	                        dialog.close();
	                    }
	                }]
	            });
			});
			
			this.$searchItems.on("click", function() {
				that.bootTable.options.queryAddParams = function(){
					return {
						 keyWord: $("#brandName").val(),
					}
				};
				that.bootTable.refresh();
			});
		
		},	
		getFormJson : function($form) {
			var tempData = $form.frmSerialize();
			data = {	
				brandName   : tempData.brandName.replace(/(^\s*)|(\s*$)/g, ""),
				sort		: tempData.sort,
				logo	    : this.picUrl,
				description : tempData.description,
			};
			return data;
		},	 	
		initFileInput : function(idName,Options){
			var control = $("#"+idName);
			control.fileinput(Options);
		},   
		save : function() {
			var $form = $('#addEditForm'),
			    that = this,
			    brandId = $form.find('input[name="brandId"]').val(),
			    action = brandId ? that.editPostUrl: that.addPostUrl,
			    data = this.getFormJson($form);
				if(brandId){data.brandId=brandId}
			    $.ajax({
				  type: 'POST',
				  url: action,
				  dataType: 'json',
				  contentType: 'application/json',
				  data:  JSON.stringify(data)
			    })
				.done(function(result) {
			 		if(result.code == "ACK"){
			 			that.dialog.close();
			 			that.bootTable.refresh();
			 			that.$dataList.loadingInfo({text : result.message});
			 		}else{
			 //			that.dialog.close();
			 		}				 	
				 })
				 .fail(function(result) {
				 	$('#addEditForm').loadingInfo({
				 		text : "保存失败",
				 		type : "error",
				 		callBack : function() {
				 			//that.dialog.close();
			           	    //that.bootTable.refresh(); 
				 		}
				 	})
				 });
			
		}
	};
	brand.init();	
});
