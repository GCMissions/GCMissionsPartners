<#assign headComponents = [] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/region.css?v=${resourceVersion}">

</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
    
         <section class="content-header">
         
          <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i> 系统设置</li>
            <li>开放城市管理</li>
          </ol>
          
          
         </section>
      
         
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary ">
                	<div class="box-body">
	                    <div class="region">
							<div class=" block provinceList  form-group">
								   <!--  <span class="mr-15" data-id="110000">北京 (<span class="openNum">1</span>/1)</span>
		            			    <span class="mr-15" data-id="120000">天津 (<span class="openNum">1</span>/1)</span>
								    <span class="mr-15" data-id="130000">河北省 (<span class="openNum">11</span>/11)</span>
								    <span class="mr-15" data-id="140000">山西省 (<span class="openNum">11</span>/11)</span>
								    <span class="mr-15" data-id="150000">内蒙古自治区 (<span class="openNum">12</span>/12)</span>
								    <span class="mr-15" data-id="210000">辽宁省 (<span class="openNum">14</span>/15)</span>
								    <span class="mr-15" data-id="220000">吉林省 (<span class="openNum">9</span>/9)</span> -->
		            		</div>
	                    </div>
	                  
	               
				        <div class="cityList">
							<form id="citys" class="form-inline citys" action="">
								<div class="form-group block">
									
									<!-- <div class="province cityList">
										<span class="mr-15 active" data-id="500100">重庆市</span>
										<span class="mr-15" data-id="500300">两江新区</span>
									</div> -->
								</div>
								
								
							</form>
						</div>
					
				        <div class="group-btn text-center">
							<button type="button" class="btn btn-primary" id="save"> 开 通 </button>
						</div>
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
<script type="text/javascript" src="${uiBase}/js/pages/region/region.js?v=${resourceVersion}"></script> 
<script>
	 $(function() {
	  
	    regionApp.init();
	});
  </script>
</html>
