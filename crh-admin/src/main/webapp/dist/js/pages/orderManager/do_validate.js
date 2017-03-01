$(function(){
	var doValidate = {
		initEvents : function(){
			$("#do-validate").click(function(){
				var productId = $("#productId").val();
				var code = $("#validate-input").val();
				validate(productId, code);
			});
			
			window.onload = function(){
				var oInput = document.getElementById("validate-input");
				oInput.focus();
			};
			
			var validate = function(productId, code){
				$.ajax({
		    		type         : 'POST',
					url          : urlPrefix + "validate/validateCode",
					dataType     : 'json',
					data 	     : {
						"productId" : productId,
						"code" : code
					},
					success : function(msg){
						if (msg.code == "ACK") {
							$("body").loadingInfo("success", msg.message);
							$("#validate-input").val("");
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