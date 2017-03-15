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
            <li><img src="${uiBase}img/generral1.png"> General</li>
          	<li>Users Managemt</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group" id="search-area" >
                    	<div class="box-body">
                    		 <div class="form-group">
                    		 	<label class="col-sm-2 control-label">Username：</label>
                    		 	<input id="text-input" name="loginId" class="form-control col-sm-3" type="text">
                    		 	<label class="col-sm-1 control-label" style="margin-left: 2%;">Email：</label>
					           	<input id="text-input" name="email" class="form-control col-sm-3" type="text">
					           	<label class="col-sm-1 control-label">Role：</label>
					           	<div class="col-sm-3" style="margin-left: 3%;">
                    		 		<select class="selectpicker form-control productBrand " id="roleId" title="roles" > 
                                    	<option value="">All</option>
                                        <#list listRoles as item>
                                        <option value="${item.roleId}">${item.role}</option>
                                        </#list>
									</select>
                    		 	</div>
                    		 </div>
                    		 <div class="form-group">   
					            <label class="col-sm-2 control-label">   
					                <button type="button" id="searchBtn" class="btn btn-primary"><i class="fa fa-search"></i> Search</button> 
					            </label>
					            <label class="col-sm-1 control-label">
					                <button type="button" class="btn btn-default addItem"><i class="fa fa-plus"></i> Add</button>                                  
					            </label>                            
					        </div>
                    		 
                    	</div>
                    </div>
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<!-- <th field="brand_id" width=70><div class="datagrid-header-check"><input type="checkbox"></div></th> -->
                        	<th>No.</th>
                            <th>Username</th>
                        	<th>Email</th>
                        	<th>Role</th>
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

<script type="text/javascript" src="${uiBase}js/pages/user/userManagement.js?v=${resourceVersion}"></script>
<script id="editTpl" type="text/html">
<form id="addEditForm" method="post" class="form-horizontal">
<div class="box-body form-horizontal addEditTpl">
	<div class="form-group row">
	  <label class="col-sm-4 control-label" ><span class="requiredField">*</span>Username</label>
	  <div class="col-sm-8">
		<input type="hidden" name="id" value={{dto.id}}>
	    <input type="text" class="form-control" name="loginId" placeholder="Please enter your Username"
        	data-rule-required="true" data-msg-required="Please enter your Username" value="{{dto.loginId}}" disabled>
	  </div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label"><span class="requiredField">*</span>Email</label>
	  	<div class="col-sm-8">
	  		<input type="text" class="form-control" name="email" placeholder="Please enter your Email"
	  			required data-msg-required = "Please enter your Email" 
	  			data-rule-isEmail="true" value="{{dto.email}}" >
	  	</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label"><span class="requiredField">*</span>Password</label>
	  	<div class="col-sm-8">
	  		<input type="password" class="form-control" name="password" id="password" data-rule-isPwd="true">
	  	</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label"><span class="requiredField">*</span>Cofirm Password</label>
	  	<div class="col-sm-8">
	  		<input type="password" class="form-control" data-rule-equalTo="#password" data-msg-equalTo="Passwords don't match" data-rule-isPwd="true">
	  	</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label">role</label>
		<div class="col-sm-8">
			{{each list}}
				{{if $value.status == '1'}}
					<label class="role_checkbox">
						<input type="checkbox" class="flat-red" value="{{$value.roleId}}"
							name="roleIds" required data-msg-required = "Select at least one role"
							{{each dto.roleIds as rid}} {{if rid == $value.roleId}} checked {{/if}} {{/each}}
							> {{$value.role}}</label>
				{{/if}}
			{{/each}}
				<label id="roleIds-error" class="fieldError" for="roleIds" style="float: right;"></label>
		</div>
    </div>	
	<div class="form-group row">
	<label class="col-sm-4 control-label" for="type-select"></label>
		<div class="col-sm-4">
			<label class="role_checkbox"><input name="unlock" type="hidden" value="1"/><input type="checkbox" class="flat-red" {{if dto.status == '禁用' }}checked{{else}}disabled{{/if}} name="lockUser" />Locked</label>
		</div>
	</div>
</div>
</form>
</script>
<script id="addEditTpl" type="text/html">
<form id="addEditForm" method="post" class="form-horizontal">
<div class="box-body form-horizontal addEditTpl">
	<div class="form-group row">
	  <label class="col-sm-4 control-label" ><span class="requiredField">*</span>Username</label>
	  <div class="col-sm-8">
	    <input type="text" class="form-control" name="loginId" placeholder="Please enter your Username"
        	data-rule-required="true" data-msg-required="Please enter your Username" data-rule-userName="true">
	  </div>
	</div>
	<div class="form-group row">
	  <label class="col-sm-4 control-label"><span class="requiredField">*</span>Email</label>
	  <div class="col-sm-8">
	    <input type="text" class="form-control" name="email" placeholder="Please enter your Email"
	  			required data-msg-required = "Please enter your Email" 
	  			 data-rule-isEmail="true">
	  </div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label">role</label>
		<div class="col-sm-8">
			{{each data}}	
				{{if $value.status == '1'}}
					<label class="role_checkbox"><input type="checkbox" class="flat-red" value="{{$value.roleId}}"
						name="roleIds" required data-msg-required = "Select at least one role"> 
						{{$value.role}}</label>
				{{/if}}
			{{/each}}
				<label id="roleIds-error" class="fieldError" for="roleIds" style="float: right;"></label>
		</div>
    </div>	
	<div class="form-group row">
		<label class="col-sm-4 control-label">Status</label>
		<div class="col-sm-5">
			  <select class="form-control" name="status">
				<option value="启用"> Enable </option>
				<option value="禁用"> Disable </option>
			  </select>
		</div>
    </div>
	<div class="form-group row">
		<label class="col-sm-4 control-label"></label>
		<div class="col-sm-8" style='color:red;'>The default password is 123456
		</div>
    </div>
</div>
</form>
</script>
 <script type="text/javascript" src="${uiBase}vendor/jquery-md5/jquery.md5.js"></script>
</html>