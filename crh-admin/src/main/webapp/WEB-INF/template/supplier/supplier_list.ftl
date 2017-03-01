<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/supplier.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
       <section class="content-header">
	 	   <ol class="breadcrumb">
			   <li><i class="fa fa-dashboard"></i> 服务商管理</li>
			   <li>服务商管理</li>
		   </ol> 
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border supplier_list">
                       <div class="row">
                            <div class="col-md-12">
               					<div class="col-sm-4">
                                    <label class="control-label" for="type-select">服务商编号：</label>
                                    <input type="text" class="form-control" id="orgCode">
                                </div>
                                <div class="col-sm-4">
                                    <label class="control-label" for="type-select">服务商名称：</label>
                                    <input type="text" class="form-control" id="orgName">
                                </div> 
                                <div class="col-sm-4">
                                    <label class="control-label" for="type-select">工商执照注册号：</label>
                                    <input type="text" class="form-control" id="registrationLicense">
                                </div>                                                                                                                         
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12"> 
                            	 <div class="col-sm-4">
                                    <label class="control-label" for="type-select">企业法人姓名：</label>
                                    <input type="text" class="form-control" id="businesser">
                                </div>                           	
                                <div class="col-sm-4">
                                    <label class="control-label" for="type-select">联系人姓名：</label>
                                    <input type="text" class="form-control" id="contact">                              
                                </div>   
                                <div class="col-sm-4">
                                    <label class="control-label" for="type-select">联系人手机号：</label>
                                    <input type="text" class="form-control" id="phone">                              
                                </div>                                 					                               
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                            	<div class="col-md-12">
                                  	<button class="btn  btn-primary" id="refreshRecord"><i class="fa fa-search"></i>开始搜索</button>
   			                      	<button type="button" class="btn btn-default reloadPage"><i class="fa  fa-refresh"></i> 刷新</button>
                                  	<a class="btn btn-default" href="../supplier/addPage"><i class="fa fa-plus"></i> 添加</a>                             
                                </div>
                            </div>
                        </div>                   
                    </div>                   
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                            <th>服务商编号</th>                        
                            <th>服务商名称</th>
                            <th>企业法人姓名</th>
                            <th>工商执照注册号</th>
                            <th>联系人姓名</th>
                            <th>添加时间</th>
                            <th>联系人手机号 </th>
                            <th>客服电话 </th>
                            <th>简介 </th>
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
<script src="${uiBase}/js/pages/supplier/supplier_list.js?v=${resourceVersion}"></script>
</html>
