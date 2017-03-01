<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/coupon.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
	 	    <ol class="breadcrumb">
			   <li><i class="fa fa-dashboard"></i> 营销管理</li>
			   <li>查看优惠券</li>
		    </ol> 
        </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
				 <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                      <li class="active"><a href="#tab_1" data-toggle="tab">基本信息</a></li>
                     <!--  <li><a href="#tab_2" data-toggle="tab">领用记录</a></li> -->
                      <!-- <li><a href="#tab_3" data-toggle="tab" class="tab_3 hidden">合作发放</a></li>        -->                                                           
                    </ul>
                    <div class="tab-content">
                        <div id="tab_1" class="tab-pane active">
                            <div class="panel-body">
                                <div class="row">
		                            <div class="col-md-12">
		               					<div class="col-sm-4">
	                                   		<span class="detail_span">优惠券编号：</span>
	                                        <input class="hidden" id="couponDetailId" value="${coupon.couponId}">
	                                    	<span>${coupon.couponId}</span>
		                                </div>
		                                <div class="col-sm-4">
	                                   		<span class="detail_span">创建日期：</span>
	                                    	<span>${coupon.createDate}</span>
		                                </div>		                                                            
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col-md-12">
		               					<div class="col-sm-4">
	                                   		<span class="detail_span">优惠券名称：</span>
	                                    	<span>${coupon.couponName}</span>
		                                </div>
		                                <div class="col-sm-4">
	                                   		<span class="detail_span">优惠券面额：</span>
	                                    	<span>${coupon.valueYuan}元</span>
		                                </div>
		                                                            
		                            </div>
		                        </div>
		                        <div class="row rechargeCoupon">
		                            <div class="col-md-12">
		               					<div class="col-sm-4">
	                                   		<span class="detail_span">开始使用时间：</span>
	                                    	<span>${coupon.effectDate?string("yyyy-MM-dd")}</span>
		                                </div>
		                                <div class="col-sm-4">
	                                   		<span class="detail_span">结束使用时间：</span>
	                                    	<span>${coupon.invalidDate}</span>
		                                </div>		                                                            
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col-md-12">
		               					<div class="col-sm-4">
	                                    	<span class="detail_span">适用平台：</span>
	                                    	<#if coupon.webUse == 1>
	                                    	<span>微信商城     </span>
	                                    	</#if>
	                                   		<#if coupon.mobileUse == 1>
	                                    	<span>APP商城、微信</span>
	                                    	</#if>
		                                </div>                                
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col-md-12">
		               					<div class="col-sm-4">
	                                   		<span class="detail_span">优惠券类型：</span>
	                                    	<span>
	                                    	<input class="couponType hidden" value="${coupon.type}">
	                                    	<#if coupon.type == "1">后台发放</#if>
	                                    	<#if coupon.type == "2">用户领取</#if>
	                                    	<#if coupon.type == "3">充值赠送</#if>
	                                    	</span>
		                                </div>                      
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col-md-12">
		               					<div class="col-sm-4">
		               						<span class="detail_span">获得商品品类：</span>
		                                   	<label class="radio-inline">
		                                   		<input type="radio" disabled  class="fetchType" value="1" name="fetchType" <#if coupon.fetchType == "1">checked</#if> ><span>全品类</span>
		                                   	</label>
		                                   	<label class="radio-inline" style='padding-right:20px;'>
		                                   		<input type="radio" disabled class="fetchType" value="0"  name="fetchType" <#if coupon.fetchType == "0">checked</#if> ><span>匹配品类</span>
		                                   	</label>
		                                </div>
		                                <div class="col-sm-4">
		                                   	<span>单笔订单中，满    </span>${coupon.fetchValueLimitYuan}元可获得</span>
		                               	</div>       
		                            </div>
		                        </div>
			                    <div class="row fetchTypeDetailDiv" <#if coupon.fetchType == "1">style="display: none;"</#if> >
		                        	<div class="col-md-12">
		                        		<div class="col-sm-12">
		                        			<span>获取时，匹配品类详情如下：</span>
		                        			<span id="fetchTypeDetail">${coupon.fetchTypeDetailNames}</span>
		                        		</div>
		                        	</div>
		                        </div>
		                        <div class="row">
		                            <div class="col-md-12">
		               					<div class="col-sm-4">
		               						<span class="detail_span">使用商品品类：</span>
		                                   	<label class="radio-inline">
		                                   		<input type="radio" disabled  class="useType" name="useType"  value="1" <#if coupon.useType == "1">checked</#if> ><span>全品类</span>
		                                   	</label>
		                                   	<label class="radio-inline" style='padding-right:20px;'>
		                                   		<input type="radio" disabled class="useType" name="useType"  value="0" <#if coupon.useType == "0">checked</#if> ><span>匹配品类</span>
		                                   	</label>
		                                </div>
		                                <div class="col-sm-4">
		                                   	<span>单笔订单中，满    </span>${coupon.userValueLimitYuan}<span>元可使用</span>
		                               	</div>       
		                            </div>
		                        </div>  
		                        <div class="row useTypeDetailDiv" <#if coupon.useType == "1">style="display: none;"</#if>>
		                        	<div class="col-md-12">
		                        		<div class="col-sm-12">
		                        			<span>使用时，匹配品类详情如下：</span>
		                        			<span id="fetchTypeDetail">${coupon.useTypeDetailNames}</span>
		                        		</div>
		                        	</div>
		                        </div>  
                            </div>
                        </div>
                        <!-- <div id="tab_2" class="tab-pane">
                            <div class="panel-body">
		                        <div class="tab-span">
                                    <div class="col-sm-8">
                                    	<span>已发送：${coupon.sendNum}张</span><span>已使用：${usedNum}张</span>
                                    </div>
		                        </div>
	                            <div class="box-body">
	                            
				                    <table id="dataList" class="table table-bordered table-hover table-color" >
				                        <thead>
				                            <th>序号</th>
				                            <th>手机号</th>
				                            <th>消费者名称</th>
				                            <th>领用时间</th>
				                            <th>使用时间</th>
				                            <th>订单编号</th>                                                                        
				                        </thead>
				                        <tbody>
				                        </tbody>
				                    </table>
			                    </div>        
                            </div>
                        </div> -->
                        <!-- <div id="tab_3" class="tab-pane">
                            <div class="panel-body">		                        
	                            <div class="box-body">	                            
				                 	<div class="cooperate">	                            
				                    </div>   
			                    </div>        
                            </div>
                        </div> -->
                    </div>
                </div>
				<div class="row">
	                <div class="col-sm-12 text-center">
	                    <button class="btn btn-success backPage" type="button"><i class="fa fa-backward"> 返回 </i></button>		                   
	                 </div>
                 </div>  						              
                  </div>                        

        </div>
        <div class="clearfix"></div>     
      </div>
   </div>
</body>
<#include "/footer.ftl" />
 <script src="${uiBase}/js/pages/coupon/coupon_detail.js?v=${resourceVersion}"></script>
</html>
