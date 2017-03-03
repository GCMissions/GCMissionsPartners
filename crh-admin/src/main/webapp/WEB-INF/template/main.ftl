<#assign headComponents = ["mainPage"] >
<#include "/header.ftl" /> 
<style type="text/css">
.newOrderPopMsg {
  z-index:10000;
  right: 22px;
  bottom:-100px;
  width: 180px;
  height:100px;
  border-right: #3c8dbc 1px solid;
  border-top: #a6b4cf 1px solid;
  border-left: #a6b4cf 1px solid;
  border-bottom: #3c8dbc 1px solid;
  /* visibility: hidden; */
  /* display: none; */
  position: fixed;
  background-color: #c9d3f3;
  filter: progid:DXImageTransform.Microsoft.BasicImage(opacity=.65);
  opacity: 0.65;
}

* html div#newOrderPopMsg {
  position:absolute;
}

.newOrderPopMsg .popMsgContent {
  border-top: #728eb8 1px solid;
  border-right: #b9c9ef 1px solid;
  border-bottom: #b9c9ef 1px solid;
  border-left: #728eb8 1px solid;
  padding: 5px 10px;
  color: #1f336b;
}

</style>
</head>
	
<body class="hold-transition skin-red sidebar-mini" style="overflow-y:hidden">
    <div class="wrapper">
        <header class="main-header">
            <!-- Logo -->
            <a href="javascript:;" class="logo">
              <!-- mini logo for sidebar mini 50x50 pixels -->
              <span class="logo-mini"><img src="${uiBase}img/wrw-round.png" title="${systemName}"  width=40></span>
              <!-- logo for regular state and mobile devices -->
              <span class="logo-lg"><img src="${uiBase}img/wrw_log.png" title="${systemName}" width=150 ></span>
            </a>
           
            <nav class="navbar navbar-static-top" role="navigation">
              <!-- Sidebar toggle button-->
              <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
              </a>
              <div class="navbar-custom-menu"> 
                <ul class="nav navbar-nav">
                    <!--<li><a href="">${systemName}商城</a></li> --> 
                </ul>
              </div>
            </nav>
        </header>
        
        <!-- Left side column. contains the logo and sidebar -->
        <aside class="main-sidebar">
            <!-- sidebar: style can be found in sidebar.less -->
            <section class="sidebar">
              <!-- Sidebar user panel -->
              <div class="user-panel" >
                
                <div class="pull-left info">
                 
                  <a href="#" data-toggle="dropdown" class="dropdown-toggle user-type" style="font-size:22px">${userName}<b class="caret"></b>
                  <!-- <a href="message/" class="message openInTab <#if !auth.hasPermission('message.message')>hide</#if>" >
	                  <i class="fa fa-envelope-o"></i>
	                  <span class="label label-success" id="message_total"></span>
	               </a> -->
                  </a>
                  <ul class="dropdown-menu   user-actions">
                   
                    <li><a href="javascript:;" class="changeProfile" data-loginid="${userDto.id}" >个人资料</a></li>
                    
                    <li class="divider"></li>
                    
                    <li><a href="javascript:;" class="logout">安全退出</a></li>
                  </ul>
                </div>
              </div> 
             
             
              <ul class="sidebar-menu">
                <!--<li class="header">MAIN NAVIGATION</li>-->
				
				<#if auth.hasPermission("authority")>
                 <li class="treeview">
                    <a href="#">
                        <i class="fa   fa-cubes"></i> 
                        <span>General </span> 
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                   		<#if auth.hasPermission("authority.user")>
                        <li><a href="user/" class="J_menuItem"><i class="fa fa-circle-o"></i>Users Managemt</a></li>
                        </#if>
                        <#if auth.hasPermission("authority.role")>
                        <li><a href="role/" class="J_menuItem"><i class="fa fa-circle-o"></i>Roles Managemt</a></li>
                        </#if>
                    </ul>
				</li>
				</#if>
              </ul>
            </section>
            <!-- /.sidebar -->
          </aside>
          
          <!-- Content Wrapper. Contains page content -->
          <div class="content-wrapper" >
                <div class=" content-tabs">
                    <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                    </button>
                    <nav class="page-tabs J_menuTabs">
                        <div class="page-tabs-content">
                            <a href="javascript:;" class="active J_menuTab" data-id="/admin/web/main/index">Index</a>
                        </div>
                    </nav>
                    <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                    </button>
                    <div class="btn-group roll-nav roll-right">
                        <button class="dropdown J_tabClose" data-toggle="dropdown">Close Opration<span class="caret"></span>

                        </button>
                        <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        	<li class="J_tabShowActive"><a>Locate the current TAB</a>
                            </li>
                        	<li class="divider"></li>
                            <li class="J_tabRefreshActive"><a>Refresh the current TAB</a>
                            </li>
                            <li class="divider"></li>
                            <li class="J_tabCloseAll"><a>Close all tabs</a>
                            </li>
                            <li class="J_tabCloseOther"><a>Close the other TAB</a>
                            </li>
                        </ul>
                    </div>
                    <a  href="javascript:;"   class="roll-nav roll-right logout J_tabExit"><i class="fa fa fa-sign-out"></i> logout</a>
                </div>
                
                <div class=" J_mainContent" id="content-main">
                   
                </div>
                
                <div class="main-footer">
                   
                </div>
      		
                <#include "/newOrderMsg.ftl" />
                
          </div><!-- /.content-wrapper -->
    
      
      
    </div> <!--- wrapper -->
</body>
<script type="text/html" id="mainFooterTpl">
	Copyright &copy;2016 中国幼儿在线·吾儿网   版权所有
</script>
<script type="text/javascript">
/* window.overTimeOrderInterval = ${overTimeOrderInterval};
<#if auth.hasPermission("order.overTime")>
	window.overTimeOrderTips = true;
<#else>
	window.overTimeOrderTips = false;
</#if>

window.newOrderInterval = ${newOrderInterval};
<#if auth.hasPermission("stock.newOrder")>
	window.newOrderTips = true;
<#else>
	window.newOrderTips = false;
</#if> */
</script>


<#include "/footer.ftl" />
<!-- Slimscroll -->
<script src="${uiBase}vendor/slimScroll/jquery.slimscroll.js"></script>
<script src="${uiBase}vendor/jquery-validator/jquery.validate.js" ></script>
<script type="text/javascript" src="${uiBase}vendor/jquery-md5/jquery.md5.js"></script> 

<#if (userDto.orgId>0) >
<script src="${uiBase}vendor/canvas2image/base64.js"></script>
<script src="${uiBase}vendor/canvas2image/canvas2image.js"></script>
  
<script type="text/javascript" src="${uiBase}vendor/jquery.qrcode.min.js"></script>
  
<script id="myQrcodeTpl" type="text/html">
<div class="box-body form-horizontal addEditTpl">
 	<div class="form-group row">
		<label class="col-sm-3 control-label">二维码内容</label>
		<div class="col-sm-8">
		  <p class="form-control-static">
             {{qrText}}
           </p>
			
		</div>
	</div>  
	<div class="form-group row">
		<label class="col-sm-3 control-label">我的二维码</label>
		<div class="col-sm-8">
			<div id="defaultQrcode">
				
			</div>
			<div id="otherQrcode" class="hide">
				
			</div>
			<div>
			    <p class="form-control-static" style="color:#363535">
	            	（二维码边长(厘米)：{{sideWidth}}<br> 建议扫描距离(米)：{{scanWidth}}<br>像素(px)：{{width}} * {{height}}）
	            </p>
				<button type=button  class="btn btn-success dlNormal"><i class="fa fa-download"></i> 下载</button>
				<button type=button  class="btn btn-success moreSizeQrcode"><i class="fa fa-bars"></i> 更多尺寸</button>
			</div>
		</div>
	</div>
	<div class="form-group row hide otherSizeList"  >
		<table class="table" cellspacing="0">
            <thead class="thead">
                <tr>
                    <th class="table_cell">二维码边长(厘米)</th>
                    <th class="table_cell">建议扫描距离(米)</th>
                    <th class="table_cell">像素(px)</th>
                    <th class="table_cell no_extra"></th>
                </tr>
            </thead>
            <tbody class="tbody">
			{{each qrcodes as item i}}
                <tr>
                    <td class="table_cell"> {{item.sideWidth}} </td>
                    <td class="table_cell"> {{item.scanWidth}} </td>
                    <td class="table_cell"> {{item.width}} * {{item.height}} </td>
                    <td class="table_cell"> <a class="btn btn-default downloadBtn" href="javascript:;" data-width="{{item.width}}"  data-height="{{item.height}}"><i class="fa fa-download"></i> 下载</a> </td>
                </tr>
                
			{{/each}}               
                
            </tbody>
        </table>
	</div>
</div>
	
</script>
</#if>

<script id="changeProfileTpl" type="text/html">
{{if dto.status == '启用'}}
<form id="addEditForm" method="post" class="form-horizontal" novalidate="false">
<div class="box-body form-horizontal addEditTpl">
 	  <div class="callout callout-info " >
       
        <p>密码不改请留空．</p>
      </div>
      
    <div class="form-group row">
	  <label class="col-sm-4 control-label"><span class="requiredField">*</span>姓名</label>
	  <div class="col-sm-8">
		<input type="hidden" name="id" value={{dto.id}}>
		 <#if (userDto.orgId>0) >
		  <p class="form-control-static">
		 	{{dto.userName}}
		 	 </p>
		 <#else>
	    <input type="text" class="form-control" name="userName" placeholder="请输入姓名"
        	required data-msg-required="请输入姓名"  value="{{dto.userName}}">
         </#if>
	  </div>
	</div>
	<div class="form-group row">
	  <label class="col-sm-4 control-label" >用户名</label>
	  <div class="col-sm-8">
	  <p class="form-control-static">
		 {{dto.loginId}}
		 </p>
	  </div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label" style="text-align:right;"><span class="requiredField">*</span>联系电话</label>
	  	<div class="col-sm-8">
	  	 <#if (userDto.orgId>0) >
	  	 <p class="form-control-static">
	  	 {{dto.phone}}
	  	  </p>
	  	 <#else>
	  		<input type="text" class="form-control" name="phone" placeholder="请输入联系电话"
	  			required data-msg-required = "请输入联系电话"
	  			data-rule-isPhone="true" value="{{dto.phone}}" >
	  	</#if>		
	  	</div>
	</div>

	<div class="form-group row">
		<label class="col-sm-4 control-label" style="text-align:right;">密码</label>
	  	<div class="col-sm-8">
	  		<input type="password" class="form-control" name="password" id="password"  data-msg-required="请输入密码">
	  	</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label" style="text-align:right;">确认密码</label>
	  	<div class="col-sm-8">
	  		<input type="password" class="form-control" data-rule-equalTo="#password" data-msg-equalTo="密码不一致">
	  	</div>
	</div>
	
	<div class="form-group row">
		<label class="col-sm-4 control-label" style="text-align:right;">角色</label>
		<div class="col-sm-8">
		<p class="form-control-static">
			{{each list}}
				{{$value.role}} 
			{{/each}}
		</p>
		</div>
    </div>	
	<div class="form-group row">
		<label class="col-sm-4 control-label" style="text-align:right;">状态</label>
		<div class="col-sm-3">
		 	<p class="form-control-static">
				{{dto.status}}
			</p>
		</div>
    </div>
</div>
</form>
{{else}}
<p class="form-control-static">
账号被禁用，您无法修改资料．
</p>
{{/if}}
</script>

</html> 
