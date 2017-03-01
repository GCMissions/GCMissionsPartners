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
.indexbox{width:90%;margin:20px auto;}
.listmargin{ margin-bottom:15px;}
.rinput{float:right;width:85% !important;}
.linediv{height:35px; margin-bottom:15px;}
.linediv span{display:inline-block;line-height:35px;}
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 广告位管理</li>
					<li>添加广告</li>
				</ol>
			</section>

			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="nav-tabs-custom">
						<!-- tab 页标题 -->
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_1" data-toggle="tab">模式一</a></li>
							<li><a href="#tab_2" data-toggle="tab">模式二</a></li>
						</ul>

						<!-- TAB页 -->
						<div class="tab-content">
							<!-- 模式一广告位管理 -->
							<div class='tab-pane active' id="tab_1">
								<div class="tab_div">
									<div class="row">
										<div class='col-sm-12' style='margin-bottom: 10px;'>
											<div class='col-sm-1'>
												<#if firstSwitchStatus == "0">
												<button class="btn btn-primary" id="first-switch">启用</button>
												<#else>
												<button class="btn btn-default" id="first-switch">关闭</button>
												</#if>
											</div>
										</div>
									</div>
									<div class="row">
										<div class='col-sm-12' style='margin-bottom: 10px;'>
											<div class='col-sm-1'>
												<label>广告：</label>
											</div>
											<div class='col-sm-2'>
												<select class="selectpicker form-control productBrand " id="skipType" name="skipType" title="跳转模式">
													<#list skipType as type>
		                                        	<option value="${type.getCode()}">${type.getDesc()}</option>
		                                        	</#list>
		                                     	</select>
											</div>
											<div id="info">
											<div class='col-sm-2'>
												<input  id="actName" name="actName" class="form-control" type="text" placeholder="相关关联" style='width:100%;text-align: center;' readonly="readonly" />
											</div>
											<div class="col-sm-1">	
		                                		<button type="button" class="btn  btn-default global_look" id="look"><i class="fa"></i>浏览</button> 
		                                	</div>
											<div class="col-sm-1">	
		                                		<button type="button" class="btn  btn-primary global_save" id="save"><i class="fa"></i>保存</button> 
		                                	</div>
		                                	</div>
										</div>
									</div>
									<div class="row">
										<div class='col-sm-12'>（至少添加四个，最多可添加八个）</div>
									</div>
									<hr/>
									<div class="indexbox">
									<#list advertiseOneList as advertiseOne>
										<#if advertiseOne.skipType == "关联其他">
											<div class="row listmargin">
											<div class='col-sm-1'>${advertiseOne.index}</div>
											<div class='col-sm-2'>
												<select class="selectpicker form-control productBrand " id="skipType_${advertiseOne.advertiseId}" title="关联其他">
		                                        	<option value="2">关联其他</option>
		                                     	</select>
		                                    </div>
											<div class='col-sm-5'>
												<div class="linediv"><input id="skipUrl_${advertiseOne.advertiseId}" class="form-control rinput" type="text" value="${advertiseOne.skipUrl}" style="text-align: center;"/></div>
												<div class="linediv"><span>标题：</span><input id="name_${advertiseOne.advertiseId}" class="form-control rinput" type="text" value="${advertiseOne.name}" style="text-align: center;"/></div>
												<div class="linediv"><span>图片：</span>
													<div class="adspic-list col-sm-3 rinput">
														<ul>
															<li class="adspic-upload ">
																<div class="upload-thumb">
																<#if advertiseOne.imageUrl??>
																<img src="${advertiseOne.imageUrl}"
																		id="imageUrl_${advertiseOne.advertiseId}">
																<#else>
																<img src="${base}/dist/img/default_goods_image_240.gif"
																		id="imageUrl_${advertiseOne.advertiseId}">
																</#if>
																</div>
																<div class="upload-btn">
																	<a href="javascript:void(0);"> <span><input
																			type="file" hidefocus="true" size="1" class="input-file"
																			name="file" id="imageUpload_${advertiseOne.advertiseId}"
																			data-id="${advertiseOne.advertiseId}"
																			accept=".jpg,.png,.gif"></span>
																		<p>
																			<i class="fa fa-fw fa-upload"></i>上传
																		</p>
																	</a>
																</div>
															</li>
														</ul>
														<p style='font-size:12px;'>建议上传格式为jpg/jpeg/png，尺寸为310×280</p>
													</div>
												</div>
											</div>
											<div class='col-sm-1'></div>
											<div class="col-sm-1">	
		                                		<button type="button" class="btn  btn-primary save_btn" id="save_${advertiseOne.advertiseId}" data-id="${advertiseOne.advertiseId}"><i class="fa"></i>保存</button> 
		                                	</div>
		                                	<div class="col-sm-1"> 
		                                   		<button type="button" class="btn btn-primary del_btn" id="delete_${advertiseOne.advertiseId}" data-id="${advertiseOne.advertiseId}"><i class="fa"></i> 删除</button>  
		                                	</div>
										</div>
										<#else>
											<div class="row listmargin">
												<div class='col-sm-1'>${advertiseOne.index}</div>
												<div class='col-sm-2'>
													<select class="selectpicker form-control productBrand skipType_one" data-id="${advertiseOne.advertiseId}" id="skipType_${advertiseOne.advertiseId}" title="${advertiseOne.skipType}">
													<#list skipType as type>
													<#if type.getCode() != "2">
		                                        	<option value="${type.getCode()}">${type.getDesc()}</option>
		                                        	</#if>
		                                        	</#list>
		                                     	</select>
												</div>
												<div class='col-sm-5'><input id="name_${advertiseOne.advertiseId}" class="form-control rinput" type="text" value="${advertiseOne.name}" style="text-align: center;" readonly="readonly"/></div>
												<div class='col-sm-1'><button type="button" class="btn  btn-default look_btn" id="look_${advertiseOne.advertiseId}" data-id="${advertiseOne.advertiseId}"><i class="fa"></i>浏览</button></div>
												<div class="col-sm-1">	
			                                		<button type="button" class="btn  btn-primary save_btn" id="save_${advertiseOne.advertiseId}" data-id="${advertiseOne.advertiseId}" actId="${advertiseOne.actId}" actype="${advertiseOne.actType}"><i class="fa"></i>保存</button> 
			                                	</div>
			                                	<div class="col-sm-1"> 
			                                   		<button type="button" class="btn btn-primary del_btn" id="delete_${advertiseOne.advertiseId}" data-id="${advertiseOne.advertiseId}"><i class="fa"></i> 删除</button>  
			                                	</div>
											</div>
										</#if>
									</#list>
									</div>
								</div>
							</div>
							<!-- 模式一广告位管理  end -->
							
							<!-- 模式二广告位管理 -->
							<div class='tab-pane' id="tab_2">
								<div class="tab_div">
									<div class="row">
										<div class='col-sm-12' style='margin-bottom: 10px;'>
											<div class='col-sm-1'>
												<#if secondSwitchStatus == "0">
												<button class="btn btn-primary" id="second-switch">启用</button>
												<#else>
												<button class="btn btn-default" id="second-switch">关闭</button>
												</#if>
											</div>
										</div>
									</div>
									<div class="row">
										<div class='col-sm-12' style='margin-bottom: 10px;'>
											<div class='col-sm-1'>
												<label>广告：</label>
											</div>
											<div class='col-sm-5'>
												<div class="linediv"><span>标题：</span><input id="global_two_name" class="form-control rinput" type="text" style="text-align: center;" placeholder="广告标题"/></div>
												<div class="linediv"><span>链接地址：</span></span><input id="global_two_skipUrl" class="form-control rinput" type="text" style="text-align: center;" placeholder="链接地址"/></div>
												<div class="linediv" id="picture"><span>图片：</span>
													<div class="adspic-list col-sm-3 rinput">
														<ul>
															<li class="adspic-upload ">
																<div class="upload-thumb">
																<img src="${base}/dist/img/default_goods_image_240.gif"
																		id="global_two_imageUrl">
																</div>
																<div class="upload-btn">
																	<a href="javascript:void(0);"> <span><input
																			type="file" hidefocus="true" size="1" class="input-file"
																			name="file" id="global_two_imageUpload"
																			accept=".jpg,.png,.gif"></span>
																		<p>
																			<i class="fa fa-fw fa-upload"></i>上传
																		</p>
																	</a>
																</div>
															</li>
														</ul>
														<p style='font-size:12px;'>建议上传格式为jpg/jpeg/png，尺寸为310×280</p>
		                                				<button type="button" class="btn  btn-primary global_two_save" id="global_two_save"><i class="fa"></i>保存</button> 
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class='col-sm-12'>（至少添加一个，最多可添加四个）</div>
									</div>
									<hr/>
									<div class="advertise_two">
										<table id="dataList"></table>
									</div>
								</div>
							</div>
							<!-- 模式二广告位管理  end -->

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
<script id="featureTpl" type="text/html">
<div class="row pad">
<div class='col-sm-12'>
	<div class='col-sm-4'>
		<select class="form-control" id="tagType">
			<option>不限</option>
			<#list tags as tag>
		    <option id="${tag.id}">${tag.name}</option>
		    </#list>
		</select>
	</div>
	<div class='col-sm-2'>
		<input  id="featureName" class="form-control" type="text" placeholder="专辑名称" style='width:100%;' />
	</div>
	<div class='col-sm-2'>
		<button type="button" class="btn btn-primary" id="search"><i class="fa fa-search"></i> 查询</button>  
	</div>
</div>
</div>
<div class="row pad">
	<div class="col-md-12">
		<div class="box-body">
			<table id="featureList" class="table table-bordered table-hover">
			</table>
		</div>
	</div>
</div>
</script>
<script id="actTpl" type="text/html">
<div class="row pad">
<div class='col-sm-12'>
	<div class='col-sm-4'>
		<select class="form-control" id="actTypes">
			<option>不限</option>
			<#list actType as type>
		    <option id="${type.getKey()}">${type.getValue()}</option>
		    </#list>
		</select>
	</div>
	<div class='col-sm-2'>
		<input  id="actNames" class="form-control" type="text" placeholder="活动名称" style='width:100%;' />
	</div>
	<div class='col-sm-2'>
		<button type="button" class="btn btn-primary" id="searchAct"><i class="fa fa-search"></i> 查询</button>  
	</div>
</div>
</div>
<div class="row pad">
	<div class="col-md-12">
		<div class="box-body">
			<table id="actList" class="table table-bordered table-hover">
			</table>
		</div>
	</div>
</div>
</script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.js"></script>
<script
	src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script type="text/javascript"
	src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script>
<script
	src="${uiBase}/js/pages/wrkd/kdAdvertise/kd_advertise_add.js?v=${resourceVersion}"></script>
</html>
