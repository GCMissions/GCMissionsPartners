<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/financeReport.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
       <section class="content-header">
	 	   <ol class="breadcrumb">
			   <li><i class="fa fa-dashboard"></i> 财务报表</li>
			   <li>财务报表</li>
		   </ol> 
       </section>
       
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                       <form id="orderForm" method="get" action="${base}/web/financeReport/export">                   
                       <div class="row">
                            <div class="col-md-12">
               					<div class="col-sm-3">
                                    <label class="control-label" for="type-select">商家名称：</label>
                                    <input type="text" name="orgName" id="orgName" class="form-control">
                                </div>
                                <div class="col-sm-3">
                                    <label class="control-label" for="type-select">商家类型：</label>                                  
                                    <select class="form-control" name="orgType" id="orgType">
                                        <option value="z">终端配送商</option>
                                        <option value="p">区域平台商</option> 
                                    </select>
                                </div>
                                <div class="col-sm-6">
                                    <label class="control-label" for="type-select">订单时间：</label>
                                    <input type="hidden"   id="csDateInput" value="" />
									<div class="dateDiv" style="margin-bottom: 0px;">
									    <input size="10" type="text" name="sDate" id="csDate" class="form-control keyword beginDate" placeholder="请选择时间" readonly>
									    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
									    <span class="add-on"><i class="icon-calendar"></i></span>
									</div>
									<span class="time">至</span>
									<input type="hidden"   id="ceDateInput" value="" />
									<div class="dateDiv" style="margin-bottom: 0px;">
									    <input size="10" type="text" name="eDate" id="ceDate" class="form-control keyword endDate" placeholder="请选择时间" readonly>
									    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
									    <span class="add-on"><i class="icon-calendar"></i></span>
									</div>
                                </div>
                            </div>
                        </div>
                        </form>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-12">
                                	<button class="btn  btn-primary" id="refreshRecord"><i class="fa fa-search"></i>开始搜索</button>
	                            	<button type="button" class="btn btn-default reloadPage"><i class="fa  fa-refresh"></i> 刷新</button>
                                	<a class="btn btn-default" id="excel"><i class="fa fa-refresh"></i> 导出</a>                              
                                </div>
                            </div>
                        </div>                   
                    </div>                   
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <caption>
                        	订单总额：<span id="totalAmount"></span> 
                        	实付金额：<span id="actualAmount"></span> 
                        	优惠券总金额：<span id="couponAmount"></span>
							总毛利：<span id="totalProfit"></span> 
							平台总分利：<span id="totalSPProfit"></span> 
							区域平台商总分利：<span id="pTotalSPProfit"></span> 
							终端配送商总分利：<span id="zTotalSPProfit"></span>
						</caption>
						<thead>
							<th>序号</th>
							<th>商家名称</th>
							<th>商家类型</th>
							<th>商家商品分利</th>
							<th>商家配送费分利</th>
							<th>罚款总额</th>
							<th>奖励总额</th>
							<th>推广费用</th>
							<th>操作</th>
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
<script src="${uiBase}/js/pages/financeReport/financeReport_list.js?v=${resourceVersion}"></script>
</html>
