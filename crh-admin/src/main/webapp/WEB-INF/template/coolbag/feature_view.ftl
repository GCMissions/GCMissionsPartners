<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/pages/coolbag/coolbag_img.css?v=${resourceVersion}">
<style>
.control-label{
	width:120px !important;
}
.info{
	line-height: 47px;
}
.checkbox {
	width:50%;	
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
              <li>酷袋专辑查看</li>
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
							<div class="feature_img col-sm-3">
								<img src="${feature.image}" id="feature_img">
							</div>
						</div>
						<div class="form-group row">
							<label class="control-label col-sm-2">所属标签:</label>
							<div class="checkbox">
								<#list tagList as tag>
					            <label>
					            	<#if feature.tagIds?? && feature.tagIds?seq_contains(tag.id)> 
					               	<input type="checkbox" name="tagIds" value="${tag.id}" checked="checked" disabled="disabled"/> <span>${tag.name}</span>
					               	<#else>
					               	<input type="checkbox" name="tagIds" value="${tag.id}" disabled="disabled"/> <span>${tag.name}</span>
					               	</#if>
					            </label>
					            </#list>
					        </div>
						</div>
						<div class="form-group row">
							<label class="control-label col-sm-2">页面标签:</label>
							<span class="info">${feature.tag}</span>
						</div>
						<div class="form-group row">
							<label class="control-label col-sm-2">专辑名称:</label>
							<span class="info">${feature.name}</span>
							&nbsp;&nbsp;
							<#if feature.ifHomeshow == 1 >
							<input type="checkbox" name="ifHomeshow" value="${feature.ifHomeshow}" checked="checked" disabled="disabled"/><span>首页显示</span>
							<#else>
							<input type="checkbox" name="ifHomeshow" value="${feature.ifHomeshow}" disabled="disabled"/><span>首页显示</span>
							</#if>
						</div>
						<div class="form-group row">
							<label class="control-label col-sm-2">购买链接:</label>
							<span class="info">${feature.buyUrl}</span>
						</div>
						<div class="form-group row">
							<label class="control-label col-sm-2">专辑描述:</label>
							<span class="info">${feature.description}</span>
						</div>
						<div class="form-group row">
							<label class="control-label col-sm-2">专辑详情:</label>
							<div class="col-sm-8" style="margin-top: 10px;">${feature.details}</div>
						</div>
						<div class="form-group row">
                         	<div class="col-md-8 text-center">
           						<#--<a class="btn btn-primary" id="back">返回</a>-->
           						<button type="button" class="btn btn-primary backPage" id="back">返回</button>
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
</html>
