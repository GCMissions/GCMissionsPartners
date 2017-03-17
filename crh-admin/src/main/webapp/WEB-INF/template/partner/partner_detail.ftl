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
            <li><img src="${uiBase}img/partner.png"> Partner</li>
          	<li><#if showType == "0">Add New Partner<#elseif showType == "1">View Partner<#else>Edit Partner</#if></li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group" id="search-area" >
                    	<div class="box-body">
                    	<input type="hidden" id="partnerId" value="${partner.id}">
                    		<div class="col-sm-12">
                    			<label><#if showType == "0">Add New Partner<#elseif showType == "1">View Partner<#else>Edit Partner</#if></label>
                    		</div>
                    		<div class="col-sm-12">
                    			<label class="col-sm-3"><#if showType != "1"><span class="requiredField">*</span></#if>Partner:</label>
                    			<#if showType == "0">
                    			<input type="text" class="col-sm-6 form-control" style="width: 385px;" id="partnerName"/>
                    			<#elseif showType == "1">
                    			<label class="col-sm-6">${partner.partnerName}</label>
                    			<#else>
                    			<input type="text" class="col-sm-6 form-control" value="${partner.partnerName}" style="width: 385px;" id="partnerName"/>
                    			</#if>
                    		</div>
                    		<div class="col-sm-12">
                    			<label class="col-sm-3"><#if showType != "1"><span class="requiredField">*</span></#if>Mission:</label>
                    			<#if showType == "0">
                    			<input type="text" class="col-sm-6 form-control" style="width: 385px;" id="mission"/>
                    			<#elseif showType == "1">
                    			<label class="col-sm-6">${partner.mission}</label>
                    			<#else>
                    			<input type="text" class="col-sm-6 form-control" value="${partner.mission}" style="width: 385px;" id="mission"/>
                    			</#if>
                    		</div>
                    		<div class="col-sm-12">
                    			<label class="col-sm-3"><#if showType != "1"><span class="requiredField">*</span></#if>Region:</label>
                    			<#if showType == "0">
                    			<select class="col-sm-6 form-control" id="region" style="width: 385px;">
                    				<#list regionAndCountry.regionList as item>
                    				<option value="${item.id}">${item.regionName}</option>
                    				</#list>
                    			</select>
                    			<#elseif showType == "1">
                    			<label class="col-sm-6">${partner.regionName}</label>
                    			<#else>
                    			<select class="col-sm-6 form-control" id="region" style="width: 385px;">
                    				<option value="${partner.regionId}">${partner.regionName}</option>
                    				<#list partner.regionList as item>
                    				<option value="${item.id}">${item.regionName}</option>
                    				</#list>
                    			</select>
                    			</#if>
                    		</div>
                    		<div class="col-sm-12">
                    			<label class="col-sm-3"><#if showType != "1"><span class="requiredField">*</span></#if>Country:</label>
                    			<#if showType == "0">
                    			<select class="col-sm-6 form-control" id="country" style="width: 385px;">
                    				<#list regionAndCountry.countryList as item>
                    				<option value="${item.id}">${item.countryName}</option>
                    				</#list>
                    			</select>
                    			<#elseif showType == "1">
                    			<label class="col-sm-6">${partner.countryName}</label>
                    			<#else>
                    			<select class="col-sm-6 form-control" id="country" style="width: 385px;">
                    				<option value="${partner.countryId}">${partner.countryName}</option>
                    				<#list partner.countryList as item>
                    				<option value="${item.id}">${item.countryName}</option>
                    				</#list>
                    			</select>
                    			</#if>
                    		</div>
                    		<div class="col-sm-12">
                    			<label class="col-sm-3">Address:</label>
                    			<#if showType == "0">
                    			<input type="text" class="col-sm-6 form-control" style="width: 385px;" id="address"/>
                    			<#elseif showType == "1">
                    			<label class="col-sm-6">${partner.address}</label>
                    			<#else>
                    			<input type="text" class="col-sm-6 form-control" value="${partner.address}" style="width: 385px;" id="address"/>
                    			</#if>
                    		</div>
                    		<div class="col-sm-12">
                    			<label class="col-sm-3"><#if showType != "1"><span class="requiredField">*</span></#if>Icon:</label>
                    			<div class="adspic-list col-sm-3">
									<ul>
										<li class="adspic-upload " id="partner_upload">
											<div class="upload-thumb">
												<#if showType == "0">
												<img src="${base}/dist/img/default_goods_image_240.gif"
													id="partner_img">
												<#else>
												<img src="${partner.image}"
													id="partner_img">
												</#if>
											</div>
											<#if showType != "1">
											<div class="upload-btn">
												<a href="javascript:void(0);"> <span><input
														type="file" hidefocus="true" size="1" class="input-file"
														name="file" id="partner"
														accept=".jpg,.png,.gif"></span>
													<p>
														<i class="fa fa-fw fa-upload" style="margin-right: 0px;"></i>upload
													</p>
												</a>
											</div>
											</#if>
										</li>
									</ul>
								</div>
                    		</div>
                    		<div class="col-sm-12" style="text-align: center;">
                    			<span class="col-sm-9" style="color: red;">suggest ratio 4:3 and size < 5M </span>
                    		</div>
                    		<div class="col-sm-12">
                    			<label class="col-sm-3"><#if showType != "1"><span class="requiredField">*</span></#if>Introduce:</label>
                    		</div>
                    		<div class="col-sm-12">
                    			<#if showType == "0">
                    			<textarea rows="10" class="form-control col-sm-12" id="introduce"></textarea>
                    			<#elseif showType == "1">
                    			<textarea rows="10" class="form-control col-sm-12" id="introduce" readonly="readonly">${partner.introduce}</textarea>
                    			<#else>
                    			<textarea rows="10" class="form-control col-sm-12" id="introduce">${partner.introduce}</textarea>
                    			</#if>
                    		</div>
                    		<div class="col-sm-6" style="margin-top: 20px;">
								<div class="col-sm-6 col-sm-offset-5">
									<#if showType == "0">
									<button class="btn  btn-default backPage" id="back" > Return</button>
									<button  class="btn  btn-primary" id="add" style="font-weight:100">Save</button>
									<#elseif showType == "1">
									<button class="btn  btn-default backPage" id="back" >  Return</button>
									<#else>
									<button class="btn  btn-default backPage" id="back" > Return</button>
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

<script type="text/javascript" src="${uiBase}js/pages/partner/partner_detail.js?v=${resourceVersion}"></script>
<script type="text/javascript"
	src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script>