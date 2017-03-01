$(function(){
	var deliveryList = {
			initEvents:function(){
				var _self = this;	
				//开始日期
				var date = new Date();
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				var day = date.getDate();

				$('#csDate,#ceDate').datetimepicker({
					minView: 'month',
					format: 'yyyy-mm-dd',
					language: 'ch',
					//endDate: year+'-'+month+'-'+day,
					autoclose : true,
					todayBtn : true
				});
				//创建日期
				$('#csDate').on('changeDate',function(){
					$('#ceDate').datetimepicker('setStartDate', $('#csDate').val());
					if($('#csDate').val()=="" && $("#csDate").next().css('display') == 'inline-block'){
						$("#csDate").next().css('display','none');
					}else{
						$("#csDate").next().css('display','inline-block');
					}
				});

				//结束日期
				$('#ceDate').on('changeDate',function(){
					if ($('#ceDate').val()) {
						$('#csDate').datetimepicker('setEndDate', $('#ceDate').val());
					}else{
						$('#csDate').datetimepicker('setEndDate', year+'-'+month+'-'+day);
					};
					if($('#ceDate').val()=="" && $("#ceDate").next().css('display') == 'inline-block'){
						$("#ceDate").next().css('display','none');
					}else{
						$("#ceDate").next().css('display','inline-block');
					}
				});
				//省份
				$.ajax({
					type: "GET",
					url: urlPrefix + "region/findAllProvince",
					contentType: "application/json;charset=utf-8",
					dataType: "json",
					success: function(message){
						if(message.code=="ACK"){
							var option = "<option value=''>请选择省份</option>";
							_.each(message.data,function(value,index){
								option += "<option value='"+value.id+"'>"+value.name+"</option>";
							});
							$("#provinceId").html(option);							
						}
					},
					error: function(message){
					}		
				});
				
				$("#provinceId").on('change',function(){
					var provinceId= $("#provinceId").val();
					//城市
					if(provinceId!="") {
						$.ajax({
							type: "GET",
							url: urlPrefix + "region/findCityByProv/"+provinceId,
							contentType: "application/json;charset=utf-8",
							dataType: "json",
							success: function(message){
								$("#cityId").html("");
								if(message.code=="ACK"){
									var option = "<option value=''>请选择城市</option>";
									_.each(message.data,function(value,index){
										option += "<option value='"+value.id+"'>"+value.name+"</option>";
									});
									$("#cityId").html(option);							
								}
							},
							error: function(message){
							}		
						});
					}else {
						$("#cityId").html("<option value=''>请选择城市</option>");
					}					
				});
				
				
				var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
					refreshBtn : $('#refreshRecord'),
					idField : "orgId",
				    url: 'delivery/list',
				    sidePagination:'server',
				    pagination : true,
				    queryParamsType: "limit",
				    queryAddParams: function() {
				    	var result= {
								orgCode    : $("#orgCode").val(),
					    		orgName    : $("#orgName").val(), 
					    		beginDate  : $("#csDate").val(), 
					    		endDate    : $("#ceDate").val(),
					    		contact    : $("#contact").val(),
					    		phone      : $("#phone").val(),
					    		provinceId : $("#provinceId").val(),
					    		cityId     : $("#cityId").val()
						}

				    	if(result.beginDate && $.GLOBAL.utils.isDate(result.beginDate)) {
				    		result.beginDate += " 00:00:00";
		                }
		                if(result.endDate && $.GLOBAL.utils.isDate(result.endDate)) {
		                	result.endDate += " 23:59:59";
		                }
		                
						if($("#csDate").val()==""){delete result.beginDate}
						if($("#ceDate").val()==""){delete result.endDate}
						return result;
				    },
				     
				    columns: [
						{
							width:50,
							align: 'center',
							formatter:function(value,row,index){  
								return index+1;
							}
						} ,	

				        {
				            field: 'orgCode',
				            checkbox: false,
				        } ,
				        {
				            field: 'orgName',
				        } ,
				        {
				            field: 'contact',
				        } ,
				        {
				            field: 'phone',
				        } ,
				        {
				            field: 'provinceName',
				        } ,
				        {
				            field: 'createDate',
				        } ,
				        {   
				        	field: 'orgId',
				            align: 'center',
				            formatter:function(value,row,index){  
				               
				                return ' <a  title="【查看】" target="_self" href="detail/'+row.orgId+'" class="editItem"> <i class="fa fa-eye" style="font-size:20px;"></i></a>'
				                + '<a  title="【编辑】" target="_self" href="edit/'+row.orgId+'" class="editItem"> <i class="fa fa-edit" style="font-size:20px;"></i></a>';
				            } 
				        }
				     ]
				});
				
			},
			
			init:function(){
				var _self = this;
				_self.initEvents();
			}
	
	}.init();	
});
