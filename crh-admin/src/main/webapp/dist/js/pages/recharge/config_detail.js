$(function(){
	var configDetail = {	
		selectedGoods : {},
	    listUrl    : "product/",
	    relatedGoodsHtml : "<div class='popTreeDialogSection box-body '><div class='row form-group'>"
	                    +" <div class='form-group '>"
	        	        +"<div class='col-sm-6' style='padding-bottom:10px;'><label class='control-label'>优惠券编号：</label><input type=text class='form-control' id='rCouponId' style='width:60%'></div>"
	        	        +"<div class='col-sm-6' style='padding-bottom:10px;'><label class='control-label'>优惠券名称：</label><input type=text class='form-control' id='rCouponName' style='width:60%'></div>"
	        	        +"</div>"
	        	        +" <div class='form-group '>"
	        	        +"<div class='col-sm-6' style='padding-bottom:10px;'><label class='control-label'>优惠券面额：</label><input type=text class='form-control' id='rCouponValue' style='width:60%' ></div>"
	        	        +"<div class='col-sm-2' style='padding-bottom:10px;'><button type=button id='rRefreshRecord' class='btn btn-primary'><i class='fa fa-search'></i>开始搜索</div>"
	        	        +"</div>"
	        	        
	                    +"<div class='row treeTable'>"
	                        +"<div class='col-sm-12'>"
	                            +"<table class='tree_table' id='relatedGoodsList'>"
	                            +"<thead class='borderRow'>"
	                            +"<th width=50% class='text-center'></th>"
	                            +"<th width=50% class='text-center'>序号</th>"
	                            +"<th width=50% class='text-center'>优惠券编号</th>"
	                            +"<th width=50% class='text-center'>优惠券名称</th>"	  
	                            +"<th width=50% class='text-center'>优惠券面额</th>"	 
	                            +"</thead>"
	                            +"<tbody>"
	                            +"</tbody>"
	                            +"</table>"
	                        +"</div>" 
	                    +"</div>" 
	                    +"</div>"
	                    ,
	    relatedGoodsTr : "<tr data-id={{couponId}}><td>{{index}}</td><td>{{couponId}}</td><td class='couponName'>{{couponName}}</td><td class='value'>{{valueYuan}}</td><td><input class='form-control num' value='{{num}}'><span class='fieldError numError hidden'>请填写张数</span></td><td style='width:200px;'><a  title=\"删除\" href=\"#\" class=\"removeItem\" data-id='{{productId}}'> <i class='fa fa-trash'  style='font-size:20px'></i></a></td></tr>",
	    
			
		initEvents:function(){	
			
			var that = this;
	        this.initInputMask();
			$('#configSumbit').on('click',_(this.submitForm).bind(this));
	        this.$relatedGoodsListTable = $('#relatedGoodsListTable');			
	        $('.addRelatedGoods').on('click', _(this.addRelatedGoods).bind(this));
	        this.$relatedGoodsListTable.on('click', ".removeItem", _(this.removeRelatedGoods).bind(this));
	        $("#configForm").on('blur','.num',_(this.numChange).bind(this));

	      //验证
			this.validator = $('#configForm').validate({
	        	rules : {
	        		amountYuan : {
	        			required : true,
	        			digits:true,
	        			min:1,
	        			maxlength:7
	        		},
	        		note : {        			
	        			maxlength:12
	        		}
	        	}, 
	        	messages : {
	        		amountYuan : {
	        			required : "充值金额不能为空"
	        		}
	        	} 
	        	
	        });
		},
		
		initInputMask : function() {
	    	$('#rCouponId').inputmask({
	    		"mask" : "9",
	    		repeat : 8,
	    		"greedy": false
	    	});
	    },
		
		numChange : function(e) {
			if($(e.target).val()=="") {
				$(e.target).next(".numError").removeClass("hidden");
			}else {
				$(e.target).next(".numError").addClass("hidden");
			}
		},

		
		addRelatedGoods : function() {
	        var that = this;
	        
	        that.dialog =  BootstrapDialog.show({
	            title: '添加优惠券',
	            //type : BootstrapDialog.TYPE_DEFAULT,
	            message: $(this.relatedGoodsHtml),
	            draggable: true,
	            buttons: [{
	                label: '确认',
	                cssClass: 'btn-primary',
	                action: function(dialog) {
	                    that.insertRelatedGoods();
	                    dialog.close();
	                }
	            }, {
	                label: '取消',
	                action: function(dialog) {
	                    dialog.close();
	                }
	            }],
	            onshown : function() {
	                that.initRelatedTable();
	                that.initInputMask();

	                
	               
	            }
	       });
	       that.dialog.getModalDialog().css('width', '700px');
	    }, 
	    /* 
	    商品条码
	    商品名称
	    分类</t
	    品牌</t
	    */
	    initRelatedTable : function() {
	        var that = this;
	        this.relatedBootTable = $.GLOBAL.utils.loadBootTable({
	            table : $('#relatedGoodsList'),
	            //removeBtn : $('#removeRecord'),
	            refreshBtn : $('#rRefreshRecord'),
	            idField : "couponId",
	            pagination : true,
	            pageSize : 10,
	            url: 'rechargeConfig/getCoupon',
	            sidePagination:'server',
	            queryAddParams: function() {
	                return {
	                	couponId : $('#rCouponId').val(),
	                	couponName : $('#rCouponName').val(),
	                	value : $('#rCouponValue').val(),
	                }
	            },
	            columns: [
	                {
	                   
	                    align: 'center',
	                    checkbox: true,
	                    onClickCell : function(field, value, row, $element) {
	                        that.selectRelatedGoods(row, $element);
	                    }
	                } ,
	                {
	                    formatter:function(value,row,index){  
	                        return index+1; 
	                    }
	                } ,
	                {
	                    field: 'couponId'  
	                } ,
	                {
	                    field: 'couponName'  
	                },
	                {
	                    field: 'valueYuan'  
	                }
	            ]
	            
	        });
	        this.relatedBootTable.$dataListTable.on("check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table", _(this.selectRelatedGoods).bind(this));
	         
	    },
	    
	    /*
	     * @TODO need to validate with the max number of relatedGoods
	     */
	    selectRelatedGoods : function(/*event, row, element*/) {
	        var that = this;
	        if(arguments.length == 3) {
	            var row = arguments[1];
	            var element = arguments[2];
	            if(!_.isUndefined(element) && element.get(0).type == "checkbox") {
	                if(element.is(":checked") ) {
	                    this.selectedGoods[row.couponId] = row;
	                } else {
	                    delete this.selectedGoods[row.couponId];
	                }
	            }
	        } else if(arguments.length == 2) { //check-all uncheck-all
	            var rows = arguments[1];
	            _(rows).each(function(row, key) {
	                if(row[0] == false) {
	                    delete that.selectedGoods[row.couponId];
	                } else {
	                    that.selectedGoods[row.couponId] = row;
	                }
	            });
	        } else {
	            return false;
	        }
	        //this.selectedGoods = this.$dataListTable.bootstrapTable('getSelections');
	    },
	       
	    
	    removeRelatedGoods : function(e) {
	        var $target = $(e.target),
	            that = this;
	        
	        $target.closest('tr').remove();    
	        this.updateRelatedGoodsListSeq();    
	    },
	    getCurrentRelatedGoodsIds : function() {
	        var ids = [];
	        this.$relatedGoodsListTable.find('tbody>tr').each(function() {
	            ids.push(parseInt($(this).data('id')));
	        });
	        return ids;
	    },
	    getCurrentRelatedGoodsCouponNames : function() {
	        var couponNames = [];
	        this.$relatedGoodsListTable.find('tbody>tr').each(function() {
	        	couponNames.push($.trim($(this).find(".couponName").text()));
	        });
	        return couponNames;
	    },
	    getCurrentRelatedGoodsValues : function() {
	        var values = [];
	        this.$relatedGoodsListTable.find('tbody>tr').each(function() {
	        	values.push($.trim($(this).find(".value").text()));
	        });
	        return values;
	    },
	    
	    
	    getCurrentRelatedGoodsNums : function() {
	        var nums = [];
	        this.$relatedGoodsListTable.find('tbody>tr').each(function() {
	        	nums.push(parseInt($(this).find("input").val()));
	        });
	        return nums;
	    },
	    insertRelatedGoods : function() {
	        var that = this;
	        _(this.selectedGoods).each(function(value, key) {
	            if(_.indexOf(that.getCurrentRelatedGoodsIds(), parseInt(key)) == -1) {
	                that.$relatedGoodsListTable.find('tbody').append(that.relatedGoodsTr.template(value));
	            }
	        });
	        this.selectedGoods = {};
	        this.updateRelatedGoodsListSeq();
	        
	    },
	    updateRelatedGoodsListSeq : function() {
	    	this.$relatedGoodsListTable.find('tbody>tr').each(function() {
	    		$(this).find("td:first").text($(this).index() + 1);
	        });
	    },
		
	    renderLists : function(products) {
	        var that = this;	   
	        var index = 1;
	        _(products).each(function(product, ik) {
	        	product.index = index;
	            that.$relatedGoodsListTable.find('tbody').append(that.relatedGoodsTr.template(product));
	            index++;
	        });
	    },
	    
	    validateNum : function(){
	    	var flag = true;
	    	var nums = $(".num");
	    	var numError = "请输入张数";
	    	var digitalError = "只允许输入零或正整数";
	    	var lengthError = "长度不允许大于4";
	    	var z= /^[0-9]*$/;
	    	var reg=/^\d{1,4}$/
	    	
	    	
	    	_(nums).each(function(num,index){
	    		if($(num).val()=="") {
	    			$(num).next(".numError").html(numError);
	    			$(num).next(".numError").removeClass("hidden");
	    			flag = false;
	    		}
	    		else if(!z.test($(num).val().trim())) {
	    			$(num).next(".numError").html(digitalError);
	    			$(num).next(".numError").removeClass("hidden");
	    			flag = false;
	    		}
	    		else if(!reg.test($(num).val().trim())) {
	    			$(num).next(".numError").html(lengthError);
	    			$(num).next(".numError").removeClass("hidden");
	    			flag = false;
	    		}
	    	});
	    	return flag;
	    },
	    
	    submitForm : function(){
			var formFlag = this.validator.form();
	    	var numFlag = this.validateNum();
	    	
	    	if(!formFlag||!numFlag) {
				return false;
			}else {
				this.doSave();
			}
	    },
	    
	    doSave : function(){
	    	var that = this;
			var relatedGoods = [];
	        _(that.getCurrentRelatedGoodsIds()).each(function(value, index) {
	            relatedGoods.push({couponId : value, couponName : "",valueYuan : "",num : ""});
	        });
	      
	        _(that.getCurrentRelatedGoodsCouponNames()).each(function(value, index) {
	            relatedGoods[index].couponName = value;
	        });
	        
	        _(that.getCurrentRelatedGoodsValues()).each(function(value, index) {
	            relatedGoods[index].valueYuan = value;
	        });
	        
	        _(that.getCurrentRelatedGoodsNums()).each(function(value, index) {
	            relatedGoods[index].num = value;
	        });
	        
	        
	        var prodList = relatedGoods;
	        
			var configAddForm = {
					configId   : $("#configId").val(),
					amountYuan     : $("#amountYuan").val(),
					note       : $("#note").val(),
					couponDtos : prodList
			};
			$.ajax({
				type: "POST",
				url: urlPrefix + "rechargeConfig/save",
				contentType: "application/json;charset=utf-8",
				data: JSON.stringify(configAddForm),
				dataType: "json",
				success: function(message){
					if(message.code=="ACK"){
						window.location.href= urlPrefix + "rechargeConfig/toAdd";
					}
				},
				error:function(message){
					//alert('shibai');
				}
			});
	    },
			
		init:function(productsList){
			var _self = this;
			_self.initEvents();
			_self.renderLists(productsList);   
		}
	}.init(productsList);

});