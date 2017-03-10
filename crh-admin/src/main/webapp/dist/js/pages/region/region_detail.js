$(function(){
	var regionDetail = {
			init : function() {
				var _self = this;
				_self.initEvents();
			},
			initEvents : function(){
				$("#add").click(function(){
					var select = document.getElementById("leftCountry");
				    for(i=0;i<select.length;i++){
				        if(select.options[i].selected){
				        	var id = select.options[i].value;
				        	var text = select.options[i].text;
				            $("#leftCountry option[value=" + id + "]").remove();
				            $("#rightCountry").append("<option value=" + id + ">" + text + "</option>");
				        }
				    }
				});
				
				$("#addAll").click(function(){
					var select = document.getElementById("leftCountry");
				    for(i=0;i<select.length;i++){
				    	var id = select.options[i].value;
				        var text = select.options[i].text;
				        $("#rightCountry").append("<option value=" + id + ">" + text + "</option>");
				    }
				    $("#leftCountry").empty(); 
				});
				
				$("#delete").click(function(){
					var select = document.getElementById("rightCountry");
				    for(i=0;i<select.length;i++){
				        if(select.options[i].selected){
				        	var id = select.options[i].value;
				        	var text = select.options[i].text;
				            $("#rightCountry option[value=" + id + "]").remove();
				            $("#leftCountry").append("<option value=" + id + ">" + text + "</option>");
				        }
				    }
				});

				$("#deleteAll").click(function(){
					var select = document.getElementById("rightCountry");
				    for(i=0;i<select.length;i++){
				    	var id = select.options[i].value;
				        var text = select.options[i].text;
				        $("#leftCountry").append("<option value=" + id + ">" + text + "</option>");
				    }
				    $("#rightCountry").empty(); 
				});
				
				$("#save").click(function(){
					var regionName = $("#regionName").val();
					var color = $("#regionColor").val();
					var select = document.getElementById("rightCountry");
				    var countryIdList = [];
				    for(i=0;i<select.length;i++){
				        countryIdList.push(select.options[i].value);
				    }
				    var type = "0";
				    if (regionName == "") {
						$("body").loadingInfo("warn", "Please enter the Region Name!");
						return;
					}
				    if (color == "") {
						$("body").loadingInfo("warn", "Please choose the color!");
						return;
					}
				    if (countryIdList.length == 0) {
						$("body").loadingInfo("warn", "Please choose at least one country!");
						return;
					}
				    var saveParam = {
						    "regionName"	: regionName,	
						    "color" : color,
							"countryIdList" : countryIdList,
							"type" : type
					}
					saveRegion(saveParam);
				});
				
				$("#edit").click(function(){
					var regionName = $("#regionName").val();
					var color = $("#regionColor").val();
					var select = document.getElementById("rightCountry");
				    var countryIdList = [];
				    for(i=0;i<select.length;i++){
				        countryIdList.push(select.options[i].value);
				    }
				    var type = "1";
				    if (regionName == "") {
						$("body").loadingInfo("warn", "Please enter the Region Name!");
						return;
					}
				    if (color == "") {
						$("body").loadingInfo("warn", "Please choose the color!");
						return;
					}
				    if (countryIdList.length == 0) {
						$("body").loadingInfo("warn", "Please choose at least one country!");
						return;
					}
				    var saveParam = {
				    		"id" : $("#regionId").val(),
						    "regionName"	: regionName,	
						    "color" : color,
							"countryIdList" : countryIdList,
							"type" : type
					}
					saveRegion(saveParam);
				});
				
				var saveRegion = function(saveParam){
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "region/save",
						dataType     : 'json',
						contentType: "application/json;charset=utf-8",
						data 	     : JSON.stringify(saveParam),
						success : function(msg){
							if (msg.code == "ACK") {
								window.location.href=urlPrefix+"region/";
							} else {
								$("body").loadingInfo("warn", msg.message);
							}
						}
			    	});
				};
			}
	}.init();
})