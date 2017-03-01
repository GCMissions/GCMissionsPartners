$(function(){
	
var productSaleApp = {
    priceMax: 9999999.99,
	init : function() {
		this.$dataList = $('#dataList');
		this.initTable();
        this.bindHandler();
	},
	
	bindHandler : function () {
		
        this.$dataList.on('click', "a.offSaleItem", _(this.offSale).bind(this));
        this.$dataList.on('click', "a.onSaleItem", _(this.onSale).bind(this));
        
        var _this = this;
        $("#onSaleComfirm").on("click", function(){
        	_this.saveSale();
        });
	},
	
    initTable : function() {
		this.bootTable = $.GLOBAL.utils.loadBootTable({
            table : this.$dataList,
            refreshBtn : $('#searchBtn'),
            pagination : true,
            pageSize : 20,
            url: "coolbag/product/search",
            sidePagination:'server',
            queryAddParams: function() {
                return {
                    pname: $('#productName').val(),
                    pcode: $.trim( $('#productCode').val()),
                    saleStatus :  $("input[name='saleStatus']:checked").val()
                } 
            },
            initSearchForm : true, 
		    fillSearchData : function(data) {
		    	 $("#productName").val(data.pname);
		    	 $("#productCode").val(data.pcode);
		    	 $("input[name='saleStatus']").each(function() {
		    		 if($(this).val() == data.saleStatus) {
		    			 $(this).prop('checked', true);
		    		 }
		    	 });
		    	
		    },
            columns: [
				{
					field: 'pcode',
                } ,
                {
                    field: 'pname',
                    class: 'productName',
                    formatter: function(value,row,index){
                    	var str = value;
                    	var re = new RegExp(" ","g");
                    	var newstr = str.replace(re, "&nbsp;");
                    	return '<span>'+ newstr +'</span>';
                    }
                } ,
                {
                    field: 'priceRange',
                } ,
                {
                	field: 'onTime',
                },
                {
                	field: "offTime",
                },
                //上下架状态
                {
                    field: 'saleStatus',
                    formatter:function(value,row,index){  
                    	if ("2" == value) {
                    		return "已上架";
                    	} 
                    	
                    	if ("1" == value) {
                    		return "待上架";
                    	}
                    	
                    	return "未上架";
                    }
                } ,
                {
                    field: 'id',
                    class:"control",
                    align: 'center',
                    formatter: function(value,row,index){  
                       var result ="<a title='查看' href='"+ urlPrefix +"coolbag/product/page?oper=view&productId="+ row.id +"' class='priceItem' data-id='"+row.id+"'> 查看</a>";

                       if ('2' != row.saleStatus){
                    	   result +="<a title='上架' href='#' class='onSaleItem' data-id='"+ row.id +"'> 上架</a>";
                       } else {
                    	   result +="<a title='下架' href='#' class='offSaleItem' data-id='"+ row.id +"'> 下架</a>";
                       }
                       return result;                    
                    } 
                } 
             ]
        });

	},
	
    saveSale : function () {
    	var that = this,
    		startDate = $("#startDate").val(),
    		endDate= $("#endDate").val();
    	
    	if("" == startDate){
    		$(window).loadingInfo("error", "请选择上架时间！");
    		return;
    	}
    	if (startDate == endDate) {
    		$(window).loadingInfo("error", "上架时间和下架时间不可相同！");
    		return;
    	}
    	var productId = $("#dateDialog .modal-body").attr("product-id");
    	if ("" == productId) {
    		return;
    	}
    	var data = {
    		productId : productId,
    		onTime : startDate,
    		offTime : endDate
    	};
    	$.ajax({
            type         : "POST",
            url          : urlPrefix + "coolbag/product/sale/onsale",
            dataType     : 'json',
			data         : JSON.stringify(data),
			contentType  : 'application/json',
			success		 : function(result) {
				if(result.code == "ACK") {
					$("#dateDialog").modal("hide");
	                $(window).loadingInfo("success", result.message);
	                that.bootTable.refresh(); 
	            }
			}
        });
    },
    
    offSale : function(e) {
    	e.preventDefault();
        var that = this;
        var $target = $(e.target),
        productId = $target.data('id');
    	that.dialog =  BootstrapDialog.show({
    		 title: "商品下架",
             type : BootstrapDialog.TYPE_WARNING,
             message: message('确认下架该商品吗？'),
             draggable: true,
             size : BootstrapDialog.SIZE_SMALL,
             buttons: [{
                 label: '确认',
                 cssClass: 'btn-primary ',
                 action: function(dialog, e) {
                 	$.ajax({
                        type         : "POST",
                        url          : urlPrefix + "coolbag/product/sale/offsale",
                        data         : {
                        	productId : productId
                        },
                        dataType     : 'json',
            			success : function(result) {
            				 if(result.code == "ACK") {
            					 dialog.close();
                                 $(window).loadingInfo("success", result.message);
                                 that.bootTable.refresh(); 
                             }
            			}
                    })
                 }
             }, {
                 label: '取消',
                 action: function(dialog, e) {
                     dialog.close();
                 }
             }],
         });
    },
    
    checkProduct : function(productId) {
    	var bool;
    	$.ajax({
            type         : "GET",
            url          : urlPrefix + "coolbag/product/sale/check?productId="+productId,
            dataType     : 'json',
            async	     : false,
			contentType  : 'application/json',
			success: function(result) {
				if(result.code == "NACK") {
	            	bool= false;
	            } else {
	                bool = true;
	            }
			}
        });
    	
    	return bool;
    },
    
    onSale: function(e) {
    	e.preventDefault();
        var $target = $(e.target),
            productId = $target.data('id'),
            that = this;        
        // 检查商品信息完整性
        if (that.checkProduct(productId)) {
        	$('#startDate, #endDate').datetimepicker("remove");
        	that.dateInit();
        	$("#dateDialog").modal("show");
        	$("#dateDialog .modal-body").attr("product-id", productId);
        }
    } ,
    
    dateInit : function(){
    	var curDate = new Date();
    	var month = curDate.getMonth() + 1 < 10 ? "0" + curDate.getMonth() + 1 : curDate.getMonth() + 1;
    	var date = curDate.getDate() < 10 ? "0" + curDate.getDate() : curDate.getDate();
    	var hour = curDate.getHours() < 10 ? "0" + curDate.getHours() : curDate.getHours();
    	var minutes = curDate.getMinutes() < 10 ? "0" + curDate.getMinutes() : curDate.getMinutes();
    	var curTimeStr = curDate.getFullYear() + "-" + month + "-" + date + " " + hour + ":" + minutes;
    	
    	$('#startDate, #endDate').val('');
    	$('#startDate, #endDate').next().css('display','none');
    	
		//初始化
    	$('#startDate, #endDate').datetimepicker({
    		startDate: curDate,
    		minView: 'hour',
    		format: 'yyyy-MM-dd hh:ii',
    		language: 'ch',
    		autoclose : true,
    		todayBtn : true,
    	});
    	
    	// 上架时间
    	$('#startDate').on('changeDate', function(ev){
    		if (ev.date){
        		$('#endDate').datetimepicker('setStartDate', $('#startDate').val());
    		} else {
    			$("#endDate").datetimepicker('setStartDate', curTimeStr);
    		}
    		if (!ev.date){
    			$("#startDate").next().css('display','none');
    		} else{
    			$("#startDate").next().css('display','inline-block');
    		}
    	});
    	
    	// 下架时间
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
	
});