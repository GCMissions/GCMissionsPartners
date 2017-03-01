<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/product.css?v=${resourceVersion}">
<style>
#relatedGoods{
	width: 40% !important;
}
.addItem{
	vertical-align: top !important;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
        <div class="content-wrapper page-content-wrapper">
         	<section class="content-header">
         
	          <ol class="breadcrumb">
	            <li><i class="fa fa-dashboard"></i>商品管理</li>
	            <li class="active">
				 <#if isReview>  
				 	查看商品     
				 <#elseif   productDto.productId>
				 	编辑商品
				 <#else>     
				       添加商品
				  </#if>
	            </li>
	          </ol>
          
          
            </section>
            
            
           
            <div class="row pad">
                <div class="col-md-12">
                  <!-- Custom Tabs -->
                   <form role="form" class="form-horizontal" method=post id="mainForm">
                  <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                      <li class="active"><a href="#tab_1" data-toggle="tab">基本信息</a></li>
                      <li><a href="#tab_2" data-toggle="tab">商品详情</a></li>
                    </ul>
                    <div class="tab-content">
                      <div class="tab-pane active" id="tab_1">
                          <div class="box-body">
                           	  <input type="hidden" name="cateId" value="${cateInfo.pCategoryEntity.cateId}">
                              <!-- text input -->
                              <div class="form-group">
                                <label class="col-sm-2 control-label">商品分类:</label>
                                <div class="col-sm-6"><p class="form-control-static">${cateInfo.pCategoryEntity.cateName}</p> </div>
                              </div>
                              <div class="form-group ">
                                  <label class="col-sm-2 control-label"><span class="requiredField">*</span>商品名称:</label>
                                  <div class="col-sm-5">
                                    <#if isReview>
                                    <p class="form-control-static">
                                    ${productDto.productName}
                                    </p>
                                    <#else>                                  
                                    <input type="text" class="form-control" name="productName"  maxlength=50 value="${productDto.productName}">
                                    </#if>
                                  </div>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              <div class="form-group ">
                                <label class="col-sm-2 control-label"><span class="requiredField">*</span>商品条码:</label>
                                  <div class="col-sm-5">
                                    <#if isReview>
                                    <p class="form-control-static">
                                    ${productDto.productCode}
                                    </p>
                                    <#else>            
                                    <input type="text" class="form-control" maxlength=20 name="productCode" value="${productDto.productCode}">
                                    </#if>
                                  </div>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              <div class="form-group  ">
                                <label class="col-sm-2 control-label"> 品牌:</label>
                                  <div class="col-sm-5">
                                  <#if isReview>
                                  <p class="form-control-static">
                                  ${productDto.brandName}
                                  </p>
                                  <#else> 
                                   
                                   <select name="brandId" class="form-control selectpicker">
                                     <option value="0">不限</option> 
                                   	 <#list cateInfo.listBrands as item>
                                   	 <option value="${item.brandId}" <#if productDto.brandId == item.brandId>selected</#if> >${item.brandName}</option> 
                                     </#list>
                                   </select>
                                   </#if>
                                  </div>
                              </div>
                              <div class="form-group  ">
                                <label class="col-sm-2 control-label"> 属性:</label>
                                  <div class="col-sm-6 form-horizonta attrsList">
                                  <#list cateInfo.listAttr as item>
                                    <div class="form-group no-margin">
                                     
                               		    <label  class="control-label"> ${item.attrName}:</label>
				                        <div>
				                        <#list item.list as attr >
				                        <label class="radio-inline  ">
				                          <input type="radio" name="attr[${item.attrId}]" data-valueInfo="${attr.valueInfo}"  data-attrId="${item.attrId}"  data-attrName="${item.attrName}" class="" value="${attr.attrValueId}" >
				                         ${attr.valueInfo}
				                        </label>
				                       	</#list>
				                        </div>
				                     
				                    </div>
				                   </#list>  
				                    
				                    
                                  </div>
                              </div>
                              <div class="form-group  ">
                                <label class="col-sm-2 control-label">商品描述：</label>
                                <div class="col-sm-5">
                                <#if isReview>
		                          	<p class="form-control-static">
		                            ${productDto.note}
		                             </p>
                             	 <#else>
                                  <!--<script id="ueEditor" name="content" type="text/plain" data-ue-height=200 style="width:100%;"></script>-->
                                   <textarea name="note"  cols=120 rows=2 class="form-control" maxlength="50" style="width:400px">${productDto.note}</textarea>
                                  
                             	 </#if>
                                </div>
                              </div>
                               <div class="form-group  ">
                                <label class="col-sm-2 control-label">活动描述：</label>
                                <div class="col-sm-5">
                                <#if isReview>
		                          	<p class="form-control-static">
		                            ${productDto.promotion}
		                             </p>
                             	 <#else>
                                  
                                   <textarea name="promotion"  cols=120 rows=2 class="form-control" maxlength="50" style="width:400px">${productDto.promotion}</textarea>
                                  
                             	 </#if>
                                </div>
                              </div>
                              
                              <div class="form-group  ">
                                <label class="col-sm-2 control-label"><span class="requiredField">*</span>单瓶商品：</label>
                                <div class="col-sm-5">
                                <#if isReview>
                                <p class="form-control-static" data-id="${productDto.goodId}">
                                ${productDto.goodName}
                                </p>
                                <#else>
                                <input type="hidden" name="goodId" value="${productDto.goodId}">
                                <input id="relatedGoods" type="text" class="form-control" name="goods" readonly value="${productDto.goodName}" data-id="${productDto.goodId}">
                                <a class="btn btn-default addItem"><i class="fa fa-plus"></i> 选择</a>
                                </#if>
                                </div>
                              </div>
                              
                              <div class="form-group  ">
                                <label class="col-sm-2 control-label"><span class="requiredField">*</span>瓶数：</label>
                                <div class="col-sm-5">
                                <#if isReview>
                                <p class="form-control-static">
                                ${productDto.specNum}
                                </p>
                                <#else> 
                                <input type="text" class="form-control" name="weight"  maxlength=4 value="${productDto.specNum}" >
                                </#if>
                                </div>
                              </div>
                              <div class="form-group  ">
                                <label class="col-sm-2 control-label">官方商品链接：</label>
                                <div class="col-sm-5">
                                <#if isReview>
                                <p class="form-control-static">
                                ${productDto.url}
                                </p>
                                <#else> 
                                <input type="text" class="form-control" name="productUrl"  maxlength=100 value="${productDto.url}" placeholder="http://">
                                </#if>
                                </div>
                              </div>
                               <div class="form-group  ">
                                <label class="col-sm-2 control-label">生产日期：</label>
                                <div class="col-sm-2">
                                    <#if isReview>
                                     <p class="form-control-static">
                                    <#if productDto.productionDate??>${productDto.productionDate?string("yyyy-MM-dd")}</#if>
                                    </p>
                                    <#else> 
                        			<div class="input-group date datetimeInput  no-padding pull-left"  
                        				data-date="<#if productDto.productionDate??>${productDto.productionDate?string("yyyy-MM-dd")}</#if>"    data-date-format="yyyy-MM-dd"  >
                                        <input class="form-control " size="16"  type="text" name="productionDate" id="productionDate" 
                                        value="<#if productDto.productionDate??>${productDto.productionDate?string("yyyy-MM-dd")}</#if>"  readonly placeholder="生产日期">
                                        
                                        <span class="add-on" ><i class="icon-remove"></i></span>
                                        <span class="add-on"><i class="icon-calendar"></i></span>
                                    </div>
                                   </#if>         
                               	 
                                
                                </div>
                              </div>
                             <div class="form-group  ">
                                <label class="col-sm-2 control-label"><span class="requiredField">*</span>官方指导价(元)：</label>
                                <div class="col-sm-5">
                                <#if isReview>
                                <p class="form-control-static">
                                ${productDto.price}
                                </p>
                                <#else>
                                <input type="text" class="form-control" name="guidePrice" maxlength=10  id="guidePrice" value="<#if productDto.price??>${productDto.price}</#if>" placeholder="">
                                </#if>
                                </div>
                              </div> 
                              
                            
                          </div>
                      </div>
                       <!-- /.tab-pane -->
                      <div class="tab-pane" id="tab_2">
                         <div class="box-body">
                           <div class="form-group">
                              
                              <div class="col-sm-10">
                              <#if isReview>
                              <p class="form-control-static">
                              ${productDto.desc}
                              </p>
                              <#else>
                                  <script id="ueEditor" name="introduction" type="text/plain" style="width:100%;height:500px;">${productDto.desc}</script>
                               </#if>
                              </div>
                            </div>
                        </div>
                      </div>
                      <!-- /.tab-pane -->
                      
                      <!-- /.tab-pane -->
                      <div class="tab-pane" id="tab_3">
                         <div class="box-body">
                           <div class="form-group">
                              <#include "/product/product_pics_list.ftl" /> 
                            </div>
                        </div>
                      </div>
                      <!-- /.tab-pane -->
                     
                      <div class="tab-pane" id="tab_4">
                         <div class="box-body">
                           <div class="form-group">
                             <#include "/product/product_related_goods.ftl" /> 
                            </div>
                        </div>
                      </div>
                     
                    </div>
                    <!-- /.tab-content -->
                  </div>
                 
                  <!-- nav-tabs-custom -->
                  <div class="row">
                   <div class="col-sm-12 text-center">
                     <#if !isReview>
                    <button class="btn btn-primary submitMainForm" type="button"  >保存</button>
                    </#if>
                    <button class="btn btn-success backPage" type="button"   ><i class="fa fa-backward"> 返回 </i></button>
                   
                   </div>
                  </div>
                </div>
                 </form>
            </div>
           
            <div class="clearfix"></div>
        </div>
       
        <!-- /.row -->

    </div><!-- ./wrapper -->
   
  </body>
<#include "/footer.ftl" />
<script id="addEditTpl" type="text/html">
	<div class="box-body">
		<div class="row form-group">    	
			<div class="form-group">
				<div class="col-sm-6"  style="padding-bottom:10px;">
	                <label class="control-label">编号：</label>
	                <input type="text" class="form-control" name="goodCode" id="goodCode" style="width:60%">
	            </div>
				<div class="col-sm-6"  style="padding-bottom:10px;">
	                <label class="control-label">名称：</label>
	                <input type="text" class="form-control" name="goodName" id="goodName" style="width:60%">
	            </div>
			</div>
			<div class="form-group">
				<div class="col-sm-6"  style="padding-bottom:10px;">
	        		<button class="btn  btn-primary" id="refreshRecord"><i class="fa fa-search"></i> 开始搜索</button>
	            </div>				
			</div>
	    </div>
	    <div class="row treeTable">
		    <div class="col-sm-12">
				<table id="dataList" class="table table-bordered table-hover table-color dataList" >
					<thead>
			            <th field="productId" width=70><div class="datagrid-header-check"><input type="checkbox"></div></th>
			            <th>序号</th>
			            <th>单瓶商品编号</th>
			            <th>单瓶商品名称</th>
			            <th>销售价</th>
						<th class="hidden"></th>
			        </thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</script>
<script>
var attrsList = [];
   var imagesList = [];
   var productsList = [];
   <#list productDto.listAttr as attr>
   attrsList.push({
      	attrValueId : "${attr.attrValueId}"
   });
   </#list>
   <#list productDto.listImages as image>
   imagesList.push({
       imageUrl : "${image.imageUrl}",
       imageId  : "${image.imageId}"
   });
   </#list>
   <#list productDto.listProducts as product>
 	productsList.push({
 		productId   : "${product.productId}",
 		productName : "${product.productName}",
 		brandName   : "${product.brandName}",
 		productCode : "${product.productCode}",
 		cateName    : "${product.cateName}",
 		cateId      : "${product.cateId}",
 		goodId      : "${product.goodId}",
 	});
   </#list>
   var productImage = "${productDto.image}";
	$(function() {
	    var cateId = "${cateInfo.pCategoryEntity.cateId}";
	    var productId = '${productDto.productId}' || 0;
	    var productInstance = productApp.init(cateId, productId, productImage, attrsList, imagesList ,productsList);
        <#if isReview>
        productInstance.convertToReviewPage();
        </#if>
	});
</script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script type="text/javascript" src="${uiBase}js/pages/product/product_save.js?v=${resourceVersion}"></script> 
<script type="text/javascript" src="${uiBase}vendor/ajaxfileupload/ajaxfileupload.js?v=${resourceVersion}"></script> 
</html>
