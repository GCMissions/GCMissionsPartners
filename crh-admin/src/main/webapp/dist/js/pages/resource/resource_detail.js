$(function(){
	var slideDetail = {
			init : function() {
				var _self = this;
				_self.initEvents();
			},
			initEvents : function(){  
				$("#add").click(function(){
					var image = $("#resource_img").attr("src");
					var link = $("#link").val();
					var title = $("#title").val();
					var type = "0";
					var remarks = "1";
					if("none" != document.getElementById("video").style.display){
						var remarks = "0"; 
					}
					if (image == uiBase + "img/default_goods_image_240.gif" || image == uiBase + "/img/default_goods_image_240.gif") {
						$("body").loadingInfo("warn", "Please upload the picture!");
						return;
					}
					if (image == uiBase + "img/loading.gif" || image == uiBase + "/img/loading.gif") {
						$("body").loadingInfo("warn", "Please wait a moment!");
						return;
					}
					if (link == "") {
						$("body").loadingInfo("warn", "Please enter the link!");
						return;
					}
					if (title == "") {
						$("body").loadingInfo("warn", "Please enter the title!");
						return;
					}
					
					var saveParam = {
						"image" : image,
						"link" : link,
						"title" : title,
						"type" : type,
						"remarks" : remarks
					}
					saveSlide(saveParam);
				});
				
				$("#edit").click(function(){
					var image = $("#resource_img").attr("src");
					var link = $("#link").val();
					var title = $("#title").val();
					var type = "1";
					var remarks = "1";
					if("none" != document.getElementById("video").style.display){
						var remarks = "0"; 
					}
					if (image == uiBase + "img/default_goods_image_240.gif" || image == uiBase + "/img/default_goods_image_240.gif") {
						$("body").loadingInfo("warn", "Please upload the picture!");
						return;
					}
					if (image == uiBase + "img/loading.gif" || image == uiBase + "/img/loading.gif") {
						$("body").loadingInfo("warn", "Please wait a moment!");
						return;
					}
					if (link == "") {
						$("body").loadingInfo("warn", "Please enter the link!");
						return;
					}
					if (title == "") {
						$("body").loadingInfo("warn", "Please enter the title!");
						return;
					}
					
					var saveParam = {
					    "id"	: $("#resourceId").val(),	
					    "image" : image,
						"link" : link,
						"title" : title,
						"type" : type,
						"remarks" : remarks
					}
					saveSlide(saveParam);
				});
				
				var saveSlide = function(saveParam){
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "resource/save",
						dataType     : 'json',
						contentType: "application/json;charset=utf-8",
						data 	     : JSON.stringify(saveParam),
						success : function(msg){
							if (msg.code == "ACK") {
								window.location.href=urlPrefix+"resource/";
							}
						}
			    	});
				};
				
				var upload = function () {
					var src = $("#resource_img").attr('src');
					$("#resource_img").attr('src', uiBase + "/img/loading.gif");
					$.ajaxFileUpload({
						url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.resource}),
						secureuri : false,
						fileElementId : "resource",
						dataType : 'json',
						global : false,
						data : {},
						success : function (data, status) {
							if (data.code == "ACK") {
								$('#resource_img').attr('src', data.data.url);   
							} else {
								$(window).loadingInfo("error", data.message);
								$('#resource_img').attr('src', src);
							}
						}
						
					});
					return false;  
				};
				
				$("#resource_upload").on('change', '.upload-btn input[type="file"]', function(){
					upload();
		        });
			}
	}.init();
})