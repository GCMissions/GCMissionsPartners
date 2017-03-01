<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />

<style>
.u191_img {
	margin-top: -4px !important;
	width: 31px;
	height: 27px;
}
.ax_image {
	font-family: 'Arial Regular', 'Arial';
	font-weight: 400;
	font-style: normal;
	font-size: 13px;
	color: #000000;
	text-align: center;
	line-height: normal;
	display: inline;
	float: left;
}
.tdLabel {
	width: 120px;
}
.tdContext{
	width: 300px;
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
            <li><i class="fa fa-dashboard"></i> 收货管理</li>
             <li><i class="fa"></i> 收货管理</li>
            <li><i class="fa"></i> 收货管理详情</li>         
       </ol>
       </section>
        
        
        <!-- Main content -->
        <div class="row pad">
			<input type="hidden" id="stockId" value="${stockId}">
			<div class="box box-primary">
                <div class="box-header with-border">
                <div class="col-md-12">
                <br/><br/>               
				<div class="nav-tabs-custom">
					<!-- tab 页标题 -->
					<ul class="nav nav-tabs">
                  		<li class="active"><a href="#tab_1" data-toggle="tab">
                  		<h3 class="box-title">查看收货单</h3></a></li>
               		 </ul>
               		 <input type="hidden" value="" id="referUrl"/>
	               	<!-- 订单信息 -->
	                <div class="tab-content">
	                	<div class="tab-pane active" id="tab_1">
		                   <table class="table table-condensed">
		                    	<tr>
		                    		<td class="tdLabel"><b>收货单号:</b></td><td class="tdContext">${shipmentDto.orderCode}</td>
		                    		<td class="tdLabel"><b>创建时间:</b></td><td class="tdContext">${shipmentDto.shipmentDate}</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>发货状态:</b></td><td class="tdContext">${shipmentDto.status}</td>
		                    		<td class="tdLabel"><b>收货数量:</b></td><td class="tdContext">${shipmentDto.totalNum}</td>
		                    	</tr>
		                    </table>
	                  	</div>
	                </div>
	                
                         <table id="dataList" class="table table-bordered table-hover" >
	                        <thead>
	                        <th>单瓶商品编号</th>
                            <th>单瓶商品名称</th>
                            <th>销售价</th>
                            <th>收货数量</th>
	                        </thead>
	                        <tbody> 
	                        	<#list shipmentDto.shipmentDetailDtos as detail>
	                        		<tr>
	                        			<td>${detail.goodCode}</td>
	                        			<td>${detail.goodName}</td>
	                        			<td>${detail.priceYuan}</td>
	                        			<td>${detail.num}</td>
	                        		</tr>
	                            </#list>
	                        </tbody>
	                      </table>
	                
				</div>
				</div>
				</div>
			</div>
        </div>
        
                                         <#if shipmentDto.status == "已创建"||shipmentDto.status == "已收货"> 
	                        			   <div class="col-sm-3 col-sm-offset-5">
                                              <button class="btn btn-success" id="goBack"><i class="fa fa-backward"></i> 返  回</button>
			                              </div>
						                   <#else>
						                    <div class="col-sm-3 col-sm-offset-5">
        	                             	  <button class="btn  btn-primary" id="confirmGet" > 扫码收货</button>
                                              <button class="btn btn-success" id="goBack"><i class="fa fa-backward"></i> 返  回</button>
			                               </div>
	                        		      </#if>
                                         
        
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->


</body>
<#include "/footer.ftl" />
 <script id="shipmentDialog"  type="text/html">
<div class="box-body form-horizontal">
	<div class="form-group row">
		<div class="col-sm-2 "> </div>
		<textarea class="col-sm-8 " rows="3"> </textarea>
	</div>
</div>
</script>
<script>
$(function() {
	var barcodeUrl = $.GLOBAL.config.urlPrefix +'barcode/index/4/${shipmentDto.orderCode}';
	$("body").on('click', '#confirmGet', function(e){
		  window.location.href = barcodeUrl ;
	});
	
	
	$('#goBack').on('click',function(){
		window.location.href = $.GLOBAL.config.urlPrefix + "shipment/list";
	});
	
});
</script>
</html>
