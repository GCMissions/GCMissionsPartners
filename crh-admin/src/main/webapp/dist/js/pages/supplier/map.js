$(function(){
		
	var map = new BMap.Map("map");
    map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
    map.addControl(new BMap.OverviewMapControl({ isOpen: true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT }));   //右下角，打开

    var localSearch = new BMap.LocalSearch(map);
    localSearch.enableAutoViewport(); //允许自动调节窗体大小
    
    var lng = $("#lng").val();
	var lat = $("#lat").val();
	
	
	var initPoint = new BMap.Point(lng,lat);
	if(lng!=""&&lat!="") {
		var marker = new BMap.Marker(new BMap.Point(lng,lat));
	    map.centerAndZoom(initPoint,13);
		map.addOverlay(marker);
	}else {
	    map.centerAndZoom("杭州", 12);
	}

    
    
     $('#serviceAddress').on('blur',function(){
    	 mapInit();
     }); 
     $("#provinceId").on('change',function(){
    	 mapInit(); 
     });
     $("#cityId").on('change',function(){
    	 mapInit(); 
     });
     $("#areaId").on('change',function(){
    	 mapInit(); 
     });
     var mapInit = function() {
    	// if (event.keyCode == "13") {
			map.clearOverlays();//清空原来的标注
			var province = $("#provinceId").find("option:selected").text();
			var city = $("#cityId").find("option:selected").text();
			var area = $("#areaId").find("option:selected").text();
			if(province=="请选择省份") province = "";
			if(city=="请选择城市") city = "";
			if(area=="请选择城区") area = "";
			var serviceAddress = document.getElementById("serviceAddress").value;
			var keyword = province + city + area + serviceAddress;
			if(serviceAddress!=""&&keyword!="") {
				localSearch.setSearchCompleteCallback(function (searchResult) {
					var poi = searchResult.getPoi(0);
					if(poi) {
						document.getElementById("result_").value = poi.point.lng + "," + poi.point.lat; //获取经度和纬度，将结果显示在文本框中
						map.centerAndZoom(poi.point, 13);
						var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
						document.getElementById("lng").value=marker.point.lng;
						document.getElementById("lat").value=marker.point.lat;
						map.addOverlay(marker);
						marker.enableDragging();
						marker.addEventListener("dragging",function(e){
							document.getElementById("lng").value=e.point.lng;
							document.getElementById("lat").value=e.point.lat;
						});
					}				
				});
			}			
			localSearch.search(keyword);
		//}
     }
})
