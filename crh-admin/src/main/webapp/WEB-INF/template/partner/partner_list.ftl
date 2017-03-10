<#assign headComponents = ["bootTable","bootDialog"] >
<#include "/header.ftl" />
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
            <li><i class="fa fa-dashboard"></i> Partners</li>
          	<li>Partners Management</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group" id="search-area" >
                    	<div class="box-body">
                    		<div class="col-sm-12">
                    			<a class="btn btn-default" href="${urlPrefix}partner/view/0/0"><i class="fa fa-plus"></i> Add</a>     
                    		</div>
                    	</div>
                    </div>
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                      <thead>
                        	<th>No.</th>
                            <th>Partner</th>
                            <th>Mission</th>
                        	<th>Country</th>
                            <th>Region</th>
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

<script type="text/javascript" src="${uiBase}js/pages/partner/partner_list.js?v=${resourceVersion}"></script>