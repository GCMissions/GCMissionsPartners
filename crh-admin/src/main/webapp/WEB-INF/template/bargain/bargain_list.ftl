<#assign headComponents = ["bootTable"] > <#include "/header.ftl" />
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">

				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 砍价管理</li>
					<li>砍价列表</li>
				</ol>
			</section>

			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="box box-primary ">
						<form id="searchForm">
							<div class="form-horizontal search-group" id="search-area">
								<div class="box-body">
									<div class="form-group ">
										<div class="col-md-12">
											<div class="col-sm-6 no-padding timegroup">
												<input type="hidden" data-ignore="true" name="stDate"
													id="stDate" value="" />
												<div class="dateDiv" style="margin-bottom: 0px;">
													<input size="10" type="text" name="startDate"
														id="startDate" class="form-control keyword startDate"
														placeholder="开始时间" readonly> <span class="add-on"
														style="display: none"><i class="icon-remove"></i></span> <span
														class="add-on"><i class="icon-calendar"></i></span>
												</div>
												<span class="time">-</span> <input type="hidden"
													data-ignore="true" name="edDate" id="edDate" value="" />
												<div class="dateDiv" style="margin-bottom: 0px;">
													<input size="10" type="text" name="endDate" id="endDate"
														class="form-control keyword endDate" placeholder="结束时间"
														readonly> <span class="add-on"
														style="display: none"><i class="icon-remove"></i></span> <span
														class="add-on"><i class="icon-calendar"></i></span>
												</div>
											</div>
											<div class="col-sm-2">
												<select class="selectpicker" name="status" id="status" title="状态">
													<option value="">不限</option> <#list status as s>
													<option value="${s.key }">${s.value }</option> </#list>
												</select>
											</div>
										</div>
									</div>
								</div>

								<div class="box-body">
									<div class="form-group ">
										<label class="control-label col-sm-1" for="type-select"></label>
										<button type="button" class="btn btn-primary" id="searchBtn">
											<i class="fa fa-search"></i>开始搜索
										</button>
										<button type="button" class="btn btn-default reloadPage">
											<i class="fa  fa-refresh"></i> 刷新
										</button>
										<button type="button" class="btn btn-default" id="addBargain">
											<i class="fa fa-plus"></i>添加砍价商品
										</button>
									</div>
								</div>
							</div>
						</form>
						<div class="box-body">
							<table id="dataList"
								class="table table-bordered table-hover dataList">
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
	src="${uiBase}js/pages/bargain/bargain_list_new.js?v=${resourceVersion}"></script>

</html>
