<#assign headComponents = ["bootTable","bootDialog"] >
<#include "/header.ftl" />
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
            <li><img src="${uiBase}img/region.png"> Region</li>
          	<li><#if showType == "0">Add New Region<#else>Edit Region</#if></li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group" id="search-area" >
                    	<div class="box-body">
                    	<input type="hidden" id="regionId" value="${region.id}">
                    		<div class="col-sm-12">
                    			<label><#if showType == "0">Add New Region<#else>Edit Region</#if></label>
                    		</div>
                    		<div class="col-sm-12">
                    			<label class="col-sm-3"><span class="requiredField">*</span>Region Name:</label>
                    			<#if showType == "1">
                    			<input id="regionName" class="form-control col-sm-3" type="text" value="${region.regionName}"/>
                    			<#else>
                    			<input id="regionName" class="form-control col-sm-3" type="text"/>
                    			</#if>
                    		</div>
                    		<div class="col-sm-12" style="margin-top: 3%;">
                    			<label class="col-sm-3"><span class="requiredField">*</span>Color:</label>
                    			<#if showType == "1">
                    			<input id="regionColor" class="form-control col-sm-1" type="color" value="${region.color}"/>
                    			<#else>
                    			<input id="regionColor" class="form-control col-sm-1" type="color"/>
                    			</#if>
                    		</div>
                    		<div class="col-sm-12" style="margin-top: 3%;">
                    			<label class="col-sm-3"><span class="requiredField">*</span>Related Country:</label>
                    			<select class="col-sm-4 form-control" size="30" multiple="" style="width: 25%;" id="leftCountry">
                    				<#if showType == "0">
                    					<#list countries as item>
                    					<option value="${item.id}">${item.countryName}</option>
                    					</#list>
                    				<#else>
                    					<#list region.notHaveCountryList as item>
                    					<option value="${item.id}">${item.countryName}</option>
                    					</#list>
                    				</#if>
                    			</select>
                    			<div class="col-sm-2">
                    				<button  class="btn  btn-default" id="add" style="font-weight:150;margin-top: 120%;margin-left: 10%;text-align: center;">Add</button>
                    				<button  class="btn  btn-default" id="addAll" style="font-weight:100;margin-top: 40%;margin-left: 10%;text-align: center;">Add All</button>
                    				<button  class="btn  btn-default" id="delete" style="font-weight:100;margin-top: 40%;margin-left: 10%;text-align: center;">Delete</button>
                    				<button  class="btn  btn-default" id="deleteAll" style="font-weight:100;margin-top: 40%;margin-left: 10%;text-align: center;">Delete All</button>
                    			</div>
                    			<select class="col-sm-4 form-control" size="30" multiple="" style="width: 25%;" id="rightCountry">
                    				<#if showType == "1">
                    					<#list region.haveCountryList as item>
                    					<option value="${item.id}">${item.countryName}</option>
                    					</#list>
                    				</#if>
                    			</select>
                    		</div>
                    		<div class="col-sm-12" style="margin-top: 40px;">
								<div class="col-sm-6 col-sm-offset-5">
									<button class="btn  btn-default backPage" id="back" > Cancel</button>
									<#if showType == "0">
									<button  class="btn  btn-primary" id="save" style="font-weight:100">Save</button>
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

<script type="text/javascript" src="${uiBase}js/pages/region/region_detail.js?v=${resourceVersion}"></script>
