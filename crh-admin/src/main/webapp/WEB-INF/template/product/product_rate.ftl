<#assign headComponents = ["bootTable", "bootDialog"] > <#include
"/header.ftl" />
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">
<link rel="stylesheet" href="${uiBase}/css/pages/product.css?v=${resourceVersion}">
<style >
.feedInfo{
word-break:break-all ;
}
.createDate{
width:95px !important;
}

</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i>商品信息</li>
					<li>查看用户评价</li>

				</ol>
			</section>



			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="box box-primary">
						<div class="form-horizontal search-group">
							<div class="box-body">
								<div class="form-group">
									<label class="col-sm-2 control-label">商品名称：</label>
									 <label  class="col-sm-4" style="font-weight:100">${productName}</label>
                                <input type="hidden" id ="productNo" value="${productNo}">
								</div>
								
								<div class="form-group "> 
									<label class="col-sm-2 control-label">综合评价：</label> 
									<div class="rateStars">
									<#if avgStar == 5>
										<div class="goodRate"> </div> 
										<div class="goodRate"> </div> 
										<div class="goodRate"> </div> 
										<div class="goodRate"> </div> 
										<div class="goodRate"> </div> 
									<#elseif avgStar == 4>
										<div class="goodRate"> </div> 
										<div class="goodRate"> </div> 
										<div class="goodRate"> </div> 
										<div class="goodRate"> </div> 
										<div class="badRate"></div> 
									<#elseif avgStar == 3>
										<div class="goodRate"> </div> 
										<div class="goodRate"> </div> 
										<div class="goodRate"> </div> 
										<div class="badRate"> </div> 
										<div class="badRate"> </div> 
									<#elseif avgStar == 2>
										<div class="goodRate"> </div> 
										<div class="goodRate"> </div> 
										<div class="badRate"> </div> 
										<div class="badRate"> </div> 
										<div class="badRate"> </div> 
									<#elseif avgStar == 1>
										<div class="goodRate"> </div> 
										<div class="badRate"> </div> 
										<div class="badRate"> </div> 
										<div class="badRate"> </div> 
										<div class="badRate"> </div> 
									<#elseif null==avgStar || avgStar == 0>
										<div class="badRate"> </div> 
										<div class="badRate"> </div> 
										<div class="badRate"> </div> 
										<div class="badRate"> </div> 
										<div class="badRate"> </div> 
									</#if>
									</div>
									<label class="col-sm-1 control-label">（${allRate}）</label> 
										 
									
								</div>

							</div>


							<!-- /.box-tools -->
						</div>
						<!-- /.box-header -->

						<div class="box-body">
							<table id="dataList" class="table table-bordered table-hover">
								<thead>
									<th>序号</th>
									<th>用户昵称</th>
									<th>手机号</th>
									<th>评价时间</th>
									<th>评价内容</th>
									<th>评价等级</th>
									<th>点评图片</th>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-12">
	        	<div class="col-sm-3 col-sm-offset-5">
	        		<button class="btn  btn-success backPage" id="back" ><i class="fa fa-backward"></i> 返  回</button>
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
	  	            class:'createDate',
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
	      //  buttons: [ {
	      //      label: '确认',
	      //      action: function(dialog) {
	      //          dialog.close();
	      //      }
	       // }
		//],
		onshown:function(dialog){
			var html="";
			var imgDiv ='<div class="rateImg" style="margin:0 10px;"> <a href="{{url}}" target="_top _blank " title="点击查看大图">'
						+'<img src="{{url}}" style ="height:130px;width:130px;"></img></a> </div>';
			var listUrls = urls.split(";");
			for(var i in listUrls ){
				if(listUrls[i]){
					html += imgDiv.template({"url":listUrls[i]});
				}
			}
			$("#imgP").html(html);
		}
	    });
	};
});
</script> 
</html>
