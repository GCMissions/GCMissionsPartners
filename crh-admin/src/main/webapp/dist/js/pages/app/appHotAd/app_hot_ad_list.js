$(function(){
	var advertise = {
			scanGoodsHtml : "<div class='popTreeDialogSection box-body form-horizontal'>"
				+"<div class='form-group '>"
		         +"<div>" 
		          +"<select name='cFirstCategory' id='cFirstCategory' class='form-control' style='width:30%;'></select>" 
		          +"<select name='cSecondCategory' id='cSecondCategory' class='form-control' style='width:30%;margin-left:25px;'></select>"
		          +"<select name='cOrgId' id='cOrgId' class='form-control' style='width:30%;margin-left:25px;'></select>" 
		         +"</div>"
		        +"</div>"
		        +"<div class='form-group '>"
		         +"<div><input type=text class='form-control' id='cActivityName' style='width:30%' placeholder='商品名称'>"
		         +"<button type=button id='rRefreshRecord' class='btn btn-primary' style='margin-left:25px'><i class='fa fa-search'></i>开始搜索</div>"
		        +"</div>"
		        +"<div class='row 　col-md-12　treeTable'>"
		                +"<table class='tree_table' id='copyGoodsList'>"
		                +"<thead class='borderRow'>"
		                +"<th  class='text-center'></th>"
		                +"<th  class='text-center'>商品品类</th>"
		                +"<th  class='text-center'>服务商</th>"
		                +"<th  class='text-center'>商品名称</th>"
		                +"</thead>"
		                +"<tbody>"
		                +"</tbody>"
		                +"</table>"
		        +"</div>" 
		        +"</div>"
		        ,	
	    ajaxFileUpload : function (pos,sort) {
			var that = this;
			var src = $('#'+pos + '_img_' + sort + '').attr('src');
			$('#'+pos + '_img_' + sort + '').attr('src', uiBase + "/img/loading.gif");
			$.ajaxFileUpload({
				url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.ad}),
				secureuri : false,
				fileElementId : pos+"_"+sort,
				dataType : 'json',
				global : false,
				data : {},
				success : function (data, status) {
					if (data.code == "ACK") {
						$('#'+pos + '_image_' + sort + '').val(data.data.url);
						$('#'+pos + '_img_' + sort + '').attr('src', data.data.url);   
					} else {
						$(window).loadingInfo("error", data.message);
						$('#'+pos + '_image_' + sort + '').val(src);
						$('#'+pos + '_img_' + sort + '').attr('src', src);
					}
				}
				
			});
			return false;  
		},
		updateImage : function(adId,acId,pos,sort,type,realatedUrl){
			var tag = '#' + pos +'_img_'+sort;
			if($(tag).attr("src") == uiBase + "img/default_goods_image_240.gif" || $(tag).attr("src") == uiBase + "/img/default_goods_image_240.gif"){
				$("body").loadingInfo("warn", "请先上传图片");
				return;
			}
			if ($(tag).attr("src") == uiBase + "img/loading.gif" || $(tag).attr("src") == uiBase + "/img/loading.gif") {
				$("body").loadingInfo("warn", "请等待图片上传");
				return;
			}
			if (!acId && !realatedUrl) {
				$("body").loadingInfo("warn", "请通过浏览选择商品或者输入关联URL");
				return;
			}
			
			if (acId != "" && acId != null && realatedUrl != "" && realatedUrl != null) {
				$("body").loadingInfo("warn", "关联商品或者关联url只能选择一种");
				return;
			}
			
			var addAdForm = {
					adId : adId,
					type : type,
					status : 1,
					productId : acId,
					url : $(tag).attr("src"),
					sort : sort,
					skipUrl : realatedUrl
			};
			
			$.ajax({ 
        		type         : 'post',
				url          : urlPrefix+"appHotAd/update",
				dataType     : 'json',
				contentType: "application/json;charset=utf-8",
				data: JSON.stringify(addAdForm)
				
			 }).done(function(result) {
				if(result.code == "ACK") {
					$(window).loadingInfo("success", result.message);
				}
			 });
		},
		
		deleteActive : function(adId, pos,sort){
			$.ajax({ 
        		type         : 'post',
				url          : urlPrefix+"appHotAd/delete",
				dataType     : 'json',
				data         : {
					adId : adId,
					sort : sort
				}
			 }).done(function(result) {
				if(result.code == "ACK") {
					$(window).loadingInfo("success", result.message);
					$("#" + pos + "_img_" + sort).attr("src", uiBase + "/img/default_goods_image_240.gif");
					$("#" + pos + "_name_" + sort).val("");
					$("#" + pos + "_save_" + sort).data("acid", "");
				}
			 });
		},
		upOrDownCarousel : function(adId, option){
			var sort = -1;
			if (option == "down") {
				sort = 1;
			}
			$.ajax({
	    		type         : 'POST',
				url          : urlPrefix + "appHotAd/upOrDwon",
				dataType     : 'json',
				data 	     : {
					"sort":sort,
					"adId": adId
				},
				success : function(msg){
					if (msg.code == "ACK") {
						window.location.reload(true);
					} else {
						$("body").loadingInfo("warn", msg.message);
					}
				}
	    	});
		},
		addCopyGoods : function(pos,sort) {
	        var that = this;
	        that.dialog =  BootstrapDialog.show({
	            title: '选择商品',
	            //type : BootstrapDialog.TYPE_DEFAULT,
	            message: $(this.scanGoodsHtml),
	            draggable: true,
	            buttons: [{
	                label: '确认',
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	                	var activities = $('#copyGoodsList').bootstrapTable('getSelections')
	                	that.getActInfo(pos,sort,activities[0]);
	                    dialog.close();
	                }
	            }, {
	                label: '取消',
	                action: function(dialog) {
	                    dialog.close();
	                }
	            }],
	            onshown : function() {
	            	that.initDialogFCategory();
	                that.initCopyTable();
	                that.initDialogOrg();
	            }
	       });
	       that.dialog.getModalDialog().css('width', '700px');
	    },
	    getActInfo : function(pos,sort,$activity) {
	    	var temp = $("input[name='btSelectItem']:checked");
	    	var actId = temp.val();
	    	var actName = temp.parent().parent().children("td:last").text();
	    	if (actId != "") {
	    		var shortActName = actName;
	    		if(actName.length > 10) {
	    			shortActName = actName.substring(0,10) + "...";
	    		}
	    		$("#" + pos + "_name_" + sort).val(shortActName);
	    		$("#" + pos + "_name_" + sort).attr("title", actName);
	    		$("#" + pos + "_name_" + sort).attr("actId",actId);
	    		$("#" + pos + "_save_" + sort).data("acid",actId);
	    	}
	    },
	    initDialogFCategory : function() {
	    	$.ajax({
	    		type         : 'get',
				url          : urlPrefix + "activity/getParentCate",
				dataType     : 'json',
				contentType  : 'application/json',
				success : function(msg){
					var data = msg.data;
					var _opt = "<option value=''>请选择一级品类</option>";
					if(data && data.length != 0){
						for(var i=0; i<data.length; i++){
							_opt += "<option value='"+data[i].cateId+"'>"+data[i].cateName+"</option>";
						}
					}
					$("#cFirstCategory").html(_opt);
					$("#cSecondCategory").html("<option value=''>请选择二级品类</option>");
				}
	    	});
	    	$("#cFirstCategory").trigger('change');
	    },
	    initDialogOrg : function() {
	    	$.ajax({
	    		type         : 'get',
				url          : urlPrefix + "supplier/getSuppliers",
				dataType     : 'json',
				contentType  : 'application/json',
				success : function(msg){
					var data = msg.data;
					var _opt = "<option value=''>服务商</option>";
					if(data && data.length != 0){
						for(var i=0; i<data.length; i++){
							_opt += "<option value='"+data[i].orgId+"'>"+data[i].orgName+"</option>";
						}
					}
					$("#cOrgId").html(_opt);
				}
	    	});
	    	$("#cOrgId").trigger('change');
	    },
	    getSecondCate : function(firstCate) {
	    	$.ajax({
	    		type         : 'get',
				url          : urlPrefix + "activity/getSubCategoryByParent/" + firstCate,
				dataType     : 'json',
				contentType  : 'application/json',
				success : function(msg){
					var data = msg.data;
					var _opt = "<option value=''>二级商品品类</option>";
					if(data && data.length != 0){
						for(var i=0; i<data.length; i++){
							_opt += "<option value='"+data[i].cateId+"'>"+data[i].cateName+"</option>";
						}
						$("#cSecondCategory").attr("disabled", false);
						$("#cSecondCategory").html(_opt);
					} else {
						_opt += "<option value=''>请选择二级品类</option>"
						$("#cSecondCategory").html(_opt);
						$("#cSecondCategory").attr("disabled", true);
					}
				}
	    	});
	    },
	    initCopyTable : function() {
	        var that = this;
	        this.relatedBootTable = $.GLOBAL.utils.loadBootTable({
	            table : $('#copyGoodsList'),
	            //removeBtn : $('#removeRecord'),
	            refreshBtn : $('#rRefreshRecord'),
	            idField : "id",
	            pagination : true,
	            singleSelect : true,
	            pageSize : 10,
	            url: 'appHotAd/activityList',
	            sidePagination:'server',
	            queryAddParams: function() { 
	            	var fCateId = $("#cFirstCategory").val(),
	            	sCateId = $("#cSecondCategory").val(),categoryId;
	            	categoryId = sCateId?sCateId:fCateId;
	                return {
	                    categoryId : categoryId,
	                    orgId : $("#cOrgId").val(),
	                    productName : $.trim($('#cActivityName').val())
	                }
	            },
	            columns: [
	                {
	                	field : 'productId',
	                    align: 'center',
	                    formatter:function(value,row,index){  
	                        return "<input data-index='" + index + "' name='btSelectItem' type='radio' value=" + value + ">"
	                    }
	                } ,
	                {
	                    field: 'psCate'  
	                } ,
	                {
	                    field: 'orgName'  
	                },
	                {
	                    field: 'productName'
	                } 
	            ]
	            
	        });
	        
	    }
	}
	
	var secondAdvertise = {
			initEvents:function(){
				var _this = this;
				var table1 = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
				    sidePagination:'server',
				    pagination : true,
				    url : "appHotAd/list",
				    queryParamsType: "limit",
				    pageList:[10,25,50],
				    queryAddParams: function() {
				    	
				    },
				    columns: [{
						title: '序号',
						field : 'index',
						align:'center',
					    width:'5%',
					    formatter:function(value,row,index){  
					    	var sort = index + 1;
					    	return "<div class='text-center'><label>" + sort + "：</label></div>";
			            }
				    },
			        {
				    	title: '图片',
						field : 'url',
						width:'35%',
						align: 'center',
						formatter:function(value,row,index){
							var url = value.substring(0, value.lastIndexOf(";"));
							var sort = value.substring(value.lastIndexOf(";") + 1, value.length);
							if (value != "" && value != null) {
								return "<div class='adspic-list'><ul><li class='adspic-upload ' " +
										"id=" + sort + "><div class='upload-thumb'>" +
										"<img src='" + url + "'  id='sale_img_" + sort + "' class='adImg'>" +
										"</div></li></ul></div>"
							} else {
								return "<div class='adspic-list col-sm-3'><ul><li class='adspic-upload ' " +
									   "id=" + sort + "><div class='upload-thumb'>" +
									   "<img src='${base}/dist/img/default_goods_image_240.gif'  id='sale_img_" + sort + "'>" +
									   "</div></li></ul></div>"
							}
						}
			        },
			        {
				    	title: '商品',
						field : 'info',
						width:'35%',
						align: 'center',
						formatter:function(value,row,index){
							var info = value.split("#&amp;#");
							var proName = info[0];
							var title = info[1];
							if (title == "null") {
								title = "";
							}
							var adId = info[2];
							return "<div><label>关联商品：</label><label>" + proName + "</label></div>" +
									"<div><label>热门说明：</label><input type='text' readonly='readonly' id='description' maxlength='6' class='form-control' value='" + title + "'/>" +
									"<button data-id='" + adId + "' class='btn btn-primary editor' style='margin-left:10px;'>修改</button></div>" +
									"<div><label>关联URL：</label><input type='text' readonly='readonly' id='url' class='form-control' value='" + row.skipUrl + "'/>" +
									"<button data-id='" + adId + "' class='btn btn-primary url-editor' style='margin-left:10px;'>修改</button></div>";
						}
			        },
					{
					    title: '操作',
						field : 'operation',
						width:'25%',
						formatter:function(value,row,index){
							var oprate = value.split(";");
							var count = oprate[0];
							var id = oprate[1];
							var sort = oprate[2];
							var result = "<div class='btn-group'><button class='btn btn-primary delete' type='button' data-id='" + id + "'>删除 </i></button>"
			            	result += "</div>";
			            	return result;
						}
			        }]
				});
				
				$("#dataList").on('click', '.delete', function(){
					var adId = $(this).data("id");
					deleteAd(adId);
		        });
				
				$("#clear-all").click(function(){
					$(this).prev().val("");
				});
				
				$("#dataList").on('click', '.editor', function(){
					if ($(this).parent().find("input").attr("readonly") == "readonly") {
						$(this).parent().find("input").attr("readonly", false);
						$(this).html("保存");
					} else {
						var adId = $(this).data("id");
						var desc = $(this).parent().find("input").val();
						if (desc == null || desc == "") {
							$(window).loadingInfo("warn", "请输入热门说明");
						} else {
							$(this).parent().find("input").attr("readonly", "readonly");
							editorDescription(adId, desc);
							$(this).html("修改");
						}
					}
				});
				
				$("#dataList").on('click', '.url-editor', function(){
					var _this = $(this).parent().find("input");
					if (_this.attr("readonly") == "readonly") {
						_this.attr("readonly", false);
						$(this).html("保存");
					} else {
						var adId = $(this).data("id");
						var url = _this.val();
						if (url == null || url == "") {
							$(window).loadingInfo("warn", "请输入关联url");
						} else {
							_this.attr("readonly", "readonly");
							editorUrl(adId, url, _this);
							$(this).html("修改");
						}
					}
				});
				
				$(".sale_upload").on('click', '.save', function(){
					var sort = $(this).data("sort");
					var adId = $(this).data("id");
					var acId = $(this).data("acid");
					var url = $("#url").val();
					var description = $("#description").val();
					var name = $("#clear-all").prev().val();
					updateImage1(adId,acId,description,"sale",sort,7,url,name);
		        });
				
				$(".upload-thumb").on('click', '.adImg', function(){
					var src = $(this).attr("src");
					window.open(src);
				});
				
				$("#dataList").on('click', '.adImg', function(){
					var src = $(this).attr("src");
					window.open(src);
				});
				
				var editorDescription = function(adId, description) {
					var editorForm = {
							adId 					: adId,
							description     		: description
					};
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "appHotAd/editorDesc",
						dataType     : 'json',
						contentType: "application/json;charset=utf-8",
						data: JSON.stringify(editorForm),
						success : function(msg){
							if (msg.code == "ACK") {
								$(window).loadingInfo("success", msg.message);
							} else {
								$(window).loadingInfo("warn", msg.message);
							}
						}
			    	});
				};
				
				var editorUrl = function(adId, url, _this) {
					var editorForm = {
							adId 					: adId,
							url     		: url
					};
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "appHotAd/editorDesc",
						dataType     : 'json',
						contentType: "application/json;charset=utf-8",
						data: JSON.stringify(editorForm),
						success : function(msg){
							if (msg.code == "ACK") {
								$(window).loadingInfo("success", msg.message);
							} else {
								$(window).loadingInfo("warn", msg.message);
								_this.val("");
							}
						}
			    	});
				};
				
				var deleteAd = function(adId){
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "appHotAd/delete",
						dataType     : 'json',
						data 	     : {
							"adId": adId
						},
						success : function(msg){
							if (msg.code == "ACK") {
								$(window).loadingInfo("success", msg.message);
								freshTable();
								update(msg.data + 2, "del");
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
				
				var updateImage1 = function(adId,acId,description,pos,sort,type,url,name){
					$("#sale_save_" + sort).attr("disabled", true);
					var tag = '#' + pos +'_img_'+sort;
					if($(tag).attr("src") == uiBase + "img/default_goods_image_240.gif" || $(tag).attr("src") == uiBase + "/img/default_goods_image_240.gif"){
						$("body").loadingInfo("warn", "请先上传图片");
						$("#sale_save_" + sort).attr("disabled", false);
						return;
					}
					if ($(tag).attr("src") == uiBase + "img/loading.gif" || $(tag).attr("src") == uiBase + "/img/loading.gif") {
						$("body").loadingInfo("warn", "请等待图片上传");
						$("#sale_save_" + sort).attr("disabled", false);
						return;
					}
					if ((name == "" || name == null) && (url == "" || url == null)) {
						$("body").loadingInfo("warn", "请关联商品或者填写关联url");
						$("#sale_save_" + sort).attr("disabled", false);
						return;
					}
					if (name != "" && name != null && url != "" && url != null) {
						$("body").loadingInfo("warn", "关联商品或者关联url只能选择一种");
						$("#sale_save_" + sort).attr("disabled", false);
						return;
					}
					if (description == null || description == "") {
						$("body").loadingInfo("warn", "请输入热门说明");
						$("#sale_save_" + sort).attr("disabled", false);
						return;
					}
					
					var addAdForm = {
							adId : adId,
							type : type,
							status : 1,
							productId : acId,
							url : $(tag).attr("src"),
							sort : sort,
							description : description,
							skipUrl : url
					};
					
					$.ajax({ 
		        		type         : 'post',
						url          : urlPrefix+"appHotAd/update",
						dataType     : 'json',
						contentType: "application/json;charset=utf-8",
						data: JSON.stringify(addAdForm)
					 }).done(function(result) {
						if(result.code == "ACK") {
							var nextSort = sort + 1;
							$(window).loadingInfo("success", result.message);
							table1.refreshThis();
							update(sort, "add");
							$("#sale_save_" + nextSort).attr("disabled", false);
							$("#url").val("");
						} else {
							$(window).loadingInfo("warn", result.message);
							$("#sale_save_" + sort).attr("disabled", false);
						}
					 });
				};
				
				var update = function(sort, option){
					if (option == "del") {
						var newSort = sort - 1;
					} else {
						var newSort = sort + 1;
					}
					var nameInput = document.getElementById("sale_name_" + sort);
					var saveInput = document.getElementById("sale_save_" + sort);
					var imgInput = document.getElementById("sale_img_" + sort);
					var fileInput = document.getElementById("sale_" + sort);
					var lookInput = document.getElementById("sale_look_" + sort);
					nameInput.id = "sale_name_" + newSort;
					saveInput.id = "sale_save_" + newSort;
					imgInput.id = "sale_img_" + newSort;
					fileInput.id = "sale_" + newSort;
					lookInput.id = "sale_look_" + newSort;
					
					$("#sale_" + newSort).data("sort", newSort);
					$("#sale_name_" + newSort).val("");
					$("#sale_name_" + newSort).data("acid", "");
					$("#sale_look_" + newSort).data("sort", newSort);
					$("#sale_save_" + newSort).data("acid", "");
					$("#sale_save_" + newSort).data("sort", newSort);
					$("#sale_img_" + newSort).attr("src", uiBase + "/img/default_goods_image_240.gif")
					$("#description").val("");
				};
			},
			
			init:function(){
				var _self = this;
				_self.initEvents();
			}
	}.init();
	
	var carousel = {
		init : function(){
			this.pos = 'carousel';
			this.bindEvent();
		},
		bindEvent : function(){
			var _this = this;
			$(".carousel_upload").on('change', '.upload-btn input[type="file"]', function(){
	            var sort = $(this).data("sort");
	            advertise.ajaxFileUpload(_this.pos,sort);
	        });
			
			$(".carousel_upload").on('click', '.save', function(){
				var sort = $(this).data("sort");
				var adId = $(this).data("id");
				var acId = $(this).data("acid");
				var realatedUrl = $(this).parent().parent().find('input[name="relatedUrl"]').val();
				advertise.updateImage(adId,acId,_this.pos,sort,6,realatedUrl);
	        });
			
			$(".carousel_upload").on('click', '.deleteC', function(){
				var adId = $(this).data("id");
				var sort = $(this).data("sort");
				advertise.deleteActive(adId, _this.pos,sort);
	        });
			
			$(".carousel_upload").on('click', '.upC', function(){
				var adId = $(this).data("id");
				advertise.upOrDownCarousel(adId,"up");
	        });
			
			$(".carousel_upload").on('click', '.downC', function(){
				var adId = $(this).data("id");
				advertise.upOrDownCarousel(adId, "down");
	        });
			
			
			$('.carousel_upload').on('click', '.look', function(){
				var sort = $(this).data("sort");
				advertise.addCopyGoods(_this.pos,sort);
			});
		}
			
	}.init();
	
	var sale = {
		init : function(){
			this.pos = 'sale';
			this.bindEvent();
		},
		bindEvent : function(){
			var _this = this;
			$(".sale_upload").on('change', '.upload-btn input[type="file"]', function(){
	            var sort = $(this).data("sort");
	            advertise.ajaxFileUpload(_this.pos,sort);
	        });
			
			$('.sale_upload').on('click', '.look', function(){
				var sort = $(this).data("sort");
				advertise.addCopyGoods(_this.pos,sort);
			});
		}
			
	}.init();
	
	$("body").on('change', '#cFirstCategory', function(){
		var categoryId = $('#cFirstCategory option:selected').val();
		if (categoryId != null && categoryId != "") {
			advertise.getSecondCate(categoryId);
		} else {
			var _opt = "<option value=''>请选择二级品类</option>"
			$("#cSecondCategory").html(_opt);
			$("#cSecondCategory").attr("disabled", true);
		}
	});
});