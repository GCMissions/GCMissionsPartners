<#assign headComponents = ["bootTable", "innerPage"] > <#include
"/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/pages/advertise/advertise_list.css?v=${resourceVersion}">
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>APP启动页管理</li>
					<li>APP启动页管理</li>
				</ol>
			</section>

			<!-- Main content -->
			<div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border supplier_list">
                        <div class="row">
                            <div class="col-md-12">
                            	<div class="col-md-12">
   			                      	<button type="button" class="btn btn-default reloadPage"><i class="fa  fa-refresh"></i> 刷新</button>
                                  	<a class="btn btn-default" href="../appStartupHomepage/addPage"><i class="fa fa-plus"></i> 添加</a>                             
                                </div>
                            </div>
                        </div>                   
                    </div>                   
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                      </table>
                    </div>           
                </div>
             </div>
        	</div>
        <div class="clearfix"></div>
		</div>
		<!-- /.content-wrapper -->
	</div>
	<!-- ./wrapper -->
</body>
<#include "/footer.ftl" />

<script type="text/javascript" src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script>
<script type="text/javascript"  src="${uiBase}js/pages/app/appStartupHomepage/ash_list.js?v=${resourceVersion}"></script>
</html>

 