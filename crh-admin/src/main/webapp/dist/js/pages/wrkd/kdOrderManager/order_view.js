$(function() {
	var details = {
			initEvents:function(){
			var table = $.GLOBAL.utils.loadBootTable({
				table : $('#detailList'),
			    url: 'kdOrder/findDetails',
			    queryParamsType: "limit",
			    queryAddParams: function() {
			    	return {"orderId":orderId};
			    },
			    columns: [
					{
						width:50,
						title: '序号',
					    align: 'center',
					    width:'3%',
					    formatter:function(value,row,index){  
					    	return index+1; 
					    }
					} , {
			        	title: '商品编号',
			            field: 'proudctCode',
			            width:'12%'
			        } , {
			        	title: '商品名称',
			        	width:'12%',
			            field: 'proudctName'
			        } , {
			        	title: '单价（元）',
			        	width:'16%',
			        	field: 'singlePrice'
			        }  , {
			        	title: '数量',
			        	width:'12%',
			        	field: 'productCount'
			        } , {
			        	title: '规格',
			        	field: 'specInfo',
			        	width:'20%',
			        	formatter : function(value,row,index){
			        		var result = "";
			        		var specs = JSON.parse(HTMLDecode(value));
			        		for(var key in specs){  
			        			result += "<p>" + key + ":" + specs[key] + "</p>"
			        	    }
			        		return result;
			        	}
			        	
			        } ,  {
			        	title: '操作',
			        	field: 'productId',
			        	width:'25%',
			        	formatter : function(value,row,index){
			        		var orderType = $("#orderTypeCode").val();
			        		var operType = $("#operType").val();
			        		var orderId = $("#orderId").val();
			        		if(row.isCanReturn == "0") {
			        			return "<p style='margin-left:5%;'>已退款" + row.returnPrice + "元</p><br><p style='margin-left:5%;'>已释放库存" + row.returnCount + "件</p>";
			        		} else if(row.isCanReturn == "1") {
			        			if(operType == "1") {
				        			if (row.orderStatus != "0" && row.orderStatus != "5") {
					        			if(row.isCanReturn == "1") {
					        				return "<input class='form-control returnAmount' placeholder='还能退款" + row.restPrice + "元' style='margin-left:5%;margin-bottom:5%;' />"
					        					   	+ "<a class='returnItem' href='javascript:void(0);' data-id='" + value + "' orderId='" + orderId + "' orderStatus='" + row.orderStatus + "' orderType='" + orderType + "' price='" + row.restPrice + "' totalCount='" + row.productCount + "' specInfo='" + row.specInfo + "'> 退款</a><br>"
					        						+ "<input class='form-control returnCount' placeholder='释放库存量' style='margin-left:5%;' onkeyup=this.value=this.value.replace(/[^0-9]/g,'')>";
					        			}
				        			}
			        			}
			        		}
			        	}
			        }
			     ]
			});
			
			//输入退款金额最多只能两位小数
			$(".details").on("keyup",".returnAmount",function(){
				var V = $(this).val();
				// 自然数或两位小数
				var patternDouble =  /^([1-9]\d{0,}|0)([.]\d{0,2})?$/;	
				if (!patternDouble.test(V)){
					$(this).val("");
				}
			});
			
			$(".details").on('click', '.returnItem', function(){
				var returnOrderAmount = $.trim( $(this).prev().val());
				var orderId = $(this).attr("orderid");
				var productId = $(this).data("id");
				var status = $(this).attr("orderstatus");
				var orderType = $(this).attr("ordertype");
				var price = $(this).attr("price");
				var returnCount = $.trim($(this).next().next().val());
				var totalCount = $(this).attr("totalCount");
				var specInfo = $(this).attr("specInfo");
				if (returnOrderAmount == "") {
					$(window).loadingInfo("warn", "退款不能为空！");
					return;
				}
				if (parseFloat(returnOrderAmount) <= 0) {
					$(window).loadingInfo("warn", "请填写正确的退款金额！");
					return;
				}
				if (parseFloat(returnOrderAmount) > parseFloat(price)) {
					$(window).loadingInfo("warn", "退款金额不能超过总金额！");
					return;
				}
				if (returnCount == "" || returnCount == null) {
					returnCount = 0;
				}
				if (parseFloat(returnCount) < 0) {
					$(window).loadingInfo("warn", "请填写正确的库存释放量！");
					return;
				}
				if (parseFloat(returnCount) > parseFloat(totalCount)) {
					$(window).loadingInfo("warn", "释放库存量不能大于该商品总量！");
					return;
				}
				showReturnRemarkTmp(orderId,returnOrderAmount,productId,status,orderType,returnCount,specInfo);
			});
			
			var showReturnRemarkTmp = function(orderId,returnOrderAmount,productId,status,orderType,returnCount,specInfo){
				var _this = this;
				_this.returnRemarkDialog = BootstrapDialog.show({
			        title: '添加备注',		    
			        size :  BootstrapDialog.SIZE_WIDE,
			        message: $(template('remarkTpl',{})),
			        draggable: false,
			        onshown: function(dialogRef){
			        	$('#operRemark').focus();
			        	$("#close").click(function(){
			        		_this.returnRemarkDialog.close();
			    		});
			        	$("#doCancel").click(function(){
			        		var remark = $.trim($("#operRemark").val());
			        		if (remark == "") {
			        			$(window).loadingInfo("warn", "请填写操作理由！");
			        			return;
			        		}
			        		var orderId = $("#orderId").val();
			        		$('body').loadingInfo("success", "正在退款,请稍候...");
			        		$.ajax({
								url: urlPrefix+"kdOrder/return",
								type:"post",
								dataType : "json",
								data :  JSON.stringify({ "orderId":orderId,"returnOrderAmount":returnOrderAmount,"productId":productId,"remark":remark,"status":status,"operType":"2","orderType":orderType,"returnCount":returnCount,"specInfo":specInfo}),
								contentType : "application/json; charset=utf-8",
								success:function(msg){
									if(msg.code=="ACK") {
										$('body').loadingInfo({
						                    type : "ok",
						                    text : msg.message,
						                    callBack : function() {
						                    	window.location.href=window.location.href;
						                    }
						                });
									}
								}
							});
			        		_this.returnRemarkDialog.close();
			        	});
		            }
			    });
			};
			
			//过滤html转义字符
			function HTMLDecode(text){
			    var temp = document.createElement("div");
			    temp.innerHTML = text;
			    var output = temp.innerText || temp.textContent;
			    temp = null;
			    return output;
			}
		},
		init:function(){
			var _self = this;
			_self.initEvents();
		}
	}.init();
});