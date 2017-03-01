<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">
<link href="${uiBase}css/pages/barcode/adminIndex.css" rel="stylesheet">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow:scroll">
      	<div class="content-wrapper page-content-wrapper">
       		<section class="content-header">       
        		<ol class="breadcrumb">
		            <li><i class="fa fa-dashboard"></i> 库存管理</li>
		            <li><i class="fa"></i> 平台库存管理</li>
		            <li><i class="fa"></i> 扫码入库</li>
		            <li><i class="fa"></i> 扫码入库详情</li> 
      		 	</ol>
       		</section>
       		<input type="hidden" id="orderId" value ="${orderId}" />
       		<input type="hidden" id="orderStatus" value ="${status}" />
       		<!-- Main content -->
       		<div class="row pad">
       			<div class="box box-primary">
       				<div class="box-header with-border">
       					<div class="col-md-12">
       						<div class="box-body">
       							<input type="hidden" value="${url}" id="referUrl"/>
			      		 		<!-- 订单信息  -->
					       		<div>
					       			<span>请扫码：</span><input type="text" class="form-control" id="shipmentInput" style="width:300px;" />
					       			<span style="color:#e60012; font-size:14px;">请将输入法切换至英文模式</span>
					       		</div>
		       					<!-- 扫码信息 -->
		       					<div class="box-body">
			       					<div class="tab-content col-md-6" id="info">
				                      <table id="dataList" class="table table-bordered table-hover" >
				                        <thead>
				                            <th>单瓶商品名称</th>
				                            <th>数量</th>                            
				                            <th>操作</th>
				                        </thead>
				                        <tbody id="product_list">
				                        	
				                        </tbody>
				                      </table>
				                    </div>
			                    </div><!-- /.box-body -->
					       		<div class="group-btn text-center">
					       			<a class="btn btn-default" id="cancel" onClick="history.go(-1);">取消</a>
			       					<a class="btn btn-primary" id="save">确认收货</a>
			       				</div>
	       					</div><!-- /.box-body -->
	       				</div><!-- /.col-md-12 -->
	       			</div><!-- /.box-header -->
	       		</div><!-- /.box -->
       		</div><!-- /.row -->
       		<!-- content -->
       		<div class="clearfix"></div>
		</div><!-- /.content-wrapper -->
	</div><!-- ./wrapper -->
</body>
<#include "/footer.ftl" />
<script src="${uiBase}/vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}/vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script type="text/javascript" src="${uiBase}js/pages/barcode/adminIndex.js"></script>
</html>

