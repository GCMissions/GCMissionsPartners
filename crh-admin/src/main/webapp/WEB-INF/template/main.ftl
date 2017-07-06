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
            <a href="javascript:;" class="logo">
              <span class="logo-lg"><img src="${uiBase}img/Church_logo.png" title="Church" width=150 ></span>
            </a>
            
            <nav class="navbar navbar-static-top" role="navigation">
              <!-- Sidebar toggle button-->
              <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
              </a>
              <div class="navbar-custom-menu"> 
                <ul class="nav navbar-nav">
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
                    <li><a href="javascript:;" class="logout">Safety Exit</a></li>
                  </ul>
                </div>
              </div> 
             
             
              <ul class="sidebar-menu">
                <!--<li class="header">MAIN NAVIGATION</li>-->
				
				<#if auth.hasPermission("authority")>
                 <li class="treeview">
                    <a href="#">
                        <img src="${uiBase}img/generral1-2.png">
                        <span>General </span> 
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                   		<#if auth.hasPermission("authority.user")>
                        <li><a href="user/" class="J_menuItem"><i class="fa fa-circle-o"></i>Users Management</a></li>
                        </#if>
                        <#if auth.hasPermission("authority.role")>
                        <li><a href="role/" class="J_menuItem"><i class="fa fa-circle-o"></i>Roles Management</a></li>
                        </#if>
                    </ul>
				</li>
				</#if>
				
				<#if auth.hasPermission("slides")>
                 <li class="treeview">
                    <a href="#">
                        <img src="${uiBase}img/partner-slides-2.png">
                        <span>Partner Slides </span> 
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                   		<#if auth.hasPermission("slides.show")>
                        <li><a href="slides/" class="J_menuItem"><i class="fa fa-circle-o"></i>Slides Management</a></li>
                        </#if>
                    </ul>
				</li>
				</#if>
				
				<#if auth.hasPermission("partners")>
                 <li class="treeview">
                    <a href="#">
                        <img src="${uiBase}img/partner-2.png"> 
                        <span>Partners </span> 
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                   		<#if auth.hasPermission("partners.show")>
                        <li><a href="partner/" class="J_menuItem"><i class="fa fa-circle-o"></i>Partners Management</a></li>
                        </#if>
                    </ul>
				</li>
				</#if>
				
				<#if auth.hasPermission("resource")>
                 <li class="treeview">
                    <a href="#">
                        <img src="${uiBase}img/resourse-2.png">  
                        <span>Resource </span> 
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                   		<#if auth.hasPermission("resource.show")>
                        <li><a href="resource/" class="J_menuItem"><i class="fa fa-circle-o"></i>Resource Management</a></li>
                        </#if>
                    </ul>
				</li>
				</#if>
				
				<#if auth.hasPermission("region")>
                 <li class="treeview">
                    <a href="#">
                        <img src="${uiBase}img/region2.png">  
                        <span>Region </span> 
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                   		<#if auth.hasPermission("region.show")>
                        <li><a href="region/" class="J_menuItem"><i class="fa fa-circle-o"></i>Region Management</a></li>
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
  
</#if>

<script id="changeProfileTpl" type="text/html">
{{if dto.status == 'Enable'}}
<form id="addEditForm" method="post" class="form-horizontal" novalidate="false">
<div class="box-body form-horizontal addEditTpl">
 	  <div class="callout callout-info " >
       
        <p>Password is not to leave blank.</p>
      </div>
      
	<div class="form-group row">
	  <label class="col-sm-4 control-label" >User Name</label>
	  <div class="col-sm-8">
	  <p class="form-control-static">
		 {{dto.loginId}}
		 </p>
	  </div>
	</div>

	<div class="form-group row">
		<label class="col-sm-4 control-label" style="text-align:right;">Password</label>
	  	<div class="col-sm-8">
	  		<input type="password" class="form-control" name="password" id="password"  data-msg-required="Password">
	  	</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label" style="text-align:right;">Confirm Password</label>
	  	<div class="col-sm-8">
	  		<input type="password" class="form-control" data-rule-equalTo="#password" data-msg-equalTo="Passwords don't match">
	  	</div>
	</div>
	
	<div class="form-group row">
		<label class="col-sm-4 control-label" style="text-align:right;">Role</label>
		<div class="col-sm-8">
		<p class="form-control-static">
			{{each list}}
				{{$value.role}} 
			{{/each}}
		</p>
		</div>
    </div>	
	<div class="form-group row">
		<label class="col-sm-4 control-label" style="text-align:right;">Status</label>
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
The account is disabled, you cannot modify data.
</p>
{{/if}}
</script>

</html> 
