<#assign headComponents = ["bootTable"] > 
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}vendor/bootstrap-slider/boostrap-slider.css">
<link rel="stylesheet" href="${uiBase}css/pages/activity/activity.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}css/pages/activity/actStock_list.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}css/pages/activity/actStock_view.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<div class="content-wrapper page-content-wrapper">
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>商品管理</li>
					<li class="active">查看商品</li>
				</ol>
			</section>
			<div class="row pad">
				<div class="col-md-12">
					<!-- Custom Tabs -->
					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs" id="mytabs">
							<li class="active"><a href="#tab_1" data-toggle="tab">商品基本信息</a></li>
							<li><a href="#tab_2" data-toggle="tab">商品详情介绍</a></li>
							<li><a href="#tab_3" data-toggle="tab">库存与规格</a></li>
							<li><a href="#tab_4" data-toggle="tab">购买信息</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<div class="box-body">
									<form id="mainForm" role="form" class="form-horizontal"
										method=post>
										<input type="hidden" name="productId" value="${productDto.productId}">
										<!-- text input -->
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>商品品类:</label>
											<div class="col-sm-10">
												<p class="form-control-static">${cateInfo}</p>
											</div>
										</div>
										<div class="form-group ">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>商品名称:</label>
											<div class="col-sm-5">
												<p class="form-control-static">${productDto.productName}</p>
											</div>
											<div class="col-sm-4"></div>
										</div>

										<div class="form-group  ">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>服务商:</label>
											<div class="col-sm-5">
												<p class="form-control-static"><#list orgs as org> <#if
													productDto.orgId == org.orgId>${org.orgName }</#if>
													</#list>
												</p>											
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>是否VIP商品:</label>
											<div class="col-sm-8">
												<p class="form-control-static">
												<#if productDto.vip==0>否<#else>是</#if>
												</p>
											</div>
										</div>
										<div class="form-group  ">
											<label class="col-sm-2 control-label">商品说明:</label>
											<div class="col-sm-5">
												<p class="form-control-static">${productDto.note}</p>
											</div>
										</div>
										<div class="form-group  ">
											<label class="col-sm-2 control-label">特别说明:</label>
											<div class="col-sm-5">
												<p class="form-control-static">${productDto.rebackNote}</p>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">原价(元):</label>
											<div class="col-sm-5">
												<p class="form-control-static">
												<#if productDto.originalPrice??>
												<#assign
													tempNum=productDto.originalPrice/100>
													${tempNum?string('currency')}
												</#if>
												</p>												
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>是否需要验证码:</label>
											<div class="col-sm-8">
												<p class="form-control-static">
												<#if productDto.isCaptcha==0>是<#else>否</#if>
												</p>
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
									</form>
								</div>
							</div>
							<!-- /.tab-pane -->
							<div class="tab-pane" id="tab_2">
								<div class="box-body">
									<form id="mainForm1" role="form" class="form-horizontal"
										method=post>
										<div class="form-group">
											<div class="col-sm-10">
												<p class="form-control-static">${productDto.description}</p>
											</div>
										</div>
									</form>
								</div>
							</div>
							<!-- /.tab-pane -->
							<!-- 库存与规格 -->
							<div class='tab-pane' id="tab_3">
								<div class="tab_div">
									<div class="row pad">
							            <div class="col-md-12">
							                <div class="box box-primary j-prd" style="padding:20px;" product_id = ${productId}>
							                    <div class="maintitle">
							                        <label>查看库存情况</label>
							                        <span>
							                        	<span class="red">*</span>显示库存:
							                        	<#if showStock??>
							                        		<#if showStock == "0">
							                        			<input type="radio" name="view_stock" value="0" checked disabled/>是 
							                        			<input type="radio" name="view_stock" value="1" disabled/>否 
							                        		<#else>
							                        			<input type="radio" name="view_stock" value="0" disabled/>是 
							                        			<input type="radio" name="view_stock" value="1" checked disabled/>否 
							                        		</#if>
							                        	<#else>
							                        		<input type="radio" name="view_stock" value="0" checked disabled/>是 
							                        		<input type="radio" name="view_stock" value="1" disabled/>否 
							                        	</#if>
							                        </span>
							                    </div>
							                    <#if actStockList??>
							                        <#list actStockList as actStock>
							                            <#if actStock.stockType != "2">
							                                <div class="m-box">
							                                    <div class="title">
							                                        <label>${actStock.actDate?string("yyyy-MM-dd")}</label>
							                                        <#if actStock.stockType == "0">
							                                        	<span>( 按规格 )</span>
							                                        <#else>
							                                        	<span>( 按人数 )</span>
							                                        </#if>
							                                        <button class="btn btn-primary pull-right">查看</button>
							                                    </div>
							                                    <#if actStock.stockType == "1">
							                                    	<p class="kcnum">
							                                    		<label>库存数量：${actStock.originalCount}</label>
							                                    		<label>剩余库存数量：<span class="red">${actStock.totalCount}</span></label>
							                                    	</p>
							                                    </#if>
							                                    <div class="boxin">
							                                        <table >
							                                            <thead>
							                                            <tr>
							                                                <th>商品规格</th>
							                                                <th>库存数量</th>
							                                                <th>剩余库存量</th>
							                                            </tr>
							                                            </thead>
							                                            <tbody>
							                                           	<#if actStock.actSpecList??>
							                                           		<#list actStock.actSpecList as specDto>
							                                           		<tr>
								                                                <td><label>${specDto.subSpec}</label></td>
								                                                <#if actStock.stockType == "1">
								                                                	<td class="delete">${specDto.unitNum * specDto.groupNum}</td>
								                                                	<td class="delete"><span class="red">${specDto.total}</span></td>
								                                                <#else>
								                                                	<td>${specDto.unitNum * specDto.groupNum}</td>
								                                                	<td><span class="red">${specDto.total}</span></td>
								                                                </#if>
							                                           		</tr>
							                                           		</#list>
							                                           	</#if>
							                                            </tbody>
							                                        </table>
							                                    </div>
							                                </div>
							                            </#if>
							                            <#if actStock.stockType == "2">
							                                <div class="m-box">
							                                    <div class="title">
							                                        <label>${actStock.actDate?string("yyyy-MM-dd")}</label>
							                                        <span>( 不需要库存 )</span>
							                                        <button class="btn btn-primary pull-right">查看</button>
							                                    </div>
							                                    <div class="boxin">
							                                        <p class="nostock">该活动日期无需库存</p>
							                                    </div>
							                                </div>
							                            </#if>
							                        </#list>
							                    </#if>
							                </div>
							            </div>
        							</div>
								</div>
							</div>
							<!-- 库存与规格  end -->

							<!-- 购买信息 -->
							<div class='tab-pane' id="tab_4">
								<div class="tab_div">
									<!-- form -->
									<form role="form">
										<div class="form-group row" style="display:none;">
											<div class="col-sm-2" style="text-align: right;">
												<label><span class="requiredField">*</span>每人限购:</label>
											</div>
											<div class="col-sm-4" >
												<input type="text" class="form-control" value="0" id="limitNum"/> <span>（0表示不限购）</span>
											</div>
										</div>
										<div class="form-group row">
											<div class="col-sm-2" style="text-align: right;">
												<label><span class="requiredField">*</span>用户必填信息:</label>
											</div>
											<div class="col-sm-9 requireInfo" style="line-height: 32px;">
												<div>
													<#if reqFieldList??>
														<#list reqFieldList as reqField>
															<input type="checkbox" value="${reqField}" disabled/>${reqField}
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
												<div style="background-color:#f2f2f2;margin-left:20px;">
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
											<div class="col-sm-6 deadlineInfo" style="margin-left: 18px;">
												<p class="form-control-static"></p>
											</div>
										</div>
										<div class="form-group row">
											<div class="col-sm-2" style="text-align: right;">
												<label><span class="requiredField">*</span>开售时间:</label>
											</div>
											<div class="col-sm-10 sale_time">
												<input type="radio" name="sale_type" id="right_sale" value="0" checked>立即开售
												<input type="radio" name="sale_type" id="ontime_sale" value="1"> 定时开售 
												<input type="text" id="sale_start" class="form-control keyword" style="margin-left:17px;" placeholder="开始时间" readonly>
												<input type="text" id="sale_end" class="form-control keyword" placeholder="结束时间" readonly>
												<span class="dateEnd_tip">（不设定表示该商品一直有效）</span>
											</div>
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
							<#if isReview>
							<button class="btn btn-success backPage" type="button"> <i class="fa fa-backward"> 返回 </i>
							</button>
							</#if>
						</div>
					</div>
				</div>
			</div>

			<div class="clearfix"></div>
		</div>

		<!-- /.row -->

	</div>
	<!-- ./wrapper -->
	
	<!-- 库存查看模态框（Modal） -->
	<div class="modal fade" id="stockDialog" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:1000px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">编辑库存</h4>
				</div>
				<div class="modal-body">
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
										<input class="form-control" value="人数" readonly>
										<div class="subSpec clearfix">
											<ul class="clearfix">
											</ul>
											<div class="selSubSpec">
												<input type="text" onfocus="this.select()"/>
												<button type="button" class="btn btn-primary" id="addSubSpecConfirm">确定</button>
												<button type="button" class="btn btn-primary" id="addSubSpecCancel">取消</button>
											</div>
										</div>
									</div>
								</div>
								<div class="selMainSpec">
									<select class="form-control">
										<option disabled="disabled">人数</option>
										<option>课程</option>
										<option>活动</option>
										<option>规格</option>
										<option>款式</option>
										<option>版本</option>
										<option>套餐</option>
										<option>尺寸</option>
										<option>系列</option>
									</select>
									<button type="button" class="btn btn-primary" id="addMainSpecConfirm">确定</button>
									<button type="button" class="btn btn-primary" id="addMainSpecCancel">关闭</button>
								</div>
								<button type="button" class="btn btn-primary" id="addMainSpec">添加主规格</button>
							</div>
						</div>

						<div class="form-group row">
							<div class="col-sm-2" style="text-align: right;">
								<label><span class="requiredField">*</span>库存:</label>
							</div>
							<div class="col-sm-10">
								<div class="table-responsive" style="background-color:#d7d7d7;">
									<table class="table">
										<thead>
											<tr>
												<th>商品规格</th>
												<th style="text-align: center;">活动库存</th>
												<th style="text-align:right;padding-right:10px;">价格（元）& VIP价（元）& 限购（份）</th>
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
								<label class="checkbox-inline" style="padding-left: 5px;"> 
									<input type="radio" name="stockType" id="sepc_stock" value="0" checked> 按规格
								</label> 
								<label class="checkbox-inline">
									<input type="radio" name="stockType" id="total_stock" value="1"> 按人数 
									<input type="text" id="modify_num" autocomplete="off" style="display:none;" onfocus="this.select()"/> 
									<a href="javascript:void(0)" id="modifyStock" style="display:none;">修改</a>
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
					</form>
					<!-- form end -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关&nbsp;&nbsp;闭</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
	</div>
	<!-- /.modal -->
	<!-- 库存查看模态框（Modal） -->
	
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
	$(function() {
		var productId = '${productDto.productId}' || 0;
		var productInstance = productApp.init(productId, imagesList);
		<#if isReview>
		productInstance.convertToReviewPage();
		</#if>
	});
</script>

<script id="mainSpecTpl" type="text/html">
<div>
	<input class="form-control" value="{{mainSpec}}" readonly>
	<div class="subSpec clearfix"> 
		<ul class="clearfix">
		{{if subSpecList}}
			{{each subSpecList}}
				<li>{{$value}}</li>
			{{/each}}
		{{/if}}
		</ul>
		<div class="selSubSpec">
			<input type="text" onfocus="this.select()"/>
			<button type="button" class="btn btn-primary" id="addSubSpecConfirm">确定</button>
			<button type="button" class="btn btn-primary" id="addSubSpecCancel">取消</button>
		</div>
	</div>
</div>
</script><!-- 添加主规格-->

<script id="subSpecTpl" type="text/html">
{{if subSpecList}}
	{{each subSpecList}}
		<li>{{$value}}</li>
	{{/each}}
{{/if}}
</script><!-- 添加子规格-->

<script id="stockInfoCopyTpl" type="text/html">
{{each actSpecList}}
	<tr subspec="{{$value.subSpec}}">
		<td style="vertical-align: middle;">
			<div class="spec_area form-group">
				<label>{{$value.subSpec}}</label>
				<div>
					<label>最小单位量:</label>
					<input type="text" class="form-control unit_num" value="{{$value.unitNum}}" />
					<span>×</span>
					<input type="text" class="form-control group_num" value="{{$value.groupNum}}" />
				</div>
			</div>
		</td>
		<td style="vertical-align: middle;">
			<div class="num_area">
				<span>{{$value.unitNum * $value.groupNum}}</span>
			</div>
		</td>
		<td style="vertical-align: middle;">
			<div class="price_area">
				{{if $value.priceDesc.price[0].subSpec}}
					<ul>
					{{each $value.priceDesc.price as data index}}
						<li subspec="{{data.subSpec}}">
							<span>{{data.subSpec}}</span>
							{{if data.price[0].subSpec }}
								<ul>
								{{each data.price as value seqNum}}
									<li subspec="{{value.subSpec}}">
										<span>{{value.subSpec}}</span>
										{{if value.limit}}
											<input type="text" class="limit" value="{{value.limit}}"/>
										{{else}}
											<input type="text" class="limit" value="0" "/>
										{{/if}}
										{{if value.vipPrice}}
											<input type="text" class="vip-price" value="{{value.vipPrice}}" />
										{{else}}
											<input type="text" class="vip-price" value="0" />
										{{/if}}
										<input type="text" class="price" value="{{value.price}}" />
									</li>
								{{/each}}
								</ul>
							{{else}}
								{{if data.limit}}
									<input type="text" class="limit" value="{{data.limit}}" />
								{{else}}
									<input type="text" class="limit" value="0" />
								{{/if}}
								{{if data.vipPrice}}
									<input type="text" class="vip-price" value="{{data.vipPrice}}"/>
								{{else}}
									<input type="text" class="vip-price" value="0" />
								{{/if}}
								<input type="text" class="price" value="{{data.price}}" />
							{{/if}}
						</li>
					{{/each}}
					</ul>
				{{else}}
					{{if $value.priceDesc.limit}}
						<input type="text" class="limit" value="{{$value.priceDesc.limit}}" />
					{{else}}
						<input type="text" class="limit" value="0" />
					{{/if}}	
					{{if $value.priceDesc.vipPrice}}
						<input type="text" class="vip-price" value="{{$value.priceDesc.vipPrice}}" />
					{{else}}
						<input type="text" class="vip-price" value="0" />
					{{/if}}					
					<input type="text" class="price" value="{{$value.priceDesc.price}}" />
				{{/if}}
			</div>
		</td>
	</tr>
{{/each}}
</script><!-- 第一个主规格添加子规格 复制已有商品信息 -->

<script id="partakeInfoTpl" type="text/html">
{{if actPartakes}}
	{{each actPartakes}}
	<tr>
		<td>
			<span class="sub-spec">{{$value.subSpec}}</span><span>×</span>
			<input type="text" class="form-control" value="{{$value.unitNum}}" readonly/>
		</td>
		<td style="vertical-align: middle;">
			{{if $value.enabled}}
				<input type="checkbox" value="1" disabled checked/>
			{{else}}
				<input type="checkbox" value="1" disabled/>
			{{/if}}
			<span>启用</span>
		</td>
	</tr>
	{{/each}}
{{/if}}
</script><!-- 参与人数信息 -->

<script src="${uiBase}vendor/bootstrap-slider/bootstrap-slider.min.js"></script>
<script type="text/javascript" src="${uiBase}js/pages/activity/actStock_view.js?v=${resourceVersion}"></script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script type="text/javascript" src="${uiBase}js/pages/activity/activity_view.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}vendor/ajaxfileupload/ajaxfileupload.js?v=${resourceVersion}"></script>

</html>
