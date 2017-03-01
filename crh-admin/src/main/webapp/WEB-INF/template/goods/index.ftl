<#assign headComponents = ["bootTable"] > <#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}vendor/zTree/css/zTreeStyle.css">
<link rel="stylesheet"
	href="${uiBase}vendor/treeTable/css/jquery.treetable.css">
<link rel="stylesheet"
	href="${uiBase}css/ztree_dropdown.css?v=${resourceVersion}">
<style>
.col-sm-3 {
    width: 30%;
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
					<li><i class="fa fa-dashboard"></i> 商品管理</li>
					<li>单瓶商品管理</li>
				</ol>

			</section>

			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="box box-primary ">

						<div class="form-horizontal search-group">
							<div class="box-body">
								<div class="form-group ">

									<label class="col-sm-2 control-label">单瓶商品编号：</label> <input
										id="goodCode" class="form-control col-sm-2" type="text">

									<label class="col-sm-2 control-label">单瓶商品名称：</label> <input
										id="goodName" class="form-control col-sm-2" type="text">

								</div>

								<div class="form-group ">

									<label for="dtp_startDate" class="control-label col-sm-2">创建日期：</label>

									<div class="col-sm-6 no-padding datetimeInputGroup">
										<div
											class="input-group date datetimeInput  no-padding pull-left"
											data-date="" data-date-format="yyyy-MM-dd">
											<input class="form-control startTimeSelection" size="16"
												id="beginDate" type="text" id="" value="" readonly
												placeholder="开始时间"> <span class="add-on"><i
												class="icon-remove"></i></span> <span class="add-on"><i
												class="icon-calendar"></i></span>
										</div>

										<span class="pull-left textTo">至</span>
										<div
											class="input-group date datetimeInput no-padding pull-left"
											data-date="" data-date-format="yyyy-MM-dd">
											<input class="form-control endTimeSelection" id="endDate"
												size="16" type="text" value="" readonly placeholder="结束时间">
											<span class="add-on"><i class="icon-remove"></i></span> <span
												class="add-on"><i class="icon-calendar"></i></span>
										</div>

									</div>

								</div>

								<div class="form-group ">
									<label class="col-sm-2 control-label no-padding">
										<button type="button" class="btn btn-primary "
											id="refreshRecord">
											<i class="fa fa-search"></i> 开始搜索
										</button>
									</label> <label class="col-sm-1 control-label pull-left">
										<button type="button" class="btn btn-default reloadPage">
											<i class="fa  fa-refresh"></i> 刷新
										</button>
									</label> <label class="col-sm-1  pull-left">
										<button class="btn btn-default addItem">
											<i class="fa fa-plus"></i> 添加
										</button>
									</label>
								</div>
							</div>
						</div>

						<div class="box-body">
							<table id="dataList"
								class="table table-bordered table-hover dataList">
								<thead>
									<th>序号</th>
									<th>单瓶商品编号</th>
									<th>单瓶商品名称</th>
									<th>销售价</th>
									<th>规格</th>
									<th>创建时间</th>
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

	<#include "/footer.ftl" />
	<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.exedit.js"></script>
	<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.excheck.js"></script>
	<script type="text/javascript" src="${uiBase}js/pages/goods/index.js?v=${resourceVersion}"></script>
	<script id="addEditTpl" type="text/html">
<form id="addEditForm" method="post" class="form-horizontal"　action=""  >
<div class="box-body form-horizontal addEditTpl">
	<div class="form-group row">
	  <label class="col-sm-3 control-label" ><span class="requiredField">*</span>单瓶商品编号：</label>
	  <div class="col-sm-7">
	    <input type="text" class="form-control" name="goodCode" placeholder="请输入单瓶商品编号" data-rule-required="true" data-msg-required="请输入单瓶商品编号"  value="{{goodCode}}" maxlength=20>
        <input type="hidden" name="goodsId" value="{{goodsId}}">
	  </div>
	</div>
    <div class="form-group row">
	  <label class="col-sm-3 control-label" ><span class="requiredField">*</span>单瓶商品名称：</label>
	  <div class="col-sm-7">
	    <input type="text" class="form-control" name="goodName" placeholder="请输入单瓶商品名称" data-rule-required="true" data-msg-required="请输入单瓶商品名称"  value="{{goodName}}" maxlength=50>
	  </div>
	</div>
	<div class="form-group row">
	  <label class="col-sm-3 control-label" ><span class="requiredField">*</span>销售价：</label>
	  <div class="col-sm-7">
	    <input type="text" class="form-control" id="priceYuan" name="priceYuan" placeholder="请输入单瓶商品销售价" data-rule-required="true" data-msg-required="请输入单瓶商品销售价" value="{{priceYuan}}" maxlength=10>
<label id="price-error" style="display:none;" class="fieldError">请输入正确的价格！</label>
	  </div>
	</div>
	<div class="form-group row">
	  <label class="col-sm-3 control-label" ><span class="requiredField">*</span>规格：</label>
	  <div class="col-sm-7">
	    <input type="text" class="form-control" name="specs" placeholder="请输入单瓶商品规格" data-rule-required="true" data-msg-required="请输入单瓶商品规格"  value="{{specs}}" maxlength=10>
	  </div>
	</div>
</div>
</form>
</script>
	<script>
		$(function() {
			goodsApp.init();
		});
	</script>
</body>
</html>