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
            <li><img src="${uiBase}img/partner.png"> Mission</li>
          	<li>Edit Mission</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group" id="search-area" >
                    	<div class="box-body">
                    	<input type="hidden" id="missionId" value="${mission.id}">
                    		<div class="col-sm-12">
                    			<label>Edit Mission</label>
                    		</div>
                    		<div class="col-sm-12">
                    			<label class="col-sm-3"><span class="requiredField">*</span>Title:</label>
                    			<input type="text" class="col-sm-6 form-control" value="${mission.title}" style="width: 385px;" id="title"/>
                    		</div>
                    		
                    		<div class="col-sm-12">
                    			<label class="col-sm-3"><span class="requiredField">*</span>Contenet:</label>
                    			<textarea rows="10" class="form-control col-sm-12" id="content">${mission.content}</textarea>
                    		</div>
                    		<div class="col-sm-6" style="margin-top: 20px;">
								<div class="col-sm-6 col-sm-offset-5">
									<button class="btn  btn-default backPage" id="back" >  Cancel</button>
									<button  class="btn  btn-primary" id="edit" style="font-weight:100">Save</button>
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

<script type="text/javascript" src="${uiBase}js/pages/mission/mission_detail.js?v=${resourceVersion}"></script>
<script type="text/javascript"
	src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script>