$(function(){
	var supplierList = {
			initEvents:function(){
				var _self = this;
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					refreshBtn : $('#refreshRecord'),
					idField : "orgId",
				    url: 'supplier/list',
				    sidePagination:'server',
				    pagination : true,
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	var orgCode =$("#orgCode").val();
				    	var orgName = $("#orgName").val();
				    	var result= {
								orgCode:$.trim($("#orgCode").val()),
					    		orgName: $.trim($("#orgName").val()), 
					    		registrationLicense: $.trim($("#registrationLicense").val()), 
					    		businesser: $.trim($("#businesser").val()),
					    		contact:$.trim($("#contact").val()),
					    		phone:$.trim($("#phone").val()),
						}
				    	return result;
				    },
				    initSearchForm : true, 
				    fillSearchData : function(data) {
				    	$("#orgCode").val(data.orgCode);
				    	$("#orgName").val(data.orgName);
				    	$("#registrationLicense").val(data.registrationLicense);
				    	$("#businesser").val(data.businesser);
				    	$("#contact").val(data.contact);
				    	$("#phone").val(data.phone);
				    },
				     
				    columns: [
				        {
				            field: 'orgCode',
				            checkbox: false,
				            width:50,
							align: 'center'
				        } ,
				        {
				            field: 'orgName',
				            width:50,
							align: 'center'
				        } ,
				        {
				            field: 'businesser',
				            width:50,
							align: 'center'
				        } ,
				        {
				            field: 'registrationLicense',
				            width:50,
							align: 'center'
				        } ,
				        {
				            field: 'contact',
				            width:50,
							align: 'center'
				        } ,
				        {
				            field: 'createDate',
				            width:50,
							align: 'center'
				        } ,
				        {
				        	field: 'phone',
				        	width:50,
							align: 'center'
				        },
				        {
				        	field: 'servicePhone',
				        	width:50,
							align: 'center'
				        },
				        {
				        	field: 'introduce',
				        	width:50,
							align: 'center',
							formatter : function(value, row, index) {
								var result = "";
								if (value != null) {
									var context = value;
									if (value.length > 4) {
										context = value.substring(0,4) + "...";
									}
									var span = "<p style='color:#3c8dbc;' ";
				  	        		span += "title='"+value+"'>"+context+"</p>";
				  	        	
				  	        		result += span;
								}
								return result;
							}
				        },{
							field: 'orgCode',
							align: 'center',
							width:100,
							checkbox: false, 
							title : '操作',
							formatter:function(value,row,index){  
								var handleField;
								handleField = '<a  title="编辑" class="editItem" href="editor/'+row.orgCode+'" data-id="'+value+'">' 
								+'<i class="fa fa-edit"  style="font-size:20px;margin-right: 10%;"></i></a>'
								+'<a  title="删除" class="removeItem" data-id="'+value+'">'
								+'<i class="fa fa-trash"  style="font-size:20px"></i></a>';
								return handleField;
							} 
						}
				     ]
				});
				/*
				$("#dataList").on('click','.editItem',function(){
					var orgCode = $(this).data("id");
					window.location.href = "editor/" + orgCode;
				});
				*/
				$("#dataList").on('click','.removeItem',function(){
					var orgCode = $(this).data("id");
					this.dialog =  BootstrapDialog.show({
						 title: '删除商家',
						 type : BootstrapDialog.TYPE_WARNING,
						 message: message('admin.dialog.deleteConfirm'),
						 draggable: true,
						 size : BootstrapDialog.SIZE_SMALL,
						 buttons: [{
							 label: '确认删除',
							 cssClass: 'btn-primary ',
							 action: function(dialog) {
								 dialog.close();
								 deleteOrg(orgCode);
							 }
						 }, {
							 label: '取消',
							 action: function(dialog) {
								 dialog.close();
							 }
						 }]
					});
				});
				
				var deleteOrg = function(orgCode){
					$.ajax({
			    		type         : 'POST',
						url          : urlPrefix + "supplier/delete",
						dataType     : 'json',
						data 	     : {
							"orgCode" : orgCode
						},
						success : function(msg){
							if (msg.code == "ACK") {
								$(window).loadingInfo("success", msg.message);
								freshTable();
							} else {
								$(window).loadingInfo("warn", msg.message);
							}
						}
			    	});
				};
				
				var freshTable = function(){
					var num =  $('#dataList').bootstrapTable('getOptions').pageNumber;
					var size =  $('#dataList').bootstrapTable('getOptions').pageSize;
					var record =  $('#dataList').bootstrapTable('getOptions').totalRows;
					var total =  $('#dataList').bootstrapTable('getOptions').totalPages
					if(num == total && (record - 1)%size == 0){
						num = num-1;
						 $('#dataList').bootstrapTable('refresh', {query: {currentPage:num}});
					}else{
						 $('#dataList').bootstrapTable('refresh', {query: {currentPage:num}});
					}; 
				};
				
			},
			
			init:function(){
				var _self = this;
				_self.initEvents();
			}
	
	}.init();	
});