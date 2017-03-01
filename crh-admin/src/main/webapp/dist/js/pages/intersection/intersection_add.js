$(function(){
	var intersectionAdd = {
			initEvents:function(){
				$("#backToHome").click(function(){
					//window.location.href=urlPrefix+"intersection/";
				});
				
				$("#saveBaseInfo").click(function(){
					var opration = $("#opration").val();
					var name = $("#intersectionName").val();
					if (name == null || name == "") {
						$(window).loadingInfo("warn", "请填写合集详情！");
						return;
					}
					var formData = $('#mainForm').frmSerialize();
			    	var description= _.isUndefined(formData['description']) ? "" : formData['description'];
					if (description == null || description == "") {
						$(window).loadingInfo("warn", "请填写合集详情！");
						return;
					}
					var id = $("#id").val();
					save(opration,name,description,id);
				});
				
				var save = function(opration,name,description,id){
					$.ajax({ 
			    		type         : 'post',
						url          : urlPrefix+"intersection/save",
						dataType     : 'json',
						contentType: "application/json;charset=utf-8",
						data: JSON.stringify({
							opration : opration,
							name : name,
							description : description,
							id : id
						}),
						success : function(msg){
							if (msg.code == "ACK") {
								window.location.href=urlPrefix+"intersection/";
							} else {
								$(window).loadingInfo("warn", msg.message);
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