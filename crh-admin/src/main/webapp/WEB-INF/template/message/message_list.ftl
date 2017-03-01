<#assign headComponents = ["bootTable", "bootDialog"] > <#include
"/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/pages/message/pMessage_list.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 站内信</li>
					<li>站内信列表</li>
				</ol>
			</section>
 
			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="box box-primary">
						<div class="form-horizontal search-group" id="search-area">
							<div class="box-body">
								<div class="form-group">
									<label class="control-label col-sm-2" for="type-select">标题：</label>
									<input type="text" class="form-control col-sm-3" name="title">
									<label class="control-label col-sm-2" for="type-select">发送时间：</label>
									<input type="hidden" name="csDateInput" id="csDateInput"
										value="" data-ignore="true" />
									<div class="dateDiv" style="margin-bottom: 0px;">
										<input size="10" type="text" name="csDate" id="csDate"
											class="form-control keyword beginDate" placeholder="请选择时间"
											readonly> <span class="add-on" style="display: none"><i
											class="icon-remove"></i></span> <span class="add-on"><i
											class="icon-calendar"></i></span>
									</div>
									<span class="time">至</span> <input type="hidden"
										name="ceDateInput" id="ceDateInput" value=""
										data-ignore="true" />
									<div class="dateDiv" style="margin-bottom: 0px;">
										<input size="10" type="text" name="ceDate" id="ceDate"
											class="form-control keyword endDate" placeholder="请选择时间"
											readonly> <span class="add-on" style="display: none"><i
											class="icon-remove"></i></span> <span class="add-on"><i
											class="icon-calendar"></i></span>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2" for="type-select">状态：</label>
									<div class="col-sm-2 no-padding">
										<select class="selectpicker form-control  " name="readStatus">
											<option value="" selected="selected">全部</option> <#list
											readStatus as type>
											<option value="${type.code}">${type.text}</option> </#list>
										</select>
									</div>
								</div>
								<div class="form-group ">
									<label class="col-sm-2 control-label pull-left">
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
							<table id="dataList" class="table table-bordered table-hover">
								<thead>
									<th>标题</th>
									<th>内容</th>
									<th>发送时间</th>
									<th>状态</th>
									<th>操作</th>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
							
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
</body>
<#include "/footer.ftl" />
<script id="viewTpl" type="text/html">
<div class="box-body form-horizontal">

    <div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">标题:</label>
	  <div class="col-sm-6">
		<span id="title" class="detail-form-control"></span>
	  </div>
	</div>

	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">发送时间:</label>
	  <div class="col-sm-6">
		<span class="detail-form-control" id="createDate"></span>
	  </div>
	</div>

	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">内容:</label>
	  <div class="col-sm-9">
	     <span id="content" class="detail-form-control"></span>
	  </div>
	</div>
</div>
</script>
<script type="text/javascript"
	src="${uiBase}/js/pages/message/message_list.js?v=${resourceVersion}"></script>
</html>
