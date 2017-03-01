$(function(){
	var carousel = {
		init : function(){
			this.pos = 'carousel';
			this.bindEvent();
			this.tempCarouselId = 0;
		},
		bindEvent : function(){
			var _this = this;
			$("#circleTab").on('change', '.carousel_upload .upload-btn input[type="file"]', function(){
	            var id = $(this).data("id");
	            coolbag.ajaxFileUpload(_this.pos,id,this);
	        });
			
			//保存（修改）
			$("#circleTab").on('click', '.carousel_upload .save', function(){
				var id = $(this).data("id");
				coolbag.updateImage(_this.pos,id,$(this));
	        });
			
			//删除
			$("#circleTab").on('click', '.carousel_upload .delete', function(){
				var id = $(this).data("id");
				var currentForm = $(this).parents('.carousel_form');
				coolbag.removeCarousel(id,currentForm);
	        });
			
			$("#circleTab").on('click', '.carousel_upload .chooseDialog', function(){
				var id;
				if($(this).data("id")){
					id = $(this).data("id");
				}else{
					id = $(this).data("tempid");
				}
				_this.showFeatureDialog(id);
	        });
			
			//新增轮播按钮
			$("#newCircle").on('click',function(){
				_this.insertNewCircle();
			});
			
			$("body").on("click",".chooseFeature",function(){
				var imageId = $(this).data("imageid");
				var id = $(this).data("id");
				var name = $(this).data("name");
				
				$("#carousel_feature_"+imageId).val(id);
        	 	$("#carousel_feature_name_"+imageId).val(name);
        		_this.featureDialog.close();

			});
		},
		
		//新增轮播
		insertNewCircle : function(){
			var _this = this;
			var currentCarouselNum = Number($("#circleTab").find('.carousel_form:last').find('.carouselSort').val()) + 1;
			var showIndexNum =  Number($("#circleTab").find('.carousel_form:last').find('label').data('index')) + 1;
			var newCircle =  '<div class="row carousel_upload carousel_form">'
				            +'<div class="col-sm-1 text-right"><label data-index='+showIndexNum+'>图片'+ showIndexNum +'：</label></div>'
				            +'<input type="hidden" class="carouselSort" value='+currentCarouselNum+'>'
				            +'<div class="adspic-list col-sm-3"><ul><li class="adspic-upload " id="carousel_upload">'
				            +'<div class="upload-thumb">'
				            +'<img src="'+ uiBase +'img/default_goods_image_240.gif"  id="carousel_img">'
				            +'<input type="hidden" name="id" value=""><input type="hidden" class="imageUrl" name="url" value=""  id="carousel_image"></div>'
				            +'<div class="upload-btn">'
				            +'<a href="javascript:void(0);"><span><input type="file" hidefocus="true" size="1" class="input-file" name="file" id="carousel_'+ _this.tempCarouselId +'" data-id="" accept=".jpg,.png,.gif"></span>'
				            +'<p><i class="fa fa-fw fa-upload"></i>上传</p>'
				            +'</a></div></li></ul></div>'
				            +'<div class="col-sm-4"><label>关联专辑：</label>'
				            +'<input type="text" class="form-control chooseDialog" name="name" value="" id="carousel_feature_name_'+ _this.tempCarouselId +'" readonly="readonly"/>'
				            +'<button class="btn btn-default chooseDialog"  style="margin-left:5px;" type="button" data-tempid="'+ _this.tempCarouselId +'">浏览</button> '
				            +'<br><label>关联url：</label><input type="text" class="form-control relfUrl" name="relfUrl" value="" id="" style="margin:10px 0 0 5px;"/>'
				            +'<input type="hidden" name="featureId" value="" id="carousel_feature_'+ _this.tempCarouselId +'"></div>'
				            +'<div class="col-sm-2"><button class="btn btn-primary save" type="button" data-id="">保存 </i></button><button class="btn btn-primary delete" type="button" style="margin-left:5px;" data-id="">删除 </i></button></div>'
				            +'</div>';
			$("#circleTab").append(newCircle);
			_this.tempCarouselId ++;
		},
		
		showFeatureDialog : function(id){
			var _this = this;
			_this.featureDialog = BootstrapDialog.show({
		        title: '选择专辑',		    
		        size :  BootstrapDialog.SIZE_WIDE,
		        message: $(template('featureTpl',{})),
		        draggable: false,
		        onshown: function(dialogRef){
		        	_this.initFeatureList(id);
	            }
		    });
		},
		
		initFeatureList : function(id){
			return $.GLOBAL.utils.loadBootTable({
				table : $('#featureList'),
			    sidePagination:'server',
			    pagination : true,
			    url : "coolbag/feature/list",
			    queryParamsType: "limit",
			    pageSize : 5,
			    pageList:[5,10,20],
			    queryAddParams: function() {
			    	return {
			    		source : 1
			    	};
			    },
			    columns: [{
					title: '序号',
				    align: 'center',
				    width:50,
				    formatter:function(value,row,index){  
				    	return index+1; 
				    }
				} , {
		        	title: '专辑封面',
		            field: 'image',
		            width: 140,
		            formatter:function(value,row,index){
		            	return '<div class="feature_img"><img src="'+ value +'"></div>'
		            }
		        } , {
		        	title: '专辑名称',
		            field: 'name'
		        } , {
		        	title: '操作',
		        	field: 'id',
		            align: 'center',
		            formatter:function(value,row,index){  
		                return ' <a href="javascript:void(0);" data-imageid="'+id+'" data-id="'+ value +'" data-name="'+row.name+'" class="chooseFeature">选择</a>';
		            } 
		        }]
			});
		}
	}.init();
	
	var tag = {
		init : function(){
			this.pos = 'tag';
			this.bindEvent();
		},
		bindEvent : function(){
			var _this = this;
			$(".tag_upload").on('change', '.upload-btn input[type="file"]', function(){
				var id = $(this).data("id");
	            coolbag.ajaxFileUpload(_this.pos,id);
	        });
			
			$(".tag_upload").on('click', '.save', function(){
				var id = $(this).data("id");
				coolbag.updateImageFeature(_this.pos,id,$(this));
	        });
		}
			
	}.init();
	
	var feature = {
		init : function(){
			this.initTable();
			this.bindEvent();
		},
		bindEvent : function(){
			var _this = this;
			
			$('#dataList').on("click", "a.removeItem", function() {
				var	id = this.getAttribute('data-id');
				_this.dialog =  BootstrapDialog.show({
	                title: '删除用户',
	                type : BootstrapDialog.TYPE_WARNING,
	                message: message('admin.dialog.deleteConfirm'),
	                draggable: true,
	                size : BootstrapDialog.SIZE_SMALL,
	                buttons: [{
	                    label: '确认删除',
	                    cssClass: 'btn-primary saveAddEditTpl',
	                    action: function(dialog) {
	                    	dialog.close();
	                    	$.ajax({ 
	                    		type         : 'GET',
	            				url          : urlPrefix + "coolbag/feature/delete/"+id,
	            				dataType     : 'json',
	            				contentType  : 'application/json',
	                		}).done(function(result) {
	                			if(result.code == "ACK"){
	                				$(window).loadingInfo({
	                					type : "success", 
	                					text: message("admin.message.success"),
	                					callBack : function() {
	                						//@TODO 删除行
	                						_this.dialog.close();
	                						_this.table.refreshThis();
	                					}
	                				});
	                			}
	                		}).fail(function(result) {
	                			$(window).loadingInfo("error", message("admin.message.error"))
	                		});
	                       // _this.doRemove(_this, id);
	                    }
	                	}, {
	                    label: '取消',
	                    action: function(dialog) {
	                        dialog.close();
	                    }
	                }]
	            });
			});
			$('#dataList').on("click", "a.sheifItem", function() {
				var	id = this.getAttribute('data-id');
				var data_name = this.getAttribute('data-name');
				var titleName;
				var messageInfo;
				if(data_name==="upSheif"){
					titleName='确认上架操作';
					messageInfo = '您确定要上架嘛?';
				}else{
					titleName='确认下架操作';
					messageInfo = '您确定要下架嘛?';
				}
				_this.dialog =  BootstrapDialog.show({
					title:titleName,
	                type : BootstrapDialog.TYPE_WARNING,
	                message: messageInfo,
	                draggable: true,
	                size : BootstrapDialog.SIZE_SMALL,
	                buttons: [{
	                    label: '确认操作',
	                    cssClass: 'btn-primary saveAddEditTpl',
	                    action: function(dialog) {
	                    	dialog.close();
	                    	$.ajax({ 
	                    		type         : 'GET',
	            				url          : urlPrefix + "coolbag/feature/sheif/"+id,
	            				dataType     : 'json',
	            				contentType  : 'application/json',
	                		}).done(function(result) {
	                			if(result.code == "ACK"){
	                				$(window).loadingInfo({
	                					type : "success", 
	                					text: message("admin.message.success"),
	                					callBack : function() {
	                						//@TODO 删除行
	                						_this.dialog.close();
	                						_this.table.refreshThis();
	                					}
	                				});
	                			}
	                		}).fail(function(result) {
	                			$(window).loadingInfo("error", message("admin.message.error"))
	                		});
	                       // _this.doRemove(_this, id);
	                    }
	                	}, {
	                    label: '取消',
	                    action: function(dialog) {
	                        dialog.close();
	                    }
	                }]
	            });
			});
			
			$("body").on('click', '.featureSort', function(){
				var id = $(this).data("id");
				var sort = $(this).data("sort");
				var type = $(this).data("type");
				var url =  urlPrefix + 'coolbag/feature/sort/' + id+"/"+sort+"/"+type;
				
				$.ajax({
				  type: 'GET',
				  url: url,
				  dataType : 'json',
				  success: function(result){
					  if(result.code == 'ACK'){
						  _this.table.refreshThis();
					  }
					}
				});
	        });
		},
		initTable : function(){
			var _this = this;
			this.table = $.GLOBAL.utils.loadBootTable({
				table : $('#dataList'),
			    sidePagination:'server',
			    pagination : true,
			    url : "coolbag/feature/list",
			    queryParamsType: "limit",
			    pageSize : 5,
			    pageList:[5,10,20],
			    queryAddParams: function() {
			    	return {
			    		source:0
			    	};
			    },
			    initSearchForm : true, 
			    columns: [
					{
						title: '序号',
					    align: 'center',
					    width:50,
					    formatter:function(value,row,index){  
					    	return index+1; 
					    }
					} , {
			        	title: '专辑封面',
			            field: 'image',
			            width: 140,
			            formatter:function(value,row,index){
			            	return '<div class="feature_img"><img src="'+ value +'"></div>'
			            }
					 } , {
			        	title: '专辑名称',
			            field: 'name'
					 } , {
						 title: '专辑描述',
						 field: 'description'
			        } , {
			        	title: '页面标签',
			        	field: 'tag'
			        } , {
			        	title: '所属标签',
			        	field: 'tagName'
			        } , {
			        	title: '创建时间',
			        	field: 'createDate'
			        } , {
			        	title: '上下架状态',
			        	field: 'sheifStatus',
			        	formatter:function(value,row,index){
			        		var status = ['未上架','已上架'];
			        		var result = '<span>'+status[value]+'</span>';
			        		return result;
			        	}
			        } , {
			        	title: '操作',
			        	field: 'id',
			        	width: 150,
			            align: 'center',
			            formatter:function(value,row,index){  
			            	var viewUrl = urlPrefix + 'coolbag/feature/view/' + row.id;
			            	var editUrl = urlPrefix + 'coolbag/feature/add/' + row.id;
			            	var result = ' <a  title="查看" href="'+viewUrl+'" class="viewItem" > <i class="fa fa-eye "  style="font-size:20px"></i></a>';
			            	
			            	//var total = _this.table.$dataListTable.bootstrapTable('getOptions').totalRows;
			            	var sheifStatus = row.sheifStatus;
			            	
			            	if(sheifStatus !== "1"){
			            		result += ' <a  title="修改" href="'+editUrl+'"> <i class="fa fa-edit "  style="font-size:20px"></i></a>';
			            	}
			            	
			            	if(row.sort < row.maxSortValue){
			            		result += ' <a  title="升序"  href="javascript:void(0)"   class="featureSort" data-id="'+row.id
			            		+'" data-sort="'+row.sort+'" data-type="desc" > <i class="fa fa-arrow-up"  style="font-size:20px"></i></a>';
			            	}
			            	
			            	if(row.sort > row.minSortValue){
			            		result += ' <a  title="降序" href="javascript:void(0)" class="featureSort" data-id="'+row.id
			            		+'" data-sort="'+row.sort+'" data-type="asc" > <i class="fa  fa-arrow-down"  style="font-size:20px"></i></a>';
			            	}
			            	if(sheifStatus == 0){
			            		result += ' <a  title="上架"  class="sheifItem" data-name ="upSheif" data-id="'+row.id +'" > <i  class ="fa fa-cart-plus" style="font-size:20px; cursor:pointer;"></i></a>';
			            	}
			            	
			            	if(sheifStatus == 1){
			            		result += ' <a  title="下架"  class="sheifItem" data-name ="downSheif" data-id="'+row.id +'" > <i  class ="fa fa-cart-arrow-down"  style="font-size:20px; cursor:pointer;"></i></a>';
			            	}
			            	
			            	if(sheifStatus !== "1"){
			            		result += ' <a  title="删除"  class="removeItem" data-id="'+row.id +'" > <i class="fa fa-trash"  style="font-size:20px; cursor:pointer;"></i></a>';
			            	}
			            	
			            	return result;
			            } 
			        }
			     ]
			});
		}
		
	}.init();
	
	var coolbag = {
		getData : function(formEl) {
			var result = {};
			formEl
				.find('input,textarea,select')
				.each(
	                function() {
	                    var key;
	                    if ($(this).attr("data-ignore") == "true") {
	                        return true;
	                    }
	                    if ($(this)
	                            .hasClass(
	                                    "select2-focusser select2-offscreen")
	                            || $(this).hasClass(
	                                    "select2-input")) {
	                        return true;
	                    }
	                    key = $(this).attr('name');
	                    if (key) {
	                    	if(key == 'file'){
	                    		return true;
	                    	}
	                    	
	                        if ($(this).attr("Type") == "checkbox") {
	                            if ($(this).val() == "true") {
	                                result[key] = true;
	                            } else {
	                                result[key] = false;
	                            }
	                        } else if ($(this).attr("Type") == "radio") {
	                            if ($(this).is(":checked")) {
	                                result[key] = $(this).val();
	                            } else {
	                                return;
	                            }
	                        } else {
	                            result[key] = $(this).val();
	                        }
	                    }
	                });
	        return result;
	    },	
	    ajaxFileUpload : function (pos,id,el) {
			var that = this;
			var elementId;
			var currentUploadLi = $(el).parents('li');
			if(id){
				$('#'+pos + '_img_' + id + '').attr('src', uiBase + "/img/loading.gif");
				elementId = pos+"_"+id;
			}else{
				currentUploadLi.find('img').attr('src', uiBase + "/img/loading.gif");
				elementId = $(el).attr('id');
			}
			$.ajaxFileUpload({
				url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.coolbag}),
				secureuri : false,
				fileElementId : elementId,
				dataType : 'json',
				global : false,
				data : {},
				success : function (data, status) {
					if (data.code == "ACK") {
						if(id){
						$('#'+pos + '_image_' + id + '').val(data.data.url);
						$('#'+pos + '_img_' + id + '').attr('src', data.data.url);   
						}else{
							currentUploadLi.find('.imageUrl').val(data.data.url);
							currentUploadLi.find('img').attr('src', data.data.url);
							}
						   
					} else {
						$(window).loadingInfo("error", data.message);
					}
				}
				
			});
			return false;  
		},
		updateImage : function(pos,id,$saveBtn){
			$saveBtn.saving();
			var formEl;
			if(id){
				formEl = $('#' + pos +'_form_'+id);
			}else{
				formEl = $saveBtn.parents('.carousel_form');
			}
			var data = coolbag.getData(formEl);
			
			if(!data.url){
				$("body").loadingInfo("warn", "请先上传图片");
				$saveBtn.saved();
				return;
			}
			$.ajax({ 
        		type         : 'post',
				url          : urlPrefix+"coolbag/img/update",
				dataType     : 'json',
				contentType  : 'application/json',
				data         : JSON.stringify(data)
			 }).done(function(result) {
				 $saveBtn.saved();
				if(result.code == "ACK") {
					$("body").loadingInfo("success", result.message);
					var currentSrc = window.frameElement.src;
					$(window.frameElement).attr('src',currentSrc);
				}
			 });
		},
		updateImageFeature : function(pos,id,$saveBtn){
			$saveBtn.saving();
			var formEl;
			if(id){
				formEl = $('#' + pos +'_form_'+id);
			}else{
				formEl = $saveBtn.parents('.carousel_form');
			}
			var data = coolbag.getData(formEl);
			
			if(!data.url){
				$("body").loadingInfo("warn", "请先上传图片");
				$saveBtn.saved();
				return;
			}
			$.ajax({ 
        		type         : 'post',
				url          : urlPrefix+"coolbag/img/update",
				dataType     : 'json',
				contentType  : 'application/json',
				data         : JSON.stringify(data)
			 }).done(function(result) {
				 $saveBtn.saved();
				if(result.code == "ACK") {
					$("body").loadingInfo("success", result.message);
					var currentSrc = window.frameElement.src;
				}
			 });
		},
		removeCarousel : function(id,currentForm){
			if(id){
				$.ajax({ 
					url          : urlPrefix+"coolbag/img/delete/"+id,
					dataType     : 'json',
					contentType  : 'application/json',
				 }).done(function(result) {
					if(result.code == "ACK") {
						$("body").loadingInfo("success", result.message);
						var currentSrc = window.frameElement.src;
						$(window.frameElement).attr('src',currentSrc);
					}
				 })
			}else{
				$(currentForm).remove();
			}
			
		}
	}
});