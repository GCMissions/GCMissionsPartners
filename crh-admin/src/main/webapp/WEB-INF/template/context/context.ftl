<#assign headComponents = ["bootTable", "innerPage"] > <#include
"/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/pages/context/context.css?v=${resourceVersion}">
<style type=text/css>
.tab_div {
	display: table;
	width: 100%;
}

.perform_underline {
	border-bottom: 2px solid #ddd;
	padding-bottom: 30px;
	margin-bottom: 30px;
}

.description {
	display: block;
	width: 360px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}
.wrapimg{
	position: absolute;
	left:50%;
	top:50%;
	width:0px;
	height:0px;
	margin-left:0px;
	margin-top: 0px;
	display: none;
	z-index: 999;
}
.wrapimg img{
	cursor:pointer;
	width:100%;
	height:auto;
	margin: 100px 0 auto 0;
}
</style>
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

			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="nav-tabs-custom">
						<!-- tab 页标题 -->
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_1" data-toggle="tab">活动相册</a></li>
							<li><a href="#tab_2" data-toggle="tab">素材库</a></li>
						</ul>

						<!-- TAB页 -->
						<div class="tab-content">
							<div class='tab-pane active' id="tab_1">
								<div class="tab_div">
									<div class="row">
										<div class="col-md-12 clearfix">
											<div class="col-sm-12 no-padding datetimeInputGroup">
												<input type="hidden" data-ignore="true" name="stDate"
													value="" />
												<div class=" date datetimeInput no-padding pull-left">
													<input size="16" type="text" name="startTime" id="csDate"
														class="form-control startTimeSelection"
														placeholder="活动开始时间" readonly> <span
														class="add-on" style="display: none"><i
														class="icon-remove"></i></span> <span class="add-on"><i
														class="icon-calendar"></i></span>
												</div>
												<span class="pull-left u-to">至</span> <input type="hidden"
													data-ignore="true" name="endTime" id="edDate" value="" />
												<div class=" date datetimeInput no-padding pull-left"
													style="margin-bottom: 0px;">
													<input size="16" type="text" name="endDate" id="ceDate"
														class="form-control endTimeSelection" placeholder="活动结束时间"
														readonly value="${today}"> <span class="add-on"
														style="display: none"><i class="icon-remove"></i></span> <span
														class="add-on"><i class="icon-calendar"></i></span>
												</div>
												<input type="text" class="form-control pull-left"
													id="actName" placeholder="活动名称">
												<input type="text" class="form-control pull-left"
													id="actCode" placeholder="活动编号">	
												<button class="btn  btn-primary pull-left"
													id="refreshRecord">
													<i class="fa fa-search"></i>查询
												</button>
											</div>
										</div>
									</div>
									<label class='suggest'>本地上传图片单次建议在5张以内,单张照片建议在300K以下</lable>
								</div>
								<div class="box-body">
									<div class="row control">
										<table id="dataList" class="table table-bordered table-hover">
										</table>
									</div>
								</div>
							</div>

							<div class='tab-pane' id="tab_2">
								<div><label class='suggest pull-right'>本地上传图片单次建议在5张以内,单张照片建议在300K以下</lable></div>
								<div class="tab_div">
									<div class="col-md-12 btn_material clearfix">
										<button class="btn  btn-delete pull-right" id="delete_img">删除</button>
										<button class="btn  btn-upload pull-right"
											id="upload_material" style="margin-right: 20px;">+上传素材</button>
										<button class="btn  btn-upload pull-left" id="allCheck">全选</button>
										<input type="file" name="file" style="display: none"
											id="material-upload" multiple="true"
											class="fileInput" accept='.jpg,.png,.gif'/>
									</div>
									<table id="dataList1">
									</table>
								</div>
							</div>

						</div>
						<!-- TAB页 end -->
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
<script type="text/javascript"
	src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script>
<script src="${uiBase}/js/pages/context/context.js?v=${resourceVersion}"></script>
</html>
