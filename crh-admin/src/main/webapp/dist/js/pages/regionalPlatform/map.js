$(function(){
	var map = new BMap.Map("map");
	var lng = document.getElementById("ng").value;
    var at = document.getElementById("at").value;
    
	map.centerAndZoom(new BMap.Point(lng, at), 14);
    map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
    map.addControl(new BMap.OverviewMapControl({ isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT }));   //右下角，打开
    
    
    
	var marker = new BMap.Marker(new BMap.Point(lng, at));  // 创建标注，为要查询的地方对应的经纬度
	document.getElementById("lng").value=marker.point.lng;
	document.getElementById("lat").value=marker.point.lat;
	map.addOverlay(marker);
	marker.enableDragging();
	marker.setAnimation(BMAP_ANIMATION_BOUNCE);
	marker.addEventListener("dragging",function(e){
		document.getElementById("lng").value=e.point.lng;
		document.getElementById("lat").value=e.point.lat;
	});

//    var localSearch = new BMap.LocalSearch(map);
//    localSearch.enableAutoViewport(); //允许自动调节窗体大小
    
    
//	var keyword = "野生动物园";
//
//
//	
//	localSearch.search(keyword); 
//	
//	localSearch.setSearchCompleteCallback(function (searchResult) {
//		var poi = searchResult.getPoi(0);
//		document.getElementById("result_").value = poi.point.lng + "," + poi.point.lat; //获取经度和纬度，将结果显示在文本框中
//		map.centerAndZoom(poi.point, 13);
		
})
