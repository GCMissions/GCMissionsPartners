$(function() {
	/*
	 * $('#warnStock').on("click", function() { table.options.queryAddParams =
	 * function(){ return { orgType:'Z', isWarning:'T' } }; table.refresh(); });
	 */
	this.validator = $('#stockForm').validate({
        	rules : {
        		orgPct : {
        			digits  : true,
        			min : 0,
        			max : 100
        		}
        	}, 
        	messages : {
        		orgPct : {
        			required : "只允许输入0-100的整数"
        		}
        	}
        });
	
	var excelReport = function(){
		/*if(!$("#orgPct").val()){
			$('body').loadingInfo("error","请先输入库存量百分比!");
			return;
		}*/
		if($("#orgPct").hasClass("fieldError")){
			return;
		}
		var title = "确定导出商家商品库存信息表？";
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
					/*table.refresh();*/
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
	
	var init = function(){
		$('input[name="orgPct"]').inputmask({
    		"mask" : "9",
    		repeat : 3,
    		"greedy": false
    	});
	}

	init();
	
	$('#excel').on('click',function(){
		excelReport();
	});
	
	$('#search').on("click", function() {
		table.options.queryAddParams = function() {
			var queryObj = {
					orgCode : $("#orgCode").val(),
					orgName : $("#orgName").val(),
					orgCate : $("#orgCate").val(),
					orgState : $("#orgState").val(),
			};
			if(queryObj.orgCate == 'Z'){
				queryObj.orgP = $('#orgP').val();
			}
			return queryObj;
		};
		table.refresh();
	});
	$('#orgCate').on('change', function() {
		console.log(this.value);
		if (this.value == 'Z') {
			$('#orgP').removeAttr("disabled");
			$('.btn.dropdown-toggle.disabled.btn-default').removeClass("disabled");
		} else {
			$('#orgP').attr("disabled", "disabled");
			$('.btn.dropdown-toggle.btn-default').eq(2).addClass("disabled");
		}
	});
	var table = $.GLOBAL.utils
			.loadBootTable({
				table : $('#dataList'),
				url : 'orgStock/list',
				sidePagination : 'server',
				pagination : true,
				queryParamsType : "limit",
				queryAddParams : function() {
					var queryObj = {
							orgCode : $("#orgCode").val(),
							orgName : $("#orgName").val(),
							orgCate : $("#orgCate").val(),
							orgState : $("#orgState").val(),
					};
					if(queryObj.orgCate == 'Z'){
						queryObj.orgP = $('#orgP').val();
					}
					return queryObj;
				},
				columns : [
						{
							width : 50,
							align : 'center',
							formatter : function(value, row, index) {
								return index + 1;
							}
						},
						{
							field : 'orgCode'
						},
						{
							field : 'orgName'
						},
						{
							field : 'orgCate'
						},
						{
							field : 'stockNum'
						},
						{
							field : 'standardNum'
						},
						{
							field : 'orgState'
						},
						{
							align : 'center',
							checkbox : false,
							formatter : function(value, row, index) {
								return ' <a  title="【查看】" target="_self" href="detail/'
										+ row.orgId
										+ '" class="editItem"> <i class="fa fa-eye" style="font-size:20px"></i></a>';
							}
						} ]
			});
});
