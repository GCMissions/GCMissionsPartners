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
					<li class="active">添加商品</li>
				</ol>
			</section>
			<div class="row pad">
				<div class="col-md-12">
					<!-- Custom Tabs -->
					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs" id="mytabs" >
							<li class="active"><a href="#tab_1">商品信息</a></li>
							<li><a href="#tab_2">库存与规格</a></li>
							<li><a href="#tab_3">商品详情介绍</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<div class="box-body">
									<form id="mainForm" role="form" class="form-horizontal" vip-flag="0" productId="${(productDto.id)!}" oper="add">
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>商品名称：</label>
											<div class="col-sm-3">
												<input type="text" class="form-control" id="productName" name="productName" maxlength="50" placeholder="商品名称" />
											</div>
											<div class="col-sm-4">
												<input type="checkbox" checked="checked" id="isShow" /><label class="control-label">是否在商城显示</label>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>是否VIP商品：</label>
											<div class="col-sm-7" style="padding-top: 7px;">
												<input type="radio" name="isVip" value="1"/>&nbsp;是&nbsp;
												<input type="radio" name="isVip" value="0" checked="checked"/>&nbsp;否&nbsp;
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">促销活动：</label>
											<div class="col-sm-7" style="padding-top: 7px;">
												<#if actTags??>
													<input type="radio" name="actTags" value="" checked="checked"/>&nbsp;无活动&nbsp;
													<#list actTags as actTag>
														<input type="radio" name="actTags" value="${actTag_index}" />&nbsp;${actTag}&nbsp; 
													</#list>
												</#if>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">商品特别说明：</label>
											<div class="col-sm-7">
												<textarea id="specialNote" name="specialNote" cols=220 rows=3 class="form-control" maxlength="100" placeholder="商品特别说明"></textarea>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">商品价格(元)：</label>
											<div class="col-sm-3 price-area">
												<input type="text" class="form-control" name="originalPrice" id="originalPrice" placeholder="价格" onfocus="this.select()"/>（原价，可不填）
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>商品描述：</label>
											<div class="col-sm-7">
												<textarea id="quickDesc" name="quickDesc" cols=220 rows=4 class="form-control" maxlength="150" placeholder="商品简单介绍"></textarea>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>运费：</label>
											<div class="col-sm-5 freight-area">
												<p>默认运费（元）：
													<input type="text" placeholder="价格" class="form-control" id="defaultFreight" style="width:90px;" value="0" onfocus="this.select()"/>（0表示免运费）
													<span class="u-freight j-freight">+区域运费设定</span>
												</p>
												<table class="m-table" id="region-table">
													<thead>
														<tr>
															<th class="lft">运送到</th>
															<th class="cent" width="20%">价格（元）</th>
															<th class="rit" width="20%">操作</th>
														</tr>
													</thead>
													<tbody>
														
													</tbody>
												</table>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>商品图片：</label>
											<div class="col-sm-10 title">
												<button type="button" class="btn btn-primary addNewPic">
													<i class="fa fa-plus"></i> 添加图片
												</button>
												<#include "/wrkd/product/imageUpload.ftl" />
											</div>
										</div>
										<div style="text-align: center">
											<button class="btn btn-primary submitMainForm" type="button" id="saveBaseInfo">下一步</button>
											<button class="btn btn-success back-away" type="button">
												<i class="fa fa-backward"> 返&nbsp;&nbsp;回 </i>
											</button>
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
												<label class="col-sm-2 control-label"><span class="requiredField">*</span>商品规格:</label>
												<div class="col-sm-10">
													<div class="j-box" id="specArea">
														
													</div>
													<div class="selMainSpec disnone">
														<input type="text" onfocus="this.select()"/>
														<button class="btn btn-primary j-selmainspfsure">确定</button>
														<button class="btn btn-primary j-selmainspfcancel">关闭</button>
													</div>
													<button class="btn btn-primary j-seladdmainspf">添加主规格</button>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label"><span class="requiredField">*</span>库存:</label>
												<div class="col-sm-10">
													<p class="guige">
														<label class="checkbox-inline">
															<input type="radio" name="stockType" checked value="0"/> 按规格
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
															<input type="radio" name="stockType" value="1"/> 按总库存
															<input type="text" id="originAmount" onfocus="this.select()"/>
															限购数量
															<input type="text" placeholder="数量" id="limitAmount" onfocus="this.select()"/>
														</label>
													</p>
												</div>
											</div>
							            </div>
        							</div>
        							<div style="text-align: center">
        								<button class="btn btn-primary" type="button" id="saveStockInfo">下一步</button>
										<button class="btn btn-success back-away" type="button">
											<i class="fa fa-backward"> 返&nbsp;&nbsp;回 </i>
										</button>
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
												<script id="ueEditor-platform" name="description" type="text/plain" style="width:100%;height:500px;"></script>
											</div>
										</div>
										<div style="text-align: center">
										    <button class="btn btn-primary" type="button" id="saveDetailDesc">完成</button>
											<button class="btn btn-success back-away" type="button">
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
	
	<!-- 通用消息提示框 -->
	<div class="modal fade" id="alertMsgDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	</div>
	<!-- /.modal -->

	<!-- 区域运费设置模态框（Modal） -->
	<div class="modal fade" id="regionalFreight" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog" style="width:700px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">区域运费设定</h4>
				</div>
				<div class="modal-body regionalfreight clearfix">
					<div class="left">
						<h1>请选择省市</h1>
						<ul class="j-xlist j-list">
							
						</ul>
					</div>
					<div class="center">
						<button class="j-add btn btn-default" style="margin-top:70px;">添 加</button>
						<button class="j-alladd btn btn-default">全部添加</button>
						<button class="j-remove btn btn-default">移 除</button>
						<button class="j-allremove btn btn-default">全部移除</button>
					</div>
					<div class="right">
						<h1>已添加区域</h1>
						<ul class="j-ylist j-list">
							
						</ul>
					</div>
					<div class="yunfei">
						<div class="msg-warning hidden" style="margin:6px;">
							<span class="requiredField" style="font-size:16px;letter-spacing:5px;"></span>
						</div>
						<span class="requiredField">*</span>请输入运费（元）:
						<input type="text" class="form-control j-yunfei" maxlength="50" placeholder="价格"onfocus="this.select()" />
					</div>
				</div>
				<div class="modal-footer" style="text-align:center;">
					<button type="button" class="btn btn-default" data-dismiss="modal"> 取 消 </button>
					<button type="button" class="btn btn-primary" id="saveRegionalFreight"> 保 存 </button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
	</div>
	<!-- /.modal -->
	<!-- 区域运费设置模态框（Modal） -->

</body>
<#include "/footer.ftl" />

<#include "/wrkd/product/template.ftl" />

<script src="${uiBase}vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script type="text/javascript" src="${uiBase}vendor/ajaxfileupload/ajaxfileupload.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}js/pages/wrkd/product/imageUpload.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}js/pages/wrkd/product/add.js?v=${resourceVersion}"></script>

</html>
