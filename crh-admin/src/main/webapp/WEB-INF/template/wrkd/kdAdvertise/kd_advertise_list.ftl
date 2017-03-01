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
            <li><i class="fa fa-dashboard"></i> 广告位管理</li>
            <li> 广告管理</li>   
          </ol>
         </section>
         
         <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary ">
                	<div class="box-header with-border">
                    <div class="form-horizontal  search-group " id="search-area" >
                    <input type="hidden" name="advertiseIds" id="advertiseIds">
                          <div class="row">
                            <div class="col-md-12">	
                                <div class="col-sm-3">
                                	<input  id="advertiseName" name="advertiseName" class="form-control col-sm-2" type="text" placeholder="广告名称">
                                </div>
                                <div class="col-sm-3"> 
                                     <select class="selectpicker form-control productBrand " id="advertiseType" name="type" title="广告模式">
                                     <option value="">不限</option>
										<#list advertiseType as item>
                                        <option value="${item.getCode()}">${item.getText()}</option>
                                        </#list>
                                     </select>
                                 </div>
                            </div>
                           </div>
                        <div class="row">
                        	<div class="col-md-12">
                        		<div class="col-sm-1">	
                                	<button type="button" class="btn  btn-primary" id="search"><i class="fa fa-search"></i>查询</button> 
                                </div>
                                <label class="col-sm-1 control-label pull-left">  
                                   <button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>  
                                </label>
                                <div class="col-sm-2">	
                                </div>
                                <div class="col-sm-1">	
                                	<a type="button" class="btn  btn-primary" href="${base}/web/kdAdvertise/adListOne"><i class="fa fa-plus"></i>添加广告</a> 
                                </div>
                            </div>
                        </div>
                        </div>
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
<script type="text/javascript" src="${uiBase}js/pages/wrkd/kdAdvertise/kd_advertise_list.js?v=${resourceVersion}"></script> 

</html>