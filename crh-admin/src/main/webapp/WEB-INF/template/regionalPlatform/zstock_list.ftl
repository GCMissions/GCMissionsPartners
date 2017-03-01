<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">
<style>
.datagrid-header-input{
    display: inline-block;
    width: 160px;
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
            <li><i class="fa fa-dashboard"></i>库存管理</li>
            <li>终端配送商库存管理</li>
          </ol>
        </section>
        
       
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                	<div class="form-horizontal search-group" id="search-area" >
                		<div class="box-body">
                			<div class="form-group ">
                				<label class="control-label col-sm-2" for="type-select">配送商编号:</label>
                				<input  class="form-control col-sm-3" id="orgCode" type="text">
                				<label class="control-label col-sm-2" for="type-select">配送商名称:</label>
                				<input  class="form-control col-sm-3" id="orgName" type="text">
                				<label class="control-label col-sm-2" for="type-select">联系人:</label>
                				<input  class="form-control col-sm-3" id="contactName" type="text">
                			</div>
                			<div class="form-group ">
                				<label class="control-label col-sm-2" for="type-select">联系人电话:</label>
                				<input  class="form-control col-sm-3" id="phone" type="text">
                				<label class="control-label col-sm-2" for="type-select">关联日期：</label>
					           	<div class="col-sm-6 no-padding timegroup">
					                <input type="hidden" data-ignore="true"  name="stDate" id="stDate" value="" />
					                <div class="dateDiv" style="margin-bottom: 0px;">
					                    <input size="10" type="text"  name="startTime" id="csDate" class="form-control keyword beginDate" placeholder="请选择时间" readonly>
					                    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
					                    <span class="add-on"><i class="icon-calendar"></i></span>
					                </div>
					                <span class="time">至</span>
					                <input type="hidden" data-ignore="true"  name="endTime" id="edDate" value="" />
					                <div class="dateDiv" style="margin-bottom: 0px;">
					                    <input size="10" type="text" name="endDate"  id="ceDate" class="form-control keyword endDate" placeholder="请选择时间" readonly>
					                    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
					                    <span class="add-on"><i class="icon-calendar"></i></span>
					                </div>
					           </div>
                			</div>
                			<div class="form-group ">
                				<label class="col-sm-2 control-label no-padding" style="text-align: right;"> 
					                <button type="button" id="search" class="btn btn-primary pull-right"><i class="fa fa-search"></i> 开始搜索</button>                                
					            </label> 
					            <label class="col-sm-1 control-label pull-left">   
					                <button type="button"  class="btn btn-default reloadPage"><i class="fa  fa-refresh"></i> 刷新</button>                                
					            </label>    
					            <label class="col-sm-1 control-label pull-left">   
					                <button class="btn  btn-normal btn-danger" id="warnStock"><i class="fa fa-exclamation"></i> 库存预警(${waringCount})</button>                                
					            </label>   
					             <label class="col-sm-2 control-label pull-left">
									<button data-exceltype="1" id="excel" class="btn btn-default" type="button"><i class="fa  fa-refresh"></i> 导出</button>
								</label>
                			</div>
                		</div>
                	</div>
                    <input  type="hidden" id="orgIds" value="${orgIdStr}"/>
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<th>序号</th>
                            <th>配送商编号</th>
                            <th>配送商名称</th>
                            <th>联系人</th>
                            <th>联系电话</th>
                            <th>库存总量(件)</th>
                            <th>库存状态</th>
                            <th>关联日期</th>
                            <th>操作</th>
                        </thead>
                        <tbody>
                        	
                        </tbody>
                      </table>
                    </div><!-- /.box-body -->
                    
                    <form id="stockForm" method="get" action="${base}/web/terminalStock/export">
	                   <input name="isWarning" id="isWarning" type="hidden" value="T">
	                   <input name="orgType" id="orgType" type="hidden" value="Z">
                   </form>
                   
                </div>
             </div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->


</body>
<#include "/footer.ftl" />

<script type="text/javascript" src="${uiBase}/js/pages/regionalPlatform/zstock_list.js?v=${resourceVersion}"></script>
</html>
