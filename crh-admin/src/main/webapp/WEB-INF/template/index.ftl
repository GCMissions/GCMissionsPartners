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
           		 Manager Index
          </h1>
        </section>
        
        <!-- Main content -->
        <section class="content body">
        <table class="input firstTable table">
		<tr>
			<th>
				System Name:
			</th>
			<td>
				Church
			</td>
			<th>
				System Version:
			</th>
			<td class="version">
				
			</td>
		</tr>
		<tr>
			<th>
				Official Website:
			</th>
			<td>
				<a href="#" target="_blank"></a>
			</td>
			<th>
				Official BBS:
			</th>
			<td>
				<a href="#" target="_blank"></a>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				&nbsp;
			</td>
		</tr>
		<tr>
			<th>
				JAVA Version:
			</th>
			<td>
				${javaVersion}
			</td>
			<th>
				JAVA Path:
			</th>
			<td>
				${javaHome}
			</td>
		</tr>
		<tr>
			<th>
				Operating System Name:
			</th>
			<td>
				${osName}
			</td>
			<th>
				Operating System Architecture:
			</th>
			<td>
				${osArch}
			</td>
		</tr>
		<tr>
			<th>
				Servlet Infomation:
			</th>
			<td>
				${serverInfo}
			</td>
			<th>
				Servlet Version:
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
 	</section>
        
        
        <div class="clearfix"></div>
        
      </div><!-- /.content-wrapper -->
   </div><!-- ./wrapper -->
</body>

<#include "/footer.ftl" />
<script>
$('td.version').text($.GLOBAL.config.version);
</script> 
</html> 

