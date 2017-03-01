<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/pages/coolbag/coolbag_img.css?v=${resourceVersion}">
<style type=text/css>
.tab_div {
	display:table;
	width:100%;
}
.perform_underline{
	border-bottom: 2px solid #ddd;
	padding-bottom: 30px;
	margin-bottom: 30px;
}

.description{
	display: block;
	width:360px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
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
            <li>吾儿酷袋管理</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
        	<div class="col-md-12">
				<div class="nav-tabs-custom">
					<!-- tab 页标题 -->
					<ul class="nav nav-tabs">
						<li <#if 'feature' != active>class="active"</#if>><a href="#tab_1" data-toggle="tab">酷袋轮播位管理</a></li>
                 		<li><a href="#tab_2" data-toggle="tab">标签管理</a></li>  
                 		<li <#if active == 'feature'>class="active"</#if>><a href="#tab_3" data-toggle="tab">专辑管理</a></li> 
              		</ul>
               		 
	               <!-- TAB页 -->
	                <div class="tab-content">
	                	<!-- 酷袋轮播位管理 -->
						<div class='tab-pane <#if "feature" != active>active</#if>' id="tab_1">
							<div class="tab_div" id="circleTab">
								<div style="padding-bottom: 10px;">
									<span class="btn btn-primary" id="newCircle" href="#" >新增轮播</span>
								</div>
								<#list carouselList as carousel>
								<div class="row carousel_upload carousel_form" id="carousel_form_${carousel.id}">
									<div class="col-sm-1 text-right">
										<label data-index=${carousel_index +1} >图片${carousel_index +1}：</label>
										<input type="hidden" class="carouselSort" value=${carousel.sort}>
									</div>
									 <div class="adspic-list col-sm-3">
										<ul>
											<li class="adspic-upload " id="carousel_upload_${carousel.id}">
											    <div class="upload-thumb">
											    	<#if carousel.url??>
														<img src="${carousel.url}"  id="carousel_img_${carousel.id}">
													<#else>
														<img src="${base}/dist/img/default_goods_image_240.gif"  id="carousel_img_${carousel.id}">
											        </#if>
											        <input type="hidden" name="id" value="${carousel.id}">
											        <input type="hidden" name="url" value="${carousel.url}"  id="carousel_image_${carousel.id}">
											    </div>
											    <div class="upload-btn">    	
												    <a href="javascript:void(0);">
													    <span><input type="file" hidefocus="true" size="1" class="input-file" name="file" id="carousel_${carousel.id}" data-id="${carousel.id}" accept=".jpg,.png,.gif"></span>
													    <p><i class="fa fa-fw fa-upload"></i>上传</p>
												    </a>
												</div>
											</li>	
										</ul>
									</div>
									<div class="col-sm-4">
									
										<label>关联专辑：</label>
										<input type="text" class="form-control chooseDialog" name="name" value="${carousel.name}" id="carousel_feature_name_${carousel.id}" readonly="readonly"/>
										<button class="btn btn-default chooseDialog" type="button" data-id="${carousel.id}">浏览</button> 
										<br>
										<label>关联url：</label>
										<input type="text" class="form-control relfUrl" name="relfUrl" value="${carousel.relfUrl}" id="carousel_feature_name_${carousel.id}" style="margin:10px 0 0 5px;"/>
										<input type="hidden" name="featureId" value="${carousel.featureId}" id="carousel_feature_${carousel.id}">
									</div>
									<div class="col-sm-2">
										<button class="btn btn-primary save" type="button" data-id="${carousel.id}">保存 </i></button>
										<button class="btn btn-primary delete" type="button" data-id="${carousel.id}">删除 </i></button> 
									</div>
								</div>
								</#list>
							</div>
	                  	</div>
	                  	<!-- 酷袋轮播位管理  end -->
	                  	
	                  	<!-- 标签管理 -->
						<div class='tab-pane' id="tab_2">
							<div class="tab_div">
								<#list tagList as tag>
								<div class="row tag_upload" id="tag_form_${tag.id}">
									<div class="col-sm-1 text-right">
										<label>位置${tag.sort}：</label>
									</div>
									 <div class="adspic-list col-sm-3">
										<ul>
											<li class="adspic-upload " id="tag_upload_${tag.id}">
											    <div class="upload-thumb">
											    	<#if tag.url??>
													<img src="${tag.url}"  id="tag_img_${tag.id}">
													<#else>
													<img src="${base}/dist/img/default_goods_image_240.gif"  id="tag_img_${tag.id}">
											        </#if>
											        <input type="hidden" name="id" value="${tag.id}">
											        <input type="hidden" name="url" value="${tag.url}"  id="tag_image_${tag.id}">
											    </div>
											    <div class="upload-btn">    	
												    <a href="javascript:void(0);">
													    <span><input type="file" hidefocus="true" size="1" class="input-file" name="file" id="tag_${tag.id}" data-id="${tag.id}" accept=".jpg,.png,.gif"></span>
													    <p><i class="fa fa-fw fa-upload"></i>上传</p>
												    </a>
												</div>
											</li>	
										</ul>
									</div>
									<div class="col-sm-3">
										<label>标签名称：</label>
										<input type="text" class="form-control" name="name" value="${tag.name}">
									</div>
									<div class="col-sm-1">
										<button class="btn btn-primary save" type="button" data-id="${tag.id}">保存 </i></button> 
									</div>
								</div>
								</#list>
							</div>
	                  	</div>
	                  	<!-- 标签管理  end -->
	                  	
	                  	<!-- 专辑管理 -->
						<div class='tab-pane <#if active == "feature">active</#if>' id="tab_3">
							<div class="tab_div formDiv">
								<div style="padding-bottom: 10px;">
									<a class="btn btn-primary"  href="${urlPrefix}coolbag/feature/add/-1" >新增专辑</a>
								</div>
								<table id="dataList" class="table table-bordered table-hover" >
                     			</table>
							</div>
	                  	</div>
	                  	<!-- 专辑管理  end -->
	                </div>
	                <!-- TAB页 end -->
				</div>
			</div>
        </div>
        <!-- /.content -->
        <div class="clearfix"></div>
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
   <input type="hidden" id="uiBaseVal" value="${uiBase}">
</body>
<#include "/footer.ftl" />
<script id="featureTpl" type="text/html">
<div class="row pad">
	<div class="col-md-12">
		<div class="box-body">
			<table id="featureList" class="table table-bordered table-hover">
			</table>
		</div>
	</div>
</div>
</script>
<script type="text/javascript" src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script> 
<script src="${uiBase}/js/pages/coolbag/coolbag.js?v=${resourceVersion}"></script> 
</html>
