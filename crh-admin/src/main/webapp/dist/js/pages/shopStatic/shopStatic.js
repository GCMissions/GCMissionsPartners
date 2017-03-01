$(function(){
	var shopStatic = {
			$mainForm:$(".mainForm"),
			loadOpenCityUrl : "region/findOpenCity",
			init : function(){
				this.bindEvents();
			},
			bindEvents : function(){
				var that = this;
				$("#staticType").on('change',  function(e) {
					var type = $(e.target).val();
					if(type==1){
						that.loadCities();
						$(".area").show();	
					}else{
						$(".area").hide();
					}
				});
				$("#sure").on('click',function(){
					var staticType=$("#staticType").val(),
						cityList=that.checkNodes(),
						data={};
					if(staticType==1){
						data={  
								type:staticType,
								list:cityList,
						};
					}else{
						data={
								type:staticType,
						};
					}
					if(data){
						$.ajax({ 
			        		type         : 'post',
							url          : 'statical',
							dataType     : 'json',
							contentType  : 'application/json',
							data         : JSON.stringify(data)
						 })
						 .done(function(result) {
							if(result.code == "ACK") {
								that.$mainForm .loadingInfo({
				    				type : "success", 
				    				text: message("admin.message.success"),
				    				callBack : function() {
				    					window.location.href=urlPrefix + "shopStatic/";;
				    				}
				    			});
							}
						 });
					}
				});
			},
			loadCities : function() {
			     //load city data
			     var that = this;
			      setting={ 	 
				    	check:{
				    		enable:true,
				    		chkboxType:{"Y":"ps","N":"ps"},
				    	},	  
				    	data:{
				            key: {
				                id   : "regionId",
				                name : "regionName",
				                pId  : "parentId",
				       	        children:"childrenList",
				            },
				        	simpleData: {
				                enable: true,
				                idKey : "id",
				                pIdKey: "pId",
				                rootPId : 0
				                //checked: true
				            }
				        },
			     }, 
			     $.ajax({
			     	type         : "GET",
			         url          : urlPrefix + this.loadOpenCityUrl,
			         dataType     : 'json',
					 contentType  : 'application/json'
				     })
				     .done(function(result) {
				         if(result.code == "ACK") {
				             var data = result.data;
				             for(i=0;i<data.length;i++){
				            	 if(data[i].regionId==100000){
				            		 data[i].regionName="无网点配送城市";
				            	 }
				            	 
				            	 data[i].isParent=true;
				             }
				             $.fn.zTree.init($("#treeDemo"), setting, data);    
				         }
				     })
				     .always(function() {
				         
				     });
			 },
			checkNodes : function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
					nodes = zTree.getCheckedNodes(true),
					len=nodes.length,
					cities = [];					
				for(i=0;i<len;i++){
					if(nodes[i].level==1||nodes[i].regionId==100000){
						cities.push(nodes[i].regionId);
					}
				}
				return cities;
			}
	}
	shopStatic.init();
});