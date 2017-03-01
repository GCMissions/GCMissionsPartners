<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" />
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">

				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 24小时管理</li>
					<li>活动列表</li>
				</ol>
			</section>

        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                    	<div class="row">
                    		<div class="col-md-12">
                    			<div class="col-sm-3"><input type="text" id="productName" class="form-control" placeholder="商品名称" style='width:161px;' /></div>
                    			<div class="col-sm-3"><input type="text" id="productCode" class="form-control" placeholder="商品编号" style='width:161px;' /></div>
                    			<div class="col-sm-3">                                
                                	<select class="form-control" id="status" title="活动状态">
                                    	<option value="">不限</option> <#list status as s>
									 	<option value="${s.key }">${s.value }</option> </#list>
                                 	</select>
                             	</div>
                    		</div>
                       	</div>
                       	<div class="row">
                       		<div class="col-md-12">
                       			<div class="col-sm-3"><input type="text" id="name" class="form-control" placeholder="活动名称" style='width:161px;' /></div>
                       			<div class="col-sm-6 timegroup">
									<input type="hidden" data-ignore="true" name="stDate"
										id="stDate" value="" />
									<div class="dateDiv" style="margin-bottom: 0px;">
										<input size="10" type="text" name="startDate"
											id="startDate" class="form-control keyword startDate"
											placeholder="开始时间" readonly> <span class="add-on"
											style="display: none"><i class="icon-remove"></i></span> <span
											class="add-on"><i class="icon-calendar"></i></span>
									</div>
									<span class="time"> - </span>
									<input type="hidden" data-ignore="true" name="edDate" id="edDate" value="" />
									<div class="dateDiv" style="margin-bottom: 0px;">
										<input size="10" type="text" name="endDate" id="endDate" class="form-control keyword endDate" placeholder="结束时间" readonly>
										<span class="add-on" style="display: none"><i class="icon-remove"></i></span>
										<span class="add-on"><i class="icon-calendar"></i></span>
									</div>
								</div>
                       		</div> 
                        </div>
                    	                   
                    </div>                   
                    <div class="box-body">
                    	<div class="row">
                    	   <div class="col-md-12">
							   <button type="button" class="btn btn-primary" id="searchBtn">
									<i class="fa fa-search"></i> 查询
							   </button>
							   <button type="button" class="btn btn-default reloadPage">
									<i class="fa  fa-refresh"></i> 刷新
							   </button>
							   
							   <button type="button" class="btn btn-primary pull-right" id="deleteItem"><i class="fa fa-remove"></i> 删除</button>
							   <button type="button" class="btn btn-default pull-right" id="addBargain" style='margin-right:20px;'>
									<i class="fa fa-plus"></i>添加活动
							   </button>
						   </div>
                        </div>
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<th><input type="checkbox" id="seletAll" value=""></th>
                            <th>活动名称</th>
                            <th>关联商品编号</th>
                            <th>关联商品 </th>
                            <th>添加时间</th>
                            <th>有效期</th>
                            <th>状态</th>
                            <th>操作</th>  
                        </thead>
                        <tbody>
                        </tbody>
                      </table>
                    </div>           
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
<script type="text/javascript" src="${uiBase}js/pages/wrkd/bargain/bargain_list_new.js?v=${resourceVersion}"></script>
</html>
