<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/pages/coolbag/coolbag_img.css?v=${resourceVersion}">
<style>
.control-label {
	width: 120px !important;
}

.checkbox {
	width:50%;	
	display:block;
	margin-top: 7px !important;
}

.checkbox>label {
	line-height: 7px !important;
	margin-right: 5px;
}

.checkbox>label>input {
	margin-top: -2px !important;
	margin-bottom: 1px !important;
	vertical-align: middle;
	margin-right: 5px !important;
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
            <li><i class="fa fa-dashboard"></i>吾儿酷袋</li>
            <#if feature.id??>
              <li>酷袋专辑编辑</li>
            <#else>
            <li>酷袋专辑添加</li>
            </#if>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
        	<div class="col-md-12">
	       		<div class="box">
	        		<div class="box-body form-horizontal">
					<form id="featureAddForm" method="post" action="${base}/web/coolbag/feature/save">
						<input type="hidden" name="id"  value="${feature.id}" />
						<div class="form-group row">
							<label class="control-label col-sm-2">专辑封面图:</label>
							<div class="adspic-list col-sm-3">
								<ul>
									<li class="adspic-upload">
									    <div class="upload-thumb">
									    	<#if feature.image??>
											<img src="${feature.image}"  id="feature_img">
											<#else>
											<img src="${base}/dist/img/default_goods_image_240.gif"  id="feature_img">
									        </#if>
									        <input type="hidden" name="image" value="${feature.image}"  id="feature_image">
									    </div>
									    <div class="upload-btn">    	
										    <a href="javascript:void(0);">
											    <span><input type="file" hidefocus="true" size="1" class="input-file" name="file" id="feature_upload_file" accept=".jpg,.png,.gif"></span>
											    <p><i class="fa fa-fw fa-upload"></i>上传</p>
										    </a>
										</div>
									</li>	
								</ul>
							</div>
						</div>
						<div class="form-group row">
							<label class="control-label col-sm-2">所属标签:</label>
							
							<div class="checkbox">
								<#list tagList as tag>
					            <label>
					            	<#if feature.tagIds?? && feature.tagIds?seq_contains(tag.id)> 
					               	<input type="checkbox" name="tagIds" value="${tag.id}" checked="checked" /> <span>${tag.name}</span>
					               	<#else>
					               	<input type="checkbox" name="tagIds" value="${tag.id}" /> <span>${tag.name}</span>
					               	</#if>
					            </label>
					            </#list>
					        </div>
						</div>
						<div class="form-group row">
							<label class="control-label col-sm-2">页面标签:</label>
							<input type="text" class="form-control" name="tag" style="width:50% !important;" value="${feature.tag}" placeholder="请输入页面标签"/>
						</div>
						<div class="form-group row">
							<label class="control-label col-sm-2">专辑名称:</label>
							<input type="text" class="form-control" name="name" style="width:50% !important;" value="${feature.name}" placeholder="请输入专辑名称"/>
							<#if feature.ifHomeshow == 1 >
							<input type="checkbox" name="ifHomeshow" value="${feature.ifHomeshow}" checked="checked"/><span>首页显示</span>
							<#else>
							<input type="checkbox" name="ifHomeshow" value="${feature.ifHomeshow}"/><span>首页显示</span>
							</#if>
						</div>
							<div class="form-group row">
							<label class="control-label col-sm-2">购买链接:</label>
							<input type="text" class="form-control" name="buyUrl" style="width:50% !important;" value="${feature.buyUrl}" placeholder="请输入购买链接"/>
						</div>
						<div class="form-group row">
							<label class="control-label col-sm-2">专辑描述:</label>
							<textarea name="description" class="form-control" style="width:60%;height:80px;" maxlength="200" placeholder="请输入专辑描述">${feature.description}</textarea>
						</div>
						<div class="form-group row">
							<label class="control-label col-sm-2">专辑详情:</label>
							<script id="ueEditor" type="text/plain" name="details" style="width:60%; height: 300px; margin-left: 120px;">${feature.details}</script>
						</div>
						<div class="form-group row">
                         	<div class="col-md-8 text-center">
           						<a class="btn btn-primary" id="save">保存</a>
           						
           						<a class="btn btn-primary" id="back">返回</a>
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
<script type="text/javascript" src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script> 
<script src="${uiBase}/js/pages/coolbag/feature_add.js?v=${resourceVersion}"></script> 
</html>
