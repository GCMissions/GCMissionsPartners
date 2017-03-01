<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/vendor/bootstrap-validator/css/bootstrapValidator.css">
<link rel="stylesheet" href="${uiBase}/css/pages/supplier.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}/css/pages/advertise/advertise_list.css?v=${resourceVersion}">
<style type="text/css">
	.img_div{
		float: right;margin-right: 265px;
	}
	.hidden{
		display:none;
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
			   <li><i class="fa fa-dashboard"></i> APP启动页管理</li>
			   <li>新增启动页</li>
		   </ol> 
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box ">
                     <div class="box-body">
                     	<form role="form" id="addForm" >
                            <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">
                                    	<label class="control-label"><span class="requiredField">*</span>手机类型：</label>
	                                	<select class="form-control selectpicker" name="mobileType">
	                                		<#list appSource as enum>
	                                			<option value="${enum.code}">${enum.source}</option>
	                                		</#list>
	                                	</select>
	                                </div>
		                         </div>
                            </div>
                            <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">  
                                 		<label class="control-label"><span class="requiredField">*</span>长度：</label>
                                 		<input type="text" class="form-control digits" name="height" id="height" data-msg-digits="请输入数字" required data-msg-required="必选">
                                 	</div>
		                         </div>
                            </div>
                            <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">  
                                 		<label class="control-label"><span class="requiredField">*</span>宽度：</label>
                                 		<input type="text" class="form-control digits" name="width" id="width" data-msg-digits="请输入数字" required data-msg-required="必选">
                                 	</div>
		                         </div>
                            </div>
                            <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">  
                                    	<label class="control-label"><span class="requiredField">*</span>版本号：</label>
                                 		<input type="text" class="form-control" name="version" id="version" required data-msg-required="必选">
                                 	</div>
		                         </div>
                            </div>  
                            </form> 
                            <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">
		                            	<label class="control-label "><span class="requiredField">*</span>启动页图片：</label>	
		                            	<div class="col-sm-4 img_div">
		                            		<input type="hidden" id="uploadImageUrl">
			                            	<div class="upload-thumb"> <img src="${base}/dist/img/default_goods_image_240.gif" id="imgUrl"></div>
											<div class="upload-btn">
												<a href="javascript:void(0);"> 
													<span>
														<input type="file" hidefocus="true" size="1" 
															class="input-file"  accept=".jpg,.png,.gif" name="file" id="appStartup_1">
													</span>
													<p><i class="fa fa-fw fa-upload"></i>上传</p>
												</a>
											</div>	                         
		                            	</div>
		                            	<div class="clearfix"></div>
		                          	</div>
		                         </div>
                            </div>    
                             <div class="row">
	                            <div class="col-md-12 text-center">
	               					 <a class="btn btn-primary" id="save">保存</a>
	               					 <button class="btn btn-success backPage" type="button" ><i class="fa fa-backward"> 返回 </i></button>		                   			               					 
	                            </div>
	                          </div>  
                       	 
                      </div>
                </div>
             </div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
   <input type="hidden" value="" id="entity_id">
</body>
<#include "/footer.ftl" /> 
<script type="text/javascript" src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script>
<script src="${uiBase}js/pages/app/appStartupHomepage/ash_add.js?v=${resourceVersion}"></script> 
</html>
