<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/pages/product.css">

</head>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
      
		<section class="content-header">
		 
		      <ol class="breadcrumb">
				<li><i class="fa fa-dashboard"></i> 商品管理</li>
				<li class="active">添加商品</li>
			 </ol>
		  
		  
		</section>
      
       <!-- Content Header (Page header) -->
       
        <div id="goodsSelectDiv"></div>
        <div id="photoDiv"></div>

    
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                   		 <h3 class="box-title"><label>选择分类</label>${msg}</h3>
                       
                        <!-- /.box-tools -->
                    </div><!-- /.box-header -->
                    
                    <div class="box-body">
                        <div class="cate-wrapper">
                            
                            <div id="cate-container">
                             
                            </div>

                            <div id="cate-path">
                                <dl>
                                    <div class="clearfix">
                                        <dt>您当前选择的是：</dt>
                                        <dd><ol class="category-path" ></ol></dd>
                                    </div> 
                                 </dl>

                            </div>

                            <div id="cateBottom" >
                                <button type="button" data-value="" id="nextBtn" class="btn btn-primary" disabled="disabled" ><i class="fa fa-forward"> </i>下一步</button>
                                <button class="btn btn-success backPage" type="button"><i class="fa fa-backward"> 返回 </i></button>
                            </div>
                        </div>

                    </div><!-- /.box-body -->
                    
                   
                </div>
             </div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
  
    
</body>
<#include "/footer.ftl" />
 <script src="${uiBase}/js/pages/product/productChooseCat.js?v=${resourceVersion}" ></script>

</html>
