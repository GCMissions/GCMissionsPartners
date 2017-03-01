<#assign headComponents = ["bootTable", "innerPage"] > <#include
"/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/pages/advertise/advertise_list.css?v=${resourceVersion}">
<style type=text/css>
.tab_div {
	display: table;
	width: 100%;
}

.perform_underline {
	border-bottom: 2px solid #ddd;
	padding-bottom: 30px;
	margin-bottom: 30px;
}

.description {
	display: block;
	width: 360px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>广告管理</li>
					<li>广告管理</li>
				</ol>
			</section>

			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="nav-tabs-custom">
						<!-- tab 页标题 -->
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_1" data-toggle="tab">轮播广告位管理</a></li>
							<li><a href="#tab_2" data-toggle="tab">热门推荐</a></li>
						</ul>

						<!-- TAB页 -->
						<div class="tab-content">
							<!-- 轮播广告位管理 -->
							<div class='tab-pane active' id="tab_1">
								<div class="tab_div">
									<#list carouselList as carousel>
									<div class="row carousel_upload"
										id="carousel_form_${carousel.sort}">
										<div class="col-sm-1 text-right">
											<label>图片${carousel.sort}：</label>
										</div>
										<div class="adspic-list col-sm-3">
											<ul>
												<li class="adspic-upload "
													id="carousel_upload_${carousel.sort}">
													<div class="upload-thumb">
														<#if carousel.url??> <img src="${carousel.url}"
															id="carousel_img_${carousel.sort}" style="width: 100%; height: 100%;" class="adImg"> <#else> <img
															src="${base}/dist/img/default_goods_image_240.gif"
															id="carousel_img_${carousel.sort}" style="width: 100%; height: 100%;"> </#if> <input
															type="hidden" name="id" value="${carousel.adId}">
														<input type="hidden" name="url" value="${carousel.url}">
													</div>
													<div class="upload-btn">
														<a href="javascript:void(0);"> <span><input
																type="file" hidefocus="true" size="1" class="input-file"
																name="file" id="carousel_${carousel.sort}"
																data-sort="${carousel.sort}" accept=".jpg,.png,.gif"></span>
															<p>
																<i class="fa fa-fw fa-upload"></i>上传
															</p>
														</a>
													</div>
												</li>
											</ul>
										</div>
										<div class="col-sm-3" style="margin-top: 10px; margin-right: -6%;">
											<label>关联商品：</label> <input type="text" class="form-control"
												name="name" value="${carousel.shortName}"
												id="carousel_name_${carousel.sort}" readonly="readonly"
												title="${carousel.activeName}">
										</div>
										<div class="col-sm-3" style="margin-top: 10px;">
											<button class="btn btn-primary look" type="button"
												data-id="${carousel.adId}" data-sort="${carousel.sort}">浏览
												</i>
											</button>
											<button class="btn btn-primary save"
												id="carousel_save_${carousel.sort}" type="button"
												data-id="${carousel.adId}" data-acId="${carousel.acId}"
												data-sort="${carousel.sort}">
												保存 </i>
											</button>
											<button class="btn btn-primary deleteC" type="button"
												data-id="${carousel.adId}" data-sort="${carousel.sort}">
												删除 </i>
											</button>
										</div>
									</div>
									</#list>
								</div>
							</div>
							<!-- 轮播广告位管理  end -->

							<!-- 豆儿抢购管理 -->
							<div class='tab-pane' id="tab_2">
								<div class="tab_div">
									<div class="row sale_upload" id="sale_form_${sort}">
										<div class="col-sm-1 text-right">
											<label>图片：</label>
										</div>
										<div class="adspic-list col-sm-3">
											<ul>
												<li class="adspic-upload " id="sale_upload_${sort}">
													<div class="upload-thumb">
														<img src="${base}/dist/img/default_goods_image_240.gif"
															id="sale_img_${sort}">
													</div>
													<div class="upload-btn">
														<a href="javascript:void(0);"> <span><input
																type="file" hidefocus="true" size="1" class="input-file"
																name="file" id="sale_${sort}" data-sort="${sort}"
																accept=".jpg,.png,.gif"></span>
															<p>
																<i class="fa fa-fw fa-upload"></i>上传
															</p>
														</a>
													</div>
												</li>
											</ul>
										</div>
										<div class="col-sm-3">
											<div><label>关联商品：</label> <input type="text" class="form-control"
												id="sale_name_${sort}" name="name" readonly="readonly"><button id="clear-all" class="btn btn-default" style="margin-left: 10px;">清空</button></div>
											<div style="margin-top: 20px;"><label>热门说明：</label> <input type="text" class="form-control"
												id="description" name="description" maxlength="6">
											</div>
											<div style="margin-top: 20px;"><label>关联URL：</label> <input type="text" class="form-control"
												id="url" name="url">
											</div>
										</div>
										<div class="col-sm-3">
											<button class="btn btn-primary look" type="button" id="sale_look_${sort}"
												data-id="" data-sort="${sort}">
												浏览 </i>
											</button>
											<button class="btn btn-primary save" id="sale_save_${sort}"
												type="button" data-id="${sale.adId}"
												data-acId="" data-sort="${sort}">
												保存 </i>
											</button>
										</div>
									</div>
									<table id="dataList"></table>
								</div>
							</div>
							<!-- 豆儿抢购管理管理  end -->

						</div>
						<!-- TAB页 end -->
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<!-- /.content-wrapper -->
	</div>
	<!-- ./wrapper -->
</body>
<#include "/footer.ftl" />
<script type="text/javascript">
	
</script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.js"></script>
<script
	src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script type="text/javascript"
	src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script>
<script
	src="${uiBase}/js/pages/advertise/advertise_list.js?v=${resourceVersion}"></script>
</html>
