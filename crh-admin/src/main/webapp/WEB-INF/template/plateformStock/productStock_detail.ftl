<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 

<link rel="stylesheet" href="${uiBase}/vendor/treeTable/css/jquery.treetable.css">
<link rel="stylesheet" href="${uiBase}/vendor/treeTable/css/jquery.treetable.theme.default.css">
<link rel="stylesheet" href="${uiBase}/vendor/treeTable/css/screen.css">
<style>
.datagrid-header-input{
    display: inline-block;
    width: 160px;
}
.stockHeader{
	margin-bottom: 5px; 
	background-color: #f9f9f9;
	padding-top: 10px;
  	padding-bottom: 5px;
}
table.treetable,table.treetable thead{
	font-family: Helvetica, Arial, sans-serif;
	font-size:14px;
	border: none;
}
table.treetable{
	width: 50%;
	line-height: 30px;
	margin-left: 8px;
}
table.treetable thead tr th{
	border:none;
	background-color: #f9f9f9;
}
table.treetable tr.branch{
	background-color: white;
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
            <li><i class="fa  fa-building-o"></i> 库存管理</li>
            <li> 单瓶商品库存查看</li> 
          </ol>
          
         </section>
        
        <!-- Main content -->
        <div class="row pad">
        	<div class="col-md-12">
				<input type="hidden" id="orgCode" value="${orgCode}">
				<div class="box box-primary">
				
	                <div class="box-header with-border">
	                <div class="row col-md-12"></br></div>
	                    <div class="row">
	                    	<div class="col-md-12">
	                    		<div class="col-sm-2">                   
	                            	<label class="control-label">单瓶商品编码: ${productStockDetailDto.goodsCode}</label>
								</div>
								<div class="col-sm-2">
	                            	<label class="control-label">单瓶商品名称: ${productStockDetailDto.goodsName}</label>
								</div>
								<div class="col-sm-2">
									<label class="control-label">销售价: ${productStockDetailDto.price}</label>
								</div>
								<div class="col-sm-2">
									<label class="control-label">库存总量: ${productStockDetailDto.stockNum}</label>
								</div>
	                    	</div>
	                    </div>
	               <div class="row col-md-12"></br></div>
	               </div><!-- /.box-header -->
	               
	               <div class="row col-md-12"></br></div>
	               
	               <div class="box-body">
	               		<div class="row col-md-12">
	               		
	               		<!-- treeTable -->
	               			<table id="tree_table" class="tree_table treetable">
	               				<thead class=" stockHeader">
	               					<tr>
	               						<th>商家名称</th>
	               						<th>当前库存量</th>
	               					</tr>
	               				</thead>
	               				<tbody id="tree_body">
	               				<#list productStockDetailDto.treeList as pStock>
	               					<tr data-tt-id=${pStock.orgId}><!--区域平台商-->
	               						<td>${pStock.orgName}</td>
	               						<td>${pStock.stockNum}</td>
	               					</tr>
	               						<#list pStock.childList as zStock>
	               						<tr data-tt-id=${zStock.orgId} data-tt-parent-id=${pStock.orgId}><!--终端配送商-->
	               							<td>${zStock.orgName}</td>
	               							<td>${zStock.stockNum}</td>
	               						</tr>
	               						</#list>
	               				</#list>
	               				</tbody>
	               			</table>
	               		<!--/treeTable-->
	               		</div>
	              </div><!-- /.box-body --> 
				</div>
			</div>
        </div>
        <div class="col-md-12">
        	<div class="col-sm-3 col-sm-offset-5">
                <button class="btn  btn-success" id="back"><i class="fa fa-backward"></i> 返  回</button>
			</div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->


</body>
<#include "/footer.ftl" />
<script src="${uiBase}vendor/treeTable/js/jquery.treetable.js?v=${resourceVersion}"></script>
<script>
$(function() {
	$('#back').on("click", function() {
		window.location.href="../../productStock/";
	});
	$("#tree_table").treetable({
		expandable : true
	});
	$("#tree_table").treetable(
		"expandAll");
});
</script>
</html>
