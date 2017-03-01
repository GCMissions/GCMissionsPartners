<#assign headComponents = ["bootTable"] > <#include "/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/pages/product.css?v=${resourceVersion}">
<style>
.mt20>p {
	color: #999999;
}

.goodspic-list ul {
	padding-left: 1px;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<div class="content-wrapper page-content-wrapper">
			<section class="content-header">

				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>商品管理</li>
					<li class="active">商品信息</li>
				</ol>
			</section>
			<div class="row pad">
				<div class="col-md-12">
					<!-- Custom Tabs -->
					<form role="form" class="form-horizontal" method=post id="mainForm">
						<div class="nav-tabs-custom">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tab_1" data-toggle="tab">商品基本信息</a></li>
								<li><a href="#tab_2" data-toggle="tab">商品详情介绍</a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="tab_1">
									<div class="box-body">
										<!-- text input -->
										<div class="form-group">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>商品品类:</label>
											<div class="col-sm-4">
												<select name="firstCategory"
													class="form-control selectpicker">
													<option value="0">不限</option>
													<option value="1">大小套餐</option>
												</select> - <select name="secondCategory"
													class="form-control selectpicker">
													<option value="0">不限</option>
													<option value="1">a套餐</option>
												</select>
											</div>
										</div>
										<div class="form-group ">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>商品名称:</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" name="productName"
													maxlength=50 placeholder="商品名称">
											</div>
											<div class="col-sm-4"></div>
										</div>
										<div class="form-group  ">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>服务商:</label>
											<div class="col-sm-5">
												<select name="serviceId" class="form-control">
													<option value="0">不限</option>
													<option value="1">小小智慧树</option>
												</select>
											</div>
										</div>
										<div class="form-group  ">
											<label class="col-sm-2 control-label">商品说明：</label>
											<div class="col-sm-5">
												<textarea name="note" cols=220 rows=2 class="form-control"
													maxlength="100" style="width: 400px" placeholder="商品简单介绍"></textarea>
											</div>
										</div>
										<div class="form-group  ">
											<label class="col-sm-2 control-label">退货退款说明：</label>
											<div class="col-sm-5">
												<textarea name="refundsDescription" cols=220 rows=5
													class="form-control" style="width: 400px" placeholder="添加关于该商品退货退款的相关说明文案"></textarea>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>商品价格(元)：</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" name="price"
													maxlength=10 id="price" placeholder="价格">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">原价(元)：</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" name="orginPrice"
													maxlength=10 id="orginPrice" placeholder="原价">
											</div>
										</div>
										<div class="form-group"><#include
											"/product/product_pics_list.ftl" /></div>
									</div>
								</div>
								<!-- /.tab-pane -->
								<div class="tab-pane" id="tab_2">
									<div class="box-body">
										<div class="form-group">
											<div class="col-sm-10">
												<script id="ueEditor" name="introduction" type="text/plain"
													style="width: 100%; height: 500px;"></script>
											</div>
										</div>
									</div>
								</div>
								<!-- /.tab-pane -->

							</div>
							<!-- /.tab-content -->
						</div>

						<!-- nav-tabs-custom -->
						<div class="row">
							<div class="col-sm-12 text-center">
								<#if !isReview>
								<button class="btn btn-primary submitMainForm" type="button">保存</button>
								</#if>
								<button class="btn btn-success backPage" type="button">
									<i class="fa fa-backward"> 返回 </i>
								</button>
								<button class="btn btn-success" id="copy-product" type="button">
									 复制已有商品信息
								</button>
							</div>
						</div>
				</div>
				</form>
			</div>

			<div class="clearfix"></div>
		</div>

		<!-- /.row -->

	</div>
	<!-- ./wrapper -->

</body>
<#include "/footer.ftl" />
<script>
	var imagesList = [];
	<#list productDto.listImages as image>
	imagesList.push({
		imageUrl : "${image.imageUrl}",
		imageId : "${image.imageId}"
	});
	</#list>
	var productImage = "${productDto.image}";
	$(function() {
		var productId = '${productDto.productId}' || 0;
		var productInstance = productApp.init(productId, imagesList);
		<#if isReview>
		productInstance.convertToReviewPage();
		</#if>
	});
</script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.js"></script>
<script
	src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script type="text/javascript"
	src="${uiBase}js/pages/product/product_add.js?v=${resourceVersion}"></script>
<script type="text/javascript"
	src="${uiBase}vendor/ajaxfileupload/ajaxfileupload.js?v=${resourceVersion}"></script>
</html>
