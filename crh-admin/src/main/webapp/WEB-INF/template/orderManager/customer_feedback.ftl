<#assign headComponents = ["bootTable", "bootDialog"] >
<#include "/header.ftl" /> 
<link rel="stylesheet" href="${uiBase}/css/list.css?v=${resourceVersion}">

</head>

<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
          <ol class="breadcrumb">
			<li><i class="fa fa-dashboard"></i> 订单管理</li>
			<li>消费者反馈</li>
			 
		  </ol>
        </section>
        
    
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                    <div class="form-horizontal search-group">
                        <div class="box-body"> 
                        
                                <div class="form-group "> 
	  									<label  class="control-label col-sm-2" for="type-select">反馈日期：</label>
				           				<div class="col-sm-6 no-padding timegroup">
		  									
		  									<input type="hidden"  name="csDateInput"  value="" id="startDateH"  >
		  									<div class="dateDiv" style="margin-bottom: 0px;">
				  								<input size="10" type="text" name="startDate" id="startDate" class="form-control keyword beginDate" placeholder="请选择时间" readonly>
											    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
											    <span class="add-on"><i class="icon-calendar"></i></span>
											</div>
		  									<label class="time">至</label>
		  									<input  type="hidden"  name="ceDateInput" value="" id="endDateH" >
		  									<div class="dateDiv" style="margin-bottom: 0px;">
											    <input size="10" type="text" name="endDate" id="endDate" class="form-control keyword endDate" placeholder="请选择时间" readonly>
											    <span class="add-on" style="display:none"><i class="icon-remove"></i></span>
											    <span class="add-on"><i class="icon-calendar"></i></span>
											</div> 
		  								 
					          			 </div> 
		  								<label class="control-label col-sm-2" for="type-select">反馈状态：</label>
		  								<div class="col-sm-2 no-padding">
		  						 		 	<select class="selectpicker" id="statusId"> 
                                               <option value="">全部</option>
                                               <#list status as type>
	                                           <option value="${type.key}">${type.value}</option> 
	                                           </#list>
                                             </select>
	  								  	</div>  
	  							</div>
                                <div class="form-group ">   
						            <label class="col-sm-2 control-label">   
						                <button type="button" id="search" class="btn btn-primary ">开始搜索</button>                                
						            </label>   
						            <div class="col-sm-5 ">
		                           		<button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>  
									</div>                         
						        </div>    
                                
                        </div>
                        
                       
                        <!-- /.box-tools -->
                    </div><!-- /.box-header -->
                    
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover" >
                        <thead>
                          	<th>序号</th>
                          	<th>消费者姓名</th>
                            <th>联系方式</th>
                            <th>收货地址</th>
                            <th>反馈内容</th>
                            <th>反馈状态</th>
                            <th>时间</th>
                        </thead>
                        <tbody>
                        </tbody>
                      </table>
                    </div>
                </div>
             </div>
        </div>
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
  
    
</body>
<#include "/footer.ftl" />
<script id="statusTpl" type="text/html">
<div class="box-body form-horizontal">
	<div class="form-group row">
	<div class="col-sm-5 ">
	 </div>
		<label  >处理完成！</label>
	</div>
</div>
</script>
<script>
function SetStatus(id) {
	$.ajax({
		url:"save",
		type:"post",
		dataType : "json",
		data : id,
		contentType : "application/json; charset=utf-8",
		success:function(msg){
			BootstrapDialog.show({
		        title: '处理反馈',
		        message: $(template('statusTpl', {})),
		        draggable: true,
		        buttons: [ {
		            label: '关闭',
		            action: function(dialog) {
		                dialog.close();
		                $('#search').click();
		            }
		        }]
		    });
			
		}
	});
};
function DetailInfo(info) {
	BootstrapDialog.show({
        title: '反馈详情',
        message: info,
        draggable: true,
        buttons: [ {
            label: '关闭',
            action: function(dialog) {
                dialog.close();
            }
        }]
    });
};
$(function() { 
	//开始日期
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();

	$('#startDate,#endDate,#usDate,#ueDate').datetimepicker({
		minView: 'month',
		format: 'yyyy-MM-dd',
		language: 'ch',
		endDate: year+'-'+month+'-'+day,
		autoclose : true,
		todayBtn : true
	});
	//创建日期
	$('#startDate').on('changeDate',function(){
		$('#endDate').datetimepicker('setStartDate', $('#startDate').val());
		if($('#startDate').val()=="" && $("#startDate").next().css('display') == 'inline-block'){
			$("#startDate").next().css('display','none');
		}else{
			$("#startDate").next().css('display','inline-block');
		}
	});

	//结束日期
	$('#endDate').on('changeDate',function(){
		if ($('#endDate').val()) {
			$('#startDate').datetimepicker('setEndDate', $('#endDate').val());
		}else{
			$('#startDate').datetimepicker('setEndDate', year+'-'+month+'-'+day);
		};
		if($('#endDate').val()=="" && $("#endDate").next().css('display') == 'inline-block'){
			$("#endDate").next().css('display','none');
		}else{
			$("#endDate").next().css('display','inline-block');
		}
	});
	
	$.GLOBAL.utils.loadBootTable({
		table : $('#dataList'),
	    refreshBtn : $('#search'),
	    idField : "feedbackId",
	    url: 'customerFeedback/list',
	    sidePagination:'server',
	    pagination : true,
	    queryAddParams: function() {
	    	return {
	    		status:$("#statusId").val(),
	    		startDate:$("#startDate").val(),
	    		endDate:$("#endDate").val()
	    	}
	    },
	    columns: [
	              {
			width:50,
			title: '序号',
		    align: 'center',
		    formatter:function(value,row,index){  
		    	return index+1; 
		    	}
			} ,
	        {
	            field: 'name',
	            sortable: false
	        } ,
	        {
	            field: 'phone',
	            sortable: false,
	        } ,
	        {
	            field: 'address',
	            sortable: false,
	        } ,
	        {
	            align: 'center',
	            checkbox: false,
	            formatter:function(value,row,index) { 
	               var info = row.feedInfo;
	               if (info.length>30){
		               info = row.feedInfo.replace(row.feedInfo.substr(30),'... ...');
	               }
	               return ' <a  title="查看详细" href="javascript:DetailInfo(\'' + row.feedInfo + '\')" class="editItem" data-id="'+row.feedbackId+'">'+ info +'</a>';
	  
	            } 
	        },
	        {
	            align: 'center',
	            checkbox: false,
	            formatter:function(value,row,index){  
	               if(row.feedStatus == 1){
	            	   return '已处理'
	               } else {
	                return ' <a  title="处理" href="javascript:SetStatus(\'' + row.feedbackId + '\')" class="editItem" data-id="'+row.feedbackId+'">未处理</a>';
	               }
	            } 
	        },
	        {
	            field: 'date',
	            sortable: false,
	        }
	     ]
	});
});
 </script>
</html>
