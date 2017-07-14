<#assign headComponents = ["bootTable","bootDialog"] >
<#include "/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/pages/slides/slides_detail.css?v=${resourceVersion}">
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

<script type="text/javascript"> 
         function uploadvideo(){
					var display = document.getElementById("video").style.display;
					if (display == "none"){
						$("#video").removeAttr("style");
					    document.getElementById("linkfile").style.display="none";
					}
					else{
						 document.getElementById("video").style.display="none";
						 $("#linkfile").removeAttr("style");
						}
				}

         function ajaxupload(){
        	 var value =  $("#uploadVideo").text();
        	 $("#uploadVideo").text("uploading...").attr("disabled", true);
        	 $.ajaxFileUpload({
 				url : $.GLOBAL.config.uploadvideoUrl.template({source: uploadSourcesMap.resource}),
 				secureuri : false,
 				fileElementId : "videoFile",
 				dataType : 'json',
 				global : false,
 				data : {},
 				success : function (data, status) {
 					if (data.code == "ACK") {
 						$("#link").val(data.data.url); 
 						uploadvideo();
 					} else {
 						$(window).loadingInfo("error", data.message);
 					}
 					$("#uploadVideo").text(value).attr("disabled", false);
 				},
                error: function (data, status){//服务器响应失败处理函数
                	$("#uploadVideo").text(value).attr("disabled", false);
                }
 				
 			});
         }
         
</script>

</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
          <ol class="breadcrumb">
            <li><img src="${uiBase}img/resourse.png"> Resource</li>
          	<li><#if showType == "0">Add New Resource<#else>Edit Resource</#if></li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group" id="search-area" >
                    	<div class="box-body">
                    	<input type="hidden" id="resourceId" value="${id}">
                    		<div class="col-sm-12">
                    			<label><#if showType == "0">Add New Resource<#else>Edit Resource</#if></label>
                    		</div>
                    		<#if showType == "1">
                    		<div class="col-sm-12">
                    			<label class="col-sm-2"><span class="requiredField">*</span>Order:</label>
                    			<label class="col-sm-3">${resource.index}</label>
                    		</div>
                    		</#if>
                    		<div class="col-sm-12">
                    			<label class="col-sm-3"><span class="requiredField">*</span>Icon:</label>
                    			<div class="adspic-list col-sm-3">
									<ul>
										<li class="adspic-upload " id="resource_upload">
											<div class="upload-thumb">
												<#if showType == "0">
												<img src="${base}/dist/img/default_goods_image_240.gif"
													id="resource_img">
												<#else>
												<img src="${resource.image}"
													id="resource_img">
												</#if>
											</div>
											<div class="upload-btn">
												<a href="javascript:void(0);"> <span><input
														type="file" hidefocus="true" size="1" class="input-file"
														name="file" id="resource"
														accept=".jpg,.png,.gif"></span>
													<p>
														<i class="fa fa-fw fa-upload" style="margin-right: 0px;"></i>upload
													</p>
												</a>
											</div>
										</li>
									</ul>
								</div>
                    		</div>
                    		<div class="col-sm-12" style="text-align: center;">
                    			<span class="col-sm-9" style="color: red;">suggest ratio 2:1 and size < 5M </span>
                    		</div>
                    		<div class="col-sm-12" style="margin-top: 3%;">
	                    		<div class="col-sm-6" style="" id="linkfile">
		                    		<label class="col-sm-2"><span class="requiredField">*</span>Link:</label>
	                    			<#if showType == "0">
	                    			<input type="text" id="link" class="col-sm-12 form-control" style="width: 300px;"/>
	                    			<#else>
	                    			<input type="text" id="link" class="col-sm-12 form-control" value="${resource.link}" style="width: 300px;"/>
	                    			</#if>
	                    		</div>
	                    		<div style="display:none;" id="video">
	                    			<label class="col-sm-1" style="margin-right:10px;"><span class="requiredField"style="">&nbsp;&nbsp;&nbsp;&nbsp;*</span>video:</label>
	                    			<input type="file" id="videoFile" name="videoFile" class="col-sm-12 form-control" onchange="ajaxupload()" value="" style="width: 300px;"/>
	                    		</div>
	                    		<div class="col-sm-4">
	                    			<button  class="btn btn-primary" id="uploadVideo" onclick="uploadvideo()" style="font-weight:100;">Upload Video</button>
	                    		</div>
                    		</div>
                    		<div class="col-sm-12" >
	                    		<div class="col-sm-6" id="radio_box">
		                    		<label class="col-sm-2"><span class="requiredField">*</span>Title:</label>
	                    			<#if showType == "0">
	                    			<input type="text" id="title" class="col-sm-12 form-control" style="width: 300px;"/>
	                    			<#else>
	                    			<input type="text" id="title" class="col-sm-12 form-control" value="${resource.title}" style="width: 300px;"/>
	                    			</#if>
	                    		</div>
                    		</div>
                    		<div class="col-sm-6" style="margin-top: 20px;">
								<div class="col-sm-6 col-sm-offset-5">
									<button class="btn  btn-default backPage" id="back" > Cancel</button>
									<#if showType == "0">
									<button  class="btn  btn-primary" id="add" style="font-weight:100">Save</button>
									<#else>
									<button  class="btn  btn-primary" id="edit" style="font-weight:100">Save</button>
									</#if>
								</div>
							</div>
                    	</div>
                    </div>
                </div>
             </div>
        </div>
        <div class="clearfix"></div>
        
      </div>
   </div>


</body>
<#include "/footer.ftl" />

<script type="text/javascript" src="${uiBase}js/pages/resource/resource_detail.js?v=${resourceVersion}"></script>
<script type="text/javascript"
	src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script>