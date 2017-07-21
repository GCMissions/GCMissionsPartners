$(function(){
	var slideDetail = {
			init : function() {
				var _self = this;
				_self.initEvents();
			},
			initEvents : function(){
				$("#edit").click(function(){
	                var title = $("#title").val();
					var content = $("#content").val();
					var saveParam = {
					    "id"	: $("#missionId").val(),	
						"title" : title,
						"content" : content,
					}
					saveSlide(saveParam);
				});
				
			
				var saveSlide = function(saveParam){
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "mission/",
						dataType     : 'json',
						contentType: "application/json;charset=utf-8",
						data 	     : JSON.stringify(saveParam),
						success : function(msg){
							if (msg.code == "ACK") {
								window.location.href=urlPrefix+"mission/";
							} else {
//								$("body").loadingInfo("warn", msg.message);
							}
						}
			    	});
				};
				

			}
	}.init();
})