<#assign headComponents = ["bootTable"] > 
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}vendor/bootstrap-slider/boostrap-slider.css">
<link rel="stylesheet" href="${uiBase}css/pages/activity/activity.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}css/pages/activity/actStock_add.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<div class="content-wrapper page-content-wrapper">
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>商品管理</li>
					<li class="active">添加商品 </li>
				</ol>
			</section>
			<div class="row pad">
				<div class="col-md-12">
					<!-- Custom Tabs -->
					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs" id="mytabs">
							<li id="base-li" class="active">
								<a href="#tab_1" id="base-a">商品基本信息</a>
							</li>
							<li id="detail-li">
								<a href="#tab_2" id="detail-a">商品详情介绍</a>
							</li>
							<li id="ss-li"><a href="#tab_3"  id="ss-a">库存与规格</a></li>
							<li><a href="#tab_4"  id="bi-a">购买信息</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<div class="box-body">
									<form id="mainForm" role="form" class="form-horizontal" method=post>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>商品品类:</label>
											<div class="col-sm-10">
												<select name="firstCategory" title="一级商品品类" class="selectpicker form-control" id="firstCategory" required> 
												<#list cates as cate>
													<option value="${cate.cateId}" data-hasSon="${cate.hasSon}">${cate.cateName}</option>
												</#list>
												</select> 
												<span class="time">-</span> 
												<select title="二级商品品类" name="secondCategory" class="selectpicker form-control"
													id="secondCategory" data-rule-checkCategory="true" value="">
												</select> 
												<label id="secondCategory-error" class="hidden" for="secondCategory" style="color:#ee0000;">商品品类设定后不可修改</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>商品名称:</label>
											<div class="col-sm-5">
												<input type="text" class="form-control"
													id="productName" name="productName" maxlength="50"
													placeholder="商品名称" required
													value=""> 
											</div>
											<div class="col-sm-4"></div>
										</div>

										<div class="form-group">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>服务商:</label>
											<div class="col-sm-5">
												<select title="服务商" name="orgId" id="orgId" class="selectpicker form-control" required> 
													<#list orgs as org>
													<option value="${org.orgId}">${org.orgName }</option>
													</#list>
												</select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>是否VIP商品:</label>
											<div class="col-sm-8">
												<label class="checkbox-inline"> 
													<input type="radio" name="isVip" value="1" > 是
												</label> 
												<label class="checkbox-inline"> 
													<input type="radio" name="isVip" value="0" checked>否
												</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">商品说明:</label>
											<div class="col-sm-5">
												<textarea id="note" name="note" cols=220 rows=4
													class="form-control" maxlength="100" style="width: 400px"
													placeholder="商品简单介绍"></textarea>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">特别说明:</label>
											<div class="col-sm-5">
												<textarea id="rebackNote" name="rebackNote" cols=220 rows=5
													class="form-control" style="width: 400px"
													placeholder="添加关于该商品相关的一些特别说明内容"></textarea>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">原价(元):</label>
											<div class="col-sm-5">
												<input type="text" class="form-control"
													name="originalPrice" maxlength=8 id="originalPrice"
													placeholder="原价" data-rule-price="true"
													value="" onchange="this.value = this.value.replace(/[０１２３４５６７８９　．]/g, function(v){if(v=='．'){return '.';}else{return v.charCodeAt(0)-65296;}});">
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>是否需要验证码:</label>
											<div class="col-sm-8">
												<label class="checkbox-inline"> 
													<input type="radio" name="isCaptcha" id="isCheckCode" value="0" checked>是
												</label> 
												<label class="checkbox-inline"> 
													<input type="radio" name="isCaptcha" id="noCheckCode" value="1"> 否
												</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>商品图片:</label>
											<div class="col-sm-5 title">
												<button type="button" class="btn btn-primary addNewPic">
													<i class="fa fa-plus"></i> 添加图片
												</button>
											</div>
										</div>
										<div class="form-group"><#include "/activity/pics_list.ftl" /></div>
										<div style="text-align: center">
											<button class="btn btn-primary submitMainForm" type="button"
												id="saveBaseInfo">下一步</button>
											<button class="btn btn-primary" id="copy-product"
												type="button">复制已有商品信息</button>
											<button class="btn btn-success" type="button" name="save-reback">
												<i class="fa fa-backward"> 返回 </i>
											</button>
										</div>
									</form>
								</div>
							</div>
							<!-- /.tab-pane -->
							<div class="tab-pane" id="tab_2">
								<div class="box-body">
									<form id="mainForm1" role="form" class="form-horizontal"
										method=post>
										<div class="form-group">
											<div class="col-sm-12">
												<script id="ueEditor-platform" name="description" type="text/plain"
													style="width: 100%; height: 500px;"></script>
											</div>
										</div>
										<div style="text-align: center">
											<button class="btn btn-primary submitMainForm" type="button"
												id="saveDetailInfo">下一步</button>
											<button class="btn btn-success" type="button" id="save-reback">
												<i class="fa fa-backward"> 返回 </i>
											</button>
										</div>
									</form>
								</div>
							</div>
							<!-- /.tab-pane -->
							<!-- 库存与规格 -->
							<div class='tab-pane' id="tab_3">
								<div class="tab_div">
									<!-- form -->
									<form role="form">
										<div class="form-group row">
											<div class="col-sm-2" style="text-align: right;">
												<label><span class="requiredField">*</span>商品活动日期:</label>
											</div>
											<div class="col-sm-10 actDate">
												<a id="addActDate" href="javascript:void(0);" target="_self">+添加活动日期</a>
												<ul class="clearfix">

												</ul>
											</div>
										</div>

										<div class="form-group row">
											<div class="col-sm-2" style="text-align: right;">
												<label><span class="requiredField">*</span>商品规格:</label>
											</div>
											<div class="col-sm-10">
												<div class="mainSpec">
													<div>
														<input class="form-control" value="人数" readonly />
														<div class="subSpec clearfix">
															<ul class="clearfix">
															</ul>
															<div class="selSubSpec">
																<input type="text" onfocus="this.select()"/>
																<button type="button" class="btn btn-primary" id="addSubSpecConfirm">确定</button>
																<button type="button" class="btn btn-primary" id="addSubSpecCancel">取消</button>
															</div>
															<a href="javascript:void(0);">+添加</a>
														</div>
													</div>
												</div>
												<div class="selMainSpec">
													<select class="form-control">
													<#if mainSpecList??>
														<#list mainSpecList as mainSpec>
															<#if mainSpec_index == 0>
																<option disabled="disabled">${mainSpec}</option>
															<#else>
																<option>${mainSpec}</option>
															</#if>
														</#list>
													</#if>
													</select>
													<button type="button" class="btn btn-primary" id="addMainSpecConfirm">确定</button>
													<button type="button" class="btn btn-primary" id="addMainSpecCancel">关闭</button>
												</div>
												<button type="button" class="btn btn-primary" id="addMainSpec">添加主规格</button>
											</div>
										</div>

										<div class="form-group row" style="width:1090px;">
											<div class="col-sm-2" style="text-align: right;">
												<label><span class="requiredField">*</span>库存:</label>
											</div>
											<div class="col-sm-10" style="background-color:#d7d7d7;max-width:80%;margin-left: 15px;">
												<div class="table-responsive">
													<table class="table">
														<thead>
															<tr>
																<th>商品规格</th>
																<th style="text-align: center;">活动库存</th>
																<th style="text-align:right;padding-right:10px;">价格（元）& VIP价（元）限购（份）</th>
															</tr>
														</thead>
														<tbody class="stockInfo">

														</tbody>
													</table>
												</div>
											</div>
										</div>
										<div class="form-group row">
											<div class="col-sm-2"></div>
											<div class="col-sm-8">
												<label class="checkbox-inline" style="padding-left:5px;"> 
													<input type="radio" name="stockType" id="sepc_stock" value="0" checked> 按规格
												</label> 
												<label class="checkbox-inline"> 
													<input type="radio" name="stockType" id="total_stock" value="1"> 按人数 
													<input type="text" id="modify_num"  prev-value="0" style="display:none;" onfocus="this.select()"/> 
												</label> 
												<label class="checkbox-inline"> 
													<input type="radio" name="stockType" id="no_stock" value="2"> 该商品不需要库存
												</label>
											</div>
										</div>
										<div class="form-group row">
											<div class="col-sm-2"></div>
											<div class="col-sm-8" style="padding-left:0px;">
												<label class="checkbox-inline"> 
													<span class="requiredField">*</span>显示库存
												</label> 
												<label class="checkbox-inline"> 
													<input type="radio" name="show_stock" id="show_stock" value="0" checked>是
												</label> 
												<label class="checkbox-inline"> 
													<input type="radio" name="show_stock" id="hide_stock" value="1"> 否
												</label>
											</div>
										</div>
										<div style="text-align: center">
											<button type="button" class="btn btn-primary btn-save" id="saveStockInfo">下一步</button>
											<button class="btn btn-success" id="back_tab2" type="button">
												<i class="fa fa-backward"> 返&nbsp;回 </i>
											</button>										
										</div>
									</form>
									<!-- form end -->
								</div>
							</div>
							<!-- 库存与规格  end -->

							<!-- 购买信息 -->
							<div class='tab-pane' id="tab_4">
								<div class="tab_div">
									<!-- form -->
									<form role="form">
										<div class="form-group row">
											<div class="col-sm-2" style="text-align: right;">
												<label><span class="requiredField">*</span>用户必填信息:</label>
											</div>
											<div class="col-sm-9 requireInfo" style="line-height: 32px;">
												<div>
													<#if reqFieldList??>
														<#list reqFieldList as reqField>
															<#if reqField_index == 0>
																<input type="checkbox" value="${reqField}" checked/>${reqField}
															<#else>
																<input type="checkbox" value="${reqField}" />${reqField}
															</#if>
														</#list>
													</#if>
												</div>
											</div>
										</div>
										<div class="form-group row">
											<div class="col-sm-2" style="text-align: right;line-height:32px;">
												<label><span class="requiredField">*</span>活动参与人数:</label>
											</div>
											<div class="col-sm-6 partakeInfo">
												<div style="background-color:#f2f2f2;">
													<table class="table">
														<thead>
															<tr>
																<th>参与人数</th>
																<th>份数控制</th>
															</tr>
														</thead>
														<tbody>
															
														</tbody>
													</table>
												</div>
											</div>
										</div>
										<div class="form-group row">
											<div class="col-sm-2" style="text-align: right;line-height:32px;">
												<label>截止下单提前天数:</label>
											</div>
											<div class="col-sm-6 deadlineInfo">
												<span><i class="fa fa-minus"></i></span>
												<input type="text" value="0" class="form-control" readonly/>
												<span><i class="fa fa-plus"></i> &nbsp;（为0表示下单时间不提前截止）</span>
											</div>
										</div>
										<div class="form-group row">
											<div class="col-sm-2" style="text-align: right;">
												<label><span class="requiredField">*</span>开售时间:</label>
											</div>
											<div class="col-sm-10 sale_time">
												<label class="checkbox-inline" style="padding-left: 10px;"> 
													<input type="radio" name="sale_type" id="right_sale" value="0" style="width: 30px;" checked>立即开售
												</label> 
												<label class="checkbox-inline"> 
												<input type="radio" name="sale_type" id="ontime_sale" value="1" style="width: 30px;"> 定时开售
												</label>
												<div class="dateDiv" style="margin-bottom: 0px; margin-left: 10px">
													<input size="10" type="text" id="sale_start" class="form-control keyword" placeholder="开始时间" readonly>
													<span class="add-on" style="display: none"><i class="icon-remove"></i></span> 
													<span class="add-on"><i class="icon-calendar"></i></span>
												</div>
												<div class="dateDiv" style="margin-bottom: 0px; margin-left: 20px">
													<input size="10" type="text" id="sale_end" class="form-control keyword" placeholder="结束时间" readonly>
													<span class="add-on" style="display: none"><i class="icon-remove"></i></span> 
													<span class="add-on"><i class="icon-calendar"></i></span>
												</div>
												<span class="dateEnd_tip">（不设定表示该商品一直有效）</span>
											</div>
										</div>
										<div style="text-align: center">
											<button type="button" class="btn btn-primary btn-save" id="savePurchaseInfo">完&nbsp;成</button>
											<button class="btn btn-success" id="back_tab3" type="button">
												<i class="fa fa-backward"> 返&nbsp;回</i>
											</button>
										</div>
									</form>
									<!-- form end -->
								</div>
							</div>
						</div>
						<!-- /.tab-content -->
					</div>

					<!-- nav-tabs-custom -->
					<div class="row">
						<div class="col-sm-12 text-center">
						</div>
					</div>
				</div>
			</div>

			<div class="clearfix"></div>
		</div>
		<!-- /.row -->

	</div>
	<!-- ./wrapper -->

	<!-- 活动时间选择模态框（Modal） -->
	<div class="modal fade" id="dateDialog" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">添加活动日期</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal">
						<div class="box-body nav-tabs-custom">
							<!-- tab 页标题 -->
							<div>
								<ul class="nav nav-tabs" id="dateTab">
									<li class="active"><a href="#time_1" data-toggle="tab">日期</a></li>
									<li><a href="#time_2" data-toggle="tab">时间段</a></li>
								</ul>
								<br class="clearfix" />
							</div>

							<!-- TAB页 -->
							<div class="tab-content">
								<!-- 日期 -->
								<div class='tab-pane active' id="time_1">
									<div class="tab_div">
										<div class="box-body form-horizontal">
											<div class="form-group row">
												<label class="col-sm-2 control-label">月份</label>
												<div class="col-sm-10">
													<input id="monthSlider" type="text" data-slider-min="1" data-slider-max="12" data-slider-step="1"
														data-slider-value="3" />
												</div>
											</div>
											<div class="form-group row">
												<label class="col-sm-2 control-label">日期</label>
												<div class="col-sm-10">
													<input id="daySlider" type="text" data-slider-min="1" data-slider-max="30" data-slider-step="1"
														data-slider-value="3" />
												</div>
											</div>
										</div>
									</div>
								</div>
								<!-- 日期 end -->
								<!-- 时间段 -->
								<div class='tab-pane' id="time_2">
									<div class="tab_div">
										<div class="box-body form-horizontal">
											<div class="form-group row">
												<label class="col-sm-3 control-label">开始日期</label>
												<div class="col-sm-9 dateDiv" style="margin-bottom: 0px;">
													<input size="25" type="text" id="act_start" class="form-control keyword" readonly> 
													<span class="add-on" style="display: none"><i class="icon-remove"></i></span> 
													<span class="add-on"><i class="icon-calendar"></i></span>
												</div>
											</div>
											<div class="form-group row">
												<label class="col-sm-3 control-label">结束日期</label>
												<div class="col-sm-9 dateDiv" style="margin-bottom: 0px;">
													<input size="25" type="text" id="act_end" class="form-control keyword" readonly> 
													<span class="add-on" style="display: none"><i class="icon-remove"></i></span> 
													<span class="add-on"><i class="icon-calendar"></i></span>
												</div>
											</div>
										</div>
									</div>
								</div>
								<!-- 时间段 end -->
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="addActDateComfirm">确定</button>
					<button type="button" class="btn btn-default" id="addActDateCancle">关闭</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
	</div>
	<!-- /.modal -->
	<!-- 活动时间选择模态框（Modal） -->
	
	<!-- 通用消息提示框 -->
	<div class="modal fade" id="alertMsgDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	</div>
	<!-- /.modal -->

</body>
<#include "/footer.ftl" />
<script>
	var imagesList = [];
	<#list productDto.listImages as image>
	imagesList.push({
		imageUrl : "${image.imageUrl}",
		imageKey : "${image.imageKey}",
		imageId : "${image.imageId}"
	});
	</#list>
	var productImage = "${productDto.image}";
	var actStockInstance;
	$(function() {
		var productId = '${productDto.productId}' || 0;
		var productInstance = productApp.init(productId, imagesList);
		actStockInstance = actStock.init();
	});
</script>

<#include "/activity/act_template.ftl" />

<script src="${uiBase}vendor/bootstrap-slider/bootstrap-slider.min.js"></script>
<script type="text/javascript" src="${uiBase}js/pages/activity/actStock_add.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}js/pages/activity/actStock_util.js?v=${resourceVersion}"></script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script type="text/javascript" src="${uiBase}js/pages/activity/activity_save.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}vendor/ajaxfileupload/ajaxfileupload.js?v=${resourceVersion}"></script>

</html>
