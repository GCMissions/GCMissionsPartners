<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">
<link href="${uiBase}css/pages/offline/index.css" rel="stylesheet">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow:scroll">
      	<div class="content-wrapper page-content-wrapper">
       		<section class="content-header">       
        		<ol class="breadcrumb">
		             <li><i class="fa fa-dashboard"></i>订单管理</li>
			   		<li>线下订单</li> 
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
					       			<span style="color:#e60012; font-size:14px;">扫码时请将输入法切换至英文模式</span>
					       		</div>
		       					<!-- 扫码信息 -->
		       					<div class="box-body">
			       					<div class="tab-content col-md-12" id="info">
				                      <table id="dataList" class="table table-bordered table-hover" >
				                        <thead>
				                            <th>商品名称</th>
				                            <th>商品编码</th>
				                            <th>价格</th>
				                            <th>规格</th>   
				                            <th>二维码明码</th>                         
				                            <th>操作</th>
				                        </thead>
				                        <tbody id="product_list">
				                        
				                        </tbody>
				                      </table>
				                    </div>
			                    </div><!-- /.box-body -->
			                    <div class="box-pay">
			                    	<div class="col-md-3 box-payInfo">
			                    		<p>订单号：<span class="orderId"></span></p>
				                    	<p>共计<span class="productNum">0</span>件商品</p>
				                    	<p>应付：￥<span class="productPay">0.00</span>元</p>
				                    	<p>实收：￥<span class="productRealPay">0.00</span>元</p>
			                    	</div>
			                    	<div class="col-md-2 box-payMethod">
			                    	    <input type="hidden" id="orderId "value="">
			                    	    <input type="hidden" id="actualAmount" value="">
				                    	<div><a class="btn order" id="orderSubmit">提交订单</a></div>
			                    	</div>
			                    	<div class="col-md-4 box-payMethod payMethodDiv hidden">			                    		
			                    		<div><a class="btn weixin payBtn" id="weixin">微信支付</a></div>	
			                    		<div><a class="btn pay payBtn" id="pay">支付宝</a></div>
			                    		<input type="text" class="hidden" id="paymentType" value=""> 
			                    	</div>			                    
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

<script id="payTpl" type="text/html">
	<div class="box-body">
		<div class="row form-group">    	
			<div class="form-group">
				<div class="col-sm-8"  style="padding-bottom:10px;">
	                <label class="paymethod control-label"></label>
	                <input type="text" class="form-control" name="authCode" id="authCode" style="width:60%">
					<p class="payStatus hidden"></p>
	            </div>
			</div>
	    </div>
	</div>
</script>

<script id="errorTpl" type="text/html">
	<div class="box-body">
		<div class="row form-group">    	
			<div class="form-group">
				<div class="col-sm-8"  style="padding-bottom:10px;">
	                <span class="errorMessage"></span>
	            </div>
			</div>
	    </div>
	</div>
</script>

<script type="text/javascript" src="${uiBase}js/pages/offline/index.js"></script>
</html>

