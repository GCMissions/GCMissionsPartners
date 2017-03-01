<#assign headComponents = ["bootTable","bootDialog"] >
<#include "/header.ftl" />
<style>
	label.role_checkbox {
   		font-weight: 500;
   	    min-width: 38%;	
   	    margin-top: 6px;
	}
	.form-control {
    	width: 68%;
	}
	.fa-edit, .fa-trash, .fa-eye { 
		cursor: pointer;	
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
            <li><i class="fa fa-dashboard"></i> 权限管理</li>
          	<li>二维码查询</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group" id="search-area" >
                    	<div class="box-body">
                    		 <div class="form-group col-sm-12">
                    		 	<label class=" control-label text-left" style="margin-left: 2%;">二维码：</label>
					           	<input id="text-input" name="prefixCode" class="form-control " type="text">
					           	
                    		 </div>
                    		 <div class="form-group">   
					            <label class="col-sm-2 control-label">   
					                <button type="button" id="searchBtn" class="btn btn-primary"><i class="fa fa-search"></i> 开始搜索</button> 
					            </label>
					            <form  method="post" enctype="multipart/form-data">
					                	<input type="file" id="excel" name="excel" class="btn" style="display:none">
					                <label class="col-sm-1 control-label">
					                <button id="importExcel" type="button" class="btn btn-primary "><i class="fa"></i> 导入excel</button>
					                 </label>   
					                                                    
					            </form>
					              <label class="col-sm-2 control-label">
					                <a id="downloadTemplet" type="button" class="btn btn-primary " href="${uiBase}barcodeTemplet.xlsx"><i class="fa"></i> 下载模板</a>
					              </label>  
                          
					        </div>
                    		 
                    	</div>
                    </div>
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<!-- <th field="brand_id" width=70><div class="datagrid-header-check"><input type="checkbox"></div></th> -->
                        	<th>序号</th>
                        	<th>流水ID</th>
                            <th>来源</th>
                        	<th>状态</th>
                            <th>发货方名称</th>
                            <th>收货方名称</th>
                            <th>订单ID</th>
                            <th>创建时间</th>
                            <th>创建人</th>
                            <th>母码</th>
                            <th>流水说明</th>
                        </thead>
                        <tbody>
                        </tbody>
                      </table>
                    </div>
                </div>
             </div>
        </div>
        <div class="clearfix"></div>
        
      </div>
   </div>


</body>
<#include "/footer.ftl" />
<script type="text/javascript" src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script>
<script type="text/javascript" src="${uiBase}js/pages/barcodeCycle/barcodeCycle.js?v=${resourceVersion}"></script>

</form>
</script>
 <script type="text/javascript" src="${uiBase}vendor/jquery-md5/jquery.md5.js"></script>
</html>