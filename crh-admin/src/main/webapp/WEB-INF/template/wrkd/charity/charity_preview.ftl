<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<title>公益详情</title>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,width=device-width,height=device-height,initial-scale=1"/>
<link rel="stylesheet" href="${uiBase}vendor/swiper/swiper.min.css" />
<link rel="stylesheet" href="${uiBase}css/pages/wrkd/charity/charityPreview.css?v=${resourceVersion}">
</head>
<body class="widthbody" id="scrollBody">
	<input type="hidden" id="charityId" value="${charityId}" />
	<div class="container"></div>
	<div class="bottom-bar graybody service" style="display: none;">
		<a><img id="on-buy" src="${uiBase}img/active_jion.png" /></a>
	</div>
</body>
<!-- 活动说明 -->
<script type="text/html" id="actDetail">
<%if(obj){%>
	<div class="swiper-container swiper-container-horizontal pro-img" >
        <div class="swiper-wrapper">
        	<%for(var i = 0; i < obj.imageList.length; i ++){%>
            <a class="swiper-slide"><img src="<%:=obj.imageList[i]%>" style="max-height:400px;" /></a>
        	<%}%>
        </div>
        <%if(obj.status == 3){%>
        <label class="jioned-num">已有<%:=obj.actNum%>人参加</label>
		<%}%>
	  	<div class="status">
	  		<%if(obj.status == 3){%>
	  			<img src="${uiBase}img/groupBuyPreview/active_now.png">
	  		<%}else if(obj.status == 4){%>
	  			<img src="${uiBase}img/groupBuyPreview/active_end.png">
	  		<%}%>
	  	</div>
        <!-- 如果需要分页器 -->
        <div class="swiper-pagination"></div>
    </div>
	<%if(obj.status == 4){%>
		<div class="act-end-info">
	    	<p>共有 <%:=obj.actNum%> 人先出爱心，募捐到 <%:=obj.actNum*0.5%> 元</p>
	    </div>
	<%}%>
    <div class="pro-info">
    	<p class="pro-info-item pro-name"><%:=obj.actName%></p>
    </div>
    <div class="act-desc">
		<label class="actDecs-header">活动介绍</label>
		<div id="actDesc">
			<%:=obj.explainNote%>
		</div>
	</div>
    <div class="act-desc">
		<label class="actDecs-header">详情介绍</label>
		<div id="proDesc">
			<%if(obj.detailDesc != null){%><%:=obj.detailDesc%><%}%>
		</div>
	</div>
	<%if(obj.status == 4){%>
		<div class="act-desc">
			<label class="actDecs-header">活动反馈</label>
			<div id="feedBackDesc" class="desc">
				<%if(obj.feedback != null){%><%:=obj.feedback%><%}%>
			</div>
		</div>
	<%}%>
<%}%>
</script>
<#include "/footer.ftl" />
<script type="text/javascript" src="${uiBase}vendor/swiper/swiper.min.js"></script>
<script type="text/javascript" src="${uiBase}vendor/template/template.js"></script>
<script src="${uiBase}js/pages/wrkd/charity/charityPreview.js?v=${resourceVersion}"></script>
</html>
