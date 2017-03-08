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
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
          <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i> ${showType}</li>
          	<li><#if showType == "0">Add Slide<#else>Edit Slide</#if></li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group" id="search-area" >
                    	<div class="box-body">
                    		<div class="col-sm-12">
                    			<label><#if showType == "0">Add New Slide<#else>Edit Slide</#if></label>
                    		</div>
                    		<#if showType == "1">
                    		<div class="col-sm-12">
                    			<label class="col-sm-3"><span class="requiredField">*</span>Order:</label>
                    		</div>
                    		</#if>
                    		<div class="col-sm-12">
                    			<label class="col-sm-3"><span class="requiredField">*</span>Slide:</label>
                    			<div class="adspic-list col-sm-3">
									<ul>
										<li class="adspic-upload " id="slide_upload">
											<div class="upload-thumb">
												<img src="${base}/dist/img/default_goods_image_240.gif"
													id="slide_img">
											</div>
											<div class="upload-btn">
												<a href="javascript:void(0);"> <span><input
														type="file" hidefocus="true" size="1" class="input-file"
														name="file" id="slide"
														accept=".jpg,.png,.gif"></span>
													<p>
														<i class="fa fa-fw fa-upload"></i>upload
													</p>
												</a>
											</div>
										</li>
									</ul>
								</div>
                    		</div>
                    		<div class="col-sm-12">
                    			<label class="col-sm-3">Description:</label>
                    		</div>
                    		<div class="col-sm-12">
                    			<textarea rows="3" class="form-control col-sm-12"></textarea>
                    		</div>
                    		<div class="col-sm-12">
                    			<#if showType == "0">
                    			<input type="radio" class="col-sm-1" style="margin-top: 11px;" name="display" checked="true"/><label class="col-sm-1">Display</label>
                    			<input type="radio" class="col-sm-1" style="margin-top: 11px;" name="display"/><label class="col-sm-1">Hide</label>
                    			<#else>
                    				<#if slide.displayed == "0">
                    				<input type="radio" class="col-sm-1" style="margin-top: 11px;" name="display"/><label class="col-sm-1">slide.displayed</label>
                    				<input type="radio" class="col-sm-1" style="margin-top: 11px;" name="display" checked="true"/><label class="col-sm-1">Hide</label>
                    				<#else>
                    				<input type="radio" class="col-sm-1" style="margin-top: 11px;" name="display"/><label class="col-sm-1">slide.displayed</label>
                    				<input type="radio" class="col-sm-1" style="margin-top: 11px;" name="display" checked="true"/><label class="col-sm-1">Hide</label>
                    				</#if>
                    			</#if>
                    		</div>
                    		<div class="col-sm-6" style="margin-top: 20px;">
								<div class="col-sm-6 col-sm-offset-5">
									<button class="btn  btn-success backPage" id="back" ><i class="fa fa-backward"></i> Cancel</button>
									<button  class="btn  btn-success" id="save" style="font-weight:100">Save</button>
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

<script type="text/javascript" src="${uiBase}js/pages/slides/slides_detail.js?v=${resourceVersion}"></script>