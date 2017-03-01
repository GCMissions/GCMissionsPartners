<#assign headComponents = ["ionicons"] >
<#include "/header.ftl" /> 
<style type="text/css">
.firstTable td {
	vertical-align: middle;
}
table.firstTable .navTr{
  height: 60px;
  padding: 5px 16px;
  background-color: #D3D3D3;
}
.firstTable input.button{
  min-width:105px;
  margin-right:10px;
  border-radius:5px;
}
</style>

<body class="hold-transition skin-blue   ">
    <div class="wrapper" style="overflow:visible">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper page-content-wrapper">
       <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
           		 管理首页
          </h1>
        </section>
        
        <!-- Main content -->
        <section class="content body">
        <table class="input firstTable table">
		<tr>
			<th>
				系统名称:
			</th>
			<td>
				${systemName}
			</td>
			<th>
				系统版本:
			</th>
			<td class="version">
				
			</td>
		</tr>
		<tr>
			<th>
				官方网站:
			</th>
			<td>
				<a href="#" target="_blank">暂无</a>
			</td>
			<th>
				官方论坛:
			</th>
			<td>
				<a href="#" target="_blank">暂无</a>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				&nbsp;
			</td>
		</tr>
		<tr>
			<th>
				JAVA版本:
			</th>
			<td>
				${javaVersion}
			</td>
			<th>
				JAVA路径:
			</th>
			<td>
				${javaHome}
			</td>
		</tr>
		<tr>
			<th>
				操作系统名称:
			</th>
			<td>
				${osName}
			</td>
			<th>
				操作系统构架:
			</th>
			<td>
				${osArch}
			</td>
		</tr>
		<tr>
			<th>
				Servlet信息:
			</th>
			<td>
				${serverInfo}
			</td>
			<th>
				Servlet版本:
			</th>
			<td>
				${servletVersion}
			</td>
		</tr>
		<tr>
			<td colspan="4">
				&nbsp;
			</td>
		</tr>
		
	</table>
		<#--
            <div class="row">
                <div class="col-lg-3 col-xs-6">
                    
                    <div class="small-box bg-aqua">
                        <div class="inner">
                        <h3>150</h3>
                        <p>新订单</p>
                        </div>
                        <div class="icon">
                        <i class="ion ion-bag"></i>
                        </div>
                        <a href="#" class="small-box-footer ">更多<i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div> 
                <div class="col-lg-3 col-xs-6">
                  
                  <div class="small-box bg-green">
                    <div class="inner">
                      <h3>53<sup style="font-size: 20px">%</sup></h3>
                      <p>跳出率</p>
                    </div>
                    <div class="icon">
                      <i class="ion ion-stats-bars"></i>
                    </div>
                    <a href="#" class="small-box-footer">更多<i class="fa fa-arrow-circle-right"></i></a>
                  </div>
                </div> 
                <div class="col-lg-3 col-xs-6">
                  
                  <div class="small-box bg-yellow">
                    <div class="inner">
                      <h3>44</h3>
                      <p>注册用户</p>
                    </div>
                    <div class="icon">
                      <i class="ion ion-person-add"></i>
                    </div>
                    <a href="#" class="small-box-footer">更多<i class="fa fa-arrow-circle-right"></i></a>
                  </div>
                </div> 
                <div class="col-lg-3 col-xs-6">
                  
                  <div class="small-box bg-red">
                    <div class="inner">
                      <h3>65</h3>
                      <p>独立访客(UV)</p>
                    </div>
                    <div class="icon">
                      <i class="ion ion-pie-graph"></i>
                    </div>
                    <a href="#" class="small-box-footer">更多<i class="fa fa-arrow-circle-right"></i></a>
                  </div>
                </div> 
            </div> 
            -->
            <#--
            
            <div class="row">
                
                <section class="col-lg-6 connectedSortable">
                  
                  <div class="nav-tabs-custom">
                     
                    <ul class="nav nav-tabs pull-right">
                      <li class="active"><a href="#revenue-chart" data-toggle="tab">地区</a></li>
                      <li><a href="#sales-chart" data-toggle="tab">圆环图</a></li>
                      <li class="pull-left header"><i class="fa fa-inbox"></i> 销售</li>
                    </ul>
                    <div class="tab-content no-padding">
                       
                      <div class="chart tab-pane active" id="revenue-chart" style="position: relative; height: 300px;"></div>
                      <div class="chart tab-pane" id="sales-chart" style="position: relative; height: 300px;"></div>
                    </div>
                  </div> 
                  
                  
                </section> 
                
                 <section class="col-lg-6 connectedSortable">
                   
                  <div class="nav-tabs-custom">
                    
                    <ul class="nav nav-tabs pull-right">
                      <li class="active"><a href="#revenue-chart" data-toggle="tab">地区</a></li>
                      <li><a href="#sales-chart" data-toggle="tab">圆环图</a></li>
                      <li class="pull-left header"><i class="fa  fa-reorder"></i> 订单</li>
                    </ul>
                    <div class="tab-content no-padding">
                      
                      <div class="chart tab-pane active" id="revenue-chart" style="position: relative; height: 300px;"></div>
                      <div class="chart tab-pane" id="sales-chart" style="position: relative; height: 300px;"></div>
                    </div>
                  </div>
                  
                </section> 
                
          
            </div> 
         --> 
        </section>
        
        
        <!-- Main content -->
        <!--
        <div class="row pad">
             <div class="col-md-12">
                <div class="box ">
                  
                    
                   
                </div>
             </div>
        </div>
        -->
       <!-- /.content -->
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
</body>

<#include "/footer.ftl" />
<script>
$('td.version').text($.GLOBAL.config.version);
</script> 
</html> 

