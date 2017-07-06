<#assign headComponents = ["bootTable","bootDialog"] >
<#include "/header.ftl" />
<style>
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
            <li><img src="${uiBase}img/generral1.png"> General</li>
            <li>Roles Managemt</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
	                <div class="form-horizontal search-group" id="search-area" >
	                   	<div class="box-body">
	                   		 <div class="form-group">
	                   		 	<label class="control-label col-sm-2">Role Name:</label>
					           	<input id="text-input" name="role" class="form-control col-sm-5" type="text">
					           	<label class="col-sm-2 control-label">
	                   		 		<button type="button" id="searchBtn" class="btn btn-primary" style="margin-left: 2%;"><i class="fa fa-search"></i> Search</button>
	                   		 	</label>
	                   		 </div>
	                   		 <div class="form-group">   
					            <label class="col-sm-2 control-label">
					                <button type="button" class="btn btn-default reloadPage"><i class="fa  fa-refresh"></i> Refresh</button> 
					            </label>   
					            <label class="col-sm-1 control-label">
					                <a class="btn btn-default" href="${urlPrefix}role/addPage"><i class="fa fa-plus"></i> Add</a>                                  
					            </label>
				            </div>
	                   	</div>
	                </div>
                    
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<!-- <th field="roleId" width=70><div class="datagrid-header-check"><input type="checkbox"></div></th> -->
                        	<th>No.</th>
                            <th>Role Name</th>
                            <!-- <th>status</th> -->
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

<script type="text/javascript" src="${uiBase}js/pages/role/roleManagement.js?v=${resourceVersion}"></script>
</html>