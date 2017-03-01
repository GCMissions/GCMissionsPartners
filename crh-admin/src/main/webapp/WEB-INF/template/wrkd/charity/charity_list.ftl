<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/wrkd/product/list.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      	<!-- Content Wrapper. Contains page content -->
    	<div class="content-wrapper page-content-wrapper">
       		<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 公益管理</li>
					<li>公益活动列表</li>
				</ol>
			</section>

			<!-- Main content -->
	        <div class="main-content">
	        	<div class="box box-primary">
					<div class="box-header with-border">
						<input id="charityName" class="form-control" type="text" maxlength=50 placeholder="公益活动名称" />
						<div class="dateDiv" style="margin-bottom: 0px;margin-left: 10px;">
							<input size="20" type="text" id="startDate" class="form-control keyword" placeholder="创建开始时间" readonly> 
							<span class="add-on" style="display: none"><i class="icon-remove"></i></span> 
							<span class="add-on"><i class="icon-calendar"></i></span>
						</div>
						<div class="dateDiv" style="margin-bottom: 0px;margin-left: 10px;">
							<input size="20" type="text" id="endDate" class="form-control keyword" placeholder="创建结束时间" readonly> 
							<span class="add-on" style="display: none"><i class="icon-remove"></i></span> 
							<span class="add-on"><i class="icon-calendar"></i></span>
						</div>
						<select name="status" class="form-control" style="margin-left:10px;width:90px;">
							<option value="">不限</option>
							<option value="2">未开始</option>
							<option value="3">进行中</option>
							<option value="4">已结束</option>
							<option value="5">已失效</option>
							<option value="6">专辑已下架</option>
						</select>
						<button type="button" class="btn btn-primary" id="search">
							<i class="fa fa-search"></i> 查询
						</button>
					</div>

					<div class="box-body">
						<div class="row">
							<div class="col-md-12">
								<button type="button" class="btn btn-primary pull-right" id="deleteItem" style='margin-right:0px;'>
									<i class="fa fa-remove"></i> 删除
								</button>
								<label class="pull-right" style='margin-right:20px;'> 
									<a class="btn btn-default" href="${urlPrefix}coolbag/charity/page?oper=add">
										<i class="fa fa-plus"></i> 添加公益活动
									</a>
								</label>
								
							</div>
						</div>
						<form id="product">
							<table id="dataList" class="table table-bordered table-hover">
								<thead>
									<th><input type="checkbox" id="selectAll" value=""></th>
									<th>公益活动名称</th>
									<th>关联专辑</th>
									<th>参与人数</th>
									<th>创建时间</th>
									<th>有效期</th>
									<th>状态</th>
									<th>操作</th>
								</thead>
								<tbody>
								
								</tbody>
							</table>
						</form>
					</div><!-- /.box-body -->
				</div>
        	</div><!-- /.content --> 
        <div class="clearfix"></div>
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
 </body>
<#include "/footer.ftl" /> 
<script type="text/javascript" src="${uiBase}js/pages/wrkd/charity/charity_list.js?v=${resourceVersion}_1116"></script> 

</html>
