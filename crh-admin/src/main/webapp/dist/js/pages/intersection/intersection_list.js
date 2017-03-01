$(function(){
	var intersection = {
			initEvents:function(){
				var date = new Date();
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				var day = date.getDate();
				var globalIds = [];
				
				$('#csDate,#ceDate').datetimepicker({
					minView: 'month',
					format: 'yyyy-MM-dd',
					language: 'ch',
					endDate: year+'-'+month+'-'+day,
					autoclose : true,
					todayBtn : true
				});
				
				$('#ceDate')
				.datetimepicker()
				.on('changeDate', function(ev){
					if (ev.date != null) {
						var endYear = ev.date.getFullYear();
						var endMonth = ev.date.getMonth()+1;
						var endDay = ev.date.getDate();
						$('#csDate').datetimepicker('setEndDate', endYear+'-'+endMonth+'-'+endDay);
					} else {
						$('#csDate').datetimepicker('setEndDate', year+'-'+month+'-'+day);
					}
				});
				
				$('#csDate')
				.datetimepicker()
				.on('changeDate', function(ev){
					if (ev.date != null) {
						var startYear = ev.date.getFullYear();
						var startMonth = ev.date.getMonth()+1;
						var startDay = ev.date.getDate();
						$('#ceDate').datetimepicker('setStartDate', startYear+'-'+startMonth+'-'+startDay);
					} else {
						$('#ceDate').datetimepicker('setStartDate', null);
					}
				});
				
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					refreshBtn : $('#refreshRecord'),
				    sidePagination:'server',
				    pagination : true,
				    url : "intersection/list",
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	var startDate = $("#csDate").val();
				    	var endDate = $("#ceDate").val();
				    	var name = $.trim($("#intersectionName").val());
				    	var result= {
				    			startDate:startDate,
				    			endDate: endDate, 
				    			name: name
						}
				    	return result;
				    },
				    initSearchForm : true, 
				    fillSearchData : function(data) {
				    	 $("#csDate").val(data.startDate);
				    	 $("#ceDate").val(data.endDate);
				    	 $("#intersectionName").val(data.name);
				    	
				    },
				    columns: [{
                		field : 'id',
                		align: 'center',
                		title : "<input type='checkbox' id='totalCheck'>",
                		width : '5%',
                		formatter:function(value,row,index){
                			$("#totalCheck").prop("checked",true);
                			if (globalIds.indexOf(value) > -1) {
                				return "<input value='" + value + "' name='btCheckItem' type='checkbox' class='intersection-check' checked='checked'>"
                			} else {
                				$("#totalCheck").prop("checked",false);
                				return "<input value='" + value + "' name='btCheckItem' type='checkbox' class='intersection-check'>"
                			}

                		}
					},{
				    	title: '序号',
				    	field : 'index',
				    	width:"5%",
				    	align: 'center'
				    },{
						title: '合集名称',
						field : 'name',
					    width:"40%",
					    align: 'center'
				    },
			        {
				    	title: '添加时间',
						field : 'createDate',
						width:"30%",
						align: 'center'
			        },
					{
					    title: '操作',
						field : 'id',
						width:"25%",
						align:'center',
						formatter:function(value,row,index){
							var result = "";
							result += "<div><a href='" + urlPrefix + "intersection/editor/" + value + "/1' style='margin-right:5px;'>查看</a>" +
									"<a href='" + urlPrefix + "intersection/editor/" + value + "/2' >编辑</a></div>";
							result += "<div><a href='javascript:void(0);' class='skip' data-id='" + value + "' style='margin-right:5px;'>链接</a>" +
									"<a href='" + urlPrefix + "intersection/toPreview/" + value + "' target='_blank' >预览</a></div>";
							return result;
						}
			        }]
				});
				
				$("#dataList").on('click','#totalCheck',function(){
					totalcheck();
				});
				
				$("#dataList").on('click','.skip',function(){
					var content = "advertise/intersection/" + $(this).data("id");
					var obj = document.createElement("input");
					obj.setAttribute("value", content);
					document.body.appendChild(obj);
					obj.select();
					document.execCommand("copy");
					document.body.removeChild(obj);
					$(window).loadingInfo("success", "链接地址已经复制到剪贴板");
				});
	
				$("#dataList").on('click','.intersection-check',function(){
					if ($(this).is(':checked') == true) {
						globalIds.push($(this).val());
					} else {
						$("#totalCheck").checked = false;
						var index = globalIds.indexOf($(this).val());
						if (index > -1) {
							globalIds.splice(index, 1);
						}
					}
					$("#ids").val(globalIds);
				});
	
				var totalcheck = function(){
					var box = $('input[name=btCheckItem]');
					if ($("#totalCheck").is(':checked') == true) {
						for(var i = 0; i < box.length; i++) {
							if (box[i].checked == false) {
								globalIds.push(box[i].value);
							}
							box[i].checked = true;
						}
					} else {
						for(var i = 0; i < box.length; i++) {
							if (box[i].checked == true) {
								var index = globalIds.indexOf(box[i].value);
								if (index > -1) {
									globalIds.splice(index, 1);
								}
							}
							box[i].checked = false;
						}
					}
					$("#ids").val(globalIds);
				};
				
				$("#del_btn").click(function(){
					var count = globalIds.length;
					if(count == 0){
						 this.dialog =  BootstrapDialog.show({
					            title: '请选择要删除的合集',
					            type : BootstrapDialog.TYPE_WARNING,
					            message: message('已选择'+count+'条合集'),
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
						 this.dialog =  BootstrapDialog.show({
						 title: '删除合集',
						 type : BootstrapDialog.TYPE_WARNING,
						 message: message('admin.dialog.deleteConfirm'),
						 
						 draggable: true,
						 size : BootstrapDialog.SIZE_SMALL,
						 buttons: [{
							 label: '确认删除',
							 cssClass: 'btn-primary ',
							 action: function(dialog) {
								 dialog.close();
								 deleteIntersection();
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
				
				var deleteIntersection = function(){
					$.ajax({
						url: urlPrefix+"intersection/delete",
						type:"POST",
						dataType: 'json',
						data:{"ids":globalIds },
						success:function(msg){
							if (msg.code == "ACK") {
								$(window).loadingInfo("success", msg.message);
								table.refreshThis();
								globalIds= [];
								$("#ids").val("");
								$("#totalCheck").prop("checked",false);
							} else {
								$(window).loadingInfo("warn", msg.message);
							}
						}
					});
				};
			},
		
			init:function(){
				var _self = this;
				_self.initEvents();
			}
	}.init();
})