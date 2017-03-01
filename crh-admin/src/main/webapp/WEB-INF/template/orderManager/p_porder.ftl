<#assign headComponents = ["bootTable", "bootDialog"] > <#include
"/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/list.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->

			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 订单管理</li>
					<li>奖惩订单</li>
				</ol>
			</section>
			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="box box-primary">
						<div class="form-horizontal search-group" id="search-area">
							<div class="box-body">
								<div class="form-group ">
									<label class="control-label col-sm-2" for="type-select">订单编号：</label>
									<input id="orderId" name="orderId"
										class="form-control col-sm-3" type="text" maxlength="20">

									<label class="control-label col-sm-2" for="type-select">订单创建时间：</label>
									<div class="col-sm-6 no-padding datetimeInputGroup">
										<div
											class="input-group date datetimeInput  no-padding pull-left"
											data-date="" data-date-format="yyyy-MM-dd">
											<input class="form-control startTimeSelection" size="16"
												id="startDate" type="text" id="" value="" readonly
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
									<label class="control-label col-sm-2" for="type-select">奖惩类型：</label>
									<div class="col-sm-2 no-padding">
										<select id="type" class="selectpicker form-control" name="type">
											<option value="" selected="selected">全部</option>
											<option value="1">转单奖励</option>
											<option value="2">超时惩罚</option>
											<option value="3">邀请奖励</option>
										</select>
									</div>
									
									<label class="control-label col-sm-2" for="type-select">终端配送商名称：</label>
									<input id="orgName" name="orgName"
										class="form-control col-sm-3" type="text" maxlength="50">
								</div>
								
								<div class="form-group ">
									<label class="col-sm-2 control-label no-padding" style="text-align: right;"> 
										<button type="button" class="btn btn-primary pull-right"
											id="refreshRecord">
											<i class="fa fa-search"></i>开始搜索
										</button>
									</label> <label class="col-sm-1 control-label pull-left">
										<button type="button" class="btn btn-default reloadPage">
											<i class="fa  fa-refresh"></i> 刷新
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
									<th>订单编号</th>
									<th>创建时间</th>
									<th>指派时间</th>
									<th>奖惩时间</th>
									<th>奖惩类型</th>
									<th>奖惩商家</th>
									<th>奖惩金额</th>
									<!-- <th>操作</th> -->
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

</body>
<#include "/footer.ftl" />
<script type="text/javascript"
	src="${uiBase}vendor/zTree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript"
	src="${uiBase}vendor/zTree/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript"
	src="${uiBase}vendor/zTree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript"
	src="${uiBase}/js/pages/orderManager/p_porder_list.js?v=${resourceVersion}"></script>
<script>
	$(function() {
		goodsApp.init();
	});
</script>
</html>