<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<title>团购详情</title>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,width=device-width,height=device-height,initial-scale=1"/>
<link rel="stylesheet" href="${uiBase}vendor/swiper/swiper.min.css" />
<link rel="stylesheet" href="${uiBase}css/pages/wrkd/group_buy_preview.css?v=${resourceVersion}">
</head>
<script type="text/javascript">
	function imgError(image){  
       image.setAttribute('src','${uiBase}img/groupBuyPreview/user_defalut.png');  
 	}
 	//反转译HTML标签
	function HTMLDecode(text) { 
	    var temp = document.createElement("div"); 
	    temp.innerHTML = text; 
	    var output = temp.innerText || temp.textContent; 
	    temp = null; 
	    return output; 
	} 
</script>
<body class="widthbody">
	<div class="mainBody" style="display: none;">
	<input type="hidden" id="teamBuyId" value="${teamBuyId}" />
		<div id="groupImg">
			
		</div>
		<h3 id="groupName"></h3>
		<p class="group-num">剩余<span id="groupNum"></span>件</p>
		<div class="choose-box" choose='false'>请选择规格<span id="speciName"></span></div>
		<section class="clearfix">
			<div class="group-price-box">
				<p class="group-price-first"><span class="price-icon">￥</span><span class="group-price"></span>/件</p>
				<p class="group-price-second"><span id="startCount"></span>件成团</p>
			</div>
			<div class="original-price-box">
				<p class="group-price-first"><span class="price-icon">￥</span><span class="product-price"></span>/件</p>
				<p class="group-price-second">原价</p>
			</div>
		</section>
		<section class="group-list-box">
			<hr color="#15A1FE"/>
		</section>
		<section>
			<div class="tab-box">
				<img src="${uiBase}img/groupBuyPreview/product-detail.png" class="tab-child" href="productDetail"/>&nbsp;&nbsp;
				<img src="${uiBase}img/groupBuyPreview/group-desc.png" class="tab-child" href="groupDesc"/>
			</div>
		</section>
		<div id="productDetail" class="active">
		</div>
		<div id="groupDesc">
		</div>
		<div class="special-desc">
			<label class="specialDesc-header">特别说明</label>
			<div id="specialDesc">
				
			</div>
		</div>
	</div>
	<div id="footerHtml"></div>
	
	<div id="loadingToast" class="weui_loading_toast" style="display:none;">
	    <div class="weui_mask_transparent"></div>
	    <div class="weui_toast">
	        <div class="weui_loading">            <!-- :) -->
	            <div class="weui_loading_leaf weui_loading_leaf_0"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_1"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_2"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_3"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_4"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_5"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_6"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_7"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_8"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_9"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_10"></div>
	            <div class="weui_loading_leaf weui_loading_leaf_11"></div>
	        </div>
	        <p class="weui_toast_content">数据加载中</p>
	    </div>
	</div>
	<div id="toast" style="display: none;">数量超出范围~</div>
</body>
<!-- 首页轮播广告 -->
<script type="text/html" id="groupImgTemp">
	<div class="swiper-container swiper-container-horizontal">
	  	<div class="swiper-wrapper">
	  		<%for (var i = 0; i < data.imageList.length; i++) {%>
	  			<a class="swiper-slide">
		    		<img src="<%=data.imageList[i]%>" style="width: 100%;">
		    	</a>
	  		<%}%>
	  	</div>
	  	<label class="jioned-num">已团购出<%=data.saleCount%>件商品</label>
	  	<div class="status">
			<%if(data.status=='3'){%>
	  			<img src="${uiBase}img/groupBuyPreview/active_now.png">
			<%}else if(data.status=='2'){%>
				未开始
			<%}else if(data.status=='4'||data.status=='0'){%>
	  			<img src="${uiBase}img/groupBuyPreview/active_end.png">
  			<%}%></div>
	  	<div class="swiper-pagination"></div>
	</div>
</script>

<script type="text/html" id="groupListTemp">
	<label class="clearfix"><img src="${uiBase}img/groupBuyPreview/laba.png" class="laba"/>
	<%if(list.length){%>
		<span>以下小伙伴正在发起团购，你可以直接参团</span><a class="more" data-href="groupList.html">更多></a>
	<%}else{%>
		<span>还没有小伙伴发起团购，快去开团吧!</span>
	<%}%>
	</label>
	<ul>
	<%for (var i = 0; i < list.length; i++) {%>
		<li class="clearfix" data-recordId="<%=list[i].recordId%>">
			<div class="organizer"  data-uid="<%=list[i].createTeamId%>">
				<img src="<%=list[i].custImage%>" onerror="imgError(this);"/>
				 <%var createTeamName = list[i].createTeamName%>
				 <%if(/^\d+$/.test(createTeamName)){%>
				 	<%var formatName = createTeamName.substring(0,3)+'****'+createTeamName.substr(createTeamName.length-1,1)%>
				 	<p><%=formatName%></p>
				 <%}else{%>
				 	<p class="createTeamName"><%=HTMLDecode(createTeamName)%></p>
				<%}%>
			</div>
			<a class="group-info" href="javascript:void(0);" data-href ="joinGroup.html?rId=<%=list[i].recordId%>" data-rId="<%=list[i].recordId%>">
				<p>还差<span><%=list[i].lessNum%></span>件成团</p>
				<p class="progressbar progressbar-in" data-progress="<%=list[i].percentage%>">
					<span style="transform: translate3d(-<%=100-list[i].percentage%>%, 0px, 0px);"></span></p>
				<p class="endtime">剩余 <span class="end-time">37:36:45</span>结束</p>
			</a>
		</li>
	<%}%>
	</ul>
	<hr color="#15A1FE"/>
</script>
<!--
	底部购买按钮
-->
<script type="text/html" id="footerTemp">
	<footer class="c-footer">
		<a class="index-href" href='javascript:void(0)'>
			<img src="${uiBase}img/groupBuyPreview/index.png" width="100%"/>
		</a>
		<div class="buy-button singleBuy">
			<p id="buy-price"><span class="price-icon">￥</span><span class="producePrice"><%=data.formatProductPrice%></span></p>
			<p class="buy-desc" id="buyOrigin">单独购买</p>
		</div>
		<div class="buy-button startGroup">
			<p id="group-price"><span class="price-icon">￥</span><span class="teamPrice"><%=data.formatTeamPrice%></span></p>
			<p class="buy-desc" id="buyGroup">我要开团</p>
		</div>
	</footer>
</script>
<#include "/footer.ftl" />
<script type="text/javascript" src="${uiBase}vendor/swiper/swiper.min.js"></script>
<script type="text/javascript" src="${uiBase}vendor/template/template.js"></script>
<script src="${uiBase}js/pages/wrkd/groupBuy/group_buy_preview.js?v=${resourceVersion}"></script>
</html>
