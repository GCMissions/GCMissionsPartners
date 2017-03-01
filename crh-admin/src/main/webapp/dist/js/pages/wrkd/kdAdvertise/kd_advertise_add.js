$(function(){
	var kdAdvertiseAdd = {
			initEvents:function(){
				var otherHtml = "<div class='col-sm-4'> "
					   + "<div class='linediv'><input id='global_skipUrl' class='form-control rinput' type='text' style='text-align: center;' placeholder='跳转链接'/></div>"
					   + "<div class='linediv'><span>标题：</span><input id='global_name' class='form-control rinput' type='text' style='text-align: center;' placeholder='广告标题'/></div>"
					   + "<div class='linediv'><span>图片：</span>"
					   + "<div class='adspic-list col-sm-3 rinput'>"
					   + "<ul>"
					   + "<li class='adspic-upload '>"
					   + "<div class='upload-thumb'>"
					   + "<img src='" + uiBase + "/img/default_goods_image_240.gif'"
					   + "id='global_imageUrl'>"
					   + "</div>"
					   + "<div class='upload-btn'>"
					   + "<a href='javascript:void(0);'> <span><input "
					   + "type='file' hidefocus='true' size='1' class='input-file'"
					   + "name='file' id='global_imageUpload'"
					   + "accept='.jpg,.png,.gif'></span>"
					   + "<p>"
					   + "<i class='fa fa-fw fa-upload'></i>上传"
					   + "</p>"
					   + "</a>"
					   + "</div>"
					   + "</li>"
					   + "</ul>"
					   + "<p style='font-size:12px;'>建议上传格式为jpg/jpeg/png，尺寸为310×280</p>"
					   + "</div>"
					   + "</div>"
					   + "</div>"
					   + "<div class='col-sm-1'></div>"
					   + "<div class='col-sm-1'>"	
					   + "<button type='button' class='btn  btn-primary global_save' id='save'><i class='fa'></i>保存</button> "
					   + "</div>"
					   + "</div>";
				var featureHtml = "<div class='col-sm-2'><input  id='actName' name='actName' class='form-control' type='text' placeholder='相关关联' style='width:100%;text-align:center;' readonly='readonly' /></div> "
					   + "<div class='col-sm-1'> "	
                       + "<button type='button' class='btn  btn-default global_look' id='look'><i class='fa'></i>浏览</button> " 
                       + "</div><div class='col-sm-1'><button type='button' class='btn  btn-primary global_save' id='save'><i class='fa'></i>保存</button></div>";
				
				$(".listmargin").on("click", ".del_btn", function(){
					var advertiseId = $(this).data("id");
					this.dialog =  BootstrapDialog.show({
			            title: "删除广告位",
			            type : BootstrapDialog.TYPE_WARNING,
			            message : "确认删除？",
			            draggable: true,
			            size : BootstrapDialog.SIZE_SMALL,
			            buttons: [{
							 label: "确认",
							 cssClass: 'btn-primary ',
							 action: function(dialog) {
								 dialog.close();
								 deleteAdvertise(advertiseId,"1");
							 }
			            }, {
							 label: '取消',
							 action: function(dialog) {
								 dialog.close();
							 }
						 }]
					});
				});
				
				$(".advertise_two").on("click", ".two_del", function(){
					var advertiseId = $(this).data("id");
					this.dialog =  BootstrapDialog.show({
			            title: "删除广告位",
			            type : BootstrapDialog.TYPE_WARNING,
			            message : "确认删除？",
			            draggable: true,
			            size : BootstrapDialog.SIZE_SMALL,
			            buttons: [{
							 label: "确认",
							 cssClass: 'btn-primary ',
							 action: function(dialog) {
								 dialog.close();
								 deleteAdvertise(advertiseId,"2");
							 }
			            }, {
							 label: '取消',
							 action: function(dialog) {
								 dialog.close();
							 }
						 }]
					});
				});
				
				$(".listmargin").on("click", ".look_btn", function(){
					var adId = $(this).data("id");
					var skipType = $(this).parent().parent().find("select").find("option:selected").text();
					if (skipType == "关联专辑") {
						showFeatureDialog(adId);
					}
					if (skipType == "关联专享") {
						showActDialog(adId);
					}
				});
				
				$(".listmargin").on("change", ".skipType_one", function(){
					var advertiseId = $(this).data("id");
					$("#name_" + advertiseId).val("");
					$("#save_" + advertiseId).attr("actid", "");
				});
				
				$(".advertise_two").on("click", ".two_save", function(){
					var adId = $(this).data("id");
					var type = "2";
					var operType = "1";
					var skipType = "2";
					var name = $.trim($("#two_name_" + adId).val());
					var skipUrl = $.trim($("#two_skipUrl_" + adId).val());
					var imageUrl = $("#two_img_" + adId).attr("src");
					if (name == "") {
						$(window).loadingInfo("warn", "请填写广告标题！");
						return;
					}
					if (skipUrl == "") {
						$(window).loadingInfo("warn", "请填写广告跳转链接！");
						return;
					}
					addOrUpdate(type,null,skipType,operType,null,name,skipUrl,imageUrl,adId);
				});
				
				$("#global_two_save").click(function(){
					var type = "2";
					var skipType = "2";
					var operType = "0";
					var name = $.trim($("#global_two_name").val());
					var skipUrl = $.trim($("#global_two_skipUrl").val());
					var imageUrl = $("#global_two_imageUrl").attr("src");
					if (name == "") {
						$(window).loadingInfo("warn", "请填写广告标题！");
						return;
					}
					if (skipUrl == "") {
						$(window).loadingInfo("warn", "请填写广告跳转链接！");
						return;
					}
					addOrUpdate(type,null,skipType,operType,null,name,skipUrl,imageUrl,null);
				});
				
				$(".listmargin").on("click", ".save_btn", function(){
					var adId = $(this).data("id");
					var skip = $(this).parent().parent().find("select").find("option:selected").text();
					var skipType;
					var type = "1";
					var operType = "1";
					if (skip == "关联专辑") {
						skipType = "0";
						var actId = $(this).attr("actid");
						if (actId == null || actId == "") {
							$(window).loadingInfo("warn", "请先选择专辑！");
							return;
						}
						addOrUpdate(type,actId,skipType,operType,null,null,null,null,adId);
					} else if (skip == "关联专享") {
						skipType = "1";
						var actId = $(this).attr("actid");
						var actType = $(this).attr("actype");
						if (actId == null || actId == "") {
							$(window).loadingInfo("warn", "请先选择专享！");
							return;
						}
						addOrUpdate(type,actId,skipType,operType,actType,null,null,null,adId);
					} else {
						skipType = "2";
						var name = $.trim($("#name_" + adId).val());
						var skipUrl = $.trim($("#skipUrl_" + adId).val());
						var imageUrl = $("#imageUrl_" + adId).attr("src");
						if (name == "") {
							$(window).loadingInfo("warn", "请填写广告标题！");
							return;
						}
						if (skipUrl == "") {
							$(window).loadingInfo("warn", "请填写广告跳转链接！");
							return;
						}
						addOrUpdate(type,actId,skipType,operType,actType,name,skipUrl,imageUrl,adId);
					}
				});
				
				$("body").on("click",".global_look",function(){
					var skipType = $("#skipType").val();
					if (skipType == "") {
						$(window).loadingInfo("warn", "请选择跳转模式！");
					}
					if (skipType == "0") {
						showFeatureDialog(-1);
					}
					if (skipType == "1") {
						showActDialog(-1);
					}
				});
				
				$("body").on("click",".global_save",function(){
					var skipType = $("#skipType").val();
					if (skipType == "") {
						$(window).loadingInfo("warn", "请选择跳转模式！");
					}
					var type = "1";
					var operType = "0";
					if (skipType == "0") {
						var actId = $(this).attr("actid");
						if (actId == null || actId == "") {
							$(window).loadingInfo("warn", "请先选择专辑！");
							return;
						}
						addOrUpdate(type,actId,skipType,operType,null,null,null,null,null);
					} else if (skipType == "1") {
						var actId = $(this).attr("actid");
						var actType = $(this).attr("actype");
						if (actId == null || actId == "") {
							$(window).loadingInfo("warn", "请先选择专享！");
							return;
						}
						addOrUpdate(type,actId,skipType,operType,actType,null,null,null,null);
					} else {
						var name = $.trim($("#global_name").val());
						var skipUrl = $.trim($("#global_skipUrl").val());
						var imageUrl = $("#global_imageUrl").attr("src");
						if (name == "") {
							$(window).loadingInfo("warn", "请填写广告标题！");
							return;
						}
						if (skipUrl == "") {
							$(window).loadingInfo("warn", "请填写广告跳转链接！");
							return;
						}
						addOrUpdate(type,actId,skipType,operType,actType,name,skipUrl,imageUrl,null);
					}
				});
				
				addOrUpdate = function(type,actId,skipType,operType,actType,name,skipUrl,imageUrl,adId){
					if(imageUrl == uiBase + "img/default_goods_image_240.gif" || imageUrl == uiBase + "/img/default_goods_image_240.gif"){
						$("body").loadingInfo("warn", "请先上传图片");
						return;
					}
					if (imageUrl == uiBase + "img/loading.gif" || imageUrl == uiBase + "/img/loading.gif") {
						$("body").loadingInfo("warn", "请等待图片上传");
						return;
					}
					$.ajax({ 
		        		type         : 'post',
						url          : urlPrefix+"kdAdvertise/addOrUpdate",
						dataType     : 'json',
						contentType: "application/json;charset=utf-8",
						data: JSON.stringify({
							skipType : skipType,
							type : type,
							actId : actId,
							operType : operType,
							actType : actType,
							name : name,
							skipUrl : skipUrl,
							imageUrl : imageUrl,
							advertiseId : adId
						}),
						success : function(msg){
							if (msg.code == "ACK") {
								if(type == "1") {
									if (operType == "0") {
										$('body').loadingInfo({
						                    type : "ok",
						                    text : msg.message,
						                    callBack : function() {
						                    	window.location.href=window.location.href;
						                    }
						                });
									} else {
										$(window).loadingInfo("success", msg.message);
									}
								} else {
									$(window).loadingInfo("success", msg.message);
									if (operType == "0") {
										table.refreshThis();
									}
								}
							} else {
								$(window).loadingInfo("warn", msg.message);
							}
						}
					});
				};
				
				$("#skipType").change(function(){
					var skipType = $("#skipType").val();
					if (skipType == "2") {
						$("#info").html(otherHtml);
					} else {
						$("#info").html(featureHtml);
					}
				});
				
				var deleteAdvertise = function(advertiseId,type){
					$.ajax({ 
		        		type         : 'post',
						url          : urlPrefix+"kdAdvertise/delete",
						dataType     : 'json',
						contentType: "application/json;charset=utf-8",
						data: JSON.stringify({
							advertiseId : advertiseId
						}),
						success : function(msg){
							if (msg.code == "ACK") {
								if(type == "1") {
									$('body').loadingInfo({
					                    type : "ok",
					                    text : msg.message,
					                    callBack : function() {
					                    	window.location.href=window.location.href;
					                    }
					                });
								} else {
									$(window).loadingInfo("success", msg.message);
									table.refreshThis();
								}
							} else {
								$(window).loadingInfo("warn", msg.message);
							}
						}
					});
				};
				
				$(".rinput").on('change', '.upload-btn input[type="file"]', function(){
					var advertiseId = $(this).data("id");
		            ajaxFileUpload(advertiseId, "1");
		        });
				
				$("#info").on('change', '.upload-btn input[type="file"]', function(){
					ajaxFileUpload(null, "1");
				});
				
				$("#picture").on('change', '.upload-btn input[type="file"]', function(){
					ajaxFileUpload(null, "2");
				});
				
				$(".advertise_two").on('change', '.upload-btn input[type="file"]', function(){
					var advertiseId = $(this).data("id");
					ajaxFileUpload(advertiseId, "2");
				});
				
				var ajaxFileUpload = function (advertiseId, type) {
					if (type == "1") {
						if (advertiseId != null) {
							$('#imageUrl_' + advertiseId).attr('src', uiBase + "/img/loading.gif");
							$.ajaxFileUpload({
								url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.kdAdvertise}),
								secureuri : false,
								fileElementId : "imageUpload_"+advertiseId,
								dataType : 'json',
								global : false,
								data : {},
								success : function (data, status) {
									if (data.code == "ACK") {
										$('#imageUrl_' + advertiseId).attr('src', data.data.url);   
									}else {
										$(window).loadingInfo("error", data.messge);
									}
								}
								
							});
							return false;  
						} else {
							$('#global_imageUrl').attr('src', uiBase + "/img/loading.gif");
							$.ajaxFileUpload({
								url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.kdAdvertise}),
								secureuri : false,
								fileElementId : "global_imageUpload",
								dataType : 'json',
								global : false,
								data : {},
								success : function (data, status) {
									if (data.code == "ACK") {
										$('#global_imageUrl').attr('src', data.data.url);   
									}else {
										$(window).loadingInfo("error", data.messge);
									}
								}
								
							});
							return false;  
						}
					}
					if(type == "2") {
						if (advertiseId == null) {
							$('#global_two_imageUrl').attr('src', uiBase + "/img/loading.gif");
							$.ajaxFileUpload({
								url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.kdAdvertise}),
								secureuri : false,
								fileElementId : "global_two_imageUpload",
								dataType : 'json',
								global : false,
								data : {},
								success : function (data, status) {
									if (data.code == "ACK") {
										$('#global_two_imageUrl').attr('src', data.data.url);   
									}else {
										$(window).loadingInfo("error", data.messge);
									}
								}
								
							});
							return false;  
						} else {
							$('#two_img_' + advertiseId).attr('src', uiBase + "/img/loading.gif");
							$.ajaxFileUpload({
								url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.kdAdvertise}),
								secureuri : false,
								fileElementId : "two_imageUpload_" + advertiseId,
								dataType : 'json',
								global : false,
								data : {},
								success : function (data, status) {
									if (data.code == "ACK") {
										$('#two_img_' + advertiseId).attr('src', data.data.url);   
									}else {
										$(window).loadingInfo("error", data.messge);
									}
								}
								
							});
							return false;  
						}
					} 
				};
				
				showFeatureDialog = function(adId){
					var _this = this;
					_this.featureDialog = BootstrapDialog.show({
				        title: '选择专辑',		    
				        size :  BootstrapDialog.SIZE_WIDE,
				        message: $(template('featureTpl',{})),
				        draggable: false,
				        onshown: function(dialogRef){
				        	initFeatureList(adId);
			            }
				    });
				};
				
				initFeatureList = function(adId){
					return $.GLOBAL.utils.loadBootTable({
						table : $('#featureList'),
					    sidePagination:'server',
					    refreshBtn : $('#search'),
					    pagination : true,
					    url : "kdAdvertise/featureList",
					    queryParamsType: "limit",
					    pageSize : 5,
					    pageList:[5,10,20],
					    queryAddParams: function() {
					    	var featureName = $.trim($("#featureName").val());
					    	var tagType = $("#tagType").find("option:selected").attr("id");
					    	var result= {
					    			featureName:featureName,
					    			tagId: tagType
							}
					    	return result;
					    },
					    columns: [{
							title: '序号',
						    align: 'center',
						    field: 'index',
						    width:50
						} , {
				        	title: '专辑名称',
				            field: 'featureName',
				            width: 140
				        } , {
				        	title: '所属标签',
				            field: 'tagName'
				        } , {
				        	title: '操作',
				        	field: 'featureId',
				            align: 'center',
				            formatter:function(value,row,index){  
				                return ' <a href="javascript:void(0);" data-id="'+ value +'" data-adId="' + adId + '" class="chooseFeature">选择</a>';
				            } 
				        }]
					});
				};
				
				showActDialog = function(adId){
					var _this = this;
					_this.actDialog = BootstrapDialog.show({
				        title: '选择专享',		    
				        size :  BootstrapDialog.SIZE_WIDE,
				        message: $(template('actTpl',{})),
				        draggable: false,
				        onshown: function(dialogRef){
				        	initActList(adId);
			            }
				    });
				};
				
				initActList = function(adId){
					return $.GLOBAL.utils.loadBootTable({
						table : $('#actList'),
					    sidePagination:'server',
					    refreshBtn : $('#searchAct'),
					    pagination : true,
					    url : "kdAdvertise/actList",
					    queryParamsType: "limit",
					    pageSize : 5,
					    pageList:[5,10,20],
					    queryAddParams: function() {
					    	var actName = $.trim($("#actNames").val());
					    	var actType = $("#actTypes").find("option:selected").attr("id");
					    	var result= {
					    			actName:actName,
					    			actType: actType
							}
					    	return result;
					    },
					    columns: [{
							title: '序号',
						    align: 'center',
						    field: 'index',
						    width:50
						} , {
				        	title: '活动名称',
				            field: 'actName',
				            width: 140
				        } , {
				        	title: '所属标签',
				            field: 'actType'
				        } , {
				        	title: '操作',
				        	field: 'actId',
				            align: 'center',
				            formatter:function(value,row,index){  
				                return ' <a href="javascript:void(0);" data-id="'+ value +'" data-adId="' + adId + '" class="chooseAct">选择</a>';
				            } 
				        }]
					});
				};
				
				$("body").on("click", ".chooseFeature", function(){
					var featureName = $(this).parent().parent().find("td").eq(1).text();
					var adId = $(this).data("adid");
					var featureId = $(this).data("id");
					if(adId == -1) {
						$("#save").attr("actId", featureId);
						$("#actName").val(featureName);
					} else {
						$("#name_" + adId).val(featureName);
						$("#save_" + adId).attr("actId", featureId);
					}
					featureDialog.close();
				});
				
				$("body").on("click", ".chooseAct", function(){
					var actName = $(this).parent().parent().find("td").eq(1).text();
					var adId = $(this).data("adid");
					var actId = $(this).data("id");
					var actType = $(this).parent().parent().find("td").eq(2).text();
					var type;
					if (actType == "团购") {
						type = 0;
					} else if(actType == "24小时"){
						type = 1;
					} else {
						type = 2;
					}
					if(adId == -1) {
						$("#actName").val(actName);
						$("#save").attr("actid", actId);
						$("#save").attr("actype", type);
					} else {
						$("#name_" + adId).val(actName);
						$("#save_" + adId).attr("actid", actId);
						$("#save_" + adId).attr("actype", type);
					}
					actDialog.close();
				});
				
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
				    sidePagination:'server',
				    pagination : true,
				    url : "kdAdvertise/adListTwo",
				    queryParamsType: "limit",
				    pageList:[5,10,15],
				    queryAddParams: function() {
				    	
				    },
				    columns: [{
						title: '序号',
						field : 'index',
						align:'center',
					    width:'5%',
					    formatter:function(value,row,index){
					    	return "<p style='margin-top:50%'>" + value + "</p>";
					    }
				    },
			        {
				    	title: '图片',
						field : 'imageUrl',
						width:'30%',
						align: 'center',
						formatter:function(value,row,index){
							resule = "<div class='adspic-list col-sm-3 rinput'> "
									 + " <ul> "
									 + " <li class='adspic-upload '> "
									 + " <div class='upload-thumb'> "
									 + " <img src='" + value + "'"
									 + " id='two_img_" + row.advertiseId + "'> "
									 + " </div> "
									 + " <div class='upload-btn'> "
									 + " <a href='javascript:void(0);'> <span><input "
									 + " type='file' hidefocus='true' size='1' class='input-file' "
									 + " data-id='" + row.advertiseId + "' name='file' id='two_imageUpload_" + row.advertiseId + "' "
									 + " accept='.jpg,.png,.gif'></span> "
									 + " <p> "
									 + " <i class='fa fa-fw fa-upload'></i>上传 "
									 + " </p> "
									 + " </a> "
									 + " </div> "
									 + " </li> "
									 + " </ul> "
									 + " </div> "
							return resule;
						}
			        },
			        {
				    	title: '标题',
						field : 'name',
						width:'20%',
						align: 'center',
						formatter:function(value,row,index){
							return "<input style='margin-top:13%' id='two_name_" + row.advertiseId + "' class='form-control two_name' value='" + value + "' />";
						}
			        },
			        {
				    	title: '链接地址',
						field : 'skipUrl',
						width:'30%',
						align: 'center',
						formatter:function(value,row,index){
							return "<input style='margin-top:8%' id='two_skipUrl_" + row.advertiseId + "' class='form-control two_skipUrl' value='" + value + "' />";
						}
			        },
					{
					    title: '操作',
						field : 'advertiseId',
						width:'15%',
						align:'center',
						formatter:function(value,row,index){
							return " <div style='margin-top:15%'><button type='button' class='btn  btn-primary two_save' data-id='" + value + "' style='margin-right:15%'><i class='fa'></i>保存</button>"	
							       + "<button type='button' class='btn  btn-primary two_del' data-id='" + value + "'><i class='fa'></i>删除</button></div>"; 
						}
			        }]
				});
				
				$("#first-switch").click(function(){
					var diaTitle = "确认要启用吗";
					var text = $(this).text();
					var _btn = $(this);
					if (text == "关闭") {
						diaTitle = "确认要关闭吗";
					}
					var diaLabel = "确认启用";
					if (text == "关闭") {
						diaLabel = "确认关闭";
					}
					this.dialog =  BootstrapDialog.show({
			            title: diaTitle,
			            type : BootstrapDialog.TYPE_WARNING,
			            draggable: true,
			            size : BootstrapDialog.SIZE_SMALL,
			            buttons: [{
							 label: diaLabel,
							 cssClass: 'btn-primary ',
							 action: function(dialog) {
								 dialog.close();
								 if (text == "启用") {
									switchStatus("1", "2", text, _btn, "1");
								} else {
									switchStatus("2", "1", text, _btn, "1");
								}
							 }
						 }, {
							 label: '取消',
							 action: function(dialog) {
								 dialog.close();
							 }
						 }]
						 });
				});
				
				$("#second-switch").click(function(){
					var diaTitle = "确认要启用吗";
					var text = $(this).text();
					var _btn = $(this);
					if (text == "关闭") {
						diaTitle = "确认要关闭吗";
					}
					var diaLabel = "确认启用";
					if (text == "关闭") {
						diaLabel = "确认关闭";
					}
					this.dialog =  BootstrapDialog.show({
			            title: diaTitle,
			            type : BootstrapDialog.TYPE_WARNING,
			            draggable: true,
			            size : BootstrapDialog.SIZE_SMALL,
			            buttons: [{
							 label: diaLabel,
							 cssClass: 'btn-primary ',
							 action: function(dialog) {
								 dialog.close();
								 if (text == "启用") {
									switchStatus("2", "1", text, _btn, "2");
								} else {
									switchStatus("1", "2", text, _btn, "2");
								}
							 }
			            }, {
							 label: '取消',
							 action: function(dialog) {
								 dialog.close();
							 }
						 }]
						 });
				});
				
				var switchStatus = function(openType, closeType, text, _btn, index){
					$.ajax({ 
		        		type         : 'post',
						url          : urlPrefix+"kdAdvertise/switchStatus",
						dataType     : 'json',
						data: {
							openType : openType,
							closeType : closeType,
							index : index
						},
						success : function(msg){
							if (msg.code == "NACK") {
								$(window).loadingInfo("warn", msg.message);
							} else {
								if (index == "1") {
									if (text == "启用") {
										 _btn.text("关闭");
										 _btn.removeClass("btn-primary").addClass("btn-default");
										$("#second-switch").text("启用");
										$("#second-switch").removeClass("btn-default").addClass("btn-primary");
									} else {
										_btn.text("启用");
										_btn.removeClass("btn-default").addClass("btn-primary");
										if (msg.message != "获取成功！") {
											$("#second-switch").text("关闭");
											$("#second-switch").removeClass("btn-primary").addClass("btn-default");
										}
									}
								} else {
									if (text == "启用") {
										_btn.text("关闭");
										 _btn.removeClass("btn-primary").addClass("btn-default");
										$("#first-switch").text("启用");
										$("#first-switch").removeClass("btn-default").addClass("btn-primary");
									} else {
										_btn.text("启用");
										_btn.removeClass("btn-default").addClass("btn-primary");
										if (msg.message != "获取成功！") {
											$("#first-switch").text("关闭");
											$("#first-switch").removeClass("btn-primary").addClass("btn-default");
										}
									}
								}
							}
						}
					});
				};
			},
			init:function(){
				var _self = this;
				_self.initEvents();
			}
	}.init();
});