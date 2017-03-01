<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/advertise.css?v=${resourceVersion}">
</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
       <section class="content-header">       
        <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i> 商城管理</li>
            <li> 广告管理</li>         
       </ol>
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                	<div class="box-body adsForm" id="mainForm">	
					 	<div class="form-group col-md-12">
	                        <label class="col-sm-2 control-label">广告位:</label>
	                          <div class="col-sm-4">	                            
								 <input   type="hidden" class="form-control" id="adLocal" value="${adDto.local}">
								 <input type="text" class="form-control" id="adPosition" readOnly value="${adDto.localName}">
	                          </div>
	                    </div>
	                    <div class="form-group col-md-12">
	                        <label class="col-sm-2 control-label">广告位排序:</label>
	                          <div class="col-sm-4">	                            
								 <input type="text" class="form-control" id="sort" readOnly value="${adDto.sort}">
	                          </div>
	                    </div>
					 	<div class="form-group col-md-12">
	                        <label class="col-sm-2 ">广告标题:</label>
	                        <div class="col-sm-4">
	                            <input type="text" class="form-control" id="adName" data-rule-required="true" value="${adDto.title}">
	                         </div>
	                    </div>	                    
	                   <!--  <div class="form-group col-md-12">
	                         <div class="col-sm-2">
	                         	<label class="control-label" for="type-select">广告使用日期：</label>
	                         </div>
                             <input type="hidden"  name="usDateInput" id="usDateInput" value="" />
							 <div class="dateDiv col-sm-2" style="margin-bottom: 0px;">
							    <input size="10" type="text" name="usDate" id="usDate" class="form-control keyword beginDate" placeholder="请选择时间" readonly>
							    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
							    <span class="add-on"><i class="icon-calendar"></i></span>
							 </div>	 
					 		 <span class="time">至</span>
                       		 <input type="hidden"  name="ueDateInput" id="ueDateInput" value="" />
							 <div class="dateDiv" style="margin-bottom: 0px;">
							    <input size="10" type="text" name="ueDate" id="ueDate" class="form-control keyword endDate" placeholder="请选择时间" readonly>
							    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
							    <span class="add-on"><i class="icon-calendar"></i></span>
							 </div>
					    </div> -->
	                    
	                    <div class="form-adpic">
		                    <div class="form-group col-md-12">
		                        <label class="col-sm-2 ">广告图片:</label>
		                        <input   type="hidden" class="form-control" id="imgUrl" value="${adDto.image}">
		                     <!--    <div class="col-sm-4">
		                             <input id="file" type="file" multiple=true>
		                         </div> -->
		                         <div class="col-sm-4 form-ads-pic">								
									   <div class="title">
								          <button type="button" class="btn btn-default addNewPic">添加图片</button>
								       </div>
								</div>
		                    </div>
		                    <div class="row col-md-12">
			                    <div class="col-sm-2"></div>
			                    <div class="adspic-list col-sm-8">
									<ul>
									</ul>
								</div>
		                    </div>
	                    </div>
	                     <div class="form-group col-md-12">
	                        <label class="col-sm-2 ">链接类型:</label>
	                 		 <div class="col-sm-4">
	                           <select class="form-control" id="type" value="">
							   	 <#list urlFlags as items>
			                        <#if adDto.urlFlag==items.code>
			                        <option value="${items.code}" selected>${items.text}</option>
			                        <#else>
			                        <option value="${items.code}">${items.text}</option>
			                        </#if>      
			                     </#list>
							   </select>
	                         </div>
	                    </div>
	                    <div class="form-group col-md-12">
	                        <label class="col-sm-2 ">广告链接:</label>
	                        <div class="col-sm-4">
	                            <input type="text" class="form-control" id="url" data-rule-required="true" value="${adDto.url}">
	                         </div>
	                    </div>	     
                		<div class="row">
		                   <div class="col-sm-12 text-center">
		                    <input   type="hidden" class="form-control" id="adId" value="${adDto.adId}">
		                    
		                    <button class="btn btn-primary" id="advertAdd" type="button"  >保存</button>		                  
		                    <button class="btn btn-success backPage" type="button"   ><i class="fa fa-backward"> 返回 </i></button>
		                   
		                   </div>
		                  </div>
                	</div><!-- /.box-body -->
               	</div><!-- /.box-primary -->
             </div>
        </div>
        <div class="clearfix"></div>     
      </div>
   </div>
</body>
<#include "/footer.ftl" />
 
<script id="picItemTpl" type="text/html">
<li class="adspic-upload" id="upload_{{id}}">
    <div class="upload-thumb">
		<img src="{{showImageUrl}}"  id="img_{{id}}">
        <input type="hidden" name="picId[{{id}}]" value=""  id="name_{{id}}">
        <input type="hidden" name="picUrl[{{id}}]" value="{{ImageUrl}}"  id="name_{{id}}">
    </div>
	<div class="upload-setDefault">
    	<div class="form-group">
    		<input type=hidden name="goodsCover[{{id}}]" class="goodsCover " value="{{isCover}}" >
            <a href="javascript:;" class="btnSetCover {{if isCover}}isCover{{/if}}">{{CoverText}}</a>
    	</div>
    </div>
    <div class="upload-btn">    	
	    <a href="javascript:void(0);">
		    <span><input type="file" hidefocus="true" size="1" class="input-file" name="file" id="{{id}}"></span>
		    <p><i class="fa fa-fw fa-upload"></i>上传</p>
	    </a>
	</div>
	
	<div class="remove-btn">    	
	    <a href="javascript:;"> 
		    <p><i class="fa fa-fw fa-times"></i>删除</p>
	    </a>
	</div>
	
</li>	
</script> 
<script src="${uiBase}/js/pages/advertisement/ad_edit.js?v=${resourceVersion}"></script>
 <script type="text/javascript" src="${uiBase}/vendor/ajaxfileupload/ajaxfileupload.js"></script> 
</html>
