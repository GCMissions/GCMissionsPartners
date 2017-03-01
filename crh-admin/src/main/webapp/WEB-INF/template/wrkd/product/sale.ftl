<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}css/pages/product.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}css/pages/wrkd/product/sale.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
    	<div class="content-wrapper page-content-wrapper">
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 商品管理</li>
					<li>商品上下架</li>
				</ol>
			</section>
			<!-- Main content -->
        	<div class="pad">
                <div class="box box-primary">
                     <div class="box-header with-border">
						<input id="productCode" class="form-control" type="text" placeholder="商品编号" />
                        <input id="productName" class="form-control" type="text" placeholder="商品名称">
						<label> 
							<input type="radio" name="saleStatus" value="" checked/>全部&nbsp;&nbsp;
							<input type="radio" name="saleStatus" value="0" />未上架&nbsp;&nbsp;
							<input type="radio" name="saleStatus" value="2" />已上架
						</label> 
						<input type="hidden" id="saleStatus">
						<button type="button" class="btn btn-primary" id="searchBtn">
							<i class="fa fa-search"></i> 查询
						</button>
					 </div>
                     
                     <div class="box-body">
                     	<table id="dataList" class="table table-bordered table-hover dataList" >
                        	<thead>
	                            <th>商品编号</th>	                            
	                            <th>商品名称</th>
	                            <th>价格（元）  </th>
	                            <th>上架时间</th>
	                            <th>下架时间</th>
	                            <th>上架状态  </th>
	                            <th>操作</th>
                        	</thead>
	                        <tbody>
	                        	   
	                        </tbody>
                      	</table>
                    </div><!-- /.box-body -->
             </div>
        </div><!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
	
	<!-- 上架时间选择模态框（Modal） -->
	<div class="modal fade" id="dateDialog" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">上架申请</h4>
				</div>
				<div class="modal-body" product-id="">
					<div class="box-body form-horizontal">
						<div class="form-group row">
							<label class="col-sm-3 control-label">上架时间</label>
							<div class="col-sm-9 dateDiv" style="margin-bottom: 0px;">
								<input size="25" type="text" id="startDate" class="form-control keyword" readonly> 
								<span class="add-on" style="display: none"><i class="icon-remove"></i></span> 
								<span class="add-on"><i class="icon-calendar"></i></span>
							</div>
						</div>
						<div class="form-group row">
							<label class="col-sm-3 control-label">下架时间</label>
							<div class="col-sm-9 dateDiv" style="margin-bottom: 0px;">
								<input size="25" type="text" id="endDate" class="form-control keyword" readonly> 
								<span class="add-on" style="display: none"><i class="icon-remove"></i></span> 
								<span class="add-on"><i class="icon-calendar"></i></span>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="onSaleComfirm">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
	</div>
	<!-- /.modal -->
	<!-- 上架时间选择模态框（Modal） -->
	
<#include "/footer.ftl" /> 
<script src="${uiBase}vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>

<script type="text/javascript" src="${uiBase}js/pages/wrkd/product/sale.js?v=${resourceVersion}"></script> 

</body>
</html>