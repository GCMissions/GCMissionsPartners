<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<title>合集预览</title>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,width=device-width,height=device-height,initial-scale=1"/>
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
<style type='text/css'>
	@charset "utf-8";
@import "../../vendor/font-awesome/css/font-awesome.min.css";
* {
  margin: 0;
  padding: 0;
  outline: none;
  box-sizing: border-box;
  -moz-box-sizing: border-box;
  /* Firefox */
  -webkit-box-sizing: border-box;
  /* Safari */
}
*:not(input,textarea) {
  -webkit-touch-callout: inherit;
  -webkit-user-select: auto;
}
body {
  width: 100%;
  font-family: Hiragino Sans GB, Arial, Helvetica, "����", sans-serif;
  -webkit-touch-callout: inherit ;
  -webkit-user-select: auto ;
  background-color: #fff;
  color: #333;
  box-sizing: border-box;
  -moz-box-sizing: border-box;
  /* Firefox */
  -webkit-box-sizing: border-box;
  /* Safari */
}
a {
  color: #878787;
  text-decoration: none;
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
}
a:hover {
  text-decoration: none;
}
button,
input,
select,
textarea {
  font-size: 100%;
  margin: 0;
  padding: 0;
  outline: none;
}
dt,
dd {
  display: inline-block;
}
textarea,
input {
  resize: none;
  outline: none;
}
textarea {
  resize: none;
  -webkit-appearance: none;
}
ul,
ol,
li {
  list-style: none;
}
em {
  font-style: normal;
}
.widthbody {
  max-width: 770px;
  width: 100%;
  margin: 0 auto;
}
.widthbody img{
  max-width: 100%;
  margin: 0;
}
.graybody {
  background-color: #f1f0ee;
}
.whitebody {
  background-color: #fff;
}
</style>
<body class="widthbody">
	<div class="mainBody">
		<input type="hidden" id="intersectionId" value="${id}">
		<div class='content'>
			
		</div>
	</div>
</body>
<#include "/footer.ftl" />
<script src="${uiBase}js/pages/intersection/intersection_preview.js?v=${resourceVersion}"></script>
</html>
