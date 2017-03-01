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
            <li><i class="fa fa-dashboard"></i> 库存管理</li>
            <li><i class="fa"></i> 库存管理</li>
       </ol>
       </section>
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary">
                  <div class="form-horizontal search-group" id="search-area" >
					    <div class="box-body"> 
					 
					        <div class="form-group ">
					        
					           <label class="control-label col-sm-2" for="type-select">单瓶商品名称:</label>
					           <input id="name"  name="orderId" class="form-control col-sm-3" type="text">
					           <label class="control-label col-sm-2" for="type-select">单瓶商品编号:</label>
					           <input id="sn"   name="orderId" class="form-control col-sm-3" type="text">
					            <label class="control-label col-sm-2" for="type-select">库存状态:</label>
					             <div class="col-sm-2 no-padding">
					                <select class="selectpicker form-control  "  id="status" name="shipmentType"> 
					                 <option value="">全部</option>
								     <option value="0">正常</option>
								     <option value="1">预警</option>
					                </select>
					             </div>
					     </div> 
					     
					        <div class="form-group ">    
					               
					           <label class="control-label col-sm-2" for="type-select">库存总量：</label>
					          <div class="col-sm-6 no-padding timegroup">
					                <div class="dateDiv" style="margin-bottom: 0px;">
					                <input type="text" value="" id="stockLow" class="form-control" >
					                </div>
					                <span class="time">至</span>
					                <div class="dateDiv" style="margin-bottom: 0px;">
					                <input type="text" value="" id="stockHigh" class="form-control" >
					                </div>
					           </div>
					          
					       </div>
					       <div class="form-group ">   
					            <label class="col-sm-2 control-label no-padding" style="text-align: right;"> 
					               <button type="button" id="search" class="btn btn-primary pull-right"><i class="fa fa-search"></i> 开始搜索</button>                                
					            </label>  
					            
					           <label class="col-sm-1 control-label pull-left">   
					               <button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>                                
					            </label> 
					            
					            <label class="col-sm-1 control-label pull-left">  
					               <button type="button" id="warn" class="btn  btn-normal btn-danger"><i class="fa fa-exclamation"></i> 库存预警(${waringCount})</button>                                
					            </label> 
					            
					            <!-- <label class="col-sm-2 control-label pull-left">
									<button data-exceltype="1" id="excel" class="btn btn-default" type="button"><i class="fa  fa-refresh"></i> 导出</button>
								</label> -->
					            
					        </div>    
					       
					     </div>  
					</div>   
                    
                    <div class="box-body">
                      <table id="dataList" class="table table-bordered table-hover dataList" >
                        <thead>
                            <th>单瓶商品编号</th>
                            <th>单瓶商品名称</th>
                            <th>销售价</th>
                            <th>库存总量</th>
                            <th>安全库存</th>
                            <th>库存状态</th>
                            <th>操作</th>
                        </thead>
                        <tbody>
                        </tbody>
                      </table>
                    </div><!-- /.box-body -->
                    
                   
                   <form id="stockForm" method="get" action="${base}/web/distribution/export">
	                   <input name="isWarning" id="isWarning" type="hidden" val="T">
	                   <input name="orgType" id="orgType" type="hidden" val="Z">
                   </form>
                   
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
    	
    	$('#warn').on("click", function() {
    		table.options.queryAddParams = function(){
    			return {
     	    		isWarning: "T",
     	    		orgType: "Z"
    			}
    		};
    		table.refresh();
    	});    	
    	
    	var excelReport = function(){
    		var title = "确定导出商家安全库存预警表？";
    		//导出
    		BootstrapDialog.show({
    			title: "导出",
    			type : BootstrapDialog.TYPE_WARNING,
    			message: message(title),
    			draggable: true,
    			size : BootstrapDialog.SIZE_SMALL,
    			buttons: [{
    				label: '确认',
    				cssClass: 'btn-primary saveAddEditTpl',
    				action: function(dialog) {
    					dialog.close();
    					$("#stockForm").submit();
    				}
    			}, {
    				label: '取消',
    				action: function(dialog) {
    					dialog.close();
    				}
    			}]
    		});
    	};
    	
    	$("#excel").on('click',function(){
	    	excelReport();
    	});
    	
    	$('#search').on("click", function() {
    		table.options.queryAddParams = function(){
    			return {
    			    name : $("#name").val(),
                    sn : $("#sn").val(),
                    status : $("#status").val(),
     	    		stockLow : $("#stockLow").val(),
     	    		stockHigh : $("#stockHigh").val(),
     	    		stock: "",
     	    		orgType: "Z",
    			}
    		};
    		//table.refresh();
    	});
    	
    	var table = $.GLOBAL.utils.loadBootTable({
    		table : $('#dataList'),
    	    refreshBtn : $('#search'),
    	    idField : "id",
    	    url: 'distribution/search',
    	    sidePagination:'server',
    	    pagination : true,
    	    queryParamsType: "limit",
    	    queryAddParams: function() {
    	    	return {
                    name : $("#name").val(),
                    sn : $("#sn").val(),
                    status : $("#status").val(),
    	    		stockLow : $("#stockLow").val(),
    	    		stockHigh : $("#stockHigh").val(),
    	    		stock: "",
    	    		orgType: "Z",
    	    		isWarning: ""
    	    	}
    	    },
    	    columns: [
    	        
    	        {
    	            field: 'goodCode',
    	            sortable: false
    	        } ,
    	        {
    	            field: 'name',
    	            sortable: false,
    	        } ,
    	        {
    	            field: 'priceYuan',
    	            sortable: false,
    	        } ,
    	        {
    	            field: 'stockNum',
    	            sortable: false,
    	        },
    	        {
    	            field: 'safeNum',
    	            sortable: false,
    	        },
    	        {
    	            field: 'status',
    	            sortable: false,
    	        }, {
    	            title: '操作',
    	            field: 'id',
    	            align: 'center',
    	            sortable: false,
    	            formatter:function(value,row,index){  
    	            	var viewUrl = $.GLOBAL.config.urlPrefix + 'distribution/detail/' + row.id;
    	                return ' <a  title="查看" href="'+ viewUrl +'" class="editItem"> <i class="fa fa-eye"  style="font-size:20px"></i></a>'
    	                	;    	  
    	            } 
    	        }
    	     ]
    	});  
    	
    	
    	
    	
    	
    });
    </script>

  
</html>
