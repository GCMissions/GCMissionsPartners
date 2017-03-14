$(function(){
	var partnerDetail = {
			init : function() {
				var _self = this;
				_self.initEvents();
			},
			initEvents : function(){
				var upload = function () {
					var src = $("#partner_img").attr('src');
					$("#partner_img").attr('src', uiBase + "/img/loading.gif");
					$.ajaxFileUpload({
						url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.partner}),
						secureuri : false,
						fileElementId : "partner",
						dataType : 'json',
						global : false,
						data : {},
						success : function (data, status) {
							if (data.code == "ACK") {
								$('#partner_img').attr('src', data.data.url);   
							} else {
								$(window).loadingInfo("error", data.message);
								$('#partner_img').attr('src', src);
							}
						}
						
					});
					return false;  
				};
				
				$("#partner_upload").on('change', '.upload-btn input[type="file"]', function(){
					upload();
		        });
				
				$("#add").click(function(){
					var partnerName = $("#partnerName").val();
					var mission = $("#mission").val();
					var regionId = $("#region").val();
					var countryId = $("#country").val();
					var address = $("#address").val();
					var image = $("#partner_img").attr("src");
					var introduce = $("#introduce").val();
					var type = "0";
					if (partnerName == "") {
						$("body").loadingInfo("warn", "Please enter the Partner!");
						return;
					}
					if (mission == "") {
						$("body").loadingInfo("warn", "Please enter the Mission!");
						return;
					}
					if (regionId == "") {
						$("body").loadingInfo("warn", "Please choose a region!");
						return;
					}
					if (countryId == "") {
						$("body").loadingInfo("warn", "Please choose a country!");
						return;
					}
					if (image == uiBase + "img/default_goods_image_240.gif" || image == uiBase + "/img/default_goods_image_240.gif") {
						$("body").loadingInfo("warn", "Please upload the picture!");
						return;
					}
					if (image == uiBase + "img/loading.gif" || image == uiBase + "/img/loading.gif") {
						$("body").loadingInfo("warn", "Please wait a moment!");
						return;
					}
					if (introduce == "") {
						$("body").loadingInfo("warn", "Please enter the introduce!");
						return;
					}
					
					var saveParam = {
						"partnerName" : partnerName,
						"mission" : mission,
						"regionId" : regionId,
						"address" : address,
						"countryId" : countryId,
						"image" : image,
						"introduce" : introduce,
						"type" : type
					}
					savePartner(saveParam);
				});
				
				$("#edit").click(function(){
					var partnerName = $("#partnerName").val();
					var mission = $("#mission").val();
					var regionId = $("#region").val();
					var countryId = $("#country").val();
					var address = $("#address").val();
					var image = $("#partner_img").attr("src");
					var introduce = $("#introduce").val();
					var type = "1";
					if (partnerName == "") {
						$("body").loadingInfo("warn", "Please enter the Partner!");
						return;
					}
					if (mission == "") {
						$("body").loadingInfo("warn", "Please enter the Mission!");
						return;
					}
					if (regionId == "") {
						$("body").loadingInfo("warn", "Please choose a region!");
						return;
					}
					if (countryId == "") {
						$("body").loadingInfo("warn", "Please choose a country!");
						return;
					}
					if (image == uiBase + "img/default_goods_image_240.gif" || image == uiBase + "/img/default_goods_image_240.gif") {
						$("body").loadingInfo("warn", "Please upload the picture!");
						return;
					}
					if (image == uiBase + "img/loading.gif" || image == uiBase + "/img/loading.gif") {
						$("body").loadingInfo("warn", "Please wait a moment!");
						return;
					}
					if (introduce == "") {
						$("body").loadingInfo("warn", "Please enter the introduce!");
						return;
					}
					
					var saveParam = {
						"id" : $("#partnerId").val(),
						"partnerName" : partnerName,
						"mission" : mission,
						"regionId" : regionId,
						"countryId" : countryId,
						"address" : address,
						"image" : image,
						"introduce" : introduce,
						"type" : type
					}
					savePartner(saveParam);
				});
				
				var savePartner = function(saveParam){
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "partner/save",
						dataType     : 'json',
						contentType: "application/json;charset=utf-8",
						data 	     : JSON.stringify(saveParam),
						success : function(msg){
							if (msg.code == "ACK") {
								window.location.href=urlPrefix+"partner/";
							} else {
								$("body").loadingInfo("warn", msg.message);
							}
						}
			    	});
				};
				
				$("#region").change(function(){
					var id = $(this).val();
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "partner/getCountry",
						dataType     : 'json',
						data 	     : {
							"regionId" : id
						},
						success : function(msg){
							$("#country").empty(); 
							var countryList = msg.data;
							for (var i = 0; i < countryList.length; i++) {
								$("#country").append("<option value=" + countryList[i].id + ">" + countryList[i].countryName + "</option>");
							}
						}
			    	});
				});
			}
	}.init();
})