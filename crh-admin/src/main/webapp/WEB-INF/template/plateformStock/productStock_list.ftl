<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">
<style>
.datagrid-header-input{
    display: inline-block;
    width: 160px;
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
            <li> 单瓶商品库存管理</li>
             
          </ol>
          
         </section>
        
       
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                
                	<div class="form-horizontal search-group" >
                    	<div class="box-body">
                    		<div class="form-group">
                    			 
                    			<label class="col-sm-2 control-label">单瓶商品编号：</label>
								<input  class="form-control col-sm-2" id="goodsCode" type="text">
                    			                  
                            	<label class="col-sm-2 control-label">单瓶商品名称：</label>
								<input  class="form-control col-sm-2" id="goodsName" type="text">
								
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
                    </div>
                    
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<th>序号</th>
                            <th>单瓶商品编号</th>
                            <th>单瓶商品名称</th>
                            <th>销售价</th>
                            <th>库存总量</th>
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

<script src="${uiBase}/js/pages/shopStock/productStock_list.js?v=${resourceVersion}" ></script>
</html>
