$(function() { 
	$('#dataList').on("click", ".viewItem", viewItem);
	$.GLOBAL.utils.loadBootTable({
		table : $('#dataList'),
		removeBtn : $('#removeRecord'),
		refreshBtn : $('#refreshRecord'),
		idField : "productId",
		pagination : true,
		url : 'rebate/list',
		sidePagination : 'server',
		queryAddParams : function() {
			//return $.GLOBAL.utils.getSearchData("#search-area");
			return {
				productName: $.trim($('#productName').val()),
                productCode: $.trim($('#productCode').val()),
                cateId     : $('#categoryId').val(),
                brandId    : $('#brandId').val(),
			};
		},
		columns : [ 
			{
				align : 'center',
				formatter : function(value, row, index) {
					return index + 1;
				}
			},
			{
				field : 'productCode',
			},
			{
				field : 'productName',
			},
			{
				field : 'cateName',
			},
			{
				field : 'brandName',
			},
			{
				field : 'productId',
				align : 'center',
				checkbox : false,
				formatter : function(value, row, index) {

					return ' <a  title="查看" href="#" class="viewItem" data-id="'
							+ value
							+ '"> <i class="fa fa-eye"  style="font-size:20px"></i></a>';

				}
			} 
		]  
	});
	function viewItem(e) {
		e.preventDefault(); 
		var value = $(e.target).parent().data('id');
		
		$.ajax({
			url : 'detail/' + value,
			type : 'get',
			dataType : 'json',
			success : function(data) {
				if (data.code == 'ACK') {
					var dataInfo = {
						content : data.data,
						productId : value
					};
					BootstrapDialog.show({
						size : BootstrapDialog.SIZE_WIDE,
						title : '查看返利详情',
						message : $(template('viewTpl', {})),
						draggable : true,
						data : dataInfo,
						onshown : function(dialogRef) {
							var content = dialogRef.getData("content");
							var productId = dialogRef.getData("productId");
							$("#product").val(productId);
							var rows = "";
							for (var i = 0; i < content.length; i++) {
								rows += "<tr data-tt-id='"
									+ content[i].regionId
									+ "' >"
									+ "<td>"
									+ content[i].regionName
									+ "</td><td></td><td></td></tr>";
								for (var j = 0; j < content[i].detail.length; j++) {
									rows += "<tr data-tt-id='"
										+ content[i].detail[j].cityId
										+ "' data-tt-parent-id='"
										+ content[i].regionId
										+ "' data-cityId='"
										+ content[i].detail[j].cityId
										+ "' data-cityName='"
										+ content[i].detail[j].cityName
										+ "'>"
										+ "<td>"
										+ content[i].detail[j].cityName
										+ "</td>"
										+ "<td><input type='number'  min='0' max='100' step='1' name='pRebate' class='form-control rebate-input' value='"
										+ content[i].detail[j].pRebate
										+ "'>&nbsp;%</td>"
										+ "<td><input type='number'  min='0' max='100' step='1' name='zRebate' class='form-control rebate-input' value='"
										+ content[i].detail[j].zRebate
										+ "'>&nbsp;%</td>"
										+ "</tr>";
								}
							}
							$("#tree_body").append(rows);
							$("#tree_table").treetable({expandable : true});
							//$("#tree_table").treetable("expandAll");
							eventInit();
							
						},
						onhide : function(dialogRef) {
							$("#refreshRecord").click();
						},
						buttons : [
								{
									label : '保存',
									cssClass : 'btn-primary',
									action : function(
											dialog) {
										var _btn = $(this);
										_btn.prop('disabled', true).text('保存中....');
										var rebateList = [];
										var flag = true;
										$("#tree_body").find("tr.leaf").each(function() {
															var rebate = new Object();
															rebate.productId = $(
																	"#product")
																	.val();
															rebate.cityId = $(
																	this)
																	.attr(
																			"data-cityId");
															var pRebate = $(
																	this)
																	.find(
																			"input[name='pRebate']")
																	.val();
															var zRebate = $(
																	this)
																	.find(
																			"input[name='zRebate']")
																	.val();
															var totalRebate = parseFloat(pRebate)
																	+ parseFloat(zRebate);
															if (totalRebate > 100) {
																$(
																		this)
																		.find(
																				"input[name='pRebate']")
																		.addClass(
																				"input-tip");
																$(
																		this)
																		.find(
																				"input[name='zRebate']")
																		.addClass(
																				"input-tip");
																flag = false;
																$('body').loadingInfo({
																	type : "error",
																	text : "返利总和不能大于100!"
																});
															}
															rebate.pRebate = pRebate;
															rebate.zRebate = zRebate;
															rebateList
																	.push(rebate);
														});
										if (!flag) {
											_btn.prop('disabled', false).text('保存');
											return;
										}
										$.ajax({
											url : 'save',
											type : 'post',
											dataType : 'json',
											contentType : "application/json",
											data : JSON
													.stringify({
														detail : rebateList
													}),
											success : function(
													msg) {
												if (msg.code == 'ACK') {
													_btn.prop('disabled', true).text('保存成功');
													dialog
															.close();
													$('body').loadingInfo({
														type : "success",
														text : msg.message
													});
												}
											}
										}).always(function() {
											_btn.prop('disabled', false).text('保存');
										 });
									}
								},
								{
									label : '取消',
									action : function(
											dialog) {
										dialog
												.close();
									}
								} ]
					});
				}
			}

		});

		
	}
	$.ajax({
				url : "cityDetail",
				type : "get",
				dataType : "json",
				contentType : "application/json",
				success : function(msg) {
					if (msg.code == "ACK") {
						var content = msg.data;
						var rows="";
						for (var i = 0; i < content.length; i++) {
							rows += "<tr data-tt-id='" + content[i].regionId
									+ "' >" + "<td>"
									+ content[i].regionName
									+ "</td><td></td><td></td></tr>";
							for (var j = 0; j < content[i].detail.length; j++) {
								rows += "<tr data-tt-id='"
										+ content[i].detail[j].cityId
										+ "' data-tt-parent-id='"
										+ content[i].regionId
										+ "' data-cityId='"
										+ content[i].detail[j].cityId
										+ "' data-cityName='"
										+ content[i].detail[j].cityName
										+ "'>"
										+ "<td>"
										+ content[i].detail[j].cityName
										+ "</td>"
										+ "<td><input type='number'  min='0' max='100' step='1' name='pRebate' class='form-control rebate-input' value='"
										+ content[i].detail[j].pRebate
										+ "'>&nbsp;%</td>"
										+ "<td><input type='number'  min='0' max='100' step='1' name='zRebate' class='form-control rebate-input' value='"
										+ content[i].detail[j].zRebate
										+ "'>&nbsp;%</td>" + "</tr>";
							}

						}
						$("#tree_body2").append(rows);
						$("#tree_table2").treetable({expandable : true});
						//$("#tree_table2").treetable("expandAll"); 
						eventInit();
					}
				}
			});
	$.ajax({
		url : "refereeRebate",
		type : "get",
		dataType : "json",
		contentType : "application/json",
		success : function(msg) {
			if (msg.code == "ACK") {
				var refereeRebate = msg.data;
				$("#refereeRebate").val(refereeRebate);
			}
		},
		error:function(){
			
		}
	});

	function eventInit() {
		// 输入框只支持数字，且小数位只有两位
		$(".tree_table").on('keyup', 'input', function(e) {
			var keyCode = (e.keyCode ? e.keyCode : e.which);
			if (keyCode != 37 && keyCode != 39 && keyCode != 8) {
				var val = $(this).val().replace(/[^\d.]/g, '');
				var decimal = val.substring(val.indexOf('.'), val.length);
				if ($(this).val().length == 1) {
					val = val.replace(/[^0-9]/g, "");
				} else if (val.length > 5) {
					val = val.substring(0, 5);
				} else if (decimal.length > 2) {
					if (val.indexOf('.') != -1) {
						val = val.substring(0, val.indexOf('.') + 3);
					} else if(val >=100){
						val =100;
					}else{
						val = val.substring(0, 2);
					}
				}
				$(this).val(val);
			}
		});
		
		$(".tree_body").on("blur","input",function() {
			if ($(this).val == 0) {
				$(this).addClass("input-tip");
			}
		});
		// 删除红色错误提示
		$(".tree_body").on("focus","input",function() {
			$(this).removeClass("input-tip");
			$(this).parent('td').siblings('td').find('input').removeClass("input-tip");
		});
	}
	//保存城市配送返点值
	$(".submitCityRebate").click(function(){
		var _btn = $(this);
		_btn.prop('disabled', true).text('保存中....');
		var rebateList=[];
		var flag = true;
		$("#tree_body2").find("tr.leaf").each(function() {
			var rebate = new Object();
			rebate.cityId = $(this).attr("data-cityId");
			var pRebate = $(this).find("input[name='pRebate']").val();
			var zRebate = $(this).find("input[name='zRebate']").val();
			var totalRebate = parseFloat(pRebate)+ parseFloat(zRebate);
			if (totalRebate > 100) {
				$(this).find("input[name='pRebate']").addClass("input-tip");
				$(this).find("input[name='zRebate']").addClass("input-tip");
				flag=false;
				$('body').loadingInfo({
					type : "error",
					text : "返利总和不能大于100!"
				});
			}
			rebate.pRebate = pRebate;
			rebate.zRebate = zRebate;
			rebateList.push(rebate);
		});
		if (!flag) {
			_btn.prop('disabled', false).text('保存');
			return;
		}
		$.ajax({
			url : 'saveFreightRebate',
			type : 'post',
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify({detail : rebateList}),
			success : function(msg) {
				if (msg.code == 'ACK') {
					_btn.prop('disabled', true).text('保存成功');
					$('body').loadingInfo({
						type : "success",
						text : msg.message
					});
				}
			}
		}).always(function() {
			_btn.prop('disabled', false).text('保存');
		 });
	});
	
	//保存推广返点值
	$(".submitPromoteRebate").click(function(){
		var _btn = $(this);
		_btn.prop('disabled', true).text('保存中....');
		var flag = true;
		if (!flag) {
			_btn.prop('disabled', false).text('保存');
			return;
		}
		var refereeRebate = $("#refereeRebate").val();
		$.ajax({
			url : 'saveRefereeRebate',
			type : 'post',
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify({refereeRebate : refereeRebate}),
			success : function(msg) {
				if (msg.code == 'ACK') {
					_btn.prop('disabled', true).text('保存成功');
					$('body').loadingInfo({
						type : "success",
						text : msg.message
					});
				}
			}
		}).always(function() {
			_btn.prop('disabled', false).text('保存');
		 });
	});
	
	
	
	
	var rebateApp = {
		loadCategoryTree : function() {
			var setting = {
				async: {
					autoParam : ["pId"],
					type      : "post",
					dataType  : "json",
					dataFilter: this.categoryTreeAjaxDataFilter,
					enable    : true,
					url       : this.getCategoryTreeUrl,
				},
				view: {
					dblClickExpand: false
				},
				data: {
					key: {
						id   : "cateId",
						name : "cateName",
						pId  : "parentId"
					},
					simpleData: {
						enable: true,
						idKey : "id",
						pIdKey: "pId",
						rootPId : 0
						//checked: true
					}
				}
			};

			
		   // $.fn.zTree.init($("#cateZtree"), setting);
			
			var $parentId = $("#categoryId");
			var $categoryTree = $("#cateZtree");
			var $categoryName = $("#cateId");
			
			
			var options = {
				url: urlPrefix + "category/list",
				$idInput: $parentId,
				$nameInput: $categoryName,
				$ztree: $categoryTree,
				setting: 
				{
					async: {
						autoParam : ["pId"],
						type      : "post",
						dataType  : "json",
						dataFilter: this.categoryTreeAjaxDataFilter,
						enable    : true,
						url       : this.getCategoryTreeUrl,
					},
					data: {
						key: {
							id   : "cateId",
							name : "cateName",
							pId  : "parentId"
						},
						simpleData: {
							enable: true,
							idKey : "id",
							pIdKey: "pId",
							rootPId : 0
							//checked: true
						}
					}
				}
			};
			
			$.dropDownMenu(options);        	
		},
		categoryTreeAjaxDataFilter : function (treeId, parentNode, responseData) {
			if (!responseData.data) return null;
			var isRootCates = true;
			for( var i =0,l=responseData.data.length;i<l;i++){
				responseData.data[i].isParent=true;
				if(responseData.data[i].parentId) {
					isRootCates = false;
				}
				if(responseData.data[i].hasSon) {
					responseData.data[i].isParent=true;
				} else {
					responseData.data[i].isParent=false;
				}
			}
			if(isRootCates && responseData.data.length > 0) {
				var rootCate = {cateId:0,cateName : "不限", hasSon:false, isParent:true, parentId:0, isNoLimitCate : 1};
				responseData.data.splice(0, 0, rootCate);
			}
			return responseData.data;
		},
		/*
		 * "category/list/0" will get all the data
		 * */
		getCategoryTreeUrl :  function (treeId,treeNode){
			if(treeNode && treeNode.isNoLimitCate != void 0) return false;
			if(treeNode){
				return urlPrefix + "category/list/"+treeNode.cateId;
			}
			else{
				return urlPrefix + "category/list/0";
			}
		}
	};
	rebateApp.loadCategoryTree();
});