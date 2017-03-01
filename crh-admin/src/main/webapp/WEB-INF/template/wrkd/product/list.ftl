<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/wrkd/product/list.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      	<!-- Content Wrapper. Contains page content -->
    	<div class="content-wrapper page-content-wrapper">
       		<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 商品管理</li>
					<li>商品列表</li>
				</ol>
			</section>

			<!-- Main content -->
	        <div class="main-content">
	        	<div class="box box-primary">
					<div class="box-header with-border">
						<input id="productCode" class="form-control" type="text" maxlength=13 placeholder="商品编号" />
						<input id="productName" class="form-control" type="text" maxlength=50 placeholder="商品名称" />
						<input id="lowPrice" class="form-control" type="text" maxlength=10 placeholder="价格范围"> 
						<span class="time">-</span> 
						<input id="highPrice" class="form-control" type="text" maxlength=10 placeholder="价格范围"> 
						<span id="msg_num_valid">请输入有效的数字</span> 
						<select name="actTags" class="form-control" style="margin-left:10px;width:90px;">
							<option value="">不限</option>
							<#if actTags??>
								<#list actTags as actTag>
									<option value="${actTag_index}">${actTag}</option>
								</#list>
							</#if>
						</select>
						<button type="button" class="btn btn-primary" id="search">
							<i class="fa fa-search"></i> 查询
						</button>
					</div>

					<div class="box-body">
						<div class="row">
							<div class="col-md-12 ">
								<button type="button" class="btn btn-primary pull-right" id="deleteItem" style='margin-right:0px;'><i class="fa fa-remove"></i> 删除</button>
								<label class="pull-right" style='margin-right:20px;'> 
									<a class="btn btn-default" href="${urlPrefix}coolbag/product/page?oper=add">
										<i class="fa fa-plus"></i> 添加商品
									</a>
								</label>
							</div>
						</div>
						<form id="product" web-host="${(wechatHost)!}">
							<table id="dataList" class="table table-bordered table-hover dataList">
								<thead>
									<th><input type="checkbox" id="selectAll" value=""></th>
									<th>商品编号</th>
									<th>商品名称</th>
									<th>价格（元）</th>
									<th>创建时间</th>
									<th>操作</th>
								</thead>
								<tbody>
								</tbody>
							</table>
						</form>
					</div><!-- /.box-body -->
				</div>
        	</div><!-- /.content --> 
        <div class="clearfix"></div>
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
 </body>
<#include "/footer.ftl" /> 
<script type="text/javascript" src="${uiBase}js/pages/wrkd/product/list.js?v=${resourceVersion}"></script> 

</html>
