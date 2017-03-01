<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />

<style>
textarea{
width: 100% !important;
 margin-left: 80px;
}
label{
    padding-top: 7px;
}

.subSpec{
 margin-right: 5px;
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
				<div class="nav-tabs-custom">
					<!-- tab 页标题 -->
					<ul class="nav nav-tabs">
						<li class="active">
                 			<a href="#tab_1" data-toggle="tab">订单信息</a>
                 		</li>
                  		<li><a href="#tab_2" data-toggle="tab">
                  		商品信息</a></li>  
                  		 
                  		<li><a href="#tab_3" data-toggle="tab">
                  		获得的优惠券</a></li> 
               		 </ul>
               		 
	               	<!-- 订单信息 -->
	                <div class="tab-content">
						<div class='tab-pane active' id="tab_1">
		                  	<div style="margin-bottom: 20px;">
							<div class="form-horizontal ">
							
								<div class="form-group">
									<label class="col-sm-2 control-label">订单号：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.orderId}</label>
									<label class="col-sm-2 control-label">订单金额（元）：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.totalAmount}
									 </label>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">订单状态：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.status}</label>
									<label class="col-sm-2 control-label">实付金额（元）：</label>
									 <#if order.status=="待支付" && order.returnType=="1">
									 <div class="col-sm-1">
									 	<input  class="form-control  col-sm-2" id="actualAmount"  style="width: 90px;font-weight:100" value ="${order.actualAmount}">
									 </div>
									 <#else>
									 <label  class="col-sm-4" style="font-weight:100">
										 ${order.actualAmount}
										</label>
									 </#if>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">手机号：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.phone}</label>
									<label class="col-sm-2 control-label">优惠（元）：</label>
									 <label  class="col-sm-4" style="font-weight:100">
									 ${order.couponAmount}
									 </label>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">创建时间：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.createDate}</label>
									<label class="col-sm-2 control-label">运费（元）：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.shipAmount}</label>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">是否退款：</label>
									
									 <#assign showStatus = "0">
									 <#if (order.status=="待出行" || order.status=="待点评" ) && order.returnType=="1" && order.source!='7'>
										 <label  class="col-sm-2" style="width: 50px;font-weight:100" id="returnLabel">否</label>
										 <button  class="btn btn-default col-sm-1" id="doReturn" >退款</button>
										 <#assign showStatus = "1">
									 <#elseif order.status=="已退款">
									 	<label  class="col-sm-2" style="color: red;font-weight:100">${order.returnOrderAmount}</label>
									 	<#if order.returnable == "1" && order.returnType=="1">
									 		<#assign showStatus = "1">
									 		<button class="btn btn-default col-sm-1" id="continueReturn" >继续退款</button>
									 	</#if>
									 <#else>
									 	<label  class="col-sm-3" style="font-weight:100">否</label>
									 </#if>
									 
									 <#if showStatus == "1">
										 <div class="col-sm-1" id="returnInput" style="display:none">
											 <input  class="form-control  col-sm-2" id="returnOrderAmount" style="width: 90px;font-weight:100" >
										 </div>
									 </#if>
								</div>
								<div class="form-group">
								<label class="col-sm-2 control-label">是否开通vip：</label>
									 <label  class="col-sm-4" style="font-weight:100">
									 <#if order.buyVip == "1">
									 	是
									 <#else>
									 	否
									 </#if>
									 </label>
								</div>
								 
								<div  >
								<#if order.returnType=="1">
									<div class="form-group ">
										<div class="col-sm-10">
										 <textarea rows="3" id="remark" class="form-control col-sm-10"  ></textarea>
										 </div>
									</div>
									<div class="form-group">
										 <button  class="btn btn-default col-sm-1" id="addRemark" style="float: right;margin-right: 12.7%;">添加备注</button>
									</div>
								</#if>
								    <#list remarks as rem>
										<div class="form-group">
											<label class="col-sm-2 control-label">序号：</label>
											 <label  class="col-sm-1"  >${rem.id}</label>
											<label class="col-sm-2 control-label">备注人：</label>
											 <label  class="col-sm-2" >${rem.creater}</label>
											<label class="col-sm-2 control-label">备注时间：</label>
											 <label  class="col-sm-3" >${rem.createDate}</label>
										</div>
										<div class="form-group">
										<div class="col-sm-10">
								    		<textarea rows="3"   class="form-control  col-sm-10"  readOnly>${rem.remark}</textarea>
										</div>
										</div>
								    </#list>  
								</div>
							
							</div>
	                  	
					        </div>
					        <div class="col-md-12" style="margin-top: 20px;">
					        	<div class="col-sm-4 col-sm-offset-5">
					        		<#if order.returnType=="1">
					        			<#if order.status=="待支付">
											<button  class="btn  btn-success" id="cancelOrder" style="font-weight:100">取消订单</button>
											<button  class="btn  btn-success" id="changeOrder" style="font-weight:100">确 定</button>
										<#elseif order.status=="待出行" || order.status=="待点评" || (order.status=="已退款" && order.returnable == "1")>
											<button  class="btn  btn-success" id="changeOrder" style="font-weight:100">确 定</button>
										</#if>
									</#if>
					        		<button class="btn  btn-success backPage" id="orderBack" ><i class="fa fa-backward"></i> 返  回</button>
								</div>
					        </div>
	                  	</div>
	                  	<!-- 订单信息 end -->
	                  	
	                  <!-- 商品信息 -->
	                  	<div class="tab-pane" id="tab_2">
		                  	<div style="margin-bottom: 20px;">
		                  		<table id="detailList" class="table table-bordered table-hover" >
	                     		</table>
		                  	</div>
					        <div class="col-md-12" style="margin-top: 20px;">
					        	<div class="col-sm-3 col-sm-offset-5">
					        		<button class="btn  btn-success backPage" id="orderBack" ><i class="fa fa-backward"></i> 返  回</button>
								</div>
				        	</div>
	                  	</div>
	                  	<!-- 商品信息 end -->
	                  	
	                  	<!-- 获得的优惠券-->
	                  	<div class="tab-pane" id="tab_3">
		                  	<div style="margin-bottom: 20px;">
		                  		<div style="line-height: 30px;"><label> 通过该笔订单，用户获得以下优惠券：</label></div>
		                  		<table id="couponList" class="table table-bordered table-hover" >
	                     		</table>
		                  	</div>
					        <div class="col-md-12" style="margin-top: 20px;">
					        	<div class="col-sm-3 col-sm-offset-5">
					        		<button class="btn  btn-success backPage" id="orderBack" ><i class="fa fa-backward"></i> 返  回</button>
								</div>
					        </div>
	                  	</div>
	                  	<!-- 获得的优惠券 end -->
	                </div>
				</div>
			</div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
</body>
<#include "/footer.ftl" />
<script src="${uiBase}/vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script type="text/javascript" src="${uiBase}/vendor/jquery-validator/jquery.validate.min.js"></script>
<script type="text/javascript">
var orderId = '${order.orderId}';
var status = '${order.status}';
var memberId = '${memberId}';

$(function() {

	$('#changeOrder').on('click',  _(changeOrder).bind(this));
	$('#doReturn').on('click',  _(doReturn).bind(this));
	$('#continueReturn').on('click', _(continueReturn).bind(this));
	$('#cancelOrder').on('click',  _(cancelOrder).bind(this));
	$('#addRemark').on('click',  _(addRemark).bind(this));
	
	$('body').on('click', '#orderBack',function(){
		if (memberId) {
			window.location.href=urlPrefix+"member/order/" + memberId;
		} else {
		//window.location.href=urlPrefix+"order/";
		}
	});
	
	$('#actualAmount').inputmask( "mask",{'alias': 'decimal',"digits": 2,"allowMinus": false });
	$('#returnOrderAmount').inputmask("mask",{ 'alias': 'decimal',"digits": 2,"allowMinus": false });
	function changeOrder(e){
    	e.preventDefault();
    	$('#changeOrder').prop("disabled",true).text("保存中...");
    	var actualAmount = $('#actualAmount');
    	var returnOrderAmount = $('#returnOrderAmount');
    	
    	if(actualAmount.length>0) {
			changeActualAmount();
    	} else if(returnOrderAmount.length>0 && $("#returnInput").is(":visible") ) {
			changeReturnOrderAmount();
    	} else {
			$('body').loadingInfo({
                type : "error",
                text : "请进行相关操作后再保存",
                callBack : function() {
                	$('#changeOrder').prop("disabled",false).text("确 定");
                }
            });
    	}
    	setTimeout(function(){
        	$('#changeOrder').prop("disabled",false).text("确 定");
    	},1500);
	}
	
	// 第一次退款
	function doReturn(e){
    	e.preventDefault();
    	$('#doReturn').hide();
    	$('#returnLabel').hide();
    	$('#returnInput').show();
	}
	
	// 后续退款
	function continueReturn(e){
    	e.preventDefault();
    	$('#continueReturn').hide();
    	$('#returnLabel').hide();
    	$('#returnInput').show();
	}
	
	function addRemark(){
		$.ajax({
			url: urlPrefix+"orderReturn/addRemark",
			type:"post",
			dataType : "json",
			data :  JSON.stringify({ "orderId":orderId,"remark":$("#remark").val()}),
			contentType : "application/json; charset=utf-8",
			success:function(msg){
				if(msg.code=="ACK") {
					$('body').loadingInfo({
	                    type : "ok",
	                    text : msg.message,
	                    callBack : function() {
	                    	window.location.href=window.location.href;
	                    }
	                });
				}
			}
		});
	}
	
	function cancelOrder(){
		BootstrapDialog.show({
			 title: '取消订单',
			 type : BootstrapDialog.TYPE_WARNING,
			 message: "确认取消订单？",
			 
			 draggable: true,
			 size : BootstrapDialog.SIZE_SMALL,
			 buttons: [{
				 label: '取消订单',
				 cssClass: 'btn-primary ',
				 action: function(dialog) {
					 dialog.close();
					$.ajax({
						url: urlPrefix+"orderReturn/cancelOrder",
						type:"post",
						dataType : "json",
						data :  JSON.stringify({ "orderId":orderId}),
						contentType : "application/json; charset=utf-8",
						success:function(msg){
							if(msg.code=="ACK") {
								$('body').loadingInfo({
				                    type : "ok",
				                    text : msg.message,
				                    callBack : function() {
				                    	window.location.href=window.location.href;
				                    }
				                });
							}
						}
					});

				 }
			 }, {
				 label: '取消',
				 action: function(dialog) {
					 dialog.close();
				 }
			 }]
			 });
	}
	
	function changeActualAmount(){
		var amount = Number($("#actualAmount").val());
		if(amount > 0 ){
			$.ajax({
				url: urlPrefix+"orderReturn/changeActualAmount",
				type:"post",
				dataType : "json",
				async:false,
				data :  JSON.stringify({"actualAmount":$("#actualAmount").val(), "orderId":orderId}),
				contentType : "application/json; charset=utf-8",
				success:function(msg){
					if(msg.code=="ACK") {
						$('body').loadingInfo({
		                    type : "ok",
		                    text : msg.message,
		                    callBack : function() {
		                    	window.location.href=window.location.href;
		                    }
		                });
					}
				}
			});
		} else {
			$('body').loadingInfo({
                type : "error",
                text : "订单金额不能改为0元",
                callBack : function() {
                	$('#changeOrder').prop("disabled",false).text("确 定");
                }
            });
		}
	}
	function changeReturnOrderAmount(){
		
		var amount = Number($("#returnOrderAmount").val());
		
		if(amount > 0 ){
			$.ajax({
				url: urlPrefix+"orderReturn/changeReturnOrderAmount",
				type:"post",
				dataType : "json",
				async:false,
				data :  JSON.stringify({"returnOrderAmount":$("#returnOrderAmount").val(), "orderId":orderId}),
				contentType : "application/json; charset=utf-8",
				success:function(msg){
					if(msg.code=="ACK") {
						$('body').loadingInfo({
		                    type : "ok",
		                    text : msg.message,
		                    callBack : function() {
		                    	window.location.href=window.location.href;
		                    }
		                });
					}
				}
			});
		} else {
			$('body').loadingInfo({
                type : "error",
                text : "退款金额不能小于等于0",
                callBack : function() {
                	$('#changeOrder').prop("disabled",false).text("确 定");
                }
            });
		}
    	 
	}
})

</script>
<script type="text/javascript" src="${uiBase}/js/pages/orderManager/order_view.js?v=${resourceVersion}"></script>
</html>
