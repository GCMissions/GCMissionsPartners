<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/points.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
       <section class="content-header">
	 	   <ol class="breadcrumb">
			   <li><i class="fa fa-dashboard"></i> 营销管理</li>
			   <li>积分设置</li>
		   </ol> 
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border"> 
                        <form id="pointAddForm" method="post" action="">
                        <div class="row">                        
	                        <div class="col-md-12">
		                        <div class="col-sm-6">
		                            <label class="control-label" for="type-select">注册积分值：</label>
		                            <input type="text" id="registerPoints" name="registerPoints" class="form-control" value="${points.registerPoints}">
		                        </div>
	                        </div>
                        </div>
                        <div class="row">                                                
	                        <div class="col-md-12">
		                        <div class="col-sm-6">
		                            <label class="control-label" for="type-select">消费积分比：</label>
		                            <span>每消费</span>
		                            <input type="text" class="form-control hidden" id="buyAmountHidden"  value="${points.buyAmount}">		                            
		                            <input type="text" class="form-control points" id="buyAmount" name="buyAmount" value="">
		                            <span>元可获</span>
		                            <input type="text"class="form-control points" id="buyPoints" name="buyPoints"value="${points.buyPoints}">
		                            <span>积分</span>
		                        </div> 
	                        </div>
                        </div>
                        <div class="row">
	                        <div class="col-md-12">
		                        <div class="col-sm-6">
		                            <span class="point-red">积分采取小数点上浮规则，取整</span>
		                        </div> 
	                        </div> 
                        </div>                            
                        <div class="row">
         					<div class="col-sm-12 text-center">
             					<button class="btn btn-primary" type="button" id="pointAdd">保存</button>
                            </div>                             
                        </div>  
                        </form>	                         
                    </div>                        
                </div>
             </div>
        </div>
        <div class="clearfix"></div>     
      </div>
   </div>
</body>
<#include "/footer.ftl" />
 <script src="${uiBase}/js/pages/points/points_main.js?v=${resourceVersion}"></script>
</html>
