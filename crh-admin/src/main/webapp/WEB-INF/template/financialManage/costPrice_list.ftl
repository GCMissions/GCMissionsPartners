<#assign headComponents = ["bootTable", "innerPage"] > 
<#include
"/header.ftl" />
<style>
.datagrid-header-input {
	display: inline-block;
	width: 160px;
}
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>商品列表</h1>
			</section>

			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="box box-primary">
						<div class="box-header with-border">

							<div class="row">
								<div class="col-md-12">
									<div class="searchAdvanced " id="searchAdvanced">
										<div class="container-fluid">
											<div class="row last">
												<div class="col-sm-3">
													<label class="control-label" for="type-select"
														>商品名称：</label> <input
														 class="form-control" type="text" name="productName" id="productName">
												</div>
												<div class="col-sm-3">
													<label class="control-label" for="type-select">品牌：</label>
													<select class="selectpicker form-control " title="请选择品牌"
														name="brandId" id="brandId">
														<option value="">--请选择品牌--</option>
														<#list brand as b>
															<option value="${b.brandId}">${b.brandName}</option>
														</#list>
													</select>
												</div>
												<div class="col-sm-3">
													<label class="control-label" for="type-select">分类：</label>
													<select class="selectpicker form-control " title="请选择类别"
														name="cateId" id="cateId">
														<option value="">--请选择类别--</option>
														<#list category as c>
															<option value="${c.cateId}">${c.cateName}</option>
														</#list>
													</select>

												</div>
												<div class="col-sm-3">
													<label style="width: 100%; height: 14px"> </label>
													<button class="btn  btn-primary" id="refreshRecord">搜索</button>
													<button class="btn  btn-primary" id="addRecord">保存</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>



							<!-- /.box-tools -->
						</div>
						<!-- /.box-header -->

						<div class="box-body">
							<table id="dataList"
								class="table table-bordered table-hover dataList">
								<thead>
									<th>商品条码</th>
									<th>商品名称</th>
									<th>品牌</th>
									<th>分类</th>
									<th data-events="editCostPrice">成本价&nbsp;<input
										id="batch_edit" class="datagrid-header-input"
										type="text"></th>
									<th field="productId"></th>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<!-- /.box-body -->


					</div>
				</div>
			</div>
			<!-- /.content -->
			<div class="clearfix"></div>

		</div>
		<!-- /.content-wrapper -->
	</div>
	<!-- ./wrapper -->


</body>
<#include "/footer.ftl" />

<script>
	$(function() {
		
		$.GLOBAL.utils.loadBootTable({
			table : $('#dataList'),
			 refreshBtn : $('#refreshRecord'),
			url : 'costPrice/list',
			sidePagination : 'server',
			pagination : true,
			queryAddParams : function() {
				var params =  {
					productName : $("#productName").val(),
					brandId : $("#brandId").val(),
					cateId : $("#cateId").val()
				}
				return params;
			},
			columns : [ {
				field : 'productCode',
				sortable : false
			}, {
				field : 'productName',
				sortable : false,
			}, {
				field : 'brandName',
				sortable : false,
			}, {
				field : 'cateName',
				sortable : false,
			}, {
				field : 'costPrice',
				sortable : false,
				width:250
			},{
				field : 'productId',
				visible : false,
			}],
			onClickCell : function(field, value, row, $element) {
				if (field == "costPrice") {
					// 获取被点击的td  
					var $td = $element;
					// 检测此td是否已经被替换了，如果被替换直接返回  
					if ($td.children("input").length > 0) {
						return false;
					}
					// 获取$td中的文本内容  
					var text = $td.text();
					// 创建替换的input 对象  
					var $input = $("<input type='text'>").css(
							"background-color", $td.css("background-color"))
							.width($td.width());
					// 设置value值  
					$input.val(text);
					// 清除td中的文本内容  
					$td.html("");
					$td.append($input);
					// 处理enter事件和esc事件  
					$input.keyup(function(event) {
						// 获取当前按下键盘的键值  
						var key = event.which;
						// 处理回车的情况  
						if (key == 13) {
							// 获取当当前文本框中的内容  
							var value = $input.val();
							// 将td中的内容修改成获取的value值  
							$td.html(value);
						} else if (key == 27) {
							// 将td中的内容还原  
							$td.html(text);
						}
					});
				}
			}
		});
		
		$('#batch_edit').focus(function() {
			$("#dataList tr td:last-child").each(function(){
				editTd($(this));
			});
		});
		$('#batch_edit').on('keyup',function(e) {
			var keyCode = (e.keyCode ? e.keyCode : e.which);
			var $batch_edit = $(this);
	        if (keyCode != 37&&keyCode != 39) {
	        	if(keyCode==13 || keyCode==27){
		        	// 获取当当前文本框中的内容  
					var value = $batch_edit.val();
		        	if(value){
		        		$batch_edit.val("");
						$("#dataList tr td:last-child").each(function(){
							$(this).html(value);
						});
		        	}
		        }else{
		        	var val = $batch_edit.val().replace(/[^\d]/g,'');
					if($batch_edit.val().length == 1){
						val = val.replace(/[^1-9]/g,"");
					}
					$batch_edit.val(val);
					$("#dataList tr td:last-child input").each(function(){
						$(this).val(val);
					});
		        }
				
	        }
		});
		
		var editTd = function($td){
			// 检测此td是否已经被替换了，如果被替换直接返回  
			if ($td.children("input").length > 0) {
				return false;
			}
			// 获取$td中的文本内容  
			var text = $td.text();
			// 创建替换的input 对象  
			var $input = $("<input type='text'>").css(
					"background-color", $td.css("background-color"))
					.width($td.width());
			// 设置value值  
			$input.val(text);
			// 清除td中的文本内容  
			$td.html("");
			$td.append($input);
		};
		
		$("#addRecord").click(function(){
			$("#batch_edit").val("");
			
			$("#dataList tr td:last-child").each(function(){
				var $td = $(this);
				var index = $td.closest("tr").attr("data-index");
				var val = $td.children("input").val();
				$("#dataList").bootstrapTable('updateCell', {index:index,field:"costPrice",value:val});
			});
			var tollerlist = {list:$('#dataList').bootstrapTable('getData')};
			$.ajax({
				url:"updateCostPrice",
				type:"post",
				dataType : "json",
				data : JSON.stringify(tollerlist),
				contentType : "application/json; charset=utf-8",
				success:function(msg){
					
				}
			});
		});

	});
</script>
</html>
