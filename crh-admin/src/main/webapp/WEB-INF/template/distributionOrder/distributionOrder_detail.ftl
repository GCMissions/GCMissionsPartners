<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">

<style>
.u_img {
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
            <li><i class="fa fa-dashboard"></i> 订单管理</li>
            <li><i class="fa"></i> 订单管理</li>
            <li><i class="fa"></i> 订单管理详情</li>         
       </ol>
       </section>

			<!-- <div class="box-body">
				 <#if distributionOrderDto.status == "待接单"> <label
					class="col-sm-2 control-label">
					<button class="btn  btn-primary" id="getGoods">接单</button>
				</label> <#else> 
				
				<#if distributionOrderDto.status == "待付款"> <#else> <label
					class="col-sm-2 control-label">
					<button class="btn  btn-default" id="alreadyGetGoods">已接单</button>
				</label> 
				</#if> 
				</#if> 
				
				<#if distributionOrderDto.status == "待发货"||distributionOrderDto.status == "待接单"> <label
					class="col-sm-2 control-label">
					<button class="btn  btn-primary" id="sendGoods">发货</button>
				</label> 
				
				<#else>
				  <label class="col-sm-2 control-label">
					<button class="btn  btn-default" id="alreadySendGoods">已发货</button>
				</label> 
				</#if>
			</div> -->
						
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
						<#if "shipment" != active> 
							<li class="active">
						<#else>
							<li>
						</#if>
                  		<a href="#tab_1" data-toggle="tab">
                  		<h3 class="box-title">订单信息</h3></a></li>
                  		
                  		<li><a href="#tab_2" data-toggle="tab">
                  		<h3 class="box-title">商品信息</h3></a></li>  
                  		 
                  		<li><a href="#tab_3" data-toggle="tab">
                  		<h3 class="box-title">支付信息</h3></a></li> 
                  		  
                  		
                  		<!-- 已完成的订单 显示“评价信息” -->
                  		<#if distributionOrderDto.status =="已完成"> 
                  		<li><a href="#tab_4" data-toggle="tab">
                  		<h3 class="box-title">评价信息</h3></a></li>   
                  		</#if>
               		 </ul>              		 
	               	<!-- 订单信息 -->
	                <div class="tab-content">
              			<#if "shipment" != active> 
						<div class='tab-pane active' id="tab_1">
						<#else>
						<div class='tab-pane' id="tab_1">
						</#if>
		                <table class="table table-condensed">
		                    	<tr>
		                    		<td class="tdLabel"><b>订单编号:</b></td><td class="tdContext">${distributionOrderDto.orderId}</td>
		                    		<td class="tdLabel"><b>订单状态:</b></td><td class="tdContext">${distributionOrderDto.status}</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>下单时间:</b></td><td class="tdContext">${distributionOrderDto.createDate}</td>
		                    		<td class="tdLabel"><b>订单金额:</b></td><td class="tdContext">${distributionOrderDto.totalAmount}</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>收货方式:</b></td><td class="tdContext">${distributionOrderDto.shipmentType}</td>
		                    		<td class="tdLabel"><b>收货时间:</b></td><td class="tdContext">${distributionOrderDto.finishDate}</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>收货人:</b></td><td class="tdContext">${distributionOrderDto.memberName}</td>
		                    		<td class="tdLabel"><b>联系方式:</b></td><td class="tdContext">${distributionOrderDto.phone}</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>收货地址:</b></td><td class="tdContext">${distributionOrderDto.address}</td>
		                    		
		                    		<#if distributionOrderDto.shipmentType== "自提"> 
						           <#else>
						           <td class="tdLabel"><b>配送时间:</b></td><td class="tdContext">${distributionOrderDto.recevingTime}</td>
						           </#if>		                    		
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>发货时间:</b></td><td class="tdContext">${distributionOrderDto.shipmentDate}</td>
		                    	</tr>
		                    </table>
	                  	</div>
	                  	<!-- 订单信息 end -->
	                  	<!-- 商品信息 -->
	                  	<div class="tab-pane" id="tab_2">
		                    <table class="table table-bordered table-hover" >
	                        <thead>
	                            <th>序号</th>
	                            <th>商品条码</th>
	                            <th>商品名称</th>
	                            <th>品牌</th>
	                            <th>分类</th>
	                            <th>数量（件）</th>
		                        <th>单价</th>
		                        <th>小计</th>
	                        </thead>
	                        <tbody> 
	                        	<#list distributionOrderDto.distributionOrdeProductDto as detail>
	                        		<tr>
	                        		    <td>${detail_index+1}</td>
	                        			<td>${detail.sn}</td>
	                        			<td>${detail.productName}</td>
	                        			<td>${detail.brand}</td>
	                        			<td>${detail.category}</td>
	                        			<td>${detail.num}</td>
	                        			<td>${detail.price}</td>
	                        			<td>${detail.totalPrice}</td>
	                        		</tr>
	                        	</#list>
	                        </tbody>
	                      </table>
	                  	</div>
	                  	<!-- 商品信息 end -->
	                  	<!-- 支付信息 -->
	                  	<div class="tab-pane" id="tab_3">
		                     <table class="table table-bordered table-hover" >
	                        <thead>
	                            <th>订单编号</th>
	                            <th>支付方式</th>
	                            <th>支付状态</th>
	                            <th>订单金额(元)</th>
	                            <th>优惠券金额(元)</th>
		                        <th>实际支付(元)</th>
		                        <th>付款日期</th>
	                        </thead>
	                        <tbody> 
	                        	<#list distributionOrderDto.distributionOrdePayDtos as detail>
	                        		<tr>
	                        			<td>${detail.orderId}</td>
	                        			<td>${detail.billType}</td>
	                        			<td>${detail.status}</td>
	                        			<td>${detail.amount}</td>
	                        			<td>${detail.coupon}</td>
	                        			<td>${detail.actualAmount}</td>
	                        			<td>${detail.payTime}</td>
	                        		</tr>
	                        	</#list>
	                        </tbody>
	                      </table>
	                  	</div>
	                  	<!-- 支付信息 end -->
	                  	<!-- 评价信息 -->
	                  		<div class="tab-pane" id="tab_4">
		                    <table class="table table-condensed">
		                    	<tr>
		                    		<td class="tdLabel"><b>服务态度:</b></td>
		                    		<td>
		                    		<#list 1..distributionOrderDto.attitudeLevel as i>
		                    				<div class="ax_image">
											 	<img class="u_img" class="img " src="${uiBase}/img/u175.png">
											     <div id="u192" class="text" style="top: 14px; -webkit-transform-origin: 13.5px -0.5px;">
								                <p><span></span></p>
								              </div>
	            							</div>
	            					</#list>
		                    		</td>	
		                    	</tr>
		                    		<tr>
		                    		<td class="tdLabel"><b>配货速度:</b></td>
		                    		<td>
		                    			<#list 1..distributionOrderDto.shipmentLevle as i>
		                    				<div class="ax_image">
											 	<img class="u_img" class="img " src="${uiBase}/img/u175.png">
											     <div id="u192" class="text" style="top: 14px; -webkit-transform-origin: 13.5px -0.5px;">
								                <p><span></span></p>
								              </div>
	            							</div>
	            						</#list>
		                    		</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>服务体验:</b></td>
		                    		<td>
		                    			<#list 1..distributionOrderDto.serviceLevel as i>
		                    				<div class="ax_image">
											 	<img class="u_img" class="img " src="${uiBase}/img/u175.png">
											     <div id="u192" class="text" style="top: 14px; -webkit-transform-origin: 13.5px -0.5px;">
								                <p><span></span></p>
								              </div>
	            							</div>
	            						</#list>
		                    		</td>
		                    	</tr>
		                    </table>
	                  	</div>
	                  	<!-- 评价信息  end -->
	                </div>
				</div>
				</div>
				</div>
			</div>
        </div>
        
        <div class="row">
				<div class="col-md-12 text-center">
					<button class="btn btn-success backPage" type="button">
						<i class="fa fa-backward"> 返回 </i>
					</button>
				</div>
			</div>
        
       <!-- /.content -->
        <div class="clearfix"></div>
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
</body>
<#include "/footer.ftl" />
<input type="hidden" name="orderId" id="orderId" value="${distributionOrderDto.orderId}"/>
<script type="text/javascript" src="${uiBase}/vendor/jquery-validator/jquery.validate.min.js"></script>
<script type="text/javascript">
$(function() {
	var orderId = $("#orderId").val();
	jQuery.validator.addMethod("checkNumber", function(value, element) { 
		return this.optional(element) || /^\d+$/.test(value); 
	});
	// 表单验证
	$("#exForm").validate({
		rules: {
			exName: {
				required:true
			},
			exNum: {
				required:true,
				checkNumber:true
			}
		},
		messages: {
			exName :{
				required:"必填"
			},
			exNum: {
				required:"必填",
				checkNumber:"订单号只能数字"
			}
		},errorPlacement: function(error, element) { 
			$(element).after(error);
		}, 
		submitHandler: function(form) {
			form.submit();
		}
	}); 
	
	
	var getUrl = $.GLOBAL.config.urlPrefix + 'distributionOrder/getGoods/'+orderId
	$("#getGoods").click(function(){
		$.ajax({
			url:getUrl,
			type:"get",
			dataType : "json",
			data : null,
			contentType : "application/json; charset=utf-8",
			success:function(msg){
				 $('body').loadingInfo("success", "接单成功！");
			     $("#getGoods").removeClass('btn-primary');
				 $("#getGoods").addClass('btn-default');
				 $("#getGoods").html("已接单");
				 $("#getGoods").attr('id','alreadyGetGoods');
				 window.location.reload();
			}
		});
	});
	
	var barcodeUrl =$.GLOBAL.config.urlPrefix +'barcode/index/5/'+orderId;
	$("#sendGoods").click(function(){
		<#if distributionOrderDto.status != "待发货"> 
		$('body').loadingInfo("error", "需先接单才能发货！");
	     <#else>
			window.location.href=barcodeUrl;
		</#if>
	     
	});
	
});
</script>
</html>
