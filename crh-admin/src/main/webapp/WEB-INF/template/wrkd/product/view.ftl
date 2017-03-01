<#assign headComponents = ["bootTable"] > 
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/pages/wrkd/product/add.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}/css/pages/wrkd/product/imageUpload.css?v=${resourceVersion}">
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
						<ul class="nav nav-tabs" id="mytabs" >
							<li class="active"><a href="#tab_1" data-toggle="tab" >商品信息</a></li>
							<li><a href="#tab_2" data-toggle="tab" >库存与规格</a></li>
							<li><a href="#tab_3" data-toggle="tab" >商品详情介绍</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<div class="box-body">
									<form id="mainForm" role="form" class="form-horizontal" productId="${(productDto.id)!}" oper="view">
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>商品名称：</label>
											<div class="col-sm-3">
												<p class="form-control-static">${(productDto.pname)!}</p>
											</div>
											<div class="col-sm-4">
												<#if productDto.isShow == "1">
													<input type="checkbox" checked="checked" id="isShow" disabled="disabled"/>
												<#else>
													<input type="checkbox" id="isShow" disabled="disabled"/>
												</#if>
												<label class="control-label">是否在商城显示</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">是否VIP商品：</label>
											<div class="col-sm-7">
												<p class="form-control-static">
													<#if productDto.vip == "0">否<#else>是</#if>
												</p>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">促销活动：</label>
											<div class="col-sm-7">
												<#if actTags??>
													<#list actTags as actTag>
														<#if productDto.actTag == actTag_index>
															<p class="form-control-static">${actTag}</p>
														</#if>
													</#list>
												</#if>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">商品特别说明：</label>
											<div class="col-sm-7">
												<p class="form-control-static">${(productDto.specialNote)!}</p>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">商品价格(元)：</label>
											<div class="col-sm-5">
												<p class="form-control-static">
												<#if productDto.originPrice??>
													#{productDto.originPrice/100;m2M2}
												</#if>
												</p>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>商品描述：</label>
											<div class="col-sm-7">
												<p class="form-control-static">${(productDto.quickDesc)!}</p>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>运费：</label>
											<div class="col-sm-5 freight-area">
												<p class="form-control-static">默认运费（元）：
													<#if defFreightPrice??>
														#{defFreightPrice/100.00;m2M2}
													</#if>
												</p>
												<#if freightMap?? && freightMap?size gt 0>
												<table class="m-table" id="region-table">
													<thead>
														<tr>
															<th class="lft">运送到</th>
															<th class="cent" width="20%">价格（元）</th>
														</tr>
													</thead>
													<tbody>
													<#list freightMap?keys as key>
														<tr>
	    													<td class='lft region-tr'>
															<#list freightMap[key] as freightDto>
																<span region-id="${freightDto.regionId}">${freightDto.regionName}</span>
																<#if freightDto_has_next>
																	、
																</#if>
	   											 			</#list>
	   											 			</td>
	    													<td class='cent' width='20%'>#{key?number/100;m2M2}</td>
	   											 		</tr>
													</#list>
													</tbody>
												</table>
												</#if>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>商品图片：</label>
											<#include "/wrkd/product/imageUpload.ftl" />
										</div>
									</form>
								</div>
							</div>
							
							<!-- 库存与规格 -->
							<div class='tab-pane' id="tab_2">
								<div class="tab_div">
									<div class="row pad">
							            <div class="col-md-12 form-horizontal">
							                <div class="form-group ">
												<label class="col-sm-2 control-label"><span class="requiredField">*</span>商品规格：</label>
												<div class="col-sm-10">
													<div class="j-box" id="specArea">
													<!-- mainSpec & subSpec-->
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label"><span class="requiredField">*</span>库存：</label>
												<div class="col-sm-10">
													<p class="guige">
														<label class="checkbox-inline">
														<#if stockDto.stockType??>
															<#if stockDto.stockType == "0"> 
																<input type="radio" name="stockType" checked value="0" disabled/> 按规格
															<#else>
																<input type="radio" name="stockType" value="0" disabled/> 按规格
															</#if>
														<#else>
															<input type="radio" name="stockType" checked value="0" disabled/> 按规格
														</#if>
														</label>
													</p>
													<table class="m-table j-table">
														<thead>
															<tr>
																<th class="lft">商品规格</th>
																<th class="cent" width="15%">价格（元）</th>
																<th class="cent" width="15%">VIP价（元）</th>
																<th class="cent" width="15%">库存</th>
																<th class="cent" width="15%">限购</th>
															</tr>
														</thead>
														<tbody id="stockInfo">
															
														</tbody>
													</table>
													<p class="guige">
														<label class="checkbox-inline">
															<#if stockDto.stockType?? && stockDto.stockType == "1">
																<input type="radio" name="stockType" checked value="1" disabled/> 按总库存
																<input type="text" id="originAmount" value="${(stockDto.restAmount)!}" disabled/>
															<#else>
																<input type="radio" name="stockType" value="1" disabled/> 按总库存
																<input type="text" id="originAmount" disabled/>
															</#if>
															限购数量
															<input type="text" placeholder="数量" id="limitAmount" onfocus="this.select()" value="${(stockDto.limitAmount)!}" disabled/>
														</label>
													</p>
												</div>
											</div>
							            </div>
        							</div>
								</div>
							</div>
							<!-- 库存与规格  end -->
							
							<!-- /.tab-pane -->
							<div class="tab-pane" id="tab_3">
								<div class="box-body">
									<form id="detailForm" role="form" class="form-horizontal" method=post>
										<div class="form-group">
											<div class="col-sm-12" id="detailDesc">
												<p class="form-control-static">
													${(productDto.detailDesc)!}
												</p>
											</div>
										</div>
									</form>
								</div>
							</div>
							<!-- /.tab-pane -->
							
						</div>
						<!-- /.tab-content -->
					</div>

					<!-- nav-tabs-custom -->
					<div class="row">
						<div class="col-sm-12 text-center">
							<button class="btn btn-success backPage" type="button"> 
								<i class="fa fa-backward"> 返回 </i>
							</button>
						</div>
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

<script id="mainSpecInitTpl" type="text/html">
{{each dataList as spec index}}
   <div class="m-box">
		<input class="form-control u-inputwidth mainSpec" value="{{spec.mainSpec}}" title="{{spec.mainSpec}}" readonly />
		<div class="subSpec clearfix">
			<ul class="clearfix fl j-selcont">
				{{each spec.subSpecs as subSpec seqNum}}
					<li>{{subSpec}}</li>
				{{/each}}
			</ul>
		</div>
	</div>
{{/each}}
</script><!-- 主规格显示 -->


<script id="tableInfo" type="text/html">
{{each firstList as value}}
	<tr>
		<td class="lft">
			<div class="spf clearfix">
				<ul {{if secondList.length == 0}} class="last-ul" {{/if}}>
					<li>
						<span title="{{value}}">{{value}}</span>
						{{if secondList.length > 0}}
						<ul {{if thirdList.length == 0}} class="last-ul" {{/if}}>
							{{each secondList as svalue}}
							<li class="last-li">
								<span title="{{svalue}}">{{svalue}}</span>
								{{if thirdList.length > 0}}
								<ul {{if fourthList.length == 0}} class="last-ul" {{/if}}>
									{{each thirdList as tvalue}}
									<li>
										<span title="{{tvalue}}">{{tvalue}}</span>
										{{if fourthList.length > 0}}
										<ul class="last-ul">
											{{each fourthList as fvalue}}
											<li>
												<span title="{{fvalue}}">{{fvalue}}</span>
											</li>
											{{/each}}
										</ul>
										{{/if}}
									</li>
									{{/each}}
								</ul>
								{{/if}}
							</li>
							{{/each}}
						</ul>
						{{/if}}
					</li>
				</ul>
			</div>
		</td>
		<td class="cent">
			<div class="u-price">
				{{each inputList as lvalue}}
					<input class="u-inpt" type="text" value='0' onfocus="this.select()"/>
				{{/each}}
			</div>
		</td>
		<td class="cent">
			<div class="u-vip-price">
				{{each inputList as lvalue}}
					<input class="u-inpt" type="text" value='0' onfocus="this.select()"/>
				{{/each}}
			</div>
		</td>
		<td class="cent">
			<div class="u-stock">
				{{each inputList as lvalue}}
					<input class="u-inpt" type="text" value='0' onfocus="this.select()"/>
				{{/each}}
			</div>
		</td>
		<td class="cent">
			<div class="u-limit">
				{{each inputList as lvalue}}
					<input class="u-inpt" type="text" value='0' onfocus="this.select()"/>
				{{/each}}
			</div>
		</td>
	</tr>  
{{/each}} 															
</script>

<script type="text/javascript" src="${uiBase}vendor/ajaxfileupload/ajaxfileupload.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}js/pages/wrkd/product/imageUpload.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}js/pages/wrkd/product/view.js?v=${resourceVersion}"></script>

</html>
