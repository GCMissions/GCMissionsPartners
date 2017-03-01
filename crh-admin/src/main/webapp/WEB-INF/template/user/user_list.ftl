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
            <li><i class="fa fa-dashboard"></i> 权限管理</li>
          	<li>用户管理</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group" id="search-area" >
                    	<div class="box-body">
                    		 <div class="form-group">
                    		 	<label class="col-sm-2 control-label">用户名：</label>
                    		 	<input id="text-input" name="loginId" class="form-control col-sm-3" type="text">
                    		 	<label class="col-sm-1 control-label" style="margin-left: 2%;">姓名：</label>
					           	<input id="text-input" name="userName" class="form-control col-sm-3" type="text">
					           	<label class="col-sm-2 control-label">手机号：</label>
                    		 	<input id="text-input" name="phone" class="form-control col-sm-3" type="text" maxlength="11">
                    		 </div>
                    		 <div class="form-group">
                    		 	<label class="col-sm-2 control-label">邮箱：</label>
                    		 	<input id="text-input" name="email" class="form-control col-sm-3" type="text" >
                    		 	<div class="col-sm-5" style="margin-left: 3%;">
                    		 		<select class="selectpicker form-control productBrand " id="roleId" title="角色" > 
                                    	<option value="">不限</option>
                                        <#list listRoles as item>
                                        <option value="${item.roleId}">${item.role}</option>
                                        </#list>
									</select>
                    		 	</div>
                    		 </div>
                    		 <div class="form-group">   
					            <label class="col-sm-2 control-label">   
					                <button type="button" id="searchBtn" class="btn btn-primary"><i class="fa fa-search"></i> 开始搜索</button> 
					            </label>
					            <label class="col-sm-1 control-label">
					                <button type="button" class="btn btn-default reloadPage"><i class="fa  fa-refresh"></i> 刷新</button> 
					            </label>   
					            <label class="col-sm-1 control-label">
					                <button type="button" class="btn btn-default addItem"><i class="fa fa-plus"></i> 添加</button>                                  
					            </label>                            
					        </div>
                    		 
                    	</div>
                    </div>
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                        	<!-- <th field="brand_id" width=70><div class="datagrid-header-check"><input type="checkbox"></div></th> -->
                        	<th>序号</th>
                            <th>用户名</th>
                        	<th>姓名</th>
                            <th>手机号</th>
                            <th>邮箱</th>
                            <th>状态</th>
                            <th>创建时间</th>
                            <th>操作</th>
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
	  <label class="col-sm-4 control-label" ><span class="requiredField">*</span>用户名</label>
	  <div class="col-sm-8">
	    <input type="text" class="form-control" name="loginId" placeholder="请输入用户名"
        	data-rule-required="true" data-msg-required="请输入用户名" value="{{dto.loginId}}" disabled>
	  </div>
	</div>
	<div class="form-group row">
	  <label class="col-sm-4 control-label"><span class="requiredField">*</span>姓名</label>
	  <div class="col-sm-8">
		<input type="hidden" name="id" value={{dto.id}}>
	    <input type="text" class="form-control" name="userName" placeholder="请输入姓名"
        	required data-msg-required="请输入姓名" data-rule-uname="true"  value="{{dto.userName}}" >
	  </div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label"><span class="requiredField">*</span>邮箱</label>
	  	<div class="col-sm-8">
	  		<input type="text" class="form-control" name="email" placeholder="请输入邮箱"
	  			required data-msg-required = "请输入邮箱" 
	  			data-rule-isEmail="true" value="{{dto.email}}" >
	  	</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label"><span class="requiredField">*</span>手机号</label>
	  	<div class="col-sm-8">
	  		<input type="text" class="form-control" name="phone" placeholder="请输入手机号"
	  			required data-msg-required = "请输入手机号" maxlength="11"
	  			data-rule-isMobile="true" value="{{dto.phone}}" >
	  	</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label"><span class="requiredField">*</span>密码</label>
	  	<div class="col-sm-8">
	  		<input type="password" class="form-control" name="password" id="password" data-rule-isPwd="true">
	  	</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label"><span class="requiredField">*</span>确认密码</label>
	  	<div class="col-sm-8">
	  		<input type="password" class="form-control" data-rule-equalTo="#password" data-msg-equalTo="密码不一致" data-rule-isPwd="true">
	  	</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label">角色</label>
		<div class="col-sm-8">
			{{each list}}
				{{if $value.status == '1'}}
					<label class="role_checkbox">
						<input type="checkbox" class="flat-red" value="{{$value.roleId}}"
							name="roleIds" required data-msg-required = "至少选择一个角色"
							{{each dto.roleIds as rid}} {{if rid == $value.roleId}} checked {{/if}} {{/each}}
							> {{$value.role}}</label>
				{{/if}}
			{{/each}}
				<label id="roleIds-error" class="fieldError" for="roleIds" style="float: right;"></label>
		</div>
    </div>	
	<!-- 
	<div class="form-group row">
		<label class="col-sm-4 control-label" for="type-select">状态</label>
		<div class="col-sm-3">
			<select class="form-control" name="status" value="{{dto.status}}">
					<option value="启用" {{if dto.status == '启用' }}selected{{/if}}> 启用 </option>
					<option value="禁用" {{if dto.status == '禁用' }}selected{{/if}}> 禁用 </option>
			</select>
		</div>
    </div>
    -->
    <#if auth.hasPermission("authority.unlock")>
	    <div class="form-group row">
	    	<label class="col-sm-4 control-label" for="type-select"></label>
			<div class="col-sm-4">
				<label class="role_checkbox"><input name="unlock" type="hidden" value="1"/><input type="checkbox" class="flat-red" {{if dto.status == '禁用' }}checked{{else}}disabled{{/if}} name="lockUser" />解锁账户</label>
			</div>
	    </div>
	</#if>
</div>
</form>
</script>
<script id="addEditTpl" type="text/html">
<form id="addEditForm" method="post" class="form-horizontal">
<div class="box-body form-horizontal addEditTpl">
	<div class="form-group row">
	  <label class="col-sm-4 control-label" ><span class="requiredField">*</span>用户名</label>
	  <div class="col-sm-8">
	    <input type="text" class="form-control" name="loginId" placeholder="请输入用户名"
        	data-rule-required="true" data-msg-required="请输入用户名" data-rule-userName="true">
	  </div>
	</div>
	<div class="form-group row">
	  <label class="col-sm-4 control-label"><span class="requiredField">*</span>姓名</label>
	  <div class="col-sm-8">
	    <input type="text" class="form-control" name="userName" placeholder="请输入姓名"
        	required data-msg-required="请输入姓名" data-rule-uname="true">
	  </div>
	</div>
	<div class="form-group row">
	  <label class="col-sm-4 control-label"><span class="requiredField">*</span>邮箱</label>
	  <div class="col-sm-8">
	    <input type="text" class="form-control" name="email" placeholder="请输入邮箱"
	  			required data-msg-required = "请输入邮箱" 
	  			 data-rule-isEmail="true">
	  </div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label"><span class="requiredField">*</span>手机号</label>
	  	<div class="col-sm-8">
	  		<input type="text" class="form-control" name="phone" placeholder="请输入手机号"
	  			required data-msg-required = "请输入手机号" maxlength="11"
	  			data-rule-isMobile="true">
	  	</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label">角色</label>
		<div class="col-sm-8">
			{{each data}}	
				{{if $value.status == '1'}}
					<label class="role_checkbox"><input type="checkbox" class="flat-red" value="{{$value.roleId}}"
						name="roleIds" required data-msg-required = "至少选择一个角色"> 
						{{$value.role}}</label>
				{{/if}}
			{{/each}}
				<label id="roleIds-error" class="fieldError" for="roleIds" style="float: right;"></label>
		</div>
    </div>	
	<div class="form-group row">
		<label class="col-sm-4 control-label">状态</label>
		<div class="col-sm-3">
			  <select class="form-control" name="status">
				<option value="启用"> 启用 </option>
				<option value="禁用"> 禁用 </option>
			  </select>
		</div>
    </div>
	<div class="form-group row">
		<label class="col-sm-4 control-label"></label>
		<div class="col-sm-8" style='color:red;'>密码默认为手机号后六位
		</div>
    </div>
</div>
</form>
</script>
 <script type="text/javascript" src="${uiBase}vendor/jquery-md5/jquery.md5.js"></script>
</html>