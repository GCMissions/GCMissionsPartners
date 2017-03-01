$(function() {

	var charityApp = {
		bootTable : void 0,
		init : function(){
			this.initDate();
			this.$dataList = $('#dataList');
			this.initTable(); 
	        this.bindEvents();
		},
		bindEvents : function(){
			var that = this;
			
			$("#selectAll").on('click', function(){
				$('input[name=charity]:enabled').prop("checked", this.checked);
				$('input[name=charity]').each(function(){
					
					$(this).on('click', function(){
						 if (!this.checked){
							 $("#selectAll").prop("checked", false);
						 }
						 var allChecked = true;
						 $('input[name=charity]').each(function(){
							 if (!this.checked){
								 allChecked = false;
								 return;
							 }
						 });
						 $("#selectAll").prop("checked", allChecked);
					});
					
				});
			});
			
			$("#search").on('click', function(){
				$("#selectAll").prop("checked", false);
			});

			$("#deleteItem").on("click", function(){
				 var ids = new Array();
				 $("input[name=charity]:checked").each(function() {
					ids.push($(this).val());
				 });
				 if (ids.length == 0) {
					 that.dialog =  BootstrapDialog.show({
				            title: '请选择要删除的公益活动',
				            type : BootstrapDialog.TYPE_WARNING,
				            message: message('已选择0条公益活动'),
				            draggable: true,
				            size : BootstrapDialog.SIZE_SMALL,
				            buttons: [{
				                label: '返回',
				                cssClass: 'btn-primary ',
				                action: function(dialog) {
				                	dialog.close();
				                }
				            }]
				        });
				 } else {
					 that.dialog =  BootstrapDialog.show({
					 title: '删除公益活动',
					 type : BootstrapDialog.TYPE_WARNING,
					 message: "您确定要删除吗？删除之后会自动将关联的广告位删除，请谨慎操作。",
					 draggable: true,
					 size : BootstrapDialog.SIZE_SMALL,
					 buttons: [{
						 label: '确认删除',
						 cssClass: 'btn-primary ',
						 action: function(dialog) {
							 dialog.close();
							 that.doDelete(ids);
						 }
					 }, {
						 label: '取消',
						 action: function(dialog) {
							 dialog.close();
						 }
					 }]
					 });
				 }
			});
		},
		
		initTable : function() {
			this.bootTable = $.GLOBAL.utils.loadBootTable({
	            table : this.$dataList,
	            refreshBtn : $('#search'), 
	            pagination : true,
	            pageSize : 20,
	            url: 'coolbag/charity/search',
	            sidePagination:'server',
	            queryAddParams: function() {
	                var queryObj =  {
	                    name :  $('#charityName').val(),
	                    startDate : $.trim($('#startDate').val()),
	                    endDate : $.trim($('#endDate').val()),       
	                    status  : $.trim($("select[name='status']").val()), 
	                } ;
	                
	                return queryObj;
	            },
	            initSearchForm : true, 
			    fillSearchData : function(data) {
			    	 $("#charityName").val(data.name);
			    	 $("#startDate").val(data.startDate);
			    	 $("#endDate").val(data.endDate);
			    	 $("select[name='status']").val(data.status);
			    },
	            columns: [
                    {
                    	field: 'id',
                    	align: 'center',
                    	width: 50,
                    	formatter:function(value,row,index){  
                    		if ("3" == row.status) {
                    			return '<input type="checkbox" name="charity" value="'+ row.id +'" disabled>';
                    		}
                    		return '<input type="checkbox" name="charity" value="'+ row.id +'">';
                    	}
                    } ,
	                {
	                    field: 'name',
	                    width: 300,
	                    formatter: function(value,row,index){
	                    	var str = value;
	                    	var re = new RegExp(" ","g");
							var newstr = str.replace(re, "&nbsp;");
	                    	return '<span>'+ newstr +'</span>';
	                    }

	                } ,
	                {
	                    field: 'featureName',
	                    width: 300,
	                    formatter: function(value,row,index){
	                    	var str = value;
	                    	var re = new RegExp(" ","g");
							var newstr = str.replace(re, "&nbsp;");
	                    	return '<span>'+ newstr +'</span>';
	                    }

	                } ,
	                {
	                    field: 'involveNum',
	                    align: 'center',
	                    width: 50
	                } ,
	                {
	                    field: 'createDate',
	                    width:160
	                } ,
	                {
	                	field: 'startTime',
	                	width: 300,
	                	formatter: function(value, row, index) {
	                		return value + "&nbsp;至&nbsp;"+ row.endTime;
	                	}
	                } ,
	                {
	                	field: 'status',
	                	width: 80,
	                	align: 'center',
	                	formatter: function(value, row, index) {
	                		if ('2' === row.status) {
	                			return '未开始';
	                		}
	                		if ('3' === row.status) {
	                			return '进行中';
	                		}
	                		if ('4' === row.status) {
	                			return '已结束';
	                		}
	                		if ('5' === row.status) {
	                			return '已失效';
	                		}
	                		return '专辑已下架';
	                	}
	                } ,
	                {
	                    field: 'id',
	                    align: 'center',
	                    width: 90,
	                    formatter: function(value, row, index){
	                    	var prefix = "<a href='" + urlPrefix + "coolbag/charity/page?oper=";
	                    	var result = '<a target="_blank" href="'+urlPrefix+'coolbag/charity/toPreview/' + row.id + '" class="previewItem" data-id="'+row.id+'">预览</a>';
	                    	result += prefix + "view&charityId=" + row.id + "' class='viewItem' data-id='" + row.id +"'> 查看</a>"
	                    	if (row.status === "2" || row.status === "6") {
	                    		result += prefix + "edit&charityId=" + row.id + "' class='editItem' data-id='" + row.id +"'> 编辑</a>"
	                    	}
	                    	if (row.status === "4") {
	                    		result += prefix + "edit&charityId=" + row.id + "' class='editItem' data-id='" + row.id +"'> 反馈</a>"
	                    	}
	                    	return result;
	                    }
	                } 
	             ],
	             onPageChange: function () {
	            	 $("#selectAll").prop("checked",false);
	             }
	        });
		},
		
		doDelete : function(ids){
			 $.ajax({
			     type: "POST",
			     url: urlPrefix+ "coolbag/charity/delete",
			     data: {
			    	 "charityIds":ids
			     },
			     dataType: "json",
			     success: function(result){
					  if (result.code == 'ACK'){
					  	 $('body').loadingInfo("success", result.message);
						 var num =  $('#dataList').bootstrapTable('getOptions').pageNumber,
						 	 size =  $('#dataList').bootstrapTable('getOptions').pageSize,
						     record =  $('#dataList').bootstrapTable('getOptions').totalRows,
						     total =  $('#dataList').bootstrapTable('getOptions').totalPages;
						 if (num != 1 && num == total && record % size == idCount) {
							 num = num-1;
							 $('#dataList').bootstrapTable('refresh', {query: {currentPage:num}});
						 }else{
							 $('#dataList').bootstrapTable('refresh', {query: {currentPage:num}});
						 }
						 $("#selectAll").prop("checked", false);
					  }
			     }
			 });
		},
		
		initDate: function() {
			var curDate = new Date();
			var year = curDate.getFullYear();
			var month = (curDate.getMonth() + 1)  < 10 ? "0" + (curDate.getMonth() + 1) : curDate.getMonth() + 1 ;  
			var date = curDate.getDate() < 10 ? "0" + curDate.getDate() : curDate.getDate();  
			var curDateStr = year + "-" + month + "-" + date;  
			
			// 初始化
			$('#startDate, #endDate').datetimepicker({
				minView: 'month',
				format: 'yyyy-MM-dd',
				language: 'ch',
				autoclose : true,
				todayBtn : true,
			});
			
			// 开始时间
			$('#startDate').on('changeDate', function(ev){
				if (ev.date){
					$('#endDate').datetimepicker('setStartDate', $('#startDate').val());
				} else {
					$("#endDate").datetimepicker('setStartDate', curDateStr);
				}
				if(!ev.date){
					$("#startDate").next().css('display','none');
				}else{
					$("#startDate").next().css('display','inline-block');
				}
			});
			
			// 结束时间
			$('#endDate').on('changeDate', function(ev){
				// 如果先选择了结束日期，则将开始日期的截止日期设为结束日期的值
				if (ev.date) {
					$('#startDate').datetimepicker('setEndDate', $('#endDate').val());
				} else {
					$('#startDate').datetimepicker('setEndDate', '');
				}
				if (!ev.date){
					$("#endDate").next().css('display','none');
				} else {
					$("#endDate").next().css('display','inline-block');
				}
			});
		}
		
	}.init();
})