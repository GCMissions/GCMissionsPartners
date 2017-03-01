<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/pages/wrkd/product/imageUpload.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}/css/pages/wrkd/bargain/bargain_add.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<div class="content-wrapper page-content-wrapper">
			<section class="content-header">

				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>24小时管理</li>
					<li class="active"><#if isReview> 查看24小时活动 <#elseif
						bargainDto.id> 编辑24小时活动 <#else> 添加24小时活动 </#if></li>
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
												class="requiredField">*</span>活动名称：</label>
											<div class="col-sm-5">
												<#if isReview>
												<p class="form-control-static">
													<input type="text" class="form-control"
													id="name" name="name" maxlength="50"
													placeholder="活动名称" required readonly="readonly"
													value="${bargainDto.name}">
												<#else> <input type="text" class="form-control"
													id="name" name="name" maxlength="50"
													placeholder="活动名称" required
													value="${bargainDto.name}"> </#if>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>活动说明图片:</label>
											<div class="col-sm-7">
												<div class="desc-image goodspic-upload">
													<div class="upload-thumb">
														<#if isReview || isEdit>
													         <img src="${(bargainDto.explainInfoPics.imageUrl)!}"  id="img_50">
														<#else>
															<img src="${uiBase}img/default_goods_image_240.gif"  id="img_50">
														</#if>
														<input type="hidden" name="picKey[50]" value="${(bargainDto.explainInfoPics.imageKey)!}" />
														<input type="hidden" name="picId[50]" value="${(bargainDto.explainInfoPics.imageId)!}" />
													    <input type="hidden" name="picUrl[50]" value="${(bargainDto.explainInfoPics.imageUrl)!}" />
												     </div>
												     <#if !isReview>
													     <div class="upload-desc-img">
														     <a href="javascript:void(0);">
															    <span><input type="file" hidefocus="true" size="1" class="input-file" name="file" id="50"></span>
															    <p><i class="fa fa-fw fa-upload"></i>上传</p>
														     </a>
														 </div>
														 <div class="remove-desc-img">
														     <a href="javascript:void(0);" disabled>
															    <p><i class="fa fa-fw fa-times"></i>删除</p>
														     </a>
														 </div>
													 </#if>
												</div>
											</div>
										</div>
										<div class="form-group">
		                              		<label class="col-sm-2 control-label">活动特别说明 ：</label>
		                                  	<div class="col-sm-10">
			                                  	<#if isReview == "1">
			                                  		${bargainDto.specialDesc}
			                                  	<#else>
			                                  		<textarea rows="5" cols="12" class="form-control" maxlength="500"  placeholder="活动特别说明" id="specialDesc" name="specialDesc">${bargainDto.specialDesc?replace('<br />','\r\n','i')}</textarea>
			                                  	</#if>
		                                  	</div>
                              			</div>
                              			<div class="form-group ">
											<label class="col-sm-2 control-label" title="说明：该说明位置将体现在'支付完成弹框内'"><span
												class="requiredField">*</span>资助说明：</label>
											<div class="col-sm-10">
												<#if isReview>
													<p class="form-control-static">${bargainDto.supportDesc}</p>
												<#else>
													<textarea  rows="5" cols="12" class="form-control" id="supportDesc" name="supportDesc" placeholder="资助说明" required>${bargainDto.supportDesc?replace('<br />','\r\n','i')}</textarea> </#if>
												<div ><span style="color: red;">  (说明：该说明位置将体现在“支付完成弹框内”)</span></div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">统一活动商品底价(元)：</label>
											<div class="col-sm-5">
												<#if isReview>
													<p class="form-control-static">
														<#if bargainDto.specType == '0'>
															<#if bargainDto.basePrice??>
																<#assign tempNum = bargainDto.basePrice/100 />
																#{tempNum;m2M2}
															</#if>
														<#else>
															暂无，请见关联规格商品的底价。
														</#if>
													</p>
												<#else> 
													<input type="text" class="form-control"
													name="basePrice"  id="basePrice" value="${((bargainDto.basePrice/100)?string('0.00'))!}"
													placeholder="价格" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"  onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" data-rule-price="true" maxlength="8">
												</#if>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>砍价金额方式：</label>
											<div class="col-sm-8">
												<#if isReview>
													<p class="form-control-static">
														<#if 0==bargainDto.bargainType>范围内随机<#else>固定金额</#if>
													</p>
												<#else> 
													<label class="checkbox-inline"
														style="padding-left: 0;"> <input type="radio"
														name="bargainType" id="bargainType" value="0"<#if
														(0 == bargainDto.bargainType) || (null == bargainDto.id)
														>checked</#if> >范围内随机
													</label> <label class="checkbox-inline" style="padding-left: 0;">
														<input type="radio" name="bargainType" id="bargainType"
														value="1"<#if 1==bargainDto.bargainType
														>checked</#if>> 固定金额
													</label> 
												</#if>
											</div>
										</div>
										<div class="form-group <#if 1==bargainDto.bargainType || bargainDto.specType == 1>hide</#if>"
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
														maxlength=8 onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"  onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="最小金额" name="bargainMinAmount"><span
														class="time">-</span> <input class="form-control"
														type="text" name="bargainMaxAmount" id="bargainMaxAmount"
														value="<#if bargainDto.id>${((bargainDto.bargainMaxAmount!0)/100)?string('0.00')}</#if>"
														maxlength=8 onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"  onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="最大金额"></#if>
												</div>
											</div>
										</div>
										<div class="form-group <#if bargainDto.specType == 0 && bargainDto.bargainType == 1>show<#else>hide</#if>"
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
														maxlength=8 onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"  onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="固定金额" name="bargainAmount"></#if>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>活动商品：</label>
											<div class="col-sm-10">
												<#if !isReview>
				                                	<a href="javascript:void(0)" class="j-addpro">+选择活动商品</a>
				                                	<span class="addpro">+选择活动商品</span>
												</#if>
												<!-- 商品id -->
												<input name="tfId" id="tfId" value="${bargainDto.id}"  style="display: none;" />
												<input name="productId" id="productId" value="${bargainDto.productId}"  style="display: none;" />
												<input name="proPriceRange" id="proPriceRange" value="${bargainDto.priceRange}"  style="display: none;" />
												<input name="pName" id="pName" value="${bargainDto.pName}"  style="display: none;" />
												<input name="pCode" id="pCode" value="${bargainDto.pCode}"  style="display: none;" />
			                                	<input name="isReview" id="isReview" value="${isReview}" style="display: none;" />
			                                	<input name="productOnTime" id="productOnTime" value="${(saleEntity.onTime?string('yyyy-MM-dd HH:mm'))!}" style="display: none;" />
			                                	<input name="productOffTime" id="productOffTime" value="${(saleEntity.offTime?string('yyyy-MM-dd HH:mm'))!}" style="display: none;" />
			                                	<div class="m-pro">
			                                		<table>
			                                			<thead>
			                                				<tr>
			                                					<td>商品编号</td>
			                                					<td>商品名称</td>
			                                					<td>上架时间</td>
																<td>下架时间</td>
																<td>价格范围</td>
			                                					<#if !isReview>
			                                						<td>操作</td>
			                                					</#if>
			                                				</tr>
			                                			</thead>
			                                			<tbody></tbody>
			                                		</table>
			                                	</div>
			                                	<div id="specChooseBox"></div>
			                                </div>
										</div>
										<div class="form-group ">
											<label class="control-label col-sm-2" for="type-select"><span
												class="requiredField">*</span>活动有效时间：</label>
											<div class="col-sm-10 timegroup">
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
												<div style="display: none;width: 200px;"  id="queryTf" >
													<a href="javascript:void(0)" class="j-queryTf">+查询关联相同商品的活动</a>
												</div>
												</#if>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>活动图片：</label>
											<div class="col-sm-10 title">
												<button type="button" class="btn btn-primary addNewPic">
													<i class="fa fa-plus"></i> 添加图片
												</button>
												<#include "/wrkd/bargain/pics_list.ftl" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span
												class="requiredField">*</span>活动详情：</label>
											<div class="col-sm-8">
												<#if isReview>
												<p class="form-control-static">
													<script id="ueEditor-platform" name="description" 
													type="text/plain" style="width: 100%; height: 500px;">${bargainDto.description}</script>
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
											</#if>
											<input name="specType" id="specType" value="${bargainDto.specType}"  style="display: none;" />
											<input name="status" id="status" value="${bargainDto.status}"  style="display: none;" />
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
						<input type="text" placeholder="商品编号" class="procode" />
						<input type="text" placeholder="商品名称" class="proname" />
						<button class="btn btn-default" id="j-selectpro" style="width:110px;">查 询</button>
					</div>
					<div class="bootstrap-table">
						<table class="table table-bordered table-hover table-striped m-table2 j-prosList">
							<thead>
								<tr>
									<th></th>
									<th>商品编号</th>
									<th>商品名称</th>
									<th>上架时间</th>
									<th>下架时间</th>
									<th>价格范围</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
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
	<!-- /.modal -->
	<!-- 选择活动商品模态框（Modal） -->
	
	<!-- 通用消息提示框 -->
	<div class="modal fade" id="alertMsgDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	</div>
	<!-- /.modal -->
	
	<!-- 24活动模态框（Modal） -->
	<div class="modal fade" id="queryTfModel" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog" style="width:700px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="showTfTitle">关联相同商品的24小时活动列表</h4>
				</div>
				<div class="modal-body">
					<div class="bootstrap-table">
						<div class="select-tf hide">
							<input type="text"  class="tf-onTime" />
							<input type="text"  class="tf-offTime" />
							<button class="btn btn-default" id="j-selectTf" style="width:110px;">查 询</button>
						</div>
						<table class="table table-bordered table-hover table-striped m-table2 j-tfList">
							<thead>
								<tr>
									<th></th>
									<th>活动名称</th>
									<th>开始时间</th>
									<th>结束时间</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer" style="text-align:center;">
					<button type="button" class="btn btn-default" data-dismiss="modal"> 取 消 </button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
	</div>
	<!-- /.modal -->
	<!-- 24活动模态框（Modal） -->
</body>
<#include "/footer.ftl" />
<!-- 活动图片 -->
<script>
    var imagesList = [];
	<#list bargainDto.listImages as image>
    imagesList.push({
        imageUrl : "${image.imageUrl}",
        imageKey : "${image.imageKey}",
        imageId : "${image.imageId}"
    });
	</#list>
    var productImage = "${productDto.image}";
    $(function() {
        var productId = '${productDto.productId}' || 0;
        var productInstance = bargainApp.init(productId, imagesList);
        <#if isReview>
        	productInstance.convertToReviewPage();
		</#if>
    });
</script>
<!-- 添加商品规格模板 -->
<script id="proSpecTpl" type="text/html">
   	<label class="checkbox-inline" style="padding-left: 0;">
		<input name="specType" id="specType" value="0"  style="display: none;" />
   		<input type="checkbox" class="j-isCheck" id="selSpec" name="selSpec" />选择规格<span style="color:red;"> (说明：勾选之后活动商品将关联商品规格，不勾选则默认适用整个商品！)</span>
   	</label>
   	<div class="m-choosegg clearfix">
   		<table id="specTable">
   			<thead>
   				<tr>
   					{{each dataList}}
   						<th style="text-align: center;">{{$value.mainSpec}}</th>
   					{{/each}}
   					<th style="text-align: center;" title="元">价格(元)</th>
   					<th style="text-align: center;" title="元">底价(元)</th>
   					<th style="text-align: center;" title="元">砍价金额(元)</th>
   					<th style="text-align: center;">操作</th>
   				</tr>
   			</thead>
   			<tbody>
   				<tr id="opraTr">
					{{each dataList}}
					<td>	
	   						<select class="form-control j-specselc" mainSpec="{{$value.mainSpec}}"  disabled="disabled">
	    						<option>请选择</option>
	    						{{each $value.subSpecs as subSpec index }}
                					<option>{{subSpec}}</option>
            					{{/each}}
	   						</select>
					</td>
   					{{/each}}
   					<td class="priceTd"></td>
   					<td>
   						<input type="text" class="form-control ac-p-input" value="0" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"  onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" data-rule-price="true"  maxlength="8"  disabled="disabled">
   					</td>
					{{if dataList.curType == 0}}
						<td class="bargaintype" bargaintype="{{dataList.curType}}">
							<input class="form-control bargainPrice" type="text" name="bargainMinAmount"  value="" maxlength="6" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="min" title="最小金额" disabled="disabled" >
							<span class="time">-</span> 
							<input class="form-control bargainPrice" type="text" name="bargainMaxAmount"  value="" maxlength="6" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" title="最大金额" placeholder="max" disabled="disabled">
						</td>
					{{else}}
						<td class="bargaintype" bargaintype="{{dataList.curType}}">
							<input class="form-control" type="text" value="" name="bargainAmount" maxlength="6" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="固定金额" disabled="disabled" >
						</td>
					{{/if}}
   					<td>
   						<a href="javascript:void(0)" class="btn btn-default" disabled="disabled">添 加</a>
   					</td>
   				</tr>
   			</tbody>
   		</table>
   	</div>
   	<p id="proChooseTip"></p>
</script>
<!-- 添加商品规格模板 -->
<script id="addProSpecTpl" type="text/html">
   <tr text="{{specName}}">
		{{each proSpecArray }}
			<td>{{$value}}</td>
        {{/each}}
		<td class="tempPriceTd">{{proPrice}}</td>
		<td >{{basePrice}}</td>
		{{if curType == 0}}
			<td><input value="{{bargainMinAmount}}" class="form-control" type="text" style="width: 34%;" disabled="true" /> - <input value="{{bargainMaxAmount}}" class="form-control" type="text" style="width: 34%;" disabled="true" /></td>
		{{else}}
			<td>{{bargainAmount}}</td>
		{{/if}}
		<td><a href="javascript:void(0)" class="btn btn-default j-reSpecItem">删除</a></td>
   </tr>
</script>
<!-- 切换砍价方式，规格商品砍价模板 -->
<script id="changeBargainStyleTpl" type="text/html">
		{{if curType == 0}}
				<input class="form-control bargainPrice" type="text" name="bargainMinAmount"  value="" maxlength="6" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="min" title="最小金额" >
				<span class="time">-</span> 
				<input class="form-control bargainPrice" type="text" name="bargainMaxAmount"  value="" maxlength="6" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" title="最大金额" placeholder="max">
		{{else}}
				<input class="form-control" type="text" value="" name="bargainAmount" maxlength="6" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="固定金额" >
		{{/if}}
</script>
<!-- 预览商品规格模板 -->
<script id="reviewProSpecTpl" type="text/html">
	{{if isReview != "1" }}
   		<label class="checkbox-inline" style="padding-left: 0;">
   			<input type="checkbox" class="j-isCheck" id="selSpec" name="selSpec" />选择规格<span style="color:red;"> (说明：勾选之后活动商品将关联商品规格，不勾选则默认适用整个商品！)</span>
   		</label>
	{{/if}}
   	<div class="m-choosegg clearfix">
   		<table id="specTable">
   			<thead>
   				<tr>
   					{{each mainSpecArry}}
   						<th style="text-align: center;">{{$value}}</th>
   					{{/each}}
   					<th style="text-align: center;" title="元">价格(元)</th>
   					<th style="text-align: center;" title="元">底价(元)</th>
					<th style="text-align: center;" title="元">砍价金额(元)</th>
					{{if isReview != "1"}}
   						<th style="text-align: center;">操作</th>
					{{/if}}
   				</tr>
   			</thead>
   			<tbody>
				{{each kdBargainSpecDtos as specDto index}}
   					<tr  text="{{specDto.specGroup}}">
						{{each specDto.subPecDetailDtos as subPecDetailDto}}
							<td>	
								{{if isReview == "1"}}
									<select class="form-control j-specselc" mainSpec="{{subPecDetailDto.mainSpec}}">
	    								<option>请选择</option>
	    								{{each subPecDetailDto.subSpecData }}
											{{if $value}}
												<option selected="selected" disabled="true" >{{$index}}</option>
											{{else}}
												<option disabled="true" >{{$index}}</option>
											{{/if}}
            							{{/each}}
	   								</select>
								{{else}}
									{{each subPecDetailDto.subSpecData }}
											{{if $value}}
												{{$index}}
											{{/if}}
            						{{/each}}
								{{/if}}
							</td>
   						{{/each}}
						<td>{{specDto.productPrice}}</td>
   						<td>
   							{{specDto.productBasePrice}}
   						</td>
						{{if specDto.bargainType == 0}}
							<td class="bargaintype" bargaintype="{{specDto.bargainType}}">
								<input class="form-control" type="text" disabled="true" style="width: 34%;" name="bargainMinAmount"  value="{{specDto.bargainMinAmount}}" maxlength="6" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="min" title="最小金额" >
								<span class="time">-</span> 
								<input class="form-control" type="text" disabled="true" style="width: 34%;" name="bargainMaxAmount"  value="{{specDto.bargainMaxAmount}}" maxlength="6" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" title="最大金额" placeholder="max">
							</td>
						{{else}}
							<td class="bargaintype" bargaintype="{{specDto.bargainType}}">
								<input class="form-control" type="text" disabled="true" value="{{specDto.bargainAmount}}" style="width: 90%;" name="bargainAmount" maxlength="6" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="固定金额" >
							</td>
						{{/if}}
						{{if isReview != "1"}}
							<td>
								<a href="javascript:void(0)" class="btn btn-default j-reSpecItem">删除</a>
							</td>
						{{/if}}
   					</tr>

					{{if index == kdBargainSpecDtos.length-1  && isReview == "0"}}
						 	<tr {{if index == kdBargainSpecDtos.length-1 }} id="opraTr" {{/if}} >
						{{each specDto.subPecDetailDtos as subPecDetailDto}}
							<td>	
								<select class="form-control j-specselc" mainSpec="{{subPecDetailDto.mainSpec}}">
	    								<option>请选择</option>
	    								{{each subPecDetailDto.subSpecData }}
											<option>{{$index}}</option>
            							{{/each}}
	   							</select>
							</td>
   						{{/each}}
						<td class="priceTd" ></td>
   						<td>
   							<input type="text"    class="ac-p-input" value="0" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"  onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" data-rule-price="true"  maxlength="8">
   						</td>
						{{if specDto.bargainType == 0}}
							<td class="bargaintype" bargaintype="{{specDto.bargainType}}">
								<input class="form-control" type="text"  style="width: 34%;" name="bargainMinAmount"  value="" maxlength="6" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="min" title="最小金额" >
								<span class="time">-</span> 
								<input class="form-control" type="text"  style="width: 34%;" name="bargainMaxAmount"  value="" maxlength="6" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" title="最大金额" placeholder="max">
							</td>
						{{else}}
							<td class="bargaintype" bargaintype="{{specDto.bargainType}}">
								<input class="form-control" type="text"  value="" style="width: 90%;" name="bargainAmount" maxlength="6" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" onafterpaste="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="固定金额" >
							</td>
						{{/if}}
						{{if isReview != "1"}}
							<td>
								<a href="javascript:void(0)" class="btn btn-default j-add">添 加</a>
							</td>
						{{/if}}
   						</tr>
					{{/if}}
				{{/each}}
   			</tbody>
   		</table>
   	</div>
   	<p id="proChooseTip"></p>
</script>
<script id="alertMsgModalTpl" type="text/html">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel"> {{title}}</h4>
         </div>
         <div class="modal-body">{{msg}}</div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
         </div>
      </div><!-- /.modal-content -->
</script><!-- 操作提示信息 -->
<script src="${uiBase}vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script type="text/javascript" src="${uiBase}js/pages/wrkd/product/imageUpload.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}js/pages/wrkd/bargain/bargain_save.js?v=${resourceVersion}_1118"></script>
<script type="text/javascript" src="${uiBase}vendor/ajaxfileupload/ajaxfileupload.js?v=${resourceVersion}"></script>
</html>