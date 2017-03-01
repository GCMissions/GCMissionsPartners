<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
</head>
<style>
.trackRecord-p{
 width: 200px;
 white-space:nowrap;
 overflow:hidden;
 text-overflow: ellipsis;
 display:block;
}
.trackRecord-s{
 width: 90px;
 white-space:nowrap;
 overflow:hidden;
 text-overflow: ellipsis;
 display:block;
}
.excel-icon{
background: url(${uiBase}img/excel-icon.png) 0 0 transparent no-repeat;
position: absolute;
height: 13px;
width: 13px;
line-height: 13px;
top: 11px;
}
</style>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
          <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i> 服务商平台验证</li>
            <li> 订单查询</li>   
          </ol>
         </section>
 
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary ">
                	<div class="box-header with-border">
                	<form id="orderForm" method="post" action="${base}/web/validate/export">
                    <div class="form-horizontal  search-group " id="search-area" >
                       <div class="row">
                       		<div class="col-md-12">
                         		<div class="col-sm-3">
                                	<input  id="orderId" name="orderId" class="form-control col-sm-2" type="text" placeholder="订单号">
                                </div>
                                <div class="col-sm-8">
                         			<div class="no-padding datetimeInputGroup" >
										<input type="hidden" data-ignore="true"  id="stDate" value="" />
										<div class="input-group date datetimeInput  no-padding pull-left">
											<input size="16" type="text"  name="startTime" id="csDate" class="form-control startTimeSelection" placeholder="开始时间" readonly>
											<span class="add-on" ><i class="icon-remove"></i></span>
											<span class="add-on"><i class="icon-calendar"></i></span>
										</div>
										<span class="pull-left textTo">至</span>
										<input type="hidden" data-ignore="true" id="edDate" value="" />
										<div class="input-group date datetimeInput no-padding pull-left" style="margin-bottom: 0px;">
											<input size="16" type="text" name="endDate"  id="ceDate" class="form-control endTimeSelection" placeholder="结束时间" readonly>
											<span class="add-on" ><i class="icon-remove"></i></span>
											<span class="add-on"><i class="icon-calendar"></i></span>
										</div>
									</div>
								</div>	
                             </div>                            
                          </div>
                          <div class="row">
                            <div class="col-md-12">	
                                <div class="col-sm-3">
                                	<input  id="phone" name="phone" class="form-control col-sm-2" type="text" maxlength=11 placeholder="手机号">
                                	<span id="numVali" style="color:#ffb042;padding-left:31px;font-weight:700;display:none;">请输入有效的数字</span>
                                </div>
                                <div class="col-sm-2">
                                	<input  id="productName" name="productName" class="form-control col-sm-2" type="text" placeholder="商品名称">
                                </div>
                            </div>
                            </div>
                     	 <div class="row">
                            <div class="col-md-12">
                            	<!-- <div class="col-sm-3"> 
                                     <select class="selectpicker form-control productBrand " id="typeId" name="typeId" title="商品类型">
                                     <option value="">不限</option>
										<#list listTypes as item>
                                        <option value="${item.getKey()}">${item.getValue()}</option>
                                        </#list>
                                     </select>
                                 </div> -->
                                 <div class="col-sm-3"> 
                                     <select class="selectpicker form-control productBrand " name="status" id="status" title="支付状态">
                                     <option value="">不限</option>
										<#list status as item>
											<#if item.getKey() != "1">
                                        		<option value="${item.getKey()}">${item.getValue()}</option>
                                        	</#if>
                                        </#list>
                                     </select>
                                 </div>
                                 <div>
                                 	<input id="orderIds" name="orderIds" style='display: none'>
                                 </div>
                                 
                     	 	</div>
                        </div>
                        <div class="row">
                        	<div class="col-md-12">
                        		<div class="col-sm-1">	
                                	<button type="button" class="btn  btn-primary" id="search"><i class="fa fa-search"></i>查询</button> 
                                </div>
                                <div class="col-sm-2">	
                                	<button type="button" class="btn btn-default" id="exportOrder" data-excelType="1">
                                	<i class="excel-icon"></i>
                                	<span style="margin-left: 15px;">导出订单</span></button> 
                                </div>
                            </div>
                        </div>
                        </div>
                      </form>
                     </div>
                     <div class="box-body">
                      	<table id="dataList" class="table table-bordered table-hover dataList" >
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
<script type="text/javascript" src="${uiBase}js/pages/orderManager/validate_list.js?v=${resourceVersion}"></script> 

</html>
