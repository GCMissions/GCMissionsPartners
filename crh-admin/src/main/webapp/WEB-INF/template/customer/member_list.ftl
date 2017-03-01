<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">
<style>
.datagrid-header-input{
    display: inline-block;
    width: 160px;
}
.shipmentEdit{
	width:120px;
}
.balance{
	width: 160px;
}
.hideProductId{
	display:none;
}
.point{
	width: 115px;
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
            <li><i class="fa  fa-users"></i> 消费者管理</li>
            <li> 消费者管理</li>
             
          </ol>
          
         </section>
       
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                
                	<div class="form-horizontal search-group" >
                		<div class="box-body">
                    	<form role="form" class="form-horizontal" method=get id="searchForm">
                    		<div class="form-group"> 
                    			                 
                    			<label class="col-sm-2 control-label">注册手机号：</label>
								<input  class="form-control col-sm-2" id="phone" type="text">
								
								<label class="control-label col-sm-2" for="type-select">注册日期：</label>
								<div class="col-sm-6 no-padding datetimeInputGroup" >
									<input type="hidden" data-ignore="true"  name="stDate" id="stDate" value="" />
									<div class="input-group date datetimeInput  no-padding pull-left">
										<input size="16" type="text"  name="startTime" id="csDate" class="form-control startTimeSelection" placeholder="开始时间" readonly>
										<span class="add-on" style="display:none"><i class="icon-remove"></i></span>
										<span class="add-on"><i class="icon-calendar"></i></span>
									</div>
									<span class="pull-left textTo">至</span>
									<input type="hidden" data-ignore="true"  name="endTime" id="edDate" value="" />
									<div class="input-group date datetimeInput no-padding pull-left" style="margin-bottom: 0px;">
										<input size="16" type="text" name="endDate"  id="ceDate" class="form-control endTimeSelection" placeholder="结束时间" readonly>
										<span class="add-on" style="display:none"><i class="icon-remove"></i></span>
										<span class="add-on"><i class="icon-calendar"></i></span>
									</div>
								</div>
								
                       		</div>
                       		
                       		<div class="form-group">
                       			<label class="col-sm-2 control-label">消费者姓名：</label>
								<input  class="form-control col-sm-2" id="memberName" type="text">
								
								<label class="col-sm-2 control-label">积分：</label>
                            	<div class="col-sm-6 no-padding " >
									<input  class="form-control point shortInput" size="10" id="pointStart" name="pointStart" type="text">
									<span class="time">-</span>
									<input  class="form-control point shortInput" size="10" id="pointEnd" name="pointEnd" type="text">
								</div>
								
                       		</div>
                       		
                       		<div class="form-group">
                       			
                            	<label class="control-label col-sm-2">账户余额：</label>
                       			<div class="col-sm-6 no-padding " >
									<input  class="form-control balance shortInput" size="16" id="balanceStart" name="balanceStart" type="text">
									<span class="time">-</span>
									<input  class="form-control balance shortInput" size="16" id="balanceEnd" name="balanceEnd" type="text">
								</div>
								
                       		</div>
                       		
                       </form>
                       
                       		<div class="form-group ">	
								<label class="col-sm-2 control-label no-padding" style="text-align: right;"> 
									<button class="btn  btn-primary" id="search"><i class="fa fa-search"></i>开始搜索</button>
                            	</label>
                            	<label class="col-sm-1 control-label pull-left">   
                            		<button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>  
                            	</label> 
                            </div>
                            
                    	</div><!-- /.box-body -->
                    </div>
                    
                    
                    <div class="box-body">
                      <table id="memberDataList" class="table table-bordered table-hover" >
                        <thead>
                        	<th>序号</th>
                            <th>注册手机号</th>
                            <th>消费者编号</th>
                            <th>消费者姓名</th>
                            <th>账户余额（元）</th>
                            <th>积分</th>
                            <th>注册日期</th>
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
<script src="${uiBase}vendor/input-mask/jquery.inputmask.js" ></script>
<script src="${uiBase}/js/pages/customer/member_list.js?v=${resourceVersion}" ></script>
</html>
