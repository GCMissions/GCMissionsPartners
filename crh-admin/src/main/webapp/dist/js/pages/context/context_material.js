$(function(){
	var contextMaterial = {
			initEvents : function(){
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
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
									if (globalPic.indexOf(value[j].url) > -1) {
										_html += "<span class='image-div'><input class='check-image' type='checkbox' name='box' value='"
										+ value[j].url + "' data-id='" + value[j].imageMaterialId + "' checked='checked'/><img src='" + value[j].url + 
										"' /></span>";
									} else {
										_html += "<span class='image-div'><input class='check-image' type='checkbox' name='box' value='"
										+ value[j].url + "' data-id='" + value[j].imageMaterialId + "'  /><img src='" + value[j].url + 
										"' /></span>";
									}
								}
								_html += "</div>";
							}
							if (value.length > rows * 5) {
								_html += "<div class='row picfive'>";
								for (var i = rows * 5; i < value.length; i++) {
									if (globalPic.indexOf(value[i].url) > -1) {
										_html += "<span class='image-div'><input class='check-image' type='checkbox' name='box' value='"
										+ value[i].url + "' data-id='" + value[i].imageMaterialId + "' checked='checked' /><img src='" + value[i].url + 
										"' /></span>";
									} else {
										_html += "<span class='image-div'><input class='check-image' type='checkbox' name='box' value='"
										+ value[i].url + "' data-id='" + value[i].imageMaterialId + "'  /><img src='" + value[i].url + 
										"' /></span>";
									}
								}
								_html += "</div>";
							}
							return _html;
						}
					}]
				});
				
				var globalPic = [];
				
				$(".btn_material").on('click', '#upload_material', function(){
					if (globalPic.length > 0) {
						uploadImages(actStockId, globalPic, fromUrl);
					} else {
						$(window).loadingInfo("warn", "请先选择素材！");
					}
				});
				
				$("#dataList").on('click','.check-image',function(){
					if ($(this).is(':checked') == true) {
						globalPic.push($(this).val());
					} else {
						var index = globalPic.indexOf($(this).val());
						if (index > -1) {
							globalPic.splice(index, 1);
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
				
				var uploadImages = function(actStockId, imageUrls, fromUrl){
					var actStockId = $("#actStockId").val();
					$(window).loadingInfo("success", "上传成功！");
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "context/uploadPics",
						dataType     : 'json',
						data : {
							'actStockId':actStockId,
							'imageUrls':imageUrls
						},
						success : function(msg){
							if (msg.code == "ACK") {
								if (fromUrl == "list") {
									window.location.href=urlPrefix + "context/";
								} else {
									globalPic = [];
									table.refreshThis();
								}
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