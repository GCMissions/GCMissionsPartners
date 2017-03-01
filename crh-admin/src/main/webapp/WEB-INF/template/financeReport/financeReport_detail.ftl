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
                      	<div class="row">
		                    <div class="col-md-6">
		               			<div class="col-sm-4">
	                                <span>超时惩罚：</span>
	                                <span id="totalFine"></span>
		                         </div>
		                         <div class="col-sm-4">
	                                <span>转单奖励：</span>
	                                <span id="totalReward"></span>
		                         </div>
		                         <div class="col-sm-4">
	                                <span>注册奖励：</span>
	                                <span id="totalReferee"></span>
		                         </div>	                                                            
		                     </div>
		                 </div>                                      
                    </div>                   
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
						<thead>
							<th>序号</th>
							<th>订单编号</th>
							<th>配送方式</th>
							<th>订单金额(元)</th>
							<th>优惠券金额(元)</th>
							<th>配送费(元)</th>
							<th>实付金额(元)</th>
							<th>平台配送费分利(元)</th>
							<th>平台商品分利(元)</th>
							<th>区域平台商配送费分利(元)</th>
							<th>区域平台商商品分利(元)</th>
							<th>终端配送商配送费分利(元)</th>
							<th>终端配送商商品分利(元)</th>
						</thead>
						<tbody>
						</tbody>
                      </table>
                    </div>           
                </div>
             </div>
             <input type="hidden" id="orgId" value="${dto.orgId}">
             <input type="hidden" id="orgType" value="${dto.orgType}">
             <input type="hidden" id="beginDate" value="${beginDate}">
             <input type="hidden" id="endDate" value="${endDate}">
        </div>
        <div class="clearfix"></div>     
      </div>
   </div>
</body>
<#include "/footer.ftl" />
<script src="${uiBase}/js/pages/financeReport/financeReport_detail.js?v=${resourceVersion}"></script>
</html>
