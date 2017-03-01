<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/recharge.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
       <section class="content-header">
	 	   <ol class="breadcrumb">
			   <li><i class="fa fa-dashboard"></i> 营销管理</li>
			   <li>充值管理</li>
		   </ol> 
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                       <div class="row">
                            <div class="col-md-12">
               					<div class="col-sm-3">
                                    <label class="control-label" for="type-select">手机号：</label>
                                    <input type="text" id="memberPhone" class="form-control">
                                </div>
                                <div class="col-sm-3">
                                    <label class="control-label" for="type-select">消费者姓名：</label>
                                    <input type="text" id="memberName" class="form-control">
                                </div>
                                <div class="col-sm-6">
                                    <label class="control-label" for="type-select">充值日期：</label>
                                    <input type="hidden"  name="csDateInput" id="csDateInput" value="" />
									<div class="dateDiv" style="margin-bottom: 0px;">
									    <input size="10" type="text" name="csDate" id="csDate" class="form-control keyword beginDate" placeholder="请选择时间" readonly>
									    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
									    <span class="add-on"><i class="icon-calendar"></i></span>
									</div>
									<span class="time">至</span>
									<input type="hidden"  name="ceDateInput" id="ceDateInput" value="" />
									<div class="dateDiv" style="margin-bottom: 0px;">
									    <input size="10" type="text" name="ceDate" id="ceDate" class="form-control keyword endDate" placeholder="请选择时间" readonly>
									    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
									    <span class="add-on"><i class="icon-calendar"></i></span>
									</div>
                                </div>
                            </div>
                        </div>
                         <div class="row">
                            <div class="col-md-12">
               					<div class="col-sm-3">
                                    <label class="control-label" for="type-select">充值金额：</label>
                                    <input type="text" id="amount" class="form-control" style="ime-mode: disabled;" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="7">
                                </div>
                                <div class="col-sm-3">
                                    <label class="control-label" for="type-select">支付方式：</label>                                  
                                    <select class="form-control" id="paymentType">
                                        <option value="">请选择</option> 
                                        <option value="2">支付宝</option>
                                        <option value="3">微信</option>
                                    </select>
                                </div>
                                <div class="col-sm-3">
                                    <label class="control-label" for="type-select"></label>
                                    <input type="text" id="couponValue" class="form-control hidden" >
                                </div>
                                
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-12">	                                
	                                <button class="btn  btn-primary" id="refreshRecord"><i class="fa fa-search"></i>开始搜索</button>
                                    <button type="button" class="btn btn-default reloadPage"><i class="fa  fa-refresh"></i> 刷新</button>
                                	<a class="btn btn-default" href="toAdd"><i class="fa fa-plus"></i> 设置充值折扣</a>                                
                                </div>
                            </div>
                        </div>                   
                    </div>                   
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<th>序号</th>
                            <th>充值金额</th>
                            <th>支付方式</th>
                            <th>充值时间</th>
                            <th>手机号</th> 
                            <th>消费者姓名</th>                                                                        
                        </thead>
                        <tbody>
                        </tbody>
                      </table>
                    </div>           
                </div>
             </div>
        </div>
        <div class="clearfix"></div>     
      </div>
   </div>
</body>
<#include "/footer.ftl" />
 <script src="${uiBase}/vendor/input-mask/jquery.inputmask.js"></script>
 <script src="${uiBase}/js/pages/recharge/config_list.js?v=${resourceVersion}"></script>
</html>
