<#include "/header.ftl" />
<style type="text/css">
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
            	<li class="active">Edit Role</li>
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
								  		<input type="hidden" name="roleId" value=${roleInfo.roleId}> 
								    	<input type="text" class="form-control" name="role" placeholder="Enter a role name"
							        		data-rule-required="true" data-msg-required="Please Enter The Role Name" data-rule-rolename="true" value="${roleInfo.role}">
								  	</div>
								</div>
								<div class="form-group row">
									<label class="col-sm-4 control-label">Role Description</label>
									<div class="col-sm-8">
										<textarea id="editor1" name="description" class="form-control">${roleInfo.description}</textarea>
									</div>
								</div>
								<#list functions as functionList>
									<div class="form-group row">
										<label class="col-sm-4 control-label">${functionList.nodeName}</label>
										<label class="col-sm-4">
											<input type="checkbox" name="functionIds" value="${functionList.id}" id=checkAll_${functionList.id} class="parent">All
										</label>
										<div class="col-sm-6 child">
											<#list functionList.childrenList as childList>	
												<label class="role_checkbox">
													<input type="checkbox" class="flat-red" value="${childList.id}" name="functionIds" id=sub_${functionList.id}
														<#list roleInfo.functionIds as fid>
															<#if fid == childList.id> checked </#if>
														</#list>
													> ${childList.nodeName}
												</label>	
											</#list>
										</div>
									</div>
								</#list>
							</div>
							<div class="col-sm-12 text-center">
	        		            <button class="btn btn-primary submitMainForm" type="button">Save</button>
	        		            <button type="button" class="btn btn-default backPage">Return</button>
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

<script type="text/javascript">
$(function() {
	var parents = $("input.parent");
	$.each(parents, function() {
		var sub = $(this).parent().siblings(".child").find("input[type='checkbox']"),
			subLen = sub.length,
			checkedLen = sub.filter(":checked").length;
		
		if(checkedLen == subLen) {
			$(this).prop('checked',true);
		}
	});
	
})
</script>
<script type="text/javascript" src="${uiBase}/js/pages/role/roleHandleManagement.js?v=${resourceVersion}"></script>
</html>