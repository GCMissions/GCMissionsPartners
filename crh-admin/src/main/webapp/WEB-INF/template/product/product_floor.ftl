<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet"	href="${uiBase}/vendor/treeTable/css/jquery.treetable.css">
<link rel="stylesheet"	href="${uiBase}/vendor/treeTable/css/jquery.treetable.theme.default.css">

<style type="text/css">
/*
样式设计有缺陷,
@TODO need refactor 
*/
.goodspic-list ul li
{
	height: 210px
}
.goodspic-list ul li .upload-title {
	position : absolute;
	top : 183px;
	left : 29px
}
/*保留*/
.floorGoodsList tbody tr {
	cursor: move;
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
            <li><i class="fa fa-dashboard"></i>商品管理</li>
            <li>楼层商品管理</li>
          </ol>
          
          
         </section>
      
         <section class="content">
         	<div class=row>
         		<div class="col-md-4 col-xs-4" style="padding-right:10px">   
	         		
         			<div class="box box-solid">
	         			<div class="box-header with-border">
		                  <h3 class="box-title">选择要设置的城市! </h3>
		                </div>
         				<div class="box-body no-padding treeTable" style="max-height:700px;overflow-y:scroll">
         					<!-- table -->
         					<table class='tree_table'　>
							<thead class='borderRow'>
							<th  class='pull-left no-border'>地区</th>
							<th class="no-border"> </th>
							</thead>
							<tbody>
								
							</tbody>
							</table>
         					<!-- /table -->
         				</div>
         			</div>
         		</div>
         		<div class="col-md-8 col-xs-8" style="padding-left:0" >
         			<div class="box box-solid">
	         			<div class="box box-solid" id="cityFloorContainer">
		                
                        </div>   
		             </div>   
         		</div>
         	</div>
			
         </section>
        
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
      
</body>
<#include "/footer.ftl" />
<script id="floorTpl" type="text/html">
    <div class="box-header with-border">
      <h3 class="box-title">设置 <span>{{cityName}}</span> 楼层商品! </h3>
    </div>
    <div class="box-body no-padding treeTable">
        <div class="tabs-container">
            <div class="nav-center">
                <ul class="nav nav-tabs">
                    <li class=""><a  href="javascript:;" aria-expanded="false">热销商品</a></li>
                    <li class=""><a  href="javascript:;" aria-expanded="true">核心产品</a>
					<li class=""><a  href="javascript:;" aria-expanded="true">高端产品</a>
                    <li class=""><a  href="javascript:;" aria-expanded="true">区域产品</a>
                    <li class=""><a  href="javascript:;" aria-expanded="true">当地特供</a>
                    </li>
                </ul>
            </div>
            <div class="tab-content">
                <div id="tab-1" class="tab-pane active">
                    <div class="panel-body panel-body1">
						<div class="row " id="adPositions">
							
							<div class="form-goods-pic col-md-12">
								<div class="container">
								
								<div class="goodspic-list">
									<ul>
									</ul>
								</div>
								</div>
							</div>
	
						</div>
						<div class=row>
						<div class="col-md-12">
							<button type="button" class="btn btn-primary addGoods"><i class="fa fa-plus"></i> 添加商品</button>
							<div class="addImageHint"></div>
						</div>
						
						</div>
						<div class=row>
                         <table  class="table table-bordered table-hover floorGoodsList" >
                            <thead>
                                <th>序号位</th>
                                <th>商品条码</th>
                                <th>商品名称</th>
                                <th>品牌</th>
                                <th>类别</th>
                                <th>上下架</th>
                                <th>操作</th>
                            </thead>
                            <tbody>
                            </tbody>
                          </table>
						</div>
						<div class="row">
						   <div class="col-sm-12 text-center">
							
							<button class="btn btn-primary saveFloorData"   type="button"  >保存设置</button>
						   
						   </div>
						</div>
			
                    </div>
                </div>
               
            </div>
        </div>
        
    </div>
</script> 
<script id="picItemTpl" type="text/html">
<li class="goodspic-upload" id="upload_{{position}}" data-position="{{position}}">
    <div class="upload-thumb">
        <img src="{{showImageUrl}}"  id="img_{{position}}">
        <input type="hidden" name="picUrl[{{id}}]" value="{{imageUrl}}"  data-origin="{{imageUrl}}"  id="name_{{position}}" data-position="{{position}}">
    </div>

    <div class="upload-setDefault">
    	<div class="form-group">
    		<a href="{{viewImageUrl}}" target="_blank" class="viewPicInTab" id="viewPicInTab_{{position}}">查看大图</a>
    	</div>
    </div>
    <div class="upload-btn">
	    <a href="javascript:void(0);">
		    <span><input type="file" hidefocus="true" size="1" class="input-file" name="file" id="{{position}}"></span>
		    <p><i class="fa fa-fw fa-upload"></i>上传</p>
	    </a>
	</div>
	
	<div class="remove-btn">
	    <a href="javascript:void(0);">
		    <p><i class="fa fa-fw fa-times"></i>删除</p>
	    </a>
	</div>
	{{if position==2 }}
	<div class="upload-title">上传团购图片</div>
	{{else}}
    <div class="upload-title">上传{{position}}号位图片</div>
	{{/if}}
</li>
   
	
</script>   
<!-- treeTable 3.2.0 -->
<script src="${uiBase}/vendor/treeTable/js/jquery.treetable.js"></script>
<script type="text/javascript" src="${uiBase}/js/pages/product/product_floor.js"></script>
<script type="text/javascript" src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script> 
<script>
 $(function() {
   
    productFloorApp.init();
});
  </script>


  
</html>
