<#assign headComponents = ["bootTable", "innerPage"] > <#include
"/header.ftl" />
<style>
.excel-icon{
background: url(${uiBase}img/excel-icon.png) 0 0 transparent no-repeat;
position: absolute;
height: 13px;
width: 13px;
line-height: 13px;
top: 21px;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>砍价管理</li>
					<li>砍价商品统计详情</li>
				</ol>
			</section>

			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="nav-tabs-custom">
						<div class="tab-content">
							<form id="rpForm" method="get"
								action="${base}/web/bargain/export">
								<div class="tab-pane">
									<div style="margin-bottom: 20px;">
										<div style="line-height: 30px;" class="form-group">
											<input type="text" class="hide" name="bargainId"
												value="${bargainDto.id}" id="bargainId"> <input
												type="text" class="hide" name="productName"
												value="${bargainDto.productName}" id="productName">
											<label style="margin-right:30px;"> 商品名称：${bargainDto.productName}</label>
											<button type="button" class="btn btn-default" id="export"
												data-excelType="1">
												<i class="excel-icon"></i><span style="margin-left: 15px;">导出
											</button>
										</div>
										<table id="dataList" class="table table-bordered table-hover">
										</table>
									</div>
									<div class="col-md-12" style="margin-top: 20px;">
										<div class="col-sm-3 col-sm-offset-5">
											<button class="btn btn-success backPage" type="button">
												<i class="fa fa-backward"> 返回 </i>
											</button>
										</div>
									</div>
								</div>
							</form>
						</div>
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
<script type="text/javascript"
	src="${uiBase}/js/pages/bargain/bargain_chart.js?v=${resourceVersion}"></script>
</html>
