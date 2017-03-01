<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- jQuery 2.1.4 -->
<script src="${uiBase}/vendor/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=826e806b86676d155282de3d37188137"></script>
<script src="http://api.map.baidu.com/library/MarkerTool/1.2/src/MarkerTool_min.js" type="text/javascript"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/CityList/1.4/src/CityList_min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>js百度地图api坐标地址标注功能 </title>
</head>
<body>

<div id="bdmapModal">
	<div class="title">
		<h2>1.选择位置>2.标注位置>3.保存位置</h2>
		
	</div>
	<div class="content">
		<div class="areaWrap">
			<div id="areaSelection"></div>
		</div>
		<div id="bdMap" style="width:700px;height:400px;"></div>
		<div style="margin-top: 15px;text-align: right">
			<input type="hidden" id="lat"><input type="hidden" id="lng">
			<input type="button"  class="btn saveMapSelection" value="保存">
			<input type="button"  class="btn cancelMapSelection" value="取消">
		</div>
	</div>
</div>
</body>
<script>
$(function() {


	var marker,
		oDrag = document.getElementById("bdmapModal");
	// 百度地图API功能
	var map = new BMap.Map("bdMap");            // 创建Map实例
	map.enableScrollWheelZoom(true);
	map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT}));    // 右下比例尺
	map.setDefaultCursor("Crosshair");//鼠标样式
	map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT}));  //右上角，仅包含平移和缩放按钮
	var cityList = new BMapLib.CityList({
		container: 'areaSelection',
		map: map
	});
	map.addEventListener("click", showInfo);
	//@todo 从url中获取坐标生成 defaultPoint
	//114.309531,30.606385 武汉
	var defaultPoint = new BMap.Point(114.309531,30.606385);
	
	map.centerAndZoom('武汉',12);
	creatMarker(defaultPoint); 
	
	          
	function showInfo(e){
		map.clearOverlays();
		marker = new BMap.Marker(new BMap.Point(e.point.lng, e.point.lat));  // 创建标注
		map.addOverlay(marker);
		//获取经纬度
		document.getElementById("lng").value = e.point.lng;
		document.getElementById("lat").value = e.point.lat;
	}
	//创建地图遮盖物
    function creatMarker(point) {
        var marker = new BMap.Marker(point);        // 创建标注
        map.addOverlay(marker);                     // 将标注添加到地图中
        marker.enableDragging();                    //标注可拖拽
        marker.addEventListener("click", function (e) {
            searchInfoWindow.open(marker);
        })
        marker.addEventListener("dragend", function (e) { //监听标注的dragend事件，获取拖拽后地理坐标
            $("#lng").val(e.point.lng);
            $("#lat").val(e.point.lat);
            var pt = e.point;
        })
        var label = new BMap.Label("拖动修改坐标", {offset: new BMap.Size(20, -10)});
        marker.setLabel(label);
        map.addOverlay(marker); //在地图中添加marker
        marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    };
        
//备用
	$("#btnSearch").click(function () {
        var txt = $(".txtProvince").val() + $(".txtCity").val() + $(".txtDetail").val();
        var lng, lat;
        if (txt != "") {
            var localSearch = new BMap.LocalSearch(map);
            map.clearOverlays();//清空原来的标注
            localSearch.setSearchCompleteCallback(function (searchResult) {
                var poi = searchResult.getPoi(0);
                lng = poi.point.lng;
                lat = poi.point.lat;
                map.centerAndZoom(poi.point, 15);
                point = new BMap.Point(poi.point.lng, poi.point.lat);
                creatMarker();
            });
            localSearch.search(txt);
        }
    });
    /**  
	 * 用经纬度设置地图中心点  
	 */  
	function theLocation() {  
	    if (document.getElementById("addhouseLongitude").value != ""  
	            && document.getElementById("addhouseLatitude").value != "") {  
	        map.clearOverlays();  
	        var new_point = new BMap.Point(  
	                document.getElementById("addhouseLongitude").value, document  
	                        .getElementById("addhouseLatitude").value);  
	        // 创建标注  
	        var marker = new BMap.Marker(new_point);  
	        // 将标注添加到地图中  
	        map.addOverlay(marker);  
	        map.panTo(new_point);  
	    }  
	}
	
	
	
	$('button.saveMapSelection').on("click", function() {
		var lng = document.getElementById("lng").value;
		var lat = document.getElementById("lat").value;
		bdmapApp.saveCoordinate(lng, lat);
	});
	$('button.cancelMapSelection').on("click", function() {

		bdmapApp.close();
	});
	
});
</script>
</html>
