<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 


</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
    
         <section class="content-header">
         
          <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i> 商品管理</li>
            <li> 商品属性管理</li>
          </ol>
          
          
         </section>
         
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <div class="row ">
                            <div class="col-md-12 col-sm-12 pull-left">
                                <div class="pull-left">
                                  
                                  <button class="btn btn-default addItem"><i class="fa fa-plus"></i> 添加</button>
                                 
                                  <button type="button" class="btn btn-default" id="refreshRecord"><i class="fa  fa-refresh"></i> 刷新</button>
                                </div>
                            </div>
                            
                            
                            
                        </div>
                        
                       
                        <!-- /.box-tools -->
                    </div><!-- /.box-header -->
                    
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                            
                            <th>序号</th>
                            <th>名称</th>
                             <th>排序</th>
                             <th>可搜索</th>
                            <th>可选项</th>
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
<script src="${uiBase}/vendor/input-mask/jquery.inputmask.js"></script>
<script type="text/javascript" src="${uiBase}/js/pages/attribute/attribute.js?v=${resourceVersion}"></script>
<script id="addEditTpl" type="text/html">
<form id="addEditForm" method="post" class="form-horizontal"　action=""  >
<div class="box-body form-horizontal addEditTpl">

    <div class="form-group row">
	  <label class="col-sm-3 control-label" ><span class="requiredField">*</span>名称：</label>
	  <div class="col-sm-7">
	    <input type="text" class="form-control" name="attrName" placeholder="请输入名称"
        data-rule-required="true" data-msg-required="请输入名称"  value="{{attrName}}" maxlength=10
        >
        <input type="hidden" name="attrId" value="{{attrId}}">
	  </div>
	</div>
	<div class="form-group row">
	  <label class="col-sm-3 control-label" >排序：</label>
	  <div class="col-sm-7">
	    <input type="text" class="form-control" name="sort" placeholder="请输入排序"
         value="{{sort}}" maxlength=4
        >

	  </div>
	</div>
	<div class="form-group row">
		<label class="col-sm-3 control-label">可搜索:</label>
		<div class="col-sm-5">
				<label class="radio-inline">
					<input type="radio" name="searchable" value="1" {{if searchable}}checked{{/if}}>是
        		</label>
        		<label class="radio-inline">
        			<input type="radio" name="searchable" value="0" {{if !searchable}}checked{{/if}}>否
       	 		</label>
		</div>
	</div>
    <div class="form-group row">
    	<label class=" col-sm-3 control-label" style="text-align:right;">可选项：</label>
    	<div class="btn-group col-sm-3">
			<button class="btn btn-sm btn-success addSubItem"  type="button">
				<i class="fa fa-plus"> 添加选项</i>
			</button>
		</div>
    </div>
    <div class="form-group row ">
   		<div class="col-sm-12" style="max-height:400px;overflow-y:scroll">
	    	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table table-striped form-inline" id="subItemList">
				<thead>
				   <tr class="active">
					<th  scope="row"><span class="requiredField">*</span>选项值</th>
					<th >操作</th>
				   </tr>
			    </thead>		
				<tbody>
					{{#subAttrHtml}}
				</tbody>
			</table>
		</div>
    </div>
	
</div>
</form>
</script> 
<script id="subItemTpl" type="text/html">
    <tr scope="row" >
        <td>
            <input type="text" value="{{valueInfo}}" name="list.valueInfo_{{avSeq}}" class="form-control col-sm-2" maxlength=10   data-rule-valueunique='true'  data-rule-required="true" data-msg-required="请输入属性名" {{if valueInfo}} readonly {{/if}}>
            <input type="hidden" name="list.attrValueId_{{avSeq}}" value="{{attrValueId}}">
        </td>
                 
        <td>
      		<p class="form-control-static">
            <a href="javascript:;" title="删除"  class="removeSubItem"><i class="fa fa-times " style="font-size:20px"></i></a>
         	</p>
        </td>
    </tr>
</script>


  
</html>
