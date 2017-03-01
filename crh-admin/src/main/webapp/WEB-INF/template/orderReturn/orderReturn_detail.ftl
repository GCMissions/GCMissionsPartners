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
            <li><i class="fa fa-dashboard"></i>订单管理</li>
            <li>订单详情</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
        <div class="col-md-12">
			<input type="hidden" id="stockId" value="${stockId}">
				<div class="nav-tabs-custom">
					<!-- tab 页标题 -->
					<ul class="nav nav-tabs">
						<#if "shipment" != active> 
							<li class="active">
						<#else>
							<li>
						</#if>
                  		<a href="#tab_1" data-toggle="tab">
                  		订单信息</a></li>
                  		
                  		<li><a href="#tab_2" data-toggle="tab">
                  		商品信息</a></li>  
                  		 
                  		<li><a href="#tab_3" data-toggle="tab">
                  		支付信息</a></li> 
                  		  
                  		<#if "shipment" == active> 
							<li class="active">
						<#else>
							<li>
						</#if>
                  		<a href="#tab_4" data-toggle="tab">
                  		配送信息</a></li> 
                  		
                  		<!-- 已完成的订单 显示“评价信息” -->
                  		<#if info.order.status ==5> 
                  		<li><a href="#tab_5" data-toggle="tab">
                  		评价信息</a></li>   
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
		                    		<td class="tdLabel"><b>订单编号:</b></td><td class="tdContext">${info.order.orderId}</td>
		                    		<td class="tdLabel"><b>订单时间:</b></td><td class="tdContext">${info.order.createDate}</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>订单状态:</b></td><td class="tdContext">${info.order.statusStr}</td>
		                    		<td class="tdLabel"><b>收货人:</b></td><td class="tdContext">${info.order.contact}</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>联系电话:</b></td><td class="tdContext">${info.order.phone}</td>
		                    		<td class="tdLabel"><b>收货时间:</b></td><td class="tdContext">${info.order.receivingDate}</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>收货地址:</b></td><td colspan="3">${info.order.address}</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>配送方式:</b></td><td class="tdContext">${info.order.shipmentTypeStr}</td>
		                    		<#if info.order.shipmentType == 2> 
		                    		<td class="tdLabel"><b>配送时间:</b></td><td class="tdContext">${info.order.recevingTime}</td>
		                    		</#if>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>发货时间:</b></td><td class="tdContext"><#if info.order.shipmentDate??>${info.order.shipmentDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
		                    	</tr>
		                    	
		                    </table>
	                  	</div>
	                  	<!-- 订单信息 end -->
	                  	<!-- 商品信息 -->
	                  	<div class="tab-pane" id="tab_2">
		                    <table class="table table-bordered table-hover" >
	                        <thead>
	                            <th>商品条码</th>
	                            <th>商品名称</th>
	                            <th>品牌</th>
	                            <th>分类</th>
	                            <th>数量（件）</th>
		                        <th>单价</th>
		                        <th>小计</th>
	                        </thead>
	                        <tbody> 
	                        	<#if info.details?? >
	                        	<#list info.details as detail>
	                        		<tr>
	                        			<td>${detail.proudctCode}</td>
	                        			<td>${detail.proudctName}</td>
	                        			<td>${detail.brandName}</td>
	                        			<td>${detail.catalogName}</td>
	                        			<td>${detail.num}</td>
	                        			<td>${detail.priceDisplay}</td>
	                        			<td>${detail.amountDisplay}</td>
	                        		</tr>
	                        	</#list>
	                        	</#if>	
	                        </tbody>
	                      </table>
	                  	</div>
	                  	<!-- 商品信息 end -->
	                  	<!-- 支付信息 -->
	                  	<div class="tab-pane" id="tab_3">
		                    <table class="table table-condensed">
		                    	<tr>
		                    		<td class="tdLabel"><b>支付方式:</b></td><td class="tdContext">${info.order.paymentTypeStr}</td>
		                    		<td class="tdLabel"><b>支付状态:</b></td><td class="tdContext">${info.order.paymentStatusStr}</td>
		                    		
		                    	</tr>
		                    	<tr>
		                    	<#if info.order.paymentType==2>
		                    		<td class="tdLabel"><b>支付账号:</b></td><td class="tdContext">${info.order.returnAmountNo}</td>
		                    	</#if>
		                    		<td class="tdLabel"><b>订单金额（元）:</b></td><td class="tdContext">${info.order.totalAmountDisplay}</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>运费（元）:</b></td><td class="tdContext">${info.order.shipAmountDisplay}</td>
		                    		<td class="tdLabel"><b>优惠券金额（元）:</b></td><td class="tdContext">${info.order.couponAmountDisplay}</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>实付金额（元）:</b></td><td class="tdContext">${info.order.actualAmountDisplay}</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>订单退款（元）:</b></td><td class="tdContext">${info.order.returnAmount}</td>
		                    		<td class="tdLabel"><b>运费退款（元）:</b></td><td class="tdContext">${info.order.returnShipAmount}</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>优惠券退款（元）:</b></td><td class="tdContext">${info.order.returnCounponAmount}</td>
		                    	</tr>
		                    </table>
	                  	</div>
	                  	<!-- 支付信息 end -->
	                  	<!-- 配送信息 -->
	                 	<#if "shipment" == active> 
						<div class='tab-pane active' id="tab_4">
						<#else>
						<div class='tab-pane' id="tab_4">
						</#if>
	                  		<table class="table table-bordered table-hover" >
		                        <thead>
		                            <th>订单编号</th>
		                            <th>配送方式</th>
		                           	
		                            <#if info.order.shipmentType == 1> 
		                           	<th>收件人</th>
		                           	<th>快递公司</th>
		                           	<th>快递单号</th>
		                           	<#else>
		                            <th>终端配送商</th>
		                            <th>联系方式</th>
		                            </#if>
		                            
		                            <th>地址</th>
		                            <th>订单状态</th>
		                        </thead>
		                        <tbody> 
	                        		<tr>
	                        			<td>${info.order.orderId}</td>
	                        			<td>${info.order.shipmentTypeStr}</td>
	                        			
	                        			<#if info.order.shipmentType == 1> 
	                        			<td>${info.order.contact}</td>
	                        			<td>${info.order.expressName}</td>
	                        			<td>${info.order.expressNum}</td>
	                        			<#else>
	                        			<td>${info.order.orgName}</td>
	                        			<td>${info.order.phone}</td>
	                        			</#if>
	                        			
	                        			<td>${info.order.address}</td>
	                        			<td>${info.order.statusStr}</td>
	                        		</tr>
		                        </tbody>
	                      	</table>
	                      	<#if info.order.shipmentType == 1> 
	                      	<div>
	                      		<#if info.order.status==2>
		                      	<div  class="col-md-6" style="margin-top: 20px;">
		                      		<form id="exForm" method="post" action="${base}/web/order/shipment">
				                    <table class="table table-condensed">
				                    	<tr>
				                    		<td class="tdLabel"><b>快递信息:</b></td>
				                    		<td>
				                    			<label>快递公司</label>   
				                    			<input name="orderId" type="hidden" value="${info.order.orderId}">
				                    			<input id="text-input-exName" name="exName" class="form-control" type="text">
				                    		</td>
				                    	</tr>
				                    	<tr>
				                    		<td class="tdLabel"></td>
				                    		<td>
				                    			<label>快递单号</label>   
				                    			<input id="text-input-exNum" name="exNum" class="form-control" type="text">
				                    		</td>
				                    	</tr>
				                    	<tr>
				                    		<td class="tdLabel"></td>
				                    		<td>
				                    			<div style="text-align: center;">
				                    				<label style="width:100%; height:14px"></label>
	                                      	 		<input type="submit" class="btn  btn-primary" value="发货">
	                                      	 	</div>
				                    		</td>
				                    	</tr>
				                    </table>
				                    </form>
			                    </div>
			                    </#if>
			                    <div  class="col-md-6" style="margin-top: 20px;">
			                    	<!--  <table class="table table-condensed">
				                    	<tr>
				                    		<td class="tdLabel"><b>快递流转信息:</b></td>
				                    		<td>
				                    		</td>
				                    	</tr>
				                    </table> -->
			                    </div>
		                    </div>
		                    </#if>
	                  	</div>
	                  	<!-- 配送信息  end-->
	                  	<!-- 评价信息 -->
	                  	<div class="tab-pane" id="tab_5">
		                    <table class="table table-condensed">
		                    	<tr>
		                    		<td class="tdLabel"><b>服务态度:</b></td>
		                    		<td>
		                   				<#if info.feedback?? && info.feedback.attitudeLevel??>
		                    			<#list 1..info.feedback.attitudeLevel as i>
		                    				<div class="ax_image">
											 	<img class="u_img" class="img " src="${uiBase}/img/u175.png">
											     <div id="u192" class="text" style="top: 14px; -webkit-transform-origin: 13.5px -0.5px;">
								                <p><span></span></p>
								              </div>
	            							</div>
	            						</#list>
		                    			</#if>
		                    		</td>	
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>配货速度:</b></td>
		                    		<td>
		                    			<#if info.feedback?? && info.feedback.shipmentLevel??>
		                    			<#list 1..info.feedback.shipmentLevel as i>
		                    				<div class="ax_image">
											 	<img class="u_img" class="img " src="${uiBase}/img/u175.png">
											     <div id="u192" class="text" style="top: 14px; -webkit-transform-origin: 13.5px -0.5px;">
								                <p><span></span></p>
								              </div>
	            							</div>
	            						</#list>
		                    			</#if>
		                    		</td>
		                    	</tr>
		                    	<tr>
		                    		<td class="tdLabel"><b>服务体验:</b></td>
		                    		<td>
		                    			<#if info.feedback?? && info.feedback.serviceLevel??>
		                    			<#list 1..info.feedback.serviceLevel as i>
		                    				<div class="ax_image">
											 	<img class="u_img" class="img " src="${uiBase}/img/u175.png">
											     <div id="u192" class="text" style="top: 14px; -webkit-transform-origin: 13.5px -0.5px;">
								                <p><span></span></p>
								              </div>
	            							</div>
	            						</#list>
		                    			</#if>
		                    		</td>
		                    	</tr>
		                    </table>
	                  	</div>
	                  	<!-- 评价信息  end -->
	                </div>
				</div>
			</div>
        </div>
        <div class="col-md-12">
        	<div class="col-sm-3 col-sm-offset-5">
        		<button class="btn  btn-success" id="back"><i class="fa fa-backward"></i> 返  回</button>
			</div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
</body>
<#include "/footer.ftl" />
<script type="text/javascript" src="${uiBase}/vendor/jquery-validator/jquery.validate.min.js"></script>
<script type="text/javascript">
$(function() {
	
	jQuery.validator.addMethod("checkNumber", function(value, element) { 
		return this.optional(element) || /^\d+$/.test(value); 
	});
	
	$('#back').on("click", function() {
		window.location.href="../../orderReturn/";
	});
	
	// 表单验证
	$("#exForm").validate({
		rules: {
			exName: {
				required:true,
				maxlength:10
			},
			exNum: {
				required:true,
				checkNumber:true,
				maxlength:20
			}
		},
		messages: {
			exName :{
			//	required:"必填"
			},
			exNum: {
				//required:"必填",
				checkNumber:"订单号只能数字"
			}
		}
	}); 
	
});
</script>
</html>
