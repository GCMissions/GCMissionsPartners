$(function() {
	var validateList = {
		initEvents : function() {
			var date = new Date();
			var year = date.getFullYear();
			var month = date.getMonth()+1;
			var day = date.getDate();
			var globalOrderIds = [];
			
			$('#csDate,#ceDate').datetimepicker({
				minView: 'month',
				format: 'yyyy-MM-dd',
				language: 'ch',
				endDate: year+'-'+month+'-'+day,
				autoclose : true,
				todayBtn : true
			});
			//创建日期
			$('#csDate').on('changeDate',function(){
				$('#ceDate').datetimepicker('setStartDate', $('#csDate').val());
				if($('#csDate').val()=="" && $("#csDate").next().css('display') == 'inline-block'){
					$("#csDate").next().css('display','none');
				}else{
					$("#csDate").next().css('display','inline-block');
				}
			});

			//结束日期
			$('#ceDate').on('changeDate',function(){
				if ($('#ceDate').val()) {
					$('#csDate').datetimepicker('setEndDate', $('#ceDate').val());
				}else{
					$('#csDate').datetimepicker('setEndDate', year+'-'+month+'-'+day);
				};
				if($('#ceDate').val()=="" && $("#ceDate").next().css('display') == 'inline-block'){
					$("#ceDate").next().css('display','none');
				}else{
					$("#ceDate").next().css('display','inline-block');
				}
			});
			
			$("#search").click(function(){
				globalOrderIds = [];
            	$("#orderIds").val("");
            	document.getElementById("totalCheck").checked = false;
            	table.refreshThis();
			});
			
			var table = $.GLOBAL.utils.loadBootTable({
		        table : $('#dataList'),
		        pagination : true,
		        pageSize : 10,
		        url: 'orgOrder/list',
		        sidePagination:'server',
		        queryAddParams: function() {
		            var queryObj =  {
		            	startTime:		$.trim($('#csDate').val()),
		            	endDate:		$.trim($('#ceDate').val()),
		            	orderId:	$.trim($('#orderId').val()),
		            	phone:	$.trim($('#phone').val()),
		                productName:  $.trim($('#productName').val()),
		                typeId:		$('#typeId').val(),
		                orgId		: $('#orgId').val(),
		                status     : $('#status').val()
		            } ;
		            return queryObj;
		        },
		        columns: [{
                		field : 'orderId',
                		align: 'center',
                		title : "<input type='checkbox' id='totalCheck'>",
                		formatter:function(value,row,index){  
                			document.getElementById("totalCheck").checked = false
                			if (globalOrderIds.indexOf(value) > -1) {
                				return "<input value='" + value + "' name='btCheckItem' type='checkbox' class='order-check' checked='checked'>"
                			} else {
                				return "<input value='" + value + "' name='btCheckItem' type='checkbox' class='order-check'>"
                			}
                		}
                	} , {
		                field: 'orderId',
		                title: '订单号'
		            } , {
		                field: 'createDate',
		                title: '创建时间'
		            } , {
		                field: 'phone',
		                title: '手机号'
		            } , {
		                field: 'productName',
		                title: '商品名称',
		                width:'200px',
			  	           formatter:function(value,row,index){
			  	        	var result = "";
			  	        	
			  	        	var str = value;   
			  	        	var arr=new Array();
			  	        	arr=str.split('#&amp;#');
			  	        	for(var i=0;i<arr.length;i++)   
			  	        	{    
			  	        		var span = "<p style='color:#3c8dbc;' ";
			  	        		span += "title='"+arr[i]+"' class='trackRecord-p'>"+arr[i]+"</p>";
			  	        	
			  	        		result += span;
			  	        	}
			  	        	  
			  	        	return result;
			  	           }
		            } , {
		                field: 'orgName',
		                title: '服务商',
		                width:"130px",
		                formatter:function(value,row,index){
			  	        	var result = "";
			  	        	
			  	        	var str = value;   
			  	        	var arr=new Array();
			  	        	arr=str.split('/');
			  	        	for(var i=0;i<arr.length;i++)   
			  	        	{    
			  	        		var span = "<p ";
			  	        		if(i != 0 && arr[i] == arr[i-1]){
			  	        			span += "style ='visibility:hidden;' "
			  	        		}
			  	        		span += "title='"+arr[i]+"' class='trackRecord-s'>"+arr[i]+"</p>";
			  	        	
			  	        		result += span;
			  	        	}
			  	        	  
			  	        	return result;
			  	           }
		            } ,
		            //{
		            //    field: 'productType',
		            //    title: '商品类型',
		            //    width:100,
		            //    formatter:function(value,row,index){
			  	    //    	var result = "";
			  	    //    	
			  	    //    	var str = value;   
			  	    //    	var arr=new Array();
			  	    //    	arr=str.split('/');
			  	    //    	for(var i=0;i<arr.length-1;i++)   
			  	    //    	{    
			  	    //    		var span = "<p ";
			  	    //    		if(i != 0 &&　arr[i] == arr[i-1]){
			  	    //    			span += "style ='visibility:hidden;' "
			  	    //    		}
			  	    //    		span += " >"+arr[i]+"</p>";
			  	    //    	
			  	    //    		result += span;
			  	    //    	}
			  	    //    	  
			  	    //    	return result;
			  	    //       }
		            //} ,
		            {
		                field: 'amount',
		                title: '金额（元）'
		            } , {
		                field: 'status',
		                title: '状态',
		                width:60
		            } , {
		                field: 'orderId',
		                align: 'center',
		                title: '操作',
		                width:50,
		                formatter: function(value,row,index){
		                	var result = "";
		                	result += ' <a   href="'+urlPrefix+'validate/view/'+ row.orderId +'/0" class="viewItem" data-id="'+row.orderId+'"> 查看</a>';
		                	return result;
		                } 
		            } 
		         ]
		    });
			
			$("#dataList").on('click','#totalCheck',function(){
				totalcheck();
			});
			
			$("#dataList").on('click','.order-check',function(){
				if ($(this).is(':checked') == true) {
					globalOrderIds.push($(this).val());
				} else {
					var index = globalOrderIds.indexOf($(this).val());
					if (index > -1) {
						globalOrderIds.splice(index, 1);
					}
				}
				$("#orderIds").val(globalOrderIds);
			});
			
			var totalcheck = function(){
				var box = document.getElementsByName("btCheckItem");
				if ($("#totalCheck").is(':checked') == true) {
					for(var i = 0; i < box.length; i++) {
						if (box[i].checked == false) {
							globalOrderIds.push(box[i].value);
						}
						box[i].checked = true;
					}
				} else {
					for(var i = 0; i < box.length; i++) {
						if (box[i].checked == true) {
							var index = globalOrderIds.indexOf(box[i].value);
							if (index > -1) {
								globalOrderIds.splice(index, 1);
							}
						}
						box[i].checked = false;
					}
				}
				$("#orderIds").val(globalOrderIds);
			};
			
			//excel导出
			$('#exportOrder').click(function(){
				//导出
				BootstrapDialog.show({
		            title: "导出",
		            type : BootstrapDialog.TYPE_WARNING,
		            message: message('确认导出订单列表吗？'),
		            draggable: true,
		            size : BootstrapDialog.SIZE_SMALL,
		            buttons: [{
		                label: '确认',
		                cssClass: 'btn-primary saveAddEditTpl',
		                action: function(dialog) {
		                	dialog.close();
		                	$("#orderForm").submit();
		                	globalOrderIds = [];
		                	$("#orderIds").val("");
		                	document.getElementById("totalCheck").checked = false;
		                	table.refreshThis();
		                }
		            }, {
		                label: '取消',
		                action: function(dialog) {
		                    dialog.close();
		                }
		            }]
		        });
			});
		},
		init : function() {
			var _self = this;
			_self.initEvents();
		}
	}.init();
});