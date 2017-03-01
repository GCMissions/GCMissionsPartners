$(function(){
	var coolbag = {
		init : function(){
			this.$form = $("#featureAddForm");
			this.$saveBtn = $("#save");
			
			this.bindEvent();
			this.formValidator();
		},
		bindEvent : function(){
			var _this = this;
			
			$("body").on("click","#save",function(){
				_this.$form.submit();
			});
			
			$("body").on("click","#back",function(){
				 window.location.href=urlPrefix+"coolbag/feature/";
			});
			
			$("body").on('change', '.upload-btn input[type="file"]', function(){
	            _this.ajaxFileUpload();
	        });
		},
	    ajaxFileUpload : function () {
			var that = this;
			$('#feature_img').attr('src', uiBase + "/img/loading.gif");
			$.ajaxFileUpload({
				url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.coolbag}),
				secureuri : false,
				fileElementId : "feature_upload_file",
				dataType : 'json',
				global : false,
				data : {},
				success : function (data, status) {
					if (data.code == "ACK") {
						$('#feature_image').val(data.data.url);
						$('#feature_img').attr('src', data.data.url);   
					} else {
						$(window).loadingInfo("error", data.message);
					}
				}
				
			});
			return false;  
		},
		formValidator : function(){ // form表单验证
			var _this = this;
			
			jQuery.validator.addMethod("length", function(value, element, param) { 
				return this.optional(element) || value.length == param; 
			});
			// 表单验证
			this.$form.validate({
				rules: {
					image:{
						required:true
					},
					tagIds:{
						required:true
					},
					tag: {
						required:true,
						maxlength:5
					}
				},
				messages: {
					image:{
						required:"请先上传图片"
					},
					tag: {
						maxlength:"最多只能输入{0}个字"
					}
				}, 
				errorPlacement: function(error, element) {
					if($(element).attr("name") === "file"){
						return false;
					} else if ($(element).attr("type") === "checkbox" ){
						error.appendTo($(element).parent().parent());
					} else if($(element).attr("name") === "image"){
						error.insertAfter($(element).parent().parent().parent());
					} else {
						error.insertAfter(element);
					}
					
				},
	    		submitHandler: function(form) {
	    			_this.$saveBtn.saving();
	    			_this.ajax_submit(form).complete(function(xhr) {
						var result = $.parseJSON(xhr.responseText);
						_this.$saveBtn.saved();
						if(result.code=="ACK"){
							$("#featureAddForm").loadingInfo({
			                    type : "success",
			                    text : result.message,
			                    timeouts : 1500,
			                    callBack : function() {
			                        window.location.href= urlPrefix+"coolbag/feature/";
			                    }
			                });
							 //window.location.href=urlPrefix+"coolbag/feature/";
						}
					});
	    		}
			}); 
		},
		ajax_submit : function(form) {
			var o = {};
			$(form).find('input,textarea,select').each(function() {
				var $this = $(this);
				var key = $this.attr('name');
				var val = $this.val();
				if (key) {
					if(key === 'file'){
						return ;
					}
					
					if(this.type === 'checkbox'){
						if(this.checked){
							if(key !== 'ifHomeshow'){
								if(o[key]){
									if($.isArray(o[key])){
										o[key].push(val);
									}else{
										o[key]=[o[key],val];
									}
								}else{
									o[key] = [];
									o[key].push(val);
								}
							}else if(key === 'ifHomeshow'){
								o[key] = '1';
							}
						}else{
							if(key === 'ifHomeshow')
								o[key] = '0';
						}
					} else if(this.type == "password"){
						o[key] = $.md5(val);
					} else{
						o[key] = val;
					}
				}
			});
			
			return $.ajax({
				url : $(form).attr('action'),
				type : $(form).attr('method'),
				dataType : 'json',
				headers : {
					'x-form-id' : $(form).attr('id')
				},
				contentType : 'application/json; charset=UTF-8',
				data : JSON.stringify(o)
			});
		}
	}.init();
});