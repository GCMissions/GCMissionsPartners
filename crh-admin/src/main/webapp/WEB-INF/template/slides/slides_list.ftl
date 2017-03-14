<#assign headComponents = ["bootTable","bootDialog"] >
<#include "/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/pages/slides/slides_list.css?v=${resourceVersion}">
<style>
	label.role_checkbox {
   		font-weight: 500;
   	    min-width: 38%;	
   	    margin-top: 6px;
	}
	.form-control {
    	width: 68%;
	}
	.fa-edit, .fa-trash, .fa-eye { 
		cursor: pointer;	
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
            <li><img src="${uiBase}img/partner-slides.png"> Partner Slides</li>
          	<li>Slides Management</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group" id="search-area" >
                    	<div class="box-body">
                    		<div class="col-sm-12">
                    			<a class="btn btn-default" href="${urlPrefix}slides/view/0"><i class="fa fa-plus"></i> Add</a>     
                    		</div>
                    	</div>
                    </div>
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                      <thead>
                        	<!-- <th field="brand_id" width=70><div class="datagrid-header-check"><input type="checkbox"></div></th> -->
                        	<th>Order</th>
                            <th>Slide</th>
                            <th>Description</th>
                        	<th>Displayed/Hidden</th>
                            <th>Action</th>
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

<script type="text/javascript" src="${uiBase}js/pages/slides/slides_list.js?v=${resourceVersion}"></script>