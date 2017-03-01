<#assign headComponents = ["bootTable"] > 
<#include "/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/pages/wrkd/charity/charity_add.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}/css/pages/wrkd/product/imageUpload.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<div class="content-wrapper page-content-wrapper">
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>公益管理</li>
					<li class="active">查看公益活动</li>
				</ol>
			</section>
			<div class="row pad">
				<div class="col-md-12">
					<!-- Custom Tabs -->
					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs" id="mytabs" >
							<li class="active"><a href="#tab_1" data-toggle="tab">公益活动信息</a></li>
							<li><a href="#tab_2" data-toggle="tab">公益活动详情</a></li>
							<li><a href="#tab_3" data-toggle="tab">公益活动反馈详情</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<div class="box-body">
									<form id="mainForm" role="form" class="form-horizontal" charity-id="${(charityDto.id)!}" oper="view">
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>公益活动名称：</label>
											<div class="col-sm-5">
												<p class="form-control-static">
													${(charityDto.name)!}
												</p>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>公益活动说明：</label>
											<div class="col-sm-7">
												<p class="form-control-static">
													${(charityDto.explainNote?replace('\r\n','<br />','i'))!}
												</p>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>公益活动时间：</label>
											<div class="col-sm-8 date-area" style="width:530px;">
												<p class="form-control-static">
													${(charityDto.startTime)!} 至 ${(charityDto.endTime)!}
												</p>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>公益活动感谢图片：</label>
											<div class="col-sm-7">
												<div class="desc-image goodspic-upload">
													<div class="upload-thumb">
												         <img src="${(charityDto.imgUrl)!}"  id="img_50">
												         <input type="hidden" name="picId[50]" value="" />
												         <input type="hidden" name="picUrl[50]" value="" />
														 <input type="hidden" name="picKey[50]" value="imageKey" />
												     </div>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>关联专辑：</label>
											<div class="col-sm-5 feature-area">
												<p style="margin:7px 0px;display:none;"><span class="u-feature">+选择专辑</span></p>
												<table class="m-table" id="feature-table">
													<thead>
														<tr>
															<th class="lft" width="30%">专辑名称</th>
															<th class="cent">专辑描述</th>
															<th class="rit" width="30%">所属标签</th>
														</tr>
													</thead>
													<tbody>
														<#if featureDto ??>
														<tr feature-id="${featureDto.id}" >
															<td>${featureDto.name}</td>
															<td>${featureDto.description}</td>
															<td>${featureDto.tagName}</td>
														</tr>
														</#if>
													</tbody>
												</table>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>公益活动图片：</label>
											<#include "/wrkd/product/imageUpload.ftl" />
										</div>
									</form>
								</div>
							</div>
							
							<!-- 公益活动详情 -->
							<div class='tab-pane' id="tab_2">
								<div class="box-body">
									<form id="detailForm" role="form" class="form-horizontal" method=post>
										<div class="form-group">
											<div class="col-sm-12">
												<p class="form-control-static">
													${(charityDto.detailDesc)!}
												</p>
											</div>
										</div>
									</form>
								</div>
							</div>
							<!-- 公益活动详情  end -->
							
							<!-- /.tab-pane -->
							<div class="tab-pane" id="tab_3">
								<div class="box-body">
									<form id="feedbackForm" role="form" class="form-horizontal" method=post>
										<div class="form-group">
											<div class="col-sm-12">
												<p class="form-control-static">
													${(charityDto.feedback)!}
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
</html>
