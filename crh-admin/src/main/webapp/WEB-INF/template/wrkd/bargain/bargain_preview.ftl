<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<title>24H详情</title>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,width=device-width,height=device-height,initial-scale=1"/>
<link rel="stylesheet" href="${uiBase}vendor/swiper/swiper.min.css" />
<link rel="stylesheet" href="${uiBase}css/pages/wrkd/bargain_preview.css?v=${resourceVersion}">
</head>
<script type="text/javascript">
	function imgError(image){  
       image.setAttribute('src','../../img/common/user_defalut.png');  
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
	<input type="hidden" id="bargainId" value="${twentyFourId}" />
	<div class="mainBody" style="display: none;">
		<div id="groupImg">
			
		</div>
		<h3 id="groupName"></h3>
		<p id="desc">这是描述</p>
		<p class="group-num">剩余<span id="groupNum"></span>件</p>
		<p class="sale-price">原价&nbsp;:&nbsp;<span id="salePrice"></span></p>
		<hr color="#15A1FE"/>
		<section class="productDetail-box">
			<div class="tab-box">
				<label class="productDetail-header">活动说明</label>
			</div>
			<div id="productDesc" class="active"></div>
			<div class="tab-box">
				<label class="productDetail-header">活动详情</label>
			</div>
			<div id="productDetail" class="active">
			</div>
		</section>
		
		<footer><a alt="我要参加" id="joinBtn"></a></footer>
	</div>
</body>
<!-- 首页轮播广告 -->
<script type="text/html" id="groupImgTemp">
	<div class="swiper-container">
	  	<div class="swiper-wrapper">
	  		<%for (var i = 0; i < data.imgUrls.length; i++) {%>
	  			<a class="swiper-slide">
		    		<img src="<%=data.imgUrls[i]%>" style="width: 100%;">
		    	</a>
	  		<%}%>
	  	</div>
	  	<label class="jioned-num">已有<%=data.participantsNum%>人参加</label>
	  	<div class="status">
			<%if(data.actStatus=='2'){%>
	  			<label class="status_text">未开始</label>
			<%}else if(data.actStatus=='3'){%>
	  			<img src="${uiBase}img/groupBuyPreview/active_now.png">
			<%}else if(data.actStatus=='4'){%>
	  			<img src="${uiBase}img/groupBuyPreview/active_end.png">
  			<%}%></div>
	  	<div class="swiper-pagination"></div>
	</div>
</script>
<#include "/footer.ftl" />
<script type="text/javascript" src="${uiBase}vendor/swiper/swiper.min.js"></script>
<script type="text/javascript" src="${uiBase}vendor/template/template.js"></script>
<script src="${uiBase}js/pages/wrkd/bargain/bargain_preview.js?v=${resourceVersion}"></script>
</html>
