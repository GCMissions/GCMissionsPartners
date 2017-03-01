<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}vendor/zTree/css/zTreeStyle.css">
<link rel="stylesheet" href="${uiBase}vendor/treeTable/css/jquery.treetable.css">
<link rel="stylesheet" href="${uiBase}css/pages/category.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}css/ztree_dropdown.css?v=${resourceVersion}">
<style>
.treetable thead tr{
	padding: 8px;
  	line-height: 24px;
  	font-weight: bold;
}
.removeItem{
	margin-left:3px;
}
.form-control {
  display: inline-block;
  width: 72%;
}
ul.ztree{
	width:188px;
	height:220px;
}
.inputArrow{
  top: 35%;
  left: 185px;
}
#treeWrap {
	position:relative
}
.menuContent{
	position : absolute;
	z-index:3;
	top : 34px;
	display: none
}
.goodspic-upload .upload-btn{
	left:40px;
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
            <li> 商品品类管理</li>             
          </ol>
        </section>
       
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <div class="row ">
                            <div class="col-md-6 col-sm-6 pull-left">
                                <div class="pull-left">
                                  <button class="btn btn-default addItem"><i class="fa fa-pencil"></i> 添加</button>
                                  <button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button> 
                                  <!-- <button type="button" class="btn btn-danger removeItem" disabled id="removeRecord"><i class="fa fa-times"></i> 删除</button>
                                  <button type="button" class="btn btn-default" id="refreshRecord"><i class="fa  fa-refresh"></i> 刷新</button> -->
                                </div>
                            </div>
                        </div>
                          <!-- /.box-tools -->
                    </div><!-- /.box-header -->
                    
                    <div class="box-body">
						  <div class="col-md-12 fixed-table-container">							
							   <table class="table linetable table-bordered table-hover" id="treeMenu">  

							   </table>   
							
						  </div>
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

<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.excheck.js"></script>

<script type="text/javascript" src="${uiBase}vendor/ajaxfileupload/ajaxfileupload.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}vendor/treeTable/js/jquery.treetable.js"></script>
<script type="text/javascript" src="${uiBase}vendor/jQueryUI/jquery-ui.js"></script>
<script type="text/javascript" src="${uiBase}js/pages/category/category.js?v=${resourceVersion}"></script>

<script id="addEditTpl" type="text/html">
<form id="addEditForm" method="post" class="form-horizontal"　action="">
<div class="box-body form-horizontal">

	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;"><span class="requiredField">*</span>名称:</label>
	  <div class="col-sm-6">
	    <input type="text" class="form-control" id="cateName" name="cateName" data-rule-required="true" maxlength="10">
	  </div>
	</div>
    
	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">上级品类:</label>
	  <div class="col-sm-6" id="treeWrap" class="menuContent">
		<input type="hidden" name="parentId" id="categoryId" value=""/>
		<input type="text" class="form-control" id="catpid" value="顶级品类" readOnly>
		<span class="inputArrow">&nbsp;</span>
		<div class="menuContent" id="categoryTreeUl">
			<ul id="treeDemo" class="ztree"></ul>
		</div>			
	  </div>
	</div>

	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">排序:</label>
	  <div class="col-sm-6">
	    <input type="text" class="form-control" id="sort" data-rule-digits="true" data-msg-digits="请输入正确的排序" maxlength="4">
	  </div>
	</div>

	<div class="form-group row">
		<label class="col-sm-3 control-label">
			<span class="requiredField">*</span>品类图片:
		</label>
		<div class="col-sm-6 title">
		</div>
	</div>
	<div class="form-group row"><#include "/category/cate_image.ftl" /></div>
</div>
</form>
</script>
<script id="editTpl" type="text/html">
<form id="addEditForm" method="post" class="form-horizontal"　action="">
<div class="box-body form-horizontal">

	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;"><span class="requiredField">*</span>名称:</label>
	  <div class="col-sm-6">
		<input type="hidden" name="catId" id="catId" value="{{cateDto.cateId}}"/>
	    <input type="text" class="form-control" id="cateName" name="cateName" data-rule-required="true" maxlength="10" value="{{cateDto.cateName}}">
	  </div>
	</div>
    
	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">上级品类:</label>
	  <div class="col-sm-6" id="treeWrap" class="menuContent">
		<input type="hidden" name="parentId" id="categoryId" value="{{cateDto.parentId}}"/>
		<input type="text" class="form-control" id="catpid" value="{{parentName}}" readOnly>
		<span class="inputArrow">&nbsp;</span>
		<div class="menuContent" id="categoryTreeUl">
			<ul id="treeDemo" class="ztree"></ul>
		</div>			
	  </div>
	</div>

	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">排序:</label>
	  <div class="col-sm-6">
	    <input type="text" class="form-control" id="sort" value="{{cateDto.sort}}" data-rule-digits="true" data-msg-digits="请输入正确的排序" maxlength="4">
	  </div>
	</div>

	<div class="form-group row">
		<label class="col-sm-3 control-label">
			<span class="requiredField">*</span>品类图片:
		</label>
		<div class="col-sm-6 title">
		</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-2 control-label"></label>
		<div class="form-goods-pic">
			<div class="container">
				<div class="goodspic-list">
					<ul>
						<li class="goodspic-upload" id="upload_0">
							<div class="upload-thumb">
								<img src="{{cateDto.image}}" id="img_0"> 
								<input type="hidden" name="picUrl0" value="{{cateDto.image}}" id="name0">
								<input type="hidden" name="picKey0" value="{{cateDto.imageKey}}" id="key0">
							</div>
							<div class="upload-btn">
								<a href="javascript:void(0);"> <span><input type="file"
										hidefocus="true" size="1" class="input-file" name="file"
										id="0"></span>
									<p>
										<i class="fa fa-fw fa-upload"></i>上传
									</p>
								</a>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
</form>
</script>
<script id="viewTpl" type="text/html">
<form id="addEditForm" method="post" class="form-horizontal"　action="">
<div class="box-body form-horizontal">

	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;"><span class="requiredField">*</span>名称:</label>
	  <div class="col-sm-6">
		<p class="form-control-static">{{cateDto.cateName}}</p>
	  </div>
	</div>
    
	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">上级品类:</label>
	  <div class="col-sm-6" id="treeWrap" class="menuContent">
		<p class="form-control-static">{{parentName}}</p>
	  </div>
	</div>

	<div class="form-group row">
	  <label class="col-sm-3 control-label" style="text-align:right;">排序:</label>
	  <div class="col-sm-6">
		<p class="form-control-static">{{cateDto.sort}}</p>
	  </div>
	</div>

	<div class="form-group row">
		<label class="col-sm-3 control-label">
			<span class="requiredField">*</span>品类图片:
		</label>
		<div class="col-sm-6 title">
		</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-3 control-label"></label>
		<div class="form-goods-pic">
			<div class="container">
				<div class="goodspic-list">
					<a href="{{cateDto.image}}" target="_blank" title="点击查看大图">
        				<img src="{{cateDto.image}}"  id="img_0" style="heigth:180px;width:140px;">
    				</a>
				</div>
			</div>
		</div>
	</div>
</div>
</form>
</script>

</html>
