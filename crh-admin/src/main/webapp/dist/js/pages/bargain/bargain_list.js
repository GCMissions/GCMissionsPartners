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
					url: 'bargain/list',
					sidePagination:'server',
					pageSize : 10,
					pagination : true,
					currentPage: 1,
					queryParamsType: "limit",
					queryAddParams: function() {
						return {
							startDate: $("#startDate").val(),
							endDate: $("#endDate").val(),
							status: ""
						}
					},
					columns: [
						{
							title:"序号",
							width: 50,
							align: 'center',
							formatter:function(value,row,index){  
	                        	return index+1; 
	                        }
						} ,
						{
							title:"砍价商品名称",
							field: 'productName',
							align: 'center'
						} ,
						{
							title:"添加时间",
							field: 'createDate',
							align: 'center'
						} ,
						{
							title:"有效期",
							field: 'effectiveDate',
							align: 'center'
						} ,
						{
							title:"参与人数",
							field: 'playerTotal',
							align: 'center',
						} ,
						{
							title:"原价（元）",
							field: 'price',
							align: 'center'
						} ,
						{
							title:"底价（元）",
							field: 'basePrice',
							align: 'center',
						} ,
						{
							title:"状态",
							field: 'status',
							align: 'center'
						} ,
						{
							title:"操作",
							field: 'id',
							align: 'center',
							formatter:function(value,row,index){
								var html = '';
				                if(row.status == "未开始" ) {
				                	html = '<a  title="修改" class="editItem" data-id="'+row.id+'" href="editPage/'+row.id+'" >' 
									+'<i class="fa fa-edit"  style="font-size:20px;margin-right: 4%;"></i></a>'
									+'<a  title="删除" href="#" class="removeItem" data-id="'+row.id+'"><i class="fa fa-trash"  style="font-size:20px"></i></a>';
				                }else if (row.status == "进行中" ) {
				                	html = '';
				                } else {
				                	html = '<a  title="删除" href="#" class="removeItem" data-id="'+row.id+'"><i class="fa fa-trash"  style="font-size:20px"></i></a>';
				                }
				            	return ' <a  title="查看" couponid='+row.id+' style="margin-right: 9px;" href="viewPage/'+row.id+'" class="datailItem" data-id="'+row.id+'"> <i class="fa fa-eye"  style="font-size:20px"></i></a>'
			                    + html;
				            
							}
						}
					 ]
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
							status: $("#status").val()
						}
					};
					that.bootTable.refresh();
				});
				$("#addBargain").on('click',function(){
					window.location.href = urlPrefix + "bargain/addPage"
				});
				$('#dataList').on("click", "a.removeItem", function() {
					var id = this.getAttribute('data-id');
					that.dialog =  BootstrapDialog.show({
						 title: '删除商品',
						 type : BootstrapDialog.TYPE_WARNING,
						 message: message('admin.dialog.deleteConfirm'),
						 
						 draggable: true,
						 size : BootstrapDialog.SIZE_SMALL,
						 buttons: [{
							 label: '确认删除',
							 cssClass: 'btn-primary ',
							 action: function(dialog) {
								 dialog.close();
								 that.doRemove(that,id);
							 }
						 }, {
							 label: '取消',
							 action: function(dialog) {
								 dialog.close();
							 }
						 }]
						 });
				});
			},
			doRemove : function(that,id){
	        	$.ajax({ 
	    	        	type         : 'GET',
	    				url          : urlPrefix + 'bargain/delete/'+id,
	    				dataType     : 'json',
	    				contentType  : 'application/json',
	    				success : function (result){
	    					if(result.code == "ACK"){
	    	    				$(window).loadingInfo({
	    	    					type : "success", 
	    	    					text: message("admin.message.success"),
	    	    					callBack : function() {
	    	    						that.dialog.close();
	    	    						that.bootTable.refresh(); 
	    	    					}
	    	    				});
	    	    			}
	    				}
	    	    	});
	    }
	};
	
	bargainList.init();
});