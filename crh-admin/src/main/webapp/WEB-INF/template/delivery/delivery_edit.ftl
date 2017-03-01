<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/vendor/bootstrap-validator/css/bootstrapValidator.css">
<link rel="stylesheet" href="${uiBase}/css/pages/delivery.css?v=${resourceVersion}">
</head>

<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
       <section class="content-header">
	 	   <ol class="breadcrumb">
			   <li><i class="fa fa-dashboard"></i> 商家管理</li>
			   <li>终端配送商信息修改</li>
		   </ol> 
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box ">
                     <div class="box-body">
                            <form role="form" id="addForm">
 								<div class="row">                              
			                      	 <div class="col-md-12">
			                      	 	<div class="col-sm-4">
			                            	<label class="control-label">终端配送商编号:</label>
	                               			<span>${orgDto.orgCode}</span>
	                               			<input class="hidden" value="${orgDto.orgId}" id="parentId">	
			                          	</div>
			                          	<div class="col-sm-4">		                          	
			                             	<label class="control-label">终端配送商登录名:</label>
	                                		<span>${orgDto.loginId}</span>	
			                          	</div>
			                         </div>
	                            </div> 
                                <div class="row">                              
			                      	 <div class="col-md-12">
			                      	 	<div class="col-sm-4">
			                            	<label class="control-label">终端配送商名称:</label>
	                                	<input type="text" class="form-control" value="${orgDto.orgName}" name="orgName" id="orgName">	                              	
			                          	</div>
			                          	<div class="col-sm-4">		                          	
			                            	<label class="control-label">账户状态:</label>
	                                		<input class="hidden" value="${orgDto.status}" id="status_input">	                                  	                                  	                                
	                                		<select class="form-control" id="status"> 
                                       	 		<option value="1">启用</option>
                                        		<option value="0">禁用</option>
                                    		</select>	
			                          	</div>
			                         </div>
	                            </div>   	
                            	<div class="row">                              
			                      	 <div class="col-md-12">
			                      	 	<div class="col-sm-4">
			                            	<label class="control-label"><span class="requiredField">*</span>联系人:</label>
	                                		<input type="text" class="form-control" value="${orgDto.contact}" name="contact" id="contact">	                              	
			                          	</div>
			                          	<div class="col-sm-4">		                          	
			                            	<label class="control-label"><span class="requiredField">*</span>联系电话:</label>
	                                		<input type="text" class="form-control" name="phone" value="${orgDto.phone}" name="phone" id="phone">                              	
			                          	</div>
			                         </div>
	                            </div> 
                                <div class="row">                              
			                      	 <div class="col-md-12">
			                      	 	<div class="col-sm-4">
			                            	<label class="control-label"><span class="requiredField">*</span>所在省份:</label>
	                                		<input class="hidden" value="${orgDto.provinceId}" id="provinceId_input">
	                                		<input class="hidden" value="${orgDto.cityId}" id="cityId_input">	                                  	                                  	                                
	                                		<input class="hidden" value="${orgDto.areaId}" id="areaId_input">	
	                                		<input class="hidden" value="${orgDto.region}" id="region_input">
                                    		<select class="form-control control_select" name="provinceId" id="provinceId" disabled>
                                    			<option value="">请选择省份</option>
                                    		</select>	
			                          	</div>
			                          	<div class="col-sm-4">		                          	
			                            	<label class="control-label"><span class="requiredField">*</span>详细地址:</label>
	                                		<input type="text" class="form-control" name="serviceAddress" id="serviceAddress" value="${orgDto.address}"> 
	                                		<input id="result_" type="hidden" />
			                          	</div>
			                         </div>
	                           </div>
                               <div class="row">                              
			                      	 <div class="col-md-12">
			                      	 	<div class="col-sm-4">
			                            	<label class="control-label">所在城市:</label>
                                    		<select class="form-control control_select" id="cityId" disabled> 
                                        		<option value="">请选择城市</option>                                   
                                    		</select>	
			                          	</div>
			                          	<div class="col-sm-4">		                          	
			                            	<label class="control-label"><span class="requiredField">*</span>经度:</label>
	                                		<input type="text" class="form-control" name="lng" id="lng" value="${orgDto.lng}" disabled>                              	
			                          	</div>
			                         </div>
	                           </div>
                               <div class="row">                              
			                      	 <div class="col-md-12">
			                      	 	<div class="col-sm-4">
			                            	<label class="control-label">所在城区:</label>
                                    		<select class="form-control control_select" id="areaId"> 
                                        		<option value="">请选择城区</option>                                   
                                    		</select>	
			                          	</div>
			                          	<div class="col-sm-4">		                          	
			                            	<label class="control-label"><span class="requiredField">*</span>纬度:</label>
	                                		<input type="text" class="form-control" name="lat" id="lat" value="${orgDto.lat}" disabled>                              	
			                          	</div>
			                         </div>
	                          </div> 
	                          <div class="row">                              
			                      	 <div class="col-md-12">
			                      	 	<div class="col-sm-4">
			                             	<label class="control-label"><span class="requiredField">*</span>开户银行:</label>
	                                		<input type="text" class="form-control" value="${orgDto.bankName}" name="bankName" id="bankName">	                              	
			                          	</div>
			                          	<div class="col-sm-4">		                          	
			                            	<label class="control-label"><span class="requiredField">*</span>开户支行:</label>
	                                		<input type="text" class="form-control" value="${orgDto.branchName}" name="branchName" id="branchName">                              	
			                          	</div>
			                         </div>
	                          </div>
                              <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">
		                            	<label class="control-label"><span class="requiredField">*</span>开户账户:</label>
                                		<input type="text" class="form-control" value="${orgDto.bankAcct}" name="bankAcct" id="bankAcct">	                              	
		                          	</div>
		                          	<div class="col-sm-4">		                          	
		                            	<label class="control-label"><span class="requiredField">*</span>开户人:</label>	                                		
		                            	<input type="text" class="form-control" value="${orgDto.bankContact}" name="bankContact" id="bankContact">                             	
		                          	</div>
		                         </div>
	                          </div>
	                          <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">
		                            	<label class="control-label"><span class="requiredField">*</span>商户号:</label>
                                		<input type="text" class="form-control" value="${orgDto.businessNumber}" name="businessNumber" id="businessNumber">	                              	
		                          	</div>
		                          	<div class="col-sm-4">		                          	
		                            	<label class="control-label"><span class="requiredField">*</span>终端号:</label>	                                		
		                            	<input type="text" class="form-control" value="${orgDto.terminalNumber}" name="terminalNumber" id="terminalNumber">                             	
		                          	</div>
		                         </div>
	                          </div>
	                          <div class="row">                              
			                      	 <div class="col-md-12">
				                      	 <div class="col-sm-4">		                          	
			                            	<label class="control-label"><span class="requiredField">*</span>设备序列号:</label>	                                		
			                            	<input type="text" class="form-control" value="${orgDto.deviceNumber}" name="deviceNumber" id="deviceNumber">                             	
			                          	</div>
			                      	 	<div class="col-sm-4">
			                            	<label class="control-label"><span class="requiredField">*</span>是否专卖店:</label>
			                            	<input class="hidden" value="${orgDto.barcodeFlag}" id="barcodeFlag_input">	
			                            	<input type="radio" name="barcodeFlag" class="barcodeFlag" value="1" checked="checked" disabled/> 是
			                            	<input type="radio" name="barcodeFlag" class="barcodeFlag" value="0" style="margin-left:20px;" disabled/> 否
			                      	 		<!-- <select class="form-control" name="barcodeFlag" id="barcodeFlag">
		                                 		<option value="">请选择</option> 
	                                        	<option value="1">是</option>
	                                        	<option value="0">否</option>
	                                    	</select> -->									
			                          	</div>			                          	
			                         </div>
	                            </div>
                              <div class="row">                              
		                      	 <div class="col-md-12">
		                      	 	<div class="col-sm-4">
		                             	<label class="control-label"><span class="requiredField">*</span>关联区域平台商:</label>
                                		<div id="relatedSupplier">
	                                		<span>${orgDto.parentName}</span>
	                              		</div>	
		                          	</div>
		                          	<!-- <div class="col-sm-4">		                          	
		                            	<a class="btn btn-default addItem">更改</a>
	                              		<span class="fieldError supplierError hidden">区域平台商必须选择一个</span> 
		                          	</div> -->
		                         </div>
	                          </div>
                              <div class="row">                              
			                      	 <div class="col-md-12">
			                      	    <div id="map" style="width:800px; height:500px; margin-left:35px;"></div>			                      	 
			                         </div>
	                          </div>                   
	                          <div class="row">
	                            <div class="col-md-12 text-center">
	               					 <a class="btn btn-primary" id="update">保存</a>
               						 <button class="btn btn-success backPage" type="button" ><i class="fa fa-backward"> 返回 </i></button>		                   			               					 
	                            </div>
	                        </div>  
                       	 </form>
                      </div>
                </div>
             </div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
</body>
<#include "/footer.ftl" /> 


<script id="addEditTpl" type="text/html">
	<div class="box-body form-horizontal">
		<div class="row row-padding">    	
			<div class="col-md-12">
				<div class="col-sm-6">
	                <label class="control-label" for="type-select">区域平台商编号：</label>
	                <input type="text" class="form-control" id="supplierOrgCode">
	            </div>
	            <div class="col-sm-6">
	                <label class="control-label" for="type-select">区域平台商名称：</label>
	                <input type="text" class="form-control" id="supplierOrgName">
	            </div>
			</div>
			<div class="col-md-12">
				<div class="col-sm-6">
	                <label class="control-label" for="type-select">联系人：</label>
	                <input type="text" class="form-control" id="supplierContact">
	            </div>
	            <div class="col-sm-6">
	                <label class="control-label" for="type-select">联系电话：</label>
	                <input type="text" class="form-control" id="supplierPhone">
	            </div>
			</div>
			<div class="col-md-12">
				<div class="col-sm-6">
	        		<button class="btn  btn-primary" id="refreshRecord">搜    索</button>
				</div>
			</div>
	    </div>
	    <div class="row row-padding">
		    <div class="col-sm-12">
				<table id="dataList" class="table table-bordered table-hover table-color dataList" >
					<thead>
			            <th field="orgId" width=70><div class="datagrid-header-check"><input type="checkbox"></div></th>
			            <th>区域平台商编号</th>
			            <th>区域平台商名称</th>
			            <th>联系人</th>
			            <th>联系电话</th>
						<th class="hidden"></th>
			        </thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</script>

<script src="${uiBase}/js/pages/delivery/delivery_edit.js?v=${resourceVersion}"></script>
<!-- 地图 -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sHLz8MFUdDtLaAj54AETxwxz"></script>
<script type="text/javascript" src="${uiBase}/js/pages/supplier/map.js?v=${resourceVersion}"></script>
</html>
