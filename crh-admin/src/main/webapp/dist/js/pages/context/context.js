$(function(){
	var context = {
			initEvents:function(){
				var date = new Date();
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				var day = date.getDate();
				var globalPic = [];
				
				$('#csDate,#ceDate').datetimepicker({
					minView: 'month',
					format: 'yyyy-MM-dd',
					language: 'ch',
					endDate: year+'-'+month+'-'+day,
					autoclose : true,
					todayBtn : true
				});
				
				$('#ceDate')
				.datetimepicker()
				.on('changeDate', function(ev){
					if (ev.date != null) {
						var endYear = ev.date.getFullYear();
						var endMonth = ev.date.getMonth()+1;
						var endDay = ev.date.getDate();
						$('#csDate').datetimepicker('setEndDate', endYear+'-'+endMonth+'-'+endDay);
					}
				});
				
				$('#csDate')
				.datetimepicker()
				.on('changeDate', function(ev){
					if (ev.date != null) {
						var startYear = ev.date.getFullYear();
						var startMonth = ev.date.getMonth()+1;
						var startDay = ev.date.getDate();
						$('#ceDate').datetimepicker('setStartDate', startYear+'-'+startMonth+'-'+startDay);
					}
				});
				
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					refreshBtn : $('#refreshRecord'),
				    sidePagination:'server',
				    pagination : true,
				    url : "context/list",
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	var startDate = $("#csDate").val();
				    	var endDate = $("#ceDate").val();
				    	var actName = $.trim($("#actName").val());
				    	var actCode = $.trim($("#actCode").val())
				    	var result= {
				    			startDate:startDate,
				    			endDate: endDate, 
				    			actName: actName, 
				    			actCode: actCode
						}
				    	return result;
				    },
				    initSearchForm : true, 
				    fillSearchData : function(data) {
				    	 $("#csDate").val(data.startDate);
				    	 $("#ceDate").val(data.endDate);
				    	 $("#actName").val(data.actName);
				    	 $("#actCode").val(data.actCode);
				    	
				    },
				    
				    columns: [{
				    	title: '序号',
				    	field : 'index',
				    	width:"5%",
				    	align: 'center'
				    },{
						title: '活动',
						field : 'description',
					    width:"40%",
					    formatter:function(value,row,index){  
					    	var strArr = value.split("#&amp;#");
					    	var proCode = strArr[0];
					    	var productName = strArr[1];
					    	var startDate = strArr[2];
			                return "<div><div><label id='proCode'>商品编号：" + proCode + 
			                "</label></div><div><label id='proName'>活动名称：" + productName + 
			                "</label></div><div><label id='dateBetween'>" + startDate + "</label></div></div>"
			            }
				    },
			        {
				    	title: '活动照片',
						field : 'imageList',
						width:"30%",
						align: 'center',
						formatter:function(value,row,index){
							if (value.length > 0) {
								var _html = "<div><div class='m-pic'>";
								if (value.length > 3) {
									for (var i = 1; i <= 3; i++) {
										var imageUrl = value[i-1].substring(0, value[i-1].lastIndexOf(";"));
										var actStockId = value[i-1].substring(value[i-1].lastIndexOf(";") + 1, value[i-1].length);
										_html += "<img src='" + imageUrl + "' id='img_" + i + "' data-pid='" + actStockId + "' class='act_image'>";
									}
									_html += "</div><div class='m-pic'>";
									for (var i = 4; i <= value.length; i++) {
										var imageUrl = value[i-1].substring(0, value[i-1].lastIndexOf(";"));
										var actStockId = value[i-1].substring(value[i-1].lastIndexOf(";") + 1, value[i-1].length);
										_html += "<img src='" + imageUrl + "' id='img_" + i + "' data-pid='" + actStockId + "' class='act_image'>";
									}
								} else {
									for (var i = 1; i <= value.length; i++) {
										var imageUrl = value[i-1].substring(0, value[i-1].lastIndexOf(";"));
										var actStockId = value[i-1].substring(value[i-1].lastIndexOf(";") + 1, value[i-1].length);
										_html += "<img src='" + imageUrl + "' id='img_" + i + "' data-pid='" + actStockId + "' class='act_image'>";
									}
								}
								_html += "</div></div>";
								return _html;
							} else {
								return "暂无活动照片";
							}
						}
			        },
					{
					    title: '操作',
						field : 'status',
						width:"25%",
						formatter:function(value,row,index){
							var actStockId = value.substring(0, value.lastIndexOf(";"));
							var imageCount = value.substring(value.lastIndexOf(";") + 1, value.length);
							if (imageCount > 0) {
								return "<div><div><button class='btn  btn-upload' id='upload_img' data-pid='" + actStockId + "'>+上传照片</button></div><div><a class='btn  btn-editor' href='contextEditor/" + actStockId + "' id='edit_img' data-pid='" + actStockId + "'>编辑</a></div><div><input type='file' name='file' style='display: none' data-pid='" + actStockId + "' id='fileInput_" + actStockId + "' data-count='" + imageCount + "'  class='fileInput' multiple='true' /></div></div>";
							} else {
								return "<div><div><button class='btn  btn-upload' id='upload_img' data-pid='" + actStockId + "'>+上传照片</button></div><div><button disabled class='btn  btn-editor-dis' href='contextEditor/" + actStockId + "' id='edit_img' data-pid='" + actStockId + "'>编辑</button></div><div><input type='file' name='file' style='display: none' data-pid='" + actStockId + "' id='fileInput_" + actStockId + "' data-count='" + imageCount + "'  class='fileInput' multiple='true' /></div></div>";
							}
						}
			        }]
				});
				
				var table1 = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList1'),
					sidePagination : 'server',
					pagination : true,
					url : "material/list",
					queryParamsType : "limit",
					queryAddParams : function() {
						
					},
					columns: [{
						field : 'imageAndIdDtos',
						formatter : function(value,row,index){
							var _html = "";
							var rows = Math.floor(value.length / 5);
							for (var i = 0; i < rows; i++) {
								_html += "<div class='row picfive'>";
								for (var j = i * 5; j < (i + 1) * 5; j++) {
									if (globalPic.indexOf(value[j].imageMaterialId.toString()) > -1) {
										_html += "<span class='image-div'><input class='check-image' type='checkbox' name='box' value='"
										+ value[j].imageMaterialId + "' checked='checked' /><img src='" + value[j].url + 
										"' /></span>";
									} else {
										_html += "<span class='image-div'><input class='check-image' type='checkbox' name='box' value='"
										+ value[j].imageMaterialId + "' /><img src='" + value[j].url + 
										"' /></span>";
									}
								}
								_html += "</div>";
							}
							if (value.length > rows * 5) {
								_html += "<div class='row picfive'>";
								for (var i = rows * 5; i < value.length; i++) {
									if (globalPic.indexOf(value[i].imageMaterialId.toString()) > -1) {
										_html += "<span class='image-div'><input class='check-image' type='checkbox' name='box' value='"
										+ value[i].imageMaterialId + "' checked='checked' /><img src='" + value[i].url + 
										"' /></span>";
									} else {
										_html += "<span class='image-div'><input class='check-image' type='checkbox' name='box' value='"
										+ value[i].imageMaterialId + "' /><img src='" + value[i].url + 
										"' /></span>";
									}
								}
								_html += "</div>";
							}
							return _html;
						}
					}]
				});
				
				$("#dataList1").on('click','.check-image',function(){
					if ($(this).is(':checked') == true) {
						globalPic.push($(this).val());
					} else {
						var index = globalPic.indexOf($(this).val());
						if (index > -1) {
							globalPic.splice(index, 1);
						}
					}
				}); 
				
				// 上传照片
				$(".control").on('click', '.btn-upload', function(){
					var actStockId = $(this).data("pid");
					askFor(actStockId);
				});
				
				$(".control").on('change', '.fileInput', function(){
					$(window).loadingInfo("warn", "正在上传，请稍等！");
					var actStockId = $(this).data("pid");
					ajaxFileUpload(actStockId);
		        });
				
				$(".control").on('click', '.act_image', function(){
					var actStockId = $(this).data("pid");
					window.location.href="contextEditor/" + actStockId; 
		        });
				
				$("#allCheck").click(function(){
					totalcheck();
				});
				
				var totalcheck = function(){
					var box = document.getElementsByName("box");
					var checkCount = 0;
					for (var i = 0; i < box.length; i++) {
						if (box[i].checked == false) {
							box[i].checked = true;
							globalPic.push(box[i].value);
						} else {
							checkCount++;
						}
					}
					if (checkCount == box.length) {
						for (var i = 0; i < box.length; i++) {
							box[i].checked = false;
							var index = globalPic.indexOf(box[i].value);
							if (index > -1) {
								globalPic.splice(index, 1);
							}
						}
					}
				};
				
				var askFor = function(actStockId){
					var title = "从本地上传或者素材库选择";
					BootstrapDialog.show({
						title: "上传",
						type : BootstrapDialog.TYPE_WARNING,
						message: message(title),
						draggable: true,
						size : BootstrapDialog.SIZE_SMALL,
						buttons: [{
							label: '本地上传',
							action: function(dialog) {
								$("#fileInput_" + actStockId).click();
								dialog.close();
							}
						}, {
							label: '素材库选择',
							action: function(dialog) {
								window.location.href="contextMaterial/" + actStockId; 
								dialog.close();
							}
						}]
					});
				};
				
				var ajaxFileUpload = function (actStockId) {
					$.ajaxFileUpload({
						url : $.GLOBAL.config.ossUploadUrlList.template({source: uploadSourcesMap.myActivity}),
						secureuri : false,
						fileElementId : "fileInput_" + actStockId,
						dataType : 'json',
						global : false,
						data : {},
						success : function (data, status) {
							if (data.code == "ACK") {
								for (var i = 0; i < data.data.length; i++) {
									uploadImage(actStockId, data.data[i].url);
								}
							} else {
								$(window).loadingInfo("error", data.message);
							}
						}
						
					});
					return false;  
				};
				
				$(".btn_material").on('click', '#upload_material', function(){
					$("#material-upload").click();
				});
				
				$(".btn_material").on('change', '#material-upload', function(){
					$(window).loadingInfo("warn", "正在上传，请稍候......");
					ajaxFileUpload1();
				});
				
				var uploadImage = function(actStockId, imageUrl) {
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "context/uploadPic",
						dataType     : 'json',
						data 	     : {
							"actStockId" : actStockId,
							"imageUrl" : imageUrl
						},
						success : function(msg){
							if (msg.code == "ACK") {
								$(window).loadingInfo("success", msg.message);
								table.refresh();
							} else {
								$(window).loadingInfo("warn", msg.message);
							}
						}
			    	});
				};
				
				var ajaxFileUpload1 = function () {
					$.ajaxFileUpload({
						url : $.GLOBAL.config.ossUploadUrlList.template({source: uploadSourcesMap.imageMaterial}),
						secureuri : false,
						fileElementId : "material-upload",
						dataType : 'json',
						global : false,
						data : {},
						success : function (data, status) {
							if (data.code == "ACK") {
								for(var i = 0; i < data.data.length; i++) {
									uploadImage1(data.data[i].url);
								}
							} else {
								$(window).loadingInfo("error", data.message);
							}
						}
						
					});
					return false;  
				};
				
				var uploadImage1 = function(imageUrl) {
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "material/uploadImage",
						dataType     : 'json',
						data 	     : {
							"url" : imageUrl
						},
						success : function(msg){
							if (msg.code == "ACK") {
								$(window).loadingInfo("success", msg.message);
								globalPic = [];
								table1.refreshThis();
							} else {
								$(window).loadingInfo("warn", msg.message);
							}
						}
			    	});
				};
				
				
				$(".btn_material").on('click', '#delete_img', function(){
					if (globalPic.length > 0) {
						deleteImages1(globalPic);
					} else {
						$(window).loadingInfo("warn", "请先选择素材！");
					}
				});
				
				var deleteImages1 = function(deleteIds){
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "material/deleteImages",
						dataType     : 'json',
						data : {
							'deleteIds':deleteIds
						},
						success : function(msg){
							if (msg.code == "ACK") {
								$(window).loadingInfo("success", msg.message);
								globalPic = [];
								table1.refresh();
							} else {
								$(window).loadingInfo("warn", msg.message);
							}
						}
			    	});
				};
				
				$("body").on("click",".image-div img",function(){
					var _thisparent = $(this).parent();
					if(_thisparent.is(":hidden")){
						var _thisImgSrc = $(this).attr("src");
						$(".wrapimg img").attr("src",_thisImgSrc);
						$(".wrapimg").show().animate({"width":"400px","height":"400px","margin-left":"-350px","margin-top":"-325px"},100);
					}else{
						$(".wrapimg").hide().css({"width":"0px","height":"0px","margin-left":"0px","margin-top":"0px"});
						var _thisImgSrc = $(this).attr("src");
						$(".wrapimg img").attr("src",_thisImgSrc);
						$(".wrapimg").show().animate({"width":"400px","height":"400px","margin-left":"-350px","margin-top":"-325px"},100);
					}
					
				});
				$(".wrapimg").click(function(){
					$(".wrapimg").hide().css({"width":"400px","height":"400px","margin-left":"0px","margin-top":"0px"});
				});
				$(".wrapper").click(function(){
					$(".wrapimg").hide().css({"width":"400px","height":"400px","margin-left":"0px","margin-top":"0px"});
				});
				
			},
			
			
			init:function(){
				var _self = this;
				_self.initEvents();
			}
	}.init();
			
});