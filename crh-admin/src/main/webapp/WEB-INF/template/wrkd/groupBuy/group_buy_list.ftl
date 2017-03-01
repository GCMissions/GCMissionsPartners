<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/pages/wrkd/group_buy.css?v=${resourceVersion}">
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 团购管理</li>
					<li>团购管理</li>
				</ol>
			</section>
			<!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary ">
                	<div class="box-header with-border">
                    	<div class="form-horizontal  search-group " id="search-area" >
                       		<div class="row">
                       			<div class="col-md-12">
                       				<input type="hidden" name="ids" id="ids">
                         			<div class="col-sm-3">
                                		<input id="groupName" name="groupName" class="form-control" type="text" placeholder="团购名称">
                                	</div>
	                                <div class="col-sm-8">
	                         			<div class="no-padding datetimeInputGroup" >
											<div class="input-group date datetimeInput no-padding pull-left">
												<input size="16" type="text" name="startTime" id="csDate" class="form-control startTimeSelection" placeholder="开始时间" readonly>
												<span class="add-on" style="display: none"><i class="icon-remove"></i></span>
												<span class="add-on"><i class="icon-calendar"></i></span>
											</div>
											<span class="pull-left textTo">至</span>
											<div class="input-group date datetimeInput no-padding pull-left" style="margin-bottom: 0px;">
												<input size="16" type="text" name="endDate"  id="ceDate" class="form-control endTimeSelection" placeholder="结束时间" readonly>
												<span class="add-on" style="display: none" ><i class="icon-remove"></i></span>
												<span class="add-on"><i class="icon-calendar"></i></span>
											</div>
										</div>
									</div>	
	                             </div>                            
							</div>
                          	<div class="row">
                          		<div class="col-md-12">	
	                                <div class="col-sm-3">
	                                	<input id="productName" name="productName" class="form-control col-sm-2" type="text" placeholder="商品名称">
	                                </div>
	                                <div class="col-sm-6">
	                                	<input id="productCode" name="productCode" class="form-control col-sm-2" style='width:194px;margin-right:30px;' type="text" placeholder="商品编号" />
	                                	<select class="selectpicker form-control" id="status" name="status" title="团购状态">
	                                		<option value="">不限</option>
	                                		<#list searchStatus as status>
	                                			<option value="${status.getCode()}">${status.getText()}</option>
	                                		</#list>
	                                     </select>
	                                </div>
	                                <div class="col-sm-1">	
	                                	<button type="button" class="btn  btn-primary" id="search"><i class="fa fa-search"></i>查询</button> 
	                                </div>
                            	</div>
                            </div>
                            
                        </div>
                     </div>
                     <div class="box-body">
                     	<div class="row">
                       		<div class="col-md-12">	
                              	<button type="button" class="btn btn-primary pull-right" id="del_btn"><i class="fa fa-remove"></i> 删除</button> 
                              	<a class="btn btn-default pull-right" href="${urlPrefix}groupPurchase/add/0/-1" style='margin-right:20px;'><i class="fa fa-plus"></i> 添加团购</a>        
                         	</div>
                         </div>
                      	<table id="dataList" class="table table-bordered table-hover dataList"></table>
                    </div>
                </div>
             </div>
        </div>
       <!-- /.content --> 
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div>
</body>
<#include "/footer.ftl" />

<script src="${uiBase}/js/pages/wrkd/groupBuy/group_buy_list.js?v=${resourceVersion}"></script>
</html>