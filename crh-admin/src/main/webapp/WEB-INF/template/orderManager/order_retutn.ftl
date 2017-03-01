<#assign headComponents = ["bootTable", "bootDialog"] > <#include
"/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}/css/pages/product.css?v=${resourceVersion}">
<style >
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>订单管理</li>
					<li>退款处理</li>

				</ol>
			</section>



			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="box box-primary">
						<div class="form-horizontal search-group">
							<div class="box-body">
								<div class="form-group">
									<label class="col-sm-2 control-label">订单号：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.orderId}</label>
									<label class="col-sm-2 control-label">订单金额（元）：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.totalAmount}</label>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">订单状态：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.status}</label>
									<label class="col-sm-2 control-label">实付金额（元）：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.actualAmount}</label>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">手机号：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.phone}</label>
									<label class="col-sm-2 control-label">优惠（元）：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.couponAmount}</label>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">创建时间：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.createDate}</label>
									<label class="col-sm-2 control-label">运费（元）：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.shipAmount}</label>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">用户备注：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.remark}</label>
									<label class="col-sm-2 control-label">是否退款：</label>
									 <label  class="col-sm-4" style="font-weight:100">${order.spec}</label>

									 <button  class="col-sm-4" style="font-weight:100">${order.spec}</button>
								</div>
								 
								<div class="form-group">
									 <textarea rows="5"   class="col-sm-4" style="font-weight:100">${order.spec}</input>
									 <button  class="col-sm-4" style="font-weight:100">${order.spec}</button>
								</div>

							</div>


							<!-- /.box-tools -->
						</div>
						<!-- /.box-header -->

						<div class="box-body">
						    <#list remarks as rem>
								<div class="form-group">
									<label class="col-sm-2 control-label">序号：</label>
									 <label  class="col-sm-4" style="font-weight:100">${rem.id}</label>
									<label class="col-sm-2 control-label">备注人：</label>
									 <label  class="col-sm-4" style="font-weight:100">${rem.creater}</label>
									<label class="col-sm-2 control-label">备注时间：</label>
									 <label  class="col-sm-4" style="font-weight:100">${rem.createrDate}</label>
								</div>
						    <textarea rows="5"   class="col-sm-4" style="font-weight:100">${order.remark}</input>
						    
						    </#list>  
							<div >
							
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /.content -->
			<div class="clearfix"></div>

		</div>
		<!-- /.content-wrapper -->
	</div>
	<!-- ./wrapper -->


</body>
<#include "/footer.ftl" />
<script id="imgTpl" type="text/html">
<div class="box-body form-horizontal">
	<div class="form-group row imgPl" id="imgP"> 
	</div>
</div>
</script>
		
<script>
$(function() {
	
	var table = $.GLOBAL.utils.loadBootTable({
		table : $('#dataList'),
	    refreshBtn : $('#search'),
	    url: 'productRate/list',
	    sidePagination:'server',
	    pagination : true,
	    queryParamsType: "limit",
	    queryAddParams: function() {
	    	return {
	    		productNo: $("#productNo").val()
	    	}
	    },
	    columns: [
				{
					width : 50,
					title : '序号',
					align : 'center',
					formatter : function(value, row, index) {
						return index + 1;
					}
				},
	  	        {
	  	            field:'userName',
	  	            sortable: false
	  	        },
	  	        {
	  	            field:'telnum',
	  	            sortable: false
	  	        }, 
	  	        {
	  	            field:'createDate',
	  	            sortable: false
	  	        }, 
	  	      	{
	  	        	class:'feedInfo',
	  	            field:'feedInfo',
	  	            sortable: false
	  	        },
	  	        {
	  	            field:'star',
		            formatter:function(value,row,index){  
		            	var html;
		            	if(row.star==5){
		            		html = '<div class="rateStars"> <div class="goodRate"></div> <div class="goodRate"></div><div class="goodRate"></div> <div class="goodRate"></div> <div class="goodRate"></div>';
		            	} else if(row.star==4) {
		            		html = '<div class="rateStars"> <div class="goodRate"></div> <div class="goodRate"></div><div class="goodRate"></div> <div class="goodRate"></div> <div class="badRate"></div>';
		            	} else if(row.star==3) {
		            		html = '<div class="rateStars"> <div class="goodRate"></div> <div class="goodRate"></div><div class="goodRate"></div> <div class="badRate"></div> <div class="badRate"></div>';
		            	} else if(row.star==2) {
		            		html = '<div class="rateStars"> <div class="goodRate"></div> <div class="goodRate"></div><div class="badRate"></div> <div class="badRate"></div> <div class="badRate"></div>';
		            	} else if(row.star==1) {
		            		html = '<div class="rateStars"> <div class="goodRate"></div> <div class="badRate"></div><div class="badRate"></div> <div class="badRate"></div> <div class="badRate"></div>';
		            	} else if(row.star==0) {
		            		html = '<div class="rateStars"> <div class="badRate"></div> <div class="badRate"></div><div class="badRate"></div> <div class="badRate"></div> <div class="badRate"></div>';
		            	}
		            	
		            	return html;
		            }
	  	        }, 
	  	      	{
		            formatter:function(value,row,index){  
		            	var html;
		            	if(row.imgUrl){
		            		html = ' <a title="【查看】" target="_self" href="" data-id='+row.imgUrl+' class="viewItem"> 查看</a>';
		            	}
		            	return html;
		            }
	  	        }
	  	     ]
	});

	$('#dataList').on('click', "a.viewItem", _(viewImg).bind(this));
    function viewImg(e){
    	e.preventDefault();
	 	var $target = $(e.target),
        urls = $target.data('id');
         
		BootstrapDialog.show({
	        title: '评价图片',
	        draggable: true,
	        message: $(template('imgTpl', {})),
	        buttons: [ {
	            label: '确认',
	            action: function(dialog) {
	                dialog.close();
	            }
	        }
		],
		onshown:function(dialog){
			var html="";
			var imgDiv ='<div class="rateImg"> <a href="{{url}}" title="点击查看大图">'
						+'<img src="'+fileUrlPrefix + '{{url}}" style ="height:130px;width:130px;"></img></a> </div>';
			var listUrls = urls.split(";");
			for(var i in listUrls ){
				html += imgDiv.template({"url":listUrls[i]});
			}
			$("#imgP").html(html);
		}
	    });
	};
});
</script> 
</html>
