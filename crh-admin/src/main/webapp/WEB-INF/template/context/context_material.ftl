<#assign headComponents = ["bootTable", "innerPage"] > <#include
"/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/pages/context/context_material.css?v=${resourceVersion}">
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapimg"><img src="" /></div>
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>内容管理</li>
					<li>内容管理</li>
				</ol>
			</section>

			<div class="col-md-12" style="margin-top: 20px;">
				<input style='display: none' id='actStockId' value='${actStockId}'/>
				<input style='display: none' id='fromUrl' value='${fromUrl}'/>
				<div>
					<label>${infoDto.cateName}</label>
				</div>
				<div>
					<label>${infoDto.actName}</label>
				</div>
				<div>
					<label>${infoDto.startDate}</label>
				</div>
			</div>
			<div class="col-md-12 btn_material clearfix">
				<button class="btn  btn-upload pull-right" id="upload_material" style="margin-right: 20px;">+上传照片</button>
				<button class="btn  btn-upload pull-left" id="allCheck">全选</button>
			</div>
			<table id="dataList">
			</table>
			<div class="row" style="margin-top: 50px;">
				<div class="col-md-12 text-center">
					<button class="btn btn-success backPage" type="button">
						<i class="fa fa-backward"> 返回 </i>
					</button>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</body>

<#include "/footer.ftl" />
<script type="text/javascript">
	
</script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.js"></script>
<script
	src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script type="text/javascript"
	src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script>
<script
	src="${uiBase}/js/pages/context/context_material.js?v=${resourceVersion}"></script>
</html>