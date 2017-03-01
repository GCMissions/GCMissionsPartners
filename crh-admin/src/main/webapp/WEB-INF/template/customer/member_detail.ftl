<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/product.css?v=${resourceVersion}">
<style>
#addressdataList.table-bordered > tbody > tr > td{
    border-right:none !important;
}
.fa-eye{
	cursor: pointer;
	color: #3c8dbc;
}
#tab_1 span{
	font-weight: 100 !important;
}
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
        <div class="content-wrapper page-content-wrapper">
         	<section class="content-header">
         
          <ol class="breadcrumb">
            <li><i class="fa  fa-users"></i> 消费者管理</li>
            <li> 消费者详情</li>
             
          </ol>
          
          
         </section>

            <div class="row pad">
                <div class="col-md-12">
                  <!-- Custom Tabs -->
                  <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                      <li class="active"><a href="#tab_1" data-toggle="tab">基本信息</a></li>
                      <li><a href="#tab_2" data-toggle="tab">收货地址</a></li>
                      <li><a href="#tab_3" data-toggle="tab">历史订单</a></li>
                      <li><a href="#tab_4" data-toggle="tab">积分记录</a></li>
                      <li><a href="#tab_5" data-toggle="tab">账户余额</a></li>
                      <li><a href="#tab_6" data-toggle="tab">优惠券记录</a></li>
                      
                       
                    </ul>
                    <div class="tab-content">
                    <!--基本信息-->
                      <div class="tab-pane active" id="tab_1">
                          <div class="box-body" style="width: 960px">
                              <!-- text input -->
                              <input type="hidden" value=${dto.memberNo} id="memberIdVal">
                              <div class="col-md-12" >
                              	<label class="col-sm-3 control-label">注册手机号: <span>${dto.phone}</span></label>  
                                <label class="col-sm-3 control-label">消费者编号: <span>${dto.memberNo}</span></label>
                              </div>
                              <div class="col-md-12">
                                <label class="col-sm-3 control-label">消费者姓名: <span>${dto.memberName}</span></label>
                                <label class="col-sm-3 control-label">积分: <span>${dto.point}</span></label>
                              </div>
                              <div class="col-md-12">
                                <label class="col-sm-3 control-label">账户余额: <span>${dto.balance}</span> 元</label>
                                <label class="col-sm-3 control-label">优惠券: <span>${dto.currentCoupoints}</span> 张</label>
                              </div>
                              <div class="col-md-12">
                                <label class="col-sm-6 control-label">注册日期: <span>${dto.registerDate}</span></label>
                              </div>
                      	 </div>
                      </div>
                    <!--基本信息end-->
                      
                    <!--收货地址 -->
                      <div class="tab-pane" id="tab_2">
                     	<div class="box-body">
                        	<div class="col-md-12">
                             	<table id="addressdataList" class="table table-bordered table-hover" >
               						<thead>
               							<th class="isDefult"></th>
               							<th class="no.">序号</th>
                            			<th class="province">省份</th>
                           			 	<th class="city">市</th>
                            			<th class="district">区（县）</th>
                            			<th class="district">小区名称</th>
                            			<th class="address">详细收货地址</th>
                           			 	<!--<th class="postCode">邮编</th>-->
                            			<th class="addressContact">收货人</th>
                            			<th class="phone">联系电话</th>
	                    			</thead>
	                    			<tbody> 
	                    			<#list dto.addresses as addressesList>
	                       	 			<tr>
	                       	 				<#if addressesList.defFlag=1 >
	                       	 				<td class="isDefult" ><i class="fa fa-check " style="font-size: 20px;"></i></td>
	                        				<#else>
	                        				<td class="isDefult" > </td>
	                        				</#if>
	                        				<td class="no." >${addressesList_index+1}</td>
	                        				<td class="province" >${addressesList.province}</td>
	                        				<td class="city" >${addressesList.city}</td>
	                        				<td class="district" >${addressesList.area}</td>
	                        				<td class="district" >${addressesList.areaName}</td>
	                        				<td class="address" >${addressesList.address}</td>
	                        				<!--<td class="postCode" >${addressesList.zipcode}</td>-->
	                        				<td class="addressContact" >${addressesList.addressContact}</td>
	                        				<td class="phone" >${addressesList.phone}</td>
	                        			</tr>
	                    			</#list>
	                    			</tbody>
	               				</table>
                            </div>
                        </div>
                    </div>
                   <!--收货地址end-->
                    
                     <!--历史订单 TDO-->
                      <div class="tab-pane" id="tab_3">
                      <!--历史订单列表-->
                     	<div class="box-body" id="customOrderListDiv">
                     		<div class="col-md-12">
                                <label class="col-sm-6 control-label">消费总金额 : ${dto.consumeNum} 元</label>
                            </div>
                        	<div class="col-md-12">
                             	<table id="customOrderdataList" class="table table-bordered table-hover" >
               						<thead>
               							<th>序号</th>
                            			<th>订单编号</th>
                           			 	<th>订单金额（元）</th>
                            			<th>实付金额（元）</th>
                            			<th>支付方式</th>
                           			 	<th>下单数量（件）</th>
                            			<th>下单日期</th>
                            			<th>订单状态</th>
                            			<th>操作</th>
	                    			</thead>
	                    			<tbody> 
	                    			<#list dto.orders as orderList>
	                       	 			<tr>
	                        				<td>${orderList_index+1}</td>
	                        				<td class="orderId">${orderList.orderId}</td>
	                        				<td>${orderList.totalAmount}</td>
	                        				<td>${orderList.actualAmount}</td>
	                        				<td>${orderList.paymentType}</td>
	                        				<td>${orderList.totalNum}</td>
	                        				<td>${orderList.createDate}</td>
	                        				<td>${orderList.status}</td>
	                        				<td><i class="fa fa-eye "  style="font-size:20px"></i></td>
	                        			</tr>
	                    			</#list>
	                    			</tbody>
	               				</table>
                            </div>
                      	</div>
                      <!--/历史订单列表-->
                      
                       <!--历史订单详情-->
                     	<div class="box-body" id="customOrderDetailDiv" style="display:none;">
                     		<div class="col-md-12">
                            	<label class="col-sm-3 control-label">订单编号 :<span id="customOrderId"></span></label>
                                <label class="col-sm-3 control-label">订单状态 :<span id="customOrderStatus"></span></label>
                                <label class="col-sm-3 control-label">下单数量 :<span id="customOrderTotalNum"></span></label>
                                <label class="col-sm-3 control-label">下单时间 :<span id="customOrderCreatDate"></span></label>
                            </div>
                            <div class="col-md-12">
                                <label class="col-sm-3 control-label">订单金额 :<span id="customOrderTotalAmount"></span></label>
                                <label class="col-sm-3 control-label">优惠券金额 :<span id="customOrderCouponAmount"></span></label>
                                <label class="col-sm-3 control-label">实付金额 :<span id="customOrderActualAmount"></span></label>
                            </div>
                            <div class="col-md-12">
                                <label class="col-sm-3 control-label">支付方式 :<span id="customOrderPaymentType"></span></label>
                                <label class="col-sm-3 control-label">支付状态 :<span id="customOrderPaymentStatus"></span></label>
                                <label class="col-sm-3 control-label">收货方式 :<span id="customOrderShipmentType"></span></label>
                                <label class="col-sm-3 control-label">收货时间 :<span id="customOrderRecivingDate"></span></label>
                            </div>
                            <div class="col-md-12">
                                <label class="col-sm-3 control-label">收货人 :<span id="customOrderContact"></span></label>
                                <label class="col-sm-3 control-label">收货地址 :<span id="customOrderAddress"></span></label>
                                <label class="col-sm-4 control-label">收货人联系电话 :<span id="customOrderPhone"></span></label>
                            </div>
                            <div class="col-md-12">
                                <label class="col-sm-3 control-label">终端配送商编号:<span id="customOrderOrgId"></span></label>
                                <label class="col-sm-4 control-label">终端配送商名称 :<span id="customOrderOrgName"></span></label>
                            </div>
                        	<div class="col-md-12">
                             	<table id="custmoOrderDetailList" class="table table-bordered table-hover" >
               						<thead>
               							<th class="no.">序号</th>
                            			<th class="orderId">商品编码</th>
                           			 	<th class="city">商品名称</th>
                            			<th class="actualAmount">品牌</th>
                            			<th class="address">分类</th>
                           			 	<th class="postCode">价格（元）</th>
                            			<th class="addressContact">下单数（件）</th>
	                    			</thead>
	                    			
	                    			<tbody> 
	                    			</tbody>
	                    			
	               				</table>
                            </div>
                      	</div>
                      <!--/历史订单列表-->
                      
                      </div>
                 <!--历史订单end-->
                 
                 <!--积分记录-->
                      <div class="tab-pane" id="tab_4">
                     	<div class="box-body">
                     		<div class="col-md-12">
                                <label class="col-sm-6 control-label">当前积分 : ${dto.point}</label>
                            </div>
                        	<div class="col-md-12">
                             	<table id="dataList" class="table table-bordered table-hover" >
               						<thead>
               							<th class="no.">序号</th>
                            			<th class="orderId">积分值</th>
                           			 	<th class="type">来源</th>
                            			<th class="createDate">时间</th>
                            			<th class="createId">订单编号</th>
	                    			</thead>
	                    			<tbody> 
	                    			<#list dto.points as pointsList>
	                       	 			<tr>
	                        				<td class="no." >${pointsList_index+1}</td>
	                        				<td class="remark" >${pointsList.changeValue}</td>
	                        				<td class="type" >${pointsList.type}</td>
	                        				<td class="createDate" >${pointsList.createDate}</td>
	                        				<td class="createId" >${pointsList.orderId}</td>
	                        			</tr>
	                    			</#list>
	                    			</tbody>
	               				</table>
                            </div>
                        </div>
                      </div>
                 <!--积分记录end-->
                 
                 <!--账户余额-->
                      <div class="tab-pane" id="tab_5">
                     	<div class="box-body">
                     		<div class="col-md-12">
                                <label class="col-sm-6 control-label">当前账户余额 : ${dto.balance}元</label>
                            </div>
                        	<div class="col-md-12">
                             	<table id="dataList" class="table table-bordered table-hover" >
               						<thead>
               							<th class="no.">序号</th>
                            			<th class="orderId">金额</th>
                           			 	<th class="remark">消费/充值</th>
                            			<th class="createDate">支付方式</th>
                            			<th class="createId">时间</th>
                            			<th class="orderId">订单编号</th>
	                    			</thead>
	                    			<tbody> 
	                    			<#list dto.balances as balancesList>
	                       	 			<tr>
	                        				<td class="no." >${balancesList_index+1}</td>
	                        				<td class="remark" >${balancesList.amount}</td>
	                        				<td class="type" >${balancesList.type}</td>
	                        				<td class="createDate" >${balancesList.paymentType}</td>
	                        				<td class="createId" >${balancesList.date}</td>
	                        				<td class="orderId" >${balancesList.orderId}</td>
	                        			</tr>
	                    			</#list>
	                    			</tbody>
	               				</table>
                            </div>
                        </div>
                      </div>
                 <!--账户余额end-->
                 
                 <!--优惠券记录-->
                      <div class="tab-pane" id="tab_6">
                     	<div class="box-body">
                     		<div class="col-md-12">
                                <label class="col-sm-6 control-label">当前优惠券 : ${dto.currentCoupoints} 张</label>
                            </div>
                        	<div class="col-md-12">
                             	<table id="dataList" class="table table-bordered table-hover" >
               						<thead>
               							<th class="no.">序号</th>
                            			<th class="couponId">优惠券编号</th>
                           			 	<th class="status">优惠券状态</th>
                            			<th class="couponName">名称</th>
                            			<th class="value">优惠券面值（元）</th>
                            			<th class="coupCode">优惠码</th>
                            			<th class="createDate">领取时间</th>
                            			<th class="usedDate">使用时间</th>
                            			<th class="orderId">订单编号</th>
	                    			</thead>
	                    			<tbody> 
	                    			<#list dto.coupons as couponsList>
	                       	 			<tr>
	                        				<td class="no." >${couponsList_index+1}</td>
	                        				<td class="couponId" >${couponsList.couponId}</td>
	                        				<td class="status" >${couponsList.status}</td>
	                        				<td class="couponName" >${couponsList.couponName}</td>
	                        				<td class="value" >${couponsList.value}</td>
	                        				<td class="coupCode" >${couponsList.coupCode}</td>
	                        				<td class="createDate" >${couponsList.createDate}</td>
	                        				<td class="usedDate" >${couponsList.usedDate}</td>
	                        				<td class="orderId" >${couponsList.orderId}</td>
	                        			</tr>
	                    			</#list>
	                    			</tbody>
	               				</table>
                            </div>
                        </div>
                      </div>
                 <!--优惠券记录end-->
                    
                 </div>
                 
                
                 
                  </div>
                 
                  <!-- nav-tabs-custom -->
                  	<div class="col-sm-3 col-sm-offset-5">
                  		<button class="btn  btn-success" id="passwordReset" href="#" style="display:none;">密码重置</button>
                  		<button class="btn  btn-success" id="back" href="#"><i class="fa fa-backward"></i> 返  回</button>
						<button class="btn  btn-success" id="orderListBack" href="#" style="display:none;"><i class="fa fa-backward"></i> 返  回</button>
					</div>
                </div>
            </div>
        

           
            <div class="clearfix"></div>
        </div>
       
        <!-- /.row -->

    </div><!-- ./wrapper -->
   
  </body>
<#include "/footer.ftl" />

<script type="text/javascript" src="${uiBase}/js/pages/customer/member_detail.js?v=${resourceVersion}"></script> 
</html>
