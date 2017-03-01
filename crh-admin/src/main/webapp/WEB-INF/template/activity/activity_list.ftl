<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
<style type="text/css">
.productBrand {width:161px !important}
</style>
<link rel="stylesheet" href="${uiBase}vendor/bootstrap-slider/boostrap-slider.css">
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
                      <div class="box-header with-border search-group" >
                     	 <div class="row">
                            <div class="col-md-12">
                            	<div class="col-sm-5"> 
                            		<select class="selectpicker form-control productBrand " id="firstCate" title="一级商品品类"> 
                                    	<option value="0">不限</option>
                                        <#list firstCates as item>
                                        		<option value="${item.cateId}">${item.cateName}</option>
                                        </#list>
                                     </select>
                                     <span class="time">-</span>
									 <select title="二级商品品类" name="secondCategory" class="selectpicker form-control productBrand"
													id="secondCate" data-rule-checkCategory="true" value="">
                                     </select>
                                 </div>
                                 <div class="col-sm-3">
                                 	<select class="selectpicker form-control productBrand" id="orgId" title="服务商"> 
                                    	<option value="">不限</option>
                                        <#list listOrgs as item>
                                        <option value="${item.orgId}">${item.orgName}</option>
                                        </#list>
                                     </select>
                                 </div>
                     	 	</div>
                     	 </div>
                         <div class="row">
                         	<div class="col-md-12">
                                <div class="col-sm-5">
                                    <input id="productName" class="form-control" type="text" maxlength="50" placeholder="商品名称">
                                	<span>&nbsp;</span>
                                    <input id="productCode" class="form-control" type="text" maxlength=13 placeholder="商品编号">
                                </div>	
                               	<div class="col-sm-3">
                                	<select class="selectpicker form-control productBrand" id="vipFlag" title="商品属性">
										<option value="">全部商品</option>
										<option value="0">普通商品</option>
										<option value="1">会员商品</option>
									</select>
                               	</div>
                             </div>                            
                          </div> 
                          <div class="row">
                         	<div class="col-md-12">
                                <div class="col-sm-5">
                                   <input class="form-control" type="text" id="lowPrice" value="" maxlength=11 placeholder="价格范围">    
                                   <span class="time">-</span>  
                                   <input class="form-control" id="highPrice" type="text" value="" maxlength=11 placeholder="价格范围">
                                   <span id="numVali" style="color:#ffb042;padding-left:10px;font-weight:700;display:none;">请输入有效的数字</span>
					               <span id="numCom" style="color:#ffb042;padding-left:10px;font-weight:700;display:none;">请输入正确的价格范围</span>
                                </div>
 
								<div>  
	                                <label class="col-sm-1 control-label">   
										<button type="button" class="btn btn-primary searchBtn" id="search"><i class="fa fa-search"></i> 查询</button>                                
	                                </label>
	                                <label class="col-sm-1 control-label pull-left">  
	                                   <button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>  
	                                </label>
                                </div>                                  
                             </div>                            
                          </div>
                     </div> 
                      
                     <div class="box-body">
                      	<div class="row">
                      		 <div class="col-md-12">
								<label class="col-sm-2  pull-right">
									<a class="btn btn-default "  href="${urlPrefix}activity/addPage" ><i class="fa fa-plus"></i> 添加商品</a>
								</label>
								<button type="button" class="btn btn-primary col-sm-1  pull-right" id="deleteItem">删除</button>
                     		</div>
                        </div>
                    	<form id="product" web-host="${(wechatHost)!}">
                      		<table id="dataList" class="table table-bordered table-hover dataList" >
                        		<thead>
                           			<th><input type="checkbox" id="seletAll" value=""></th>
                            		<th>商品编号</th>
                            		<th>商品品类</th>
                            		<th>服务商</th>
                            		<th>商品名称</th>
                            		<th>价格（元）</th>
                            		<th>创建时间</th> 
                            		<th>操作</th>
                        		</thead>
                        		<tbody>
                        		</tbody>
                      	</table>
                      </form>
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
<script type="text/javascript" src="${uiBase}js/pages/activity/activity_list.js?v=${resourceVersion}"></script> 

</html>
