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
			<li><i class="fa fa-dashboard"></i> 单瓶商品库存列表</li>
			<li>单瓶商品库存详情</li>
			 
		  </ol>
  
 		</section>

        
       
        
        <!-- Main content -->
        <div class="row pad">
        <div class="col-md-12">
			<input type="hidden" id="stockId" value="${stockId}">
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
	                    		<td><b>单瓶商品编号:</b></td><td>${stock.goodCode}</td>
	                    		<td><b>单瓶商品名称:</b></td><td>${stock.name}</td>
	                    	</tr>
	                    	<tr>
	                    		<td><b>销售价:</b></td><td>${stock.price}</td>
	                    		<td><b>创建时间:</b></td><td>${stock.createDate}</td>
	                    	</tr>
	                    	<tr>
	                    		<td><b>安全库存:</b></td><td>${stock.safeNum}</td>
	                    		<td><b>当前库存:</b></td><td>${stock.stockNum}</td>
	                    	</tr>
	                    </table>
                  	</div>
                  	<div class="tab-pane" id="tab_2">
	                    <table class="table table-condensed">
	                    	<tr>
	                    		<td><b>安全库存:</b>&nbsp;&nbsp;&nbsp;${stock.safeNum}</td>
	                    		<td><b>当前库存:</b>&nbsp;&nbsp;&nbsp;${stock.stockNum}</td>
	                    	</tr>
	                    </table>
	                    <table id="dataList" class="table table-bordered table-hover" >
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
        <div class="col-md-12">
        	<div class="col-sm-3 col-sm-offset-5">
                <button class="btn btn-success backPage" type="button"   ><i class="fa fa-backward"> 返回 </i></button> 
			</div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->


</body>
<#include "/footer.ftl" />

<script type="text/javascript" src="${uiBase}/js/pages/regionalPlatform/pstock_detail.js?v=${resourceVersion}"></script>
</html>
