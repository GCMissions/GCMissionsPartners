<#assign headComponents = ["bootTable"] > <#include "/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/pages/product/do_validate.css?v=${resourceVersion}">
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 服务商平台验证</li>
					<li>商品验证</li>
				</ol>
			</section>
			<div class="col-md-12">
			<input style='display: none' id='productId' value='${productId}'/>
				<div class='box box-primary '>
					<div class='box-body'>
						<div class="col-md-12">
							<div>
								<label style="margin-left: 5%; margin-top: 1%">验证</label>
							</div>
							<div id="productName">
								<label>商品名称：</label>
								<label>${productName}</label>
							</div>
							<div>
								<input id='validate-input' class='form-control'
									placeholder="请输入验证码" />
							</div>
							<div id="btn-group">
								<div class="row">
									<div class="col-md-12">
										<a class="btn btn-primary" id="do-validate">确定</a>
										<button class="btn btn-success backPage" type="button">
											<i class="fa fa-backward"> 返回 </i>
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<#include "/footer.ftl" />
<script type="text/javascript"
	src="${uiBase}js/pages/orderManager/do_validate.js?v=${resourceVersion}"></script>

</html>