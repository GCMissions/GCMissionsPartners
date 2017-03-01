var productSaleApp = {
    listUrl      : "productShief/list",
    savePriceGetUrl : "productShief/shiefPage/{{productId}}", //get or post
    savePricePostUrl : "productShief/updatePrice",
    getSaleUrl   : "productShief/shiefPage/{{productId}}",
    saveSaleUrl  : "productShief/productShief",
    checkProdUrl  : "productShief/checkProduct/",
    countyRegionId  : 100000, //全国（除以上罗列的市以外）的regionId
    priceMax        : 9999999.99,
    
//    saleUpTitle : "上架申请",                
            
    toSaleHtml : "<div class='popTreeDialogSection '>"
                   // +"<div class='col-sm-10'><label class='control-label'>{{title}}</label>"
			                   // +"</div>"
			    	+"<div class='col-sm-10 no-padding timegroup' style='display:none'>"
					+"  <input type='hidden'  name='csDateInput'  value='' id='startDateH'  >"
					+"  <div class='dateDiv' style='margin-bottom: 0px;'>"
					+"		<input size='10' type='text' name='startDate' id='startDate' class='form-control keyword beginDate' placeholder='上架日期' readonly>"
					+"    <span class='add-on' style='display:none'><i class='icon-remove'></i></span>"
					+"    <span class='add-on'><i class='icon-calendar'></i></span>"
					+"  </div>"
					+"	<label style='    padding: 8px;'> - </label>"
					+"	<input  type='hidden'  name='ceDateInput' value='' id='endDateH' >"
					+"	<div class='dateDiv' style='margin-bottom: 0px;'>"
			        +"    <input size='10' type='text' name='endDate' id='endDate' class='form-control keyword endDate' placeholder='下架日期' readonly>"
			        +"    <span class='add-on' style='display:none'><i class='icon-remove'></i></span>"
			        +"    <span class='add-on'><i class='icon-calendar'></i></span>"
			        +"  </div>"
			        +"</div>"
			        
                    +"<div class='row treeTable'>"
	                    +"<div class='col-sm-12' >"
		                    +"<table class='tree_table globalRegionTree'　>"
		                    +"<tbody>"
		                    +"</tbody>"
		                    +"</table>"
		                +"</div>"
                        +"<div class='col-sm-12' >"
                         + "<div style='max-height:400px;border: 1px solid #dadada;background-color: #F2F2F2;overflow-y:scroll'>"
	                            +"<table class='tree_table regionTree'>"
	                            +"<tbody>"
	                            +"</tbody>"
	                            +"</table>"
                            +"</div>" 
                        +"</div>" 
                       
                    +"</div>"
                    +"</div>" 
                    ,
	init : function() {
		this.$dataList = $('#dataList');
		this.bindHandler();
		this.initTable();
		$("#secondCate").attr("disabled","disabled");
       
        
	},
	bindHandler : function () {
        this.$dataList.on('click', "a.saleItem", _(this.toSale).bind(this));
        this.$dataList.on('click', "a.unSaleItem", _(this.unSale).bind(this));
        $(document).on("click", ".popTreeDialogSection  div.shipTypeSelection span", _(this.setCityShipType).bind(this));
        $(document).on("click", ".popTreeDialogSection .regionTree label input", _(this.setProvinceType).bind(this));
        $(document).on("click", ".globalRegionTree  td input.radioCity", _(this.setAllCityShip).bind(this));
        
        $("#firstCate").on('change',function(){
				$("#secondCate").html('');
				var firstCateId = $(this).val();
				if(firstCateId==0){
					firstCateId = -1;
				}
					$.ajax({
						type: "GET",
						url:urlPrefix+"activity/getSubCategoryByParent/"+firstCateId,
						dataType: 'json',
						success: function(result){
							if(result.code == 'ACK'){
								var data = result.data;
								if(data && data.length != 0){
									$("#secondCate").attr("disabled",false);
									$("#secondCate").append('<option value="">不限</option>');
								
									$.each(data,function(i,v){
										$("#secondCate").append('<option value="'+ v.cateId+'">'+ v.cateName +'</option>');
									});
								} else {
									$("#secondCate").attr("disabled","disabled");
								}
								if($("#secondCate").data('val')) {
									$("#secondCate").val($("#secondCate").data('val'));
									$('#secondCate').data('val', '');
								}
								$("#secondCate").selectpicker('refresh');
							}
						}
					});
				
		});
	},
    initTable : function() {
		this.bootTable = $.GLOBAL.utils.loadBootTable({
            table : this.$dataList,
            //removeBtn : $('#removeRecord'),  
            refreshBtn : $('#refreshRecord'), 
            //idField : "productId",
            pagination : true,
            pageSize : 50,
            url: this.listUrl,
            sidePagination:'server',
            queryAddParams: function() {
            	if($("#saleStatusShief").prop("checked")==true && $("#saleStatusUnShief").prop("checked")==false){
        			$('#saleStatus').val( "1");
        		} else if($("#saleStatusShief").prop("checked")==false && $("#saleStatusUnShief").prop("checked")==true) {
        			$('#saleStatus').val("0");
        		} else {
        			  $('#saleStatus').val("");
        		}
                return {
                    productName: $.trim($('#productName').val()),
                    orgId		: $('#orgId').val(),
                    firstCateId     : $('#firstCate').val(),
                    secondCateId     : $('#secondCate').val() || $('#secondCate').data('val'),
                    saleStatus :  $('#saleStatus').val()
                } 
            },
            
            initSearchForm : true, 
		    fillSearchData : function(data) {
		    	$('#productName').val(data.productName);
		    	$('#orgId').val(data.orgId);
		    	$('#firstCate').val(data.firstCateId);
		    	$('#secondCate').selectpicker();
		    	$('#secondCate').data('val', data.secondCateId);
		    	$('#secondCate').selectpicker('val', data.secondCateId);
		    
		    	$('#saleStatus').val(data.saleStatus);
		    	if(data.saleStatus == 1) {
		    		$("#saleStatusShief").prop("checked", true);
		    		$("#saleStatusUnShief").prop("checked", false);
		    	} else if(data.saleStatus === "") {
		    		$("#saleStatusUnShief").prop("checked", false);
		    		$("#saleStatusShief").prop("checked", false);
		    	} else {
		    		
		    		
		    		$("#saleStatusShief").prop("checked", false);
		    		$("#saleStatusUnShief").prop("checked", true);
		    	}
		    	if(data.firstCateId) {
		    		$("#firstCate").trigger('change');
		    	}
		    },
            columns: [
               
				{
                    formatter:function(value,row,index){  
                    	return "P"+row.productId ;
                    }
                } ,
                {
                    field: 'cateName',
                } ,
                {
                    field: 'orgName',
                } ,
                {
                    field: 'productName',
                    class: 'productName',
                    formatter: function(value,row,index){
                    	var str = value;
                    	re=new RegExp(" ","g");
                    	var newstr=str.replace(re,"&nbsp;");
                    	return '<span>'+newstr+'</span>';
                    }
                } ,
                {
                    field: 'price',
                } ,
                //上下架状态
                {
                	
                    field: 'saleFlag',
                    formatter:function(value,row,index){  
                    	if(value != '0') return "已上架";
                    	else return "未上架";
                    }
                } ,
               
                {
                    field: 'productId',
                    class:"control",
                    align: 'center',
                    formatter: function(value,row,index){  
                       var result ='<a  title="查看" href="'+urlPrefix +'activity/viewPage/'+row.productId +'" class="priceItem" data-id="'+row.productId+'"> 查看</a>';

                       if(row.saleFlag != '0')
                       {
                    	   result +='<a  title="区域" href="#" class="saleItem"     data-id="'+row.productId+'"> 区域</a>';
                        	result +='<a  title="下架" href="#" class="unSaleItem"     data-id="'+row.productId+'"> 下架</a>';
                       } else {

                    	   result +='<a  title="上架" href="#" class="saleItem"     data-id="'+row.productId+'"> 上架</a>';
                       }
                        return result;                    
          
                    } 
                } 
             ]
        });

	},
	setCityShipType :function(e ) {
		var $target = $(e.target),
			provinceId = $target.closest('tr').data('tt-parent-id'),
        	isActive = $target.hasClass('active'),
        	that = this;
		$target.parent().find('span').removeClass('active');
		if(!isActive) {
			$target.addClass('active');
		} else {
			$('TR.collapsed').each(function(index,element) {
				if (provinceId==$(element).closest('tr').data('tt-id')) {
					$(element).find("input").attr("checked",false);
				}
			});
		}
		
	},
	setProvinceType :function(e ) {
		var $target = $(e.target),
			provinceId = $target.closest('tr').data('tt-id'),
        	isActive = $target.prop("checked") ;
		$('DIV.shipTypeSelection').each(function(index,element) {
			if (provinceId==$(element).closest('tr').data('tt-parent-id')) {
	    		var citySpan = $(element).find('span');
	    		citySpan.find('span').removeClass('active');
	    		if(isActive) {
	    			citySpan.addClass('active');
	    		} else {
		    		citySpan.removeClass('active');
	    		}
			}
    	});
		
	},
	setAllCityShip :function(e ) {
		var $target = $(e.target);
        	
		if($target.attr('id')==='noNetCity') {
			$(".regionTree").disabled= true;
			$("#div_cover").remove(); 
			var div = $(".regionTree").closest("div");
			var width = div.width();  
			var height = div.height();  
			var offset = div.offset();  

			$("<div id='div_cover' class='modal-backdrop fade in'  style='width:" + width + "px;height:" + height + "px;left:" +15 + "px;top:" + 0+ "px;position:absolute; '></div>").appendTo(div.closest("div").closest("div"));  
		} else {
			$("#div_cover").remove(); 
		}
		
	},
    saveSale : function ($btn) {
    	var data = this.getSaleDataJson(), 
    		that = this;
    	data.productId = this.productId;

    	if(data.list.length==0){
    		$(".bootstrap-dialog").loadingInfo("error", "请至少选择一个上架城市");
    		return;
    	}
    	if(!$("#startDate").val()){
    		$(".bootstrap-dialog").loadingInfo("error", "请选择上架日期");
    		return;
    	}
    	
    	data.startDate= $("#startDate").val();
    	data.endDate= $("#endDate").val();
    	$btn.saving();
    	$.ajax({
            type         : "POST",
            url          : urlPrefix + this.saveSaleUrl.template(data),
            dataType     : 'json',
			contentType  : 'application/json',
			data         : JSON.stringify(data),
			$loadingObject : $('.popTreeDialogSection')
        })
        .done(function(result) {
            if(result.code == "ACK") {
                $(".bootstrap-dialog").loadingInfo({
                    type : "ok",
                    text : result.message,
                    callBack : function() {
                    	that.bootTable.refresh(); 
                    	that.dialog.close();
                    }
                });
               
            }
        })
        .always(function() {
        	$btn.saved();
            //that.dialog.close();
            
        })
        ;
    },
    
    
    
    getSaleDataJson : function() {
    	var data = {
    		list : []
    	};
    	if($('table.treetable').find('input[id=noNetCity]').is(":checked")) {
            data.list.push({
                regionId: this.countyRegionId,
                saleFlag: 1  
            });
    	} else {
        	$('DIV.shipTypeSelection').each(function() {
        		var activeSpan = $(this).find('span.active');
        		var regionId = activeSpan.data('id');
        		if(activeSpan.length>0) {
        			data.list.push({
        				regionId : regionId,
        				saleFlag : 1
        			});
                }
        	});
        }
    	return data;
    },
    unSale : function(e) {
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
                 	var data = {
                    		list : []
                	};
                 	data.productId = productId;
                 	$.ajax({
                        type         : "POST",
                        url          : urlPrefix + that.saveSaleUrl.template(data),
                        dataType     : 'json',
            			contentType  : 'application/json',
            			data         : JSON.stringify(data),
            			$loadingObject : $('.popTreeDialogSection')
                    }).done(function(result) {
                        if(result.code == "ACK") {
                            $(".bootstrap-dialog").loadingInfo({
                                type : "ok",
                                text : result.message,
                                callBack : function() {
                                	that.bootTable.refresh(); 
                                	that.dialog.close();
                                }
                            });
                           
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
            url          : urlPrefix + this.checkProdUrl+productId,
            dataType     : 'json',
            async: false,
			contentType  : 'application/json'
        }).done(function(result) {
            if(result.code == "NACK") {
            	bool= false;
            } else {
                bool = true;
            }
        });
    	return bool;
    },
    toSale: function(e) {
    	e.preventDefault();
        var $target = $(e.target),
            productId = $target.data('id'),
            that = this;
        this.productId = productId;
        that = this;
        if(!that.checkProduct(productId)) {
        	return;
        }
        that.dialog =  BootstrapDialog.show({
            title    : '上架申请',
     
            message  : $(this.toSaleHtml.template({'title' : this.saleUpTitle})),
            draggable: true,
            size : BootstrapDialog.SIZE_NORMAL,
            buttons: [{
                label: '确认',
                cssClass: 'btn-primary ',
                action: function(dialog, e) {
                     that.saveSale($(e.target));
                }
            }, {
                label: '取消',
                action: function(dialog, e) {
                    dialog.close();
                }
            }],
            onshown : function() {
                $.ajax({
            		url 		 : urlPrefix+ that.getSaleUrl.template({productId: that.productId}),
            		dataType     : 'json',
    				contentType  : 'application/json',
            	})
            	.success(function(result) {
            		if(result.code == "ACK" ) {
                        that.renderToSalePage(result.data);
                    }
            		
            	});
                
            	
            }
        });
        that.dialog.getModalDialog().css('width', '700px');
    } ,
    /*显示上下架页面*/
    renderToSalePage : function(data) {
        var result = "", that = this, tempOjb = {},  saleFlg=0,
            countryRecord = {regionId: this.countyRegionId,  saleFlag: 0, checked: "",countyChecked:""};
        _(data.list).each(function(value, index) {
            if(value.regionId == that.countyRegionId) {
                countryRecord = $.extend(countryRecord, value);
                return;
            }
            result += "<tr  data-tt-id='"+value.regionId+"'><td colspan=2><label><input type='checkbox' >"+value.regionName+"</label></td></tr>";
            result += "<tr  data-tt-parent-id='"+value.regionId+"' ><td class='regionTD'>"
            if(!_.isUndefined(value.childrenList) && value.childrenList.length>0) {
                _(value.childrenList).each(function(item, key) {
                	tempObj = {
                		terminal : "",
                		platform : ""
                	};
                	if(parseInt(item.saleFlag) > 0) {
                		tempObj.terminal = "active";
                		countryRecord.countyChecked=" checked ";
                		saleFlg=1;
                	}
                	
                    result += "<div class='shipTypeSelection'><span class='mr-15  terminal " +tempObj.terminal+ "' data-id='"+item.regionId+"'>"+item.regionName+"</div>"
                    	   ;
                });
                
            }  
            result += "</td></tr>";
        });
        if(countryRecord.saleFlag != '0' && !_.isNull(countryRecord.saleFlag)) {
            countryRecord.checked = " checked ";
            countryRecord.countyChecked=" ";
    		saleFlg=1;
        }
        var resultGlobal = 
        		"<tr><td style='width: 120px'>上架区域：</td><td><label><input type=radio class='radioCity ' name='city' id='noNetCity' {{checked}} >全国</label></td><td ><label><input type=radio class='radioCity' name='city' {{countyChecked}} >选择区域</label></td><td ></td><td ></td></tr>".template(countryRecord);
        $('.popTreeDialogSection').find('.treeTable .regionTree tbody').html(result);
        $('.popTreeDialogSection').find('.treeTable .globalRegionTree tbody').html(resultGlobal);
        that.$dataTable = $(".popTreeDialogSection table");
        that.$dataTable.treetable({expandable : true});
        that.dateInit(saleFlg,data.saleTime,data.unSaleTime);
        if((countryRecord.saleFlag != '0' && !_.isNull(countryRecord.saleFlag))||saleFlg==0) {
        	$('#noNetCity').trigger("click");
        }
        //that.$dataTable.treetable("expandAll");
    },
    dateInit : function(saleFlg,saleTime,unSaleTime){
    	var date = new Date();
    	var year = date.getFullYear();
    	var month = date.getMonth()+1;
    	var day = date.getDate();
    	var hour = date.getHours();
    	
    	
    	$('#startDate,#endDate').datetimepicker({
    		startDate: date,
    		minView: 'month',
    		format: 'yyyy-MM-dd',
    		language: 'ch',
    		autoclose : true,
    		todayBtn : true
    		
    	});
    	if(saleFlg==0) {
	    	if(month<10)  
		   	 {  
	    		month="0"+month;  
		   	 }
		   	 if(day<10)  
		   	 {  
		   		day="0"+day;  
		   	 }
	    	$('#startDate').val(year+"-"+month+"-"+day);
    	} else {
	    	$('#startDate').val(saleTime);
	    	$('#endDate').val(unSaleTime);
    	}
    	
    	$('#startDate').on('changeDate',function(){
    		$('#startDate').datetimepicker('setStartDate', date);
    		if($('#startDate').val()!=''){
    			if(saleTime && new Date(saleTime)<date) {
            		$('#endDate').datetimepicker('setStartDate', date);
    			} else {
            		$('#endDate').datetimepicker('setStartDate', $('#startDate').val());
    			}
    		} else {
    			$('#endDate').datetimepicker('setStartDate', date);
    		}
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
    			$('#startDate').datetimepicker('setEndDate', "");
    		};
    		if($('#endDate').val()=="" && $("#endDate").next().css('display') == 'inline-block'){
    			$("#endDate").next().css('display','none');
    		}else{
    			$("#endDate").next().css('display','inline-block');
    		}
    	});
    	 $(".dateDiv .icon-remove, .datetimeInput .icon-remove").click(function(){
             $(this).closest("div").find("input").val("");
             $(this).closest("div").find("input").trigger("changeDate");
         });
         $(".dateDiv .icon-calendar, .datetimeInput .icon-calendar").click(function(){
             $(this).closest("div").find("input").focus();
         });
         
         $('.datetimeInput input').on('changeDate',function(){
         	var $curInput = $(this);
				if( $curInput.val()=="" &&  $curInput.next().css('display') == 'inline-block'){
					 $curInput.next().css('display','none');
				}else{
					 $curInput.next().css('display','inline-block');
				}
			});
     	if(saleFlg==0) {
     		$('#startDate').trigger("changeDate");
     	} else {
     		$('#startDate').attr("disabled","disabled");

			if(new Date(saleTime)<date) {
        		$('#endDate').datetimepicker('setStartDate', date);
			} else {
        		$('#endDate').datetimepicker('setStartDate', $('#startDate').val());
			}
     		if (null !=unSaleTime) {
         		$('#endDate').trigger("changeDate");
     		}
     	}
    }
}