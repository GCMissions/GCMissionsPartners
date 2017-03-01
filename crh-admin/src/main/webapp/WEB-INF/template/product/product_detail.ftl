<!DOCTYPE html>
<html> 
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
        <title>五粮液商城</title>
        <meta name="description" content="">
		
        <link href="${b2cUIBase}/css/detail.css" rel="stylesheet">
        
      	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	    <!--[if lt IE 9]>
	    <script type="text/javascript" src="${b2cUIBase}/vendor/html5shiv/html5shiv.js></script>
	    <script type="text/javascript" src="${b2cUIBase}/vendor/respond/respond.js"></script>
	    <![endif]-->
 		
	    <script type="text/javascript" src="${b2cUIBase}/js/require.js"></script>
	    <script type="text/javascript">
            define('global', {
                context: '${b2cBase}',
                assets: '${b2cUIBase}',
            })
    	</script>
    	<script type="text/javascript" src="${b2cUIBase}/js/base.js"></script>
    </head>
    <body>
	
	
<div class="content">
		
	<div class="main-body">
		<!--加入购物车/选择商品-->
		<div class="p-box clearfix">
			<div class="preview">
				<div id="vertical" class="bigImg">
					<img src="" alt="" id="midimg" />
					<div style="display:none;" id="winSelector"></div>
				</div><!--bigImg end-->	
				<div class="smallImg">
					<div class="scrollbutton disabled"><a class="smallImgLeft"></a></div>
					<div id="imageMenu">
						<ul class="clearfix">
							<#list productImage as product>
								<li>
									<img src="${file}/${product.image}"/>
								</li>
							</#list>
							
						</ul>
					</div>
					<div class="scrollbutton"><a class="smallImgRight"></a></div>
				</div><!--smallImg end-->	
				<div id="bigView" style="display:none;"><img width="800" height="800" alt="" src="" /></div>
			</div>
			
			<div class="item-inner">
				<div id="itemInfo" data-pId="${product.productId}">
					<h2 class="name">${product.productName}</h2>
					<p class="note">${product.note}</p>
					<div id="summary" class="clearfix">
						
						<div class="summary-price-box">
							<p class="summary-price">厂商指导价：<span id="originalPrice"></span></p>
							<p class="summary-price">活动价：<span id="salePrice"></span></p>
							<div class="summary-info">
								<a>去官网了解更多</a>
							</div>
						</div>
					</div>
				</div>
				<div class="choose-box">
					<div class="relation-box">
						<div class="relation">
							<span>关联</span>
						</div>
						<div class="relation-list clearfix">
							<!--<a>52度五粮液（新品）375ml</a>
							<a>52度五粮液</a>
							<a>52度五粮液特曲500ml</a>
							<a>52度五粮液特曲500ml</a>
							<a>52度五粮液特曲500ml</a>
							<a>52度五粮液特曲500ml</a>-->
						</div>
					</div>
					<div class="number-box">
						<div class="number">数量</div>
						<div class="number-input clearfix">
							<span class="stock">
								<a href="javascript:void(0);" title="减1" hidefocus="" id="reduce" class="disabled"><i class="fa fa-minus" aria-hidden="true"></i></a>
								<input id="numText" type="text" class="text" value="1" maxlength="6" title="请输入购买量">
								<a href="javascript:void(0);" id="increase" title="加1"><i class="fa fa-plus" aria-hidden="true"></i></a>
							</span>
						</div>
					</div>
					<div class="btn-box">
						<button class="btn" id="addCartBtn">加入购物车</button>
						<button class="btn" id="buyBtn">立即购买</button>
					</div>
				</div>
			</div>
		</div>
		<!--图文详情-->
		
		<div class="p-info clearfix">
			
			<div class="m-list">
				<div class="ml-wrap">
					<ul id="pTab" class="nav nav-tabs">
						<li class="active">
							<a href="#detail" data-toggle="tab">
								图文详情
							</a>
						</li>
						
					</ul>
					<div id="p-tab-content" class="tab-content">
						<div class="tab-pane fade in active" id="detail">
							${product.description}
						</div>
						
					</div>
				</div>
			</div>
			
			<div class="aside-bar">
				<h4>浏览了该商品的用户还买了</h4>
				<ul>
					
				</ul>
			</div>
		</div>
	</div>
</div>


    </body>
    <script type="text/javascript" src="${b2cUIBase}/js/page/product/detail.js"></script>
</html>