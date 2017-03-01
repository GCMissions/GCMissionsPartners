<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
       <section class="content-header">
          <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i> 商城管理</li>
            <li> APP消息管理</li>             
          </ol>
        </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                       <div class="row">
                            <div class="col-md-12">
               					<div class="col-sm-3">
                                    <label class="control-label" for="type-select">APP消息标题：</label>
                                    <input type="text" id="appTitle" class="form-control" >
                                </div>
                            </div>
                        </div>
                        <div class="row ">
                        	<div class="col-md-12">
                            <div class=" col-sm-6 pull-left">
                                <div class="pull-left">
                                  <button class="btn  btn-primary" id="search"><i class="fa fa-search"></i>开始搜索</button>
                                  <a class="btn btn-default" href="addPage"><i class="fa fa-pencil"></i> 添加</a>
                                  <button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>
                                </div>
                            </div>
                            </div>
                        </div>                   
                    </div>                   
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<th>序号</th>
                            <th>消息标题</th>
                            <th>创建时间</th>
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

<script type="text/javascript" src="${uiBase}js/pages/appMessage/appMessage.js?v=${resourceVersion}"></script>
</html>
