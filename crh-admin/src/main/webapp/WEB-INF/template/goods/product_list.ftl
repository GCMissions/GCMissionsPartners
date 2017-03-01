<#assign headComponents = ["bootTable"] > <#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}vendor/zTree/css/zTreeStyle.css">
<link rel="stylesheet"
	href="${uiBase}vendor/treeTable/css/jquery.treetable.css">
<link rel="stylesheet"
	href="${uiBase}css/ztree_dropdown.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">

				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 商品管理</li>
					<li class="active">查看单瓶商品</li>
				</ol>

			</section>

			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="box box-primary ">

						<input id="goodsId" type="hidden" value="${goodsId}">


						<div class="box-header with-border">
							<div class="row col-md-12">
								</br>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="col-sm-3">
										<label class="control-label">单瓶商品编号:${goodsDto.goodCode}</label>
									</div>
									<div class="col-sm-3">
										<label class="control-label">单瓶商品名称:${goodsDto.goodName}</label>
									</div>
									<div class="col-sm-2">
										<label class="control-label">规格: ${goodsDto.specs}</label>
									</div>
								</div>
							</div>
							<div class="row col-md-12">
								</br>
							</div>
						</div>
						<!-- /.box-header -->

						<div class="box-body">
							<table id="dataList"
								class="table table-bordered table-hover dataList">
								<thead>

									<th>序号</th>
									<th>商品条码</th>
									<th>商品名称</th>
									<th>分类</th>
									<th>品牌</th>
									<th>瓶数</th>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<!-- /.box-body -->

					</div>
				</div>
			</div>

			<div class="col-sm-3 col-sm-offset-5">
				<button class="btn  btn-success" id="back">
					<i class="fa fa-backward"></i> 返 回
				</button>
			</div>
			<!-- /.content -->
			<div class="clearfix"></div>

		</div>
		<!-- /.content-wrapper -->
	</div>
	<!-- ./wrapper -->

	<#include "/footer.ftl" />
	<script type="text/javascript"
		src="${uiBase}vendor/zTree/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript"
		src="${uiBase}vendor/zTree/js/jquery.ztree.exedit.js"></script>
	<script type="text/javascript"
		src="${uiBase}vendor/zTree/js/jquery.ztree.excheck.js"></script>
	<script type="text/javascript"
		src="${uiBase}js/pages/goods/product_list.js?v=${resourceVersion}"></script>
	<script>
		$(function() {
			productApp.init();
		});
	</script>
</body>
</html>