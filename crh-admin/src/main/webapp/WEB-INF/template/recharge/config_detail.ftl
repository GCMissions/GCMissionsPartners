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
			   <li>添加充值折扣</li>
		   </ol> 
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                    	<form id="configForm" method="post" action="">  
                    	<div class="row">
                            <div class="col-md-12">
               					<div class="col-sm-3">
                                   	<label class="control-label" for="type-select">充值金额：</label>
                                    <input type="text" class="form-control hidden"  id="configId" value="${config.configId}">                                   
                                   	<input type="text" class="form-control" name="amountYuan" id="amountYuan" value="${config.amountYuan}">
                               </div>
                               <div class="col-sm-8">
                                   	<label class="control-label" for="type-select">备注：</label>
                                   	<input type="text" class="form-control" name="note" id="note" value="${config.note}">
                               </div>
                            </div>
                        </div>                                                         
                        <div class="row">
                            <div class="col-md-12">
               					 <div class="col-sm-3">
              					    <label class="control-label" for="type-select">添加优惠券：</label>
                                    <a class="btn btn-default addRelatedGoods"><i class="fa fa-plus"></i> 添加</a>
                                 </div>                               
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
		                        <div class="relatedGoods-list">
								  	<div class="box-body">
						              	<table id="relatedGoodsListTable" class="table table-bordered table-hover" >
							                <thead>						                    
							                    <th>序号</th>						                   
							                    <th>优惠券编号</th>
							                    <th>优惠券名称</th>
							                    <th>优惠券面额</th>
							                    <th>优惠券张数</th>
							                    <th style="width:200px;">操作</th>
							                </thead>
						                	<tbody>
						               		</tbody>
						              	</table>
						            </div>
							 	</div>
							</div>
                    	</div>                                      
                        </form>   
                        <div class="row">
			                <div class="col-sm-12 text-center">
			                    <button class="btn btn-primary submitMainForm" type="button" id="configSumbit">保存</button>
			                    <button class="btn btn-success backPage" type="button"><i class="fa fa-backward"> 返回 </i></button>		                   
			                 </div>
		                </div>                           
                    </div>                        
                </div>
             </div>
        </div>
        <div class="clearfix"></div>     
      </div>
   </div>
</body>
<script>
   var productsList = [];
   <#list config.couponDtos as coupon>
   productsList.push({
	    couponId    : "${coupon.couponId}",
	    couponName  : "${coupon.couponName}",
	    valueYuan   : "${coupon.valueYuan}",
	    num         : "${coupon.num}"
 	});
   </#list>
 </script>
<#include "/footer.ftl" />
 <script src="${uiBase}/vendor/input-mask/jquery.inputmask.js"></script>
 <script src="${uiBase}/js/pages/recharge/config_detail.js?v=${resourceVersion}"></script>
</html>
