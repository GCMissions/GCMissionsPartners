<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">

<style>
.datagrid-header-input {
	display: inline-block;
	width: 160px;
}

.shipmentEdit {
	width: 80px;
}

.hideProductId {
	display: none;
}

.warnShipmentS {
	color: #3c8dbc;
	cursor: pointer;
}

.bootstrap-select {
	width: 161px !important;
}

.btnGroup {
	margin-left: 70px;
}

#startDate, #endDate {
	width: 120px;
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
            <li> 发货管理</li>
             
          </ol>
          
         </section>
        
       
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                
                	<div class="form-horizontal search-group" >
                    	<div class="box-body">
                    		
                       		<div class="form-group">
                       				
								<label class="col-sm-2 control-label">商家编号:</label>
								<input  class="form-control col-sm-2" id="orgCode" type="text">
								
								<label class="col-sm-2 control-label">商家名称:</label>
								<input  class="form-control col-sm-2" id="orgName" type="text">
								
								<label class="col-sm-2 control-label" for="type-select">商家类别：</label>
                            	<select class="col-sm-1 selectpicker form-control" name="paymentStatus" id="orgCate">
                            		<option value="" selected="selected">全部</option> 
                             		<option value="Z" >终端配送商</option>
                                	<option value="P" >区域平台商</option>
                            	</select>
                            	
                       		</div>
                       		
                    		<div class="form-group">
                    		                 
                           		<label class="col-sm-2 control-label">发货单号:</label>
								<input  class="form-control col-sm-2" id="shipmentConde" type="text">
							
								<label class="col-sm-2 control-label" for="type-select">发货状态：</label>
                            	<select class="col-sm-1 selectpicker form-control" id="shipmentState" name="paymentStatus">
                             		<option value="" selected="selected">全部</option>
                             		<option value="0" >已创建</option>
                             		<option value="1" >已发货</option>
                                	<option value="2" >已收货</option>
                             	</select>
								
                       		</div>
                       		
                       		<div class="form-group">
                       				
								<label class="control-label col-sm-2" for="type-select">创建时间：</label>
								<div class="col-sm-6 no-padding datetimeInputGroup" >
									<input type="hidden" data-ignore="true"  name="stDate" id="stDate" value="" />
									<div class="input-group date datetimeInput  no-padding pull-left" style="margin-bottom: 0px;">
										<input size="16" type="text"  name="startTime" id="csDate" class="form-control keyword beginDate" placeholder="开始时间" readonly>
										<span class="add-on" style="display:none"><i class="icon-remove"></i></span>
										<span class="add-on"><i class="icon-calendar"></i></span>
									</div>
									<span class="pull-left textTo">至</span>
									<input type="hidden" data-ignore="true"  name="endTime" id="edDate" value="" />
									<div class="input-group date datetimeInput no-padding pull-left" style="margin-bottom: 0px;">
										<input size="16" type="text" name="endDate"  id="ceDate" class="form-control keyword endDate" placeholder="结束时间" readonly>
										<span class="add-on" style="display:none"><i class="icon-remove"></i></span>
										<span class="add-on"><i class="icon-calendar"></i></span>
									</div>
								</div>
								
                       		</div>
                       		
                       		
                       		<div class="form-group">
                       			<div class="btnGroup">
                       				<button class="btn  btn-primary" id="search"><i class="fa fa-search"></i>开始搜索</button>
                       				<button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>
                       				<button class="btn  btn-primary" id="shipment">创建发货单</button>
                       				<button class="btn  btn-normal btn-danger" id="warnStock"><i class="fa fa-exclamation"></i> 库存预警(${warnNum})</button>
                       				<button class="btn  btn-primary" id="shipmentBtn"> 扫码出库</button>
                       				<button class="btn  btn-primary" id="exportBtn"> 导出出库详情表</button>
                       				<button class="btn  btn-primary" id="exportWarning"> 导出库存预警表</button>
                       			</div>
                       			<!--
                       			<label class="col-sm-2 control-label no-padding" style="text-align: right;"> 
									<button class="btn  btn-primary" id="search"><i class="fa fa-search"></i>开始搜索</button>
                            	</label>
                            	<label class="col-sm-1 control-label pull-left">   
                            		<button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>  
                            	</label> 
                            	<label class="col-sm-1 control-label pull-left">
                            		<button class="btn  btn-primary" id="shipment">创建发货单</button>
                            	</label> 
                            	<label class="col-sm-2 control-label pull-left">
                            		<button class="btn  btn-normal btn-danger" id="warnStock"><i class="fa fa-exclamation"></i> 库存预警(${warnNum})</button>
								</label>
                       			-->
                       			
                            		
                       		</div>
                       		
                    	</div><!-- /.box-header -->
                    </div>
                    
                    
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<th><input type="checkbox" /></th>
                        	<th>序号</th>
                            <th>发货单</th>
                            <th>商家名称</th>
                            <th>商家类别</th>
                            <th>发货数量</th>
                            <th>创建时间</th>
                            <th>发货状态</th>
                            <th>操作</th>
                        </thead>
                        <tbody>
                        	
                        </tbody>
                      </table>
                    </div><!-- /.box-body -->

						<form id="stockForm" method="get" class="form-horizontal" action="${base}/web/platformShipment/toExcel">
							<input name="orgType" id="orgType" type="hidden" value="P">
						</form>
						
					</div>
             </div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->


</body>
<#include "/footer.ftl" />
<script src="${uiBase}/vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}/vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script src="${uiBase}/js/pages/shopStock/shipment_list.js?v=${resourceVersion}" ></script>
<script id="warnStockDialog" type="text/html">
	<table id="warnStockList" class="table table-bordered table-hover" >
		<thead>
			<th>商家编号</th>
            <th>商家名称</th>
            <th>商家类别</th>
            <th>操作</th>
        </thead>
    	<tbody>
        </tbody>
	</table>
</script>
<script id="exportWarningDialog" type="text/html">
	<div class="box-body form-horizontal">
		<div class="form-group">
			<center>
				<button class='btn  btn-primary' id='exportWarningP'>导出区域平台商预警表</button>
				<button class='btn  btn-primary' id='exportWarningZ' style='margin-left: 30px;'>导出终端配送商预警表</button>
			</center>
		</div>
	</div>
</script>
<script id="warnStockShipmentDialog" type="text/html">
	<div class="box box-primary">
    	<div class="box-header with-border">
        	<div class="row">
            	<div class="col-md-12">
                	<div class="col-sm-4">                   
                    	<label class="control-label">商家编号: <span id="orgCodeShow"></span></label>
					</div>
					<div class="col-sm-4">
                    	<label class="control-label">商家名称: <span id="orgNameShow"></span></label>
					</div>
					<div class="col-sm-4">
						<label class="control-label">商家类别: <span id="orgCateShow"></span></label>
					</div>
            	</div>
        	</div>
        </div><!-- /.box-header -->
                    
        <div class="box-body">
        	<table id="warnStockShipmentList" class="table table-bordered table-hover" >
				<thead>
					<th>单瓶商品编号</th>
            		<th>单瓶商品名称</th>
            		<th>销售价</th>
            		<th>安全库存</th>
            		<th>标准库存</th>
            		<th>当前库存</th>
            		<th class="hideProductId"></th>
            		<th>新增库存</th>
        		</thead>
    			<tbody>
        		</tbody>
			</table>
        </div><!-- /.box-body -->
	</div>
</script>
</html>
