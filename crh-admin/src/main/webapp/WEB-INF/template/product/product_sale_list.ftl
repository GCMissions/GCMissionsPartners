<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
<link rel="stylesheet"	href="${uiBase}vendor/treeTable/css/jquery.treetable.css">
<link rel="stylesheet"	href="${uiBase}vendor/treeTable/css/jquery.treetable.theme.default.css">
<link rel="stylesheet" href="${uiBase}css/pages/product.css?v=${resourceVersion}">
<style type="text/css">
.productBrand {max-width:161px !important}
.control{width:120px}
.productName{width:320px}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      
      <div class="content-wrapper page-content-wrapper">
      
        <section class="content-header">
         
          <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i> 商品管理</li>
            <li> 商品上下架</li>
            
          </ol>
          
          
         </section>
        
       
         
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                   
                    
                    <div class="form-horizontal search-group" >
                     	 <div class="box-body">
                    
                  			 <div class="form-group ">
                  				 <div class="col-sm-5">  
                            		<select class="selectpicker form-control productBrand " id="firstCate" title="一级商品品类"> 
                                    	<option value="0">不限</option>
                                        <#list firstCates as item>
                                        		<option value="${item.cateId}">${item.cateName}</option>
                                        </#list>
                                     </select>
                                     <span class="textTo">——</span>
                                     <select class="selectpicker form-control productBrand " id="secondCate" title="二级商品品类">
                                     	<!-- <option value="">不限</option>  -->

                                     </select>
                                 </div>
                                 <div class="col-sm-3">
                                 	<select class="selectpicker form-control productBrand " id="orgId" title="服务商"> 
                                    	<option value="">不限</option>
                                        <#list listOrgs as item>
                                        <option value="${item.orgId}">${item.orgName}</option>
                                        </#list>
                                     </select>
                                 </div>
                                 <div class="col-sm-3">
                                    <input  id="productName" class="form-control col-sm-1" type="text" placeholder="商品名称">
                                </div>  
                  			 </div>
                  			 <div class="form-group ">   
                  			    <div   class="col-sm-3"  >      
                                <label> 
                                <input type="checkbox" id ="saleStatusShief">已上架</label> 
                                <label style="padding-left: 50px;"> 
                                <input type="checkbox"  id ="saleStatusUnShief">未上架</label>
                                <input type="hidden" id ="saleStatus">
                                </div>
                                <div>  
                                <label class="col-sm-1 control-label">   
									<button type="button" class="btn btn-primary  searchBtn" id="refreshRecord"><i class="fa fa-search"></i> 查询</button>                                
                                </label>
                                 <label class="col-sm-1 control-label pull-left">  
                                   <button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>  
                                </label>
                                     </div>                          
                            </div>                            
                          </div> 
                     </div> 
                     
                           
                        
                        
                        
                        
                        
                    
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover dataList" >
                        <thead>
                            <!--<th field="brand_id" width=70 ><div class="datagrid-header-check"><input type="checkbox"></div></th>-->
                            <th>商品编号</th>
                            <th>商品品类</th>
                            <th>服务商</th>
                            
                            <th>商品名称</th>
                            <th>价格（元）  </th>
                            <th>上架状态  </th>
                            <th>操作</th>
                        </thead>
                        <tbody>
                        	
                        	   
                        </tbody>
                      </table>
                    </div><!-- /.box-body -->
                    
                   
             </div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->

<#include "/footer.ftl" /> 
<script src="${uiBase}vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>


<!-- treeTable 3.2.0 -->
<script src="${uiBase}vendor/treeTable/js/jquery.treetable.js"></script>
<script type="text/javascript" src="${uiBase}js/pages/product/product_sale.js?v=${resourceVersion}"></script> 
<script>
 $(function() {
   
    productSaleApp.init();
});
</script>
</body>
  
</html>