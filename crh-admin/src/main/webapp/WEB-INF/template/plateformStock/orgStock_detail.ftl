<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" /> 
<style>
.datagrid-header-input{
    display: inline-block;
    width: 160px;
}
.stockHeader{
	margin-bottom: 5px; 
	background-color: #f9f9f9;
	padding-top: 10px;
  	padding-bottom: 5px;
}
.fa-edit,.fa-check,.fa-remove{
	color: #3c8dbc;
}
.stockId{
	display:none;
}
.fa-edit,.fa-check,.fa-remove{
	cursor: pointer;
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
            <li><i class="fa  fa-building-o"></i> 库存管理</li>
            <li> 商家库存查看/编辑</li> 
          </ol>
          
         </section>
        
       
        
        <!-- Main content -->
        <div class="row pad">
        	<div class="col-md-12">
				<!--<input type="hidden" id="orgCode" value="${orgCode}">-->
				<div class="box box-primary">
				
	                <div class="box-header with-border">
	                <div class="row col-md-12"></br></div>
	                    <div class="row">
	                    	<div class="col-md-12">
	                    		<div class="col-sm-3">                   
	                            	<label class="control-label">商家编号: ${orgStockDetailDto.orgCode}</label>
								</div>
								<div class="col-sm-3">
	                            	<label class="control-label">商家名称: ${orgStockDetailDto.orgName}</label>
								</div>
								<div class="col-sm-3">
									<label class="control-label">商家类别: ${orgStockDetailDto.orgCate}</label>
								</div>
								<div class="col-sm-2">
									<label class="control-label">库存总量: ${orgStockDetailDto.stockNum}</label>
								</div>
	                    	</div>
	                    </div>
	                <div class="row col-md-12"></br></div>
	               	</div><!-- /.box-header -->
	               
	               <div class="row col-md-12"></br></div>
	               
	               <div class="box-body">
	               		<table id="dataList" class="table table-bordered table-hover" >
	               			<thead>
	                            <th class="stockId" data-field="stockId"></th>
	                            <th class="no" data-field="no">序号</th>
	               				<th class="goodsCode" data-field="goodsCode">单瓶商品编号</th>
	                            <th class="goodsName" data-field="goodslName">单瓶商品名称</th>
	                            <th class="price" data-field="price">销售价</th>
	                            <th class="safeNum" data-field="safeNum">安全库存</th>
	                            <th class="standardNum" data-field="standardNum">标准库存</th>
	                            <th class="stockNum" data-field="stockNum">库存量</th>
	                            <th class="orgState" data-field="orgState">库存状态</th>
	                            <th class="stockSet" data-field="stockSet">操作</th>
		                    </thead>
		                    <tbody> 
		                    <#list orgStockDetailDto.list as orgStockDetailList>
		                        <tr>
		                        	<td class="stockId" >${orgStockDetailList.stockId}</td>
		                        	<td class="no" >${orgStockDetailList_index+1}</td>
		                        	<td class="goodsCode" >${orgStockDetailList.goodsCode}</td>
		                        	<td class="goodsName" >${orgStockDetailList.goodsName}</td>
		                        	<td class="price" >${orgStockDetailList.price}</td>
		                        	<td class="safeNum" >${orgStockDetailList.safeStockSetting}</td>
		                        	<td class="standardNum" >${orgStockDetailList.standardStockSetting}</td>
		                        	<td class="stockNum" >
		                        		<span class="stockNumVal">${orgStockDetailList.stockSetting}</span>
		                        		<input class="stockNumEdit" style="display:none" value=${orgStockDetailList.stockSetting}></input>
		                        	</td>
		                        	<td class="orgState" >${orgStockDetailList.orgState}</td>
		                        	<td class="stockSet" >
		                        		<i class="fa fa-edit "  style="font-size:20px"></i>
		                        	  	<i class="fa fa-check "  style="font-size:20px; display:none"></i>
		                        	  	<i class="fa fa-remove "  style="font-size:20px; display:none"></i>
		                        	</td>
		                        </tr>
		                    </#list>
		                    </tbody>
		               </table>
	               		
	              </div><!-- /.box-body -->
	            </div>
	    	</div>
        </div>
        <div class="col-md-12">
        	<!--
        	<div class="col-sm-3 col-sm-offset-3">
                <button class="btn  btn-success" id="back"><i class="fa fa-backward"></i> 库存设置</button>
			</div>
        	-->
        	
			<div class="col-sm-3 col-sm-offset-5">
                <button class="btn  btn-success" id="back"><i class="fa fa-backward"></i> 返  回</button>
			</div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->


</body>
<#include "/footer.ftl" />
<script src="${uiBase}/vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}/vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script src="${uiBase}/js/pages/shopStock/orgStock_detail.js?v=${resourceVersion}" ></script>
</html>
