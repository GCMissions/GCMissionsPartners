$(function() {
	
	//开始日期
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();

	$('#csDate,#ceDate').datetimepicker({
		minView: 'month',
		format: 'yyyy-MM-dd',
		language: 'ch',
		endDate: year+'-'+month+'-'+day,
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

	$.GLOBAL.utils.loadBootTable({
		table : $('#dataList'),
	    removeBtn : $('#removeRecord'),
	    refreshBtn : $('#refreshRecord'),
	    idField : "messageId",
	    pagination:true,
	    url: 'message/list',
	    sidePagination:'server',
	    queryAddParams: function() {
	    	return $.GLOBAL.utils.getSearchData("#search-area");
	    },
	    columns: [
	        {
	            field: 'title',
	        } ,
	        {
	            field: 'content',
	            formatter:function(value,row,index){  
		               var content = value.substring(0,10)+"......";
	                return content;
	  
	            }
	        } ,
	        {
	            field: 'createDate',
	        } ,
	        {
	            field: 'readStatus',
	            formatter:function(value,row,index){  
		               if(value=="0"){
		            	   return "未读";
		               }else{
		            	   return "已读";
		               }
	  
	            }
	        } ,
	        {
	            //title: '操作',
	            field: 'messageId',
	            align: 'center',
	            checkbox: false,
	            formatter:function(value,row,index){  
	               
	                return ' <a  title="查看" href="#" class="view" data-id="'+value+'"> <i class="fa fa-eye"  style="font-size:20px"></i></a>';
	  
	            } 
	        }
	     ],
	     onClickCell : function(field, value, row, $element) {
	    	 if(field=="messageId"){
	    		 $.ajax({
		    		 url:'detail/'+value,
		    		 type:'get',
		    		 dataType:'json',
		    		 success:function(dataInfo){
		    			 if(dataInfo.code=="ACK"){
		    				 var data = dataInfo.data;
		    				 $.ajax({
		    					 url:'/admin/web/main/message/getUnredMessageNum',
		    					 type:'get',
		    					 dataType:'json',
		    					 async:false,
		    					 success:function(msg){
		    						 if(msg.code=="ACK"){
		    							 $('#message_total', parent.document).text(msg.data); 
		    						 }
		    					 }
		    					 
		    				 });
		    				 
		    				 BootstrapDialog.show({
				    		        title: '查看',
				    		        message: $(template('viewTpl', {})),
				    		        data:data,
				    		        draggable: true,
				    		        onshown: function(dialogRef){
				    	                $("#title").html(dialogRef.getData('title'));
				    	                $("#createDate").html(dialogRef.getData('createDate'));
				    	                $("#content").html(dialogRef.getData('content'));
				    	            },
				    	            onhide:function(dialogRef){
				    	            	$("#refreshRecord").click();
				    	            },
				    		        buttons: [{
				    		            label: '关闭',
				    		            action: function(dialog) {
				    		                dialog.close();
				    		            }
				    		        }]
				    		    });
		    			 }
		    			 
		    		 }
		    		 
		    	 });
	    		 
	    	 }
	     }
	});
});