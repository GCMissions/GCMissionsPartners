<#assign headComponents = ["bootTable", "innerPage"] > <#include
"/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/list.css?v=${resourceVersion}">
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>库存管理</li>
					<li>单瓶商品库存管理</li>
				</ol>
			</section>



			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="box box-primary">
						<div class="form-horizontal search-group" id="search-area">
							<div class="box-body">
								<div class="form-group ">
									<label class="control-label col-sm-2" for="type-select">单瓶商品名称：</label>
									<input id="goodsName" class="form-control col-sm-3" type="text">
									<label class="control-label col-sm-2" for="type-select">单瓶商品编号：</label>
									<input id="goodsCode" class="form-control col-sm-3" type="text">
									<label class="control-label col-sm-2" for="type-select">状态：</label>
									<div class="col-sm-2 no-padding">
										<select class="form-control element" id="status">
											<option value="">全部</option>
											<option value="0">正常</option>
											<option value="1">预警</option>
										</select>
									</div>
								</div>
								<div class="form-group ">
									<label class="control-label col-sm-2" for="type-select">库存总量：</label>
									<div class="col-sm-6 no-padding timegroup">
										<div class="dateDiv" style="margin-bottom: 0px;">
											<input type="text" value="" id="stockLow"
												class="form-control">
										</div>
										<span class="time">至</span>
										<div class="dateDiv" style="margin-bottom: 0px;">
											<input type="text" value="" id="stockHigh"
												class="form-control">
										</div>
									</div>
								</div>
								<div class="form-group ">
									<label class="col-sm-2 control-label">
										<button type="button" id="search"
											class="btn btn-primary pull-right">
											<i class="fa fa-search"></i>开始搜索
										</button>
									</label> <label class="col-sm-2 control-label">
										<button type="button" class="btn btn-default reloadPage">
											<i class="fa  fa-refresh"></i> 刷新
										</button>
									</label> <label class="col-sm-2 control-label">
										<button class="btn  btn-normal btn-danger" id="warnStock">
											<i class="fa fa-exclamation"></i> 库存预警(${waringCount})
										</button>
									</label>
								</div>
							</div>
						</div>
						<!-- /.box-header -->


						<div class="box-body">
							<table id="dataList" class="table table-bordered table-hover">
								<thead>
									<th>序号</th>
									<th>单瓶商品编号</th>
									<th>单瓶商品名称</th>
									<th>销售价</th>
									<th>库存总量</th>
									<th>安全库存</th>
									<th>库存状态</th>
									<th>操作</th>
								</thead>
								<tbody>

								</tbody>
							</table>
						</div>
						<!-- /.box-body -->


					</div>
				</div>
			</div>
			<!-- /.content -->
			<div class="clearfix"></div>

		</div>
		<!-- /.content-wrapper -->
	</div>
	<!-- ./wrapper -->


</body>
<#include "/footer.ftl" />
<style>
.datagrid-header-input {
	display: inline-block;
	width: 160px;
}
</style>
<script type="text/javascript"
	src="${uiBase}/js/pages/regionalPlatform/pStock_list.js?v=${resourceVersion}"></script>
</html>
