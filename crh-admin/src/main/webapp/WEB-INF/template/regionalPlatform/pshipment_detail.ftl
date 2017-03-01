<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 


</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
          <ol class="breadcrumb">
			<li><i class="fa fa-dashboard"></i> 库存管理</li>
        
			<li>发货管理</li>
          <li>
            查看发货单
            
          </li>
		  </ol>
        </section>
        
        
        <!-- Main content -->
        <div class="row pad">
			<div class="box box-primary">
			<input type="hidden" id="orderId" value="${order.orderId}">
                <div class="box-header with-border">
                <div class="col-md-12">
                <br/><br/>
	                <div class="box-body">
		                    <table class="table table-condensed">
		                    	<tr>
		                    		<td><b>发货单号:</b></td><td>${order.orderId}</td>
		                    		<td><b>创建时间:</b></td><td>${order.createDate}</td>
		                    	</tr>
		                    	<tr>
		                    		<td><b>发货状态:</b></td><td>${order.status}</td>
		                    		<td><b>发货数量:</b></td><td>${order.totalNum}</td>
		                    	</tr>
		                    	
		                    	<tr>
		                    		<td><b>终端配送商编号:</b></td><td>${order.regNo}</td>
		                    		<td><b>终端配送商名称:</b></td><td>${order.regName}</td>
		                    	</tr>
		                    	
		                    	<tr>
		                    		<td><b>收货人:</b></td><td>${order.contact}</td>
		                    		<td><b>联系电话:</b></td><td>${order.phone}</td>
		                    	</tr>
		                    	
		                    	<tr>
		                    		<td><b>收货地址:</b></td><td colspan="3">${order.address}</td>
		                    	</tr>
		                    </table>
	                  	<div class="" >
		                    
		                    <table id="dataList" class="table table-bordered table-hover" >
	                        <thead>
	                            <th>单瓶商品编号</th>
	                            <th>单瓶商品名称</th>
	                            <th>销售价</th>
	                            <th>发货数量</th>
	                        </thead>
	                        <tbody> 	
	                        </tbody>
	                      </table>
	                  	</div>
	                </div>
				</div>
				</div>
			</div>
        </div>
        <div class="col-md-12">
        	<div class="col-sm-3 col-sm-offset-5">
        	<#if statusId == 0>
        		<button class="btn  btn-default" id="shipment">扫码发货</button>
        	</#if> 
                <button  class="btn btn-success" id="goBack"><i class="fa fa-backward"></i> 返  回</button >
			</div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->


</body>
<#include "/footer.ftl" />

<script>
$(function() {
	
	$('#goBack').on('click',function(){
		window.location.href = $.GLOBAL.config.urlPrefix + "pshipment/" ;
	});
	$('#shipment').on("click", function() {
		 var orderId =$("#orderId").val();
		window.location.href = $.GLOBAL.config.urlPrefix +"barcode/index/3/"+orderId;
	});
	$.GLOBAL.utils.loadBootTable({
		table : $('#dataList'),
	    refreshBtn : $('#search'),
	    url: 'pshipment/detailList',
	    pagination : false,
	    queryParamsType: "limit",
	    queryAddParams: function() {
	    	return {
	    		orderId: $("#orderId").val()
	    	}
	    },
	    columns: [
	  	        {
	  	            field:'goodCode'
	  	        },
	  	        {
	  	            field:'goodName'
	  	        }, 
	  	        {
	  	            field:'priceYuan'
	  	        }, 
	  	        {
	  	            field:'num'
	  	        }
	  	     ]
	});
});
</script>
</html>
