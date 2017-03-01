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
                <#if auth.hasPermission("product")>
                <li class="treeview">
                  <a href="#">
                    <i class="fa fa-th"></i> 
                    <span>商品管理</span> 
                    <i class="fa fa-angle-left pull-right"></i>
                  </a>
                  <ul class="treeview-menu">
                  		<#if auth.hasPermission("product.product")>
                        <li><a href="activity/" class="J_menuItem"><i class="fa fa-circle-o"></i>商品列表</a></li>
                        </#if>
                        <#if auth.hasPermission("product.productshief")>
                        <li><a href="productShief/" class="J_menuItem"><i class="fa fa-circle-o"></i>商品上下架</a></li>
						</#if>
                        <#if auth.hasPermission("product.category")>
                        <li><a href="category/" class="J_menuItem"><i class="fa fa-circle-o"></i>商品品类管理</a></li>
                        </#if>
                  </ul>
                </li>
                </#if>

				  
				 
				<#if auth.hasPermission("seller")> 
                <li class="treeview">
                    <a href="#"> <i class="fa  fa-server"></i><span>服务商管理</span> <i class="fa fa-angle-left pull-right"></i> </a>
                    <ul class="treeview-menu">
                   		<#if auth.hasPermission("seller.supplier")> 
                        <li><a href="supplier/" class="J_menuItem"><i class="fa fa-circle-o"></i>服务商管理</a></li>
                        </#if>
                    </ul>
				</li>
                 </#if>
                 
                <#if auth.hasPermission("validate")>
					<li class="treeview">
			            <a href="#"> <i class="fa  fa-bank"></i><span>服务商平台验证</span> <i class="fa fa-angle-left pull-right"></i> </a>
			            <ul class="treeview-menu">
			            	<li><a href="orgOrder/" class="J_menuItem"><i class="fa fa-circle-o"></i>订单查询</a></li>
			                <li><a href="validate/" class="J_menuItem"><i class="fa fa-circle-o"></i>商品验证</a></li>
			            </ul>
					</li> 
				</#if>   
                 
                <#if auth.hasPermission("marketing")>  
                <li class="treeview">
                    <a href="#">
                        <i class="fa  fa-map-signs"></i> 
                        <span>营销管理</span> 
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                    	<#if auth.hasPermission("marketing.coupon")>  
                        <li><a href="coupon/" class="J_menuItem"><i class="fa fa-circle-o"></i>优惠券管理</a></li>
                         </#if>
                         <#if auth.hasPermission("product.bargain")>  
                        <li><a href="bargain/" class="J_menuItem"><i class="fa fa-circle-o"></i>砍价管理</a></li>
                         </#if>
                    </ul>
				</li>
				 </#if>
				 
                <#if auth.hasPermission("order")>
                <li class="treeview">
                    <a href="#">
                        <i class="fa  fa-credit-card"></i> 
                        <span>订单管理 </span> 
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                    	<#if auth.hasPermission("order.ordeManager")>
                        <li><a href="order/" class="J_menuItem"><i class="fa fa-circle-o"></i>订单管理</a></li>
                        </#if>
                    </ul>
				</li>
                </#if>
                
				<#if auth.hasPermission("advertise")>
				<li class="treeview">
	                <a href="#"> <i class="fa  fa-cube"></i><span>广告管理</span> <i class="fa fa-angle-left pull-right"></i> </a>
	                <ul class="treeview-menu">
	                    <li><a href="advertise/" class="J_menuItem"><i class="fa fa-circle-o"></i>广告管理</a></li>
	                </ul>
				</li> 
				</#if>
				
				<#if auth.hasPermission("context")>
				<li class="treeview">
	                <a href="#"> <i class="fa   fa-facebook-official"></i><span>内容管理</span> <i class="fa fa-angle-left pull-right"></i> </a>
	                <ul class="treeview-menu">
	                	<#if auth.hasPermission("context.manager")>
	                    <li><a href="context/" class="J_menuItem"><i class="fa fa-circle-o"></i>内容管理</a></li>
	                    </#if>
	                    <#if auth.hasPermission("context.intersection")>
	                    <li><a href="intersection/" class="J_menuItem"><i class="fa fa-circle-o"></i>合集管理</a></li>
	                    </#if>
	                </ul>
				</li>
				</#if>
				
				<#if auth.hasPermission("wrkd")>  
		            <li class="treeview">
		                <a href="#"> <i class="fa  fa-graduation-cap"></i><span>吾儿酷袋</span> <i class="fa fa-angle-left pull-right"></i> </a>
		                <ul class="treeview-menu">
		                    <li><a href="coolbag/" class="J_menuItem"><i class="fa fa-circle-o"></i>吾儿酷袋管理</a></li>
		                </ul>
					</li>    
				</#if>
				
				<#if auth.hasPermission("kdProduct")>
                <li class="treeview">
                  <a href="#">
                    <i class="fa fa-th"></i> 
                    <span>酷袋商品管理</span> 
                    <i class="fa fa-angle-left pull-right"></i>
                  </a>
                  <ul class="treeview-menu">
                  		<#if auth.hasPermission("kd.product")>
                        <li><a href="coolbag/product/" class="J_menuItem"><i class="fa fa-circle-o"></i>商品列表</a></li>
                        </#if>
                        <#if auth.hasPermission("kd.productshief")>
                        <li><a href="coolbag/product/sale/" class="J_menuItem"><i class="fa fa-circle-o"></i>商品上下架</a></li>
						</#if>
                  </ul>
                </li>
                </#if>
                
                <#if auth.hasPermission("kdOrder")>
                <li class="treeview">
                    <a href="#">
                        <i class="fa  fa-credit-card"></i> 
                        <span>酷袋订单管理 </span> 
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                    	<#if auth.hasPermission("kdOrder.ordeManager")>
                        <li><a href="kdOrder/" class="J_menuItem"><i class="fa fa-circle-o"></i>订单管理</a></li>
                        </#if>
                    </ul>
				</li>
                </#if>
                
                <#if auth.hasPermission("kdAdvertise")>
                <li class="treeview">
                    <a href="#">
                        <i class="fa  fa-credit-card"></i> 
                        <span>酷袋广告位管理 </span> 
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                    	<#if auth.hasPermission("kdAdvertise.advertiseManager")>
                        <li><a href="kdAdvertise/" class="J_menuItem"><i class="fa fa-circle-o"></i>广告位管理</a></li>
                        </#if>
                    </ul>
				</li>
                </#if>
                
                <#if auth.hasPermission("appHotAd")>
					<li class="treeview">
		                <a href="#"> <i class="fa  fa-cube"></i><span>APP管理</span> <i class="fa fa-angle-left pull-right"></i> </a>
		                <ul class="treeview-menu">
		                	<#if auth.hasPermission("appHotAd.indexManager")>
		                    	<li><a href="appHotAd/" class="J_menuItem"><i class="fa fa-circle-o"></i>首页管理</a></li>
		                    </#if>
		                    <!-- <#if auth.hasPermission("app.startup")>
		                    	<li><a href="appStartupHomepage/" class="J_menuItem"><i class="fa fa-circle-o"></i>启动页管理</a></li>
		                    </#if> -->
		                </ul>
					</li>
                </#if>
                
                <#if auth.hasPermission("kdActivity")>  
                <li class="treeview">
                    <a href="#">
                        <i class="fa  fa-map-signs"></i> 
                        <span>酷袋专享管理</span> 
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                    	<#if auth.hasPermission("kd.gp")>  
                        	<li><a href="groupPurchase/" class="J_menuItem"><i class="fa fa-circle-o"></i>团购管理</a></li>
                        </#if>
                        <#if auth.hasPermission("kd.bargain")>  
                        	<li><a href="kdBargain/" class="J_menuItem"><i class="fa fa-circle-o"></i>24小时管理</a></li>
                        </#if>
                        <#if auth.hasPermission("kd.pWelfare")>  
                        	<li><a href="coolbag/charity/" class="J_menuItem"><i class="fa fa-circle-o"></i>公益管理</a></li>
                        </#if>
                    </ul>
				</li>
				 </#if>
				
				<#if auth.hasPermission("authority")>
                 <li class="treeview">
                    <a href="#">
                        <i class="fa   fa-cubes"></i> 
                        <span>权限管理 </span> 
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                   		<#if auth.hasPermission("authority.user")>
                        <li><a href="user/" class="J_menuItem"><i class="fa fa-circle-o"></i>用户管理</a></li>
                        </#if>
                        <#if auth.hasPermission("authority.role")>
                        <li><a href="role/" class="J_menuItem"><i class="fa fa-circle-o"></i>角色管理</a></li>
                        </#if>
                    </ul>
				</li>
				</#if>
				
				<#if auth.hasPermission("member")>
					<li class="treeview">
		                <a href="#"> <i class="fa  fa-cube"></i><span>平台用户管理</span> <i class="fa fa-angle-left pull-right"></i> </a>
		                <ul class="treeview-menu">
		                	<#if auth.hasPermission("member.manager")>
		                    	<li><a href="member/" class="J_menuItem"><i class="fa fa-circle-o"></i>平台用户管理</a></li>
		                    </#if>
		                </ul>
					</li>
                </#if>
				
				<#if auth.hasPermission("setting")>
                 <li class="treeview">
                    <a href="#">
                        <i class="fa   fa-gears"></i> 
                        <span>系统设置 </span> 
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                   		<!-- <#if auth.hasPermission("setting.regionCity")>
                        <li><a href="regionCity/" class="J_menuItem"><i class="fa fa-circle-o "></i>开放城市管理</a></li>
                        </#if> -->
                        <#if auth.hasPermission("setting.messageModel")>
                        <li><a href="messageModel/" class="J_menuItem"><i class="fa fa-circle-o"></i>消息模板</a></li>
                        </#if>
                        <!-- <#if auth.hasPermission("setting.paraSetting")>
                        <li><a href="paraSetting/" class="J_menuItem"><i class="fa fa-circle-o "></i>参数设置</a></li>
                        </#if> -->
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
                            <a href="javascript:;" class="active J_menuTab" data-id="/admin/web/main/index">首页</a>
                        </div>
                    </nav>
                    <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                    </button>
                    <div class="btn-group roll-nav roll-right">
                        <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                        </button>
                        <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        	<li class="J_tabShowActive"><a>定位当前选项卡</a>
                            </li>
                        	<li class="divider"></li>
                            <li class="J_tabRefreshActive"><a>刷新当前选项卡</a>
                            </li>
                            <li class="divider"></li>
                            <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                            </li>
                            <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                            </li>
                        </ul>
                    </div>
                    <a  href="javascript:;"   class="roll-nav roll-right logout J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
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
