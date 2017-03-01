<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/vendor/bootstrap-validator/css/bootstrapValidator.css">
<link rel="stylesheet" href="${uiBase}/css/pages/supplier.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
       <section class="content-header">
	 	   <ol class="breadcrumb">
			   <li><i class="fa fa-dashboard"></i> 服务商管理</li>
			   <li>编辑商家</li>
		   </ol> 
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box ">
                     <div class="box-body">
                     <form role="form" id="editorForm">
                            <div class="row">                              
		                      	 	<div class="col-sm-4" style="width: 400px;">
		                            	<label class="control-label" style="width: 125px;"><span class="requiredField">*</span>服务商编号：</label>		                         
		                            	<label class="control-label" id="orgCode">${org.orgCode}</label>
		                         </div>
                            </div>
                            <div class="row">                              
		                      	 	<div class="col-sm-4" style="width: 400px;">
                                    	<label class="control-label" style="width: 125px;"><span class="requiredField">*</span>服务商名称：</label>
	                                	<input type="text" class="form-control" name="orgName" id="orgName" value="${org.orgName}">
	                                </div>
                            </div>
                            <div class="row">                              
		                      	 	<div class="col-sm-4" style="width: 400px;">  
                                 		<label class="control-label" style="width: 125px;"><span class="requiredField">*</span>企业法人姓名：</label>
                                 		<input type="text" class="form-control" name="businesser" id="businesser" value="${org.businesser}">
                                 	</div>
                            </div>
                            <div class="row">                              
		                      	 	<div class="col-sm-4" style="width: 400px;">  
                                 		<label class="control-label" style="width: 125px;"><span class="requiredField">*</span>工商执照注册号：</label>
                                 		<input type="text" class="form-control" name="registrationLicense" id="registrationLicense" value="${org.registrationLicense}">
                                 	</div>
                            </div>
                            <div class="row">                              
		                      	 	<div class="col-sm-4" style="width: 400px;">  
                                    	<label class="control-label" style="width: 125px;"><span class="requiredField">*</span>联系人姓名：</label>
                                 		<input type="text" class="form-control" name="contact" id="contact" value="${org.contact}">
                                 	</div>
                            </div>       
	                        <div class="row">                              
		                      	 	<div class="col-sm-4" style="width: 400px;">  
                                    	<label class="control-label" style="width: 125px;"><span class="requiredField">*</span>联系人手机号：</label>
                                    	<input type="text" class="form-control" name="phone" id="phone" value="${org.phone}">                                    	
                                 	</div>
                            </div>
                            <div class="row">                              
		                      	 <div class="col-md-4" style="width: 400px;">
                                    	<label class="control-label" style="width: 125px;"><span class="requiredField">*</span>客服电话：</label>
                                    	<input type="text" class="form-control" name="servicePhone" id="servicePhone" value="${org.servicePhone}">                                    	
		                         </div>
                            </div>       
                            <div class="row">                              
		                      	 <div class="col-md-4" style="width: 125px;">
                                    	<label class="control-label"><span class="requiredField"></span>简介：</label>
		                         </div>
                            </div> 
                            <div class="row">
                            	<div class="col-md-4">
                            			<textarea rows="3"  class="form-control" name="introduce" id="introduce" style="width: 550px;">${org.introduce}</textarea>
                            	</div>
                            </div>        
                             <div class="row">
	                            <div class="col-md-12 text-center">
	               					 <a class="btn btn-primary" id="save">保存</a>
	               					 <button class="btn btn-success backPage" type="button" ><i class="fa fa-backward"> 返回 </i></button>		                   			               					 
	                            </div>
	                          </div> 
	                          </form> 
                      </div>
                </div>
             </div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
</body>
<#include "/footer.ftl" /> 
<script src="${uiBase}/js/pages/supplier/org_editor.js?v=${resourceVersion}"></script> 
</html>
