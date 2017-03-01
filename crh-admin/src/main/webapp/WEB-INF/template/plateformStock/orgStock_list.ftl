<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">
<style>
.datagrid-header-input{
    display: inline-block;
    width: 160px;
}
.bootstrap-select {
  width: 161px !important;
}

.bootstrap-select.btn-group:not(.input-group-btn), .bootstrap-select.btn-group[class*="col-"] {
	float:left;
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
            <li> 商家库存管理</li>
             
          </ol>
          
         </section>
        
       
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                
                	<div class="form-horizontal search-group" >
                    	<div class="box-body">
                    	
                    	<form id="stockForm" method="get" action="${base}/web/orgStock/export">
                    	
                    		<div class="form-group">
                    		                  
                            	<label class="col-sm-2 control-label">商家编号：</label>
								<input class="form-control col-sm-2" id="orgCode" name="orgCode" type="text">
								
								<label class="col-sm-2 control-label">商家名称：</label>
								<input  class="form-control col-sm-2" id="orgName" name="orgName" type="text">
								
								<label class="col-sm-2 control-label" for="type-select">库存状态：</label>
                           		<select class="col-sm-1 selectpicker form-control" name="orgState" id="orgState"> 
                             		<option value="" selected="selected">全部</option>
                                	<option value="1" >预警</option>
                                	<option value="0" >正常</option>
                             	</select>
                            	
                    		</div>
                    		
                    		<div class="form-group">
                    			
								<label class="col-sm-2 control-label" for="type-select">商家类别：</label>
                            	<select class="col-sm-1 selectpicker form-control" name="orgCate" id="orgCate"> 
                             		<option value="" selected="selected">全部</option>
                             		<option value="Z" >终端配送商</option>
                                	<option value="P" >区域平台商</option>
                            	</select>
                            	
                            	<label class="col-sm-2 control-label" for="type-select">关联区域平台商：</label>
                            	<select class="col-sm-1 selectpicker form-control" name="orgP" id="orgP" disabled> 
                             		<option value="" selected="selected">全部</option>
                             		<#list orgP as p>
                             		<option value="${p.orgId}" >${p.orgName}</option>
                             		</#list>
                            	</select>
                            	
                    		</div>
                    		
                    		<div class="form-group ">	
                    		
								<label class="col-sm-2 control-label no-padding" style="text-align: right;"> 
									<button type="button" class="btn  btn-primary" id="search"><i class="fa fa-search"></i> 开始搜索</button>
                            	</label>
                            	<label class="col-sm-1 control-label pull-left">   
                            		<button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>  
                            	</label> 
                            	
                            	<label class="col-sm-1 control-label pull-left">
									<button data-exceltype="1" id="excel" class="btn btn-default" type="button"><i class="fa  fa-refresh"></i> 导出</button>
								</label>
								
								<label class="col-sm-2 control-label">库存量百分比：</label>
								<input class="form-control col-sm-2" id="orgPct" name="orgPct" type="tel" value="" maxlength="3">
                            	
                            </div>
                    			
                    	</form>
                    	
                    	</div><!-- /.box-body -->
                    </div><!-- /.search-group -->
                    
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<th>序号</th>
                            <th>商家编号</th>
                            <th>商家名称</th>
                            <th>商家类别</th>
                            <th>库存总量(件)</th>
                            <th>标准库存(件)</th>
                            <th>库存状态</th>
                            <th>操作</th>
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

</body>
<#include "/footer.ftl" />
<script src="${uiBase}vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script src="${uiBase}/js/pages/shopStock/orgStock.js?v=${resourceVersion}" ></script>
</html>
