<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
<style type="text/css">
.subBlock {
border: 0px solid #ddd;font-size:18px;
border-bottom: 2px solid #3c8dbc;padding-bottom:5px;color: #3c8dbc
}
textarea {
	min-width: 400px
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
        <div class="content-wrapper page-content-wrapper">
         	<section class="content-header">
         
	          <ol class="breadcrumb">
	            <li><i class="fa fa-dashboard"></i>系统设置</li>
	            <li class="active">
				消息模板
	            </li>
	          </ol>
          
          
            </section>
            
            
           
            <div class="row pad">
                <div class="col-md-12">
                  <!-- Custom Tabs -->
                   <form role="form" class="form-horizontal" method=post id="mainForm">
                  <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                      <li class="active"><a href="#tab_2" data-toggle="tab">短信模板</a></li>
                    </ul>
                    
                    <div class="tab-content">
                       <!-- /.tab-pane -->
                      <div class="tab-pane active" id="tab_2">
                         <div class="box-body">
                          	  <div class="form-group ">
                                <label class="col-sm-3 control-label"><span class="subBlock">消费者端</span></label>
                              </div>
							  <div class="form-group ">
                                  <label class="col-sm-3 control-label">获取验证码：</label>
                                  <div class="col-sm-5">
                                    <textarea name="sms_C_getCaptcha"  cols=50 rows=3 class="form-control" maxlength="500">${messageDto.sms_C_getCaptcha}</textarea>
                                    <br>
                                    		（注册时、忘记密码时）
                                     <br>
                                    		<strong>&#36;{code}：</strong>验证码
                                  </div>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              <div class="form-group ">
                                <label class="col-sm-3 control-label">支付成功提醒-会员（有商品验证码）：</label>
                                  <div class="col-sm-5">
                                    <textarea name="sms_C_sendOrderANotify"  cols=50 rows=5 class="form-control" maxlength="500">${messageDto.sms_C_sendOrderANotify}</textarea>
                                   <br>
                                   <strong>&#36;{name}：</strong>商品名
                                   <br>
                                   <strong>&#36;{code}：</strong>商品验证码
                                  </div>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              <div class="form-group ">
                                <label class="col-sm-3 control-label">支付成功提醒-会员（无商品验证码）：</label>
                                  <div class="col-sm-5">
                                    <textarea name="sms_C_sendOrderBNotify"  cols=50 rows=4 class="form-control" maxlength="500">${messageDto.sms_C_sendOrderBNotify}</textarea>
                                   <br>
                                   <strong>&#36;{orderTime}：</strong>订单时间
                                   <br>
                                   <strong>&#36;{name}：</strong>商品名
                                  </div>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              <div class="form-group ">
                                <label class="col-sm-3 control-label">成功支付提醒-服务商：</label>
                                  <div class="col-sm-5">
                                    <textarea name="sms_S_sendNewOrderNotify"  cols=50 rows=6 class="form-control" maxlength="500">${messageDto.sms_S_sendNewOrderNotify}</textarea>
                                   <br>
                                   <strong>&#36;{serviceName}：</strong>服务商名称
                                   <br>
                                   <strong>&#36;{productName}：</strong>商品名称
                                   <br>
                                   <strong>&#36;{serviceId}：</strong>商家编号
                                  </div>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              <div class="form-group ">
                                <label class="col-sm-3 control-label">退款提醒-会员：</label>
                                  <div class="col-sm-5">
                                    <textarea name="sms_C_refundNotify"  cols=50 rows=5 class="form-control" maxlength="500">${messageDto.sms_C_refundNotify}</textarea>
                                   <br>
                                   <strong>&#36;{serviceName}：</strong>服务商名称
                                   <br>
                                   <strong>&#36;{productName}：</strong>商品名称
                                   <br>
                                   <strong>&#36;{amount}：</strong>退款金额
                                   <br>
                                   <strong>&#36;{day}：</strong>工作日
                                  </div>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              <div class="form-group ">
                                <label class="col-sm-3 control-label">退款提醒-服务商：</label>
                                  <div class="col-sm-5">
                                    <textarea name="sms_S_refundNotify"  cols=50 rows=5 class="form-control" maxlength="500">${messageDto.sms_S_refundNotify}</textarea>
                                   <br>
                                   <strong>&#36;{serviceName}：</strong>服务商名称
                                   <br>
                                   <strong>&#36;{productName}：</strong>商品名称
                                   <br>
                                   <strong>&#36;{amount}：</strong>退款金额
                                  </div>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              <div class="form-group ">
                                <label class="col-sm-3 control-label">优惠券发送提醒-会员：</label>
                                  <div class="col-sm-5">
                                    <textarea name="sms_C_sendCouponNoitfy"  cols=50 rows=5 class="form-control" maxlength="500">${messageDto.sms_C_sendCouponNoitfy}</textarea>
                                   <br>
                                   <strong>&#36;{couponName}：</strong>优惠券名称
                                   <br>
                                  <div class="col-sm-4">
                                  </div>
                              	 </div>
                        	</div>
                        	<div class="form-group ">
                                <label class="col-sm-3 control-label">吾儿酷袋退款提醒-普通商品：</label>
                                  <div class="col-sm-5">
                                    <textarea name="sms_C_KdProductRefundNotify"  cols=50 rows=5 class="form-control" maxlength="500">${messageDto.sms_C_KdProductRefundNotify}</textarea>
                                   <br>
                                   <strong>&#36;{productName}：</strong>商品名称
                                   <br>
                                   <strong>&#36;{amount}：</strong>退款金额
                                   <br>
                                   <strong>&#36;{day}：</strong>工作日
                                   <br>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              </div>
                              <div class="form-group ">
                                <label class="col-sm-3 control-label">吾儿酷袋退款提醒-活动：</label>
                                  <div class="col-sm-5">
                                    <textarea name="sms_C_KdActRefundNotify"  cols=50 rows=5 class="form-control" maxlength="500">${messageDto.sms_C_KdActRefundNotify}</textarea>
                                   <br>
                                   <strong>&#36;{actType}：</strong>活动类型
                                   <br>
                                   <strong>&#36;{productName}：</strong>商品名称
                                   <br>
                                   <strong>&#36;{amount}：</strong>退款金额
                                   <br>
                                   <strong>&#36;{day}：</strong>工作日
                                   <br>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              </div>
                      </div>
                      <!-- /.tab-pane -->
                      
                     
                    </div>
                    <!-- /.tab-content -->
                  </div>
                 
                  <!-- nav-tabs-custom -->
                  <div class="row">
                   <div class="col-sm-12 text-center">
                     <#if !isReview>
                    <button class="btn btn-primary submitMainForm" type="button"  >保存</button>
                    </#if>
         
                   
                   </div>
                  </div>
                </div>
                 </form>
            </div>
           
            <div class="clearfix"></div>
        </div>
       
        <!-- /.row -->

    </div><!-- ./wrapper -->
   
  </body>
  <#include "/footer.ftl" />

  <script type="text/javascript" src="${uiBase}/js/pages/setting/setting.js?v=${resourceVersion}"></script> 
 <script>
 $(function() {
	  templateApp.init();
 });
  </script>
</html>
