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
			   <li>充值折扣列表</li>
		   </ol> 
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                    	<form id="couponAddForm" method="post" action="">  
                    	<div class="row">
                    	    <div class="col-md-12">                   	
	                            <div class="col-md-3">
	                               <a class="btn btn-default" href="detail/0"><i class="fa fa-plus"></i> 添加</a>                                  
	                            </div>
                            </div>
                        </div>  
                        <div class="row">
                            <div class="col-md-12">
		                        <table id="dataList" class="table table-bordered table-hover" >
			                        <thead>
			                            <th>充值金额</th>
			                            <th class="span_length"><span>优惠券编号</span><span>优惠券名称</span><span>优惠券面额</span><span>优惠券数量</span></th>
			                            <th style="width: 200px;">操作</th>                                                                         
			                        </thead>
			                        <tbody>
	                        		</tbody>
                      			</table>
							</div>
                    	</div>                        
                        </form>
                         <div class="row">
			                <div class="col-sm-12 text-center">
			                    <button class="btn btn-success backPage" type="button"><i class="fa fa-backward"> 返回 </i></button>		                   
			                 </div>
		                </div>                            
                    </div>                        
                </div>
             </div>
        </div>
      </div>
   </div>
</body>
<#include "/footer.ftl" />
 <script src="${uiBase}/js/pages/recharge/config_add.js?v=${resourceVersion}"></script>
</html>
