<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
</head>
<style>
.trackRecord-p{
 width: 210px;
 white-space:nowrap;
 overflow:hidden;
 text-overflow: ellipsis;
 display:block;
}
.trackRecord-s{
 width: 100px;
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
            <li><i class="fa fa-dashboard"></i> 订单管理</li>
            <li> 订单管理</li>   
          </ol>
         </section>
 
        <!-- Main content -->
        <div class="row pad">
        <input style='display: none' id='isOrg' value='${isOrg}'/>
             <div class="col-md-12">
                <div class="box box-primary ">
                	<div class="box-header with-border">
                	<form id="orderForm" method="post" action="${base}/web/order/export">
                    <div class="form-horizontal  search-group " id="search-area" >
                       <div class="row">
                       		<div class="col-md-12">
                       			<input type="hidden" name="orderIds" id="orderIds">
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
                                	<span id="numVali" style="color:#ffb042;padding-left:36px;font-weight:700;display:none;">请输入有效的数字</span>
                                </div>
                                <div class="col-sm-2">
                                	<input  id="productName" name="productName" class="form-control col-sm-2" type="text" placeholder="商品名称">
                                </div>
                            </div>
                            </div>
                     	 <div class="row">
                            <div class="col-md-12">
                            	<div class="col-sm-3"> 
                                     <select class="selectpicker form-control productBrand " id="typeId" name="typeId" title="商品类型">
                                     <option value="">不限</option>
										<#list listTypes as item>
                                        <option value="${item.getKey()}">${item.getValue()}</option>
                                        </#list>
                                     </select>
                                 </div>
                                 <div class="col-sm-3">
                                 	<select class="selectpicker form-control productBrand " id="orgId" name="orgId" title="服务商">
                                 	<option value="">不限</option> 
                                        <#list listOrgs as item>
                                        <option value="${item.orgId}">${item.orgName}</option>
                                        </#list>
                                     </select>
                                 </div>
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
								 <div class="col-sm-3"> 
                                     <select class="selectpicker form-control productBrand " name="vip" id="vip" title="是否开通vip">
                                     <option value="">不限</option>
                                     <option value="1">是</option>
                                     <option value="0">否</option>
                                     </select>
                                 </div>                                 
                                 
                     	 	</div>
                        </div>
                        <div class="row">
                        	<div class="col-md-12">
                        		<button type="button" class="btn  btn-primary" id="search"><i class="fa fa-search"></i>查询</button> 
                                <button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button> 
                                <button type="button" class="btn btn-default" id="exportOrder" data-excelType="1">
                                	<i class="excel-icon"></i>
                                	<span style="margin-left: 15px;">导出订单</span>
                                </button> 
                                <button data-exceltype="1" id="makeupOrderDown" class="btn btn-default" type="button">
	                                	<i class="fa fa-download"></i> 线下订单补录模板
	                            </button>
	                            <button data-exceltype="1" id="importMakeupOrder" class="btn btn-default" type="button"><i class="fa fa-plus"></i> 导入线下订单</button>
	                            <div id="exportDiv" style="display: none">
									<input type="file" name="postExcel" id="postExcel" style="display:none;" accept=".xls,.xlsx">
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
<script src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}js/pages/orderManager/order_list.js?v=${resourceVersion}"></script> 

</html>
