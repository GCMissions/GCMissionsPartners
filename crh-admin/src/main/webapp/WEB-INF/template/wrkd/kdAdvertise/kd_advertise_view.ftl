<#assign headComponents = ["bootTable", "innerPage"] >
<#include "/header.ftl" />

<style>
textarea{
width: 100% !important;
 margin-left: 80px;
}
label{
    padding-top: 7px;
}

.subSpec{
 margin-right: 5px;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
         <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i> 广告位管理</li>
            <li>广告信息</li>
          </ol>
        </section>
        
        <!-- Main content -->
        <div class="row pad">
        <div class="col-md-12">
				<div class="nav-tabs-custom">
               		 
	               	<!-- 广告信息 -->
	                <div class="tab-content">
		                  	<div style="margin-bottom: 100px;margin-top: 100px;">
							<div class="form-horizontal ">
							
								<div class="form-group">
									<label class="col-sm-2 control-label">广告标题：</label>
									 <label  class="col-sm-4" style="font-weight:100">${advertiseList.name}</label>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">跳转类型：</label>
									 <label  class="col-sm-4" style="font-weight:100">${advertiseList.skipType}</label>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">所属标签：</label>
									 <label  class="col-sm-4" style="font-weight:100">${advertiseList.tagName}</label>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">广告模式：</label>
									 <label  class="col-sm-4" style="font-weight:100">${advertiseList.type}</label>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">链接地址：</label>
									 <label  class="col-sm-4" style="font-weight:100">${advertiseList.skipUrl}</label>
								</div>
								 
								 <div class="form-group">
								 	<label class="col-sm-2 control-label">图片：</label>
								 	<img src="${advertiseList.imageUrl}" style="width: 200px; height: 200px;">
								 </div>
								 
							</div>
	                  	
					        </div>
					        <div class="col-md-12" style="margin-top: 20px;">
					        	<div class="col-sm-4 col-sm-offset-5">
					        		<button class="btn  btn-success" id="back" ><i class="fa fa-backward"></i> 返  回</button>
								</div>
					        </div>
	                  	<!-- 广告信息  end -->
	                </div>
				</div>
			</div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
</body>
<#include "/footer.ftl" />
<script src="${uiBase}/vendor/input-mask/jquery.inputmask.js"></script>
<script src="${uiBase}vendor/input-mask/jquery.inputmask.numeric.extensions.js"></script>
<script type="text/javascript" src="${uiBase}/vendor/jquery-validator/jquery.validate.min.js"></script>
<script type="text/javascript" src="${uiBase}/js/pages/wrkd/kdAdvertise/kd_advertise_view.js?v=${resourceVersion}"></script>
<script type="text/javascript">
$(function(){
	$('body').on('click', '#back',function(){
		window.location.href=urlPrefix+"kdAdvertise/";
	});
})
</script>
</html>
