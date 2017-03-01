<#assign headComponents = ["bootTable", "innerPage"] > <#include
"/header.ftl" />
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>内容管理</li>
					<li><#if opration == "1">查看合集<#elseif opration == "2">编辑合集<#else>添加合集</#if></li>
				</ol>
			</section>
			
			<div class="row pad">
				<div class="col-md-12">
				<form role="form" class="form-horizontal" method="post" id="mainForm">
					<div class="nav-tabs-custom">
						<div class="tab-content">
						<input type="hidden" id="opration" value="${opration}">
						<input type="hidden" id="id" value="${intersection.id}">
							<div class="row">
								<div class='col-sm-12' style='margin-bottom: 45px;'>
									<div class='col-sm-1'>
										<label>添加合集</label>
									</div>
								</div>
								<div class='col-sm-8'>
									<div class="col-sm-2"><label style="margin-top: 5px;"><span class="requiredField">*</span>合集名称：</label></div>
									<div class="col-sm-6">
									<#if opration == "1">
									<p class="form-control-static">
										${intersection.name}
									</p>
									<#else>
									<input class="form-control pull-left" id="intersectionName" placeholder="合集名称" style="text-align: center;" value="${intersection.name}" maxlength="30"/>
									</#if>
									</div>
								</div>
								<div class="col-sm-12" style="margin-top: 30px;">
									<div class="col-sm-8">
										<#if opration == "1">
										<p class="form-control-static">${intersection.description}</p>
										<#else>
										<script id="ueEditor-platform" name="description" type="text/plain" style="width: 100%; height: 500px;">${intersection.description}</script>
										</#if>
									</div>
								</div>
								<div class="row col-sm-8" style="margin-top: 40px;">
								<div style="text-align: center">
									<#if opration != "1">
									<button class="btn btn-primary submitMainForm" type="button" id="saveBaseInfo" style="margin-right: 20px;">保存</button>
									</#if>
									<button class="btn btn-primary backPage" type="button" id="backToHome">返回</button>
								</div>
								</div>
							</div>
						</div>
					</div>
					</form>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<!-- /.content-wrapper -->
	</div>
	<!-- ./wrapper -->
</body>
<#include "/footer.ftl" />
<script type="text/javascript">
	
</script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.js"></script>
<script
	src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script
	src="${uiBase}/js/pages/intersection/intersection_add.js?v=${resourceVersion}"></script>
</html>
