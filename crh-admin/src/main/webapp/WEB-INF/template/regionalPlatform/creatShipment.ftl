<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">
<style>
.datagrid-header-input{
    display: inline-block;
    width: 160px;
}
.search-group input.form-control {
  width: 210px;
}
.input-group[class*=col-] {
	float: left;
}
.input-group td[data-name=orgId]{
	display:none;
}
#chooseProduct{
	margin-left:35px;
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
            		<li> 创建发货单</li> 
          		</ol>
           </section>
        
       
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                
                	<div class="form-horizontal search-group" >
                    	<div class="box-body">
                    		<div class="form-group">   
                        		<label class="col-sm-2 control-label" for="type-select">商家类别：</label>
                        		<div class="col-sm-3 input-group">
                                    <select class="col-sm-1 selectpicker form-control" name="paymentStatus" id="orgCate">
                                        <option value="Z" selected="selected">终端配送商</option>
                                    </select>
                               </div> 
                               
                            	<label class="col-sm-2 control-label" for="type-select">请选择商家:</label>
                            	<div class="col-sm-3 input-group">
                            		<input type="text" class="form-control" id="chooseOrg">
                            		<div class="input-group-btn">
                            			<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                            				<span class="caret"></span>
                            			</button>
                            			<ul class="dropdown-menu dropdown-menu-right" role="menu"></ul>
                        			</div>
                        		</div><!-- /btn-group -->
								<button class="btn  btn-primary" id="chooseProduct">选择单瓶商品</button>
                    			<!--<select  class="selectpicker form-control" id="chooseShop" data-live-search="true"></select>-->
                    		</div>
                    	</div><!-- /.box-body -->
                    </div>
                    
                    <div class="box-body">
                      <table id="relatedGoodsListTable" class="table table-bordered table-hover" >
                        <thead>
                        	<th>序号</th>
                            <th>单瓶商品编号</th>
                            <th>单瓶商品名称</th>
                            <th>销售价</th>
                            <th>库存总量</th>
                            <th>发货数量</th>
                        </thead>
                        <tbody>
                        	
                        </tbody>
                      </table>
                    </div><!-- /.box-body -->
                    
                   
                </div>
             </div>
        </div>
       <!-- /.content -->
       
       
        <div class="col-sm-3 col-sm-offset-5">
        	<button class="btn  btn-success" id="createShipment" href="# ">创建发货单</button>
        	<button class="btn  btn-success" id="back" href="# "><i class="fa fa-backward"></i> 返  回</button>
		</div>
       
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->


</body>
<#include "/footer.ftl" />
<script src="${uiBase}/vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}/vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script src="${uiBase}/js/pages/shopStock/bootstrap-suggest.min.js?v=${resourceVersion}" ></script>
<script src="${uiBase}/js/pages/regionalPlatform/creatShipment.js?v=${resourceVersion}" ></script>
</html>
