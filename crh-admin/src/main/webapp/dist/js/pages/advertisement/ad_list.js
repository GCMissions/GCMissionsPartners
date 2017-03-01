$(function(){
	var adList = {
		init :function(){
			
			this.table = $.GLOBAL.utils.loadBootTable({
				table : $('#dataList'), 
				idField : "adId",
			    url: 'advertisement/list',
			    sidePagination:'server',
			    pagination : true,
			    queryParamsType: "limit",
			    queryAddParams: function() {
			    	var result= {
							local:$("#adLocal").val(),
							type:$("#adType").val(),
				    		title: $("#adName").val(), 
						}
			    	return result;
			    	
			    },
			    
			    columns: [
			        {
						width : 50,
						align: 'center',
						formatter:function(value,row,index){  
							return index+1;
						}
      	            },
			        {
			            field: 'localName',
			            
			        } ,
			        {
			            field:'sort',
			            
			        } ,
			        {
			            field: 'title',
			           
			        } ,
			        {
			            field: 'url',
			           
			        } ,
//			        {
//			            field: 'beginDate',
//			            
//			        } ,
//			        {
//			            field: 'endDate',
//			            
//			        } ,
			        {
			        	field: 'adId',
			            align: 'center',
			            //checkbox: false,
			            formatter:function(value,row,index){  
			               
			            	return '<a  title="修改" href="editPage/'+row.adId+'" class="editItem" data-id="'+row.adId+'"> <i class="fa fa-edit "  style="font-size:20px"></i></a>';
			            } 
			        }
			     ]
			});
			this.bindEvents();
		},
		bindEvents :function(){
			var that=this;
			var date = new Date();
			var year = date.getFullYear();
			var month = date.getMonth()+1;
			var day = date.getDate();

			$('#usDate,#ueDate').datetimepicker({
				minView: 'month',
				format: 'yyyy-mm-dd hh:ii:ss',
				language: 'ch',
//				endDate: year+'-'+month+'-'+day,
				autoclose : true,
				todayBtn : true
			});
			
			//使用日期
			$('#usDate').on('changeDate',function(){
				$('#ueDate').datetimepicker('setStartDate', $('#usDate').val());
				if($('#usDate').val()=="" && $("#usDate").next().css('display') == 'inline-block'){
					$("#usDate").next().css('display','none');
				}else{
					$("#usDate").next().css('display','inline-block');
				}
			});

			//结束日期
			$('#ueDate').on('changeDate',function(){
				if ($('#ueDate').val()) {
					$('#usDate').datetimepicker('setEndDate', $('#ueDate').val());
				}else{
					$('#usDate').datetimepicker('setEndDate', year+'-'+month+'-'+day);
				};
				if($('#ueDate').val()=="" && $("#ueDate").next().css('display') == 'inline-block'){
					$("#ueDate").next().css('display','none');
				}else{
					$("#ueDate").next().css('display','inline-block');
				}
			});
			
			
			$('#search').on("click", function() {
				that.table.options.queryAddParams = function(){
					var result= {
						local:$("#adLocal").val(),
						type:$("#adType").val(),
			    		title: $("#adName").val(), 
			    		startDate: $("#usDate").val(), 
			    		endDate: $("#ueDate").val()
					}
					if($("#usDate").val()==""){delete result.effectDate}
					if($("#ueDate").val()==""){delete result.invalidDate}
					return result;
				};
				that.table.refresh();
			});
			
			
			$('#dataList').on("click", "a.removeItem", function() {
				var adId = this.getAttribute('data-id');
//				if(isConfirm){
//					$.ajax({
//						type: "GET",
//						dateType: "json",
//						contentType: "application/json;charset=utf-8",
//						url:"delete/"+adId
//					})
//					.done(function(response) {
//						if(response.indexOf('ACK') > 0){
//							table.refresh();
//						}else{
//							
//						}
//					})
//					.fail(function(result) {
//						window.confirm("删除出错");
//					});
//				}
				that.dialog=BootstrapDialog.show({
			        title: '删除广告',
			        message: ("确定要删除此广告吗？"),
			        draggable: true,
			        buttons: [{
			            label: '确定',
			            cssClass: 'btn-primary',
			            action: function(dialog) {
			            	$.ajax({
			            		type: "GET",
								dataType: "json",
								contentType: "application/json;charset=utf-8",
								url:"delete/"+adId})
								.done(function(response){
									if(response.code="ACK"){
										that.table.refresh();
										that.dialog.close();
										$('body').loadingInfo({ text : response.message});
									}
								})
								.fail(function(result){
									alert('ajax error');
								});
//								success:function(result){
//									that.bootTable.refresh();
//									that.dialog.close();
//								},
//				                error: function(xhr,type) {
//									alert('Ajax error!');
//								}
			            }
			        }, {
			            label: '取消',
			            action: function(dialog) {
			                that.dialog.close();
			            }
			        }]
				 });
			});
		},
	}
	adList.init();	
});