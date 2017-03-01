<#assign headComponents = ["bootTable"] > <#include "/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/pages/product.css?v=${resourceVersion}">
<style>
caption>span {
	margin-right: 30px;
}

.modal-content {
	width: 300px !important;
	margin-left: 0px !important;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<div class="content-wrapper page-content-wrapper">
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>报表管理</li>
					<li class="active">报表列表</li>
				</ol>
			</section>

			<div class="row pad">
				<div class="col-md-12">
					<!-- Custom Tabs -->
					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_1" data-toggle="tab"
								data-index="0">财务报表</a></li>
							<!-- 							<li><a href="#tab_2" data-toggle="tab" data-index="1">平台邮寄</a></li> -->
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<div class="form-horizontal search-group" id="search-area">
									<div class="box-body">
										<form id="orderForm1" method="get"
											action="${base}/web/zfinancialStatement/export">
											<div class="form-group">
												<label class="control-label col-sm-2" for="type-select">订单编号：</label>
												<input type="text" class="form-control col-sm-3"
													name="orderId" id="orderId1"> <label
													class="control-label col-sm-2" for="type-select">下单时间：</label>
												<div class="col-sm-6 no-padding timegroup">
													<input type="hidden" data-ignore="true" name="stDate"
														id="stDate" value="" />
													<div class="dateDiv" style="margin-bottom: 0px;">
														<input size="10" type="text" name="sDate" id="csDate1"
															class="form-control keyword beginDate"
															placeholder="请选择时间" readonly> <span
															class="add-on" style="display: none"><i
															class="icon-remove"></i></span> <span class="add-on"><i
															class="icon-calendar"></i></span>
													</div>
													<span class="time">至</span> <input type="hidden"
														data-ignore="true" name="edDate" id="edDate" value="" />
													<div class="dateDiv" style="margin-bottom: 0px;">
														<input size="10" type="text" name="eDate" id="ceDate1"
															class="form-control keyword endDate" placeholder="请选择时间"
															readonly> <span class="add-on"
															style="display: none"><i class="icon-remove"></i></span>
														<span class="add-on"><i class="icon-calendar"></i></span>
													</div>
												</div>
											</div>
										</form>
										<div class="form-group ">
											<label class="col-sm-2 control-label pull-left">
												<button type="button" class="btn btn-primary pull-right"
													id="refreshRecord1">
													<i class="fa fa-search"></i>开始搜索
												</button>
											</label> <label class="col-sm-1 control-label pull-left">
												<button type="button" class="btn btn-default reloadPage">
													<i class="fa  fa-refresh"></i> 刷新
												</button>
											</label> <label class="col-sm-1 control-label pull-left">
												<button type="button" class="btn btn-default" id="excel1"
													data-excelType="1">
													<i class="fa  fa-refresh"></i> 导出
												</button>
											</label>
										</div>
									</div>
								</div>
								<div class="box-body">
									<table id="dataList1" class="table table-bordered table-hover">
										<caption>
											订单总额：<span id="totalAmount1"></span> 配送费总分利：<span
												id="zTotalShipProfit"></span> 商品总分利：<span
												id="zTotalProProfit"></span> 合计分利：<span id="zTotalSPProfit"></span>
										</caption>
										<thead>
											<th>序号</th>
											<th>订单编号</th>
											<th>下单时间</th>
											<th>订单金额(元)</th>
											<th>配送费分利(元)</th>
											<th>商品分利(元)</th>
											<th>操作</th>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>

						</div>
						<!-- /.tab-content -->
					</div>
				</div>
			</div>

			<div class="clearfix"></div>
		</div>

		<!-- /.row -->

	</div>
	<!-- ./wrapper -->

</body>
<#include "/footer.ftl" />
<script type="text/javascript"
	src="${uiBase}js/pages/zfinancialManage/zfinancialStatement_list.js?v=${resourceVersion}"></script>
</html>
