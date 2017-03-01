<#assign headComponents = ["bootTable"] > <#include "/header.ftl" />
<style>
.goodspic-list ul {
	padding-left: 1px;
}

.goodspic-upload-show img {
	width: 140px;
	height: 180px
}

.goodspic-upload-show .upload-setDefault {
	color: #dd4b39;
	font-weight: bold
}

.form-goods-pic {
	margin-left: 50px;
}

.form-control {
	width: 46%;
}
.bootstrap-dialog-body{
	height: 450px;
    overflow-y: scroll;
    overflow-x: hidden;
}
.detail-container{
	background-color: #ff6f02;
}
.des{
	background-color: #ffffff;
    margin: 0 18px;
    padding: 5px 15px;
    overflow-x: hidden;
}
.img-width{
    width:498px;
}
.dateDiv input {
	width:175px;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<div class="content-wrapper page-content-wrapper">
			<section class="content-header">

				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>砍价管理</li>
					<li class="active"><#if isReview> 查看砍价商品 <#elseif
						bargainDto.id> 编辑砍价商品 <#else> 添加砍价商品 </#if></li>
				</ol>
			</section>
			<div class="row pad">
				<div class="col-md-12">
					<!-- Custom Tabs -->
					<div class="nav-tabs-custom">
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<div class="box-body">
									<form id="mainForm" role="form" class="form-horizontal"
										method=post>
										<div class="form-group ">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>砍价商品名称:</label>
											<div class="col-sm-5">
												<#if isReview>
												<p class="form-control-static">
													${bargainDto.productName}</p>
												<#else> <input type="text" class="form-control"
													id="productName" name="productName" maxlength="50"
													placeholder="砍价商品名称" required
													value="${bargainDto.productName}"> </#if>
											</div>
											<div class="col-sm-4">
												<label class="control-label <#if !isReview>hide</#if>">参与人数（人）:${bargainDto.playerTotal}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>商品原价(元):</label>
											<div class="col-sm-5">
												<#if isReview>
												<p class="form-control-static">
													${((bargainDto.price!0)/100)?string('0.00')}</p>
												<#else> <input type="text" class="form-control" name="price"
													maxlength=8 id="price" required placeholder="原价"
													data-rule-price="true"
													value="<#if bargainDto.id>${((bargainDto.price!0)/100)?string('0.00')}</#if>"
													onchange="this.value = this.value.replace(/[０１２３４５６７８９　．]/g, function(v){if(v=='．'){return '.';}else{return v.charCodeAt(0)-65296;}});">
												</#if>
											</div>
											<div class="col-sm-4">
												<label class="control-label <#if !isReview>hide</#if>">${bargainDto.status}</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>商品底价(元):</label>
											<div class="col-sm-5">
												<#if isReview>
												<p class="form-control-static">
													${((bargainDto.basePrice!0)/100)?string('0.00')}</p>
												<#else> <input type="text" class="form-control"
													name="basePrice" maxlength=8 id="basePrice" required
													placeholder="底价" data-rule-price="true"
													value="<#if bargainDto.id>${((bargainDto.basePrice!0)/100)?string('0.00')}</#if>"
													onchange="this.value = this.value.replace(/[０１２３４５６７８９　．]/g, function(v){if(v=='．'){return '.';}else{return v.charCodeAt(0)-65296;}});">
												</#if>
											</div>
										</div>
										<div class="form-group ">
											<label class="control-label col-sm-2" for="type-select"><span
												class="requiredField">*</span>砍价有效期：</label>
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
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>砍价金额方式:</label>
											<div class="col-sm-8">
												<#if isReview>
												<p class="form-control-static"><#if
													0==bargainDto.bargainType>范围内随机<#else>固定金额</#if></p>
												<#else> <label class="checkbox-inline"
													style="padding-left: 0;"> <input type="radio"
													name="bargainType" id="bargainType" value="0"<#if
													(0 == bargainDto.bargainType) || (null == bargainDto.id)
													>checked</#if> >范围内随机
												</label> <label class="checkbox-inline" style="padding-left: 0;">
													<input type="radio" name="bargainType" id="bargainType"
													value="1"<#if 1==bargainDto.bargainType
													>checked</#if>> 固定金额
												</label> </#if>
											</div>
										</div>
										<div
											class="form-group <#if 1==bargainDto.bargainType>hide</#if>"
											id="random">
											<div class="form-group">
												<label class="col-sm-2 control-label"></label>
												<div class="col-sm-8">
													<#if !isReview><span class="control-label"
														style="padding-left: 10px; text-align: left;">请输入金额范围（元）</span>
													<#else><span class="control-label"
														style="padding-left: 10px; text-align: left;">金额范围（元）</span></#if>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label"></label>
												<div class="col-sm-3" style="padding-left: 25px;">
													<#if isReview>
													<p class="form-control-static">
														${((bargainDto.bargainMinAmount!0)/100)?string('0.00')}-${((bargainDto.bargainMaxAmount!0)/100)?string('0.00')}
													</p>
													<#else> <input class="form-control" type="text"
														id="lowPrice"
														value="<#if bargainDto.id>${((bargainDto.bargainMinAmount!0)/100)?string('0.00')}</#if>"
														maxlength=8 placeholder="最小金额" name="bargainMinAmount"><span
														class="time">-</span> <input class="form-control"
														type="text" name="bargainMaxAmount" id="bargainMaxAmount"
														value="<#if bargainDto.id>${((bargainDto.bargainMaxAmount!0)/100)?string('0.00')}</#if>"
														maxlength=8 placeholder="最大金额"></#if>
												</div>
											</div>

										</div>
										<div
											class="form-group <#if (isReview && 1==bargainDto.bargainType) || (bargainDto.id && 1==bargainDto.bargainType)>show<#else>hide</#if>"
											id="fixed">
											<div class="form-group">

												<label class="col-sm-2 control-label"></label>
												<div class="col-sm-8">
													<#if !isReview><span class="control-label"
														style="padding-left: 10px; text-align: left;">请输入金额（元）</span><#else><span
														class="control-label"
														style="padding-left: 10px; text-align: left;">金额（元）</span></#if>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label"></label>
												<div class="col-sm-3" style="padding-left: 25px;">
													<#if isReview>
													<p class="form-control-static">
														${((bargainDto.bargainAmount!0)/100)?string('0.00')}</p>
													<#else> <input class="form-control" type="text"
														id="bargainAmount"
														value="<#if bargainDto.id>${((bargainDto.bargainAmount!0)/100)?string('0.00')}</#if>"
														maxlength=11 placeholder="固定金额" name="bargainAmount"></#if>
												</div>
											</div>
										</div>
										<div class="form-group"><#include "/bargain/pics_list.ftl" /></div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>商品详情:</label>
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
											<#if !isReview>
											<button class="btn btn-primary submitMainForm" type="button"
												id="saveBaseInfo">保存</button>
											<button class="btn btn-default" type="button"
												id="preview">预览</button>
											</#if>
											<button class="btn btn-success backPage" type="button">
												<i class="fa fa-backward"> 返回 </i>
											</button>
										</div>
									</form>
								</div>
							</div>
							<!-- /.tab-pane -->
						</div>
						<!-- /.tab-content -->
					</div>

					<div class="row">
						<div class="col-sm-12 text-center"></div>
					</div>
				</div>
			</div>

			<div class="clearfix"></div>
		</div>

		<!-- /.row -->

	</div>
	<!-- ./wrapper -->

</body>
<#include "/footer.ftl" />
<script>
	var imagesList = [];
	imagesList.push({
		imageUrl : "${bargainDto.bargainImage}",
		imageId : ""
	});
	$(function() {
		var id = '${bargainDto.id}' || 0;
		var bargainInstance = bargainApp.init(id, imagesList);
		<#if isReview>
		bargainInstance.convertToReviewPage();
		</#if>
	});
</script>
<script type="text/javascript"
	src="${uiBase}js/pages/bargain/bargain_save.js?v=${resourceVersion}"></script>
<script type="text/javascript"
	src="${uiBase}vendor/ajaxfileupload/ajaxfileupload.js?v=${resourceVersion}"></script>
</html>
