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
			   <li>优惠券管理</li>
		   </ol> 
       </section>
       
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                    	<div class="row">
                             <div class="col-sm-3" style="width:260px;">
                                 <label class="control-label" for="type-select">优惠券名称：</label>
                                 <input type="text" id="couponName" class="form-control">
                             </div>
                             <div class="col-sm-3" style="width:260px;">
                                 <label class="control-label" for="type-select">优惠券状态：</label>                                  
                                 <select class="form-control" id="status">
                                     <option value="">请选择</option>
                                     <option value="1">启用</option>
                                     <option value="2">禁用</option>
                                     <option value="0">失效</option> 
                                 </select>
                             </div>
                       	</div>
                       	<div class="row">
                            <!-- <div class="col-sm-3">
                                  <label class="control-label" for="type-select">优惠券编号：</label>
                                  <input type="text" id="couponId" class="form-control" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="8">
                             </div> -->
                               <div class="col-sm-3" style="width:260px;">
                                  	<label class="control-label" for="type-select">优惠券面额：</label>
                                   	<input type="text" id="valueYuan" class="form-control" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"  onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" data-rule-price="true" maxlength="8">
                               </div>	
                               <div class="col-sm-6" style="width:520px;">
                                   	<label class="control-label" for="type-select">创建日期：</label>	
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
                        <input type="hidden" id="zNodes" name="zNodes" value="" />
                        <!-- 使用品类加载tree标识 -->
                        <input type="hidden" id="useTypeInit" name="useTypeInit" value="0" />
                        <!-- 使用品类data -->
                        <input type="hidden" id="useTypeData" name="useTypeData" value="" />
                        <div class="row">
                             <div class="col-sm-8">
                                    <label class="control-label" for="type-select">优惠券品类：</label>
                                    <label class="radio-inline" style="width:65px;">
                                   		<input type="radio"  class="useType" name="useType"  value="1" style='margin-top:10px;'><span>全品类</span>
                                   	</label>
                                   	<label class="radio-inline" style='padding-right:20px;'>
                                   		<input type="radio" class="useType" name="useType"  value="0" style='margin-top:10px;'><span>选择品类</span>
                                   	</label>
                                    <label style="display: none;width:54px;" id="useTypeShow">
                                   		<input id="useTypeSelect" type="text"  class="form-control  useType" placeholder="选择" readonly value="" style="width: 100%;" />
                                   	</label>
                                	<p  style="display: none;width:auto;" class="useTypeDetail clearfix">
                        				<label style="float: left;">详情如下：</label>
                        				<span id="useTypeDetail" style="text-align:left;float:left;width:610px;margin-top:8px;"></span>
                        			</p>
                             </div>
                        </div>
                        <div class="row">
                           <div class="col-md-12">
                               	<button class="btn  btn-primary" id="refreshRecord"><i class="fa fa-search"></i>开始搜索</button>
<!--                            <button type="button" class="btn btn-danger removeItem" disabled id="removeRecord"><i class="fa fa-times"></i> 删除</button>  --> 
                           		<button type="button" class="btn btn-default reloadPage"><i class="fa  fa-refresh"></i> 刷新</button>
                               	<a class="btn btn-default" href="toAddCoupon"><i class="fa fa-plus"></i> 添加优惠券</a>
                               	<button type="button" id="sendCoupon" class="btn btn-default "><i class="fa  fa-send"></i> 发送</button>
                               	<input name="couponIds" id="couponIds" style="display: none;" value="" />
                     			<input name="userIds" id="userIds" style="display: none;" value="" />
                             </div>
                        </div>                   
                    </div>                   
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<th><input type="checkbox" /></th>
                        	<th>序号</th>
                            <th>优惠券名称</th>
                            <th>优惠券面额(元)</th>
                            <th>添加日期 </th>
                            <th>有效期</th>
                            <th>优惠券状态</th>
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
   <div class="modal fade in Tree" tabindex="-1" role="dialog">
   		<div class="modal-dialog">
   		<div class="modal-content" style='width:500px;'>
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">使用商品品类设置</h4>
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
	<form id="userTmlForm" method="get" class="form-horizontal" action="/admin/web/coupon/toTmlExcel">
	</form>
</body>
<script type="text/html" id="sendCouponTpl">
<form id="addEditForm" method="post" class="form-horizontal">
 	<div class="row pad" style='max-height:350px;overflow-y:auto;'>
     	<div class="form-horizontal" >
			<div id="onCouponShow" style="display: none;">
				<div class="form-group col-sm-6">   
					<label class="col-sm-5  control-label" for="type-select">优惠券名称：</label>
					<input id="couponName" class="form-control col-sm-2" readOnly type="text" value="{{couponName}}">
				</div>
				<div class="form-group col-sm-6">   
					<label class="col-sm-5  control-label" for="type-select">优惠券金额（元）：</label>
					<input id="valueYuan" class="form-control col-sm-2" readOnly type="text" value="{{valueYuan}}">
				</div>
				<div class="form-group col-sm-6">   
					<label class="col-sm-5  control-label" for="type-select">生效时间：</label>
					<input id="invalidDate" class="form-control col-sm-2" readOnly type="text" value="{{effectDate}}">
				</div>
				<div class="form-group col-sm-6">   
					<label class="col-sm-5  control-label" for="type-select">失效时间：</label>
					<input id="invalidDate" class="form-control col-sm-2" readOnly type="text" value="{{invalidDate}}">
				</div>
				<div class="form-group col-sm-6">   
					<label class="col-sm-5  control-label" for="type-select">获取商品品类：</label>
					<input id="type" class="form-control col-sm-2" readOnly type="text" value="{{fetchTypeDetailNames}}">
				</div>
				<div class="form-group col-sm-6">   
					<label class="col-sm-5  control-label" for="type-select">使用商品品类：</label>
					<input id="type" class="form-control col-sm-2" readOnly type="text" value="{{useTypeDetailNames}}">
				</div>
			</div>
			<div id="moreCouponShow" style="display: none;">
				<div class="form-group col-sm-6">   
					<label class="col-sm-5  control-label" for="type-select" style="margin-left: 115px;">发送的优惠券数量：{{couponCnt}}张</label>
				</div>
			</div>
			<div class="form-group col-sm-10">   
				<label class="col-sm-5  control-label" for="type-select">发送：</label>
				<label class="radio-inline">
                     <input type="radio"  class="sendType" name="sendType" checked="checked" value="0" ><span>全网发送</span>
                </label>
                <label class="radio-inline" style='padding-right:20px;'>
                     <input type="radio" class="sendType" name="sendType"  value="1" ><span>指定用户</span>
                </label>
				<div id="exportDiv" style="display: none">
					<input type="file" name="postExcel" id="postExcel" style="display:none;" accept=".xls,.xlsx">
				</div>
				<lable style="display:none;" id="batchSend">
					<button data-exceltype="1" id="import" class="btn btn-primary" type="button"><i class="fa fa-plus"></i> 批量发送</button>
					<button data-exceltype="1" id="importTml" class="btn btn-primary" type="button"><i class="fa fa-download"></i> 批量导入模板</button>
				</lable>
			</div>
			<div class="form-group col-sm-12 phoneDiv" style="display: none;">   
				<input id="sPhone" class="form-control col-sm-2" name="phone"  type="text" maxlength="11" placeholder="请输入用户手机号" data-msg-required="请输入手机号" data-rule-ismobile="true" required style="width:271px;margin-left: 115px;">
				<button type="button" class="btn btn-primary" style="margin-left: 20px;" id="searchmember"><i class="fa fa-search addPhone"></i> 添加</button>  
			</div>
		   
		</div> <!-- search group -->
		<div class="box-body">
		  	<table id="chooseUsersList" class="table table-bordered table-hover dataList" style="width:750px;margin: 0 auto;display: none;">
				<thead>
					<th align="center" width="35%">手机号</th>
					<th align="center" width="25%">操作</th>
				</thead>
		  	</table>
		</div>
	</div>
</form>
</script>
<script id="editTpl" type="text/html">
<div class="box-body form-horizontal addEditTpl">
	<div class="form-group row">
		<label class="col-sm-4  control-label" for="type-select">优惠券名称：</label>
		<div class="form-group col-sm-6">   
			<input name="couponName" class="form-control col-sm-6" style="margin-left: 15px;" readOnly type="text" value="{{couponName}}">
			<input type="hidden"  name="couponId" value="{{couponId}}" />
		</div>
	</div>	
	<div class="form-group row">
		<label class="col-sm-4 control-label" for="type-select">状态：</label>
		<div class="col-sm-6">
			<select class="form-control" name="status" id="couponSelect">
					<option value="1" {{if status == '启用'}}selected {{/if}}> 启用 </option>
					<option value="0" {{if status == '禁用'}}selected {{/if}}> 禁用 </option>
			</select>
		</div>
    </div>
</div>
</script>
<#include "/footer.ftl" />
 <script src="${uiBase}/vendor/input-mask/jquery.inputmask.js"></script>
 <script src="${uiBase}/js/pages/coupon/coupon_list.js?v=${resourceVersion}"></script>
 <script src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js?v=${resourceVersion}"></script>
 <script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.core.min.js"></script>
 <script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.excheck.js"></script>
</html>
