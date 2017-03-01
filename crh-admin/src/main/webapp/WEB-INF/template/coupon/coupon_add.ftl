<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/coupon.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}vendor/zTree/css/zTreeStyle.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
       <section class="content-header">
	 	   <ol class="breadcrumb">
			   <li><i class="fa fa-dashboard"></i> 营销管理</li>
			   <li>添加优惠券</li>
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
               					<div class="col-sm-4">
                                   	<label class="control-label" for="type-select"><span class="requiredField">*</span>优惠券名称：</label>                                    
                                   	<input type="text" class="form-control" name="couponName" id="couponName">
                               </div>
                                <div class="col-sm-4">
                                   	<label class="control-label" for="type-select"><span class="requiredField">*</span>优惠券面额：</label>                            
                                   	<input type="text" class="form-control" name="valueYuan" id="valueYuan" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"  onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" maxlength="8">
                               </div>
                            </div>
                        </div>
                        <div class="row rechargeCoupon">
                            <div class="col-md-12 date">                            
                               <div class="col-sm-4">
                                   	<label class="control-label" for="type-select"><span class="requiredField">*</span>开始使用日期：</label>                                    
                                    <input type="hidden"  name="usDateInput" id="usDateInput" value="" />
									<div class="dateDiv" style="margin-bottom: 0px;">
									    <input size="10" type="text" name="usDate" id="usDate" class="form-control keyword beginDate" placeholder="请选择时间" readonly>
									    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
									    <span class="add-on"><i class="icon-calendar"></i></span>
									</div>
									<span class="fieldError dateError hidden">请选择开始使用日期</span>										    																				
								</div>
								<div class="col-sm-4">
									<label class="control-label" for="type-select"><span class="requiredField">*</span>结束使用日期：</label>									
									<input type="hidden"  name="ueDateInput" id="ueDateInput" value="" />
									<div class="dateDiv" style="margin-bottom: 0px;">
									    <input size="10" type="text" name="ueDate" id="ueDate" class="form-control keyword endDate" placeholder="请选择时间" readonly>
									    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
									    <span class="add-on"><i class="icon-calendar"></i></span>
									</div>
									<span class="fieldError dateError hidden">请选择结束使用日期</span>										    										
                                </div>
                            </div>
                        </div>       
                        <div class="row">
                            <div class="col-md-12">
               					<div class="col-sm-6">
                                   	<label class="control-label" for="type-select"><span class="requiredField">*</span>优惠券类别：</label>
                                    <input type="checkbox" class="typeListII" name="useCategory" id="useCategory" checked="checked" value="1"><span>后台发放</span>
                                    <span class="fieldError categoryError hidden">优惠券类型必须选择一个</span>
                                </div>                                
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
               					<div class="col-sm-6">
                                   	<label class="control-label" for="type-select"><span class="requiredField">*</span>适用平台：</label>
                                    <input type="checkbox" name="webUse" id="webUse" value="1" checked="checked"><span>微信商城</span>
                                    <span class="fieldError platError hidden">适用平台必须选择一个</span>
                                </div>                                
                            </div>
                        </div>
                        <input type="hidden" id="zNodes" name="zNodes" value="" />
                        <!-- 获得品类加载tree标识 -->
                        <input type="hidden" id="fetchTypeInit" name="fetchTypeInit" value="0" />
                        <!-- 使用品类加载tree标识 -->
                        <input type="hidden" id="useTypeInit" name="useTypeInit" value="0" />
                        <!-- 获得品类data -->
                        <input type="hidden" id="fetchTypeData" name="fetchTypeData" value="" />
                        <!-- 使用品类data -->
                        <input type="hidden" id="useTypeData" name="useTypeData" value="" />
                        <div class="row">
                            <div class="col-md-12">
               					<div class="col-sm-4">
               						<label class="control-label" for="type-select"><span class="requiredField">*</span>获得商品品类：</label>
                                   	<label class="radio-inline" style="width:auto;">
                                   		<input type="radio"  class="fetchType" name="fetchType" checked="checked" value="1" style='margin-top:10px;'><span>全品类</span>
                                   	</label>
                                   	<label class="radio-inline" style='padding-right:20px;width:auto;'>
                                   		<input type="radio" class="fetchType" name="fetchType"  value="0" style='margin-top:10px;'><span>选择品类</span>
                                   	</label>
                                   	<label style="display: none;width:54px;" id="fetchType">
                                   		<input id="fetchTypeSelect" style="width:100%" type="text"  class="form-control  fetchTypeSelect" placeholder="选择" readonly value="" />
                                   	</label>
                                </div>
                                <div class="col-sm-4">
                                   	<span>单笔订单中，满    </span><input type="text" class="form-control  fetchTypeYuan" name="fetchTypeYuan" id="fetchTypeYuan" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"  onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" maxlength="8"><span>元可获得</span>
                               	</div>       
                            </div>
                        </div>
                        <div class="row fetchTypeDetailDiv" style="display: none;">
                        	<div class="col-md-12">
                        		<div class="col-sm-12">
                        			<p style="width:auto;" class="clearfix">
										<label style="float: left;width: auto;">获取时，匹配品类详情如下：</label>
                        				<span id="fetchTypeDetail" style="float: left;width:610px;margin-top: 8px;"></span>
                        				<span class="fieldError dateError hidden fetchTypeDetail">请选择商品品类</span>
                        			</p>
                        		</div>
                        	</div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
               					<div class="col-sm-4">
               						<label class="control-label" for="type-select"><span class="requiredField">*</span>使用商品品类：</label>
                                   	<label class="radio-inline" style="width:auto;">
                                   		<input type="radio"  class="useType" name="useType" checked="checked" value="1" style='margin-top:10px;'><span>全品类</span>
                                   	</label>
                                   	<label class="radio-inline" style='padding-right:20px;width:auto;'>
                                   		<input type="radio" class="useType" name="useType"  value="0" style='margin-top:10px;'><span>选择品类</span>
                                   	</label>
                                   	<label style="display: none;width:54px;" id="useType">
                                   		<input id="useTypeSelect" style="width:100%" type="text"  class="form-control  useTypeSelect" placeholder="选择" readonly value="" />
                                   	</label>
                                </div>
                                <div class="col-sm-4">
                                   	<span>单笔订单中，满    </span><input type="text" class="form-control  useTypeYuan" name="useTypeYuan" id="useTypeYuan" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"  onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" maxlength="8"><span>元可使用</span>
                               	</div>       
                            </div>
                        </div>  
                        <div class="row useTypeDetailDiv" style="display: none;">
                        	<div class="col-md-12">
                        		<div class="col-sm-12">
                        			<p  style="width:auto;" >
                                        <label style="float: left;width: auto;">使用时，匹配品类详情如下：</label>
                        				<span id="useTypeDetail" style="float: left;width:610px;margin-top: 8px;"></span>
                        				<span class="fieldError dateError hidden useTypeDetail">请选择商品品类</span>
                        			</p>
                        		</div>
                        	</div>
                        </div>  
                        <div class="row">
			                <div class="col-sm-12 text-center">
			                    <button class="btn btn-primary submitMainForm" type="button"  id="couponAdd">保存</button>
			                    <button class="btn btn-success backPage" type="button"><i class="fa fa-backward"> 返回 </i></button>		                   
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
   <div class="modal fade in Tree" tabindex="-1" role="dialog">
   		<div class="modal-dialog">
   		<div class="modal-content" style='width:500px;'>
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">获得商品品类设置</h4>
	      </div>
	      <div class="modal-body">
	        <div style='overflow:auto;'><ul id="categoryTree" class="ztree" style="margin-top:0; width:250px; height: 500px;"></ul></div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
	        <!-- <button type="button" class="btn btn-primary">确定</button> -->
	      </div>
	    </div>
	</div>
	</div>
	<div class="modal fade in Tree2" tabindex="-1" role="dialog">
   		<div class="modal-dialog">
   		<div class="modal-content" style='width:500px;'>
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">使用商品品类设置</h4>
	      </div>
	      <div class="modal-body">
	        <div style='overflow:auto;'><ul id="categoryTree2" class="ztree" style="margin-top:0; width:250px; height: 500px;"></ul></div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
	        <!-- <button type="button" class="btn btn-primary">确定</button> -->
	      </div>
	    </div>
	</div>
	</div>
</body>
<#include "/footer.ftl" />
 <script src="${uiBase}/js/pages/coupon/coupon_add.js?v=${resourceVersion}"></script>
 <script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.core.min.js"></script>
 <script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.excheck.js"></script>
 
</html>
