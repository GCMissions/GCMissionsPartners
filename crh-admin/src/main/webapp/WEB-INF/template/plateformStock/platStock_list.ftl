<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">
<style>
.datagrid-header-input {
	display: inline-block;
	width: 160px;
}

.bootstrap-select {
	width: 161px !important;
}

.bootstrap-select.btn-group:not (.input-group-btn ), .bootstrap-select.btn-group[class*="col-"]
	{
	float: left;
}

.modal-header {
	background-color: #337ab7;
}

.modal-dialog .modal-header {
	border-top-left-radius: 4px;
	border-top-right-radius: 4px;
}

.modal-dialog .bootstrap-dialog-title {
	color: #fff;
	display: inline-block;
	font-size: 16px;
}

.btnGroup button {
	margin-left: 10px;
}

.bootstrap-dialog-message {
	width: 100%;
	max-height: 400px;
	word-break: break-word;
	overflow-y: scroll;
	word-wrap: break-word;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
         
          <ol class="breadcrumb">
            <li><i class="fa  fa-building-o"></i> 库存管理</li>
            <li> 平台库存管理</li>
             
          </ol>
          
         </section>
        
       
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                
                	<div class="form-horizontal search-group" >
                    	<div class="box-body">
                    	
                    		<div class="form-group">
                    		                  
                            	<label class="col-sm-2 control-label">单瓶商品名称：</label>
								<input class="form-control col-sm-2" id="materialName" type="text">
								
								<label class="col-sm-2 control-label">单瓶商品编码：</label>
								<input  class="form-control col-sm-2" id="materialCode" type="text">
                            	
                    		</div>
                    			
                    		<div class="form-group ">	
                    		
								<label class="col-sm-2 control-label no-padding" style="text-align: right;"> 
									<button class="btn  btn-primary" id="search"><i class="fa fa-search"></i> 开始搜索</button>
                            	</label>
                            	<div class="btnGroup">
                            		<button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>  
									<button data-exceltype="1" id="scan" class="btn btn-primary" type="button"> 扫码入库 </button>
									<button data-exceltype="1" id="export" class="btn btn-primary" type="button" data-toggle="modal" data-target="#myModal"><i class="fa  fa-refresh"></i> 导出入库表</button>
									
									<input type="file" name="postExcel" id="postExcel" style="display:none;" accept=".xls,.xlsx">
									<button data-exceltype="1" id="import" class="btn btn-primary" type="button"><i class="fa fa-plus"></i> 批量入库 </button>
								</div>

									<div class="modal fade" id="myModal" tabindex="-1"
										role="dialog" aria-labelledby="myModalLabel"
										aria-hidden="true">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header bootstrap-dialog-draggable">
													<button type="button" class="close" data-dismiss="modal"
														aria-hidden="true">×</button>
													<div class="bootstrap-dialog-title">日期选择</div>
												</div>
												<div class="modal-body">
													<form id="inStockForm" method="get"
														action="${base}/web/adminStock/toExcel">
														<label class="control-label" for="type-select">请选择时间：</label>
														<div class="dateDiv" style="margin-bottom: 0px;">
															<input size="10" type="text" name="startTime"
																id="startTime" class="form-control keyword beginDate"
																placeholder="请选择时间" readonly> <span
																class="add-on" style="display: none"><i
																class="icon-remove"></i></span> <span class="add-on"><i
																class="icon-calendar"></i></span>
														</div>
														<span class="time">至</span>
														<div class="dateDiv" style="margin-bottom: 0px;">
															<input size="10" type="text" name="endTime" id="endTime"
																class="form-control keyword endDate" placeholder="请选择时间"
																readonly> <span class="add-on"
																style="display: none"><i class="icon-remove"></i></span>
															<span class="add-on"><i class="icon-calendar"></i></span>
														</div>
													</form>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-primary"
														id="exportBtn">确定</button>
													<button type="button" class="btn closeBtn"
														data-dismiss="modal" aria-hidden="true">取消</button>
												</div>
											</div>
											<!-- /.modal-content -->
										</div>
										<!-- /.modal -->

									</div>

								</div><!-- /.box-body -->
                    </div><!-- /.search-group -->
                    
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<th>序号</th>
                            <th>单瓶商品编码</th>
                            <th>单瓶商品名称</th>
                            <th>库存总量</th>                            
                        </thead>
                        <tbody>
                        	
                        </tbody>
                      </table>
                    </div><!-- /.box-body -->
                    
               </div>
             </div>
           </div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->


	<form id="errorCodeForm" method="get" class="form-horizontal"
		action="${base}/web/adminStock/toErrorCodeExcel">
	</form>

</body>
<#include "/footer.ftl" />
<script src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js?v=${resourceVersion}"></script>
<script src="${uiBase}/js/pages/shopStock/platStock.js?v=${resourceVersion}" ></script>
</html>
