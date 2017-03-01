<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/freight.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
       <section class="content-header">
	 	   <ol class="breadcrumb">
			   <li><i class="fa fa-dashboard"></i> 营销管理</li>
			   <li>配送费管理</li>
		   </ol> 
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
				 <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                        <li class=""><a href="postageIndex"> 邮费设置</a>
                        </li>
                        <li class=""><a href="freightIndex">终端配送商配送费</a>
                        </li>
                        <li class="active"><a href="freeIndex">商品免运费设置</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                       <div class="row">
                            <div class="col-md-12">
               					<div class="col-sm-3">
                                    <label class="control-label" for="type-select">商品名称：</label>
                                    <input type="text" class="form-control" id="productName">
                                </div>
                                <div class="col-sm-3">
                                    <label class="control-label" for="type-select">商品条码：</label>
                                    <input type="text" class="form-control" id="productCode">
                                </div>
                                <div class="col-sm-3">
                                    <label class="control-label" for="type-select">分类：</label>                                  
                                    <select class="form-control" id="cateId">	                                       
                                        <option value="">请选择</option>
                                        <#list cate as cateList>
                                        <option value="${cateList.cateId}">${cateList.cateName}</option>
                                        </#list>                                        
                                    </select>
                                </div>
                                <div class="col-sm-3">
                                    <label class="control-label" for="type-select">品牌：</label>                                  
                                    <select class="form-control" id="brandId">
                                        <option value="">请选择</option>
                                        <#list brand as brandList>
                                        <option value="${brandList.brandId}">${brandList.brandName}</option>
                                        </#list>                                         
                                    </select>
                                </div>	
                                
                            </div>
                        </div>	                   
                        <div class="row">
                        	<div class="col-md-12">
                            	<div class="col-md-3 col-sm-4 pull-left">
                                	<div class="pull-left">
                                		<button class="btn  btn-primary" id="refreshRecord"><i class="fa fa-search"></i>开始搜索</button>		                                   
                                	</div>
                            	</div>
                            </div>
                        </div> 
                        <div class="row">
                        	<div class="col-md-12">
                            	<table id="dataList" class="table table-bordered table-hover" >
			               			<thead>
			                            <th class="productId" data-field="productId">序号</th>
			               				<th class="productCode" data-field="productCode">商品条码</th>
			                            <th class="productName" data-field="productName">商品名称</th>
			                            <th class="productBrand" data-field="productBrand">商品品牌</th>
			                            <th class="productCate" data-field="productCate">分类</th>
			                            <th class="hidden" data-field="shipfeeConfig"></th>
                          				<th class="freeSet" data-field="freeSet">操作</th>
				                    </thead>
				                    <tbody> 						                    						                    	
				                    </tbody>
				               </table>		                          		                            	
                            </div>
                        </div> 
                    </div>                     
                </div>
             </div>
        </div>
        <input type="hidden" id="priceExpression" name="priceExpression" value="${shipfeeConfig}"/>
        <div class='gradientList hidden'>
	        <table id="gradientTemplate">
				<tbody>
					<tr class="grad">
						<th class="quantity">起始数量:</th>
						<td class="quantity">
							<input type="text" class="form-control startQuantity disabled" disabled/>
							<input type="hidden" class="form-control startQuantity" name="startQuantity" data-name="startQuantity"/>
						</td>
						<th class="quantity">终止数量:</th>
						<td class="quantity">
							<input type="text" class="form-control endQuantity numbercode" name="endQuantity" data-name="endQuantity"/>
						</td>
						<th class="discount ">折扣:</th>
						<td class="discount ">
							<input type="text" class="form-control discount decimalscode" name="discount" data-name="discount"/>
						</td>
						<td width="400">
							<a href="javascript:;" class="gradbtn delete">删除</a>
							<input type="hidden" data-name="gradientCheck" class="gradientCheck" />
						</td>
					</tr>
				</tbody>
			</table>
		<div>
        <div class="clearfix"></div>     
      </div>
   </div>
</body>
<#include "/footer.ftl" />
 <script src="${uiBase}/js/pages/freightConfig/freeConfig.js?v=${resourceVersion}"></script>
</html>
