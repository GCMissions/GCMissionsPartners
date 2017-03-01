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
			   <li><i class="fa fa-dashboard"></i>  营销管理</li>
			   <li>配送费管理</li>
		   </ol> 
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
				 <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="postageIndex"> 邮费设置</a>
                        </li>
                        <li class=""><a href="freightIndex">终端配送商配送费</a>
                        </li>
                        <li class=""><a href="freeIndex">商品免运费设置</a>
                        </li>
                    </ul>
                    <form id="postageConfigForm" method="post" action="">                      
                    <div class="tab-content">
                        <div class="row hidden">
	                        <div class="col-md-12">
		                        <div class="col-md-12">
			                        <label class="control-label" for="type-select">全场满  </label>
			                        <input type="text" class="form-control freeFreight default_input" value="${config.freeFreight}">
			                        <label class="control-label" for="type-select">元包邮</label>
		                        </div>
	                        </div>
                        </div>
                        <div class="row">
	                        <div class="col-md-12">
		                        <div class="col-md-12">
		                        	<label class="control-label" for="type-select">默认运费</label>
		                        	<input type="text" class="form-control defaultStartNum default_input" name="defaultStartNum" value="${config.startNum}">
		                        	<label class="control-label" for="type-select">瓶内,</label>		                        
		                        	<input type="text" class="form-control defaultStartCost default_input" name="defaultStartCost" value="${config.startCostYuan}">
		                        	<label class="control-label" for="type-select">元,</label>
		                        	<label class="control-label" for="type-select">每增加</label>
		                        	<input type="text" class="form-control defaultPlusNum default_input" name="defaultPlusNum" value="${config.plusNum}">
		                        	<label class="control-label" for="type-select">瓶,</label>
		                        	<label class="control-label" for="type-select">增加运费</label>
		                        	<input type="text" class="form-control defaultPlusCost default_input" name="defaultPlusCost" value="${config.plusCostYuan}">
		                        	<label class="control-label" for="type-select">元</label>
		                        </div>
	                        </div>
                        </div>
                        <div class="row">
	                        <div class="col-md-12">
		                        <div class="col-md-12">
			                        <table class="freightConfig">
			                        	<thead>
					                        <th style="width:600px">配送到</th>
					                        <th>首瓶（瓶）</th>
					                        <th>首费（元）</th>
					                        <th>续瓶（瓶）</th>
					                        <th>续费（元）</th>
					                        <th>操作 </th>
			                            </thead>
		                              	<tbody id="freightConfig">
		                              	   <#list config.list as configList>
				                           <tr> 
			                              	    <td>
			                              	    	<a class="editFreight"><i class="fa fa-edit" style="font-size:20px;"></i></a>					                              	    
			                              	        <input type="text" class="form-control hidden configIndex"  value="${configList_index}">					                              	        
			                              	    	<input type="text" class="form-control hidden configId"  value="${configList.configId}">
			                              	    	<input type="text" class="form-control hidden regionIds" id="regionId_${configList_index}" value="${configList.regionIdString}">					                              	    						                              	    
			                              	        <span class="regionNames" id="regionName_${configList_index}">${configList.regionNames}</span>
			                              	    </td>
			                              	    <td><input type="text" class="form-control startNum" name="startNum" value="${configList.startNum}"><span class="fieldError startNumError hidden">只允许输入零或正整数</span>
			                              	    </td>
			                              	    <td><input type="text" class="form-control startCostYuan" name="startCostYuan" value="${configList.startCostYuan}"><span class="fieldError startCostYuanErrorI hidden">只允许输入数字</span><span class="fieldError startCostYuanErrorII hidden">只允许输入两位小数</span>
			                              	    </td>
			                              	    <td><input type="text" class="form-control plusNum" name="plusNum" value="${configList.plusNum}"><span class="fieldError plusNumError hidden">只允许输入零或正整数</span>
			                              	    </td>
			                              	    <td><input type="text" class="form-control plusCostYuan" name="plusCostYuan" value="${configList.plusCostYuan}"><span class="fieldError plusCostYuanErrorI hidden">只允许输入数字</span><span class="fieldError plusCostYuanErrorII hidden">只允许输入两位小数</span>
			                              	    </td>
			                              	    <td><a class="removeFreight"><i class="fa fa-trash"  style="font-size:20px"></i></a>
			                              	    </td>
				                            </tr>	
				                            </#list>
				                                                	  
		                              	</tbody>
		                            </table>
		                            <a id="addFreight">为指定地区城市设置运费</a>
	                            </div>
                            </div>
                           </div>
                           <div class="row">
                            <div class="col-md-12">
               					 <div style="width:100px; margin:0 auto; padding-top:10px;">
	               					 <a class="btn btn-primary" style="float:left;" id="save"><i class="fa"></i>修改</a>
                                 </div>                            
                            </div>
                       	</div>                            
                    </div>
                    </form>
                </div>			              
             </div>
        </div>
        <div class="clearfix"></div>     
      </div>
   </div>
</body>
<#include "/footer.ftl" />
<div id="addEditTpl" style="height:260px;">
	<!-- <div class="ecity">
		<span class="gareas"><input type="checkbox" value="360000" id="J_Province_360000" class="J_Province">
		<label for="J_Province_360000">江西</label><span class="check_num"></span><img class="trigger" src=""></span>
		<div class="citys">
			<span class="areas"><input type="checkbox" value="360100" id="J_City_360100" class="J_City">
			<label for="J_City_360100">南昌</label></span>
			<span class="areas"><input type="checkbox" value="360200" id="J_City_360200" class="J_City">
			<label for="J_City_360200">景德镇</label></span>
			<span class="areas"><input type="checkbox" value="360300" id="J_City_360300" class="J_City">
			<label for="J_City_360300">萍乡</label></span>
			<span class="areas"><input type="checkbox" value="360400" id="J_City_360400" class="J_City">
			<label for="J_City_360400">九江</label></span>
			<span class="areas"><input type="checkbox" value="360500" id="J_City_360500" class="J_City">
			<label for="J_City_360500">新余</label></span>
			<span class="areas"><input type="checkbox" value="360600" id="J_City_360600" class="J_City">
			<p style="text-align:right;"><input type="button" value="关闭" class="close_button"></p>
		</div>
	</div> -->
</div> 
 <script src="${uiBase}/js/pages/freightConfig/postageConfig.js?v=${resourceVersion}"></script>
</html>
