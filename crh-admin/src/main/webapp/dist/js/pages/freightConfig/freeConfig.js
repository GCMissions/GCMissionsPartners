$(function(){
	var freeConfig = {	
		freePriceHtml :"<div class='gradientTitle'><span>折扣梯度:</span><a class='gradbtn' href='javascript:;' id='addGradient'>增加梯度</a></div>"
					  +"<div class='gradientList'>" 
					  		+"<form id='gradientListForm' method='post'><table class='input' id='gradientList'>" 
					  			+"<tbody>" 
					  				+"<tr class='grad'>"
					  					+"<th class='quantity'>起始数量:</th>"
					  					+"<td class='quantity'><input type='text' class='form-control startQuantity numbercode' name='startQuantity' data-name='startQuantity'></td>"
					  					+"<th class='quantity'>终止数量:</th>"
					  					+"<td class='quantity'><input type='text' class='form-control endQuantity numbercode' name='endQuantity' data-name='endQuantity'></td>"
					  					+"<th class='discount'>折扣:</th>" 
					  					+"<td class='discount'><input type='text' class='form-control discount decimalscode' name='discount' data-name='discount'></td>"
					  					+"<td width='400'><a href='javascript:;' class='gradbtn delete'>删除</a><input type='hidden' data-name='gradientCheck' class='gradientCheck' /></td>"
					  				+"</tr>"
					  			+"</tbody>" 
					  		+"</table></form>"
					  +"<div>",
		initEvents:function(){	
			
			var table = $.GLOBAL.utils.loadBootTable({
				table : $('#dataList'),
        	    refreshBtn : $('#refreshRecord'),
				idField : "productId",
			    url: 'freightConfig/freeList', 
			    sidePagination:'server',
			    pagination : true,
			    queryParamsType: "limit",
			    queryAddParams: function() {
			    	var result= {
			    			productName  : $("#productName").val(),
			    			productCode: $("#productCode").val(), 
			    			cateId     : $("#cateId").val(),
			    			brandId    : $("#brandId").val()

						}
					return result;
			    },
			     
			    columns: [
					{
						width:50,
						align: 'center',
						formatter:function(value,row,index){  
							return index+1;
						}
					} ,
			        {
			            field: 'productCode',			            
			        } ,
			        {
			            field: 'productName',
			        } ,
			        {
			            field: 'brandName',
			        } ,
			        {
			            field: 'cateName',
			        } ,
			        {
			        	field: 'shipfeeConfig',
			            formatter:function(value,row,index){  	
			            	if(row.shipfeeConfig == null) {
				            	return ' <input class="hidden priceExpression" value="">';
			            	}else {
				            	return ' <input class="hidden priceExpression" value="'+row.shipfeeConfig+'">';
			            	}
			            } 
			        },
			        {
			        	field: 'productId',
			            align: 'center',
			            formatter:function(value,row,index){  			               
			            	return ' <input class="hidden" value="'+row.productId+'"><a title="修改"><i class="fa fa-edit"  style="font-size:20px"></i></a>';
			            } 
			        }
			        
			        
			     ]
			});
	


			$('#dataList').on("click",'.fa-edit',_(this.addfreePrice).bind(this));
			$(document).on("click",'#addGradient',_(this.addGradient).bind(this));
			$(document).on("click",'.delete',_(this.deleteGradient).bind(this));
			$(document).on("change",'.endQuantity',_(this.endQuantity).bind(this));
			
			
		
		},
		
		checkGradTr : function($giftTable){
			//显示每一个梯度第一个<删除梯度>按钮		
			if($("#gradientList .grad").length>1){
				$("#gradientList .grad").find(".delete").removeClass("hidden");
				$("#gradientList .grad:not(:last)").find(".delete").addClass("hidden");
			}else{
				$("#gradientList .grad").find(".delete").addClass("hidden");
			}
		},
		
		deleteGradient : function(e){
			$(e.target).closest(".grad").remove();
			this.checkGradTr();
		},
		addGradient :function(e) {
			var formFlag = this.validator.form();
			if(!formFlag) {
				return false;
			}
				/*if(!this.validator.form())
					return false;*/
				var gradTemplate = $("#gradientTemplate tbody");
				$("#gradientList tbody").append(gradTemplate.html());
				this.checkGradTr();
				this.endQuantity(e);				
			//}			
		},
		
		// 终止数量和起始数量联动
		endQuantity : function(e){
			var target = $(e.target);
			if(!target.hasClass("gradbtn")) {
				var endQuantity = target.val();
				var next = $(e.target).closest(".grad").next(".grad");
			}else {
				var endQuantity = $("#gradientList .grad:last").prev(".grad").find(".endQuantity").val();
				var next = $("#gradientList .grad:last");
			}			
			if(isNaN(endQuantity)) {
				endQuantity = "";
			}	
			var $next = $(next);
			if($next.size() > 0) {
				$next.find(".startQuantity").val(isNaN(endQuantity)?"":(parseInt(endQuantity) + 1)).change();
			}			
		},
		
		validateForm :function() {
			this.validator = $('#gradientListForm').validate({
				rules : {
					startQuantity: {
						min: 1,
						startQuantityMax: true,
						digits: true
					},
					endQuantity: {
						endQuantityMax: true,
						endQuantityRequired: true,
						biggerThanStart: true
					},
					discount: {
						decimal: true,
						min: 0.01,
						max: 0.99,
						checkDiscountLessThanPrev:true,
						checkDiscountMoreThanNext:true
					},
					gradientCheck: {
						gradientCheck: true
					},
				},
				errorPlacement: function(error, element) {
					if($(element).is(".startQuantity") || $(element).is(".endQuantity") || $(element).is(".discount")) {
						if($(element).closest("tr").find("td:last label.fieldError").size() > 0) {
							$(element).closest("tr").find("td:last label.fieldError").html(error.html()).show();
						} else {
							$('<label class="fieldError">' + error.html() + '</label>').appendTo($(element).closest("tr").find("td:last"));
						}
						if($(element).closest("tr").find("td:last label.fieldError").length>0){
							$(element).closest("tr").find("td:last label.fieldError").remove();
						}
						$(element).closest("tr").find("td:last").append(error);
					} else {				
						error.appendTo(element.parent());   
					}
				},
				
			});
			
			$.validator.addMethod("gradientCheck", function(value, element){
				var $tr = $(element).closest("tr");
				if($tr.find("input.startQuantity").val() == "") {
					return false;
				}
				if($tr.next().size() != 0 && $tr.find("input.endQuantity").val() == "") {
					return false;
				}
							
				if($tr.find("input.discount").val() == "") {
					return false;
				}				

				return true;
			}, "梯度信息不能为空");
			
			$.validator.addMethod("startQuantityMax", function(value, element) {
				if(value>1000000) return false;
				return true;
			},"起始数量不允许大于1000000");
			
			$.validator.addMethod("endQuantityMax", function(value, element) {
				if(value>1000000) return false;
				return true;
			},"终止数量不允许大于1000000");
			
			$.validator.addMethod("endQuantityRequired", function(value, element){
				if($(element).closest("tr").next().size() == 0) {
					return true;
				}
				return value != "";
			}, "终止数量必填");
			
			$.validator.addMethod("biggerThanStart", function(value, element){
				return value == "" || parseInt($(element).closest("tr").find(".startQuantity").val()) <= parseInt(value);
			}, "终止数量必须大于等于起始数量");
			
			$.validator.addMethod("decimal", function(value, element) {
				var decimal = /^-?\d+(\.\d{1,2})?$/;
				return this.optional(element) || (decimal.test(value));
			}, $.validator.format("小数位数不能超过两位"));
			
			$.validator.addMethod("checkDiscountLessThanPrev",function(value,element){
				var currValue = value;
			    var prevValue = $(element).closest("tr").prev().find("input.discount").val();

				return !prevValue || !value || parseFloat(currValue) < parseFloat(prevValue);
			},"此折扣必须小于前一个梯度的折扣");
			
			$.validator.addMethod("checkDiscountMoreThanNext",function(value,element){
				var currValue = value
			    var nextValue = $(element).closest("tr").next().find("input.discount").val();
			
				return !nextValue || !value || parseFloat(currValue) > parseFloat(nextValue);
			},"此折扣必须大于后一个梯度的折扣");
		},
		
		addfreePrice : function(e) {
	        var that = this;
	        
	        var $freeNumTrEdit = e.target;
			var $freeNumTr = $($freeNumTrEdit).parents('tr');
            var productId = $freeNumTr.find(".freeSet").find("input").val();
            var priceExpression = $freeNumTr.find(".priceExpression").val();
	       
	        that.dialog =  BootstrapDialog.show({
	            title: '添加运费',
	            //type : BootstrapDialog.TYPE_DEFAULT,
	            message: $(this.freePriceHtml),
	            draggable: true,
	            buttons: [{
	                label: '保存',
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	                	that.priceExpression();
	                	var flag = false;
	                	flag = that.validator.form();
	                	if(flag) {
	                		that.doSave(productId);
	                	}
	                	
	                    //dialog.close();
	                }
	            }, {
	                label: '取消',
	                action: function(dialog) {
	                    dialog.close();
	                }
	            }],
	            onshown : function() {
	            	that.insertToGradient(that.parseExp(priceExpression,",",";"));
	            	that.validateForm();
	            }
	       });
	       that.dialog.getModalDialog().css('width', '950px');
	    }, 
		
	    priceExpression: function() {
	    	var gradientExpression = this.packageingExp("",",",";",$("#gradientList"),$("#gradientList").find("tr"),"input.startQuantity","input.endQuantity","","input.discount");
			$("#priceExpression").val(gradientExpression);
	    },
	    
	    packageingExp : function(type,parseKeyValue,parseObjects,$table,tr,start,end,price,discount){
			var exp = null;
			$table.find(tr).each(function(i,item){
				var obj = ($(item).find(start).val()?$(item).find(start).val():"")+parseKeyValue;
				obj += ($(item).find(end).val()?$(item).find(end).val():"")+parseKeyValue;
				if(type=="subAmount"){
					obj+=$(item).find(price).val();
				}else{
					obj+=$(item).find(discount).val();
				}
				if(!exp){
					exp = /*"{"+*/obj;
				}else{
					exp += parseObjects+obj;
				}
			});
			
			return exp/*+"}"*/;
		},
		
		

		doSave : function(productId){
			var freeConfigForm = {
					productId   : productId,
					shipfeeConfig : $("#priceExpression").val()
			};
			$.ajax({
				  type: 'POST',
				  url: 'saveFree',
				  dataType: 'json',
				  contentType: 'application/json',
				  data: JSON.stringify(freeConfigForm),
				  success: function(message){
					  if(message.code=="ACK"){
			          window.location.href= urlPrefix + "freightConfig/freeIndex";
					  }
				  },
				  error:function(message){
						//alert('shibai');
				  }
	         });
	            
		},
		
		//解析priceExpression
		parseExp : function(exp,parseKeyValue,parseObjects){
			/*exp = exp.split("{")[1];
			exp = exp.split("}")[0];*/
			var objs={};
			$.each(exp.split(";"),function(i,item){
				if(!item)
					return true;
				var obj={};
				$.each(item.split(","),function(j,item){
					obj[j]=item;
				});
				objs[i]=obj;
			})
			return objs
		},
		//解析后的priceExpression插入梯度表格
		insertToGradient : function(objs){
			var gradTemplate = $("#gradientTemplate tbody");
			$.each(objs,function(i,item){
				if(i>0) {
					$("#gradientList tbody").append(gradTemplate.html());
				}
				$("#gradientList tbody").find("tr").not(".grad").addClass("grad");
				var tr = $("#gradientList .grad")[i];
				$(tr).find("input.startQuantity").val(item[0]);
				$(tr).find("input.endQuantity").val(item[1]);
				$(tr).find("input.discount").val(item[2]);
			})
			this.checkGradTr();
		},
	 	
		init:function(){
			var _self = this;
			_self.initEvents();			
		}
	}.init();
	

});