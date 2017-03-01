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
            <li><i class="fa fa-dashboard"></i> 平台用户管理</li>
          	<li>平台用户管理</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group" id="search-area" >
                    	<div class="box-body">
	                    	<div class="col-sm-12">
	                    		<label class="col-sm-2 control-label">注册时间：</label>
		                         <div class="no-padding datetimeInputGroup" >
									<div class="input-group date datetimeInput no-padding pull-left">
										<input size="16" type="text" name="startTime" id="csDate" class="form-control startTimeSelection" placeholder="开始时间" readonly>
										<span class="add-on" style="display: none"><i class="icon-remove"></i></span>
										<span class="add-on"><i class="icon-calendar"></i></span>
									</div>
									<span class="pull-left textTo">至</span>
									<div class="input-group date datetimeInput no-padding pull-left" style="margin-bottom: 0px;">
										<input size="16" type="text" name="endDate"  id="ceDate" class="form-control endTimeSelection" placeholder="结束时间" readonly>
										<span class="add-on" style="display: none" ><i class="icon-remove"></i></span>
										<span class="add-on"><i class="icon-calendar"></i></span>
									</div>
								</div>
							</div>	
							<div class="col-sm-12" style="padding-top: 20px;">
                    		 	<label class="col-sm-2 control-label">手机号：</label>
                    		 	<input id="mobile" name="mobile" class="form-control col-sm-3" type="text" >
                    		 	<label class="col-sm-1 control-label">昵称：</label>
                    		 	<input id="nickName" name="nickName" class="form-control col-sm-3" type="text">
                    		 	<div class="col-sm-3">
                    		 		<select class="selectpicker form-control productBrand " id="vip" title="是否VIP" > 
                                    	<option value="">不限</option>
                                    	<option value="1">是</option>
                                    	<option value="0">否</option>
									</select>
                    		 	</div>
                    		 	<div class="col-sm-1">
                    		 		<select class="selectpicker form-control productBrand " id="sex" title="性别" > 
                                    	<option value="">不限</option>
                                    	<option value="0">男</option>
                                    	<option value="1">女</option>
									</select>
                    		 	</div>
							</div>
							<div class="col-sm-12" style="padding-top: 20px;padding-left: 5%;">
							<div class="form-group">   
					            <label class="col-sm-2 control-label">   
					                <button type="button" id="searchBtn" class="btn btn-primary"><i class="fa fa-search"></i> 查询</button> 
					            </label>
					        </div>
							</div>
                    	</div>
                    </div>
                    <div class="box-body">
                    <div class="col-sm-12">
                    	<label class="control-label"> 
                    	现有注册用户数：${registerCount}（人）
                    	</label>
                    </div>
                      <table id="dataList" class="table table-bordered table-hover" >
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

<script type="text/javascript" src="${uiBase}js/pages/member/member_list.js?v=${resourceVersion}"></script>