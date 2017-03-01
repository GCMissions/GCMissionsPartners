<#assign headComponents = [] >
<#assign siteTitle = "管理员登录" >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}css/login.css?v=${resourceVersion}">

 
</head>
  <body class="hold-transition login-page">
   
     <div class="wrapper">
     	<div class="right_body">
     	<form action="/admin/web/login/authc" method="post" id="loginForm" class="pull-left" autocomplete="off">
     		<ul class="login_area clearfix">
	     		<li class="margin_b m-border">
	     			<div class="login-input m-tip">
	     				<input class="input-text " type="text" name="loginId" id="loginId" placeholder="请输入账号">
	     				<span class="account"></span>
	     			</div>
	     		</li>
	     		<li class="border m-border">
	     			<div class="login-input m-tip">
	     				<input class="input-text " type="password" name="password"   id="password" placeholder="请输入密码">
	     				<span class="password"></span>
	     			</div>
	     		</li>
	     		<li class="border hide" id="verifyCodeLi" style="display:none;">
	     			<div class="login-input verifyCode">
	     				<input class="input-text" type="text" maxlength="5" name="captcha" id="captcha" value=""  placeholder="请输入验证码">
	     				<span class=" pull-right">
	     					<img src="" id="captchaImage"  title="点击更换验证码">
	     				</span>
	     			</div>
	     		</li>
	     		<li>
	     			<label>
	     				<input type=checkbox value="1" name="isRememberUsername" class="rememberme" id="isRememberUsername"> 记住账号
	     			</label>
	     		</li>
	     		<li style="margin-bottom: 10px;">
	     			<input type="hidden" name="key" id="key">
	     		
	     			<button type="button" class="text-center loginButton">登 录</button>
	     		</li>
     		</ul>
			<div class="bottomimg"><img src="${uiBase}img/login_white_bg3.png" /></div>
     		</form>
     	</div>
     </div>


  </body>
  <#include "/footer.ftl" /> 
   <script type="text/javascript" src="${uiBase}vendor/jquery-md5/jquery.md5.js"></script> 
  <script type="text/javascript" src="${uiBase}js/pages/login/login.js?v=${resourceVersion}"></script> 
<script type="text/javascript">
 $(function() {
   
    loginApp.init();
});
  </script>
</html>
