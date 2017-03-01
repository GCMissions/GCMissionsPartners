<#assign headComponents = ["bootTable", "innerPage"] > <#include
"/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/pages/intersection/intersection_list.css?v=${resourceVersion}">
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>内容管理</li>
					<li>合集管理</li>
				</ol>
			</section>

			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="nav-tabs-custom">
						<div class="tab-content">
							<div class="tab_div">
								<div class="row">
								<input type="hidden" name="ids" id="ids">
									<div class="col-md-12 clearfix">
										<div class="col-sm-12 no-padding datetimeInputGroup">
											<input type="text" class="form-control pull-left"
												id="intersectionName" placeholder="合集名称" style="text-align: center;">
											<div class=" date datetimeInput no-padding pull-left">
												<input size="16" type="text" name="startTime" id="csDate"
													class="form-control startTimeSelection"
													placeholder="开始时间" readonly style="text-align: center;"> <span class="add-on"
													style="display: none"><i class="icon-remove"></i></span> <span
													class="add-on"><i class="icon-calendar"></i></span>
											</div>
											<span class="pull-left u-to">至</span> <input type="hidden"
												data-ignore="true" name="endTime" id="edDate" value="" />
											<div class=" date datetimeInput no-padding pull-left"
												style="margin-bottom: 0px;margin-right: 20px;">
												<input size="16" type="text" name="endDate" id="ceDate"
													class="form-control endTimeSelection" placeholder="结束时间"
													readonly style="text-align: center;"> <span class="add-on"
													style="display: none"><i class="icon-remove"></i></span> <span
													class="add-on"><i class="icon-calendar"></i></span>
											</div>
											<button class="btn  btn-primary pull-left" id="refreshRecord">
												<i class="fa fa-search"></i>查询
											</button>
										</div>
									</div>
								</div>
							</div>
							<div class="perform_underline"></div>
							<div class="row">
                       			<div class="col-md-12">	
                       				<a class="btn btn-primary pull-right" href="${urlPrefix}intersection/editor/0/3"><i class="fa fa-plus"></i> 添加合集</a> 
                              		<button type="button" class="btn btn-primary pull-right" id="del_btn" style='margin-right:20px;'><i class="fa fa-remove"></i> 删除</button> 
                         		</div>
                         	</div>
							<div class="box-body">
								<div class="row control">
									<table id="dataList" class="table table-bordered table-hover">
									</table>
								</div>
							</div>
						</div>
					</div>
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
	src="${uiBase}/js/pages/intersection/intersection_list.js?v=${resourceVersion}"></script>
</html>
