<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<style>
.file-input	.file-actions, .kv-upload-progress{
   		display: none;
}
.file-input .file-preview .file-preview-image{
	width:228px;
	height:160px;
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
            <li><i class="fa fa-dashboard"></i> 商品管理</li>
            <li> 商品品牌管理</li>
             
          </ol>
        </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                    	 <div class="form-group row">
                    	 	<div class="col-md-12">
                            	<div class="col-sm-4">
	                            	<label  control-label" >品牌名称：</label>
	                                <input  id="brandName" class="form-control " type="text">
                         		</div>
                         	</div>
                         </div>
                    	 <div class="row ">
                            <div class="col-md-6 col-sm-6 pull-left">
                                <div class="pull-left">
                                  <button type="button" class="btn btn-primary" id="search"><i class="fa fa-search"></i>开始搜索</button> 
                                  <button class="btn btn-default addItem"><i class="fa fa-plus"></i> 添加</button>
                                  <button type="button" class="btn btn-default reloadPage " id="refreshRecord"><i class="fa  fa-refresh"></i> 刷新</button>
                                </div>
                            </div>
                         </div>
                        
                        <!-- /.box-tools -->
                    </div><!-- /.box-header -->
                    
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                            <th>序号</th>
                            <th>品牌名称</th>
                            <th>Logo</th>
                            <th>排序</th>
                            <th>创建时间  </th>
                            <th>操作</th>
                        </thead>
                        <tbody>
                        </tbody>
                      </table>
                    </div><!-- /.box-body -->                   
                </div>
             </div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
  
    
</body>
<#include "/footer.ftl" />
<script type="text/javascript" src="${uiBase}/js/pages/brand/brand.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}/vendor/bootstrap-fileinput/js/fileinput.js"></script>
<script type="text/javascript" src="${uiBase}/vendor/bootstrap-fileinput/js/fileinput_locale_zh.js"></script>

<script id="addEditTpl" type="text/html">
<form id="addEditForm" method="post" class="form-horizontal"　action=""  >
<div class="box-body form-horizontal">

		<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;"><span class="requiredField">*</span>品牌名称</label>
	  <div class="col-sm-6">
	    <input type="text" class="form-control " data-rule-required="true"  maxlength="10" id="brandName" placeholder="请输入名称">
	  </div>
	</div>

	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">排序</label>
	  <div class="col-sm-6">
		<input type="text" class="form-control" name="sort"  id="sort" data-rule-digits="true" data-msg-digits="请输入数字" maxlength="4" value="">
	  </div>
	</div>

	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">品牌LOGO</label>

		 <div class="col-sm-6">
               <input id="file-0a" type="file" name="file" >
         </div>
	</div>

	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">品牌简介</label>
	  <div class="col-sm-9">
	     <textarea id="description" name="description" class="ckeditor" data-editor-type="basic" rows="4" cols="50" maxlength="100"></textarea>
	  </div>
	</div>
</div>
</form>
</script>
<script id="editTpl" type="text/html">
<form id="addEditForm" method="post" class="form-horizontal"　action=""  >
<div class="box-body form-horizontal">
	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;"><span class="requiredField">*</span>品牌名称</label>
	  <div class="col-sm-6">
	 	 <input type="hidden" name="brandId" value="{{brandDto.brandId}}" >
	    <input type="text" class="form-control" id="brandName" data-rule-required="true"  maxlength="10" placeholder="请输入名称" value="{{brandDto.brandName}}">
	  </div>
	</div>

	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">排序</label>
	  <div class="col-sm-6">
	    <input type="text" class="form-control" name="sort" id="sort" data-rule-digits="true" data-msg-digits="请输入数字" maxlength="4" value="{{brandDto.sort}}">
	  </div>
	</div>

	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">品牌LOGO</label>
	    <div class="col-sm-6">		    
		  	<div class="col-sm-12">
		  	{{if brandDto.logo }}
	        	<input id="file-0a" type="file" name="file"  value="{{brandDto.logo}}">
	        {{else}}
	        	<input id="file-0a" type="file" name="file"  value="">
	        {{/if}}
	     	</div>      
		</div>
	</div>

	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">品牌简介</label>
	  <div class="col-sm-9">
	     <textarea id="description" name="description" class="ckeditor" data-editor-type="basic" rows="4" cols="50" maxlength="100">{{brandDto.description}}</textarea>
	  </div>
	</div>
</div>
</form>
</script>
 
</html>
