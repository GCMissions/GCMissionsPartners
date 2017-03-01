<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/delivery.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
       <section class="content-header">
	 	   <ol class="breadcrumb">
			   <li><i class="fa fa-dashboard"></i> 商家管理</li>
			   <li>终端配送商管理</li>
		   </ol> 
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border delivery_list">
                       <div class="row">
                            <div class="col-md-12">
               					<div class="col-sm-4">
                                    <label class="control-label" for="type-select">终端配送商编号：</label>
                                    <input type="text" class="form-control" id="orgCode">
                                </div> 
                                <div class="col-sm-4">
                                    <label class="control-label" for="type-select">终端配送商名称：</label>
                                    <input type="text" class="form-control" id="orgName">
                                </div>                               
                                <div class="col-sm-4">
                                    <label class="control-label" for="type-select">联系人：</label>
                                    <input type="text" class="form-control" id="contact">
                                </div>                                                              
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">                            	                         	
                                <div class="col-sm-4">
                                    <label class="control-label" for="type-select">联系电话：</label> 
                                    <input type="text" class="form-control" id="phone">                                 
                                </div>
                            	<div class="col-sm-8">
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
                        </div>
                        <div class="row">
                            <div class="col-md-12">                           	   
               					<div class="col-sm-6">
                                    <label class="control-label" for="type-select">所在地区：</label>
                                    <select class="form-control form_select" id="provinceId"> 
                                    	<option value="">请选择省份</option>
                                    </select>
                                    <select class="form-control form_select" id="cityId"> 
                                    	<option value="">请选择城市</option>
                                    </select>
                                </div>                                                              
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                            	<div class="col-md-12">
                                  <button class="btn  btn-primary" id="refreshRecord"><i class="fa fa-search"></i>开始搜索</button>
   			                      <button type="button" class="btn btn-default reloadPage"><i class="fa  fa-refresh"></i> 刷新</button>
                                  <a class="btn btn-default" href="../delivery/addPage"><i class="fa fa-plus"></i> 添加</a>                             
                                </div>
                            </div>
                        </div>                   
                    </div>                   
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                            <th>序号</th>
                            <th>终端配送商编号</th>
                            <th>终端配送商名称</th>
                            <th>联系人</th>
                            <th>联系电话</th>
                            <th>地址</th>
                            <th>创建日期 </th>
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
<script src="${uiBase}/js/pages/delivery/delivery_list.js?v=${resourceVersion}"></script>
</html>
