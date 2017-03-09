$(function(){
	var slideDetail = {
			init : function() {
				var _self = this;
				_self.initEvents();
			},
			initEvents : function(){
				$("#add").click(function(){
					var image = $("#slide_img").attr("src");
					var description = $("#desc").val();
					var display;
					if ($('#radio_box input[type="radio"]:checked').val() == "display") {
						display = "1";
					} else {
						display = "0";
					}
					var type = "0";
					if (image == uiBase + "img/default_goods_image_240.gif" || image == uiBase + "/img/default_goods_image_240.gif") {
						$("body").loadingInfo("warn", "Please upload the picture!");
						return;
					}
					if (image == uiBase + "img/loading.gif" || image == uiBase + "/img/loading.gif") {
						$("body").loadingInfo("warn", "Please wait a moment!");
						return;
					}
					
					var saveParam = {
						"image" : image,
						"description" : description,
						"display" : display,
						"type" : type
					}
					saveSlide(saveParam);
				});
				
				$("#edit").click(function(){
					var image = $("#slide_img").attr("src");
					var description = $("#desc").val();
					var display;
					if ($('#radio_box input[type="radio"]:checked').val() == "display") {
						display = "1";
					} else {
						display = "0";
					}
					var type = "1";
					if (image == uiBase + "img/default_goods_image_240.gif" || image == uiBase + "/img/default_goods_image_240.gif") {
						$("body").loadingInfo("warn", "Please upload the picture!");
						return;
					}
					if (image == uiBase + "img/loading.gif" || image == uiBase + "/img/loading.gif") {
						$("body").loadingInfo("warn", "Please wait a moment!");
						return;
					}
					
					var saveParam = {
					    "id"	: $("#slideId").val(),	
						"image" : image,
						"description" : description,
						"display" : display,
						"type" : type
					}
					saveSlide(saveParam);
				});
				
				var saveSlide = function(saveParam){
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "slides/save",
						dataType     : 'json',
						contentType: "application/json;charset=utf-8",
						data 	     : JSON.stringify(saveParam),
						success : function(msg){
							if (msg.code == "ACK") {
								window.location.href=urlPrefix+"slides/";
							} else {
								$("body").loadingInfo("warn", msg.message);
							}
						}
			    	});
				};
				
				var upload = function () {
					var src = $("#slide_img").attr('src');
					$("#slide_img").attr('src', uiBase + "/img/loading.gif");
					$.ajaxFileUpload({
						url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.slides}),
						secureuri : false,
						fileElementId : "slide",
						dataType : 'json',
						global : false,
						data : {},
						success : function (data, status) {
							if (data.code == "ACK") {
								$('#slide_img').attr('src', data.data.url);   
							} else {
								$(window).loadingInfo("error", data.message);
								$('#slide_img').attr('src', src);
							}
						}
						
					});
					return false;  
				};
				
				$("#slide_upload").on('change', '.upload-btn input[type="file"]', function(){
					upload();
		        });
			}
	}.init();
})