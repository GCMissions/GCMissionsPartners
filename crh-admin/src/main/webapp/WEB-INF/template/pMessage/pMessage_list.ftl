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
            <li><i class="fa fa-dashboard"></i> 站内信管理</li>
            <li>站内信列表</li>
             
          </ol>
          
          
         </section>
        
       
        
        <!-- Main content -->
        <div class="row pad">
             <div class="col-md-12">
                <div class="box box-primary ">
                    
                       <!-- /.box-header -->  
                                        
                        
                    
                      <div class="form-horizontal search-group" >
                     	 <div class="box-body">
                            <div class="form-group ">
                            
                                	<label class="col-sm-2 control-label" for="type-select">标题：</label>
                                 
                                    <input  id="sTitle" class="form-control col-sm-2" type="text">
                               
                                
                               
                               
                                
                                     
                                 <label class="col-sm-2 control-label" for="type-select">发送对象：</label>
                                 <div class="col-sm-3">    
                                        <select    class="selectpicker form-control " id="sOrgType" title="请选择发送对象"> 
                                        	<option value="">全部</option>
                                        	<option value="z">终端配送商</option>
                                            <option value="p">区域平台商</option>
                                           
                                        </select>
                                   
                                </div>
                            </div>
                           
                            
                            <div class="form-group ">   
                            	  
                                <label class="col-sm-2 control-label">   
	                                <button type="button" class="btn btn-primary " id="refreshRecord"><i class="fa fa-search"></i> 开始搜索</button>                                
                                </label>
                                 <label class="col-sm-1 control-label pull-left">  
                                   <button type="button" class="btn btn-default reloadPage" ><i class="fa  fa-refresh"></i> 刷新</button>  
                                   </label>       
                                <label class="col-sm-2  pull-left">   
                            	 <a class="btn btn-default addItem" href="${urlPrefix}pMessage/add" ><i class="fa fa-plus"></i> 发送短信</a>
                            	</label>                       
                            </div>                            
                          </div> 
                     </div> 
                         
                      
                   
                    <div class="box-body">
                      <table id="merchantList" class="table table-bordered table-hover dataList" >
                        <thead>
                           
                            <th>序号</th> 
                            <th>标题</th>
                            
                            <th>发送时间 </th> 
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
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
 
<#include "/footer.ftl" /> 

<script type="text/javascript" src="${uiBase}/js/pages/pMessage/pMessage_list.js?v=${resourceVersion}"></script> 
<script>
 $(function() {
    messageApp.init();
});
</script>
</body>
  
</html>