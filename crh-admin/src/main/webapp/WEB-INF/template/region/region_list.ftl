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
            <li><i class="fa fa-dashboard"></i> Region</li>
          	<li>Region Management</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group" id="search-area" >
                    	<div class="box-body">
                    		<div class="col-sm-12">
                    		 	<label class="col-sm-2 control-label">Region Name:</label>
                    		 	<input id="regionName" class="form-control col-sm-3" type="text"/>
                    		 	<label class="col-sm-2 control-label">   
					                <button type="button" id="searchBtn" class="btn btn-primary"><i class="fa fa-search"></i> Search</button> 
					            </label>
                    		 </div>
                    		<div class="col-sm-12" style="margin-top: 5%;">
                    			<a class="btn btn-default" href="${urlPrefix}region/view/0"><i class="fa fa-plus"></i> Add</a>     
                    		</div>
                    	</div>
                    </div>
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                      <thead>
                        	<th>No.</th>
                            <th>Region Name</th>
                            <th>Create Time</th>
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

<script type="text/javascript" src="${uiBase}js/pages/region/region_list.js?v=${resourceVersion}"></script>