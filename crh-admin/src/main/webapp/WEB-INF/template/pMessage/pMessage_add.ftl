<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 

</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
         
          <ol class="breadcrumb"> 
            <li><i class="fa fa-dashboard"></i> 营销管理</li>
			 <#if isReview>
			 <li>查看站内信</li>
			 <#else>  
            <li>发送站内信</li>
			</#if>
             
          </ol>
          
         </section>
        
       
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary ">
                   
                     
                   <form role="form" class="form-horizontal" method=post id="mainForm">
                    <div class="box-body">
                     	  <div class="form-group ">
	                          <label class="col-sm-2 control-label  no-padding"><span class="requiredField">*</span>标题：</label>
	                          <div class="col-sm-4">
	                            <#if isReview>
	                            <p class="form-control-static">
	                            ${msgDto.title}
	                            </p>
	                            <#else>                                  
	                            <input type="text" class="form-control" name="title"  value="${msgDto.title}" maxlength=50>
	                            </#if>
	                          </div>
	                          <div class="col-sm-4">
	                          </div>
	                      </div>
	                      
	                      <div class="form-group ">
	                          <label class="col-sm-2 control-label  no-padding"><span class="requiredField">*</span>内容：</label>
	                          <div class="col-sm-8">
	                          <#if isReview>
	                          <p class="form-control-static">
	                            ${msgDto.content}
	                             </p>
                              <#else>
                                  <!--<script id="ueEditor" name="content" type="text/plain" data-ue-height=200 style="width:100%;"></script>-->
                                   <textarea name="content"  cols=50 rows=8 class="form-control" maxlength="300"></textarea>
                                  
                              </#if>
                              </div>
	                          
	                          <div class="col-sm-2">
	                          </div>
	                      </div>
	                      
	                      <div class="form-group ">
							  <label class="col-sm-2 control-label  no-padding">发送对象：</label>
							  <div class="col-sm-6">
							   <div class="form-group no-margin">
							    <#if isReview>
							  	     <p class="form-control-static">
									 <#if msgDto.type=='p'>区域平台商
									 <#else>终端配送商
									 </#if>
									 </p>
								<#else>
						            <label class="checkbox-inline no-padding ">
						              <input type="radio" name="orgType"  checked value="p" > 区域平台商
						            </label>
						         
											
						            <label class="checkbox-inline   no-padding ">
						              <input type="radio" name="orgType"  class="" value="z" > 终端配送商
						            </label>
						            <button type="button" class="btn btn-primary addMerchant"><i class="fa fa-plus"></i> 添加商家</button>
						        </#if>
								</div>
										                    
							  </div>
						 </div>
						 
						 <div class="form-group row">
						 	<label class="col-sm-2 control-label"> </label>
							<div class="col-sm-8">
								 
								 <label class="fieldError merchantError" ></label>
								 <table id="merchantsList" class="table table-bordered table-hover dataList" >
					                <thead>
					                    <th>序号</th>
					                    <th>商家编号</th>
					                    <th>商家名称</th>
					                    <th>联系人</th>
					                    <th>联系电话</th>
					                    <th>地址</th>
					                    <#if !isReview> <th>操作</th> </#if>
					                </thead>
					                <tbody>
									<#list msgDto.orglist as item>
									<tr >
									<td>${item_index + 1}</td>
									<td>${item.orgCode}</td><td>${item.orgName}</td><td>${item.contact}</td><td>${item.phone}</td><td>${item.address}</td>
									
									</tr>
									</#list>
					                </tbody>
					              </table>
							</div>
						</div>
						
						<!-- nav-tabs-custom -->
		                  <div class="row">
		                   <div class="col-sm-12 text-center">
		                    <#if !isReview>
							<button class="btn btn-primary submitMainForm" type="button"  >保存</button>
		                    </#if>
		                    <button class="btn btn-success backMainForm" type="button"   ><i class="fa fa-backward"> 返回 </i></button>
		                   </div>
		                  </div>
		                  
		                  
                    </div><!-- /.box-body --> 
                    
                   </form>
                </div>
             </div>
        </div>
       <!-- /.content --> 
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
 
<#include "/footer.ftl" /> 

<#if !isReview>
<script type="text/html" id="chooseMerchantTpl">

 <div class="row pad">
     <div class="col-md-12">
        
        
		<div class="form-horizontal " >
			
				<div class="form-group  col-sm-6">
						
					<label class="col-sm-5  control-label" >商家编号：</label>
				 
					<input  id="sOrgCode" class="form-control " type="text">
				</div>
				<div class="form-group col-sm-6">      
					<label class="col-sm-5  control-label" for="type-select">商家名称：</label>
					
					<input id="sOrgName" class="form-control " type="text">
						 
				   
				</div>
				<div class="form-group col-sm-6">
						
					<label class="col-sm-5  control-label" for="type-select">联 系 人：</label>
				 
					<input  id="sContact" class="form-control col-sm-2" type="text">
				   
				</div>
				<div class="form-group col-sm-6">   
					<label class="col-sm-5  control-label" for="type-select">联系电话：</label>
					
					<input id="sPhone" class="form-control col-sm-2" type="text">
				</div>
				<div class="form-group col-sm-3">      
				   <button type="button" class="btn btn-primary pull-right" id="searchMerchant"><i class="fa fa-search"></i> 开始搜索</button>  
					   
				</div>
		   
		</div> <!-- search group -->
		
		 <div class="box-body">
		  <table id="chooseMerchantsList" class="table table-bordered table-hover dataList" >
			<thead>
			   
				<th></th>
				<th>序号</th>
				<th>商家编号</th>
				<th>商家名称</th>
				<th>联系人</th>
				<th>联系电话</th>
				<th>地址</th>
			</thead>
			<tbody>
			
			
		
		
			</tbody>
		  </table>
		</div>
                    
        
    </div>
</div>
</script>

<script type="text/javascript" src="${uiBase}js/pages/pMessage/pMessage_add.js?v=${resourceVersion}"></script> 
<script>
 $(function() {
   
    messageApp.init();
});
</script>

</#if>
<script>
 $(function() {
   
    $('#mainForm').find('.backMainForm').on('click', function() {
		window.location.href = urlPrefix + "pMessage/";
	});
});
</script>
</body>
  
</html>