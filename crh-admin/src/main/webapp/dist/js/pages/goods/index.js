var goodsApp = {
	$dataList : $('#dataList'),
	$addItem  : $('.addItem'),
	dialog : void 0,
    bootTable : void 0,
    defaultAttr : {valueInfo : "", attrValueId : ""},
    avSeq     : 0,
    savePostUrl : "goods/save",
    updatePostUrl : "goods/update",
    editUrl : "goods/detail",
	init : function(cateId) {
		this.initTimeSelect();
		this.initTable();

		this.bindEvents();
		
	},
	bindEvents : function() {
		 //add edit 
        var that = this;
        this.$addItem.on("click", function() {
        	var subAttrHtml = "";
        	that.defaultAttr.avSeq  = that.avSeq ++ ;
			//subAttrHtml = template('subItemTpl', that.defaultAttr);
			
           that.dialog =  BootstrapDialog.show({
                title: '添加单瓶商品',
                //type : BootstrapDialog.TYPE_DEFAULT,
                message: $(template('addEditTpl', {subAttrHtml: subAttrHtml})),
                draggable: true,
                buttons: [{
                    label: '保存',
                    cssClass: 'btn-primary saveAddEditTpl',
                    action: function(dialog, e) {
                        that.save($(e.target));
                    }
                }, {
                    label: '取消',
                    action: function(dialog, e) {
                        dialog.close($(e.target));
                    }
                }],
                onshown : function() {
                	$("#priceYuan").on('blur',function(){
	            		var valid = /^\d{0,6}(\.\d{0,2})?$/.test(this.value),
	            	    	val = this.value;
	            	    
	            	    if(!valid){
	            	        $("#price-error").show().text("请输入正确的价格！");
	            	        $("#priceYuan").addClass("fieldError");
	            	    }else{
	            	    	$("#price-error").hide();
	            	    	$("#priceYuan").removeClass("fieldError");
	            	    }
            		});
                	/*$("#priceYuan").on('keyup',function(){
	            		var valid = /^\d{0,6}(\.\d{0,2})?$/.test(this.value),
	            	    	val = this.value;
	            	    
	            	    if(!valid){
	            	    	console.log("Invalid input!");
	            	        this.value = val.substring(0, val.length - 1);
	            	    }
            		});*/
                }
           });
           that.dialog.getModalDialog().css('width', '500px');
        });
        this.$dataList.on("click", "a.editItem", _(this.editItemHandler).bind(this));
	},
	editItemHandler: function(e) {
		e.preventDefault();
    	var $target = $(e.target),
    		goodsId = $target.parent().data('id');
    	
    	console.log(goodsId);
    	//disabled $target 
    	$target.prop("disabled", true);
		
    	var data = {
    		goodsId : goodsId,
    	};
    	var that = this;
    	$.ajax(
        { 
    		type: 'POST',
			url: urlPrefix + this.editUrl,
			dataType: 'json',
			data : JSON.stringify(data),
			contentType: 'application/json',
		})
        .done(function(result) {
        	if(result.code == "ACK") {
        		var subAttrHtml = "";
        		var message = $(template('addEditTpl', result.data));
                that.dialog =  BootstrapDialog.show({
                    title: '编辑单瓶商品',
                    message: $(template('addEditTpl', result.data)),
                    draggable: true,
                    buttons: [{
                        label: '保存',
                        cssClass: 'btn-primary saveAddEditTpl',
                        action: function(dialog, e) {
                            that.save($(e.target));
                        }
                    }, {
                        label: '取消',
                        action: function(dialog) {
                            dialog.close();
                        }
                    }],
                    onshown : function() {
                    	$("#priceYuan").on('blur',function(){
    	            		var valid = /^\d{0,6}(\.\d{0,2})?$/.test(this.value),
    	            	    	val = this.value;
    	            	    
    	            	    if(!valid){
    	            	        $("#price-error").show().text("请输入正确的价格！");
    	            	        $("#priceYuan").addClass("fieldError");
    	            	    }else{
    	            	    	$("#price-error").hide();
    	            	    	$("#priceYuan").removeClass("fieldError");
    	            	    }
                		});
                    }
                });
                that.dialog.getModalDialog().css('width', '500px');
        	} else {
            }
        })
        .always(function() {
        	$target.prop("disabled", false);
        })
        .fail(function(result) {
        	alert("获取数据失败"); 
        });
    },
    getFormJson : function($form) {
    	var tempData = $form.frmSerialize();
    	data = {
    		goodsId : tempData.goodsId,
    		goodCode : tempData.goodCode,
    		goodName : tempData.goodName,
    		priceYuan : tempData.priceYuan,
    		specs : tempData.specs
    	};
    	
    	return data;
    },
    save : function($btn){
    	var $form = $('#addEditForm'),
		    that = this,
	        isValid = true,
	        goodsId = $form.find('input[name="goodsId"]').val(),
	        action = goodsId ? this.updatePostUrl: this.savePostUrl,
	        data = this.getFormJson($form);
	    
	    //同名只检查一次
	    if( $('#addEditForm').validate().form()) {
	    	$btn.saving();
	        $.ajax({
			  type: 'POST',
			  url: urlPrefix+action,
			  dataType: 'json',
			  contentType: 'application/json',
			  data:  JSON.stringify(data),
			  $loadingObject : $form
	        })
	    	.done(function(result) {
	    	 	
	    	 	if(result.code == "ACK") {
	        	 	$('#addEditForm').loadingInfo({
	        	 		type : "success",
	        	 		text : "保存成功",
	        	 		callBack : function() {
	        	 			that.dialog.close();
	                   	    that.bootTable.refresh(); 
	        	 		}
	        	 	});
	    	 	}
	    	 })
	    	 .always(function(result) {
	    		 $btn.saved();
	    	 });
	    } else {
	    	//console.log("valid fail");
	    }
    },
	initTimeSelect : function() {
		$.pages.initDateTime();
	},
	initTable : function() {
		this.bootTable = $.GLOBAL.utils
				.loadBootTable({
					table : this.$dataList,
					refreshBtn : $('#refreshRecord'),
					pagination : true,
					pageSize : 10,
					url : 'goods/search',
					sidePagination : 'server',
					queryAddParams : function() {
						var queryObj = {
							goodCode : $.trim($('#goodCode').val()),
							goodName : $.trim($('#goodName').val()),
							beginDate : $('#beginDate').val(),
							endDate : $('#endDate').val(),
						};
						if (queryObj.beginDate
								&& $.GLOBAL.utils.isDate(queryObj.beginDate)) {
							queryObj.beginDate += " 00:00:00";
						}
						if (queryObj.endDate
								&& $.GLOBAL.utils.isDate(queryObj.endDate)) {
							queryObj.endDate += " 23:59:59";
						}
						return queryObj;
					},
					columns : [
							{
								width : 50,
								align : 'center',
								formatter : function(value, row, index) {
									return index + 1;
								}
							},
							{
								field : 'goodCode',
							},
							{
								field : 'goodName',
							},
							{
								field : 'priceYuan',
							},
							{
								field : 'specs',
							},
							{
								field : 'createDate',
							},
							{
								field : 'goodsId',
								align : 'center',
								formatter : function(value, row, index) {

									return '<a title="查看" href="' + urlPrefix + 'goods/productList?goodsId=' + row.goodsId + '" class="viewItem" data-id="' + row.goodsId + '"><i class="fa fa-eye" style="font-size:20px"></i></a>  '
										 + '<a title="修改" class="editItem" href="#" data-id="' + row.goodsId + '"><i class="fa fa-edit" style="font-size:20px"></i></a>';
								}
							} ]
				});
	}
};