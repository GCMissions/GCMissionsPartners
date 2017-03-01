<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/advertise.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
       <section class="content-header">
       <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i> 商城管理</li>
            <li>APP消息管理 </li>             
          </ol>
       </section>
        <!-- Main content -->
        <div class="row pad">
            <div class="col-md-12">
                <div class="box box-primary">                  
                    <div class="box-body" id="messageForm">
                    <form action="" id="mainForm"  method=post>
                    <div class="row form-group">
	                    <div class="col-md-12">
	       					<div class="col-sm-4">
	                  	        <#if review>
	                  	        <label class="control-label" >消息标题：</label>
	                            <span class="form-control-static">
                               	 ${detail.title} 
                                </span>
                                <#else>
                                <label class="control-label" ><span class="requiredField">*</span>消息标题：</label>
                                <input type="text" id="appTitle" class="form-control" required >
                                </#if> 
                                 
	                             
	                        </div>
	                    </div>
                    </div> 
                    <div class="row form-group">
	                    <div class="col-md-12">
	       					<div class="col-sm-4 form-app-pic">
	                  	        <#if review>
	                  	         <label class="control-label">消息主图：</label>
	                  	        	<#if detail.image>
	                            		<p class="form-control-static"><img src="${file}${detail.image}"></p>
	                            	</#if>
                                <#else>
                                <label class="control-label"><span class="requiredField">*</span>消息主图：</label> <label class="fieldError mainImgError" ></label>				
								<div class="appImg">
									<button type="button" class="btn btn-default addNewPic">添加图片</button>
								 </div>
								 <div class="adspic-list col-sm-8">
									<ul>
									</ul>
								</div>
                                </#if> 
	                        </div>
	                    </div>
                    </div> 
                    <div class="row form-group">
                     <div class="col-md-12"> 
                          <div class="col-sm-8">
                          	<#if review>
                          	<label class="control-label" for="type-select">消息内容：</label>
                          	<p class="form-control-static">
                               	 ${detail.content} 
                            </p>
                            <#else>
                            <label class="control-label" for="type-select"><span class="requiredField">*</span>消息内容：</label>
                            <label class="fieldError editFormError" ></label>	
                            <script id="ueEditor" name="message" type="text/plain" style="width:100%;height:500px;"></script>
                            </#if>
                            
                          </div>
                          </div>
                    </div>
                    <div class="row">                            
						<div class="col-sm-12 text-center">
							<div class="submit_length">
							<#if review>							                  
		                    <button class="btn btn-success backPage" type="button"   ><i class="fa fa-backward"> 返回 </i></button>
							<#else>
							<button class="btn btn-primary submitMainForm" id="save" type="button"  >保存</button>	
							<button class="btn btn-success backPage" type="button"><i class="fa fa-backward"> 返回 </i></button>
							</#if>
							</div>  
						</div>                                                        
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
<script id="picItemTpl" type="text/html">
<li class="adspic-upload" id="upload_{{id}}">
    <div class="upload-thumb">
		<img src="{{showImageUrl}}"  id="img_{{id}}">
        <input type="hidden" name="picId[{{id}}]" value=""  id="name_{{id}}">
        <input type="hidden" name="picUrl[{{id}}]" value="{{imageUrl}}"  id="name_{{id}}">
    </div>
	<div class="upload-setDefault">
    	<div class="form-group">
    		<input type=hidden name="goodsCover[{{id}}]" class="goodsCover " value="{{isCover}}" >
            <a href="javascript:;" class="btnSetCover {{if isCover}}isCover{{/if}}">{{CoverText}}</a>
    	</div>
    </div>
    <div class="upload-btn">    	
	    <a href="javascript:void(0);">
		    <span><input type="file" hidefocus="true" size="1" class="input-file" name="file" id="{{id}}"></span>
		    <p><i class="fa fa-fw fa-upload"></i>上传</p>
	    </a>
	</div>
	
	<div class="remove-btn">    	
	    <a href="javascript:;"> 
		    <p><i class="fa fa-fw fa-times"></i>删除</p>
	    </a>
	</div>
	
</li>	
</script>
<script type="text/javascript" src="${uiBase}js/pages/appMessage/appMesAdd.js?v=${resourceVersion}"></script>
 <script type="text/javascript" src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script>
</html>
