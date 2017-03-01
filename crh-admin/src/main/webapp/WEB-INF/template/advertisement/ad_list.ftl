<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<style>
#adLocal.form-control{
width:66%;
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
            <li><i class="fa fa-dashboard"></i> 商城管理</li>
            <li>  广告管理</li>             
       </ol>
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                       <div class="row">
                            <div class="col-md-12">
                            	<div class="col-md-3 col-sm-3 pull-left">
               						 <label class="control-label" for="type-select">广告类型：</label>
               						 <select class="form-control" id="adType" value="">
               						 	 <#list adTypeList as list > 
							             	<option value="${list.code}">${list.desc}</option>      
							             </#list>
								 	 </select>
               					</div>
               					
               					<div class="col-md-3 col-sm-3 pull-left">
               						 <label class="control-label" for="type-select">广告位：</label>
               						 <select class="form-control" id="adLocal" value="">
										 <#list positionList as list > 
							             	<option value="${list.position}">${list.name}</option>      
							             </#list>
								 	 </select>
               					</div>
               					<div class="col-sm-3">
                                    <label class="control-label" for="type-select">广告标题：</label>
                                    <input type="text" id="adName" class="form-control" >
                                </div>
                                
                                 <!-- <div class="col-sm-6">
                                    <label class="control-label" for="type-select">使用日期：</label>
                                    <input type="hidden"  name="usDateInput" id="usDateInput" value="" />
									<div class="dateDiv" style="margin-bottom: 0px;">
									    <input size="10" type="text" name="usDate" id="usDate" class="form-control keyword beginDate" placeholder="请选择时间" readonly>
									    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
									    <span class="add-on"><i class="icon-calendar"></i></span>
									</div>
									<span class="time">至</span>
									<input type="hidden"  name="ueDateInput" id="ueDateInput" value="" />
									<div class="dateDiv" style="margin-bottom: 0px;">
									    <input size="10" type="text" name="ueDate" id="ueDate" class="form-control keyword endDate" placeholder="请选择时间" readonly>
									    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
									    <span class="add-on"><i class="icon-calendar"></i></span>
									</div>
                                </div> -->
                            </div>
                        </div>
                        <div class="row ">
                         <div class="col-md-12">
                            <div class=" col-sm-6 pull-left">
                                <div class="pull-left">
                                  <button class="btn  btn-primary" id="search"><i class="fa fa-search"></i>开始搜索</button>
                                  <button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>  
                                 <!--  <a class="btn btn-default" href="addPage"><i class="fa fa-pencil"></i> 添加</a> -->
                                </div>
                            </div>
                           </div> 
                        </div>                   
                    </div>                   
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<th>序号</th>
                            <th>广告位</th>
                            <th>排序</th>
                            <th>广告标题</th>                     
                        <!--     <th>起始日期  </th>
                            <th>结束日期 </th> -->
                            <th>广告链接</th>
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
</body>
<#include "/footer.ftl" />

 <script src="${uiBase}js/pages/advertisement/ad_list.js?v=${resourceVersion}"></script>
</html>
