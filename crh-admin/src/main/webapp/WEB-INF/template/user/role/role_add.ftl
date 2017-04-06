<#include "/header.ftl" />
<style>
	label.role_checkbox {
   		font-weight: 500;
   		min-width: 24%;
	}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper" style="overflow:visible">
     <div class="content-wrapper page-content-wrapper">
		<section class="content-header">
	         <ol class="breadcrumb">
	           <li><i class="fa fa-dashboard"></i> Role Managemt</li>
            	<li class="active"> Add Roles</li>
	         </ol>
      	</section>
		<!-- Main content -->
		<div class="row pad">
			<div class="col-md-12">
				<div class="box box-primary">
					<!-- <div class="box-header with-border"></div> -->
					<div class="box-body">
						<form id="addEditForm" method="post" class="form-horizontal">
							<div class="box-body form-horizontal addEditTpl">
								<div class="form-group row">
								  	<label class="col-sm-4 control-label">
								  		<span class="requiredField">*</span>Role Name
								  	</label>
								  	<div class="col-sm-5">
								    	<input type="text" class="form-control" name="role" placeholder="Enter a role name"
							        		data-rule-required="true" data-msg-required="Please Enter The Role Name" data-rule-rolename="true">
								  	</div>
								</div>
								<div class="form-group row">
									<label class="col-sm-4 control-label">Role Description</label>
									<div class="col-sm-8">
										<textarea id="editor" name="description" class="form-control"></textarea>
									</div>
								</div>
								
								<#list functions as functionList>
									<div class="form-group row">
										<label class="col-sm-4 control-label">${functionList.nodeName}</label>
										<label class="col-sm-4">
											<input type="checkbox" name="functionIds" value="${functionList.id}" id=checkAll_${functionList.id}>All
										</label>
										<div class="col-sm-6 child">
											<#list functionList.childrenList as childList>	
												<label class="role_checkbox">
													<input type="checkbox" class="flat-red" value="${childList.id}" name="functionIds" id=sub_${functionList.id}> 
													${childList.nodeName}
												</label>	
											</#list>
										</div>
									</div>
								</#list>
							</div>
							
							<div class="col-sm-12 text-center">
	        		            <button class="btn btn-primary submitMainForm" type="button">Save</button>
	        		            <button type="button" class="btn btn-default backPage">Cancel</button>
    		               </div>	
						</form>
					
					</div>
				</div>
  			</div>
  		</div>
       <div class="clearfix"></div>
  	</div>
</div>
</body>
<#include "/footer.ftl" />


<script type="text/javascript" src="${uiBase}/js/pages/role/roleHandleManagement.js?v=${resourceVersion}"></script>
</html>