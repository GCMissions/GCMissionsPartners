<#assign headComponents = ["bootTable","bootDialog"] >
<#include "/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/pages/slides/slides_list.css?v=${resourceVersion}">
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
            <li><img src="${uiBase}img/partner-slides.png"> Mission Value</li>
          	<li>Mission Management</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group" id="search-area" >
                    	<div class="box-body">
                    		<div class="col-sm-12" style = "height:50px; width = "100%">
                    			<div class="col-sm-3"><lable>The Current Model:</lable></div>
                    			<div class="col-sm-3">
                    			<select class="col-sm-10 form-control" id="model" style="width: 200px; height:80%;line-height:50px;">
                    				<#list model as item>
                    				<option value="${item.id}" <#if item.display == "1">selected=selected</#if>>${item.name}</option>
                    				</#list>
                    			</select>
                    			</div>
                    		    <div class="col-sm-3"><button class="btn  btn-primary" id="change" >  Change model as the current</button></div>
                    		</div>
                    	</div>
                    </div>
                    
                    <!-- textAreal -->
                    <div id = "textAreal" class="box-body">
                   		 <input  class="col-sm-6 form-control" type="hidden" id="sal"/>
	                    <div class="col-md-12">
	                		<label class="col-sm-1"><span class="requiredField">*</span>Title:</label>
	                		<input type="text" class="col-sm-6 form-control"style="width: 385px;" id="title"/>
	            		</div>
	            		<div class="col-md-12">
	                		<label class="col-sm-1"><span class="requiredField">*</span>Content:</label>
	            		</div>
	                    <div  class="box-body">
	                   		<textarea id="TextArea1" cols="20" rows="2" class="ckeditor" style = "margin-top:100px"></textarea>
	                    </div>
	                    <div class="col-sm-6">
									<button  class="btn  btn-primary" id="editbtn" style="font-weight:100">Edit</button>
									<button  class="btn  btn-primary" id="savebtn" style="font-weight:100;">Save</button>
									<button  class="btn  btn-primary" id="resetbtn" style="font-weight:100">Reset</button>
						</div>
                    </div>
                    
                    <!-- table  -->
                    <div class="box-body" id = "missionTable">
                    <table id="dataList" class="table table-bordered table-hover" >
                      <thead>
                        	<!-- <th field="brand_id" width=70><div class="datagrid-header-check"><input type="checkbox"></div></th> -->
                        	<th>index</th>
                            <th>title</th>
                            <th>content</th>
                            <th>Action</th>
                        </thead>
                        <tbody>
                        </tbody>
                      </table>
                    </div>
                    
                    <div></div>
                </div>
             </div>
        </div>
        <div class="clearfix"></div>
        
      </div>
   </div>


</body>
<#include "/footer.ftl" />
<script type="text/javascript" src="${uiBase}vendor/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
    CKEDITOR.replace('TextArea1');
</script>
<script id="editMission" type="text/html">
<form id="addEditForm" method="post" class="form-horizontal">
<div class="box-body form-horizontal addEditTpl">

	<div class="form-group row">
	  <label class="col-sm-4 control-label" ><span class="requiredField">*</span>Title</label>
	  <div class="col-sm-8">
		<input type="hidden" name="id" value={{id}}>
	    <input type="text" class="form-control" name="title" placeholder="Please enter your title"
        	data-rule-required="true" data-msg-required="Please enter your title" value="{{title}}" >
	  </div>
	</div>
	<div class="form-group row">
		<label class="col-sm-4 control-label"><span class="requiredField">*</span>Content</label>
	  	<div class="col-sm-8">
	  		<textarea rows="10" data-rule-required="true" class="form-control col-sm-12" name = "content" value="{{content}}">{{content}}</textarea>
	  	</div>
	</div>
</div>
</form>
</script>
<script type="text/javascript" src="${uiBase}js/pages/mission/mission_list.js?v=${resourceVersion}"></script>