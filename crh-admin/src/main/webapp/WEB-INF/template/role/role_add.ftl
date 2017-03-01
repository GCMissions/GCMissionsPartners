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
	           <li><i class="fa fa-dashboard"></i> 角色管理</li>
            	<li class="active"> 添加角色</li>
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
								  		<span class="requiredField">*</span>角色名称
								  	</label>
								  	<div class="col-sm-5">
								    	<input type="text" class="form-control" name="role" placeholder="请输入角色名称"
							        		data-rule-required="true" data-msg-required="请输入名称" data-rule-rolename="true">
								  	</div>
								</div>
								<div class="form-group row">
									<label class="col-sm-4 control-label">角色描述</label>
									<div class="col-sm-8">
										<textarea id="editor" name="description" class="form-control"></textarea>
									</div>
								</div>
								
								<#list functions as functionList>
									<div class="form-group row">
										<label class="col-sm-4 control-label">${functionList.nodeName}</label>
										<label class="col-sm-4">
											<input type="checkbox" name="functionIds" value="${functionList.id}" id=checkAll_${functionList.id}>全选
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
	        		            <button class="btn btn-primary submitMainForm" type="button">保存</button>
	        		            <button type="button" class="btn btn-default backPage">取消</button>
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