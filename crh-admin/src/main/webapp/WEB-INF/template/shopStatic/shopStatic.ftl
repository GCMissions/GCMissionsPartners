<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}vendor/zTree/css/zTreeStyle.css"> 
<style>
#treeDemo{
	margin-left:66px;	
}
.area{
	display:none;
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
            <li> 商城静态化</li>             
          </ol>
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary mainForm">
                    <div class="box-header with-border">
                       <div class="row">
                            <div class="col-md-12">
               					<div class="col-sm-3">
                                    <label class="control-label">生成类型：</label>
                                    <select class="form-control" id="staticType" value="">										 
							             	<option value="0">全部</option>
							             	<option value="1">首页</option>
							             	<option value="2">商品详情</option>      
								 	 </select>
                                </div>
                            </div>
                        </div>
                         <div class="row area">
                            <div class="col-md-12">
               					<div class="col-sm-3">
                                    <label class="control-label">选择区域：</label>
								 	 <ul id="treeDemo" class="ztree"></ul>
                                </div>
                            </div>
                        </div>
                        <div class="row ">
                        	<div class="col-md-12">
                            <div class=" col-sm-4 pull-left">
                                <div class="pull-left">
                                  <button class="btn  btn-primary" id="sure">确定</button>
                                  <button class="btn  btn-primary" id="cancel">取消</button>
                                </div>
                            </div>
                            </div>
                        </div>                   
                    </div>                   
                               
                </div>
             </div>
        </div>
        <div class="clearfix"></div>     
      </div>
   </div>
</body>
<#include "/footer.ftl" />

<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${uiBase}js/pages/shopStatic/shopStatic.js?v=${resourceVersion}"></script>

</html>
