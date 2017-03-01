<#assign headComponents = ["bootTable"] > <#include "/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/pages/product/product_validate.css?v=${resourceVersion}">
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
			<div class='box box-primary '>
			<div class='condition-sel'>
			<select class="selectpicker form-control" id="fCategory" title="一级商品品类"> 
				<option value="">不限</option>
                <#list fatherCategorys as fCategory>
					<option value="${fCategory.cateId}">${fCategory.cateName}</option>
				</#list>
            </select>
            <span class="time">-</span>
			<select title="二级商品品类" name="sCategory" class="selectpicker form-control" id="sCategory" data-rule-checkCategory="true" value=""
			disabled="disabled">
            </select>
			</div>

			<div class='condition-input'>
				<input class='form-control pname-input' placeholder="商品名称" /> <input
					class='form-control min-price' placeholder="价格范围" maxlength="11"/> <label
					style="margin-right: 20px">—</label> <input
					class='form-control max-price' placeholder="价格范围" maxlength="11"/>
				<button type="button" class="btn btn-primary" id='refreshRecord'>
					<i class="fa fa-search"></i> 查询
				</button>
			</div>
			<div style="margin-bottom: 1%;">
				<span id="numVali" style="color:#ffb042;padding-left:10px;font-weight:700;display:none;margin-left: 35%;">请输入有效的数字</span>
				<span id="numCom" style="color:#ffb042;padding-left:10px;font-weight:700;display:none;margin-left: 35%;">请输入正确的价格范围</span>
			</div>
			<div class='box-body'>
			<table id="dataList" class='data-list'>
			</table>
			</div>
			</div>
			</div>
		</div>
	</div>
</body>

<#include "/footer.ftl" />
<script type="text/javascript"
	src="${uiBase}js/pages/orderManager/product_validate.js?v=${resourceVersion}"></script>

</html>