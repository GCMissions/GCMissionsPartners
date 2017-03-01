<html>
<head>
<#assign headComponents = ["bootTable"] >
<#include "/header.ftl" /> 
<style>
.btnGroup{
	margin-left:70px;
}
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
         <section class="content-header">       
        <ol class="breadcrumb">
            <li><i class="fa fa-dashboard"></i> 收货管理</li>
             <li><i class="fa"></i> 收货管理</li>
       </ol>
       </section>
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary ">
                    <div class="form-horizontal search-group" id="search-area" >
					    <div class="box-body"> 
					 
					        <div class="form-group ">
					           <label class="control-label col-sm-2" for="type-select">收货单号:</label>
					           <input id="orderCode"  name="orderCode" class="form-control col-sm-3" type="text">
					          
					           <label class="control-label col-sm-2" for="type-select">收货数量：</label>
					           <div class="col-sm-6 no-padding timegroup">
					                <div class="dateDiv" style="margin-bottom: 0px;">
					                <input type="text" id="numLow" class="form-control" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
					                </div>
					                <span class="time">至</span>
					                <div class="dateDiv" style="margin-bottom: 0px;">
					                <input type="text" id="numHign" class="form-control" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
					                </div>
					           </div>
					     </div> 
					     
					        <div class="form-group ">    
					            <label class="control-label col-sm-2" for="type-select">收货状态:</label>
					             <div class="col-sm-2 no-padding">
					                <select class="selectpicker form-control  "  id="status" name="shipmentType"> 
					                           <option value="">全部</option>
                                               <option value="1">已创建</option>
                                               <option value="3">已收货</option>
                                               <option value="2">已发货</option>
					                </select>
					             </div>
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
					            <div class="btnGroup">   
						            <button type="button" id="search" class="btn btn-primary">开始搜索</button>                                
						            <button type="button" id="noShipment" class="btn  btn-normal btn-danger">待收货(${noShipmentCount})</button>                                
						         	<button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>  
						         	<button id="exportBtn" type="button" class="btn btn-primary" > 导出收货单</button>  
						         </div>
					        </div>    
					       
					     </div>  
					</div>
                    
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover dataList" >
                        <thead>
                        	<th><input type="checkbox"/></th>
                            <th>收货单</th>
                            <th>收货数量(件)</th>
                            <th>创建时间</th>
                            <th>收货状态</th>
                            <th>查看</th>
                            <th>发货</th>
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
    	
    	$('#noShipment').on("click", function() {
    		table.options.queryAddParams = function(){
    			return {
                       isReceipt:"F",
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
					var id = $(this).find('.orderCode').text();
					orderId +=id+",";
				}
			});
					
			orderId = orderId.substring(0,orderId.length-1);
			window.location.href = $.GLOBAL.config.urlPrefix +"shipment/toExcelShipment/?orderId="+orderId;
		});
    	$('#search').on("click", function() {
    		table.options.queryAddParams = function(){
    			return {
                 orderCode : $("#orderCode").val(),
                 status : $("#status").val(),
                 numLow : $("#numLow").val(),
                 numHign : $("#numHign").val(),
                 startDate : $("#csDate").val(),
                 endDate : $("#ceDate").val(),
    			}
    		};
    		//table.refresh();
    	});
    	
    	   	  $("body").on('click', '.shipment', function(e){
    		  var orderId = $(this).data('id');
    			window.location.href = $.GLOBAL.config.urlPrefix +'barcode/index/4/'+orderId;
    			});
    		});
    	   	
    	   	
    	   	
    	var table = $.GLOBAL.utils.loadBootTable({
    		table : $('#dataList'),
    	    refreshBtn : $('#search'),
    	    idField : "id",
    	    url: 'shipment/search',
    	    sidePagination:'server',
    	    pagination : true,
    	    queryParamsType: "limit",
    	    queryAddParams: function() {
    	    	return {
                    orderCode : $("#orderCode").val(),
                    status : $("#status").val(),
                    numLow : $("#numLow").val(),
                    numHign : $("#numHign").val(),
                    startDate : $("#csDate").val(),
                    endDate : $("#ceDate").val(),
    	    	}
    	    },
    	    columns: [
    	        {
    	        	checkbox:true
    	        },
    	        {
    	            field: 'orderCode',
    	            class:"orderCode",
    	            sortable: false
    	        } ,
    	        {
    	            field: 'totalNum',
    	            sortable: false,
    	        } ,
    	        {
    	            field: 'shipmentDate',
    	            sortable: false,
    	        } ,
    	        {
    	            field: 'status',
    	            sortable: false,
    	        }, {
    	            title: '查看',
    	            field: 'id',
    	            align: 'center',
    	            sortable: false,
    	            formatter:function(value,row,index){  
    	            	var viewUrl = $.GLOBAL.config.urlPrefix + 'shipment/detail/' + row.id;
                        var html;
                        if (row.status == "已发货") {
                        	html = ' <a  title="【查看】" target="_self"  href="'+ viewUrl +'" class="editItem"> <i class="fa fa-eye "  style="font-size:20px"></i></a>';
                        }else{
                        	html = ' <a  title="【查看】" target="_self"  href="'+ viewUrl +'" class="editItem"> <i class="fa fa-eye "  style="font-size:20px"></i></a>';
                        }
		            	return html;
    	            } 
    	        },
    	         {
    	            title: '收货',
    	            field: 'shipment',
    	            align: 'center',
    	            sortable: false,
    	            formatter:function(value,row,index){  
    	            	var listUrl = $.GLOBAL.config.urlPrefix + 'shipment/list';
                        var html;
                        if (row.status == "已发货") {
		            		html = '<a data-id="'+row.id+'" title="【收货】" target="_self"  href="#"  class="shipment"  ><i class="fa fa-truck " id="confirmGet" style="font-size:20px"></i></a>';
                        }else{ }
		            	return html;
    	            } 
    	        }
    	     ]
    	});  
    	
    	
    </script>
    
 <script id="shipmentDialog"  type="text/html">
<div class="box-body form-horizontal">
	<div class="form-group row">
		<div class="col-sm-2 "> </div>
		<textarea class="col-sm-8 " rows="3"> </textarea>
	</div>
</div>
</script>

  
</html>
