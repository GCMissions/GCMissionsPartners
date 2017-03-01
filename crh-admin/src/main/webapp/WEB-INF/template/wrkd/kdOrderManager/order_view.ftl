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
               		 </ul>
               		 
	               	<!-- 订单信息 -->
	                <div class="tab-content">
						<div class='tab-pane active' id="tab_1">
						<input type="hidden" id="operType" value="${order.returnType}">
						<input type="hidden" id="orderId" value="${order.orderId}">
						<input type="hidden" id="orderType" value="${order.orderType}">
						<input type="hidden" id="orderTypeCode" value="${order.orderTypeCode}">
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
									<label class="col-sm-2 control-label">运费（元）：</label>
									 <label  class="col-sm-4" style="font-weight:100">
									 ${order.shipAmount}
									 </label>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">创建时间：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.createDate}</label>
									 <label class="col-sm-2 control-label">是否退款：</label>
									
									 <#if order.showReturnInfo=="1">
									 <label  class="col-sm-4" style="color: red;font-weight:100">${order.returnInfo}</label>
									 <#else>
									 <label  class="col-sm-1" style="font-weight:100">否</label>
									 </#if>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">订单类型：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.orderType}</label>
									<label class="col-sm-2 control-label">发货信息：</label>
									 <label  class="col-sm-4" style="font-weight:100">
									 ${order.expressInfo}
									 </label>
								</div>
								 
								 <div class="form-group">
								 	<label class="col-sm-2 control-label">收货地址：</label>
								 	<label  class="col-sm-10" style="font-weight:100">${order.addressInfo}</label>
								 </div>
								 
								 <div class="form-group">
								 	<label class="col-sm-2 control-label">是否购买VIP：</label>
								 	<label  class="col-sm-10" style="font-weight:100">
								 	<#if order.buyVip == "0">
								 		否
								 	<#else>
								 		是
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
								    <#list order.remarks as rem>
										<div class="form-group">
											<label class="col-sm-2 control-label">序号：</label>
											 <label  class="col-sm-1"  >${rem.index}</label>
											<label class="col-sm-2 control-label">备注人：</label>
											 <label  class="col-sm-2" >${rem.name}</label>
											<label class="col-sm-2 control-label">备注时间：</label>
											 <label  class="col-sm-3" >${rem.remarkDate}</label>
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
					        			</#if>
					        			<button  class="btn  btn-success" id="changeOrder" style="font-weight:100">确 定</button>
					        		</#if>
					        		<button class="btn  btn-success backPage" id="orderBack" ><i class="fa fa-backward"></i> 返  回</button>
								</div>
					        </div>
	                  	</div>
	                  	<!-- 订单信息 end -->
	                  	
	                  <!-- 商品信息 -->
	                  	<div class="tab-pane" id="tab_2">
	                  		<div class="form-group">
									<label class="col-sm-10 control-label">用户备注：</label>
							</div>
							<div class="form-group">
								<div class="col-sm-11" style="margin-bottom: 40px;">
								    <textarea rows="3"   class="form-control  col-sm-12"  readOnly>${memberRemark}</textarea>
								</div>
							</div>
		                  	<div style="margin-bottom: 20px;" class='details'>
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
var totalActualAmount = '${order.actualAmount}';
var memberId = '${memberId}';

$(function() {

	$('#changeOrder').on('click',  _(changeOrder).bind(this));
	$('#cancelOrder').on('click',  _(cancelOrder).bind(this));
	$('#addRemark').on('click',  _(addRemark).bind(this));
	
	$('body').on('click', '#orderBack',function(){
		if (memberId) {
			window.location.href=urlPrefix+"member/order/" + memberId;
		} else {
		//window.location.href=urlPrefix+"kdOrder/";
		}
	});
	
	var orderType = $("#orderTypeCode").val();
	
	$('#actualAmount').inputmask( "mask",{'alias': 'decimal',"digits": 2,"allowMinus": false });
	
	function changeOrder(e){
    	e.preventDefault();
    	var actualAmount = $.trim($('#actualAmount').val());
    	
    	if(actualAmount != null && actualAmount != "") {
    		if (actualAmount == totalActualAmount) {
    			window.location.href=urlPrefix+"kdOrder/";
    		} else {
    			changeActualAmount();
    		}
    	} else if($("#actualAmount").is(":visible")){
			$('body').loadingInfo({
                type : "error",
                text : "请填写实付金额再保存",
                callBack : function() {
                	
                }
            });
    	} else {
    		window.location.href=urlPrefix+"kdOrder/";
    	}
	}
	
	function addRemark(){
		$.ajax({
			url: urlPrefix+"kdOrder/addRemark",
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
					 showRemarkTmp("2");
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
		showRemarkTmp("1");
	}
	
	function showRemarkTmp(operation){
		var _this = this;
		_this.remarkDialog = BootstrapDialog.show({
	        title: '添加备注',		    
	        size :  BootstrapDialog.SIZE_WIDE,
	        message: $(template('remarkTpl',{})),
	        draggable: false,
	        onshown: function(dialogRef){
	        	$("#operRemark").focus();
	        	$("#close").click(function(){
	        		_this.remarkDialog.close();
	    		});
	        	$("#doCancel").click(function(){
	        		var remark = $.trim($("#operRemark").val());
	        		if (remark == "") {
	        			$(window).loadingInfo("warn", "请填写操作理由！");
	        			return;
	        		}
	        		var orderId = $("#orderId").val();
	        		if (operation == "1") {
	        			var amount = Number($("#actualAmount").val());
	        			if(amount > 0 ){
	        				$.ajax({
	        					url: urlPrefix+"kdOrder/changeActualAmount",
	        					type:"post",
	        					dataType : "json",
	        					async:false,
	        					data :  JSON.stringify({"actualAmount":$("#actualAmount").val(), "orderId":orderId,"remark":remark,"operType":"3","orderType":orderType}),
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
	        						_this.remarkDialog.close();
	        					}
	        				});
	        			} else {
	        				$('body').loadingInfo({
	        	                type : "error",
	        	                text : "订单金额不能改为0元",
	        	                callBack : function() {
	        	                	
	        	                }
	        	            });
	        			}
	        		} else {
	        			$.ajax({
							url: urlPrefix+"kdOrder/cancelOrder",
							type:"post",
							dataType : "json",
							data :  JSON.stringify({ "orderId":orderId,"remark":remark,"operType":"1","orderType":orderType}),
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
		        		_this.remarkDialog.close();
	        		}
	        	});
            }
	    });
	};
})

</script>
<script id="remarkTpl" type="text/html">
<div class="row pad">
	<div class="col-sm-10" style="margin-bottom: 40px;">
		<textarea rows="3" class="form-control col-sm-12" placeholder="输入操作理由" id="operRemark"></textarea>
	</div>
	<div class="col-md-12" style="margin-top: 20px;">
		<div class="col-sm-3 col-sm-offset-5">
			<button class="btn  btn-primary" id="doCancel" ><i class="fa"></i>确 定</button>
			<button class="btn  btn-default" id="close" style="font-weight:100">取 消</button>
		</div>
	</div>
</div>
</script>
<script type="text/javascript" src="${uiBase}/js/pages/wrkd/kdOrderManager/order_view.js?v=${resourceVersion}"></script>
</html>
