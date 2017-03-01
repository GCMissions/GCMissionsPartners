<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/pages/product.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}css/pages/wrkd/group_buy.css?v=${resourceVersion}">
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 团购管理</li>
					<li>查看团购详情</li>
				</ol>
			</section>
			<!-- Main content -->
        	<div class="row pad">
                <div class="col-md-12">
                  <!-- Custom Tabs -->
                	<form role="form" class="form-horizontal" method="post" id="mainForm">
                  		<div class="nav-tabs-custom">
		                    <div class="tab-content">
                      <div class="tab-pane active" id="tab_1">
                          <div class="box-body">
                           	  <input type="hidden" name="cateId" value="${cateInfo.pCategoryEntity.cateId}">
                              <!-- text input -->
                              <div class="form-group">
                                  <label class="col-sm-2 control-label"><span class="requiredField">*</span>活动名称:</label>
                                  <div class="col-sm-5">
                                    <#if isReview>
                                    <p class="form-control-static">
                                    ${productDto.productName}
                                    </p>
                                    <#else>                                  
                                    <input type="text" class="form-control" name="productName"  maxlength=50 value="${productDto.productName}">
                                    <input type="hidden" class="form-control" name="cateName" value="${cateInfo.category.cateName}" >
                                    </#if>
                                  </div>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              <div class="form-group ">
											<label class="control-label col-sm-2" for="type-select"><span
												class="requiredField">*</span>活动有效时间：</label>
											<div class="col-sm-8 timegroup">
												<#if isReview>
												<p class="form-control-static">
													${bargainDto.effectiveStartDate?string('yyyy-MM-dd HH:mm')} 至 ${bargainDto.effectiveEndDate?string('yyyy-MM-dd HH:mm')}
												</p>
												<#else> <input type="hidden" data-ignore="true"
													name="stDate" id="stDate"
													value="<#if bargainDto.effectiveStartDate>${(bargainDto.effectiveStartDate?string('yyyy-MM-dd HH:mm'))!}</#if>" />
												<div class="dateDiv" style="margin-bottom: 0px;">
													<input size="10" type="text" name="effectiveStartDate"
														id="startDate" class="form-control keyword startDate"
														placeholder="开始时间" readonly
														value="<#if bargainDto.effectiveStartDate>${(bargainDto.effectiveStartDate?string('yyyy-MM-dd HH:mm'))!}</#if>">
													<span class="add-on" style="display: none"><i
														class="icon-remove"></i></span> <span class="add-on"><i
														class="icon-calendar"></i></span>
												</div>
												<span class="time">-</span> <input type="hidden"
													data-ignore="true" name="edDate" id="edDate"
													value="<#if bargainDto.effectiveEndDate>${(bargainDto.effectiveEndDate?string('yyyy-MM-dd HH:mm'))!}</#if>" />
												<div class="dateDiv" style="margin-bottom: 0px;">
													<input size="10" type="text" name="effectiveEndDate"
														id="endDate" class="form-control keyword endDate"
														placeholder="结束时间" readonly
														value="<#if bargainDto.effectiveEndDate>${(bargainDto.effectiveEndDate?string('yyyy-MM-dd HH:mm'))!}</#if>">
													<span class="add-on" style="display: none"><i
														class="icon-remove"></i></span> <span class="add-on"><i
														class="icon-calendar"></i></span>
												</div>
												</#if>
											</div>
										</div>
                              <div class="form-group">
                              		<label class="col-sm-2 control-label">活动说明图片:</label>
                                	<div class="col-sm-6">
                                	</div>
                              </div>
                              <div class="form-group">
                              		<label class="col-sm-2 control-label">活动特别说明 :</label>
                                  	<div class="col-sm-6"><textarea cols=120 rows=2 class="form-control" maxlength="50" style="width:400px"></textarea></div>
                              </div>
                              <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="requiredField">*</span>活动商品：</label>
                                <div class="col-sm-10">
                                	<a href="javascript:void(0)" class="j-addpro">+选择活动商品</a>
                                	<span class="addpro">+选择活动商品</span>
                                	<div class="m-pro">
                                		<table>
                                			<thead><tr><td>商品编号</td><td>商品名称</td><td>操作</td></tr></thead>
                                			<tbody></tbody>
                                		</table>
                                	</div>
                                	<div id="specChooseBox"></div>
                                </div>
                              </div>
                               <div class="form-group  ">
                                <label class="col-sm-2 control-label"><span class="requiredField">*</span>活动商品价格（元）：</label>
                                <div class="col-sm-5">
                                <#if isReview>
		                          	<p class="form-control-static">
		                            ${productDto.promotion}
		                             </p>
                             	 <#else>
                                  <input type="text" class="form-control" name="price" value="" />   
                             	 </#if>
                                </div>
                              </div>
                              <div class="form-group  ">
                                <label class="col-sm-2 control-label"><span class="requiredField">*</span>成团商品数量：</label>
                                <div class="col-sm-5">
                                <#if isReview>
                                <p class="form-control-static">
                                ${productDto.specNum}
                                </p>
                                <#else> 
                                <input type="text" class="form-control"  maxlength=4 value="" />（0表示不限购）
                                </#if>
                                </div>
                              </div>
                              <div class="form-group">
									<label class="col-sm-2 control-label"><span class="requiredField">*</span>商品图片：</label>
									<div class="col-sm-5 title">
										<button type="button" class="btn btn-primary addNewPic">
											<i class="fa fa-plus"></i> 添加图片
										</button>
									</div>
								</div>
								<div class="form-group"><#include "/activity/pics_list.ftl" /></div>
								<div class="form-group">
									<label class="col-sm-2 control-label"><span class="requiredField">*</span>商品详情：</label>
									<div class="col-sm-8">
												<#if isReview>
												<p class="form-control-static">
													${bargainDto.description}</p>
												<#else>
												<script id="ueEditor-platform" name="description"
													type="text/plain" style="width: 100%; height: 500px;">${bargainDto.description}</script>
												</#if>
											</div>
										</div>
								<div style="text-align: center">
									<button class="btn btn-primary submitMainForm" type="button" id="saveBaseInfo">保存</button>
								</div>
                          </div>
                      </div>
                      <!-- /.tab-pane -->
                    </div>
                  		</div>
                  	</form>
                </div>
            </div>
       <!-- /.content --> 
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div>
   <!-- 选择活动商品模态框（Modal） -->
	<div class="modal fade" id="chooseProduct" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog" style="width:700px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">选择商品</h4>
				</div>
				<div class="modal-body">
					<div class="select-prd">
						<input type="text" class="procode" placeholder="商品编号" />
						<input type="text" class="proname" placeholder="商品名称" />
						<button class="btn btn-primary" id="j-selectpro" style="width:110px;">查 询</button>
					</div>
					<div class="bootstrap-table">
						<table class="table table-bordered table-hover table-striped m-table2 j-prosList">
							<thead>
								<tr>
									<th></th>
									<th>商品编号</th>
									<th>商品名称</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer" style="text-align:center;">
					<button type="button" class="btn btn-default" data-dismiss="modal"> 取 消 </button>
					<button type="button" class="btn btn-primary" id="confirmPrd"> 确 认 </button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
	</div>
</body>
<#include "/footer.ftl" />
</html>