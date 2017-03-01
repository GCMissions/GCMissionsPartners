<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/product.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
        <div class="content-wrapper page-content-wrapper">
         	<section class="content-header">
	        	<ol class="breadcrumb">
            		<li><i class="fa  fa-building-o"></i> 库存管理</li>
            		<li> 发货单详情</li> 
          		</ol>
           </section>

            <div class="row pad">
                <div class="col-md-12">
                  <!-- Custom Tabs -->
                  <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                      <li class="active"><a href="#tab_1" data-toggle="tab">发货信息</a></li>
                      <li><a href="#tab_2" data-toggle="tab">单瓶商品信息</a></li>
                      <input type="hidden" id="orgCateValue" value="${shipmentDetailDto.orgCate}" >
                      <input type="hidden" id="shipmentStatusValue" value="${shipmentDetailDto.shipmentStatus}" >
                      <input type="hidden" id="orderIdValue" value="${shipmentDetailDto.orderId}" >
                       
                    </ul>
                    <div class="tab-content">
                      <div class="tab-pane active" id="tab_1">
                          <div class="box-body">
                              <!-- text input -->
                              <div class="col-md-12">
                              	<label class="col-sm-1 control-label">商家编号:</label>
                                <div class="col-sm-2">${shipmentDetailDto.orgCode} </div>
                                <label class="col-sm-1 control-label">商家名称:</label>
                                <div class="col-sm-2">${shipmentDetailDto.orgName} </div>
                              </div>
                              <div class="col-md-12">
                                <label class="col-sm-1 control-label">商家类别:</label>
                                <div class="col-sm-2">${shipmentDetailDto.orgCate} </div>
                                <label class="col-sm-1 control-label">发货状态:</label>
                                <div class="col-sm-2">${shipmentDetailDto.shipmentStatus} </div>
                              </div>
                              <div class="col-md-12">
                                <label class="col-sm-1 control-label">联系人:</label>
                                <div class="col-sm-2">${shipmentDetailDto.createName} </div>
                                <label class="col-sm-1 control-label">联系电话:</label>
                                <div class="col-sm-2">${shipmentDetailDto.phone} </div>
                              </div>
                              <div class="col-md-12">
                                <label class="col-sm-1 control-label">创建时间:</label>
                                <div class="col-sm-2">${shipmentDetailDto.createDate} </div>
                                <label class="col-sm-1 control-label">收货时间:</label>
                                <div class="col-sm-2">${shipmentDetailDto.receivingDate} </div>
                              </div>
                            
                          </div>
                      </div>
                      
                      <!-- /.tab-pane -->
                      <div class="tab-pane" id="tab_2">
                     	<div class="box-body">
                        	<div class="col-md-12">
                             	<table id="dataList" class="table table-bordered table-hover" >
               						<thead>
               							<th class="no">序号</th>
               							<th class="goodsCode">单瓶商品编号</th>
                            			<th class="goodsName">单瓶商品名称</th>
                           			 	<th class="price">销售价</th>
                            			<th class="shipmentNum">发货数量</th>
	                    			</thead>
	                    			<tbody> 
	                    			<#list shipmentDetailDto.productList as productDetailList>
	                       	 			<tr>
	                        				<td class="no" >${productDetailList_index+1}</td>
	                        				<td class="goodsCode" >${productDetailList.goodsCode}</td>
	                        				<td class="goodsName" >${productDetailList.goodsName}</td>
	                        				<td class="price" >${productDetailList.price}</td>
	                        				<td class="shipmentNum" >${productDetailList.shipmentNum}</td>
	                        			</tr>
	                    			</#list>
	                    			</tbody>
	               				</table>
                            </div>
                        </div>
                      </div>
                    </div>
                    <!-- /.tab-content -->
                  </div>
                 
                  <!-- nav-tabs-custom -->
                  	<div class="col-sm-3 col-sm-offset-5">
                  		<button class="btn  btn-success" id="back" href="# "><i class="fa fa-backward"></i> 返  回</button>
                  		<!--<button class="btn  btn-success" id="shipmentButton" href="# " style="display:none;">发货</button> -->
					</div>
                </div>
            </div>
        

           
            <div class="clearfix"></div>
        </div>
       
        <!-- /.row -->

    </div><!-- ./wrapper -->
   
  </body>
<#include "/footer.ftl" />
<script type="text/javascript" src="${uiBase}/js/pages/shopStock/shipment_detail.js?v=${resourceVersion}"></script> 
</html>
