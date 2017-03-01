$(function() {
	var supplierAdd = {
		initEvents : function() {
			var _self = this;
			// 验证
			this.validator = $('#addForm').validate({});
			$("#save").on('click', _(this.submitForm).bind(this));

			$(".img_div").on('change', '.upload-btn input[type="file"]',
					_(this.ajaxFileUpload).bind(this));

		},

		submitForm : function() {
			var formFlag = this.validator.form();
			if (!formFlag) {
				return false;
			} else {
				if ($("#uploadImageUrl").val() =='' || $("#uploadImageUrl").val() == null) {
					$("body").loadingInfo("warn", "请上传图片");
					return;
				}
				this.doSave();
			}
		},
		doSave : function() {
			var addForm = {
				id : $('#entity_id').val(),
				uploadImageUrl : $("#uploadImageUrl").val(),
				mobileType : $("select[name='mobileType'] option:selected")
						.val(),
				height : $("#height").val(),
				width : $("#width").val(),
				version : $("#version").val(),
			};
			$.ajax({
				type : "POST",
				url : urlPrefix+"appStartupHomepage/updateOrInsert",
				contentType : "application/json;charset=utf-8",
				data : JSON.stringify(addForm),
				dataType : "json",
				success : function(message) {
					if (message.code == "ACK") {
						window.location.href = urlPrefix
								+ "appStartupHomepage/";
					}
				},
				error : function(message) {
					// alert('shibai');
				}
			});

		},
		
		ajaxFileUpload : function(){
			var that = this;
			var src = $('#imgUrl').attr('src');
			$('#imgUrl').attr('src', uiBase + "/img/loading.gif");
			$.ajaxFileUpload({
				url : $.GLOBAL.config.ossUploadUrl.template({source: uploadSourcesMap.appStartup}),
				secureuri : false,
				fileElementId : "appStartup_1",
				dataType : 'json',
				global : false,
				data : {},
				success : function (data, status) {
					if (data.code == "ACK") {
						$('#uploadImageUrl').val(data.data.url);
						$('#imgUrl').attr('src', data.data.url);   
					} else {
						$(window).loadingInfo("error", data.message);
						$('#uploadImageUrl').val('');
						$('#imgUrl').attr('src', src);
					}
				}
				
			});
			return false; 
		},
		init : function() {
			var _self = this;
			_self.initEvents();
		}

	}.init();
});