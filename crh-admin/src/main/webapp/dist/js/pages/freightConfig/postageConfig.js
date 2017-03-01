$(function(){
	var postageConfig = {	
	    
			
		initEvents:function(){	
		
			$("#addFreight").on("click",function(){
				index = $("#freightConfig tr").length+1;
				$("#freightConfig").append(
						'<tr>' +
							'<td>'+
								'<a class="editFreight"><i class="fa fa-edit" style="font-size:20px;"></i></a>'+
							    '<input type="text" class="form-control hidden configIndex"  value="'+index+'">'+					                              	        
                  	    	    '<input type="text" class="form-control hidden configId"  value="">'+				                              	    
	                  	    	'<input type="text" class="form-control hidden regionIds" id="regionId_'+index+'" value="">'+ 
							    '<span class="regionNames" id="regionName_'+index+'">未添加地区</span>'+								
                  	    	 '</td>' +
							'<td><input type="text" class="form-control startNum" value=""><span class="fieldError startNumError hidden">只允许输入零或正整数</span></td>'+
                      	    '<td><input type="text" class="form-control startCostYuan" value=""><span class="fieldError startCostYuanErrorI hidden">只允许输入数字</span><span class="fieldError startCostYuanErrorII hidden">只允许输入两位小数</span></td>'+
                      	    '<td><input type="text" class="form-control plusNum" value=""><span class="fieldError plusNumError hidden">只允许输入零或正整数</span></td>'+
                      	    '<td><input type="text" class="form-control plusCostYuan" value=""><span class="fieldError plusCostYuanErrorI hidden">只允许输入数字</span><span class="fieldError plusCostYuanErrorII hidden">只允许输入两位小数</span></td>'+
							'<td><a class="removeFreight"><i class="fa fa-trash"  style="font-size:20px"></i></a></td>' +
						'</tr>'
		
				);	
			});
			
			$("#freightConfig").on("click",".removeFreight",function(){
				$(this).closest("tr").remove();			
			});
			
			//省份和城市
			$.ajax({
				type: "GET",
				url: urlPrefix + "region/findProvAndCity",
				contentType: "application/json;charset=utf-8",
				dataType: "json",
				success: function(message){
					if(message.code=="ACK"){
						
						var provCityHtml = "";
						_(message.data).each(function(prov,index){
							var provId = prov.provId;
							var provName = prov.provName;     
							var cityDtos = prov.cityDtos;   
							provCityHtml = provCityHtml + '<div class="ecity">' +
        		      							'<span class="gareas">' +
        		      								'<input type="checkbox" value="'+provId+'" id="J_Province_'+provId+'" class="J_Province">' +
        		      								'<label for="J_Province_'+provId+'">'+provName+'</label>' +
        		      								'<span class="check_num"></span>' +
        		      								'<img class="trigger" src="../../dist/img/open.gif">' +
        		      							 '</span><div class="citys">' ;
							
							
							_(cityDtos).each(function(city,index){
								var cityId = city.id;
								var cityName = city.name;
								provCityHtml = provCityHtml +'<span class="areas">' +
        		      												'<input type="checkbox" value="'+cityId+'" id="J_City_'+cityId+'" class="J_City">' +
        		      												'<label for="J_City_'+cityId+'">'+cityName+'</label>' +
        		      										'</span>';
        		      											
							});
							
	                        provCityHtml =  provCityHtml +'<p style="text-align:right;">' +
															'<input type="button" value="关闭" class="close_button">' +
														  '</p></div></div>';

						});
                        console.log(message);
                        $("#addEditTpl").append(provCityHtml).addClass("hidden");
                        addEditTpl = $("#addEditTpl");
					}
				},
				error: function(message){
				}		
			});
			
			
			
			$(document).on("click",".trigger",function(){
				$("#addEditTpl").find(".showCityPop").removeClass("showCityPop");
				$(this).closest(".ecity").addClass("showCityPop");	
				
			});
			
			$(document).on("click",".close_button",function(){
				$(this).closest(".ecity").removeClass("showCityPop");			
			});
			
			$(document).on("change",".J_Province",function(){
				var J_City = $(this).closest(".ecity").find(".J_City");
				if(this.checked) {
					_(J_City).each(function(city,index){
						if(!city.disabled) {
							$(city).prop("checked",true);
						}
					});
				}else {
					_(J_City).each(function(city,index){
						if(!city.disabled) {
							$(city).prop("checked", false);
						}
					});
				}
			});
			
			$(document).on("click",".J_City",function(){
				var J_Citys = $(this).closest(".ecity").find(".J_City");
				var J_Province = $(this).closest(".ecity").find(".J_Province");
				var isChecked = true;
				if(this.checked) {
					_(J_Citys).each(function(city,index){
						if(!city.checked) {
							isChecked = false;
						}
					});
				}else {
					isChecked = false;
				}
				$(J_Province).prop("checked", isChecked);
			});
			
			$(document).on("click",".editFreight",function(){
				var that = this;
				//表格顺序
				var configIndex = $(that).parent().find(".configIndex").val();
				//区域Id字符串
            	var regionIds = $(that).parent().find(".regionIds").val();
            	//区域名字字符串
            	var regionNames = $.trim($(that).parent().find(".regionNames").text());
            	//
            	var configId = $(that).parent().find(".configId").val();
            	
            	
            	//所有已选择区域字符串拼接
            	
            	var regionIdsAllDom = $(".regionIds");
            	var regionIdsAll = "";
            	_(regionIdsAllDom).each(function(regionIdsDom,index){
            		regionIdsAll  = regionIdsAll + $(regionIdsDom).val()+",";
            	});
            	
            	//所有已选择区域id数组
            	var regionIdsAllArr=new Array();
            	regionIdsAllArr = regionIdsAll.split(",");
            	
            	//本条已选择区域id数组
            	var regionIdsArr=new Array();           	
            	regionIdsArr = regionIds.split(",");
            	
            	$("#addEditTpl").removeClass("hidden");
            	
            	//点击本条编辑，对面板重置，对本条已选地区打勾并置灰
            	var J_Prov_City=addEditTpl.find("input").not("input.close_button");
            	_(J_Prov_City).each(function(Prov_City,index){
            		$(Prov_City).prop("disabled",false);  //重置置白
            		
            		//选过的所有区域置灰
            		if(regionIdsAllArr.indexOf($(Prov_City).val())!="-1"){
            			$(Prov_City).prop("disabled",true); //置灰
            		}
            		
            		if(regionIdsArr.indexOf($(Prov_City).val())=="-1") { //不包含
    					$(Prov_City).prop("checked", false); //不打勾

            		}else {
            			$(Prov_City).prop("checked", true); //打勾
            		}
            			
				});
            	
				BootstrapDialog.show({
			        title: '选择区域',
			        message: addEditTpl,
			        draggable: true,
			        buttons: [{
			            label: '保存',
			            cssClass: 'btn-primary',
			            action: function(dialog) {
			            	
                            var regionId = "";
                            var regionName = "";

                            
                            //防止重复添加区域
			            	var chks=$("#addEditTpl input:checked");
			            	
			            	_(chks).each(function(chk,index){
			            		
			            		if(regionIdsArr.indexOf($(chk).val())=="-1") {
			            			
			            			regionId=regionId+$(chk).val()+",";
				            		regionName=regionName+$(chk).next().text()+",";				            						            		
			            		}			            		
			            		
			            	});
			            	if(regionIds=="") {
				            	regionIds = regionIds + regionId;
			            	}else {
				            	regionIds = regionIds +","+ regionId;
			            	}
			            	if(regionNames=="未添加地区") {
			            		regionNames = regionName;
			            	}else {
			            		regionNames = regionNames +","+ regionName;
			            	}
			            	
			            	dialog.close();
						    $("#regionId_"+configIndex).attr("value",regionIds);//该组织被勾选的地区的id存到隐藏域
						    $("#regionName_"+configIndex).text(regionNames);//该组织被勾选的地区的id存到隐藏域


			            }
			        }, {
			            label: '取消',
			            action: function(dialog) {
			                dialog.close();
			            }
			        }]
			        		      
			    });
			});
			//验证
			this.validator = $("#postageConfigForm").validate({
				rules : {
					defaultStartNum : {
	        			digits:true
					},
					defaultStartCost : {
						number:true,
						decimal:true
					},
					defaultPlusNum : {
	        			digits:true
					},
					defaultPlusCost : {
						number:true,
						decimal:true
					}
				},
				message : {
					defaultStartNum : {
	        			digits:true
					},
					defaultStartCost : {
						number:true,
						decimal:true
					},
					defaultPlusNum : {
	        			digits:true
					},
					defaultPlusCost : {
						number:true,
						decimal:true
					}
				}
			});		
			// 验证值小数位数不能超过两位
			$.validator.addMethod("decimal", function(value, element) {
			var decimal = /^-?\d+(\.\d{1,2})?$/;
			return this.optional(element) || (decimal.test(value));
			}, $.validator.format("只允许输入两位小数"));

	        $("#postageConfigForm").on('blur','.startNum',_(this.validateStartNum).bind(this));
	        $("#postageConfigForm").on('blur','.startCostYuan',_(this.validateStartCostYuan).bind(this));
	        $("#postageConfigForm").on('blur','.plusNum',_(this.validatePlusNum).bind(this));
	        $("#postageConfigForm").on('blur','.plusCostYuan',_(this.validatePlusCostYuan).bind(this));
			$('#save').on("click",_(this.submitForm).bind(this));
		},
		
		validateStartNum : function(){
	    	var flag = true;
	    	var startNums = $(".startNum");
            var digits= /^[0-9]*$/;
	    	_(startNums).each(function(startNum,index){
	    		if($(startNum).val()!=""){
	    			if(!digits.test($(startNum).val())) {
		    			$(startNum).next(".startNumError").removeClass("hidden");
		    			flag = false;
		    		}else {
		    			$(startNum).next(".startNumError").addClass("hidden");
		    			flag = true;
		    		}
	    		}	    		
	    	});
	    	return flag;
	    },
	    validateStartCostYuan : function() {
	    	var flagI = true;
	    	var flagII = true;	    	
	    	var startCostYuans = $(".startCostYuan");
            var digits= /^[+-]?\d+\.?\d*$/;
            var decimal = /^-?\d+(\.\d{1,2})?$/;
            
	    	_(startCostYuans).each(function(startCostYuan,index){
	    		if($(startCostYuan).val()!=""){
	    			if(!digits.test($(startCostYuan).val())) {
		    			$(startCostYuan).parent().find(".startCostYuanErrorII").addClass("hidden");
		    			$(startCostYuan).next(".startCostYuanErrorI").removeClass("hidden");
		    			flagI = false;
		    		}else {
		    			$(startCostYuan).next(".startCostYuanErrorI").addClass("hidden");
		    			flagI = true;
		    		}
		    		if(flagI) {
		    			if(!decimal.test($(startCostYuan).val())) {
			    			$(startCostYuan).next(".startCostYuanErrorI").addClass("hidden");
			    			$(startCostYuan).parent().find(".startCostYuanErrorII").removeClass("hidden");
			    			flagII = false;
			    		}else {
			    			$(startCostYuan).parent().find(".startCostYuanErrorII").addClass("hidden");
			    			flagII = true;
			    		}
		    		}    	
	    		}	    			
	    	});	    	
	    	return flagI&&flagII;
	    },
	    validatePlusNum : function(){
	    	var flag = true;
	    	var plusNums = $(".plusNum");
            var digits= /^[0-9]*$/;
	    	_(plusNums).each(function(plusNum,index){
	    		if($(plusNum).val()!=""){
	    			if(!digits.test($(plusNum).val())) {
		    			$(plusNum).next(".plusNumError").removeClass("hidden");
		    			flag = false;
		    		}else {
		    			$(plusNum).next(".plusNumError").addClass("hidden");
		    			flag = true;
		    		}
	    		}	    		
	    	});
	    	return flag;
	    },
	    validatePlusCostYuan : function() {
	    	var flagI = true;
	    	var flagII = true;	    	
	    	var plusCostYuans = $(".plusCostYuan");
            var digits= /^[+-]?\d+\.?\d*$/;
            var decimal = /^-?\d+(\.\d{1,2})?$/;
            
	    	_(plusCostYuans).each(function(plusCostYuan,index){
	    		if($(plusCostYuan).val()!=""){
	    			if(!digits.test($(plusCostYuan).val())) {
		    			$(plusCostYuan).parent().find(".plusCostYuanErrorII").addClass("hidden");
		    			$(plusCostYuan).next(".plusCostYuanErrorI").removeClass("hidden");
		    			flagI = false;
		    		}else {
		    			$(plusCostYuan).next(".plusCostYuanErrorI").addClass("hidden");
		    			flagI = true;
		    		}
		    		if(flagI) {
		    			if(!decimal.test($(plusCostYuan).val())) {
			    			$(plusCostYuan).next(".plusCostYuanErrorI").addClass("hidden");
			    			$(plusCostYuan).parent().find(".plusCostYuanErrorII").removeClass("hidden");
			    			flagII = false;
			    		}else {
			    			$(plusCostYuan).parent().find(".plusCostYuanErrorII").addClass("hidden");
			    			flagII = true;
			    		}
		    		}   
	    		}	    		 		
	    	});	    	
	    	return flagI&&flagII;
	    },
		submitForm : function(){
			var formFlag = this.validator.form();
	    	var startNumFlag = this.validateStartNum();
	    	var startCostYuanFlag = this.validateStartCostYuan();
	    	var plusNumFlag = this.validatePlusNum();
            var plusCostYuanFlag = this.validatePlusCostYuan();
			if(!formFlag||!startNumFlag||!startCostYuanFlag||!plusNumFlag||!plusCostYuanFlag) {
				return false;
			}else {
				this.doSave();
			}
		},
		
		doSave : function(){
			var lists = [];
			_($("#freightConfig tr")).each(function(tr,index){
				if($(tr).find(".regionIds").val()!="") {
					var list = {
							configId : $(tr).find(".configId").val(),
							regionIdString : $(tr).find(".regionIds").val(),
							regionNames : $.trim($(tr).find(".regionNames").text()),
							startNum : $(tr).find(".startNum").val(),
							startCostYuan : $(tr).find(".startCostYuan").val(),
							plusNum : $(tr).find(".plusNum").val(),
							plusCostYuan : $(tr).find(".plusCostYuan").val()
					}
					lists.push(list);
				}
				
			});
			
			
			
			var postageConfigAdd = {
					freeFreight: $(".freeFreight").val(),
					startNum : $(".defaultStartNum").val(),
					startCostYuan : $(".defaultStartCost").val(),
					plusNum : $(".defaultPlusNum").val(),
					plusCostYuan : $(".defaultPlusCost").val(),
					list : lists
			};
			$.ajax({
				type: "POST",
				url: "postageSave",
				contentType: "application/json;charset=utf-8",
				data: JSON.stringify(postageConfigAdd),
				dataType: "json",
				success: function(message){
					if(message.code=="ACK"){
						alert('修改成功');
					}
				},
				error:function(message){
					alert('修改失败');
				}
			});
		},
	 	
		init:function(){
			var _self = this;
			_self.initEvents();
		}
	}.init();
	

});