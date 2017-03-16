var loginApp = {
	init : function() {
		if(window != top) {
			top.location.reload(true);
		}
		
		
		$('#loginForm').validate({
			rules : {
				"loginId" : {
					required : true
				},
				"password" : {
					required : true
				},
				"captcha" : {
					required : false
				},
			},
			messages : {
				"loginId" : {
					required : "Please enter the User Name! "
				},
				"password" : {
					required : "Please enter the Password!"
				},
				"captcha" : {
					required : "Please enter the Captcha"
				},
			}
			
		});
		this.changeCaptcha();
		this.cookieCheck();
		
		$("#captchaImage").on('click', _(this.changeCaptcha).bind(this)); 
		$('.loginButton').on('click', _(this.doLogin).bind(this)); 
		
		// 用户名输入框回车直接进入密码输入框
		$("#loginId").keydown(function(e) {
			if (e.keyCode == "13") {
				$("#loginId").removeClass("focus");
				$("#password").focus();
				$("#password").select();
			}
		});
		// 密码输入框回车直接进入验证码输入框
		$("#password").keydown(function(e) {
			var isCapthaHide = $('#verifyCodeLi').hasClass('hide');
			if (e.keyCode == "13") {
				if(isCapthaHide){
					$(".loginButton").click();
				}else{
					$("#password").removeClass("focus");
					$("#captcha").focus();
					$("#captcha").select();
				}
			}
		});
		// 验证码输入框回车直接登录
		$("#captcha").keydown(function(e) {
			if (e.keyCode == "13") {
				$(".loginButton").click();
			}
		});
		
	},
	doLogin : function () {
		var that = this;
		if($('#loginForm').validate().form()) {
			var data = $('#loginForm').frmSerialize();
            $(".loginButton").attr("disabled",true).text("Is the login..."); 
            data.password =  $.md5(data.password);
            that.saveUserInfo();
            
            delete data.isRememberUsername;
            
            
			$.ajax({
				type: 'POST',
				url: urlPrefix + "login/authc",
				dataType: 'json',
				contentType: 'application/json',
				data:  JSON.stringify(data),
				$loadingObject :  $('#loginForm')
            })
        	.done(function(result) {
        	 	if(result.code =="ACK") {
        	 		$('#verifyCodeLi').hide();
        	 		$('#verifyCodeLi').addClass('hide');
        	 		$('#loginForm').loadingInfo({
                        type : "success",
                        text : "Login success",
                        timeouts : 1500,
                        callBack : function() {
                            window.location.href= $.GLOBAL.config.mainUrl;
                        }
                    });
                }else {
                	$('#verifyCodeLi').show();
                	$('#verifyCodeLi').removeClass('hide');
                	$('#loginForm').loadingInfo("warn", result.message);
                }
		     
        	 })
             .always(function() {
                 $(".loginButton").attr("disabled",false).text("Login"); 
                 that.changeCaptcha();
             })
        	 .fail(function(result) {
        	 	
        	 	that.changeCaptcha();
		     
        	 });
		}
	},
	changeCaptcha : function() {
		var key = Math.random();
		$("#captchaImage").attr("src", urlPrefix + "login/captcha?key=" + key);
		$("#key").val(key);
	},
	saveUserInfo : function() {
		if ($("#isRememberUsername").is(":checked")) {
			var userName = $("#loginId").val();
			addCookie("sysUser", "true", {
				expires : 7*24*3600
			}); // 存储一个带7天期限的 cookie
			addCookie("loginId", userName, {
				expires : 7*24*3600
			}); // 存储一个带7天期限的 cookie
		} else {
			addCookie("sysUser", "false", {
				expires : -1
			}); // 删除 cookie
			addCookie("loginId", '', {
				expires : -1
			});
		}
	},
	cookieCheck : function() {
		if (getCookie("sysUser") == "true") {
			$("#isRememberUsername").attr("checked", true);
			$("#loginId").val(getCookie("loginId"));
		}
	},
	
		
};