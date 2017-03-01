$(function(){
	var bargainList = {
			$dataList : $('#dataList'),
	        $searchItem : $('#searchBtn'),
	        bootTable : void 0,
	        init : function() {
	        	this.bindEvents();
				this.bootTable = $.GLOBAL.utils.loadBootTable({
					table : this.$dataList,
					removeBtn : $('#removeRecord'),
					refreshBtn : $('#refreshRecord'),
					idField : "",
					url: 'kdBargain/list',
					sidePagination:'server',
					pageSize : 10,
					pagination : true,
					currentPage: 1,
					queryParamsType: "limit",
					queryAddParams: function() {
						return {
							status: $("#status").val(),
							productName:$("#productName").val(),
							productCode:$("#productCode").val(),
							name:$("#name").val(),
							startDate: $("#startDate").val(),
							endDate: $("#endDate").val()
						}
					},
					initSearchForm : true, 
				    fillSearchData : function(data) {
				    	
				    	 $("#startDate").val(data.startDate);
				    	 $("#endDate").val(data.endDate);
				    	 $("#status").val(data.status);
				    	 $("#productName").val(data.productName);
				    	 $("#productCode ").val(data.productCode);
				    	 $("#name ").val(data.name);
				    	
				    },
					columns: [
						{
	                    	field: 'id',
	                    	align: 'center',
	                    	formatter:function(value,row,index){  
	                    		if(row.status != "进行中") {
	                    			return '<input type="checkbox" name="bargain" value="'+row.id+'">';
	    						}else{
	    							return '<input type="checkbox" disabled="true" name="bargain" value="'+row.id+'">';
	    						}
	                    	}
	                    } ,
						{
							field: 'name',
							align: 'center'
						} ,
						{
							field: 'productCode',
							align: 'center'
						} ,
						{
							field: 'productName',
							align: 'center'
						} ,
						{
							field: 'createDate',
							align: 'center'
						} ,
						{
							align: 'center',
							formatter:function(value,row,index){  
	                        	return row.effectiveStartDate + "至" + row.effectiveEndDate; 
	                        }
						} ,
						{
							field: 'status',
							align: 'center'
						} ,
						{
							field: 'id',
							align: 'center',
							formatter:function(value,row,index){
								var html = '';
				                if(row.status == "未开始" || row.status == "商品已下架") {
				                	html = '<a class="editItem" data-id="'+row.id+'" href="editPage/'+row.id+'" >编辑</a>';
				                }
				            	return '<a target="_blank" href="'+urlPrefix+'kdBargain/toPreview/' + row.id + '" class="previewItem" data-id="'+row.id+'">预览</a> <a couponid='+row.id+' style="margin-right: 9px;" href="viewPage/'+row.id+'" class="datailItem" data-id="'+row.id+'">查看</a>'
			                    + html;
				            
							}
						}
					 ],onLoadSuccess: function () {
						 	$("#seletAll").prop("checked",false);
						},
				});
			},
			bindEvents : function() {
				var that = this;
				//开始日期
		    	var date = new Date();
		    	var year = date.getFullYear();
		    	var month = date.getMonth()+1;
		    	var day = date.getDate();
		    	
		    	$('#startDate,#endDate').datetimepicker({
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
				that.$searchItem.on("click",function() {
					that.bootTable.options.queryAddParams = function(){
						return {
							startDate: $("#startDate").val(),
							endDate: $("#endDate").val(),
							status: $("#status").val(),
							productName:$("#productName").val(),
							productCode:$("#productCode").val(),
							name:$("#name").val()
						}
					};
					that.bootTable.refresh();
				});
				$("#addBargain").on('click',function(){
					window.location.href = urlPrefix + "kdBargain/addPage"
				});
				
				$("#dataList").on('click','#seletAll',function(){
					totalcheck();
				});
				
				var totalcheck = function(){
					var box = $("input[name='bargain']");
					if ($("#seletAll").is(':checked') == true) {
						for(var i = 0; i < box.length; i++) {
							if(!box[i].disabled)
							box[i].checked = true;
						}
					} else {
						for(var i = 0; i < box.length; i++) {
							if(!box[i].disabled)
							box[i].checked = false;
						}
					}
				};
				
				$("#deleteItem").on("click",function(){
					
					var ids = "";
					 var idCount= 0 ;
					 $("input[name=bargain]:checked").each(function(){
					  ids += $(this).val()+";";
					  
					  idCount++;
					 });
					 ids = ids.substr(0,ids.length-1);
					 if(idCount == 0){
						 that.dialog =  BootstrapDialog.show({
					            title: '请选择要删除的活动',
					            type : BootstrapDialog.TYPE_WARNING,
					            message: message('已选择'+idCount+'个活动'),
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
					 }else{
						 that.dialog =  BootstrapDialog.show({
						 title: '删除活动',
						 type : BootstrapDialog.TYPE_WARNING,
						 message: "您确定要删除吗？删除之后会自动将关联的广告位删除，请谨慎操作。",
						 
						 draggable: true,
						 size : BootstrapDialog.SIZE_SMALL,
						 buttons: [{
							 label: '确认删除',
							 cssClass: 'btn-primary ',
							 action: function(dialog) {
								 dialog.close();
								 that.doDelete(that,ids,idCount);
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
			doDelete : function(that,ids,idCount){
				 $.ajax({
				     type: "POST",
				     url: urlPrefix+ "kdBargain/delete",
				     data:{"ids":ids},
				     dataType:"json",
//				     contentType  : 'application/json',
				     success: function(result){
						  if(result.code == 'ACK'){
							  $('body').loadingInfo("success", result.message);
									var num =  $('#dataList').bootstrapTable('getOptions').pageNumber;
									var size =  $('#dataList').bootstrapTable('getOptions').pageSize;
									var record =  $('#dataList').bootstrapTable('getOptions').totalRows;
									var total =  $('#dataList').bootstrapTable('getOptions').totalPages
									if(num != 1 && num == total && record%size == idCount){
										num = num-1;
										 $('#dataList').bootstrapTable('refresh', {query: {currentPage:num}});
									}else{
										 $('#dataList').bootstrapTable('refresh', {query: {currentPage:num}});
									}
									$("#seletAll").prop("checked",false);
						}
				     }
				 })
			}
	};
	
	bargainList.init();
});