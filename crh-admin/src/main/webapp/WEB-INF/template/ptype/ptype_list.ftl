<#assign headComponents = ["bootTable","bootDialog"] >
<#include "/header.ftl" />

 <link rel="stylesheet" href="${uiBase}/vendor/iCheck/all.css">
 </head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
          <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i> 商品管理</li>
            <li class="active">商品类型管理</li>
          </ol>
        </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <div class="row ">
                            <div class="col-md-3 col-sm-4 pull-left">
                                <div class="pull-left">
                                  <button class="btn btn-default addItem"><i class="fa fa-plus"></i> 添加</button>
                                  <button type="button" class="btn btn-default reloadPage "><i class="fa  fa-refresh"></i> 刷新</button>
                                </div>
                            </div>
                        </div>
                      <!-- /.box-tools -->
                    </div><!-- /.box-header -->
                    
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                            <!-- <th field="attrId" width=70><div class="datagrid-header-check"><input type="checkbox"></div></th> -->
                            <th>序号</th>
                            <th>名称</th>
                            <th>品牌</th>
                            <th>属性</th>
                            <th>排序</th>
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
<style>
	label.brand_checkbox, label.attr_checkbox {
   		font-weight: 500;
   		min-width: 30%;
   		margin-top: 6px;
	}
	.fa-edit, .fa-trash { 
		cursor: pointer;	
	}
</style>
<script id="addEditTpl" type="text/html">
<form id="addEditForm" method="post" class="form-horizontal"　action="">
<div class="box-body form-horizontal addEditTpl">
    <div class="form-group row">
	  <label class="col-sm-3 control-label"><span class="requiredField">*</span>名称</label>
	  <div class="col-sm-9">
	    <input type="text" class="form-control" name="typeName" placeholder="请输入名称"
        	required data-msg-required="请输入名称" maxlength="10">
	  </div>
	</div>
	<div class="form-group row">
		<label class="col-sm-3 control-label">排序</label>
	  	<div class="col-sm-4">
	  		<input type="text" class="form-control" name="sort" data-rule-digits="true" data-msg-digits="请输入正确的排序" maxlength="4">
	  	</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-3 control-label">品牌</label>
		<div class="col-sm-8">
			{{each listBrands}}
				<label class="brand_checkbox"><input type="checkbox" class="flat-red" value="{{$value.brandId}}" name="brandIds"> {{$value.brandName}}</label>
			{{/each}}
		</div>
    </div>	
	<div class="form-group row">
		<label class="col-sm-3 control-label">属性</label>
		<div class="col-sm-8">
			{{each listAttrs}}
				<label class="attr_checkbox"><input type="checkbox" class="flat-red" value="{{$value.attrId}}" name="attrIds"> {{$value.attrName}}</label>
			{{/each}}
		</div>
    </div>
</div>
</form>
</script>
<script id="editTpl" type="text/html">
<form id="addEditForm" method="post" class="form-horizontal"　action="">
<div class="box-body form-horizontal addEditTpl">
    <div class="form-group row">
	  <label class="col-sm-3 control-label"><span class="requiredField">*</span>名称</label>
	  <div class="col-sm-9">
	  	<input type="hidden" name="typeId" value={{dto.typeId}} >
	    <input type="text" class="form-control" value="{{dto.typeName}}" name="typeName"
        	data-rule-required="true" data-msg-required="请输入名称" maxlength="10">
	  </div>
	</div>
	<div class="form-group row">
		<label class="col-sm-3 control-label">排序</label>
	  	<div class="col-sm-4">
	  		<input type="text" class="form-control" name="sort"  data-rule-digits="true" data-msg-digits="请输入正确的排序" value="{{dto.sort}}" maxlength="4">
	  	</div>
	</div>
	<div class="form-group row">
		<label class="col-sm-3 control-label">品牌</label>
		<div class="col-sm-8">
			{{each listBrands as brand brandIndex}}
				{{if dto.brandIds}}
					<label class="brand_checkbox"><input type="checkbox" class="flat-red" value="{{brand.brandId}}" name="brandIds"
						{{each dto.brandIds as bid bidIndex}}
							{{if bid == brand.brandId}} checked {{/if}}
						{{/each}}
					>{{brand.brandName}}</label>
				{{else}}
					<label class="brand_checkbox"><input type="checkbox" class="flat-red" value="{{brand.brandId}}" name="brandIds">{{brand.brandName}}</label>	
				{{/if}}
			{{/each}}
		</div>
    </div>	
	<div class="form-group row">
		<label class="col-sm-3 control-label">属性</label>
		<div class="col-sm-8">
			{{each listAttrs as attr}}
				{{if dto.attrIds}}
					<label class="attr_checkbox"><input type="checkbox" class="flat-red" value="{{attr.attrId}}" name="attrIds"
						{{each dto.attrIds as aid}}
							{{if aid == attr.attrId}} checked {{/if}}
						{{/each}}
					>{{attr.attrName}}</label>
				{{else}}
					<label class="attr_checkbox"><input type="checkbox" class="flat-red" value="{{attr.attrId}}" name="attrIds">{{attr.attrName}}</label>	
				{{/if}}
			{{/each}}
		</div>
    </div>
</div>
</form>
</script>
<script type="text/javascript" src="${uiBase}js/pages/ptype/ptype.js?v=${resourceVersion}"></script>
</html>