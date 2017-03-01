// 
var productApp = {
	cateId : 0,	
    maxPics : 5,
    maxRelatedGoods : 8,
    currentSeq : 0,
    $mainForm : void 0,
	$navTabs : void 0,
    validator : void 0,
    isCoverText : "[封面图片]",
    notCoverText : "[设为封面]",
    $picList : $('.goodspic-list'),
    setCoverElement : "a.btnSetCover",
    coverElement : ".goodsCover",
    removeBtn : ".remove-btn",
    newPostUrl : "product/add",
    editPostUrl : "product/edit",
    defaultImg   : uiBase + "img/default_goods_image_240.gif",
    selectedGoods : {},
    listUrl    : "product/",
    relatedGoodsHtml : "<div class='popTreeDialogSection box-body form-horizontal'>"
		+" <div class='form-group '>"
        +"<div class='col-sm-5'><label class='control-label'>商品条码：</label><input type=text class='form-control' id='rProductCode' style='width:70%'></div>"
        +"<div class='col-sm-5'><label class='control-label'>商品名称：</label><input type=text class='form-control' id='rProductName' style='width:70%'></div>"
        +"<div class='col-sm-2'><button type=button id='rRefreshRecord' class='btn btn-primary'><i class='fa fa-search'></i>开始搜索</div>"
        +"</div>"
       
        +"<div class='row 　col-md-12　treeTable'>"
          // +"<div class='col-sm-12'>"
                +"<table class='tree_table' id='relatedGoodsList'>"
                +"<thead class='borderRow'>"
                +"<th  class='text-center'></th>"
                +"<th  class='text-center'>序号</th>"
                +"<th  class='text-center'>商品条码</th>"
                +"<th  class='text-center'>商品名称</th>"
                +"<th  class='text-center'>分类</th>"
                +"<th  class='text-center'>品牌</th>"
               
                +"</thead>"
                +"<tbody>"
                +"</tbody>"
                +"</table>"
            //+"</div>" 
        +"</div>" 
        +"</div>"
        ,
    relatedGoodsTr : "<tr data-id={{productId}} data-productName='{{productName}}'><td></td><td>{{productCode}}</td><td>{{productName}}</td><td>{{brandName}}</td><td>{{cateName}}</td><td><a  title=\"删除\" href=\"#\" class=\"removeItem\" data-id='{{productId}}'> <i class='fa fa-remove '  style='font-size:20px'></i></a></td></tr>",
    init : function(cateId, productId, productImage, attrsList, imagesList, productsList) {
    	this.cateId = cateId; 
    	this.productId = productId; 
        this.productImage = productImage; 
    	this.$navTabs = $('ul.nav-tabs');
        this.$mainForm = $('#mainForm');
        this.$goodspicList = $('.goodspic-list');
        this.$relatedGoodsListTable = $('#relatedGoodsListTable');
        this.defaultImageData = {showImageUrl : this.defaultImg, imageUrl : "", isCover : 0, CoverText : this.notCoverText, imageId: ""}; 
        this.initEvents();
        
        this.renderLists(attrsList, imagesList, productsList);   
        this.updateRelatedGoodsListSeq();
        
        //init sort
        $(".goodspic-list ul").sortable({connectWith:".connectList"}).disableSelection();
        return this;
        
    },
    renderLists : function(attrs, images, products) {
        var that = this, 
            isCover, 
            defaultData = {}
            ;
    	
        this.initAttrs = attrs; 
        this.initImages = images;
        this.initProducts = products; 
        
        _(attrs).each(function(attr, ik) {
        	that.$mainForm.find('.attrsList').find('input[value="{0}"][data-attrId]'.format(attr.attrValueId)).prop('checked', true);
       
        });
        _(images).each(function(image, ik) {
            isCover = 0;
            
            var defaultData = $.extend({id: that.currentSeq}, that.defaultImageData);
            if(that.productImage && that.productImage == image.imageUrl) {
            	image.isCover = 1;
            	image.CoverText = that.isCoverText;
            }
            if(image.imageUrl) {
            	image.showImageUrl = fileUrlPrefix + image.imageUrl;
            }
            
            var $newPic = template('picItemTpl', $.extend(defaultData, image));
            
            
            that.$picList.find('ul').append($newPic);
            that.currentSeq ++ ;
        });
        _(products).each(function(product, ik) {
            that.$relatedGoodsListTable.find('tbody').append(that.relatedGoodsTr.template(product));
        });
       
    },
    convertToReviewPage : function() {
       
        var that = this;
        
        _(attrsList).each(function(attr, ik) {
        	//that.$mainForm.find('input[value="{0}"]'.format(attr.attrValueId)).prop('checked', true);
        	var _selAttr = that.$mainForm.find('input[value="{0}"][data-attrId]'.format(attr.attrValueId));
        	_selAttr.parent().parent().text(_selAttr.parent().text());
        });
        
        //没有选中的项目
        var attrs = that.$mainForm.find('.attrsList').children('div.form-group');
        $.each(attrs, function() {
        	if($(this).find('input[data-attrId]').length>0) {
        		$(this).remove(); 
        	}
        });
        /*
        $('button.submitMainForm').off().text('返回').on('click', _(function() {
            window.location.href=urlPrefix + this.listUrl;
        }).bind(this));
        */
        $('.addRelatedGoods').off().remove();
        $('.addNewPic').off().remove();
        /*
        this.$goodspicList.find('.upload-btn').remove();
        this.$goodspicList.find('.remove-btn').remove();
        this.$goodspicList.find('.btnSetCover').off();
        this.$goodspicList.find('.upload-setDefault').each(function() {
            if($(this).find('.goodsCover').val()) {
                $(this).find('.btnSetCover').remove();
            }
        });
        */
        that.$picList.find('ul').empty();
        _(imagesList).each(function(image, ik) {
            isCover = 0;
            
            var defaultData = $.extend({id: that.currentSeq}, that.defaultImageData);
            if(that.productImage && that.productImage == image.imageUrl) {
            	image.isCover = 1;
            	image.CoverText = that.isCoverText;
            }
            if(image.imageUrl) {
            	image.showImageUrl = fileUrlPrefix + image.imageUrl;
            }
            
            var $newPic = template('picShowItemTpl', $.extend(defaultData, image));
            
            
            that.$picList.find('ul').append($newPic);
            that.currentSeq ++ ;
        });
        
        this.$relatedGoodsListTable.find("th:last").remove();
        this.$relatedGoodsListTable.find("tr").find("td:last").remove();
        $(".goodspic-list ul").sortable('destroy');
    },
    initEvents : function() {
        var that = this;
        this.validator = $('#mainForm').validate({
        	rules : {
        		productName : {
        			required : true
        		},
        		productCode : {
        			required : true
        		},
        		
        		weight : {
        			required : true,
        			digits  : true,
        			min : 1
        		},
        		guidePrice : {
        			required : true,
        			//number : true,
        			price : true,
        			max   : 9999999.99
         		},
         		goods : {
         			required : true
         		}
        	}, 
        	messages : {
        		productName : {
        			//required : "商品名称不能为空"
        		},
        		productCode : {
        			//required : "商品条码不能为空"
        		},
        		
        		weight : {
        			//required : "瓶数不能为空"
        		},
        		goods : {
        			required : "必选"
        		}
        	}
        	
        });
        this.initInputMask();
        $.pages.initDateTime();
        $('button.submitMainForm').on("click", _(this.submitForm).bind(this));
        $('.addNewPic').on('click', _(this.addNewPic).bind(this));
        $('.addRelatedGoods').on('click', _(this.addRelatedGoods).bind(this));
        $('.addItem').on('click', _(this.addItem).bind(this));
        // 商品图片ajax上传
        
        this.$mainForm.on('change', '.upload-btn input[type="file"]', function(){
            var id = $(this).attr('id');
            that.ajaxFileUpload(id);
        });
        //
        this.$mainForm.on("click", this.setCoverElement,  _(this.setCover).bind(this));
        this.$mainForm.on('click', this.removeBtn, _(this.removePic).bind(this));
        
        this.$relatedGoodsListTable.on('click', ".removeItem", _(this.removeRelatedGoods).bind(this));
        
    
    },
    initInputMask : function() {
    	$('#guidePrice').inputmask("mask", {
    		alias : "decimal",
    		rightAlignNumerics : false
    		
    	});
    	$('input[name="weight"]').inputmask({
    		"mask" : "9",
    		repeat : 4,
    		"greedy": false
    	});
    },
    submitForm : function() {
        
    	if(!this.validator.form()) {
    		
    		this.$navTabs.find('li:eq(0) a').tab('show') ;
    		this.$navTabs.find('li:eq(0)').addClass('has-error')
    	} else {
    		this.$navTabs.find('li:eq(0)').removeClass('has-error');
            var tempData = $('#mainForm').frmSerialize();
            
            this.doSave();
    	}
    },
    doSave : function() {
    	 var result = this.getPostData(),
    	     url    = this.newPostUrl,
    	 	 that   = this;
    	 if(this.productId) {
    		url = this.editPostUrl;
            result.productId = this.productId;
    	 }
    	 $('button.submitMainForm').prop('disabled', true).text('保存中....');
    	 $.ajax(
            { 
        		type         : 'post',
				url          : urlPrefix+url,
				dataType     : 'json',
				contentType  : 'application/json',
				data         : JSON.stringify(result)
		 })
		 .done(function(result) {
			if(result.code == "ACK") {
				$('button.submitMainForm').prop('disabled', true).text('保存成功');
				that.$mainForm .loadingInfo({
    				type : "success", 
    				text: message("admin.message.success"),
    				callBack : function() {
    					window.location.href=urlPrefix + that.listUrl;
    				}
    			});
			} 
		 }).always(function() {
			 $('button.submitMainForm').prop('disabled', false).text('保存');
		 });
    },
    //选择物料
    addItem : function(){
		var that = this;
		that.dialog =  BootstrapDialog.show({
	        title: '关联单瓶商品',
	        message: $(template('addEditTpl', {})),
	        draggable: true,
	        buttons: [{
	        	label: '确认',
	            cssClass: 'btn-primary',
	            action: function(dialog) {
	            	$("#relatedGoods").html("");
	            	var relatedGoods = $('#dataList').bootstrapTable('getSelections');
	            	if(relatedGoods!=""){
	            		var goodsId = relatedGoods[0].goodsId;
		            	var goodName = relatedGoods[0].goodName;
            	
		            	$("#relatedGoods").val(goodName).attr("data-id",goodsId).focus();
		            	$('input[name="goodId"]').val(goodsId);
		            	$(".addItem").html("<i class='fa fa-plus'></i> 重新选择");
	            	}
	            	dialog.close();
	            }
	        }, {
	        	label: '取消',
	            action: function(dialog) {
	                dialog.close();
	            }
	        }],
	        onshown : function() {
	        	var table = $.GLOBAL.utils.loadBootTable({
					table : $('#dataList'),
				    refreshBtn : $('#refreshRecord'),
				    url: 'product/search',
				    sidePagination:'server',
				    pagination : true,
				    queryParamsType: "limit", 
				    singleSelect : true,
				    queryAddParams: function() {
				    	return {
				    		goodCode : $('#goodCode').val(),
				    		goodName : $('#goodName').val(),
				    	}
				    },
				    columns: [
				        {
				            radio: true,
				        } ,
				        {
				        	formatter:function(value,row,index){  
		                        return index+1; 
		                    }  
		                } ,
		                {
		                    field: 'goodCode'  
		                } ,
		                {
		                    field: 'goodName'  
		                } ,
		                {
		                    field: 'priceYuan' ,
		                },
				        {
				            field: 'goodsId',
				        }						        						     
				     ]
				});
	        }
	    }); 
        that.dialog.getModalDialog().css('width', '700px');
	},
    
    addNewPic : function() {
    	if(this.$goodspicList.find('li').length >= this.maxPics) {
    		this.$mainForm.loadingInfo("error", "最多只能添加 "+ this.maxPics + " 张图片");
    		return false;
    	}
    	
        var $newPic = template('picItemTpl', $.extend({id: this.currentSeq}, this.defaultImageData));
        this.$picList.find('ul').append($newPic);
        this.currentSeq ++ ;
    },
    canAddRelatedGoods : function() {
        if(this.getCurrentRelatedGoodsIds().length >= this.maxRelatedGoods) {
            return false;
        }
        return true;
    },
    addRelatedGoods : function() {
        var that = this;
        if(!this.canAddRelatedGoods()) {
            this.$relatedGoodsListTable.loadingInfo('error', "关联商品最多只能添加{0}个".format(this.maxRelatedGoods));
            return false;
        }
        that.dialog =  BootstrapDialog.show({
            title: '关联商品',
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
            idField : "productId",
            pagination : true,
            pageSize : 10,
            url: 'product/list',
            sidePagination:'server',
            queryAddParams: function() { 
                return {
                    productName : $.trim($('#rProductName').val()),
                    productCode : $.trim($('#rProductCode').val()),
                    cateId      : "",
                    brandId     : "",
                    relateProductId   : that.productId
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
					width: 0,
					align: 'center',
                    formatter:function(value,row,index){  
                        return index+1; 
                    }
                } ,
                {
                    field: 'productCode'  
                } ,
                {
                    field: 'productName'  
                } ,
                {
                    field: 'cateName'  
                } ,
                {
                    field: 'brandName'  
                } 
            ]
            
        });
        this.relatedBootTable.$dataListTable.on("check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table", _(this.selectRelatedGoods).bind(this));
        
        this.relatedBootTable.$dataListTable.on("post-body.bs.table ", _(this.reCheckedRows).bind(this)); 
        
         
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
                    this.selectedGoods[row.productId] = row;
                } else {
                    delete this.selectedGoods[row.productId];
                }
            }
        } else if(arguments.length == 2) { //check-all uncheck-all
            var rows = arguments[1];
            _(rows).each(function(row, key) {
                if(row[0] == false) {
                    delete that.selectedGoods[row.productId];
                } else {
                    that.selectedGoods[row.productId] = row;
                }
            });
        } else {
            return false;
        }
        //this.selectedGoods = this.$dataListTable.bootstrapTable('getSelections');
        
         
        
    }, 
    reCheckedRows : function() {
        this.relatedBootTable.$dataListTable.bootstrapTable('checkBy', {field:'productId', values:_(_(this.selectedGoods).keys()).map(function(str, k) {
        	return parseInt(str);
        })});
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
    insertRelatedGoods : function() {
        var that = this;
        _(this.selectedGoods).each(function(value, key) {
            if(_.indexOf(that.getCurrentRelatedGoodsIds(), parseInt(key)) == -1 && that.canAddRelatedGoods()) {
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
    setCover : function(e) {
    	var $target = $(e.target);
    	
    	//wheather have imageurl 
    	var itsUrl = $target.closest('li.goodspic-upload').find('input[name^=picUrl]').val();
    	if(!_.isEmpty(itsUrl)) {
	    	this.$picList.find(this.setCoverElement).text(this.notCoverText).removeClass("isCover");
	    	this.$picList.find('input'+this.coverElement+'').val(0);
	    	$target.text(this.isCoverText).addClass("isCover"); 
	    	$target.parent().find('input'+this.coverElement+'').val(1);
    	}
    },
    /*
    http://localhost:8080/admin/web/main/addImage/{source}   souce值在global中定义  返回{"code":"ACK","message":"","data":"/wrw-admin/static/upload/image/brand/201605134e19bd834-7f3b-49e5-8e85-907489e836f6-source.jpg","nonBizError":false}
    
    */
    // 图片上传ajax
    ajaxFileUpload:   function (id, o) {
        var that = this;
        $('#img_' + id + '').attr('src', uiBase + "/img/loading.gif");
    
        $.ajaxFileUpload({
            url : $.GLOBAL.config.uploadUrl.template({source: uploadSourcesMap.product}), 
            secureuri : false,
            fileElementId : id,
            dataType : 'json',
            global : false,
            data : {},
            success : function (data, status) {
                if (data.code == "ACK") {
                    $('input[name="picUrl['+id+']"]').val(data.data);
                    $('#img_{{id}}'.template({id: id})).attr('src', fileUrlPrefix+data.data);
                    
                } else {
					$(window).loadingInfo("error", data.message);
				}
                
            }
        });
        return false;
 
    },
   
    removePic : function (e) {
    	var $target = $(e.target),
    		$goodUpload = $target.closest('.goodspic-upload'),
    		picName = $goodUpload.find('input[name="pic.name"]').val();
           
        $goodUpload.remove();
    },
    
    getPostData : function() {
       
        var formData = $('#mainForm').frmSerialize();
        var result = {
            brandId: formData['brandId'],
            price: formData['guidePrice'],
            productionDate: formData['productionDate'],
            productCode: formData['productCode'],
            productName: formData['productName'],
            brandName  : this.$mainForm.find('select[name=brandId]').find("option:selected").text(),
            url: formData['productUrl'],
            cateId: formData['cateId'],
            desc: _.isUndefined(formData['introduction']) ? "" : formData['introduction'] ,
            cateName:  	formData['cateName'],
            goodId : formData['goodId'],
            specNum:  	formData['weight']	,
            note   : formData['note'],	
            promotion   : formData['promotion'],	
        
        };
     
        
        if(result.productionDate && $.GLOBAL.utils.isDate(result.productionDate)) {
        	result.productionDate += " 00:00:00";
        }
        //attr
        var attrData = [];
        var attrs  = $('#mainForm').find('input[name^=attr]');
        $(attrs).each(function(key, value) {
            var attrId = $(value).attr('name').replace("attr[",'').replace("]", '');
            //attrData[attrId] = attrData[attrId] || [];
            if($(value).is(':checked')) {
                //attrData[attrId].push($(value).val());
            
                attrData.push({
                    attrId : $(value).data('attrid'),
                    attrName : $(value).data('attrname'),
                    attrValueId : $(value).val(),
                    attrValue: $(value).data('valueinfo')
                });
            }
        });
       
        result.listAttr = attrData;
        delete attrData;
        delete attrs;
        //file
        var picData = [];  
        var picIds  = $('#mainForm').find('input[name^=picId]');
        var sort = 0;
        //@TODO ignore the item with empty picUrl
        
        $(picIds).each(function(key, value ) {
            var seq = $(value).attr('name').replace("picId[",'').replace("]", '');
            var url =  $('#mainForm').find('input[name="picUrl['+seq+']"]').val();
            var picId =  $('#mainForm').find('input[name="picId['+seq+']"]').val();
            if(!_.isEmpty(url)) {
                picData.push({
                    imageUrl : url,
                    sort     : sort++,
                    imageId  : picId
                });
                if($('#mainForm').find('input[name="goodsCover['+seq+']"]').val()==1) {
                    result.image = url;
                }
            }
           
        });
        result.listImages = picData;
        var relatedGoods = [];
        _(this.getCurrentRelatedGoodsIds()).each(function(value, index) {
            relatedGoods.push(value);
        });
        result.relationShipIds = relatedGoods;
        return result;
    }
};
