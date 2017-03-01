<#assign headComponents = ["bootTable", "innerPage"] > <#include
"/header.ftl" />
<link rel="stylesheet"
	href="${uiBase}/css/list.css?v=${resourceVersion}">
<link rel="stylesheet"
	href="${uiBase}/css/pages/p_shipment_receiving.css?v=${resourceVersion}">

</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper" style="overflow: visible">
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper page-content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<ol class="breadcrumb">
					<li><i class="fa fa-dashboard"></i> 库存管理</li>
					<li>发货管理</li>

				</ol>
			</section>



			<!-- Main content -->
			<div class="row pad">
				<div class="col-md-12">
					<div class="box box-primary">
						<div class="form-horizontal search-group">
							<div class="box-body">
								<div class="form-group ">
									<label class="control-label col-sm-2">发货单号:</label> <input
										class="form-control col-sm-2" id="orderId" type="text">
									<label class="control-label col-sm-2">发货数量:</label>
									<div class="col-sm-2 no-padding">
										<input class="form-control " id="totalNoFrom" type="text">
										<span class="time">—</span> <input class="form-control "
											id="totalNoTo" type="text">
									</div>
									<label class="control-label  col-sm-2" for="type-select">状态:</label>
									<div class="col-sm-2 no-padding">
										<select class="form-control selectpicker " id="status">
											<option value="">全部</option> <#list status as type>
											<option value="${type.key}">${type.value}</option> </#list>
										</select>
									</div>

								</div>
								<div class="form-group">
									<label class="control-label col-sm-2">创建时间:</label>
									<div class="col-sm-6 no-padding timegroup">
										<input type="hidden" name="csDateInput" value=""
											id="startDateH">
										<div class="dateDiv" style="margin-bottom: 0px;">
											<input size="10" type="text" name="startDate" id="startDate"
												class="form-control keyword beginDate" placeholder="请选择时间"
												readonly> <span class="add-on" style="display: none"><i
												class="icon-remove"></i></span> <span class="add-on"><i
												class="icon-calendar"></i></span>
										</div>
										<span class="time">至</span> <input type="hidden"
											name="ceDateInput" value="" id="endDateH">
										<div class="dateDiv" style="margin-bottom: 0px;">
											<input size="10" type="text" name="endDate" id="endDate"
												class="form-control keyword endDate" placeholder="请选择时间"
												readonly> <span class="add-on" style="display: none"><i
												class="icon-remove"></i></span> <span class="add-on"><i
												class="icon-calendar"></i></span>
										</div>
									</div>
								</div>
								<div class="form-group ">
									<div class="col-sm-2 control-label">
										<button class="btn btn-primary pull-right" id="search">开始搜索</button>
									</div>
									<div class="btnGroup">
										<button type="button" class="btn btn-default reloadPage">
											<i class="fa  fa-refresh"></i> 刷新
										</button>
										<button class="btn  btn-normal btn-danger" id="shipmentSearch">未发货(${count})</button>
										<button class="btn  btn-primary" id="shipmentBtn">扫码出库</button>
										<button class="btn  btn-primary" id="exportBtn">
											导出出库详情表</button>
										<button class="btn  btn-primary" id="createShipmentOrder">
											创建发货单</button>
									</div>
								</div>
							</div>
						</div>
						<!-- /.box-header -->

						<div class="box-body">
							<table id="dataList" class="table table-bordered table-hover">
								<thead>
									<th><input type="checkbox" /></th>
									<th>发货单</th>
									<th>发货数量(件)</th>
									<th>终端配送商编号</th>
									<th>终端配送商名</th>
									<th>创建时间</th>
									<th>发货状态</th>
									<th>操作</th>
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
	function shipment(orderId) {
		$.ajax({
			url : "shipment",
			type : "post",
			dataType : "json",
			data : orderId,
			contentType : "application/json; charset=utf-8",
			success : function(msg) {
				if (msg.code == "ACK") {
					BootstrapDialog.show({
						title : '订单发货',
						message : msg.message,
						draggable : true,
						buttons : [ {
							label : '关闭',
							action : function(dialog) {
								dialog.close();
								window.location.href = window.location.href;
							}
						} ]
					});
				}
			}
		});
	};
	$(function() {
		
		$('#createShipmentOrder').on("click", function() {
			window.location.href = $.GLOBAL.config.urlPrefix+"pshipment/creatShipment";
		});

		$('#totalNoFrom').inputmask("mask", {
			alias : "decimal",
			"repeat" : 11,
			rightAlignNumerics : false

		});

		$('#totalNoTo').inputmask("mask", {
			alias : "decimal",
			"repeat" : 11,
			rightAlignNumerics : false

		});
		//开始日期
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		var day = date.getDate();

		$('#startDate,#endDate,#usDate,#ueDate').datetimepicker({
			minView : 'month',
			format : 'yyyy-MM-dd',
			language : 'ch',
			endDate : year + '-' + month + '-' + day,
			autoclose : true,
			todayBtn : true
		});
		//创建日期
		$('#startDate')
				.on(
						'changeDate',
						function() {
							$('#endDate').datetimepicker('setStartDate',
									$('#startDate').val());
							if ($('#startDate').val() == ""
									&& $("#startDate").next().css('display') == 'inline-block') {
								$("#startDate").next().css('display', 'none');
							} else {
								$("#startDate").next().css('display',
										'inline-block');
							}
						});

		//结束日期
		$('#endDate')
				.on(
						'changeDate',
						function() {
							if ($('#endDate').val()) {
								$('#startDate').datetimepicker('setEndDate',
										$('#endDate').val());
							} else {
								$('#startDate').datetimepicker('setEndDate',
										year + '-' + month + '-' + day);
							}
							;
							if ($('#endDate').val() == ""
									&& $("#endDate").next().css('display') == 'inline-block') {
								$("#endDate").next().css('display', 'none');
							} else {
								$("#endDate").next().css('display',
										'inline-block');
							}
						});
		$('#shipmentSearch').on("click", function() {
			table.options.queryAddParams = function() {
				return {
					status : '0'
				}
			};
			table.refresh();
		});
		$('#search').on("click", function() {
			table.options.queryAddParams = function() {
				return {
					orderId : $("#orderId").val(),
					totalNoFrom : $("#totalNoFrom").val(),
					totalNoTo : $("#totalNoTo").val(),
					status : $("#status").val(),
					startDate : $("#startDate").val(),
					endDate : $("#endDate").val()
				}
			};
			//table.refresh();
		});
		//区域平台商发货
		$('#shipmentBtn').on(
				'click',
				function() {
					var orderId = "";
					$('#dataList tbody tr').each(function() {
						if ($(this).hasClass('selected')) {
							var text = $(this).find('td').text();
							if (text.indexOf('已创建') != -1) {
								//获取当前的orderId
								var id = $(this).find('.orderId').text();
								orderId += id + ",";
							} else if (text.indexOf('已创建') == -1) {
								orderId = "";
								return;
							}
						}
					});
					if (orderId != null && orderId != "") {
						orderId = orderId.substring(0, orderId.length - 1);
						window.location.href = $.GLOBAL.config.urlPrefix
								+ "barcode/index/3/" + orderId;
					} else{
						BootstrapDialog.show({
							title: "提示",
							type : BootstrapDialog.TYPE_WARNING,
							message: message("选择错误，请选择已创建状态的订单!"),
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
		//导出表格
		$('#exportBtn').on(
				'click',
				function() {
					var orderId = "";
					$('#dataList tbody tr').each(function() {
						if ($(this).hasClass('selected')) {
							var text = $(this).find('td').text();
								//获取当前的orderId
								var id = $(this).find('.orderId').text();
								orderId += id + ",";
						}
					});
						orderId = orderId.substring(0, orderId.length - 1);
						window.location.href = $.GLOBAL.config.urlPrefix
								+ "pshipment/toExcelShipment/?orderId="
								+ orderId;
				});
		var table = $.GLOBAL.utils
				.loadBootTable({
					table : $('#dataList'),
					refreshBtn : $('#search'),
					url : 'pshipment/list',
					sidePagination : 'server',
					pagination : true,
					queryParamsType : "limit",
					queryAddParams : function() {
						return {
							orderId : $("#orderId").val(),
							totalNoFrom : $("#totalNoFrom").val(),
							totalNoTo : $("#totalNoTo").val(),
							status : $("#status").val(),
							startDate : $("#startDate").val(),
							endDate : $("#endDate").val()

						}
					},
					columns : [
							{
								checkbox : true
							},
							{
								field : 'orderId',
								sortable : false,
								class : 'orderId'
							},
							{
								field : 'totalNum',
								sortable : false
							},
							{
								field : 'regNo',
								sortable : false
							},
							{
								field : 'regName',
								sortable : false
							},
							{
								field : 'createDate',
								sortable : false
							},
							{
								field : 'status',
								sortable : false
							},
							{
								align : 'center',
								checkbox : false,
								formatter : function(value, row, index) {
									var html;
									if (row.status == "已创建") {
										html = ' <a  title="【查看】" target="_self" href="detail/'+row.orderId+'" class="editItem"> <i class="fa fa-eye "  style="font-size:20px"></i></a>';
									} else {
										html = ' <a  title="【查看】" target="_self" href="detail/'+row.orderId+'" class="editItem"> <i class="fa fa-eye "  style="font-size:20px"></i></a>';
									}
									return html;
								}
							} ]
				});
	});
</script>
<script
	src="${uiBase}/vendor/input-mask/jquery.inputmask.js?v=${resourceVersion}"></script>
<script
	src="${uiBase}/vendor/input-mask/jquery.inputmask.numeric.extensions.js?v=${resourceVersion}"></script>
</html>