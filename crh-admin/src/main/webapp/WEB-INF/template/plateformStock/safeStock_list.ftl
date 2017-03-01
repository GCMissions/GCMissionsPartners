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
            <li> 安全库存管理</li>
             
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
                            		<select class="selectpicker form-control" name="orgCate" id="orgCate"> 
                            			<option value="" selected="selected">全部</option>
                             			<option value="Z" >终端配送商</option>
                                		<option value="P" >区域平台商</option>
                            		</select>
                            		
                    		</div>
                    		
                    		<div class="form-group ">	
                    		
								<label class="col-sm-2 control-label no-padding" style="text-align: right;"> 
									<button class="btn  btn-primary" id="search"><i class="fa fa-search"></i>开始搜索</button>
                            	</label>
                            	<label class="col-sm-1 control-label pull-left">   
                            		<button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>  
                            	</label> 
                            	
                            </div>
                    		
                    	</div><!-- /.box-body -->
                    </div><!-- /.search-group -->
                    
                    <div class="box-body">
                      <table id="safeStockList" class="table table-bordered table-hover" >
                        <thead>
                        	<th>序号</th>
                            <th>商家编号</th>
                            <th>商家名称</th>
                            <th>商家类别</th>
                            <th>安全库存</th>
                            <th>标准库存</th>
                            <th>操作</th>
                        </thead>
                        <tbody>
                        	
                        </tbody>
                      </table>
                    </div><!-- /.box-body -->
                    
                   
                </div>
             </div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->


</body>
<#include "/footer.ftl" />

<script src="${uiBase}/js/pages/shopStock/safeStock_list.js?v=${resourceVersion}" ></script>
</html>
