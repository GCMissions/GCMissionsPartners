$(function(){
	var missionList = {
			
			init : function() {
				var _self = this;
				_self.initEvents();
			},
			initEvents : function(){
				var bootTable = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					pagination : true,
					pageSize : 4,
					url: 'mission/list',
					sidePagination:'server',
					queryAddParams: function() {
						
					},
					columns: [{
						field: 'id',
                		width: '5%'
					} ,{
						field: 'title',
						width: '25%',
					} , {
						field: 'content',
						width: '60%'
					} , {
						field: 'id',
						width: '10%',
						formatter: function(value,row,id){
							var result = "";
							result += "<a href='" + urlPrefix + "mission/view/" + value + "' class='detailItem' data-id='"+value+"'>Edit</a>";
							return result;
						} 
					}]
				});
				
				var setVal = function(){
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "salutatory/get",
						contentType: "application/json;charset=utf-8",
						dataType     : 'json',
						success : function(msg){
							debugger;
							if (msg != null &&  msg != "") {
								$("#sal").attr("value",msg.id);
								$("#title").attr("value",msg.title);
								setTimeout(function(){
									 CKEDITOR.instances.TextArea1.settData(msg.content);
								},0);
							} 
						}
			    	});
	
			   };

				$("#savebtn").click(function(){
					//获取当前编辑的内容
					var content = CKEDITOR.instances.TextArea1.getData();
					var title  = $("#title").val();
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "salutatory/save",
						contentType: "application/json;charset=utf-8",
						dataType     : 'json',
						data 	     : {
							"id":$("#sal").val(),
							"title" : title,
							"content":content
						},
						success : function(msg){
							if (msg.code == "ACK") {
								alert("修改成功!");
							} 
						}
			    	});
				});
				

				var changeModel = function(){
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "model/change",
						dataType     : 'json',
						data 	     : {
							"id" : $("#model").val()
						},
						success : function(msg){
							if (msg.code == "ACK") {
								alert("修改成功!");
							} 
						}
			    	});
				};
				
				var changeTab = function(){
					var code = $("#model").val();
					if(code==1){
						$("#missionTable").show();
						$("#textAreal").hide();
					}else{
						$("#missionTable").hide();
						$("#textAreal").show();
					}
				};
				
				var code = $("#model").val();
				if(code==1){
					$("#missionTable").show();
					$("#textAreal").hide();
				}else{
					$("#missionTable").hide();
					$("#textAreal").show();
				}
				
				setVal();
				$("#change").click(function(){
					changeModel();
					changeTab();
				});
				
			}
	}.init();
	
})