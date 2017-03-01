<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />

<style>
.u191_img {
	margin-top: -4px !important;
	width: 31px;
	height: 27px;
}
.ax_image {
	font-family: 'Arial Regular', 'Arial';
	font-weight: 400;
	font-style: normal;
	font-size: 13px;
	color: #000000;
	text-align: center;
	line-height: normal;
	display: inline;
	float: left;
}
.tdLabel {
	width: 120px;
}
.tdContext{
	width: 300px;
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
            <li><i class="fa fa-dashboard"></i> 库存管理</li>
            <li><i class="fa"></i> 库存管理</li>
            <li><i class="fa"></i> 库存管理详情</li>         
       </ol>
       </section>
        
        <!-- Main content -->
        <div class="row pad">
			<input type="hidden" id="stockId" value="${stockId}">
			<div class="box box-primary">
                <div class="box-header with-border">
                <div class="col-md-12">
                <br/><br/>
                
				<div class="nav-tabs-custom">
					<!-- tab 页标题 -->
					<ul class="nav nav-tabs">
                  		<li class="active"><a href="#tab_1" data-toggle="tab">
                  		<h3 class="box-title">单瓶商品信息</h3></a></li>
                  		<li><a href="#tab_2" data-toggle="tab">
                  		<h3 class="box-title">库存信息</h3></a></li>   
               		 </ul>
	               	<!-- 订单信息 -->
	                <div class="tab-content">
	                	<div class="tab-pane active" id="tab_1">
		                   <table class="table table-condensed">
		                    	<tr>
		                    		<td class="tdLabel"><b>单瓶商品编号:</b></td><td class="tdContext">${stockDto.goodCode}</td>
		                    		<td class="tdLabel"><b>单瓶商品名称:</b></td><td class="tdContext">${stockDto.name}</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>安全库存:</b></td><td class="tdContext">${stockDto.safeNum}</td>
		                    		<td class="tdLabel"><b>当前库存:</b></td><td class="tdContext">${stockDto.stockNum}</td>
		                    	</tr>
		                    </table>
	                  	</div>
	                  	<!-- 支付信息 -->
	                  	<div class="tab-pane" id="tab_2">
	                  	     <table id="dataList" class="table table-bordered table-hover" >
	                        <tbody> 
	                        		<tr>
	                        			<td>安全库存：</td>
	                        			<td>${stockDto.safeNum}</td>
	                        			<td>当前库存：</td>
	                        			<td>${stockDto.stockNum}</td>
	                        		</tr>
	                        </tbody>
	                      </table>
	                      
		                     <table id="recordDataList" class="table table-bordered table-hover" >
	                        <thead>
	                            <th>件数</th>
	                            <th>进/出</th>
	                            <th>时间</th>
	                        </thead>
	                        <tbody> 	                        		
	                        		
	                        </tbody>
	                      </table>
	                  	</div>
	                </div>
				</div>
				</div>
				</div>
			</div>
        </div>

			<div class="row">
				<div class="col-md-12 text-center">
					<button class="btn btn-success backPage" type="button">
						<i class="fa fa-backward"> 返回 </i>
					</button>
				</div>
			</div>
			<!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->


</body>
<#include "/footer.ftl" />
<script type="text/javascript" src="${uiBase}/js/pages/distribution/zstock_detail.js"></script>
</html>
