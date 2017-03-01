<#assign headComponents = ["bootTable", "bootDialog"] > 
<#include "/header.ftl" />

<link rel="stylesheet" href="${uiBase}vendor/treeTable/css/jquery.treetable.css">
<link rel="stylesheet" href="${uiBase}vendor/treeTable/css/jquery.treetable.theme.default.css">
<link rel="stylesheet" href="${uiBase}vendor/treeTable/css/screen.css">
<link rel="stylesheet" href="${uiBase}css/list.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}css/pages/marketing/rebate_list.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}vendor/zTree/css/zTreeStyle.css">
<link rel="stylesheet" href="${uiBase}css/ztree_dropdown.css?v=${resourceVersion}">
<style type="text/css">

.categoryTreeWrap  .ztree li span.roots_close {visibility:hidden; width:1px;}

</style>

</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 营销管理</li>
					<li>返点比例设置</li>
				</ol> 
			</section>

			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="nav-tabs-custom">
						<!-- tab 页标题 -->
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_1" data-toggle="tab">商品返点比例 </a></li>
							<li><a href="#tab_2" data-toggle="tab"> 配送费返点比例 </a></li>
							<li><a href="#tab_3" data-toggle="tab"> 推广返点比例 </a></li>
						</ul>
						<div class="tab-content">
							<div class='tab-pane active' id="tab_1">
								<div class="form-horizontal search-group box no-border" id="search-area">
									<div class="box-body">
										<div class="form-group">
											<label class="control-label col-sm-2"  >商品名称：</label>
											<input type="text" class="form-control col-sm-2"name="productName" id="productName"> 
											<label class="control-label col-sm-2"  >商品条码：</label>
											<input type="text" class="form-control col-sm-2" id="productCode" name="productCode"> 
											<label 	class="control-label col-sm-1"  >分类：</label>
											<!--<div class="col-sm-2 no-padding">
												<select class="selectpicker form-control  " name="cateId">
													<option value="" selected="selected">全部</option> <#list
													category as cate>
													<option value="${cate.cateId}">${cate.cateName}</option>
													</#list>
												</select>
											</div>
											-->
											
											 <div class="col-sm-3 categoryTreeWrap l">    
			                                 		<input type="hidden" name="parentId" id="categoryId" value=""/>
				                                 		<input type="text" name="cateId" id="cateId" class=" form-control text show" readonly placeholder="请选择分类"/>
				                                 		<span class="inputArrow">&nbsp;</span>
				                                 		
				                                        <div class="menuContent" id="ztreeWraper"><ul  id="cateZtree"  class="ztree" ></ul></div>
			                                </div>
                                
                                
										</div>
										<div class="form-group ">
											<label class="control-label col-sm-2" for="type-select">品牌：</label>
											<div class="col-sm-2 no-padding">
												<select class="selectpicker form-control  " name="brandId"  id="brandId">
													<option value="" selected="selected">不限</option>
													<option value="0">无品牌</option> <#list
													brand as brand>
													<option value="${brand.brandId}">${brand.brandName}</option>
													</#list>
												</select>
											</div>
										</div>
										<div class="form-group ">
											<label class="col-sm-2 control-label pull-left">
												<button type="button" class="btn btn-primary "
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
											<th>序号</th>
											<th>商品条码</th>
											<th>商品名称</th>
											<th>分类</th>
											<th>品牌</th>
											<th>商品利润返点</th>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
							<div class="tab-pane" id="tab_2">
								<div class="box-body form-horizontal">
									<div class="form-group row">
										<div class="col-md-12">
											<table id="tree_table2" class="tree_table">
												<thead>
													<th></th>
													<th>区域平台商返点</th>
													<th>终端配送商返点</th>
												</thead>
												<tbody id="tree_body2" class="tree_body">
												</tbody>
											</table>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12 text-center">
											<button class="btn btn-primary submitCityRebate"
												type="button">保存</button>
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane" id="tab_3">
								<div class="box-body promoteRebate tree_table">
									<div class="form-group row">
										<div class="col-sm-8"> 
											<label class="control-label col-sm-2">推广返点比例：</label>
											<input type="text" class="form-control col-sm-2"name="refereeRebate" id="refereeRebate">
										</div> 
									</div>
									<div class="row">
										<div class="col-sm-12 text-center">
											<button class="btn btn-primary submitPromoteRebate" type="button">保存</button>
										</div>
									</div>
								</div>
							</div>
						</div>
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
	  <div class="col-md-12" style="overflow-y: scroll;max-height: 400px;">
		<input type="hide" id="product" class="hide">
		<table id="tree_table" class="tree_table">
			<thead>
				<th>地区:</th>
				<th>区域平台商返点</th>
				<th>终端配送商返点</th>
			</thead>
			<tbody id="tree_body" class="tree_body">
			</tbody>
		</table>
	  </div>
	</div>

</div>
</script>
<!-- treeTable 3.2.0 -->
<script src="${uiBase}vendor/treeTable/js/jquery.treetable.js"></script>
<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${uiBase}js/pages/marketing/rebate_list.js?v=${resourceVersion}"></script>
</html>