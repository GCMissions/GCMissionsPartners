<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}css/pages/wrkd/product/imageUpload.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}css/pages/wrkd/group_buy.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}css/pages/advertise/advertise_list.css?v=${resourceVersion}">
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 团购管理</li>
					<li><#if isReview == "0">添加团购<#elseif isReview == "1">查看团购<#else>编辑团购</#if></li>
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
                           	  <input type="hidden" id="teamBuyId" value="${teamBuyId}" />
                           	  <input type="hidden" id="isReview" value="${isReview}" />
                              <!-- text input -->
                              <div class="form-group">
                                  <label class="col-sm-2 control-label"><span class="requiredField">*</span>活动名称：</label>
                                  <div class="col-sm-5">
                                    <#if isReview == "1">
                                    <p class="form-control-static">
                                    ${groupBuyDto.groupBuyName}
                                    </p>
                                    <#else>                                  
                                    <input type="text" class="form-control" name="productName" id="productName"  maxlength=50 value="${groupBuyDto.groupBuyName}" placeholder="活动标题" style="text-align: center;">
                                    <input type="hidden" class="form-control" name="cateName" id="cateName" value="${cateInfo.category.cateName}" >
                                    </#if>
                                  </div>
                                  <div class="col-sm-4">
                                  </div>
                              </div>
                              <div class="form-group ">
											<label class="control-label col-sm-2" for="type-select"><span
												class="requiredField">*</span>活动有效时间：</label>
											<div class="col-sm-8 timegroup">
												<#if isReview == "1">
												<p class="form-control-static">
													${groupBuyDto.startDate} 至 ${groupBuyDto.endDate}
												</p>
												<#else> <input type="hidden" data-ignore="true"
													name="stDate" id="stDate"
													value="<#if groupBuyDto.startDate>${groupBuyDto.startDate}</#if>" />
												<div class="dateDiv" style="margin-bottom: 0px;">
													<input size="10" type="text" name="effectiveStartDate"
														id="startDate" class="form-control keyword startDate"
														placeholder="开始时间" readonly
														value="<#if groupBuyDto.startDate>${groupBuyDto.startDate}</#if>">
													<span class="add-on" style="display: none"><i
														class="icon-remove"></i></span> <span class="add-on"><i
														class="icon-calendar"></i></span>
												</div>
												<span class="time">-</span> <input type="hidden"
													data-ignore="true" name="edDate" id="edDate"
													value="<#if groupBuyDto.endDate>${groupBuyDto.endDate}</#if>" />
												<div class="dateDiv" style="margin-bottom: 0px;">
													<input size="10" type="text" name="effectiveEndDate"
														id="endDate" class="form-control keyword endDate"
														placeholder="结束时间" readonly
														value="<#if groupBuyDto.endDate>${groupBuyDto.endDate}</#if>">
													<span class="add-on" style="display: none"><i
														class="icon-remove"></i></span> <span class="add-on"><i
														class="icon-calendar"></i></span>
												</div>
												</#if>
											</div>
										</div>
                              <div class="form-group">
                              		<label class="col-sm-2 control-label"><span class="requiredField">*</span>活动说明图片：</label>
                                	<div class="col-sm-6">
                                		<div class="adspic-list col-sm-3 rinput">
											<ul>
												<li class="adspic-upload ">
													<div class="upload-thumb">
														<#if groupBuyDto.groupBuyImage>
														<img src="${groupBuyDto.groupBuyImage}" id="desc_imageUrl">
														<#else>
														<img src="${base}/dist/img/default_goods_image_240.gif" id="desc_imageUrl">
														</#if>
													</div>
													<#if isReview != "1">
													<div class="upload-btn">
														<a href="javascript:void(0);"> 
														<span>
														<input type="file" hidefocus="true" size="1" class="input-file" name="file" id="desc_imageUpload" accept=".jpg,.png,.gif"></span>
														<p>
															<i class="fa fa-fw fa-upload"></i>上传
														</p>
														</a>
													</div>
													</#if>
												</li>
											</ul>
										</div>
                                	</div>
                              </div>
                              <div class="form-group">
                              		<label class="col-sm-2 control-label">活动特别说明 ：</label>
                                  	<div class="col-sm-6">
                                  	<#if isReview == "1">
                                  		${groupBuyDto.specialDesc}
                                  	<#else>
                                  		<textarea cols=120 rows=2 class="form-control" maxlength="150" style="width:400px;" placeholder="活动特别说明" id="special_desc">${groupBuyDto.specialDesc?replace('<br />','\r\n','i')}</textarea>
                                  	</#if>
                                  	</div>
                              </div>
                              <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="requiredField">*</span>活动商品：</label>
                                <div class="col-sm-10">
                                	<a href="javascript:void(0)" class="j-addpro">+选择活动商品</a>
                                	<span class="addpro">+选择活动商品</span>
                                	<div class="m-pro">
                                		<table id="pro_list">
                                			<thead><tr><td>商品编号</td><td>商品名称</td><td>操作</td></tr></thead>
                                			<tbody></tbody>
                                		</table>
                                	</div>
                                	<div id="specChooseBox"></div>
                                </div>
                              </div>
                               <div class="form-group  ">
                                <label class="col-sm-2 control-label">活动商品价格（元）：</label>
                                <div class="col-sm-5">
                                <#if isReview == "1">
		                          	<p class="form-control-static">
		                            ${groupBuyDto.totalPrice}
		                             </p>
                             	 <#else>
                                  <input type="text" id="price" class="form-control" name="price" value="${groupBuyDto.totalPrice}" placeholder="价格" style="text-align: center;"
                                   		onkeyup="value=value.replace(/[^\d.]/g,'')" maxlength="7"/>   
                             	 </#if>
                                </div>
                              </div>
                              <div class="form-group  ">
                                <label class="col-sm-2 control-label"><span class="requiredField">*</span>成团商品数量：</label>
                                <div class="col-sm-5">
                                <#if isReview == "1">
                                <p class="form-control-static">
                                ${groupBuyDto.startCount}
                                </p>
                                <#else> 
                                <input type="text" id="startCount" class="form-control"  maxlength=4 value="${groupBuyDto.startCount}" placeholder="数量" style="text-align: center;"
                                	onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/>
                                </#if>
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-sm-2 control-label">团购商品限购数量：</label>
                                <div class="col-sm-5">
                                <#if isReview == "1">
                                <p class="form-control-static">
                                ${groupBuyDto.limitCount}
                                </p>
                                <#else> 
                                <input type="text" id="limitCount" class="form-control"  maxlength=4 value="${groupBuyDto.limitCount}" placeholder="数量" style="text-align: center;"
                                	onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/>（0表示不限购）
                                </#if>
                                </div>
                              </div>
                              <div class="form-group">
									<label class="col-sm-2 control-label"><span class="requiredField">*</span>活动图片：</label>
									<div class="col-sm-10 title">
										<#if isReview != "1">
											<button type="button" class="btn btn-primary addNewPic"><i class="fa fa-plus"></i> 添加图片</button>
										</#if>
										<#include "/wrkd/groupBuy/group_buy_pic.ftl" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"><span class="requiredField">*</span>商品详情：</label>
									<div class="col-sm-8">
											<#if isReview == "1">
											<p class="form-control-static">${groupBuyDto.description}</p>
											<#else>
											<script id="ueEditor-platform" name="description" type="text/plain" style="width: 100%; height: 500px;">${groupBuyDto.description}</script>
											</#if>
										</div>
									</div>
								<div style="text-align: center">
									<#if isReview != "1">
									<button class="btn btn-primary submitMainForm" type="button" id="saveBaseInfo">保存</button>
									</#if>
									<button class="btn btn-primary backPage" type="button" id="backToHome">返回</button>
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
					<button type="button" class="btn btn-default" data-dismiss="modal" id="cancelPrd"> 取 消 </button>
					<button type="button" class="btn btn-primary" id="confirmPrd"> 确 认 </button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
	</div>
</body>
<#include "/footer.ftl" />
<script src="${uiBase}vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script type="text/javascript" src="${uiBase}js/pages/wrkd/product/imageUpload.js?v=${resourceVersion}"></script>
<script src="${uiBase}/js/pages/wrkd/groupBuy/group_buy_add.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}vendor/ajaxfileupload/ajaxfileupload.js?v=${resourceVersion}"></script>
</html>
