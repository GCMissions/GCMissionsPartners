<#include "/header.ftl" />
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper" style="overflow:visible">
     <div class="content-wrapper page-content-wrapper">
		<section class="content-header">
	         <ol class="breadcrumb">
	           <li><i class="fa fa-dashboard"></i> Role Managemt</li>
            	<li class="active">Check The Role</li>
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
								    	<input type="text" class="form-control" name="role" value="${roleInfo.role}" disabled>
								  	</div>
								</div>
								<#if roleInfo.description>
								<div class="form-group row">
									<label class="col-sm-4 control-label">Role Description</label>
									<div class="col-sm-8">
										<textarea id="editor1" name="description" class="form-control" disabled> ${roleInfo.description} </textarea>
									</div>
								</div>
								</#if>
								<#list functions as functionList>
									<#list roleInfo.functionIds as fid>
										<#if fid == functionList.id> 
											<div class="form-group row" ${nodeIds?seq_contains(functionList.id)?string("", "style='display:none'")}>
												<label class="col-sm-4 control-label">${functionList.nodeName}</label>
												<label class="col-sm-4"></label>
												<div class="col-sm-6">
													<#list functionList.childrenList as childList>
														<#list roleInfo.functionIds as fids>
															<#if fids == childList.id>
																<label class="role_checkbox"><input type="checkbox" class="flat-red"
																	 value="${childList.id}" name="functionIds" checked disabled>${childList.nodeName}</label>
															</#if>
														</#list>
													</#list>
												</div>
											</div>
										</#if>
									</#list>
								</#list>
							</div>
							<div class="col-sm-12 text-center">
	        		            <button type="button" class="btn btn-default backPage">Return </i></button>
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
<style>
	label.role_checkbox {
   		font-weight: 500;
   		min-width: 24%;
	}
</style>
</html>