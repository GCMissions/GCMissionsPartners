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
					<li class="active">添加公益活动</li>
				</ol>
			</section>
			<div class="row pad">
				<div class="col-md-12">
					<!-- Custom Tabs -->
					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs" id="mytabs" >
							<li class="active"><a href="#tab_1">公益活动信息</a></li>
							<li style="display:none;"><a href="#tab_3">公益活动反馈详情</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<div class="box-body">
									<form id="mainForm" role="form" class="form-horizontal" oper="add">
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>公益活动名称：</label>
											<div class="col-sm-5">
												<input type="text" class="form-control" id="charityName" name="charityName" maxlength="50" placeholder="公益活动名称" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>公益活动说明：</label>
											<div class="col-sm-7">
												<textarea rows=3 cols=220 class="form-control" id="explainNote" name="explainNote" maxlength="100" placeholder="公益活动说明"></textarea>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>公益活动时间：</label>
											<div class="col-sm-9 date-area">
												<div class="form-horizontal">
													<div class="dateDiv" style="margin-bottom: 0px;">
														<input size="20" type="text" id="startDate" class="form-control keyword" placeholder="活动开始时间" readonly> 
														<span class="add-on" style="display: none"><i class="icon-remove"></i></span> 
														<span class="add-on"><i class="icon-calendar"></i></span>
													</div>
												
													<div class="dateDiv" style="margin-bottom: 0px;">
														<input size="20" type="text" id="endDate" class="form-control keyword" placeholder="活动结束时间" readonly> 
														<span class="add-on" style="display: none"><i class="icon-remove"></i></span> 
														<span class="add-on"><i class="icon-calendar"></i></span>
													</div>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>公益活动感谢图片:</label>
											<div class="col-sm-7">
												<div class="desc-image goodspic-upload">
													<div class="upload-thumb">
												         <img src="${uiBase}img/default_goods_image_240.gif"  id="img_50">
												         <input type="hidden" name="picId[50]" value="" />
												         <input type="hidden" name="picUrl[50]" value="" />
														 <input type="hidden" name="picKey[50]" value="" />
												     </div>
												
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
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>关联专辑：</label>
											<div class="col-sm-5 feature-area">
												<p style="margin:7px 0px;"><span class="u-feature">+选择专辑</span></p>
												<table class="m-table" id="feature-table">
													<thead>
														<tr>
															<th class="lft" width="30%">专辑名称</th>
															<th class="cent">专辑描述</th>
															<th class="rit" width="20%">所属标签</th>
															<th class="rit" width="10%">操作</th>
														</tr>
													</thead>
													<tbody>
														
													</tbody>
												</table>
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label"><span class="requiredField">*</span>公益活动图片：</label>
											<div class="col-sm-10 title">
												<button type="button" class="btn btn-primary addNewPic">
													<i class="fa fa-plus"></i> 添加图片
												</button>
												<#include "/wrkd/product/imageUpload.ftl" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label">公益活动详情：</label>
											<div class="col-sm-8 control-label">
												<script id="ueEditor-platform" name="detailDesc" type="text/plain" style="width:100%;height:500px;"></script>
											</div>
										</div>
										<div style="text-align: center">
											<button class="btn btn-primary submitMainForm" type="button" id="saveBaseInfo">完成</button>
											<button class="btn btn-success back-away" type="button">
												<i class="fa fa-backward"> 返&nbsp;&nbsp;回 </i>
											</button>
										</div>
									</form>
								</div>
							</div>
							
							<!-- 公益活动详情
							<div class='tab-pane' id="tab_2">
								<div class="box-body">
									<form id="detailForm" role="form" class="form-horizontal" method=post>
										
										<div style="text-align: center">
										    <button class="btn btn-primary" type="button" id="saveDetailDesc">完成</button>
											<button class="btn btn-success back-away" type="button">
												<i class="fa fa-backward"> 返回 </i>
											</button>
										</div>
									</form>
								</div>
							</div> -->
							<!-- 公益活动详情  end -->
							
							<!-- /.tab-pane -->
							<div class="tab-pane" id="tab_3">
								<div class="box-body">
									<form id="feedbackForm" role="form" class="form-horizontal" method=post>
										<div class="form-group">
											<div class="col-sm-12">
												<script id="ueEditor" name="detailFeedback" type="text/plain" style="width:100%;height:500px;"></script>
											</div>
										</div>
										<div style="text-align: center">
										    <button class="btn btn-primary" type="button" id="saveFeedback" disabled>完成</button>
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
	
	<!-- 选择专辑模态框（Modal） -->
	<div class="modal fade" id="chooseFeature" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">选择专辑</h4>
				</div>
				<div class="modal-body">
					<div class="select-feature">
						<div class="form-group"  style="margin-left:120px;">
							<input type="text" id="featureName" class="form-control" maxlength=50 placeholder="专辑名称" style="width:250px;"/>
							<button type="button" class="btn btn-primary" id="search" style="margin-left:30px;width:90px;">
								<i class="fa fa-search"></i> 查询
							</button>	
						</div>				
						<#if tagList??>
						<div class="form-group">
							<#list tagList as tag>
								<input type="checkbox" name="tags" value="${tag.name}" />${tag.name}
							</#list> 
						</div>
						</#if>
					</div>
					<div class="bootstrap-table">
						<table id="featureList" class="table table-bordered table-hover">
							<thead>
								<tr>
									<th></th>
									<th>专辑名称</th>
									<th>专辑描述</th>
									<th>所属标签</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer" style="text-align:center;">
					<button type="button" class="btn btn-default" data-dismiss="modal"> 取 消 </button>
					<button type="button" class="btn btn-primary" id="confirmAdd"> 确 认 </button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
	</div>
	
	<!-- 通用消息提示框 -->
	<div class="modal fade" id="alertMsgDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	</div>
	<!-- /.modal -->

</body>
<#include "/footer.ftl" />

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

<script type="text/javascript" src="${uiBase}vendor/ajaxfileupload/ajaxfileupload.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}js/pages/wrkd/product/imageUpload.js?v=${resourceVersion}"></script>
<script type="text/javascript" src="${uiBase}js/pages/wrkd/charity/charity_add.js?v=${resourceVersion}"></script>
</html>
