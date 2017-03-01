<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">
<link href="${uiBase}css/pages/barcode/shipmentIndex.css" rel="stylesheet">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow:scroll">
      	<div class="content-wrapper page-content-wrapper">
       		<section class="content-header">
      		 	<ol class="breadcrumb">
		            <li><i class="fa fa-dashboard"></i> 订单管理</li>
		            <li><i class="fa"></i> 退货订单管理</li>
		            <li><i class="fa"></i> 扫码退货</li>
		            <li><i class="fa"></i> 收货详情</li> 
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
			      		 		<!-- 订单信息  -->
					       		<div>
					       			<span>请扫码：</span><input type="text" class="form-control" id="shipmentInput" style="width:300px;" />
					       		</div>
		       					<!-- 扫码信息 -->
		       					<div class="tab-content col-md-6" id="info">
		       						<div>
		       							<table id="dataList" class="table table-bordered table-hover dataList table-striped">
		       							<thead>
		       								<tr>
			       								<th>单瓶名称</th>
			       								<th>待退货</th>
			       								<th>已扫码</th>
			       								<th>操作</th>
		       								</tr>
		       							</thead>
		       							<tbody id="product_list">
		       								<#list orderDetail.data.goodDtos as list>
					       					<tr data-id=${list.goodId}>
					       						<td>${list.goodName}</td>
					       						<td id="data_${list.goodId}">${list.goodNum}</td>
					       						<td id="num_ok${list.goodId}">0</td>
					       						<td><a title="查看" href="" data-toggle="modal" data-target="#myModal${list.goodId}"> <i class="fa fa-eye" style="font-size:20px"></i></a></td>
					       					</tr>
					       					<input type="hidden" id="data${list.goodId}" value="">
											</#list>
										</tbody>
		       							</table>
		       						</div>
		       						<#list orderDetail.data.goodDtos as list>
		       						<div>
		       						<div class="modal fade" id="myModal${list.goodId}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
									   <div class="modal-dialog">
									      <div class="modal-content">
									      	 <div class="modal-header">
									      	 	<h4>扫码号</h4>
									      	 </div>
									         <div class="modal-body">
									         	<input type="hidden" class="pId" value=${list.goodId}>
									            <ul id="codeList${list.goodId}">
									            	
									            </ul>
									         </div>
									         <div class="modal-footer">
									            <button type="button" class="btn btn-default submitOk" data-dismiss="modal">确定
									            </button>
									         </div>
									      </div><!-- /.modal-content -->
									</div><!-- /.modal -->
									</div>
									</#list>
		       					</div><!-- /.tab-content -->
					       		<div class="group-btn text-center">
					       			<a class="btn btn-default" id="cancel" onClick="history.go(-1);">取消</a>
			       					<a class="btn btn-primary" id="returnConfirm">确认退货</a>
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
<script type="text/javascript" src="${uiBase}js/pages/barcode/shipmentIndex.js"></script>
</html>

