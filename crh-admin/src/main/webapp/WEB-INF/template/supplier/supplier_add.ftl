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
			   <li>新增商家</li>
		   </ol> 
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box ">
                     <div class="box-body">
                     	<form role="form" id="addForm">
                            <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">
		                            	<label class="control-label "><span class="requiredField">*</span>服务商编号：</label>		                         
		                            	<label class="control-label" id="orgCode"></label>
		                          	</div>
		                         </div>
                            </div>
                            <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">
                                    	<label class="control-label"><span class="requiredField">*</span>服务商名称：</label>
	                                	<input type="text" class="form-control" name="orgName" id="orgName">
	                                </div>
		                         </div>
                            </div>
                            <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">  
                                 		<label class="control-label"><span class="requiredField">*</span>企业法人姓名：</label>
                                 		<input type="text" class="form-control" name="businesser" id="businesser">
                                 	</div>
		                         </div>
                            </div>
                            <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">  
                                 		<label class="control-label"><span class="requiredField">*</span>工商执照注册号：</label>
                                 		<input type="text" class="form-control" name="registrationLicense" id="registrationLicense">
                                 	</div>
		                         </div>
                            </div>
                            <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">  
                                    	<label class="control-label"><span class="requiredField">*</span>联系人姓名：</label>
                                 		<input type="text" class="form-control" name="contact" id="contact">
                                 	</div>
		                         </div>
                            </div>       
	                        <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">  
                                    	<label class="control-label"><span class="requiredField">*</span>联系人手机号：</label>
                                    	<input type="text" class="form-control" name="phone" id="phone">                                    	
                                 	</div>
		                         </div>
                            </div>    
                            <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">  
                                    	<label class="control-label"><span class="requiredField">*</span>客服电话：</label>
                                    	<input type="text" class="form-control" name="servicePhone" id="servicePhone">                                    	
                                 	</div>
		                         </div>
                            </div>       
                            <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">  
                                    	<label class="control-label"><span class="requiredField"></span>简介：</label>
                                 	</div>
		                         </div>
                            </div> 
                            <div class="row">
                            	<div class="col-md-12">
                            		<div class="col-sm-10">  
                            			<textarea rows="3"  class="form-control" name="introduce" id="introduce"></textarea> 
                                	</div>
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
<script src="${uiBase}/js/pages/supplier/supplier_add.js?v=${resourceVersion}"></script> 
</html>
