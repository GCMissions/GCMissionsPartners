<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}vendor/zTree/css/zTreeStyle.css">
<link rel="stylesheet" href="${uiBase}vendor/treeTable/css/jquery.treetable.css">
<link rel="stylesheet" href="${uiBase}css/ztree_dropdown.css?v=${resourceVersion}">
<style type="text/css">
.productBrand {width:161px !important}
.categoryTreeWrap  .ztree li span.roots_close {visibility:hidden; width:1px;}

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
            <li> 商品列表</li>
             
          </ol>
          
          
         </section>
        
       
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary ">
                    
                                        
                    
                      <div class="form-horizontal search-group" >
                     	 <div class="box-body">
                            <div class="form-group ">
                            
                                	<label class="col-sm-2 control-label" >商品名称：</label>
                                 
                                    <input  id="productName" class="form-control col-sm-2" type="text">
                               
                                
                                <label class="col-sm-2 control-label" >商品条码：</label>
                                
                                   <input id="productCode" class="form-control col-sm-2" type="text">
                               
                                
                                     
                                 <label class="col-sm-1 control-label" >分类：</label>
                                 <div class="col-sm-3 categoryTreeWrap l">    
                                 		<input type="hidden" name="parentId" id="categoryId" value=""/>
	                                 		<input type="text" name="categoryName" id="cateId" class=" form-control text show" readonly value="请选择分类"/>
	                                 		<span class="inputArrow">&nbsp;</span>
	                                 		
	                                        <div class="menuContent" id="ztreeWraper"><ul  id="cateZtree"  class="ztree" ></ul></div>
                                </div>
                            </div>
                            <div class="form-group ">
                                    
                             	   
                            	   <label for="dtp_startDate" class="control-label col-sm-2">创建日期：</label>
                           
                                   <div class="col-sm-6 no-padding datetimeInputGroup" >
                                       <div class="input-group date datetimeInput  no-padding pull-left" data-date=""     data-date-format="yyyy-MM-dd"   >
                                            <input class="form-control startTimeSelection" size="16"  id="startDate"   type="text" id="" value=""  readonly placeholder="开始时间">
                                             <span class="add-on" ><i class="icon-remove"></i></span>
                                    		<span class="add-on"><i class="icon-calendar"></i></span>
                                        </div>
                                        
                                        <span class="pull-left textTo">至</span> 
                                        <div class="input-group date datetimeInput no-padding pull-left" data-date=""    data-date-format="yyyy-MM-dd"   >
                                            <input class="form-control endTimeSelection" id="endDate"  size="16" type="text" value="" readonly placeholder="结束时间">
                                             <span class="add-on" ><i class="icon-remove"></i></span>
                                   			 <span class="add-on"><i class="icon-calendar"></i></span>
                                        </div>
                                         
                                    </div>
                           		  
                            
                                        
                                    <label class="control-label col-sm-1"  for="type-select">品牌：</label>
                                    <div class="col-sm-3">  
                                          <select        class="selectpicker form-control productBrand " id="brandId" title="请选择品牌" > 
                                              <option value="">不限</option>
                                              <option value="0">无品牌</option>
                                              <#list listBrands as item>
                                              <option value="${item.brandId}">${item.brandName}</option>
                                              </#list>
                                          </select>
                                    </div> 
                            </div>
                            
                            <div class="form-group ">   
                                <label class="col-sm-2 control-label no-padding">   
									<button type="button" class="btn btn-primary " id="refreshRecord"><i class="fa fa-search"></i>开始搜索</button>                                
                                  </label>    
                                   <label class="col-sm-1 control-label pull-left">  
                                   <button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>  
                                   </label>    
                                <label class="col-sm-1  pull-left">   
                                 <a class="btn btn-default "  href="${urlPrefix}activity/addPage" ><i class="fa fa-plus"></i> 添加</a>
                                 
                                 
                                  <!--<a class="btn btn-default "  href="#" ><i class="fa fa-pencil"></i> 批量添加</a>-->
                                 </label>      
                                <label class="col-sm-2  pull-left">   
                                
                                   <!--<button class="btn btn-default openInTab "  data-href="${urlPrefix}product/trash/" data-text="商品回收站" ><i class="fa  fa-trash"></i> 回收站</button>-->                        
								</label>   
                            </div>                            
                          </div> 
                     </div> 
                         
                      
                   
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover dataList" >
                        <thead>
                           
                            <th>序号</th>
                            <th>商品条码</th>
                            <th>商品名称</th>
                            <th>单瓶商品名称</th>
                            <th>分类</th>
                            <th>品牌</th>
                            <th>瓶数</th>
                            <th>官方指导价</th>
                            <th>创建时间 </th> 
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
 
<#include "/footer.ftl" /> 
<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript" src="${uiBase}vendor/zTree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${uiBase}js/pages/product/product_list.js?v=${resourceVersion}"></script> 
<script>
 $(function() {
   
    productApp.init();
});
</script>
</body>
</html>
