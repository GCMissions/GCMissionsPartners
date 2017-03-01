<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">

<style>
.datagrid-header-input{
    display: inline-block;
    width: 160px;
}
.hideProductId{
	display:none;
}
.returnSpan{
	color: #3c8dbc;
	cursor: pointer;
}
.bootstrap-select {
  width: 161px !important;
}
.btnGroup{
	margin-left:70px;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
         
          <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i>订单管理</li>
            <li>退货订单管理</li>
          </ol>
          
         </section>
        
       
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                
                	<div class="form-horizontal search-group" >
                    	<div class="box-body">
                    		
                       		<div class="form-group">
                       				
								<label class="col-sm-2 control-label">订单编号:</label>
								<input  class="form-control col-sm-2" id="orderId" type="text">
								
								<label class="control-label col-sm-2" for="type-select">订单时间：</label>
								<div class="col-sm-6 no-padding datetimeInputGroup" >
									<input type="hidden" data-ignore="true"  name="stDate" id="stDate" value="" />
									<div class="input-group date datetimeInput  no-padding pull-left" style="margin-bottom: 0px;">
										<input size="16" type="text"  name="startTime" id="csDate" class="form-control keyword beginDate" placeholder="开始时间" readonly>
										<span class="add-on" style="display:none"><i class="icon-remove"></i></span>
										<span class="add-on"><i class="icon-calendar"></i></span>
									</div>
									<span class="pull-left textTo">至</span>
									<input type="hidden" data-ignore="true"  name="endTime" id="edDate" value="" />
									<div class="input-group date datetimeInput no-padding pull-left" style="margin-bottom: 0px;">
										<input size="16" type="text" name="endDate"  id="ceDate" class="form-control keyword endDate" placeholder="结束时间" readonly>
										<span class="add-on" style="display:none"><i class="icon-remove"></i></span>
										<span class="add-on"><i class="icon-calendar"></i></span>
									</div>
								</div>
                            	
                       		</div>
                       		
                       		<div class="form-group" style="padding-left:32px;">
                    		    <div class="col-sm-4 no-padding">
                    		    	<label class="col-sm-5 control-label" for="type-select">配送方式：</label>
	                            	<select class="col-sm-1 selectpicker form-control" id="shipmentType" name="shipmentType">
	                             		<option value="" selected="selected">全部</option>
	                             		<#list shipmentType as type>
					                    <option value="${type.key}">${type.value}</option> 
					                    </#list>
	                             	</select>
                    		    </div>
                    		    
                             	<div class="col-sm-4 no-padding">
                    		    	<label class="col-sm-5 control-label" for="type-select">订单状态：</label>
	                            	<select class="col-sm-1 selectpicker form-control" id="status" name="status">
	                             		<option value="" selected="selected">全部</option>
	                             		<#list orderStatus as status>
				                        <option value="${status.key}">${status.value}</option> 
				                        </#list>
	                             	</select>
                    		    </div>
                    		    
                    		    <div class="col-sm-4 no-padding">        
	                           		<label class="col-sm-5 control-label">联系电话:</label>
									<input  class="form-control col-sm-1" id="phone" type="text">
								</div>
                       		</div>
                       		
                       		<!-- 
                       		<div class="form-group">
                    		    <label class="col-sm-2 control-label" for="type-select">配送方式：</label>
	                            <select class="col-sm-2 selectpicker form-control" id="shipmentType" name="shipmentType">
	                             	<option value="" selected="selected">全部</option>
	                             	<option value="0" >已创建</option>
	                             	<option value="1" >已发货</option>
	                                <option value="2" >已收货</option>
	                             </select>
                    		    
                    		    <label class="col-sm-2 control-label" for="type-select">订单状态：</label>
	                            <select class="col-sm-2 selectpicker form-control" id="status" name="status">
	                             	<option value="" selected="selected">全部</option>
	                             	<option value="0" >已创建</option>
	                             	<option value="1" >已发货</option>
	                                <option value="2" >已收货</option>
	                             </select>
                    		          
	                           	<label class="col-sm-2 control-label">联系电话:</label>
								<input  class="form-control col-sm-2" id="phone" type="text">
								
                       		</div>
                       		 -->
                    		
                       		<div class="form-group">
                       			<div class="btnGroup">
                       				<button class="btn  btn-primary" id="search"><i class="fa fa-search"></i>开始搜索</button>
                       				<button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>
                       			</div>
                       		</div>
                       		
                    	</div><!-- /.box-header -->
                    </div>
                    <#if auth.hasPermission("order.returnOrder.drawback")>
					<input type="hidden" id="returnPerssion" value=1>
					<#else>
					<input type="hidden" id="returnPerssion" value=0>
					</#if>
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<th>序号</th>
                            <th>订单编号</th>
                            <th>订单金额（元）</th>
                            <th>消费者姓名</th>
                            <th>联系电话</th>
                            <th>订单时间</th>
                            <th>订单状态</th>
                            <th>配送类型</th>
                            <th>终端配送商</th>
                            <th>操作</th>
                        </thead>
                        <tbody>
                        	
                        </tbody>
                      </table>
                    </div><!-- /.box-body -->
                    
                   
                </div>
             </div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->


</body>
<#include "/footer.ftl" />
<script src="${uiBase}/vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}/vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script src="${uiBase}/js/pages/orderReturn/orderReturn_list.js?v=${resourceVersion}" ></script>

</html>
