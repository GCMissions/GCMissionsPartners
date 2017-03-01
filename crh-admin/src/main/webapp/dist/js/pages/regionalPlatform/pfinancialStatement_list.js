$(function() {
	var financial = {
		init : function(cateId) {
			this.initTimeSelect();
	        this.initTable1(); 
	        this.bindEvents();
		},
		bindEvents: function() {
			var _self = this;
	        $(".nav-tabs").find("a").click(function(){
	        	if($(this).attr("data-index")==0){
	        		_self.initTable1(); 
	        	}else{
	        		_self.initTable2(); 
	        	}
	        });
	        $("button[id^=excel]").click(function(){
	        	_self.excelReport($(this).attr("data-excelType"));
	        });
	        
	      //开始日期
	    	var date = new Date();
	    	var year = date.getFullYear();
	    	var month = date.getMonth()+1;
	    	var day = date.getDate();

	    	$('#csDate1,#ceDate1,#csDate2,#ceDate2').datetimepicker({
	    		minView: 'month',
	    		format: 'yyyy-MM-dd',
	    		language: 'ch',
	    		endDate: year+'-'+month+'-'+day,
	    		autoclose : true,
	    		todayBtn : true
	    	});
	    	//创建日期
	    	$('#csDate1').on('changeDate',function(){
	    		$('#ceDate1').datetimepicker('setStartDate', $('#csDate1').val());
	    		if($('#csDate1').val()=="" && $("#csDate1").next().css('display') == 'inline-block'){
	    			$("#csDate1").next().css('display','none');
	    		}else{
	    			$("#csDate1").next().css('display','inline-block');
	    		}
	    	});
	    	//结束日期
	    	$('#ceDate1').on('changeDate',function(){
	    		if ($('#ceDate1').val()) {
	    			$('#csDate1').datetimepicker('setEndDate', $('#ceDate1').val());
	    		}else{
	    			$('#csDate1').datetimepicker('setEndDate', year+'-'+month+'-'+day);
	    		};
	    		if($('#ceDate1').val()=="" && $("#ceDate1").next().css('display') == 'inline-block'){
	    			$("#ceDate1").next().css('display','none');
	    		}else{
	    			$("#ceDate1").next().css('display','inline-block');
	    		}
	    	});
	    	//创建日期
	    	$('#csDate2').on('changeDate',function(){
	    		$('#ceDate2').datetimepicker('setStartDate', $('#csDate2').val());
	    		if($('#csDate2').val()=="" && $("#csDate2").next().css('display') == 'inline-block'){
	    			$("#csDate2").next().css('display','none');
	    		}else{
	    			$("#csDate2").next().css('display','inline-block');
	    		}
	    	});

	    	//结束日期
	    	$('#ceDate2').on('changeDate',function(){
	    		if ($('#ceDate2').val()) {
	    			$('#csDate2').datetimepicker('setEndDate', $('#ceDate2').val());
	    		}else{
	    			$('#csDate2').datetimepicker('setEndDate', year+'-'+month+'-'+day);
	    		};
	    		if($('#ceDate2').val()=="" && $("#ceDate2").next().css('display') == 'inline-block'){
	    			$("#ceDate2").next().css('display','none');
	    		}else{
	    			$("#ceDate2").next().css('display','inline-block');
	    		}
	    	});
	        
		},
		excelReport:function(param){
			var title = "确认导出财务报表？";
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
                    	$("#orderForm"+param).submit();
                    }
                }, {
                    label: '取消',
                    action: function(dialog) {
                        dialog.close();
                    }
                }]
            });
		},
		initTimeSelect : function() {
			$.pages.initDateTime();
		},
		formatPrice:function(price){//格式化金额
			return "￥"+(price/100).toFixed(2);
		},
		initTable1 : function() {
			var _self = this;
			$.GLOBAL.utils.loadBootTable({
	            table : $('#dataList1'),
	            refreshBtn : $('#refreshRecord1'), 
	            pagination : true,
	            pageSize : 10,
	            url: "pfinancialStatement/list",
	            sidePagination:'server',
	            queryAddParams: function() {
	                var queryObj =  {
	                	orderId: $('#orderId1').val(),
	                    sDate: $('#csDate1').val(),
	                    eDate: $('#ceDate1').val(),
	                    orgType:'P'
	                } ;
	                return queryObj;
	            },
	            columns: [
					{
						width : 50,
						align: 'center',
	                    formatter:function(value,row,index){  
	                    	return index+1;
	                    }
	                } ,
	                {
	                    field: 'orderId',
	                } ,
	                {
	                    field: 'createDate',
	                } ,
	                 {
	                    field: 'totalAmount',
	                    formatter:function(value,row,index){
	                    	return _self.formatPrice(value);
	                    }
	                },
	                {
	                    field: 'pShipProfit',
	                    formatter:function(value,row,index){
	                    	return _self.formatPrice(value);
	                    }
	                } ,
	                {
	                    field: 'pProdProfit',
	                    formatter:function(value,row,index){
	                    	return _self.formatPrice(value);
	                    }
	                } ,
	                {
	                    field: 'orderId',
	                    align: 'center',
	                    formatter: function(value,row,index){  
	                        return  ' <a  title="查看" href="detail/'+value+'" class="viewItem" data-id="'+row.orderId+'"> <i class="fa fa-eye "  style="font-size:20px"></i></a>'
			                        ;
	                    } 
	                } 
	             ],
	             onLoadSuccess:function(data){
	            	 if(data){
	            		 $("#totalAmount").html(_self.formatPrice(data.totalAmount)+"元");
	            		 $("#pTotalShipProfit").html(_self.formatPrice(data.pTotalShipProfit)+"元");
	            		 $("#pTotalProProfit").html(_self.formatPrice(data.pTotalProProfit)+"元");
	            		 $("#pTotalSPProfit").html(_self.formatPrice(data.pTotalSPProfit)+"元");
	            	 }
	             }
	        });
	
		},
};
	
	financial.init();
});