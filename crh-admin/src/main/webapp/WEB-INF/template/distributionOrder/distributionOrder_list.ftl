<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
</head>


<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
         <section class="content-header">       
        <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i> 订单管理</li>
            <li><i class="fa"></i> 订单管理</li>         
       </ol>
       </section>
       
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                   <div class="form-horizontal search-group" id="search-area" >
					    <div class="box-body"> 
					 
					        <div class="form-group ">
					        
					           <label class="control-label col-sm-2" for="type-select">订单编号:</label>
					           <input id="orderId"  name="orderId" class="form-control col-sm-3" type="text">
					             
					            <label class="control-label col-sm-2" for="type-select">订单时间：</label>
					           <div class="col-sm-6 no-padding timegroup">
					                <input type="hidden" data-ignore="true"  name="stDate" id="stDate" value="" />
					                <div class="dateDiv" style="margin-bottom: 0px;">
					                    <input size="10" type="text"  name="startDate" id="csDate" class="form-control keyword beginDate" placeholder="请选择时间" readonly>
					                    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
					                    <span class="add-on"><i class="icon-calendar"></i></span>
					                </div>
					                <span class="time">至</span>
					                <input type="hidden" data-ignore="true"  name="edDate" id="edDate" value="" />
					                <div class="dateDiv" style="margin-bottom: 0px;">
					                    <input size="10" type="text" name="endDate"  id="ceDate" class="form-control keyword endDate" placeholder="请选择时间" readonly>
					                    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
					                    <span class="add-on"><i class="icon-calendar"></i></span>
					                </div>
					           </div>
					     </div> 					     
					        <div class="form-group ">    
					               
					           <label class="control-label col-sm-2" for="type-select">订单状态:</label>
					             <div class="col-sm-2 no-padding">
					                <select class="selectpicker form-control  "  id="status" name="shipmentType"> 
					                              <option value="">全部</option> 
					                              <#list orderStatus as status>
				                                  <option value="${status.key}">${status.value}</option> 
				                                  </#list>
					                </select>
					             </div>					             
					           <label class="control-label col-sm-2" for="type-select">收货人:</label>
					           <input id="consignee"  name="consignee" class="form-control col-sm-3" type="text">
					           <label class="control-label col-sm-2" for="type-select">收货人联系方式:</label>
					           <input id="phone"  name="phone" class="form-control col-sm-3" type="text">
					       </div>
					       <div class="form-group ">   
					            <label class="col-sm-2 control-label">   
					               <button type="button" id="search" class="btn btn-primary pull-right">开始搜索</button>                                
					            </label>  
					            
					            <label class="col-sm-2 control-label">   
					               <button type="button" id="newOrder" class="btn  btn-normal btn-danger">新订单(${newOrder})</button>                                
					            </label> 
					            
					              <label class="col-sm-2 control-label">   
					             <button type="button" class="btn btn-default reloadPage " id="refreshRecord"><i class="fa  fa-refresh"></i> 刷新</button>
					            </label> 
					            
					            <label class="col-sm-2 control-label">   
					               <button type="button" id="exportBtn" class="btn  btn-primary">导出出库单</button>                                
					            </label>
					            
					        </div>    				       
					     </div>  
					</div>   
                    
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover dataList" >
                        <thead>
                        	<th><input type="checkbox"/></th>
                            <th>序号</th>
                            <th>订单编号</th>
                            <th>订单金额（元）</th>
                            <th>收货人</th>
                            <th>联系方式</th>
                            <th>订单状态</th>
                            <th>收货方式</th>
                            <th>下单日期</th>
                            <th>查看</th>
                            <th>操作</th>
                        </thead>
                        <tbody>
                        </tbody>
                      </table>
                    </div><!-- /.box-body -->
                    
                   
                </div>
             </div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        <input type="hidden" name="newOrderFlag" id="newOrderFlag" value= "${newOrderFlag}"  />
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
</body>
<#include "/footer.ftl" /> 
<script>
    $(function () {
    	
    	//开始日期
    	var date = new Date();
    	var year = date.getFullYear();
    	var month = date.getMonth()+1;
    	var day = date.getDate();

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
    	
    	
    	$('#newOrder').on("click", function() {
    		table.options.queryAddParams = function(){
    			return {
                    newOrder:"T",
    			}
    		};
    		table.refresh();
    	});
    	
    		
    	//导出表格
		$('#exportBtn').on('click',function(){
			var orderId = "";
					$('#dataList tbody tr').each(function(){
						if($(this).hasClass('selected')){
							var text = $(this).find('td').text();
							if(text.indexOf('待发货') != -1 ){
								//获取当前的orderId
								var id = $(this).find('.orderId').text();
								orderId +=id+",";
							}else {
								orderId="";
								return;
							}
						}
					});
					
					if(orderId !=null && orderId !=""){
						orderId = orderId.substring(0,orderId.length-1);
						window.location.href = $.GLOBAL.config.urlPrefix +"distributionOrder/toExcelOrder/?orderId="+orderId;
					}else{
						BootstrapDialog.show({
							title: "提示",
							type : BootstrapDialog.TYPE_WARNING,
							message: message("选择错误，请至少选择一个待发货的订单!"),
							draggable: true,
							size : BootstrapDialog.SIZE_SMALL,
							buttons: [{
								label: '确认',
								cssClass: 'btn-primary',
								action: function(dialog) {
									dialog.close();
								}
							}]
						});
					}
				});
    	
    	$('#search').on("click", function() {
    		table.options.queryAddParams = function(){
    			return {
    				orderId : $("#orderId").val(),
                    status : $("#status").val(),
                    startDate : $("#csDate").val(),
                    endDate : $("#ceDate").val(),
                    consignee : $("#consignee").val(),
                    phone : $("#phone").val(),
    			}
    		};
    		//table.refresh();
    	});
    	
    	var table = $.GLOBAL.utils.loadBootTable({
    		table : $('#dataList'),
    	    refreshBtn : $('#search'),
    	    idField : "id",
    	    url: 'distributionOrder/search',
    	    sidePagination:'server',
    	    pagination : true,
    	    queryParamsType: "limit",
    	    queryAddParams: function() {
    	    	return {
    	    		orderId : $("#orderId").val(),
                    status : $("#status").val(),
                    startDate : $("#csDate").val(),
                    endDate : $("#ceDate").val(),
                    consignee : $("#consignee").val(),
                    phone : $("#phone").val(),
                    newOrder: $("#newOrderFlag").val(),
    	    	}
    	    },
    	    columns: [
    	              {
    	            	checkbox:true,
    	            	
    	              },
    	              {
  						width : 50,
  						align: 'center',
  						formatter:function(value,row,index){  
  							return index+1;
  						}
        	            },
    	        {
    	            field: 'orderId',
    	            class:"orderId",
    	            visible : true
    	        }, 
    	        {
    	            field: 'totalAmount',
    	            sortable: false
    	        } ,
    	        {
    	            field: 'memberName',
    	            sortable: false
    	        } ,
    	        {
    	            field: 'phone',
    	            sortable: false
    	        } ,
    	        {
    	            field: 'status',
    	            sortable: false
    	        } ,
    	        {
    	            field: 'shipmentType',
    	            sortable: false
    	        },
    	        {
    	            field: 'createDate',
    	            sortable: false
    	        },
    	         {
    	            title: '查看',
    	            field: 'orderId',
    	            align: 'center',
    	            sortable: false,
    	            formatter:function(value,row,index){  
    	            	var viewUrl = $.GLOBAL.config.urlPrefix + 'distributionOrder/detail/' + row.orderId;
    	            	return ' <a  title="查看" href="'+ viewUrl +'" class="editItem"> <i class="fa fa-eye"  style="font-size:20px"></i></a>';
    	            } 	
    	        },
    	         {
    	            field: 'id',
    	            align: 'center',
    	            sortable: false,
    	            formatter:function(value,row,index){  
    	            	var status = row.status;
    	            	var scanCodeFlag=row.scanCodeFlag;
    	            	var barcodeUrl = $.GLOBAL.config.urlPrefix +"barcode/index/7/"+row.orderId;
    	            	var result = "";
    	            	
    	            	if(status=="待接单"){
    	            		result = ' <button class="btn  btn-default" id="noShipment">接单</button>';
    	            	}else if(status=="待发货"){
    	            		result = ' <button class="btn  btn-primary" id="shipmentBtn">扫码出库</button>';
    	            	}else if(status=="待退货"&&scanCodeFlag==true){
    	            		result = '<a  title="【扫码】"  target="_self" href="'+ barcodeUrl +'">'+'<button class="btn  btn-primary">扫码退货</button>'+'</a>';
    	            	}else{
    	            		result = ' -';
    	            	}
    	            	
    	            	//未支付状态同步支付结果
    	                if((row.source==6||row.source==7)&&(row.paymentStatus==1)) {
    	                	result += '<input type="hidden" class="orderNo" value="'+row.orderId+'"><button type="button" class="btn btn-primary syncPaymentStatus" style="padding: 0px 3px;margin-left: 10px;">同步支付结果</button>';
    	                }
    	            	return result;
    	            } 
    	        }
    	     ],
    	     onClickCell : function(field, value, row, $element) {
    	    	 
    	    	 if(field=="id"){
    	    		 if(row.status=="待接单"){
    	    			 $.ajax({
     	    				url:$.GLOBAL.config.urlPrefix + 'distributionOrder/getGoods/' + row.orderId,
     	    				type:"get",
     	    				dataType : "json",
     	    				data : null,
     	    				contentType : "application/json; charset=utf-8",
     	    				success:function(msg){
     	    					 $('body').loadingInfo("success", "接单成功！");
     	    				     $("#noShipment").removeClass('btn-primary');
     	    					 $("#noShipment").addClass('btn-default');
     	    					 $("#noShipment").html("已接单");
     	    					 window.location.reload();
     	    				}
     	    			});
    	    		 }
    	    	 }
    	     }
    	});   
    	
    	$('#dataList').on('click','#shipmentBtn',function(){
      		var orderId = $(this).parent().parent().find('.orderId').text();
      		window.location.href = $.GLOBAL.config.urlPrefix +"barcode/index/5/"+orderId;
    	});
    	
    	$(document).on("click",".syncPaymentStatus",function(){
    		var orderNo = $(this).closest("td").find(".orderNo").val();
    		var data = {
    			orderNo	: orderNo	
    		};
    		$.ajax({
    			type: 'POST',
    			url: urlPrefix + "offline/orderconfirm/sync",
    			dataType: 'json',
    			contentType: 'application/json',
    			data : JSON.stringify(data)
    		})
    		.done(function(data){
    			if(data.code == "ACK") {
    				var payStatus = data.data.code;		
    				if(payStatus=="SUCCESS") {
    					BootstrapDialog.show({
    						title : "同步支付结果",
    						message : "订单支付成功",
    						draggable : true,
    						buttons : [ {
    							label : '确定',
    							action : function(dialog) {
    								dialog.close();
    								window.location.reload();
    							}
    						} ]
    					});
    					
    				}else {
    					BootstrapDialog.show({
    						title : "同步支付结果",
    						message : "订单支付不成功",
    						draggable : true,
    						buttons : [ {
    							label : '确定',
    							action : function(dialog) {
    								dialog.close();
    							}
    						} ]
    					});					
    				}
    			}		
    		})
    		.fail(function(){
    			
    		});
    	});
    	
    });
    
    
    </script>

  
</html>
