<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 
<style>
.datagrid-header-input{
    display: inline-block;
    width: 160px;
}
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
         
		  <ol class="breadcrumb">
			<li><i class="fa fa-dashboard"></i> 终端配送商列表</li>
			<li>终端配送商详情</li>
			 
		  </ol>
  
 		</section>
        
       
        
        <!-- Main content -->
        <div class="row pad">
        <div class="col-md-12">
			<input type="hidden" id="orgCode" value="${orgCode}">
				<div class="nav-tabs-custom">
					<ul class="nav nav-tabs">
                  		<li class="active"><a href="#tab_1" data-toggle="tab">
                  		基本信息</a></li>
                  		<li><a href="#tab_2" data-toggle="tab">
                  		单瓶商品库存</a></li>   
                </ul>
                <div class="tab-content">
                	<div class="tab-pane active" id="tab_1">
	                    <table class="table table-condensed">
	                    	<tr>
	                    		<td><b>配送商编号:</b></td><td>${terminalDetail.orgCode}</td>
	                    		<td><b>配送商名称:</b></td><td>${terminalDetail.orgName}</td>
	                    	</tr>
	                    	<tr>
	                    		<td><b>配送商登录名:</b></td><td>${terminalDetail.loginName}</td>
	                    		<td><b>联系人:</b></td><td>${terminalDetail.contactName}</td>
	                    	</tr>
	                    	<tr>
	                    		<td><b>联系电话:</b></td><td>${terminalDetail.phone}</td>
	                    		<td><b>所在地区:</b></td><td>${terminalDetail.region}</td>
	                    	</tr>
	                    	<tr>
	                    		<td><b>详细地址:</b></td><td>${terminalDetail.address}</td>
	                    	</tr>
	                    </table>
	                    <div class="row">
							<div class="col-md-12">
						<div id="map" style="width:500px; height:300px; margin-left:35px;"></div>
			</div>
		</div>
                  	</div>
                  	<div class="tab-pane" id="tab_2">	        
	                    <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                            <th>单瓶商品编号</th>
                            <th>单瓶商品名称</th>
                            <th>销售价</th>
                            <th>创建时间</th>
                            <th>库存总量(件)</th>
                            <th>安全库存(件)</th>
                            <th>库存状态</th>
                        </thead>
                        <tbody> 	
                        </tbody>
                      </table>
                  	</div>
                </div>
				</div>
			</div>
        </div>
        
        <div class="col-md-12">
        	<div class="col-sm-3 col-sm-offset-5">
                <button class="btn btn-success backPage" type="button"   ><i class="fa fa-backward"> 返回 </i></button> 
			</div>
        </div>
        <input  type="hidden" id="lng" />
        <input  type="hidden" id="lat" />
        <input  type="hidden" id="ng" value="${terminalDetail.longitude}"/>
        <input  type="hidden" id="at" value="${terminalDetail.latitude}"/>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->


</body>
<#include "/footer.ftl" />

<script type="text/javascript" src="${uiBase}/js/pages/regionalPlatform/zstock_detail.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}/js/pages/regionalPlatform/map.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sHLz8MFUdDtLaAj54AETxwxz"></script>
</html>
