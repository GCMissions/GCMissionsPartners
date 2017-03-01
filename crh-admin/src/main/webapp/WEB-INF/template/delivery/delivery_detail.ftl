<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/delivery.css?v=${resourceVersion}">

</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
       <section class="content-header">
	 	   <ol class="breadcrumb">
			   <li><i class="fa fa-dashboard"></i> 商家管理</li>
			   <li>终端配送商信息</li>
		   </ol> 
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
				 <div class="nav-tabs-custom">
	               <ul class="nav nav-tabs">
                      <li class="active"><a href="#tab_1" data-toggle="tab">基本信息</a></li>
                    </ul>
                    <div class="tab-content">
                        <div id="tab_1" class="tab-pane active">
                            <div class="panel-body">
                                <div class="row">
		                            <div class="col-md-12">
		               					<div class="col-sm-4">
	                                   		<label class="control-label">终端配送商编号：</label>
	                                    	<span>${orgDto.orgCode}</span>
	                                    	<input class="hidden" value="${orgDto.orgId}" id="parentId">
		                                </div>
		                                <div class="col-sm-4">
	                                   		<label class="control-label">终端配送商名称：</label>
	                                    	<span>${orgDto.orgName}</span>
		                                </div>
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col-md-12">
		               					<div class="col-sm-4">
	                                   		<label class="control-label">终端配送商登录名：</label>
	                                    	<span>${orgDto.loginId}</span>
		                                </div>
		                                <div class="col-sm-4">
		               					    <a class="btn btn-default" id="resetPsd">重置密码</a>
		                                </div>
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col-md-12">
		               					<div class="col-sm-4">
	                                   		<label class="control-label">联系人：</label>
	                                    	<span>${orgDto.contact}</span>
		                                </div>
		                               	<div class="col-sm-8">
	                                   		<label class="control-label">所在地区：</label>
	                                    	<span>${orgDto.regionName}</span>
		                                </div>	                                                            
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col-md-12">
		                             	<div class="col-sm-4">
	                                   		<label class="control-label">联系电话：</label>
	                                    	<span>${orgDto.phone}</span>
		                                </div>		               					
		                                <div class="col-sm-8">
	                                   		<label class="control-label">详细地址：</label>
	                                    	<span>${orgDto.address}</span>
	                                    	<input type="text" class="form-control hidden" id="serviceAddress" value="${orgDto.address}">
	                                    	<input type="text" class="form-control hidden" id="lng" value="${orgDto.lng}">
	                                    	<input type="text" class="form-control hidden" id="lat" value="${orgDto.lat}"> 	                                    		                                    	
		                                </div>		                                                            
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col-md-12">
		               					<div class="col-sm-4">
	                                   		<label class="control-label">开户银行：</label>
	                                    	<span>${orgDto.bankName}</span>
		                                </div>
		                                <div class="col-sm-4">
	                                   		<label class="control-label">开户支行：</label>
	                                    	<span>${orgDto.branchName}</span>
		                                </div>		                                                            
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col-md-12">
		               					<div class="col-sm-4">
	                                   		<label class="control-label">开户账户：</label>
	                                    	<span>${orgDto.bankAcct}</span>
		                                </div>
		                                <div class="col-sm-4">
	                                   		<label class="control-label">开户人：</label>
	                                    	<span>${orgDto.bankContact}</span>
		                                </div>		                                                            
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col-md-12">
		               					<div class="col-sm-4">
	                                   		<label class="control-label">商户号：</label>
	                                    	<span>${orgDto.businessNumber}</span>
		                                </div>
		                                <div class="col-sm-4">
	                                   		<label class="control-label">终端号：</label>
	                                    	<span>${orgDto.terminalNumber}</span>
		                                </div>		                                                            
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col-md-12">
		                            	<div class="col-sm-4">
	                                   		<label class="control-label">终端号：</label>
	                                    	<span>${orgDto.deviceNumber}</span>
		                                </div>	
		               					<div class="col-sm-4">
	                                   		<label class="control-label">是否专卖店：</label>
	                                    	<span><#if orgDto.barcodeFlag == 1>是<#else>否</#if></span>
		                                </div>		                                                            
		                            </div>
		                        </div>
		                        <div class="row">
		                            <div class="col-md-12">
		                            	<div class="col-sm-4">
	                                    	<label class="control-label">关联区域平台商：</label>
		                                    <span>${orgDto.parentName}</span>
		                                </div>   
		               					<div class="col-sm-4">
		               						<input type="text" id="qrCodeUrl" class="hidden" value="${orgDto.qrCodeUrl}">		               	
	                                    	<label class="control-label">二维码：</label>
		                                    <a id="codePic">查看</a>
		                                    <div id="code" class="hidden"></div>
		                                </div>                                
		                            </div>
		                        </div>
		                        <div class="row">
		                        	<div class="col-md-12">
		                        		<div id="map" style="width:800px; height:500px;"></div>
		                        	</div>
		                        </div>
                            </div>
                        </div>
                    </div>
                </div>
				<div class="row">
	                <div class="col-sm-12 text-center">
	                    <button class="btn btn-success backPage" type="button"><i class="fa fa-backward"> 返回 </i></button>		                   
	                 </div>
                </div> 
	          </div>                        
        	</div>
        <div class="clearfix"></div>     
      </div>
   </div>
</body>
<#include "/footer.ftl" />
 <script src="${uiBase}/js/pages/delivery/delivery_detail.js?v=${resourceVersion}"></script>
 
 <!-- 地图 -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sHLz8MFUdDtLaAj54AETxwxz"></script>
<script type="text/javascript" src="${uiBase}/js/pages/supplier/map.js?v=${resourceVersion}"></script>
</html>
