<#assign headComponents = ["bootTable"] > <#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 订单查看</li>
					<li>订单详情</li>
				</ol>
			</section>
			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="box box-primary">
						<div class="box-header with-border">
							<!-- 订单信息 -->
							<div class="box-body">
								<table class="table table-bordered table-hover">
									<thead>
										<th>序号</th>
										<th>商品条码</th>
										<th>商品名称</th>
										<th>品牌</th>
										<th>分类</th>
										<th>数量（件）</th>
										<th>瓶数</th>
										<th>优惠劵金额(元)</th>
										<th>销售额(元)</th>
									</thead>
									<tbody>
										<#if info.detail?? > 
										<#list info.detail as detail>
										<tr>
											<td>${detail_index+1}</td>
											<td>${detail.productCode}</td>
											<td>${detail.productName}</td>
											<td>${detail.brandName}</td>
											<td>${detail.cateName}</td>
											<td>${detail.num}</td>
											<td>${detail.bottles}</td>
											<td>￥${((detail.couponAmount!0)/100)?string('0.00')}</td>
											<td>￥${((detail.value!0)/100)?string('0.00')}</td>
										</tr>
										</#list> </#if>
									</tbody>
								</table>
							</div>
							<!-- 商品信息 end -->
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-12">
				<div class="col-sm-3 col-sm-offset-5">
					<button class="btn btn-success backPage" type="button">
						<i class="fa fa-backward"> 返回 </i>
					</button>
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
</html>
