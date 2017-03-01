$(function(){
	var contextEditor = {
		initEvents : function(){
			var deleteIds = [];
			var table = $.GLOBAL.utils.loadBootTable({
				table : $('#dataList'),
				sidePagination : 'server',
				pagination : true,
				url : "context/editorList",
				queryParamsType : "limit",
				queryAddParams : function() {
					var actStockId = $("#actStockId").val();
					var result = {
							actStockId : actStockId,
					}
					return result;
				},
				columns: [{
					field : 'stockImageDtos',
					formatter : function(value,row,index){
						var _html = "";
						var rows = Math.floor(value.length / 5);
						for (var i = 0; i < rows; i++) {
							_html += "<div class='row picfive'>";
							for (var j = i * 5; j < (i + 1) * 5; j++) {
								if (deleteIds.indexOf(value[j].url) > -1) {
									_html += "<span class='image-div'><input class='check-image' type='checkbox' name='box' value='"
										+ value[j].actImageRecordId + "' checked='checked'/><img src='" + value[j].imageUrl + 
										"' /></span>";
								} else {
									_html += "<span class='image-div'><input class='check-image' type='checkbox' name='box' value='"
										+ value[j].actImageRecordId + "' /><img src='" + value[j].imageUrl + 
										"' /></span>";
								}
							}
							_html += "</div>";
						}
						if (value.length > rows * 5) {
							_html += "<div class='row picfive'>";
							for (var i = rows * 5; i < value.length; i++) {
								if (deleteIds.indexOf(value[i].url) > -1) {
									_html += "<span class='image-div'><input class='check-image' type='checkbox' name='box' value='"
										+ value[i].actImageRecordId + "' checked='checked'/><img src='" + value[i].imageUrl + 
										"' /></span>";
								} else {
									_html += "<span class='image-div'><input class='check-image' type='checkbox' name='box' value='"
										+ value[i].actImageRecordId + "' /><img src='" + value[i].imageUrl + 
										"' /></span>";
								}
							}
							_html += "</div>";
						}
						return _html;
					}
				}]
			});
			
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
							for(var i = 0; i < data.data.length; i++) {
								uploadImage(actStockId, data.data[i].url);
							}
						} else {
							$(window).loadingInfo("error", data.message);
						}
					}
					
				});
				return false;  
			};
			
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
			
			$(".btn_material").on('click', '#upload_material', function(){
				var actStockId = $("#actStockId").val();
				askFor(actStockId);
			});
			
			$(".btn_material").on('change', '.fileInput', function(){
				$(window).loadingInfo("warn", "正在上传，请稍等！");
				var actStockId = $(this).data("pid");
				ajaxFileUpload(actStockId);
			});
			
			$(".btn_material").on('click', '#delete_img', function(){
				if (deleteIds.length > 0) {
					deleteImages(deleteIds);
				} else {
					$(window).loadingInfo("warn", "请先选择照片！");
				}
			});
			
			$("#dataList").on('click','.check-image',function(){
				if ($(this).is(':checked') == true) {
					deleteIds.push($(this).val());
				} else {
					var index = deleteIds.indexOf($(this).val());
					if (index > -1) {
						deleteIds.splice(index, 1);
					}
				}
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
						deleteIds.push(box[i].value);
					} else {
						checkCount++;
					}
				}
				if (checkCount == box.length) {
					for (var i = 0; i < box.length; i++) {
						box[i].checked = false;
						var index = deleteIds.indexOf(box[i].value);
						if (index > -1) {
							deleteIds.splice(index, 1);
						}
					}
				}
			};
			
			var deleteImages = function(deleteIds){
				$.ajax({
		    		type         : 'POST',
					url          : urlPrefix + "context/deleteImages",
					dataType     : 'json',
					data : {
						'deleteIds':deleteIds
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
							$(".fileInput").click();
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